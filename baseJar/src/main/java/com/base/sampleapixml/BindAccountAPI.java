package com.base.sampleapixml;

import com.base.database.trading.model.TradingMessageAddmembermessage;
import org.apache.commons.lang.StringUtils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
/**获取token*/
    public static String getFetchToken(String sessionID){
        String xml="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<FetchTokenRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">" +
                "  <SessionID>"+sessionID+"</SessionID>" +
                "</FetchTokenRequest>​";
        return xml;
    }

    /**获取ebay帐号名*/
    public static String getEbayUserInfo(String token){
        String xml="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<GetUserRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">\n" +
                "<RequesterCredentials>\n" +
                "<eBayAuthToken>"+token+"</eBayAuthToken>\n" +
                "</RequesterCredentials>\n" +
                "</GetUserRequest>​";
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
                "<ser:creationDateRangeFilter>" +
                "<ser:fromDate>"+map.get("fromTime")+"</ser:fromDate>" +
                "<ser:toDate>"+map.get("toTime")+"</ser:toDate>" +
                "</ser:creationDateRangeFilter>" +
                "<ser:paginationInput>" +
                "<ser:pageNumber>"+map.get("page")+"</ser:pageNumber>" +
                "<ser:entriesPerPage>100</ser:entriesPerPage>" +
                "</ser:paginationInput>"+
              "</getUserCasesRequest>";
              return xml;
    }
    public static String getGetOrders(Map<String,String> map){
        String xml="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<GetOrdersRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">" +
                "  <RequesterCredentials>" +
                "    <eBayAuthToken>"+map.get("token")+"</eBayAuthToken>" +
                "  </RequesterCredentials>" +
                "  <ModTimeFrom>"+map.get("fromTime")+"</ModTimeFrom>" +
                "  <ModTimeTo>"+map.get("toTime")+"</ModTimeTo>" +
             /*   "<CreateTimeFrom>"+map.get("fromTime")+"</CreateTimeFrom>" +
                "<CreateTimeTo>"+map.get("toTime")+"</CreateTimeTo>"+*/
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
    public static  String GetSellerTransactions(String token,String page,String startime,String endtime){
        String xml="​<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<GetSellerTransactionsRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">" +
                "<RequesterCredentials>" +
                "<eBayAuthToken>"+token+"</eBayAuthToken>" +
                "</RequesterCredentials>" +
                "<Pagination>" +
                "<EntriesPerPage>100</EntriesPerPage>" +
                "<PageNumber>"+page+"</PageNumber>" +
                "</Pagination>" +
              /*  "<IncludeFinalValueFee>true</IncludeFinalValueFee>" +
                "<IncludeContainingOrder>true</IncludeContainingOrder>" +
                "<Platform>eBay</Platform>" +*/
               /* "<NumberOfDays>7</NumberOfDays>" +*/
               /* "<InventoryTrackingMethod>ItemID</InventoryTrackingMethod>" +
                "<IncludeCodiceFiscale>true</IncludeCodiceFiscale>" +*/
                "<DetailLevel>ReturnAll</DetailLevel>" +
/*                "<ModTimeFrom>"+startime+"</ModTimeFrom>" +
                "<ModTimeTo>"+endtime+"</ModTimeTo>"+*/
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
               /* "<getEBPCaseDetailRequest xmlns:soap=\"http://www.ebay.com/marketplace/resolution/v1/services\">" +*/
                "<getEBPCaseDetailRequest xmlns=\"http://www.ebay.com/marketplace/resolution/v1/services\">"+
                "<RequesterCredentials>" +
                "<eBayAuthToken>"+map.get("token")+"</eBayAuthToken>" +
                "</RequesterCredentials>" +
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
    /*
  *四海邮AddOrder
  */
    public static  String getSiHaiYouAddOrder(){
        String md=md5("11111111"+"auctivision");
        String xml="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                "  <soap:Body>" +
                "    <AddOrder xmlns=\"http://www.4haiyou.com/\">" +
                "      <order>" +
                "        <Address1>XXX Address -1</Address1>" +
                "        <Address2></Address2>" +
                "        <City>Shen Zhen</City>" +
                "        <ContactName>Andy George</ContactName>" +
                "        <Country>CA</Country>" +
                "        <Email>caixu23@msn.com</Email>" +
                "        <OrderNumber>number001-002</OrderNumber>" +
                "        <Phone>800-111-1234</Phone>" +
                "        <Province>CA</Province>" +
                "        <Zipcode>12345</Zipcode>" +
                "        <ShippingType>International</ShippingType>" +
                "        <Value>16.21</Value>" +
                "        <OrderLine>" +
                "          <OrderLine>" +
                "            <SKU>SKU001</SKU>" +
                "            <Quantity>1</Quantity>" +
                "          </OrderLine>" +
                "        </OrderLine>" +
                "        <ShippingFee>0</ShippingFee>" +
                "        <ExtraFee>0</ExtraFee>" +
                "        <TrackingNumber></TrackingNumber>" +
                "        <Status></Status>" +
                "        <OrderError></OrderError>" +
                "        <ShippingDateTime></ShippingDateTime>" +
                "        <GuaranteePrice>0</GuaranteePrice>" +
                "        <Fulfillment>0</Fulfillment>" +
                "      </order>" +
                "      <userName>demo1@4haiyou.com</userName>" +
                "      <password>"+md+"</password>" +
                "    </AddOrder>" +
                "  </soap:Body>" +
                "</soap:Envelope>";
        return xml;
    }
    //提供运输信息provideShippingInfo
    public static  String ProvideShippingInfo(String token,String caseId,String caseType,String carrier,String shippedTime1,String message){
       /* String xml="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<provideShippingInfoRequest xmlns:\"http://www.ebay.com/marketplace/resolution/v1/services\">" +
                "  <RequesterCredentials>" +
                "    <eBayAuthToken>"+token+"</eBayAuthToken>" +
                "  </RequesterCredentials>" +
                "  <caseId>" +
                "    <id>"+caseId+"</id>" +
                "    <type>"+caseType+"</type>" +
                "  </caseId>" +
                "  <carrierUsed>"+carrier+"</carrierUsed>" +
                "  <shippedDate>"+shippedTime1+"</shippedDate>" +
               *//* "  <!--Optional:-->" +*//*
                "  <comments>"+message+"</comments>" +
                "</provideShippingInfoRequest>";*/
        String xml="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<provideShippingInfoRequest xmlns=\"http://www.ebay.com/marketplace/resolution/v1/services\">" +
                "  <carrierUsed>"+carrier+"</carrierUsed>" +
                "  <caseId>" +
                "    <id>"+caseId+"</id>" +
                "    <type>"+caseType+"</type>" +
                "  </caseId>" +
                "  <comments>"+message+"</comments>" +
                "  <shippedDate>"+shippedTime1+"</shippedDate>" +
                "</provideShippingInfoRequest>";
        return xml;
    }
    //提供跟踪信息provideTrackingInfo
    public static  String provideTrackingInfo(String token,String caseId,String caseType,String trackingNum,String carrier,String message){
        String xml="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<provideTrackingInfoRequest xmlns:\"http://www.ebay.com/marketplace/resolution/v1/services\">" +
                "   <RequesterCredentials>" +
                "        <eBayAuthToken>"+token+"</eBayAuthToken>" +
                "   </RequesterCredentials>" +
                "   <caseId>" +
                "      <id>"+caseId+"</id>" +
                "      <type>"+caseType+"</type>" +
                "   </caseId>" +
                "   <trackingNumber>"+trackingNum+"</trackingNumber>" +
                "   <carrierUsed>"+carrier+"</carrierUsed>" +
             /*   "   <!--Optional:-->" +*/
                "   <comments>"+message+"</comments>" +
                "</provideTrackingInfoRequest>";
        return xml;
    }
    //退全款issueFullRefund
    public static  String issueFullRefund(String token,String caseId,String caseType,String message){
        String xml="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<issueFullRefundRequest xmlns:\"http://www.ebay.com/marketplace/resolution/v1/services\">" +
                "   <RequesterCredentials>" +
                "        <eBayAuthToken>"+token+"</eBayAuthToken>" +
                "   </RequesterCredentials>" +
                "   <caseId>" +
                "      <id>"+caseId+"</id>" +
                "      <type>"+caseType+"</type>" +
                "   </caseId>" +
                "   <comments>"+message+"</comments>" +
                "</issueFullRefundRequest>";
        return xml;
    }
    //退半款issuePartialRefund
    public static  String issuePartialRefund(String token,String caseId,String caseType,String message,String amount){
        String xml="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<issuePartialRefundRequest xmlns:\"http://www.ebay.com/marketplace/resolution/v1/services\">" +
                "  <RequesterCredentials>" +
                "    <eBayAuthToken>"+token+"</eBayAuthToken>" +
                "  </RequesterCredentials>" +
                "  <caseId>" +
                "    <id>"+caseId+"</id>" +
                "    <type>"+caseType+"</type>" +
                "  </caseId>" +
                "  <amount currencyCode=\"USD\">"+amount+"</amount>" +
                "  <comments>"+message+"</comments>" +
                "</issuePartialRefundRequest>";
        return xml;
    }
    //offerOtherSolution 发送消息
    public static  String offerOtherSolution(String token,String caseId,String caseType,String message){
        String xml="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<offerOtherSolutionRequest  xmlns:\"http://www.ebay.com/marketplace/resolution/v1/services\">" +
                "   <RequesterCredentials>" +
                "        <eBayAuthToken>"+token+"</eBayAuthToken>" +
                "   </RequesterCredentials>" +
                "   <caseId>" +
                "      <id>"+caseId+"</id>" +
                "      <type>"+caseType+"</type>" +
                "   </caseId>" +
                "   <messageToBuyer>"+message+"</messageToBuyer>" +
                "</offerOtherSolutionRequest>";
        return xml;
    }
    /*
 *出口易
 */
    public static  String getChuKouYi(){
        /*String xml="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                "  <soap:Body>" +
                "    <VerifyUser xmlns=\"http://www.chukou1.com/\">" +
                "      <request>" +
                "        <Token>887E99B5F89BB18BEA12B204B620D236</Token>"+
                "        <UserKey>wr5qjqh4gj</UserKey>" +
                "        <UserID>guest</UserID>" +
                "      </request>" +
                "    </VerifyUser>" +
                "  </soap:Body>" +
                "</soap:Envelope>";*/
        //添加单个产品型号的信息
       /* String xml="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                "  <soap:Body>" +
                "    <ProductAddModel xmlns=\"http://www.chukou1.com/\">" +
                "      <request>" +
                "        <Token>887E99B5F89BB18BEA12B204B620D236</Token>"+
                "        <UserKey>wr5qjqh4gj</UserKey>" +
                "        <ModelDetail>" +
                "          <SKU>SKU123</SKU>" +
                "          <Custom></Custom>" +
                "          <Category>电子产品</Category>" +
                "          <Description>description</Description>" +
                "          <ProductFlag>Normal</ProductFlag>" +
                "          <Packing>" +
                "            <Length>1</Length>" +
                "            <Width>1</Width>" +
                "            <Height>1</Height>" +
                "          </Packing>" +
                "          <Weight>2</Weight>" +
                "          <DeclareName>iii</DeclareName>" +
                "          <DeclareValue>20</DeclareValue>" +
                "          <Warning>5</Warning>" +
                "        </ModelDetail>" +
                "      </request>" +
                "    </ProductAddModel>" +
                "  </soap:Body>" +
                "</soap:Envelope>";*/
        //获取库存编码
        String xml="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                "  <soap:Body>" +
                "    <ProductGetStorageNo xmlns=\"http://www.chukou1.com/\">" +
                "      <request>" +
                "        <Token>887E99B5F89BB18BEA12B204B620D236</Token>"+
                "        <UserKey>wr5qjqh4gj</UserKey>" +
                "        <StartIndex>1</StartIndex>" +
                "        <Count>100</Count>" +
         /*       "        <Warehouse>UK</Warehouse>" +
                "        <SKU>SKU123</SKU>" +*/
                "      </request>" +
                "    </ProductGetStorageNo>" +
                "  </soap:Body>" +
                "</soap:Envelope>";
  /*  *//*    String xml="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                "  <soap:Body>" +
                "    <ProductGetStock xmlns=\"http://www.chukou1.com/\">" +
                "      <request>" +
                "        <Token>887E99B5F89BB18BEA12B204B620D236</Token>"+
                "        <UserKey>wr5qjqh4gj</UserKey>" +
                "        <SKU>SKU123</SKU>" +
                "        <Custom>string</Custom>" +
                "        <Warehouse>US</Warehouse>" +
                "      </request>" +
                "    </ProductGetStock>" +
                "  </soap:Body>" +
                "</soap:Envelope>";*//*
        String xml="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                "  <soap:Body>" +
                "    <InStoreAddOrder xmlns=\"http://www.chukou1.com/\">" +
                "      <request>" +
                "        <Token>887E99B5F89BB18BEA12B204B620D236</Token>"+
                "        <UserKey>wr5qjqh4gj</UserKey>" +
                "        <OrderDetail>" +
                "          <OrderSign>123456789</OrderSign>" +
                "          <State>Received</State>" +
                "          <ShippingMethod>EMS</ShippingMethod>" +
                "          <Warehouse>US</Warehouse>" +
                "          <Location>SZ</Location>" +
                "          <PickupType>0</PickupType>" +
                "          <PickUpAddress>" +
                "            <District>cd</District>" +
                "            <DistrictCode>cd</DistrictCode>" +
                "            <CityCode>cd</CityCode>" +
                "            <ProvinceCode>614900</ProvinceCode>" +
                "          </PickUpAddress>" +
                "          <ArriveTime>2014-01-01</ArriveTime>" +
                "          <CaseList>" +
                "            <InStoreCase xsi:nil=\"true\"/>" +
               *//* "            <CaseNo>1</CaseNo>"+
                "            <State>Received</State>"+
                "            <Packing>" +
                "               <Length>1</Length>" +
                "               <Width>1</Width>" +
                "               <Height>1</Height>" +
                "            </Packing>"+
                "            <Weight>2</Weight>"+
                "            <ProductList>" +
                "               <DeclareName>iii</DeclareName>" +
                "               <DeclareValue>20</DeclareValue>" +
                "               <Quantity>1</Quantity>" +
                "               <SKU>SKU123</SKU>" +
                "            </ProductList>"+
                "            </InStoreCase>"+*//*
                "          </CaseList>" +
                "          <Remark>98765</Remark>" +
                "        </OrderDetail>" +
                "        <Submit>true</Submit>" +
                "      </request>" +
                "    </InStoreAddOrder>" +
                "  </soap:Body>" +
                "</soap:Envelope>";*/
        return xml;
    }
        public static String md5(String s) {
            if (s != null) {
                String md5_str = "";
                try {
                    MessageDigest m = MessageDigest.getInstance("MD5");
                    m.update(s.getBytes(), 0, s.length());
                    md5_str = new BigInteger(1, m.digest()).toString(16);
                    // Log.i("MD5", md5_str);
                    if (md5_str.length() < 32) {
                        md5_str = "0".concat(md5_str);
                    }
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                return md5_str;
            }
            return "";
        }

}
