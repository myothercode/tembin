package com.base.xmlpojo.trading.addproduct;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

/**
 * 产品兼容属性列表
 * Created by cz on 2014/7/16.
 */
@XStreamAlias("ItemCompatibilityList")
public class ItemCompatibilityList {
    @XStreamImplicit(itemFieldName = "Compatibility")
    private List<Compatibility> Compatibility;

    public List<Compatibility> getCompatibility() {
        return Compatibility;
    }

    public void setCompatibility(List<Compatibility> compatibility) {
        Compatibility = compatibility;
    }
}
