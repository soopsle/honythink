
package com.honythink.biz.system.service;

import java.util.List;

import com.honythink.biz.system.dto.BaseDto;
import com.honythink.biz.system.dto.ResumeDto;
import com.honythink.db.entity.Resume;

public interface ResumeService {
    int deleteByPrimaryKey(Integer id);

    int insert(Resume record);

    int insertSelective(Resume record);

    Resume selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Resume record);

    int updateByPrimaryKey(Resume record);
    
    List<Resume> list(ResumeDto dto);
}
