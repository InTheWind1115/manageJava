package cn.edu.njtech.mapper;


import cn.edu.njtech.domain.SysUser;

public interface UserMapper {
    public SysUser findByUsername(String username);

    int updateMessageByUserId(String formId, String userId);
}
