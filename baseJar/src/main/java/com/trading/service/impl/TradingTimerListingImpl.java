package com.trading.service.impl;

import com.base.database.trading.mapper.TradingItemMapper;
import com.base.database.trading.mapper.TradingTimerListingMapper;
import com.base.database.trading.model.TradingTimerListing;
import com.base.database.trading.model.TradingTimerListingExample;
import com.base.database.trading.model.TradingTimerListingWithBLOBs;
import com.base.utils.common.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrtor on 2014/8/27.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TradingTimerListingImpl implements com.trading.service.ITradingTimerListing {
    @Autowired
    private TradingTimerListingMapper tradingTimerListingMapper;

    @Override
    public void saveTradingTimer(TradingTimerListingWithBLOBs tradingTimerListing) throws Exception {
        ObjectUtils.toInitPojoForInsert(tradingTimerListing);
        TradingTimerListingWithBLOBs ttlw = this.selectByItem(tradingTimerListing.getItem()+"");
        if(ttlw==null){
            this.tradingTimerListingMapper.insertSelective(tradingTimerListing);
        }else{
            tradingTimerListing.setId(ttlw.getId());
            tradingTimerListing.setCheckFlag("0");
            tradingTimerListing.setTimerFlag("0");
            this.tradingTimerListingMapper.updateByPrimaryKeyWithBLOBs(tradingTimerListing);
        }
    }

    @Override
    public void delTradingTimer(String itemid){
        TradingTimerListingExample ttle = new TradingTimerListingExample();
        ttle.createCriteria().andItemEqualTo(Long.parseLong(itemid)).andCheckFlagEqualTo("0").andTimerFlagEqualTo("0");
        List<TradingTimerListingWithBLOBs> littl = this.tradingTimerListingMapper.selectByExampleWithBLOBs(ttle);
        if(littl==null||littl.size()==0){
            //未找到数据
        }else{
            for(TradingTimerListingWithBLOBs ttl :littl){
                ttl.setCheckFlag("1");
                this.tradingTimerListingMapper.updateByPrimaryKeySelective(ttl);
            }
        }
    }

    @Override
    public TradingTimerListingWithBLOBs selectByItem(String itemId){
        TradingTimerListingExample ttle =new TradingTimerListingExample();
        ttle.createCriteria().andItemEqualTo(Long.parseLong(itemId));
        List<TradingTimerListingWithBLOBs> libl = this.tradingTimerListingMapper.selectByExampleWithBLOBs(ttle);
        if(libl!=null&&libl.size()>0){
            return libl.get(0);
        }else{
            return null;
        }
    }


}
