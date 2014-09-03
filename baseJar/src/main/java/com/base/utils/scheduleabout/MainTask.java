package com.base.utils.scheduleabout;

import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.common.AppcenterClassFinder;
import com.base.utils.common.CommAutowiredClass;
import com.base.utils.common.MyClassUtil;
import com.base.utils.scheduleabout.commontask.TimeListingItemTaskRunAble;
import com.base.utils.threadpool.TaskPool;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.OrderComparator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * Created by Administrtor on 2014/8/29.
 * 定时任务入口
 */
@Component
public class MainTask {
    static Logger logger = Logger.getLogger(MainTask.class);
    private String isDongInitMethod="no";//是否已经执行了初始化方法


    /**主入口*/
    @Scheduled(cron="0 0/15 *  * * ?")
    public void mainMethod(){
        CommAutowiredClass isStartTimerTask= (CommAutowiredClass) ApplicationContextUtil.getBean(CommAutowiredClass.class);
        if("false".equalsIgnoreCase(isStartTimerTask.isStartTimerTask)){
            return;
        }
        int i=TaskPool.scheduledThreadPoolTaskExecutor.getActiveCount();
        if(i>0){
            System.out.println("队列中还有任务没有完成，等待下一次执行...........");
            return;
        }

        List<Class<? extends Scheduledable>> classList = AppcenterClassFinder.getInstance()
                .findSubClass(Scheduledable.class);
        List<? extends Scheduledable> scheduledableList = MyClassUtil.newInstance(classList);

        for (Scheduledable s : scheduledableList){
            TaskPool.scheduledThreadPoolTaskExecutor.execute(s);
        }
    }

    /**spring启动后执行一次任务*/
    @Scheduled(cron="0/30 * *  * * ?")
    private void DoItAfterBoot() throws SchedulerException {
        if("no".equalsIgnoreCase(isDongInitMethod)){
            isDongInitMethod="yes";
            List<Class<? extends Initable>> classList = AppcenterClassFinder.getInstance()
                    .findSubClass(Initable.class);

            List<? extends Initable> initableList = MyClassUtil.newInstance(classList);
            Collections.sort(initableList, new OrderComparator());
            for (Initable s : initableList){
                s.init();
            }

        }else {
            return;
        }
    }

}
