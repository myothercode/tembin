package com.base.domains.querypojos;

import com.base.database.trading.model.TradingItem;
import com.base.database.trading.model.TradingSetComplement;

import java.util.Date;

public class SetComplementQuery extends TradingSetComplement{

    private String createUserName;

    private String updateUserName;

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }
}