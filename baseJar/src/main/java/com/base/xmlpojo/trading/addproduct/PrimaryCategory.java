package com.base.xmlpojo.trading.addproduct;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by cz on 2014/7/16.
 */
@XStreamAlias("PrimaryCategory")
public class PrimaryCategory {
    /**
     * 产品分类ＩＤ
     */
    @XStreamAlias("CategoryID")
    private String categoryID;

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }
}
