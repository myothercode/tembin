package com.base.xmlpojo.trading.addproduct;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 *增值税详情
 * Created by Administrtor on 2014/7/17.
 */
@XStreamAlias("VATDetails")
public class VATDetails {
    /*
    *是否企业卖家
     */
    private Boolean BusinessSeller;
    /*
    *是否限用于商业
     */
    private Boolean RestrictedToBusiness;
    /*
    *增值税百分比
     */
    private Float VATPercent;

    public Boolean getBusinessSeller() {
        return BusinessSeller;
    }

    public void setBusinessSeller(Boolean businessSeller) {
        BusinessSeller = businessSeller;
    }

    public Boolean getRestrictedToBusiness() {
        return RestrictedToBusiness;
    }

    public void setRestrictedToBusiness(Boolean restrictedToBusiness) {
        RestrictedToBusiness = restrictedToBusiness;
    }

    public Float getVATPercent() {
        return VATPercent;
    }

    public void setVATPercent(Float VATPercent) {
        this.VATPercent = VATPercent;
    }
}
