package com.base.xmlpojo.trading.addproduct;

import com.base.xmlpojo.trading.addproduct.attrclass.StartPrice;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.Date;
import java.util.List;

/**
 * 商品信息
 * Created by cz on 2014/7/17.
 */
public class Item {
    /**
     * 应用数据
     */
    private String ApplicationData;
    /**
     * 属性设置数组
     */
    private String AttributeSetArray;
    /**
     * 是否自动支付
     */
    private Boolean AutoPay;

    //最优惠详情
    private BestOfferDetails BestOfferDetails;

    //买方要求的细节
    private BuyerRequirementDetails BuyerRequirementDetails;

    //预填充 基于分类的属性
    private Boolean CategoryBasedAttributesPrefill;

    //是否允许类别映射
    private Boolean CategoryMappingAllowed;

    //慈善事业
    private Charity Charity;

    //条件的描述
    private String ConditionDescription;

    //状态标识
    private Integer ConditionID;

    //国家地区
    private String Country;

    //跨境贸易
    @XStreamImplicit(itemFieldName = "CrossBorderTrade")
    private List<String> CrossBorderTrade;

    //货币
    private String Currency;

    //描述
    private String Description;

    //买方要求禁用
    private Boolean DisableBuyerRequirements;

    //折扣价格信息
    private DiscountPriceInfo DiscountPriceInfo;

    //调度时间最大
    private Integer DispatchTimeMax;

    //ebay现在是否有资格
    private Boolean eBayNowEligible;

    //外部的产品ID
    private String ExternalProductID;

    //是否快速
    private Boolean GetItFast;

    //礼物图标
    private String GiftIcon;

    //礼品服务
    @XStreamImplicit(itemFieldName = "GiftServices")
    private List<String> GiftServices;

    //计数器
    private String HitCounter;

    //包括建议
    private Boolean IncludeRecommendations;

    //库存跟踪方法
    private String InventoryTrackingMethod;

    //兼容性列表项目
    private ItemCompatibilityList ItemCompatibilityList;

    //项目细节
    private ItemSpecifics ItemSpecifics;

    //上市测试重定向的偏好
    private ListingCheckoutRedirectPreference ListingCheckoutRedirectPreference;

    //上市的设计师
    private ListingDesigner ListingDesigner;

    //列出的细节
    private ListingDetails ListingDetails;

    //上市时间
    private String ListingDuration;

    //上市增强
    @XStreamImplicit(itemFieldName = "ListingEnhancement")
    private List<String> ListingEnhancement;

    //清单类型
    private String ListingType;

    //位置
    private String Location;

    //查看属性数组
    private String LookupAttributeArray;

    //库存控制
    private Boolean OutOfStockControl;

    //付款方式
    @XStreamImplicit(itemFieldName = "PaymentMethods")
    private List<String> PaymentMethods;

    //paypal 邮箱
    private String PayPalEmailAddress;

    //获取店铺信息
    private PickupInStoreDetails PickupInStoreDetails;

    //图片详情
    private PictureDetails PictureDetails;

    //邮编
    private String PostalCode;

    //是否结账经验
    private Boolean PostCheckoutExperienceEnabled;

    //主要类别
    private PrimaryCategory PrimaryCategory;

    //是否民营上市
    private Boolean PrivateListing;

    //私人笔记
    private String PrivateNotes;

    //产品上市详情
    private ProductListingDetails ProductListingDetails;

    //商品数量
    private Integer Quantity;

    //商品数量信息
    private QuantityInfo QuantityInfo;

    //买家购买数量限制
    private QuantityRestrictionPerBuyer QuantityRestrictionPerBuyer;

    //退货政策
    private ReturnPolicy ReturnPolicy;

    //安排时间
    private Date ScheduleTime;

    //第二类别
    private SecondaryCategory SecondaryCategory;

    //卖家概况
    private SellerProfiles SellerProfiles;

    //运输详情
    private ShippingDetails ShippingDetails;

    //运输包装详情
    private ShippingPackageDetails ShippingPackageDetails;

    //航运服务成本优先列表
    private ShippingServiceCostOverrideList ShippingServiceCostOverrideList;

    //是否 描述运输条款
    private Boolean ShippingTermsInDescription;

    //运输地区
    @XStreamImplicit(itemFieldName = "ShipToLocations")
    private List<String> ShipToLocations;

    //站点
    private String Site;

    //SKU
    private String SKU;

    //Skype 联系 选项
    @XStreamImplicit(itemFieldName = "SkypeContactOption")
    private List<String> SkypeContactOption;

    //是否启用Skype
    private Boolean SkypeEnabled;

    //SkypeID
    private String SkypeID;

    //起始价格
    private StartPrice StartPrice;

    //店前
    private Storefront Storefront;

    //子标题
    private String SubTitle;

