package com.trading.service;

import com.base.database.trading.model.TradingCaseResponseHistory;

import java.util.List;

/**
 * Created by Administrtor on 2014/7/22.
 */
public interface ITradingCaseResponseHistory {
    void saveCaseResponseHistory(TradingCaseResponseHistory CaseResponseHistory) throws Exception;

    List<TradingCaseResponseHistory> selectCaseResponseHistoryByEBPId(Long Id);

    void deleteCaseResponseHistory(TradingCaseResponseHistory CaseResponseHistory) throws Exception;
}
