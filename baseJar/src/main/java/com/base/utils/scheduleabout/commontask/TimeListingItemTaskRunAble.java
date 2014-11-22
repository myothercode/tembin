package com.base.utils.scheduleabout.commontask;

import com.base.database.trading.mapper.TradingTimerListingMapper;
import com.base.database.trading.model.TradingItemWithBLOBs;
import com.base.database.trading.model.TradingTimerListingExample;
import com.base.database.trading.model.TradingTimerListingWithBLOBs;
import com.base.database.trading.model.UsercontrollerEbayAccount;
import com.base.database.userinfo.mapper.UsercontrollerUserMapper;
import com.base.database.userinfo.model.UsercontrollerUser;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.userinfo.service.UserInfoService;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.cache.TempStoreDataSupport;
import com.base.utils.common.CommAutowiredClass;
import com.base.utils.common.ObjectUtils;
import com.base.utils.scheduleabout.BaseScheduledClass;
import com.base.utils.scheduleabout.MainTask;
import com.base.utils.scheduleabout.Scheduledable;
import com.base.utils.threadpool.AddApiTask;
import com.base.utils.xmlutils.SamplePaseXml;
import com.trading.service.ITradingItem;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/8/29.
 * 定时刊登任务
 */
public class TimeListingItemTaskRunAble extends BaseScheduledClass implements Scheduledable {
    static Logger logger = Logger.getLogger(TimeListingItemTaskRunAble.class);
    @Override
    public void run() {





        String isRunging = TempStoreDataSupport.pullData("task_"+getScheduledType());
        if(StringUtils.isNotEmpty(isRunging)){return;}
        TempStoreDataSupport.pushData("task_"+getScheduledType(),"x");
        /*TestService testService=ApplicationContextUtil.getBean(TestService.class);

        Test1Service test1Service=ApplicationContextUtil.getBean(Test1Service.class);
        testService.serviceTest();
        if(true){throw new RuntimeException("d");}
        test1Service.serviceTest();

        if(true){return;}*/

        TradingTimerListingMapper timeMapper = (TradingTimerListingMapper) ApplicationContextUtil.getBean(TradingTimerListingMapper.class);
        TradingTimerListingExample texample=new TradingTimerListingExample();
        texample.createCriteria().andTimerLessThanOrEqualTo(new Date()).andTimerFlagEqualTo("0").andCheckFlagEqualTo("0");
        List<TradingTimerListingWithBLOBs> listingWithBLOBses= timeMapper.selectByExampleWithBLOBs(texample);/**查询出有哪些刊登任务是需要执行的*/
        if(ObjectUtils.isLogicalNull(listingWithBLOBses)){
            TempStoreDataSupport.removeData("task_"+getScheduledType());
            return;
        }

        UserInfoService userInfoService= (UserInfoService) ApplicationContextUtil.getBean(UserInfoService.class);
        UsercontrollerUserMapper user = (UsercontrollerUserMapper) ApplicationContextUtil.getBean(UsercontrollerUserMapper.class);
        CommAutowiredClass commV = (CommAutowiredClass) ApplicationContextUtil.getBean(CommAutowiredClass.class);

        ITradingItem iTradingItem = (ITradingItem) ApplicationContextUtil.getBean(ITradingItem.class);

        if(listingWithBLOBses.size()>20){
            listingWithBLOBses =filterLimitList(listingWithBLOBses);
        }

        for (TradingTimerListingWithBLOBs withBLOBs : listingWithBLOBses){
            TradingTimerListingWithBLOBs t=new TradingTimerListingWithBLOBs();
            TradingTimerListingWithBLOBs t1=new TradingTimerListingWithBLOBs();
            t1.setRunStartTime(new Date());
            t1.setTimerFlag("0");
            t1.setId(withBLOBs.getId());
            timeMapper.updateByPrimaryKeySelective(t1);//设置一个开始时间
            //首先根据ebay帐号id获取到token
            if(StringUtils.isEmpty(withBLOBs.getEbayId())){continue;}
            UsercontrollerEbayAccount ebay=userInfoService.getEbayAccountByEbayID(Long.parseLong(withBLOBs.getEbayId()));
            Long userId = ebay.getUserId();//获取到系统用户id
            if(ObjectUtils.isLogicalNull(userId)){continue;}
            //根据系统id去获取绑定的默认开发者id
            UsercontrollerUser userVo= user.selectByPrimaryKey(userId.intValue());
            Long devId = userVo.getDefaultDevAccount();//获取到开发帐号
            if(ObjectUtils.isLogicalNull(devId)){continue;}
            try {
                UsercontrollerDevAccountExtend devInfo= userInfoService.getDevInfo(devId);
                devInfo.setApiSiteid(withBLOBs.getStateId());
                devInfo.setApiCallName(withBLOBs.getApiMethod());
                AddApiTask addApiTask = new AddApiTask();
                Map<String, String> resMap = addApiTask.exec2(devInfo, withBLOBs.getTimerMessage(), commV.apiUrl);
                String r1 = resMap.get("stat");
                String res = resMap.get("message");
                if ("fail".equalsIgnoreCase(r1)) {
                    logger.error("定时刊登失败!"+withBLOBs.getId());
                    t.setReturnMessage(res);
                    t.setTimerFlag("2");//刊登失败，但是也已经刊登过了
                }else {
                    t.setReturnMessage(res);
                    t.setTimerFlag("1");
                }
//将刊登成功的itemid写入item表
                String ack = SamplePaseXml.getVFromXmlString(res, "Ack");
                if ("Success".equalsIgnoreCase(ack) || "Warning".equalsIgnoreCase(ack)) {
                    String itemId = SamplePaseXml.getVFromXmlString(res, "ItemID");
                    TradingItemWithBLOBs tradingItem=new TradingItemWithBLOBs();
                    tradingItem.setId(withBLOBs.getItem());
                    tradingItem.setItemId(itemId);
                    tradingItem.setIsFlag("Success");
                    iTradingItem.saveTradingItem(tradingItem);
                    iTradingItem.saveListingSuccess(res,itemId);
                }

            } catch (Exception e) {
                logger.error(e.getMessage()+"定时刊登任务报错 id"+withBLOBs.getId(),e);
            }finally {
                t.setId(withBLOBs.getId());
                t.setRunEndTime(new Date());
                timeMapper.updateByPrimaryKeySelective(t);
            }
        }
        TempStoreDataSupport.removeData("task_"+getScheduledType());
    }

    /**只从集合记录取多少条*/
    private List<TradingTimerListingWithBLOBs> filterLimitList(List<TradingTimerListingWithBLOBs> tlist){

       return filterLimitListFinal(tlist,20);

        /*List<TradingTimerListingWithBLOBs> x=new ArrayList<TradingTimerListingWithBLOBs>();
        for (int i = 0;i<20;i++){
            x.add(tlist.get(i));
        }
        return x;*/
    }

    public TimeListingItemTaskRunAble(){
    }

    @Override
    public String getScheduledType() {
        return MainTask.LISTING_SCHEDULE;
    }

    @Override
    public Integer crTimeMinu() {
        return null;
    }
}
