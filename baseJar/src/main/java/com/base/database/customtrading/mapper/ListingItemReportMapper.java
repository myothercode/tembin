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
}