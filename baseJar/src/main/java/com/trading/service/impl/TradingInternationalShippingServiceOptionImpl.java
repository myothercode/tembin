package com.trading.service.impl;

import com.base.database.trading.mapper.TradingInternationalshippingserviceoptionMapper;
import com.base.database.trading.model.TradingInternationalshippingserviceoption;
import com.base.utils.common.ConvertPOJOUtil;
import com.base.utils.common.ObjectUtils;
import com.base.xmlpojo.trading.addproduct.InternationalShippingServiceOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by cz on 2014/7/24.
 */
@Service
public class TradingInternationalShippingServiceOptionImpl implements com.trading.service.ITradingInternationalShippingServiceOption {
    @Autowired
    private TradingInternationalshippingserviceoptionMapper tradingInternationalshippingserviceoptionMapper;

    @Override
    public void saveInternationalShippingServiceOption(TradingInternationalshippingserviceoption pojo){
        this.tradingInternationalshippingserviceoptionMapper.insert(pojo);
    }

    @Override
    public TradingInternationalshippingserviceoption toDAOPojo(InternationalShippingServiceOption isso) throws Exception {
        TradingInternationalshippingserviceoption pojo = new TradingInternationalshippingserviceoption();
        ObjectUtils.toPojo(pojo);
        ConvertPOJOUtil.convert(pojo,isso);
        return pojo;
    }
}
