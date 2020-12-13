package cn.edu.njtech.configuration;

import cn.edu.njtech.domain.Store;
import cn.edu.njtech.filter.TokenLoginFilter;
import cn.edu.njtech.filter.TokenVerifyFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    // qualifier是自己添加的
    @Qualifier("userServiceImpl")
    @Autowired
    private UserDetailsService myCustomUserService;
    @Autowired
    private RsaKeyProperties prop;

    @Autowired
    private Store store;

    @Bean
    public BCryptPasswordEncoder myPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //关闭跨站请求防护
                .cors().and().csrf().disable()
                //允许不登陆就可以访问的方法，多个用逗号分隔
                .authorizeRequests()
                .antMatchers("/mywebsocket/**").permitAll()
                .antMatchers("/product").hasAnyRole("USER")
                .antMatchers("/manageuserinfo").permitAll()
                .antMatchers("/manageusersinfo").permitAll()
                //其他的需要授权后访问
                .anyRequest().authenticated()
                .and()
                //增加自定义认证过滤器
                .addFilter(new TokenLoginFilter(authenticationManager(), prop))
                //增加自定义验证认证过滤器
                .addFilter(new TokenVerifyFilter(authenticationManager(), prop, store))
                // 前后端分离是无状态的，不用session了，直接禁用。
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        //UserDetailsService类
        auth.userDetailsService(myCustomUserService)
                //加密策略
                .passwordEncoder(myPasswordEncoder());
    }
}