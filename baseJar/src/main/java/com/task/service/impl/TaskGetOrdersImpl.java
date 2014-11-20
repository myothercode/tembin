package com.task.service.impl;

import com.base.database.task.mapper.TaskGetOrdersMapper;
import com.base.database.task.model.TaskGetOrders;
import com.base.database.task.model.TaskGetOrdersExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
       /* Date date=new Date();*/
        c.andSavetimeEqualTo(new Date());
        return this.taskGetOrdersMapper.selectByExampleWithBLOBs(tde);
    }
}
