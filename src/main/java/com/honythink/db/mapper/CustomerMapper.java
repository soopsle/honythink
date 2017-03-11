package com.honythink.db.mapper;

import java.util.List;

import com.honythink.biz.system.dto.BaseDto;
import com.honythink.db.entity.Customer;

public interface CustomerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Customer record);

    int insertSelective(Customer record);

    Customer selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Customer record);

    int updateByPrimaryKey(Customer record);
    
    List<Customer> list(BaseDto dto);
}