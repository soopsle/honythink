package com.honythink.db.mapper;

import java.util.List;

import com.honythink.db.entity.Dic;

public interface DicMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Dic record);

    int insertSelective(Dic record);

    Dic selectByPrimaryKey(Integer id);

    List<Dic> selectByName(String dicName);

    int updateByPrimaryKeySelective(Dic record);

    int updateByPrimaryKey(Dic record);
}