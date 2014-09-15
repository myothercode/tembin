package com.trading.service.impl;


import com.base.database.trading.mapper.TradingOrderShippingDetailsMapper;
import com.base.database.trading.model.TradingOrderShippingDetails;
import com.base.database.trading.model.TradingOrderShippingDetailsExample;
import com.base.utils.common.ObjectUtils;
import com.base.utils.exception.Asserts;
import com.trading.service.ITradingOrderShippingDetails;
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
public class TradingOrderShippingDetailsImpl implements ITradingOrderShippingDetails {

    @Autowired
    private TradingOrderShippingDetailsMapper tradingOrderShippingDetailsMapper;

    @Override
    public void saveOrderShippingDetails(TradingOrderShippingDetails orderShippingDetails) throws Exception {
        if(orderShippingDetails.getId()==null){
            ObjectUtils.toInitPojoForInsert(orderShippingDetails);
            tradingOrderShippingDetailsMapper.insert(orderShippingDetails);
        }else{
            TradingOrderShippingDetails t=tradingOrderShippingDetailsMapper.selectByPrimaryKey(orderShippingDetails.getId());
            Asserts.assertTrue(t != null && t.getCreateUser() != null, "没有找到记录或者记录创建者为空");
            ObjectUtils.valiUpdate(t.getCreateUser(),TradingOrderShippingDetailsMapper.class,orderShippingDetails.getId(),"Synchronize");
            tradingOrderShippingDetailsMapper.updateByPrimaryKeySelective(orderShippingDetails);
        }
    }

    @Override
    public List<TradingOrderShippingDetails> selectOrderGetItemById(Long Id) {
        TradingOrderShippingDetailsExample shippingDetailsExample=new TradingOrderShippingDetailsExample();
        TradingOrderShippingDetailsExample.Criteria cr=shippingDetailsExample.createCriteria();
        cr.andIdEqualTo(Id);
        List<TradingOrderShippingDetails> lists=tradingOrderShippingDetailsMapper.selectByExample(shippingDetailsExample);
        return lists;
    }
}
