package com.base.xmlpojo.trading.addproduct;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 品牌
 * Created by cz on 2014/7/17.
 */
@XStreamAlias("BrandMPN")
public class BrandMPN {
    private String Brand;
    private String MPN;

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public String getMPN() {
        return MPN;
    }

    public void setMPN(String MPN) {
        this.MPN = MPN;
    }
}
