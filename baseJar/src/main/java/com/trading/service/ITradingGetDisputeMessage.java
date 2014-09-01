package com.trading.service;

import com.base.database.trading.model.TradingGetDisputeMessage;

import java.util.List;

/**
 * Created by Administrtor on 2014/7/22.
 */
public interface ITradingGetDisputeMessage {
    void saveGetDisputeMessage(TradingGetDisputeMessage GetDisputeMessage) throws Exception;
    List<TradingGetDisputeMessage> selectGetDisputeMessageByDisputeId(Long disputeId);
    void deleteGetDisputeMessage(TradingGetDisputeMessage GetDisputeMessage) throws Exception;
}
