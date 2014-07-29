package com.base.xmlpojo.trading.addproduct;

import com.base.xmlpojo.trading.addproduct.attrclass.ShippingServiceAdditionalCost;
import com.base.xmlpojo.trading.addproduct.attrclass.ShippingServiceCost;
import com.base.xmlpojo.trading.addproduct.attrclass.ShippingSurcharge;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 代理运输服务费
 * Created by cz on 2014/7/17.
 */
@XStreamAlias("ShippingServiceCostOverride")
public class ShippingServiceCostOverride {
    /**
     * 运输服务附加费
     */
    private ShippingServiceAdditionalCost ShippingServiceAdditionalCost;
    /**
     * 运输服务费
     */
    private ShippingServiceCost ShippingServiceCost;
    /**
     * 运输服务优先级
     */
    private Integer ShippingServicePriority;
    /**
     * 运输服务类型
     */
    private String ShippingServiceType;
    /**
     * 运输服务超载费
     */
    private ShippingSurcharge ShippingSurcharge;

    public ShippingServiceAdditionalCost getShippingServiceAdditionalCost() {
        return ShippingServiceAdditionalCost;
    }

    public void setShippingServiceAdditionalCost(ShippingServiceAdditionalCost shippingServiceAdditionalCost) {
        ShippingServiceAdditionalCost = shippingServiceAdditionalCost;
    }

    public ShippingServiceCost getShippingServiceCost() {
        return ShippingServiceCost;
    }

    public void setShippingServiceCost(ShippingServiceCost shippingServiceCost) {
        ShippingServiceCost = shippingServiceCost;
    }

    public Integer getShippingServicePriority() {
        return ShippingServicePriority;
    }

    public void setShippingServicePriority(Integer shippingServicePriority) {
        ShippingServicePriority = shippingServicePriority;
    }

    public String getShippingServiceType() {
        return ShippingServiceType;
    }

    public void setShippingServiceType(String shippingServiceType) {
        ShippingServiceType = shippingServiceType;
    }

    public ShippingSurcharge getShippingSurcharge() {
        return ShippingSurcharge;
    }

    public void setShippingSurcharge(ShippingSurcharge shippingSurcharge) {
        ShippingSurcharge = shippingSurcharge;
    }
}

