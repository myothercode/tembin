package com.base.xmlpojo.trading.addproduct;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 卖家运输资料
 * Created by cz on 2014/7/17.
 */
@XStreamAlias("SellerShippingProfile")
public class SellerShippingProfile {
    /**
     * 运输ＩＤ
     */
    private Long ShippingProfileID;
    /**
     * 运输名称
     */
    private String ShippingProfileName;

    public Long getShippingProfileID() {
        return ShippingProfileID;
    }

    public void setShippingProfileID(Long shippingProfileID) {
        ShippingProfileID = shippingProfileID;
    }

    public String getShippingProfileName() {
        return ShippingProfileName;
    }

    public void setShippingProfileName(String shippingProfileName) {
        ShippingProfileName = shippingProfileName;
    }
}