    //税收分类
    private String TaxCategory;

    //标题
    private String Title;

    //是否使用推荐产品
    private Boolean UseRecommendedProduct;

    //是否使用税表
    private Boolean UseTaxTable;

    //唯一刊登识别码
    private String UUID;

    //多属性商品信息组
    private Variations Variations;

    //增值税详情
    private VATDetails VATDetails;

    //车辆识别码
    private String VIN;


    private String VRM;


    public String getApplicationData() {
        return ApplicationData;
    }

    public void setApplicationData(String applicationData) {
        ApplicationData = applicationData;
    }

    public String getAttributeSetArray() {
        return AttributeSetArray;
    }

    public void setAttributeSetArray(String attributeSetArray) {
        AttributeSetArray = attributeSetArray;
    }

    public Boolean getAutoPay() {
        return AutoPay;
    }

    public void setAutoPay(Boolean autoPay) {
        AutoPay = autoPay;
    }

    public BestOfferDetails getBestOfferDetails() {
        return BestOfferDetails;
    }

    public void setBestOfferDetails(BestOfferDetails bestOfferDetails) {
        BestOfferDetails = bestOfferDetails;
    }

    public BuyerRequirementDetails getBuyerRequirementDetails() {
        return BuyerRequirementDetails;
    }

    public void setBuyerRequirementDetails(BuyerRequirementDetails buyerRequirementDetails) {
        BuyerRequirementDetails = buyerRequirementDetails;
    }

    public Boolean getCategoryBasedAttributesPrefill() {
        return CategoryBasedAttributesPrefill;
    }

    public void setCategoryBasedAttributesPrefill(Boolean categoryBasedAttributesPrefill) {
        CategoryBasedAttributesPrefill = categoryBasedAttributesPrefill;
    }

    public Boolean getCategoryMappingAllowed() {
        return CategoryMappingAllowed;
    }

    public void setCategoryMappingAllowed(Boolean categoryMappingAllowed) {
        CategoryMappingAllowed = categoryMappingAllowed;
    }

    public Charity getCharity() {
        return Charity;
    }

    public void setCharity(Charity charity) {
        Charity = charity;
    }

    public String getConditionDescription() {
        return ConditionDescription;
    }

    public void setConditionDescription(String conditionDescription) {
        ConditionDescription = conditionDescription;
    }

    public Integer getConditionID() {
        return ConditionID;
    }

