package com.module.controller;

import com.base.database.publicd.model.PublicUserConfig;
import com.base.database.trading.model.TradingDataDictionary;
import com.base.database.trading.model.TradingShippingdetails;
import com.base.domains.SessionVO;
import com.base.domains.querypojos.BuyerRequirementDetailsQuery;
import com.base.utils.cache.DataDictionarySupport;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.common.ObjectUtils;
import com.base.xmlpojo.trading.addproduct.CalculatedShippingRate;
import com.base.xmlpojo.trading.addproduct.ShippingDetails;
import com.base.xmlpojo.trading.addproduct.ShippingServiceOptions;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
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
 * Created by Administrtor on 2014/7/30.
 */
@Controller
public class ShippingDetailsController extends BaseAction{

    /**
     * 查询列表跳转
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping("/shippingDetailsList.do")
    public ModelAndView shippingDetailsList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        /*Map m = new HashMap();
        List<BuyerRequirementDetailsQuery> li = this.iTradingBuyerRequirementDetails.selectTradingBuyerRequirementDetailsByList(m);
        modelMap.put("li",li);*/
        return forword("module/shipping/shippingDetailsList",modelMap);
    }

    /**
     * 新增页面跳转
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping("/addshippingDetails.do")
    public ModelAndView addshippingDetails(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        /*Map m = new HashMap();
        List<BuyerRequirementDetailsQuery> li = this.iTradingBuyerRequirementDetails.selectTradingBuyerRequirementDetailsByList(m);
        modelMap.put("li",li);*/
        List<TradingDataDictionary> lidata = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.DATA_DICT_SITE);
        modelMap.put("siteList",lidata);

        List<TradingDataDictionary> litype = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.DATA_DICT_SHIPPING_TYPE);
        List<TradingDataDictionary> li1 = new ArrayList<TradingDataDictionary>();
        List<TradingDataDictionary> li2 = new ArrayList<TradingDataDictionary>();
        List<TradingDataDictionary> li3 = new ArrayList<TradingDataDictionary>();
        List<TradingDataDictionary> li4 = new ArrayList<TradingDataDictionary>();
        List<TradingDataDictionary> li5 = new ArrayList<TradingDataDictionary>();
        for(TradingDataDictionary tdd:litype){
            if(tdd.getName1().equals("Economy services")){
                li1.add(tdd);
            }else if(tdd.getName1().equals("Expedited services")){
                li2.add(tdd);
            }else if(tdd.getName1().equals("One-day services")){
                li3.add(tdd);
            }else if(tdd.getName1().equals("Other services")){
                li4.add(tdd);
            }else if(tdd.getName1().equals("Standard services")){
                li5.add(tdd);
            }
        }
        modelMap.put("li1",li1);
        modelMap.put("li2",li2);
        modelMap.put("li3",li3);
        modelMap.put("li4",li4);
        modelMap.put("li5",li5);

        List<TradingDataDictionary> lipackage = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.DATA_DICT_SHIPPINGPACKAGE);

        modelMap.put("lipackage",lipackage);

        SessionVO c= SessionCacheSupport.getSessionVO();
        List<PublicUserConfig> ebayList = DataDictionarySupport.getPublicUserConfigByType(DataDictionarySupport.PUBLIC_DATA_DICT_EBAYACCOUNT, c.getId());
        modelMap.put("ebayList",ebayList);

        return forword("module/shipping/addshippingDetails",modelMap);
    }

    /**
     * 跳转到洲或地区的列表界面
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping("/locationList.do")
    public ModelAndView locationList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        List<TradingDataDictionary> lidata = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.DATA_DICT_DELTA);
        List<TradingDataDictionary> li1 = new ArrayList();
        List<TradingDataDictionary> li2 = new ArrayList();
        List<TradingDataDictionary> li3 = new ArrayList();
        for(TradingDataDictionary tdd : lidata){
            if(tdd.getName1().equals("Additional Locations")){
                li3.add(tdd);
            }else if(tdd.getName1().equals("Domestic")){
                li1.add(tdd);
            }else if(tdd.getName1().equals("International")){
                li2.add(tdd);
            }
        }
        modelMap.put("li1",li1);
        modelMap.put("li2",li2);
        modelMap.put("li3",li3);
        return forword("module/shipping/locationList",modelMap);
    }

    /**
     * 跳转到国家列表页面
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping("/countryList.do")
    public ModelAndView countryList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        String parentid = request.getParameter("parentid");
        List<TradingDataDictionary> lidata = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.DATA_DICT_COUNTRY,Long.parseLong(parentid));
        modelMap.put("lidata",lidata);
        return forword("module/shipping/countryList",modelMap);
    }

    /**
     * 保存运输选项数据
     * @param request
     * @throws Exception
     */
    @RequestMapping("/saveShippingDetails.do")
    @ResponseBody
    public void saveShippingDetails(HttpServletRequest request,ShippingDetails shippingDetails) throws Exception {

        //ObjectUtils.toPojo(tradingShippingdetails);

        AjaxSupport.sendSuccessText("啊", "dfd");
    }



}