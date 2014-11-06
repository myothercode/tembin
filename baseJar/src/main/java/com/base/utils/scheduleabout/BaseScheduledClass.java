package com.base.utils.scheduleabout;

import com.base.database.trading.model.TradingTimerListingWithBLOBs;
import com.base.utils.common.ObjectUtils;
import com.base.utils.threadpool.TaskPool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrtor on 2014/8/29.
 * 所有的任务类都必须继承此类
 */
public class BaseScheduledClass {
    /**获取当前线程池中还有多少任务*/
    public int getCurrActiveCount(){
        return TaskPool.scheduledThreadPoolTaskExecutor.getActiveCount();
    }

    /**只从集合记录取多少条*/
    public <T> List<T> filterLimitListFinal(List<T> tlist,Integer count){
        if(count==null || count<=0){
            count=20;
        }
        if(ObjectUtils.isLogicalNull(tlist) || tlist.size()<=count){
            return tlist;
        }
        List<T> x=new ArrayList<T>();
        for (int i = 0;i<count;i++){
            x.add(tlist.get(i));
        }
        return x;
    }
}
