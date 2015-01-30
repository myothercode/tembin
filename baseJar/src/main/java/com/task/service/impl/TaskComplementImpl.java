package com.task.service.impl;

import com.base.database.customtrading.mapper.AutoComplementMapper;
import com.base.database.task.mapper.TaskComplementMapper;
import com.base.database.task.model.TaskComplement;
import com.base.database.task.model.TaskComplementExample;
import com.base.mybatis.page.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/12/11.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TaskComplementImpl implements com.task.service.ITaskComplement {

    @Autowired
    private TaskComplementMapper taskComplementMapper;
    @Autowired
    private AutoComplementMapper autoComplementMapper;

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

    @Override
    public List<TaskComplement> selectByTaskComplement(String taskFlag){
        Map m = new HashMap();
        m.put("taskFlag",taskFlag);
        Page page = new Page();
        page.setPageSize(20);
        page.setCurrentPage(1);
        return this.autoComplementMapper.selectTaskComplementList(m,page);
    }
}
