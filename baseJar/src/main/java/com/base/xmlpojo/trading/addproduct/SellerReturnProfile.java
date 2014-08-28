package com.base.xmlpojo.trading.addproduct;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 卖家返回资料
 * Created by cz on 2014/7/17.
 */
@XStreamAlias("SellerReturnProfile")
public class SellerReturnProfile {
    /**
     * 卖家返回资料ＩＤ
     */
    private Long ReturnProfileID;
    /**
     * 卖家返回资料名称
     */
    private String ReturnProfileName;

    public Long getReturnProfileID() {
        return ReturnProfileID;
    }

    public void setReturnProfileID(Long returnProfileID) {
        ReturnProfileID = returnProfileID;
    }

    public String getReturnProfileName() {
        return ReturnProfileName;
    }

    public void setReturnProfileName(String returnProfileName) {
        ReturnProfileName = returnProfileName;
    }
}
