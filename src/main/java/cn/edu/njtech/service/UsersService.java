package cn.edu.njtech.service;

import cn.edu.njtech.domain.dao.Form;
import cn.edu.njtech.domain.dao.FormRecord;
import cn.edu.njtech.domain.dao.UserInfo;

import java.util.LinkedList;
import java.util.List;

public interface UsersService {
    LinkedList queryUsers(int status, String academy, String department, int myLimit);

    UserInfo queryUserByUserId(String userId);

    int queryUserRoleByUserId(String userId);

    int updateUserRoleByUserId(String userId, int limit);

    int updateUsersRoleByUserId(UserInfo userInfo, int limit, int myLimit);

    int insertForm(Form form);

    int updateUserMessageByUserId(String formId, String userId);

    int updateUsersMessageByUserId(String formId, int status, String department, String academy);

    int insertFormRecord(FormRecord formRecord);

    int replaceUserMessageById(String username, String formId);

    List<FormRecord> selectRecordForms(String formId);

    List<Form> selectForms();

    String queryUserMessage(String username);

    Form queryFormByFormId(String formId);
}
