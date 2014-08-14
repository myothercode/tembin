package com.base.domains.querypojos;

import com.base.database.trading.model.TradingOrderGetOrders;

/**
 * Created by cz on 2014/7/28.
 */
public class OrderGetOrdersQuery extends TradingOrderGetOrders{

    private Long countNum;

    public Long getCountNum() {
        return countNum;
    }

    public void setCountNum(Long countNum) {
        this.countNum = countNum;
    }

}
