package com.trading.service.impl;

import com.base.database.trading.mapper.TradingShippingpackagedetailsMapper;
import com.base.database.trading.model.TradingShippingpackagedetails;
import com.base.utils.common.ConvertPOJOUtil;
import com.base.utils.common.ObjectUtils;
import com.base.xmlpojo.trading.addproduct.ShippingPackageDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by cz on 2014/7/24.
 */
@Service
public class TradingShippingPackageDetailsImpl implements com.trading.service.ITradingShippingPackageDetails {
    @Autowired
    private TradingShippingpackagedetailsMapper tsm;

    @Override
    public void saveShippingPackageDetails(TradingShippingpackagedetails pojo){
        this.tsm.insert(pojo);
    }
    @Override
    public TradingShippingpackagedetails toDAOPojo(ShippingPackageDetails spd) throws Exception {
        TradingShippingpackagedetails pojo = new TradingShippingpackagedetails();
        ObjectUtils.toInitPojoForInsert(pojo);
        ConvertPOJOUtil.convert(pojo,spd);
        return pojo;
    }
}
