package com.base.sampleapixml;

import com.base.database.trading.model.TradingMessageAddmembermessage;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

/**
 * Created by Administrtor on 2014/8/7.
 * 一些简单的api直接拼接字符串
 */
public class BindAccountAPI {
    /**获取ebay时间*/
    public static String getGeteBayOfficialTime(String eBayAuthToken){
        //AgAAAA**AQAAAA**aAAAAA**QM20Uw**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6wFk4GhCpGGoA+dj6x9nY+seQ**Sd0CAA**AAMAAA**a8K8fmx7s3vurAo/SGFiTl1PTp5pI/+JwbMuDqmBGpz/3++lJUPnHvIVgzjMBQtP89Qx/RDRY0s2Y10elagAew81uMb4EkWRIZIYw7YZdpp+ExQ5BUNnopAPRXeWz9ODtU2ujdk7m1y+wGLeNS8iFwP3bmHjcGyMLHDWvKkyBsky/y9kbpTmMbcry3SlUVykFG5cYeQFgpEjqJohfH6mX2T7NcD0L0eVWzrU4/Wh7NFpmGfxsgzN1e3FA89sLG6HZfJhSg+SBRT+dR29BAf2A9oVI6+yIctnfGPnHL68UrGzmgh+EgUf8aQW8n17zLEZEatUjpwlrAoRIH/CbG+gVZkSkGQyxI/WoqWtwAZKyAApvOSqGNYVRwef61GHMmAyf7eXojMBP3na1wMMAHpde6APji+3QiDlGT49T6wzcUA8TPRTTIQCYuqsEBY0tAjVrTEwcbsPUW9/533q8MNsVywH99VDme1fsKKLK4v93mZg9JzAMbdeNIfrtcH8CVQYQzv4n+xGNvchUD8pwGtZ98RxGk/8dZr0pEMZpcM70YNGLAtzbNEsPvfPdQfDUV6bgsHRBzySa+jAeCDesslrC3fanWJFFa/7YjLnNqdcVpWsC0V/uRWbdzOJ9mo2+sJelL5mPCgWS+YdFHYgdxYRnJCb/VBkkrm7IuSHUuBXWVdlaqs5Miu0fWIj0CZ/KYjBZK7XZyAN7LP1spAiFQJ2vo8/UCqcoay4ftMT68QgNcAyucVEr8gAF7k7RFDFEYSf
        String x="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<GeteBayOfficialTimeRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">" +
                "<RequesterCredentials>" +
                "<eBayAuthToken>"+eBayAuthToken+"</eBayAuthToken>" +
                "</RequesterCredentials>" +
                "</GeteBayOfficialTimeRequest>​";
        return x;
    }

    /**获取sessionid*/
    public static String getSessionID(String runName){
      String xml="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
        "<GetSessionIDRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">" +
        "<RuName>"+runName+"</RuName>" +
        "</GetSessionIDRequest>​";
        return xml;
    }

