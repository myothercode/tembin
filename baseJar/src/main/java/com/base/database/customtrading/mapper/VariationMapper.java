package com.base.database.customtrading.mapper;

import com.base.database.trading.model.TradingVariation;
import com.base.database.trading.model.TradingVariationExample;
import com.base.domains.querypojos.VariationQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface VariationMapper {

    List<VariationQuery> selectByExample(Map map);

}