package com.trading.service.impl;

import com.base.database.trading.mapper.TradingShippingserviceoptionsMapper;
import com.base.database.trading.model.TradingShippingserviceoptions;
import com.base.utils.common.ConvertPOJOUtil;
import com.base.utils.common.ObjectUtils;
import com.base.xmlpojo.trading.addproduct.ShippingServiceOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by cz on 2014/7/24.
 */
@Service
public class TradingShippingServiceOptionsImpl implements com.trading.service.ITradingShippingServiceOptions {
    @Autowired
    private TradingShippingserviceoptionsMapper tsm;

    @Override
    public void saveShippingServiceOptions(TradingShippingserviceoptions pojo){
        this.tsm.insert(pojo);
    }

    @Override
    public TradingShippingserviceoptions toDAOPojo(ShippingServiceOptions sso) throws Exception {
        TradingShippingserviceoptions pojo = new TradingShippingserviceoptions();
        ObjectUtils.toPojo(pojo);
        ConvertPOJOUtil.convert(pojo,sso);
        return pojo;
    }
}
