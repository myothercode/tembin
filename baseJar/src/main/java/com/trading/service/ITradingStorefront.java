package com.trading.service;

import com.base.database.trading.model.TradingStorefront;
import com.base.xmlpojo.trading.addproduct.Storefront;

/**
 * Created by Administrtor on 2014/7/24.
 */
public interface ITradingStorefront {
    void saveStorefront(TradingStorefront pojo);

    TradingStorefront toDAOPojo(Storefront sf) throws Exception;
}
