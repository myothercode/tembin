package com.base.xmlpojo.trading.addproduct;

import com.base.xmlpojo.trading.addproduct.attrclass.CODCost;
import com.base.xmlpojo.trading.addproduct.attrclass.InsuranceFee;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

/**
 * 运输详情
 * Created by cz on 2014/7/17.
 */
@XStreamAlias("ShippingDetails")
public class ShippingDetails {

    //计算运费率
    private CalculatedShippingRate CalculatedShippingRate;
    //货到付款的费用
    private CODCost CODCost;
    //排除收货地点
    @XStreamImplicit(itemFieldName = "ExcludeShipToLocation")
    private List<String> ExcludeShipToLocation;//该字段未创建，可以通过表ＩＤ或ＵＵＩＤ和字段名称：ExcludeShipToLocation到数据库attr_mores表中查询值
    //是否全球航运
    private Boolean GlobalShipping;
    //保险详情
    private InsuranceDetails InsuranceDetails;
    //保险费用
    private InsuranceFee InsuranceFee;
    //保险选项
    private String InsuranceOption;
    //国际保险详情
    private InternationalInsuranceDetails InternationalInsuranceDetails;
    //国际促销优惠折扣
    private Boolean InternationalPromotionalShippingDiscount;
    //邮费优惠折扣资料编号
    private String InternationalShippingDiscountProfileID;
    //国际运输服务选项
    @XStreamImplicit(itemFieldName = "InternationalShippingServiceOption")
    private List<InternationalShippingServiceOption> InternationalShippingServiceOption;
    //国际支付
    private String PaymentInstructions;
    //促销优惠折扣
    private Boolean PromotionalShippingDiscount;
    //费率表详细
    private RateTableDetails RateTableDetails;
    //营业税
    private SalesTax SalesTax;
    //运输折扣资料编号
    private String ShippingDiscountProfileID;
    //运输服务选项
    @XStreamImplicit(itemFieldName = "ShippingServiceOptions")
    private List<ShippingServiceOptions> ShippingServiceOptions;
    //运输类型
    private String ShippingType;

    public CalculatedShippingRate getCalculatedShippingRate() {
        return CalculatedShippingRate;
    }

    public void setCalculatedShippingRate(CalculatedShippingRate calculatedShippingRate) {
        CalculatedShippingRate = calculatedShippingRate;
    }

    public CODCost getCODCost() {
        return CODCost;
    }

    public void setCODCost(CODCost CODCost) {
        this.CODCost = CODCost;
    }

    public List<String> getExcludeShipToLocation() {
        return ExcludeShipToLocation;
    }

    public void setExcludeShipToLocation(List<String> excludeShipToLocation) {
        ExcludeShipToLocation = excludeShipToLocation;
    }

    public Boolean getGlobalShipping() {
        return GlobalShipping;
    }

    public void setGlobalShipping(Boolean globalShipping) {
        GlobalShipping = globalShipping;
    }

    public InsuranceDetails getInsuranceDetails() {
        return InsuranceDetails;
    }

    public void setInsuranceDetails(InsuranceDetails insuranceDetails) {
        InsuranceDetails = insuranceDetails;
    }

    public InsuranceFee getInsuranceFee() {
        return InsuranceFee;
    }

    public void setInsuranceFee(InsuranceFee insuranceFee) {
        InsuranceFee = insuranceFee;
    }

    public String getInsuranceOption() {
        return InsuranceOption;
    }

    public void setInsuranceOption(String insuranceOption) {
        InsuranceOption = insuranceOption;
    }

    public InternationalInsuranceDetails getInternationalInsuranceDetails() {
        return InternationalInsuranceDetails;
    }

    public void setInternationalInsuranceDetails(InternationalInsuranceDetails internationalInsuranceDetails) {
        InternationalInsuranceDetails = internationalInsuranceDetails;
    }

    public Boolean getInternationalPromotionalShippingDiscount() {
        return InternationalPromotionalShippingDiscount;
    }

    public void setInternationalPromotionalShippingDiscount(Boolean internationalPromotionalShippingDiscount) {
        InternationalPromotionalShippingDiscount = internationalPromotionalShippingDiscount;
    }

    public String getInternationalShippingDiscountProfileID() {
        return InternationalShippingDiscountProfileID;
    }

    public void setInternationalShippingDiscountProfileID(String internationalShippingDiscountProfileID) {
        InternationalShippingDiscountProfileID = internationalShippingDiscountProfileID;
    }

    public List<InternationalShippingServiceOption> getInternationalShippingServiceOption() {
        return InternationalShippingServiceOption;
    }

    public void setInternationalShippingServiceOption(List<InternationalShippingServiceOption> internationalShippingServiceOption) {
        InternationalShippingServiceOption = internationalShippingServiceOption;
    }

    public String getPaymentInstructions() {
        return PaymentInstructions;
    }

    public void setPaymentInstructions(String paymentInstructions) {
        PaymentInstructions = paymentInstructions;
    }

    public Boolean getPromotionalShippingDiscount() {
        return PromotionalShippingDiscount;
    }

    public void setPromotionalShippingDiscount(Boolean promotionalShippingDiscount) {
        PromotionalShippingDiscount = promotionalShippingDiscount;
    }

    public RateTableDetails getRateTableDetails() {
        return RateTableDetails;
    }

    public void setRateTableDetails(RateTableDetails rateTableDetails) {
        RateTableDetails = rateTableDetails;
    }

    public SalesTax getSalesTax() {
        return SalesTax;
    }

    public void setSalesTax(SalesTax salesTax) {
        SalesTax = salesTax;
    }

    public String getShippingDiscountProfileID() {
        return ShippingDiscountProfileID;
    }

    public void setShippingDiscountProfileID(String shippingDiscountProfileID) {
        ShippingDiscountProfileID = shippingDiscountProfileID;
    }

    public List<ShippingServiceOptions> getShippingServiceOptions() {
        return ShippingServiceOptions;
    }

    public void setShippingServiceOptions(List<ShippingServiceOptions> shippingServiceOptions) {
        ShippingServiceOptions = shippingServiceOptions;
    }

    public String getShippingType() {
        return ShippingType;
    }

    public void setShippingType(String shippingType) {
        ShippingType = shippingType;
    }
}
