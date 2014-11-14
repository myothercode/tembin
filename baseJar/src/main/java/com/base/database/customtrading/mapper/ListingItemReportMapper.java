package com.base.database.customtrading.mapper;

import com.base.domains.querypojos.FeedBackReportQuery;
import com.base.domains.querypojos.ListingItemReportQuery;

import java.util.List;
import java.util.Map;

public interface ListingItemReportMapper {

    /**
     *
     * @param map
     * @return
     */
    List<ListingItemReportQuery> selectListingItemReportList(Map map);

    /**
     *
     * @param map
     * @return
     */
    List<ListingItemReportQuery> selectListingItemReportFee(Map map);

    /**
     *
     * @param map
     * @return
     */
    List<ListingItemReportQuery> selectListingItemSales(Map map);

}