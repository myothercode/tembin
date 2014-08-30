package com.trading.service;

import com.base.database.trading.model.TradingReseCategory;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/8/29.
 */
public interface ITradingReseCategory {
    void saveTradingReseCategory(TradingReseCategory tradingReseCategory) throws Exception;

    List<TradingReseCategory> selectByList(Map map);
}
