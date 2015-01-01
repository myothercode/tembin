package com.trading.service;

import com.base.database.trading.model.TradingShippingserviceoptionsDoc;

import java.util.List;

/**
 * Created by Administrtor on 2014/12/29.
 */
public interface ITradingShippingServiceOptionsDoc {
    void saveTradingShippingserviceoptionsDoc(List<TradingShippingserviceoptionsDoc> lidoc);

    void delTradingShippingserviceoptionsDoc(long docId);

    List<TradingShippingserviceoptionsDoc> slectByParentIdDocId(long docId);
}
