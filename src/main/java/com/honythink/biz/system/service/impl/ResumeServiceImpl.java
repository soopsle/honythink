package com.honythink.biz.system.service.impl;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.honythink.biz.system.dto.BaseDto;
import com.honythink.biz.system.dto.ResumeDto;
import com.honythink.biz.system.service.ResumeService;
import com.honythink.db.entity.Resume;
import com.honythink.db.mapper.ResumeMapper;


@Service
public class ResumeServiceImpl implements ResumeService{

	@Resource
	private ResumeMapper resumeMapper;


    @Override
    public int deleteByPrimaryKey(Integer id) {
        return resumeMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Resume record) {
        return resumeMapper.insert(record);
    }

    @Override
    public int insertSelective(Resume record) {
        return resumeMapper.insertSelective(record);
    }

    @Override
    public Resume selectByPrimaryKey(Integer id) {
        return resumeMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Resume record) {
        return resumeMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Resume record) {
        return resumeMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<Resume> list(ResumeDto dto) {
        return resumeMapper.list(dto);
    }

}
