package com.base.utils.scheduleabout.commontask;

import com.base.database.task.model.TaskGetMessages;
import com.base.database.task.model.TaskGetUserCases;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.cache.TempStoreDataSupport;
import com.base.utils.common.MyStringUtil;
import com.base.utils.scheduleabout.BaseScheduledClass;
import com.base.utils.scheduleabout.MainTask;
import com.base.utils.scheduleabout.MainTaskStaticParam;
import com.base.utils.scheduleabout.Scheduledable;
import com.base.utils.threadpool.TaskPool;
import com.task.service.IScheduleGetUserCasesTimer;
import com.task.service.ITaskGetMessages;
import com.task.service.ITaskGetUserCases;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrtor on 2014/8/29.
 * 定时同步纠纷，定时任务
 */
public class SynchronizeGetUserCasesTimerTaskRun extends BaseScheduledClass implements Scheduledable {
    static Logger logger = Logger.getLogger(SynchronizeGetUserCasesTimerTaskRun.class);
    String mark="";

    public void synchronizeOrders(List<TaskGetUserCases> TaskGetUserCases){
        IScheduleGetUserCasesTimer iScheduleGetUserCasesTimer = (IScheduleGetUserCasesTimer) ApplicationContextUtil.getBean(IScheduleGetUserCasesTimer.class);
        try {
            iScheduleGetUserCasesTimer.synchronizeUserCases(TaskGetUserCases);
        } catch (Exception e) {
            logger.error("定时同步纠纷出错:", e);
        }
        /*UserInfoService userInfoService=(UserInfoService) ApplicationContextUtil.getBean(UserInfoService.class);*/


    }
    @Override
    public void run(){
        int i= TaskPool.scheduledThreadPoolTaskExecutor.getActiveCount();
        if(i>30){
            return;
        }


        List<TaskGetUserCases> list=null;
        if (MainTaskStaticParam.CATCH_CASE_QUEUE.isEmpty()){
            if (!"0".equalsIgnoreCase(this.mark)){
                return;
            }
            //logger.error("thread_" + getScheduledType()+"_当前线程编号为0，开始查数据");
            ITaskGetUserCases iTaskGetUserCases = (ITaskGetUserCases) ApplicationContextUtil.getBean(ITaskGetUserCases.class);
            list=iTaskGetUserCases.selectTaskGetUserCasesByFlagIsFalseOrderBysaveTime();
            if (list==null || list.isEmpty()){return;}

            if(MainTaskStaticParam.CATCH_CASE_QUEUE.isEmpty()){
                for (TaskGetUserCases t : list){
                    try {
                        if(MainTaskStaticParam.CATCH_CASE_QUEUE.contains(t)){continue;}
                        MainTaskStaticParam.CATCH_CASE_QUEUE.put(t);
                    } catch (Exception e) {logger.error("放入case队列出错",e);continue;}
                }
            }

        }

        TaskGetUserCases o=null;
        try {
            Iterator<TaskGetUserCases> iterator=MainTaskStaticParam.CATCH_CASE_QUEUE.iterator();
            while (iterator.hasNext()){
                if (MainTaskStaticParam.CATCH_CASE_QUEUE.isEmpty()){break;}
                TaskGetUserCases oo=MainTaskStaticParam.CATCH_CASE_QUEUE.take();
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
        Thread.currentThread().setName("thread_" + getScheduledType() + "_" + o.getId());
        List<TaskGetUserCases> list1=new ArrayList<TaskGetUserCases>();
        list1.add(o);
        synchronizeOrders(list1);
        TaskPool.threadRunTime.remove("thread_" + getScheduledType()+"_"+o.getId());
        Thread.currentThread().setName("thread_" + getScheduledType()+"_"+o.getId()+ MyStringUtil.getRandomStringAndNum(5));



        /*ITaskGetUserCases iTaskGetUserCases = (ITaskGetUserCases) ApplicationContextUtil.getBean(ITaskGetUserCases.class);
        List<TaskGetUserCases> list=iTaskGetUserCases.selectTaskGetUserCasesByFlagIsFalseOrderBysaveTime();
        if(list!=null&&list.size()>0){
            List<TaskGetUserCases> list1=new ArrayList<TaskGetUserCases>();
            list1.add(list.get(0));
            synchronizeOrders(list1);
        }

        TempStoreDataSupport.removeData("task_"+getScheduledType());*/
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
        Integer i=MainTaskStaticParam.SOME_CRTIMEMINU.get(getScheduledType());
        return i==null?5:i;
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
