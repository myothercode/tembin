package com.base.xmlpojo.trading.addproduct;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

/**
 * Created by cz on 2014/7/16.
 */
@XStreamAlias("Compatibility")
public class Compatibility {

    private String CompatibilityNotes;
    @XStreamImplicit(itemFieldName = "NameValueList")
    private List<NameValueList> NameValueList;

    public String getCompatibilityNotes() {
        return CompatibilityNotes;
    }

    public void setCompatibilityNotes(String compatibilityNotes) {
        CompatibilityNotes = compatibilityNotes;
    }

    public List<NameValueList> getNameValueList() {
        return NameValueList;
    }

    public void setNameValueList(List<NameValueList> nameValueList) {
        NameValueList = nameValueList;
    }
}
