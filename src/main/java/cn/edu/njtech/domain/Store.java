package cn.edu.njtech.domain;

import cn.edu.njtech.configuration.RsaKeyProperties;
import org.springframework.stereotype.Component;

@Component
public class Store {

    String token;

    RsaKeyProperties prop;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public RsaKeyProperties getProp() {
        return prop;
    }

    public void setProp(RsaKeyProperties prop) {
        this.prop = prop;
    }
}
