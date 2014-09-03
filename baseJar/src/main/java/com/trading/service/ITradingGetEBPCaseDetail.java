package com.trading.service;

import com.base.database.trading.model.TradingGetEBPCaseDetail;

import java.util.List;

/**
 * Created by Administrtor on 2014/7/22.
 */
public interface ITradingGetEBPCaseDetail {
    void saveGetEBPCaseDetail(TradingGetEBPCaseDetail GetEBPCaseDetail) throws Exception;
    List<TradingGetEBPCaseDetail> selectGetEBPCaseDetailByTransactionId(String transactionid);
}
