package com.base.database.customtrading.mapper;

import com.base.domains.querypojos.ShippingdetailsQuery;
import com.base.mybatis.page.Page;

import java.util.List;
import java.util.Map;

public interface ShippingdetailsMapper {

    /**
     * 查询运输选项列表
     * @param map
     * @return
     */
    List<ShippingdetailsQuery> selectByShippingdetails(Map map,Page page);

}