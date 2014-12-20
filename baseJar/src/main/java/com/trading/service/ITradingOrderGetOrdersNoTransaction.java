package com.trading.service;

import com.base.database.trading.model.TradingOrderGetOrders;

/**
 * Created by Administrtor on 2014/7/22.
 */
public interface ITradingOrderGetOrdersNoTransaction {

    void saveOrderGetOrders(TradingOrderGetOrders OrderGetOrders) throws Exception;

}
