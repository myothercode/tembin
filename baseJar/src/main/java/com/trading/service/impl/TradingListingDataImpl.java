package com.trading.service.impl;

import com.base.database.customtrading.mapper.ListingDataMapper;
import com.base.database.trading.mapper.TradingListingDataMapper;
import com.base.database.trading.model.TradingListingData;
import com.base.database.trading.model.TradingListingDataExample;
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

    @Override
    public List<TradingListingData> selectData(Map map, Page page){
        return this.listingDataMapper.selectByExample(map,page);
    }
}
