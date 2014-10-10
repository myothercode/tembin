package com.base.domains.querypojos;

import com.base.database.trading.model.TradingItem;

import java.util.Date;

public class ItemQuery extends TradingItem{

    private String siteName;

    public String galleryURL;

    public String ebayaccountname;

    public String getEbayaccountname() {
        return ebayaccountname;
    }

    public void setEbayaccountname(String ebayaccountname) {
        this.ebayaccountname = ebayaccountname;
    }

    public String getGalleryURL() {
        return galleryURL;
    }

    public void setGalleryURL(String galleryURL) {
        this.galleryURL = galleryURL;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }
}