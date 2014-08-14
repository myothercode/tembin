package com.message.controller;

import com.base.database.trading.model.TradingMessageAddmembermessage;
import com.base.database.trading.model.TradingMessageGetmymessage;
import com.base.domains.CommonParmVO;
import com.base.domains.querypojos.MessageGetmymessageQuery;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.domains.userinfo.UsercontrollerEbayAccountExtend;
import com.base.mybatis.page.Page;
import com.base.mybatis.page.PageJsonBean;
import com.base.sampleapixml.APINameStatic;
import com.base.sampleapixml.BindAccountAPI;
import com.base.sampleapixml.GetMyMessageAPI;
import com.base.userinfo.service.UserInfoService;
import com.base.utils.annotations.AvoidDuplicateSubmission;
import com.base.utils.common.DateUtils;
import com.base.utils.threadpool.AddApiTask;
import com.base.utils.xmlutils.SamplePaseXml;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
import com.trading.service.ITradingMessageAddmembermessage;
import com.trading.service.ITradingMessageGetmymessage;
import org.apache.log4j.Logger;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/8/6.
 */
@Controller
@RequestMapping("message")
public class GetmymessageController extends BaseAction{

    static Logger logger = Logger.getLogger(GetmymessageController.class);
    @Autowired
    private ITradingMessageGetmymessage iTradingMessageGetmymessage;
    @Autowired
    private ITradingMessageAddmembermessage iTradingMessageAddmembermessage;
    @Autowired
    private UserInfoService userInfoService;


    @Value("${EBAY.API.URL}")
    private String apiUrl;
    /**
     * 消息列表
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping("/MessageGetmymessageList.do")
    public ModelAndView MessageGetmymessageList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        return forword("MessageGetmymessage/MessageGetmymessageList",modelMap);
    }

    /**获取list数据的ajax方法*/
    @RequestMapping("/ajax/loadMessageGetmymessageList.do")
    @ResponseBody
    public void loadMessageGetmymessageList(CommonParmVO commonParmVO) throws Exception {
        Map m = new HashMap();
        /**分页组装*/
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        Page page=jsonBean.toPage();
        List<UsercontrollerEbayAccountExtend> ebays=userInfoService.getEbayAccountForCurrUser();
        m.put("ebays",ebays);
        List<MessageGetmymessageQuery> lists= this.iTradingMessageGetmymessage.selectMessageGetmymessageByGroupList(m,page);
        jsonBean.setList(lists);
        jsonBean.setTotal((int)page.getTotalCount());
        AjaxSupport.sendSuccessText("", jsonBean);
    }

   /**
     * 查看消息
     * @param request
     * @param response
     * @param modelMap
     * @return
     */

    @RequestMapping("/viewMessageGetmymessage.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView viewTemplateInitTable(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        String messageID=request.getParameter("messageID");
        UsercontrollerDevAccountExtend dev= (UsercontrollerDevAccountExtend) request.getSession().getAttribute("dveId");
        List<UsercontrollerEbayAccountExtend> ebays = userInfoService.getEbayAccountForCurrUser();
        Map m=new HashMap();
        m.put("messageID",messageID);
        m.put("ebays",ebays);
        List<MessageGetmymessageQuery> messages=iTradingMessageGetmymessage.selectMessageGetmymessageBySender(m);
        for(MessageGetmymessageQuery message:messages){
            if("false".equals(message.getRead())){
                Map parms=new HashMap();
                parms.put("messageId", message.getMessageid());
                parms.put("ebayId",message.getEbayAccountId());
                parms.put("devAccount",dev);
                parms.put("url",apiUrl);
                parms.put("userInfoService",userInfoService);
                String content=GetMyMessageAPI.getContent(parms);
                message.setTextHtml(content);
                message.setRead("true");
                iTradingMessageGetmymessage.saveMessageGetmymessage(message);
            }
        }
        modelMap.put("messages",messages);
        modelMap.put("messageID",messages.get(0).getMessageid());

        return forword("MessageGetmymessage/viewMessageGetmymessage",modelMap);
    }

