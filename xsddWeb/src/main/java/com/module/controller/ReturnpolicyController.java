package com.module.controller;

import com.base.database.trading.model.TradingDataDictionary;
import com.base.database.trading.model.TradingReturnpolicy;
import com.base.domains.CommonParmVO;
import com.base.domains.SessionVO;
import com.base.domains.querypojos.ReturnpolicyQuery;
import com.base.domains.userinfo.UsercontrollerUserExtend;
import com.base.mybatis.page.Page;
import com.base.mybatis.page.PageJsonBean;
import com.base.userinfo.service.SystemUserManagerService;
import com.base.utils.annotations.AvoidDuplicateSubmission;
import com.base.utils.cache.DataDictionarySupport;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.common.ObjectUtils;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
import com.trading.service.ITradingReturnpolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
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
 * Created by lq on 2014/7/29.
 * 退货政策
 */
@Controller
public class ReturnpolicyController extends BaseAction{

    @Autowired
    private ITradingReturnpolicy iTradingReturnpolicy;
    @Autowired
    private SystemUserManagerService systemUserManagerService;

    @RequestMapping("/ReturnpolicyList.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
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
    public void loadReturnpolicyList(HttpServletRequest request,CommonParmVO commonParmVO){
        Map m = new HashMap();
        String checkFlag = request.getParameter("checkFlag");
        m.put("checkFlag",checkFlag);
        SessionVO c= SessionCacheSupport.getSessionVO();
        if(systemUserManagerService.isAdminRole()){
            List<UsercontrollerUserExtend> liuue = systemUserManagerService.queryAllUsersByOrgID("yes");
            List<String> liue = new ArrayList<String>();
            for(UsercontrollerUserExtend uue:liuue){
                liue.add(uue.getUserId()+"");
            }
            liue.add(c.getId()+"");
            m.put("liue",liue);
        }else{
            m.put("userid",c.getId());
        }
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
        String type = request.getParameter("type");
        if(type!=null&&!"".equals(type)){
            modelMap.put("type",type);
        }
        return forword("module/returnpolicy/addReturnpolicy",modelMap);
    }

    @RequestMapping("/ajax/saveReturnpolicy.do")
    @AvoidDuplicateSubmission(needRemoveToken = true)
    @ResponseBody
    public void saveReturnpolicy(HttpServletRequest request,HttpServletResponse response,
                                         ModelMap modelMap,
                                         TradingReturnpolicy tradingReturnpolicy) throws Exception {

        if(ObjectUtils.isLogicalNull(tradingReturnpolicy.getId())){
            tradingReturnpolicy.setId(null);
        }
        this.iTradingReturnpolicy.saveTradingReturnpolicy(tradingReturnpolicy);
        AjaxSupport.sendSuccessText("","操作成功!");
    }

    /**
     * 保存数据
     * @param request
     * @param response
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping("/ajax/delReturnPolicy.do")
    @AvoidDuplicateSubmission(needRemoveToken = true)
    @ResponseBody
    public void delReturnPolicy(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        String id = request.getParameter("id");
        TradingReturnpolicy tp= this.iTradingReturnpolicy.selectById(Long.parseLong(id));
        if("1".equals(tp.getCheckFlag())){
            tp.setCheckFlag("0");
        }else{
            tp.setCheckFlag("1");
        }
        this.iTradingReturnpolicy.saveTradingReturnpolicy(tp);
        AjaxSupport.sendSuccessText("","操作成功!");
    }
}
