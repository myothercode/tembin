package com.base.aboutpaypal.domain;

/**
 * Created by Administrator on 2014/9/13.
 * 关于paypal操作的vo
 */
public class PaypalVO {
    private String balance;//余额
    private String unitName;//单位名名称
    private String transactionID;//交易id，用于查询指定的交易详情
    private String ack;//支付状态
    private String paymentType;//支付方式

    private String grossAmount;//交易的总花费
    private String grossAmountUnit;//交易的总花费
    private String feeAmount;//手续费
    private String feeAmountUnit;//手续费
    private String taxAmount;//税费
    private String taxAmountUnit;//税费


    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getAck() {
        return ack;
    }

    public void setAck(String ack) {
        this.ack = ack;
    }

    public String getGrossAmount() {
        return grossAmount;
    }

    public void setGrossAmount(String grossAmount) {
        this.grossAmount = grossAmount;
    }

    public String getGrossAmountUnit() {
        return grossAmountUnit;
    }

    public void setGrossAmountUnit(String grossAmountUnit) {
        this.grossAmountUnit = grossAmountUnit;
    }

    public String getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(String feeAmount) {
        this.feeAmount = feeAmount;
    }

    public String getFeeAmountUnit() {
        return feeAmountUnit;
    }

    public void setFeeAmountUnit(String feeAmountUnit) {
        this.feeAmountUnit = feeAmountUnit;
    }

    public String getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(String taxAmount) {
        this.taxAmount = taxAmount;
    }

    public String getTaxAmountUnit() {
        return taxAmountUnit;
    }

    public void setTaxAmountUnit(String taxAmountUnit) {
        this.taxAmountUnit = taxAmountUnit;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }
}
