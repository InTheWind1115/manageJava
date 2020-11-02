package cn.edu.njtech.mapper;

import cn.edu.njtech.domain.dao.SchoolAdmin;

import java.util.LinkedList;
import java.util.List;

public interface SchoolAdminMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SchoolAdmin record);

    int insertSelective(SchoolAdmin record);

    SchoolAdmin selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SchoolAdmin record);

    int updateByPrimaryKey(SchoolAdmin record);

    LinkedList<SchoolAdmin> selectUsersSelective(SchoolAdmin schoolAdmin);
}