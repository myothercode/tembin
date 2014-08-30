package com.trading.service.impl;

import com.base.database.trading.mapper.TradingCasePaymentDetailMapper;
import com.base.database.trading.model.TradingCasePaymentDetail;
import com.base.utils.common.ObjectUtils;
import com.base.utils.exception.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 退货政策
 * Created by lq on 2014/7/29.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TradingCasePaymentDetailImpl implements com.trading.service.ITradingCasePaymentDetail {

    @Autowired
    private TradingCasePaymentDetailMapper tradingCasePaymentDetailMapper;

    @Override
    public void saveCasePaymentDetail(TradingCasePaymentDetail CasePaymentDetail) throws Exception {
        if(CasePaymentDetail.getId()==null){
            ObjectUtils.toInitPojoForInsert(CasePaymentDetail);
            tradingCasePaymentDetailMapper.insert(CasePaymentDetail);
        }else{
            TradingCasePaymentDetail t=tradingCasePaymentDetailMapper.selectByPrimaryKey(CasePaymentDetail.getId());
            Asserts.assertTrue(t != null && t.getCreateUser() != null, "没有找到记录或者记录创建者为空");
            ObjectUtils.valiUpdate(t.getCreateUser(),TradingCasePaymentDetailMapper.class,CasePaymentDetail.getId());
            tradingCasePaymentDetailMapper.updateByPrimaryKeySelective(CasePaymentDetail);
        }
    }
}
