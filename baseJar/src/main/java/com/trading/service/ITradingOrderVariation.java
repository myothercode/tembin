package com.trading.service;

import com.base.database.trading.model.TradingOrderVariation;

import java.util.List;

/**
 * Created by Administrtor on 2014/8/16.
 */
public interface ITradingOrderVariation {

    void saveOrderVariation(TradingOrderVariation OrderVariation) throws Exception;

    List<TradingOrderVariation> selectOrderVariationByItemId(Long Id);

    void deleteOrderVariation (TradingOrderVariation OrderVariation) throws Exception;

}
