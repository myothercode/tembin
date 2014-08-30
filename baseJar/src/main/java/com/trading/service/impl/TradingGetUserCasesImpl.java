package com.trading.service.impl;

import com.base.database.trading.mapper.TradingGetUserCasesMapper;
import com.base.database.trading.model.TradingGetUserCases;
import com.base.database.trading.model.TradingGetUserCasesExample;
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
public class TradingGetUserCasesImpl implements com.trading.service.ITradingGetUserCases {

    @Autowired
    private TradingGetUserCasesMapper tradingGetUserCasesMapper;

    @Override
    public void saveGetUserCases(TradingGetUserCases GetUserCases) throws Exception {
        if(GetUserCases.getId()==null){
            ObjectUtils.toInitPojoForInsert(GetUserCases);
            tradingGetUserCasesMapper.insert(GetUserCases);
        }else{
            TradingGetUserCases t=tradingGetUserCasesMapper.selectByPrimaryKey(GetUserCases.getId());
            Asserts.assertTrue(t != null && t.getCreateUser() != null, "没有找到记录或者记录创建者为空");
            ObjectUtils.valiUpdate(t.getCreateUser(),TradingGetUserCasesMapper.class,GetUserCases.getId());
            tradingGetUserCasesMapper.updateByPrimaryKeySelective(GetUserCases);
        }
    }

    @Override
    public List<TradingGetUserCases> selectOrderGetItemByTransactionId(String transactionid) {
        TradingGetUserCasesExample example=new TradingGetUserCasesExample();
        TradingGetUserCasesExample.Criteria cr=example.createCriteria();
        cr.andTransactionidEqualTo(transactionid);
        List<TradingGetUserCases> list=tradingGetUserCasesMapper.selectByExample(example);
        return list;
    }


}
