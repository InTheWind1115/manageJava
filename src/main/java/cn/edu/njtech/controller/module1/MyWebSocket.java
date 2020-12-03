package cn.edu.njtech.controller.module1;

import cn.edu.njtech.configuration.RsaKeyProperties;
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
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/mywebsocket")
@Component
public class MyWebSocket {

    @Autowired
    UsersService usersService;

    @Autowired
    private Store store;

    private static Map<String, MyWebSocket> onlineUsers = new ConcurrentHashMap<>();

    private Session session;

    @OnOpen
    public void onOpen(Session session) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Result mess = new Result();
        String result;
        String token = store.getToken();
        RsaKeyProperties prop = store.getProp();
        Payload<SysUser> payload = JwtUtils.getInfoFromToken(token.replace("Bearer ", ""), prop.getPublicKey(), SysUser.class);
        SysUser user = payload.getUserInfo();
        this.session = session;
        onlineUsers.put(user.getUsername(), this);
        String messages = usersService.queryUserMessage(user.getUsername());
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
    }

    @OnMessage
    public void onMessage(String message, Session session) {

    }

}
