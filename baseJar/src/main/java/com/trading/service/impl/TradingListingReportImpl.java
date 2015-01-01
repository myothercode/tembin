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
    public List<TradingListingReport> selectByNowDate(String dataType,String userId) throws DateParseException {
        TradingListingReportExample tlse = new TradingListingReportExample();
        tlse.createCriteria().andCreateDateBetween(DateUtils.turnToDateStart(new Date()),DateUtils.turnToDateEnd(new Date())).andDatatypeEqualTo(dataType).andUserIdEqualTo(userId);
        return this.tradingListingReportMapper.selectByExample(tlse);
    }

    /**
     * 初始化统计数据
     * @param userId
     */
    @Override
    public void initListingReport(long userId){
        Map m = new HashMap();
        m.put("0","1");
        m.put("1","2");
        m.put("2","3");
        m.put("3","4");
        m.put("4","5");
        m.put("5","6");
        for(int i=0;i<m.size();i++){
            TradingListingReport tlr = new TradingListingReport();
            tlr.setDatatype(m.get(i+"").toString());
            tlr.setDay("0");
            tlr.setYesterday("0");
            tlr.setWeek("0");
            tlr.setThatweek("0");
            tlr.setMonth("0");
            tlr.setThatmonth("0");
            tlr.setUserId(userId+"");
            tlr.setCreateDate(new Date());
            this.save(tlr);
        }
    }
}

