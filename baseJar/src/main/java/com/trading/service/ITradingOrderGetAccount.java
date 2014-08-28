package com.trading.service;

import com.base.database.trading.model.TradingOrderGetAccount;

import java.util.List;

/**
 * Created by Administrtor on 2014/7/22.
 */
public interface ITradingOrderGetAccount {

    void saveOrderGetAccount(TradingOrderGetAccount OrderGetAccount) throws Exception;
    List<TradingOrderGetAccount> selectTradingOrderGetAccountByTransactionId(String TransactionID);
}
