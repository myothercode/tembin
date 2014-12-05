package com.task.service.impl;

import com.base.database.task.mapper.TaskGetOrdersMapper;
import com.base.database.task.model.TaskGetOrders;
import com.base.database.task.model.TaskGetOrdersExample;
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
        TaskGetOrdersExample tde = new TaskGetOrdersExample();
        TaskGetOrdersExample.Criteria c = tde.createCriteria();
        Date date=new Date();
        String date3=date.toString();
        int year=Integer.valueOf(date3.substring(24));
        int month=date.getMonth();
        int day=Integer.valueOf(date3.substring(8, 10));
        Date date1=DateUtils.buildDateTime(year,month,day,16,0,0);
        Date date2= org.apache.commons.lang.time.DateUtils.addDays(date1,-1);
        if(date.before(date1)){
            c.andSavetimeBetween(date2,date1);
        }else if(date.after(date1)||date.equals(date1)){
            Date date4= org.apache.commons.lang.time.DateUtils.addDays(date1,1);
            c.andSavetimeBetween(date1,date4);
        }
        return this.taskGetOrdersMapper.selectByExampleWithBLOBs(tde);
    }
}
