package com.trading.service;

import com.base.database.trading.model.TradingOrderAddMemberMessageAAQToPartner;

import java.util.List;

/**
 * Created by Administrtor on 2014/7/22.
 */
public interface ITradingOrderAddMemberMessageAAQToPartner {

    void saveOrderAddMemberMessageAAQToPartner(TradingOrderAddMemberMessageAAQToPartner OrderAddMemberMessageAAQToPartner) throws Exception;
    List<TradingOrderAddMemberMessageAAQToPartner> selectTradingOrderAddMemberMessageAAQToPartnerByTransactionId(String TransactionId,Integer type);
}
