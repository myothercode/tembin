package com.trading.service.impl;

import com.base.database.customtrading.mapper.ListingItemReportMapper;
import com.base.database.trading.mapper.TradingListingReportMapper;
import com.base.database.trading.mapper.TradingListingSuccessMapper;
import com.base.database.trading.model.TradingListingReport;
import com.base.database.trading.model.TradingListingReportExample;
import com.base.database.trading.model.TradingListingSuccess;
import com.base.database.trading.model.TradingListingSuccessExample;
import com.base.domains.querypojos.ListingItemReportQuery;
import com.base.utils.common.DateUtils;
import org.apache.http.impl.cookie.DateParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrtor on 2014/11/8.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TradingListingReportImpl implements com.trading.service.ITradingListingReport {
    @Autowired
    private TradingListingReportMapper tradingListingReportMapper;

    @Override
    public void save(TradingListingReport tradingListingReport){
        if(tradingListingReport.getId()!=null){
            this.tradingListingReportMapper.updateByPrimaryKeySelective(tradingListingReport);
        }else{
            this.tradingListingReportMapper.insertSelective(tradingListingReport);
        }
    }

    @Override
    public List<TradingListingReport> selectByNowDate(String dataType) throws DateParseException {
        TradingListingReportExample tlse = new TradingListingReportExample();
        tlse.createCriteria().andCreateDateBetween(DateUtils.turnToDateStart(new Date()),DateUtils.turnToDateEnd(new Date())).andDatatypeEqualTo(dataType);
        return this.tradingListingReportMapper.selectByExample(tlse);
    }

}

