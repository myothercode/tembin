package com.trading.service;

import com.base.database.trading.model.TradingPriceTrackingAutoPricingRecord;
import com.base.domains.querypojos.PriceTrackingAutoPricingRecordQuery;
import com.base.mybatis.page.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/8/14.
 */
public interface ITradingPriceTrackingAutoPricingRecord {

    List<PriceTrackingAutoPricingRecordQuery> selectPriceTrackingAutoPricingRecordList(Map map, Page page);

    void savePriceTrackingAutoPricingRecord(TradingPriceTrackingAutoPricingRecord tradingPriceTrackingAutoPricingRecord) throws Exception;
}
