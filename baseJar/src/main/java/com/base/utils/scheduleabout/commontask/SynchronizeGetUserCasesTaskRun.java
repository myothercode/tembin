package com.base.utils.scheduleabout.commontask;

import com.base.database.task.model.TaskGetUserCases;
import com.base.database.trading.mapper.UsercontrollerEbayAccountMapper;
import com.base.database.trading.model.UsercontrollerEbayAccount;
import com.base.database.trading.model.UsercontrollerEbayAccountExample;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.common.DateUtils;
import com.base.utils.scheduleabout.BaseScheduledClass;
import com.base.utils.scheduleabout.MainTask;
import com.base.utils.scheduleabout.Scheduledable;
import com.task.service.ITaskGetUserCases;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrtor on 2014/8/29.
 * 在线商品每晚执行，定时任务
 */
public class SynchronizeGetUserCasesTaskRun extends BaseScheduledClass implements Scheduledable {
    static Logger logger = Logger.getLogger(SynchronizeGetUserCasesTaskRun.class);

    public void saveTaskGetUserCases(List<UsercontrollerEbayAccount> ebays,Date date){
        ITaskGetUserCases iTaskGetUserCases = (ITaskGetUserCases) ApplicationContextUtil.getBean(ITaskGetUserCases.class);
        try{
            for(UsercontrollerEbayAccount ebay:ebays){
                Date date1=DateUtils.subDays(date, 1);
                Date end1= DateUtils.turnToDateEnd(date1);
                Date start1= DateUtils.turnToDateStart(date1);
                String start= DateUtils.DateToString(start1);
                String end=DateUtils.DateToString(end1);
                TaskGetUserCases TaskGetUserCases=new TaskGetUserCases();
                TaskGetUserCases.setToken(ebay.getEbayToken());
                TaskGetUserCases.setTokenflag(0);
                TaskGetUserCases.setFromtime(start);
                TaskGetUserCases.setEndtime(end);
                TaskGetUserCases.setSavetime(date);
                TaskGetUserCases.setUserid(ebay.getUserId());
                iTaskGetUserCases.saveListTaskGetUserCases(TaskGetUserCases);
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
        saveTaskGetUserCases(liue,date);
    }

    public SynchronizeGetUserCasesTaskRun(){

    }

    @Override
    public String getScheduledType() {
        return MainTask.SYNCHRONIZE_GET_USER_CASES;
    }
}