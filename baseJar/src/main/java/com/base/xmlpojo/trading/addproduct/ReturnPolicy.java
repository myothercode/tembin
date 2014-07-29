package com.base.xmlpojo.trading.addproduct;
import com.base.utils.xmlutils.PojoXmlUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
/**
 * 退货政策模块
 * Created by  cz on 2014/7/16.
 */
@XStreamAlias("ReturnPolicy")
public class ReturnPolicy {
    /**
     *是否允许退货
     */
    private String ReturnsAcceptedOption;
    /**
     *是否退款
     */
    private String RefundOption;
    /**
     *退货天数
     */
    private String ReturnsWithinOption;
    /**
     *退货描述
     */
    private String Description;
    /**
     *指定那方支付退货运费
     */
    private String ShippingCostPaidByOption;

    /**
     * 欧洲物品号
     */
    private String EAN;
    /**
     * 节假日是否允计退货
     */
    private Boolean ExtendedHolidayReturns;
    /**
     * 补充库存费用选项
     */
    private String RestockingFeeValueOption;
    /**
     * 保证持续选项
     */
    private String WarrantyDurationOption;
    /**
     * 保证团购选项
     */
    private String WarrantyOfferedOption;
    /**
     * 保证类型选项
     */
    private String WarrantyTypeOption;

    public String getReturnsAcceptedOption() {
        return ReturnsAcceptedOption;
    }

    public void setReturnsAcceptedOption(String returnsAcceptedOption) {
        ReturnsAcceptedOption = returnsAcceptedOption;
    }

    public String getRefundOption() {
        return RefundOption;
    }

    public void setRefundOption(String refundOption) {
        RefundOption = refundOption;
    }

    public String getReturnsWithinOption() {
        return ReturnsWithinOption;
    }

    public void setReturnsWithinOption(String returnsWithinOption) {
        ReturnsWithinOption = returnsWithinOption;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getShippingCostPaidByOption() {
        return ShippingCostPaidByOption;
    }

    public void setShippingCostPaidByOption(String shippingCostPaidByOption) {
        ShippingCostPaidByOption = shippingCostPaidByOption;
    }

    public String getEAN() {
        return EAN;
    }

    public void setEAN(String EAN) {
        this.EAN = EAN;
    }

    public Boolean getExtendedHolidayReturns() {
        return ExtendedHolidayReturns;
    }

    public void setExtendedHolidayReturns(Boolean extendedHolidayReturns) {
        ExtendedHolidayReturns = extendedHolidayReturns;
    }

    public String getRestockingFeeValueOption() {
        return RestockingFeeValueOption;
    }

    public void setRestockingFeeValueOption(String restockingFeeValueOption) {
        RestockingFeeValueOption = restockingFeeValueOption;
    }

    public String getWarrantyDurationOption() {
        return WarrantyDurationOption;
    }

    public void setWarrantyDurationOption(String warrantyDurationOption) {
        WarrantyDurationOption = warrantyDurationOption;
    }

    public String getWarrantyOfferedOption() {
        return WarrantyOfferedOption;
    }

    public void setWarrantyOfferedOption(String warrantyOfferedOption) {
        WarrantyOfferedOption = warrantyOfferedOption;
    }

    public String getWarrantyTypeOption() {
        return WarrantyTypeOption;
    }

    public void setWarrantyTypeOption(String warrantyTypeOption) {
        WarrantyTypeOption = warrantyTypeOption;
    }
}
