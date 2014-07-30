package com.module.controller;

import com.base.database.publicd.model.PublicUserConfig;
import com.base.database.trading.model.TradingDiscountpriceinfo;
import com.base.database.trading.model.TradingItemAddress;
import com.base.domains.SessionVO;
import com.base.domains.querypojos.DiscountpriceinfoQuery;
import com.base.domains.querypojos.PaypalQuery;
import com.base.utils.cache.DataDictionarySupport;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.common.DateUtils;
import com.base.utils.common.ObjectUtils;
import com.common.base.web.BaseAction;
import com.trading.service.ITradingDiscountPriceInfo;
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
 * Created by Administrtor on 2014/7/29.
 */
@Controller
public class DiscountPriceInfoController extends BaseAction {

    @Autowired
    private ITradingDiscountPriceInfo iTradingDiscountPriceInfo;

    /**
     * 查询列表页面跳转
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping("/discountPriceInfoList.do")
    public ModelAndView discountPriceInfoList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        SessionVO c= SessionCacheSupport.getSessionVO();
        Map m = new HashMap();
        m.put("userid",c.getId());
        List<DiscountpriceinfoQuery> disli = this.iTradingDiscountPriceInfo.selectByDiscountpriceinfo(m);
        modelMap.put("disli",disli);

        return forword("module/discountpriceinfo/discountpriceinfoList",modelMap);
    }

    /**
     * 新增界面跳转
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping("/addDiscountPriceInfo.do")
    public ModelAndView addDiscountPriceInfo(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        SessionVO c= SessionCacheSupport.getSessionVO();
        List<PublicUserConfig> userLi= DataDictionarySupport.getPublicUserConfigByType(DataDictionarySupport.PUBLIC_DATA_DICT_EBAYACCOUNT, c.getId());
        modelMap.put("userli",userLi);
        return forword("module/discountpriceinfo/adddiscountpriceinfo",modelMap);
    }

    /**
     * 编辑界面跳转
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping("/editDiscountPriceInfo.do")
    public ModelAndView editDiscountPriceInfo(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        String id = request.getParameter("id");

        SessionVO c= SessionCacheSupport.getSessionVO();
        List<PublicUserConfig> userLi= DataDictionarySupport.getPublicUserConfigByType(DataDictionarySupport.PUBLIC_DATA_DICT_EBAYACCOUNT, c.getId());
        modelMap.put("userli",userLi);
        Map m = new HashMap();
        m.put("userid",c.getId());
        m.put("id",id);
        List<DiscountpriceinfoQuery> disli = this.iTradingDiscountPriceInfo.selectByDiscountpriceinfo(m);
        modelMap.put("dis",disli.get(0));

        return forword("module/discountpriceinfo/adddiscountpriceinfo",modelMap);
    }

    /**
     * 保存数据
     * @param request
     * @param response
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping("/saveDiscountPriceInfo.do")
    public ModelAndView saveDiscountPriceInfo( HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws Exception {

        String name = request.getParameter("name");
        String ebayAccount = request.getParameter("ebayAccount");
        String disStarttime = request.getParameter("disStarttime");
        String disEndtime = request.getParameter("disEndtime");
        String MadeForOutletComparisonPrice = request.getParameter("MadeForOutletComparisonPrice");
        String MinimumAdvertisedPrice = request.getParameter("MinimumAdvertisedPrice");
        String isShippingFee = request.getParameter("isShippingFee");

        String id = request.getParameter("id");


        TradingDiscountpriceinfo tdpi = new TradingDiscountpriceinfo();
        ObjectUtils.toPojo(tdpi);
        if(!ObjectUtils.isLogicalNull(id)){
            tdpi.setId(Long.parseLong(id));
        }
        tdpi.setName(name);
        tdpi.setEbayAccount(ebayAccount);
        tdpi.setDisStarttime(DateUtils.parseDateTime(disStarttime + ":00"));
        tdpi.setDisEndtime(DateUtils.parseDateTime(disEndtime + ":00"));
        tdpi.setIsShippingfee(isShippingFee);
        if(!ObjectUtils.isLogicalNull(MadeForOutletComparisonPrice)){
            tdpi.setMadeforoutletcomparisonprice(Long.parseLong(MadeForOutletComparisonPrice));
        }
        if(!ObjectUtils.isLogicalNull(MinimumAdvertisedPrice)){
            tdpi.setMinimumadvertisedprice(Long.parseLong(MinimumAdvertisedPrice));
        }
        tdpi.setMinimumadvertisedpriceexposure("DuringCheckout");
        this.iTradingDiscountPriceInfo.saveDiscountpriceinfo(tdpi);

        return forword("module/discountpriceinfo/discountpriceinfoList",modelMap);
    }
}
