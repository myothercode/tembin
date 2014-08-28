package com.base.domains.querypojos;

import com.base.database.trading.model.TradingPaypal;

/**
 * Created by cz on 2014/7/28.
 */
public class PaypalQuery extends TradingPaypal{

    private String siteName;

    private String payPalName;

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getPayPalName() {
        return payPalName;
    }

    public void setPayPalName(String payPalName) {
        this.payPalName = payPalName;
    }
}
