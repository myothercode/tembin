package com.aboutpaypal.controller;

import com.base.aboutpaypal.domain.PaypalVO;
import com.base.aboutpaypal.service.PayPalService;
import com.base.database.trading.model.UsercontrollerPaypalAccount;
import com.base.domains.CommonParmVO;
import com.base.mybatis.page.Page;
import com.base.mybatis.page.PageJsonBean;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2014/9/13.
 * 有关paypal帐号的操作
 */
@Controller
@RequestMapping("paypal")
public class PaypalController extends BaseAction {
    @Autowired
    private PayPalService payPalService;

    @RequestMapping("addPayPalPage.do")
    /**打开新增paypal帐号页面*/
    public ModelAndView addPayPalPage(@ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
        return   forword("/userinfo/pop/addPayPalPage",modelMap);
    }


    @RequestMapping("getPaypalBalance")
    @ResponseBody
    /**获取paypal余额*/
    public void getPaypalBalance(CommonParmVO commonParmVO) throws Exception {
        PaypalVO acc = payPalService.getPaypalBalance(commonParmVO.getId());
        AjaxSupport.sendSuccessText("",acc);
        return;
    }

    /**获取交易的税费和交易费*/
    @RequestMapping("getTransactionDetails")
    @ResponseBody
    public void getTransactionDetails(CommonParmVO commonParmVO) throws Exception {
        Map map =new HashMap();
        map.put("paypalId",commonParmVO.getId());
        map.put("transactionID",commonParmVO.getStrV1());
        PaypalVO acc = payPalService.getTransactionDetails(map);
        AjaxSupport.sendSuccessText("",acc);
        return;
    }

    @RequestMapping("queryPaypalList")
    @ResponseBody
    /**查询paypalList 分页*/
    public void queryPaypalList(com.base.domains.querypojos.CommonParmVO commonParmVO){
        Map map =new HashMap();
        PageJsonBean jsonBean = commonParmVO.getJsonBean();
        Page page = jsonBean.toPage();
        List<UsercontrollerPaypalAccount> paypalAccounts = payPalService.queryPayPalList(map, page);
        jsonBean.setList(paypalAccounts);
        jsonBean.setPageCount((int) page.getTotalCount());
        AjaxSupport.sendSuccessText("",jsonBean);
    }

    @RequestMapping("operationPayPalAccount.do")
    @ResponseBody
    /**修改paypal状态*/
    public void operationPayPalAccount(Long paypalId,String stat){
        Map map=new HashMap();
        map.put("paypalId",paypalId);
        map.put("status",stat);
        payPalService.operationPayPalAccount(map);
        AjaxSupport.sendSuccessText("","操作成功!");
    }

    @RequestMapping("addPayPalAccount.do")
    @ResponseBody
    /**增加y一条paypal账户*/
    public void addPayPalAccount(UsercontrollerPaypalAccount paypalAccount){
        Map map =new HashMap();
        map.put("paypalAccountVO",paypalAccount);
        payPalService.addPayPalAccount(map);
        AjaxSupport.sendSuccessText("","操作成功！");
    }


}
