package com.order.controller;

import com.base.aboutpaypal.domain.PaypalVO;
import com.base.aboutpaypal.service.PayPalService;
import com.base.database.publicd.model.PublicUserConfig;
import com.base.database.trading.model.*;
import com.base.database.userinfo.model.SystemLog;
import com.base.domains.CommonParmVO;
import com.base.domains.SessionVO;
import com.base.domains.querypojos.OrderGetOrdersQuery;
import com.base.domains.querypojos.SystemLogQuery;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.domains.userinfo.UsercontrollerEbayAccountExtend;
import com.base.mybatis.page.Page;
import com.base.mybatis.page.PageJsonBean;
import com.base.sampleapixml.APINameStatic;
import com.base.sampleapixml.BindAccountAPI;
import com.base.userinfo.service.SystemUserManagerService;
import com.base.userinfo.service.UserInfoService;
import com.base.utils.annotations.AvoidDuplicateSubmission;
import com.base.utils.cache.DataDictionarySupport;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.common.DateUtils;
import com.base.utils.common.SystemLogUtils;
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
    @Autowired
    private PayPalService payPalService;
    @Autowired
    private IUsercontrollerEbayAccount iUsercontrollerEbayAccount;
    @Autowired
    private ITradingGetUserCases iTradingGetUserCases;
    @Autowired
    private ITradingDataDictionary iTradingDataDictionary;

   /* @Autowired
    private ITradingOrderVariation iTradingOrderVariation;
    @Autowired
    private ITradingOrderVariationSpecifics iTradingOrderVariationSpecifics;*/
    @Value("${EBAY.API.URL}")
    private String apiUrl;
   /* SystemLogUtils
    log.setEventname();
    log.setOperuser();
    log.setEventdesc();*/
    @RequestMapping("/queryOrdersList.do")
    public ModelAndView queryOrdersList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        String content=request.getParameter("content");
        modelMap.put("content",content);
        return forword("/orders/order/queryOrdersList",modelMap);
    }
    @RequestMapping("/getOrdersList.do")
    public ModelAndView OrdersList(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
        List<PublicUserConfig> configs=new ArrayList<PublicUserConfig>();
        List<PublicUserConfig> list=iPublicUserConfig.selectUserConfigByItemType("orderFolder");
        for(PublicUserConfig config:list){
            String value=config.getConfigValue();
            if(StringUtils.isNotBlank(value)){
                configs.add(config);
            }
        }
        modelMap.put("folders",configs);
        modelMap.put("count",configs.size());
        return forword("/orders/order/getOrdersList",modelMap);
    }
    //跳转日志界面
    @RequestMapping("/viewSystemlog.do")
    public ModelAndView viewSystemlog(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        return forword("/orders/order/viewSystemlog",modelMap);
    }
    /**refleshTabRemark*/
    @RequestMapping("/refleshTabRemark.do")
    @ResponseBody
    public void refleshTabRemark(CommonParmVO commonParmVO,HttpServletRequest request) throws Exception {
        String folderType=request.getParameter("folderType");
        List<PublicUserConfig> configs=new ArrayList<PublicUserConfig>();
        List<PublicUserConfig> list=iPublicUserConfig.selectUserConfigByItemType(folderType);
        for(PublicUserConfig config:list){
            String value=config.getConfigValue();
            if(StringUtils.isNotBlank(value)){
                configs.add(config);
            }
        }
        AjaxSupport.sendSuccessText("", configs);
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
    //选择地区
    @RequestMapping("/selectCountrys.do")
    public ModelAndView selectCountrys(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        String countryNames=request.getParameter("countryNames");
        String num=request.getParameter("num");
        List<TradingDataDictionary> lidata = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.DATA_DICT_DELTA);
        List<TradingDataDictionary> li1 = new ArrayList();
        List<String> names=new ArrayList<String>();
        List<String> countryIds=new ArrayList<String>();
        for(TradingDataDictionary tdd : lidata){
            if(tdd.getName1().equals("International")){
                li1.add(tdd);
                names.add(tdd.getName());
                countryIds.add(String.valueOf(tdd.getId()));
            }
        }
        String root=request.getContextPath();
        modelMap.put("countrys",li1);
        modelMap.put("count",li1.size());
        modelMap.put("names",names);
        modelMap.put("countryIds",countryIds);
        modelMap.put("root",root);
        modelMap.put("num",num);
        modelMap.put("countryNames",countryNames);
        return forword("/orders/order/selectCountrys",modelMap);
    }

    /**获取国家*/
    @RequestMapping("/selectCountry.do")
    @ResponseBody
    public void selectCountry(CommonParmVO commonParmVO,HttpServletRequest request) throws Exception {
        String countryId=request.getParameter("countryId");
        List<TradingDataDictionary> lidata = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.DATA_DICT_COUNTRY,Long.parseLong(countryId));
        AjaxSupport.sendSuccessText("", lidata);
    }
    /*//将文件夹设置到order中
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
    }*/
    //删除文件夹
    @RequestMapping("/removeOrderTabremark.do")
    public void removeOrderTabremark(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws Exception {
        String id=request.getParameter("id");
        PublicUserConfig config=iPublicUserConfig.selectUserConfigById(Long.valueOf(id));
        SessionVO sessionVO= SessionCacheSupport.getSessionVO();
        SystemLog systemLog=new SystemLog();
        systemLog.setEventname(SystemLogUtils.ORDER_OPERATE_RECORD);
        systemLog.setOperuser(sessionVO.getUserName());
        if(config!=null){
            List<UsercontrollerEbayAccountExtend> ebays=userInfoService.getEbayAccountForCurrUser(new HashMap(),Page.newAOnePage());
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
            systemLog.setEventdesc("删除文件夹成功");
            SystemLogUtils.saveLog(systemLog);
            AjaxSupport.sendSuccessText("", "删除文件夹成功");
            return;
        }else{
            systemLog.setEventdesc("删除文件夹失败");
            SystemLogUtils.saveLog(systemLog);
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
        String id=request.getParameter("id");
        TradingOrderGetOrders order=iTradingOrderGetOrders.selectOrderGetOrdersById(Long.valueOf(id));
        modelMap.put("order",order);
        return forword("/orders/order/addComment",modelMap);
    }
    //移动订单到指定文件夹初始化
    @RequestMapping("/moveFolder.do")
    public ModelAndView moveFolder(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        String table=request.getParameter("table");
        String table1=request.getParameter("table1");
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
        modelMap.put("table",table);
        modelMap.put("table1",table1);
        return forword("/orders/order/moveFolder",modelMap);
    }
    //删除订单
    @RequestMapping("/deleteOrder.do")
    public void deleteOrder(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws Exception {
        String id=request.getParameter("id");
        SessionVO sessionVO= SessionCacheSupport.getSessionVO();
        SystemLog systemLog=new SystemLog();
        systemLog.setEventname(SystemLogUtils.ORDER_OPERATE_RECORD);
        systemLog.setOperuser(sessionVO.getUserName());
        if(StringUtils.isNotBlank(id)){
            iTradingOrderGetOrders.deleteOrderGetOrders(Long.valueOf(id));
            systemLog.setEventdesc("订单删除成功");
            SystemLogUtils.saveLog(systemLog);
            AjaxSupport.sendSuccessText("","删除成功");
        }else{
            systemLog.setEventdesc("订单删除失败");
            SystemLogUtils.saveLog(systemLog);
            AjaxSupport.sendFailText("fail","订单不存在,请核实");
        }
    }
    //保存订单到指定文件夹
    @RequestMapping("/saveFolder.do")
    public void saveFolder(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws Exception {
        String folderId=request.getParameter("folderId");
        SessionVO sessionVO= SessionCacheSupport.getSessionVO();
        SystemLog systemLog=new SystemLog();
        systemLog.setEventname(SystemLogUtils.ORDER_OPERATE_RECORD);
        systemLog.setOperuser(sessionVO.getUserName());
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
            systemLog.setEventdesc("移动文件夹失败");
            SystemLogUtils.saveLog(systemLog);
            AjaxSupport.sendFailText("fail","文件夹不存在");
            return;
        }
        systemLog.setEventdesc("移动文件夹成功");
        SystemLogUtils.saveLog(systemLog);
        AjaxSupport.sendSuccessText("","已经移动到指定的文件夹中");

    }
    //保存备注
    @RequestMapping("/saveComment.do")
    public void saveComment(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws Exception {
        String orderid=request.getParameter("orderid");
        String comment=request.getParameter("comment");
        SessionVO sessionVO= SessionCacheSupport.getSessionVO();
        SystemLog systemLog=new SystemLog();
        systemLog.setEventname(SystemLogUtils.ORDER_OPERATE_RECORD);
        systemLog.setOperuser(sessionVO.getUserName());
        if(StringUtils.isNotBlank(orderid)){
            List<TradingOrderGetOrders> orders=iTradingOrderGetOrders.selectOrderGetOrdersByOrderId(orderid);
            for(TradingOrderGetOrders order:orders){
                order.setComment(comment);
                iTradingOrderGetOrders.saveOrderGetOrders(order);
            }
            systemLog.setEventdesc("保存备注成功");
            SystemLogUtils.saveLog(systemLog);
            AjaxSupport.sendSuccessText("","添加备注成功");
        }else{
            systemLog.setEventdesc("保存备注失败");
            SystemLogUtils.saveLog(systemLog);
            AjaxSupport.sendFailText("fail","该订单不存在");
        }
    }
    //保存文件夹
    @RequestMapping("/saveTabremark.do")
    public void saveTabremark(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws Exception {
        String tabName=request.getParameter("tabName");
        String folderType=request.getParameter("folderType");
        SessionVO sessionVO= SessionCacheSupport.getSessionVO();
        SystemLog systemLog=new SystemLog();
        systemLog.setEventname(SystemLogUtils.ORDER_OPERATE_RECORD);
        systemLog.setOperuser(sessionVO.getUserName());
        if(!StringUtils.isNotBlank(tabName)){
            systemLog.setEventdesc("保存文件夹失败");
            SystemLogUtils.saveLog(systemLog);
            AjaxSupport.sendFailText("fail","文件夹名称不能为空");
            return;
        }
        PublicUserConfig config=new PublicUserConfig();
        config.setConfigType(folderType);
        config.setConfigName(tabName);
        config.setConfigValue("true");
        iPublicUserConfig.saveUserConfig(config);
        systemLog.setEventdesc("保存文件夹成功");
        SystemLogUtils.saveLog(systemLog);
        AjaxSupport.sendSuccessText("", config);
    }
    /**获取list数据的ajax方法*/
    @RequestMapping("/ajax/loadSystemList.do")
    @ResponseBody
    public void loadSystemList(CommonParmVO commonParmVO,HttpServletRequest request) throws Exception {
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        Page page=jsonBean.toPage();
        Map map=new HashMap();
        SessionVO sessionVO= SessionCacheSupport.getSessionVO();
        map.put("userName",sessionVO.getUserName());
        map.put("eventName",SystemLogUtils.ORDER_OPERATE_RECORD);
        List<SystemLogQuery> lists=SystemLogUtils.selectSystemLogList(map,page);
        jsonBean.setList(lists);
        jsonBean.setTotal((int)page.getTotalCount());
        AjaxSupport.sendSuccessText("", jsonBean);
    }
    /**获取list数据的ajax方法*/
    @RequestMapping("/ajax/loadOrdersList.do")
    @ResponseBody
    public void loadOrdersList(CommonParmVO commonParmVO,HttpServletRequest request) throws Exception {
        String countryQ=request.getParameter("countryQ");
        String typeQ=request.getParameter("typeQ");
        String daysQ=request.getParameter("daysQ");
        String statusQ=request.getParameter("statusQ");
        String itemType=request.getParameter("itemType");
        String content=request.getParameter("content");
        String status=request.getParameter("status");
        String folderId=request.getParameter("folderId");
        String framConten=request.getParameter("framConten");
        String starttime1=request.getParameter("starttime1");
        String endtime1=request.getParameter("endtime1");
        String orderby=request.getParameter("orderby");
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
        if(!StringUtils.isNotBlank(orderby)){
            orderby=null;
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
        String statusQ1=null;
        if(StringUtils.isNotBlank(statusQ)){
            if("1".equals(statusQ)){
                status="Complete";
            }else{
                statusQ1=statusQ;
            }
        }
        if(!StringUtils.isNotBlank(itemType)){
            itemType=null;
        }
        if(!StringUtils.isNotBlank(folderId)){
            folderId=null;
        }
        if(!StringUtils.isNotBlank(framConten)){
            framConten=null;
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
        List<OrderGetOrdersQuery> lists=new ArrayList<OrderGetOrdersQuery>();
        if(ebays.size()>0){
            map.put("ebays",ebays);
            map.put("starttime",starttime);
            map.put("endtime",endtime);
            map.put("status",status);
            map.put("countryQ",countryQ);
            map.put("typeQ",typeQ);
            map.put("content",content);
            map.put("folderId",folderId);
            map.put("itemType",itemType);
            map.put("framConten",framConten);
            map.put("statusQ1",statusQ1);
            map.put("orderby",orderby);
            lists= this.iTradingOrderGetOrders.selectOrderGetOrdersByGroupList(map,page);
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
                    List<TradingDataDictionary> dictionarys=iTradingDataDictionary.selectDictionaryByType("site",null,null,itemList.get(0).getSite());
                    if(dictionarys.size()>0){
                        String root=request.getContextPath();
                        list.setItemSite(root+dictionarys.get(0).getImgurl());
                        list.setSiteName(dictionarys.get(0).getValue());
                    }
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
                List<TradingOrderAddMemberMessageAAQToPartner> partners=iTradingOrderAddMemberMessageAAQToPartner.selectTradingOrderAddMemberMessageAAQToPartnerByTransactionId(list.getTransactionid(),3,list.getSelleruserid());
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
        List<UsercontrollerEbayAccountExtend> ebays = userInfoService.getEbayAccountForCurrUser(new HashMap(),Page.newAOnePage());
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
    /*    SessionVO sessionVO= SessionCacheSupport.getSessionVO();
        SystemLog systemLog=new SystemLog();
        systemLog.setEventname(SystemLogUtils.ORDER_OPERATE_RECORD);
        systemLog.setOperuser(sessionVO.getUserName());
        systemLog.setEventdesc("上传跟踪号成功");
        SystemLogUtils.saveLog(systemLog);*/
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
        modelMap.put("accs",accs);
        modelMap.put("flag","true");
        /*Map<String,String> map=new HashMap<String, String>();
        map.put("orderStatus","Completed");
        map.put("selleraccount",lists.get(0).getSelleruserid());
        map.put("buyaccount",lists.get(0).getBuyeruserid());
        List<OrderGetOrdersQuery> querys= this.iTradingOrderGetOrders.selectOrderGetOrdersByGroupList(map,page);*/
        return forword("orders/order/viewOrderGetOrders",modelMap);
    }
    //退款选项初始化
    @RequestMapping("/initIssueRefund.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView initIssueRefund(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        String transactionid=request.getParameter("transactionid");
        String selleruserid=request.getParameter("selleruserid");
        List<TradingOrderGetOrders> orders=iTradingOrderGetOrders.selectOrderGetOrdersByTransactionId(transactionid,selleruserid);
        modelMap.put("order",orders.get(0));
        return forword("orders/order/issueRefund",modelMap);
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
    *批量上传跟踪号
    */
    @RequestMapping("/modifyOrderNums.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView modifyOrderNums(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        List<String> ids=new ArrayList<String>();
        int i=0;
        while(i>=0){
            String id=request.getParameter("id["+i+"]");
            if(id!=null){
                ids.add(id);
                i++;
            }else{
                i=-1;
            }
        }
        List<TradingOrderGetOrders> orders=new ArrayList<TradingOrderGetOrders>();
        for(String id1:ids){
            Long id=Long.valueOf(id1);
            TradingOrderGetOrders order=iTradingOrderGetOrders.selectOrderGetOrdersById(id);
            if(order!=null){
                orders.add(order);
            }
        }
        modelMap.put("orders",orders);
        return forword("orders/order/modifyOrderNums",modelMap);
    }

    /*
    *退款
    */
    @RequestMapping("/sendRefund.do")
    @AvoidDuplicateSubmission(needRemoveToken = true)
    @ResponseBody
    public void sendRefund(CommonParmVO commonParmVO,HttpServletRequest request) throws Exception {
        String transactionid=request.getParameter("transactionid");
        String selleruserid=request.getParameter("selleruserid");
        String fullRefund=request.getParameter("fullRefund");
        String partialRefund=request.getParameter("partialRefund");
        String amout=request.getParameter("amout");
        SessionVO sessionVO= SessionCacheSupport.getSessionVO();
        SystemLog systemLog=new SystemLog();
        systemLog.setEventname(SystemLogUtils.ORDER_OPERATE_RECORD);
        systemLog.setOperuser(sessionVO.getUserName());
        List<TradingGetUserCases> caseses=iTradingGetUserCases.selectGetUserCasesByTransactionId(transactionid,selleruserid);
        List<TradingOrderGetOrders> orders=iTradingOrderGetOrders.selectOrderGetOrdersByTransactionId(transactionid,selleruserid);
        if(StringUtils.isNotBlank(fullRefund)&&StringUtils.isNotBlank(partialRefund)){
            AjaxSupport.sendFailText("fail","请选择一个");
            return;
        }
        if(caseses==null||caseses.size()==0){
            AjaxSupport.sendFailText("fail","该订单无纠纷");
            return;
        }
        if(orders==null||orders.size()==0){
            AjaxSupport.sendFailText("fail","该订单无效,请核实订单");
            return;
        }
        if("Active".equals(orders.get(0).getOrderstatus())){
            AjaxSupport.sendFailText("fail","该订单未付款");
            return;
        }
        UsercontrollerDevAccountExtend d=new UsercontrollerDevAccountExtend();
        List<UsercontrollerEbayAccountExtend> dList= userInfoService.getEbayAccountForCurrUser(new HashMap(),Page.newAOnePage());
        String token=null;
        for(UsercontrollerEbayAccountExtend list:dList){
            if(StringUtils.isNotBlank(selleruserid)&&selleruserid.equals(list.getEbayName())){
                token=list.getEbayToken();
            }
        }
       /* String token="AgAAAA**AQAAAA**aAAAAA**CLSRUQ**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6AFlYCjDJGCqA+dj6x9nY+seQ**FdYBAA**AAMAAA**w2sMbwlQ7TBHWxj9EsVedHQRI3+lonY9MDfiyayQbnFkjEanjL/yMCpS/D2B9xHRzRx+ppxWZkRPgeAKJvNotPLLrVTuEzOl5M7pi6Tw8+pzcmIEsOh7HQO78JlyFlvLc/ruE6/hG0E/HO1UX76YBwxp00N9f1NNUpo5u36D/TYsx5O2jXFTKkCOHwz6RW9vtN6TU39aLm+JQme2+NfFFXnbX8MHzoUiX7Sty0R88ZpX5wLp8ZdgXCEc5zZDQziYB1MSXF9hsmby5wKbxFF+OvW/zKADThk1gprgAgnEOucyoao+cUMHopLlYgMbjnLzdCXP5F9z+fkYTnKF6AEl5eHBpcKQGbPzswnKebRoBVw+bI2I1C/iq+PvBUyndFAexjrvlDQbEKr6qb6AWRVTTfkW2ce6a0ixRuCTq35zEpWpfAqkSKo+X23d/Q4V8R30rDXotOWDZL6o408cMO+UQ17uVA2arA1JNkYfc/AZ0T0z7ze5o/yp93jJPlDgi05Ut4fpCAMZw3X85GxrTlbEtawWgoyUbmMuv4f6QHZLZAerOaJA8DRJkzkzjJJ025bp1HvAECOc4ggdv0cofu4q96shssgNYYZJUPM+q4+0fnGK0pxQTNY9SV6vSaVCVoTZJo6vefW7OiHX2/eLoPKFuUfsKXXEv9OY71gD1xzYg/rpCMAqCTq1dKqqyT1R5fxANnoRX7vwkq+7jkCj2fAfKTnHi9mSuBFsilKLmnsqqWy3IGShMgdxiQwBEk6IWi9C";
        */
        d.setSoaSecurityToken(token);
        d.setHeaderType("DisputeApiHeader");
        if(caseses!=null&&caseses.size()>0){
            String caseId=caseses.get(0).getCaseid();
            String caseType=caseses.get(0).getCasetype();
            String xml="";
            //issueFullRefund退全款
            if(fullRefund!=null){
                d.setSoaOperationName("issueFullRefund");
                xml=BindAccountAPI.issueFullRefund(token,caseId,caseType,"");
            }
            //issuePartialRefund退半款
            if(partialRefund!=null){
                d.setSoaOperationName("issuePartialRefund");
                xml=BindAccountAPI.issuePartialRefund(token,caseId,caseType,"",amout);
            }
            AddApiTask addApiTask = new AddApiTask();
            Map<String, String> resEbpMap = addApiTask.exec(d, xml, "https://svcs.ebay.com/services/resolution/ResolutionCaseManagementService/v1");
            String r1 = resEbpMap.get("stat");
            String res = resEbpMap.get("message");
            if ("fail".equalsIgnoreCase(r1)) {
                systemLog.setEventdesc("退款操作失败:调用API失败");
                SystemLogUtils.saveLog(systemLog);
                AjaxSupport.sendFailText("fail", res);
                return;
            }
            String ack = SamplePaseXml.getVFromXmlString(res, "ack");
            if ("Success".equalsIgnoreCase(ack)) {
                TradingGetUserCases cases=caseses.get(0);
                cases.setHandled(1);
                iTradingGetUserCases.saveGetUserCases(cases);
                systemLog.setEventdesc("退款操作成功");
                SystemLogUtils.saveLog(systemLog);
                AjaxSupport.sendSuccessText("message", "退款成功!");
            }else{
                String errors = SamplePaseXml.getVFromXmlString(res, "Errors");
                logger.error("退款失败!" + errors);
                systemLog.setEventdesc("退款操作失败:调用API失败");
                SystemLogUtils.saveLog(systemLog);
                AjaxSupport.sendFailText("fail", "退款失败！请稍后重试");
            }
        }else{
            AjaxSupport.sendFailText("fail","纠纷不存在");
        }
    }
    /*
     *修改订单状态
     */
    @RequestMapping("/apiModifyOrdersMessage.do")
    @AvoidDuplicateSubmission(needRemoveToken = true)
    @ResponseBody
    public void apiModifyOrdersMessage(CommonParmVO commonParmVO,HttpServletRequest request) throws Exception {
        int i=0;
        SessionVO sessionVO = SessionCacheSupport.getSessionVO();
        SystemLog systemLog = new SystemLog();
        systemLog.setEventname(SystemLogUtils.ORDER_OPERATE_RECORD);
        systemLog.setOperuser(sessionVO.getUserName());
        while (i>=0) {
            String itemid = request.getParameter("itemid"+i);
            String transactionid = request.getParameter("transactionid"+i);
            String shippStatus = request.getParameter("shippStatus"+i);
            String ShipmentTrackingNumber = request.getParameter("ShipmentTrackingNumber"+i);
            String ShippingCarrierUsed = request.getParameter("ShippingCarrierUsed"+i);
            if(StringUtils.isNotBlank(transactionid)) {
                UsercontrollerDevAccountExtend d = userInfoService.getDevInfo(null);//开发者帐号id
                d.setApiSiteid("0");
                d.setApiCallName(APINameStatic.CompleteSale);
                Map<String, String> map = new HashMap();
                String ebayName = request.getParameter("selleruserid" + i);
                List<UsercontrollerEbayAccountExtend> dList = userInfoService.getEbayAccountForCurrUser(new HashMap(), Page.newAOnePage());
                String token = null;
                for (UsercontrollerEbayAccountExtend list : dList) {
                    if (StringUtils.isNotBlank(ebayName) && ebayName.equals(list.getEbayName())) {
                        token = list.getEbayToken();
                    }
                }
                map.put("token", token);
                map.put("shippStatus", shippStatus);
                map.put("itemid", itemid);
                map.put("ShipmentTrackingNumber", ShipmentTrackingNumber);
                map.put("ShippingCarrierUsed", ShippingCarrierUsed);
                map.put("transactionid", transactionid);
                String xml = BindAccountAPI.getModifyOrderStatus(map);
                AddApiTask addApiTask = new AddApiTask();
          /*  Map<String, String> resMap = addApiTask.exec(d, xml, "https://api.ebay.com/ws/api.dll");*/
                Map<String, String> resMap = addApiTask.exec(d, xml, apiUrl);
                String r1 = resMap.get("stat");
                String res = resMap.get("message");
                if ("fail".equalsIgnoreCase(r1)) {
                 /*   systemLog.setEventdesc("批量上传跟踪号失败:调用API失败");
                    SystemLogUtils.saveLog(systemLog);*/
                    i = -1;
                  /*  AjaxSupport.sendFailText("fail", res);*/
                }
                String ack = SamplePaseXml.getVFromXmlString(res, "Ack");
                if ("Success".equalsIgnoreCase(ack)) {
                    List<TradingOrderGetOrders> orderList = iTradingOrderGetOrders.selectOrderGetOrdersByTransactionId(transactionid, ebayName);
                    TradingOrderGetOrders order = orderList.get(0);
                    order.setShipmenttrackingnumber(ShipmentTrackingNumber);
                    order.setShippingcarrierused(ShippingCarrierUsed);
                    iTradingOrderGetOrders.saveOrderGetOrders(order);
                    i++;
               /* systemLog.setEventdesc("批量上传跟踪号成功");
                SystemLogUtils.saveLog(systemLog);
                AjaxSupport.sendSuccessText("success", "订单状态修改成功");*/
                } else {
                 /*   systemLog.setEventdesc("批量上传跟踪号失败:调用API错误");
                    SystemLogUtils.saveLog(systemLog);*/
                    i = -1;
                    /*AjaxSupport.sendFailText("fail", "获取必要的参数失败！请稍后重试");*/
                }
            }else if(!StringUtils.isNotBlank(transactionid)&&i==0){
                i=-3;
            }else{
                i=-2;
            }

        }
        if(i==-1){
            systemLog.setEventdesc("批量上传跟踪号失败:调用API错误");
            SystemLogUtils.saveLog(systemLog);
            AjaxSupport.sendFailText("fail", "获取必要的参数失败！请稍后重试");
        }
        if(i==-2){
            systemLog.setEventdesc("批量上传跟踪号成功");
            SystemLogUtils.saveLog(systemLog);
            AjaxSupport.sendSuccessText("success", "订单状态修改成功");
        }
        if(i==-3){
            AjaxSupport.sendFailText("fail", "没找到数据,请核实!");
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
            List<TradingOrderAddMemberMessageAAQToPartner> addmessageList1=iTradingOrderAddMemberMessageAAQToPartner.selectTradingOrderAddMemberMessageAAQToPartnerByTransactionId(order.getTransactionid(),1,order.getSelleruserid());
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
         List<UsercontrollerEbayAccountExtend> dList= userInfoService.getEbayAccountForCurrUser(new HashMap(),Page.newAOnePage());
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
             message1.setReplied("true");
             iTradingOrderAddMemberMessageAAQToPartner.saveOrderAddMemberMessageAAQToPartner(message1);
             AjaxSupport.sendSuccessText("success", "评价发送成功");
         }else{
             TradingOrderAddMemberMessageAAQToPartner message1=new TradingOrderAddMemberMessageAAQToPartner();
             message1.setBody(CommentText);
             message1.setItemid(itemid);
             message1.setRecipientid(buyeruserid);
             message1.setSender(ebayName);
             message1.setMessagetype(3);
             message1.setTransactionid(transactionid);
             message1.setReplied("false");
             iTradingOrderAddMemberMessageAAQToPartner.saveOrderAddMemberMessageAAQToPartner(message1);
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
        Map map=new HashMap();
        List<UsercontrollerEbayAccountExtend> ebays=systemUserManagerService.queryACurrAllEbay(map);
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
        /*if(status==null){
            orders=iTradingOrderGetOrders.selectOrderGetOrdersByFolder(folderId, ebayNames);
        }*/
        if(folderId==null){
            orders=iTradingOrderGetOrders.selectOrderGetOrdersByPaypalStatus(status,ebayNames);
        }else{
            orders=iTradingOrderGetOrders.selectOrderGetOrdersByFolder(folderId, ebayNames);
        }
        response.setHeader("Content-Disposition","attachment;filename=orders.xls");// 组装附件名称和格式
        ServletOutputStream outputStream = response.getOutputStream();
        SessionVO sessionVO= SessionCacheSupport.getSessionVO();
        SystemLog systemLog=new SystemLog();
        systemLog.setEventname(SystemLogUtils.ORDER_OPERATE_RECORD);
        systemLog.setOperuser(sessionVO.getUserName());
        systemLog.setEventdesc("订单下载成功");
        SystemLogUtils.saveLog(systemLog);
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
        SessionVO sessionVO= SessionCacheSupport.getSessionVO();
        SystemLog systemLog=new SystemLog();
        systemLog.setEventname(SystemLogUtils.ORDER_OPERATE_RECORD);
        systemLog.setOperuser(sessionVO.getUserName());
        if(!StringUtils.isNotBlank(itemid)){
            itemid=request.getParameter("itemid1");
        }
        if(!StringUtils.isNotBlank(buyeruserid)){
            buyeruserid=request.getParameter("buyeruserid1");
        }
        if(!StringUtils.isNotBlank(sender)){
            sender=request.getParameter("selleruserid1");
        }
        if(subject.length()>100){
            subject=subject.substring(0,100);
        }
        UsercontrollerDevAccountExtend d = userInfoService.getDevInfo(null);//开发者帐号id
        d.setApiSiteid("0");
        d.setApiCallName(APINameStatic.AddMemberMessageAAQToPartner);
        Map map=new HashMap();
        String ebayName=sender;
        List<UsercontrollerEbayAccountExtend> dList= userInfoService.getEbayAccountForCurrUser(new HashMap(),Page.newAOnePage());
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
            systemLog.setEventdesc("发送消息失败:调用API失败");
            SystemLogUtils.saveLog(systemLog);
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
            message1.setReplied("true");
            iTradingOrderAddMemberMessageAAQToPartner.saveOrderAddMemberMessageAAQToPartner(message1);
            systemLog.setEventdesc("发送消息成功");
            SystemLogUtils.saveLog(systemLog);
            AjaxSupport.sendSuccessText("success", "发送成功");
        }else{
            TradingOrderAddMemberMessageAAQToPartner message1=new TradingOrderAddMemberMessageAAQToPartner();
            message1.setBody(body);
            message1.setItemid(itemid);
            message1.setRecipientid(buyeruserid);
            message1.setSubject(subject);
            message1.setSender(sender);
            message1.setMessagetype(4);
            message1.setTransactionid(transactionid);
            message1.setReplied("false");
            iTradingOrderAddMemberMessageAAQToPartner.saveOrderAddMemberMessageAAQToPartner(message1);
            systemLog.setEventdesc("发送消息失败:调用API失败");
            SystemLogUtils.saveLog(systemLog);
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
        //--测试环境
        UsercontrollerDevAccountExtend d = userInfoService.getDevInfo(null);//开发者帐号id
        d.setApiSiteid("0");
        d.setApiCallName(APINameStatic.GetOrders);
        //--真实环境
       /* UsercontrollerDevAccountExtend d=new UsercontrollerDevAccountExtend();
        d.setApiDevName("5d70d647-b1e2-4c7c-a034-b343d58ca425");
        d.setApiAppName("sandpoin-23af-4f47-a304-242ffed6ff5b");
        d.setApiCertName("165cae7e-4264-4244-adff-e11c3aea204e");
        d.setApiCompatibilityLevel("883");
        d.setApiSiteid("0");
        d.setApiCallName(APINameStatic.GetOrders);*/
        //-------
        request.getSession().setAttribute("dveId", d);
        Map map=new HashMap();
     /*   Date startTime2=DateUtils.subDays(new Date(),9);
        Date endTime= DateUtils.addDays(startTime2, 9);*/
        Date startTime2=DateUtils.subDays(new Date(),90);
        Date endTime= DateUtils.addDays(startTime2, 90);
        Date end1= com.base.utils.common.DateUtils.turnToDateEnd(endTime);
        String start= DateUtils.DateToString(startTime2);
        String end=DateUtils.DateToString(end1);
        //测试环境
        String token=userInfoService.getTokenByEbayID(ebay);
        //真实环境
       /* String token="AgAAAA**AQAAAA**aAAAAA**jek4VA**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6AFlIWnC5iEpAidj6x9nY+seQ**tSsCAA**AAMAAA**y8BaJPw6GUdbbbco8zXEwRR4Ttr9sLd78jL0FyYa0yonvk5hz1RY6DtKkaDtn9NuzluKeFZoqsNbujZP48S4QZhHVa5Dp0bDGqBdKaosolzsrPDm8qozoxbsTiWY8X/M5xev/YU2zJ42/JRGDlEdnQhwCASG1BcSo+DqXuG3asbj0INJr4/HsArf8cCYsPQCtUDkq5QJY6Rvil+Kla/dGhViTQ3gt7a4t3KjxKH+/jlhDU/6sUEKlvb2nY1gCmX8S9pU48c+4Vy6G6NpfcGUcIG/TXFWBTqU0R+v+/6DOIfDW8s90rrLSVMGFqnRxA2sexdEmVhyF5csBmv9+TVfjdyEZK5UgvDqWJHesuDMFTr0KIc8EtdnTQaE3YeZch15DdoEbqcyyBQBZHidBPdDHz/DkpTg7iq1953yKodm2y0mW6aaYAfc5beW+PoqMW8C3WwGJmWZqh3dBi+QEKznEJ9SRg43Bc3q2344JFY7YpIEfJDaQ36BHRcIZxLew8v7RIGL5YYO1BBdTolVV9/eMCQDsUB0mUeMYjxnH5w0K/6CDmJ9WNMQTblNol0x3vhJbil1L/CMP9KGEHj5Yqx0003MLL9Yod7nL89Zpy+a8I/E5byxFt21KZTGE90Ot0LyLpRXsotDwIm5+ZdvATsU6mGADX4tk970CpCeM487v9fn1opouaCBvknCINqXoSeGXLQ7uZFpeqkWts1lIWh9vEuuiuZa4vNoL7aCr+93LTFnsO6AsZp7dmboQcI96I/o";
       *//* String token="AgAAAA**AQAAAA**aAAAAA**j+o4VA**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6AFlICkCpSGoA6dj6x9nY+seQ**tSsCAA**AAMAAA**w9ucV7+/Za+QiC82KESb5VYCbY2/n18LiFAO6UdYHbIhnt/gYioIQe3Aq5aIUnCe3VHmXgHZ9EH4gNMhiqtdYLn4qYVRh3+MfdM58KycszijKBHb8ddmeiswKa7w5LjiMTBT1EZRByoQOuTcXpmnBIVDCSWS0QqIcwKHj7ZOdrpMVrkd/zZQgXWMeYSI/NiKsSGo7MLuEc4dsAz3ifgt/d00HQXrntrr8iwh4HpC8g80mNpQ/2yd/5ZM7mDBGFviMFgUuA2qIWua6/PpCRCmoUTL2t9YoCcOh5qScgUvyRj8qeJtcZ/4VrQT2c1r2Ud6/7EEpm8wDn11NaxLJqRZJN/BXT1SD7+5qf5W4CpIsHXN7Xklao2Y4qrKoGdxIKEs1Yya4T58Bs0PIv9Iupfz3ogibPK3KMETosJnUZxteo08JhEO8iWufLBEyfCK5gbVsueZz4BXYoARqcoeJ5Us3mDw26oxx5GtyVC7ipKefnmBghY5nbD1kzOZ9hAuFIyhjedZLj2qORajy7wnsa87A0YI6EOLIk1v7szqRUNPBsY4m2AFSYdAjLnwOiOirv0TbfYOS2ofMD1+AQPR5nbopvwLYYMUi/LlA3UUEU3zEzHpPP+A4rMIVUzjXEGgq0LzwipwtALpnS1CfOXq0FDayN3J6q2PLs0jveF+nmop8yOPkZRRyd4EtuCRYD+ADxQHRZ89+zVqwFg0xGVQe3r1V95wga23esmbmgVj2E5s7tq1GM8+e5SA/x3H9fzP6/pK";
        */
        map.put("token", token);
/*        map.put("token","AgAAAA**AQAAAA**aAAAAA**CLSRUQ**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6AFlYCjDJGCqA+dj6x9nY+seQ**FdYBAA**AAMAAA**w2sMbwlQ7TBHWxj9EsVedHQRI3+lonY9MDfiyayQbnFkjEanjL/yMCpS/D2B9xHRzRx+ppxWZkRPgeAKJvNotPLLrVTuEzOl5M7pi6Tw8+pzcmIEsOh7HQO78JlyFlvLc/ruE6/hG0E/HO1UX76YBwxp00N9f1NNUpo5u36D/TYsx5O2jXFTKkCOHwz6RW9vtN6TU39aLm+JQme2+NfFFXnbX8MHzoUiX7Sty0R88ZpX5wLp8ZdgXCEc5zZDQziYB1MSXF9hsmby5wKbxFF+OvW/zKADThk1gprgAgnEOucyoao+cUMHopLlYgMbjnLzdCXP5F9z+fkYTnKF6AEl5eHBpcKQGbPzswnKebRoBVw+bI2I1C/iq+PvBUyndFAexjrvlDQbEKr6qb6AWRVTTfkW2ce6a0ixRuCTq35zEpWpfAqkSKo+X23d/Q4V8R30rDXotOWDZL6o408cMO+UQ17uVA2arA1JNkYfc/AZ0T0z7ze5o/yp93jJPlDgi05Ut4fpCAMZw3X85GxrTlbEtawWgoyUbmMuv4f6QHZLZAerOaJA8DRJkzkzjJJ025bp1HvAECOc4ggdv0cofu4q96shssgNYYZJUPM+q4+0fnGK0pxQTNY9SV6vSaVCVoTZJo6vefW7OiHX2/eLoPKFuUfsKXXEv9OY71gD1xzYg/rpCMAqCTq1dKqqyT1R5fxANnoRX7vwkq+7jkCj2fAfKTnHi9mSuBFsilKLmnsqqWy3IGShMgdxiQwBEk6IWi9C");*/
        map.put("fromTime", start);
        map.put("toTime", end);
        map.put("page","1");
        String xml = BindAccountAPI.getGetOrders(map);
        AddApiTask addApiTask = new AddApiTask();

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
        //测试环境
        addApiTask.execDelayReturn(d, xml,apiUrl, taskMessageVO);
        //真实环境
        /*addApiTask.execDelayReturn(d, xml,"https://api.ebay.com/ws/api.dll", taskMessageVO);*/
        AjaxSupport.sendSuccessText("message", "操作成功！结果请稍后查看消息！");
    }
    /*public Boolean queryTrack(String statusQ,OrderGetOrdersQuery query) throws Exception {
        BufferedReader in = null;
        String content = null;
        String trackNum=query.getShipmenttrackingnumber();
        String token=(URLEncoder.encode("RXYaxblwfBeNY+2zFVDbCYTz91r+VNWmyMTgXE4v16gCffJam2FcsPUpiau6F8Yk"));
        String url="http://api.91track.com/track?culture=zh-CN&numbers="+trackNum+"&token="+token;
        *//*String url="http://api.91track.com/track?culture=en&numbers="+"RD275816257CN"+"&token="+token;*//*
        HttpClient client=new DefaultHttpClient();
        HttpGet get=new HttpGet();
        get.setURI(new URI(url));
        HttpResponse response = client.execute(get);

        in = new BufferedReader(new InputStreamReader(response.getEntity()
                .getContent()));
        StringBuffer sb = new StringBuffer("");
        String line ="";
        String NL = System.getProperty("line.separator");
        while ((line = in.readLine()) != null) {
            sb.append(line + NL);
        }
        in.close();
        content = sb.toString();
        String[] arr=content.split(",");
        String content1=arr[1];
        if((content1.contains("1")||content1.contains("2"))&&"4".equals(statusQ)){
            return true;
        }
        if((content1.contains("3")||content1.contains("4"))&&"3".equals(statusQ)){
            return true;
        }
         return false;
    }*/
}
