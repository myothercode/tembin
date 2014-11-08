package com.base.domains.querypojos;

import com.base.database.trading.model.TradingPaypal;

/**
 * Created by cz on 2014/7/28.
 */
public class BaseTjReportQuery{

    private String returnType;

    private int tjNumber;

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public int getTjNumber() {
        return tjNumber;
    }

    public void setTjNumber(int tjNumber) {
        this.tjNumber = tjNumber;
    }
}
