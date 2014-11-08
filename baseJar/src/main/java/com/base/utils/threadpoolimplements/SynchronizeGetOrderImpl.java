package com.base.utils.threadpoolimplements;

import com.base.database.trading.model.*;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.sampleapixml.APINameStatic;
import com.base.sampleapixml.BindAccountAPI;
import com.base.sampleapixml.GetOrderItemAPI;
import com.base.sampleapixml.GetOrdersAPI;
import com.base.utils.common.DateUtils;
import com.base.utils.threadpool.AddApiTask;
import com.base.utils.threadpool.TaskMessageVO;
import com.base.utils.xmlutils.SamplePaseXml;
import com.sitemessage.service.SiteMessageStatic;
import com.trading.service.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/9/13.
 */
@Service
public class SynchronizeGetOrderImpl implements ThreadPoolBaseInterFace {
    static Logger logger = Logger.getLogger(SynchronizeGetOrderImpl.class);
    @Value("${EBAY.API.URL}")
    private String apiUrl;
    @Autowired
    private ITradingOrderGetOrders iTradingOrderGetOrders;
    @Autowired
    private ITradingOrderShippingServiceOptions iTradingOrderShippingServiceOptions;
    @Autowired
    private ITradingOrderGetItem iTradingOrderGetItem;
    @Autowired
    private ITradingOrderListingDetails iTradingOrderListingDetails;
    @Autowired
    private ITradingOrderSeller iTradingOrderSeller;
    @Autowired
    private ITradingOrderSellingStatus iTradingOrderSellingStatus;
    @Autowired
    private ITradingOrderShippingDetails iTradingOrderShippingDetails;
    @Autowired
    private ITradingOrderPictureDetails iTradingOrderPictureDetails;
    @Autowired
    private ITradingOrderReturnpolicy iTradingOrderReturnpolicy;
    @Autowired
    private ITradingOrderSellerInformation iTradingOrderSellerInformation;
    @Autowired
    private  ITradingOrderCalculatedShippingRate iTradingOrderCalculatedShippingRate;
    @Autowired
    private  ITradingMessageGetmymessage iTradingMessageGetmymessage;
    @Autowired
    private ITradingOrderSenderAddress iTradingOrderSenderAddress;
    @Autowired
    private  ITradingOrderAddMemberMessageAAQToPartner iTradingOrderAddMemberMessageAAQToPartner;
    @Autowired
    private  ITradingOrderGetAccount iTradingOrderGetAccount;
    @Autowired
    private  ITradingOrderItemSpecifics iTradingOrderItemSpecifics;
    @Autowired
    private  ITradingOrderVariation iTradingOrderVariation;
    @Autowired
    private  ITradingOrderVariationSpecifics iTradingOrderVariationSpecifics;
    @Autowired
    private  ITradingOrderPictures iTradingOrderPictures;
    @Autowired
    private  ITradingOrderGetSellerTransactions iTradingOrderGetSellerTransactions;
    @Autowired
    private ITradingOrderOrderVariationSpecifics iTradingOrderOrderVariationSpecifics;
    @Autowired
    private ITradingAutoMessage iTradingAutoMessage;
    @Autowired
    private ITradingMessageTemplate iTradingMessageTemplate;
    @Autowired
    private IUsercontrollerEbayAccount iUsercontrollerEbayAccount;
    @Override
    public <T> void doWork(String res, T... t) {
        if(StringUtils.isEmpty(res)){return;}
        TaskMessageVO taskMessageVO = (TaskMessageVO)t[0];
        Map m= (Map) taskMessageVO.getObjClass();
        String ack = null;
        try {
            Map map=new HashMap();
            Date startTime2= DateUtils.subDays(new Date(), 9);
            Date endTime= DateUtils.addDays(startTime2, 9);
            /*Date startTime2= DateUtils.subDays(new Date(),90);
            Date endTime= DateUtils.addDays(startTime2,90);*/
            Date end1= com.base.utils.common.DateUtils.turnToDateEnd(endTime);
            String start= DateUtils.DateToString(startTime2);
            String end=DateUtils.DateToString(end1);
            String token= (String) m.get("token");
            UsercontrollerDevAccountExtend d= (UsercontrollerDevAccountExtend) m.get("d");
            d.setApiSiteid("0");
            d.setApiCallName(APINameStatic.GetOrders);
            map.put("token", token);
            map.put("fromTime", start);
            map.put("toTime", end);
            map.put("page","1");
            AddApiTask addApiTask = new AddApiTask();
            ack = SamplePaseXml.getVFromXmlString(res, "Ack");
            if ("Success".equalsIgnoreCase(ack)) {

                Map<String,Object> mapOrder= GetOrdersAPI.parseXMLAndSave(res);
                String totalPage1= (String) mapOrder.get("totalPage");
                String page1= (String) mapOrder.get("page");
                int totalPage= Integer.parseInt(totalPage1);
                for(int i=1;i<=totalPage;i++) {
                    if (i != 1) {
                        map.put("page", i + "");
                        String xml = BindAccountAPI.getGetOrders(map);
                        //--测试环境
                        Map<String, String>  resMap = addApiTask.exec(d, xml, apiUrl);
                        //--真实环境
                      /*  Map<String, String>  resMap = addApiTask.exec(d, xml, "https://api.ebay.com/ws/api.dll");*/
                   /* resMap = addApiTask.exec(d, xml, "https://api.ebay.com/ws/api.dll");*/
                        String r1 = resMap.get("stat");
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
                                orderOrderVariationSpecifics.setCreateUser(taskMessageVO.getMessageTo());
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
                                   /* if(partner.getCreateUser()==1){*/
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
                        Map<String,String> itemresmap= GetOrderItemAPI.apiGetOrderItem(d, token, apiUrl, order.getItemid());
                        //真实环境
                        /*Map<String,String> itemresmap= GetOrderItemAPI.apiGetOrderItem(d, token, "https://api.ebay.com/ws/api.dll", order.getItemid());*/
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
                            shippingRate.setCreateUser(taskMessageVO.getMessageTo());
                            shippingDetails.setCreateUser(taskMessageVO.getMessageTo());
                            sellerInformation.setCreateUser(taskMessageVO.getMessageTo());
                            listingDetails.setCreateUser(taskMessageVO.getMessageTo());
                            orderSeller.setCreateUser(taskMessageVO.getMessageTo());
                            sellingStatus.setCreateUser(taskMessageVO.getMessageTo());
                            pictureDetails.setCreateUser(taskMessageVO.getMessageTo());
                            returnpolicy.setCreateUser(taskMessageVO.getMessageTo());
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
                                serviceOptionse.setCreateUser(taskMessageVO.getMessageTo());
                                iTradingOrderShippingServiceOptions.saveOrderShippingServiceOptions(serviceOptionse);
                            }
                            order.setShippingdetailsId(shippingDetails.getId());
                            item.setCreateUser(taskMessageVO.getMessageTo());
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
                                specifics.setCreateUser(taskMessageVO.getMessageTo());
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
                                specifics.setCreateUser(taskMessageVO.getMessageTo());
                                iTradingOrderVariation.saveOrderVariation(specifics);
                                List<TradingOrderVariationSpecifics> specificsList= (List<TradingOrderVariationSpecifics>) items.get(GetOrderItemAPI.VARIATION_SPECIFICS);
                                for(TradingOrderVariationSpecifics specificsVariation:specificsList){
                                    specificsVariation.setOrdervariationId(specifics.getId());
                                    specificsVariation.setCreateUser(taskMessageVO.getMessageTo());
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
                                specifics.setCreateUser(taskMessageVO.getMessageTo());
                                iTradingOrderPictures.saveOrderPictures(specifics);
                            }
                        }else {
                            return;
                        }
                        //同步account-----
                       /*UsercontrollerDevAccountExtend ds=new UsercontrollerDevAccountExtend();
                       ds.setApiDevName("5d70d647-b1e2-4c7c-a034-b343d58ca425");
                       ds.setApiAppName("sandpoin-23af-4f47-a304-242ffed6ff5b");
                       ds.setApiCertName("165cae7e-4264-4244-adff-e11c3aea204e");
                       ds.setApiCompatibilityLevel("883");
                       ds.setApiSiteid("0");
                      ds.setApiCallName("GetAccount");
                     Map accountmap=new HashMap();
                    accountmap.put("token","AgAAAA**AQAAAA**aAAAAA**CLSRUQ**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6AFlYCjDJGCqA+dj6x9nY+seQ**FdYBAA**AAMAAA**w2sMbwlQ7TBHWxj9EsVedHQRI3+lonY9MDfiyayQbnFkjEanjL/yMCpS/D2B9xHRzRx+ppxWZkRPgeAKJvNotPLLrVTuEzOl5M7pi6Tw8+pzcmIEsOh7HQO78JlyFlvLc/ruE6/hG0E/HO1UX76YBwxp00N9f1NNUpo5u36D/TYsx5O2jXFTKkCOHwz6RW9vtN6TU39aLm+JQme2+NfFFXnbX8MHzoUiX7Sty0R88ZpX5wLp8ZdgXCEc5zZDQziYB1MSXF9hsmby5wKbxFF+OvW/zKADThk1gprgAgnEOucyoao+cUMHopLlYgMbjnLzdCXP5F9z+fkYTnKF6AEl5eHBpcKQGbPzswnKebRoBVw+bI2I1C/iq+PvBUyndFAexjrvlDQbEKr6qb6AWRVTTfkW2ce6a0ixRuCTq35zEpWpfAqkSKo+X23d/Q4V8R30rDXotOWDZL6o408cMO+UQ17uVA2arA1JNkYfc/AZ0T0z7ze5o/yp93jJPlDgi05Ut4fpCAMZw3X85GxrTlbEtawWgoyUbmMuv4f6QHZLZAerOaJA8DRJkzkzjJJ025bp1HvAECOc4ggdv0cofu4q96shssgNYYZJUPM+q4+0fnGK0pxQTNY9SV6vSaVCVoTZJo6vefW7OiHX2/eLoPKFuUfsKXXEv9OY71gD1xzYg/rpCMAqCTq1dKqqyT1R5fxANnoRX7vwkq+7jkCj2fAfKTnHi9mSuBFsilKLmnsqqWy3IGShMgdxiQwBEk6IWi9C");
                    accountmap.put("Itemid","161282030915");
                    accountmap.put("fromTime", start);
                    accountmap.put("toTime", end);
                   *//* d.setApiCallName(APINameStatic.GetAccount);
                    Map accountmap=new HashMap();
                    accountmap.put("token",token);
                    accountmap.put("Itemid",order.getItemid());
                    accountmap.put("fromTime", start);
                    accountmap.put("toTime", end);*//*
                    String accountxml = BindAccountAPI.getGetAccount(accountmap);
                    Map<String, String> accountresmap = addApiTask.exec(ds, accountxml, "https://api.ebay.com/ws/api.dll");
                   *//* Map<String, String> accountresmap = addApiTask.exec(d, accountxml, apiUrl);*//*
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
                            acc.setCreateUser(taskMessageVO.getMessageTo());
                            iTradingOrderGetAccount.saveOrderGetAccount(acc);
                        }
                    }else{
                        return;
                    }*/
                        //----------------
                        //同步外部交易
                        /*d.setApiCallName("GetSellerTransactions");
                        String sellerxml = BindAccountAPI.GetSellerTransactions(token);//获取接受消息
                        Map<String, String> resSellerMap = addApiTask.exec(d, sellerxml, apiUrl);
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
                                list.setCreateUser(taskMessageVO.getMessageTo());
                                iTradingOrderGetSellerTransactions.saveOrderGetSellerTransactions(list);
                            }
                        } else {
                            return;
                        }*/
                        //---------------------------------------
                        order.setCreateUser(taskMessageVO.getMessageTo());
                        iTradingOrderGetOrders.saveOrderGetOrders(order);
                    }
                }
            }else {return;}
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("解析xml出错,请稍后到ebay网站确认结果");
            return;
        }
    }
    private String dateToString(Date date){
        String d=null;
        if(date!=null){
            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String startTime=f.format(date);
            d=startTime.substring(0,4)+startTime.substring(5,7)+startTime.substring(8,10);
        }
        return d;
    }
    @Override
    public String getType() {
        return SiteMessageStatic.SYNCHRONIZE_GET_ORDER_BEAN;
    }
}
