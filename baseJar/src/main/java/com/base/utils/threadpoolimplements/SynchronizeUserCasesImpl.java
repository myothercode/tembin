package com.base.utils.threadpoolimplements;

import com.base.database.trading.model.TradingGetUserCases;
import com.base.sampleapixml.UserCasesAPI;
import com.base.utils.threadpool.TaskMessageVO;
import com.base.utils.xmlutils.SamplePaseXml;
import com.sitemessage.service.SiteMessageStatic;
import com.trading.service.ITradingGetUserCases;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
                List<TradingGetUserCases> userCasesList= (List<TradingGetUserCases>) map1.get("cases");
                for(TradingGetUserCases userCases:userCasesList){
                    List<TradingGetUserCases> casesList=iTradingGetUserCases.selectGetUserCasesByTransactionId(userCases.getTransactionid());
                    if(casesList!=null&&casesList.size()>0){
                        userCases.setId(casesList.get(0).getId());
                    }
                    userCases.setCreateUser(taskMessageVO.getMessageTo());
                    iTradingGetUserCases.saveGetUserCases(userCases);
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
