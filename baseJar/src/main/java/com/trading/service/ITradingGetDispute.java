package com.trading.service;

import com.base.database.trading.model.TradingGetDispute;

import java.util.List;

/**
 * Created by Administrtor on 2014/7/22.
 */
public interface ITradingGetDispute {
    void saveGetDispute(TradingGetDispute GetDispute) throws Exception;
    List<TradingGetDispute> selectGetDisputeByTransactionId(String TransactionId);
}
