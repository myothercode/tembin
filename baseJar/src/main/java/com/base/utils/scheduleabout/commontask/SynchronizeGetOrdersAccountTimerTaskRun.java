package com.base.utils.scheduleabout.commontask;

import com.base.database.trading.model.TradingOrderGetOrders;
import com.base.domains.querypojos.OrderGetOrdersQuery;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.common.MyStringUtil;
import com.base.utils.scheduleabout.BaseScheduledClass;
import com.base.utils.scheduleabout.MainTask;
import com.base.utils.scheduleabout.MainTaskStaticParam;
import com.base.utils.scheduleabout.Scheduledable;
import com.base.utils.threadpool.TaskPool;
import com.task.service.IScheduleGetTimerOrders;
import com.trading.service.ITradingOrderGetOrders;
import com.trading.service.ITradingOrderGetOrdersNoTransaction;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrtor on 2014/8/29.
 * 根据订单定时同步account获取其成交费，定时任务 //两分钟
 */
public class SynchronizeGetOrdersAccountTimerTaskRun extends BaseScheduledClass implements Scheduledable {
    static Logger logger = Logger.getLogger(SynchronizeGetOrdersAccountTimerTaskRun.class);
    String mark;

    public void synchronizeOrderAcoount(List<TradingOrderGetOrders> orders){
        IScheduleGetTimerOrders iScheduleGetTimerOrders=(IScheduleGetTimerOrders) ApplicationContextUtil.getBean(IScheduleGetTimerOrders.class);
        ITradingOrderGetOrdersNoTransaction iTradingOrderGetOrdersNoTransaction=(ITradingOrderGetOrdersNoTransaction) ApplicationContextUtil.getBean(ITradingOrderGetOrdersNoTransaction.class);
        if(orders!=null&&orders.size()>0){
            try{
                iScheduleGetTimerOrders.synchronizeOrderAccount(orders);
            }catch (Exception e){
                logger.error("定时同步订单Account失败task:", e);
            }
        }
    }
    @Override
    public void run(){
        int i= TaskPool.scheduledThreadPoolTaskExecutor.getActiveCount();
        if(i>30){
            return;
        }

        int tnumber=TaskPool.countThreadNumByName("thread_" + getScheduledType());
        if(tnumber>10){logger.error("当前执行获取交易费信息的线程过多...");return;}

        if(MainTaskStaticParam.CATCH_OEDERACCOUNT_QUEUE.isEmpty()){
            if (!"0".equalsIgnoreCase(this.mark)){
                return;
            }
            //logger.error("thread_" + getScheduledType()+"_当前线程编号为0，开始查数据");
            ITradingOrderGetOrders iTradingOrderGetOrders=(ITradingOrderGetOrders) ApplicationContextUtil.getBean(ITradingOrderGetOrders.class);
            //todo 记得限制每次获取数据的数量
            List<OrderGetOrdersQuery> orders1=iTradingOrderGetOrders.selectOrderGetOrdersByAccountFlag();
            List<TradingOrderGetOrders> orders=new ArrayList<TradingOrderGetOrders>();
            if(orders1!=null&&orders1.size()>0){
                orders.addAll(orders1);
            }
            if (orders==null || orders.isEmpty()){return;}

            if(MainTaskStaticParam.CATCH_OEDERACCOUNT_QUEUE.isEmpty()){
                for (TradingOrderGetOrders order:orders){
                    if(MainTaskStaticParam.CATCH_OEDERACCOUNT_QUEUE.size()>=2000){break;}
                    try {
                        if (MainTaskStaticParam.CATCH_OEDERACCOUNT_QUEUE.contains(order)){logger.error("队列中已有"); continue;}
                        Boolean b= TaskPool.threadIsAliveByName("thread_" + getScheduledType() + "_" + order.getId());
                        if (b){logger.error(getScheduledType()+order.getId()+"===之前的帐号任务还未结束不放入===");continue;}
                        MainTaskStaticParam.CATCH_OEDERACCOUNT_QUEUE.put(order);
                    } catch (Exception e) {logger.error("放入orderAccount队列出错",e);continue;}
                }
            }
        }else {
            logger.error("线程编号不为0,执行====");
        }

        if( MainTaskStaticParam.CATCH_OEDERACCOUNT_QUEUE.isEmpty()){return;}
       // List<TradingOrderGetOrders> ord=new ArrayList<TradingOrderGetOrders>();
        Iterator<TradingOrderGetOrders> iterator=MainTaskStaticParam.CATCH_OEDERACCOUNT_QUEUE.iterator();
        int ix=0;
        while (iterator.hasNext()){
            if(MainTaskStaticParam.CATCH_OEDERACCOUNT_QUEUE.isEmpty()||ix>=20){break;}
            TradingOrderGetOrders oo=null;
            try {
                oo=MainTaskStaticParam.CATCH_OEDERACCOUNT_QUEUE.take();
                if (oo==null){continue;}
            } catch (Exception e) {continue;}
            String threadName="thread_" + getScheduledType() + "_" + oo.getId();

            Boolean b= TaskPool.threadIsAliveByName(threadName);
            if (b){logger.error(getScheduledType()+oo.getId()+"===之前的帐号任务还未结束取下一个===");continue;}
            //ord.add(oo);
            //logger.error(threadName + "===任务开始===");
            Thread.currentThread().setName(threadName);
            List<TradingOrderGetOrders> ord=new ArrayList<TradingOrderGetOrders>();
            ord.add(oo);
            synchronizeOrderAcoount(ord);
            TaskPool.threadRunTime.remove(threadName);
            Thread.currentThread().setName("_"+threadName + MyStringUtil.getRandomStringAndNum(5));
            //logger.error(getScheduledType()+oo.getId() + "===任务结束===");
            ix++;
        }


        /**============================================================================*/
        /*Boolean b= TaskPool.threadIsAliveByName("thread_" + getScheduledType());
        if(b){
            logger.error(getScheduledType()+"===之前的任务还未完成继续等待下一个循环===");
            return;
        }
        Thread.currentThread().setName("thread_" + getScheduledType());

        ITradingOrderGetOrders iTradingOrderGetOrders=(ITradingOrderGetOrders) ApplicationContextUtil.getBean(ITradingOrderGetOrders.class);
        List<OrderGetOrdersQuery> orders1=iTradingOrderGetOrders.selectOrderGetOrdersByAccountFlag();
        List<TradingOrderGetOrders> orders=new ArrayList<TradingOrderGetOrders>();
        if(orders1!=null&&orders1.size()>0){
            orders.addAll(orders1);
        }
        synchronizeOrderAcoount(orders);
        TaskPool.threadRunTime.remove("thread_" + getScheduledType());
        Thread.currentThread().setName("thread_" + getScheduledType()+ MyStringUtil.getRandomStringAndNum(5));*/
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
        return MainTaskStaticParam.SOME_CRTIMEMINU.get(getScheduledType());
    }

    @Override
    public void setMark(String x) {
        this.mark=x;
    }

    @Override
    public String getMark() {
        return this.mark;
    }
}