    @RequestMapping("/sendMessageGetmymessage.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    /**初始化回复消息页面*/
    public ModelAndView sendMessageGetmymessage(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
        String messageID=request.getParameter("messageid");
        Map m=new HashMap();
        m.put("messageID",messageID);
        List<MessageGetmymessageQuery> messages=iTradingMessageGetmymessage.selectMessageGetmymessageBySender(m);
        modelMap.put("message",messages.get(0));
        return forword("MessageGetmymessage/sendMessageGetmymessageList",modelMap);
    }

    @RequestMapping("/ajax/saveMessageGetmymessage.do")
    @AvoidDuplicateSubmission(needRemoveToken = true)
    @ResponseBody
    /**回复消息*/
    public void saveMessageGetmymessage(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws Exception {
        String messageid=request.getParameter("messageid");
        String sendHeader=request.getParameter("sendHeader");
        String content= request.getParameter("content");
        String emailCopyToSender=request.getParameter("emailCopyToSender");
        String displayToPublic=request.getParameter("displayToPublic");
        UsercontrollerDevAccountExtend dev= (UsercontrollerDevAccountExtend) request.getSession().getAttribute("dveId");
        Map<String,Object> parms=new HashMap<String, Object>();
        Map m=new HashMap();
        m.put("messageID",messageid);
        List<MessageGetmymessageQuery> messages=iTradingMessageGetmymessage.selectMessageGetmymessageBySender(m);
        MessageGetmymessageQuery message=messages.get(0);
        TradingMessageAddmembermessage tm=new TradingMessageAddmembermessage();
        tm.setItemid(message.getItemid());
        tm.setMessageid(messageid);
        tm.setBody(content);
        tm.setRecipientid(message.getSender());
        tm.setParentmessageid(message.getExternalmessageid());
        tm.setEmailcopytosender(emailCopyToSender);
        tm.setDisplaytopublic(displayToPublic);
        parms.put("addMessage", tm);
        parms.put("ebayId",message.getEbayAccountId());
        parms.put("devAccount",dev);
        parms.put("url",apiUrl);
        parms.put("userInfoService",userInfoService);
        Map<String,String> map= GetMyMessageAPI.apiAddmembermessage(parms);
        String flag=map.get("msg");
        if(!"true".equals(flag)){
            AjaxSupport.sendSuccessText("fail", map.get("par"));
        }else{
            iTradingMessageAddmembermessage.saveMessageAddmembermessage(tm);
            AjaxSupport.sendSuccessText("message",  map.get("par"));
        }
    }

    @RequestMapping("/GetmymessageEbay.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    @ResponseBody
    public ModelAndView GetmymessageEbay(@ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
      /*  Asserts.assertTrue(commonParmVO.getId() != null, "参数获取失败！");*/
        List<UsercontrollerEbayAccountExtend> ebays = userInfoService.getEbayAccountForCurrUser();
        modelMap.put("ebays",ebays);
        return forword("MessageGetmymessage/GetmymessageEbay",modelMap);
    }

    @RequestMapping("/apiGetMyMessagesRequest")
    @AvoidDuplicateSubmission(needRemoveToken = true)
    @ResponseBody
    /**获取接受信息总数*/
    public void apiGetMyMessagesRequest(CommonParmVO commonParmVO,HttpServletRequest request) throws Exception {
        String ebayId=request.getParameter("ebayId");
        Long ebay=Long.valueOf(ebayId);
        UsercontrollerDevAccountExtend d = userInfoService.getDevInfo(null);//开发者帐号id
      /*  UsercontrollerDevAccountExtend d=new UsercontrollerDevAccountExtend();
        d.setApiDevName("5d70d647-b1e2-4c7c-a034-b343d58ca425");
        d.setApiAppName("sandpoin-23af-4f47-a304-242ffed6ff5b");
        d.setApiCertName("165cae7e-4264-4244-adff-e11c3aea204e");
        d.setApiCompatibilityLevel("881");*/
        d.setApiSiteid("0");
        d.setApiCallName(APINameStatic.GetMyMessages);
        request.getSession().setAttribute("dveId", d);
        Map map=new HashMap();
        Date startTime2= com.base.utils.common.DateUtils.subDays(new Date(),8);
        Date endTime= DateUtils.addDays(startTime2,9);
        Date end1= com.base.utils.common.DateUtils.turnToDateEnd(endTime);
        String start=DateUtils.DateToString(startTime2);
        String end= DateUtils.DateToString(end1);
        String token=userInfoService.getTokenByEbayID(ebay);
        map.put("token", token);
        map.put("detail", "ReturnHeaders");
        map.put("startTime", start);
        map.put("endTime", end);
        String xml = BindAccountAPI.getGetMyMessages(map);//获取接受消息
        AddApiTask addApiTask = new AddApiTask();
        /*  Map<String, String> resMap = addApiTask.exec(d, xml, "https://api.ebay.com/ws/api.dll");*/
        Map<String, String> resMap = addApiTask.exec(d, xml, apiUrl);
        String r1 = resMap.get("stat");
        String res = resMap.get("message");
        if ("fail".equalsIgnoreCase(r1)) {
            AjaxSupport.sendFailText("fail", res);
            return;
        }
        String ack = SamplePaseXml.getVFromXmlString(res, "Ack");
        if ("Success".equalsIgnoreCase(ack)) {
            List<Element> messages =GetMyMessageAPI.getMessages(res);
            for(Element message:messages){
                TradingMessageGetmymessage ms= GetMyMessageAPI.addDatabase(message, commonParmVO.getId(), ebay);//保存到数据库
                iTradingMessageGetmymessage.saveMessageGetmymessage(ms);
            }
            AjaxSupport.sendSuccessText("success", "同步成功！");
        } else {
            String errors = SamplePaseXml.getVFromXmlString(res, "Errors");
            logger.error("获取apisessionid失败!" + errors);
            AjaxSupport.sendFailText("fail", "获取必要的参数失败！请稍后重试");
        }
    }
    /*public  List<Element> getMessages(String xml) throws Exception {
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
    public void addDatabase(List<Element> messages,Long accountId,Long ebay) throws Exception {
        for(Element message:messages){
            TradingMessageGetmymessage ms=new TradingMessageGetmymessage();
            ms.setSender(saveDatabase(message,"Sender"));
            ms.setRecipientuserid(saveDatabase(message, "RecipientUserID"));
            ms.setSendtoname(saveDatabase(message, "SendToName"));
            ms.setSubject(saveDatabase(message, "Subject"));
            ms.setMessageid(saveDatabase(message, "MessageID"));
            ms.setExternalmessageid(saveDatabase(message, "ExternalMessageID"));
            ms.setFlagged(saveDatabase(message, "Flagged"));
            ms.setRead(saveDatabase(message, "Read"));
            ms.setItemid(saveDatabase(message, "ItemID"));
            ms.setReplied(saveDatabase(message, "Replied"));
            String ReceiveDate1=saveDatabase(message,"ReceiveDate");
            String ReceiveDate=ReceiveDate1.substring(0,10)+" "+ReceiveDate1.substring(11,19);
            Date date=StringToDate(ReceiveDate);
            ms.setReceivedate(date);
            String ExpirationDate1=saveDatabase(message,"ExpirationDate");
            String ExpirationDate=ExpirationDate1.substring(0,10)+" "+ExpirationDate1.substring(11,19);
            ms.setExpirationdate(StringToDate(ExpirationDate));
            ms.setResponseenabled(saveDatabase1(message, "ResponseEnabled"));
            ms.setResponseurl(saveDatabase1(message, "ResponseURL"));
            ms.setFolderid(saveDatabase1(message, "FolderID"));
            ms.setLoginAccountId(accountId);
            ms.setEbayAccountId(ebay);
            iTradingMessageGetmymessage.saveMessageGetmymessage(ms);
        }

    }
    public String saveDatabase(Element message,String nodename){
        Element element=message.element(nodename);
        if(element==null){
            return null;
        }
        return element.getTextTrim();
    }
    public String saveDatabase1(Element message,String nodename){
        Element ResponseDetails=message.element("ResponseDetails");
        Element Folder=message.element("Folder");
        Element node= Folder.element(nodename);
        Element node1= ResponseDetails.element(nodename);
        if(node!=null){
             return node.getTextTrim();
         }
       if(node1!=null){
           return node1.getTextTrim();
       }
        return null;
    }
    public Date StringToDate(String date) throws ParseException {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date d = f.parse(date);
        return d;
    }
    public String DateToString(Date date) throws ParseException {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String startTime=f.format(date);
        String d=startTime.substring(0,10)+"T"+startTime.substring(11)+".000Z";
        return d;
    }
    public Map<String,String> apiAddmembermessage(TradingMessageAddmembermessage addmessage,Long ebayId,UsercontrollerDevAccountExtend dev) throws Exception {
        dev.setApiCallName(APINameStatic.AddMemberMessageRTQ);
        String xml= BindAccountAPI.getAddMemberMessageRTQ(addmessage,userInfoService.getTokenByEbayID(ebayId));
        AddApiTask addApiTask = new AddApiTask();
       *//* Map<String, String> resMap= addApiTask.exec(dev, xml, "https://api.ebay.com/ws/api.dll");*//*
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
    public String getContent(String messageID,Long ebayId,UsercontrollerDevAccountExtend dev) throws Exception {
        dev.setApiCallName(APINameStatic.GetMyMessages);
        String xml= BindAccountAPI.getGetMyMessagesByReturnHeader(messageID,userInfoService.getTokenByEbayID(ebayId));//获取接受消息
        AddApiTask addApiTask = new AddApiTask();
        *//*Map<String, String> resMap= addApiTask.exec(dev, xml, "https://api.ebay.com/ws/api.dll");*//*
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
    public String getText(String xml) throws DocumentException {
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
    }*/
}
