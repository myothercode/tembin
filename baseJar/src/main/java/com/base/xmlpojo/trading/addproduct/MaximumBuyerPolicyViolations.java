package com.base.xmlpojo.trading.addproduct;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 最大买家违规次数
 * Created by cz on 2014/7/16.
 */
@XStreamAlias("MaximumBuyerPolicyViolations")
public class MaximumBuyerPolicyViolations {
    /**
     * 次数
     */
    private Integer Count;
    /**
     *周期
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
