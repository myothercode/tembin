package com.base.xmlpojo.trading.addproduct;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 买家要求
 * Created by Administrtor on 2014/7/16.
 */
@XStreamAlias("BuyerRequirementDetails")
public class BuyerRequirementDetails {
    /**
     * 是否连接paypal账号
     */
    private Boolean LinkedPayPalAccount;

    private MaximumBuyerPolicyViolations MaximumBuyerPolicyViolations;

    private MaximumItemRequirements MaximumItemRequirements;

    private MaximumUnpaidItemStrikesInfo MaximumUnpaidItemStrikesInfo;
    /**
     * 最少积分
     */
    private Integer MinimumFeedbackScore;
    /**
     * 是否是注册地区
     */
    private Boolean ShipToRegistrationCountry;

    private VerifiedUserRequirements VerifiedUserRequirements;
    /**
     * 是否是０积分
     */
    private Boolean ZeroFeedbackScore;

    public Boolean getLinkedPayPalAccount() {
        return LinkedPayPalAccount;
    }

    public void setLinkedPayPalAccount(Boolean linkedPayPalAccount) {
        LinkedPayPalAccount = linkedPayPalAccount;
    }

    public MaximumBuyerPolicyViolations getMaximumBuyerPolicyViolations() {
        return MaximumBuyerPolicyViolations;
    }

    public void setMaximumBuyerPolicyViolations(MaximumBuyerPolicyViolations maximumBuyerPolicyViolations) {
        MaximumBuyerPolicyViolations = maximumBuyerPolicyViolations;
    }

    public MaximumItemRequirements getMaximumItemRequirements() {
        return MaximumItemRequirements;
    }

    public void setMaximumItemRequirements(MaximumItemRequirements maximumItemRequirements) {
        MaximumItemRequirements = maximumItemRequirements;
    }

    public MaximumUnpaidItemStrikesInfo getMaximumUnpaidItemStrikesInfo() {
        return MaximumUnpaidItemStrikesInfo;
    }

    public void setMaximumUnpaidItemStrikesInfo(MaximumUnpaidItemStrikesInfo maximumUnpaidItemStrikesInfo) {
        MaximumUnpaidItemStrikesInfo = maximumUnpaidItemStrikesInfo;
    }

    public Integer getMinimumFeedbackScore() {
        return MinimumFeedbackScore;
    }

    public void setMinimumFeedbackScore(Integer minimumFeedbackScore) {
        MinimumFeedbackScore = minimumFeedbackScore;
    }

    public Boolean getShipToRegistrationCountry() {
        return ShipToRegistrationCountry;
    }

    public void setShipToRegistrationCountry(Boolean shipToRegistrationCountry) {
        ShipToRegistrationCountry = shipToRegistrationCountry;
    }

    public VerifiedUserRequirements getVerifiedUserRequirements() {
        return VerifiedUserRequirements;
    }

    public void setVerifiedUserRequirements(VerifiedUserRequirements verifiedUserRequirements) {
        VerifiedUserRequirements = verifiedUserRequirements;
    }

    public Boolean getZeroFeedbackScore() {
        return ZeroFeedbackScore;
    }

    public void setZeroFeedbackScore(Boolean zeroFeedbackScore) {
        ZeroFeedbackScore = zeroFeedbackScore;
    }
}
