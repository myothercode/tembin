package com.automessage.controller;

import com.base.database.trading.model.TradingAutoMessage;
import com.base.database.trading.model.TradingAutoMessageAttr;
import com.base.database.trading.model.TradingDataDictionary;
import com.base.domains.CommonParmVO;
import com.base.domains.SessionVO;
import com.base.domains.querypojos.AutoMessageQuery;
import com.base.domains.userinfo.UsercontrollerEbayAccountExtend;
import com.base.domains.userinfo.UsercontrollerUserExtend;
import com.base.mybatis.page.Page;
import com.base.mybatis.page.PageJsonBean;
import com.base.userinfo.service.SystemUserManagerService;
import com.base.utils.annotations.AvoidDuplicateSubmission;
import com.base.utils.cache.DataDictionarySupport;
import com.base.utils.cache.SessionCacheSupport;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
import com.trading.service.ITradingAutoMessage;
import com.trading.service.ITradingAutoMessageAttr;
import com.trading.service.ITradingMessageTemplate;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/10/14.
 */
@Controller
@RequestMapping("autoMessage")
public class AutoMessageController extends BaseAction {
    @Autowired
    private ITradingMessageTemplate iTradingMessageTemplate;
    @Autowired
    private SystemUserManagerService systemUserManagerService;
    @Autowired
    private ITradingAutoMessage iTradingAutoMessage;
    @Autowired
    private ITradingAutoMessageAttr iTradingAutoMessageAttr;

