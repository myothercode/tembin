package com.base.utils.scheduleabout.commontask;

import com.base.database.task.model.TaskGetMessages;
import com.base.database.trading.mapper.UsercontrollerEbayAccountMapper;
import com.base.database.trading.model.UsercontrollerEbayAccount;
import com.base.database.trading.model.UsercontrollerEbayAccountExample;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.common.DateUtils;
import com.base.utils.scheduleabout.BaseScheduledClass;
import com.base.utils.scheduleabout.MainTask;
import com.base.utils.scheduleabout.Scheduledable;
import com.task.service.ITaskGetMessages;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrtor on 2014/8/29.
 * 每天一次消息message Task列表
 */
public class SynchronizeGetMessagesTaskRun extends BaseScheduledClass implements Scheduledable {
    static Logger logger = Logger.getLogger(SynchronizeGetMessagesTaskRun.class);

    public void saveTaskGetMessages(List<UsercontrollerEbayAccount> ebays,Date date){
        ITaskGetMessages iTaskGetMessages = (ITaskGetMessages) ApplicationContextUtil.getBean(ITaskGetMessages.class);
        try{
            for(UsercontrollerEbayAccount ebay:ebays){
                Date date2=DateUtils.addDays(date,1);
                Date date1=DateUtils.subDays(date2, 7);
                Date end1= com.base.utils.common.DateUtils.turnToDateEnd(date2);
                Date start1=com.base.utils.common.DateUtils.turnToDateStart(date1);
                String start= DateUtils.DateToString(start1);
                String end=DateUtils.DateToString(end1);
                TaskGetMessages TaskGetMessages=new TaskGetMessages();
                TaskGetMessages.setToken(ebay.getEbayToken());
                TaskGetMessages.setTokenflag(0);
                TaskGetMessages.setFromtime(start);
                TaskGetMessages.setEndtime(end);
                TaskGetMessages.setSavetime(date);
                TaskGetMessages.setUserid(ebay.getUserId());
                TaskGetMessages.setEbayid(ebay.getId());
                TaskGetMessages.setEbayname(ebay.getEbayName());
                iTaskGetMessages.saveListTaskGetMessages(TaskGetMessages);
            }
        }catch (Exception e){
            logger.error("/定时每天插入账号去获取消息出错:"+e.getMessage(),e);
        }

    }
    @Override
    public void run(){
        UsercontrollerEbayAccountMapper ueam = (UsercontrollerEbayAccountMapper) ApplicationContextUtil.getBean(UsercontrollerEbayAccountMapper.class);
        UsercontrollerEbayAccountExample ueame = new UsercontrollerEbayAccountExample();
        ueame.createCriteria().andEbayStatusEqualTo("1");
        List<UsercontrollerEbayAccount> liue = ueam.selectByExampleWithBLOBs(ueame);
        Date date=new Date();
        saveTaskGetMessages(liue,date);
    }

    public SynchronizeGetMessagesTaskRun(){

    }

    @Override
    public String getScheduledType() {
        return MainTask.SYNCHRONIZE_GET_MESSAGES;
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
