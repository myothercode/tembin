package com.task.service.impl;

import com.base.database.task.mapper.TaskGetOrdersMapper;
import com.base.database.task.model.TaskGetOrders;
import com.base.database.task.model.TaskGetOrdersExample;
import com.base.database.trading.model.TradingOrderGetOrders;
import com.base.database.trading.model.TradingOrderGetOrdersExample;
import com.base.utils.common.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrtor on 2014/10/17.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TaskGetOrdersImpl implements com.task.service.ITaskGetOrders {
    @Autowired
    private TaskGetOrdersMapper taskGetOrdersMapper;

    @Override
    public void saveListTaskGetOrders(TaskGetOrders taskGetOrders){
        if(taskGetOrders.getId()==null){
            this.taskGetOrdersMapper.insertSelective(taskGetOrders);
        }else{
            this.taskGetOrdersMapper.updateByPrimaryKeySelective(taskGetOrders);
        }
    }

    @Override
    public List<TaskGetOrders> selectTaskGetOrdersByflagAndSaveTime(Integer flag, Date saveTime) {
        TaskGetOrdersExample tde = new TaskGetOrdersExample();
        TaskGetOrdersExample.Criteria c = tde.createCriteria();
        c.andTokenflagEqualTo(flag);
        c.andSavetimeLessThan(saveTime);
        return this.taskGetOrdersMapper.selectByExampleWithBLOBs(tde);
    }

    @Override
    public List<TaskGetOrders> selectTaskGetOrdersByFlagIsFalseOrderBysaveTime() {
        Date date=new Date();
        String date3=date.toString();
        int year=Integer.valueOf(date3.substring(24));
        int month=date.getMonth();
        int day=Integer.valueOf(date3.substring(8, 10));
        Date date1=DateUtils.buildDateTime(year,month,day,3,0,0);
        Date date2= org.apache.commons.lang.time.DateUtils.addDays(date1,-1);
        //----------查某个账户是否需要同步3个月的订单---------
        TaskGetOrdersExample example=new TaskGetOrdersExample();
        TaskGetOrdersExample.Criteria cr=example.createCriteria();
        if(date.before(date1)){
            cr.andSavetimeBetween(date2,date1);
        }else if(date.after(date1)||date.equals(date1)){
            Date date4= org.apache.commons.lang.time.DateUtils.addDays(date1,1);
            cr.andSavetimeBetween(date1,date4);
        }
        cr.andNewuserflagEqualTo(0);
        example.setOrderByClause("newUserFlag");
        List<TaskGetOrders> taskGetOrderses=taskGetOrdersMapper.selectByExampleWithBLOBs(example);
        if(taskGetOrderses!=null&&taskGetOrderses.size()>0){
            return taskGetOrderses;
        }else{
            //------------同步最近7天的订单---------------------------------
            TaskGetOrdersExample tde = new TaskGetOrdersExample();
            TaskGetOrdersExample.Criteria c = tde.createCriteria();
            if(date.before(date1)){
                c.andSavetimeBetween(date2,date1);
            }else if(date.after(date1)||date.equals(date1)){
                Date date4= org.apache.commons.lang.time.DateUtils.addDays(date1,1);
                c.andSavetimeBetween(date1,date4);
            }
            tde.setOrderByClause("tokenFlag");
            c.andNewuserflagIsNull();
            return this.taskGetOrdersMapper.selectByExampleWithBLOBs(tde);
        }
    }

    @Override
    public List<TaskGetOrders> selectTaskGetOrdersByNewUserFlagIsFalseOrderBysaveTime() {
        //------------同步最近7天的订单---------------------------------
        Date date=new Date();
        String date3=date.toString();
        int year=Integer.valueOf(date3.substring(24));
        int month=date.getMonth();
        int day=Integer.valueOf(date3.substring(8, 10));
        Date date1=DateUtils.buildDateTime(year,month,day,3,0,0);
        Date date2= org.apache.commons.lang.time.DateUtils.addDays(date1,-1);
        TaskGetOrdersExample tde = new TaskGetOrdersExample();
        TaskGetOrdersExample.Criteria c = tde.createCriteria();
        if(date.before(date1)){
            c.andSavetimeBetween(date2,date1);
        }else if(date.after(date1)||date.equals(date1)){
            Date date4= org.apache.commons.lang.time.DateUtils.addDays(date1,1);
            c.andSavetimeBetween(date1,date4);
        }
        tde.setOrderByClause("tokenFlag");
        c.andNewuserflagIsNull();
        return this.taskGetOrdersMapper.selectByExampleWithBLOBs(tde);
    }

    @Override
    public List<TaskGetOrders> selectTaskGetMessagesByFlagIsFalseOrderByLastSycTimeAndEbayName(String ebayName) {
        TaskGetOrdersExample example=new TaskGetOrdersExample();
        TaskGetOrdersExample.Criteria cr=example.createCriteria();
        cr.andEbaynameEqualTo(ebayName);
        cr.andLastsyctimeIsNotNull();
        example.setOrderByClause("lastsyctime");
        return this.taskGetOrdersMapper.selectByExample(example);
    }
}
