package com.honythink.db.mapper;

import java.util.List;

import com.honythink.db.entity.SysRole;

public interface SysRoleMapper {
    int deleteByPrimaryKey(Integer rid);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(Integer rid);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);
    
    List<SysRole> selectRoles();
}