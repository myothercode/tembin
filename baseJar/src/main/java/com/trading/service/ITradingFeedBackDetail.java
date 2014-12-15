package com.trading.service;

import com.base.database.trading.model.TradingFeedBackDetail;
import com.base.domains.querypojos.FeedBackReportQuery;
import com.base.mybatis.page.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/8/16.
 */
public interface ITradingFeedBackDetail {
    void saveFeedBackDetail(List<TradingFeedBackDetail> lifb) throws Exception;

    TradingFeedBackDetail selectFeedBackDetailByTransactionId(String transactionId);

    List<TradingFeedBackDetail> selectFeedBackDetailByAutoMessageFlag(List<String> types);

    int selectByCount(Map m);

    TradingFeedBackDetail selectFeedBackDetailByBuyerAndFeedBackId(String buyer,String feedBackId);

    List<FeedBackReportQuery> selectFeedBackReportList(String type);

    List<TradingFeedBackDetail> selectClientAssessFeedBackList(Map m, Page page);
}
