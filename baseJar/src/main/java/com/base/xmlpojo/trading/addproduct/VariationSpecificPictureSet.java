package com.base.xmlpojo.trading.addproduct;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

/**
 * 变异特定图片集
 * Created by Administrtor on 2014/7/17.
 */
@XStreamAlias("VariationSpecificPictureSet")
public class VariationSpecificPictureSet {
    @XStreamImplicit(itemFieldName = "PictureURL")
    /*
    * 图片URL
    * */
    private List<String> PictureURL;
    /*
    *变化的具体数值
    * */
    private String VariationSpecificValue;

    public List<String> getPictureURL() {
        return PictureURL;
    }

    public void setPictureURL(List<String> pictureURL) {
        PictureURL = pictureURL;
    }

    public String getVariationSpecificValue() {
        return VariationSpecificValue;
    }

    public void setVariationSpecificValue(String variationSpecificValue) {
        VariationSpecificValue = variationSpecificValue;
    }
}
