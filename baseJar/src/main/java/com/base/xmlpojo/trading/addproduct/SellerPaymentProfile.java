package com.base.xmlpojo.trading.addproduct;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 卖家支付资料
 * Created by cz on 2014/7/17.
 */
@XStreamAlias("SellerPaymentProfile")
public class SellerPaymentProfile {
    /**
     * 支付ＩＤ
     */
    private Long PaymentProfileID;
    /**
     * 支付名称
     */
    private String PaymentProfileName;


    public Long getPaymentProfileID() {
        return PaymentProfileID;
    }

    public void setPaymentProfileID(Long paymentProfileID) {
        PaymentProfileID = paymentProfileID;
    }

    public String getPaymentProfileName() {
        return PaymentProfileName;
    }

    public void setPaymentProfileName(String paymentProfileName) {
        PaymentProfileName = paymentProfileName;
    }
}
