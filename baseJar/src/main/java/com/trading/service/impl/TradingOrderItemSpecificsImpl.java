package com.trading.service.impl;


import com.base.database.trading.mapper.TradingOrderItemSpecificsMapper;
import com.base.database.trading.model.TradingOrderItemSpecifics;
import com.base.database.trading.model.TradingOrderItemSpecificsExample;
import com.base.utils.common.ObjectUtils;
import com.base.utils.exception.Asserts;
import com.trading.service.ITradingOrderItemSpecifics;
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
public class TradingOrderItemSpecificsImpl implements ITradingOrderItemSpecifics {

    @Autowired
    private TradingOrderItemSpecificsMapper tradingOrderItemSpecificsMapper;

    @Override
    public void saveOrderItemSpecifics(TradingOrderItemSpecifics OrderItemSpecifics) throws Exception {
        if(OrderItemSpecifics.getId()==null){
            ObjectUtils.toInitPojoForInsert(OrderItemSpecifics);
            tradingOrderItemSpecificsMapper.insert(OrderItemSpecifics);
        }else{
            TradingOrderItemSpecifics t=tradingOrderItemSpecificsMapper.selectByPrimaryKey(OrderItemSpecifics.getId());
            Asserts.assertTrue(t != null && t.getCreateUser() != null, "没有找到记录或者记录创建者为空");
            ObjectUtils.valiUpdate(t.getCreateUser(),TradingOrderItemSpecificsMapper.class,OrderItemSpecifics.getId(),"Synchronize");
            tradingOrderItemSpecificsMapper.updateByPrimaryKeySelective(OrderItemSpecifics);
        }
    }

    @Override
    public List<TradingOrderItemSpecifics> selectOrderItemSpecificsByItemId(Long Id) {
        TradingOrderItemSpecificsExample shippingDetailsExample=new TradingOrderItemSpecificsExample();
        TradingOrderItemSpecificsExample.Criteria cr=shippingDetailsExample.createCriteria();
        cr.andOrderitemIdEqualTo(Id);
        List<TradingOrderItemSpecifics> lists=tradingOrderItemSpecificsMapper.selectByExample(shippingDetailsExample);
        return lists;
    }

    @Override
    public void deleteOrderItemSpecifics(TradingOrderItemSpecifics OrderItemSpecifics) throws Exception {
        Long id=OrderItemSpecifics.getId();
        if(id!=null){
            tradingOrderItemSpecificsMapper.deleteByPrimaryKey(id);
        }
    }
}
