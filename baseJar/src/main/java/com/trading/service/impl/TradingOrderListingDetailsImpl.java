package com.trading.service.impl;

import com.base.database.trading.mapper.TradingOrderListingDetailsMapper;
import com.base.database.trading.model.TradingOrderListingDetails;
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
public class TradingOrderListingDetailsImpl implements com.trading.service.ITradingOrderListingDetails {

    @Autowired
    private TradingOrderListingDetailsMapper tradingOrderListingDetailsMapper;

    @Override
    public void saveOrderListingDetails(TradingOrderListingDetails OrderListingDetails) throws Exception {
        if(OrderListingDetails.getId()==null){
            ObjectUtils.toInitPojoForInsert(OrderListingDetails);
            tradingOrderListingDetailsMapper.insert(OrderListingDetails);
        }else{
            TradingOrderListingDetails t=tradingOrderListingDetailsMapper.selectByPrimaryKey(OrderListingDetails.getId());
            Asserts.assertTrue(t != null && t.getCreateUser() != null, "没有找到记录或者记录创建者为空");
            ObjectUtils.valiUpdate(t.getCreateUser(),TradingOrderListingDetailsMapper.class,OrderListingDetails.getId());
            tradingOrderListingDetailsMapper.updateByPrimaryKeySelective(OrderListingDetails);
        }
    }





}
