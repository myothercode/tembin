package com.base.utils.scheduleabout.commontask;

import com.alibaba.fastjson.JSONObject;
import com.base.database.trading.model.TradingOrderGetOrders;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.cache.TempStoreDataSupport;
import com.base.utils.scheduleabout.BaseScheduledClass;
import com.base.utils.scheduleabout.MainTask;
import com.base.utils.scheduleabout.Scheduledable;
import com.base.utils.thirdpart.OrderQueryTrack;
import com.base.utils.threadpool.TaskPool;
import com.trading.service.ITradingOrderGetOrders;
import com.trading.service.ITradingOrderGetOrdersNoTransaction;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrtor on 2014/8/29.
 * 在线商品每晚执行，定时任务 //两分钟
 */
public class SynchronizeGetOrdersTrackNumberTimerTaskRun extends BaseScheduledClass implements Scheduledable {
    static Logger logger = Logger.getLogger(SynchronizeGetOrdersTrackNumberTimerTaskRun.class);
    public void synchronizeOrderTrackNumer(List<TradingOrderGetOrders> orderses){
        ITradingOrderGetOrdersNoTransaction iTradingOrderGetOrdersNoTransaction=(ITradingOrderGetOrdersNoTransaction) ApplicationContextUtil.getBean(ITradingOrderGetOrdersNoTransaction.class);
        try {
            List<JSONObject> list= OrderQueryTrack.queryTrack(orderses);
            if(list!=null&&list.size()>0){
                for(TradingOrderGetOrders order:orderses){
                    for(JSONObject json:list){
                        if(order.getShipmenttrackingnumber().equals(json.get("Number"))){
                            order.setTrackstatus(json.get("Status")+"");
                            iTradingOrderGetOrdersNoTransaction.saveOrderGetOrders(order);
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("SynchronizeGetOrdersTrackNumberTimerTaskRun91track调用出错:",e);
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
        List<TradingOrderGetOrders> orderses=iTradingOrderGetOrders.selectOrderGetOrdersByTrackNumber();
        Integer size=orderses.size()/20;
        if(orderses.size()>20){
            for(i = 0; i<=size;i++){
                List<TradingOrderGetOrders> list=filterLimitList(orderses,i);
                synchronizeOrderTrackNumer(list);
            }
        }else{
            synchronizeOrderTrackNumer(orderses);
        }
        TempStoreDataSupport.removeData("task_" + getScheduledType());

    }

    /**只从集合记录取多少条*/
    private List<TradingOrderGetOrders> filterLimitList(List<TradingOrderGetOrders> tlist,Integer i){

        int dou=tlist.size() / 20;
        List<TradingOrderGetOrders> list=new ArrayList<TradingOrderGetOrders>();
        if(i==dou){
            for(int j=i*20;j<tlist.size();j++){
                TradingOrderGetOrders l=tlist.get(j);
                list.add(l);
            }
        }else{
            for(int j=i*20;j<(i+1)*20;j++){
                TradingOrderGetOrders l=tlist.get(j);
                list.add(l);
            }
        }
        return list;
    }

    public SynchronizeGetOrdersTrackNumberTimerTaskRun(){

    }

    @Override
    public String getScheduledType() {
        return MainTask.SYNCHRONIZE_GET_ORDERS_TRACK_NUMBER_TIMER;
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
