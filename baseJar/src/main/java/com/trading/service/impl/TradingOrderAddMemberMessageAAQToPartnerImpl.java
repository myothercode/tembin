package com.trading.service.impl;

import com.base.database.customtrading.mapper.AddMemberMessageAAQToPartnerMapper;
import com.base.database.trading.mapper.TradingOrderAddMemberMessageAAQToPartnerMapper;
import com.base.database.trading.model.TradingOrderAddMemberMessageAAQToPartner;
import com.base.database.trading.model.TradingOrderAddMemberMessageAAQToPartnerExample;
import com.base.domains.querypojos.TradingOrderAddMemberMessageAAQToPartnerQuery;
import com.base.mybatis.page.Page;
import com.base.utils.common.ObjectUtils;
import com.base.utils.exception.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 退货政策
 * Created by lq on 2014/7/29.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TradingOrderAddMemberMessageAAQToPartnerImpl implements com.trading.service.ITradingOrderAddMemberMessageAAQToPartner {

    @Autowired
    private TradingOrderAddMemberMessageAAQToPartnerMapper tradingOrderAddMemberMessageAAQToPartnerMapper;
    @Autowired
    private AddMemberMessageAAQToPartnerMapper addMemberMessageAAQToPartnerMapper;
    @Override
    public void saveOrderAddMemberMessageAAQToPartner(TradingOrderAddMemberMessageAAQToPartner OrderAddMemberMessageAAQToPartner) throws Exception {
        if(OrderAddMemberMessageAAQToPartner.getId()==null){
            ObjectUtils.toInitPojoForInsert(OrderAddMemberMessageAAQToPartner);
            tradingOrderAddMemberMessageAAQToPartnerMapper.insert(OrderAddMemberMessageAAQToPartner);
        }else{
            TradingOrderAddMemberMessageAAQToPartner t=tradingOrderAddMemberMessageAAQToPartnerMapper.selectByPrimaryKey(OrderAddMemberMessageAAQToPartner.getId());
            Asserts.assertTrue(t != null && t.getCreateUser() != null, "没有找到记录或者记录创建者为空");
            ObjectUtils.valiUpdate(t.getCreateUser(),TradingOrderAddMemberMessageAAQToPartnerMapper.class,OrderAddMemberMessageAAQToPartner.getId(),"Synchronize");
            tradingOrderAddMemberMessageAAQToPartnerMapper.updateByPrimaryKeySelective(OrderAddMemberMessageAAQToPartner);
        }
    }

    @Override
    public List<TradingOrderAddMemberMessageAAQToPartner> selectTradingOrderAddMemberMessageAAQToPartnerByTransactionId(String TransactionId,Integer type,String sender,Integer...messageflag) {
        TradingOrderAddMemberMessageAAQToPartnerExample example=new TradingOrderAddMemberMessageAAQToPartnerExample();
        TradingOrderAddMemberMessageAAQToPartnerExample.Criteria cr=example.createCriteria();
        cr.andTransactionidEqualTo(TransactionId);
        cr.andMessagetypeEqualTo(type);
        if(sender!=null){
            cr.andSenderEqualTo(sender);
        }
        if(messageflag!=null&&messageflag.length>0){
            cr.andMessageflagEqualTo(messageflag[0]);
        }
        List<TradingOrderAddMemberMessageAAQToPartner> list=tradingOrderAddMemberMessageAAQToPartnerMapper.selectByExample(example);
        return list;
    }

    @Override
    public List<TradingOrderAddMemberMessageAAQToPartnerQuery> selectTradingOrderAddMemberMessageAAQToPartner(Map map, Page page) {
        return addMemberMessageAAQToPartnerMapper.selectByAddMemberMessageAAQToPartnerList(map,page);
    }

    @Override
    public void deleteTradingOrderAddMemberMessageAAQToPartner(Long id) {
        if(id!=null){
            tradingOrderAddMemberMessageAAQToPartnerMapper.deleteByPrimaryKey(id);
        }
    }

    @Override
    public List<TradingOrderAddMemberMessageAAQToPartner> selectTradingOrderAddMemberMessageAAQToPartnerByItemIdAndSender(String itemid, Integer type, String sender,String recipient) {
        TradingOrderAddMemberMessageAAQToPartnerExample example=new TradingOrderAddMemberMessageAAQToPartnerExample();
        TradingOrderAddMemberMessageAAQToPartnerExample.Criteria cr=example.createCriteria();
        cr.andItemidEqualTo(itemid);
        cr.andMessagetypeEqualTo(type);
        cr.andSenderEqualTo(sender);
        cr.andRecipientidEqualTo(recipient);
        List<TradingOrderAddMemberMessageAAQToPartner> list=tradingOrderAddMemberMessageAAQToPartnerMapper.selectByExample(example);
        return list;
    }
}
