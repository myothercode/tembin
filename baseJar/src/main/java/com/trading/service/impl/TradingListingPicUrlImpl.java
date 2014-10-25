package com.trading.service.impl;

import com.base.database.customtrading.mapper.ItemAddressMapper;
import com.base.database.trading.mapper.TradingItemAddressMapper;
import com.base.database.trading.mapper.TradingListingpicUrlMapper;
import com.base.database.trading.model.TradingItemAddress;
import com.base.database.trading.model.TradingListingpicUrl;
import com.base.database.trading.model.TradingListingpicUrlExample;
import com.base.domains.querypojos.ItemAddressQuery;
import com.base.mybatis.page.Page;
import com.base.utils.common.ObjectUtils;
import com.base.utils.exception.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 物品所在地
 * Created by cz on 2014/7/23.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TradingListingPicUrlImpl implements com.trading.service.ITradingListingPicUrl {
    @Autowired
    private TradingListingpicUrlMapper tradingListingpicUrlMapper;

    @Override
    public void saveListingPicUrl(TradingListingpicUrl tradingListingpicUrl) throws Exception {
        if(tradingListingpicUrl.getId()==null){
            this.tradingListingpicUrlMapper.insertSelective(tradingListingpicUrl);
        }else{
            this.tradingListingpicUrlMapper.updateByPrimaryKeySelective(tradingListingpicUrl);
        }
    }

    @Override
    public List<TradingListingpicUrl> selectByMackId(String mackId){
        TradingListingpicUrlExample tlue = new TradingListingpicUrlExample();
        tlue.createCriteria().andMackIdEqualTo(mackId);
        return this.tradingListingpicUrlMapper.selectByExample(tlue);
    }

}
