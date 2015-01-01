package com.trading.service;

import com.base.database.trading.model.TradingInternationalshippingserviceoptionDoc;

import java.util.List;

/**
 * Created by Administrtor on 2014/12/29.
 */
public interface ITradingInternationalShippingServiceOptionDoc {
    void saveTradingInternationalshippingserviceoptionDoc(List<TradingInternationalshippingserviceoptionDoc> litd);

    void delTradingInternationalshippingserviceoptionDoc(long docId);

    List<TradingInternationalshippingserviceoptionDoc> selectByParentIdDocId(long docId);
}
