package com.task.service.impl;

import com.base.database.task.mapper.TaskGetOrdersSellerTransactionMapper;
import com.base.database.task.model.TaskGetOrdersSellerTransaction;
import com.base.database.task.model.TaskGetOrdersSellerTransactionExample;
import com.base.utils.common.DateUtils;
import com.task.service.ITaskGetOrdersSellerTransaction;
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
public class TaskGetOrdersSellerTransactionImpl implements ITaskGetOrdersSellerTransaction {
    @Autowired
    private TaskGetOrdersSellerTransactionMapper taskGetOrdersSellerTransactionMapper;
    @Override
    public void saveTaskGetOrdersSellerTransaction(TaskGetOrdersSellerTransaction taskGetOrdersSellerTransaction) {
        if(taskGetOrdersSellerTransaction.getId()==null){
            this.taskGetOrdersSellerTransactionMapper.insertSelective(taskGetOrdersSellerTransaction);
        }else{
            this.taskGetOrdersSellerTransactionMapper.updateByPrimaryKeySelective(taskGetOrdersSellerTransaction);
        }
    }

    @Override
    public List<TaskGetOrdersSellerTransaction> selectTaskGetOrdersSellerTransactionByflagAndSaveTime(Integer flag, Date saveTime) {
        TaskGetOrdersSellerTransactionExample tde = new TaskGetOrdersSellerTransactionExample();
        TaskGetOrdersSellerTransactionExample.Criteria c = tde.createCriteria();
        c.andTokenflagEqualTo(flag);
        c.andSavetimeLessThan(saveTime);
        return this.taskGetOrdersSellerTransactionMapper.selectByExampleWithBLOBs(tde);
    }

    @Override
    public List<TaskGetOrdersSellerTransaction> selectTaskGetOrdersSellerTransactionByFlagIsFalseOrderBysaveTime() {
        TaskGetOrdersSellerTransactionExample tde = new TaskGetOrdersSellerTransactionExample();
        TaskGetOrdersSellerTransactionExample.Criteria c = tde.createCriteria();
        Date date=new Date();
        String date3=date.toString();
        int year=Integer.valueOf(date3.substring(24));
        int month=date.getMonth();
        int day=Integer.valueOf(date3.substring(8, 10));
        Date date1=DateUtils.buildDateTime(year,month,day,3,0,0);
        Date date2= org.apache.commons.lang.time.DateUtils.addDays(date1,-1);
        if(date.before(date1)){
            c.andSavetimeBetween(date2,date1);
        }else if(date.after(date1)||date.equals(date1)){
            Date date4= org.apache.commons.lang.time.DateUtils.addDays(date1,1);
            c.andSavetimeBetween(date1,date4);
        }
        tde.setOrderByClause("tokenFlag");
        return this.taskGetOrdersSellerTransactionMapper.selectByExampleWithBLOBs(tde);
    }
}
