package com.base.utils.scheduleabout.commontask;

import com.base.database.task.model.ListingDataTask;
import com.base.database.task.model.TaskComplement;
import com.base.database.trading.mapper.TradingListingDataMapper;
import com.base.database.trading.model.TradingListingData;
import com.base.database.trading.model.TradingListingDataExample;
import com.base.database.trading.model.TradingListingSuccess;
import com.base.database.userinfo.model.SystemLog;
import com.base.domains.SessionVO;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.sampleapixml.APINameStatic;
import com.base.userinfo.service.UserInfoService;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.cache.TempStoreDataSupport;
import com.base.utils.common.CommAutowiredClass;
import com.base.utils.common.DateUtils;
import com.base.utils.common.SystemLogUtils;
import com.base.utils.scheduleabout.BaseScheduledClass;
import com.base.utils.scheduleabout.MainTask;
import com.base.utils.scheduleabout.Scheduledable;
import com.base.utils.threadpool.AddApiTask;
import com.base.utils.threadpool.TaskMessageVO;
import com.base.utils.xmlutils.SamplePaseXml;
import com.sitemessage.service.SiteMessageService;
import com.sitemessage.service.SiteMessageStatic;
import com.task.service.IListingDataTask;
import com.task.service.ITaskComplement;
import com.trading.service.ITradingListingSuccess;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrtor on 2014/8/29.
 * 自动补数，定时任务
 */
public class AutoComplementTimerTaskRun extends BaseScheduledClass implements Scheduledable {
    static Logger logger = Logger.getLogger(AutoComplementTimerTaskRun.class);

    public String getColXml(String token,String itemid,String quvalue,Double reqPrice){
        String xml="<?xml version=\"1.0\" encoding=\"utf-8\"?><ReviseItemRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">\n" +
                "  <RequesterCredentials>\n" +
                "    <eBayAuthToken>"+token+"</eBayAuthToken>\n" +
                "  </RequesterCredentials>\n" +
                "  <ErrorLanguage>en_US</ErrorLanguage>\n" +
                "  <WarningLevel>High</WarningLevel>\n" +
                "  <Item>\n";
        if(quvalue!=null) {
            xml += "    <Quantity>" + quvalue + "</Quantity>\n";
        }
        if (reqPrice != null&&reqPrice > 0){
            xml += "    <StartPrice>"+reqPrice+"</StartPrice>";
        }
        xml+="    <ItemID>"+itemid+"</ItemID>\n" +
                "  </Item>\n" +
                "</ReviseItemRequest>";
        return xml;
    }

