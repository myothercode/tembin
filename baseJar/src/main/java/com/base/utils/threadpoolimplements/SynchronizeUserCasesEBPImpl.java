package com.base.utils.threadpoolimplements;

import com.base.database.trading.model.TradingCasePaymentDetail;
import com.base.database.trading.model.TradingCaseResponseHistory;
import com.base.database.trading.model.TradingGetEBPCaseDetail;
import com.base.sampleapixml.GetEBPCaseDetailAPI;
import com.base.utils.threadpool.TaskMessageVO;
import com.base.utils.xmlutils.SamplePaseXml;
import com.sitemessage.service.SiteMessageStatic;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/9/13.
 */
public class SynchronizeUserCasesEBPImpl implements ThreadPoolBaseInterFace {
    static Logger logger = Logger.getLogger(SynchronizeUserCasesEBPImpl.class);
    @Override
    public <T> void doWork(String ebpRes, T... t) {
        if(StringUtils.isEmpty(ebpRes)){return;}
        TaskMessageVO taskMessageVO = (TaskMessageVO)t[0];
        String ebpAck = null;
        try {
            ebpAck = SamplePaseXml.getVFromXmlString(ebpRes, "ack");
            if ("Success".equalsIgnoreCase(ebpAck)) {
                Map<String,Object> ebpmap= GetEBPCaseDetailAPI.parseXMLAndSave(ebpRes);
                TradingGetEBPCaseDetail caseDetail= (TradingGetEBPCaseDetail) ebpmap.get("ebpCaseDetail");
                List<TradingCaseResponseHistory> historyList= (List<TradingCaseResponseHistory>) ebpmap.get("historyList");
                List<TradingCasePaymentDetail> paymentDetailList= (List<TradingCasePaymentDetail>) ebpmap.get("paymentDetailList");
            }else {return;}
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("解析xml出错,请稍后到ebay网站确认结果");
            return;
        }
    }

    @Override
    public String getType() {
        return SiteMessageStatic.SYNCHRONIZE_USER_CASE_EBP_BEAN;
    }
}
