package com.task.service.impl;

import com.base.database.sitemessage.model.PublicSitemessage;
import com.base.database.task.model.TaskGetOrders;
import com.base.database.trading.model.*;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.sampleapixml.APINameStatic;
import com.base.sampleapixml.BindAccountAPI;
import com.base.sampleapixml.GetOrderItemAPI;
import com.base.sampleapixml.GetOrdersAPI;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.common.CommAutowiredClass;
import com.base.utils.threadpool.AddApiTask;
import com.base.utils.threadpool.TaskMessageVO;
import com.base.utils.xmlutils.SamplePaseXml;
import com.sitemessage.service.SiteMessageService;
import com.sitemessage.service.SiteMessageStatic;
import com.task.service.IScheduleGetTimerOrders;
import com.task.service.ITaskGetOrders;
import com.trading.service.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/10/17.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ScheduleGetTimerOrdersImpl implements IScheduleGetTimerOrders {
    static Logger logger = Logger.getLogger(ScheduleGetTimerOrdersImpl.class);
    @Autowired
    private SiteMessageService siteMessageService;
    @Autowired
    private ITradingOrderOrderVariationSpecifics iTradingOrderOrderVariationSpecifics;
    @Autowired
    private ITradingOrderGetOrders iTradingOrderGetOrders;
    @Autowired
    private ITradingOrderAddMemberMessageAAQToPartner iTradingOrderAddMemberMessageAAQToPartner;
    @Autowired
    private ITradingAutoMessage iTradingAutoMessage;
    @Autowired
    private IUsercontrollerEbayAccount iUsercontrollerEbayAccount;
    @Autowired
    private ITradingOrderGetItem iTradingOrderGetItem;
    @Autowired
    private ITradingOrderSeller iTradingOrderSeller;
    @Autowired
    private ITradingOrderShippingDetails iTradingOrderShippingDetails;
    @Autowired
    private ITradingOrderCalculatedShippingRate iTradingOrderCalculatedShippingRate;
    @Autowired
    private ITradingOrderSellerInformation iTradingOrderSellerInformation;
    @Autowired
    private ITradingOrderSellingStatus iTradingOrderSellingStatus;
    @Autowired
    private ITradingOrderReturnpolicy iTradingOrderReturnpolicy;
    @Autowired
    private ITradingOrderShippingServiceOptions iTradingOrderShippingServiceOptions;
    @Autowired
    private ITradingOrderItemSpecifics iTradingOrderItemSpecifics;
    @Autowired
    private ITradingOrderVariation iTradingOrderVariation;
    @Autowired
    private ITradingOrderVariationSpecifics iTradingOrderVariationSpecifics;
    @Autowired
    private ITradingOrderPictures iTradingOrderPictures;
    @Autowired
    private ITradingOrderListingDetails iTradingOrderListingDetails;
    @Autowired
    private ITradingOrderPictureDetails iTradingOrderPictureDetails;
    @Autowired
    private ITradingOrderGetAccount iTradingOrderGetAccount;
    @Autowired
    private ITradingOrderGetSellerTransactions iTradingOrderGetSellerTransactions;
    @Autowired
    private ITaskGetOrders iTaskGetOrders;


    @Override
    public void synchronizeOrders(List<TaskGetOrders> taskGetOrders) {
        CommAutowiredClass commPars = (CommAutowiredClass) ApplicationContextUtil.getBean(CommAutowiredClass.class);
        try{
            for(TaskGetOrders taskGetOrder:taskGetOrders){
                UsercontrollerDevAccountExtend d = new UsercontrollerDevAccountExtend();//开发者帐号id
                d.setApiSiteid("0");
                d.setApiCallName(APINameStatic.GetOrders);
                Map map=new HashMap();
                map.put("token", taskGetOrder.getToken());
                map.put("fromTime", taskGetOrder.getFromtime());
                map.put("toTime", taskGetOrder.getTotime());
                map.put("page","1");
                String xml = BindAccountAPI.getGetOrders(map);
                AddApiTask addApiTask = new AddApiTask();
                Map<String, String> resMap = addApiTask.exec(d, xml, commPars.apiUrl);
                String r1 = resMap.get("stat");
                String res = resMap.get("message");
                if ("fail".equalsIgnoreCase(r1)) {
                    List<PublicSitemessage> list1=siteMessageService.selectPublicSitemessageByMessage("synchronize_get_order_timer_FAIL","订单定时任务:"+taskGetOrder.getId());
                    if(list1!=null&&list1.size()>0){
                        return;
                    }
                    TaskMessageVO taskMessageVO = new TaskMessageVO();
                    taskMessageVO.setMessageType(SiteMessageStatic.SYNCHRONIZE_GET_ORDER_TIMER + "_FAIL");
                    taskMessageVO.setMessageTitle("定时同步订单失败!");
                    taskMessageVO.setMessageContext("订单调用API失败:" + res);
                    taskMessageVO.setMessageTo(taskGetOrder.getUserid());
                    taskMessageVO.setMessageFrom("system");
                    taskMessageVO.setOrderAndSeller("订单定时任务:"+taskGetOrder.getId());
                    siteMessageService.addSiteMessage(taskMessageVO);
                    return;
                }
                String ack = SamplePaseXml.getVFromXmlString(res, "Ack");
                if ("Success".equalsIgnoreCase(ack)) {


                    Map<String,Object> mapOrder= GetOrdersAPI.parseXMLAndSave(res);
                    String totalPage1= (String) mapOrder.get("totalPage");
                    String page1= (String) mapOrder.get("page");
                    int totalPage= Integer.parseInt(totalPage1);
                    for(int i=1;i<=totalPage;i++) {
                        if (i != 1) {
                            map.put("page", i + "");
                            xml = BindAccountAPI.getGetOrders(map);
                            resMap = addApiTask.exec(d, xml, commPars.apiUrl);
                      /*  Map<String, String>  resMap = addApiTask.exec(d, xml, "https://api.ebay.com/ws/api.dll");*/
                   /* resMap = addApiTask.exec(d, xml, "https://api.ebay.com/ws/api.dll");*/
                            r1 = resMap.get("stat");
                            res = resMap.get("message");
                            if ("fail".equalsIgnoreCase(r1)) {
                                return;
                            }
                            ack = SamplePaseXml.getVFromXmlString(res, "Ack");
                            if (!"Success".equalsIgnoreCase(ack)) {
                                return;
                            }
                            mapOrder = GetOrdersAPI.parseXMLAndSave(res);
                        }

                        List<TradingOrderGetOrders> orders = (List<TradingOrderGetOrders>) mapOrder.get("OrderList");
                        List<List<TradingOrderOrderVariationSpecifics>> OrderVariationSpecificses= (List<List<TradingOrderOrderVariationSpecifics>>) mapOrder.get("OrderVariation");
                        for(List<TradingOrderOrderVariationSpecifics> orderVariationSpecificses:OrderVariationSpecificses){
                            for(TradingOrderOrderVariationSpecifics orderOrderVariationSpecifics:orderVariationSpecificses){
                                List<TradingOrderOrderVariationSpecifics> list=iTradingOrderOrderVariationSpecifics.selectOrderOrderVariationSpecificsByAll(orderOrderVariationSpecifics.getSku(),orderOrderVariationSpecifics.getName(),orderOrderVariationSpecifics.getValue());
                                if(list==null||list.size()==0){
                                    orderOrderVariationSpecifics.setCreateUser(taskGetOrder.getUserid());
                                    iTradingOrderOrderVariationSpecifics.saveOrderOrderVariationSpecifics(orderOrderVariationSpecifics);
                                }
                            }
                        }
                        for (TradingOrderGetOrders order : orders) {
                            List<TradingOrderGetOrders> ls = iTradingOrderGetOrders.selectOrderGetOrdersByOrderId(order.getOrderid());
                            for (TradingOrderGetOrders l : ls) {
                                if (l.getTransactionid().equals(order.getTransactionid())) {
                                    order.setId(l.getId());
                                }
                            }
                            //--------------自动发送消息-------------------------
                            List<TradingOrderAddMemberMessageAAQToPartner> addmessages=iTradingOrderAddMemberMessageAAQToPartner.selectTradingOrderAddMemberMessageAAQToPartnerByTransactionId(order.getTransactionid(),2,order.getSelleruserid());
                            if(addmessages!=null&&addmessages.size()>0){
                                List<TradingAutoMessage> partners=iTradingAutoMessage.selectAutoMessageByType("标记已发货");
                                TradingAutoMessage autoMessage=new TradingAutoMessage();
                                UsercontrollerEbayAccount ebay=iUsercontrollerEbayAccount.selectByEbayAccount(order.getSelleruserid());
                                for(TradingAutoMessage partner:partners){
                                    if(partner.getCreateUser()==ebay.getUserId()){
                                        int day=partner.getDay();
                                        int hour=partner.getHour();
                                        Date date=order.getLastmodifiedtime();
                                        Date date1=org.apache.commons.lang.time.DateUtils.addDays(date,day);
                                        Date date2=org.apache.commons.lang.time.DateUtils.addHours(date1,hour);
                                        order.setSendmessagetime(date2);
                                    }
                                }
                                boolean flag=false;
                                String trackingnumber=order.getShipmenttrackingnumber();
                                for(TradingOrderAddMemberMessageAAQToPartner addmessage:addmessages){
                                    if(StringUtils.isNotBlank(trackingnumber)&&addmessage.getMessageflag()==2){
                                        flag=true;
                                    }
                                }
                                if(flag&&StringUtils.isNotBlank(trackingnumber)){
                                    order.setPaypalflag(null);
                                    order.setShippedflag(1);
                                }else{
                                    order.setPaypalflag(1);
                                    order.setShippedflag(1);
                                }
                            }else{
                                String trackingnumber=order.getShipmenttrackingnumber();
                                if("Complete".equals(order.getStatus())&&!StringUtils.isNotBlank(trackingnumber)&&"Completed".equals(order.getOrderstatus())){
                                    //付款后发送消息
                                    List<TradingAutoMessage> partners=iTradingAutoMessage.selectAutoMessageByType("收到买家付款");
                                    TradingAutoMessage autoMessage=new TradingAutoMessage();
                                    UsercontrollerEbayAccount ebay=iUsercontrollerEbayAccount.selectByEbayAccount(order.getSelleruserid());
                                    for(TradingAutoMessage partner:partners){
                                        if(partner.getCreateUser()==ebay.getUserId()){
                                            int day=partner.getDay();
                                            int hour=partner.getHour();
                                            Date date=order.getLastmodifiedtime();
                                            Date date1=org.apache.commons.lang.time.DateUtils.addDays(date,day);
                                            Date date2=org.apache.commons.lang.time.DateUtils.addHours(date1,hour);
                                            order.setSendmessagetime(date2);
                                        }
                                    }
                                    order.setPaypalflag(1);
                                    order.setShippedflag(null);
                                }
                                if(StringUtils.isNotBlank(trackingnumber)){
                                    List<TradingAutoMessage> partners=iTradingAutoMessage.selectAutoMessageByType("标记已发货");
                                    TradingAutoMessage autoMessage=new TradingAutoMessage();
                                    UsercontrollerEbayAccount ebay=iUsercontrollerEbayAccount.selectByEbayAccount(order.getSelleruserid());
                                    for(TradingAutoMessage partner:partners){
                                        if(partner.getCreateUser()==ebay.getUserId()){
                                            int day=partner.getDay();
                                            int hour=partner.getHour();
                                            Date date=order.getLastmodifiedtime();
                                            Date date1=org.apache.commons.lang.time.DateUtils.addDays(date,day);
                                            Date date2=org.apache.commons.lang.time.DateUtils.addHours(date1,hour);
                                            order.setSendmessagetime(date2);
                                        }
                                    }
                                    order.setPaypalflag(null);
                                    order.setShippedflag(1);
                                }
                            }
                            //------------同步订单商品-----------
                            d.setApiCallName(APINameStatic.GetItem);
                            //测试环境
                            Map<String,String> itemresmap= GetOrderItemAPI.apiGetOrderItem(d, taskGetOrder.getToken(), commPars.apiUrl, order.getItemid());
                            //真实环境
                            // Map<String,String> itemresmap= GetOrderItemAPI.apiGetOrderItem(d, token, "https://api.ebay.com/ws/api.dll", order.getItemid());
                            String itemr1 = itemresmap.get("stat");
                            String itemres = itemresmap.get("message");
                            if ("fail".equalsIgnoreCase(itemr1)) {
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
                                shippingRate.setCreateUser(taskGetOrder.getUserid());
                                shippingDetails.setCreateUser(taskGetOrder.getUserid());
                                sellerInformation.setCreateUser(taskGetOrder.getUserid());
                                listingDetails.setCreateUser(taskGetOrder.getUserid());
                                orderSeller.setCreateUser(taskGetOrder.getUserid());
                                sellingStatus.setCreateUser(taskGetOrder.getUserid());
                                pictureDetails.setCreateUser(taskGetOrder.getUserid());
                                returnpolicy.setCreateUser(taskGetOrder.getUserid());
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
                                    serviceOptionse.setCreateUser(taskGetOrder.getUserid());
                                    iTradingOrderShippingServiceOptions.saveOrderShippingServiceOptions(serviceOptionse);
                                }
                                order.setShippingdetailsId(shippingDetails.getId());
                                item.setCreateUser(taskGetOrder.getUserid());
                                item.setSku(order.getSku());
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
                                    specifics.setCreateUser(taskGetOrder.getUserid());
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
                                    specifics.setCreateUser(taskGetOrder.getUserid());
                                    iTradingOrderVariation.saveOrderVariation(specifics);
                                    List<TradingOrderVariationSpecifics> specificsList= (List<TradingOrderVariationSpecifics>) items.get(GetOrderItemAPI.VARIATION_SPECIFICS);
                                    for(TradingOrderVariationSpecifics specificsVariation:specificsList){
                                        specificsVariation.setOrdervariationId(specifics.getId());
                                        specificsVariation.setCreateUser(taskGetOrder.getUserid());
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
                                    specifics.setCreateUser(taskGetOrder.getUserid());
                                    iTradingOrderPictures.saveOrderPictures(specifics);
                                }
                            }else {
                                return;
                            }
                            //同步account-----
                            /*UsercontrollerDevAccountExtend ds=new UsercontrollerDevAccountExtend();
                            //----加了就是真实环境
                            //ds.setApiDevName("5d70d647-b1e2-4c7c-a034-b343d58ca425");
                            //ds.setApiAppName("sandpoin-23af-4f47-a304-242ffed6ff5b");
                            //ds.setApiCertName("165cae7e-4264-4244-adff-e11c3aea204e");
                            //ds.setApiCompatibilityLevel("883");
                            ds.setApiSiteid("0");
                            ds.setApiCallName(APINameStatic.GetAccount);
                            Map accountmap=new HashMap();
                            //真实环境
                            //accountmap.put("token","AgAAAA**AQAAAA**aAAAAA**CLSRUQ**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6AFlYCjDJGCqA+dj6x9nY+seQ**FdYBAA**AAMAAA**w2sMbwlQ7TBHWxj9EsVedHQRI3+lonY9MDfiyayQbnFkjEanjL/yMCpS/D2B9xHRzRx+ppxWZkRPgeAKJvNotPLLrVTuEzOl5M7pi6Tw8+pzcmIEsOh7HQO78JlyFlvLc/ruE6/hG0E/HO1UX76YBwxp00N9f1NNUpo5u36D/TYsx5O2jXFTKkCOHwz6RW9vtN6TU39aLm+JQme2+NfFFXnbX8MHzoUiX7Sty0R88ZpX5wLp8ZdgXCEc5zZDQziYB1MSXF9hsmby5wKbxFF+OvW/zKADThk1gprgAgnEOucyoao+cUMHopLlYgMbjnLzdCXP5F9z+fkYTnKF6AEl5eHBpcKQGbPzswnKebRoBVw+bI2I1C/iq+PvBUyndFAexjrvlDQbEKr6qb6AWRVTTfkW2ce6a0ixRuCTq35zEpWpfAqkSKo+X23d/Q4V8R30rDXotOWDZL6o408cMO+UQ17uVA2arA1JNkYfc/AZ0T0z7ze5o/yp93jJPlDgi05Ut4fpCAMZw3X85GxrTlbEtawWgoyUbmMuv4f6QHZLZAerOaJA8DRJkzkzjJJ025bp1HvAECOc4ggdv0cofu4q96shssgNYYZJUPM+q4+0fnGK0pxQTNY9SV6vSaVCVoTZJo6vefW7OiHX2/eLoPKFuUfsKXXEv9OY71gD1xzYg/rpCMAqCTq1dKqqyT1R5fxANnoRX7vwkq+7jkCj2fAfKTnHi9mSuBFsilKLmnsqqWy3IGShMgdxiQwBEk6IWi9C");
                            //accountmap.put("Itemid",order.getItemid());
                            //accountmap.put("fromTime",start);
                            //accountmap.put("toTime",end);
                            //测试环境
                            accountmap.put("token",taskGetOrder.getToken());
                            accountmap.put("Itemid",order.getItemid());
                            accountmap.put("fromTime",taskGetOrder.getFromtime());
                            accountmap.put("toTime", taskGetOrder.getTotime());
                            String accountxml = BindAccountAPI.getGetAccount(accountmap);
                            //真实环境
                            //Map<String, String> accountresmap = addApiTask.exec(ds, accountxml, "https://api.ebay.com/ws/api.dll");
                            //测试环境
                            Map<String, String> accountresmap = addApiTask.exec(d, accountxml, commPars.apiUrl);
                            String accountr1 = accountresmap.get("stat");
                            String accountres = accountresmap.get("message");
                            if ("fail".equalsIgnoreCase(accountr1)) {
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
                                    acc.setCreateUser(taskGetOrder.getUserid());
                                    iTradingOrderGetAccount.saveOrderGetAccount(acc);
                                }
                            }else{
                                return;
                            }*/
                            //----------------
                            //同步外部交易
                            /*d.setApiCallName("GetSellerTransactions");
                            String sellerxml = BindAccountAPI.GetSellerTransactions(taskGetOrder.getToken());//获取接受消息
                            Map<String, String> resSellerMap = addApiTask.exec(d, sellerxml, commPars.apiUrl);
                            //------------------------
                            String sellerR1 = resSellerMap.get("stat");
                            String sellerRes = resSellerMap.get("message");
                            if ("fail".equalsIgnoreCase(sellerR1)) {
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
                                    list.setCreateUser(taskGetOrder.getUserid());
                                    iTradingOrderGetSellerTransactions.saveOrderGetSellerTransactions(list);
                                }
                            } else {
                                return;
                            }*/
                            //---------------------------------------
                            order.setCreateUser(taskGetOrder.getUserid());
                            iTradingOrderGetOrders.saveOrderGetOrders(order);
                        }
                    }

                }else {
                    List<PublicSitemessage> list1=siteMessageService.selectPublicSitemessageByMessage("synchronize_get_order_timer_FAIL","订单获取必要的参数失败:"+taskGetOrder.getId());
                    if(list1!=null&&list1.size()>0){
                        return;
                    }
                    String errors = SamplePaseXml.getVFromXmlString(res, "Errors");
                    TaskMessageVO taskMessageVO = new TaskMessageVO();
                    taskMessageVO.setMessageType(SiteMessageStatic.SYNCHRONIZE_GET_ORDER_TIMER + "_FAIL");
                    taskMessageVO.setMessageTitle("定时同步订单失败!");
                    taskMessageVO.setMessageContext("订单调用用API失败:获取必要的参数失败,！请稍后重试," + errors);
                    taskMessageVO.setMessageTo(taskGetOrder.getUserid());
                    taskMessageVO.setMessageFrom("system");
                    taskMessageVO.setOrderAndSeller("订单获取必要的参数失败:"+taskGetOrder.getId());
                    siteMessageService.addSiteMessage(taskMessageVO);
                    logger.error("Order获取apisessionid失败!" + errors);
                }
                taskGetOrder.setTokenflag(1);
                taskGetOrder.setSavetime(null);
                iTaskGetOrders.saveListTaskGetOrders(taskGetOrder);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
