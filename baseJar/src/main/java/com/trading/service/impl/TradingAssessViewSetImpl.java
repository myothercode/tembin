package com.trading.service.impl;

import com.base.database.trading.mapper.TradingAssessViewSetMapper;
import com.base.database.trading.mapper.TradingListingReportMapper;
import com.base.database.trading.model.TradingAssessViewSet;
import com.base.database.trading.model.TradingAssessViewSetExample;
import com.base.database.trading.model.TradingListingReport;
import com.base.database.trading.model.TradingListingReportExample;
import com.base.domains.userinfo.UsercontrollerUserExtend;
import com.base.userinfo.service.SystemUserManagerService;
import com.base.utils.common.DateUtils;
import org.apache.http.impl.cookie.DateParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrtor on 2014/11/8.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TradingAssessViewSetImpl implements com.trading.service.ITradingAssessViewSet {
    @Autowired
    private TradingAssessViewSetMapper tradingAssessViewSetMapper;
    @Autowired
    public SystemUserManagerService systemUserManagerService;
    @Override
    public void save(TradingAssessViewSet tradingAssessViewSet){
        if(tradingAssessViewSet.getId()!=null){
            this.tradingAssessViewSetMapper.updateByPrimaryKeySelective(tradingAssessViewSet);
        }else{
            this.tradingAssessViewSetMapper.insertSelective(tradingAssessViewSet);
        }
    }

    @Override
    public TradingAssessViewSet selectByUserid(long userid) throws DateParseException {
        List<UsercontrollerUserExtend> liuue = systemUserManagerService.queryAllUsersByOrgID("yes");
        List<Long> liuserid = new ArrayList<Long>();
        if(liuserid!=null&&liuue.size()>0){
            for(UsercontrollerUserExtend ue:liuue){
                liuserid.add(ue.getUserId().longValue());
            }
        }else{
            liuserid.add(userid);
        }
        TradingAssessViewSetExample tlse = new TradingAssessViewSetExample();
        tlse.createCriteria().andCreateUserIn(liuserid);
        List<TradingAssessViewSet> lits = this.tradingAssessViewSetMapper.selectByExample(tlse);
        if(lits!=null&&lits.size()>0){
            return lits.get(0);
        }else{
            return null;
        }
    }

}

