package com.trading.service;

import com.base.database.trading.model.TradingOrderAddMemberMessageAAQToPartner;
import com.base.domains.querypojos.TradingOrderAddMemberMessageAAQToPartnerQuery;
import com.base.mybatis.page.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/7/22.
 */
public interface ITradingOrderAddMemberMessageAAQToPartner {

    void saveOrderAddMemberMessageAAQToPartner(TradingOrderAddMemberMessageAAQToPartner OrderAddMemberMessageAAQToPartner) throws Exception;
    List<TradingOrderAddMemberMessageAAQToPartner> selectTradingOrderAddMemberMessageAAQToPartnerByTransactionId(String TransactionId,Integer type,Integer...messageflag);
    List<TradingOrderAddMemberMessageAAQToPartnerQuery> selectTradingOrderAddMemberMessageAAQToPartner(Map map, Page page);
    void deleteTradingOrderAddMemberMessageAAQToPartner(Long id);
    List<TradingOrderAddMemberMessageAAQToPartner> selectTradingOrderAddMemberMessageAAQToPartnerByItemIdAndSender(String itemid,Integer type,String sender,String recipient);
}
