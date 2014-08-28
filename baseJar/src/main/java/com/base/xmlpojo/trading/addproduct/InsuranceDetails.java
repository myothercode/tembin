package com.base.xmlpojo.trading.addproduct;

import com.base.xmlpojo.trading.addproduct.attrclass.InsuranceFee;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 运输保险详情
 * Created by cz on 2014/7/17.
 */
@XStreamAlias("InsuranceDetails")
public class InsuranceDetails {
    /**
     * 保险费
     */
    private InsuranceFee InsuranceFee;
    /**
     * 保险选项
     */
    private String InsuranceOption;

    public InsuranceFee getInsuranceFee() {
        return InsuranceFee;
    }

    public void setInsuranceFee(InsuranceFee insuranceFee) {
        InsuranceFee = insuranceFee;
    }

    public String getInsuranceOption() {
        return InsuranceOption;
    }

    public void setInsuranceOption(String insuranceOption) {
        InsuranceOption = insuranceOption;
    }
}
