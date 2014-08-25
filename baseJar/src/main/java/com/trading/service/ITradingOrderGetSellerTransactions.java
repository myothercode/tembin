package com.trading.service;

import com.base.database.trading.model.TradingOrderGetSellerTransactions;

import java.util.List;

/**
 * Created by Administrtor on 2014/7/22.
 */
public interface ITradingOrderGetSellerTransactions {

    void saveOrderGetSellerTransactions(TradingOrderGetSellerTransactions OrderGetSellerTransactions) throws Exception;
    List<TradingOrderGetSellerTransactions> selectTradingOrderGetSellerTransactionsByTransactionId(String TransactionID);
}
