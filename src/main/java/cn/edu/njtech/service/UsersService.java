package cn.edu.njtech.service;

import java.util.LinkedList;

public interface UsersService {
    LinkedList queryUsers(Byte status, String academy, String department, Byte myLimit);
}
