package cn.edu.njtech.controller.module1;

import cn.edu.njtech.configuration.RsaKeyProperties;
import cn.edu.njtech.domain.GETApplicationContext;
import cn.edu.njtech.domain.Payload;
import cn.edu.njtech.domain.Store;
import cn.edu.njtech.domain.SysUser;
import cn.edu.njtech.domain.dao.Form;
import cn.edu.njtech.domain.json.Result;
import cn.edu.njtech.service.UsersService;
import cn.edu.njtech.utils.JwtUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/mywebsocket/{user}")
@Component
public class MyWebSocket {

    private static Map<String, MyWebSocket> onlineUsers = new ConcurrentHashMap<>();

    private Session session;

    @OnOpen
    public void onOpen(Session session, @PathParam("user") String username) throws IOException {

        ApplicationContext ctx = GETApplicationContext.getApplicationContext();
        UsersService usersService = ctx.getBean(UsersService.class);

        System.out.println(usersService + "!!!!!");
        System.out.println(username);
        ObjectMapper objectMapper = new ObjectMapper();
        Result mess = new Result();
        String result;
        this.session = session;
        onlineUsers.put(username, this);
        String messages = usersService.queryUserMessage(username);
        String[] formIds = messages.split(",");
        List<Form> forms = new ArrayList<>();
        for (int i = 0; i < formIds.length; i++) {
            if (formIds[i] != "") {
                forms.add(usersService.queryFormByFormId(formIds[i]));
            }
        }
        mess.setSuccess(true);
        mess.setMessage("操作成功!");
        mess.setCode(200);
        mess.setResult(forms);
        result = objectMapper.writeValueAsString(mess);
        this.session.getBasicRemote().sendText(result);
        System.out.println("hello websocket");
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println(message);
    }

    @OnError
    public void onError(Session session, Throwable error) {

    }

}
