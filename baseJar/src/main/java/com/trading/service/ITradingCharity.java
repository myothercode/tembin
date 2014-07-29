package com.trading.service;

import com.base.database.trading.model.TradingCharity;
import com.base.xmlpojo.trading.addproduct.Charity;

/**
 * Created by Administrtor on 2014/7/24.
 */
public interface ITradingCharity {
    void saveCharity(TradingCharity tradingCharity);

    TradingCharity toDAOPojo(Charity charity) throws Exception;
}
