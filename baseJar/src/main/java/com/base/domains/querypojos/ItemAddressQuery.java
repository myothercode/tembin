package com.base.domains.querypojos;

import com.base.database.trading.model.TradingItemAddress;

/**
 * Created by cz on 2014/7/28.
 */
public class ItemAddressQuery extends TradingItemAddress{
    private String countryName;

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
