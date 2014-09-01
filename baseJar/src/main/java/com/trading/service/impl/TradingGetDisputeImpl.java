package com.trading.service.impl;

import com.base.database.trading.mapper.TradingGetDisputeMapper;
import com.base.database.trading.model.TradingGetDispute;
import com.base.database.trading.model.TradingGetDisputeExample;
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
public class TradingGetDisputeImpl implements com.trading.service.ITradingGetDispute {

    @Autowired
    private TradingGetDisputeMapper tradingGetDisputeMapper;

    @Override
    public void saveGetDispute(TradingGetDispute GetDispute) throws Exception {
        if(GetDispute.getId()==null){
            ObjectUtils.toInitPojoForInsert(GetDispute);
            tradingGetDisputeMapper.insert(GetDispute);
        }else{
            TradingGetDispute t=tradingGetDisputeMapper.selectByPrimaryKey(GetDispute.getId());
            Asserts.assertTrue(t != null && t.getCreateUser() != null, "没有找到记录或者记录创建者为空");
            ObjectUtils.valiUpdate(t.getCreateUser(),TradingGetDisputeMapper.class,GetDispute.getId());
            tradingGetDisputeMapper.updateByPrimaryKeySelective(GetDispute);
        }
    }

    @Override
    public List<TradingGetDispute> selectGetDisputeByTransactionId(String TransactionId) {
        TradingGetDisputeExample example=new TradingGetDisputeExample();
        TradingGetDisputeExample.Criteria cr=example.createCriteria();
        cr.andTransactionidEqualTo(TransactionId);
        List<TradingGetDispute> list=tradingGetDisputeMapper.selectByExample(example);
        return list;
    }


}
