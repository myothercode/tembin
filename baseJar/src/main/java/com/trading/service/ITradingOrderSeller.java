package com.trading.service;

import com.base.database.trading.model.TradingOrderSeller;

import java.util.List;

/**
 * Created by Administrtor on 2014/7/22.
 */
public interface ITradingOrderSeller {

    void saveOrderSeller(TradingOrderSeller OrderSeller) throws Exception;

    List<TradingOrderSeller> selectOrderGetItemById(Long Id);
}
