package com.trading.service;

import com.base.database.trading.model.TradingOrderVariationSpecifics;

import java.util.List;

/**
 * Created by Administrtor on 2014/8/16.
 */
public interface ITradingOrderVariationSpecifics {

    void saveOrderVariationSpecifics(TradingOrderVariationSpecifics OrderVariationSpecifics) throws Exception;

    List<TradingOrderVariationSpecifics> selectOrderVariationSpecificsByVariationId(Long Id);

    void deleteOrderVariationSpecifics (TradingOrderVariationSpecifics OrderVariationSpecifics) throws Exception;

}
