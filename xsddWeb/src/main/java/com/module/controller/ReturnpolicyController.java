package com.module.controller;

import com.base.domains.CommonParmVO;
import com.base.domains.querypojos.ItemAddressQuery;
import com.base.domains.querypojos.ReturnpolicyQuery;
import com.base.mybatis.page.Page;
import com.base.mybatis.page.PageJsonBean;
import com.base.utils.annotations.AvoidDuplicateSubmission;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
import com.trading.service.ITradingReturnpolicy;
import com.base.database.trading.model.TradingDataDictionary;
import com.base.utils.cache.DataDictionarySupport;
import com.base.database.trading.model.TradingReturnpolicy;
import com.base.utils.common.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lq on 2014/7/29.
 * 退货政策
 */
@Controller
public class ReturnpolicyController extends BaseAction{

    @Autowired
    private ITradingReturnpolicy iTradingReturnpolicy;

    @RequestMapping("/ReturnpolicyList.do")
    public ModelAndView ReturnpolicyList(HttpServletRequest request,HttpServletResponse response,
                                         @ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
        /*Map m = new HashMap();
        List<ReturnpolicyQuery> Returnpolicyli = this.iTradingReturnpolicy.selectByReturnpolicyList(m);
        modelMap.put("Returnpolicyli",Returnpolicyli);*/
        return forword("module/returnpolicy/ReturnpolicyList",modelMap);
    }
    /**获取list数据的ajax方法*/
    @RequestMapping("/ajax/loadReturnpolicyList.do")
    @ResponseBody
    public void loadReturnpolicyList(CommonParmVO commonParmVO){
        Map m = new HashMap();
        /**分页组装*/
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        Page page=jsonBean.toPage();
        List<ReturnpolicyQuery> Returnpolicyli = this.iTradingReturnpolicy.selectByReturnpolicyList(m, page);
        jsonBean.setList(Returnpolicyli);
        jsonBean.setTotal((int)page.getTotalCount());
        AjaxSupport.sendSuccessText("", jsonBean);
    }

    @RequestMapping("/addReturnpolicy.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView addReturnpolicy(HttpServletRequest request,HttpServletResponse response,
                                        @ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
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
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView editReturnpolicy(HttpServletRequest request,HttpServletResponse response,
                                         @ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
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

    @RequestMapping("/ajax/saveReturnpolicy.do")
    @AvoidDuplicateSubmission(needRemoveToken = true)
    @ResponseBody
    public void saveReturnpolicy(HttpServletRequest request,HttpServletResponse response,
                                         ModelMap modelMap,
                                         TradingReturnpolicy tradingReturnpolicy) throws Exception {

        ObjectUtils.toPojo(tradingReturnpolicy);
        if(ObjectUtils.isLogicalNull(tradingReturnpolicy.getId())){
            tradingReturnpolicy.setId(null);
        }

        this.iTradingReturnpolicy.saveTradingReturnpolicy(tradingReturnpolicy);
        AjaxSupport.sendSuccessText("","操作成功!");
    }
}
