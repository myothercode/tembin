package com.trading.service;

import com.base.database.trading.model.TradingFeedBackDetail;

import java.util.List;

/**
 * Created by Administrtor on 2014/8/16.
 */
public interface ITradingFeedBackDetail {
    void saveFeedBackDetail(List<TradingFeedBackDetail> lifb) throws Exception;
}
