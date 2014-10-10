package com.order.controller;

import com.base.database.publicd.model.PublicUserConfig;
import com.base.database.trading.model.*;
import com.base.domains.CommonParmVO;
import com.base.domains.SessionVO;
import com.base.domains.querypojos.OrderGetOrdersQuery;
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
import com.base.utils.xmlutils.SamplePaseXml;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
import com.publicd.service.IPublicUserConfig;
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

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by Administrtor on 2014/8/13.
 */
@Controller
@RequestMapping("order")
public class GetOrdersController extends BaseAction {

    static Logger logger = Logger.getLogger(GetOrdersController.class);
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private SystemUserManagerService systemUserManagerService;
    @Autowired
    private ITradingOrderGetOrders iTradingOrderGetOrders;
    @Autowired
    private ITradingOrderGetItem iTradingOrderGetItem;
    @Autowired
    private ITradingOrderPictureDetails iTradingOrderPictureDetails;
    @Autowired
    private  ITradingMessageGetmymessage iTradingMessageGetmymessage;
    @Autowired
    private ITradingOrderSenderAddress iTradingOrderSenderAddress;
    @Autowired
    private  ITradingOrderAddMemberMessageAAQToPartner iTradingOrderAddMemberMessageAAQToPartner;
    @Autowired
    private  ITradingOrderGetAccount iTradingOrderGetAccount;
    @Autowired
    private  ITradingOrderGetSellerTransactions iTradingOrderGetSellerTransactions;
    @Autowired
    private IPublicUserConfig iPublicUserConfig;
    @Autowired
    private ITradingOrderOrderVariationSpecifics iTradingOrderOrderVariationSpecifics;
    @Autowired
    private ITradingFeedBackDetail iTradingFeedBackDetail ;
   /* @Autowired
    private ITradingOrderVariation iTradingOrderVariation;
    @Autowired
    private ITradingOrderVariationSpecifics iTradingOrderVariationSpecifics;*/
    @Value("${EBAY.API.URL}")
    private String apiUrl;

