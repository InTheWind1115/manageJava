package cn.edu.njtech.mapper;


import cn.edu.njtech.domain.SysUser;

public interface UserMapper {
    public SysUser findByUsername(String username);

    int updateMessageByUserId(String formId, String userId);

    int updateMessages(String formId, int status, String department, String academy);

    int replaceUserMessageById(String username, String formId);

    String selectUserMessage(String username);
}
