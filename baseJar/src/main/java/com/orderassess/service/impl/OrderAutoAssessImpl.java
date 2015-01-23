package com.orderassess.service.impl;

import com.base.database.publicd.model.PublicUserConfig;
import com.base.database.trading.mapper.OrderAutoAssessMapper;
import com.base.database.trading.model.OrderAutoAssess;
import com.base.database.trading.model.OrderAutoAssessExample;
import com.base.domains.userinfo.UsercontrollerUserExtend;
import com.base.userinfo.service.SystemUserManagerService;
import com.base.utils.cache.DataDictionarySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by Administrtor on 2014/11/19.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OrderAutoAssessImpl implements com.orderassess.service.IOrderAutoAssess {
    @Autowired
    public OrderAutoAssessMapper orderAutoAssessMapper;
    @Autowired
    public SystemUserManagerService systemUserManagerService;

    @Override
    public void saveAssessConent(OrderAutoAssess orderAutoAssess){
        if(orderAutoAssess.getId()!=null){
            this.orderAutoAssessMapper.updateByPrimaryKeySelective(orderAutoAssess);
        }else{
            this.orderAutoAssessMapper.insertSelective(orderAutoAssess);
        }
    }
    @Override
    public void deltelAccessConent(Long id){
        this.orderAutoAssessMapper.deleteByPrimaryKey(id);
    }
    @Override
    public OrderAutoAssess selectById(Long id){
        return this.orderAutoAssessMapper.selectByPrimaryKey(id);
    }
    @Override
    public List<OrderAutoAssess> selectAssessList(Long userid){
        List<UsercontrollerUserExtend> liuue = systemUserManagerService.queryAllUsersByOrgID("yes");
        List<Long> liuserid = new ArrayList<Long>();
        if(liuserid!=null&&liuue.size()>0){
            for(UsercontrollerUserExtend ue:liuue){
                liuserid.add(ue.getUserId().longValue());
            }
        }else{
            liuserid.add(userid);
        }
        OrderAutoAssessExample oaae = new OrderAutoAssessExample();
        oaae.createCriteria().andCreateUserIn(liuserid);
        return this.orderAutoAssessMapper.selectByExample(oaae);
    }

    @Override
    public OrderAutoAssess selectRandomContent(List<UsercontrollerUserExtend> liuue){
        List<Long> listr = new ArrayList<Long>();
        for(UsercontrollerUserExtend uue:liuue){
            listr.add(uue.getUserId().longValue());
        }
        OrderAutoAssessExample oaae = new OrderAutoAssessExample();
        oaae.createCriteria().andCreateUserIn(listr);
        List<OrderAutoAssess> lioaa = this.orderAutoAssessMapper.selectByExample(oaae);
        Random r1 = new Random();
        int randomnumber=r1.nextInt(lioaa.size());
        return lioaa.get(randomnumber);
    }
}
