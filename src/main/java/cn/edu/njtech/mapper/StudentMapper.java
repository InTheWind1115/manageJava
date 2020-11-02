package cn.edu.njtech.mapper;

import cn.edu.njtech.domain.dao.Student;

import java.util.LinkedList;
import java.util.List;

public interface StudentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Student record);

    int insertSelective(Student record);

    Student selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Student record);

    int updateByPrimaryKey(Student record);

    LinkedList<Student> selectUsersSelective(Student student);
}