package com.base.utils.threadpoolimplements;

import com.base.database.trading.model.TradingGetDispute;
import com.base.database.trading.model.TradingGetDisputeMessage;
import com.base.sampleapixml.GetDisputeAPI;
import com.base.utils.threadpool.TaskMessageVO;
import com.base.utils.xmlutils.SamplePaseXml;
import com.sitemessage.service.SiteMessageStatic;
import com.trading.service.ITradingGetDispute;
import com.trading.service.ITradingGetDisputeMessage;
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
public class SynchronizeUserCasesDisputeImpl implements ThreadPoolBaseInterFace {
    static Logger logger = Logger.getLogger(SynchronizeUserCasesDisputeImpl.class);
    @Autowired
    private ITradingGetDispute iTradingGetDispute;
    @Autowired
    private ITradingGetDisputeMessage iTradingGetDisputeMessage;
    @Override
    public <T> void doWork(String res, T... t) {

        if(StringUtils.isEmpty(res)){return;}
        TaskMessageVO taskMessageVO = (TaskMessageVO)t[0];
        String ack = null;
        try {
            ack =  SamplePaseXml.getVFromXmlString(res, "Ack");
            if ("Success".equalsIgnoreCase(ack)) {
                Map<String,Object> disputeMap= GetDisputeAPI.parseXMLAndSave(res);
                TradingGetDispute dispute= (TradingGetDispute) disputeMap.get("dispute");
                List<TradingGetDispute> disputeList=iTradingGetDispute.selectGetDisputeByTransactionId(dispute.getTransactionid());
                if(disputeList!=null&&disputeList.size()>0){
                    dispute.setId(disputeList.get(0).getId());
                }
                dispute.setCreateUser(taskMessageVO.getMessageTo());
                iTradingGetDispute.saveGetDispute(dispute);
                List<TradingGetDisputeMessage> messageList= (List<TradingGetDisputeMessage>) disputeMap.get("disputeMessage");
                List<TradingGetDisputeMessage> list=iTradingGetDisputeMessage.selectGetDisputeMessageByDisputeId(dispute.getId());
                if(list!=null&&list.size()>0){
                    for(TradingGetDisputeMessage message:list){
                        iTradingGetDisputeMessage.deleteGetDisputeMessage(message);
                    }
                }
                for(TradingGetDisputeMessage message:messageList){
                    message.setDisputeId(dispute.getId());
                    message.setCreateUser(taskMessageVO.getMessageTo());
                    iTradingGetDisputeMessage.saveGetDisputeMessage(message);
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
        return SiteMessageStatic.SYNCHRONIZE_USER_CASE_DISPUTE_BEAN;
    }
}
