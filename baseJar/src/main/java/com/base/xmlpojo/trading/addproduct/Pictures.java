package com.base.xmlpojo.trading.addproduct;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

/**
 * 多属性图片
 * Created by Administrtor on 2014/7/17.
 */
@XStreamAlias("Pictures")
public class Pictures {
    private String VariationSpecificName;
    @XStreamImplicit(itemFieldName = "VariationSpecificPictureSet")
    private List<VariationSpecificPictureSet> VariationSpecificPictureSet;

    public List<VariationSpecificPictureSet> getVariationSpecificPictureSet() {
        return VariationSpecificPictureSet;
    }

    public void setVariationSpecificPictureSet(List<VariationSpecificPictureSet> variationSpecificPictureSet) {
        VariationSpecificPictureSet = variationSpecificPictureSet;
    }

    public String getVariationSpecificName() {
        return VariationSpecificName;
    }

    public void setVariationSpecificName(String variationSpecificName) {
        VariationSpecificName = variationSpecificName;
    }


}
