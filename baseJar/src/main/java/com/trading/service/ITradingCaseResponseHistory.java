package com.trading.service;

import com.base.database.trading.model.TradingCaseResponseHistory;

/**
 * Created by Administrtor on 2014/7/22.
 */
public interface ITradingCaseResponseHistory {
    void saveCaseResponseHistory(TradingCaseResponseHistory CaseResponseHistory) throws Exception;
}
