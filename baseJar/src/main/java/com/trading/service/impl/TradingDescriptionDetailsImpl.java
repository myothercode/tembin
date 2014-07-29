package com.trading.service.impl;

import com.base.database.trading.mapper.TradingDescriptionDetailsMapper;
import com.base.database.trading.model.TradingDescriptionDetailsWithBLOBs;
import com.base.utils.common.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;

/**
 * 商品描述模块
 * Created by cz on 2014/7/23.
 */
@Service
public class TradingDescriptionDetailsImpl implements com.trading.service.ITradingDescriptionDetails {
    @Autowired
    private TradingDescriptionDetailsMapper tradingDescriptionDetailsMapper;

    @Override
    public void saveDescriptionDetails(TradingDescriptionDetailsWithBLOBs pojo){
        this.tradingDescriptionDetailsMapper.insertSelective(pojo);
    }
    @Override
    public TradingDescriptionDetailsWithBLOBs toDAOPojo(String payInfo, String shippingInfo, String contactInfo, String guaranteeInfo, String feedbackInfo) throws Exception {
        TradingDescriptionDetailsWithBLOBs pojo = new TradingDescriptionDetailsWithBLOBs();
        pojo.setPayInfo(payInfo);
        pojo.setShippingInfo(shippingInfo);
        pojo.setContactInfo(contactInfo);
        pojo.setGuaranteeInfo(guaranteeInfo);
        pojo.setFeedbackInfo(feedbackInfo);
        ObjectUtils.toPojo(pojo);
        return pojo;
    }
}
