package com.task.service.impl;

import com.base.database.task.mapper.TaskGetMessagesMapper;
import com.base.database.task.model.TaskGetMessages;
import com.base.database.task.model.TaskGetMessages;
import com.base.database.task.model.TaskGetMessagesExample;
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
public class TaskGetMessagesImpl implements com.task.service.ITaskGetMessages {
    @Autowired
    private TaskGetMessagesMapper taskGetMessagesMapper;

    @Override
    public void saveListTaskGetMessages(TaskGetMessages taskGetMessages){
        if(taskGetMessages.getId()==null){
            this.taskGetMessagesMapper.insertSelective(taskGetMessages);
        }else{
            this.taskGetMessagesMapper.updateByPrimaryKeySelective(taskGetMessages);
        }
    }

    @Override
    public List<TaskGetMessages> selectTaskGetMessagesByflagAndSaveTime(Integer flag, Date saveTime) {
        TaskGetMessagesExample tde = new TaskGetMessagesExample();
        TaskGetMessagesExample.Criteria c = tde.createCriteria();
        c.andTokenflagEqualTo(flag);
        c.andSavetimeLessThan(saveTime);
        return this.taskGetMessagesMapper.selectByExampleWithBLOBs(tde);
    }

    @Override
    public List<TaskGetMessages> selectTaskGetMessagesByFlagIsFalseOrderBysaveTime() {
        TaskGetMessagesExample tde = new TaskGetMessagesExample();
        TaskGetMessagesExample.Criteria c = tde.createCriteria();
        c.andTokenflagEqualTo(0);
        return this.taskGetMessagesMapper.selectByExampleWithBLOBs(tde);
    }
}
