package com.message.controller;

import com.base.database.trading.model.TradingMessageAddmembermessage;
import com.base.domains.CommonParmVO;
import com.base.domains.SessionVO;
import com.base.domains.querypojos.MessageAddmymessageQuery;
import com.base.domains.querypojos.MessageGetmymessageQuery;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.domains.userinfo.UsercontrollerEbayAccountExtend;
import com.base.mybatis.page.Page;
import com.base.mybatis.page.PageJsonBean;
import com.base.sampleapixml.APINameStatic;
import com.base.sampleapixml.BindAccountAPI;
import com.base.sampleapixml.GetMyMessageAPI;
import com.base.userinfo.service.SystemUserManagerService;
import com.base.userinfo.service.UserInfoService;
import com.base.utils.annotations.AvoidDuplicateSubmission;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.common.DateUtils;
import com.base.utils.threadpool.AddApiTask;
import com.base.utils.threadpool.TaskMessageVO;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
import com.sitemessage.service.SiteMessageStatic;
import com.trading.service.ITradingMessageAddmembermessage;
import com.trading.service.ITradingMessageGetmymessage;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
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
import java.util.*;

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
    @Autowired
    private SystemUserManagerService systemUserManagerService;


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
    //获取未读消息ajax方法
    @RequestMapping("/noReadMessageGetmymessageList.do")
    @ResponseBody
    public void noReadMessageGetmymessageList(HttpServletRequest request,CommonParmVO commonParmVO) throws Exception {
        String readed=request.getParameter("readed");
        Map map=new HashMap();
        List<UsercontrollerEbayAccountExtend> ebays=systemUserManagerService.queryCurrAllEbay(map);
        Map m = new HashMap();
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        Page page=jsonBean.toPage();
        List<MessageGetmymessageQuery> lists=new ArrayList<MessageGetmymessageQuery>();
        if(ebays.size()>0){
            m.put("read",readed);
            m.put("ebays",ebays);
            lists= this.iTradingMessageGetmymessage.selectMessageGetmymessageByGroupList(m,page);
        }

        jsonBean.setList(lists);
        AjaxSupport.sendSuccessText("",jsonBean);

    }
    /**获取list数据的ajax方法*/
    @RequestMapping("/ajax/loadMessageAddmymessageList.do")
    @ResponseBody
    public void loadMessageAddmymessageList(HttpServletRequest request,CommonParmVO commonParmVO) throws Exception {
        String replied=request.getParameter("replied");
        String amount=request.getParameter("amount");
        String status=request.getParameter("status");
        String day=request.getParameter("day");
        String type=request.getParameter("type");
        String content=request.getParameter("content");
        Date starttime=null;
        Date endtime=null;
        if(!StringUtils.isNotBlank(replied)){
            replied=null;
        }
        if(!StringUtils.isNotBlank(amount)){
            amount=null;
        }
        if(!StringUtils.isNotBlank(status)){
            status=null;
        }else{
            replied=status;
        }
        if(!StringUtils.isNotBlank(day)){
            day=null;
        }else{
            if("2".equals(day)){
                starttime=DateUtils.subDays(new Date(),1);
                Date endTime= DateUtils.addDays(starttime, 0);
                endtime= com.base.utils.common.DateUtils.turnToDateEnd(endTime);
            }else{
                int days=Integer.parseInt(day);
                starttime=DateUtils.subDays(new Date(),days-1);
                Date endTime= DateUtils.addDays(starttime,days-1);
                endtime= com.base.utils.common.DateUtils.turnToDateEnd(endTime);
            }
        }
        Map m = new HashMap();
        /**分页组装*/
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        Page page=jsonBean.toPage();
        Map map=new HashMap();
        List<UsercontrollerEbayAccountExtend> ebays=systemUserManagerService.queryCurrAllEbay(map);
        List<MessageAddmymessageQuery> lists=new ArrayList<MessageAddmymessageQuery>();
        if(ebays.size()>0){
            m.put("ebays",ebays);
            m.put("amount",amount);
            m.put("starttime",starttime);
            m.put("endtime",endtime);
            m.put("replied",replied);
            lists=iTradingMessageAddmembermessage.selectMessageGetmymessageByGroupList(m,page);
        }
        jsonBean.setList(lists);
        AjaxSupport.sendSuccessText("",jsonBean);
    }
    /**获取list数据的ajax方法*/
    @RequestMapping("/ajax/loadMessageGetmymessageList.do")
    @ResponseBody
    public void loadMessageGetmymessageList(HttpServletRequest request,CommonParmVO commonParmVO) throws Exception {
         /*amount,status,day,type,content*/
        String amount=request.getParameter("amount");
        String status=request.getParameter("status");
        String day=request.getParameter("day");
        String type=request.getParameter("type");
        String content=request.getParameter("content");
        Date starttime=null;
        Date endtime=null;
        if(!StringUtils.isNotBlank(amount)){
            amount=null;
        }
        if(!StringUtils.isNotBlank(status)){
            status=null;
        }
        if(!StringUtils.isNotBlank(day)){
            day=null;
        }else{
            if("2".equals(day)){
                starttime=DateUtils.subDays(new Date(),1);
                Date endTime= DateUtils.addDays(starttime, 0);
                endtime= com.base.utils.common.DateUtils.turnToDateEnd(endTime);
            }else{
                int days=Integer.parseInt(day);
                starttime=DateUtils.subDays(new Date(),days-1);
                Date endTime= DateUtils.addDays(starttime,days-1);
                endtime= com.base.utils.common.DateUtils.turnToDateEnd(endTime);
            }
        }
        Map m = new HashMap();
        /**分页组装*/
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        Page page=jsonBean.toPage();
        Map map=new HashMap();
        List<UsercontrollerEbayAccountExtend> ebays=systemUserManagerService.queryCurrAllEbay(map);
        List<MessageGetmymessageQuery> lists=new ArrayList<MessageGetmymessageQuery>();
        if(ebays.size()>0){
            m.put("ebays",ebays);
            m.put("amount",amount);
            m.put("status",status);
            m.put("starttime",starttime);
            m.put("endtime",endtime);
            lists= this.iTradingMessageGetmymessage.selectMessageGetmymessageByGroupList(m,page);
        }
        jsonBean.setList(lists);
        jsonBean.setTotal((int)page.getTotalCount());
        AjaxSupport.sendSuccessText("", jsonBean);
    }
    @RequestMapping("/viewMessageAddmymessage.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView viewMessageAddmymessage(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        String messageID=request.getParameter("messageID");
        List<TradingMessageAddmembermessage> addmembermessages=iTradingMessageAddmembermessage.selectMessageGetmymessageByMessageId(messageID,"true");
        modelMap.put("addMessages",addmembermessages);
        return forword("MessageGetmymessage/viewMessageAddmymessage",modelMap);
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
        UsercontrollerDevAccountExtend dev = userInfoService.getDevInfo(null);
        dev.setApiSiteid("0");
        dev.setApiCallName(APINameStatic.GetMyMessages);
        request.getSession().setAttribute("dveId", dev);
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
        tm.setSender(message.getRecipientuserid());
        tm.setSubject(message.getSubject());
        parms.put("addMessage", tm);
        parms.put("ebayId",message.getEbayAccountId());
        parms.put("devAccount",dev);
        parms.put("url",apiUrl);
        parms.put("userInfoService",userInfoService);
        Map<String,String> map= GetMyMessageAPI.apiAddmembermessage(parms);
        String flag=map.get("msg");
        if(!"true".equals(flag)){
            tm.setReplied("false");
            iTradingMessageAddmembermessage.saveMessageAddmembermessage(tm);
            AjaxSupport.sendSuccessText("fail", map.get("par"));
        }else{
            tm.setReplied("true");
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
      /*  UsercontrollerDevAccountExtend d=new UsercontrollerDevAccountExtend();
        d.setApiDevName("5d70d647-b1e2-4c7c-a034-b343d58ca425");
        d.setApiAppName("sandpoin-23af-4f47-a304-242ffed6ff5b");
        d.setApiCertName("165cae7e-4264-4244-adff-e11c3aea204e");
        d.setApiCompatibilityLevel("881");*/
        //----修改前---
        /*Map map=new HashMap();
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
        UsercontrollerDevAccountExtend d = userInfoService.getDevInfo(null);//开发者帐号id
        d.setApiSiteid("0");
        d.setApiCallName(APINameStatic.GetMyMessages);
        request.getSession().setAttribute("dveId", d);
         *//* Map<String, String> resMap = addApiTask.exec(d, xml, "https://api.ebay.com/ws/api.dll");*//*
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
                if("false".equals(ms.getRead())){
                    iTradingMessageGetmymessage.saveMessageGetmymessage(ms);
                }
            }
            AjaxSupport.sendSuccessText("success", "同步成功！");
        } else {
            String errors = SamplePaseXml.getVFromXmlString(res, "Errors");
            logger.error("执行失败!" + errors);
            AjaxSupport.sendFailText("fail", "获取必要的参数失败！请稍后重试");
        }*/
        //--修改后---
        UsercontrollerDevAccountExtend d = new UsercontrollerDevAccountExtend();
        d.setApiSiteid("0");
        d.setApiCallName(APINameStatic.GetMyMessages);
        request.getSession().setAttribute("dveId", d);
        Map map=new HashMap();
    /*   Date startTime2= com.base.utils.common.DateUtils.subDays(new Date(),8);
        Date endTime= DateUtils.addDays(startTime2, 9);*/
        Date startTime2= com.base.utils.common.DateUtils.subDays(new Date(),60);
        Date endTime= DateUtils.addDays(startTime2, 60);/*MutualWithdrawalAgreementLate*/


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
          /*Map<String, String> resMap = addApiTask.exec(d, xml, "https://api.ebay.com/ws/api.dll");*/
        /*Map<String, String> resMap = addApiTask.exec(d, xml, apiUrl);*/
        TaskMessageVO taskMessageVO=new TaskMessageVO();
        taskMessageVO.setMessageContext("接受的消息");
        taskMessageVO.setMessageTitle("获取接受消息的");
        taskMessageVO.setMessageType(SiteMessageStatic.SYNCHRONIZE_GET_MESSAGE_TYPE);
        taskMessageVO.setBeanNameType(SiteMessageStatic.SYNCHRONIZE_GET_MESSAGE_BEAN);
        taskMessageVO.setMessageFrom("system");
        Map m=new HashMap();
        m.put("accountId",commonParmVO.getId());
        m.put("ebay",ebay);
        taskMessageVO.setObjClass(m);
        SessionVO sessionVO= SessionCacheSupport.getSessionVO();
        taskMessageVO.setMessageTo(sessionVO.getId());
        addApiTask.execDelayReturn(d, xml, apiUrl, taskMessageVO);
        AjaxSupport.sendSuccessText("message", "操作成功！结果请稍后查看消息！");
    }
}
