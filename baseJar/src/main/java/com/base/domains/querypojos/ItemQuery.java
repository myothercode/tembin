package com.base.domains.querypojos;

import com.base.database.trading.model.TradingItem;
import com.base.database.userinfo.model.SystemLog;

import java.util.Date;

public class ItemQuery extends TradingItem{

    private String siteName;

    public String galleryURL;

    public String ebayaccountname;

    private String siteImg;

    private String currencyId;

    private String remark;

    private Date defaultTimer;

    private SystemLog sl;

    public SystemLog getSl() {
        return sl;
    }

    public void setSl(SystemLog sl) {
        this.sl = sl;
    }

    public Date getDefaultTimer() {
        return defaultTimer;
    }

    public void setDefaultTimer(Date defaultTimer) {
        this.defaultTimer = defaultTimer;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public String getSiteImg() {
        return siteImg;
    }

    public void setSiteImg(String siteImg) {
        this.siteImg = siteImg;
    }

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