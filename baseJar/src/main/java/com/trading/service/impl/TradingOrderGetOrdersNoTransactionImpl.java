package com.trading.service.impl;

import com.base.database.trading.mapper.TradingOrderGetOrdersMapper;
import com.base.database.trading.model.TradingOrderGetOrders;
import com.base.utils.common.ObjectUtils;
import com.base.utils.exception.Asserts;
import com.trading.service.ITradingOrderGetOrdersNoTransaction;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 退货政策
 * Created by lq on 2014/7/29.
 */
@Service
@Transactional(propagation= Propagation.NOT_SUPPORTED)
public class TradingOrderGetOrdersNoTransactionImpl implements ITradingOrderGetOrdersNoTransaction {
    static Logger logger = Logger.getLogger(TradingOrderGetOrdersNoTransactionImpl.class);
    @Autowired
    private TradingOrderGetOrdersMapper tradingOrderGetOrdersMapper;
    @Override
    public void saveOrderGetOrders(TradingOrderGetOrders OrderGetOrders) throws Exception {
        if(OrderGetOrders.getId()==null){
            ObjectUtils.toInitPojoForInsert(OrderGetOrders);
            tradingOrderGetOrdersMapper.insert(OrderGetOrders);
        }else{
            TradingOrderGetOrders t=tradingOrderGetOrdersMapper.selectByPrimaryKey(OrderGetOrders.getId());
            Asserts.assertTrue(t != null && t.getCreateUser() != null, "没有找到记录或者记录创建者为空");
            ObjectUtils.valiUpdate(t.getCreateUser(),TradingOrderGetOrdersMapper.class,OrderGetOrders.getId(),"Synchronize");
            tradingOrderGetOrdersMapper.updateByPrimaryKeySelective(OrderGetOrders);
        }
    }
}
