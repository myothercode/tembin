package com.base.xmlpojo.trading.addproduct;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 价格表详情
 * Created by cz on 2014/7/17.
 */
@XStreamAlias("RateTableDetails")
public class RateTableDetails {
    /**
     * 国内价格表
     */
    private String DomesticRateTable;
    /**
     * 国际价格表
     */
    private String InternationalRateTable;

    public String getDomesticRateTable() {
        return DomesticRateTable;
    }

    public void setDomesticRateTable(String domesticRateTable) {
        DomesticRateTable = domesticRateTable;
    }

    public String getInternationalRateTable() {
        return InternationalRateTable;
    }

    public void setInternationalRateTable(String internationalRateTable) {
        InternationalRateTable = internationalRateTable;
    }
}
