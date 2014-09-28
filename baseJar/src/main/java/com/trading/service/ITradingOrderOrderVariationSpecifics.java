package com.trading.service;

import com.base.database.trading.model.TradingOrderOrderVariationSpecifics;

import java.util.List;

/**
 * Created by Administrtor on 2014/8/16.
 */
public interface ITradingOrderOrderVariationSpecifics {

    void saveOrderOrderVariationSpecifics(TradingOrderOrderVariationSpecifics OrderOrderVariationSpecifics) throws Exception;

    List<TradingOrderOrderVariationSpecifics> selectOrderOrderVariationSpecificsByAll(String sku,String name,String value);

    List<TradingOrderOrderVariationSpecifics> selectOrderOrderVariationSpecificsBySku(String sku);

}
