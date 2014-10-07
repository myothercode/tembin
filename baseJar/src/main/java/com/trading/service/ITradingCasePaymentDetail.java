package com.trading.service;

import com.base.database.trading.model.TradingCasePaymentDetail;

import java.util.List;

/**
 * Created by Administrtor on 2014/7/22.
 */
public interface ITradingCasePaymentDetail {
    void saveCasePaymentDetail(TradingCasePaymentDetail CasePaymentDetail) throws Exception;

    List<TradingCasePaymentDetail> selectCasePaymentDetailByEBPId(Long Id);

    void deleteCasePaymentDetail(TradingCasePaymentDetail CasePaymentDetail) throws Exception;
}
