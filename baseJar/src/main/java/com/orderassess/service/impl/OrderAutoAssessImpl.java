package com.orderassess.service.impl;

import com.base.database.publicd.model.PublicUserConfig;
import com.base.database.trading.mapper.OrderAutoAssessMapper;
import com.base.database.trading.model.OrderAutoAssess;
import com.base.database.trading.model.OrderAutoAssessExample;
import com.base.utils.cache.DataDictionarySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

/**
 * Created by Administrtor on 2014/11/19.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OrderAutoAssessImpl implements com.orderassess.service.IOrderAutoAssess {
    @Autowired
    public OrderAutoAssessMapper orderAutoAssessMapper;

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
        OrderAutoAssessExample oaae = new OrderAutoAssessExample();
        oaae.createCriteria().andCreateUserEqualTo(userid);
        return this.orderAutoAssessMapper.selectByExample(oaae);
    }

    @Override
    public OrderAutoAssess selectRandomContent(Long userid){
        OrderAutoAssessExample oaae = new OrderAutoAssessExample();
        oaae.createCriteria().andCreateUserEqualTo(userid);
        List<OrderAutoAssess> lioaa = this.orderAutoAssessMapper.selectByExample(oaae);
        Random r1 = new Random();
        int randomnumber=r1.nextInt(lioaa.size());
        return lioaa.get(randomnumber);
    }
}