    @RequestMapping("/getOrdersList.do")
    public ModelAndView OrdersList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        List<PublicUserConfig> configs=new ArrayList<PublicUserConfig>();
        List<PublicUserConfig> list=iPublicUserConfig.selectUserConfigByItemType("orderFolder");
        for(PublicUserConfig config:list){
            String value=config.getConfigValue();
            if(StringUtils.isNotBlank(value)){
                configs.add(config);
            }
        }
        modelMap.put("folders",configs);
        return forword("/orders/order/getOrdersList",modelMap);
    }
    //选择文件夹
    @RequestMapping("/selectTabRemark.do")
    public ModelAndView selectTabRemark(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        String folderType=request.getParameter("folderType");
        List<PublicUserConfig> list=iPublicUserConfig.selectUserConfigByItemType(folderType);
        modelMap.put("folders",list);
        modelMap.put("folderType",folderType);
        return forword("/orders/order/selectTabRemark",modelMap);
    }
    //将文件夹设置到order中
    @RequestMapping("/saveOrderTabremark.do")
    public void saveOrderTabremark(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws Exception {
        String id=request.getParameter("id");
        PublicUserConfig config=iPublicUserConfig.selectUserConfigById(Long.valueOf(id));
        if(config==null){
            AjaxSupport.sendFailText("fail","文件夹不存在");
            return;
        }
        config.setConfigValue("true");
        iPublicUserConfig.saveUserConfig(config);
        AjaxSupport.sendSuccessText("", "添加文件夹成功");
    }
    //删除文件夹
    @RequestMapping("/removeOrderTabremark.do")
    public void removeOrderTabremark(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws Exception {
        String id=request.getParameter("id");
        PublicUserConfig config=iPublicUserConfig.selectUserConfigById(Long.valueOf(id));
        if(config!=null){
            List<UsercontrollerEbayAccountExtend> ebays=userInfoService.getEbayAccountForCurrUser();
            List<String> ebayNames=new ArrayList<String>();
            for(UsercontrollerEbayAccountExtend ebay:ebays){
                ebayNames.add(ebay.getEbayName());
            }
            List<TradingOrderGetOrders> orders=iTradingOrderGetOrders.selectOrderGetOrdersByFolder(config.getId()+"",ebayNames);
            if(orders!=null&&orders.size()>0){
                for(TradingOrderGetOrders order:orders){
                    order.setFolder(null);
                    iTradingOrderGetOrders.saveOrderGetOrders(order);
                }
            }
            iPublicUserConfig.deleteUserConfig(config);
            AjaxSupport.sendSuccessText("", "删除文件夹成功");
            return;
        }else{
            AjaxSupport.sendFailText("fail","文件夹不存在");
        }

    }

    //新建文件夹
    @RequestMapping("/addTabRemark.do")
    public ModelAndView addTabRemark(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        String folderType=request.getParameter("folderType");
        modelMap.put("folderType",folderType);
        return forword("/orders/order/addTabRemark",modelMap);
    }
    //添加备注
    @RequestMapping("/addComment.do")
    public ModelAndView addComment(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        String orderid=request.getParameter("orderid");
        modelMap.put("orderid",orderid);
        return forword("/orders/order/addComment",modelMap);
    }
    //移动订单到指定文件夹初始化
    @RequestMapping("/moveFolder.do")
    public ModelAndView moveFolder(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        List<String> orderids=new ArrayList<String>();
        int i=0;
        while(i>=0){
            String order=request.getParameter("orderid["+i+"]");
            if(order!=null){
                orderids.add(order);
                i++;
            }else{
                i=-1;
            }
        }
        List<PublicUserConfig> list=iPublicUserConfig.selectUserConfigByItemType("orderFolder");
        List<PublicUserConfig> configs=new ArrayList<PublicUserConfig>();
        for(PublicUserConfig config:list){
            if(StringUtils.isNotBlank(config.getConfigValue())){
                configs.add(config);
            }
        }
        modelMap.put("folders",configs);
        modelMap.put("orderids",orderids);
        return forword("/orders/order/moveFolder",modelMap);
    }
    //保存订单到指定文件夹
    @RequestMapping("/saveFolder.do")
    public void saveFolder(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws Exception {
        String folderId=request.getParameter("folderId");
        int i=0;
        while(i>=0){
            String orderid=request.getParameter("orderid["+i+"]");
            if(orderid!=null&&StringUtils.isNotBlank(folderId)){
                List<TradingOrderGetOrders> orders=iTradingOrderGetOrders.selectOrderGetOrdersByOrderId(orderid);
                for(TradingOrderGetOrders order:orders){
                    order.setFolder(folderId);
                    iTradingOrderGetOrders.saveOrderGetOrders(order);
                }
                i++;
            }else if(!StringUtils.isNotBlank(folderId)){
                i=-2;
            }else{
                i=-1;
            }
        }
        if(i==-2){
            AjaxSupport.sendFailText("fail","文件夹不存在");
            return;
        }
        AjaxSupport.sendSuccessText("","添加备注成功");

    }
    //保存文件夹
    @RequestMapping("/saveComment.do")
    public void saveComment(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws Exception {
        String orderid=request.getParameter("orderid");
        String comment=request.getParameter("comment");
        if(StringUtils.isNotBlank(orderid)){
            List<TradingOrderGetOrders> orders=iTradingOrderGetOrders.selectOrderGetOrdersByOrderId(orderid);
            for(TradingOrderGetOrders order:orders){
                order.setComment(comment);
                iTradingOrderGetOrders.saveOrderGetOrders(order);
            }
            AjaxSupport.sendSuccessText("","添加备注成功");
        }else{
            AjaxSupport.sendFailText("fail","该订单不存在");
        }
    }
    //保存文件夹
    @RequestMapping("/saveTabremark.do")
    public void saveTabremark(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws Exception {
        String tabName=request.getParameter("tabName");
        String folderType=request.getParameter("folderType");
        if(!StringUtils.isNotBlank(tabName)){
            AjaxSupport.sendFailText("fail","文件夹名称不能为空");
            return;
        }
        PublicUserConfig config=new PublicUserConfig();
        config.setConfigType(folderType);
        config.setConfigName(tabName);
        iPublicUserConfig.saveUserConfig(config);
        AjaxSupport.sendSuccessText("", "文件夹保存成功");
    }
    /**获取list数据的ajax方法*/
    @RequestMapping("/ajax/loadOrdersList.do")
    @ResponseBody
    public void loadOrdersList(CommonParmVO commonParmVO,HttpServletRequest request) throws Exception {
        String countryQ=request.getParameter("countryQ");
        String typeQ=request.getParameter("typeQ");
        String daysQ=request.getParameter("daysQ");
        String itemType=request.getParameter("itemType");
        String content=request.getParameter("content");
        String status=request.getParameter("status");
        String folderId=request.getParameter("folderId");
        /**分页组装*/
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        Page page=jsonBean.toPage();
        Map ebayMap=new HashMap();
        List<UsercontrollerEbayAccountExtend> ebays=systemUserManagerService.queryCurrAllEbay(ebayMap);
        Map map=new HashMap();
        Date starttime=null;
        Date endtime=null;
        if(StringUtils.isNotBlank(daysQ)){
            if("2".equals(daysQ)){
                starttime=DateUtils.subDays(new Date(),1);
                Date endTime= DateUtils.addDays(starttime, 0);
                endtime= com.base.utils.common.DateUtils.turnToDateEnd(endTime);
            }else{
                int day=Integer.parseInt(daysQ);
                starttime=DateUtils.subDays(new Date(),day-1);
                Date endTime= DateUtils.addDays(starttime,day-1);
                endtime= com.base.utils.common.DateUtils.turnToDateEnd(endTime);
            }
        }
        if(!StringUtils.isNotBlank(status)||"All".equals(status)){
            status=null;
        }
        if(!StringUtils.isNotBlank(countryQ)){
            countryQ=null;
        }
        if(!StringUtils.isNotBlank(typeQ)){
            typeQ=null;
        }
        if(!StringUtils.isNotBlank(itemType)){
            itemType=null;
        }
        if(!StringUtils.isNotBlank(folderId)){
            folderId=null;
        }
        map.put("ebays",ebays);
        map.put("starttime",starttime);
        map.put("endtime",endtime);
        map.put("status",status);
        map.put("countryQ",countryQ);
        map.put("typeQ",typeQ);
        map.put("content",content);
        map.put("folderId",folderId);
        map.put("itemType",itemType);
        List<OrderGetOrdersQuery> lists= this.iTradingOrderGetOrders.selectOrderGetOrdersByGroupList(map,page);
        for(OrderGetOrdersQuery list:lists){
            String itemid=list.getItemid();
            List<TradingOrderGetItem> itemList= iTradingOrderGetItem.selectOrderGetItemByItemId(itemid);
            if(itemList!=null&&itemList.size()>0){
                Long pictureid=itemList.get(0).getPicturedetailsId();
                List<TradingOrderPictureDetails> pictureDetailses=iTradingOrderPictureDetails.selectOrderGetItemById(pictureid);
                if(pictureDetailses!=null&&pictureDetailses.size()>0){
                    list.setPictrue(pictureDetailses.get(0).getPictureurl());
                }
             /*   List<TradingDataDictionary> dictionaries=iTradingDataDictionary.s()*/
                list.setItemSite(itemList.get(0).getSite());
            }
            String variationSKU=list.getVariationsku();
            if(StringUtils.isNotBlank(variationSKU)){
                List<TradingOrderOrderVariationSpecifics> specificses=iTradingOrderOrderVariationSpecifics.selectOrderOrderVariationSpecificsBySku(variationSKU);
                List<String> s=new ArrayList<String>();
                if(specificses!=null&&specificses.size()>0){
                    for(TradingOrderOrderVariationSpecifics specificse:specificses){
                        String va=specificse.getName()+":"+specificse.getValue();
                        s.add(va);
                    }
                }
                list.setVariationspecificsMap(s);
            }
            List<TradingOrderAddMemberMessageAAQToPartner> partners=iTradingOrderAddMemberMessageAAQToPartner.selectTradingOrderAddMemberMessageAAQToPartnerByTransactionId(list.getTransactionid(),3);
            if(partners!=null&&partners.size()>0){
                list.setMessage(partners.get(0).getBody());
            }
            List<TradingOrderGetSellerTransactions> transactionses=iTradingOrderGetSellerTransactions.selectTradingOrderGetSellerTransactionsByTransactionId(list.getTransactionid());
           /* iTradingOrderVariation.selectOrderVariationByItemId()*/
            for(TradingOrderGetSellerTransactions transaction:transactionses){
                list.setPaypalPaidTime(transaction.getPaidtime());
                list.setPaypalPaymentTime(transaction.getPaymenttime());
                list.setExternalTransactionID(transaction.getExternaltransactionid());
            }
            String url="http://www.sandbox.ebay.com/itm/"+list.getItemid();
            list.setItemUrl(url);
            if("notAllComplete".equals(status)){
                list.setFlagNotAllComplete(true);
            }else{
                list.setFlagNotAllComplete(false);
            }
            TradingFeedBackDetail feedBackDetail=iTradingFeedBackDetail.selectFeedBackDetailByTransactionId(list.getTransactionid());
            if(feedBackDetail!=null){
                list.setFeedbackMessage("true");
            }
        }
        jsonBean.setList(lists);
        jsonBean.setTotal((int)page.getTotalCount());
        AjaxSupport.sendSuccessText("", jsonBean);
    }

    /**
     * 初始化同步订单界面
     */
    @RequestMapping("/GetOrder.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    @ResponseBody
    public ModelAndView GetOrder(@ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
        List<UsercontrollerEbayAccountExtend> ebays = userInfoService.getEbayAccountForCurrUser();
        modelMap.put("ebays",ebays);
        return forword("orders/order/synOrders",modelMap);
    }

    /**
     * 发货时修改订单(运输)
     */
    @RequestMapping("/ajax/updateOrder.do")
    @AvoidDuplicateSubmission(needRemoveToken = true)
    @ResponseBody
    public void updateOrder( HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws Exception {
        String orderid=request.getParameter("id");
        String freight=request.getParameter("freight");
        /*String shippedtime=request.getParameter("shippedtime");*/
        /*Date shipptime=null;
        if(StringUtils.isNotBlank(shippedtime)){
            int year= Integer.parseInt(shippedtime.substring(0,4));
            int month= Integer.parseInt(shippedtime.substring(5,7));
            int day= Integer.parseInt(shippedtime.substring(8,10));
            int hour= Integer.parseInt(shippedtime.substring(11,13));
            int minite= Integer.parseInt(shippedtime.substring(14, 16));
            int ss=0;
            shipptime=DateUtils.buildDateTime(year,month,day,hour,minite,0);
        }*/
        List<TradingOrderGetOrders> orderList= iTradingOrderGetOrders.selectOrderGetOrdersByOrderId(orderid);
        for(TradingOrderGetOrders order:orderList){
            /*order.setShippedtime(shipptime);*/
            if(StringUtils.isNotBlank(freight)){
                order.setActualshippingcost(Double.valueOf(freight));
            }
           /* order.setOrderstatus("Shipped");*/
            iTradingOrderGetOrders.saveOrderGetOrders(order);
        }
        AjaxSupport.sendSuccessText("","操作成功!");
    }
    /**
     * 查看订单详情
     */
    @RequestMapping("/viewOrderGetOrders.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView viewTemplateInitTable(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        String orderid=request.getParameter("orderid");
        String transactionid=request.getParameter("transactionid");
        String selleruserid=request.getParameter("selleruserid");
        List<TradingOrderGetOrders> lists=iTradingOrderGetOrders.selectOrderGetOrdersByOrderId(orderid);
        List<TradingOrderGetOrders> lists1=iTradingOrderGetOrders.selectOrderGetOrdersByTransactionId(transactionid,selleruserid);
        modelMap.put("order",lists1.get(0));
        modelMap.put("orders",lists);
        modelMap.put("orderId",orderid);
        String rootpath=request.getContextPath();
        modelMap.put("rootpath",rootpath);
        List<TradingMessageGetmymessage> messages=new ArrayList<TradingMessageGetmymessage>();
        List<TradingOrderAddMemberMessageAAQToPartner> addMessages=new ArrayList<TradingOrderAddMemberMessageAAQToPartner>();
        List<String> palpays=new ArrayList<String>();
        List<String> grossdetailamounts=new ArrayList<String>();
        List<String> pictures=new ArrayList<String>();
        for(TradingOrderGetOrders order:lists){
            List<TradingOrderGetSellerTransactions> sellerTransactions=iTradingOrderGetSellerTransactions.selectTradingOrderGetSellerTransactionsByTransactionId(order.getTransactionid());
            if(sellerTransactions!=null&&sellerTransactions.size()>0){
                palpays.add(sellerTransactions.get(0).getExternaltransactionid());
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
        List<TradingOrderSenderAddress> senderAddresses=iTradingOrderSenderAddress.selectOrderSenderAddressByOrderId(orderid);
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
        String itemid=lists.get(0).getItemid();
        List<TradingMessageGetmymessage> messageList=iTradingMessageGetmymessage.selectMessageGetmymessageByItemIdAndSender(itemid,lists.get(0).getBuyeruserid(),lists.get(0).getSelleruserid());
        List<TradingOrderAddMemberMessageAAQToPartner> addmessageList=iTradingOrderAddMemberMessageAAQToPartner.selectTradingOrderAddMemberMessageAAQToPartnerByItemIdAndSender(itemid,4,lists.get(0).getSelleruserid(),lists.get(0).getBuyeruserid());
        messages.addAll(messageList);
        addMessages.addAll(addmessageList);
        for(TradingMessageGetmymessage message:messages){
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
        modelMap.put("messages1", messages);
        modelMap.put("addMessage1",addMessages1);
        modelMap.put("addresstype1",type1);
        modelMap.put("addresstype2",type2);
        modelMap.put("paypals",palpays);
        modelMap.put("grossdetailamounts",grossdetailamounts);
        modelMap.put("pictures",pictures);
        modelMap.put("sender",lists1.get(0).getBuyeruserid());
        modelMap.put("recipient",lists1.get(0).getSelleruserid());
        /*Map<String,String> map=new HashMap<String, String>();
        map.put("orderStatus","Completed");
        map.put("selleraccount",lists.get(0).getSelleruserid());
        map.put("buyaccount",lists.get(0).getBuyeruserid());
        List<OrderGetOrdersQuery> querys= this.iTradingOrderGetOrders.selectOrderGetOrdersByGroupList(map,page);*/
        return forword("orders/order/viewOrderGetOrders",modelMap);
    }
    /*
     *修改订单状态初始化
     */
    @RequestMapping("/modifyOrderStatus.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView modifyOrderStatus(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        String transactionid=request.getParameter("transactionid");
        String selleruserid=request.getParameter("selleruserid");
        List<TradingOrderGetOrders> orderList=iTradingOrderGetOrders.selectOrderGetOrdersByTransactionId(transactionid,selleruserid);
        modelMap.put("order",orderList.get(0));
        return forword("orders/order/modifyOrderStatus",modelMap);
    }
    /*
     *修改订单状态
     */
    @RequestMapping("/apiModifyOrdersMessage.do")
    @AvoidDuplicateSubmission(needRemoveToken = true)
    @ResponseBody
    public void apiModifyOrdersMessage(CommonParmVO commonParmVO,HttpServletRequest request) throws Exception {
        String itemid= request.getParameter("itemid");
        String transactionid=request.getParameter("transactionid");
        String shippStatus=request.getParameter("shippStatus");
        String ShipmentTrackingNumber=request.getParameter("ShipmentTrackingNumber");
        String ShippingCarrierUsed=request.getParameter("ShippingCarrierUsed");
        UsercontrollerDevAccountExtend d = userInfoService.getDevInfo(null);//开发者帐号id
        d.setApiSiteid("0");
        d.setApiCallName(APINameStatic.CompleteSale);
        Map<String,String> map=new HashMap();
        String ebayName=request.getParameter("selleruserid");
        List<UsercontrollerEbayAccountExtend> dList= userInfoService.getEbayAccountForCurrUser();
        String token=null;
        for(UsercontrollerEbayAccountExtend list:dList){
            if(StringUtils.isNotBlank(ebayName)&&ebayName.equals(list.getEbayName())){
                token=list.getEbayToken();
            }
        }
        map.put("token", token);
        map.put("shippStatus",shippStatus);
        map.put("itemid",itemid);
        map.put("ShipmentTrackingNumber",ShipmentTrackingNumber);
        map.put("ShippingCarrierUsed",ShippingCarrierUsed);
        map.put("transactionid",transactionid);
        String xml = BindAccountAPI.getModifyOrderStatus(map);
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
            List<TradingOrderGetOrders> orderList=iTradingOrderGetOrders.selectOrderGetOrdersByTransactionId(transactionid,ebayName);
            TradingOrderGetOrders order=orderList.get(0);
            order.setShipmenttrackingnumber(ShipmentTrackingNumber);
            order.setShippingcarrierused(ShippingCarrierUsed);
            iTradingOrderGetOrders.saveOrderGetOrders(order);
            AjaxSupport.sendSuccessText("success", "订单状态修改成功");
        }else{
            AjaxSupport.sendFailText("fail", "获取必要的参数失败！请稍后重试");
        }

    }


    /*
     *保存寄件人地址/退货地址
     */
    @RequestMapping("/ajax/saveReturnAddress.do")
    @AvoidDuplicateSubmission(needRemoveToken = true)
    @ResponseBody
    public void saveReturnAddress( HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws Exception {
        String returnAddress=request.getParameter("returnAddress");
        String returnContacts=request.getParameter("returnContacts");
        String returnCompany=request.getParameter("returnCompany");
        String returnCountry=request.getParameter("returnCountry");
        String returnProvince=request.getParameter("returnProvince");
        String returnCity=request.getParameter("returnCity");
        String returnArea=request.getParameter("returnArea");
        String returnStreet=request.getParameter("returnStreet");
        String returnPostCode=request.getParameter("returnPostCode");
        String returnPhone=request.getParameter("returnPhone");
        String returnEmail=request.getParameter("returnEmail");
        String orderid=request.getParameter("orderId");
        TradingOrderSenderAddress senderAddress=new TradingOrderSenderAddress();
        senderAddress.setType(returnAddress);
        senderAddress.setContacts(returnContacts);
        senderAddress.setCompany(returnCompany);
        senderAddress.setCountry(returnCountry);
        senderAddress.setProvince(returnProvince);
        senderAddress.setCity(returnCity);
        senderAddress.setArea(returnArea);
        senderAddress.setStreet(returnStreet);
        senderAddress.setPostcode(returnPostCode);
        senderAddress.setPhone(returnPhone);
        senderAddress.setEmail(returnEmail);
        senderAddress.setOrderid(orderid);
        List<TradingOrderSenderAddress> senderAddressesList=iTradingOrderSenderAddress.selectOrderSenderAddressByOrderId(orderid);
        if(senderAddressesList!=null&&senderAddressesList.size()>0) {
            for (TradingOrderSenderAddress senderAddr : senderAddressesList) {
                if (senderAddr.getType().equals(senderAddress.getType())) {
                    senderAddress.setId(senderAddr.getId());
                }
            }
        }
        iTradingOrderSenderAddress.saveOrderSenderAddress(senderAddress);
        AjaxSupport.sendSuccessText("","操作成功!");
    }
    /*
     *订单详情摘要交易信息,付款信息等
     */
    @RequestMapping("/viewOrderAbstractLeft.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView viewOrderAbstractLeft(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        String TransactionId=request.getParameter("TransactionId");
        String selleruserid=request.getParameter("selleruserid");
        List<TradingOrderGetOrders> lists=iTradingOrderGetOrders.selectOrderGetOrdersByTransactionId(TransactionId,selleruserid);
        TradingOrderGetOrders order=lists.get(0);
        /*List<TradingOrderShippingDetails> detailsList=iTradingOrderShippingDetails.selectOrderGetItemById(order.getShippingdetailsId());*/
/*
        List<TradingOrderShippingServiceOptions> serviceOptionList=iTradingOrderShippingServiceOptions.selectOrderGetItemByShippingDetailsId(order.getShippingdetailsId());
*/
        List<TradingOrderGetSellerTransactions> sellerTransactions=iTradingOrderGetSellerTransactions.selectTradingOrderGetSellerTransactionsByTransactionId(lists.get(0).getTransactionid());
        String transactionid="";
        if(sellerTransactions!=null&&sellerTransactions.size()>0){
            transactionid=sellerTransactions.get(0).getExternaltransactionid();
        }
        List<TradingOrderGetAccount> accountlist=iTradingOrderGetAccount.selectTradingOrderGetAccountByTransactionId(lists.get(0).getTransactionid());
        String grossdetailamount="";
        String grossdetailamount1="";
        if(accountlist!=null&&accountlist.size()>0){
            for(TradingOrderGetAccount account:accountlist){
                if("成交費".equals(account.getDescription())){
                    grossdetailamount=account.getGrossdetailamount();
                }
            }

        }
        modelMap.put("grossdetailamount",grossdetailamount);
        modelMap.put("paypal",transactionid);
        modelMap.put("order",order);
        /*modelMap.put("options",serviceOptionList);*/
        return forword("orders/order/viewOrderAbstractLeft",modelMap);
    }
    /*
     *摘要里的订单地址
     */
    @RequestMapping("/viewOrderAbstractRight.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView viewOrderAbstractRight(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        String TransactionId=request.getParameter("TransactionId");
        String selleruserid=request.getParameter("selleruserid");
        List<TradingOrderGetOrders> lists=iTradingOrderGetOrders.selectOrderGetOrdersByTransactionId(TransactionId,selleruserid);
        TradingOrderGetOrders order=lists.get(0);
        modelMap.put("order",order);
        return forword("orders/order/viewOrderAbstractRight",modelMap);
    }
    /*
     *摘要里的最近的发送消息
     */
    @RequestMapping("/viewOrderAbstractDown.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView viewOrderShipmentsHistory(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        String TransactionId=request.getParameter("TransactionId");
        String selleruserid=request.getParameter("selleruserid");
        List<TradingOrderGetOrders> lists=iTradingOrderGetOrders.selectOrderGetOrdersByTransactionId(TransactionId,selleruserid);
        List<TradingOrderAddMemberMessageAAQToPartner> addMes=new ArrayList<TradingOrderAddMemberMessageAAQToPartner>();
        for(TradingOrderGetOrders order:lists){
            List<TradingOrderAddMemberMessageAAQToPartner> addmessageList1=iTradingOrderAddMemberMessageAAQToPartner.selectTradingOrderAddMemberMessageAAQToPartnerByTransactionId(order.getTransactionid(),1);
            addMes.addAll(addmessageList1);
        }
        TradingOrderAddMemberMessageAAQToPartner partner=new TradingOrderAddMemberMessageAAQToPartner();
        if(addMes!=null&&addMes.size()>0){
            partner= addMes.get(addMes.size()-1);
        }
        modelMap.put("addQMessage",partner);
        return forword("orders/order/viewOrderAbstractDown",modelMap);
    }
    /*@RequestMapping("/viewOrderEbayMessage.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView viewOrderEbayMessage(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        String orderId=request.getParameter("orderId");
        List<TradingOrderGetOrders> lists=iTradingOrderGetOrders.selectOrderGetOrdersByOrderId(orderId);
        TradingOrderGetOrders order=lists.get(0);
        modelMap.put("order",order);
        return forword("orders/order/viewOrderAbstractDown",modelMap);

    }*/
    /*
     *购买历史初始化
     */
    @RequestMapping("/viewOrderBuyHistory.do")
    public ModelAndView viewOrderBuyHistory(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        String orderId=request.getParameter("orderId");
        modelMap.put("orderId",orderId);
        return forword("/orders/order/viewOrderBuyHistory",modelMap);
    }
    /*
     *购买历史信息
     */
    @RequestMapping("/ajax/loadBuyHistory.do")
    @ResponseBody
    public void loadBuyHistory(CommonParmVO commonParmVO,HttpServletRequest request) throws Exception {
        String orderId=request.getParameter("orderId");
        /**分页组装*/
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        Page page=jsonBean.toPage();
        Map map=new HashMap();
        List<TradingOrderGetOrders> orderList=iTradingOrderGetOrders.selectOrderGetOrdersByOrderId(orderId);
        String selleraccount=null;
        String buyaccount=null;
        if(orderList!=null&&orderList.size()>0){
            selleraccount=orderList.get(0).getSelleruserid();
            buyaccount=orderList.get(0).getBuyeruserid();
        }
        List<String> statusList=new ArrayList<String>();
        map.put("status","Complete");
        map.put("selleraccount",selleraccount);
        map.put("buyaccount",buyaccount);
        List<OrderGetOrdersQuery> lists= this.iTradingOrderGetOrders.selectOrderGetOrdersByGroupList(map,page);
        for(OrderGetOrdersQuery list:lists){
            String itemid=list.getItemid();
            List<TradingOrderGetItem> itemList= iTradingOrderGetItem.selectOrderGetItemByItemId(itemid);
            if(itemList!=null&&itemList.size()>0){
                Long pictureid=itemList.get(0).getPicturedetailsId();
                List<TradingOrderPictureDetails> pictureDetailses=iTradingOrderPictureDetails.selectOrderGetItemById(pictureid);
                if(pictureDetailses!=null&&pictureDetailses.size()>0){
                    list.setPictrue(pictureDetailses.get(0).getPictureurl());
                }
            }
            String url="http://www.sandbox.ebay.com/itm/"+list.getItemid();
            list.setItemUrl(url);
        }
        jsonBean.setList(lists);
        jsonBean.setTotal((int)page.getTotalCount());
        AjaxSupport.sendSuccessText("", jsonBean);
    }
    /*
     *发送消息初始化
     */
    @RequestMapping("/initOrdersSendMessage.do")
    public ModelAndView initOrdersSendMessage(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        String orderid=request.getParameter("orderid");
        List<TradingOrderGetOrders> list=iTradingOrderGetOrders.selectOrderGetOrdersByOrderId(orderid);
        modelMap.put("itemid",list.get(0).getItemid());
        modelMap.put("order",list.get(0));
        return forword("/orders/order/orderSendMessage",modelMap);
    }
    /*
     *买家评价和该状态初始化
    */
    @RequestMapping("/initSendEvaluateMessage.do")
    public ModelAndView initSendEvaluateMessage(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        String transactionid=request.getParameter("transactionid");
        String selleruserid=request.getParameter("selleruserid");
        List<TradingOrderGetOrders> list=iTradingOrderGetOrders.selectOrderGetOrdersByTransactionId(transactionid,selleruserid);
        modelMap.put("order",list.get(0));
        return forword("/orders/order/orderSendEvaluateMessage",modelMap);
    }
     /*
     *给买家评价和该状态
    */
     @RequestMapping("/apiGetOrdersEvaluteSendMessage.do")
     @AvoidDuplicateSubmission(needRemoveToken = true)
     @ResponseBody
     public void apiGetOrdersEvaluteSendMessage(CommonParmVO commonParmVO,HttpServletRequest request) throws Exception {
         String itemid= request.getParameter("itemid");
         String buyeruserid=request.getParameter("buyeruserid");
         String transactionid=request.getParameter("transactionid");
         String CommentText=request.getParameter("CommentText");
         UsercontrollerDevAccountExtend d = userInfoService.getDevInfo(null);//开发者帐号id
         d.setApiSiteid("0");
         d.setApiCallName(APINameStatic.CompleteSale);
         Map map=new HashMap();
         String ebayName=request.getParameter("selleruserid");
         List<UsercontrollerEbayAccountExtend> dList= userInfoService.getEbayAccountForCurrUser();
         String token=null;
         for(UsercontrollerEbayAccountExtend list:dList){
             if(StringUtils.isNotBlank(ebayName)&&ebayName.equals(list.getEbayName())){
                 token=list.getEbayToken();
             }
         }
         map.put("token", token);
         map.put("CommentText",CommentText);
         map.put("itemid",itemid);
         map.put("buyeruserid",buyeruserid);
         map.put("transactionid",transactionid);
         String xml = BindAccountAPI.getEvaluate(map);
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
             TradingOrderAddMemberMessageAAQToPartner message1=new TradingOrderAddMemberMessageAAQToPartner();
             message1.setBody(CommentText);
             message1.setItemid(itemid);
             message1.setRecipientid(buyeruserid);
             message1.setSender(ebayName);
             message1.setMessagetype(3);
             message1.setTransactionid(transactionid);
             iTradingOrderAddMemberMessageAAQToPartner.saveOrderAddMemberMessageAAQToPartner(message1);
             AjaxSupport.sendSuccessText("success", "评价发送成功");
         }else{
             AjaxSupport.sendFailText("fail", "获取必要的参数失败！请稍后重试");
         }
     }
    /*
   *下载订单downloadOrders
   */
    @RequestMapping("/downloadOrders.do")
    public void  downloadOrders(HttpServletRequest request,HttpServletResponse response) throws Exception {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("UTF-8");
        String outputFile1= request.getSession().getServletContext().getRealPath("/");
        String outputFile=outputFile1+"download\\orders.xls";
        String status=request.getParameter("status");
        String folderId=request.getParameter("folderId");
        List<UsercontrollerEbayAccountExtend> ebays=userInfoService.getEbayAccountForCurrUser();
        List<String> ebayNames=new ArrayList<String>();
        for(UsercontrollerEbayAccountExtend ebay:ebays){
            String name=ebay.getEbayName();
            ebayNames.add(name);
        }
        if(!StringUtils.isNotBlank(status)||"null".equals(status)){
            status=null;
        }
        if(!StringUtils.isNotBlank(folderId)||"null".equals(folderId)){
            folderId=null;
        }
        List<TradingOrderGetOrders> orders=new ArrayList<TradingOrderGetOrders>();
        if(status!=null){
           orders=iTradingOrderGetOrders.selectOrderGetOrdersByPaypalStatus(status,ebayNames);
        }
        if(folderId!=null){
            orders=iTradingOrderGetOrders.selectOrderGetOrdersByFolder(folderId, ebayNames);
        }
        response.setHeader("Content-Disposition","attachment;filename=orders.xls");// 组装附件名称和格式
        ServletOutputStream outputStream = response.getOutputStream();
        iTradingOrderGetOrders.downloadOrders(orders, outputFile,outputStream);
    }
     /*
     *订单中卖家发消息
     */
    @RequestMapping("/apiGetOrdersSendMessage.do")
    @AvoidDuplicateSubmission(needRemoveToken = true)
    @ResponseBody
    public void apiGetOrdersSendMessage(CommonParmVO commonParmVO,HttpServletRequest request) throws Exception {
        String body=request.getParameter("body");
        String subject=request.getParameter("subject");
        String itemid= request.getParameter("itemid");
        String buyeruserid=request.getParameter("buyeruserid");
        String sender=request.getParameter("selleruserid");
        String transactionid=request.getParameter("transactionid");
        if(subject.length()>100){
            subject=subject.substring(0,100);
        }
        UsercontrollerDevAccountExtend d = userInfoService.getDevInfo(null);//开发者帐号id
        d.setApiSiteid("0");
        d.setApiCallName(APINameStatic.AddMemberMessageAAQToPartner);
        Map map=new HashMap();
        String ebayName=request.getParameter("selleruserid");
        List<UsercontrollerEbayAccountExtend> dList= userInfoService.getEbayAccountForCurrUser();
        String token=null;
        for(UsercontrollerEbayAccountExtend list:dList){
            if(StringUtils.isNotBlank(ebayName)&&ebayName.equals(list.getEbayName())){
                token=list.getEbayToken();
            }
        }
        map.put("token", token);
        map.put("subject",subject);
        map.put("body",body);
        map.put("itemid",itemid);
        map.put("buyeruserid",buyeruserid);
        String xml = BindAccountAPI.getAddMemberMessageAAQToPartner(map);//获取接受消息
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
            TradingOrderAddMemberMessageAAQToPartner message1=new TradingOrderAddMemberMessageAAQToPartner();
            message1.setBody(body);
            message1.setItemid(itemid);
            message1.setRecipientid(buyeruserid);
            message1.setSubject(subject);
            message1.setSender(sender);
            message1.setMessagetype(4);
            message1.setTransactionid(transactionid);
            iTradingOrderAddMemberMessageAAQToPartner.saveOrderAddMemberMessageAAQToPartner(message1);
            AjaxSupport.sendSuccessText("success", "发送成功");
        }else{
            AjaxSupport.sendFailText("fail", "获取必要的参数失败！请稍后重试");
        }
    }
    /*
     *同步订单
     */
    @RequestMapping("/apiGetOrdersRequest.do")
    @AvoidDuplicateSubmission(needRemoveToken = true)
    @ResponseBody
    public void apiGetOrdersRequest(CommonParmVO commonParmVO,HttpServletRequest request) throws Exception {
        String ebayId=request.getParameter("ebayId");
        Long ebay=Long.valueOf(ebayId);
        UsercontrollerDevAccountExtend d = userInfoService.getDevInfo(null);//开发者帐号id
        d.setApiSiteid("0");
        d.setApiCallName(APINameStatic.GetOrders);
        request.getSession().setAttribute("dveId", d);
       /* UsercontrollerDevAccountExtend d=new UsercontrollerDevAccountExtend();
        d.setApiDevName("5d70d647-b1e2-4c7c-a034-b343d58ca425");
        d.setApiAppName("sandpoin-23af-4f47-a304-242ffed6ff5b");
        d.setApiCertName("165cae7e-4264-4244-adff-e11c3aea204e");
        d.setApiCompatibilityLevel("883");
        d.setApiSiteid("0");
        d.setApiCallName(APINameStatic.GetOrders);
        request.getSession().setAttribute("dveId", d);*/
        Map map=new HashMap();
        Date startTime2=DateUtils.subDays(new Date(),9);
        Date endTime= DateUtils.addDays(startTime2, 9);
   /*     Date startTime2=DateUtils.subDays(new Date(),40);
        Date endTime= DateUtils.addDays(startTime2, 40);*/
        Date end1= com.base.utils.common.DateUtils.turnToDateEnd(endTime);
        String start= DateUtils.DateToString(startTime2);
        String end=DateUtils.DateToString(end1);
        String token=userInfoService.getTokenByEbayID(ebay);
        map.put("token", token);
/*        map.put("token","AgAAAA**AQAAAA**aAAAAA**CLSRUQ**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6AFlYCjDJGCqA+dj6x9nY+seQ**FdYBAA**AAMAAA**w2sMbwlQ7TBHWxj9EsVedHQRI3+lonY9MDfiyayQbnFkjEanjL/yMCpS/D2B9xHRzRx+ppxWZkRPgeAKJvNotPLLrVTuEzOl5M7pi6Tw8+pzcmIEsOh7HQO78JlyFlvLc/ruE6/hG0E/HO1UX76YBwxp00N9f1NNUpo5u36D/TYsx5O2jXFTKkCOHwz6RW9vtN6TU39aLm+JQme2+NfFFXnbX8MHzoUiX7Sty0R88ZpX5wLp8ZdgXCEc5zZDQziYB1MSXF9hsmby5wKbxFF+OvW/zKADThk1gprgAgnEOucyoao+cUMHopLlYgMbjnLzdCXP5F9z+fkYTnKF6AEl5eHBpcKQGbPzswnKebRoBVw+bI2I1C/iq+PvBUyndFAexjrvlDQbEKr6qb6AWRVTTfkW2ce6a0ixRuCTq35zEpWpfAqkSKo+X23d/Q4V8R30rDXotOWDZL6o408cMO+UQ17uVA2arA1JNkYfc/AZ0T0z7ze5o/yp93jJPlDgi05Ut4fpCAMZw3X85GxrTlbEtawWgoyUbmMuv4f6QHZLZAerOaJA8DRJkzkzjJJ025bp1HvAECOc4ggdv0cofu4q96shssgNYYZJUPM+q4+0fnGK0pxQTNY9SV6vSaVCVoTZJo6vefW7OiHX2/eLoPKFuUfsKXXEv9OY71gD1xzYg/rpCMAqCTq1dKqqyT1R5fxANnoRX7vwkq+7jkCj2fAfKTnHi9mSuBFsilKLmnsqqWy3IGShMgdxiQwBEk6IWi9C");*/
        map.put("fromTime", start);
        map.put("toTime", end);
        map.put("page","1");
        String xml = BindAccountAPI.getGetOrders(map);
        AddApiTask addApiTask = new AddApiTask();
        //---修改前---
       /* Map<String, String> resMap = addApiTask.exec(d, xml, "https://api.ebay.com/ws/api.dll");*/


        /*Map<String, String> resMap = addApiTask.exec(d, xml, apiUrl);
        String r1 = resMap.get("stat");
        String res = resMap.get("message");
        if ("fail".equalsIgnoreCase(r1)) {
            AjaxSupport.sendFailText("fail", res);
            return;
        }
        String ack = SamplePaseXml.getVFromXmlString(res, "Ack");
        if ("Success".equalsIgnoreCase(ack)) {
            Map<String,Object> mapOrder=GetOrdersAPI.parseXMLAndSave(res);
            String totalPage1= (String) mapOrder.get("totalPage");
            String page1= (String) mapOrder.get("page");
            int totalPage= Integer.parseInt(totalPage1);
            for(int i=1;i<=totalPage;i++){
                if(i!=1){
                    map.put("page",i+"");
                    xml = BindAccountAPI.getGetOrders(map);
                    resMap = addApiTask.exec(d, xml, apiUrl);
                   *//* resMap = addApiTask.exec(d, xml, "https://api.ebay.com/ws/api.dll");*//*
                    r1 = resMap.get("stat");
                    res = resMap.get("message");
                    if ("fail".equalsIgnoreCase(r1)) {
                        AjaxSupport.sendFailText("fail", res);
                        return;
                    }
                    ack = SamplePaseXml.getVFromXmlString(res, "Ack");
                    if (!"Success".equalsIgnoreCase(ack)) {
                        String errors = SamplePaseXml.getVFromXmlString(res, "Errors");
                        logger.error("Order获取apisessionid失败!" + errors);
                        AjaxSupport.sendFailText("fail", "获取必要的参数失败！请稍后重试");
                        return;
                    }
                    mapOrder=GetOrdersAPI.parseXMLAndSave(res);
                }

                List<TradingOrderGetOrders> orders= (List<TradingOrderGetOrders>) mapOrder.get("OrderList");
                for(TradingOrderGetOrders order:orders){
                    List<TradingOrderGetOrders> ls=iTradingOrderGetOrders.selectOrderGetOrdersByOrderId(order.getOrderid());
                    for(TradingOrderGetOrders l:ls){
                        if(l.getItemid().equals(order.getItemid())){
                            order.setId(ls.get(0).getId());
                        }
                    }*/


                    //--------------自动发送消息-------------------------
                   /* List<TradingOrderAddMemberMessageAAQToPartner> addmessages=iTradingOrderAddMemberMessageAAQToPartner.selectTradingOrderAddMemberMessageAAQToPartnerByTransactionId(order.getTransactionid(),2);
                    if(addmessages!=null&&addmessages.size()>0){
                        for(TradingOrderAddMemberMessageAAQToPartner addmessage:addmessages){
                            TradingOrderAddMemberMessageAAQToPartner message=new TradingOrderAddMemberMessageAAQToPartner();
                            String trackingnumber=order.getShipmenttrackingnumber();
                            if(StringUtils.isNotBlank(trackingnumber)){
                                if(addmessage.getMessageflag()<=1){
                                    //自动发送发货消息
                                    Map<String,String> messageMap=autoSendMessage(order,2-1,token,d);
                                    if("false".equals(messageMap.get("flag"))){
                                        AjaxSupport.sendFailText("fail", messageMap.get("message"));
                                        return;
                                    }
                                    if("true".equals(messageMap.get("flag"))){
                                        message.setMessageflag(2);
                                        message.setBody(messageMap.get("body"));
                                        message.setSender(order.getSelleruserid());
                                        message.setItemid(order.getItemid());
                                        message.setMessagetype(addmessage.getMessagetype());
                                        message.setRecipientid(order.getBuyeruserid());
                                        message.setSubject(messageMap.get("subject"));
                                        message.setTransactionid(order.getTransactionid());
                                        iTradingOrderAddMemberMessageAAQToPartner.saveOrderAddMemberMessageAAQToPartner(message);
                                    }

                                }else if(addmessage.getMessageflag()==2){
                                    Date shippDate1=order.getShippedtime();
                                    Date shippDate2= org.apache.commons.lang.time.DateUtils.addDays(shippDate1,2);
                                    String shippDate=dateToString(shippDate2);
                                    Date nowDate1=new Date();
                                    String nowDate=dateToString(nowDate1);
                                    if(shippDate!=null){
                                        Integer days=Integer.valueOf(shippDate);
                                        if(days<=Integer.valueOf(nowDate)){
                                            //发货n天后自动发送消息
                                            Map<String,String> messageMap=autoSendMessage(order,3-1,token,d);
                                            if("false".equals(messageMap.get("flag"))){
                                                AjaxSupport.sendFailText("fail", messageMap.get("message"));
                                                return;
                                            }
                                            if("true".equals(messageMap.get("flag"))){
                                                message.setMessageflag(3);
                                                message.setBody(messageMap.get("body"));
                                                message.setSubject(messageMap.get("subject"));
                                                message.setSender(order.getSelleruserid());
                                                message.setItemid(order.getItemid());
                                                message.setMessagetype(addmessage.getMessagetype());
                                                message.setRecipientid(order.getBuyeruserid());
                                                message.setTransactionid(order.getTransactionid());
                                                iTradingOrderAddMemberMessageAAQToPartner.saveOrderAddMemberMessageAAQToPartner(message);
                                            }

                                        }
                                    }

                                }
                            }
                        }
                    }else{
                        TradingOrderAddMemberMessageAAQToPartner message=new TradingOrderAddMemberMessageAAQToPartner();
                        String trackingnumber=order.getShipmenttrackingnumber();
                        if("Complete".equals(order.getStatus())&&!StringUtils.isNotBlank(trackingnumber)&&"Completed".equals(order.getOrderstatus())){
                            //付款后发送消息
                            Map<String,String> messageMap=autoSendMessage(order,1-1,token,d);
                            if("false".equals(messageMap.get("flag"))){
                                AjaxSupport.sendFailText("fail", messageMap.get("message"));
                                return;
                            }
                            if("true".equals(messageMap.get("flag"))){
                                message.setMessageflag(1);
                                message.setBody(messageMap.get("body"));
                                message.setSender(order.getSelleruserid());
                                message.setItemid(order.getItemid());
                                message.setMessagetype(2);
                                message.setRecipientid(order.getBuyeruserid());
                                message.setSubject(messageMap.get("subject"));
                                message.setTransactionid(order.getTransactionid());
                                iTradingOrderAddMemberMessageAAQToPartner.saveOrderAddMemberMessageAAQToPartner(message);
                            }
                        }
                        if(StringUtils.isNotBlank(trackingnumber)){
                            Date shippDate1=order.getShippedtime();
                            Date shippDate2= org.apache.commons.lang.time.DateUtils.addDays(shippDate1,2);
                            String shippDate=dateToString(shippDate2);
                            Date nowDate1=new Date();
                            String nowDate=dateToString(nowDate1);
                            Integer days=Integer.valueOf(shippDate);
                            if(days<=Integer.valueOf(nowDate)){
                                //发货n天后自动发送消息
                                Map<String,String> messageMap=autoSendMessage(order,3-1,token,d);
                                if("false".equals(messageMap.get("flag"))){
                                    AjaxSupport.sendFailText("fail", messageMap.get("message"));
                                    return;
                                }
                                if("true".equals(messageMap.get("flag"))){
                                    message.setMessageflag(3);
                                    message.setBody(messageMap.get("body"));
                                    message.setSubject(messageMap.get("subject"));
                                    message.setSender(order.getSelleruserid());
                                    message.setItemid(order.getItemid());
                                    message.setMessagetype(2);
                                    message.setRecipientid(order.getBuyeruserid());
                                    message.setTransactionid(order.getTransactionid());
                                    iTradingOrderAddMemberMessageAAQToPartner.saveOrderAddMemberMessageAAQToPartner(message);
                                }
                            }else{
                                //发送发货消息
                                Map<String,String> messageMap=autoSendMessage(order,2-1,token,d);
                                if("false".equals(messageMap.get("flag"))){
                                    AjaxSupport.sendFailText("fail", messageMap.get("message"));
                                    return;
                                }
                                if("true".equals(messageMap.get("flag"))){
                                    message.setMessageflag(2);
                                    message.setBody(messageMap.get("body"));
                                    message.setSender(order.getSelleruserid());
                                    message.setItemid(order.getItemid());
                                    message.setMessagetype(2);
                                    message.setRecipientid(order.getBuyeruserid());
                                    message.setSubject(messageMap.get("subject"));
                                    message.setTransactionid(order.getTransactionid());
                                    iTradingOrderAddMemberMessageAAQToPartner.saveOrderAddMemberMessageAAQToPartner(message);
                                }
                            }
                        }
                    }*/


                    //------------同步订单商品-----------
                    /*d.setApiCallName(APINameStatic.GetItem);
                    Map<String,String> itemresmap=GetOrderItemAPI.apiGetOrderItem(d,token,apiUrl,order.getItemid());
                    String itemr1 = itemresmap.get("stat");
                    String itemres = itemresmap.get("message");
                    if ("fail".equalsIgnoreCase(itemr1)) {
                        AjaxSupport.sendFailText("fail", itemres);
                        return;
                    }
                    String itemack = SamplePaseXml.getVFromXmlString(itemres, "Ack");
                    if ("Success".equalsIgnoreCase(itemack)) {
                        Map<String,Object> items=GetOrderItemAPI.parseXMLAndSave(itemres);
                        TradingOrderGetItem item= (TradingOrderGetItem) items.get(GetOrderItemAPI.ORDER_ITEM);
                        String ItemId=order.getItemid();
                        List<TradingOrderGetItem> itemList=iTradingOrderGetItem.selectOrderGetItemByItemId(ItemId);
                        TradingOrderListingDetails listingDetails= (TradingOrderListingDetails) items.get(GetOrderItemAPI.LISTING_DETAILS);
                        TradingOrderSeller orderSeller= (TradingOrderSeller) items.get(GetOrderItemAPI.ORDER_SELLER);
                        TradingOrderSellingStatus sellingStatus= (TradingOrderSellingStatus) items.get(GetOrderItemAPI.SELLING_STATUS);
                        TradingOrderShippingDetails shippingDetails= (TradingOrderShippingDetails) items.get(GetOrderItemAPI.SHIPPING_DETAILS);
                        TradingOrderPictureDetails pictureDetails= (TradingOrderPictureDetails) items.get(GetOrderItemAPI.PICTURE_DETAILS);
                        TradingOrderReturnpolicy returnpolicy= (TradingOrderReturnpolicy) items.get(GetOrderItemAPI.RETURN_POLICY);
                        TradingOrderSellerInformation sellerInformation= (TradingOrderSellerInformation) items.get(GetOrderItemAPI.SELLER_INFORMATION);
                        TradingOrderCalculatedShippingRate shippingRate= (TradingOrderCalculatedShippingRate) items.get(GetOrderItemAPI.SHIPPING_RATE);
                        List<TradingOrderShippingServiceOptions> serviceOptionses= (List<TradingOrderShippingServiceOptions>) items.get(GetOrderItemAPI.SERVICE_OPTIONS);
                        if(itemList!=null&&itemList.size()>0){
                            item.setId(itemList.get(0).getId());
                            listingDetails.setId(itemList.get(0).getListingdetailsId());
                            orderSeller.setId(itemList.get(0).getSellerId());
                            sellingStatus.setId(itemList.get(0).getSellingstatusId());
                            shippingDetails.setId(itemList.get(0).getShippingdetailsId());
                            pictureDetails.setId(itemList.get(0).getPicturedetailsId());
                            returnpolicy.setId(itemList.get(0).getOrderreturnpolicyId());
                            List<TradingOrderSeller> sellerList=iTradingOrderSeller.selectOrderGetItemById(orderSeller.getId());
                            if(sellerList!=null&&sellerList.size()>0){
                                sellerInformation.setId(sellerList.get(0).getSellerinfoId());
                            }
                            List<TradingOrderShippingDetails> shippingDetailsList=iTradingOrderShippingDetails.selectOrderGetItemById(shippingDetails.getId());
                            if(shippingDetailsList!=null&&shippingDetailsList.size()>0){
                                shippingRate.setId(shippingDetailsList.get(0).getCalculatedshippingrateId());
                            }
                            iTradingOrderCalculatedShippingRate.saveOrderCalculatedShippingRate(shippingRate);
                            shippingDetails.setCalculatedshippingrateId(shippingRate.getId());
                            iTradingOrderShippingDetails.saveOrderShippingDetails(shippingDetails);
                            iTradingOrderSellerInformation.saveOrderSellerInformation(sellerInformation);
                            iTradingOrderListingDetails.saveOrderListingDetails(listingDetails);
                            orderSeller.setSellerinfoId(sellerInformation.getId());
                            iTradingOrderSeller.saveOrderSeller(orderSeller);
                            iTradingOrderSellingStatus.saveOrderSellingStatus(sellingStatus);
                            iTradingOrderPictureDetails.saveOrderPictureDetails(pictureDetails);
                            iTradingOrderReturnpolicy.saveOrderReturnpolicy(returnpolicy);

                        }else{
                            iTradingOrderCalculatedShippingRate.saveOrderCalculatedShippingRate(shippingRate);
                            iTradingOrderSellerInformation.saveOrderSellerInformation(sellerInformation);
                            iTradingOrderListingDetails.saveOrderListingDetails(listingDetails);
                            orderSeller.setSellerinfoId(sellerInformation.getId());
                            iTradingOrderSeller.saveOrderSeller(orderSeller);
                            iTradingOrderSellingStatus.saveOrderSellingStatus(sellingStatus);
                            shippingDetails.setCalculatedshippingrateId(shippingRate.getId());
                            iTradingOrderShippingDetails.saveOrderShippingDetails(shippingDetails);
                            iTradingOrderPictureDetails.saveOrderPictureDetails(pictureDetails);
                            iTradingOrderReturnpolicy.saveOrderReturnpolicy(returnpolicy);
                            item.setListingdetailsId(listingDetails.getId());
                            item.setSellerId(orderSeller.getId());
                            item.setSellingstatusId(sellingStatus.getId());
                            item.setShippingdetailsId(shippingDetails.getId());
                            item.setPicturedetailsId(pictureDetails.getId());
                            item.setOrderreturnpolicyId(returnpolicy.getId());
                        }
                        List<TradingOrderShippingServiceOptions> shippingServiceOptionses=iTradingOrderShippingServiceOptions.selectOrderGetItemByShippingDetailsId(shippingDetails.getId());
                        for(TradingOrderShippingServiceOptions shippingServiceOptionse:shippingServiceOptionses){
                            iTradingOrderShippingServiceOptions.deleteOrderShippingServiceOptions(shippingServiceOptionse);
                        }
                        for(TradingOrderShippingServiceOptions serviceOptionse:serviceOptionses){
                            serviceOptionse.setShippingdetailsId(shippingDetails.getId());
                            iTradingOrderShippingServiceOptions.saveOrderShippingServiceOptions(serviceOptionse);
                        }
                        order.setShippingdetailsId(shippingDetails.getId());
                        iTradingOrderGetItem.saveOrderGetItem(item);

                        List<TradingOrderItemSpecifics> specificItemList= (List<TradingOrderItemSpecifics>) items.get(GetOrderItemAPI.ITEM_SPECIFICS);
                        List<TradingOrderItemSpecifics> Itemspecifics=iTradingOrderItemSpecifics.selectOrderItemSpecificsByItemId(item.getId());
                        if(Itemspecifics!=null&&Itemspecifics.size()>0){
                            for(TradingOrderItemSpecifics specifics:Itemspecifics){
                                iTradingOrderItemSpecifics.deleteOrderItemSpecifics(specifics);
                            }
                        }
                        for(TradingOrderItemSpecifics specifics:specificItemList){
                            specifics.setOrderitemId(item.getId());
                            iTradingOrderItemSpecifics.saveOrderItemSpecifics(specifics);
                        }
                        List<TradingOrderVariation> variationList= (List<TradingOrderVariation>) items.get(GetOrderItemAPI.VARIATION);
                        List<TradingOrderVariation> variationLists=iTradingOrderVariation.selectOrderVariationByItemId(item.getId());
                        if(variationLists!=null&&variationLists.size()>0){
                            for(TradingOrderVariation specifics:variationLists){
                                List<TradingOrderVariationSpecifics> specificsVariations=iTradingOrderVariationSpecifics.selectOrderVariationSpecificsByVariationId(specifics.getId());
                                if(specificsVariations!=null&&specificsVariations.size()>0){
                                    for(TradingOrderVariationSpecifics specificsVariation:specificsVariations){
                                        iTradingOrderVariationSpecifics.deleteOrderVariationSpecifics(specificsVariation);
                                    }
                                }
                                iTradingOrderVariation.deleteOrderVariation(specifics);
                            }
                        }
                        for(TradingOrderVariation specifics:variationList){
                            specifics.setOrderitemId(item.getId());
                            iTradingOrderVariation.saveOrderVariation(specifics);
                            List<TradingOrderVariationSpecifics> specificsList= (List<TradingOrderVariationSpecifics>) items.get(GetOrderItemAPI.VARIATION_SPECIFICS);
                            for(TradingOrderVariationSpecifics specificsVariation:specificsList){
                                specificsVariation.setOrdervariationId(specifics.getId());
                                iTradingOrderVariationSpecifics.saveOrderVariationSpecifics(specificsVariation);
                            }
                        }
                        List<TradingOrderPictures> pictrueList= (List<TradingOrderPictures>) items.get(GetOrderItemAPI.PICTURES);
                        List<TradingOrderPictures> pictrueLists=iTradingOrderPictures.selectOrderPicturesByItemId(item.getId());
                        if(pictrueLists!=null&&pictrueLists.size()>0){
                            for(TradingOrderPictures specifics:pictrueLists){
                                iTradingOrderPictures.deleteOrderPictures(specifics);
                            }
                        }
                        for(TradingOrderPictures specifics:pictrueList){
                            specifics.setOrderitemId(item.getId());
                            iTradingOrderPictures.saveOrderPictures(specifics);
                        }
                    }else {
                        String errors = SamplePaseXml.getVFromXmlString(itemres, "Errors");
                        logger.error("GetItem获取apisessionid失败!" + errors);
                        AjaxSupport.sendFailText("fail", "获取必要的参数失败！请稍后重试");
                        return;
                    }*/


                    //同步account-----
                    /*   UsercontrollerDevAccountExtend ds=new UsercontrollerDevAccountExtend();
                       ds.setApiDevName("5d70d647-b1e2-4c7c-a034-b343d58ca425");
                       ds.setApiAppName("sandpoin-23af-4f47-a304-242ffed6ff5b");
                       ds.setApiCertName("165cae7e-4264-4244-adff-e11c3aea204e");
                       ds.setApiCompatibilityLevel("883");
                       ds.setApiSiteid("0");
                      ds.setApiCallName("GetAccount");
                     Map accountmap=new HashMap();
                    accountmap.put("token","AgAAAA**AQAAAA**aAAAAA*axvUQ**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6AFlIWnC5iEpAidj6x9nY+seQ**I9ABAA**AAMAAA**fxldhpfo5xKyb9gLGSZgZtGZ35LrWfrf6xXxYJN/x1dazCBJKQIPEKf8Jf1YhWtmekAMZAJh4XkfsCuJfo+ROf/KEATGPEJEFI/bK+5DdZRndlY4AY9V3SM+iNPq0kXNAEy7hfWNqpb1PeA1TRWU21O1z07Vcnq+8Rr6XpwUWCL16T+KKsYpeM7CIudbtNY6+jsl0Vp65tOgYLfBmqgz6Q3XwVZXWQ914BetqWQsGudhvxsrwItxQruXZgOgiUZjJl8fjL1YWIYj+sa3CJPPDJy1l6+/NRMfD7cbfMbaF4m5tCXgcziyq/IQVnJHHxonUvNJj6zOQD/j0baLBdqOz+bGEWOSaPjEyRLvpwPfXVahl41OjJmKTqQTT1otJMw0LtDIWuhE6VhsQqFD79zw4GzEUrgWdjf9GUaHiirsC3U+1XFPiuA29djDJS9Rk8flXxZJstCuPCX9V0L8fcTiqrKr6k5c+yE2doyBV5FkM5JfJ+SJLk6r3qiIBQFwpvPQc/+R6UYhvY8msN1TiIVEQiBNAH1VfjRAq+SAmKhWW2UP/fOgy8aop2W/HnpG89WQ3UDIFRkOWijU/sepdwGVVYvGvtPEd9xHX1CZO6ZIJ6pMdPTaHxisPSQxJGvZ1GJW/daKgeNbSDDwoKYkzrG5CGJysOj3MOWeJNXKDbu9scB5nfiOX7JLVKurr1p0zxWXF4Mhhk3MWfqjP2SYYL3RIiTDKXHZiju4LluIxUcx9VDo85wXGV6JdO5xT4VrLgEA");
                    accountmap.put("Itemid","161282030915");
                    accountmap.put("fromTime", start);
                    accountmap.put("toTime", end);*/
      /*              d.setApiCallName(APINameStatic.GetAccount);
                    Map accountmap=new HashMap();
                    accountmap.put("token",token);
                    accountmap.put("Itemid",order.getItemid());
                    accountmap.put("fromTime", start);
                    accountmap.put("toTime", end);
                    String accountxml = BindAccountAPI.getGetAccount(accountmap);*/
                   /* Map<String, String> accountresmap = addApiTask.exec(ds, accountxml, "https://api.ebay.com/ws/api.dll");*/
          /*          Map<String, String> accountresmap = addApiTask.exec(d, accountxml, apiUrl);
                    String accountr1 = accountresmap.get("stat");
                    String accountres = accountresmap.get("message");
                    if ("fail".equalsIgnoreCase(accountr1)) {
                        AjaxSupport.sendFailText("fail", itemres);
                        return;
                    }
                    String accountack = SamplePaseXml.getVFromXmlString(accountres, "Ack");
                    if ("Success".equalsIgnoreCase(accountack)) {
                        List<TradingOrderGetAccount> accountList= GetAccountAPI.parseXMLAndSave(accountres);
                        for(TradingOrderGetAccount acc:accountList){
                            List<TradingOrderGetAccount> accountList1=iTradingOrderGetAccount.selectTradingOrderGetAccountByTransactionId(order.getTransactionid());
                            if(accountList1!=null&&accountList1.size()>0) {
                                for(TradingOrderGetAccount acc1:accountList1){
                                    if (acc.getDescription().equals(acc1.getDescription())&&acc.getTransactionid().equals(acc1.getTransactionid())) {
                                        acc.setId(acc1.getId());
                                    }
                                }
                            }
                            iTradingOrderGetAccount.saveOrderGetAccount(acc);
                        }
                    }else{
                        String errors = SamplePaseXml.getVFromXmlString(accountres, "Errors");
                        logger.error("GetAccount获取apisessionid失败!" + errors);
                        AjaxSupport.sendFailText("fail", "获取必要的参数失败！请稍后重试");
                        return;
                    }*/


                    //----------------
                    //同步外部交易
                   /* d.setApiCallName("GetSellerTransactions");
                    String sellerxml = BindAccountAPI.GetSellerTransactions(token);//获取接受消息
                    Map<String, String> resSellerMap = addApiTask.exec(d, sellerxml, apiUrl);
                    //------------------------
                    String sellerR1 = resSellerMap.get("stat");
                    String sellerRes = resSellerMap.get("message");
                    if ("fail".equalsIgnoreCase(sellerR1)) {
                        AjaxSupport.sendFailText("fail", sellerRes);
                        return;
                    }
                    String sellerAck = SamplePaseXml.getVFromXmlString(res, "Ack");
                    if ("Success".equalsIgnoreCase(sellerAck)) {
                        List<TradingOrderGetSellerTransactions> lists= GetSellerTransactionsAPI.parseXMLAndSave(sellerRes);
                        for(TradingOrderGetSellerTransactions list:lists){
                            List<TradingOrderGetSellerTransactions>  transactionseList=iTradingOrderGetSellerTransactions.selectTradingOrderGetSellerTransactionsByTransactionId(list.getTransactionid());
                            if(transactionseList!=null&&transactionseList.size()>0){
                                list.setId(transactionseList.get(0).getId());
                            }
                            iTradingOrderGetSellerTransactions.saveOrderGetSellerTransactions(list);
                        }
                    } else {
                        String errors = SamplePaseXml.getVFromXmlString(sellerRes, "Errors");
                        logger.error("获取外部交易失败!" + errors);
                        AjaxSupport.sendFailText("fail", "获取必要的参数失败！请稍后重试");
                        return;
                    }
                    //---------------------------------------
                    iTradingOrderGetOrders.saveOrderGetOrders(order);
                }
            }
            AjaxSupport.sendSuccessText("success", "同步成功！");
        } else {
            String errors = SamplePaseXml.getVFromXmlString(res, "Errors");
            logger.error("Order获取apisessionid失败!" + errors);
            AjaxSupport.sendFailText("fail", "获取必要的参数失败！请稍后重试");
        }*/


        //---修改后---
        TaskMessageVO taskMessageVO=new TaskMessageVO();
        taskMessageVO.setMessageContext("订单");
        taskMessageVO.setMessageTitle("同步订单");
        taskMessageVO.setMessageType(SiteMessageStatic.SYNCHRONIZE_GET_ORDER_TYPE);
        taskMessageVO.setBeanNameType(SiteMessageStatic.SYNCHRONIZE_GET_ORDER_BEAN);
        taskMessageVO.setMessageFrom("system");
        SessionVO sessionVO= SessionCacheSupport.getSessionVO();
        taskMessageVO.setMessageTo(sessionVO.getId());
        Map map2=new HashMap();
        map2.put("token",token);
        map2.put("d",d);
        taskMessageVO.setObjClass(map2);
        addApiTask.execDelayReturn(d, xml,apiUrl, taskMessageVO);
        AjaxSupport.sendSuccessText("message", "操作成功！结果请稍后查看消息！");
    }
    //---修改前---
    /*private String dateToString(Date date){
        String d=null;
        if(date!=null){
            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String startTime=f.format(date);
            d=startTime.substring(0,4)+startTime.substring(5,7)+startTime.substring(8,10);
        }
        return d;
    }*/
    /*private Map<String,String> autoSendMessage(TradingOrderGetOrders order,Integer flag,String token,UsercontrollerDevAccountExtend d) throws Exception {
        Map<String,String> messageMap=new HashMap<String, String>();
       *//* String[] bodys={"付款的",
                        "发货的",
                        "发货n天的"};*//*
        String[] bodys={"Hello "+order.getBuyeruserid()+",\n" +
                "\n" +
                "Thank you for your payment for:\n" +
                "\n" +
                "eBay item number: \n" +order.getItemid()+
                " \n" +
                "eBay item name: "+order.getTitle()+"\n" +
                "\n" +
                "We have received the cleared payment \n" +
                "and we will ship your package within specified handling time.The different \n" +
                "products has different handling time,please notice the handling time.Please \n" +
                "DON’T leave us 1, 2, 3 or 4-Detailed Seller Ratings on Shipping Time which \n" +
                "equaling to negative feedback. We welcome your positive 5-Detailed Seller \n" +
                "Ratings for us.If you have any questions,please don’t hesitate to contact \n" +
                "us.\n" +
                "\n" +
                "Yours Sincerely,\n" +
                "Thanks and best regards.",*//*---*//*
                "Dear fed53\n" +
                        "\n" +
                        "We have shipped your eBay item "+order.getTitle()+". It is estimated to arrive in 8-15 business days in normal conditions. If not, please do not hesitate to contact us. We shall do whatever we can to help you.\n" +
                        "\n" +
                        "Here is the tracking number of your parcel "+order.getShipmenttrackingnumber()+", and you can log in http://91.sellertool.com/track/"+order.getShipmenttrackingnumber()+" to view the updated shipment, which will be shown in 1-2 business days.\n" +
                        "\n" +
                        "Thanks again for your great purchase and great understanding. We sincerely hope our item and customer service can give you the BEST BUYING EXPERIENCE on eBay.If you have any other concerns, feel free to let me know. Please give us a chance to rectify problem before leaving a negative feedback, and then we will try our best to help resolve it rightly. \n" +
                        "\n" +
                        "Yours Sincerely,\n" +
                        "Thanks and best regards.",*//*---*//*
                "Dear elainegs85,\n" +
                        "\n" +
                        "It is 2 days since your item was shipped. May I know whether you've received it?\n" +
                        "\n" +
                        "If not,the tracking number of your package is "+order.getShipmenttrackingnumber()+",and you can log in http://91.sellertool.com/en-us/track/"+order.getShipmenttrackingnumber()+" to view the updated shipment\n" +
                        "\n" +
                        "If you've received it, we sincerely hope you can leave us a positive feedback and 5-star Detailed Seller Ratings if the item works fine for you. Your encouragement will keep us moving forward and constantly improving our service.\n" +
                        "\n" +
                        "Thanks again for your great purchase and great understanding. We sincerely hope our item and customer service can give you the BEST BUYING EXPERIENCE on eBay.If you have any other concerns, feel free to let me know. Please give us a chance to rectify problem before leaving the negative/neutral feedback with low stars rating, and then we will try our best to help resolve it rightly. \n" +
                        "\n" +
                        "Thanks again! \n" +
                        "\n" +
                        "Best regards."};
        String subjects="auto send message";
        d.setApiCallName(APINameStatic.AddMemberMessageAAQToPartner);
        Map map=new HashMap();
        messageMap.put("body",bodys[flag]);
        messageMap.put("subject",subjects);
        map.put("token", token);
        map.put("subject",subjects);
        map.put("body",bodys[flag]);
        map.put("itemid",order.getItemid());
        map.put("buyeruserid",order.getBuyeruserid());
        String xml = BindAccountAPI.getAddMemberMessageAAQToPartner(map);
        AddApiTask addApiTask = new AddApiTask();
        Map<String, String> resMap = addApiTask.exec(d, xml, apiUrl);
        String r1 = resMap.get("stat");
        String res = resMap.get("message");
        if ("fail".equalsIgnoreCase(r1)) {
            messageMap.put("flag","false");
            messageMap.put("message",res);
        }
        String ack = SamplePaseXml.getVFromXmlString(res, "Ack");
        if ("Success".equalsIgnoreCase(ack)) {
            messageMap.put("flag","true");
            messageMap.put("message","发送成功");
        }else{
            messageMap.put("flag","false");
            messageMap.put("message","获取必要的参数失败！请稍后重试");
        }
        return messageMap;
    }*/
}
