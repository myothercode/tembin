package com.listingitem.controller.ResponseVO;

import com.base.database.trading.model.TradingListingData;

/**
 * Created by Administrator on 2015/1/23.
 */
public class TradingListingDataResponseVO extends TradingListingData {

    private String errMessage;//错误信息

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }
}
