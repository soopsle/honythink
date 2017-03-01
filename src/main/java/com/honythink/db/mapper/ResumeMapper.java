package com.honythink.db.mapper;

import java.util.List;

import com.honythink.biz.system.dto.BaseDto;
import com.honythink.db.entity.Resume;

public interface ResumeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Resume record);

    int insertSelective(Resume record);

    Resume selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Resume record);

    int updateByPrimaryKey(Resume record);
    
    List<Resume> list(BaseDto dto);
}