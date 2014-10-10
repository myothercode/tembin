package com.trading.service.impl;

import com.base.database.customtrading.mapper.ListingDataAmendMapper;
import com.base.database.customtrading.mapper.ListingDataMapper;
import com.base.database.trading.mapper.TradingListingAmendMapper;
import com.base.database.trading.mapper.TradingListingDataMapper;
import com.base.database.trading.model.TradingListingAmend;
import com.base.database.trading.model.TradingListingData;
import com.base.database.trading.model.TradingListingDataExample;
import com.base.domains.querypojos.ListingDataAmendQuery;
import com.base.mybatis.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/9/23.
 * 在线商品查询
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TradingListingDataImpl implements com.trading.service.ITradingListingData {
    @Autowired
    private ListingDataMapper listingDataMapper;
    @Autowired
    private ListingDataAmendMapper listingDataAmendMapper;
    @Autowired
    private TradingListingDataMapper tradingListingDataMapper;
    @Autowired
    private TradingListingAmendMapper tradingListingAmendMapper;
    @Override
    public List<TradingListingData> selectData(Map map, Page page){
        return this.listingDataMapper.selectByExample(map,page);
    }

    @Override
    public List<ListingDataAmendQuery> selectAmendData(Map map, Page page){
        return listingDataAmendMapper.selectByExample(map,page);
    }
    @Override
    public TradingListingData selectById(Long id){
        return this.tradingListingDataMapper.selectByPrimaryKey(id);
    }

    @Override
    public TradingListingData selectByItemid(String itemid){
        TradingListingDataExample tld = new TradingListingDataExample();
        tld.createCriteria().andItemIdEqualTo(itemid);
        List<TradingListingData> litl = this.tradingListingDataMapper.selectByExample(tld);
        if(litl==null||litl.size()==0){
            return null;
        }else{
            return litl.get(0);
        }
    }

    @Override
    public void updateTradingListingData(TradingListingData tld){
        this.tradingListingDataMapper.updateByPrimaryKeySelective(tld);
    }

    @Override
    public void insertTradingListingAmend(TradingListingAmend tla){
        this.tradingListingAmendMapper.insertSelective(tla);
    }
    @Override
    public List<TradingListingData> selectByList(String sku,String ebayAccount){
        TradingListingDataExample tlde = new TradingListingDataExample();
        tlde.createCriteria().andSkuEqualTo(sku).andEbayAccountEqualTo(ebayAccount);
        return this.tradingListingDataMapper.selectByExample(tlde);
    }

    @Override
    public void saveTradingListingDataList(List<TradingListingData> litld){
        for(TradingListingData tld: litld){
            if(tld.getId()==null){
                this.tradingListingDataMapper.insertSelective(tld);
            }else{
                this.tradingListingDataMapper.updateByPrimaryKey(tld);
            }
        }
    }
}
