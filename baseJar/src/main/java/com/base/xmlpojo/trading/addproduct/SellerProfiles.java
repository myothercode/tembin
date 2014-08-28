package com.base.xmlpojo.trading.addproduct;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 卖家资料
 * Created by cz on 2014/7/17.
 */
@XStreamAlias("SellerProfiles")
public class SellerProfiles {
    private SellerPaymentProfile SellerPaymentProfile;

    private SellerReturnProfile SellerReturnProfile;

    private SellerShippingProfile SellerShippingProfile;

    public SellerPaymentProfile getSellerPaymentProfile() {
        return SellerPaymentProfile;
    }

    public void setSellerPaymentProfile(SellerPaymentProfile sellerPaymentProfile) {
        SellerPaymentProfile = sellerPaymentProfile;
    }

    public SellerReturnProfile getSellerReturnProfile() {
        return SellerReturnProfile;
    }

    public void setSellerReturnProfile(SellerReturnProfile sellerReturnProfile) {
        SellerReturnProfile = sellerReturnProfile;
    }

    public SellerShippingProfile getSellerShippingProfile() {
        return SellerShippingProfile;
    }

    public void setSellerShippingProfile(SellerShippingProfile sellerShippingProfile) {
        SellerShippingProfile = sellerShippingProfile;
    }
}
