package com.base.xmlpojo.trading.addproduct;

import com.base.xmlpojo.trading.addproduct.attrclass.*;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by cz on 2014/7/17.
 */
@XStreamAlias("ListingDetails")
public class ListingDetails {
    /**
     * 最好的自动报价
     */
    private BestOfferAutoAcceptPrice BestOfferAutoAcceptPrice;
    /**
     * 地区信息
     */
    private String LocalListingDistance;
    /**
     * 报价最少是好多
     */
    private MinimumBestOfferPrice MinimumBestOfferPrice;

    public BestOfferAutoAcceptPrice getBestOfferAutoAcceptPrice() {
        return BestOfferAutoAcceptPrice;
    }

    public void setBestOfferAutoAcceptPrice(BestOfferAutoAcceptPrice bestOfferAutoAcceptPrice) {
        BestOfferAutoAcceptPrice = bestOfferAutoAcceptPrice;
    }

    public String getLocalListingDistance() {
        return LocalListingDistance;
    }

    public void setLocalListingDistance(String localListingDistance) {
        LocalListingDistance = localListingDistance;
    }

    public MinimumBestOfferPrice getMinimumBestOfferPrice() {
        return MinimumBestOfferPrice;
    }

    public void setMinimumBestOfferPrice(MinimumBestOfferPrice minimumBestOfferPrice) {
        MinimumBestOfferPrice = minimumBestOfferPrice;
    }
}
