package com.trading.service;

import com.base.database.trading.model.TradingOrderSellerInformation;

/**
 * Created by Administrtor on 2014/7/22.
 */
public interface ITradingOrderSellerInformation {

    void saveOrderSellerInformation(TradingOrderSellerInformation OrderSellerInformation) throws Exception;

   /* List<TradingOrderSellerInformation> selectOrderGetItemById(Long Id);*/
}
