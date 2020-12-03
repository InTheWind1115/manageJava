package cn.edu.njtech;

import cn.edu.njtech.utils.RsaUtils;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class RsaUtilsTest {
    private String publicFile = "E:\\auth_key\\rsa_key.pub";
    private String privateFile = "E:\\auth_key\\rsa_key";
    @Test
    public void generateKey() throws Exception {
        RsaUtils.generateKey(publicFile, privateFile, "heima", 2048);
    }

    @Test
    public void generatePass() throws Exception {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        System.out.println(bCryptPasswordEncoder.encode("zhangzhenzhen"));
    }

    @Test
    public void randomFormId() {
        String res = "";
        for (int i = 0; i < 20; i++) {
            res += new Random().nextInt(10);
        }
        System.out.println(res);
    }

    @Test
    public void testDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        Date date = new Date();
        System.out.println(dateFormat.format(date.getTime()));
    }
}