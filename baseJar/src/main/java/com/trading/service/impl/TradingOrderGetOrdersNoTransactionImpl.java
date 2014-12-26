package com.trading.service.impl;

import com.base.database.trading.mapper.TradingOrderGetOrdersMapper;
import com.base.database.trading.model.TradingOrderGetOrders;
import com.base.utils.cache.TempStoreDataSupport;
import com.base.utils.common.ObjectUtils;
import com.base.utils.exception.Asserts;
import com.base.utils.threadpool.TaskPool;
import com.trading.service.ITradingOrderGetOrdersNoTransaction;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 退货政策
 * Created by lq on 2014/7/29.
 */

//@Transactional(propagation= Propagation.NOT_SUPPORTED)
@Service
@Transactional(rollbackFor = Exception.class)
public class TradingOrderGetOrdersNoTransactionImpl implements ITradingOrderGetOrdersNoTransaction {
    static Logger logger = Logger.getLogger(TradingOrderGetOrdersNoTransactionImpl.class);
    @Autowired
    private TradingOrderGetOrdersMapper tradingOrderGetOrdersMapper;

    @Override
    public void saveOrderGetOrders(TradingOrderGetOrders OrderGetOrders1) throws Exception {
        //String xx=TempStoreDataSupport.pullData("TradingOrderGetOrders_temp");
        if (!"0".equals(TaskPool.togosBS[0])) {
            return;
        }
        TaskPool.togosBS[0]="1";
        while (!TaskPool.togos.isEmpty()) {
            TaskPool.togosBS[0]="1";
        try {
            TradingOrderGetOrders oo = TaskPool.togos.take();//获取记录
            oo.setUpdatetime(new Date());
            if (oo.getId() == null) {
                ObjectUtils.toInitPojoForInsert(oo);
                tradingOrderGetOrdersMapper.insert(oo);
            } else {
                TradingOrderGetOrders t = tradingOrderGetOrdersMapper.selectByPrimaryKey(oo.getId());
                Asserts.assertTrue(t != null && t.getCreateUser() != null, "没有找到记录或者记录创建者为空");
                ObjectUtils.valiUpdate(t.getCreateUser(), TradingOrderGetOrdersMapper.class, oo.getId(), "Synchronize");
                tradingOrderGetOrdersMapper.updateByPrimaryKeySelective(oo);
            }
        } catch (Exception e) {
            logger.error("写入TradingOrderGetOrders报错:",e);
            continue;
        }
            Thread.sleep(50);
        }

        TaskPool.togosBS[0]="0";
    }
}
