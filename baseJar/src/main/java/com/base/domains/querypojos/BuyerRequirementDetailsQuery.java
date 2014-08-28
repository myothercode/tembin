package com.base.domains.querypojos;

import com.base.database.trading.model.TradingBuyerRequirementDetails;

/**
 * 用于查询买家要求列表
 * Created by Administrtor on 2014/7/28.
 */
public class BuyerRequirementDetailsQuery extends TradingBuyerRequirementDetails {

    /**
     * 站点名称
     */
    private String siteName;

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }
}
