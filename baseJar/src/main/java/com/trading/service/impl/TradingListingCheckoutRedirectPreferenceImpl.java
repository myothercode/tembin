package com.trading.service.impl;

import com.base.database.trading.mapper.TradingListingcheckoutredirectpreferenceMapper;
import com.base.database.trading.model.TradingListingcheckoutredirectpreference;
import com.base.utils.common.ConvertPOJOUtil;
import com.base.utils.common.ObjectUtils;
import com.base.xmlpojo.trading.addproduct.ListingCheckoutRedirectPreference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by cz on 2014/7/24.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TradingListingCheckoutRedirectPreferenceImpl implements com.trading.service.ITradingListingCheckoutRedirectPreference {
    @Autowired
    private TradingListingcheckoutredirectpreferenceMapper tradingListMapper;

    @Override
    public void saveListingCheckoutRedirectPreference(TradingListingcheckoutredirectpreference pojo){
        this.tradingListMapper.insert(pojo);
    }

    @Override
    public  TradingListingcheckoutredirectpreference toDAOPojo(ListingCheckoutRedirectPreference lcrp) throws Exception {
        TradingListingcheckoutredirectpreference pojo = new TradingListingcheckoutredirectpreference();
        ObjectUtils.toInitPojoForInsert(pojo);
        ConvertPOJOUtil.convert(pojo,lcrp);
        return pojo;
    }
}
