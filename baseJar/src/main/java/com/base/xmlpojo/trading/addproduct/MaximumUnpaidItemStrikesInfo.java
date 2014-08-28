package com.base.xmlpojo.trading.addproduct;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 在一定周期内买家未付款次数
 * Created by cz on 2014/7/16.
 */
@XStreamAlias("MaximumUnpaidItemStrikesInfo")
public class MaximumUnpaidItemStrikesInfo {
    /**
     * 数量
     */
    private Integer Count;
    /**
     * 周期
     */
    private String Period;

    public Integer getCount() {
        return Count;
    }

    public void setCount(Integer count) {
        Count = count;
    }

    public String getPeriod() {
        return Period;
    }

    public void setPeriod(String period) {
        Period = period;
    }
}
