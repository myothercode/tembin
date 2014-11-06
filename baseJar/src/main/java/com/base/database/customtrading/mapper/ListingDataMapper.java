package com.base.database.customtrading.mapper;

import com.base.database.trading.model.TradingListingData;
import com.base.domains.querypojos.ListingDataQuery;
import com.base.mybatis.page.Page;

import java.util.List;
import java.util.Map;

public interface ListingDataMapper {

    List<ListingDataQuery> selectByExample(Map map,Page page);

}