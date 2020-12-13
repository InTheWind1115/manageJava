package cn.edu.njtech.controller.module1;

import cn.edu.njtech.configuration.RsaKeyProperties;
import cn.edu.njtech.domain.Payload;
import cn.edu.njtech.domain.Store;
import cn.edu.njtech.domain.SysRole;
import cn.edu.njtech.domain.SysUser;
import cn.edu.njtech.domain.dao.Form;
import cn.edu.njtech.domain.dao.FormRecord;
import cn.edu.njtech.domain.dao.UserInfo;
import cn.edu.njtech.domain.json.Result;
import cn.edu.njtech.service.UsersService;
import cn.edu.njtech.utils.JwtUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class Module1 {

    @Autowired
    private Store store;

    @Resource
    UsersService usersService;


    @RequestMapping("/test")
    @ResponseBody
    public String test() throws JsonProcessingException {
        System.out.println(store.getToken());
        return "sdfjsdfkjsldf";
    }

    @RequestMapping("/product")
    @ResponseBody
    public String test2() throws JsonProcessingException {
        return "product-list";
    }

    @GetMapping("/manageusersinfo")
    @ResponseBody
    public String getUsersData(HttpServletRequest request) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Result mess = new Result();
        String result;
        int status = Integer.parseInt(request.getParameter("status"));
        String academy = request.getParameter("academy");
        String department = request.getParameter("department");

        String token = request.getHeader("Authorization");
        RsaKeyProperties prop = store.getProp();
        Payload<SysUser> payload = JwtUtils.getInfoFromToken(token.replace("Bearer ", ""), prop.getPublicKey(), SysUser.class);
        SysUser user = payload.getUserInfo();
        int myLimit = 0;
        List<SysRole> roleList = user.getRoles();
        myLimit = getMyLimit(roleList);

        if (myLimit == 4 || (myLimit >= status && status != -1)) {
            mess.setSuccess(false);
            mess.setMessage("您的权限不够！");
            mess.setCode(200);
            result = objectMapper.writeValueAsString(mess);
            return result;
        }
        List users = usersService.queryUsers(status, academy, department, myLimit);
        mess.setSuccess(true);
        mess.setMessage("操作成功!");
        mess.setCode(200);
        mess.setResult(users);
        result = objectMapper.writeValueAsString(mess);
        return result;
    }

    private int getMyLimit(List<SysRole> roleList) {
        int max = 4;
        if (roleList != null) {
            for (int i = 0; i < roleList.size(); i++) {
                String roleName = roleList.get(i).getRoleName();
                int limit = 4;
                switch (roleName) {
                    case "ROLE_USER":
                        limit = 4;
                        break;
                    case "ROLE_TEACHER":
                        limit = 3;
                        break;
                    case "ROLE_ADMIN":
                        limit = 2;
                        break;
                    case "ROLE_SUPERADMIN":
                        limit = 1;
                        break;
                }
                if (limit < max)
                    max = limit;
            }
        }
        return max;
    }

    @GetMapping("/manageuserinfo")
    @ResponseBody
    public String getUserData(HttpServletRequest request) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Result mess = new Result();
        String result;
        String user_id = request.getParameter("userid");

        String token = request.getHeader("Authorization");
        RsaKeyProperties prop = store.getProp();
        Payload<SysUser> payload = JwtUtils.getInfoFromToken(token.replace("Bearer ", ""), prop.getPublicKey(), SysUser.class);
        SysUser user = payload.getUserInfo();
        int myLimit = 0;
        List<SysRole> roleList = user.getRoles();
        myLimit = getMyLimit(roleList);

        // 权限为4直接排除，权限不为1且权限>=想要查询的身份的也排除
        if (myLimit == 4) {
            mess.setSuccess(false);
            mess.setMessage("您的权限不够！");
            mess.setCode(200);
            result = objectMapper.writeValueAsString(mess);
            return result;
        }

        UserInfo userInfo = usersService.queryUserByUserId(user_id);

        if (userInfo.getStatus() <= myLimit) {
            mess.setSuccess(false);
            mess.setMessage("您的权限不够！");
            mess.setCode(200);
            result = objectMapper.writeValueAsString(mess);
            return result;
        }

        mess.setSuccess(true);
        mess.setMessage("操作成功！");
        mess.setCode(200);
        mess.setResult(userInfo);
        result = objectMapper.writeValueAsString(mess);
        return result;
    }

    @GetMapping("/updateuserlimit")
    @ResponseBody
    public String updateUserLimit(HttpServletRequest request) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Result mess = new Result();
        String result;
        String userId = request.getParameter("userid");
        int limit = Integer.parseInt(request.getParameter("limit"));
        String token = request.getHeader("Authorization");
        RsaKeyProperties prop = store.getProp();
        Payload<SysUser> payload = JwtUtils.getInfoFromToken(token.replace("Bearer ", ""), prop.getPublicKey(), SysUser.class);
        SysUser user = payload.getUserInfo();
        int myLimit = 0;
        List<SysRole> roleList = user.getRoles();
        myLimit = getMyLimit(roleList);

        // 权限为4直接排除
        if (myLimit == 4 || myLimit > limit) {
            mess.setSuccess(false);
            mess.setMessage("您的权限不够！");
            mess.setCode(403);
            result = objectMapper.writeValueAsString(mess);
            return result;
        }

        int userRole = usersService.queryUserRoleByUserId(userId);

        if (userRole <= myLimit) {
            mess.setSuccess(false);
            mess.setMessage("您的权限不够！");
            mess.setCode(403);
            result = objectMapper.writeValueAsString(mess);
            return result;
        }

        usersService.updateUserRoleByUserId(userId, limit);

        mess.setSuccess(true);
        mess.setMessage("操作成功！");
        mess.setCode(200);
        result = objectMapper.writeValueAsString(mess);
        return result;
    }

    @GetMapping("/updateuserslimit")
    @ResponseBody
    public String updateUsersLimit(HttpServletRequest request) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Result mess = new Result();
        String result;
        int status = Integer.parseInt(request.getParameter("status"));
        String academy = request.getParameter("academy");
        String department = request.getParameter(("department"));
        int limit = Integer.parseInt(request.getParameter("limit"));
        String token = request.getHeader("Authorization");
        RsaKeyProperties prop = store.getProp();
        Payload<SysUser> payload = JwtUtils.getInfoFromToken(token.replace("Bearer ", ""), prop.getPublicKey(), SysUser.class);
        SysUser user = payload.getUserInfo();
        int myLimit = 0;
        List<SysRole> roleList = user.getRoles();
        myLimit = getMyLimit(roleList);

        // 权限为4直接排除
        if (myLimit == 4 || myLimit > limit) {
            mess.setSuccess(false);
            mess.setMessage("您的权限不够！");
            mess.setCode(403);
            result = objectMapper.writeValueAsString(mess);
            return result;
        }

        UserInfo userInfo = new UserInfo();
        if (status != -1)
            userInfo.setStatus(status);
        if (!"-1".equals(academy))
            userInfo.setAcademy(academy);
        if (!"-1".equals(department))
            userInfo.setDepartment(department);

        usersService.updateUsersRoleByUserId(userInfo, limit, myLimit);

        mess.setSuccess(true);
        mess.setMessage("操作成功！");
        mess.setCode(200);
        result = objectMapper.writeValueAsString(mess);
        return result;
    }

    @GetMapping("/formtouser")
    @ResponseBody
    public String formToUser(HttpServletRequest request) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Result mess = new Result();
        String result;
        String userId = request.getParameter("userid");
        String formContent = request.getParameter("formcontent");
        String token = request.getHeader("Authorization");
        RsaKeyProperties prop = store.getProp();
        Payload<SysUser> payload = JwtUtils.getInfoFromToken(token.replace("Bearer ", ""), prop.getPublicKey(), SysUser.class);
        SysUser user = payload.getUserInfo();
        int myLimit = 0;
        List<SysRole> roleList = user.getRoles();
        myLimit = getMyLimit(roleList);

        // 权限为4直接排除
        if (myLimit == 4 || myLimit == 3) {
            mess.setSuccess(false);
            mess.setMessage("您的权限不够！");
            mess.setCode(403);
            result = objectMapper.writeValueAsString(mess);
            return result;
        }

        int userRole = usersService.queryUserRoleByUserId(userId);

        // 不能向同等级或者等级比自己高的用户发送表格
        if (userRole <= myLimit) {
            mess.setSuccess(false);
            mess.setMessage("您的权限不够！");
            mess.setCode(403);
            result = objectMapper.writeValueAsString(mess);
            return result;
        }

        String formId = randomFormId();
        Form form = new Form();
        form.setFormId(formId);
        form.setPublisher(user.getUsername());
        Date date = new Date();
        long timeNow = date.getTime();
        date.setTime(timeNow);
        form.setPublishTime(date);
        form.setPublishContent(formContent);
        form.setReceiver(userId);
        form.setIsSuper(user.getRoles().get(0).getRoleName() == "ROLE_SUPERADMIN"? 1 : 0);
        usersService.insertForm(form);
        usersService.updateUserMessageByUserId(formId, userId);

        // 通过websocket通知用户的填写

        // 返回操作成功提示
        mess.setSuccess(true);
        mess.setMessage("操作成功！");
        mess.setCode(200);
        result = objectMapper.writeValueAsString(mess);
        return result;
    }

    @GetMapping("/formtousers")
    @ResponseBody
    public String formToUsers(HttpServletRequest request) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Result mess = new Result();
        String result;
        int status = Integer.parseInt(request.getParameter("status"));
        String academy = request.getParameter("academy");
        String department = request.getParameter("department");
        String formContent = request.getParameter("formcontent");
        String token = request.getHeader("Authorization");
        RsaKeyProperties prop = store.getProp();
        Payload<SysUser> payload = JwtUtils.getInfoFromToken(token.replace("Bearer ", ""), prop.getPublicKey(), SysUser.class);
        SysUser user = payload.getUserInfo();
        int myLimit = 0;
        List<SysRole> roleList = user.getRoles();
        myLimit = getMyLimit(roleList);

        // 权限为4和3(学生和老师)直接排除 或者 部门管理员向学校管理员发布表格
        if (myLimit == 4 || myLimit == 3 || (myLimit == 2 && status == 1)) {
            mess.setSuccess(false);
            mess.setMessage("您的权限不够！");
            mess.setCode(403);
            result = objectMapper.writeValueAsString(mess);
            return result;
        }

        String formId = randomFormId();
        Form form = new Form();
        form.setFormId(formId);
        form.setPublisher(user.getUsername());
        Date date = new Date();
        long timeNow = date.getTime();
        date.setTime(timeNow);
        form.setPublishTime(date);
        form.setPublishContent(formContent);
        form.setReceiver("" + status + "," + department + "," + academy);
        form.setIsSuper(user.getRoles().get(0).getRoleName() == "ROLE_SUPERADMIN"? 1 : 0);
        usersService.insertForm(form);
        usersService.updateUsersMessageByUserId(formId, status, department, academy);

        // 通过websocket通知用户的填写

        // 返回操作成功提示
        mess.setSuccess(true);
        mess.setMessage("操作成功！");
        mess.setCode(200);
        result = objectMapper.writeValueAsString(mess);
        return result;
    }

    @GetMapping("/formwrite")
    @ResponseBody
    public String formWrite(HttpServletRequest request) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Result mess = new Result();
        String result;
        String formId = request.getParameter("formid");
        String formContent = request.getParameter("formcontent");
        String token = request.getHeader("Authorization");
        RsaKeyProperties prop = store.getProp();
        Payload<SysUser> payload = JwtUtils.getInfoFromToken(token.replace("Bearer ", ""), prop.getPublicKey(), SysUser.class);
        SysUser user = payload.getUserInfo();
        FormRecord formRecord = new FormRecord();
        formRecord.setFormId(formId);
        formRecord.setWriter(user.getUsername());
        Date date = new Date();
        long timeNow = date.getTime();
        date.setTime(timeNow);
        formRecord.setWriteTime(date);
        formRecord.setWriteContent(formContent);

        // 写入数据库
        int number = usersService.insertFormRecord(formRecord);
        int number2 = 0;

        // 写入操作成功
        if (number != 0) {
            number2 = usersService.replaceUserMessageById(user.getUsername(), formId);
        }

        // 操作失败
        if (number2 * number == 0) {
            mess.setSuccess(false);
            mess.setMessage("操作失败！");
            mess.setCode(500);
            result = objectMapper.writeValueAsString(mess);
            return result;
        }
        mess.setSuccess(true);
        mess.setMessage("操作成功！");
        mess.setCode(200);
        result = objectMapper.writeValueAsString(mess);
        return result;
    }

    @GetMapping("/aboutform")
    @ResponseBody
    public String aboutForm(HttpServletRequest request) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Result mess = new Result();
        String result;
        String formId = request.getParameter("formid");
        String token = request.getHeader("Authorization");
        RsaKeyProperties prop = store.getProp();
        Payload<SysUser> payload = JwtUtils.getInfoFromToken(token.replace("Bearer ", ""), prop.getPublicKey(), SysUser.class);
        SysUser user = payload.getUserInfo();
        int myLimit = 0;
        List<SysRole> roleList = user.getRoles();
        myLimit = getMyLimit(roleList);

        // 权限为4和3(学生和老师)直接排除
        if (myLimit == 4 || myLimit == 3) {
            mess.setSuccess(false);
            mess.setMessage("您的权限不够！");
            mess.setCode(403);
            result = objectMapper.writeValueAsString(mess);
            return result;
        }

        List<FormRecord> formRecords = usersService.selectRecordForms(formId);

        if (formRecords.size() != 0) {
            mess.setSuccess(true);
            mess.setMessage("操作成功");
            mess.setCode(200);
            mess.setResult(formRecords);
            result = objectMapper.writeValueAsString(mess);
            return result;
        }
        mess.setSuccess(false);
        mess.setMessage("未知错误！");
        mess.setCode(500);
        result = objectMapper.writeValueAsString(mess);
        return result;
    }


    @GetMapping("/formselect")
    @ResponseBody
    public String formSelect(HttpServletRequest request) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Result mess = new Result();
        String result;
        String token = request.getHeader("Authorization");
        RsaKeyProperties prop = store.getProp();
        Payload<SysUser> payload = JwtUtils.getInfoFromToken(token.replace("Bearer ", ""), prop.getPublicKey(), SysUser.class);
        SysUser user = payload.getUserInfo();
        int myLimit = 0;
        List<SysRole> roleList = user.getRoles();
        myLimit = getMyLimit(roleList);

        // 权限为4和3(学生和老师)直接排除
        if (myLimit == 4 || myLimit == 3) {
            mess.setSuccess(false);
            mess.setMessage("您的权限不够！");
            mess.setCode(403);
            result = objectMapper.writeValueAsString(mess);
            return result;
        }

        List<Form> forms = usersService.selectForms();

        if (forms.size() != 0) {
            mess.setSuccess(true);
            mess.setMessage("操作成功");
            mess.setCode(200);
            mess.setResult(forms);
            result = objectMapper.writeValueAsString(mess);
            return result;
        }
        mess.setSuccess(false);
        mess.setMessage("未知错误！");
        mess.setCode(500);
        result = objectMapper.writeValueAsString(mess);
        return result;
    }

    public String randomFormId() {
        String res = "";
        for (int i = 0; i < 20; i++) {
            res += new Random().nextInt(10);
        }
        return res;
    }

    @GetMapping("/selectreceive")
    @ResponseBody
    public String selectReceive(HttpServletRequest request) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Result mess = new Result();
        String result;
        String token = request.getHeader("Authorization");
        RsaKeyProperties prop = store.getProp();
        Payload<SysUser> payload = JwtUtils.getInfoFromToken(token.replace("Bearer ", ""), prop.getPublicKey(), SysUser.class);
        SysUser user = payload.getUserInfo();
        String messages = usersService.queryUserMessage(user.getUsername());
        String[] formIds = messages.split(",");
        List<Form> forms = new ArrayList<>();
        for (int i = 0; i < formIds.length; i++) {
            if (formIds[i].length() == 20) {
                forms.add(usersService.queryFormByFormId(formIds[i]));
            }
        }
        mess.setSuccess(true);
        mess.setMessage("操作成功!");
        mess.setCode(200);
        mess.setResult(forms);
        result = objectMapper.writeValueAsString(mess);
        return result;
    }
}
