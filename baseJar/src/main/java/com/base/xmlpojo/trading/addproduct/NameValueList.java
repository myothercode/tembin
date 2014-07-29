package com.base.xmlpojo.trading.addproduct;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

/**
 *
 * Created by cz on 2014/7/16.
 */
@XStreamAlias("NameValueList")
public class NameValueList {

    private String Name;
    @XStreamImplicit(itemFieldName = "Value")
    private List<String> Value;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public List<String> getValue() {
        return Value;
    }

    public void setValue(List<String> value) {
        Value = value;
    }
}
