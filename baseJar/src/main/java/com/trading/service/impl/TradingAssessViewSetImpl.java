package com.trading.service.impl;

import com.base.database.trading.mapper.TradingAssessViewSetMapper;
import com.base.database.trading.mapper.TradingListingReportMapper;
import com.base.database.trading.model.TradingAssessViewSet;
import com.base.database.trading.model.TradingAssessViewSetExample;
import com.base.database.trading.model.TradingListingReport;
import com.base.database.trading.model.TradingListingReportExample;
import com.base.utils.common.DateUtils;
import org.apache.http.impl.cookie.DateParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrtor on 2014/11/8.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TradingAssessViewSetImpl implements com.trading.service.ITradingAssessViewSet {
    @Autowired
    private TradingAssessViewSetMapper tradingAssessViewSetMapper;

    @Override
    public void save(TradingAssessViewSet tradingAssessViewSet){
        if(tradingAssessViewSet.getId()!=null){
            this.tradingAssessViewSetMapper.updateByPrimaryKeySelective(tradingAssessViewSet);
        }else{
            this.tradingAssessViewSetMapper.insertSelective(tradingAssessViewSet);
        }
    }

    @Override
    public TradingAssessViewSet selectByUserid(long userid) throws DateParseException {
        TradingAssessViewSetExample tlse = new TradingAssessViewSetExample();
        tlse.createCriteria().andCreateUserEqualTo(userid);
        List<TradingAssessViewSet> lits = this.tradingAssessViewSetMapper.selectByExample(tlse);
        if(lits!=null&&lits.size()>0){
            return lits.get(0);
        }else{
            return null;
        }
    }

}

