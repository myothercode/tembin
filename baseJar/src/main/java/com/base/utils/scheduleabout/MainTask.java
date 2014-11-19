package com.base.utils.scheduleabout;

import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.common.AppcenterClassFinder;
import com.base.utils.common.CommAutowiredClass;
import com.base.utils.common.DateUtils;
import com.base.utils.common.MyClassUtil;
import com.base.utils.scheduleother.StaticParam;
import com.base.utils.scheduleother.domain.ImageCheckVO;
import com.base.utils.scheduleother.dorun.ImageCheckTaskPut;
import com.base.utils.scheduleother.dorun.ImageCheckTaskTake;
import com.base.utils.scheduleother.dorun.ScheduleOtherBase;
import com.base.utils.threadpool.TaskPool;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.quartz.SchedulerException;
import org.springframework.core.OrderComparator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by Administrtor on 2014/8/29.
 * 定时任务入口
 * http://www.cnblogs.com/sunjie9606/archive/2012/03/15/2397626.html
 * CRON表达式    含义
 * 30 10 * * * ?  每小时的10分30秒
 "0 0 12 * * ?"    每天中午十二点触发
 "0 15 10 ? * *"    每天早上10：15触发
 "0 15 10 * * ?"    每天早上10：15触发
 "0 15 10 * * ? *"    每天早上10：15触发
 "0 15 10 * * ? 2005"    2005年的每天早上10：15触发
 "0 * 14 * * ?"    每天从下午2点开始到2点59分每分钟一次触发
 "0 0/5 14 * * ?"    每天从下午2点开始到2：55分结束每5分钟一次触发
 "0 0/5 14,18 * * ?"    每天的下午2点至2：55和6点至6点55分两个时间段内每5分钟一次触发
 "0 0-5 14 * * ?"    每天14:00至14:05每分钟一次触发
 "0 10,44 14 ? 3 WED"    三月的每周三的14：10和14：44触发
 "0 15 10 ? * MON-FRI"    每个周一、周二、周三、周四、周五的10：15触发
 */
@Component
public class MainTask {
    static Logger logger = Logger.getLogger(MainTask.class);

    public static String isDongInitMethod="no";//是否已经执行了初始化字典的方法

    private CommAutowiredClass isStartTimerTask=null;

    public static final String LISTING_SCHEDULE="listSchedule";//定时刊登任务
    public static final String KEY_MOVE_LIST_TASK="keyMoveListTask";//一键搬家任务
    public static final String LISTING_DATA="tradingListData";//在线数据同步每晚12点执行
    public static final String LISTING_REPORT="listingReport";//在线数据同步每晚12点执行
    public static final String LISTING_TIMER_TASK_DATA="tradingListtimertask";//在线数据同步每两分钟执行
    public static final String SET_DEV_ZERO="setDevZero";//将开发帐号的调用次数清零
    public static final String

            AUTO_MESSAGE="autoMessage";//定时发送自动消息
    public static final String FEEDBACK_AUTOM_ESSAGE="FeedBackAutoMessageTaskRun";//定时发送评价自动消息
    public static final String SYNCHRONIZE_GET_ORDERS="synchronize_get_orders";//定时每天插入账号去获取订单
    public static final String SYNCHRONIZE_GET_TIMER_ORDERS="synchronize_get_timer_orders";//定时同步订单每两分钟
    public static final String SYNCHRONIZE_FEED_BACK="synchronize_feed_back";//定时每天插入账号去获取评价
    public static final String SYNCHRONIZE_FEED_BACK_TIMER="synchronize_feed_back_timer";//定时同步订单每两分钟

    /**主入口,2分钟执行一次的任务*/
    @Scheduled(cron="0 0/2 *  * * ?")
    public void mainMethod(){

        //定义一组该类型任务需要执行的任务类型
        List<String> doList=new ArrayList<String>();
        //doList.add(SET_DEV_ZERO); //排除掉归零任务
        doList.add(LISTING_SCHEDULE);
        doList.add(KEY_MOVE_LIST_TASK);
        doList.add(LISTING_TIMER_TASK_DATA);
        doList.add(AUTO_MESSAGE);
        doList.add(FEEDBACK_AUTOM_ESSAGE);
        doList.add(SYNCHRONIZE_GET_TIMER_ORDERS);
        doList.add(SYNCHRONIZE_FEED_BACK_TIMER);
        if (isStartTimerTask==null) {
            isStartTimerTask = ApplicationContextUtil.getBean(CommAutowiredClass.class);
        }
        if(isStartTimerTask==null){return;}

        List<String> taskList=new ArrayList<String>();
        if("false".equalsIgnoreCase(isStartTimerTask.isStartTimerTask)){
            return;
        }else {
            List<String> taskListTe=Arrays.asList(StringUtils.split(isStartTimerTask.isStartTimerTask,","));
            for (String t :taskListTe){
                if(doList.contains(t)){//判断该任务师傅在可执行列表中
                    taskList.add(t);
                }else {
                    continue;
                }
            }
        }
        if(taskList.isEmpty()){return;}

        int i=TaskPool.scheduledThreadPoolTaskExecutor.getActiveCount();
        if(i>30){//如果队列大于30，那么不放入任何任务
            logger.info("队列中还有任务太多，等待下一次执行...........");
            return;
        }
        List<Class<? extends Scheduledable>> classList = AppcenterClassFinder.getInstance()
                .findSubClass(Scheduledable.class);
        List<? extends Scheduledable> scheduledableList = MyClassUtil.newInstance(classList);
        if(i>0){//如果任务大于0，小于30，那么就只放入定时刊登任务
            for (Scheduledable s : scheduledableList){
                if(taskList !=null && LISTING_SCHEDULE.equalsIgnoreCase(s.getScheduledType())){
                    TaskPool.scheduledThreadPoolTaskExecutor.execute(s);
                }
            }
        }else {
            for (Scheduledable s : scheduledableList){
                if(taskList !=null && taskList.contains(s.getScheduledType())){
                    TaskPool.scheduledThreadPoolTaskExecutor.execute(s);
                }
            }
        }

    }

