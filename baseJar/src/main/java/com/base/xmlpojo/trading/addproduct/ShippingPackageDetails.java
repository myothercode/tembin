package com.base.xmlpojo.trading.addproduct;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 运输包装明细
 * Created by cz on 2014/7/17.
 */
@XStreamAlias("ShippingPackageDetails")
public class ShippingPackageDetails {
    /**
     * 计量单位
     */
    private String MeasurementUnit;
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
     * 包裹是否不规则
     */
    private boolean ShippingIrregular;
    /**
     * 运送包裹
     */
    private String ShippingPackage;
    /**
     * 尽重
     */
    private double WeightMajor;
    /**
     * 附加重量
     */
    private double WeightMinor;

    public String getMeasurementUnit() {
        return MeasurementUnit;
    }

    public void setMeasurementUnit(String measurementUnit) {
        MeasurementUnit = measurementUnit;
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

