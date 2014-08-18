package com.trading.service;

import com.base.database.trading.model.TradingOrderShippingDetails;

import java.util.List;

/**
 * Created by Administrtor on 2014/8/16.
 */
public interface ITradingOrderShippingDetails {

    void saveOrderShippingDetails(TradingOrderShippingDetails OrderShippingDetails) throws Exception;

    List<TradingOrderShippingDetails> selectOrderGetItemById(Long Id);

}
