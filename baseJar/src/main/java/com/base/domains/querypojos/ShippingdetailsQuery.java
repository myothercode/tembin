package com.base.domains.querypojos;

import com.base.database.trading.model.TradingInternationalshippingserviceoption;
import com.base.database.trading.model.TradingShippingdetails;
import com.base.database.trading.model.TradingShippingserviceoptions;

import java.util.List;

/**
 * Created by Administrtor on 2014/8/1.
 */
public class ShippingdetailsQuery  extends TradingShippingdetails {

    private String siteName;

    private String ebayName;

    private List<TradingShippingserviceoptions> lits;

    private List<TradingInternationalshippingserviceoption> liti;

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

    public List<TradingShippingserviceoptions> getLits() {
        return lits;
    }

    public void setLits(List<TradingShippingserviceoptions> lits) {
        this.lits = lits;
    }

    public List<TradingInternationalshippingserviceoption> getLiti() {
        return liti;
    }

    public void setLiti(List<TradingInternationalshippingserviceoption> liti) {
        this.liti = liti;
    }
}
