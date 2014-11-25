package com.message.controller;

import com.base.aboutpaypal.domain.PaypalVO;
import com.base.aboutpaypal.service.PayPalService;
import com.base.database.trading.model.*;
import com.base.domains.CommonParmVO;
import com.base.domains.SessionVO;
import com.base.domains.querypojos.MessageGetmymessageQuery;
import com.base.domains.querypojos.TradingOrderAddMemberMessageAAQToPartnerQuery;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.domains.userinfo.UsercontrollerEbayAccountExtend;
import com.base.mybatis.page.Page;
import com.base.mybatis.page.PageJsonBean;
import com.base.sampleapixml.APINameStatic;
import com.base.sampleapixml.BindAccountAPI;
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
import com.trading.service.*;
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
    @Autowired
    private ITradingOrderAddMemberMessageAAQToPartner iTradingOrderAddMemberMessageAAQToPartner;
    @Autowired
    private ITradingOrderGetOrders iTradingOrderGetOrders;
    @Autowired
    private ITradingOrderGetSellerTransactions iTradingOrderGetSellerTransactions;
    @Autowired
    private PayPalService payPalService;
    @Autowired
    private  ITradingOrderGetAccount iTradingOrderGetAccount;
    @Autowired
    private ITradingOrderGetItem iTradingOrderGetItem;
    @Autowired
    private ITradingOrderPictureDetails iTradingOrderPictureDetails;
    @Autowired
    private ITradingOrderSenderAddress iTradingOrderSenderAddress;
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
        String starttime1=request.getParameter("starttime1");
        String endtime1=request.getParameter("endtime1");
        Date starttime=null;
        Date endtime=null;
     /*   if(!StringUtils.isNotBlank(content)){
            content=null;
        }*/
        if(!StringUtils.isNotBlank(type)){
            type=null;
        }
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
        if(StringUtils.isNotBlank(starttime1)){
            int year=Integer.valueOf(starttime1.substring(0,4));
            int month=Integer.valueOf(starttime1.substring(5,7))-1;
            int day1=Integer.valueOf(starttime1.substring(8));
            starttime=DateUtils.turnToDateStart(DateUtils.buildDate(year,month,day1));
        }
        if(StringUtils.isNotBlank(endtime1)){
            int year=Integer.valueOf(endtime1.substring(0,4));
            int month=Integer.valueOf(endtime1.substring(5,7))-1;
            int day1=Integer.valueOf(endtime1.substring(8));
            endtime=DateUtils.turnToDateEnd(DateUtils.buildDate(year,month,day1));
        }
        Map m = new HashMap();
        /**分页组装*/
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        Page page=jsonBean.toPage();
        Map map=new HashMap();
        List<UsercontrollerEbayAccountExtend> ebays=systemUserManagerService.queryCurrAllEbay(map);
        List<TradingOrderAddMemberMessageAAQToPartnerQuery> lists=new ArrayList<TradingOrderAddMemberMessageAAQToPartnerQuery>();
        if(ebays.size()>0){
            m.put("ebays",ebays);
            m.put("amount",amount);
            m.put("starttime",starttime);
            m.put("endtime",endtime);
            m.put("replied",replied);
            m.put("messageType",4);
            m.put("type",type);
            m.put("content",content);
            lists=iTradingOrderAddMemberMessageAAQToPartner.selectTradingOrderAddMemberMessageAAQToPartner(m, page);
        }
        jsonBean.setList(lists);
        jsonBean.setTotal((int)page.getTotalCount());
        AjaxSupport.sendSuccessText("",jsonBean);
    }
    /**标记为已读*/
    @RequestMapping("/ajax/markReaded.do")
    @ResponseBody
    public void markReaded(HttpServletRequest request) throws Exception {
        String ids1=request.getParameter("value");
        String value=request.getParameter("value1");
        String[] ids=ids1.split(",");
        for(int i=0;i<ids.length;i++){
            Long id= Long.valueOf(ids[i]);
            TradingMessageGetmymessage message=iTradingMessageGetmymessage.selectMessageGetmymessageById(id);
            if(message!=null){
                message.setRead(value);
                iTradingMessageGetmymessage.saveMessageGetmymessage(message);
            }
        }
        if("true".equals(value)){
            AjaxSupport.sendSuccessText("", "已标记为已读");
        }else{
            AjaxSupport.sendSuccessText("", "已标记为未读");
        }

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
        String starttime1=request.getParameter("starttime1");
        String endtime1=request.getParameter("endtime1");
        String messageFrom=request.getParameter("messageFrom");
        Date starttime=null;
        Date endtime=null;
        if(!StringUtils.isNotBlank(messageFrom)){
            messageFrom=null;
        }
        if(!StringUtils.isNotBlank(amount)){
            amount=null;
        }
       /* if(!StringUtils.isNotBlank(content)){
            content=null;
        }*/
        if(!StringUtils.isNotBlank(type)){
            type=null;
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
        if(StringUtils.isNotBlank(starttime1)){
            int year=Integer.valueOf(starttime1.substring(0,4));
            int month=Integer.valueOf(starttime1.substring(5,7))-1;
            int day1=Integer.valueOf(starttime1.substring(8));
            starttime=DateUtils.turnToDateStart(DateUtils.buildDate(year,month,day1));
        }
        if(StringUtils.isNotBlank(endtime1)){
            int year=Integer.valueOf(endtime1.substring(0,4));
            int month=Integer.valueOf(endtime1.substring(5,7))-1;
            int day1=Integer.valueOf(endtime1.substring(8));
            endtime=DateUtils.turnToDateEnd(DateUtils.buildDate(year,month,day1));
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
            m.put("messageFrom",messageFrom);
            m.put("type",type);
            m.put("content",content);
            lists= this.iTradingMessageGetmymessage.selectMessageGetmymessageByGroupList(m,page);
        }
        jsonBean.setList(lists);
        jsonBean.setTotal((int)page.getTotalCount());
        AjaxSupport.sendSuccessText("", jsonBean);
    }
    @RequestMapping("/viewMessageAddmymessage.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView viewMessageAddmymessage(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        String itemid=request.getParameter("itemid");
        String recipientid=request.getParameter("recipientid");
        String sender=request.getParameter("sender");
        String transactionid=request.getParameter("transactionid");
        if(!StringUtils.isNotBlank(transactionid)){
            transactionid=null;
        }
        List<TradingOrderAddMemberMessageAAQToPartner> addMessages=new ArrayList<TradingOrderAddMemberMessageAAQToPartner>();
        List<TradingMessageGetmymessage> messages1=new ArrayList<TradingMessageGetmymessage>();
        List<TradingMessageGetmymessage> messageList=iTradingMessageGetmymessage.selectMessageGetmymessageByItemIdAndSender(itemid,recipientid,sender);
        List<TradingOrderAddMemberMessageAAQToPartner> addmessageList=iTradingOrderAddMemberMessageAAQToPartner.selectTradingOrderAddMemberMessageAAQToPartnerByItemIdAndSender(itemid,4,sender,recipientid);
        messages1.addAll(messageList);
        addMessages.addAll(addmessageList);
        for(TradingMessageGetmymessage message:messages1){
            TradingOrderAddMemberMessageAAQToPartner partner=new TradingOrderAddMemberMessageAAQToPartner();
            partner.setSender(message.getSender());
            partner.setSubject(message.getSubject());
            partner.setRecipientid(message.getRecipientuserid());
            partner.setCreateTime(message.getReceivedate());
            String text=message.getTextHtml();
            if(StringUtils.isNotBlank(text)){
                String[] text1=text.split("</strong><br><br>");
                String[] text2=text1[1].split("<br/><br>");
                partner.setBody(text2[0]);
            }
            addMessages.add(partner);
        }
        Object[] addMessages1=addMessages.toArray();
        for(int i=0;i<addMessages1.length;i++){
            for(int j=i+1;j<addMessages1.length;j++){
                TradingOrderAddMemberMessageAAQToPartner l1= (TradingOrderAddMemberMessageAAQToPartner) addMessages1[i];
                TradingOrderAddMemberMessageAAQToPartner l2= (TradingOrderAddMemberMessageAAQToPartner) addMessages1[j];
                if(l1.getCreateTime().after(l2.getCreateTime())){
                    addMessages1[i]=l2;
                    addMessages1[j]=l1;
                }
            }
        }

        List<TradingOrderGetOrders> lists=new ArrayList<TradingOrderGetOrders>();
        List<TradingOrderGetOrders> lists1=new ArrayList<TradingOrderGetOrders>();
        if(messageList!=null&&messageList.size()>0){
            modelMap.put("message",messageList.get(0));
            modelMap.put("sender",messageList.get(0).getSender());
            modelMap.put("recipient",messageList.get(0).getRecipientuserid());
        }
        lists=iTradingOrderGetOrders.selectOrderGetOrdersByBuyerAndItemid(itemid,recipientid);
        if(lists!=null&&lists.size()>0){
            lists1=iTradingOrderGetOrders.selectOrderGetOrdersByTransactionId(lists.get(0).getTransactionid(),lists.get(0).getSelleruserid());
        }
        List<String> palpays=new ArrayList<String>();
        List<String> grossdetailamounts=new ArrayList<String>();
        List<String> pictures=new ArrayList<String>();
        List<PaypalVO> accs=new ArrayList<PaypalVO>();
        for(TradingOrderGetOrders order:lists){
            List<TradingOrderGetSellerTransactions> sellerTransactions=iTradingOrderGetSellerTransactions.selectTradingOrderGetSellerTransactionsByTransactionId(order.getTransactionid());
            if(sellerTransactions!=null&&sellerTransactions.size()>0){
                palpays.add(sellerTransactions.get(0).getExternaltransactionid());
                /*UsercontrollerEbayAccount u= iUsercontrollerEbayAccount.selectByEbayAccount(order.getSelleruserid());
                Map map =new HashMap();
                map.put("paypalId",u.getId());
                map.put("transactionID",sellerTransactions.get(0).getExternaltransactionid());
                PaypalVO acc = payPalService.getTransactionDetails(map);*/
                Map map =new HashMap();
                map.put("paypalId",1l);
                map.put("transactionID","4RJ37607494399203");
                PaypalVO acc = payPalService.getTransactionDetails(map);
                accs.add(acc);
            }else{
                palpays.add("");
            }
            List<TradingOrderGetAccount> accountlist=iTradingOrderGetAccount.selectTradingOrderGetAccountByTransactionId(order.getTransactionid());
            if(accountlist!=null&&accountlist.size()>0){
                for(TradingOrderGetAccount account:accountlist){
                    if("成交費".equals(account.getDescription())){
                        grossdetailamounts.add(account.getGrossdetailamount());
                    }else{
                        grossdetailamounts.add("");
                    }
                }
            }
            String ItemId=order.getItemid();
            List<TradingOrderGetItem> itemList= iTradingOrderGetItem.selectOrderGetItemByItemId(ItemId);
            if(itemList!=null&&itemList.size()>0){
                Long pictureid=itemList.get(0).getPicturedetailsId();
                List<TradingOrderPictureDetails> pictureDetailses=iTradingOrderPictureDetails.selectOrderGetItemById(pictureid);
                if(pictureDetailses!=null&&pictureDetailses.size()>0){
                    pictures.add(pictureDetailses.get(0).getPictureurl());
                }else{
                    pictures.add("");
                }
            }

        }
        List<TradingOrderSenderAddress> senderAddresses=new ArrayList<TradingOrderSenderAddress>();
        if(lists1!=null&&lists1.size()>0){
            senderAddresses=iTradingOrderSenderAddress.selectOrderSenderAddressByOrderId(lists1.get(0).getOrderid());
            modelMap.put("orderId",lists1.get(0).getOrderid());
            modelMap.put("order",lists1.get(0));
            modelMap.put("sender",lists1.get(0).getBuyeruserid());
            modelMap.put("recipient",lists1.get(0).getSelleruserid());
        }
        TradingOrderSenderAddress type1=new TradingOrderSenderAddress();
        TradingOrderSenderAddress type2=new TradingOrderSenderAddress();
        for(TradingOrderSenderAddress senderAddresse:senderAddresses){
            if("1".equals(senderAddresse.getType())){
                type1=senderAddresse;
            }
            if("2".equals(senderAddresse.getType())){
                type2=senderAddresse;
            }
        }
        if(lists!=null&&lists.size()>0){
            modelMap.put("flag","true");
        }else{
            modelMap.put("flag","false");
        }
       /* modelMap.put("messages",messages);
        modelMap.put("messageID",messages.get(0).getMessageid());*/
        modelMap.put("addMessage1",addMessages1);
        modelMap.put("orders",lists);
        String rootpath=request.getContextPath();
        modelMap.put("rootpath",rootpath);
        modelMap.put("addresstype1",type1);
        modelMap.put("addresstype2",type2);
        modelMap.put("paypals",palpays);
        modelMap.put("grossdetailamounts",grossdetailamounts);
        modelMap.put("pictures",pictures);

        modelMap.put("accs",accs);
        return forword("orders/order/viewOrderGetOrders",modelMap);
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
        //测试环境
       /* UsercontrollerDevAccountExtend dev = userInfoService.getDevInfo(null);
        dev.setApiSiteid("0");*/
        //真实环境
       /* UsercontrollerDevAccountExtend dev=new UsercontrollerDevAccountExtend();
        dev.setApiDevName("5d70d647-b1e2-4c7c-a034-b343d58ca425");
        dev.setApiAppName("sandpoin-23af-4f47-a304-242ffed6ff5b");
        dev.setApiCertName("165cae7e-4264-4244-adff-e11c3aea204e");
        dev.setApiCompatibilityLevel("881");
        dev.setApiSiteid("0");*/


       /* dev.setApiCallName(APINameStatic.GetMyMessages);
        request.getSession().setAttribute("dveId", dev);*/
        List<UsercontrollerEbayAccountExtend> ebays = userInfoService.getEbayAccountForCurrUser(new HashMap(),Page.newAOnePage());
        Map m=new HashMap();
        m.put("messageID",messageID);
        m.put("ebays",ebays);
        List<MessageGetmymessageQuery> messages=iTradingMessageGetmymessage.selectMessageGetmymessageBySender(m);
       /* for(MessageGetmymessageQuery message:messages){
            if(message.getTextHtml()==null||"".equals(message.getTextHtml())){
                Map parms=new HashMap();
                parms.put("messageId", message.getMessageid());
                parms.put("ebayId",message.getEbayAccountId());
                parms.put("devAccount",dev);
                //测试环境
                parms.put("url",apiUrl);
                //真实环境
               *//* parms.put("url","https://api.ebay.com/ws/api.dll");*//*
                parms.put("userInfoService",userInfoService);
                String content=GetMyMessageAPI.getContent(parms);
                message.setTextHtml(content);
                message.setRead("true");
                iTradingMessageGetmymessage.saveMessageGetmymessage(message);
            }
        }*/
        List<TradingOrderAddMemberMessageAAQToPartner> addMessages=new ArrayList<TradingOrderAddMemberMessageAAQToPartner>();
        List<TradingMessageGetmymessage> messages1=new ArrayList<TradingMessageGetmymessage>();
        List<TradingMessageGetmymessage> messageList=iTradingMessageGetmymessage.selectMessageGetmymessageByItemIdAndSender(messages.get(0).getItemid(),messages.get(0).getSender(),messages.get(0).getRecipientuserid());
        List<TradingOrderAddMemberMessageAAQToPartner> addmessageList=iTradingOrderAddMemberMessageAAQToPartner.selectTradingOrderAddMemberMessageAAQToPartnerByItemIdAndSender(messages.get(0).getItemid(),4,messages.get(0).getRecipientuserid(),messages.get(0).getSender());
        messages1.addAll(messageList);
        addMessages.addAll(addmessageList);
        for(TradingMessageGetmymessage message:messages1){
            TradingOrderAddMemberMessageAAQToPartner partner=new TradingOrderAddMemberMessageAAQToPartner();
            partner.setSender(message.getSender());
            partner.setSubject(message.getSubject());
            partner.setRecipientid(message.getRecipientuserid());
            partner.setCreateTime(message.getReceivedate());
            String text=message.getTextHtml();
            if(StringUtils.isNotBlank(text)){
                String[] text1=text.split("</strong><br><br>");
                String[] text2=text1[1].split("<br/><br>");
                partner.setBody(text2[0]);
            }
            addMessages.add(partner);
        }
        Object[] addMessages1=addMessages.toArray();
        for(int i=0;i<addMessages1.length;i++){
            for(int j=i+1;j<addMessages1.length;j++){
                TradingOrderAddMemberMessageAAQToPartner l1= (TradingOrderAddMemberMessageAAQToPartner) addMessages1[i];
                TradingOrderAddMemberMessageAAQToPartner l2= (TradingOrderAddMemberMessageAAQToPartner) addMessages1[j];
                if(l1.getCreateTime().after(l2.getCreateTime())){
                    addMessages1[i]=l2;
                    addMessages1[j]=l1;
                }
            }
        }

        List<TradingOrderGetOrders> lists=new ArrayList<TradingOrderGetOrders>();
        List<TradingOrderGetOrders> lists1=new ArrayList<TradingOrderGetOrders>();
        if(messages!=null&&messages.size()>0){
            modelMap.put("message",messages.get(0));
            modelMap.put("sender",messages.get(0).getSender());
            modelMap.put("recipient",messages.get(0).getRecipientuserid());
            lists=iTradingOrderGetOrders.selectOrderGetOrdersByBuyerAndItemid(messages.get(0).getItemid(),messages.get(0).getSender());
        }
        if(lists!=null&&lists.size()>0){
            lists1=iTradingOrderGetOrders.selectOrderGetOrdersByTransactionId(lists.get(0).getTransactionid(),lists.get(0).getSelleruserid());
        }
        List<String> palpays=new ArrayList<String>();
        List<String> grossdetailamounts=new ArrayList<String>();
        List<String> pictures=new ArrayList<String>();
        List<PaypalVO> accs=new ArrayList<PaypalVO>();
        for(TradingOrderGetOrders order:lists){
            List<TradingOrderGetSellerTransactions> sellerTransactions=iTradingOrderGetSellerTransactions.selectTradingOrderGetSellerTransactionsByTransactionId(order.getTransactionid());
            if(sellerTransactions!=null&&sellerTransactions.size()>0){
                palpays.add(sellerTransactions.get(0).getExternaltransactionid());
                /*UsercontrollerEbayAccount u= iUsercontrollerEbayAccount.selectByEbayAccount(order.getSelleruserid());
                Map map =new HashMap();
                map.put("paypalId",u.getId());
                map.put("transactionID",sellerTransactions.get(0).getExternaltransactionid());
                PaypalVO acc = payPalService.getTransactionDetails(map);*/
                Map map =new HashMap();
                map.put("paypalId",1l);
                map.put("transactionID","4RJ37607494399203");
                PaypalVO acc = payPalService.getTransactionDetails(map);
                accs.add(acc);
            }else{
                palpays.add("");
            }
            List<TradingOrderGetAccount> accountlist=iTradingOrderGetAccount.selectTradingOrderGetAccountByTransactionId(order.getTransactionid());
            if(accountlist!=null&&accountlist.size()>0){
                for(TradingOrderGetAccount account:accountlist){
                    if("成交費".equals(account.getDescription())){
                        grossdetailamounts.add(account.getGrossdetailamount());
                    }else{
                        grossdetailamounts.add("");
                    }
                }
            }
            String ItemId=order.getItemid();
            List<TradingOrderGetItem> itemList= iTradingOrderGetItem.selectOrderGetItemByItemId(ItemId);
            if(itemList!=null&&itemList.size()>0){
                Long pictureid=itemList.get(0).getPicturedetailsId();
                List<TradingOrderPictureDetails> pictureDetailses=iTradingOrderPictureDetails.selectOrderGetItemById(pictureid);
                if(pictureDetailses!=null&&pictureDetailses.size()>0){
                    pictures.add(pictureDetailses.get(0).getPictureurl());
                }else{
                    pictures.add("");
                }
            }

        }
        List<TradingOrderSenderAddress> senderAddresses=new ArrayList<TradingOrderSenderAddress>();
        if(lists1!=null&&lists1.size()>0){
            senderAddresses=iTradingOrderSenderAddress.selectOrderSenderAddressByOrderId(lists1.get(0).getOrderid());
            modelMap.put("orderId",lists1.get(0).getOrderid());
            modelMap.put("order",lists1.get(0));
            modelMap.put("sender",lists1.get(0).getBuyeruserid());
            modelMap.put("recipient",lists1.get(0).getSelleruserid());
        }
        TradingOrderSenderAddress type1=new TradingOrderSenderAddress();
        TradingOrderSenderAddress type2=new TradingOrderSenderAddress();
        for(TradingOrderSenderAddress senderAddresse:senderAddresses){
            if("1".equals(senderAddresse.getType())){
                type1=senderAddresse;
            }
            if("2".equals(senderAddresse.getType())){
                type2=senderAddresse;
            }
        }
        if(lists!=null&&lists.size()>0){
            modelMap.put("flag","true");
        }else{
            modelMap.put("flag","false");
        }
       /* modelMap.put("messages",messages);
        modelMap.put("messageID",messages.get(0).getMessageid());*/
        modelMap.put("addMessage1",addMessages1);
        modelMap.put("orders",lists);
        String rootpath=request.getContextPath();
        modelMap.put("rootpath",rootpath);
        modelMap.put("addresstype1",type1);
        modelMap.put("addresstype2",type2);
        modelMap.put("paypals",palpays);
        modelMap.put("grossdetailamounts",grossdetailamounts);
        modelMap.put("pictures",pictures);

        modelMap.put("accs",accs);
        return forword("orders/order/viewOrderGetOrders",modelMap);
      /*  return forword("MessageGetmymessage/viewMessageGetmymessage",modelMap);*/
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

   /* @RequestMapping("/ajax/saveMessageGetmymessage.do")
    @AvoidDuplicateSubmission(needRemoveToken = true)
    @ResponseBody
    *//**回复消息*//*
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
*/
    @RequestMapping("/GetmymessageEbay.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    @ResponseBody
    public ModelAndView GetmymessageEbay(@ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
      /*  Asserts.assertTrue(commonParmVO.getId() != null, "参数获取失败！");*/
        List<UsercontrollerEbayAccountExtend> ebays = userInfoService.getEbayAccountForCurrUser(new HashMap(),Page.newAOnePage());
        modelMap.put("ebays",ebays);
        return forword("MessageGetmymessage/GetmymessageEbay",modelMap);
    }
    //选择消息模板初始化
    @RequestMapping("/selectSendMessage.do")
    public ModelAndView selectSendMessage(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        String transactionid=request.getParameter("transactionid");
        String seller=request.getParameter("seller");
        String paypal=request.getParameter("paypal");
        List<TradingOrderGetOrders> orders=iTradingOrderGetOrders.selectOrderGetOrdersByTransactionId(transactionid,seller);
        if(orders.size()>0){
            List<TradingOrderGetItem> items=iTradingOrderGetItem.selectOrderGetItemByItemId(orders.get(0).getItemid());
            modelMap.put("order",orders.get(0));
            if(items.size()>0){
                modelMap.put("item",items.get(0));
            }
        }
        modelMap.put("date",new Date());
        modelMap.put("paypal",paypal);
        return forword("MessageGetmymessage/selectSendMessage",modelMap);
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
        //测试环境
        UsercontrollerDevAccountExtend d = new UsercontrollerDevAccountExtend();
        d.setApiSiteid("0");
        //真实环境
        /*UsercontrollerDevAccountExtend d=new UsercontrollerDevAccountExtend();
        d.setApiDevName("5d70d647-b1e2-4c7c-a034-b343d58ca425");
        d.setApiAppName("sandpoin-23af-4f47-a304-242ffed6ff5b");
        d.setApiCertName("165cae7e-4264-4244-adff-e11c3aea204e");
        d.setApiCompatibilityLevel("881");
        d.setApiSiteid("0");*/
        Map map=new HashMap();
        d.setApiCallName(APINameStatic.GetMyMessages);
        request.getSession().setAttribute("dveId", d);

   /*    Date startTime2= com.base.utils.common.DateUtils.subDays(new Date(),6);
        Date endTime= DateUtils.addDays(startTime2, 6);*/
        Date startTime2= com.base.utils.common.DateUtils.subDays(new Date(),90);
        Date endTime= DateUtils.addDays(startTime2, 90);
        /*MutualWithdrawalAgreementLate*/


        Date end1= com.base.utils.common.DateUtils.turnToDateEnd(endTime);
        String start=DateUtils.DateToString(startTime2);
        String end= DateUtils.DateToString(end1);
        //测试环境
        String token=userInfoService.getTokenByEbayID(ebay);
        //真实环境
       /* String token="AgAAAA**AQAAAA**aAAAAA**jek4VA**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6AFlIWnC5iEpAidj6x9nY+seQ**tSsCAA**AAMAAA**y8BaJPw6GUdbbbco8zXEwRR4Ttr9sLd78jL0FyYa0yonvk5hz1RY6DtKkaDtn9NuzluKeFZoqsNbujZP48S4QZhHVa5Dp0bDGqBdKaosolzsrPDm8qozoxbsTiWY8X/M5xev/YU2zJ42/JRGDlEdnQhwCASG1BcSo+DqXuG3asbj0INJr4/HsArf8cCYsPQCtUDkq5QJY6Rvil+Kla/dGhViTQ3gt7a4t3KjxKH+/jlhDU/6sUEKlvb2nY1gCmX8S9pU48c+4Vy6G6NpfcGUcIG/TXFWBTqU0R+v+/6DOIfDW8s90rrLSVMGFqnRxA2sexdEmVhyF5csBmv9+TVfjdyEZK5UgvDqWJHesuDMFTr0KIc8EtdnTQaE3YeZch15DdoEbqcyyBQBZHidBPdDHz/DkpTg7iq1953yKodm2y0mW6aaYAfc5beW+PoqMW8C3WwGJmWZqh3dBi+QEKznEJ9SRg43Bc3q2344JFY7YpIEfJDaQ36BHRcIZxLew8v7RIGL5YYO1BBdTolVV9/eMCQDsUB0mUeMYjxnH5w0K/6CDmJ9WNMQTblNol0x3vhJbil1L/CMP9KGEHj5Yqx0003MLL9Yod7nL89Zpy+a8I/E5byxFt21KZTGE90Ot0LyLpRXsotDwIm5+ZdvATsU6mGADX4tk970CpCeM487v9fn1opouaCBvknCINqXoSeGXLQ7uZFpeqkWts1lIWh9vEuuiuZa4vNoL7aCr+93LTFnsO6AsZp7dmboQcI96I/o";
        */
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
        m.put("dev",d);
        taskMessageVO.setObjClass(m);
        SessionVO sessionVO= SessionCacheSupport.getSessionVO();
        taskMessageVO.setMessageTo(sessionVO.getId());
        //测试环境
        addApiTask.execDelayReturn(d, xml, apiUrl, taskMessageVO);
        //真实环境
        /*addApiTask.execDelayReturn(d, xml, "https://api.ebay.com/ws/api.dll", taskMessageVO);*/
        AjaxSupport.sendSuccessText("message", "操作成功！结果请稍后查看消息！");
    }
}
