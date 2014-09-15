package com.trading.service.impl;

import com.base.database.trading.mapper.TradingOrderSellerInformationMapper;
import com.base.database.trading.model.TradingOrderSellerInformation;
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
public class TradingOrderSellerInformationImpl implements com.trading.service.ITradingOrderSellerInformation {

    @Autowired
    private TradingOrderSellerInformationMapper tradingOrderSellerInformationMapper;

    @Override
    public void saveOrderSellerInformation(TradingOrderSellerInformation OrderSellerInformation) throws Exception {
        if(OrderSellerInformation.getId()==null){
            ObjectUtils.toInitPojoForInsert(OrderSellerInformation);
            tradingOrderSellerInformationMapper.insert(OrderSellerInformation);
        }else{
            TradingOrderSellerInformation t=tradingOrderSellerInformationMapper.selectByPrimaryKey(OrderSellerInformation.getId());
            Asserts.assertTrue(t != null && t.getCreateUser() != null, "没有找到记录或者记录创建者为空");
            ObjectUtils.valiUpdate(t.getCreateUser(),TradingOrderSellerInformationMapper.class,OrderSellerInformation.getId(),"Synchronize");
            tradingOrderSellerInformationMapper.updateByPrimaryKeySelective(OrderSellerInformation);
        }
    }
}
