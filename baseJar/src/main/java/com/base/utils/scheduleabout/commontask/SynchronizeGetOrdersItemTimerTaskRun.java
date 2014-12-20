package com.base.utils.scheduleabout.commontask;

import com.base.database.trading.model.TradingOrderGetOrders;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.cache.TempStoreDataSupport;
import com.base.utils.scheduleabout.BaseScheduledClass;
import com.base.utils.scheduleabout.MainTask;
import com.base.utils.scheduleabout.Scheduledable;
import com.base.utils.threadpool.TaskPool;
import com.task.service.IScheduleGetTimerOrders;
import com.trading.service.ITradingOrderGetOrders;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by Administrtor on 2014/8/29.
 * 在线商品每晚执行，定时任务 //两分钟
 */
public class SynchronizeGetOrdersItemTimerTaskRun extends BaseScheduledClass implements Scheduledable {
    static Logger logger = Logger.getLogger(SynchronizeGetOrdersItemTimerTaskRun.class);

    public void synchronizeOrderItems(List<TradingOrderGetOrders> orders){
        IScheduleGetTimerOrders iScheduleGetTimerOrders=(IScheduleGetTimerOrders) ApplicationContextUtil.getBean(IScheduleGetTimerOrders.class);
        if(orders!=null&&orders.size()>0){
            try{
                iScheduleGetTimerOrders.synchronizeOrderItems(orders);
            }catch (Exception e){
                logger.error("定时同步订单商品失败task:",e);
                TempStoreDataSupport.removeData("task_"+getScheduledType());
            }
        }
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
        ITradingOrderGetOrders iTradingOrderGetOrders=(ITradingOrderGetOrders) ApplicationContextUtil.getBean(ITradingOrderGetOrders.class);
        List<TradingOrderGetOrders> orders=iTradingOrderGetOrders.selectOrderGetOrdersByItemFlag();
        if(orders.size()>20){
            orders=filterLimitList(orders);
        }
        synchronizeOrderItems(orders);
        TempStoreDataSupport.removeData("task_" + getScheduledType());
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

    public SynchronizeGetOrdersItemTimerTaskRun(){

    }

    @Override
    public String getScheduledType() {
        return MainTask.SYNCHRONIZE_GET_ORDERS_ITEM_TIMER;
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
        return 10;
    }
}
