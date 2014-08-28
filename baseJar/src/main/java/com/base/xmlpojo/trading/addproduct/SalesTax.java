package com.base.xmlpojo.trading.addproduct;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 销售税
 * Created by cz on 2014/7/17.
 */
@XStreamAlias("SalesTax")
public class SalesTax {
    /**
     * 销售税率
     */
    private Float SalesTaxPercent;
    /**
     * 销售税规定
     */
    private String SalesTaxState;
    /**
     * 是否包含运输税
     */
    private Boolean ShippingIncludedInTax;

    public Float getSalesTaxPercent() {
        return SalesTaxPercent;
    }

    public void setSalesTaxPercent(Float salesTaxPercent) {
        SalesTaxPercent = salesTaxPercent;
    }

    public String getSalesTaxState() {
        return SalesTaxState;
    }

    public void setSalesTaxState(String salesTaxState) {
        SalesTaxState = salesTaxState;
    }

    public Boolean getShippingIncludedInTax() {
        return ShippingIncludedInTax;
    }

    public void setShippingIncludedInTax(Boolean shippingIncludedInTax) {
        ShippingIncludedInTax = shippingIncludedInTax;
    }
}
