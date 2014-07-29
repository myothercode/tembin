package com.base.xmlpojo.trading.addproduct;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 买家拍后未付款次数
 * Created by cz on 2014/7/16.
 */
@XStreamAlias("MaximumItemRequirements")
public class MaximumItemRequirements {
    /**
     * 最多几次未付款
     */
    private Integer MaximumItemCount;
    /**
     * 买家积份最少是
     */
    private Integer MinimumFeedbackScore;

    public Integer getMinimumFeedbackScore() {
        return MinimumFeedbackScore;
    }

    public void setMinimumFeedbackScore(Integer minimumFeedbackScore) {
        MinimumFeedbackScore = minimumFeedbackScore;
    }

    public Integer getMaximumItemCount() {
        return MaximumItemCount;
    }

    public void setMaximumItemCount(Integer maximumItemCount) {
        MaximumItemCount = maximumItemCount;
    }
}
