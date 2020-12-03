package cn.edu.njtech;

import cn.edu.njtech.configuration.RsaKeyProperties;
import cn.edu.njtech.domain.Store;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@MapperScan(basePackages = "cn.edu.njtech.mapper")
@EnableConfigurationProperties(RsaKeyProperties.class)
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
