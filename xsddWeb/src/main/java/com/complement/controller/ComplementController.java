package com.complement.controller;

import com.base.database.trading.mapper.UsercontrollerEbayAccountMapper;
import com.base.database.trading.model.*;
import com.base.domains.CommonParmVO;
import com.base.domains.SessionVO;
import com.base.domains.querypojos.SetComplementQuery;
import com.base.domains.querypojos.SystemLogQuery;
import com.base.domains.userinfo.UsercontrollerEbayAccountExtend;
import com.base.mybatis.page.Page;
import com.base.mybatis.page.PageJsonBean;
import com.base.userinfo.service.SystemUserManagerService;
import com.base.utils.cache.SessionCacheSupport;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
import com.complement.service.ITradingAutoComplement;
import com.complement.service.ITradingInventoryComplement;
import com.complement.service.ITradingSetComplement;
import com.trading.service.IUsercontrollerEbayAccount;
import org.apache.commons.lang.StringUtils;
import org.apache.http.impl.cookie.DateParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by Administrtor on 2014/12/11.
 */
@Controller
public class ComplementController extends BaseAction{
    @Autowired
    private ITradingAutoComplement iTradingAutoComplement;
    @Autowired
    private SystemUserManagerService systemUserManagerService;
    @Autowired
    private IUsercontrollerEbayAccount iUsercontrollerEbayAccount;
    @Autowired
    private ITradingInventoryComplement iTradingInventoryComplement;
    @Autowired
    private ITradingSetComplement iTradingSetComplement;

