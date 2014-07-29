package com.base.xmlpojo.trading.addproduct;
import com.base.xmlpojo.trading.addproduct.attrclass.*;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 折扣详情
 * Created by cz on 2014/7/16.
 */
@XStreamAlias("DiscountPriceInfo")
public class DiscountPriceInfo {
    /**
     * 折扣比例
     */
    private MadeForOutletComparisonPrice MadeForOutletComparisonPrice;
    /**
     *折扣金额
     */
    private MinimumAdvertisedPrice MinimumAdvertisedPrice;
    /**
     *折扣方式
     */
    private String MinimumAdvertisedPriceExposure;
    /**
     *原始价格
     */
    private OriginalRetailPrice OriginalRetailPrice;
    /**
     *是否售完
     */
    private Boolean SoldOffeBay;
    /**
     *是否已售出
     */
    private Boolean SoldOneBay;

    public MadeForOutletComparisonPrice getMadeForOutletComparisonPrice() {
        return MadeForOutletComparisonPrice;
    }

    public void setMadeForOutletComparisonPrice(MadeForOutletComparisonPrice madeForOutletComparisonPrice) {
        MadeForOutletComparisonPrice = madeForOutletComparisonPrice;
    }

    public MinimumAdvertisedPrice getMinimumAdvertisedPrice() {
        return MinimumAdvertisedPrice;
    }

    public void setMinimumAdvertisedPrice(MinimumAdvertisedPrice minimumAdvertisedPrice) {
        MinimumAdvertisedPrice = minimumAdvertisedPrice;
    }

    public String getMinimumAdvertisedPriceExposure() {
        return MinimumAdvertisedPriceExposure;
    }

    public void setMinimumAdvertisedPriceExposure(String minimumAdvertisedPriceExposure) {
        MinimumAdvertisedPriceExposure = minimumAdvertisedPriceExposure;
    }

    public OriginalRetailPrice getOriginalRetailPrice() {
        return OriginalRetailPrice;
    }

    public void setOriginalRetailPrice(OriginalRetailPrice originalRetailPrice) {
        OriginalRetailPrice = originalRetailPrice;
    }

    public Boolean getSoldOffeBay() {
        return SoldOffeBay;
    }

    public void setSoldOffeBay(Boolean soldOffeBay) {
        SoldOffeBay = soldOffeBay;
    }

    public Boolean getSoldOneBay() {
        return SoldOneBay;
    }

    public void setSoldOneBay(Boolean soldOneBay) {
        SoldOneBay = soldOneBay;
    }
}
