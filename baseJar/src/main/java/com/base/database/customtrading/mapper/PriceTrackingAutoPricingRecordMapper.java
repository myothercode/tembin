package com.base.database.customtrading.mapper;

import com.base.domains.querypojos.PriceTrackingAutoPricingRecordQuery;
import com.base.mybatis.page.Page;

import java.util.List;
import java.util.Map;

public interface PriceTrackingAutoPricingRecordMapper {

    /**
     *
     * @param map
     * @return
     */
    List<PriceTrackingAutoPricingRecordQuery> selectPriceTrackingAutoPricingRecordList(Map map, Page page);

}