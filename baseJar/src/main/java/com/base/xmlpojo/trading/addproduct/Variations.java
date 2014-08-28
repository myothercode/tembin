package com.base.xmlpojo.trading.addproduct;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

/**
 * Created by Administrtor on 2014/7/17.
 */
@XStreamAlias("Variations")
public class Variations {
    private Pictures Pictures;
    @XStreamImplicit(itemFieldName = "Variation")
    private List<Variation> Variation;
    private VariationSpecificsSet VariationSpecificsSet;
    public Pictures getPictures() {
        return Pictures;
    }

    public void setPictures(Pictures pictures) {
        Pictures = pictures;
    }

    public List<Variation> getVariation() {
        return Variation;
    }

    public void setVariation(List<Variation> variation) {
        Variation = variation;
    }

    public VariationSpecificsSet getVariationSpecificsSet() {
        return VariationSpecificsSet;
    }

    public void setVariationSpecificsSet(VariationSpecificsSet variationSpecificsSet) {
        VariationSpecificsSet = variationSpecificsSet;
    }



}
