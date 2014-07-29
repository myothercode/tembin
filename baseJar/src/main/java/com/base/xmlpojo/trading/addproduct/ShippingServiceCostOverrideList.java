package com.base.xmlpojo.trading.addproduct;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

/**
 * 代理运输服务费清单
 * Created by Administrtor on 2014/7/17.
 */
@XStreamAlias("ShippingServiceCostOverrideList")
public class ShippingServiceCostOverrideList {

    @XStreamImplicit(itemFieldName = "ShippingServiceCostOverride")
    private List<ShippingServiceCostOverride> ShippingServiceCostOverride;

    public List<ShippingServiceCostOverride> getShippingServiceCostOverride() {
        return ShippingServiceCostOverride;
    }

    public void setShippingServiceCostOverride(List<ShippingServiceCostOverride> shippingServiceCostOverride) {
        ShippingServiceCostOverride = shippingServiceCostOverride;
    }
}
