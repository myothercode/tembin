package com.base.domains.querypojos;

import com.base.database.trading.model.TradingShippingdetails;

/**
 * Created by Administrtor on 2014/8/1.
 */
public class ShippingdetailsQuery  extends TradingShippingdetails{

    private String siteName;

    private String ebayName;

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getEbayName() {
        return ebayName;
    }

    public void setEbayName(String ebayName) {
        this.ebayName = ebayName;
    }
}
