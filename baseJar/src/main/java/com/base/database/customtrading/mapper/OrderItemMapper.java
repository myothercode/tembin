package com.base.database.customtrading.mapper;

import com.base.domains.querypojos.OrderItemQuery;
import com.base.mybatis.page.Page;

import java.util.List;
import java.util.Map;

public interface OrderItemMapper {

    /**
     *
     * @param map
     * @return
     */
    List<OrderItemQuery> selectOrderItemList(Map map, Page page);

}