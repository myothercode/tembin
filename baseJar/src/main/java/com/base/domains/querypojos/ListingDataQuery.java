package com.base.domains.querypojos;


import com.base.database.trading.model.TradingListingData;

public class ListingDataQuery extends TradingListingData{


    private String docTitle;

    private String docId;

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getDocTitle() {
        return docTitle;
    }

    public void setDocTitle(String docTitle) {
        this.docTitle = docTitle;
    }
}