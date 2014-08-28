package com.base.xmlpojo.trading.addproduct;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 产品清单详情
 * Created by cz on 2014/7/16.
 */
@XStreamAlias("ProductListingDetails")
public class ProductListingDetails {
    /**
     * 商品品牌
     */
    private BrandMPN BrandMPN;
    /**
     * 商品号
     */
    private String EAN;
    /**
     * 全球贸易识别号码
     */
    private String GTIN;
    /**
     * 是否包含预设信息
     */
    private Boolean IncludePrefilledItemInformation;
    /**
     * 是否包含照片信息
     */
    private Boolean IncludeStockPhotoURL;
    /**
     * 国际标准图书编号
     */
    private String ISBN;
    /**
     * 是否是产品列表
     */
    private Boolean ListIfNoProduct;
    /**
     * 商品ＩＤ
     */
    private String ProductID;
    /**
     * 商品参照ＩＤ
     */
    private String ProductReferenceID;
    /**
     * 查询时是否返回多条记录
     */
    private Boolean ReturnSearchResultOnDuplicates;
    /**
     * 商品品牌
     */
    private TicketListingDetails TicketListingDetails;
    /**
     * 商品识别码
     */
    private String UPC;
    /**
     * 是否是用户的第一个产品
     */
    private Boolean UseFirstProduct;
    /**
     * 是否包含图片ＵＲＬ
     */
    private Boolean UseStockPhotoURLAsGallery;

    public BrandMPN getBrandMPN() {
        return BrandMPN;
    }

    public void setBrandMPN(BrandMPN brandMPN) {
        BrandMPN = brandMPN;
    }

    public String getEAN() {
        return EAN;
    }

    public void setEAN(String EAN) {
        this.EAN = EAN;
    }

    public String getGTIN() {
        return GTIN;
    }

    public void setGTIN(String GTIN) {
        this.GTIN = GTIN;
    }

    public Boolean getIncludePrefilledItemInformation() {
        return IncludePrefilledItemInformation;
    }

    public void setIncludePrefilledItemInformation(Boolean includePrefilledItemInformation) {
        IncludePrefilledItemInformation = includePrefilledItemInformation;
    }

    public Boolean getIncludeStockPhotoURL() {
        return IncludeStockPhotoURL;
    }

    public void setIncludeStockPhotoURL(Boolean includeStockPhotoURL) {
        IncludeStockPhotoURL = includeStockPhotoURL;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public Boolean getListIfNoProduct() {
        return ListIfNoProduct;
    }

    public void setListIfNoProduct(Boolean listIfNoProduct) {
        ListIfNoProduct = listIfNoProduct;
    }

    public String getProductID() {
        return ProductID;
    }

    public void setProductID(String productID) {
        ProductID = productID;
    }

    public String getProductReferenceID() {
        return ProductReferenceID;
    }

    public void setProductReferenceID(String productReferenceID) {
        ProductReferenceID = productReferenceID;
    }

    public Boolean getReturnSearchResultOnDuplicates() {
        return ReturnSearchResultOnDuplicates;
    }

    public void setReturnSearchResultOnDuplicates(Boolean returnSearchResultOnDuplicates) {
        ReturnSearchResultOnDuplicates = returnSearchResultOnDuplicates;
    }

    public TicketListingDetails getTicketListingDetails() {
        return TicketListingDetails;
    }

    public void setTicketListingDetails(TicketListingDetails ticketListingDetails) {
        TicketListingDetails = ticketListingDetails;
    }

    public String getUPC() {
        return UPC;
    }

    public void setUPC(String UPC) {
        this.UPC = UPC;
    }

    public Boolean getUseFirstProduct() {
        return UseFirstProduct;
    }

    public void setUseFirstProduct(Boolean useFirstProduct) {
        UseFirstProduct = useFirstProduct;
    }

    public Boolean getUseStockPhotoURLAsGallery() {
        return UseStockPhotoURLAsGallery;
    }

    public void setUseStockPhotoURLAsGallery(Boolean useStockPhotoURLAsGallery) {
        UseStockPhotoURLAsGallery = useStockPhotoURLAsGallery;
    }
}
