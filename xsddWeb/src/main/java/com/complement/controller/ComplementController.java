package com.complement.controller;

import com.base.database.trading.mapper.UsercontrollerEbayAccountMapper;
import com.base.database.trading.model.TradingAutoComplement;
import com.base.database.trading.model.TradingFeedBackDetail;
import com.base.database.trading.model.UsercontrollerEbayAccount;
import com.base.domains.CommonParmVO;
import com.base.domains.SessionVO;
import com.base.domains.querypojos.SystemLogQuery;
import com.base.domains.userinfo.UsercontrollerEbayAccountExtend;
import com.base.mybatis.page.Page;
import com.base.mybatis.page.PageJsonBean;
import com.base.userinfo.service.SystemUserManagerService;
import com.base.utils.cache.SessionCacheSupport;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
import com.complement.service.ITradingAutoComplement;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}
