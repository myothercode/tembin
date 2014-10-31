package com.base.sampleapixml;

import com.base.database.trading.model.TradingMessageAddmembermessage;
import com.base.database.trading.model.TradingMessageGetmymessage;
import com.base.domains.SessionVO;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.userinfo.service.UserInfoService;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.common.DateUtils;
import com.base.utils.threadpool.AddApiTask;
import com.base.utils.xmlutils.SamplePaseXml;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.*;

/**
 * Created by Administrtor on 2014/8/14.
 */
public class GetMyMessageAPI {
    public static List<Element> getMessages(String xml) throws Exception {
        Document document= DocumentHelper.parseText(xml);
        Element root= document.getRootElement();
        Element messages= root.element("Messages");
        Iterator iterator=messages.elementIterator("Message");
        List<Element> list=new ArrayList<Element>();
        Map<String,Object> map=new HashMap<String, Object>();
        while(iterator.hasNext()){
            list.add((Element) iterator.next());
        }
        return list;
    }
    public static TradingMessageGetmymessage addDatabase(Element message,Long accountId,Long ebay) throws Exception {
       /* for(Element message:messages){*/
        TradingMessageGetmymessage ms=new TradingMessageGetmymessage();
        ms.setSender(SamplePaseXml.getSpecifyElementText(message,"Sender"));
        ms.setRecipientuserid(SamplePaseXml.getSpecifyElementText(message,"RecipientUserID"));
        ms.setSendtoname(SamplePaseXml.getSpecifyElementText(message,"SendToName"));
        ms.setSubject(SamplePaseXml.getSpecifyElementText(message,"Subject"));
        ms.setMessageid(SamplePaseXml.getSpecifyElementText(message,"MessageID"));
        ms.setExternalmessageid(SamplePaseXml.getSpecifyElementText(message,"ExternalMessageID"));
        ms.setFlagged(SamplePaseXml.getSpecifyElementText(message,"Flagged"));
        ms.setRead(SamplePaseXml.getSpecifyElementText(message,"Read"));
        ms.setItemid(SamplePaseXml.getSpecifyElementText(message,"ItemID"));
        ms.setReplied(SamplePaseXml.getSpecifyElementText(message,"Replied"));
        String ReceiveDate=SamplePaseXml.getSpecifyElementText(message,"ReceiveDate");
        Date date=DateUtils.returnDate(ReceiveDate);
        if(date!=null){
            ms.setReceivedate(date);
        }
        String ExpirationDate=SamplePaseXml.getSpecifyElementText(message,"ExpirationDate");
        Date date1=DateUtils.returnDate(ExpirationDate);
        if(date1!=null){
            ms.setExpirationdate(date1);
        }
        ms.setResponseenabled(SamplePaseXml.getSpecifyElementText(message,"ResponseDetails","ResponseEnabled"));
        ms.setResponseurl(SamplePaseXml.getSpecifyElementText(message,"ResponseDetails","ResponseURL"));
        ms.setFolderid(SamplePaseXml.getSpecifyElementText(message,"Folder","FolderID"));
        ms.setLoginAccountId(accountId);
        ms.setEbayAccountId(ebay);
        return ms;
    }
    public static Map<String,String> apiAddmembermessage(Map<String,Object> m) throws Exception {
        UsercontrollerDevAccountExtend dev= (UsercontrollerDevAccountExtend) m.get("devAccount");
        TradingMessageAddmembermessage addmessage= (TradingMessageAddmembermessage) m.get("addMessage");
        UserInfoService userInfoService= (UserInfoService) m.get("userInfoService");
        Long ebayId= (Long) m.get("ebayId");
        String apiUrl= (String) m.get("url");
        dev.setApiCallName(APINameStatic.AddMemberMessageRTQ);
        String xml= BindAccountAPI.getAddMemberMessageRTQ(addmessage,userInfoService.getTokenByEbayID(ebayId));
        AddApiTask addApiTask = new AddApiTask();
       /* Map<String, String> resMap= addApiTask.exec(dev, xml, "https://api.ebay.com/ws/api.dll");*/
        Map<String, String> resMap= addApiTask.exec(dev, xml, apiUrl);
        String r1=resMap.get("stat");
        String res=resMap.get("message");
        Map map=new HashMap();
        if("fail".equalsIgnoreCase(r1)){
            map.put("msg","false");
            map.put("par","发送失败");
        }
        String ack = SamplePaseXml.getVFromXmlString(res, "Ack");
        if("Success".equalsIgnoreCase(ack)){
            map.put("msg","true");
            map.put("par","发送成功");
        }else {
            map.put("msg","false");
            map.put("par","获取必要的参数失败！请稍后重试");
        }
        return map;
    }
    public static String getContent(Map m) throws Exception {
        UsercontrollerDevAccountExtend dev= (UsercontrollerDevAccountExtend) m.get("devAccount");
        String messageID= (String) m.get("messageId");
        UserInfoService userInfoService= (UserInfoService) m.get("userInfoService");
        Long ebayId= (Long) m.get("ebayId");
        String apiUrl= (String) m.get("url");
        dev.setApiCallName(APINameStatic.GetMyMessages);
        //测试环境
        String xml= BindAccountAPI.getGetMyMessagesByReturnHeader(messageID,userInfoService.getTokenByEbayID(ebayId));//获取接受消息
        //真实环境
       /* String xml= BindAccountAPI.getGetMyMessagesByReturnHeader(messageID,"AgAAAA**AQAAAA**aAAAAA**jek4VA**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6AFlIWnC5iEpAidj6x9nY+seQ**tSsCAA**AAMAAA**y8BaJPw6GUdbbbco8zXEwRR4Ttr9sLd78jL0FyYa0yonvk5hz1RY6DtKkaDtn9NuzluKeFZoqsNbujZP48S4QZhHVa5Dp0bDGqBdKaosolzsrPDm8qozoxbsTiWY8X/M5xev/YU2zJ42/JRGDlEdnQhwCASG1BcSo+DqXuG3asbj0INJr4/HsArf8cCYsPQCtUDkq5QJY6Rvil+Kla/dGhViTQ3gt7a4t3KjxKH+/jlhDU/6sUEKlvb2nY1gCmX8S9pU48c+4Vy6G6NpfcGUcIG/TXFWBTqU0R+v+/6DOIfDW8s90rrLSVMGFqnRxA2sexdEmVhyF5csBmv9+TVfjdyEZK5UgvDqWJHesuDMFTr0KIc8EtdnTQaE3YeZch15DdoEbqcyyBQBZHidBPdDHz/DkpTg7iq1953yKodm2y0mW6aaYAfc5beW+PoqMW8C3WwGJmWZqh3dBi+QEKznEJ9SRg43Bc3q2344JFY7YpIEfJDaQ36BHRcIZxLew8v7RIGL5YYO1BBdTolVV9/eMCQDsUB0mUeMYjxnH5w0K/6CDmJ9WNMQTblNol0x3vhJbil1L/CMP9KGEHj5Yqx0003MLL9Yod7nL89Zpy+a8I/E5byxFt21KZTGE90Ot0LyLpRXsotDwIm5+ZdvATsU6mGADX4tk970CpCeM487v9fn1opouaCBvknCINqXoSeGXLQ7uZFpeqkWts1lIWh9vEuuiuZa4vNoL7aCr+93LTFnsO6AsZp7dmboQcI96I/o");//获取接受消息
        */

        AddApiTask addApiTask = new AddApiTask();
        /*Map<String, String> resMap= addApiTask.exec(dev, xml, "https://api.ebay.com/ws/api.dll");*/
        Map<String, String> resMap= addApiTask.exec(dev, xml, apiUrl);
        String r1=resMap.get("stat");
        String res=resMap.get("message");
        if("fail".equalsIgnoreCase(r1)){
            return null;
        }
        String ack = SamplePaseXml.getVFromXmlString(res, "Ack");
        if("Success".equalsIgnoreCase(ack)){
            SessionVO sessionVO= SessionCacheSupport.getSessionVO();
            String text=getText(res);
            return text;
        }
        return null;
    }
    public static String getText(String xml) throws DocumentException {
        Document document= DocumentHelper.parseText(xml);
        Element root= document.getRootElement();
        Element messages= root.element("Messages");
        Iterator iterator=messages.elementIterator("Message");
        String text="";
        while(iterator.hasNext()){
            Element message= (Element) iterator.next();
            Element Text=message.element("Text");
            if(Text!=null){
                text=Text.getTextTrim();
            }
        }
        return text;
    }
}
