package com.base.xmlpojo.trading.addproduct;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

/**
 * 细节变化
 * Created by Administrtor on 2014/7/17.
 */
@XStreamAlias("VariationSpecifics")
public class VariationSpecifics {

    /*
    *键值列表
     */
    @XStreamImplicit(itemFieldName = "NameValueList")
    private List<NameValueList> NameValueList;

    public void setNameValueList(List<NameValueList> nameValueList) {
        NameValueList = nameValueList;
    }

    public List<NameValueList> getNameValueList() {
        return NameValueList;
    }
}
