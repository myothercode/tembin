package com.base.xmlpojo.trading.addproduct;

import com.base.xmlpojo.trading.addproduct.attrclass.InsuranceFee;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 国际保险明细
 * Created by cz on 2014/7/17.
 */
@XStreamAlias("InternationalInsuranceDetails")
public class InternationalInsuranceDetails {

    private InsuranceFee InsuranceFee;

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
