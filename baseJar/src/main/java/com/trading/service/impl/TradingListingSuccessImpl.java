package com.trading.service.impl;

import com.base.database.customtrading.mapper.ListingItemReportMapper;
import com.base.database.trading.mapper.TradingListingSuccessMapper;
import com.base.database.trading.model.TradingListingSuccess;
import com.base.database.trading.model.TradingListingSuccessExample;
import com.base.domains.SessionVO;
import com.base.domains.querypojos.ListingItemReportQuery;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.common.DateUtils;
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
public class TradingListingSuccessImpl implements com.trading.service.ITradingListingSuccess {
    @Autowired
    private TradingListingSuccessMapper tradingListingSuccessMapper;
    @Autowired
    private ListingItemReportMapper listingItemReportMapper;

    @Override
    public void save(TradingListingSuccess tradingListingSuccess){
        if(tradingListingSuccess.getId()!=null){
            this.tradingListingSuccessMapper.updateByPrimaryKeySelective(tradingListingSuccess);
        }else{
            this.tradingListingSuccessMapper.insertSelective(tradingListingSuccess);
        }
    }

    @Override
    public List<TradingListingSuccess> selectByItemid(String itemId){
        TradingListingSuccessExample tlse = new TradingListingSuccessExample();
        tlse.createCriteria().andItemIdEqualTo(itemId);
        return this.tradingListingSuccessMapper.selectByExample(tlse);
    }

    @Override
    public List<ListingItemReportQuery> selectListingItemReport(long userid,String type,String flag,String soldflag){
        SessionVO c= SessionCacheSupport.getSessionVO();
        SimpleDateFormat sdfmonth = new SimpleDateFormat("yyyy-MM");
        Map m = new HashMap();
        m.put("datetype",type);
        if("1".equals(type)){//当天反馈信息统计
            m.put("datestr", DateUtils.formatDate(new Date()));
        }else if("2".equals(type)){//昨天反馈信息统计
            m.put("datestr", DateUtils.formatDate(DateUtils.turnToYesterday(new Date())));
        }else if("3".equals(type)){//本周反馈信息统计
            m.put("startDate", DateUtils.formatDate(DateUtils.turnToWeekStart(new Date())));
            m.put("endDate",DateUtils.formatDate(DateUtils.turnToDateEnd(new Date())));
        }else if("4".equals(type)){//上周反馈信息统计
            Calendar date = Calendar.getInstance();
            date.setTime(new Date());
            date.set(Calendar.DATE, date.get(Calendar.DATE) - 7);
            m.put("startDate", DateUtils.formatDate(DateUtils.turnToWeekStart(date.getTime())));
            m.put("endDate",DateUtils.formatDate(DateUtils.turnToDateEnd(date.getTime())));
        }else if("5".equals(type)){//本月反馈信息统计
            m.put("datestr", sdfmonth.format(new Date()));
        }else if("6".equals(type)){//上月反馈信息统计
            Calendar date = Calendar.getInstance();
            date.setTime(new Date());
            date.set(Calendar.MONTH, date.get(Calendar.MONTH) - 1);
            m.put("datestr", sdfmonth.format(date.getTime()));
        }
        if(flag!=null){
            m.put("flag",flag);
        }
        if(soldflag!=null){
            m.put("soldflag",soldflag);
        }
        m.put("userid",userid);
        return this.listingItemReportMapper.selectListingItemReportList(m);
    }


    @Override
    public List<ListingItemReportQuery> selectListingItemReportFee(long userid,String type,String flag,String soldflag){
        SimpleDateFormat sdfmonth = new SimpleDateFormat("yyyy-MM");
        Map m = new HashMap();
        m.put("datetype",type);
        if("1".equals(type)){//当天反馈信息统计
            m.put("datestr", DateUtils.formatDate(new Date()));
        }else if("2".equals(type)){//昨天反馈信息统计
            m.put("datestr", DateUtils.formatDate(DateUtils.turnToYesterday(new Date())));
        }else if("3".equals(type)){//本周反馈信息统计
            m.put("startDate", DateUtils.formatDate(DateUtils.turnToWeekStart(new Date())));
            m.put("endDate",DateUtils.formatDate(DateUtils.turnToDateEnd(new Date())));
        }else if("4".equals(type)){//上周反馈信息统计
            Calendar date = Calendar.getInstance();
            date.setTime(new Date());
            date.set(Calendar.DATE, date.get(Calendar.DATE) - 7);
            m.put("startDate", DateUtils.formatDate(DateUtils.turnToWeekStart(date.getTime())));
            m.put("endDate",DateUtils.formatDate(DateUtils.turnToDateEnd(date.getTime())));
        }else if("5".equals(type)){//本月反馈信息统计
            m.put("datestr", sdfmonth.format(new Date()));
        }else if("6".equals(type)){//上月反馈信息统计
            Calendar date = Calendar.getInstance();
            date.setTime(new Date());
            date.set(Calendar.MONTH, date.get(Calendar.MONTH) - 1);
            m.put("datestr", sdfmonth.format(date.getTime()));
        }
        if(flag!=null){
            m.put("flag",flag);
        }
        if(soldflag!=null){
            m.put("soldflag",soldflag);
        }
        m.put("userid",userid);
        return this.listingItemReportMapper.selectListingItemReportFee(m);
    }

    @Override
    public List<ListingItemReportQuery> selectListingItemSales(String type,String flag,String soldflag){
        SessionVO c= SessionCacheSupport.getSessionVO();
        SimpleDateFormat sdfmonth = new SimpleDateFormat("yyyy-MM");
        Map m = new HashMap();
        m.put("datetype",type);
        if("1".equals(type)){//当天反馈信息统计
            m.put("datestr", DateUtils.formatDate(new Date()));
        }else if("2".equals(type)){//昨天反馈信息统计
            m.put("datestr", DateUtils.formatDate(DateUtils.turnToYesterday(new Date())));
        }else if("3".equals(type)){//本周反馈信息统计
            m.put("startDate", DateUtils.formatDate(DateUtils.turnToWeekStart(new Date())));
            m.put("endDate",DateUtils.formatDate(DateUtils.turnToDateEnd(new Date())));
        }else if("4".equals(type)){//上周反馈信息统计
            Calendar date = Calendar.getInstance();
            date.setTime(new Date());
            date.set(Calendar.DATE, date.get(Calendar.DATE) - 7);
            m.put("startDate", DateUtils.formatDate(DateUtils.turnToWeekStart(date.getTime())));
            m.put("endDate",DateUtils.formatDate(DateUtils.turnToDateEnd(date.getTime())));
        }else if("5".equals(type)){//本月反馈信息统计
            m.put("datestr", sdfmonth.format(new Date()));
        }else if("6".equals(type)){//上月反馈信息统计
            Calendar date = Calendar.getInstance();
            date.setTime(new Date());
            date.set(Calendar.MONTH, date.get(Calendar.MONTH) - 1);
            m.put("datestr", sdfmonth.format(date.getTime()));
        }
        if(flag!=null){
            m.put("flag",flag);
        }
        if(soldflag!=null){
            m.put("soldflag",soldflag);
        }
        m.put("userid",c.getId());
        return this.listingItemReportMapper.selectListingItemSales(m);
    }
}

