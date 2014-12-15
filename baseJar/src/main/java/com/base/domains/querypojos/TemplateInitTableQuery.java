package com.base.domains.querypojos;

import com.base.database.trading.model.TradingTemplateInitTable;


/**
 * Created by cz on 2014/7/28.
 */
public class TemplateInitTableQuery extends TradingTemplateInitTable {
    private String templateTypeName;


    public String getTemplateTypeName() {
        return templateTypeName;
    }

    public void setTemplateTypeName(String templateTypeName) {
        this.templateTypeName = templateTypeName;
    }
}
