package com.base.database.customtrading.mapper;

import com.base.database.trading.model.TradingItem;
import com.base.database.trading.model.TradingItemExample;
import com.base.domains.querypojos.ItemQuery;
import com.base.mybatis.page.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItemMapper {

    List<ItemQuery> selectByItemList(Map map,Page page);

}