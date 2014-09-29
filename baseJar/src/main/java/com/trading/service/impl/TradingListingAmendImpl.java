package com.trading.service.impl;

import com.base.database.trading.mapper.TradingListingAmendMapper;
import com.base.database.trading.model.TradingListingAmend;
import com.base.database.trading.model.TradingListingAmendExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrtor on 2014/9/24.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TradingListingAmendImpl implements com.trading.service.ITradingListingAmend {
    @Autowired
    private TradingListingAmendMapper tradingListingAmendMapper;
    @Override
    public void saveListingAmend(TradingListingAmend tradingListingAmend){
        this.tradingListingAmendMapper.insertSelective(tradingListingAmend);
    }
    @Override
    public TradingListingAmend selectByItemID(String itemid, String amendtype){
        TradingListingAmendExample tlae = new TradingListingAmendExample();
        tlae.createCriteria().andItemEqualTo(Long.parseLong(itemid)).andAmendTypeEqualTo(amendtype).andIsFlagEqualTo("1");
        List<TradingListingAmend> li = this.tradingListingAmendMapper.selectByExampleWithBLOBs(tlae);
        if(li==null||li.size()==0){
            return null;
        }else{
            return li.get(0);
        }
    }

}
