package com.trading.service.impl;

import com.base.database.trading.mapper.TradingShippingservicecostoverrideMapper;
import com.base.database.trading.model.TradingShippingservicecostoverride;
import com.base.utils.common.ConvertPOJOUtil;
import com.base.utils.common.ObjectUtils;
import com.base.xmlpojo.trading.addproduct.ShippingServiceCostOverride;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by cz on 2014/7/24.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TradingShippingServiceCostOverrideImpl implements com.trading.service.ITradingShippingServiceCostOverride {
    @Autowired
    private TradingShippingservicecostoverrideMapper ts;

    @Override
    public void saveShippingServiceCostOverride(TradingShippingservicecostoverride pojo){
        this.ts.insert(pojo);
    }

    @Override
    public TradingShippingservicecostoverride toDAOPojo(ShippingServiceCostOverride ssco) throws Exception {
        TradingShippingservicecostoverride pojo = new TradingShippingservicecostoverride();
        ObjectUtils.toInitPojoForInsert(pojo);
        ConvertPOJOUtil.convert(pojo,ssco);
        return pojo;
    }
}
