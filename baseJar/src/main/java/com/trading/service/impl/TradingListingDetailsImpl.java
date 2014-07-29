package com.trading.service.impl;

import com.base.database.trading.mapper.TradingListingdetailsMapper;
import com.base.database.trading.model.TradingListingdetails;
import com.base.utils.common.ConvertPOJOUtil;
import com.base.utils.common.ObjectUtils;
import com.base.xmlpojo.trading.addproduct.ListingDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by cz on 2014/7/24.
 */
@Service
public class TradingListingDetailsImpl implements com.trading.service.ITradingListingDetails {

    @Autowired
    private TradingListingdetailsMapper tradingListingdetailsMapper;

    @Override
    public void saveListingdetails(TradingListingdetails pojo){
        this.tradingListingdetailsMapper.insert(pojo);
    }

    @Override
    public TradingListingdetails toDAOPojo(ListingDetails listingDetails) throws Exception {
        TradingListingdetails pojo = new TradingListingdetails();
        ObjectUtils.toPojo(pojo);
        ConvertPOJOUtil.convert(pojo,listingDetails);
        return pojo;
    }
}
