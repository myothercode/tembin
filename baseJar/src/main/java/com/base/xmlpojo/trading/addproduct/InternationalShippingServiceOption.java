package com.base.xmlpojo.trading.addproduct;

import com.base.xmlpojo.trading.addproduct.attrclass.ShippingServiceAdditionalCost;
import com.base.xmlpojo.trading.addproduct.attrclass.ShippingServiceCost;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

/**
 * 国际运输服务选项
 * Created by cz on 2014/7/17.
 */
@XStreamAlias("InternationalShippingServiceOption")
public class InternationalShippingServiceOption {
    /**
     * 运输服务
     */
    private String ShippingService;
    /**
     * 运输服务附加费
     */
    private ShippingServiceAdditionalCost ShippingServiceAdditionalCost;
    /**
     * 运输服务费
     */
    private ShippingServiceCost ShippingServiceCost;
    /**
     * 运输服务等级
     */
    private Integer ShippingServicePriority;
    /**
     * 运输地址
     */
    @XStreamImplicit(itemFieldName = "ShipToLocation")
    private List<String> ShipToLocation;

    public String getShippingService() {
        return ShippingService;
    }

    public void setShippingService(String shippingService) {
        ShippingService = shippingService;
    }

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

    public List<String> getShipToLocation() {
        return ShipToLocation;
    }

    public void setShipToLocation(List<String> shipToLocation) {
        ShipToLocation = shipToLocation;
    }
}


