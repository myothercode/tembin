package com.base.xmlpojo.trading.addproduct;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by cz on 2014/7/16.
 */
@XStreamAlias("Charity")
public class Charity {
    private String CharityID;

    private Integer CharityNumber;

    private Float DonationPercent;

    public String getCharityID() {
        return CharityID;
    }

    public void setCharityID(String charityID) {
        CharityID = charityID;
    }

    public Integer getCharityNumber() {
        return CharityNumber;
    }

    public void setCharityNumber(Integer charityNumber) {
        CharityNumber = charityNumber;
    }

    public Float getDonationPercent() {
        return DonationPercent;
    }

    public void setDonationPercent(Float donationPercent) {
        DonationPercent = donationPercent;
    }
}
