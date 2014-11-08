package com.base.database.customtrading.mapper;

import com.base.database.trading.model.TradingListingData;
import com.base.database.trading.model.TradingListingReport;
import com.base.domains.querypojos.ListingItemReportQuery;

import java.util.List;
import java.util.Map;

public interface ItemReportMapper {

    /**
     *
     * @param map
     * @return
     */
    List<TradingListingReport> selectItemReportList(Map map);
}