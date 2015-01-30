package com.trading.service.impl;

import com.base.database.trading.mapper.TradingAutoMessageAndOrderMapper;
import com.base.database.trading.model.TradingAutoMessageAndOrder;
import com.base.database.trading.model.TradingAutoMessageAndOrderExample;
import com.base.utils.common.ObjectUtils;
import com.base.utils.exception.Asserts;
import com.trading.service.ITradingAutoMessageAndOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrtor on 2014/8/14.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TradingAutoMessageAndOrderImpl implements ITradingAutoMessageAndOrder {

    @Autowired
    private TradingAutoMessageAndOrderMapper tradingAutoMessageAndOrderMapper;
    @Override
    public void saveAutoMessageAndOrder(TradingAutoMessageAndOrder autoMessageAndOrder) throws Exception {
        if(autoMessageAndOrder.getId()==null){
            ObjectUtils.toInitPojoForInsert(autoMessageAndOrder);
            this.tradingAutoMessageAndOrderMapper.insertSelective(autoMessageAndOrder);
        }else{
            TradingAutoMessageAndOrder t=tradingAutoMessageAndOrderMapper.selectByPrimaryKey(autoMessageAndOrder.getId());
            this.tradingAutoMessageAndOrderMapper.updateByPrimaryKeySelective(autoMessageAndOrder);
        }
    }

    @Override
    public TradingAutoMessageAndOrder selectAutoMessageAndOrderById(Long id) {
        return tradingAutoMessageAndOrderMapper.selectByPrimaryKey(id);
    }

    @Override
    public void deleteAutoMessageAndOrder(TradingAutoMessageAndOrder autoMessageAndOrder) throws Exception {
        if(autoMessageAndOrder!=null&&autoMessageAndOrder.getId()!=null){
            tradingAutoMessageAndOrderMapper.deleteByPrimaryKey(autoMessageAndOrder.getId());
        }
    }

    @Override
    public List<TradingAutoMessageAndOrder> selectAutoMessageAndOrderByAutoMessageId(Long autoMessageId) {
        TradingAutoMessageAndOrderExample example=new TradingAutoMessageAndOrderExample();
        TradingAutoMessageAndOrderExample.Criteria cr=example.createCriteria();
        cr.andAutomessageIdEqualTo(autoMessageId);
        List<TradingAutoMessageAndOrder> list=tradingAutoMessageAndOrderMapper.selectByExample(example);
        return list;
    }

    @Override
    public List<TradingAutoMessageAndOrder> selectAutoMessageAndOrderByAutoOrderId(Long orderid) {
        TradingAutoMessageAndOrderExample example=new TradingAutoMessageAndOrderExample();
        TradingAutoMessageAndOrderExample.Criteria cr=example.createCriteria();
        cr.andOrderIdEqualTo(orderid);
        List<TradingAutoMessageAndOrder> list=tradingAutoMessageAndOrderMapper.selectByExample(example);
        return list;
    }
}
