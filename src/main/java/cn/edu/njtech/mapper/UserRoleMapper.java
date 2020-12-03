package cn.edu.njtech.mapper;

import cn.edu.njtech.domain.dao.UserInfo;

public interface UserRoleMapper {
    public String selectUserRoleByUserId(String userId);

    public int updateUserRoleByUserId(String userId, String roleName);

    int updateUsersRoleByUserId(UserInfo userInfo, String roleName, int myLimit);
}
