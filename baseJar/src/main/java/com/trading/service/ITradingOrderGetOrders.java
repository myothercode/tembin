package com.trading.service;

import com.base.database.trading.model.TradingOrderGetOrders;
import com.base.domains.querypojos.OrderGetOrdersQuery;
import com.base.mybatis.page.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/7/22.
 */
public interface ITradingOrderGetOrders {

    void saveOrderGetOrders(TradingOrderGetOrders OrderGetOrders) throws Exception;

    List<OrderGetOrdersQuery> selectOrderGetOrdersByGroupList(Map map, Page page);

    List<TradingOrderGetOrders> selectOrderGetOrdersByOrderId(String orderId);
}