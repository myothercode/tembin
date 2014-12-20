package com.base.utils.scheduleabout.commontask;

import com.base.database.task.model.TaskFeedBack;
import com.base.database.trading.mapper.UsercontrollerEbayAccountMapper;
import com.base.database.trading.model.UsercontrollerEbayAccount;
import com.base.database.trading.model.UsercontrollerEbayAccountExample;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.scheduleabout.BaseScheduledClass;
import com.base.utils.scheduleabout.MainTask;
import com.base.utils.scheduleabout.Scheduledable;
import com.task.service.ITaskFeedBack;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrtor on 2014/8/29.
 * 在线商品每晚执行，定时任务
 */
public class SynchronizeFeedBackTaskRun extends BaseScheduledClass implements Scheduledable {
    static Logger logger = Logger.getLogger(SynchronizeFeedBackTaskRun.class);

    public void saveTaskFeedBack(List<UsercontrollerEbayAccount> ebays,Date date,String commentType){
        ITaskFeedBack iTaskFeedBack = (ITaskFeedBack) ApplicationContextUtil.getBean(ITaskFeedBack.class);
        try{
            for(UsercontrollerEbayAccount ebay:ebays){
                TaskFeedBack taskFeedBack=new TaskFeedBack();
                taskFeedBack.setSavetime(date);
                taskFeedBack.setTokenflag(0);
                taskFeedBack.setToken(ebay.getEbayToken());
                taskFeedBack.setUserid(ebay.getUserId());
                taskFeedBack.setEbayname(ebay.getEbayAccount());
                taskFeedBack.setCommenttype(commentType);
                iTaskFeedBack.saveListTaskFeedBack(taskFeedBack);
            }
        }catch (Exception e){
            logger.error("定时每天插入账号去获取评价出错:"+e.getMessage(),e);
        }

    }
    @Override
    public void run(){
        UsercontrollerEbayAccountMapper ueam = (UsercontrollerEbayAccountMapper) ApplicationContextUtil.getBean(UsercontrollerEbayAccountMapper.class);
        UsercontrollerEbayAccountExample ueame = new UsercontrollerEbayAccountExample();
        ueame.createCriteria().andEbayStatusEqualTo("1");
        List<UsercontrollerEbayAccount> liue = ueam.selectByExampleWithBLOBs(ueame);
        Date date=new Date();
        saveTaskFeedBack(liue,date,"Positive");
        saveTaskFeedBack(liue,date,"Neutral");
        saveTaskFeedBack(liue,date,"Negative");
    }
    public SynchronizeFeedBackTaskRun(){

    }

    @Override
    public String getScheduledType() {
        return MainTask.SYNCHRONIZE_FEED_BACK;
    }

    @Override
    public Integer crTimeMinu() {
        return null;
    }
}
