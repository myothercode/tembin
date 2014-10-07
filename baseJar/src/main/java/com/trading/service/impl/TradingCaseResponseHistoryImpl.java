package com.trading.service.impl;

import com.base.database.trading.mapper.TradingCaseResponseHistoryMapper;
import com.base.database.trading.model.TradingCaseResponseHistory;
import com.base.database.trading.model.TradingCaseResponseHistoryExample;
import com.base.utils.common.ObjectUtils;
import com.base.utils.exception.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 退货政策
 * Created by lq on 2014/7/29.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TradingCaseResponseHistoryImpl implements com.trading.service.ITradingCaseResponseHistory {

    @Autowired
    private TradingCaseResponseHistoryMapper tradingCaseResponseHistoryMapper;

    @Override
    public void saveCaseResponseHistory(TradingCaseResponseHistory CaseResponseHistory) throws Exception {
        if(CaseResponseHistory.getId()==null){
            ObjectUtils.toInitPojoForInsert(CaseResponseHistory);
            tradingCaseResponseHistoryMapper.insert(CaseResponseHistory);
        }else{
            TradingCaseResponseHistory t=tradingCaseResponseHistoryMapper.selectByPrimaryKey(CaseResponseHistory.getId());
            Asserts.assertTrue(t != null && t.getCreateUser() != null, "没有找到记录或者记录创建者为空");
            ObjectUtils.valiUpdate(t.getCreateUser(),TradingCaseResponseHistoryMapper.class,CaseResponseHistory.getId(),"Synchronize");
            tradingCaseResponseHistoryMapper.updateByPrimaryKeySelective(CaseResponseHistory);
        }
    }

    @Override
    public List<TradingCaseResponseHistory> selectCaseResponseHistoryByEBPId(Long Id) {
        TradingCaseResponseHistoryExample example=new TradingCaseResponseHistoryExample();
        TradingCaseResponseHistoryExample.Criteria cr=example.createCriteria();
        cr.andEbpcasedetailIdEqualTo(Id);
        List<TradingCaseResponseHistory> list=tradingCaseResponseHistoryMapper.selectByExample(example);
        return list;
    }

    @Override
    public void deleteCaseResponseHistory(TradingCaseResponseHistory CaseResponseHistory) throws Exception {
        if(CaseResponseHistory!=null&&CaseResponseHistory.getId()!=null){
            tradingCaseResponseHistoryMapper.deleteByPrimaryKey(CaseResponseHistory.getId());
        }
    }
}
