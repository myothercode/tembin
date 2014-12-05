package com.base.utils.scheduleabout.commontask;

import com.base.database.task.model.TaskGetOrders;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.cache.TempStoreDataSupport;
import com.base.utils.scheduleabout.BaseScheduledClass;
import com.base.utils.scheduleabout.MainTask;
import com.base.utils.scheduleabout.Scheduledable;
import com.base.utils.threadpool.TaskPool;
import com.task.service.IScheduleGetTimerOrders;
import com.task.service.ITaskGetOrders;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by Administrtor on 2014/8/29.
 * 在线商品每晚执行，定时任务 //两分钟
 */
public class SynchronizeGetTimerOrdersTaskRun extends BaseScheduledClass implements Scheduledable {
    static Logger logger = Logger.getLogger(SynchronizeGetTimerOrdersTaskRun.class);

    public void synchronizeOrders(List<TaskGetOrders> taskGetOrders){
        IScheduleGetTimerOrders iScheduleGetTimerOrders=(IScheduleGetTimerOrders) ApplicationContextUtil.getBean(IScheduleGetTimerOrders.class);
        iScheduleGetTimerOrders.synchronizeOrders(taskGetOrders);
    }
    @Override
    public void run(){
        int i= TaskPool.scheduledThreadPoolTaskExecutor.getActiveCount();
        if(i>30){
            return;
        }
        List<TaskGetOrders> list= null;
        String isRunging = TempStoreDataSupport.pullData("task_"+getScheduledType());
        if(StringUtils.isNotEmpty(isRunging)){return;}
        TempStoreDataSupport.pushData("task_" + getScheduledType(), "x");
        ITaskGetOrders iTaskGetOrders = (ITaskGetOrders) ApplicationContextUtil.getBean(ITaskGetOrders.class);
        list = iTaskGetOrders.selectTaskGetOrdersByFlagIsFalseOrderBysaveTime();
        synchronizeOrders(list);
        TempStoreDataSupport.removeData("task_"+getScheduledType());
    }

    /**只从集合记录取多少条*/
    private List<TaskGetOrders> filterLimitList(List<TaskGetOrders> tlist){

        return filterLimitListFinal(tlist,50);

        /*List<TaskGetOrders> x=new ArrayList<TaskGetOrders>();
        for (int i = 0;i<2;i++){
            x.add(tlist.get(i));
        }
        return x;*/
    }

    public SynchronizeGetTimerOrdersTaskRun(){

    }

    @Override
    public String getScheduledType() {
        return MainTask.SYNCHRONIZE_GET_TIMER_ORDERS;
    }

    @Override
    public Integer crTimeMinu() {
        ITaskGetOrders iTaskGetOrders = (ITaskGetOrders) ApplicationContextUtil.getBean(ITaskGetOrders.class);
        List<TaskGetOrders> list=iTaskGetOrders.selectTaskGetOrdersByFlagIsFalseOrderBysaveTime();
        if(list.size()>0&&list.size()<=50){
            return 60;
        }else{
            return 2;
        }
    }
}
