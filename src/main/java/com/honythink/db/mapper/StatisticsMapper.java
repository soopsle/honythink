package com.honythink.db.mapper;

import java.util.List;
import java.util.Map;

import com.honythink.biz.system.dto.KPIDto;

public interface StatisticsMapper {
    List<KPIDto> selectRecommendKPIs();
    KPIDto selectRecommendKPI(Map params);
    int selectRecommendTotal(Map params);
}