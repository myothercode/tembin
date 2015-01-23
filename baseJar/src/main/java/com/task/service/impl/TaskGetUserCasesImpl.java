package com.task.service.impl;

import com.base.database.task.mapper.TaskGetUserCasesMapper;
import com.base.database.task.model.TaskGetUserCases;
import com.base.database.task.model.TaskGetUserCasesExample;
import com.base.utils.common.DateUtils;
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
public class TaskGetUserCasesImpl implements com.task.service.ITaskGetUserCases {
    @Autowired
    private TaskGetUserCasesMapper taskGetUserCasesMapper;

    @Override
    public void saveListTaskGetUserCases(TaskGetUserCases TaskGetUserCases){
        if(TaskGetUserCases.getId()==null){
            this.taskGetUserCasesMapper.insertSelective(TaskGetUserCases);
        }else{
            this.taskGetUserCasesMapper.updateByPrimaryKeySelective(TaskGetUserCases);
        }
    }

    @Override
    public List<TaskGetUserCases> selectTaskGetUserCasesByflagAndSaveTime(Integer flag, Date saveTime) {
        TaskGetUserCasesExample tde = new TaskGetUserCasesExample();
        TaskGetUserCasesExample.Criteria c = tde.createCriteria();
        c.andTokenflagEqualTo(flag);
        c.andSavetimeLessThan(saveTime);
        return this.taskGetUserCasesMapper.selectByExampleWithBLOBs(tde);
    }

    @Override
    public List<TaskGetUserCases> selectTaskGetUserCasesByFlagIsFalseOrderBysaveTime() {
        TaskGetUserCasesExample tde = new TaskGetUserCasesExample();
        TaskGetUserCasesExample.Criteria c = tde.createCriteria();
        Date date=new Date();
        String date3=date.toString();
        int year=Integer.valueOf(date3.substring(24));
        int month=date.getMonth();
        int day=Integer.valueOf(date3.substring(8, 10));
        Date date1= DateUtils.buildDateTime(year, month, day, 3, 0, 0);
        Date date2= org.apache.commons.lang.time.DateUtils.addDays(date1,-1);
        if(date.before(date1)){
            c.andSavetimeBetween(date2,date1);
        }else if(date.after(date1)||date.equals(date1)){
            Date date4= org.apache.commons.lang.time.DateUtils.addDays(date1,1);
            c.andSavetimeBetween(date1,date4);
        }
        tde.setOrderByClause("tokenFlag");
        return this.taskGetUserCasesMapper.selectByExampleWithBLOBs(tde);
    }

    @Override
    public List<TaskGetUserCases> selectTaskGetMessagesByFlagIsFalseOrderByLastSycTimeAndEbayName(String ebayName) {
        TaskGetUserCasesExample example=new TaskGetUserCasesExample();
        TaskGetUserCasesExample.Criteria cr=example.createCriteria();
        cr.andEbaynameEqualTo(ebayName);
        cr.andLastsyctimeIsNotNull();
        example.setOrderByClause("lastsyctime");
        return this.taskGetUserCasesMapper.selectByExample(example);
    }
}
