package com.base.database.customtrading.mapper;

import com.base.domains.querypojos.PriceTrackingAutoPricingQuery;
import com.base.mybatis.page.Page;

import java.util.List;
import java.util.Map;

public interface PriceTrackingAutoPricingMapper {

    /**
     *
     * @param map
     * @return
     */
    List<PriceTrackingAutoPricingQuery> selectPriceTrackingAutoPricingList(Map map, Page page);

}