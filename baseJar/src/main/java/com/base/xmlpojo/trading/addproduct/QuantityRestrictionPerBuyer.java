package com.base.xmlpojo.trading.addproduct;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 买家最多买数量
 * Created by cz on 2014/7/17.
 */
@XStreamAlias("QuantityRestrictionPerBuyer")
public class QuantityRestrictionPerBuyer {
    /**
     * 最多购买数量
     */
    private Integer MaximumQuantity;

    public Integer getMaximumQuantity() {
        return MaximumQuantity;
    }

    public void setMaximumQuantity(Integer maximumQuantity) {
        MaximumQuantity = maximumQuantity;
    }
}
