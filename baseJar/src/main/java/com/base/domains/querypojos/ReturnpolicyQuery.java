package com.base.domains.querypojos;

import com.base.database.trading.model.TradingReturnpolicy;

/**
 * Created by cz on 2014/7/28.
 */
public class ReturnpolicyQuery extends TradingReturnpolicy{

    private String returnsAcceptedOptionName;

    private String returnsWithinOptionName;

    private String refundOptionName;

    private String shippingCostPaidByOptionName;

    private String siteName;

    private String siteImg;

    public String getSiteImg() {
        return siteImg;
    }

    public void setSiteImg(String siteImg) {
        this.siteImg = siteImg;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getReturnsWithinOptionName() {
        return returnsWithinOptionName;
    }

    public void setReturnsWithinOptionName(String returnsWithinOptionName) {
        this.returnsWithinOptionName = returnsWithinOptionName;
    }

    public String getReturnsAcceptedOptionName() {
        return returnsAcceptedOptionName;
    }

    public void setReturnsAcceptedOptionName(String returnsAcceptedOptionName) {
        this.returnsAcceptedOptionName = returnsAcceptedOptionName;
    }

    public String getRefundOptionName() {
        return refundOptionName;
    }

    public void setRefundOptionName(String refundOptionName) {
        this.refundOptionName = refundOptionName;
    }

    public String getShippingCostPaidByOptionName() {
        return shippingCostPaidByOptionName;
    }

    public void setShippingCostPaidByOptionName(String shippingCostPaidByOptionName) {
        this.shippingCostPaidByOptionName = shippingCostPaidByOptionName;
    }
}
