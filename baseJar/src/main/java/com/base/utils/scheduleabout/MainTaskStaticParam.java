package com.base.utils.scheduleabout;

import com.base.database.task.model.*;
import com.base.database.trading.model.TradingOrderGetOrders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Administrator on 2015/1/15.
 * MainTask的一些运行参数
 */
public class MainTaskStaticParam {
    /**定义一些每次执行时需要开多个线程的任务，以及次数*/
    public static final Map<String,Integer> SOME_MULIT_TASK=new HashMap<String, Integer>();
    static {
        SOME_MULIT_TASK.put("Test_Test_test",7);
        SOME_MULIT_TASK.put(MainTask.SYNCHRONIZE_GET_TIMER_ORDERS,2);
        SOME_MULIT_TASK.put(MainTask.LISTING_TIMER_TASK_DATA,4);
        SOME_MULIT_TASK.put(MainTask.SYNCHRONIZE_GET_ORDERS_ACCOUNT_TIMER,2);
        SOME_MULIT_TASK.put(MainTask.SYNCHRONIZE_GET_MESSAGES_TIMER,1);
        SOME_MULIT_TASK.put(MainTask.SYNCHRONIZE_GET_USER_CASES_TIMER,2);
        SOME_MULIT_TASK.put(MainTask.SYNCHRONIZE_FEED_BACK_TIMER,2);
    }
        /**定义一个抓订单需要的ebay帐号队列*/
        public final static BlockingQueue<TaskGetOrders> CATCH_ORDER_QUEUE=new ArrayBlockingQueue<TaskGetOrders>(10000);

        /**在线商品同步队列*/
        public final static BlockingQueue<ListingDataTask> CATCH_LISTINGDATA_QUEUE=new ArrayBlockingQueue<ListingDataTask>(10000);

        /**抓取message需要的ebay账号队列*/
        public final static BlockingQueue<TaskGetMessages> CATCH_EBAYMESSAGE_QUEUE=new ArrayBlockingQueue<TaskGetMessages>(10000);
        /**获取订单交易费等信息的队列*/
        public final static BlockingQueue<TradingOrderGetOrders> CATCH_OEDERACCOUNT_QUEUE=new ArrayBlockingQueue<TradingOrderGetOrders>(10000);

        /**获取case的队列*/
        public final static BlockingQueue<TaskGetUserCases> CATCH_CASE_QUEUE=new ArrayBlockingQueue<TaskGetUserCases>(10000);
        /**获取FEEDBACK评价队列*/
        public final static BlockingQueue<TaskFeedBack> CATCH_FEEDBACK_QUEUE=new ArrayBlockingQueue<TaskFeedBack>(10000);




        /**定义两分钟一次的定时，需要跑哪些任务*/
        public final static List<String> doList=new ArrayList<String>();
        static {
            // doList.add(SET_DEV_ZERO); //排除掉归零任务
            doList.add(MainTask.LISTING_SCHEDULE);
            doList.add(MainTask.KEY_MOVE_LIST_TASK);
            doList.add(MainTask.LISTING_DATA);
            doList.add(MainTask.LISTING_TIMER_TASK_DATA);
            doList.add(MainTask.AUTO_MESSAGE);
            doList.add(MainTask.AUTO_ASSESS);
            doList.add(MainTask.FEEDBACK_AUTOM_ESSAGE);
            doList.add(MainTask.SYNCHRONIZE_GET_TIMER_ORDERS);
            doList.add(MainTask.SYNCHRONIZE_FEED_BACK_TIMER);
            doList.add(MainTask.SYNCHRONIZE_GET_MESSAGES_TIMER);
            doList.add(MainTask.SYNCHRONIZE_GET_USER_CASES_TIMER);
            doList.add(MainTask.ITEM_INFORMATION_TYPE);
            doList.add(MainTask.AUTO_COMPLEMENT);
            doList.add(MainTask.SYNCHRONIZE_GET_ORDERS_ITEM_TIMER);
            doList.add(MainTask.SYNCHRONIZE_GET_ORDERS_ACCOUNT_TIMER);
            doList.add(MainTask.SYNCHRONIZE_GET_ORDERS_SELLER_TRANSACTION_TIMER);
            doList.add(MainTask.SYNCHRONIZE_GET_ORDERS_TRACK_NUMBER_TIMER);
            doList.add(MainTask.PRICE_TRACKING_BY_ITEMID);
            doList.add(MainTask.PRICE_TRACKING_AUTO_PRICING);
            doList.add(MainTask.LISTING_TIMER_REPORT);
            doList.add(MainTask.CHECK_AUTO_ASSESS);

            doList.add("Test_Test_test");
    }
    /**定义各个任务的间隔时间*/
    public static final Map<String,Integer> SOME_CRTIMEMINU=new HashMap<String, Integer>();
    static {
        SOME_CRTIMEMINU.put(MainTask.SYNCHRONIZE_GET_ORDERS_ACCOUNT_TIMER,2);//获取订单交易费信息
        SOME_CRTIMEMINU.put(MainTask.SYNCHRONIZE_GET_MESSAGES_TIMER,2);//获取抓取message
        SOME_CRTIMEMINU.put(MainTask.SYNCHRONIZE_GET_USER_CASES_TIMER,2);//获取抓取case
        SOME_CRTIMEMINU.put(MainTask.SYNCHRONIZE_FEED_BACK_TIMER,10);//获取抓取feedback评价

    }


}
