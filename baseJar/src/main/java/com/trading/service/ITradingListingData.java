package com.trading.service;

import com.base.database.trading.model.TradingItem;
import com.base.database.trading.model.TradingListingAmendWithBLOBs;
import com.base.database.trading.model.TradingListingData;
import com.base.domains.querypojos.ListingDataAmendQuery;
import com.base.domains.querypojos.ListingDataQuery;
import com.base.mybatis.page.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/9/23.
 */
public interface ITradingListingData {

    List<ListingDataQuery> selectData(Map map, Page page);

    List<ListingDataAmendQuery> selectAmendData(Map map, Page page);


    TradingListingData selectById(Long id);

    TradingListingData selectByItemid(String itemid);

    void updateTradingListingData(TradingListingData tld);

    void insertTradingListingAmend(TradingListingAmendWithBLOBs tla);

    List<TradingListingData> selectByList(String sku, String ebayAccount);

    void saveTradingListingDataList(List<TradingListingData> litld);

    void saveTradingListingDataByTradingItem(TradingItem tradingItem,String res);

    List<ListingDataQuery> selectListDateByExample(Map map, Page page);
}
