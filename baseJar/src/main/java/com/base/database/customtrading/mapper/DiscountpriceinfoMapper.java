package com.base.database.customtrading.mapper;

import com.base.database.trading.model.TradingDiscountpriceinfo;
import com.base.database.trading.model.TradingDiscountpriceinfoExample;
import com.base.domains.querypojos.DiscountpriceinfoQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DiscountpriceinfoMapper {

    List<DiscountpriceinfoQuery> selectByDiscountpriceinfoList(Map map);

}