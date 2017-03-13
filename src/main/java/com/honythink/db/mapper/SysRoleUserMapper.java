package com.honythink.db.mapper;

import java.util.List;

import com.honythink.biz.system.dto.TreeDto;
import com.honythink.db.entity.SysRoleUser;
import com.honythink.db.entity.SysUser;

public interface SysRoleUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysRoleUser record);

    int insertSelective(SysRoleUser record);

    SysRoleUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysRoleUser record);

    int updateByPrimaryKey(SysRoleUser record);
    
    SysUser findByUserName(String username);
}