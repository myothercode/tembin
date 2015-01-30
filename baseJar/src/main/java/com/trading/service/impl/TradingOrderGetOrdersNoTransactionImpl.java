package com.trading.service.impl;

import com.base.database.trading.mapper.TradingOrderGetOrdersMapper;
import com.base.database.trading.model.TradingOrderGetOrders;
import com.base.utils.cache.TempStoreDataSupport;
import com.base.utils.common.ObjectUtils;
import com.base.utils.exception.Asserts;
import com.base.utils.scheduleother.updateorder.UpdateTradingOrderGetOrders;
import com.base.utils.threadpool.TaskPool;
import com.trading.service.ITradingOrderGetOrdersNoTransaction;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 写入order表的任务
 * Created by lq on 2014/7/29.
 */

//@Transactional(propagation= Propagation.NOT_SUPPORTED)
@Service
@Transactional(rollbackFor = Exception.class)
public class TradingOrderGetOrdersNoTransactionImpl implements ITradingOrderGetOrdersNoTransaction {
    static Logger logger = Logger.getLogger(TradingOrderGetOrdersNoTransactionImpl.class);

    @Override
    public void saveOrderGetOrders(TradingOrderGetOrders OrderGetOrders1) throws Exception {
        TaskPool.togosBS[0]="0";
        Boolean b= TaskPool.threadIsAliveByName("thread_UpdateTradingOrderGetOrders");
        if(b){
            //logger.error("UpdateTradingOrderGetOrders===之前的任务还未完成继续等待下一个循环===");
            return;
        }
        UpdateTradingOrderGetOrders x=new UpdateTradingOrderGetOrders();
        x.start();
    }
}