    @RequestMapping("/autoMessageList.do")
    public ModelAndView sendMessageList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        return forword("autoMessage/autoMessageList",modelMap);
    }
    /**获取发送消息list数据的ajax方法*/
    @RequestMapping("/ajax/loadAutoMessageList.do")
    @ResponseBody
    public void loadSendMessageList(CommonParmVO commonParmVO,HttpServletRequest request) throws Exception {
        Map m=new HashMap();
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        Page page=jsonBean.toPage();
        SessionVO sessionVO= SessionCacheSupport.getSessionVO();
        String status=request.getParameter("status");
        if(!StringUtils.isNotBlank(status)){
            status=null;
        }
        List<UsercontrollerUserExtend> orgUsers=systemUserManagerService.queryAllUsersByOrgID("yes");
        m.put("status",status);
        m.put("orgUsers",orgUsers);
        List<AutoMessageQuery> lists=iTradingAutoMessage.selectAutoMessageList(m,page);
        if(lists!=null&&lists.size()>0){
           for(AutoMessageQuery auto:lists){
               String country="";
               String amount="";
               List<TradingAutoMessageAttr> countrys=iTradingAutoMessageAttr.selectAutoMessageListByautoMessageId(auto.getId(),"country");

               List<TradingAutoMessageAttr> allEbay=iTradingAutoMessageAttr.selectAutoMessageListByautoMessageId(auto.getId(),"allEbay");
               for(TradingAutoMessageAttr attr:countrys){
                   country+=attr.getValue()+" ";
               }
               auto.setCountry(country);
               if(allEbay!=null&&allEbay.size()>0){
                   auto.setAmount("所有账户");
               }else{
                   List<TradingAutoMessageAttr> amounts=iTradingAutoMessageAttr.selectAutoMessageListByautoMessageId(auto.getId(),"amount");
                   for(TradingAutoMessageAttr attr:amounts){
                       amount+=attr.getValue()+" ";
                   }
               }
           }
        }
        jsonBean.setList(lists);
        jsonBean.setTotal((int)page.getTotalCount());
        AjaxSupport.sendSuccessText("", jsonBean);
    }
    /**获取发送消息list数据的ajax方法*/
    @RequestMapping("/ajax/shippingServiceOptionList.do")
    @ResponseBody
    public void shippingServiceOptionList(CommonParmVO commonParmVO,HttpServletRequest request) throws Exception {
        String siteId=request.getParameter("siteId");
        Map m=new HashMap();
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        Page page=jsonBean.toPage();
        m.put("siteId",siteId);
        List<AutoMessageQuery> lists=iTradingAutoMessage.selectShippingServiceOptionList(m, page);
        jsonBean.setList(lists);
        jsonBean.setTotal((int)page.getTotalCount());
        AjaxSupport.sendSuccessText("", jsonBean);
    }
    /**获取发送消息list数据的ajax方法*/
    @RequestMapping("/ajax/internationalShippingServiceList.do")
    @ResponseBody
    public void internationalShippingServiceList(CommonParmVO commonParmVO,HttpServletRequest request) throws Exception {
        Map m=new HashMap();
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        Page page=jsonBean.toPage();
        List<AutoMessageQuery> lists=iTradingAutoMessage.selectInternationalShippingServiceList(m, page);
        jsonBean.setList(lists);
        jsonBean.setTotal((int)page.getTotalCount());
        AjaxSupport.sendSuccessText("", jsonBean);
    }
    @RequestMapping("/addAutoMessage.do")
    public ModelAndView addAutoMessage(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        String id=request.getParameter("id");
        Map m=new HashMap();
        TradingAutoMessage autoMessage=new TradingAutoMessage();
        List<TradingAutoMessageAttr> items=null;
        List<TradingAutoMessageAttr> countrys=null;
        List<TradingAutoMessageAttr> amounts=null;
        List<TradingAutoMessageAttr> services=null;
        List<TradingAutoMessageAttr> internationalServices=null;
        TradingAutoMessageAttr order=null;
        TradingAutoMessageAttr allEbay=null;
        List<TradingAutoMessageAttr> exceptCountrys=null;
        if(StringUtils.isNotBlank(id)){
            List<TradingAutoMessage> messages=iTradingAutoMessage.selectAutoMessageById(Long.valueOf(id));
            autoMessage=messages.get(0);
            items=iTradingAutoMessageAttr.selectAutoMessageListByautoMessageId(autoMessage.getId(),"orderItem");
            countrys=iTradingAutoMessageAttr.selectAutoMessageListByautoMessageId(autoMessage.getId(),"country");
            amounts=iTradingAutoMessageAttr.selectAutoMessageListByautoMessageId(autoMessage.getId(),"amount");
            services=iTradingAutoMessageAttr.selectAutoMessageListByautoMessageId(autoMessage.getId(),"service");
            List<TradingAutoMessageAttr> orders=iTradingAutoMessageAttr.selectAutoMessageListByautoMessageId(autoMessage.getId(),"allOrder");
            List<TradingAutoMessageAttr> allEbays=iTradingAutoMessageAttr.selectAutoMessageListByautoMessageId(autoMessage.getId(),"allEbay");
            exceptCountrys=iTradingAutoMessageAttr.selectAutoMessageListByautoMessageId(autoMessage.getId(),"exceptCountry");
            internationalServices=iTradingAutoMessageAttr.selectAutoMessageListByautoMessageId(autoMessage.getId(),"internationalService");
            if(orders!=null&&orders.size()>0){
                order=orders.get(0);
            }
            if(allEbays!=null&&allEbays.size()>0){
                allEbay=allEbays.get(0);
            }
        }
        String orderItemIds="";
        String countryIds="";
        String amountIds="";
        String serviceIds="";
        String exceptCountryIds="";
        String internationalServiceIds="";
        if(items!=null&&items.size()>0){
            for(TradingAutoMessageAttr item:items){
                orderItemIds+=item.getId()+",";
            }
        }
        if(countrys!=null&&countrys.size()>0){
            for(TradingAutoMessageAttr country:countrys){
                countryIds+=country.getId()+",";
            }
        }
        if(amounts!=null&&amounts.size()>0){
            for(TradingAutoMessageAttr amount:amounts){
                amountIds+=amount.getId()+",";
            }
        }
        if(services!=null&&services.size()>0){
            for(TradingAutoMessageAttr service:services){
                serviceIds+=service.getId()+",";
            }
        }
        if(exceptCountrys!=null&&exceptCountrys.size()>0){
            for(TradingAutoMessageAttr exceptCountry:exceptCountrys){
                exceptCountryIds+=exceptCountry.getId()+",";
            }
        }
        if(internationalServices!=null&&internationalServices.size()>0){
            for(TradingAutoMessageAttr internationalService:internationalServices){
                internationalServiceIds+=internationalService.getId()+",";
            }
        }
        serviceIds=serviceIds+internationalServiceIds;
        modelMap.put("items",items);
        modelMap.put("countrys",countrys);
        modelMap.put("amounts",amounts);
        modelMap.put("services",services);
        modelMap.put("internationalServices",internationalServices);
        modelMap.put("order",order);
        modelMap.put("allEbay",allEbay);
        modelMap.put("exceptCountrys",exceptCountrys);
        modelMap.put("autoMessage",autoMessage);
        modelMap.put("orderItemIds",orderItemIds);
        modelMap.put("countryIds",countryIds);
        modelMap.put("amountIds",amountIds);
        modelMap.put("serviceIds",serviceIds);
        modelMap.put("exceptCountryIds",exceptCountryIds);
        return forword("autoMessage/addAutoMessage",modelMap);
    }
    /**添加备注初始化*/
    @RequestMapping("/addComment.do")
    public ModelAndView addComment(HttpServletRequest request,ModelMap modelMap){
        String id=request.getParameter("id");
        modelMap.put("id",id);
        return forword("autoMessage/addComment",modelMap);
    }

    /**添加备注*/
    @RequestMapping("/ajax/saveComment.do")
    @ResponseBody
    public void saveComment(CommonParmVO commonParmVO,HttpServletRequest request) throws Exception {
        String id=request.getParameter("id");
        String comment=request.getParameter("comment");
        if(!StringUtils.isNotBlank(id)){
            AjaxSupport.sendFailText("fail","该消息不存在,请核实");
            return;
        }
        List<TradingAutoMessage> messages=iTradingAutoMessage.selectAutoMessageById(Long.valueOf(id));
        if(messages.size()>0){
            TradingAutoMessage message=messages.get(0);
            message.setComment(comment);
            iTradingAutoMessage.saveAutoMessage(message);
            AjaxSupport.sendSuccessText("","保存成功");
        }else{
            AjaxSupport.sendFailText("fail","该消息不存在,请核实");
            return;
        }
    }
    /**选择有效国家初始化*/
    @RequestMapping("/selectCountrys.do")
    public ModelAndView selectCountrys(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        String id=request.getParameter("id");
        List<TradingAutoMessageAttr> attrs=new ArrayList<TradingAutoMessageAttr>();
        if(StringUtils.isNotBlank(id)){
            attrs=iTradingAutoMessageAttr.selectAutoMessageListByMessageId(Long.valueOf(id));
        }
        modelMap.put("items",attrs);
        return forword("autoMessage/selectCountrys",modelMap);
    }

    /**选择有效国家之外初始化*/
    @RequestMapping("/selectExceptCountrys.do")
    public ModelAndView selectExceptCountrys(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        String id=request.getParameter("id");
        List<TradingAutoMessageAttr> attrs=new ArrayList<TradingAutoMessageAttr>();
        if(StringUtils.isNotBlank(id)){
            attrs=iTradingAutoMessageAttr.selectAutoMessageListByMessageId(Long.valueOf(id));
        }
        modelMap.put("items",attrs);
        return forword("autoMessage/selectExceptCountrys",modelMap);
    }
    /**选择指定商品初始化*/
    @RequestMapping("/selectItems.do")
    public ModelAndView selectItems(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        String id=request.getParameter("id");
        List<TradingAutoMessageAttr> attrs=new ArrayList<TradingAutoMessageAttr>();
        if(StringUtils.isNotBlank(id)){
            attrs=iTradingAutoMessageAttr.selectAutoMessageListByMessageId(Long.valueOf(id));
        }
        modelMap.put("items",attrs);
        return forword("autoMessage/selectItems",modelMap);
    }

    /**选择指定账号初始化*/
    @RequestMapping("/selectAmounts.do")
    public ModelAndView selectAmounts(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        Map m=new HashMap();
        List<UsercontrollerEbayAccountExtend> ebays=systemUserManagerService.queryCurrAllEbay(m);
        modelMap.put("ebays",ebays);
        return forword("autoMessage/selectAmounts",modelMap);
    }

    /**选择指定账号初始化*/
    @RequestMapping("/selectShippingServices.do")
    public ModelAndView selectShippingServices(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        List<TradingDataDictionary> sites=DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.DATA_DICT_SITE);
        modelMap.put("sites",sites);
        return forword("autoMessage/selectShippingServices",modelMap);
    }
    /**ajax某地区的国家*/
    @RequestMapping("/ajax/selectCountry.do")
    @ResponseBody
    public void selectCountry(CommonParmVO commonParmVO,HttpServletRequest request) throws Exception {
       /* String countryId=request.getParameter("countryId");
        List<TradingDataDictionary> lidata = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.DATA_DICT_COUNTRY, Long.parseLong(countryId));*/
        List<TradingDataDictionary> lidata = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.DATA_DICT_DELTA);
        Map<String,List<TradingDataDictionary>> map=new HashMap<String, List<TradingDataDictionary>>();
        List<TradingDataDictionary> li1 = new ArrayList();
        List<String> names=new ArrayList<String>();
        List<String> countryIds=new ArrayList<String>();
        for(TradingDataDictionary tdd : lidata){
            if(tdd.getName1().equals("International")){
                li1.add(tdd);
                names.add(tdd.getName());
                countryIds.add(String.valueOf(tdd.getId()));
            }
            List<TradingDataDictionary> list=DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.DATA_DICT_COUNTRY,tdd.getId());
            map.put(tdd.getValue(),list);
        }
        map.put("place",lidata);
        AjaxSupport.sendSuccessText("", map);
    }

    /**ajax运输方式*/
    @RequestMapping("/selectShippingService.do")
    public ModelAndView selectShippingService(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        String siteId=request.getParameter("siteId");
        /*List<TradingDataDictionary> list1=DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.DATA_DICT_SHIPPINGINTER_TYPE);
        List<TradingDataDictionary> list=new ArrayList<TradingDataDictionary>();
        if(StringUtils.isNotBlank(siteId)){
            list=DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.DATA_DICT_SHIPPING_TYPE,Long.valueOf(siteId));
        }*/
        modelMap.put("siteId",siteId);
        return forword("autoMessage/selectShippingService",modelMap);
    }
    /**保存指定国家*/
    @RequestMapping("/ajax/saveAmount.do")
    @ResponseBody
    public void saveAmount(CommonParmVO commonParmVO,HttpServletRequest request) throws Exception {
        String autoMessageId=request.getParameter("autoMessageId");
        String flag=request.getParameter("flag");
        String amountIds=request.getParameter("amountIds");
        String ebayName=request.getParameter("ebayName");
        if("true".equals(flag)){
            List<TradingAutoMessageAttr> orderItems=iTradingAutoMessageAttr.selectAutoMessageListByDictionaryIdIsNull("amount");
            for(TradingAutoMessageAttr orderItem:orderItems){
                iTradingAutoMessageAttr.deleteAutoMessageAttr(orderItem);
            }
        }
        if(StringUtils.isNotBlank(autoMessageId)){
            List<TradingAutoMessageAttr> coutrys=iTradingAutoMessageAttr.selectAutoMessageListByautoMessageId(Long.valueOf(autoMessageId),"amount");
            for(TradingAutoMessageAttr country:coutrys){
                iTradingAutoMessageAttr.deleteAutoMessageAttr(country);
            }
        }
        if(StringUtils.isNotBlank(amountIds)){
            List<Long> list=new ArrayList<Long>();
            String[] amountIds1=amountIds.split(",");
            String[] ebayNames=ebayName.split(",");
            for(int i=0;i<amountIds1.length;i++){
                TradingAutoMessageAttr attr=new TradingAutoMessageAttr();
                attr.setType("amount");
                if(StringUtils.isNotBlank(autoMessageId)){
                    attr.setAutomessageId(Long.valueOf(autoMessageId));
                }
                attr.setDictionaryId(Long.valueOf(amountIds1[i]));
                attr.setValue(ebayNames[i]);
                iTradingAutoMessageAttr.saveAutoMessageAttr(attr);
                list.add(attr.getId());
            }
            AjaxSupport.sendSuccessText("", list);
        }else{
            AjaxSupport.sendFailText("fail","内容为空");
        }
    }

    /**保存有效运输方式*/
    @RequestMapping("/ajax/saveService.do")
    @ResponseBody
    public void saveService(CommonParmVO commonParmVO,HttpServletRequest request) throws Exception {
        String service=request.getParameter("service");
        String flag=request.getParameter("flag");
        String service1=request.getParameter("service1");
        String autoMessageId=request.getParameter("autoMessageId");
        String name=request.getParameter("name");
        String name1=request.getParameter("name1");
        if("true".equals(flag)){
            List<TradingAutoMessageAttr> orderItems=iTradingAutoMessageAttr.selectAutoMessageListByDictionaryIdIsNull("service");
            for(TradingAutoMessageAttr orderItem:orderItems){
                iTradingAutoMessageAttr.deleteAutoMessageAttr(orderItem);
            }
            List<TradingAutoMessageAttr> orderItems1=iTradingAutoMessageAttr.selectAutoMessageListByDictionaryIdIsNull("internationalService");
            for(TradingAutoMessageAttr orderItem:orderItems1){
                iTradingAutoMessageAttr.deleteAutoMessageAttr(orderItem);
            }
        }
        if(StringUtils.isNotBlank(autoMessageId)){
            List<TradingAutoMessageAttr> coutrys=iTradingAutoMessageAttr.selectAutoMessageListByautoMessageId(Long.valueOf(autoMessageId),"service");
            for(TradingAutoMessageAttr country:coutrys){
                iTradingAutoMessageAttr.deleteAutoMessageAttr(country);
            }
            List<TradingAutoMessageAttr> coutrys1=iTradingAutoMessageAttr.selectAutoMessageListByautoMessageId(Long.valueOf(autoMessageId),"internationalService");
            for(TradingAutoMessageAttr country:coutrys1){
                iTradingAutoMessageAttr.deleteAutoMessageAttr(country);
            }
        }
        if(StringUtils.isNotBlank(service)||StringUtils.isNotBlank(service1)){
            List<Long> list=new ArrayList<Long>();

            if(StringUtils.isNotBlank(service)){
                String[] amountIds1=service.split(",");
                String[] names=name.split(",");
                for(int i=0;i<amountIds1.length;i++){
                    if(StringUtils.isNotBlank(amountIds1[i])){
                        TradingAutoMessageAttr attr=new TradingAutoMessageAttr();
                        attr.setType("service");
                        if(StringUtils.isNotBlank(autoMessageId)){
                            attr.setAutomessageId(Long.valueOf(autoMessageId));
                        }
                        attr.setDictionaryId(Long.valueOf(amountIds1[i]));
                        attr.setValue(names[i]);
                        iTradingAutoMessageAttr.saveAutoMessageAttr(attr);
                        list.add(attr.getId());
                    }

                }
            }
            if(StringUtils.isNotBlank(service1)){
                String[] amountIds1=service1.split(",");
                String[] name1s=name1.split(",");
                for(int i=0;i<amountIds1.length;i++){
                    if(StringUtils.isNotBlank(amountIds1[i])){
                        TradingAutoMessageAttr attr=new TradingAutoMessageAttr();
                        attr.setType("internationalService");
                        if(StringUtils.isNotBlank(autoMessageId)){
                            attr.setAutomessageId(Long.valueOf(autoMessageId));
                        }
                        attr.setDictionaryId(Long.valueOf(amountIds1[i]));
                        attr.setValue(name1s[i]);
                        iTradingAutoMessageAttr.saveAutoMessageAttr(attr);
                        list.add(attr.getId());
                    }
                }
            }
            AjaxSupport.sendSuccessText("", list);
        }else{
            AjaxSupport.sendFailText("fail","内容为空");
        }
    }
    /**保存有效国家*/
    @RequestMapping("/ajax/saveCountry.do")
    @ResponseBody
    public void saveCountry(CommonParmVO commonParmVO,HttpServletRequest request) throws Exception {
        String aountryText=request.getParameter("aountryText");
        String countryIds=request.getParameter("countryIds");
        String autoMessageId=request.getParameter("autoMessageId");
        String flag=request.getParameter("flag");
        if("true".equals(flag)){
            List<TradingAutoMessageAttr> coutrys=iTradingAutoMessageAttr.selectAutoMessageListByDictionaryIdIsNull("country");
            for(TradingAutoMessageAttr country:coutrys){
                iTradingAutoMessageAttr.deleteAutoMessageAttr(country);
            }
        }
        if(StringUtils.isNotBlank(autoMessageId)){
            List<TradingAutoMessageAttr> coutrys=iTradingAutoMessageAttr.selectAutoMessageListByautoMessageId(Long.valueOf(autoMessageId),"country");
            for(TradingAutoMessageAttr country:coutrys){
                iTradingAutoMessageAttr.deleteAutoMessageAttr(country);
            }
        }
        if(StringUtils.isNotBlank(aountryText)){
            List<Long> list=new ArrayList<Long>();
            String[] countryNames=aountryText.split(",");
            String[] countryId=countryIds.split(",");
            for(int i=0;i<countryNames.length;i++){
                TradingAutoMessageAttr attr=new TradingAutoMessageAttr();
                attr.setType("country");
                if(StringUtils.isNotBlank(autoMessageId)){
                    attr.setAutomessageId(Long.valueOf(autoMessageId));
                }
                attr.setDictionaryId(Long.valueOf(countryId[i]));
                attr.setValue(countryNames[i]);
                iTradingAutoMessageAttr.saveAutoMessageAttr(attr);
                list.add(attr.getId());
            }
            AjaxSupport.sendSuccessText("", list);
        }else{
            AjaxSupport.sendFailText("fail","内容为空");
        }
    }

    /**保存有效国家之外*/
    @RequestMapping("/ajax/saveExceptCountry.do")
    @ResponseBody
    public void saveExceptCountry(CommonParmVO commonParmVO,HttpServletRequest request) throws Exception {
        String aountryText=request.getParameter("aountryText");
        String countryIds=request.getParameter("countryIds");
        String autoMessageId=request.getParameter("autoMessageId");
        String flag=request.getParameter("flag");
        if("true".equals(flag)){
            List<TradingAutoMessageAttr> coutrys=iTradingAutoMessageAttr.selectAutoMessageListByDictionaryIdIsNull("exceptCountry");
            for(TradingAutoMessageAttr country:coutrys){
                iTradingAutoMessageAttr.deleteAutoMessageAttr(country);
            }
        }
        if(StringUtils.isNotBlank(autoMessageId)){
            List<TradingAutoMessageAttr> coutrys=iTradingAutoMessageAttr.selectAutoMessageListByautoMessageId(Long.valueOf(autoMessageId),"exceptCountry");
            for(TradingAutoMessageAttr country:coutrys){
                iTradingAutoMessageAttr.deleteAutoMessageAttr(country);
            }
        }
        if(StringUtils.isNotBlank(aountryText)){
            List<Long> list=new ArrayList<Long>();
            String[] countryNames=aountryText.split(",");
            String[] countryId=countryIds.split(",");
            for(int i=0;i<countryNames.length;i++){
                TradingAutoMessageAttr attr=new TradingAutoMessageAttr();
                attr.setType("exceptCountry");
                if(StringUtils.isNotBlank(autoMessageId)){
                    attr.setAutomessageId(Long.valueOf(autoMessageId));
                }
                attr.setDictionaryId(Long.valueOf(countryId[i]));
                attr.setValue(countryNames[i]);
                iTradingAutoMessageAttr.saveAutoMessageAttr(attr);
                list.add(attr.getId());
            }
            AjaxSupport.sendSuccessText("", list);
        }else{
            AjaxSupport.sendFailText("fail","内容为空");
        }
    }
    /**保存指定商品*/
    @RequestMapping("/ajax/saveOrderItem.do")
    @ResponseBody
    public void saveOrderItem(CommonParmVO commonParmVO,HttpServletRequest request) throws Exception {
        String autoMessageId=request.getParameter("autoMessageId");
        String flag=request.getParameter("flag");
        String itemIds=request.getParameter("itemIds");
        String sku=request.getParameter("sku");
        if("true".equals(flag)){
            List<TradingAutoMessageAttr> orderItems=iTradingAutoMessageAttr.selectAutoMessageListByDictionaryIdIsNull("orderItem");
            for(TradingAutoMessageAttr orderItem:orderItems){
                iTradingAutoMessageAttr.deleteAutoMessageAttr(orderItem);
            }
        }
        if(StringUtils.isNotBlank(autoMessageId)){
            List<TradingAutoMessageAttr> coutrys=iTradingAutoMessageAttr.selectAutoMessageListByautoMessageId(Long.valueOf(autoMessageId),"orderItem");
            for(TradingAutoMessageAttr country:coutrys){
                iTradingAutoMessageAttr.deleteAutoMessageAttr(country);
            }
        }
        if(StringUtils.isNotBlank(itemIds)){
            List<Long> list=new ArrayList<Long>();
            String[] itemIds1=itemIds.split(",");
            String[] skus=sku.split(",");
            for(int i=0;i<itemIds1.length;i++){
                if(StringUtils.isNotBlank(itemIds1[i])){
                    TradingAutoMessageAttr attr=new TradingAutoMessageAttr();
                    attr.setType("orderItem");
                    if(StringUtils.isNotBlank(autoMessageId)){
                        attr.setAutomessageId(Long.valueOf(autoMessageId));
                    }
                    attr.setDictionaryId(Long.valueOf(itemIds1[i]));
                    attr.setValue(skus[i]);
                    iTradingAutoMessageAttr.saveAutoMessageAttr(attr);
                    list.add(attr.getId());
                }
            }
            AjaxSupport.sendSuccessText("", list);
        }else{
            AjaxSupport.sendFailText("fail","内容为空");
        }
    }
    //保存AutoMessage
    @RequestMapping("/ajax/saveAutoMessage.do")
    @ResponseBody
    public void saveAutoMessage(CommonParmVO commonParmVO,HttpServletRequest request) throws Exception {
        String id=request.getParameter("id");
        String countryIds=request.getParameter("countryIds");
        String messageId=request.getParameter("messageId");
        String subject=request.getParameter("subject");
        String type=request.getParameter("type");
        String hour=request.getParameter("hour");
        String day=request.getParameter("day");
        String ebay=request.getParameter("ebay");
        String email=request.getParameter("email");
        String orderItems=request.getParameter("orderItems");
        String amounts=request.getParameter("amounts");
        String service=request.getParameter("service");
        String exceptCountryIds=request.getParameter("exceptCountryIds");
        String allOrder=request.getParameter("allOrder");
        String allEbay=request.getParameter("allEbay");
        String starUse=request.getParameter("starUse");
        TradingAutoMessage message=new TradingAutoMessage();
        if(StringUtils.isNotBlank(id)){
            message.setId(Long.valueOf(id));
        }
        if(StringUtils.isNotBlank(starUse)){
            message.setStartuse(Integer.valueOf(starUse));
        }
        if(StringUtils.isNotBlank(messageId)){
            message.setMessagetemplateId(Long.valueOf(messageId));
        }
        message.setSubject(subject);
        if("null".equals(type)){
            message.setType(null);
        }else{
            message.setType(type);
        }
        if(StringUtils.isNotBlank(ebay)){
            message.setEbayemail(Integer.valueOf(ebay));
        }else{
            /*message.setEbayemail(0);*/
            //只有ebay message的时候
            message.setEbayemail(1);
        }
        if(StringUtils.isNotBlank(email)){
            message.setEmail(Integer.valueOf(email));
        }else{
            message.setEmail(0);
        }
        if(StringUtils.isNotBlank(day)){
            message.setDay(Integer.valueOf(day));
        }
        if(StringUtils.isNotBlank(hour)){
            message.setHour(Integer.valueOf(hour));
        }

        iTradingAutoMessage.saveAutoMessage(message);
        if(StringUtils.isNotBlank(countryIds)){
            String[] coutryids=countryIds.split(",");
            for(int i=0;i<coutryids.length;i++){
                List<TradingAutoMessageAttr> coutrys=iTradingAutoMessageAttr.selectAutoMessageListById(Long.valueOf(coutryids[i]));
                if(coutrys!=null&&coutrys.size()>0){
                    TradingAutoMessageAttr country=coutrys.get(0);
                    country.setAutomessageId(message.getId());
                    iTradingAutoMessageAttr.saveAutoMessageAttr(country);
                }
            }
        }else{
            List<TradingAutoMessageAttr> coutrys=iTradingAutoMessageAttr.selectAutoMessageListByautoMessageId(message.getId(),"country");
            if(coutrys.size()>0){
                for(TradingAutoMessageAttr coutry:coutrys){
                    iTradingAutoMessageAttr.deleteAutoMessageAttr(coutry);
                }
            }
        }
        if(StringUtils.isNotBlank(exceptCountryIds)){
            String[] coutryids=exceptCountryIds.split(",");
            for(int i=0;i<coutryids.length;i++){
                List<TradingAutoMessageAttr> coutrys=iTradingAutoMessageAttr.selectAutoMessageListById(Long.valueOf(coutryids[i]));
                if(coutrys!=null&&coutrys.size()>0){
                    TradingAutoMessageAttr country=coutrys.get(0);
                    country.setAutomessageId(message.getId());
                    iTradingAutoMessageAttr.saveAutoMessageAttr(country);
                }
            }
        }else{
            List<TradingAutoMessageAttr> coutrys=iTradingAutoMessageAttr.selectAutoMessageListByautoMessageId(message.getId(),"exceptCountry");
            if(coutrys.size()>0){
                for(TradingAutoMessageAttr coutry:coutrys){
                    iTradingAutoMessageAttr.deleteAutoMessageAttr(coutry);
                }
            }
        }
        if(StringUtils.isNotBlank(orderItems)){
            String[] orderItems1=orderItems.split(",");
            for(int i=0;i<orderItems1.length;i++){
                if(StringUtils.isNotBlank(orderItems)){
                    List<TradingAutoMessageAttr> orderItem=iTradingAutoMessageAttr.selectAutoMessageListById(Long.valueOf(orderItems1[i]));
                    if(orderItem!=null&&orderItem.size()>0){
                        TradingAutoMessageAttr country=orderItem.get(0);
                        country.setAutomessageId(message.getId());
                        iTradingAutoMessageAttr.saveAutoMessageAttr(country);
                    }
                }
            }
        }else{
            List<TradingAutoMessageAttr> coutrys=iTradingAutoMessageAttr.selectAutoMessageListByautoMessageId(message.getId(),"orderItem");
            if(coutrys.size()>0){
                for(TradingAutoMessageAttr coutry:coutrys){
                    iTradingAutoMessageAttr.deleteAutoMessageAttr(coutry);
                }
            }
        }
        if(StringUtils.isNotBlank(amounts)){
            String[] amounts1=amounts.split(",");
            for(int i=0;i<amounts1.length;i++){
                List<TradingAutoMessageAttr> orderItem=iTradingAutoMessageAttr.selectAutoMessageListById(Long.valueOf(amounts1[i]));
                if(orderItem!=null&&orderItem.size()>0){
                    TradingAutoMessageAttr country=orderItem.get(0);
                    country.setAutomessageId(message.getId());
                    iTradingAutoMessageAttr.saveAutoMessageAttr(country);
                }
            }
        }else{
            List<TradingAutoMessageAttr> coutrys=iTradingAutoMessageAttr.selectAutoMessageListByautoMessageId(message.getId(),"amount");
            if(coutrys.size()>0){
                for(TradingAutoMessageAttr coutry:coutrys){
                    iTradingAutoMessageAttr.deleteAutoMessageAttr(coutry);
                }
            }
        }
        if(StringUtils.isNotBlank(service)){
            String[] amounts1=service.split(",");
            for(int i=0;i<amounts1.length;i++){
                List<TradingAutoMessageAttr> orderItem=iTradingAutoMessageAttr.selectAutoMessageListById(Long.valueOf(amounts1[i]));
                if(orderItem!=null&&orderItem.size()>0){
                    TradingAutoMessageAttr country=orderItem.get(0);
                    country.setAutomessageId(message.getId());
                    iTradingAutoMessageAttr.saveAutoMessageAttr(country);
                }
            }
        }else{
            List<TradingAutoMessageAttr> coutrys=iTradingAutoMessageAttr.selectAutoMessageListByautoMessageId(message.getId(),"service");
            if(coutrys.size()>0){
                for(TradingAutoMessageAttr coutry:coutrys){
                    iTradingAutoMessageAttr.deleteAutoMessageAttr(coutry);
                }
            }
            List<TradingAutoMessageAttr> coutrys1=iTradingAutoMessageAttr.selectAutoMessageListByautoMessageId(message.getId(),"internationalService");
            if(coutrys.size()>0){
                for(TradingAutoMessageAttr coutry:coutrys1){
                    iTradingAutoMessageAttr.deleteAutoMessageAttr(coutry);
                }
            }
        }
        List<TradingAutoMessageAttr> orders=iTradingAutoMessageAttr.selectAutoMessageListByautoMessageId(message.getId(),"allOrder");
        for(TradingAutoMessageAttr order:orders){
            iTradingAutoMessageAttr.deleteAutoMessageAttr(order);
        }
        if("true".equals(allOrder)){
            TradingAutoMessageAttr m=new TradingAutoMessageAttr();
            m.setAutomessageId(message.getId());
            m.setType("allOrder");
            m.setValue("所有订单");
            iTradingAutoMessageAttr.saveAutoMessageAttr(m);
        }
        List<TradingAutoMessageAttr> allEbays=iTradingAutoMessageAttr.selectAutoMessageListByautoMessageId(message.getId(),"allEbay");
        for(TradingAutoMessageAttr order:orders){
            iTradingAutoMessageAttr.deleteAutoMessageAttr(order);
        }
        if("true".equals(allEbay)){
            TradingAutoMessageAttr m=new TradingAutoMessageAttr();
            m.setAutomessageId(message.getId());
            m.setType("allEbay");
            m.setValue("所有账户");
            iTradingAutoMessageAttr.saveAutoMessageAttr(m);
        }
        AjaxSupport.sendSuccessText("", "保存成功!");
    }
    //启用或者禁用自动消息
    @RequestMapping("/ajax/startUsing.do")
    @ResponseBody
    public void startUsing(CommonParmVO commonParmVO,HttpServletRequest request) throws Exception {
        String id=request.getParameter("id");
        String status=request.getParameter("status");
        if(StringUtils.isNotBlank(id)){
            TradingAutoMessage message=new TradingAutoMessage();
            message.setId(Long.valueOf(id));
            if("1".equals(status)){
                message.setStartuse(Integer.valueOf(status));
            }
            if("0".equals(status)){
                message.setStartuse(Integer.valueOf(status));
            }
            iTradingAutoMessage.saveAutoMessage(message);
        }else{
            AjaxSupport.sendFailText("fail","自动消息不存在");
            return;
        }
        if("1".equals(status)){
            AjaxSupport.sendSuccessText("", "启用成功");
        }else{
            AjaxSupport.sendSuccessText("", "禁用成功");
        }


    }
    //删除autoMessage
    @RequestMapping("/ajax/deleteAutoMessage.do")
    @ResponseBody
    public void deleteAutoMessage(CommonParmVO commonParmVO,HttpServletRequest request) throws Exception {
        String id=request.getParameter("id");
        if(StringUtils.isNotBlank(id)){
            TradingAutoMessage message=new TradingAutoMessage();
            message.setId(Long.valueOf(id));
            List<TradingAutoMessageAttr> attrs=iTradingAutoMessageAttr.selectAutoMessageListByMessageId(message.getId());
            for(TradingAutoMessageAttr attr:attrs){
                iTradingAutoMessageAttr.deleteAutoMessageAttr(attr);
            }
            iTradingAutoMessage.deleteAutoMessage(message);
        }else{
            AjaxSupport.sendFailText("fail","自动消息不存在");
            return;
        }
        AjaxSupport.sendSuccessText("", "删除成功");
    }

    //批量删除autoMessage
    @RequestMapping("/ajax/deleteAutoMessages.do")
    @AvoidDuplicateSubmission(needRemoveToken = true)
    @ResponseBody
    public void deleteAutoMessages(HttpServletRequest request) throws Exception {
        String id1=request.getParameter("id");
        String[] ids=id1.split(",");
        for(int i=0;i<ids.length;i++){
            TradingAutoMessage message=new TradingAutoMessage();
            Long id= Long.valueOf(ids[i]);
            message.setId(id);
            List<TradingAutoMessageAttr> attrs=iTradingAutoMessageAttr.selectAutoMessageListByMessageId(message.getId());
            for(TradingAutoMessageAttr attr:attrs){
                iTradingAutoMessageAttr.deleteAutoMessageAttr(attr);
            }
            iTradingAutoMessage.deleteAutoMessage(message);
        }
        AjaxSupport.sendSuccessText("", "删除成功");
    }
    @RequestMapping("/selectMessageTemplate.do")
    public ModelAndView selectMessageTemplate(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        return forword("autoMessage/selectMessageTemplate",modelMap);
    }
}
