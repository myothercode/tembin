package com.base.domains.querypojos;

import com.base.database.trading.model.TradingAutoMessage;

import java.util.List;

/**
 * Created by cz on 2014/7/28.
 */
public class AutoMessageQuery extends TradingAutoMessage{
    private List<String> countrys;
    private List<String> ebayNames;

    public List<String> getCountrys() {
        return countrys;
    }

    public void setCountrys(List<String> countrys) {
        this.countrys = countrys;
    }

    public List<String> getEbayNames() {
        return ebayNames;
    }

    public void setEbayNames(List<String> ebayNames) {
        this.ebayNames = ebayNames;
    }
}
