package com.trading.service;

import com.base.database.trading.model.TradingAssessViewSet;
import org.apache.http.impl.cookie.DateParseException;

/**
 * Created by Administrtor on 2014/11/28.
 */
public interface ITradingAssessViewSet {
    void save(TradingAssessViewSet tradingAssessViewSet);

    TradingAssessViewSet selectByUserid(long userid) throws DateParseException;
}
