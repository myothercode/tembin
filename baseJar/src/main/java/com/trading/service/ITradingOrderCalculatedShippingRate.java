package com.trading.service;

import com.base.database.trading.model.TradingOrderCalculatedShippingRate;

/**
 * Created by Administrtor on 2014/7/22.
 */
public interface ITradingOrderCalculatedShippingRate {

    void saveOrderCalculatedShippingRate(TradingOrderCalculatedShippingRate OrderCalculatedShippingRate) throws Exception;
}
