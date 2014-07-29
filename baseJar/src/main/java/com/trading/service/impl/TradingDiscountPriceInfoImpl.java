package com.trading.service.impl;

import com.base.database.trading.mapper.TradingDiscountpriceinfoMapper;
import com.base.database.trading.model.TradingDiscountpriceinfo;
import com.base.utils.common.ConvertPOJOUtil;
import com.base.utils.common.ObjectUtils;
import com.base.xmlpojo.trading.addproduct.DiscountPriceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Administrtor on 2014/7/23.
 */
@Service
public class TradingDiscountPriceInfoImpl implements com.trading.service.ITradingDiscountPriceInfo {
    @Autowired
    private TradingDiscountpriceinfoMapper tradingDiscountpriceinfoMapper;

    @Override
    public void saveDiscountpriceinfo(TradingDiscountpriceinfo pojo){
        this.tradingDiscountpriceinfoMapper.insertSelective(pojo);
    }

    @Override
    public TradingDiscountpriceinfo toDAOPojo(DiscountPriceInfo discountPriceInfo) throws Exception {
        TradingDiscountpriceinfo pojo = new TradingDiscountpriceinfo();
        ObjectUtils.toPojo(pojo);
        ConvertPOJOUtil.convert(pojo,discountPriceInfo);
        return pojo;
    }
}
