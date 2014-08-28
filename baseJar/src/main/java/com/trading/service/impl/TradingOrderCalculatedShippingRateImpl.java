package com.trading.service.impl;

import com.base.database.trading.mapper.TradingOrderCalculatedShippingRateMapper;
import com.base.database.trading.model.TradingOrderCalculatedShippingRate;
import com.base.utils.common.ObjectUtils;
import com.base.utils.exception.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 退货政策
 * Created by lq on 2014/7/29.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TradingOrderCalculatedShippingRateImpl implements com.trading.service.ITradingOrderCalculatedShippingRate {

    @Autowired
    private TradingOrderCalculatedShippingRateMapper tradingOrderCalculatedShippingRateMapper;

    @Override
    public void saveOrderCalculatedShippingRate(TradingOrderCalculatedShippingRate OrderCalculatedShippingRate) throws Exception {
        if(OrderCalculatedShippingRate.getId()==null){
            ObjectUtils.toInitPojoForInsert(OrderCalculatedShippingRate);
            tradingOrderCalculatedShippingRateMapper.insert(OrderCalculatedShippingRate);
        }else{
            TradingOrderCalculatedShippingRate t=tradingOrderCalculatedShippingRateMapper.selectByPrimaryKey(OrderCalculatedShippingRate.getId());
            Asserts.assertTrue(t != null && t.getCreateUser() != null, "没有找到记录或者记录创建者为空");
            ObjectUtils.valiUpdate(t.getCreateUser(),TradingOrderCalculatedShippingRateMapper.class,OrderCalculatedShippingRate.getId());
            tradingOrderCalculatedShippingRateMapper.updateByPrimaryKeySelective(OrderCalculatedShippingRate);
        }
    }
}
