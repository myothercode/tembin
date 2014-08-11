package com.base.sampleapixml;

import com.base.database.trading.model.TradingMessageAddmembermessage;

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
}
