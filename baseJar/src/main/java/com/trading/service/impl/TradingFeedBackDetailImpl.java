package com.trading.service.impl;

import com.base.database.customtrading.mapper.ClientAssessFeedBackMapper;
import com.base.database.customtrading.mapper.FeedBackReportMapper;
import com.base.database.trading.mapper.TradingFeedBackDetailMapper;
import com.base.database.trading.model.TradingFeedBackDetail;
import com.base.database.trading.model.TradingFeedBackDetailExample;
import com.base.domains.SessionVO;
import com.base.domains.querypojos.FeedBackReportQuery;
import com.base.mybatis.page.Page;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.common.DateUtils;
import com.base.utils.common.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrtor on 2014/8/16. */
@Service
@Transactional(rollbackFor = Exception.class)
public class TradingFeedBackDetailImpl implements com.trading.service.ITradingFeedBackDetail {

    @Autowired
    private TradingFeedBackDetailMapper tradingFeedBackDetailMapper;
    @Autowired
    private FeedBackReportMapper feedBackReportMapper;
    @Autowired
    private ClientAssessFeedBackMapper clientAssessFeedBackMapper;
    @Override
    public void saveFeedBackDetail(List<TradingFeedBackDetail> lifb) throws Exception {
        for(TradingFeedBackDetail tfbd : lifb){
           /* ObjectUtils.toInitPojoForInsert(lifb);*/
            if(tfbd.getId()==null){
                ObjectUtils.toInitPojoForInsert(tfbd);
                this.tradingFeedBackDetailMapper.insertSelective(tfbd);
            }else{
                this.tradingFeedBackDetailMapper.updateByPrimaryKeySelective(tfbd);
            }

        }
    }

    @Override
    public TradingFeedBackDetail selectFeedBackDetailByTransactionId(String transactionId) {
        TradingFeedBackDetailExample example=new TradingFeedBackDetailExample();
        TradingFeedBackDetailExample.Criteria cr=example.createCriteria();
        cr.andTransactionidEqualTo(transactionId);
        List<TradingFeedBackDetail> list=tradingFeedBackDetailMapper.selectByExample(example);
        return list!=null&&list.size()>0?list.get(0):null;
    }

    @Override
    public List<TradingFeedBackDetail> selectFeedBackDetailByAutoMessageFlag(List<String> types) {
        TradingFeedBackDetailExample example=new TradingFeedBackDetailExample();
        TradingFeedBackDetailExample.Criteria cr=example.createCriteria();
        cr.andAutomessageflagIsNull();
        cr.andCommenttypeIn(types);
        cr.andSenttimeLessThan(new Date());
        List<TradingFeedBackDetail> list=tradingFeedBackDetailMapper.selectByExample(example);
        return list;
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

    @Override
    public TradingFeedBackDetail selectFeedBackDetailByBuyerAndFeedBackId(String buyer, String feedBackId) {
        TradingFeedBackDetailExample example=new TradingFeedBackDetailExample();
        TradingFeedBackDetailExample.Criteria cr=example.createCriteria();
        cr.andCommentinguserEqualTo(buyer);
        cr.andFeedbackidEqualTo(feedBackId);
        List<TradingFeedBackDetail> list=tradingFeedBackDetailMapper.selectByExample(example);
        return list!=null&&list.size()>0?list.get(0):null;
    }

    @Override
    public List<FeedBackReportQuery> selectFeedBackReportList(String type){
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
        m.put("userid",c.getId());
        return this.feedBackReportMapper.selectFeedBackReportList(m);
    }

    @Override
    public List<TradingFeedBackDetail> selectClientAssessFeedBackList(Map m,Page page){
        return this.clientAssessFeedBackMapper.selectClientAssessFeedBackList(m,page);
    }
}
