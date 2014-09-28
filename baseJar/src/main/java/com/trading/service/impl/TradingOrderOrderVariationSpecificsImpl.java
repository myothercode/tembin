package com.trading.service.impl;


import com.base.database.trading.mapper.TradingOrderOrderVariationSpecificsMapper;
import com.base.database.trading.model.TradingOrderOrderVariationSpecifics;
import com.base.database.trading.model.TradingOrderOrderVariationSpecificsExample;
import com.base.utils.common.ObjectUtils;
import com.base.utils.exception.Asserts;
import com.trading.service.ITradingOrderOrderVariationSpecifics;
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
public class TradingOrderOrderVariationSpecificsImpl implements ITradingOrderOrderVariationSpecifics {

    @Autowired
    private TradingOrderOrderVariationSpecificsMapper tradingOrderOrderVariationSpecificsMapper;

    @Override
    public void saveOrderOrderVariationSpecifics(TradingOrderOrderVariationSpecifics OrderOrderVariationSpecifics) throws Exception {
        if(OrderOrderVariationSpecifics.getId()==null){
            ObjectUtils.toInitPojoForInsert(OrderOrderVariationSpecifics);
            tradingOrderOrderVariationSpecificsMapper.insert(OrderOrderVariationSpecifics);
        }else{
            TradingOrderOrderVariationSpecifics t=tradingOrderOrderVariationSpecificsMapper.selectByPrimaryKey(OrderOrderVariationSpecifics.getId());
            Asserts.assertTrue(t != null && t.getCreateUser() != null, "没有找到记录或者记录创建者为空");
            ObjectUtils.valiUpdate(t.getCreateUser(),TradingOrderOrderVariationSpecificsMapper.class,OrderOrderVariationSpecifics.getId(),"Synchronize");
            tradingOrderOrderVariationSpecificsMapper.updateByPrimaryKeySelective(OrderOrderVariationSpecifics);
        }
    }

    @Override
    public List<TradingOrderOrderVariationSpecifics> selectOrderOrderVariationSpecificsByAll(String sku,String name,String value) {
        TradingOrderOrderVariationSpecificsExample example=new TradingOrderOrderVariationSpecificsExample();
        TradingOrderOrderVariationSpecificsExample.Criteria cr=example.createCriteria();
        cr.andSkuEqualTo(sku);
        cr.andNameEqualTo(name);
        cr.andValueEqualTo(value);
        List<TradingOrderOrderVariationSpecifics> list=tradingOrderOrderVariationSpecificsMapper.selectByExample(example);
        return list;
    }

    @Override
    public List<TradingOrderOrderVariationSpecifics> selectOrderOrderVariationSpecificsBySku(String sku) {
        TradingOrderOrderVariationSpecificsExample example=new TradingOrderOrderVariationSpecificsExample();
        TradingOrderOrderVariationSpecificsExample.Criteria cr=example.createCriteria();
        cr.andSkuEqualTo(sku);
        List<TradingOrderOrderVariationSpecifics> list=tradingOrderOrderVariationSpecificsMapper.selectByExample(example);
        return list;
    }
}
