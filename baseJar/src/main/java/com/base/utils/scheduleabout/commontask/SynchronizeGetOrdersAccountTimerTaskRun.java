package com.base.utils.scheduleabout.commontask;

import com.base.database.trading.model.TradingOrderGetOrders;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.scheduleabout.BaseScheduledClass;
import com.base.utils.scheduleabout.MainTask;
import com.base.utils.scheduleabout.Scheduledable;
import com.base.utils.threadpool.TaskPool;
import com.task.service.IScheduleGetTimerOrders;
import com.trading.service.ITradingOrderGetOrders;
import com.trading.service.ITradingOrderGetOrdersNoTransaction;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by Administrtor on 2014/8/29.
 * 根据订单定时同步account获取其成交费，定时任务 //两分钟
 */
public class SynchronizeGetOrdersAccountTimerTaskRun extends BaseScheduledClass implements Scheduledable {
    static Logger logger = Logger.getLogger(SynchronizeGetOrdersAccountTimerTaskRun.class);

    int synchronizeOrderAcoountbj=0;//0为可以执行，1为不能执行

    public void synchronizeOrderAcoount(List<TradingOrderGetOrders> orders){
        synchronizeOrderAcoountbj=1;
        IScheduleGetTimerOrders iScheduleGetTimerOrders=(IScheduleGetTimerOrders) ApplicationContextUtil.getBean(IScheduleGetTimerOrders.class);
        ITradingOrderGetOrdersNoTransaction iTradingOrderGetOrdersNoTransaction=(ITradingOrderGetOrdersNoTransaction) ApplicationContextUtil.getBean(ITradingOrderGetOrdersNoTransaction.class);
        if(orders!=null&&orders.size()>0){
            try{
                iScheduleGetTimerOrders.synchronizeOrderAccount(orders);
            }catch (Exception e){
                synchronizeOrderAcoountbj=0;
                logger.error("定时同步订单Account失败task:", e);
            }
        }
        synchronizeOrderAcoountbj=0;
    }
    @Override
    public void run(){
        int i= TaskPool.scheduledThreadPoolTaskExecutor.getActiveCount();
        if(i>30){
            return;
        }

        if (synchronizeOrderAcoountbj==1){
            logger.error(getScheduledType()+"===synchronizeOrderAcoountbj之前的任务还未完成继续等待下一个循环===");
            return;
        }

        Boolean b= TaskPool.threadIsAliveByName("thread_" + getScheduledType());
        if(b){
            logger.error(getScheduledType()+"===之前的任务还未完成继续等待下一个循环===");
            return;
        }
        Thread.currentThread().setName("thread_" + getScheduledType());


        ITradingOrderGetOrders iTradingOrderGetOrders=(ITradingOrderGetOrders) ApplicationContextUtil.getBean(ITradingOrderGetOrders.class);
        List<TradingOrderGetOrders> orders=iTradingOrderGetOrders.selectOrderGetOrdersByAccountFlag();
        if(orders.size()>20){
            orders=filterLimitList(orders);
        }
        synchronizeOrderAcoount(orders);
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

    public SynchronizeGetOrdersAccountTimerTaskRun(){

    }

    @Override
    public String getScheduledType() {
        return MainTask.SYNCHRONIZE_GET_ORDERS_ACCOUNT_TIMER;
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
        return 30;
    }
}
