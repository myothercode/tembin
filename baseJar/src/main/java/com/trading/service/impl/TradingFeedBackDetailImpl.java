package com.trading.service.impl;

import com.base.database.trading.mapper.TradingFeedBackDetailMapper;
import com.base.database.trading.model.TradingFeedBackDetail;
import com.base.utils.common.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrtor on 2014/8/16.
 */
@Service
public class TradingFeedBackDetailImpl implements com.trading.service.ITradingFeedBackDetail {

    @Autowired
    private TradingFeedBackDetailMapper tradingFeedBackDetailMapper;

    @Override
    public void saveFeedBackDetail(List<TradingFeedBackDetail> lifb) throws Exception {
        for(TradingFeedBackDetail tfbd : lifb){
            ObjectUtils.toInitPojoForInsert(lifb);
            this.tradingFeedBackDetailMapper.insertSelective(tfbd);
        }
    }
}
