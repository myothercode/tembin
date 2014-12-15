package com.trading.service.impl;

import com.base.database.customtrading.mapper.ListingDataAmendMapper;
import com.base.database.customtrading.mapper.ListingDataMapper;
import com.base.database.trading.mapper.TradingListingAmendMapper;
import com.base.database.trading.mapper.TradingListingDataMapper;
import com.base.database.trading.model.*;
import com.base.domains.querypojos.ListingDataAmendQuery;
import com.base.domains.querypojos.ListingDataQuery;
import com.base.mybatis.page.Page;
import com.base.utils.cache.DataDictionarySupport;
import com.base.utils.common.DateUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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
    @Value("ITEM_LIST_ICON_URL")
    private String item_list_icon_url;
    @Override
    public List<ListingDataQuery> selectData(Map map, Page page){
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
    public void insertTradingListingAmend(TradingListingAmendWithBLOBs tla){
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

    @Override
    public void saveTradingListingDataByTradingItem(TradingItem tradingItem,String res){
        Document document= null;
        try {
            document = DocumentHelper.parseText(res);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        Element rootElt = document.getRootElement();
        Date startDate= DateUtils.returnDate(rootElt.elementText("StartTime"));
        Date endDate=DateUtils.returnDate(rootElt.elementText("EndTime"));
        TradingListingData tld = new TradingListingData();
        tld.setCreateDate(new Date());
        tld.setEbayAccount(tradingItem.getEbayAccount());
        tld.setIsFlag("0");
        tld.setQuantity(tradingItem.getQuantity());
        tld.setQuantitysold(0L);
        tld.setSku(tradingItem.getSku());
        tld.setBuyitnowprice(0d);
        tld.setPrice(tradingItem.getStartprice());
        tld.setCurrencyId(tradingItem.getCurrency());
        tld.setItemId(tradingItem.getItemId());
        tld.setListingduration(tradingItem.getListingduration());
        tld.setTitle(tradingItem.getTitle());
        tld.setSubtitle(tradingItem.getSubtitle());
        tld.setSite(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(tradingItem.getSite())).getValue());
        tld.setSiteName(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(tradingItem.getSite())).getValue());
        tld.setUpdateDate(new Date());
        tld.setShippingPrice(0d);
        tld.setReserveprice(0d);
        tld.setPicUrl(item_list_icon_url+tradingItem.getItemId()+".jpg");
        tld.setListingType(tradingItem.getListingtype());
        tld.setStarttime(startDate);
        tld.setEndtime(endDate);
        this.tradingListingDataMapper.insertSelective(tld);
    }
}
