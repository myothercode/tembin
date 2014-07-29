package com.trading.service.impl;

import com.base.database.trading.mapper.TradingShippingdetailsMapper;
import com.base.database.trading.model.TradingShippingdetails;
import com.base.utils.common.ConvertPOJOUtil;
import com.base.utils.common.ObjectUtils;
import com.base.xmlpojo.trading.addproduct.ShippingDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;

/**
 * 运输选项
 * Created by cz on 2014/7/23.
 */
@Service
public class TradingShippingDetailsImpl implements com.trading.service.ITradingShippingDetails {
    @Autowired
    private TradingShippingdetailsMapper tradingShippingdetailsMapper;

    @Override
    public void saveShippingDetails(TradingShippingdetails pojo){
        this.tradingShippingdetailsMapper.insertSelective(pojo);
    }

    @Override
    public TradingShippingdetails toDAOPojo(ShippingDetails shippingDetails) throws Exception {
        TradingShippingdetails pojo = new TradingShippingdetails();
        ObjectUtils.toPojo(pojo);
        ConvertPOJOUtil.convert(pojo,shippingDetails);
        ConvertPOJOUtil.convert(pojo,shippingDetails.getCalculatedShippingRate());
        ConvertPOJOUtil.convert(pojo,shippingDetails.getInsuranceDetails());
        ConvertPOJOUtil.convert(pojo,shippingDetails.getRateTableDetails());
        ConvertPOJOUtil.convert(pojo,shippingDetails.getSalesTax());
        return pojo;
    }
}
