package com.task.service.impl;

import com.base.database.task.mapper.TaskComplementMapper;
import com.base.database.task.model.TaskComplement;
import com.base.database.task.model.TaskComplementExample;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrtor on 2014/12/11.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TaskComplementImpl implements com.task.service.ITaskComplement {

    @Autowired
    private TaskComplementMapper taskComplementMapper;

    @Override
    public void saveTaskComplement(TaskComplement taskComplement){
        if(taskComplement.getId()==null||"".equals(taskComplement.getId())){
            this.taskComplementMapper.insertSelective(taskComplement);
        }else{
            this.taskComplementMapper.updateByPrimaryKeyWithBLOBs(taskComplement);
        }
    }

    @Override
    public List<TaskComplement> selectByList(){
        TaskComplementExample tce = new TaskComplementExample();
        tce.createCriteria().andTaskFlagEqualTo("0");
        return this.taskComplementMapper.selectByExampleWithBLOBs(tce);
    }
}