    public void setConditionID(Integer conditionID) {
        ConditionID = conditionID;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public List<String> getCrossBorderTrade() {
        return CrossBorderTrade;
    }

    public void setCrossBorderTrade(List<String> crossBorderTrade) {
        CrossBorderTrade = crossBorderTrade;
    }

    public String getCurrency() {
        return Currency;
    }

    public void setCurrency(String currency) {
        Currency = currency;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Boolean getDisableBuyerRequirements() {
        return DisableBuyerRequirements;
    }

    public void setDisableBuyerRequirements(Boolean disableBuyerRequirements) {
        DisableBuyerRequirements = disableBuyerRequirements;
    }

    public DiscountPriceInfo getDiscountPriceInfo() {
        return DiscountPriceInfo;
    }

    public void setDiscountPriceInfo(DiscountPriceInfo discountPriceInfo) {
        DiscountPriceInfo = discountPriceInfo;
    }

    public Integer getDispatchTimeMax() {
        return DispatchTimeMax;
    }

    public void setDispatchTimeMax(Integer dispatchTimeMax) {
        DispatchTimeMax = dispatchTimeMax;
    }

    public Boolean geteBayNowEligible() {
        return eBayNowEligible;
    }

    public void seteBayNowEligible(Boolean eBayNowEligible) {
        this.eBayNowEligible = eBayNowEligible;
    }

    public String getExternalProductID() {
        return ExternalProductID;
    }

    public void setExternalProductID(String externalProductID) {
        ExternalProductID = externalProductID;
    }

    public Boolean getGetItFast() {
        return GetItFast;
    }

    public void setGetItFast(Boolean getItFast) {
        GetItFast = getItFast;
    }

    public String getGiftIcon() {
        return GiftIcon;
    }

    public void setGiftIcon(String giftIcon) {
        GiftIcon = giftIcon;
    }

    public List<String> getGiftServices() {
        return GiftServices;
    }

    public void setGiftServices(List<String> giftServices) {
        GiftServices = giftServices;
    }

    public String getHitCounter() {
        return HitCounter;
    }

    public void setHitCounter(String hitCounter) {
        HitCounter = hitCounter;
    }

    public Boolean getIncludeRecommendations() {
        return IncludeRecommendations;
    }

    public void setIncludeRecommendations(Boolean includeRecommendations) {
        IncludeRecommendations = includeRecommendations;
    }

    public String getInventoryTrackingMethod() {
        return InventoryTrackingMethod;
    }

    public void setInventoryTrackingMethod(String inventoryTrackingMethod) {
        InventoryTrackingMethod = inventoryTrackingMethod;
    }

    public ItemCompatibilityList getItemCompatibilityList() {
        return ItemCompatibilityList;
    }

    public void setItemCompatibilityList(ItemCompatibilityList itemCompatibilityList) {
        ItemCompatibilityList = itemCompatibilityList;
    }

    public ItemSpecifics getItemSpecifics() {
        return ItemSpecifics;
    }

    public void setItemSpecifics(ItemSpecifics itemSpecifics) {
        ItemSpecifics = itemSpecifics;
    }

    public ListingCheckoutRedirectPreference getListingCheckoutRedirectPreference() {
        return ListingCheckoutRedirectPreference;
    }

    public void setListingCheckoutRedirectPreference(ListingCheckoutRedirectPreference listingCheckoutRedirectPreference) {
        ListingCheckoutRedirectPreference = listingCheckoutRedirectPreference;
    }

    public ListingDesigner getListingDesigner() {
        return ListingDesigner;
    }

    public void setListingDesigner(ListingDesigner listingDesigner) {
        ListingDesigner = listingDesigner;
    }

    public ListingDetails getListingDetails() {
        return ListingDetails;
    }

    public void setListingDetails(ListingDetails listingDetails) {
        ListingDetails = listingDetails;
    }

    public String getListingDuration() {
        return ListingDuration;
    }

    public void setListingDuration(String listingDuration) {
        ListingDuration = listingDuration;
    }

    public List<String> getListingEnhancement() {
        return ListingEnhancement;
    }

    public void setListingEnhancement(List<String> listingEnhancement) {
        ListingEnhancement = listingEnhancement;
    }

    public String getListingType() {
        return ListingType;
    }

    public void setListingType(String listingType) {
        ListingType = listingType;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getLookupAttributeArray() {
        return LookupAttributeArray;
    }

    public void setLookupAttributeArray(String lookupAttributeArray) {
        LookupAttributeArray = lookupAttributeArray;
    }

    public Boolean getOutOfStockControl() {
        return OutOfStockControl;
    }

    public void setOutOfStockControl(Boolean outOfStockControl) {
        OutOfStockControl = outOfStockControl;
    }

    public List<String> getPaymentMethods() {
        return PaymentMethods;
    }

    public void setPaymentMethods(List<String> paymentMethods) {
        PaymentMethods = paymentMethods;
    }

    public String getPayPalEmailAddress() {
        return PayPalEmailAddress;
    }

    public void setPayPalEmailAddress(String payPalEmailAddress) {
        PayPalEmailAddress = payPalEmailAddress;
    }

    public PickupInStoreDetails getPickupInStoreDetails() {
        return PickupInStoreDetails;
    }

    public void setPickupInStoreDetails(PickupInStoreDetails pickupInStoreDetails) {
        PickupInStoreDetails = pickupInStoreDetails;
    }

    public PictureDetails getPictureDetails() {
        return PictureDetails;
    }

    public void setPictureDetails(PictureDetails pictureDetails) {
        PictureDetails = pictureDetails;
    }

    public String getPostalCode() {
        return PostalCode;
    }

    public void setPostalCode(String postalCode) {
        PostalCode = postalCode;
    }

    public Boolean getPostCheckoutExperienceEnabled() {
        return PostCheckoutExperienceEnabled;
    }

    public void setPostCheckoutExperienceEnabled(Boolean postCheckoutExperienceEnabled) {
        PostCheckoutExperienceEnabled = postCheckoutExperienceEnabled;
    }

    public PrimaryCategory getPrimaryCategory() {
        return PrimaryCategory;
    }

    public void setPrimaryCategory(PrimaryCategory primaryCategory) {
        PrimaryCategory = primaryCategory;
    }

    public Boolean getPrivateListing() {
        return PrivateListing;
    }

    public void setPrivateListing(Boolean privateListing) {
        PrivateListing = privateListing;
    }

    public String getPrivateNotes() {
        return PrivateNotes;
    }

    public void setPrivateNotes(String privateNotes) {
        PrivateNotes = privateNotes;
    }

    public ProductListingDetails getProductListingDetails() {
        return ProductListingDetails;
    }

    public void setProductListingDetails(ProductListingDetails productListingDetails) {
        ProductListingDetails = productListingDetails;
    }

    public Integer getQuantity() {
        return Quantity;
    }

    public void setQuantity(Integer quantity) {
        Quantity = quantity;
    }

    public QuantityInfo getQuantityInfo() {
        return QuantityInfo;
    }

    public void setQuantityInfo(QuantityInfo quantityInfo) {
        QuantityInfo = quantityInfo;
    }

    public QuantityRestrictionPerBuyer getQuantityRestrictionPerBuyer() {
        return QuantityRestrictionPerBuyer;
    }

    public void setQuantityRestrictionPerBuyer(QuantityRestrictionPerBuyer quantityRestrictionPerBuyer) {
        QuantityRestrictionPerBuyer = quantityRestrictionPerBuyer;
    }

    public ReturnPolicy getReturnPolicy() {
        return ReturnPolicy;
    }

    public void setReturnPolicy(ReturnPolicy returnPolicy) {
        ReturnPolicy = returnPolicy;
    }

    public Date getScheduleTime() {
        return ScheduleTime;
    }

    public void setScheduleTime(Date scheduleTime) {
        ScheduleTime = scheduleTime;
    }

    public SecondaryCategory getSecondaryCategory() {
        return SecondaryCategory;
    }

    public void setSecondaryCategory(SecondaryCategory secondaryCategory) {
        SecondaryCategory = secondaryCategory;
    }

    public SellerProfiles getSellerProfiles() {
        return SellerProfiles;
    }

    public void setSellerProfiles(SellerProfiles sellerProfiles) {
        SellerProfiles = sellerProfiles;
    }

    public ShippingDetails getShippingDetails() {
        return ShippingDetails;
    }

    public void setShippingDetails(ShippingDetails shippingDetails) {
        ShippingDetails = shippingDetails;
    }

    public ShippingPackageDetails getShippingPackageDetails() {
        return ShippingPackageDetails;
    }

    public void setShippingPackageDetails(ShippingPackageDetails shippingPackageDetails) {
        ShippingPackageDetails = shippingPackageDetails;
    }

    public ShippingServiceCostOverrideList getShippingServiceCostOverrideList() {
        return ShippingServiceCostOverrideList;
    }

    public void setShippingServiceCostOverrideList(ShippingServiceCostOverrideList shippingServiceCostOverrideList) {
        ShippingServiceCostOverrideList = shippingServiceCostOverrideList;
    }

    public Boolean getShippingTermsInDescription() {
        return ShippingTermsInDescription;
    }

    public void setShippingTermsInDescription(Boolean shippingTermsInDescription) {
        ShippingTermsInDescription = shippingTermsInDescription;
    }

    public List<String> getShipToLocations() {
        return ShipToLocations;
    }

    public void setShipToLocations(List<String> shipToLocations) {
        ShipToLocations = shipToLocations;
    }

    public String getSite() {
        return Site;
    }

    public void setSite(String site) {
        Site = site;
    }

    public String getSKU() {
        return SKU;
    }

    public void setSKU(String SKU) {
        this.SKU = SKU;
    }

    public List<String> getSkypeContactOption() {
        return SkypeContactOption;
    }

    public void setSkypeContactOption(List<String> skypeContactOption) {
        SkypeContactOption = skypeContactOption;
    }

    public Boolean getSkypeEnabled() {
        return SkypeEnabled;
    }

    public void setSkypeEnabled(Boolean skypeEnabled) {
        SkypeEnabled = skypeEnabled;
    }

    public String getSkypeID() {
        return SkypeID;
    }

    public void setSkypeID(String skypeID) {
        SkypeID = skypeID;
    }

    public StartPrice getStartPrice() {
        return StartPrice;
    }

    public void setStartPrice(StartPrice startPrice) {
        StartPrice = startPrice;
    }

    public Storefront getStorefront() {
        return Storefront;
    }

    public void setStorefront(Storefront storefront) {
        Storefront = storefront;
    }

    public String getSubTitle() {
        return SubTitle;
    }

    public void setSubTitle(String subTitle) {
        SubTitle = subTitle;
    }

    public String getTaxCategory() {
        return TaxCategory;
    }

    public void setTaxCategory(String taxCategory) {
        TaxCategory = taxCategory;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public Boolean getUseRecommendedProduct() {
        return UseRecommendedProduct;
    }

    public void setUseRecommendedProduct(Boolean useRecommendedProduct) {
        UseRecommendedProduct = useRecommendedProduct;
    }

    public Boolean getUseTaxTable() {
        return UseTaxTable;
    }

    public void setUseTaxTable(Boolean useTaxTable) {
        UseTaxTable = useTaxTable;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public Variations getVariations() {
        return Variations;
    }

    public void setVariations(Variations variations) {
        Variations = variations;
    }

    public VATDetails getVATDetails() {
        return VATDetails;
    }

    public void setVATDetails(VATDetails VATDetails) {
        this.VATDetails = VATDetails;
    }

    public String getVIN() {
        return VIN;
    }

    public void setVIN(String VIN) {
        this.VIN = VIN;
    }

    public String getVRM() {
        return VRM;
    }

    public void setVRM(String VRM) {
        this.VRM = VRM;
    }
}
