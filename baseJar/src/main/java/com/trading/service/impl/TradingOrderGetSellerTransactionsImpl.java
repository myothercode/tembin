package com.trading.service.impl;

import com.base.database.trading.mapper.TradingOrderGetSellerTransactionsMapper;
import com.base.database.trading.model.TradingOrderGetSellerTransactions;
import com.base.database.trading.model.TradingOrderGetSellerTransactionsExample;
import com.base.utils.common.ObjectUtils;
import com.base.utils.exception.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 退货政策
 * Created by lq on 2014/7/29.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TradingOrderGetSellerTransactionsImpl implements com.trading.service.ITradingOrderGetSellerTransactions {

    @Autowired
    private TradingOrderGetSellerTransactionsMapper tradingOrderGetSellerTransactionsMapper;

    @Override
    public void saveOrderGetSellerTransactions(TradingOrderGetSellerTransactions OrderGetSellerTransactions) throws Exception {
        if(OrderGetSellerTransactions.getId()==null){
            ObjectUtils.toInitPojoForInsert(OrderGetSellerTransactions);
            tradingOrderGetSellerTransactionsMapper.insert(OrderGetSellerTransactions);
        }else{
            TradingOrderGetSellerTransactions t=tradingOrderGetSellerTransactionsMapper.selectByPrimaryKey(OrderGetSellerTransactions.getId());
            Asserts.assertTrue(t != null && t.getCreateUser() != null, "没有找到记录或者记录创建者为空");
            ObjectUtils.valiUpdate(t.getCreateUser(),TradingOrderGetSellerTransactionsMapper.class,OrderGetSellerTransactions.getId(),"Synchronize");
            tradingOrderGetSellerTransactionsMapper.updateByPrimaryKeySelective(OrderGetSellerTransactions);
        }
    }

    @Override
    public List<TradingOrderGetSellerTransactions> selectTradingOrderGetSellerTransactionsByTransactionId(String TransactionID) {
        TradingOrderGetSellerTransactionsExample example=new TradingOrderGetSellerTransactionsExample();
        TradingOrderGetSellerTransactionsExample.Criteria cr=example.createCriteria();
        cr.andTransactionidEqualTo(TransactionID);
        List<TradingOrderGetSellerTransactions> list=tradingOrderGetSellerTransactionsMapper.selectByExample(example);
        return list;
    }
}
