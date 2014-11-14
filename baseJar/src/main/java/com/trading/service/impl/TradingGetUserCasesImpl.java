package com.trading.service.impl;

import com.base.database.customtrading.mapper.UserCasesMapper;
import com.base.database.trading.mapper.TradingGetUserCasesMapper;
import com.base.database.trading.model.TradingGetUserCases;
import com.base.database.trading.model.TradingGetUserCasesExample;
import com.base.domains.querypojos.UserCasesQuery;
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
public class TradingGetUserCasesImpl implements com.trading.service.ITradingGetUserCases {

    @Autowired
    private TradingGetUserCasesMapper tradingGetUserCasesMapper;
    @Autowired
    private UserCasesMapper userCasesMapper;

    @Override
    public void saveGetUserCases(TradingGetUserCases GetUserCases) throws Exception {
        if(GetUserCases.getId()==null){
            ObjectUtils.toInitPojoForInsert(GetUserCases);
            tradingGetUserCasesMapper.insert(GetUserCases);
        }else{
            TradingGetUserCases t=tradingGetUserCasesMapper.selectByPrimaryKey(GetUserCases.getId());
            Asserts.assertTrue(t != null && t.getCreateUser() != null, "没有找到记录或者记录创建者为空");
            ObjectUtils.valiUpdate(t.getCreateUser(),TradingGetUserCasesMapper.class,GetUserCases.getId(),"Synchronize");
            tradingGetUserCasesMapper.updateByPrimaryKeySelective(GetUserCases);
        }
    }

    @Override
    public List<TradingGetUserCases> selectGetUserCasesByTransactionId(String transactionid,String seller) {
        TradingGetUserCasesExample example=new TradingGetUserCasesExample();
        TradingGetUserCasesExample.Criteria cr=example.createCriteria();
        cr.andTransactionidEqualTo(transactionid);
        cr.andSelleridEqualTo(seller);
        List<TradingGetUserCases> list=tradingGetUserCasesMapper.selectByExample(example);
        return list;
    }

    @Override
    public List<UserCasesQuery> selectGetUserCases(Map map, Page page) {
        return userCasesMapper.selectUserCases(map,page);
    }

    @Override
    public TradingGetUserCases selectUserCasesById(Long id) {
        TradingGetUserCasesExample example=new TradingGetUserCasesExample();
        TradingGetUserCasesExample.Criteria cr=example.createCriteria();
        cr.andIdEqualTo(id);
        List<TradingGetUserCases> list=tradingGetUserCasesMapper.selectByExample(example);
        return list.size()>0?list.get(0):null;
    }

    @Override
    public List<TradingGetUserCases> selectGetUserCasesByHandled(Long userId) {
        TradingGetUserCasesExample example=new TradingGetUserCasesExample();
        TradingGetUserCasesExample.Criteria cr=example.createCriteria();
        cr.andCasetypeLike("EBP_");
        cr.andHandledEqualTo(0);
        cr.andCreateUserEqualTo(userId);
        List<TradingGetUserCases> list=tradingGetUserCasesMapper.selectByExample(example);
        return list;
    }
}