    public static String getFetchToken(String sessionID){
        String xml="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<FetchTokenRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">" +
                "  <SessionID>"+sessionID+"</SessionID>" +
                "</FetchTokenRequest>​";
        return xml;
    }
    public static String getGetMyMessages(Map map){
        String xml="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<GetMyMessagesRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">" +
                "<RequesterCredentials>" +
                "<eBayAuthToken>"+map.get("token")+"</eBayAuthToken>" +
                "</RequesterCredentials>" +
                "<DetailLevel EnumType=\"DetailLevelCodeType\">"+map.get("detail")+"</DetailLevel>" +
                "<StartTime>"+map.get("startTime")+"</StartTime>" +
                "<EndTime>"+map.get("endTime")+"</EndTime>" +
                "</GetMyMessagesRequest>​​";
        return xml;
    }
    public static String getAddMemberMessageRTQ(TradingMessageAddmembermessage addmessage,String token){
        String xml="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<AddMemberMessageRTQRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">" +
                "<RequesterCredentials>" +
                "<eBayAuthToken>"+token+"</eBayAuthToken>" +
                "</RequesterCredentials>" +
                "<MemberMessage ComplexType=\"MemberMessageType\">" +
                "<EmailCopyToSender>"+addmessage.getEmailcopytosender()+"</EmailCopyToSender>" +
                "<DisplayToPublic>"+addmessage.getDisplaytopublic()+"</DisplayToPublic>" +
                "<RecipientID>"+addmessage.getRecipientid()+"</RecipientID>" +
                "<Body>"+addmessage.getBody()+"</Body>" +
                "<ParentMessageID>"+addmessage.getParentmessageid()+"</ParentMessageID>" +
                "</MemberMessage>" +
                "<ItemID>"+addmessage.getItemid()+"</ItemID>" +
                "<MessageID>"+addmessage.getMessageid()+"</MessageID>" +
                /*"<Version>879</Version>" +*/
                "</AddMemberMessageRTQRequest>​";
        return xml;
    }
    public static String getGetMyMessagesByReturnHeader(String messageID,String token){
        String xml="<?xml version=\"1.0\" encoding=\"utf-8\"?> " +
                "<GetMyMessagesRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\"> " +
                "<Version>467</Version> " +
                "<DetailLevel>ReturnMessages</DetailLevel>" +
                "<RequesterCredentials>" +
                "<eBayAuthToken>"+token+"</eBayAuthToken>" +
                "</RequesterCredentials>" +
                "<MessageIDs>" +
                "<MessageID>"+messageID+"</MessageID>" +
                "</MessageIDs>" +
                "</GetMyMessagesRequest> ";
        return xml;
    }
    public static String getUserCases(Map map){
        String xml="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<getUserCasesRequest xmlns:ser=\"http://www.ebay.com/marketplace/resolution/v1/services\" xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\">" +
                "<creationDateRangeFilter>" +
                "<fromDate>"+map.get("fromTime")+"</fromDate>" +
                "<toDate>"+map.get("toTime")+"</toDate>" +
                "</creationDateRangeFilter>" +
               /* "<paginationInput>" +
                "<pageNumber>"+map.get("page")+"</pageNumber>" +
                "<entriesPerPage>100</entriesPerPage>" +
                "</paginationInput>" +*/
                "<paginationInput>" +
                "    <pageNumber>2</pageNumber>" +
                "    <entriesPerPage>40</entriesPerPage>" +
                "  </paginationInput>"+
                "</getUserCasesRequest>";
              return xml;
    }
    public static String getGetOrders(Map<String,String> map){
        String xml="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<GetOrdersRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">" +
                "  <RequesterCredentials>" +
                "    <eBayAuthToken>"+map.get("token")+"</eBayAuthToken>" +
                "  </RequesterCredentials>" +
                "  <CreateTimeFrom>"+map.get("fromTime")+"</CreateTimeFrom>" +
                "  <CreateTimeTo>"+map.get("toTime")+"</CreateTimeTo>" +
                "  <OrderRole>Seller</OrderRole>" +
                "<Pagination>" +
                "<EntriesPerPage>100</EntriesPerPage>" +
                "<PageNumber>"+map.get("page")+"</PageNumber>" +
                "</Pagination>"+
                "</GetOrdersRequest>";
        return xml;
    }
    public static String getGetOrderItem(Map<String,String> map){
        String xml="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<GetItemRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">" +
                "  <RequesterCredentials>" +
                "    <eBayAuthToken>"+map.get("token")+"</eBayAuthToken>" +
                "  </RequesterCredentials>" +
                "  <ItemID>"+map.get("Itemid")+"</ItemID>" +
                "  <IncludeItemSpecifics>true</IncludeItemSpecifics>" +
                "</GetItemRequest>";
        return xml;
    }
    public static  String getAddMemberMessageAAQToPartner(Map<String, String> map){
        String xml="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<AddMemberMessageAAQToPartnerRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">" +
                "<RequesterCredentials>" +
                "<eBayAuthToken>"+map.get("token")+"</eBayAuthToken>" +
                "</RequesterCredentials>" +
                "<ItemID>"+map.get("itemid")+"</ItemID>" +
                "<MemberMessage>" +
                "<Subject>"+map.get("subject")+"</Subject>" +
                "<Body>"+map.get("body")+"</Body>" +
                "<QuestionType>General</QuestionType>" +
                "<RecipientID>"+map.get("buyeruserid")+"</RecipientID>" +
                "</MemberMessage>" +
                "</AddMemberMessageAAQToPartnerRequest>​";
        return xml;
    }
    public static  String GetSellerTransactions(String token){
        String xml="​<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<GetSellerTransactionsRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">" +
                "<RequesterCredentials>" +
                "<eBayAuthToken>"+token+"</eBayAuthToken>" +
                "</RequesterCredentials>" +
                "<Pagination>" +
                "<EntriesPerPage>100</EntriesPerPage>" +
                "<PageNumber>1</PageNumber>" +
                "</Pagination>" +
                "<IncludeFinalValueFee>true</IncludeFinalValueFee>" +
                "<IncludeContainingOrder>true</IncludeContainingOrder>" +
                "<Platform>eBay</Platform>" +
                "<NumberOfDays>7</NumberOfDays>" +
                "<InventoryTrackingMethod>ItemID</InventoryTrackingMethod>" +
                "<IncludeCodiceFiscale>true</IncludeCodiceFiscale>" +
                "<DetailLevel>ReturnAll</DetailLevel>" +
                "<Version>883</Version>" +
                "</GetSellerTransactionsRequest>";
        return xml;
    }
    /*
     *getAccount
     */
    public static  String getGetAccount(Map map){
        String xml="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<GetAccountRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">" +
                "<RequesterCredentials>" +
                "<eBayAuthToken>"+map.get("token")+"</eBayAuthToken>" +
                "</RequesterCredentials>" +
                "<Pagination>" +
                "<EntriesPerPage>100</EntriesPerPage>" +
                "<PageNumber>1</PageNumber>" +
                "</Pagination>" +
                "<BeginDate>"+map.get("fromTime")+"</BeginDate>" +
                "<EndDate>"+map.get("toTime")+"</EndDate>" +
                "<ItemID>"+map.get("Itemid")+"</ItemID>" +
              /*  "<Version>881</Version>" +*/
                "</GetAccountRequest>";
        return xml;
    }
    /*
    *评价
    */
    public static  String getEvaluate(Map map){
        String xml="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<CompleteSaleRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">" +
                "  <RequesterCredentials>" +
                "    <eBayAuthToken>"+map.get("token")+"</eBayAuthToken>" +
                "  </RequesterCredentials>" +
                "  <WarningLevel>High</WarningLevel>" +
                "  <FeedbackInfo>" +
                "    <CommentText>"+map.get("CommentText")+"</CommentText>" +
                "    <CommentType>Positive</CommentType>" +
                "    <TargetUser>"+map.get("buyeruserid")+"</TargetUser>" +
                "  </FeedbackInfo>" +
                "  <ItemID>"+map.get("itemid")+"</ItemID>" +
                "  <Shipment>" +
                "    <Notes>Shipped USPS Media</Notes>" +
                "  </Shipment>" +
                "  <Shipped>true</Shipped>" +
                "  <TransactionID>"+map.get("transactionid")+"</TransactionID>" +
                "</CompleteSaleRequest>";
        return xml;
    }

