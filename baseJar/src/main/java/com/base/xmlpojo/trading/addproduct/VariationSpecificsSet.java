package com.base.xmlpojo.trading.addproduct;

import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

/**
 * Created by Administrtor on 2014/7/17.
 */
public class VariationSpecificsSet {
    /*
    *键值列表
     */
    @XStreamImplicit(itemFieldName = "NameValueList")

    private List<NameValueList> NameValueList;


    public List<NameValueList> getNameValueList() {
        return NameValueList;
    }

    public void setNameValueList(List<NameValueList> nameValueList) {
        NameValueList = nameValueList;
    }
}
