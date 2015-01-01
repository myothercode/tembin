package com.base.utils.scheduleabout.commontask;

import com.base.database.task.model.TaskGetUserCases;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.cache.TempStoreDataSupport;
import com.base.utils.scheduleabout.BaseScheduledClass;
import com.base.utils.scheduleabout.MainTask;
import com.base.utils.scheduleabout.Scheduledable;
import com.base.utils.threadpool.TaskPool;
import com.task.service.IScheduleGetUserCasesTimer;
import com.task.service.ITaskGetUserCases;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by Administrtor on 2014/8/29.
 * 定时同步纠纷，定时任务
 */
public class SynchronizeGetUserCasesTimerTaskRun extends BaseScheduledClass implements Scheduledable {
    static Logger logger = Logger.getLogger(SynchronizeGetUserCasesTimerTaskRun.class);

    public void synchronizeOrders(List<TaskGetUserCases> TaskGetUserCases){
        IScheduleGetUserCasesTimer iScheduleGetUserCasesTimer = (IScheduleGetUserCasesTimer) ApplicationContextUtil.getBean(IScheduleGetUserCasesTimer.class);
        try {
            iScheduleGetUserCasesTimer.synchronizeUserCases(TaskGetUserCases);
        } catch (Exception e) {
            logger.error("定时同步纠纷出错:",e);
            TempStoreDataSupport.removeData("task_"+getScheduledType());
        }
        /*UserInfoService userInfoService=(UserInfoService) ApplicationContextUtil.getBean(UserInfoService.class);*/


    }
    @Override
    public void run(){
        int i= TaskPool.scheduledThreadPoolTaskExecutor.getActiveCount();
        if(i>30){
            return;
        }
        String isRunging = TempStoreDataSupport.pullData("task_"+getScheduledType());
        if(StringUtils.isNotEmpty(isRunging)){return;}
        TempStoreDataSupport.pushData("task_" + getScheduledType(), "x");

        ITaskGetUserCases iTaskGetUserCases = (ITaskGetUserCases) ApplicationContextUtil.getBean(ITaskGetUserCases.class);
        List<TaskGetUserCases> list=iTaskGetUserCases.selectTaskGetUserCasesByFlagIsFalseOrderBysaveTime();
        synchronizeOrders(list);
        TempStoreDataSupport.removeData("task_"+getScheduledType());
    }

    /**只从集合记录取多少条*/
    private List<TaskGetUserCases> filterLimitList(List<TaskGetUserCases> tlist){

        return filterLimitListFinal(tlist,2);

        /*List<TaskGetUserCases> x=new ArrayList<TaskGetUserCases>();
        for (int i = 0;i<2;i++){
            x.add(tlist.get(i));
        }
        return x;*/
    }

    public SynchronizeGetUserCasesTimerTaskRun(){

    }

    @Override
    public String getScheduledType() {
        return MainTask.SYNCHRONIZE_GET_USER_CASES_TIMER;
    }

    @Override
    public Integer crTimeMinu() {
        /*ITaskGetUserCases iTaskGetUserCases = (ITaskGetUserCases) ApplicationContextUtil.getBean(ITaskGetUserCases.class);
        List<TaskGetUserCases> list=iTaskGetUserCases.selectTaskGetUserCasesByFlagIsFalseOrderBysaveTime();
        if(list.size()>0&&list.size()<=50){
            return 60;
        }
        return 2;*/
        return 30;
    }
}
