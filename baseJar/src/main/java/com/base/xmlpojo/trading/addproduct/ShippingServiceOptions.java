package com.base.xmlpojo.trading.addproduct;

import com.base.utils.xmlutils.PojoXmlUtil;
import com.base.xmlpojo.trading.addproduct.attrclass.ShippingServiceAdditionalCost;
import com.base.xmlpojo.trading.addproduct.attrclass.ShippingServiceCost;
import com.base.xmlpojo.trading.addproduct.attrclass.ShippingSurcharge;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import java.lang.annotation.Target;

/**
 * 运送服务配置
 * Created by cz on 2014/7/16.
 */
@XStreamAlias("ShippingServiceOptions")
public class ShippingServiceOptions {

    /**
     *装运服务等级
     */
    private Integer ShippingServicePriority;
    /**
     * 装运服务
     */
    private String ShippingService;
    /**
     *运费
     */
    private ShippingServiceCost ShippingServiceCost;
    /**
     *运费附加费
     */
    private ShippingServiceAdditionalCost ShippingServiceAdditionalCost;
    /**
     *是否免费运送
     */
    private Boolean FreeShipping;
    /**
     * 运输超载费
     */
    private ShippingSurcharge ShippingSurcharge;


    public Integer getShippingServicePriority() {
        return ShippingServicePriority;
    }

    public void setShippingServicePriority(Integer shippingServicePriority) {
        ShippingServicePriority = shippingServicePriority;
    }

    public String getShippingService() {
        return ShippingService;
    }

    public void setShippingService(String shippingService) {
        ShippingService = shippingService;
    }

    public ShippingServiceCost getShippingServiceCost() {
        return ShippingServiceCost;
    }

    public void setShippingServiceCost(ShippingServiceCost shippingServiceCost) {
        ShippingServiceCost = shippingServiceCost;
    }

    public ShippingServiceAdditionalCost getShippingServiceAdditionalCost() {
        return ShippingServiceAdditionalCost;
    }

    public void setShippingServiceAdditionalCost(ShippingServiceAdditionalCost shippingServiceAdditionalCost) {
        ShippingServiceAdditionalCost = shippingServiceAdditionalCost;
    }

    public Boolean getFreeShipping() {
        return FreeShipping;
    }

    public void setFreeShipping(Boolean freeShipping) {
        FreeShipping = freeShipping;
    }

    public ShippingSurcharge getShippingSurcharge() {
        return ShippingSurcharge;
    }

    public void setShippingSurcharge(ShippingSurcharge shippingSurcharge) {
        ShippingSurcharge = shippingSurcharge;
    }
}
