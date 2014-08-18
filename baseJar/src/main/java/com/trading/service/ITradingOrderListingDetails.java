package com.trading.service;

import com.base.database.trading.model.TradingOrderListingDetails;

/**
 * Created by Administrtor on 2014/7/22.
 */
public interface ITradingOrderListingDetails {

    void saveOrderListingDetails(TradingOrderListingDetails OrderListingDetails) throws Exception;

}
