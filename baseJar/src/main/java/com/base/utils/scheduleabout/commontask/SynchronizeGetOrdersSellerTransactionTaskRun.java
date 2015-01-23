package com.base.utils.scheduleabout.commontask;

import com.base.database.task.model.TaskGetOrdersSellerTransaction;
import com.base.database.trading.mapper.UsercontrollerEbayAccountMapper;
import com.base.database.trading.model.UsercontrollerEbayAccount;
import com.base.database.trading.model.UsercontrollerEbayAccountExample;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.scheduleabout.BaseScheduledClass;
import com.base.utils.scheduleabout.MainTask;
import com.base.utils.scheduleabout.Scheduledable;
import com.task.service.ITaskGetOrdersSellerTransaction;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrtor on 2014/8/29.
 * 获取订单外部交易，每日任务
 */
public class SynchronizeGetOrdersSellerTransactionTaskRun extends BaseScheduledClass implements Scheduledable {
    static Logger logger = Logger.getLogger(SynchronizeGetOrdersSellerTransactionTaskRun.class);

    public void saveTaskGetOrdersSellerTransaction(List<UsercontrollerEbayAccount> ebays,Date date){
        ITaskGetOrdersSellerTransaction iTaskGetOrdersSellerTransaction = (ITaskGetOrdersSellerTransaction) ApplicationContextUtil.getBean(ITaskGetOrdersSellerTransaction.class);
        try{
            for(UsercontrollerEbayAccount ebay:ebays){
                TaskGetOrdersSellerTransaction taskGetOrdersSellerTransaction=new TaskGetOrdersSellerTransaction();
                taskGetOrdersSellerTransaction.setToken(ebay.getEbayToken());
                taskGetOrdersSellerTransaction.setTokenflag(0);
                taskGetOrdersSellerTransaction.setSavetime(date);
                taskGetOrdersSellerTransaction.setUserid(ebay.getUserId());
                iTaskGetOrdersSellerTransaction.saveTaskGetOrdersSellerTransaction(taskGetOrdersSellerTransaction);
            }
        }catch (Exception e){
            logger.error("定时每天插入账号去获取外部交易出错:" + e.getMessage(), e);
        }

    }
    @Override
    public void run(){
        UsercontrollerEbayAccountMapper ueam=(UsercontrollerEbayAccountMapper) ApplicationContextUtil.getBean(UsercontrollerEbayAccountMapper.class);
        UsercontrollerEbayAccountExample ueame = new UsercontrollerEbayAccountExample();
        ueame.createCriteria().andEbayStatusEqualTo("1");
        List<UsercontrollerEbayAccount> liue = ueam.selectByExampleWithBLOBs(ueame);
        Date date=new Date();
        saveTaskGetOrdersSellerTransaction(liue, date);
    }

    public SynchronizeGetOrdersSellerTransactionTaskRun(){

    }

    @Override
    public String getScheduledType() {
        return MainTask.SYNCHRONIZE_GET_ORDERS_SELLER_TRANSACTION;
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
