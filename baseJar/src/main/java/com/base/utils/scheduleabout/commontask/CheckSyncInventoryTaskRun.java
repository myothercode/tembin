package com.base.utils.scheduleabout.commontask;

import com.base.database.inventory.mapper.UserInventorySetMapper;
import com.base.database.inventory.model.TaskSyncInventory;
import com.base.database.inventory.model.UserInventorySet;
import com.base.database.inventory.model.UserInventorySetExample;
import com.base.database.publicd.model.PublicUserConfig;
import com.base.database.trading.mapper.AutoAssessDetailMapper;
import com.base.database.trading.mapper.TradingFeedBackDetailMapper;
import com.base.database.trading.mapper.TradingOrderGetOrdersMapper;
import com.base.database.trading.mapper.UsercontrollerEbayAccountMapper;
import com.base.database.trading.model.*;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.domains.userinfo.UsercontrollerUserExtend;
import com.base.sampleapixml.APINameStatic;
import com.base.sampleapixml.BindAccountAPI;
import com.base.userinfo.mapper.SystemUserManagerServiceMapper;
import com.base.userinfo.service.SystemUserManagerService;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.cache.TempStoreDataSupport;
import com.base.utils.common.CommAutowiredClass;
import com.base.utils.common.DateUtils;
import com.base.utils.scheduleabout.BaseScheduledClass;
import com.base.utils.scheduleabout.MainTask;
import com.base.utils.scheduleabout.Scheduledable;
import com.base.utils.threadpool.AddApiTask;
import com.inventory.service.ITaskSyncInventory;
import com.orderassess.service.IAutoAssessDetail;
import com.orderassess.service.IOrderAutoAssess;
import com.publicd.service.IPublicUserConfig;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Created by Administrtor on 2015/1/21.
 */
public class CheckSyncInventoryTaskRun extends BaseScheduledClass implements Scheduledable {

    static Logger logger = Logger.getLogger(AutoAssessTaskRun.class);
    @Override
    public void run() {
        String isRunging = TempStoreDataSupport.pullData("task_" + getScheduledType());
        if(StringUtils.isNotEmpty(isRunging)){return;}
        TempStoreDataSupport.pushData("task_" + getScheduledType(), "x");

        UserInventorySetMapper uism = (UserInventorySetMapper) ApplicationContextUtil.getBean(UserInventorySetMapper.class);//获取注入的参数
        ITaskSyncInventory iTaskSyncInventory = (ITaskSyncInventory) ApplicationContextUtil.getBean(ITaskSyncInventory.class);

        UserInventorySetExample uise = new UserInventorySetExample();
        uise.createCriteria();
        List<UserInventorySet> liins = uism.selectByExample(uise);

        try {
            for(UserInventorySet ins:liins){
                //判断该仓库，同一用户是否还有未执行的任务，如果没有，新加任务
                TaskSyncInventory tsi  = iTaskSyncInventory.selectByUserName(ins.getDataType(),ins.getUserName());
                if(tsi==null){
                    TaskSyncInventory ts = new TaskSyncInventory();
                    ts.setDataType(ins.getDataType());
                    ts.setOrgId(ins.getOrgId());
                    ts.setCreateDate(new Date());
                    ts.setTaskFlag("0");
                    ts.setUserKey(ins.getUserKey());
                    ts.setUserName(ins.getUserName());
                    iTaskSyncInventory.saveTaskSyncInventory(ts);
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
        return MainTask.CHECK_SYNC_INVENTORY;
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
