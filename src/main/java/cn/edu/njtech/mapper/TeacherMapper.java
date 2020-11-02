package cn.edu.njtech.mapper;

import cn.edu.njtech.domain.dao.Teacher;

import java.util.LinkedList;
import java.util.List;

public interface TeacherMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Teacher record);

    int insertSelective(Teacher record);

    Teacher selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Teacher record);

    int updateByPrimaryKey(Teacher record);

    LinkedList<Teacher> selectUsersSelective(Teacher teacher);
}