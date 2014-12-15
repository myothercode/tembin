package com.base.database.customtrading.mapper;

import com.base.domains.querypojos.PriceTrackingQuery;
import com.base.mybatis.page.Page;

import java.util.List;
import java.util.Map;

public interface PriceTrackingMapper {

    /**
     *
     * @param map
     * @return
     */
    List<PriceTrackingQuery> selectPriceTrackingList(Map map, Page page);

}