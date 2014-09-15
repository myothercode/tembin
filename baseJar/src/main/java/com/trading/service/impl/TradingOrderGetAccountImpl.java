package com.trading.service.impl;

import com.base.database.trading.mapper.TradingOrderGetAccountMapper;
import com.base.database.trading.model.TradingOrderGetAccount;
import com.base.database.trading.model.TradingOrderGetAccountExample;
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
public class TradingOrderGetAccountImpl implements com.trading.service.ITradingOrderGetAccount {

    @Autowired
    private TradingOrderGetAccountMapper tradingOrderGetAccountMapper;

    @Override
    public void saveOrderGetAccount(TradingOrderGetAccount OrderGetAccount) throws Exception {
        if(OrderGetAccount.getId()==null){
            ObjectUtils.toInitPojoForInsert(OrderGetAccount);
            tradingOrderGetAccountMapper.insert(OrderGetAccount);
        }else{
            TradingOrderGetAccount t=tradingOrderGetAccountMapper.selectByPrimaryKey(OrderGetAccount.getId());
            Asserts.assertTrue(t != null && t.getCreateUser() != null, "没有找到记录或者记录创建者为空");
            ObjectUtils.valiUpdate(t.getCreateUser(),TradingOrderGetAccountMapper.class,OrderGetAccount.getId(),"Synchronize");
            tradingOrderGetAccountMapper.updateByPrimaryKeySelective(OrderGetAccount);
        }
    }

    @Override
    public List<TradingOrderGetAccount> selectTradingOrderGetAccountByTransactionId(String TransactionID) {
        TradingOrderGetAccountExample example=new TradingOrderGetAccountExample();
        TradingOrderGetAccountExample.Criteria cr=example.createCriteria();
        cr.andTransactionidEqualTo(TransactionID);
        List<TradingOrderGetAccount> list=tradingOrderGetAccountMapper.selectByExample(example);
        return list;
    }
}
