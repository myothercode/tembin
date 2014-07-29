package com.module.controller;

import com.base.domains.querypojos.PaypalQuery;
import com.common.base.web.BaseAction;
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
 * Created by Administrtor on 2014/7/29.
 */
@Controller
public class DiscountPriceInfoController extends BaseAction {

    @RequestMapping("/discountPriceInfoList.do")
    public ModelAndView discountPriceInfoList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
/*        Map m = new HashMap();
        List<PaypalQuery> paypalli = this.iTradingPayPal.selectByPayPalList(m);
        modelMap.put("paypalli",paypalli);*/

        return forword("module/discountpriceinfo/discountpriceinfoList",modelMap);
    }


    @RequestMapping("/addDiscountPriceInfo.do")
    public ModelAndView addDiscountPriceInfo(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        return forword("module/discountpriceinfo/adddiscountpriceinfo",modelMap);
    }
}
