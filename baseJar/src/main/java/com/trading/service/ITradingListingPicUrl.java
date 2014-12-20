package com.trading.service;

import com.base.database.trading.model.TradingItem;
import com.base.database.trading.model.TradingListingpicUrl;

import java.util.List;

/**
 * Created by Administrtor on 2014/10/23.
 */
public interface ITradingListingPicUrl {
    void saveListingPicUrl(TradingListingpicUrl tradingListingpicUrl) throws Exception;

    List<TradingListingpicUrl> selectByMackId(String mackId);

    TradingListingpicUrl uploadPic(TradingItem tradingItem, String url, String picName, TradingListingpicUrl tlu) throws Exception;
}
