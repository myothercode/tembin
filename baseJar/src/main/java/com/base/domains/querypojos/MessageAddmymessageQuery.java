package com.base.domains.querypojos;

import com.base.database.trading.model.TradingMessageAddmembermessage;

/**
 * Created by cz on 2014/7/28.
 */
public class MessageAddmymessageQuery extends TradingMessageAddmembermessage{
    private Long countNum;

    public Long getCountNum() {
        return countNum;
    }

    public void setCountNum(Long countNum) {
        this.countNum = countNum;
    }
}
