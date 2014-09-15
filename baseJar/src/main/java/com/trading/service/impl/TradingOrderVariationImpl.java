package com.trading.service.impl;


import com.base.database.trading.mapper.TradingOrderVariationMapper;
import com.base.database.trading.model.TradingOrderVariation;
import com.base.database.trading.model.TradingOrderVariationExample;
import com.base.utils.common.ObjectUtils;
import com.base.utils.exception.Asserts;
import com.trading.service.ITradingOrderVariation;
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
public class TradingOrderVariationImpl implements ITradingOrderVariation {

    @Autowired
    private TradingOrderVariationMapper tradingOrderVariationMapper;

    @Override
    public void saveOrderVariation(TradingOrderVariation OrderVariation) throws Exception {
        if(OrderVariation.getId()==null){
            ObjectUtils.toInitPojoForInsert(OrderVariation);
            tradingOrderVariationMapper.insert(OrderVariation);
        }else{
            TradingOrderVariation t=tradingOrderVariationMapper.selectByPrimaryKey(OrderVariation.getId());
            Asserts.assertTrue(t != null && t.getCreateUser() != null, "没有找到记录或者记录创建者为空");
            ObjectUtils.valiUpdate(t.getCreateUser(),TradingOrderVariationMapper.class,OrderVariation.getId(),"Synchronize");
            tradingOrderVariationMapper.updateByPrimaryKeySelective(OrderVariation);
        }
    }

    @Override
    public List<TradingOrderVariation> selectOrderVariationByItemId(Long Id) {
        TradingOrderVariationExample shippingDetailsExample=new TradingOrderVariationExample();
        TradingOrderVariationExample.Criteria cr=shippingDetailsExample.createCriteria();
        cr.andOrderitemIdEqualTo(Id);
        List<TradingOrderVariation> lists=tradingOrderVariationMapper.selectByExample(shippingDetailsExample);
        return lists;
    }

    @Override
    public void deleteOrderVariation(TradingOrderVariation OrderVariation) throws Exception {
        Long id=OrderVariation.getId();
        if(id!=null){
            tradingOrderVariationMapper.deleteByPrimaryKey(id);
        }
    }
}
