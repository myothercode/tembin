package com.base.utils.scheduleabout.commontask;

import com.base.database.task.model.TaskGetOrders;
import com.base.database.trading.mapper.UsercontrollerEbayAccountMapper;
import com.base.database.trading.model.TradingOrderGetOrders;
import com.base.database.trading.model.UsercontrollerEbayAccount;
import com.base.database.trading.model.UsercontrollerEbayAccountExample;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.common.DateUtils;
import com.base.utils.scheduleabout.BaseScheduledClass;
import com.base.utils.scheduleabout.MainTask;
import com.base.utils.scheduleabout.Scheduledable;
import com.task.service.ITaskGetOrders;
import com.trading.service.ITradingOrderGetOrders;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrtor on 2014/8/29.
 * 每次一次订单task列表，定时任务
 */
public class SynchronizeGetOrdersTaskRun extends BaseScheduledClass implements Scheduledable {
    static Logger logger = Logger.getLogger(SynchronizeGetOrdersTaskRun.class);

    public void saveTaskGetOrders(List<UsercontrollerEbayAccount> ebays,Date date){
        ITaskGetOrders iTaskGetOrders = (ITaskGetOrders) ApplicationContextUtil.getBean(ITaskGetOrders.class);
        ITradingOrderGetOrders iTradingOrderGetOrders = (ITradingOrderGetOrders) ApplicationContextUtil.getBean(ITradingOrderGetOrders.class);
        try{
            for(UsercontrollerEbayAccount ebay:ebays){
                Date date2=DateUtils.addDays(date,1);
                Date date1=DateUtils.subDays(date2, 7);
                Date end1= com.base.utils.common.DateUtils.turnToDateEnd(date2);
                Date start1=com.base.utils.common.DateUtils.turnToDateStart(date1);
                String start= DateUtils.DateToString(start1);
                String end=DateUtils.DateToString(end1);
                TaskGetOrders taskGetOrders=new TaskGetOrders();
                taskGetOrders.setToken(ebay.getEbayToken());
                taskGetOrders.setTokenflag(0);
                taskGetOrders.setFromtime(start);
                taskGetOrders.setTotime(end);
                taskGetOrders.setSavetime(date);
                taskGetOrders.setUserid(ebay.getUserId());
                taskGetOrders.setEbayname(ebay.getEbayName());
                Date createDate1=ebay.getCreateTime();
                Date createDate=DateUtils.subDays(createDate1,60);
                taskGetOrders.setNewuserflag(null);
                iTaskGetOrders.saveListTaskGetOrders(taskGetOrders);
                List<TradingOrderGetOrders> orders=iTradingOrderGetOrders.selectOrderGetOrdersByCreatedDateAndEbayAcount(createDate,ebay.getEbayName());
                if(orders!=null&&orders.size()==0){
                    TaskGetOrders taskGetOrders1=new TaskGetOrders();
                    taskGetOrders1=taskGetOrders;
                    //-----------------
                    Date createdate2=DateUtils.addDays(createDate1,1);
                    Date createdate1=DateUtils.subDays(createdate2, 90);
                    Date createend1= com.base.utils.common.DateUtils.turnToDateEnd(createdate2);
                    Date createstart1=com.base.utils.common.DateUtils.turnToDateStart(createdate1);
                    String createstart= DateUtils.DateToString(createstart1);
                    String createend=DateUtils.DateToString(createend1);
                    //---------------------
                    taskGetOrders1.setFromtime(createstart);
                    taskGetOrders1.setTotime(createend);
                    taskGetOrders1.setNewuserflag(0);
                    iTaskGetOrders.saveListTaskGetOrders(taskGetOrders1);
                }
            }
        }catch (Exception e){
            logger.error("定时每天插入账号去获取订单出错:"+e.getMessage(),e);
        }

    }
    @Override
    public void run(){
        UsercontrollerEbayAccountMapper ueam = (UsercontrollerEbayAccountMapper) ApplicationContextUtil.getBean(UsercontrollerEbayAccountMapper.class);
        UsercontrollerEbayAccountExample ueame = new UsercontrollerEbayAccountExample();
        ueame.createCriteria().andEbayStatusEqualTo("1");
        List<UsercontrollerEbayAccount> liue = ueam.selectByExampleWithBLOBs(ueame);
        Date date=new Date();
        saveTaskGetOrders(liue,date);
    }

    public SynchronizeGetOrdersTaskRun(){

    }

    @Override
    public String getScheduledType() {
        return MainTask.SYNCHRONIZE_GET_ORDERS;
    }

    @Override
    public Integer crTimeMinu() {
        return null;
    }

    @Override
    public void setMark(String x) {

    }

    @Override
    public String getMark() {
        return null;
    }
}
