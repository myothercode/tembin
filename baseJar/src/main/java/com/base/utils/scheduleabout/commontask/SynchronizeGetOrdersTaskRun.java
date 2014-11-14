package com.base.utils.scheduleabout.commontask;

import com.base.database.task.model.TaskGetOrders;
import com.base.database.trading.mapper.UsercontrollerEbayAccountMapper;
import com.base.database.trading.model.UsercontrollerEbayAccount;
import com.base.database.trading.model.UsercontrollerEbayAccountExample;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.common.DateUtils;
import com.base.utils.scheduleabout.BaseScheduledClass;
import com.base.utils.scheduleabout.MainTask;
import com.base.utils.scheduleabout.Scheduledable;
import com.task.service.ITaskGetOrders;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrtor on 2014/8/29.
 * 在线商品每晚执行，定时任务
 */
public class SynchronizeGetOrdersTaskRun extends BaseScheduledClass implements Scheduledable {
    static Logger logger = Logger.getLogger(SynchronizeGetOrdersTaskRun.class);

    public void saveTaskGetOrders(List<UsercontrollerEbayAccount> ebays,Date date){
        ITaskGetOrders iTaskGetOrders = (ITaskGetOrders) ApplicationContextUtil.getBean(ITaskGetOrders.class);
        try{
            for(UsercontrollerEbayAccount ebay:ebays){
                Date date1=DateUtils.subDays(date, 1);
                Date end1= com.base.utils.common.DateUtils.turnToDateEnd(date1);
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
                iTaskGetOrders.saveListTaskGetOrders(taskGetOrders);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    @Override
    public void run(){
        UsercontrollerEbayAccountMapper ueam = (UsercontrollerEbayAccountMapper) ApplicationContextUtil.getBean(UsercontrollerEbayAccountMapper.class);
        UsercontrollerEbayAccountExample ueame = new UsercontrollerEbayAccountExample();
        ueame.createCriteria();
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
}