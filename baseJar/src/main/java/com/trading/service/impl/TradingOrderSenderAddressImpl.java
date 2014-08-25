package com.trading.service.impl;

import com.base.database.trading.mapper.TradingOrderSenderAddressMapper;
import com.base.database.trading.model.TradingOrderSenderAddress;
import com.base.database.trading.model.TradingOrderSenderAddressExample;
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
public class TradingOrderSenderAddressImpl implements com.trading.service.ITradingOrderSenderAddress {

    @Autowired
    private TradingOrderSenderAddressMapper tradingOrderSenderAddressMapper;

    @Override
    public void saveOrderSenderAddress(TradingOrderSenderAddress OrderSenderAddress) throws Exception {
        if(OrderSenderAddress.getId()==null){
            ObjectUtils.toInitPojoForInsert(OrderSenderAddress);
            tradingOrderSenderAddressMapper.insert(OrderSenderAddress);
        }else{
            TradingOrderSenderAddress t=tradingOrderSenderAddressMapper.selectByPrimaryKey(OrderSenderAddress.getId());
            Asserts.assertTrue(t != null && t.getCreateUser() != null, "没有找到记录或者记录创建者为空");
            ObjectUtils.valiUpdate(t.getCreateUser(),TradingOrderSenderAddressMapper.class,OrderSenderAddress.getId());
            tradingOrderSenderAddressMapper.updateByPrimaryKeySelective(OrderSenderAddress);
        }
    }

    @Override
    public List<TradingOrderSenderAddress> selectOrderSenderAddressByOrderId(String orderid) {
        TradingOrderSenderAddressExample example=new TradingOrderSenderAddressExample();
        TradingOrderSenderAddressExample.Criteria cr=example.createCriteria();
        cr.andOrderidEqualTo(orderid);
        List<TradingOrderSenderAddress> list=tradingOrderSenderAddressMapper.selectByExample(example);
        return list;
    }
}
