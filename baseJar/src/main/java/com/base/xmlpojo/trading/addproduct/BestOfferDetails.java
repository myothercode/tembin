package com.base.xmlpojo.trading.addproduct;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 更好报价配置
 * Created by cz on 2014/7/16.
 */
@XStreamAlias("BestOfferDetails")
public class BestOfferDetails {
    /**
     * 是否启用更好报价
     */

    private  Boolean BestOfferEnabled;

    public Boolean getBestOfferEnabled() {
        return BestOfferEnabled;
    }

    public void setBestOfferEnabled(Boolean bestOfferEnabled) {
        BestOfferEnabled = bestOfferEnabled;
    }
}
