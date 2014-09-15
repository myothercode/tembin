package com.base.aboutpaypal.service;

import com.base.aboutpaypal.domain.PaypalVO;

import java.util.Map;

/**
 * Created by Administrator on 2014/9/12.
 */
public interface PayPalService {
    PaypalVO getPaypalBalance(Long paypalId) throws Exception;

    /**获取交易的交易费和杂费*/
    PaypalVO getTransactionDetails(Map map) throws Exception;
}