    public String cosPostXml(String colStr,String month) throws Exception {
        UserInfoService userInfoService = (UserInfoService) ApplicationContextUtil.getBean(UserInfoService.class);
        UsercontrollerDevAccountExtend d = userInfoService.getDevByOrder(new HashMap());
        CommAutowiredClass commPars = (CommAutowiredClass) ApplicationContextUtil.getBean(CommAutowiredClass.class);//获取注入的参数
        d.setApiSiteid("0");
        d.setApiCallName(month);
        AddApiTask addApiTask = new AddApiTask();
        Map<String, String> resMap= addApiTask.exec(d, colStr, commPars.apiUrl);
        String res=resMap.get("message");
        String ack = SamplePaseXml.getVFromXmlString(res,"Ack");
        if(ack.equals("Success")||"Warning".equalsIgnoreCase(ack)){
            return res;
        }else{
            return res;
        }

    }
    @Override
    public void run() {
        String isRunging = TempStoreDataSupport.pullData("task_"+getScheduledType());
        if(StringUtils.isNotEmpty(isRunging)){return;}
        TempStoreDataSupport.pushData("task_" + getScheduledType(), "x");
        ITaskComplement iTaskComplement = (ITaskComplement) ApplicationContextUtil.getBean(ITaskComplement.class);
        List<TaskComplement> lildt = iTaskComplement.selectByTaskComplement("0");
        if(lildt.size()>20){
            lildt =filterLimitList(lildt);
        }
        String taskFlag="";
        for(TaskComplement tc:lildt){
            String xml = this.getColXml(tc.getToken(),tc.getItemId(),tc.getRepValue(),tc.getRepPrice());
            String returnString = null;
            try {
                returnString = this.cosPostXml(xml, APINameStatic.ReviseItem);
            } catch (Exception e) {
                logger.error(xml + ":AutoComplementTimerTaskRun:", e);
            }
            String ack = null;
            try {
                ack = SamplePaseXml.getVFromXmlString(returnString, "Ack");
            } catch (Exception e) {
                logger.error(returnString+":AutoComplementTimerTaskRun:",e);
            }
            String doName = "";
            if(tc.getDataType().equals("1")){
                doName="自动补数";
            }else if(tc.getDataType().equals("2")){
                doName="库存补数";
            }
            if("Success".equalsIgnoreCase(ack)||"Warning".equalsIgnoreCase(ack)){//修改数量成功
                tc.setTaskFlag("1");
                iTaskComplement.saveTaskComplement(tc);
                String context="商品号为："+tc.getItemId()+";执行方式："+doName+";自动调整数量：由"+tc.getOldValue()+"调整为："+tc.getRepValue()+";执行成功！";
                try {
                    this.saveSystemLog(context,"AutoComplement",tc.getEbayAccount());
                } catch (Exception e) {
                    logger.error(context+"记录日志报错(补数):AutoComplementTimerTaskRun:",e);
                }
                if(tc.getRepPrice()!=null&&tc.getRepPrice()>0){
                    context="商品号为："+tc.getItemId()+";执行方式：自动调价;自动调整价格：由"+tc.getOldValue()+"调整为："+tc.getRepPrice()+";执行成功！";
                    try {
                        this.saveSystemLog(context,"AutoPriceComplement",tc.getEbayAccount());
                    } catch (Exception e) {
                        logger.error(context+"记录日志报错（调价）:AutoComplementTimerTaskRun:",e);
                    }
                }
            }else{//修改数量失败
                try {
                    String context="商品号为："+tc.getItemId()+";执行方式："+doName+";自动调整数量：由"+tc.getOldValue()+"调整为："+tc.getRepValue()+";执行失败！失败原因如下："+SamplePaseXml.getSpecifyElementTextAllInOne(returnString,"Errors","LongMessage");
                    this.saveSystemLog(context,"AutoComplement",tc.getEbayAccount());
                } catch (Exception e) {
                    logger.error("记录日志报错（补数）:AutoComplementTimerTaskRun:",e);
                }
                if(tc.getRepPrice()!=null&&tc.getRepPrice()>0){
                    try {
                        String context="商品号为："+tc.getItemId()+";执行方式：自动调价;自动调整价格：由"+tc.getOldPrice()+"调整为："+tc.getRepPrice()+";执行失败！失败原因如下："+SamplePaseXml.getSpecifyElementTextAllInOne(returnString,"Errors","LongMessage");
                        this.saveSystemLog(context,"AutoPriceComplement",tc.getEbayAccount());
                    } catch (Exception e) {
                        logger.error("记录日志报错(调价):AutoComplementTimerTaskRun:",e);
                    }
                }
            }
            try {
                Thread.sleep(5000L);
            } catch (InterruptedException e) {
                logger.error("暂停线程出错:AutoComplementTimerTaskRun:",e);
            }
        }
        TempStoreDataSupport.removeData("task_"+getScheduledType());
    }

    public void saveSystemLog(String context,String type,String ebayAccount) throws Exception {
        SystemLog systemLog = new SystemLog();
        systemLog.setCreatedate(new Date());
        systemLog.setOperuser(ebayAccount);
        systemLog.setEventname(type);
        systemLog.setEventdesc(context);
        SystemLogUtils.saveLog(systemLog);
    }
    /**只从集合记录取多少条*/
    private List<TaskComplement> filterLimitList(List<TaskComplement> tlist){

        return filterLimitListFinal(tlist,20);

        /*List<ListingDataTask> x=new ArrayList<ListingDataTask>();
        for (int i = 0;i<2;i++){
            x.add(tlist.get(i));
        }
        return x;*/
    }

    public AutoComplementTimerTaskRun(){

    }

    @Override
    public String getScheduledType() {
        return MainTask.AUTO_COMPLEMENT;
    }

    @Override
    public Integer crTimeMinu() {
        return 2;
    }

    @Override
    public void setMark(String x) {

    }

    @Override
    public String getMark() {
        return null;
    }
}
