package com.base.xmlpojo.trading.addproduct;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 数量信息
 * Created by cz on 2014/7/17.
 */
@XStreamAlias("QuantityInfo")
public class QuantityInfo {
    /**
     * 最少数量
     */
    private int MinimumRemnantSet;

    public int getMinimumRemnantSet() {
        return MinimumRemnantSet;
    }

    public void setMinimumRemnantSet(int minimumRemnantSet) {
        MinimumRemnantSet = minimumRemnantSet;
    }
}
