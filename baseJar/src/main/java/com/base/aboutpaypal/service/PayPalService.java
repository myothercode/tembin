package com.base.aboutpaypal.service;

import com.base.aboutpaypal.domain.PaypalVO;
import com.base.database.trading.model.UsercontrollerPaypalAccount;
import com.base.mybatis.page.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2014/9/12.
 */
public interface PayPalService {
    PaypalVO getPaypalBalance(Long paypalId) throws Exception;

    /**修改paypal帐号为已验证*/
    void setPayPalSFCheck(Long paypalId, String sfCheck);

    /**获取交易的交易费和杂费*/
    Map getTransactionDetails(Map map) throws Exception;

    /**获取paypal账户列表*/
    List<UsercontrollerPaypalAccount> queryPayPalList(Map map, Page page);

    UsercontrollerPaypalAccount selectById(Long id);

    /**启用或者停用paypal账户*/
    void operationPayPalAccount(Map map);

    /**新增一跳paypal记录*/
    void addPayPalAccount(Map map);
    /**paypal退全款:根据paypal交易号*/
    Map<String, String> refundTransactionFull(Map map) throws Exception;
    /**paypal退半款:根据paypal交易号*/
    Map<String, String> refundTransactionPartial(Map map) throws Exception;

    List<UsercontrollerPaypalAccount> selectByOrgId();
}
