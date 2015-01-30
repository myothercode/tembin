package com.inventory.service.impl;

import com.base.database.customtrading.mapper.TaskInventoryMapper;
import com.base.database.inventory.mapper.TaskSyncInventoryMapper;
import com.base.database.inventory.model.TaskSyncInventory;
import com.base.database.inventory.model.TaskSyncInventoryExample;
import com.base.mybatis.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrtor on 2015/1/29.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TaskSyncInventoryImpl implements com.inventory.service.ITaskSyncInventory {
    @Autowired
    private TaskSyncInventoryMapper taskSyncInventoryMapper;
    @Autowired
    private TaskInventoryMapper taskInventoryMapper;
    @Override
    public void saveTaskSyncInventory(TaskSyncInventory taskSyncInventory){
        if(taskSyncInventory.getId()==null){
            this.taskSyncInventoryMapper.insertSelective(taskSyncInventory);
        }else{
            this.taskSyncInventoryMapper.updateByPrimaryKeySelective(taskSyncInventory);
        }
    }

    @Override
    public TaskSyncInventory selectByUserName(String dataType, String userName){
        TaskSyncInventoryExample tse = new TaskSyncInventoryExample();
        tse.createCriteria().andUserNameEqualTo(userName).andDataTypeEqualTo(dataType).andTaskFlagEqualTo("0");
        List<TaskSyncInventory> ltsi = this.taskSyncInventoryMapper.selectByExample(tse);
        if(ltsi!=null&&ltsi.size()>0){
            return ltsi.get(0);
        }else{
            return null;
        }
    }
    @Override
    public List<TaskSyncInventory> selectByPageList(){
        Page page = new Page();
        page.setPageSize(20);
        page.setCurrentPage(1);
        return this.taskInventoryMapper.selectBydoExample(new HashMap(),page);
    }
}
