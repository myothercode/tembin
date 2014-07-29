package com.base.xmlpojo.trading.addproduct;

import com.base.xmlpojo.trading.addproduct.attrclass.StartPrice;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

/**
 * Created by Administrtor on 2014/7/17.
 */
@XStreamAlias("Variation")
public class Variation {
    /*
    * 增值税详情
    */
    private DiscountPriceInfo DiscountPriceInfo;
    /*
    * 数量
    */
    private Integer Quantity;
    /*
* 起始价格
*/
    private StartPrice StartPrice;
    /*
* SKU
*/
    private String SKU;
    /*
    *细节变化
     */
    @XStreamImplicit(itemFieldName = "VariationSpecifics")
    private List<VariationSpecifics> VariationSpecifics;

    public DiscountPriceInfo getDiscountPriceInfo() {
        return DiscountPriceInfo;
    }

    public void setDiscountPriceInfo(DiscountPriceInfo discountPriceInfo) {
        DiscountPriceInfo = discountPriceInfo;
    }

    public Integer getQuantity() {
        return Quantity;
    }

    public void setQuantity(Integer quantity) {
        Quantity = quantity;
    }

    public StartPrice getStartPrice() {
        return StartPrice;
    }

    public void setStartPrice(StartPrice startPrice) {
        StartPrice = startPrice;
    }

    public String getSKU() {
        return SKU;
    }

    public void setSKU(String SKU) {
        this.SKU = SKU;
    }

    public List<VariationSpecifics> getVariationSpecifics() {
        return VariationSpecifics;
    }

    public void setVariationSpecifics(List<VariationSpecifics> variationSpecifics) {
        VariationSpecifics = variationSpecifics;
    }
}
