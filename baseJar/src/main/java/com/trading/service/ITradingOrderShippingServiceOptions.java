package com.trading.service;

import com.base.database.trading.model.TradingOrderShippingServiceOptions;

import java.util.List;

/**
 * Created by Administrtor on 2014/8/16.
 */
public interface ITradingOrderShippingServiceOptions {

    void saveOrderShippingServiceOptions(TradingOrderShippingServiceOptions OrderShippingServiceOptions) throws Exception;
    List<TradingOrderShippingServiceOptions> selectOrderGetItemByShippingDetailsId(Long id);
    void deleteOrderShippingServiceOptions (TradingOrderShippingServiceOptions OrderShippingServiceOptions) throws Exception;
}
