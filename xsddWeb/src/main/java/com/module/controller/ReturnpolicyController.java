package com.module.controller;

import com.base.domains.querypojos.ReturnpolicyQuery;
import com.common.base.web.BaseAction;
import com.trading.service.ITradingReturnpolicy;
import com.base.database.trading.model.TradingDataDictionary;
import com.base.utils.cache.DataDictionarySupport;
import com.base.database.trading.model.TradingReturnpolicy;
import com.base.utils.common.ObjectUtils;
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
 * Created by lq on 2014/7/29.
 */
@Controller
public class ReturnpolicyController extends BaseAction{

    @Autowired
    private ITradingReturnpolicy iTradingReturnpolicy;

    @RequestMapping("/ReturnpolicyList.do")
    public ModelAndView ReturnpolicyList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        Map m = new HashMap();
        List<ReturnpolicyQuery> Returnpolicyli = this.iTradingReturnpolicy.selectByReturnpolicyList(m);
        modelMap.put("Returnpolicyli",Returnpolicyli);
        return forword("module/returnpolicy/ReturnpolicyList",modelMap);
    }

    @RequestMapping("/addReturnpolicy.do")
    public ModelAndView addReturnpolicy(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        List<TradingDataDictionary> lidata = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.DATA_DICT_SITE);
        modelMap.put("siteList",lidata);

        List<TradingDataDictionary> acceptList = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.RETURNS_ACCEPTED_OPTION);
        modelMap.put("acceptList",acceptList);

        List<TradingDataDictionary> withinList = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.RETURNS_WITHIN_OPTION);
        modelMap.put("withinList",withinList);

        List<TradingDataDictionary> refundList = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.REFUND_OPTION);
        modelMap.put("refundList",refundList);

        List<TradingDataDictionary> costPaidList = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.SHIPPING_COST_PAID);
        modelMap.put("costPaidList",costPaidList);

        return forword("module/returnpolicy/addReturnpolicy",modelMap);
    }

    @RequestMapping("/editReturnpolicy.do")
    public ModelAndView editReturnpolicy(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        List<TradingDataDictionary> lidata = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.DATA_DICT_SITE);
        modelMap.put("siteList",lidata);

        List<TradingDataDictionary> acceptList = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.RETURNS_ACCEPTED_OPTION);
        modelMap.put("acceptList",acceptList);

        List<TradingDataDictionary> withinList = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.RETURNS_WITHIN_OPTION);
        modelMap.put("withinList",withinList);

        List<TradingDataDictionary> refundList = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.REFUND_OPTION);
        modelMap.put("refundList",refundList);

        List<TradingDataDictionary> costPaidList = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.SHIPPING_COST_PAID);
        modelMap.put("costPaidList",costPaidList);
        Map m = new HashMap();
        m.put("id",request.getParameter("id"));
        List<ReturnpolicyQuery> Returnpolicyli = this.iTradingReturnpolicy.selectByReturnpolicyList(m);
        modelMap.put("Returnpolicy",Returnpolicyli.get(0));
        return forword("module/returnpolicy/addReturnpolicy",modelMap);
    }

    @RequestMapping("/saveReturnpolicy.do")
    public ModelAndView saveReturnpolicy(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws Exception {
        String name = request.getParameter("name");
        String site = request.getParameter("site");
        String ReturnsAcceptedOption = request.getParameter("ReturnsAcceptedOption");
        String ReturnsWithinOption = request.getParameter("ReturnsWithinOption");
        String RefundOption=request.getParameter("RefundOption");
        String ShippingCostPaidByOption=request.getParameter("ShippingCostPaidByOption");
        String Returnpolicy_desc=request.getParameter("Returnpolicy_desc");
        String id = request.getParameter("id");

        TradingReturnpolicy tp = new TradingReturnpolicy();
        if(!ObjectUtils.isLogicalNull(id)){
            tp.setId(Long.parseLong(id));
        }
        tp.setName(name);
        tp.setSite(site);
        tp.setReturnsacceptedoption(ReturnsAcceptedOption);
        tp.setReturnswithinoption(ReturnsWithinOption);
        tp.setRefundoption(RefundOption);
        tp.setShippingcostpaidbyoption(ShippingCostPaidByOption);
        tp.setDescription(Returnpolicy_desc);
        ObjectUtils.toPojo(tp);
        this.iTradingReturnpolicy.saveTradingReturnpolicy(tp);
        return forword("module/returnpolicy/addReturnpolicy",modelMap);
    }
}
