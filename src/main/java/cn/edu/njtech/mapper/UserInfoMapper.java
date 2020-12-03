package cn.edu.njtech.mapper;

import cn.edu.njtech.domain.dao.UserInfo;

import java.util.List;

public interface UserInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);

    List selectUsersSelective(UserInfo userInfo);

    UserInfo selectUserByUserId(String userId);
}