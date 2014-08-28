package com.base.xmlpojo.trading.addproduct;

import com.base.xmlpojo.trading.addproduct.attrclass.PackagingHandlingCosts;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 包装信息
 * Created by cz on 2014/7/17.
 * todo 暂时可能不需要MeasureType属性
 */
@XStreamAlias("CalculatedShippingRate")
public class CalculatedShippingRate {
    /**
     * 计量单位
     */
    private String MeasurementUnit;
    /**
     * 发送地邮编
     */
    private String OriginatingPostalCode;
    /**
     * 包裹深度
     */
    private double PackageDepth;
    /**
     * 包裹长度
     */
    private double PackageLength;
    /**
     * 包裹宽度
     */
    private double PackageWidth;
    /**
     * 包装价格
     */
    private PackagingHandlingCosts PackagingHandlingCosts;
    /**
     * 是否不规则运输
     */
    private boolean ShippingIrregular;
    /**
     * 运送方式
     */
    private String ShippingPackage;
    /**
     * 主要重量
     */
    private double WeightMajor;
    /**
     * 次要重量
     */
    private double WeightMinor;

    public String getMeasurementUnit() {
        return MeasurementUnit;
    }

    public void setMeasurementUnit(String measurementUnit) {
        MeasurementUnit = measurementUnit;
    }

    public String getOriginatingPostalCode() {
        return OriginatingPostalCode;
    }

    public void setOriginatingPostalCode(String originatingPostalCode) {
        OriginatingPostalCode = originatingPostalCode;
    }

    public double getPackageDepth() {
        return PackageDepth;
    }

    public void setPackageDepth(double packageDepth) {
        PackageDepth = packageDepth;
    }

    public double getPackageLength() {
        return PackageLength;
    }

    public void setPackageLength(double packageLength) {
        PackageLength = packageLength;
    }

    public double getPackageWidth() {
        return PackageWidth;
    }

    public void setPackageWidth(double packageWidth) {
        PackageWidth = packageWidth;
    }

    public PackagingHandlingCosts getPackagingHandlingCosts() {
        return PackagingHandlingCosts;
    }

    public void setPackagingHandlingCosts(PackagingHandlingCosts packagingHandlingCosts) {
        PackagingHandlingCosts = packagingHandlingCosts;
    }

    public boolean getShippingIrregular() {
        return ShippingIrregular;
    }

    public void setShippingIrregular(boolean shippingIrregular) {
        ShippingIrregular = shippingIrregular;
    }

    public String getShippingPackage() {
        return ShippingPackage;
    }

    public void setShippingPackage(String shippingPackage) {
        ShippingPackage = shippingPackage;
    }

    public double getWeightMajor() {
        return WeightMajor;
    }

    public void setWeightMajor(double weightMajor) {
        WeightMajor = weightMajor;
    }

    public double getWeightMinor() {
        return WeightMinor;
    }

    public void setWeightMinor(double weightMinor) {
        WeightMinor = weightMinor;
    }
}
