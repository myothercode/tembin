package com.trading.service;

import com.base.database.trading.model.TradingAttrMores;

/**
 * Created by Administrtor on 2014/7/24.
 */
public interface ITradingAttrMores {
    void saveAttrMores(TradingAttrMores tradingAttrMores);

    TradingAttrMores toDAOPojo(String attrValue, String value) throws Exception;

    void deleteByParentId(Long id);
}
