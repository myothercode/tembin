package com.trading.service.impl;

import com.base.database.trading.mapper.TradingListingdesignerMapper;
import com.base.database.trading.model.TradingListingdesigner;
import com.base.utils.common.ConvertPOJOUtil;
import com.base.utils.common.ObjectUtils;
import com.base.xmlpojo.trading.addproduct.ListingDesigner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by cz on 2014/7/24.
 */
@Service
public class TradingListingDesignerImpl implements com.trading.service.ITradingListingDesigner {
    @Autowired
    private TradingListingdesignerMapper tradingListingdesignerMapper;

    @Override
    public void saveListingdesigner(TradingListingdesigner pojo){
        this.tradingListingdesignerMapper.insert(pojo);
    }

    @Override
    public TradingListingdesigner toDAOPojo(ListingDesigner ld) throws Exception {
        TradingListingdesigner pojo = new TradingListingdesigner();
        ObjectUtils.toPojo(pojo);
        ConvertPOJOUtil.convert(pojo,ld);
        return pojo;
    }
}
