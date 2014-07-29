package com.trading.service.impl;

import com.base.database.trading.mapper.TradingItemMapper;
import com.base.database.trading.model.TradingItem;
import com.base.utils.common.ConvertPOJOUtil;
import com.base.utils.common.ObjectUtils;
import com.base.xmlpojo.trading.addproduct.Charity;
import com.base.xmlpojo.trading.addproduct.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 商品信息
 * Created by cz on 2014/7/23.
 */
@Service
public class TradingItemImpl implements com.trading.service.ITradingItem {

    @Autowired
    private TradingItemMapper tradingItemMapper;

    @Override
    public void saveTradingItem(TradingItem pojo){
        this.tradingItemMapper.insertSelective(pojo);
    }

    @Override
    public TradingItem toDAOPojo(Item item) throws Exception {
        TradingItem pojo = new TradingItem();
        ObjectUtils.toPojo(pojo);
        ConvertPOJOUtil.convert(pojo, item);
        ConvertPOJOUtil.convert(pojo, item.getBestOfferDetails());
        ConvertPOJOUtil.convert(pojo, item.getPrimaryCategory());
        ConvertPOJOUtil.convert(pojo, item.getQuantityInfo());
        ConvertPOJOUtil.convert(pojo, item.getQuantityRestrictionPerBuyer());

        return pojo;
    }
}
