package com.base.xmlpojo.trading.addproduct;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by Administrtor on 2014/8/22.
 */
@XStreamAlias("Seller")
public class Seller {
    private String AboutMePage;
    private String Email;
    private String FeedbackScore;
    private String PositiveFeedbackPercent;
    private String FeedbackPrivate;
    private String FeedbackRatingStar;
    private String IDVerified;
    private String eBayGoodStanding;
    private String NewUser;
    private String RegistrationDate;
    private String Site;
    private String Status;
    private String UserID;
    private String UserIDChanged;
    private String UserIDLastChanged;
    private String VATStatus;

    public String getAboutMePage() {
        return AboutMePage;
    }

    public void setAboutMePage(String aboutMePage) {
        AboutMePage = aboutMePage;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getFeedbackScore() {
        return FeedbackScore;
    }

    public void setFeedbackScore(String feedbackScore) {
        FeedbackScore = feedbackScore;
    }

    public String getPositiveFeedbackPercent() {
        return PositiveFeedbackPercent;
    }

    public void setPositiveFeedbackPercent(String positiveFeedbackPercent) {
        PositiveFeedbackPercent = positiveFeedbackPercent;
    }

    public String getFeedbackPrivate() {
        return FeedbackPrivate;
    }

    public void setFeedbackPrivate(String feedbackPrivate) {
        FeedbackPrivate = feedbackPrivate;
    }

    public String getFeedbackRatingStar() {
        return FeedbackRatingStar;
    }

    public void setFeedbackRatingStar(String feedbackRatingStar) {
        FeedbackRatingStar = feedbackRatingStar;
    }

    public String getIDVerified() {
        return IDVerified;
    }

    public void setIDVerified(String IDVerified) {
        this.IDVerified = IDVerified;
    }

    public String geteBayGoodStanding() {
        return eBayGoodStanding;
    }

    public void seteBayGoodStanding(String eBayGoodStanding) {
        this.eBayGoodStanding = eBayGoodStanding;
    }

    public String getNewUser() {
        return NewUser;
    }

    public void setNewUser(String newUser) {
        NewUser = newUser;
    }

    public String getRegistrationDate() {
        return RegistrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        RegistrationDate = registrationDate;
    }

    public String getSite() {
        return Site;
    }

    public void setSite(String site) {
        Site = site;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getUserIDChanged() {
        return UserIDChanged;
    }

    public void setUserIDChanged(String userIDChanged) {
        UserIDChanged = userIDChanged;
    }

    public String getUserIDLastChanged() {
        return UserIDLastChanged;
    }

    public void setUserIDLastChanged(String userIDLastChanged) {
        UserIDLastChanged = userIDLastChanged;
    }

    public String getVATStatus() {
        return VATStatus;
    }

    public void setVATStatus(String VATStatus) {
        this.VATStatus = VATStatus;
    }
}
