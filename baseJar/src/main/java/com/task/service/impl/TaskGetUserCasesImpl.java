package com.task.service.impl;

import com.base.database.task.mapper.TaskGetUserCasesMapper;
import com.base.database.task.model.TaskGetUserCases;
import com.base.database.task.model.TaskGetUserCasesExample;
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
        c.andTokenflagEqualTo(0);
        return this.taskGetUserCasesMapper.selectByExampleWithBLOBs(tde);
    }
}
