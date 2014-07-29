package com.trading.service.impl;

import com.base.database.trading.mapper.TradingSellerprofilesMapper;
import com.base.database.trading.model.TradingSellerprofiles;
import com.base.database.trading.model.TradingShippingdetails;
import com.base.utils.common.ConvertPOJOUtil;
import com.base.utils.common.ObjectUtils;
import com.base.xmlpojo.trading.addproduct.SellerProfiles;
import com.base.xmlpojo.trading.addproduct.ShippingDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by cz on 2014/7/24.
 */
@Service
public class TradingSellerProfilesImpl implements com.trading.service.ITradingSellerProfiles {
    @Autowired
    private TradingSellerprofilesMapper tradingSellerprofilesMapper;

    @Override
    public void saveSellerprofiles(TradingSellerprofiles pojo){
        this.tradingSellerprofilesMapper.insert(pojo);
    }

    @Override
    public TradingSellerprofiles toDAOPojo(SellerProfiles sp) throws Exception {
        TradingSellerprofiles pojo = new TradingSellerprofiles();
        ObjectUtils.toPojo(pojo);
        ConvertPOJOUtil.convert(pojo,sp.getSellerPaymentProfile());
        ConvertPOJOUtil.convert(pojo,sp.getSellerShippingProfile());
        ConvertPOJOUtil.convert(pojo,sp.getSellerReturnProfile());
        return pojo;
    }
}
