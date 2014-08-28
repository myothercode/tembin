package com.base.xmlpojo.trading.addproduct.attrclass;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.converters.extended.ToAttributedValueConverter;

/**
 * Created by cz on 2014/7/17.
 */
@XStreamAlias("MinimumBestOfferPrice")
@XStreamConverter(value=ToAttributedValueConverter.class, strings={"value"})
public class MinimumBestOfferPrice {
    public MinimumBestOfferPrice(){

    }

    public MinimumBestOfferPrice(String currencyID,double value){
        this.currencyID = currencyID;
        this.value = value;
    }
    /**
     * 货币种类
     */
    @XStreamAsAttribute
    private String currencyID;
    /**
     * 设置值
     */
    private double value;

    public String getCurrencyID() {
        return currencyID;
    }

    public void setCurrencyID(String currencyID) {
        this.currencyID = currencyID;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
