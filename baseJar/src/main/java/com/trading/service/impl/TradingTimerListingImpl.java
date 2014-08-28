package com.trading.service.impl;

import com.base.database.trading.mapper.TradingTimerListingMapper;
import com.base.database.trading.model.TradingTimerListing;
import com.base.database.trading.model.TradingTimerListingWithBLOBs;
import com.base.utils.common.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrtor on 2014/8/27.
 */
@Service
public class TradingTimerListingImpl implements com.trading.service.ITradingTimerListing {
    @Autowired
    private TradingTimerListingMapper tradingTimerListingMapper;

    @Override
    public void saveTradingTimer(TradingTimerListingWithBLOBs tradingTimerListing) throws Exception {
        ObjectUtils.toInitPojoForInsert(tradingTimerListing);
        this.tradingTimerListingMapper.insertSelective(tradingTimerListing);
    }


}
