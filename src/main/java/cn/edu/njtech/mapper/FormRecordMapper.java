package cn.edu.njtech.mapper;

import cn.edu.njtech.domain.dao.FormRecord;

public interface FormRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FormRecord record);

    int insertSelective(FormRecord record);

    FormRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FormRecord record);

    int updateByPrimaryKey(FormRecord record);
}