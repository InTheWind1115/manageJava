package cn.edu.njtech.service.impl;


import cn.edu.njtech.domain.dao.Form;
import cn.edu.njtech.domain.dao.FormRecord;
import cn.edu.njtech.domain.dao.UserInfo;
import cn.edu.njtech.mapper.*;
import cn.edu.njtech.service.UsersService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class UsersServiceImpl implements UsersService {

    @Resource
    UserInfoMapper userInfoMapper;

    @Resource
    UserRoleMapper userRoleMapper;

    @Resource
    FormMapper formMapper;

    @Resource
    UserMapper userMapper;

    @Resource
    FormRecordMapper formRecordMapper;

    /**
     *
     * @param status 身份
     * @param academy 学院
     * @param department 专业/部门
     * @param myLimit 此用户的权限
     * @return LinkedList集合
     */
    @Override
    public List queryUsers(int status, String academy, String department, int myLimit) {
        List list = new ArrayList();
        switch (status) {
            case -1:
                switch (myLimit) {
                    case 1:
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
        return list;
    }

    @Override
    public UserInfo queryUserByUserId(String userId) {
        return userInfoMapper.selectUserByUserId(userId);
    }

    @Override
    public int queryUserRoleByUserId(String userId) {
        String userRole = userRoleMapper.selectUserRoleByUserId(userId);
        int res = 4;
        switch (userRole) {
            case "ROLE_USER":
                res = 4;
                break;
            case "ROLE_TEACHER":
                res = 3;
                break;
            case "ROLE_ADMIN":
                res = 2;
                break;
            case "ROLE_SUPERADMIN":
                res = 1;
                break;
        }
        return res;
    }

    @Override
    public int updateUserRoleByUserId(String userId, int limit) {
        String roleName = null;
        roleName = convertLimitNumToStr(limit);
        return userRoleMapper.updateUserRoleByUserId(userId, roleName);
    }

    @Override
    public int updateUsersRoleByUserId(UserInfo userInfo, int limit, int myLimit) {
        String roleName = convertLimitNumToStr(limit);
        return userRoleMapper.updateUsersRoleByUserId(userInfo, roleName, myLimit);
    }

    @Override
    public int insertForm(Form form) {
        return formMapper.insertSelective(form);
    }

    @Override
    public int updateUserMessageByUserId(String formId, String userId) {
        return userMapper.updateMessageByUserId("," + formId, userId);
    }

    @Override
    public int updateUsersMessageByUserId(String formId, int status, String department, String academy) {
        return userMapper.updateMessages("," + formId, status, department, academy);
    }

    @Override
    public int insertFormRecord(FormRecord formRecord) {
        return formRecordMapper.insertSelective(formRecord);
    }

    @Override
    public int replaceUserMessageById(String username, String formId) {
        return userMapper.replaceUserMessageById(username, formId);
    }

    @Override
    public List<FormRecord> selectRecordForms(String formId) {
        return formRecordMapper.selectsRecordForms(formId);
    }

    @Override
    public List<Form> selectForms() {
        return formMapper.selectForms();
    }

    @Override
    public String queryUserMessage(String username) {
        return userMapper.selectUserMessage(username);
    }

    @Override
    public Form queryFormByFormId(String formId) {
        return formMapper.selectFormByFormId(formId);
    }


    public String convertLimitNumToStr(int limit) {
        String roleName = "";
        switch (limit) {
            case 4:
                roleName = "ROLE_USER";
                break;
            case 3:
                roleName = "ROLE_TEACHER";
                break;
            case 2:
                roleName = "ROLE_ADMIN";
                break;
            case 1:
                roleName = "ROLE_SUPERADMIN";
                break;
        }
        return roleName;
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
    public List queryUsersByStatus(int status, String academy, String department) {
        List list = new ArrayList();
        UserInfo userInfo = new UserInfo();
        userInfo.setStatus(status);
        if (!"-1".equals(academy)) {
            userInfo.setAcademy(academy);
        }
        if (!"-1".equals(department)) {
            userInfo.setDepartment(department);
        }
        list = userInfoMapper.selectUsersSelective(userInfo);
        return list;
    }
}
