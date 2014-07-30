package com.module.controller;

import com.base.database.publicd.model.PublicUserConfig;
import com.base.database.trading.model.TradingDataDictionary;
import com.base.domains.SessionVO;
import com.base.domains.querypojos.BuyerRequirementDetailsQuery;
import com.base.utils.cache.DataDictionarySupport;
import com.base.utils.cache.SessionCacheSupport;
import com.common.base.web.BaseAction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
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

        SessionVO c= SessionCacheSupport.getSessionVO();
        List<PublicUserConfig> ebayList = DataDictionarySupport.getPublicUserConfigByType(DataDictionarySupport.PUBLIC_DATA_DICT_EBAYACCOUNT, c.getId());
        modelMap.put("ebayList",ebayList);

        return forword("module/shipping/addshippingDetails",modelMap);
    }

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

    @RequestMapping("/countryList.do")
    public ModelAndView countryList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        String parentid = request.getParameter("parentid");
        List<TradingDataDictionary> lidata = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.DATA_DICT_COUNTRY,Long.parseLong(parentid));
        modelMap.put("lidata",lidata);
        return forword("module/shipping/countryList",modelMap);
    }


}
