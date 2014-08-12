package com.trading.service;

import com.base.database.trading.model.TradingVariations;
import com.base.xmlpojo.trading.addproduct.Variations;

/**
 * Created by Administrtor on 2014/8/11.
 */
public interface ITradingVariations {
    void saveVariations(TradingVariations pojo) throws Exception;

    TradingVariations toDAOPojo(Variations var) throws Exception;

    TradingVariations selectByParentId(Long id);
}
