package com.trading.service;

import com.base.database.trading.model.TradingOrderGetItem;
import com.base.domains.querypojos.OrderItemQuery;
import com.base.mybatis.page.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/7/22.
 */
public interface ITradingOrderGetItem {

    void saveOrderGetItem(TradingOrderGetItem OrderGetItem) throws Exception;
    List<TradingOrderGetItem> selectOrderGetItemByItemId(String ItemId);
    List<OrderItemQuery> selectOrderItemList(Map map,Page page);
}
