package com.trading.service.impl;

import com.base.database.trading.mapper.TradingFeedBackDetailMapper;
import com.base.database.trading.model.TradingFeedBackDetail;
import com.base.database.trading.model.TradingFeedBackDetailExample;
import com.base.utils.common.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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

    @Override
    public int selectByCount(Map m){
        TradingFeedBackDetailExample tfb = new TradingFeedBackDetailExample();
        TradingFeedBackDetailExample.Criteria c = tfb.createCriteria();
        if(m.get("itemid")!=null&&!"".equals(m.get("itemid"))){
            c.andItemidEqualTo(m.get("itemid").toString());
        }
        if(m.get("commentType")!=null&&!"".equals(m.get("commentType"))){
            c.andCommenttypeEqualTo(m.get("commentType").toString());
        }
        List<TradingFeedBackDetail> litf = this.tradingFeedBackDetailMapper.selectByExampleWithBLOBs(tfb);
        if(litf==null)
            return 0;
        return litf.size();
    }
}
