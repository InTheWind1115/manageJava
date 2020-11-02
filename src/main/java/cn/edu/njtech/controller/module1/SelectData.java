package cn.edu.njtech.controller.module1;

import cn.edu.njtech.domain.json.Result;
import cn.edu.njtech.service.UsersService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.LinkedList;

@Controller
@RequestMapping("/njtech")
public class SelectData {

    @Autowired
    UsersService usersService;

    @GetMapping("/users")
    @ResponseBody
    public String getData(HttpServletRequest request) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Result mess = new Result();
        String result;
        Byte status = Byte.parseByte(request.getParameter("status"));
        String academy = request.getParameter("academy");
        String department = request.getParameter("department");

        HttpSession httpSession = request.getSession();

        // 判断登录状态
        String userId = (String) httpSession.getAttribute("userId");
        if (userId == null) {
            mess.setSuccess(false);
            mess.setMessage("您还没有登录，请登录后再次尝试！");
            mess.setCode(200);
            result = objectMapper.writeValueAsString(mess);
            return result;
        }

        // 判断身份
        Byte myLimit = Byte.parseByte((String) httpSession.getAttribute("myStatus")); // myStatus为当前用户的身份
        // 权限为4直接排除，权限不为1且权限>=想要查询的身份的也排除
        if (myLimit == 4 || (myLimit != 1 && myLimit >= status)) {
            mess.setSuccess(false);
            mess.setMessage("您的权限不够！");
            mess.setCode(200);
            result = objectMapper.writeValueAsString(mess);
            return result;
        }
        LinkedList users = usersService.queryUsers(status, academy, department, myLimit);
        mess.setSuccess(true);
        mess.setMessage("操作成功!");
        mess.setCode(200);
        mess.setResult(users);
        result = objectMapper.writeValueAsString(mess);
        return result;
    }
}
