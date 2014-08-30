package com.module.controller;

import com.base.database.trading.model.TradingDataDictionary;

import java.util.List;

/**
 * Created by Administrtor on 2014/8/30.
 */
public class ShippingDetailsSSVO {

    private List<TradingDataDictionary> dictionaries;
    private String name1;

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public List<TradingDataDictionary> getDictionaries() {
        return dictionaries;
    }

    public void setDictionaries(List<TradingDataDictionary> dictionaries) {
        this.dictionaries = dictionaries;
    }
}
