package com.base.sampleapixml;

import com.base.database.trading.model.TradingGetDispute;
import com.base.database.trading.model.TradingGetDisputeMessage;
import com.base.utils.common.DateUtils;
import com.base.utils.xmlutils.SamplePaseXml;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.*;

/**
 * Created by Administrtor on 2014/8/29.
 */
public class GetDisputeAPI {
    public static Map<String,Object> parseXMLAndSave(String res) throws Exception {
        Map<String,Object> map=new HashMap<String, Object>();
        TradingGetDispute dispute=new TradingGetDispute();
        List<TradingGetDisputeMessage> messageList=new ArrayList<TradingGetDisputeMessage>();
        Document document= DocumentHelper.parseText(res);
        Element root=document.getRootElement();
        Element Dispute=root.element("Dispute");
        dispute.setDisputeid(SamplePaseXml.getSpecifyElementText(Dispute,"DisputeID"));
        dispute.setDisputerecordtype(SamplePaseXml.getSpecifyElementText(Dispute, "DisputeRecordType"));
        dispute.setDisputestate(SamplePaseXml.getSpecifyElementText(Dispute, "DisputeState"));
        dispute.setDisputestatus(SamplePaseXml.getSpecifyElementText(Dispute, "DisputeStatus"));
        dispute.setBuyeruserid(SamplePaseXml.getSpecifyElementText(Dispute, "BuyerUserID"));
        dispute.setSelleruserid(SamplePaseXml.getSpecifyElementText(Dispute, "SellerUserID"));
        dispute.setTransactionid(SamplePaseXml.getSpecifyElementText(Dispute, "TransactionID"));
        dispute.setItemid(SamplePaseXml.getSpecifyElementText(Dispute, "Item", "ItemID"));
        String listStartTime=SamplePaseXml.getSpecifyElementText(Dispute,"Item","ListingDetails","StartTime");
        dispute.setListstarttime(DateUtils.returnDate(listStartTime));
        String listEndTime=SamplePaseXml.getSpecifyElementText(Dispute,"Item","ListingDetails","EndTime");
        dispute.setListendtime(DateUtils.returnDate(listEndTime));
        dispute.setQuantity(SamplePaseXml.getSpecifyElementText(Dispute, "Item", "Quantity"));
        dispute.setConvertedcurrentprice(SamplePaseXml.getSpecifyElementText(Dispute, "Item", "SellingStatus", "ConvertedCurrentPrice"));
        dispute.setCurrentprice(SamplePaseXml.getSpecifyElementText(Dispute, "Item", "SellingStatus", "CurrentPrice"));
        dispute.setSite(SamplePaseXml.getSpecifyElementText(Dispute, "Item", "Site"));
        dispute.setTitle(SamplePaseXml.getSpecifyElementText(Dispute, "Item", "Title"));
        dispute.setDisputereason(SamplePaseXml.getSpecifyElementText(Dispute, "DisputeReason"));
        dispute.setDisputeexplanation(SamplePaseXml.getSpecifyElementText(Dispute, "DisputeExplanation"));
        dispute.setDisputecrediteligibility(SamplePaseXml.getSpecifyElementText(Dispute,"DisputeCreditEligibility"));
        String DisputeCreatedTime=SamplePaseXml.getSpecifyElementText(Dispute,"DisputeCreatedTime");
        dispute.setDisputecreatedtime(DateUtils.returnDate(DisputeCreatedTime));
        String DisputeModifiedTime=SamplePaseXml.getSpecifyElementText(Dispute,"DisputeModifiedTime");
        dispute.setDisputemodifiedtime(DateUtils.returnDate(DisputeModifiedTime));
        dispute.setEscalation(SamplePaseXml.getSpecifyElementText(Dispute, "Escalation"));
        dispute.setPurchaseprotection(SamplePaseXml.getSpecifyElementText(Dispute,"PurchaseProtection"));
        Iterator iterator=Dispute.elementIterator("DisputeMessage");
        while(iterator.hasNext()){
            Element message= (Element) iterator.next();
            TradingGetDisputeMessage disputeMessage=new TradingGetDisputeMessage();
            disputeMessage.setMessageid(SamplePaseXml.getSpecifyElementText(message,"MessageID"));
            disputeMessage.setMessagesource(SamplePaseXml.getSpecifyElementText(message,"MessageSource"));
            String MessageCreationTime=SamplePaseXml.getSpecifyElementText(message,"MessageCreationTime");
            disputeMessage.setMessagecreationtime(DateUtils.returnDate(MessageCreationTime));
            disputeMessage.setMessagetext(SamplePaseXml.getSpecifyElementText(message,"MessageText"));
            messageList.add(disputeMessage);
        }
        map.put("dispute",dispute);
        map.put("disputeMessage", messageList);
        return map;
    }
}
