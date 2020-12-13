package cn.edu.njtech.filter;

import cn.edu.njtech.configuration.RsaKeyProperties;
import cn.edu.njtech.controller.module1.Module1;
import cn.edu.njtech.domain.SysRole;
import cn.edu.njtech.domain.SysUser;
import cn.edu.njtech.domain.json.Result;
import cn.edu.njtech.utils.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    private RsaKeyProperties prop;

    public TokenLoginFilter(AuthenticationManager authenticationManager, RsaKeyProperties prop) {
        this.authenticationManager = authenticationManager;
        this.prop = prop;
    }

    /**
     *接收并解析用户凭证，出現错误时，返回json数据前端
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) {
        try {
            //将json格式请求体转成JavaBean对象
            SysUser user = new ObjectMapper().readValue(req.getInputStream(), SysUser.class);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUsername(),
                            user.getPassword()
                    )
            );
        } catch (Exception e) {
            e.printStackTrace();
            try {
                //如果认证失败，提供自定义json格式异常
                res.setContentType("application/json;charset=utf-8");
//                res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                res.setStatus(HttpServletResponse.SC_OK);
                PrintWriter out = res.getWriter();
                Result result = new Result();
                result.setSuccess(false);
                result.setMessage("账号或密码错误!");
                result.setCode(401);
                out.write(new ObjectMapper().writeValueAsString(result));
                out.flush();
                out.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            throw new RuntimeException(e);
        }
    }

    /*** 用户登录成功后，生成token,并且返回json数据给前端 */
    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) {
        //得到当前认证的用户对象
        SysUser user = new SysUser();
        user.setUsername(auth.getName());
        user.setRoles((List<SysRole>) auth.getAuthorities());
        //json web token构建
        String token = JwtUtils.generateTokenExpireInMinutes(user, prop.getPrivateKey(), 24 * 60);
        //返回token
        res.addHeader("Authorization", "Bearer " + token);
        try {
            //登录成功時，返回json格式进行提示
            res.setContentType("application/json;charset=utf-8");
            res.setStatus(HttpServletResponse.SC_OK);
            PrintWriter out = res.getWriter();
//            Map<String, Object> map = new HashMap<String, Object>();
//            map.put("code", HttpServletResponse.SC_OK);
//            map.put("message", "登陆成功！");

            Result result = new Result();
            result.setSuccess(true);
            result.setMessage(user.getUsername());
            result.setCode(200);
            result.setResult(user.getAuthorities());
            out.write(new ObjectMapper().writeValueAsString(result));
            out.flush();
            out.close();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}