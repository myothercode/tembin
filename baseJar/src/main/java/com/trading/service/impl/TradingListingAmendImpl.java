package com.trading.service.impl;

import com.base.database.trading.mapper.TradingListingAmendMapper;
import com.base.database.trading.model.TradingListingAmend;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrtor on 2014/9/24.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TradingListingAmendImpl {
    private TradingListingAmendMapper tradingListingAmendMapper;

    public void saveListingAmend(TradingListingAmend tradingListingAmend){
        this.tradingListingAmendMapper.insertSelective(tradingListingAmend);
    }


}
