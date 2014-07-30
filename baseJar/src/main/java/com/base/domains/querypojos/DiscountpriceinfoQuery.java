package com.base.domains.querypojos;

import com.base.database.trading.model.TradingDiscountpriceinfo;

/**
 * Created by cz on 2014/7/29.
 */

public class DiscountpriceinfoQuery extends TradingDiscountpriceinfo {
    private String ebayName;

    public String getEbayName() {
        return ebayName;
    }

    public void setEbayName(String ebayName) {
        this.ebayName = ebayName;
    }
}