    @RequestMapping("/complement/complementManager.do")
    public ModelAndView complementManager(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws DateParseException {
        return forword("complement/complementManager",modelMap);
    }

    @RequestMapping("/complement/addComplement.do")
    public ModelAndView addComplement(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws DateParseException {
        List<UsercontrollerEbayAccountExtend> ebayList=systemUserManagerService.queryCurrAllEbay(new HashMap());
        modelMap.put("ebayList",ebayList);
        return forword("complement/addComplement",modelMap);
    }

    @RequestMapping("/ajax/loadComplementTable.do")
     @ResponseBody
     public void loadComplementTable(HttpServletRequest request,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap,CommonParmVO commonParmVO) throws DateParseException {
        Map m = new HashMap();
        SessionVO c= SessionCacheSupport.getSessionVO();
        m.put("userId",c.getId());
        /**分页组装*/
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        Page page=jsonBean.toPage();
        List<TradingAutoComplement> paypalli = this.iTradingAutoComplement.selectByList(m, page);
        jsonBean.setList(paypalli);
        jsonBean.setTotal((int)page.getTotalCount());
        AjaxSupport.sendSuccessText("", jsonBean);
    }

    @RequestMapping("/ajax/saveComplement.do")
    @ResponseBody
    public void saveComplement(TradingAutoComplement tradingAutoComplement,HttpServletRequest request,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap,CommonParmVO commonParmVO) throws DateParseException {
        SessionVO c= SessionCacheSupport.getSessionVO();
        String[] ebayAccounts = request.getParameterValues("ebayAccounts");
        for(String ebayAccount:ebayAccounts){
            tradingAutoComplement.setCreateUser(c.getId());
            tradingAutoComplement.setCreateDate(new Date());
            tradingAutoComplement.setEbayId(Long.parseLong(ebayAccount));
            UsercontrollerEbayAccount ue = this.iUsercontrollerEbayAccount.selectById(Long.parseLong(ebayAccount));
            tradingAutoComplement.setEbayAccount(ue.getEbayAccount());
            this.iTradingAutoComplement.saveAutoComplement(tradingAutoComplement);
        }

        AjaxSupport.sendSuccessText("", "");
    }
    @RequestMapping("/ajax/delComplement.do")
    @ResponseBody
    public void delComplement(TradingAutoComplement tradingAutoComplement,HttpServletRequest request,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap,CommonParmVO commonParmVO) throws DateParseException {
        String id = request.getParameter("id");
        this.iTradingAutoComplement.delAutoComplement(Long.parseLong(id));
        AjaxSupport.sendSuccessText("", "");
    }

    @RequestMapping("/ajax/loadComplementLog.do")
    @ResponseBody
    public void loadComplementLog(HttpServletRequest request,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap,CommonParmVO commonParmVO) throws DateParseException {
        Map m = new HashMap();
        SessionVO c= SessionCacheSupport.getSessionVO();
        m.put("userId",c.getId());
        m.put("eventName","AutoComplement");
        /**分页组装*/
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        Page page=jsonBean.toPage();
        List<SystemLogQuery> paypalli = this.iTradingAutoComplement.selectLogList(m, page);
        jsonBean.setList(paypalli);
        jsonBean.setTotal((int)page.getTotalCount());
        AjaxSupport.sendSuccessText("", jsonBean);
    }

    /**
     * 加载用户设置列表
     * @param request
     * @param modelMap
     * @param commonParmVO
     * @throws DateParseException
     */
    @RequestMapping("/ajax/loadSetComplementTable.do")
    @ResponseBody
    public void loadSetComplementTable(HttpServletRequest request,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap,CommonParmVO commonParmVO) throws DateParseException {
        Map m = new HashMap();
        SessionVO c= SessionCacheSupport.getSessionVO();
        m.put("userId",c.getId());
        /**分页组装*/
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        Page page=jsonBean.toPage();
        List<SetComplementQuery> paypalli = this.iTradingSetComplement.selectBySetComplementList(m, page);
        jsonBean.setList(paypalli);
        jsonBean.setTotal((int)page.getTotalCount());
        AjaxSupport.sendSuccessText("", jsonBean);
    }

    @RequestMapping("/complement/addSetEbayComplement.do")
    public ModelAndView addSetEbayComplement(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws DateParseException {
        List<UsercontrollerEbayAccountExtend> ebayList=systemUserManagerService.queryCurrAllEbay(new HashMap());
        modelMap.put("ebayList",ebayList);
        return forword("complement/addSetEbayComplement",modelMap);
    }

    @RequestMapping("/ajax/saveSetEbayComplement.do")
    @ResponseBody
    public void saveSetEbayComplement(TradingSetComplement tradingSetComplement,HttpServletRequest request,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap,CommonParmVO commonParmVO) throws DateParseException {
        SessionVO c= SessionCacheSupport.getSessionVO();
        String[] ebayAccounts = request.getParameterValues("ebayAccounts");
        for(String ebayAccount:ebayAccounts){
            TradingSetComplement tsc = this.iTradingSetComplement.selectByEbayId(Long.parseLong(ebayAccount));
            if(tsc!=null){
                tsc.setComplementType(tradingSetComplement.getComplementType());
                tsc.setUpdateUser(c.getId());
                tsc.setUpdateDate(new Date());
                if(!tsc.getComplementType().equals(tradingSetComplement.getComplementType())){
                    this.iTradingAutoComplement.delByEbayId(tsc.getEbayId());
                }
                this.iTradingSetComplement.saveTradingSetComplement(tsc);
            }else{
                UsercontrollerEbayAccount ue = this.iUsercontrollerEbayAccount.selectById(Long.parseLong(ebayAccount));
                tradingSetComplement.setEbayAccount(ue.getEbayAccount());
                tradingSetComplement.setEbayId(Long.parseLong(ebayAccount));
                tradingSetComplement.setCreateUser(c.getId());
                tradingSetComplement.setCreateDate(new Date());
                tradingSetComplement.setUpdateUser(c.getId());
                tradingSetComplement.setUpdateDate(new Date());
                this.iTradingSetComplement.saveTradingSetComplement(tradingSetComplement);
            }
        }
        AjaxSupport.sendSuccessText("", "");
    }

    @RequestMapping("/ajax/delSetEbayComplement.do")
    @ResponseBody
    public void delSetEbayComplement(TradingAutoComplement tradingAutoComplement,HttpServletRequest request,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap,CommonParmVO commonParmVO) throws DateParseException {
        String id = request.getParameter("id");
        TradingSetComplement tsc = this.iTradingSetComplement.selectById(Long.parseLong(id));
        this.iTradingAutoComplement.delByEbayId(tsc.getEbayId());
        this.iTradingSetComplement.delTradingSetComplement(Long.parseLong(id));
        AjaxSupport.sendSuccessText("", "");
    }

    @RequestMapping("/complement/editSetEbayComplement.do")
    public ModelAndView editSetEbayComplement(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws DateParseException {
        String id = request.getParameter("id");
        TradingSetComplement tsc = this.iTradingSetComplement.selectById(Long.parseLong(id));
        List<UsercontrollerEbayAccountExtend> ebayList=systemUserManagerService.queryCurrAllEbay(new HashMap());
        modelMap.put("ebayList",ebayList);
        modelMap.put("tsc",tsc);
        return forword("complement/addSetEbayComplement",modelMap);
    }

    /**
     * 库存调数设置
     */

    @RequestMapping("/complement/addInventoryComplement.do")
    public ModelAndView addInventoryComplement(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws DateParseException {
        List<UsercontrollerEbayAccountExtend> ebayList=systemUserManagerService.queryCurrAllEbay(new HashMap());
        modelMap.put("ebayList",ebayList);
        return forword("complement/addInventoryComplement",modelMap);
    }


    @RequestMapping("/ajax/saveInventoryComplement.do")
    @ResponseBody
    public void saveInventoryComplement(TradingInventoryComplement tradingInventoryComplement,HttpServletRequest request,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap,CommonParmVO commonParmVO) throws DateParseException {
        SessionVO c= SessionCacheSupport.getSessionVO();
        String[] ebayAccounts = request.getParameterValues("ebayAccounts");
        String skuStr = request.getParameter("skuStr");
        String [] skus = skuStr.split(",");
        for(String ebayAccount:ebayAccounts){
            tradingInventoryComplement.setCreateDate(new Date());
            tradingInventoryComplement.setCreateUser(c.getId());
            tradingInventoryComplement.setCreateUserName(c.getUserName());
            tradingInventoryComplement.setEbayId(Long.parseLong(ebayAccount));
            UsercontrollerEbayAccount ue = this.iUsercontrollerEbayAccount.selectById(Long.parseLong(ebayAccount));
            tradingInventoryComplement.setEbayAccount(ue.getEbayAccount());
            List<TradingInventoryComplementMore> lim = new ArrayList<TradingInventoryComplementMore>();
            for(String sku:skus){
                TradingInventoryComplementMore ticm = new TradingInventoryComplementMore();
                ticm.setSkuValue(sku);
                lim.add(ticm);
            }
            this.iTradingInventoryComplement.saveInventoryComplement(tradingInventoryComplement,lim);
        }
        AjaxSupport.sendSuccessText("", "");
    }

    /**
     * 加载库存补数设置列表
     * @param request
     * @param modelMap
     * @param commonParmVO
     * @throws DateParseException
     */
    @RequestMapping("/ajax/loadInventoryComplementTable.do")
    @ResponseBody
    public void loadInventoryComplementTable(HttpServletRequest request,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap,CommonParmVO commonParmVO) throws DateParseException {
        Map m = new HashMap();
        SessionVO c= SessionCacheSupport.getSessionVO();
        m.put("userId",c.getId());
        /**分页组装*/
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        Page page=jsonBean.toPage();
        List<TradingInventoryComplement> paypalli = this.iTradingInventoryComplement.selectByInventoryComplementList(m,page);
        jsonBean.setList(paypalli);
        jsonBean.setTotal((int)page.getTotalCount());
        AjaxSupport.sendSuccessText("", jsonBean);
    }

    /**
     * 编辑库存补数规则
     */

    @RequestMapping("/complement/editInventoryComplement.do")
    public ModelAndView editInventoryComplement(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws DateParseException {
        List<UsercontrollerEbayAccountExtend> ebayList=systemUserManagerService.queryCurrAllEbay(new HashMap());
        modelMap.put("ebayList",ebayList);
        String id = request.getParameter("id");
        TradingInventoryComplement tic = this.iTradingInventoryComplement.selectById(Long.parseLong(id));
        modelMap.put("tic",tic);
        List<TradingInventoryComplementMore> litcm = this.iTradingInventoryComplement.slectByParentId(tic.getId());
        modelMap.put("litcm",litcm);
        return forword("complement/addInventoryComplement",modelMap);
    }

    @RequestMapping("/ajax/delInventoryComplement.do")
    @ResponseBody
    public void delInventoryComplement(TradingAutoComplement tradingAutoComplement,HttpServletRequest request,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap,CommonParmVO commonParmVO) throws DateParseException {
        String id = request.getParameter("id");
        this.iTradingInventoryComplement.delAllData(Long.parseLong(id));
        AjaxSupport.sendSuccessText("", "");
    }
}
