package com.base.domains.querypojos;

import com.base.database.trading.model.TradingItem;

import java.util.Date;

public class ItemQuery extends TradingItem{

    private String siteName;

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }
}