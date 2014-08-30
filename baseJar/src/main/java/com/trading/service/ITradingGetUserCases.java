package com.trading.service;

import com.base.database.trading.model.TradingGetUserCases;

import java.util.List;

/**
 * Created by Administrtor on 2014/7/22.
 */
public interface ITradingGetUserCases {

    void saveGetUserCases(TradingGetUserCases GetUserCases) throws Exception;

    List<TradingGetUserCases> selectOrderGetItemByTransactionId(String transactionid);
}
