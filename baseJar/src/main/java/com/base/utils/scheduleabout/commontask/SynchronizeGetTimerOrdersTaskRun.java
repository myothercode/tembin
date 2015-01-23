package com.base.utils.scheduleabout.commontask;

import com.base.database.task.model.TaskGetOrders;
import com.base.database.trading.model.TradingOrderGetOrders;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.common.MyStringUtil;
import com.base.utils.scheduleabout.BaseScheduledClass;
import com.base.utils.scheduleabout.MainTask;
import com.base.utils.scheduleabout.MainTaskStaticParam;
import com.base.utils.scheduleabout.Scheduledable;
import com.base.utils.threadpool.TaskPool;
import com.task.service.IScheduleGetTimerOrders;
import com.task.service.ITaskGetOrders;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrtor on 2014/8/29.
 * 定时同步订单，定时任务 //两分钟
 */
public class SynchronizeGetTimerOrdersTaskRun extends BaseScheduledClass implements Scheduledable {
    static Logger logger = Logger.getLogger(SynchronizeGetTimerOrdersTaskRun.class);

    public void synchronizeOrders(List<TaskGetOrders> taskGetOrders){
        IScheduleGetTimerOrders iScheduleGetTimerOrders=(IScheduleGetTimerOrders) ApplicationContextUtil.getBean(IScheduleGetTimerOrders.class);
        try {
            iScheduleGetTimerOrders.synchronizeOrders(taskGetOrders);
        } catch (Exception e) {
            logger.error("定时同步订单失败task:", e);
        }
    }
    @Override
    public void run(){
        int i= TaskPool.scheduledThreadPoolTaskExecutor.getActiveCount();
        if(i>30){
            return;
        }
        List<TaskGetOrders> list= null;
        /*String isRunging = TempStoreDataSupport.pullData("task_"+getScheduledType());
        if(StringUtils.isNotEmpty(isRunging)){return;}
        TempStoreDataSupport.pushData("task_" + getScheduledType(), "x");*/
    if (MainTaskStaticParam.CATCH_ORDER_QUEUE.isEmpty()){
        ITaskGetOrders iTaskGetOrders = (ITaskGetOrders) ApplicationContextUtil.getBean(ITaskGetOrders.class);
        list = iTaskGetOrders.selectTaskGetOrdersByFlagIsFalseOrderBysaveTime();
        if (list==null || list.isEmpty()){return;}
    }

        if(MainTaskStaticParam.CATCH_ORDER_QUEUE.isEmpty()){
            for (TaskGetOrders t : list){
                try {
                    MainTaskStaticParam.CATCH_ORDER_QUEUE.put(t);
                } catch (Exception e) {logger.error("放入order队列出错",e);continue;}
            }
        }
        TaskGetOrders o=null;
        try {
            Iterator<TaskGetOrders> iterator=MainTaskStaticParam.CATCH_ORDER_QUEUE.iterator();
            while (iterator.hasNext()){
                TaskGetOrders oo=MainTaskStaticParam.CATCH_ORDER_QUEUE.take();
                if (oo==null){continue;}

                Boolean b= TaskPool.threadIsAliveByName("thread_" + getScheduledType()+"_"+oo.getId());
                if (b){logger.error(getScheduledType()+oo.getId()+"===之前的帐号任务还未结束取下一个===");continue;}
                o=oo;
                break;
            }
        } catch (Exception e) {}

        if(o==null){
            return;
        }
        /*if(list!=null&&list.size()>0){
            List<TaskGetOrders> list1=new ArrayList<TaskGetOrders>();
            list1.add(list.get(0));

        }*/

        /*Boolean b= TaskPool.threadIsAliveByName("thread_" + getScheduledType());
        if(b){
            logger.error(getScheduledType()+"===之前的任务还未完成继续等待下一个循环===");
            return;
        }*/
        //logger.error(getScheduledType() +o.getId() + "===任务开始===");
        Thread.currentThread().setName("thread_" + getScheduledType()+"_"+o.getId());
        List<TaskGetOrders> list1=new ArrayList<TaskGetOrders>();
        list1.add(o);
        synchronizeOrders(list1);
        TaskPool.threadRunTime.remove("thread_" + getScheduledType()+"_"+o.getId());
        Thread.currentThread().setName("thread_" + getScheduledType()+"_"+o.getId()+ MyStringUtil.getRandomStringAndNum(5));
        //logger.error(getScheduledType()+o.getId() + "===任务结束===");
        //TempStoreDataSupport.removeData("task_"+getScheduledType());
    }

    /**只从集合记录取多少条*/
    private List<TradingOrderGetOrders> filterLimitList(List<TradingOrderGetOrders> tlist){

        return filterLimitListFinal(tlist,20);

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
     /*   ITaskGetOrders iTaskGetOrders = (ITaskGetOrders) ApplicationContextUtil.getBean(ITaskGetOrders.class);
        List<TaskGetOrders> list=iTaskGetOrders.selectTaskGetOrdersByFlagIsFalseOrderBysaveTime();
        if(list.size()>0&&list.size()<=50){
            return 60;
        }else{
            return 2;
        }*/
        return 2;
    }

    @Override
    public void setMark(String x) {

    }

    @Override
    public String getMark() {
        return null;
    }
}
