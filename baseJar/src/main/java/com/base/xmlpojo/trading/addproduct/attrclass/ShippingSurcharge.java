package com.base.xmlpojo.trading.addproduct.attrclass;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.converters.extended.ToAttributedValueConverter;

/**
 * Created by Administrtor on 2014/7/17.
 */
@XStreamAlias("ShippingSurcharge")
@XStreamConverter(value=ToAttributedValueConverter.class, strings={"value"})
public class ShippingSurcharge {
    public ShippingSurcharge(){

    }

    public ShippingSurcharge(String currencyID,Double value){
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
    private Double value;

    public String getCurrencyID() {
        return currencyID;
    }

    public void setCurrencyID(String currencyID) {
        this.currencyID = currencyID;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
