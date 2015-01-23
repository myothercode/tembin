package com.base.utils.scheduleabout.commontask;

import com.base.database.keymove.model.KeyMoveList;
import com.base.database.publicd.model.PublicUserConfig;
import com.base.database.trading.mapper.TradingFeedBackDetailMapper;
import com.base.database.trading.mapper.TradingOrderGetOrdersMapper;
import com.base.database.trading.mapper.UsercontrollerEbayAccountMapper;
import com.base.database.trading.model.*;
import com.base.database.userinfo.model.SystemLog;
import com.base.domains.SessionVO;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.domains.userinfo.UsercontrollerEbayAccountExtend;
import com.base.domains.userinfo.UsercontrollerUserExtend;
import com.base.sampleapixml.APINameStatic;
import com.base.sampleapixml.BindAccountAPI;
import com.base.userinfo.mapper.SystemUserManagerServiceMapper;
import com.base.userinfo.service.SystemUserManagerService;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.cache.DataDictionarySupport;
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
import com.orderassess.service.IAutoAssessDetail;
import com.orderassess.service.IOrderAutoAssess;
import com.publicd.service.IPublicUserConfig;
import com.sitemessage.service.SiteMessageService;
import com.sitemessage.service.SiteMessageStatic;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import java.util.*;

/**
 * Created by Administrtor on 2014/8/29.
 * 卖家发给买家的评价定时
 */
public class AutoAssessTaskRun extends BaseScheduledClass implements Scheduledable {
    static Logger logger = Logger.getLogger(AutoAssessTaskRun.class);
    @Override
    public void run() {
        String isRunging = TempStoreDataSupport.pullData("task_"+getScheduledType());
        if(StringUtils.isNotEmpty(isRunging)){return;}
        TempStoreDataSupport.pushData("task_" + getScheduledType(), "x");
        CommAutowiredClass commPars = (CommAutowiredClass) ApplicationContextUtil.getBean(CommAutowiredClass.class);//获取注入的参数
        IAutoAssessDetail iAutoAssessDetail = (IAutoAssessDetail) ApplicationContextUtil.getBean(IAutoAssessDetail.class);
        UsercontrollerEbayAccountMapper ueam = (UsercontrollerEbayAccountMapper) ApplicationContextUtil.getBean(UsercontrollerEbayAccountMapper.class);
        SystemUserManagerServiceMapper systemUserManagerServiceMapper = (SystemUserManagerServiceMapper) ApplicationContextUtil.getBean(SystemUserManagerServiceMapper.class);
        SystemUserManagerService systemUserManagerService = (SystemUserManagerService) ApplicationContextUtil.getBean(SystemUserManagerService.class);
        UsercontrollerDevAccountExtend d = new UsercontrollerDevAccountExtend();
        d.setApiSiteid("0");
        d.setApiCallName(APINameStatic.CompleteSale);
        AddApiTask addApiTask = new AddApiTask();
        try{
            //查询需要发送评价列表
            List<AutoAssessDetailWithBLOBs> liaad = iAutoAssessDetail.selectByListIsFlag("0");
            if(liaad!=null&&liaad.size()>0){
                for(AutoAssessDetailWithBLOBs aad:liaad){
                    Map<String, String> resMap = addApiTask.exec2(d, aad.getPostXml(), commPars.apiUrl);
                    String r1 = resMap.get("stat");
                    String res = resMap.get("message");
                    String ack = "";
                    try {
                        ack = SamplePaseXml.getVFromXmlString(res, "Ack");
                    } catch (Exception e) {
                        logger.error("定时AutoAssess解析xml出错:"+e.getMessage(),e);
                    }
                    if ("Success".equalsIgnoreCase(ack)) {
                        aad.setIsFlag("1");
                    }else {
                        SystemLog sl = new SystemLog();
                        sl.setCreatedate(new Date());
                        try {
                            sl.setEventdesc(aad.getSendUsername()+"发送给："+aad.getTargetUserid()+"评价；发送失败，原因如下："+SamplePaseXml.getSpecifyElementTextAllInOne(res,"Errors","LongMessage"));
                        } catch (Exception e) {
                            logger.error("定时AutoAssess解析出错:"+e.getMessage(),e);
                        }
                        sl.setEventname(SystemLogUtils.AUTO_ASSESS);
                        sl.setOperuser(aad.getSendUsername()+"");
                        try {
                            SystemLogUtils.saveLog(sl);
                        } catch (Exception e) {
                            logger.error("定时AutoAssess日志保存出错:"+e.getMessage(),e);
                        }
                        //用户已经发过了，也会报错误信息
                        aad.setIsFlag("1");
                    }
                    iAutoAssessDetail.saveAutoAssessDetail(aad);
                }
            }
        }catch(Exception e){
            TempStoreDataSupport.removeData("task_" + getScheduledType());
            logger.error("发送自动评价报错：",e);
        }
        TempStoreDataSupport.removeData("task_" + getScheduledType());
    }

    /**只从集合记录取多少条*/
    private List<KeyMoveList> filterLimitList(List<KeyMoveList> tlist){
        return filterLimitListFinal(tlist,20);
        /*List<KeyMoveList> x=new ArrayList<KeyMoveList>();
        for (int i = 0;i<20;i++){
            x.add(tlist.get(i));
        }
        return x;*/
    }

    public AutoAssessTaskRun(){
    }

    @Override
    public String getScheduledType() {
        return MainTask.AUTO_ASSESS;
    }

    @Override
    public Integer crTimeMinu() {
        return 5;
    }

    @Override
    public void setMark(String x) {

    }

    @Override
    public String getMark() {
        return null;
    }
}
