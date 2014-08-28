package com.base.xmlpojo.trading.addproduct;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 二级分类
 * Created by cz on 2014/7/17.
 */
@XStreamAlias("SecondaryCategory")
public class SecondaryCategory {
    /**
     * 分类ＩＤ
     */
    private String CategoryID;

    public String getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(String categoryID) {
        CategoryID = categoryID;
    }
}
