package cn.edu.njtech.mapper;

import cn.edu.njtech.domain.dao.DepartmentInformation;

public interface DepartmentInformationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DepartmentInformation record);

    int insertSelective(DepartmentInformation record);

    DepartmentInformation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DepartmentInformation record);

    int updateByPrimaryKey(DepartmentInformation record);
}