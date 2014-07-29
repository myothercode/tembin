package com.base.xmlpojo.trading.addproduct;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

/**
 * 产品特性
 * Created by cz on 2014/7/17.
 */
@XStreamAlias("ItemSpecifics")
public class ItemSpecifics {
    @XStreamImplicit(itemFieldName = "NameValueList")
    private List<NameValueList> NameValueList;

    public List<NameValueList> getNameValueList() {
        return NameValueList;
    }

    public void setNameValueList(List<NameValueList> nameValueList) {
        NameValueList = nameValueList;
    }
}
