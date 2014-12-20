package com.base.utils.threadpoolimplements;

import com.base.database.trading.model.TradingCasePaymentDetail;
import com.base.database.trading.model.TradingCaseResponseHistory;
import com.base.database.trading.model.TradingGetEBPCaseDetail;
import com.base.sampleapixml.GetEBPCaseDetailAPI;
import com.base.utils.threadpool.TaskMessageVO;
import com.base.utils.xmlutils.SamplePaseXml;
import com.sitemessage.service.SiteMessageStatic;
import com.trading.service.ITradingCasePaymentDetail;
import com.trading.service.ITradingCaseResponseHistory;
import com.trading.service.ITradingGetEBPCaseDetail;
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
public class SynchronizeUserCasesEBPImpl implements ThreadPoolBaseInterFace {
    static Logger logger = Logger.getLogger(SynchronizeUserCasesEBPImpl.class);
    @Autowired
    private ITradingGetEBPCaseDetail iTradingGetEBPCaseDetail;
    @Autowired
    private ITradingCaseResponseHistory iTradingCaseResponseHistory;
    @Autowired
    private ITradingCasePaymentDetail iTradingCasePaymentDetail;
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
                List<TradingGetEBPCaseDetail> lists=iTradingGetEBPCaseDetail.selectGetEBPCaseDetailByTransactionId(caseDetail.getTransactionid());
                if(lists!=null&&lists.size()>0){
                    List<TradingCasePaymentDetail> list1=iTradingCasePaymentDetail.selectCasePaymentDetailByEBPId(lists.get(0).getId());
                    List<TradingCaseResponseHistory> list2=iTradingCaseResponseHistory.selectCaseResponseHistoryByEBPId(lists.get(0).getId());
                    for(TradingCaseResponseHistory history:list2){
                        iTradingCaseResponseHistory.deleteCaseResponseHistory(history);
                    }
                    for(TradingCasePaymentDetail detail:list1){
                        iTradingCasePaymentDetail.deleteCasePaymentDetail(detail);
                    }
                }
                caseDetail.setCreateUser(taskMessageVO.getMessageTo());
                iTradingGetEBPCaseDetail.saveGetEBPCaseDetail(caseDetail);
                List<TradingCaseResponseHistory> historyList= (List<TradingCaseResponseHistory>) ebpmap.get("historyList");
                List<TradingCasePaymentDetail> paymentDetailList= (List<TradingCasePaymentDetail>) ebpmap.get("paymentDetailList");
                for(TradingCaseResponseHistory history:historyList){
                    history.setCreateUser(taskMessageVO.getMessageTo());
                    history.setEbpcasedetailId(caseDetail.getId());
                    iTradingCaseResponseHistory.saveCaseResponseHistory(history);
                }
                for(TradingCasePaymentDetail detail:paymentDetailList){
                    detail.setCreateUser(taskMessageVO.getMessageTo());
                    detail.setEbpcasedetailId(caseDetail.getId());
                    iTradingCasePaymentDetail.saveCasePaymentDetail(detail);
                }
            }else {return;}
        } catch (Exception e) {
            logger.error("解析xml出错,请稍后到ebay网站确认结果16"+ebpRes,e);
            return;
        }
    }

    @Override
    public String getType() {
        return SiteMessageStatic.SYNCHRONIZE_USER_CASE_EBP_BEAN;
    }
}
