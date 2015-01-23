package com.base.database.customtrading.mapper;

import com.base.domains.querypojos.OrderGetOrdersQuery;
import com.base.mybatis.page.Page;

import java.util.List;
import java.util.Map;

public interface OrderGetOrdersMapper {

    List<OrderGetOrdersQuery> selectOrderGetOrdersByGroupList(Map map, Page page);

    List<OrderGetOrdersQuery> selectOrderGetOrdersByAccountFlag(Map map,Page page);

    List<OrderGetOrdersQuery> selectOrderGetOrdersByItemFlag(Map map,Page page);

    List<OrderGetOrdersQuery> selectOrderGetOrdersBySendPaidMessage(Map map,Page page);

    List<OrderGetOrdersQuery> selectOrderGetOrdersBySendShipMessage(Map map,Page page);


}