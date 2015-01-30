package com.base.utils.scheduleabout;

import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.common.AppcenterClassFinder;
import com.base.utils.common.CommAutowiredClass;
import com.base.utils.common.DateUtils;
import com.base.utils.common.MyClassUtil;
import com.base.utils.scheduleother.StaticParam;
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
    public static final String AUTO_ASSESS="autoAssess";//自动发送评价
    public static final String CHECK_SYNC_INVENTORY="checkSyncInventory";//检查需要同步库存的数据
    public static final String SYNC_INVENTORY="syncInventory";//同步库存的定时任务
    public static final String CHECK_AUTO_ASSESS="checkAutoAssess";//检查需要自动发送评价
    public static final String LISTING_DATA="tradingListData";//在线数据同步每晚12点执行 同步在线商品
    public static final String LISTING_REPORT="listingReport";//首页统计每晚12点执行，统计昨天，上周，上月数据
    public static final String LISTING_TIMER_REPORT="listingTimerReport";//每两分钟执行一次统计，以提高首页查询速度
    public static final String LISTING_TIMER_TASK_DATA="tradingListtimertask";//在线数据同步每两分钟执行 同步在线商品
    public static final String SET_DEV_ZERO="setDevZero";//将开发帐号的调用次数清零
    public static final String AUTO_MESSAGE="autoMessage";//定时发送自动消息
    public static final String AUTO_COMPLEMENT="autocomplement";//定时发送自动消息
    public static final String FEEDBACK_AUTOM_ESSAGE="FeedBackAutoMessageTaskRun";//定时发送评价自动消息
    public static final String SYNCHRONIZE_GET_ORDERS="synchronize_get_orders";//定时每天插入账号去获取订单
    public static final String SYNCHRONIZE_GET_TIMER_ORDERS="synchronize_get_timer_orders";//定时同步订单每两分钟
    public static final String SYNCHRONIZE_FEED_BACK="synchronize_feed_back";//定时每天插入账号去获取评价
    public static final String SYNCHRONIZE_FEED_BACK_TIMER="synchronize_feed_back_timer";//定时同步订单每两分钟
    public static final String SYNCHRONIZE_GET_MESSAGES="synchronize_get_messages";//定时每天插入账号去获取消息
    public static final String SYNCHRONIZE_GET_MESSAGES_TIMER="synchronize_get_messages_timer";//定时同步消息每两分钟
    public static final String SYNCHRONIZE_GET_USER_CASES="synchronize_get_user_cases";//定时每天插入账号去获取纠纷
    public static final String SYNCHRONIZE_GET_USER_CASES_TIMER="synchronize_get_user_cases_timer";//定时同步纠纷每两分钟
    public static final String ITEM_INFORMATION_TYPE="item_information_type";//定时每两分钟去调用商品分类名称和ID
    public static final String SYNCHRONIZE_GET_ORDERS_ITEM_TIMER="synchronize_get_orders_item_timer";//定时同步根据订单订单商品每两分钟
    public static final String SYNCHRONIZE_GET_ORDERS_ACCOUNT_TIMER="synchronize_get_orders_account_timer";//定时账户根据订单订单商品每两分钟
    public static final String SYNCHRONIZE_GET_ORDERS_SELLER_TRANSACTION_TIMER="synchronize_get_orders_seller_transaction_timer";//定时外部交易根据订单订单商品每两分钟
    public static final String PRICE_TRACKING_BY_ITEMID="price_tracking_by_itemid";//定时价格跟踪商品
    public static final String SYNCHRONIZE_GET_ORDERS_TRACK_NUMBER_TIMER="synchronize_get_orders_track_number_timer";//定时价获取91track状态
    public static final String PRICE_TRACKING_AUTO_PRICING="price_tracking_auto_pricing"; //定时价格跟踪自动调价
    public static final String SYNCHRONIZE_GET_ORDERS_SELLER_TRANSACTION="synchronize_get_orders_seller_transaction"; //每天一次定时同步外部交易任务
    public static final String SYNCHRONIZE_SELECT_AUTO_MESSAGE_TASK_RUN="synchronize_select_auto_message_task_run";//通过订单表选取自动消息
    /**
     * 记录任务上次运行的时间
     */
    public static Map<String, Date> taskRunTime = new HashMap<String, Date>();//每增加一组任务。该值加1

    /**主入口,2分钟执行一次的任务*/
    @Scheduled(cron="0 0/2 *  * * ?")
    public void mainMethod(){
        String isLimit= SessionCacheSupport.getOther("devLimit");
        if(StringUtils.isNotEmpty(isLimit) && "limit".equalsIgnoreCase(isLimit)){
            return;
        }

        //定义一组该类型任务需要执行的任务类型
        List<String> doList=MainTaskStaticParam.doList;
        if (isStartTimerTask==null) {
            isStartTimerTask = ApplicationContextUtil.getBean(CommAutowiredClass.class);
        }
        if(isStartTimerTask==null){return;}

        List<String> taskList=new ArrayList<String>();
        if("false".equalsIgnoreCase(isStartTimerTask.isStartTimerTask)){
            return;
        }else {
            List<String> taskListTe=Arrays.asList(StringUtils.split(isStartTimerTask.isStartTimerTask,","));
            if(taskListTe==null || taskListTe.isEmpty()){return;}
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
        if(i>40){//如果队列大于30，那么不放入任何任务
            logger.info("队列中还有任务太多，等待下一次执行...........");
            return;
        }
        List<Class<? extends Scheduledable>> classList = AppcenterClassFinder.getInstance()
                .findSubClass(Scheduledable.class);
        List<? extends Scheduledable> scheduledableList = MyClassUtil.newInstance(classList);
        if(i>20){//如果任务大于20，小于30，那么就只放入定时刊登任务
            for (Scheduledable s : scheduledableList){
                if(taskList !=null && LISTING_SCHEDULE.equalsIgnoreCase(s.getScheduledType())){
                    TaskPool.scheduledThreadPoolTaskExecutor.execute(s);
                    taskRunTime.put(s.getScheduledType(),new Date());
                }
            }
        }else {
            for (Scheduledable s : scheduledableList){
                if(taskList !=null && taskList.contains(s.getScheduledType())){
                    Integer ii=s.crTimeMinu();
                    if(ii==null||ii==-1||ii==0){ii=2;}
                    if(ii!=null && ii !=-1){
                        Date lastTime=taskRunTime.get(s.getScheduledType());//上一次执行的时间
                        if(lastTime!=null){
                            int c= DateUtils.minuteBetween(lastTime,new Date());//现在时间与上次时间相差多少分钟
                            if(c<ii){
                                continue;
                            }
                        }
                    }

                    Integer ci=MainTaskStaticParam.SOME_MULIT_TASK.get(s.getScheduledType());
                    if (ci!=null && ci>1){
                        for (int iii=0;iii<ci;iii++){
                            try {
                                Scheduledable ss1=s.getClass().newInstance();
                                ss1.setMark(String.valueOf(iii));
                                TaskPool.scheduledThreadPoolTaskExecutor.execute(ss1);
                            } catch (Exception e) {
                                logger.error(s.getScheduledType() + "新建实例失败!", e);
                            }
                            //try {Thread.sleep(50L);} catch (Exception e) {logger.error(e);}
                        }
                    }else {
                        TaskPool.scheduledThreadPoolTaskExecutor.execute(s);
                    }
                    taskRunTime.put(s.getScheduledType(),new Date());
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
    @Scheduled(cron="0 31 3 * * ?")
    //@Scheduled(cron="0/10 * *  * * ?")
    private void doItEveryDay() throws Exception{
        if (isStartTimerTask==null) {
            isStartTimerTask = (CommAutowiredClass) ApplicationContextUtil.getBean(CommAutowiredClass.class);
        }

        List<String> doList=new ArrayList<String>();
        //doList.add(SET_DEV_ZERO); //api次数归零任务

        doList.add(SYNCHRONIZE_GET_ORDERS);
        doList.add(SYNCHRONIZE_FEED_BACK);
        doList.add(LISTING_REPORT);
        doList.add(SYNCHRONIZE_GET_MESSAGES);
        doList.add(SYNCHRONIZE_GET_USER_CASES);
        doList.add(SYNCHRONIZE_GET_ORDERS_SELLER_TRANSACTION);
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




    /**内部循环任务，基本不涉及到api的*/
    @Scheduled(cron="0 0/5 *  * * ?")
    public void mainMethodOther() throws Exception {

        if (isStartTimerTask==null) {
            isStartTimerTask = ApplicationContextUtil.getBean(CommAutowiredClass.class);
        }
        if(isStartTimerTask==null){return;}
        List<String> doList=new ArrayList<String>();
        doList.add(StaticParam.IMG_CHECK_SC);
        doList.add(StaticParam.IMG_CHECK_SC_TAKE);
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
