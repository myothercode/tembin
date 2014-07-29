package com.module.controller;

import com.base.database.trading.model.TradingDataDictionary;
import com.base.database.trading.model.TradingPaypal;
import com.base.domains.querypojos.BuyerRequirementDetailsQuery;
import com.base.domains.querypojos.PaypalQuery;
import com.base.utils.cache.DataDictionarySupport;
import com.base.utils.common.ObjectUtils;
import com.common.base.web.BaseAction;
import com.trading.service.ITradingDataDictionary;
import com.trading.service.ITradingPayPal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cz on 2014/7/28.
 */
@Controller
public class PayPalController extends BaseAction{


    @Autowired
    private ITradingDataDictionary iTradingDataDictionary;

    @Autowired
    private ITradingPayPal iTradingPayPal;

    @RequestMapping("/PayPalList.do")
    public ModelAndView payPalList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        Map m = new HashMap();
        List<PaypalQuery> paypalli = this.iTradingPayPal.selectByPayPalList(m);
        modelMap.put("paypalli",paypalli);
        return forword("module/paypal/PayPalList",modelMap);
    }

    @RequestMapping("/addPayPal.do")
    public ModelAndView addPayPal(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        List<TradingDataDictionary> lidata = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.DATA_DICT_SITE);

        modelMap.put("siteList",lidata);

        List<TradingDataDictionary> paypalList = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.DATA_DICT_PAYPAL);
        modelMap.put("paypalList",paypalList);

        return forword("module/paypal/addPayPal",modelMap);
    }

    @RequestMapping("/editPayPal.do")
    public ModelAndView editPayPal(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        List<TradingDataDictionary> lidata = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.DATA_DICT_SITE);
        modelMap.put("siteList",lidata);
        List<TradingDataDictionary> paypalList = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.DATA_DICT_PAYPAL);
        modelMap.put("paypalList",paypalList);
        Map m = new HashMap();
        m.put("id",request.getParameter("id"));
        List<PaypalQuery> paypalli = this.iTradingPayPal.selectByPayPalList(m);
        modelMap.put("paypal",paypalli.get(0));
        return forword("module/paypal/addPayPal",modelMap);
    }

    @RequestMapping("/savePayPal.do")
    public ModelAndView savePayPal(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws Exception {
        String name = request.getParameter("name");
        String site = request.getParameter("site");
        String paypal = request.getParameter("paypal");
        String paypalDesc = request.getParameter("paypal_desc");
        String id = request.getParameter("id");

        TradingPaypal tp = new TradingPaypal();
        if(!ObjectUtils.isLogicalNull(id)){
            tp.setId(Long.parseLong(id));
        }
        tp.setPayName(name);
        tp.setPaypal(paypal);
        tp.setSite(site);
        tp.setPaymentinstructions(paypalDesc);
        ObjectUtils.toPojo(tp);
        this.iTradingPayPal.savePaypal(tp);
        return forword("module/paypal/addPayPal",modelMap);
    }
}
