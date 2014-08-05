package com.item.controller;

import com.base.database.publicd.model.PublicUserConfig;
import com.base.database.trading.model.*;
import com.base.domains.SessionVO;
import com.base.utils.annotations.AvoidDuplicateSubmission;
import com.base.utils.cache.DataDictionarySupport;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.xmlutils.PojoXmlUtil;
import com.base.xmlpojo.trading.addproduct.Item;
import com.base.xmlpojo.trading.addproduct.ShippingDetails;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
import com.trading.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Administrtor on 2014/8/2.
 */
@Controller
public class ItemController extends BaseAction{

    @Autowired
    private ITradingItem iTradingItem;
    @Autowired
    private ITradingPayPal iTradingPayPal;
    @Autowired
    private ITradingShippingDetails iTradingShippingDetails;
    @Autowired
    private ITradingDiscountPriceInfo iTradingDiscountPriceInfo;
    @Autowired
    private ITradingItemAddress iTradingItemAddress;
    @Autowired
    private ITradingReturnpolicy iTradingReturnpolicy;
    @Autowired
    private ITradingBuyerRequirementDetails iTradingBuyerRequirementDetails;
    /**
     * 商品展示列表
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping("/itemList.do")
    public ModelAndView itemList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        return forword("item/itemList",modelMap);
    }

    @RequestMapping("/addItem.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView addItem(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        List<TradingDataDictionary> lidata = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.DATA_DICT_SITE);
        modelMap.put("siteList",lidata);

        SessionVO c= SessionCacheSupport.getSessionVO();
        List<PublicUserConfig> ebayList = DataDictionarySupport.getPublicUserConfigByType(DataDictionarySupport.PUBLIC_DATA_DICT_EBAYACCOUNT, c.getId());
        modelMap.put("ebayList",ebayList);

        return forword("item/addItem",modelMap);
    }

    /**
     * 保存运输选项数据
     * @param request
     * @throws Exception
     */
    @RequestMapping("/saveItem.do")
    @AvoidDuplicateSubmission(needRemoveToken = true)
    @ResponseBody
    public void saveItem(HttpServletRequest request,Item item,TradingItem tradingItem) throws Exception {

        //保存商品信息到数据库中
        this.iTradingItem.saveTradingItem(tradingItem);
        TradingPaypal tpay = this.iTradingPayPal.selectById(tradingItem.getPayId());
        TradingItemAddress tadd = this.iTradingItemAddress.selectById(tradingItem.getItemLocationId());
        TradingShippingdetails tshipping = this.iTradingShippingDetails.selectById(tradingItem.getShippingDeailsId());
        TradingDiscountpriceinfo tdiscount = this.iTradingDiscountPriceInfo.selectById(tradingItem.getDiscountpriceinfoId());
        TradingBuyerRequirementDetails tbuyer = this.iTradingBuyerRequirementDetails.selectById(tradingItem.getBuyerId());
        TradingReturnpolicy treturn = this.iTradingReturnpolicy.selectById(tradingItem.getReturnpolicyId());

        System.out.println(PojoXmlUtil.pojoToXml(item));
        AjaxSupport.sendSuccessText("message", "操作成功！");
    }
}
