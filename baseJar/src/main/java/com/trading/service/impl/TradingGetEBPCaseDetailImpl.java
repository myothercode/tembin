package com.trading.service.impl;

import com.base.database.trading.mapper.TradingGetEBPCaseDetailMapper;
import com.base.database.trading.model.TradingGetEBPCaseDetail;
import com.base.database.trading.model.TradingGetEBPCaseDetailExample;
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
public class TradingGetEBPCaseDetailImpl implements com.trading.service.ITradingGetEBPCaseDetail {

    @Autowired
    private TradingGetEBPCaseDetailMapper tradingGetEBPCaseDetailMapper;

    @Override
    public void saveGetEBPCaseDetail(TradingGetEBPCaseDetail GetEBPCaseDetail) throws Exception {
        if(GetEBPCaseDetail.getId()==null){
            ObjectUtils.toInitPojoForInsert(GetEBPCaseDetail);
            tradingGetEBPCaseDetailMapper.insert(GetEBPCaseDetail);
        }else{
            TradingGetEBPCaseDetail t=tradingGetEBPCaseDetailMapper.selectByPrimaryKey(GetEBPCaseDetail.getId());
            Asserts.assertTrue(t != null && t.getCreateUser() != null, "没有找到记录或者记录创建者为空");
            ObjectUtils.valiUpdate(t.getCreateUser(),TradingGetEBPCaseDetailMapper.class,GetEBPCaseDetail.getId(),"Synchronize");
            tradingGetEBPCaseDetailMapper.updateByPrimaryKeySelective(GetEBPCaseDetail);
        }
    }

    @Override
    public List<TradingGetEBPCaseDetail> selectGetEBPCaseDetailByTransactionId(String transactionid) {
        TradingGetEBPCaseDetailExample example=new TradingGetEBPCaseDetailExample();
        TradingGetEBPCaseDetailExample.Criteria cr=example.createCriteria();
        cr.andTransactionidEqualTo(transactionid);
        List<TradingGetEBPCaseDetail> list=tradingGetEBPCaseDetailMapper.selectByExample(example);
        return list;
    }
}
