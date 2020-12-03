package cn.edu.njtech.mapper;

import cn.edu.njtech.domain.dao.Form;

import java.util.List;

public interface FormMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Form record);

    int insertSelective(Form record);

    Form selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Form record);

    int updateByPrimaryKey(Form record);

    List<Form> selectForms();

    Form selectFormByFormId(String formId);
}