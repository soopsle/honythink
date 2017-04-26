package com.honythink.db.mapper;

import java.util.List;

import com.honythink.biz.system.dto.BaseDto;
import com.honythink.biz.system.dto.EntryDto;
import com.honythink.db.entity.Entry;

public interface EntryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Entry record);

    int insertSelective(Entry record);

    Entry selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Entry record);

    int updateByPrimaryKey(Entry record);
    
    List<EntryDto> list(BaseDto dto);
}