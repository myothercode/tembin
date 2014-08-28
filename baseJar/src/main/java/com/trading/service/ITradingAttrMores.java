package com.trading.service;

import com.base.database.trading.model.TradingAttrMores;

import java.util.List;

/**
 * Created by Administrtor on 2014/7/24.
 */
public interface ITradingAttrMores {
    void saveAttrMores(TradingAttrMores tradingAttrMores);

    TradingAttrMores toDAOPojo(String attrValue, String value) throws Exception;

    void deleteByParentId(String attrValue,Long id);

    List<TradingAttrMores> selectByParnetid(Long id, String attrValue);
}
