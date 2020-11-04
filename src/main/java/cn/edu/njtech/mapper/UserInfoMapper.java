package cn.edu.njtech.mapper;

import cn.edu.njtech.domain.dao.UserInfo;

import java.util.LinkedList;

public interface UserInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);

    LinkedList<UserInfo> selectUsersSelective(UserInfo user);

    UserInfo selectUserByUserId(String userId);

}