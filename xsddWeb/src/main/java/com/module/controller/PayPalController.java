package com.module.controller;

import com.base.database.publicd.model.PublicUserConfig;
import com.base.database.trading.model.TradingDataDictionary;
import com.base.database.trading.model.TradingPaypal;
import com.base.domains.SessionVO;
import com.base.domains.CommonParmVO;
import com.base.domains.querypojos.PaypalQuery;
import com.base.mybatis.page.Page;
import com.base.mybatis.page.PageJsonBean;
import com.base.utils.cache.DataDictionarySupport;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.common.ObjectUtils;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
import com.trading.service.ITradingDataDictionary;
import com.trading.service.ITradingPayPal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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

    /**
     * 列表页面跳转及查询
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping("/PayPalList.do")
    public ModelAndView payPalList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        /*Map m = new HashMap();
        List<PaypalQuery> paypalli = this.iTradingPayPal.selectByPayPalList(m);
        modelMap.put("paypalli",paypalli);*/
        return forword("module/paypal/PayPalList",modelMap);
    }


    @RequestMapping("/ajax/loadPayPalList.do")
    @ResponseBody
    public void loadPayPalList(ModelMap modelMap,CommonParmVO commonParmVO){
        Map m = new HashMap();
        /**分页组装*/
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        Page page=jsonBean.toPage();
        List<PaypalQuery> paypalli = this.iTradingPayPal.selectByPayPalList(m,page);
        jsonBean.setList(paypalli);
        jsonBean.setTotal((int)page.getTotalCount());
        AjaxSupport.sendSuccessText("",jsonBean);
    }


    /**
     * 新增页面跳转
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping("/addPayPal.do")
    public ModelAndView addPayPal(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        List<TradingDataDictionary> lidata = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.DATA_DICT_SITE);

        modelMap.put("siteList",lidata);
        SessionVO c= SessionCacheSupport.getSessionVO();
        List<PublicUserConfig> paypalList = DataDictionarySupport.getPublicUserConfigByType(DataDictionarySupport.PUBLIC_DATA_DICT_PAYPAL, c.getId());
        modelMap.put("paypalList",paypalList);

        return forword("module/paypal/addPayPal",modelMap);
    }

    /**
     * 编辑页面跳转
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping("/editPayPal.do")
    public ModelAndView editPayPal(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        SessionVO c= SessionCacheSupport.getSessionVO();
        List<TradingDataDictionary> lidata = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.DATA_DICT_SITE);
        modelMap.put("siteList",lidata);
        List<PublicUserConfig> paypalList = DataDictionarySupport.getPublicUserConfigByType(DataDictionarySupport.PUBLIC_DATA_DICT_EBAYACCOUNT, c.getId());
        modelMap.put("paypalList",paypalList);
        Map m = new HashMap();
        m.put("id",request.getParameter("id"));
        PaypalQuery paypalli = this.iTradingPayPal.selectByPayPal(m);
        modelMap.put("paypal",paypalli);
        return forword("module/paypal/addPayPal",modelMap);
    }

    /**
     * 保存数据
     * @param request
     * @param response
     * @param modelMap
     * @return
     * @throws Exception
     */
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
