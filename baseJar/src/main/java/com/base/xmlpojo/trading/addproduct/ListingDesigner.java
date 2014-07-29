package com.base.xmlpojo.trading.addproduct;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 设计商品展示
 * Created by cz on 2014/7/17.
 */
@XStreamAlias("ListingDesigner")
public class ListingDesigner {
    /**
     * 模板ＩＤ
     */
    private Integer LayoutID;
    /**
     *是否启用最佳图片大小
     */
    private Boolean OptimalPictureSize;
    /**
     *主题ＩＤ
     */
    private Integer ThemeID;

    public Integer getLayoutID() {
        return LayoutID;
    }

    public void setLayoutID(Integer layoutID) {
        LayoutID = layoutID;
    }

    public Boolean getOptimalPictureSize() {
        return OptimalPictureSize;
    }

    public void setOptimalPictureSize(Boolean optimalPictureSize) {
        OptimalPictureSize = optimalPictureSize;
    }

    public Integer getThemeID() {
        return ThemeID;
    }

    public void setThemeID(Integer themeID) {
        ThemeID = themeID;
    }
}
