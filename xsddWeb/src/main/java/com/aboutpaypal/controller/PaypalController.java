package com.aboutpaypal.controller;

import com.base.aboutpaypal.domain.PaypalVO;
import com.base.aboutpaypal.service.PayPalService;
import com.base.domains.CommonParmVO;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
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
}
