package cn.edu.njtech.mapper;

import cn.edu.njtech.domain.dao.DepartmentAdmin;

import java.util.LinkedList;
import java.util.List;

public interface DepartmentAdminMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DepartmentAdmin record);

    int insertSelective(DepartmentAdmin record);

    DepartmentAdmin selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DepartmentAdmin record);

    int updateByPrimaryKey(DepartmentAdmin record);

    LinkedList<DepartmentAdmin> selectUsersSelective(DepartmentAdmin departmentAdmin);
}