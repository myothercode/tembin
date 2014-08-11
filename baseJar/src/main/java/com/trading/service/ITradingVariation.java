package com.trading.service;

import com.base.database.trading.model.TradingVariation;
import com.base.xmlpojo.trading.addproduct.Variation;

/**
 * Created by Administrtor on 2014/8/11.
 */
public interface ITradingVariation {
    void saveVariation(TradingVariation pojo) throws Exception;

    TradingVariation toDAOPojo(Variation var) throws Exception;
}
