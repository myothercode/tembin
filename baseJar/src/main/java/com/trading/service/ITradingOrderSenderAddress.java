package com.trading.service;

import com.base.database.trading.model.TradingOrderSenderAddress;

import java.util.List;

/**
 * Created by Administrtor on 2014/7/22.
 */
public interface ITradingOrderSenderAddress {

    void saveOrderSenderAddress(TradingOrderSenderAddress OrderSenderAddress) throws Exception;
    List<TradingOrderSenderAddress> selectOrderSenderAddressByOrderId(String orderid);

}