    /**spring启动后执行一次任务，加载数据字典*/
    @Scheduled(cron="0/59 * *  * * ?")
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

    /**每天凌晨执行一次的任务比如userInfoServiceMapper.initUseNum todo*/
    @Scheduled(cron="0 1 16 * * ?")
    //@Scheduled(cron="0/10 * *  * * ?")
    private void doItEveryDay() throws Exception{

        if (isStartTimerTask==null) {
            isStartTimerTask = (CommAutowiredClass) ApplicationContextUtil.getBean(CommAutowiredClass.class);
        }

        List<String> doList=new ArrayList<String>();
        doList.add(SET_DEV_ZERO); //api次数归零任务
        doList.add(LISTING_DATA);
        doList.add(SYNCHRONIZE_GET_ORDERS);
        doList.add(SYNCHRONIZE_FEED_BACK);
        doList.add(LISTING_REPORT);
        List<String> taskList=new ArrayList<String>();
        if("false".equalsIgnoreCase(isStartTimerTask.isStartTimerTask)){
            return;
        }else {
            List<String> taskListTe=Arrays.asList(StringUtils.split(isStartTimerTask.isStartTimerTask,","));
            for (String t :taskListTe){
                if(doList.contains(t)){//判断该任务师傅在可执行列表中
                    taskList.add(t);
                }else {
                    continue;
                }
            }
        }
        if(taskList.isEmpty()){return;}

        List<Class<? extends Scheduledable>> classList = AppcenterClassFinder.getInstance()
                .findSubClass(Scheduledable.class);
        List<? extends Scheduledable> scheduledableList = MyClassUtil.newInstance(classList);

        for (Scheduledable s : scheduledableList){
            if(taskList !=null && taskList.contains(s.getScheduledType())){
                TaskPool.scheduledThreadPoolTaskExecutor.execute(s);
            }
        }
    }



    /**
     * 记录任务上次运行的时间
     */
    public static Map<String, Date> taskRunTime = new HashMap<String, Date>();//每增加一组任务。该值加1
    /**内部循环任务，基本不涉及到api的*/
    @Scheduled(cron="0 0/1 *  * * ?")
    public void mainMethodOther() throws Exception {

        if (isStartTimerTask==null) {
            isStartTimerTask = ApplicationContextUtil.getBean(CommAutowiredClass.class);
        }
        List<String> doList=new ArrayList<String>();
        doList.add(StaticParam.IMG_CHECK_SC);
        doList.add(StaticParam.IMG_CHECK_SC_TAKE);
        doList.add(StaticParam.INVENTORY_CHECK_SC_TAKE);

        List<String> taskList=new ArrayList<String>();

        List<String> taskListTe=Arrays.asList(StringUtils.split(isStartTimerTask.isStartTimerTask,","));

        for (String t :taskListTe){
            if(doList.contains(t)){//判断该任务师傅在可执行列表中
                taskList.add(t);
            }else {
                continue;
            }
        }
        if(taskList.isEmpty()){return;}

        /**=========任务开始============*/
        List<Class<? extends ScheduleOtherBase>> classList = AppcenterClassFinder.getInstance("com.base.utils.scheduleother.dorun")
                .findSubClass(ScheduleOtherBase.class);
        List<? extends ScheduleOtherBase> scheduledableList = MyClassUtil.newInstance(classList);
        for(ScheduleOtherBase s :scheduledableList){
            if(!scheduledableList.isEmpty() && taskList.contains(s.stype()) ){
                int i =s.cyclesTime();//取得时间间隔 分钟
                String taskName=s.stype();//任务名称
                if(i==0){//如果定义为0，那么就是后台伺服进程
                    if(!TaskPool.threadIsAliveByName(taskName)){
                        TaskPool.otherScheduledThreadPoolTaskExecutor.execute(s);
                    }
                    continue;
                }
                if(TaskPool.SCBaseQueue.size()>40){continue;}

                Date lastTime=taskRunTime.get(taskName);//上一次执行的时间
                if(lastTime!=null){
                    int c= DateUtils.minuteBetween(lastTime,new Date());//现在时间与上次时间相差多少分钟
                    if(c<i){
                        continue;
                    }
                }
                TaskPool.otherScheduledThreadPoolTaskExecutor.execute(s);
                taskRunTime.put(taskName,new Date());
            }
        }
/**此类任务不适合统一执行了*/
        /***图片检查任务,后台进程*/
        /*if(!taskList.isEmpty() && taskList.contains(StaticParam.IMG_CHECK_SC) ){
            if(!TaskPool.threadIsAliveByName(StaticParam.IMG_CHECK_SC_TAKE)){
                Runnable runnable=new ImageCheckTaskTake(null);
                TaskPool.otherScheduledThreadPoolTaskExecutor.execute(runnable);
            }
         //   int i=StaticParam.taskTime.get(StaticParam.IMG_CHECK_SC);//找到该任务定义的时间间隔

            Runnable r=new ImageCheckTaskPut(null);
            TaskPool.otherScheduledThreadPoolTaskExecutor.execute(r);
            StaticParam.taskRunTime.put(StaticParam.IMG_CHECK_SC,new Date());
        }*/

    }


}
