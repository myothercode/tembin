package com.base.xmlpojo.trading.addproduct;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by cz on 2014/7/16.
 */
@XStreamAlias("VerifiedUserRequirements")
public class VerifiedUserRequirements {

    /**
     * 买家用户最少用户积分
     */
    private int MinimumFeedbackScore;
    /**
     * 是否是认证用户
     */
    private boolean VerifiedUser;

    public int getMinimumFeedbackScore() {
        return MinimumFeedbackScore;
    }

    public void setMinimumFeedbackScore(int minimumFeedbackScore) {
        MinimumFeedbackScore = minimumFeedbackScore;
    }

    public boolean getVerifiedUser() {
        return VerifiedUser;
    }

    public void setVerifiedUser(boolean verifiedUser) {
        VerifiedUser = verifiedUser;
    }
}
