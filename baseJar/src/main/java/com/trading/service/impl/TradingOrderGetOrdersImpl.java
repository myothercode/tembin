package com.trading.service.impl;

import com.base.database.customtrading.mapper.OrderGetOrdersMapper;
import com.base.database.trading.mapper.TradingOrderGetOrdersMapper;
import com.base.database.trading.model.TradingOrderGetOrders;
import com.base.database.trading.model.TradingOrderGetOrdersExample;
import com.base.domains.querypojos.OrderGetOrdersQuery;
import com.base.mybatis.page.Page;
import com.base.utils.common.ObjectUtils;
import com.base.utils.exception.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 退货政策
 * Created by lq on 2014/7/29.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TradingOrderGetOrdersImpl implements com.trading.service.ITradingOrderGetOrders {

    @Autowired
    private TradingOrderGetOrdersMapper tradingOrderGetOrdersMapper;

    @Autowired
    private OrderGetOrdersMapper orderGetOrdersMapper;
    @Override
    public void saveOrderGetOrders(TradingOrderGetOrders OrderGetOrders) throws Exception {
        if(OrderGetOrders.getId()==null){
            ObjectUtils.toInitPojoForInsert(OrderGetOrders);
            tradingOrderGetOrdersMapper.insertSelective(OrderGetOrders);
        }else{
            TradingOrderGetOrders t=tradingOrderGetOrdersMapper.selectByPrimaryKey(OrderGetOrders.getId());
            Asserts.assertTrue(t != null && t.getCreateUser() != null, "没有找到记录或者记录创建者为空");
            ObjectUtils.valiUpdate(t.getCreateUser(),TradingOrderGetOrdersMapper.class,OrderGetOrders.getId());
            tradingOrderGetOrdersMapper.updateByPrimaryKeySelective(OrderGetOrders);
        }
    }

    @Override
    public List<OrderGetOrdersQuery> selectOrderGetOrdersByGroupList(Map map, Page page) {
        return orderGetOrdersMapper.selectOrderGetOrdersByGroupList(map, page);
    }

    @Override
    public List<TradingOrderGetOrders> selectOrderGetOrdersByOrderId(String orderId) {
        TradingOrderGetOrdersExample or=new TradingOrderGetOrdersExample();
        TradingOrderGetOrdersExample.Criteria cr=or.createCriteria();
        cr.andOrderidEqualTo(orderId);
        List<TradingOrderGetOrders> lists=tradingOrderGetOrdersMapper.selectByExample(or);
        return lists;
    }

    @Override
    public List<TradingOrderGetOrders> selectOrderGetOrdersByTransactionId(String TransactionId) {
        TradingOrderGetOrdersExample or=new TradingOrderGetOrdersExample();
        TradingOrderGetOrdersExample.Criteria cr=or.createCriteria();
        cr.andTransactionidEqualTo(TransactionId);
        List<TradingOrderGetOrders> lists=tradingOrderGetOrdersMapper.selectByExample(or);
        return lists;
    }


}
