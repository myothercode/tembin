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

    /**获取交易的交易费和杂费*/
    PaypalVO getTransactionDetails(Map map) throws Exception;

    /**获取paypal账户列表*/
    List<UsercontrollerPaypalAccount> queryPayPalList(Map map, Page page);

    /**启用或者停用paypal账户*/
    void operationPayPalAccount(Map map);

    /**新增一跳paypal记录*/
    void addPayPalAccount(Map map);
}
