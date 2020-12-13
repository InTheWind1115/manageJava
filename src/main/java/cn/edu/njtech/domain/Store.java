package cn.edu.njtech.domain;

import cn.edu.njtech.configuration.RsaKeyProperties;
import cn.edu.njtech.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class Store {

    String token;

    @Autowired
    static UsersService usersService;

    private static RsaKeyProperties prop;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static RsaKeyProperties getProp() {
        return prop;
    }

    public void setProp(RsaKeyProperties prop) {
        this.prop = prop;
    }

    public static UsersService getUsersService() {
        return usersService;
    }
}
