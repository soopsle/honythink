package com.honythink.db.mapper;

import java.util.List;

import com.honythink.biz.system.dto.BaseDto;
import com.honythink.biz.system.dto.InterviewDto;
import com.honythink.db.entity.Interview;

public interface InterviewMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Interview record);

    int insertSelective(Interview record);

    Interview selectByPrimaryKey(Integer id);
    InterviewDto selectDtoByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Interview record);

    int updateByPrimaryKey(Interview record);
    
    List<InterviewDto> list(BaseDto dto);
    List<InterviewDto> listTomorrow(BaseDto dto);
}