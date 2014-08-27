package com.trading.service.impl;

import com.base.database.trading.mapper.TradingOrderAddMemberMessageAAQToPartnerMapper;
import com.base.database.trading.model.TradingOrderAddMemberMessageAAQToPartner;
import com.base.database.trading.model.TradingOrderAddMemberMessageAAQToPartnerExample;
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
public class TradingOrderAddMemberMessageAAQToPartnerImpl implements com.trading.service.ITradingOrderAddMemberMessageAAQToPartner {

    @Autowired
    private TradingOrderAddMemberMessageAAQToPartnerMapper tradingOrderAddMemberMessageAAQToPartnerMapper;

    @Override
    public void saveOrderAddMemberMessageAAQToPartner(TradingOrderAddMemberMessageAAQToPartner OrderAddMemberMessageAAQToPartner) throws Exception {
        if(OrderAddMemberMessageAAQToPartner.getId()==null){
            ObjectUtils.toInitPojoForInsert(OrderAddMemberMessageAAQToPartner);
            tradingOrderAddMemberMessageAAQToPartnerMapper.insert(OrderAddMemberMessageAAQToPartner);
        }else{
            TradingOrderAddMemberMessageAAQToPartner t=tradingOrderAddMemberMessageAAQToPartnerMapper.selectByPrimaryKey(OrderAddMemberMessageAAQToPartner.getId());
            Asserts.assertTrue(t != null && t.getCreateUser() != null, "没有找到记录或者记录创建者为空");
            ObjectUtils.valiUpdate(t.getCreateUser(),TradingOrderAddMemberMessageAAQToPartnerMapper.class,OrderAddMemberMessageAAQToPartner.getId());
            tradingOrderAddMemberMessageAAQToPartnerMapper.updateByPrimaryKeySelective(OrderAddMemberMessageAAQToPartner);
        }
    }

    @Override
    public List<TradingOrderAddMemberMessageAAQToPartner> selectTradingOrderAddMemberMessageAAQToPartnerByTransactionId(String TransactionId,Integer type) {
        TradingOrderAddMemberMessageAAQToPartnerExample example=new TradingOrderAddMemberMessageAAQToPartnerExample();
        TradingOrderAddMemberMessageAAQToPartnerExample.Criteria cr=example.createCriteria();
        cr.andTransactionidEqualTo(TransactionId);
        cr.andMessagetypeEqualTo(type);
        List<TradingOrderAddMemberMessageAAQToPartner> list=tradingOrderAddMemberMessageAAQToPartnerMapper.selectByExample(example);
        return list;
    }
}
