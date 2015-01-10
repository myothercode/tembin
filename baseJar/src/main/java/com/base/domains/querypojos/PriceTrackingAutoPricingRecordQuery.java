package com.base.domains.querypojos;


import com.base.database.trading.model.TradingPriceTrackingAutoPricingRecord;

/**
 * Created by cz on 2014/7/28.
 */
public class PriceTrackingAutoPricingRecordQuery extends TradingPriceTrackingAutoPricingRecord{
    private  String sku;
    private Long listingDateId;

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Long getListingDateId() {
        return listingDateId;
    }

    public void setListingDateId(Long listingDateId) {
        this.listingDateId = listingDateId;
    }
}
