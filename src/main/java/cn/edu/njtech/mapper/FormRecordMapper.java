package cn.edu.njtech.mapper;

import cn.edu.njtech.domain.dao.FormRecord;

import java.util.List;

public interface FormRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FormRecord record);

    int insertSelective(FormRecord record);

    FormRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FormRecord record);

    int updateByPrimaryKey(FormRecord record);

    List<FormRecord> selectsRecordForms(String formId);
}