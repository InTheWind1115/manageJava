package cn.edu.njtech.service;

import cn.edu.njtech.domain.dao.UserInfo;

import java.util.LinkedList;

public interface UsersService {
    LinkedList queryUsers(Byte status, String academy, String department, Byte myLimit);

    UserInfo queryUserByUserId(String userId);
}
