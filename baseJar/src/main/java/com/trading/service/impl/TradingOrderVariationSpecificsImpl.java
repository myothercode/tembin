package com.trading.service.impl;


import com.base.database.trading.mapper.TradingOrderVariationSpecificsMapper;
import com.base.database.trading.model.TradingOrderVariationSpecifics;
import com.base.database.trading.model.TradingOrderVariationSpecificsExample;
import com.base.utils.common.ObjectUtils;
import com.base.utils.exception.Asserts;
import com.trading.service.ITradingOrderVariationSpecifics;
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
public class TradingOrderVariationSpecificsImpl implements ITradingOrderVariationSpecifics {

    @Autowired
    private TradingOrderVariationSpecificsMapper tradingOrderVariationSpecificsMapper;

    @Override
    public void saveOrderVariationSpecifics(TradingOrderVariationSpecifics OrderVariationSpecifics) throws Exception {
        if(OrderVariationSpecifics.getId()==null){
            ObjectUtils.toInitPojoForInsert(OrderVariationSpecifics);
            tradingOrderVariationSpecificsMapper.insert(OrderVariationSpecifics);
        }else{
            TradingOrderVariationSpecifics t=tradingOrderVariationSpecificsMapper.selectByPrimaryKey(OrderVariationSpecifics.getId());
            Asserts.assertTrue(t != null && t.getCreateUser() != null, "没有找到记录或者记录创建者为空");
            ObjectUtils.valiUpdate(t.getCreateUser(),TradingOrderVariationSpecificsMapper.class,OrderVariationSpecifics.getId());
            tradingOrderVariationSpecificsMapper.updateByPrimaryKeySelective(OrderVariationSpecifics);
        }
    }

    @Override
    public List<TradingOrderVariationSpecifics> selectOrderVariationSpecificsByVariationId(Long Id) {
        TradingOrderVariationSpecificsExample shippingDetailsExample=new TradingOrderVariationSpecificsExample();
        TradingOrderVariationSpecificsExample.Criteria cr=shippingDetailsExample.createCriteria();
        cr.andOrdervariationIdEqualTo(Id);
        List<TradingOrderVariationSpecifics> lists=tradingOrderVariationSpecificsMapper.selectByExample(shippingDetailsExample);
        return lists;
    }

    @Override
    public void deleteOrderVariationSpecifics(TradingOrderVariationSpecifics OrderVariationSpecifics) throws Exception {
        Long id=OrderVariationSpecifics.getId();
        if(id!=null){
            tradingOrderVariationSpecificsMapper.deleteByPrimaryKey(id);
        }
    }
}
