package com.trading.service;

import com.base.database.trading.model.TradingOrderGetOrders;
import com.base.domains.querypojos.OrderGetOrdersQuery;
import com.base.mybatis.page.Page;

import javax.servlet.ServletOutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/7/22.
 */
public interface ITradingOrderGetOrders {

    void saveOrderGetOrders(TradingOrderGetOrders OrderGetOrders) throws Exception;

    List<OrderGetOrdersQuery> selectOrderGetOrdersByGroupList(Map map, Page page);

    List<TradingOrderGetOrders> selectOrderGetOrdersByOrderId(String orderId);

    List<TradingOrderGetOrders> selectOrderGetOrdersByTransactionId(String TransactionId,String seller);

    List<TradingOrderGetOrders> selectOrderGetOrdersByPaypalStatus(String status,List<String> ebays);

    List<TradingOrderGetOrders> selectOrderGetOrdersByFolder(String folderId,List<String> ebays);

    List<TradingOrderGetOrders> selectOrderGetOrdersByBuyerAndItemid(String itemid,String buyer);

    void downloadOrders(List<TradingOrderGetOrders> list,String outputFile,ServletOutputStream outputStream) throws Exception;

    List<TradingOrderGetOrders> selectOrderGetOrdersBySendPaidMessage();

    List<TradingOrderGetOrders> selectOrderGetOrdersBySendShipMessage();

    List<TradingOrderGetOrders> selectOrderGetOrdersByeBayAccountAndTime(String ebay,Date start,Date end);

    TradingOrderGetOrders selectOrderGetOrdersById(Long id);

    void deleteOrderGetOrders(Long id) throws Exception;
}
