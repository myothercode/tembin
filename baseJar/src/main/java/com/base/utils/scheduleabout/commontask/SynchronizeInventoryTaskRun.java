package com.base.utils.scheduleabout.commontask;

import com.base.database.inventory.mapper.UserInventorySetMapper;
import com.base.database.inventory.model.TaskSyncInventory;
import com.base.database.inventory.model.UserInventorySet;
import com.base.database.inventory.model.UserInventorySetExample;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.cache.DataDictionarySupport;
import com.base.utils.cache.TempStoreDataSupport;
import com.base.utils.scheduleabout.BaseScheduledClass;
import com.base.utils.scheduleabout.MainTask;
import com.base.utils.scheduleabout.Scheduledable;
import com.inventory.service.IItemInventory;
import com.inventory.service.ITaskSyncInventory;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrtor on 2015/1/21.
 */
public class SynchronizeInventoryTaskRun extends BaseScheduledClass implements Scheduledable {

    static Logger logger = Logger.getLogger(AutoAssessTaskRun.class);
    @Override
    public void run() {
        String isRunging = TempStoreDataSupport.pullData("task_" + getScheduledType());
        if(StringUtils.isNotEmpty(isRunging)){return;}
        TempStoreDataSupport.pushData("task_" + getScheduledType(), "x");

        ITaskSyncInventory iTaskSyncInventory = (ITaskSyncInventory) ApplicationContextUtil.getBean(ITaskSyncInventory.class);
        IItemInventory iItemInventory = (IItemInventory) ApplicationContextUtil.getBean(IItemInventory.class);
        List<TaskSyncInventory> lits = iTaskSyncInventory.selectByPageList();

        try {
            for(TaskSyncInventory ts:lits){
                if("四海邮".equals(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(ts.getDataType())).getName())){
                    iItemInventory.getSiHaiYouInventory(ts.getUserName(),ts.getUserKey(),ts.getOrgId()+"");
                }else if("第四方".equals(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(ts.getDataType())).getName())){
                    iItemInventory.getDeShiFangInventory(ts.getUserName(),ts.getUserKey(),ts.getOrgId()+"");
                }else if("出口易".equals(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(ts.getDataType())).getName())){
                    iItemInventory.getChuKouYiInventory(ts.getUserName(),ts.getUserKey(),ts.getOrgId()+"");
                }
            }
        }catch(Exception e){
            TempStoreDataSupport.removeData("task_" + getScheduledType());
            logger.error("检查需要同步库存数据报错：",e);
        }
        TempStoreDataSupport.removeData("task_" + getScheduledType());
    }

    @Override
    public String getScheduledType() {
        return MainTask.SYNC_INVENTORY;
    }

    @Override
    public Integer crTimeMinu() {
        return 10;
    }

    @Override
    public void setMark(String x) {

    }

    @Override
    public String getMark() {
        return null;
    }
}
