package com.trading.service.impl;

import com.base.database.trading.mapper.TradingFeedBackDetailMapper;
import com.base.database.trading.model.TradingFeedBackDetail;
import com.base.database.trading.model.TradingFeedBackDetailExample;
import com.base.utils.common.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/8/16. */
@Service
@Transactional(rollbackFor = Exception.class)
public class TradingFeedBackDetailImpl implements com.trading.service.ITradingFeedBackDetail {

    @Autowired
    private TradingFeedBackDetailMapper tradingFeedBackDetailMapper;

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
}
