package com.base.utils.threadpoolimplements;

import com.base.database.trading.model.TradingGetUserCases;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.sampleapixml.BindAccountAPI;
import com.base.sampleapixml.UserCasesAPI;
import com.base.utils.common.DateUtils;
import com.base.utils.threadpool.AddApiTask;
import com.base.utils.threadpool.TaskMessageVO;
import com.base.utils.xmlutils.SamplePaseXml;
import com.sitemessage.service.SiteMessageStatic;
import com.trading.service.ITradingGetUserCases;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/9/13.
 */
@Service
public class SynchronizeUserCasesImpl implements ThreadPoolBaseInterFace {
    static Logger logger = Logger.getLogger(SynchronizeUserCasesImpl.class);
    @Autowired
    private ITradingGetUserCases iTradingGetUserCases;
    @Override
    public <T> void doWork(String res, T... t) {
          if(StringUtils.isEmpty(res)){return;}
        TaskMessageVO taskMessageVO = (TaskMessageVO)t[0];
        String ack = null;
        try {
            ack = SamplePaseXml.getVFromXmlString(res, "ack");
            if ("Success".equalsIgnoreCase(ack)) {
                Map<String,Object> map1= UserCasesAPI.parseXMLAndSave(res);
                String totalPages= (String) map1.get("totalPages");
                int totalPage= Integer.parseInt(totalPages);
                for(int i=1;i<=totalPage;i++) {
                    if (i != 1) {
                        Map map=new HashMap();
                      /*  Date startTime2= DateUtils.subDays(new Date(), 60);
                        Date endTime= DateUtils.addDays(startTime2, 60);*/
                        Date startTime2= DateUtils.subDays(new Date(), 9);
                        Date endTime= DateUtils.addDays(startTime2,9);
                        Date end1= com.base.utils.common.DateUtils.turnToDateEnd(endTime);
                        String start= DateUtils.DateToString(startTime2);
                        String end=DateUtils.DateToString(end1);
                        map.put("fromTime", start);
                        map.put("toTime", end);
                        map.put("page", i + "");
                        String xml = BindAccountAPI.getUserCases(map);
                        AddApiTask addApiTask = new AddApiTask();
                        UsercontrollerDevAccountExtend d=(UsercontrollerDevAccountExtend)taskMessageVO.getObjClass();
                        Map<String, String> resMap = addApiTask.exec(d, xml, "https://svcs.ebay.com/services/resolution/ResolutionCaseManagementService/v1?REST-PAYLOAD");
                        String r1 = resMap.get("stat");
                        res = resMap.get("message");
                        if ("fail".equalsIgnoreCase(r1)) {
                            return;
                        }
                        if (!"Success".equalsIgnoreCase(ack)) {
                            String errors = SamplePaseXml.getVFromXmlString(res, "Errors");
                            logger.error("Order获取apisessionid失败!" + errors);
                            return;
                        }
                        map1 = UserCasesAPI.parseXMLAndSave(res);
                    }
                    List<TradingGetUserCases> userCasesList = (List<TradingGetUserCases>) map1.get("cases");
                    for (TradingGetUserCases userCases : userCasesList) {
                        List<TradingGetUserCases> casesList = iTradingGetUserCases.selectGetUserCasesByTransactionId(userCases.getTransactionid());
                        if (casesList != null && casesList.size() > 0) {
                            userCases.setId(casesList.get(0).getId());
                            userCases.setHandled(casesList.get(0).getHandled());
                        } else {
                            userCases.setHandled(0);
                        }
                        userCases.setCreateUser(taskMessageVO.getMessageTo());
                        iTradingGetUserCases.saveGetUserCases(userCases);
                    }
                }
            }else {return;}
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("解析xml出错,请稍后到ebay网站确认结果");
            return;
        }
    }

    @Override
    public String getType() {
        return SiteMessageStatic.SYNCHRONIZE_USER_CASE_BEAN;
    }
}