    /*
     * 修改订单发货状态
     */
    public static  String getModifyOrderStatus(Map<String,String> map){
        String xml1="";
        if(StringUtils.isNotBlank(map.get("ShipmentTrackingNumber"))&&StringUtils.isNotBlank(map.get("ShippingCarrierUsed"))){
            xml1= "<Shipment>" +
                    "<ShipmentTrackingDetails>" +
                    "<ShipmentTrackingNumber>"+map.get("ShipmentTrackingNumber")+"</ShipmentTrackingNumber>"+
                    "<ShippingCarrierUsed>"+map.get("ShippingCarrierUsed")+"</ShippingCarrierUsed>" +
                    "</ShipmentTrackingDetails>" +
                    "</Shipment>";
        }
        String xml="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<CompleteSaleRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">" +
                "<RequesterCredentials>" +
                "<eBayAuthToken>"+map.get("token")+"</eBayAuthToken>" +
                "</RequesterCredentials>" +
                "<TransactionID>"+map.get("transactionid")+"</TransactionID>" +
                "<ItemID>"+map.get("itemid")+"</ItemID>" +xml1+
                "<Shipped>"+map.get("shippStatus")+"</Shipped>" +
                "</CompleteSaleRequest>​";
        return xml;
    }
    /*
   *获取EBP纠纷
   */
    public static  String getEBPCase(Map map){
        String xml="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<getEBPCaseDetailRequest xmlns:soap=\"http://www.ebay.com/marketplace/resolution/v1/services\">" +
                /*"<RequesterCredentials>" +
                "<eBayAuthToken>"+map.get("token")+"</eBayAuthToken>" +
                "</RequesterCredentials>" +*/
                "<caseId>" +
                "<id>"+map.get("caseId")+"</id>" +
                "<type>"+map.get("caseType")+"</type>" +
                "</caseId>" +
                "</getEBPCaseDetailRequest>";
        return xml;
    }
    /*
   *获取一般纠纷
   */
    public static  String getGetDispute(String token,String caseId){
        String xml="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<GetDisputeRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">" +
                "  <RequesterCredentials>" +
                "    <eBayAuthToken>"+token+"</eBayAuthToken>" +
                "  </RequesterCredentials>" +
                "  <DisputeID>"+caseId+"</DisputeID>" +
                "</GetDisputeRequest>";
        return xml;
    }
}
