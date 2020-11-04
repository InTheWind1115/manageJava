package cn.edu.njtech.service.impl;


import cn.edu.njtech.domain.dao.UserInfo;
import cn.edu.njtech.mapper.UserInfoMapper;
import cn.edu.njtech.service.UsersService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

@Service
public class UsersServiceImpl implements UsersService {

    @Resource
    UserInfoMapper userInfoMapper;

    /**
     *
     * @param status 身份
     * @param academy 学院
     * @param department 专业/部门
     * @param myLimit 此用户的权限
     * @return LinkedList集合
     */
    @Override
    public LinkedList queryUsers(Byte status, String academy, String department, Byte myLimit) {
        List list = new LinkedList();
        switch (status) {
            case -1:
                switch (myLimit) {
                    case 1:
                        list.addAll(this.queryUsersByStatus((byte) 1, academy, department));
                        list.addAll(this.queryUsersByStatus((byte) 2, academy, department));
                        list.addAll(this.queryUsersByStatus((byte) 3, academy, department));
                        list.addAll(this.queryUsersByStatus((byte) 4, academy, department));
                        break;
                    case 2:
                        list.addAll(this.queryUsersByStatus((byte) 3, academy, department));
                        list.addAll(this.queryUsersByStatus((byte) 4, academy, department));
                        break;
                    case 3:
                        list.addAll(this.queryUsersByStatus((byte) 4, academy, department));
                        break;
                    default:
                        break;
                }
                break;
            case 1:
                list.addAll(this.queryUsersByStatus((byte) 1, academy, department));
                break;
            case 2:
                list.addAll(this.queryUsersByStatus((byte) 2, academy, department));
                break;
            case 3:
                list.addAll(this.queryUsersByStatus((byte) 3, academy, department));
                break;
            case 4:
                list.addAll(this.queryUsersByStatus((byte) 4, academy, department));
                break;
            default:
                break;
        }
        return (LinkedList) list;
    }

    @Override
    public UserInfo queryUserByUserId(String userId) {
        return userInfoMapper.selectUserByUserId(userId);
    }


    /**
     * @author 刘成
     * @time 2020/10/28
     * @param status 身份
     * @param academy 学院
     * @param department 专业/部门
     * @return
     * 用来查询所在学院 专业/部门 的某身份的用户，返回一个LinkedList集合
     */
    public LinkedList queryUsersByStatus(Byte status, String academy, String department) {
        List list = new LinkedList();
        UserInfo userInfo = new UserInfo();
        userInfo.setStatus(status);
        if (!"-1".equals(academy)) {
            userInfo.setAcademy(academy);
        }
        if (!"-1".equals(department)) {
            userInfo.setDepartment(academy);
        }
        list = userInfoMapper.selectUsersSelective(userInfo);
        return (LinkedList) list;
    }
}
