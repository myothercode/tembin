package com.base.xmlpojo.trading.addproduct;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 *清单付款重定向的偏好
 * Created by cz on 2014/7/17.
 */
@XStreamAlias("ListingCheckoutRedirectPreference")
public class ListingCheckoutRedirectPreference {
    /**
     *商品所在商店名称
     */
    private String ProStoresStoreName;
    /**
     *卖家名称
     */
    private String SellerThirdPartyUsername;

    public String getProStoresStoreName() {
        return ProStoresStoreName;
    }

    public void setProStoresStoreName(String proStoresStoreName) {
        ProStoresStoreName = proStoresStoreName;
    }

    public String getSellerThirdPartyUsername() {
        return SellerThirdPartyUsername;
    }

    public void setSellerThirdPartyUsername(String sellerThirdPartyUsername) {
        SellerThirdPartyUsername = sellerThirdPartyUsername;
    }
}
