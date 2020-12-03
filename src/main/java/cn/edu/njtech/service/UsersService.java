package cn.edu.njtech.service;

import cn.edu.njtech.domain.dao.Form;
import cn.edu.njtech.domain.dao.UserInfo;

import java.util.LinkedList;

public interface UsersService {
    LinkedList queryUsers(int status, String academy, String department, int myLimit);

    UserInfo queryUserByUserId(String userId);

    int queryUserRoleByUserId(String userId);

    int updateUserRoleByUserId(String userId, int limit);

    int updateUsersRoleByUserId(UserInfo userInfo, int limit, int myLimit);

    int insertForm(Form form);

    int updateUserMessageByUserId(String formId, String userId);
}
