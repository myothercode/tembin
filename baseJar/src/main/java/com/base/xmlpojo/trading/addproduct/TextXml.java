package com.base.xmlpojo.trading.addproduct;

import com.base.utils.xmlutils.PojoXmlUtil;
import com.base.xmlpojo.trading.addproduct.attrclass.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrtor on 2014/7/16.
 */
public class TextXml {
    //public static void main(String[] args){
        //testReturnPolicy();
        //testShippingServiceOptions();
        //testProductListingDetails();
        //testPrimaryCategory();
        //testPictureDetails();
        //testBestOfferDetails();
        //testMaximumBuyerPolicyViolations();
        //testMaximumItemRequirements();
        //testMaximumUnpaidItemStrikesInfo();
        //testVerifiedUserRequirements();
        //testBuyerRequirementDetails();
        //testNameValueList();
        //testCompatibility();
        //testItemCompatibilityList();
        //testItemSpecifics();
        //testListingCheckoutRedirectPreference();
        //testListingDesigner();
        //testListingDetails();
        //testPickupInStoreDetails();
        //testBrandMPN();
        //testTicketListingDetails();
        //testQuantityInfo();
        //testQuantityRestrictionPerBuyer();
        //testSecondaryCategory();
        //testSellerPaymentProfile();
    //}

    /**
     * 测试生成退货政策
     */
    public static void testReturnPolicy(){
        ReturnPolicy rp = new ReturnPolicy();
        rp.setReturnsWithinOption("abcd");
        rp.setShippingCostPaidByOption("tcds");
        rp.setDescription("asfasdf");
        rp.setRefundOption("54546");
        rp.setReturnsAcceptedOption("afdasdfafdsfs");

        System.out.println(PojoXmlUtil.pojoToXml(rp));
    }

    /**
     * 生成运货配置信息
     */
    public static void testShippingServiceOptions(){
        ShippingServiceOptions sso = new ShippingServiceOptions();
        sso.setFreeShipping(true);
        sso.setShippingService("table");
        sso.setShippingServicePriority(1);
        ShippingServiceCost ssc = new ShippingServiceCost();
        ssc.setCurrencyID("USD");
        ssc.setValue(1.2);
        ShippingServiceAdditionalCost ssac = new ShippingServiceAdditionalCost();
        ssac.setCurrencyID("USD");
        ssac.setValue(1.0);
        sso.setShippingServiceCost(ssc);
        sso.setShippingServiceAdditionalCost(ssac);
        System.out.println(PojoXmlUtil.pojoToXml(sso));
    }

    /**
     * 测试产品详情
     */
    public static void testProductListingDetails(){
        ProductListingDetails pld = new ProductListingDetails();
        pld.setUPC("79874321");
        pld.setIncludePrefilledItemInformation(true);
        pld.setIncludeStockPhotoURL(true);
        pld.setReturnSearchResultOnDuplicates(true);
        pld.setUseFirstProduct(true);
        pld.setUseStockPhotoURLAsGallery(true);
        System.out.println(PojoXmlUtil.pojoToXml(pld));
    }

    /**
     * 测试产品分类
     */
    public static void testPrimaryCategory(){
        PrimaryCategory pc = new PrimaryCategory();
        pc.setCategoryID("6546231");
        System.out.println(PojoXmlUtil.pojoToXml(pc));
    }

    /**
     * 测试产品图片分类
     */
    public static void testPictureDetails(){
        PictureDetails pd = new PictureDetails();
        pd.setGalleryType("Gallery");
        pd.setGalleryDuration("afafas");
        pd.setGalleryURL("http://baidu.com");
        pd.setPhotoDisplay("none");
        pd.setPictureSource("baidu");
        List li = new ArrayList();
        li.add("http://baidu.com");
        li.add("http://5oo.com");
        pd.setPictureURL(li);
        System.out.println(PojoXmlUtil.pojoToXml(pd));
    }

    /**
     * 测试更好报价
     */
    public static void testBestOfferDetails(){
        BestOfferDetails bod = new BestOfferDetails();
        bod.setBestOfferEnabled(true);
        System.out.println(PojoXmlUtil.pojoToXml(bod));
    }

    /**
     * 测试买家在一定周期内最多违规次数
     */
    public static void testMaximumBuyerPolicyViolations(){
        MaximumBuyerPolicyViolations mbpv = new MaximumBuyerPolicyViolations();
        mbpv.setCount(4);
        mbpv.setPeriod("30_DAY");
        System.out.println(PojoXmlUtil.pojoToXml(mbpv));
    }

    /**
     * 测试买最多拍了未付款次数
     */
    public static void testMaximumItemRequirements(){
        MaximumItemRequirements mir = new MaximumItemRequirements();
        mir.setMaximumItemCount(3);
        mir.setMinimumFeedbackScore(90);
        System.out.println(PojoXmlUtil.pojoToXml(mir));
    }

    /**
     *测试最多未付款
     */
    public static void testMaximumUnpaidItemStrikesInfo(){
        MaximumUnpaidItemStrikesInfo muis = new MaximumUnpaidItemStrikesInfo();
        muis.setCount(3);
        muis.setPeriod("7_Day");
        System.out.println(PojoXmlUtil.pojoToXml(muis));
    }

    /**
     * 测试用户认证
     */
    public static void testVerifiedUserRequirements(){
        VerifiedUserRequirements vur = new VerifiedUserRequirements();
        vur.setMinimumFeedbackScore(4);
        vur.setVerifiedUser(true);
        System.out.println(PojoXmlUtil.pojoToXml(vur));
    }

    /**
     * 买家要求
     */
    public static void testBuyerRequirementDetails(){
        BuyerRequirementDetails brd = new BuyerRequirementDetails();
       // brd.setMinimumFeedbackScore(60);
        brd.setLinkedPayPalAccount(true);
        brd.setShipToRegistrationCountry(true);
        brd.setZeroFeedbackScore(true);
        MaximumBuyerPolicyViolations mbpv = new MaximumBuyerPolicyViolations();
        mbpv.setCount(4);
        mbpv.setPeriod("30_DAY");
        brd.setMaximumBuyerPolicyViolations(mbpv);
        MaximumItemRequirements mir = new MaximumItemRequirements();
        mir.setMaximumItemCount(3);
        mir.setMinimumFeedbackScore(90);
        brd.setMaximumItemRequirements(mir);
        MaximumUnpaidItemStrikesInfo muis = new MaximumUnpaidItemStrikesInfo();
        muis.setCount(3);
        muis.setPeriod("7_Day");
        brd.setMaximumUnpaidItemStrikesInfo(muis);
        VerifiedUserRequirements vur = new VerifiedUserRequirements();
        vur.setMinimumFeedbackScore(4);
        vur.setVerifiedUser(true);
        brd.setVerifiedUserRequirements(vur);
        System.out.println(PojoXmlUtil.pojoToXml(brd));

    }

    /**
     * 值
     */
    public static void testNameValueList(){
        NameValueList nvl = new NameValueList();
        nvl.setName("cz");
        List li = new ArrayList();
        li.add("1");
        li.add("2");
        li.add("3");
        nvl.setValue(li);
        System.out.println(PojoXmlUtil.pojoToXml(nvl));
    }

    /**
     * 兼容性
     */
    public static void testCompatibility(){
        Compatibility cb = new Compatibility();
        cb.setCompatibilityNotes("chenzhuo");
        NameValueList nvl = new NameValueList();
        nvl.setName("cz");
        List li = new ArrayList();
        li.add("1");
        li.add("2");
        li.add("3");
        nvl.setValue(li);
        NameValueList nvl1 = new NameValueList();
        List<NameValueList> linv = new ArrayList<NameValueList>();
        linv.add(nvl);
        nvl1.setName("cd");
        List li1 = new ArrayList();
        li1.add("4");
        li1.add("5");
        li1.add("6");
        nvl1.setValue(li1);
        linv.add(nvl1);
        cb.setNameValueList(linv);
        System.out.println(PojoXmlUtil.pojoToXml(cb));
    }

    /**
     * 商品兼容性明细
     */
    public static void testItemCompatibilityList(){
        ItemCompatibilityList icl = new ItemCompatibilityList();
        List icll = new ArrayList();
        Compatibility cb = new Compatibility();
        cb.setCompatibilityNotes("chenzhuo");
        NameValueList nvl = new NameValueList();
        nvl.setName("cz");
        List li = new ArrayList();
        li.add("1");
        li.add("2");
        li.add("3");
        nvl.setValue(li);
        NameValueList nvl1 = new NameValueList();
        List<NameValueList> linv = new ArrayList<NameValueList>();
        linv.add(nvl);
        nvl1.setName("cd");
        List li1 = new ArrayList();
        li1.add("4");
        li1.add("5");
        li1.add("6");
        nvl1.setValue(li1);
        linv.add(nvl1);
        cb.setNameValueList(linv);
        icll.add(cb);
        icl.setCompatibility(icll);
        System.out.println(PojoXmlUtil.pojoToXml(icl));
    }

    /**
     * 商品特性
     */
    public static void testItemSpecifics(){
        ItemSpecifics is = new ItemSpecifics();
        NameValueList nvl = new NameValueList();
        nvl.setName("cz");
        List li = new ArrayList();
        li.add("1");
        li.add("2");
        li.add("3");
        nvl.setValue(li);
        List linv = new ArrayList();

        NameValueList nvl1 = new NameValueList();
        nvl1.setName("cz");
        List li1 = new ArrayList();
        li1.add("1");
        li1.add("2");
        li1.add("3");
        nvl1.setValue(li1);

        linv.add(nvl);
        linv.add(nvl1);
        is.setNameValueList(linv);

        System.out.println(PojoXmlUtil.pojoToXml(is));
    }

    /**
     * 卖家店铺信息
     */
    public static void testListingCheckoutRedirectPreference(){
        ListingCheckoutRedirectPreference lcrp = new ListingCheckoutRedirectPreference();
        lcrp.setProStoresStoreName("全球第一");
        lcrp.setSellerThirdPartyUsername("ert");
        System.out.println(PojoXmlUtil.pojoToXml(lcrp));
    }

    /**
     * 设计商品展示模板
     */
    public static void testListingDesigner(){
        ListingDesigner ld = new ListingDesigner();
        ld.setLayoutID(32145);
        ld.setOptimalPictureSize(true);
        ld.setThemeID(456);
        System.out.println(PojoXmlUtil.pojoToXml(ld));
    }

    /**
     * 在线商品价格详情
     */
    public static void testListingDetails(){
        ListingDetails ld = new ListingDetails();
        ld.setLocalListingDistance("成都");
        MinimumBestOfferPrice mbop = new MinimumBestOfferPrice();
        mbop.setCurrencyID("USD");
        mbop.setValue(32.4);
        BestOfferAutoAcceptPrice boaa = new BestOfferAutoAcceptPrice();
        boaa.setCurrencyID("USD");
        boaa.setValue(24.5);
        ld.setBestOfferAutoAcceptPrice(boaa);
        ld.setMinimumBestOfferPrice(mbop);
        System.out.println(PojoXmlUtil.pojoToXml(ld));
    }

    private static void testPickupInStoreDetails(){
        PickupInStoreDetails pisd = new PickupInStoreDetails();
        pisd.setEligibleForPickupInStore(true);
        System.out.println(PojoXmlUtil.pojoToXml(pisd));
    }

    /**
     * 品牌
     */
    private static void testBrandMPN(){
        BrandMPN bm = new BrandMPN();
        bm.setBrand("bank");
        bm.setMPN("中行");
        System.out.println(PojoXmlUtil.pojoToXml(bm));
    }

    /**
     * 活动详情
     */
    private static void testTicketListingDetails(){
        TicketListingDetails tld = new TicketListingDetails();
        tld.setEventTitle("国庆");
        tld.setPrintedDate("20140706");
        tld.setPrintedTime("30天");
        tld.setVenue("美年广场");
        System.out.println(PojoXmlUtil.pojoToXml(tld));
    }

    /**
     * 数量信息
     */
    private static void testQuantityInfo(){
        QuantityInfo qi = new QuantityInfo();
        qi.setMinimumRemnantSet(10);
        System.out.println(PojoXmlUtil.pojoToXml(qi));
    }

    /**
     * 买家一次最多购买数量
     */
    public static void testQuantityRestrictionPerBuyer(){
        QuantityRestrictionPerBuyer qrp = new QuantityRestrictionPerBuyer();
        qrp.setMaximumQuantity(2);
        System.out.println(PojoXmlUtil.pojoToXml(qrp));
    }

    /**
     * 产品第二分类
     */
    public static void testSecondaryCategory(){
        SecondaryCategory sc = new SecondaryCategory();
        sc.setCategoryID("645689");
        System.out.println(PojoXmlUtil.pojoToXml(sc));
    }

    /**
     * 卖家支付资　料
     */
    public static void testSellerPaymentProfile(){
        SellerPaymentProfile spp = new SellerPaymentProfile();
        spp.setPaymentProfileID(123123L);
        spp.setPaymentProfileName("chenzhuo");
        System.out.println(PojoXmlUtil.pojoToXml(spp));
    }

    /**
     * 卖家返回资料
     */
    public static void testSellerReturnProfile(){
        SellerReturnProfile srp = new SellerReturnProfile();
        srp.setReturnProfileID(1234123L);
        srp.setReturnProfileName("chengdu");
        System.out.println(PojoXmlUtil.pojoToXml(srp));
    }

    /**
     * 卖家运输资料
     */
    public static void testSellerShippingProfile(){
        SellerShippingProfile ssp = new SellerShippingProfile();
        ssp.setShippingProfileID(23412321L);
        ssp.setShippingProfileName("重庆");
        System.out.println(PojoXmlUtil.pojoToXml(ssp));
    }

    /**
     * 卖家资料
     */
    public static void testSellerProfiles(){
        SellerProfiles sp = new SellerProfiles();
        SellerPaymentProfile spp = new SellerPaymentProfile();
        spp.setPaymentProfileID(123123L);
        spp.setPaymentProfileName("chenzhuo");
        SellerReturnProfile srp = new SellerReturnProfile();
        srp.setReturnProfileID(1234123L);
        srp.setReturnProfileName("chengdu");
        SellerShippingProfile ssp = new SellerShippingProfile();
        ssp.setShippingProfileID(23412321L);
        ssp.setShippingProfileName("重庆");
        sp.setSellerPaymentProfile(spp);
        sp.setSellerReturnProfile(srp);
        sp.setSellerShippingProfile(ssp);
        System.out.println(PojoXmlUtil.pojoToXml(sp));
    }

    /**
     * 商品包装信息
     */
    public static void testCalculatedShippingRate(){
        CalculatedShippingRate csr = new CalculatedShippingRate();
        csr.setMeasurementUnit("重量");
        csr.setOriginatingPostalCode("adfasdf");
        csr.setPackageDepth(4.5);
        csr.setPackageLength(1.2);
        csr.setPackageWidth(3.4);
        PackagingHandlingCosts phc = new PackagingHandlingCosts();
        phc.setCurrencyID("USD");
        phc.setValue(0.3);
        csr.setPackagingHandlingCosts(phc);
        csr.setShippingIrregular(true);
        csr.setShippingPackage("asdfasdf");
        csr.setWeightMajor(1.2);
        csr.setWeightMinor(1.3);
        System.out.println(PojoXmlUtil.pojoToXml(csr));
    }

    /**
     * 保险详情
     */
    public  static void testInsuranceDetails(){
        InsuranceDetails id= new InsuranceDetails();
        InsuranceFee ifs = new InsuranceFee();
        ifs.setValue(3.4);
        ifs.setCurrencyID("USD");
        id.setInsuranceFee(ifs);
        id.setInsuranceOption("abcd");
        System.out.println(PojoXmlUtil.pojoToXml(id));
    }
    /**
     * 保险详情
     */
    public  static void testInternationalInsuranceDetails(){
        InternationalInsuranceDetails id= new InternationalInsuranceDetails();
        InsuranceFee ifs = new InsuranceFee();
        ifs.setValue(3.4);
        ifs.setCurrencyID("USD");
        id.setInsuranceFee(ifs);
        id.setInsuranceOption("abcd");
        System.out.println(PojoXmlUtil.pojoToXml(id));
    }

    /**
     *
     * 国际运输服务
     */
    public static void testInternationalShippingServiceOption(){
        InternationalShippingServiceOption iss = new InternationalShippingServiceOption();
        iss.setShippingService("abcd");
        iss.setShippingServicePriority(1);
        ShippingServiceAdditionalCost ssac = new ShippingServiceAdditionalCost();
        ssac.setCurrencyID("USD");
        ssac.setValue(1.2);
        ShippingServiceCost ssc = new ShippingServiceCost();
        ssc.setCurrencyID("USD");
        ssc.setValue(1.2);
        iss.setShippingServiceCost(ssc);
        iss.setShippingServiceAdditionalCost(ssac);
        List li = new ArrayList();
        li.add("adsfasdfas");
        li.add("bbbbbbbbbbbbb");
        li.add("cccccccccc");
        iss.setShipToLocation(li);
        System.out.println(PojoXmlUtil.pojoToXml(iss));
    }

    /**
     * 价格表
     */
    public static void testRateTableDetails(){
        RateTableDetails rtd = new RateTableDetails();
        rtd.setDomesticRateTable("一般");
        rtd.setInternationalRateTable("tve v");
        System.out.println(PojoXmlUtil.pojoToXml(rtd));
    }

    /**
     * 销售税
     */
    public static void testSalesTax(){
        SalesTax st = new SalesTax();
        st.setSalesTaxPercent(1.2f);
        st.setSalesTaxState("tve asdfasd");
        st.setShippingIncludedInTax(true);
        System.out.println(PojoXmlUtil.pojoToXml(st));
    }

    /**
     * 运输包裹详情
     */
    public static void testShippingPackageDetails(){
        ShippingPackageDetails spd = new ShippingPackageDetails();
        spd.setMeasurementUnit("KG");
        spd.setPackageDepth(1.2);
        spd.setPackageLength(1.3);
        spd.setPackageWidth(1.4);
        spd.setShippingIrregular(false);
        spd.setShippingPackage("cdneadfa");
        spd.setWeightMajor(1.2);
        spd.setWeightMinor(0.3);
        System.out.println(PojoXmlUtil.pojoToXml(spd));
    }

    public static void testShippingServiceCostOverrideList(){
        ShippingServiceCostOverrideList sscol = new ShippingServiceCostOverrideList();
        List li = new ArrayList();
        ShippingServiceCostOverride ssco = new ShippingServiceCostOverride();
        ssco.setShippingServiceAdditionalCost(new ShippingServiceAdditionalCost("USD",1.2));
        ssco.setShippingServiceCost(new ShippingServiceCost("USD",4.5));
        ssco.setShippingSurcharge(new ShippingSurcharge("USD",0.9));
        li.add(ssco);

        ShippingServiceCostOverride ssco2 = new ShippingServiceCostOverride();
        ssco2.setShippingServiceAdditionalCost(new ShippingServiceAdditionalCost("USD",1.2));
        ssco2.setShippingServiceCost(new ShippingServiceCost("USD",4.5));
        ssco2.setShippingSurcharge(new ShippingSurcharge("USD",0.9));
        li.add(ssco2);
        sscol.setShippingServiceCostOverride(li);
        System.out.println(PojoXmlUtil.pojoToXml(sscol));
    }

    public static void main(String[] args){
        //testSellerReturnProfile();
        //testSellerShippingProfile();
        //testSellerProfiles();
        //testCalculatedShippingRate();
        //testInsuranceDetails();
        //testInternationalInsuranceDetails();
        //testInternationalShippingServiceOption();
        //testRateTableDetails();
        //testSalesTax();
        //testShippingPackageDetails();
        //testShippingServiceCostOverrideList();
        //testAddFixedPriceItemRequest();
        //testBuyerRequirementDetails();
        //testAddFixedPriceItemRequests();
        //System.out.println(57/100);
        /*String url="http://i.ebayimg.sandbox.ebay.com/00/s/NjAwWDk2MA==/$(KGrHqVHJBEFE1c-ciWoBUR2LG8Ul!~~60_0.JPG";
        System.out.println(url.substring(url.lastIndexOf("_")+1,url.lastIndexOf(".")));
        System.out.println(url.substring(0,url.lastIndexOf("_")+1));
        System.out.println(url.substring(url.lastIndexOf(".")));*/
        Random r = new Random();
        System.out.println(r.nextInt(10));
    }

    /**
     * 多属性
     */
    public static void testAddFixedPriceItemRequests(){
        AddFixedPriceItemRequest addItem = new AddFixedPriceItemRequest();
        addItem.setXmlns("urn:ebay:apis:eBLBaseComponents");
        addItem.setErrorLanguage("en_US");
        addItem.setWarningLevel("High");
        RequesterCredentials rc = new RequesterCredentials();
        rc.seteBayAuthToken("AgAAAA**AQAAAA**aAAAAA**wV1JUQ**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6wFk4GhCpGGoA+dj6x9nY+seQ**cx0CAA**AAMAAA**2kuzIn+bBej1QDsDFfI2N74mj8psZYNYrtgX97fzWSGXO7EjvdlE9leu9HCY1bR9wdrzlAE7AKcT9Oz5BDNZbNQLS+uoifmNUM47lSqxWeYTQS2GtMK25LPYhxY+OQp6UVZ8lUh6Oqr91ub03emzufuZHo+6KSNJfNXMtOBVaB7PDeBQyNWoFBO0/LYiS5ql6HXB7vCj0W+K/iT4t3aPs5KlXAXjewM/Sa+nUDtjT9SseqrKrxdZx5fkAePeSrBs229tdCrkTtE0n+ZE9ppwJjElZpu7yfQL44McNa16KBxYYO0PnX7ENg2yMxf3H4aji0BEfB41lrC1LwhmNSebJGrJXRQVS9jmZyDqYiBdn1t536va/LPTP8kc3GZ7hnZRJuhMxoGGgx4ev5Hip0L7dk6cAPKHIkHUIjfA5pwVHEJZpvea+7uvwAh5pj9U7r6rmB9FXH2G9l+F5SytYlIXsDjwNtrEN53k5HrM0vhnGdd7pUwvyu7Nu4U5aPkZQZjTr6OrTWioDsZZwEz+pf0scw0IYweMhicCqMTNbvkJsj2cikX49C6XSAcoUyrGtGa11vFChrifmq74dPZmUEtT1hDtwL1Ix3VPyZcJtTukKljxa0W0IwIe676X5HmiGhvk5qPPUImkXcZdQUK1gMdZmw0seMl5xmFG33kKVSD9H0p0JAEF4lOcDvjADQZtwLXY3qIhvYcKdOrIffrUAURnJRYnrB/MixizWvw252xBn9tmxpm68O3KsGBzcUwEB0Su");
        addItem.setRequesterCredentials(rc);
        Item tem = new Item();
        tem.setShippingTermsInDescription(false);
        List liship = new ArrayList();
        liship.add("CA");
        tem.setShipToLocations(liship);
        tem.setDescription("Ce shi yong shang pin miao shu");
        tem.setConditionID(1000);
        tem.setCountry("US");
        tem.setCurrency("USD");
        tem.setDispatchTimeMax(3);
        tem.setListingDuration("GTC");
        tem.setListingType("FixedPriceItem");
        List liPay = new ArrayList();
        liPay.add("PayPal");
        tem.setPaymentMethods(liPay);
        tem.setPayPalEmailAddress("caixu23@gmail.com");
        tem.setPostalCode("95125");
        tem.setTitle("Ce shi yong shang pin");
        tem.setSite("US");
        tem.setSKU("YL987");
        //tem.setQuantity(5);
       // StartPrice sp = new StartPrice("USD",19.99);
        //tem.setStartPrice(sp);
        tem.setGetItFast(false);

        BuyerRequirementDetails brd = new BuyerRequirementDetails();
        brd.setLinkedPayPalAccount(true);
        brd.setShipToRegistrationCountry(false);
        brd.setZeroFeedbackScore(true);
        //brd.setMinimumFeedbackScore(-1);
        MaximumBuyerPolicyViolations mbpv = new MaximumBuyerPolicyViolations();
        mbpv.setCount(4);
        mbpv.setPeriod("Days_30");
        brd.setMaximumBuyerPolicyViolations(mbpv);

        MaximumItemRequirements mir = new MaximumItemRequirements();
        mir.setMaximumItemCount(2);
        mir.setMinimumFeedbackScore(1);
        brd.setMaximumItemRequirements(mir);

        MaximumUnpaidItemStrikesInfo muis = new MaximumUnpaidItemStrikesInfo();
        muis.setCount(2);
        muis.setPeriod("Days_30");
        brd.setMaximumUnpaidItemStrikesInfo(muis);

        VerifiedUserRequirements vur = new VerifiedUserRequirements();
        vur.setMinimumFeedbackScore(3);
        vur.setVerifiedUser(false);
        brd.setVerifiedUserRequirements(vur);

        tem.setBuyerRequirementDetails(brd);

        PictureDetails pd = new PictureDetails();
        List li = new ArrayList();
        li.add("http://i12.ebayimg.com/03/i/04/8a/5f/a1_1_sbl.JPG");
        li.add("http://i22.ebayimg.com/01/i/04/8e/53/69_1_sbl.JPG");
        li.add("http://i4.ebayimg.ebay.com/01/i/000/77/3c/d88f_1_sbl.JPG");
        pd.setPictureURL(li);
        tem.setPictureDetails(pd);

        ReturnPolicy rp = new ReturnPolicy();
        rp.setReturnsWithinOption("Days_60");
        rp.setShippingCostPaidByOption("Buyer");
        rp.setDescription("Text description of return policy details");
        rp.setRefundOption("MoneyBack");
        rp.setReturnsAcceptedOption("ReturnsAccepted");
        tem.setReturnPolicy(rp);

        ShippingDetails sd = new ShippingDetails();

        CalculatedShippingRate csr = new CalculatedShippingRate();
        csr.setOriginatingPostalCode("95125");
        csr.setPackageDepth(6);
        csr.setPackageLength(7);
        csr.setPackageWidth(7);
        csr.setShippingPackage("PackageThickEnvelope");
        csr.setWeightMinor(0);
        csr.setWeightMajor(2);
        sd.setCalculatedShippingRate(csr);

        sd.setPaymentInstructions("Payment must be received within 7 business days of purchase.");
        SalesTax st = new SalesTax();
        st.setSalesTaxPercent(8.75f);
        st.setSalesTaxState("CA");
        sd.setSalesTax(st);


        /*InternationalShippingServiceOption iss = new InternationalShippingServiceOption();
        iss.setShippingService("UPSWorldWideExpressPlus");
        iss.setShippingServicePriority(2);
        ShippingServiceAdditionalCost ssac = new ShippingServiceAdditionalCost("USD",4.00);
        ShippingServiceCost ssc = new ShippingServiceCost("USD",4.00);
        iss.setShippingServiceCost(ssc);
        iss.setShippingServiceAdditionalCost(ssac);
        List liShip = new ArrayList();
        liShip.add("CA");
        iss.setShipToLocation(liShip);
        List liss = new ArrayList();
        liss.add(iss);
        sd.setInternationalShippingServiceOption(liss);*/
        sd.setShippingType("Calculated");

        ShippingServiceOptions sso = new ShippingServiceOptions();
        sso.setFreeShipping(true);
        sso.setShippingService("USPSPriority");
        sso.setShippingServicePriority(1);
        //sso.setShippingServiceCost(new ShippingServiceCost("USD", 1.00));
        //sso.setShippingServiceAdditionalCost(new ShippingServiceAdditionalCost("USD", 1));
        ShippingServiceOptions sso2 = new ShippingServiceOptions();
        //sso2.setFreeShipping(false);
        sso2.setShippingService("UPSGround");
        sso2.setShippingServicePriority(2);
        //sso2.setShippingServiceCost(new ShippingServiceCost("USD", 2.00));
        //sso2.setShippingServiceAdditionalCost(new ShippingServiceAdditionalCost("USD", 2));
        ShippingServiceOptions sso3 = new ShippingServiceOptions();
        sso3.setShippingService("UPSNextDay");
        sso3.setShippingServicePriority(3);
        List lisso = new ArrayList();
        lisso.add(sso);
        lisso.add(sso2);
        lisso.add(sso3);
        sd.setShippingServiceOptions(lisso);
        tem.setShippingDetails(sd);


        ItemSpecifics is = new ItemSpecifics();
        NameValueList nvl = new NameValueList();
        nvl.setName("Occasion");
        List livalue1 = new ArrayList();
        livalue1.add("Casual");
        nvl.setValue(livalue1);
        List linv = new ArrayList();
        NameValueList nvl1 = new NameValueList();
        nvl1.setName("Brand");
        List li1 = new ArrayList();
        li1.add("Ralph Lauren");
        nvl1.setValue(li1);
        NameValueList nvl2 = new NameValueList();
        nvl2.setName("Style");
        List li2 = new ArrayList();
        li2.add("Polo Shirt");
        nvl2.setValue(li2);
        NameValueList nvl3 = new NameValueList();
        nvl3.setName("Sleeve Style");
        List li3 = new ArrayList();
        li3.add("Short Sleeve");
        nvl3.setValue(li3);

        linv.add(nvl3);
        linv.add(nvl2);
        linv.add(nvl);
        linv.add(nvl1);
        is.setNameValueList(linv);
        tem.setItemSpecifics(is);

        PrimaryCategory pc = new PrimaryCategory();
        pc.setCategoryID("37565");
        tem.setPrimaryCategory(pc);

        Variations var = new Variations();
        VariationSpecificsSet vss = new VariationSpecificsSet();
        List livss = new ArrayList();
        NameValueList vssnv1 = new NameValueList();
        vssnv1.setName("Size");
        List liva1 = new ArrayList();
        liva1.add("XS");
        liva1.add("S");
        liva1.add("M");
        liva1.add("L");
        liva1.add("XL");
        vssnv1.setValue(liva1);

        NameValueList vssnv2 = new NameValueList();
        vssnv2.setName("Color");
        List liva2 = new ArrayList();
        liva2.add("Black");
        liva2.add("Pink");
        liva2.add("Yellow");
        liva2.add("Blue");
        vssnv2.setValue(liva2);
        livss.add(vssnv2);
        livss.add(vssnv1);
        vss.setNameValueList(livss);
        var.setVariationSpecificsSet(vss);
        Variation vtion1 = new Variation();
        List vtLi = new ArrayList();
        vtion1.setSKU("RLauren_Wom_TShirt_Pnk_S");
        vtion1.setStartPrice(new StartPrice("USD",17.99));
        vtion1.setQuantity(4);
        VariationSpecifics vs1 = new VariationSpecifics();
        List vs1li = new ArrayList();
        List vsli = new ArrayList();
        NameValueList vsnvl1 = new NameValueList();
        vsnvl1.setName("Color");
        List vsli1 = new ArrayList();
        vsli1.add("Pink");
        vsnvl1.setValue(vsli1);
        vs1li.add(vsnvl1);
        NameValueList vsnvl2 = new NameValueList();
        vsnvl2.setName("Size");
        List vsli2 = new ArrayList();
        vsli2.add("S");
        vsnvl2.setValue(vsli2);
        vs1li.add(vsnvl2);
        vs1.setNameValueList(vs1li);
        vsli.add(vs1);
        vtion1.setVariationSpecifics(vsli);

        Variation vtion2 = new Variation();
        vtion2.setSKU("RLauren_Wom_TShirt_Pnk_M");
        vtion2.setStartPrice(new StartPrice("USD", 17.99));
        vtion2.setQuantity(8);
        VariationSpecifics vs2 = new VariationSpecifics();
        List vs22 = new ArrayList();
        List vs2li = new ArrayList();
        NameValueList vs2nvl1 = new NameValueList();
        vs2nvl1.setName("Color");
        List vs2nvlli1=new ArrayList();
        vs2nvlli1.add("Pink");
        vs2nvl1.setValue(vs2nvlli1);
        NameValueList vs2nvl2 = new NameValueList();
        vs2nvl2.setName("Size");
        List vs2nvlli2=new ArrayList();
        vs2nvlli2.add("M");
        vs2nvl2.setValue(vs2nvlli2);
        vs2li.add(vs2nvl1);
        vs2li.add(vs2nvl2);
        vs2.setNameValueList(vs2li);
        vs22.add(vs2);
        vtion2.setVariationSpecifics(vs22);

        Variation vtion3 = new Variation();
        vtion3.setSKU("RLauren_Wom_TShirt_Blk_S");
        vtion3.setStartPrice(new StartPrice("USD",20.00));
        vtion3.setQuantity(10);
        VariationSpecifics vs3 = new VariationSpecifics();
        List vs33 = new ArrayList();
        List vs3li = new ArrayList();
        NameValueList vs3nvl1 = new NameValueList();
        vs3nvl1.setName("Color");
        List vs3nvlli1=new ArrayList();
        vs3nvlli1.add("Black");
        vs3nvl1.setValue(vs3nvlli1);
        NameValueList vs3nvl2 = new NameValueList();
        vs3nvl2.setName("Size");
        List vs3nvlli2=new ArrayList();
        vs3nvlli2.add("S");
        vs3nvl2.setValue(vs3nvlli2);
        vs3li.add(vs3nvl1);
        vs3li.add(vs3nvl2);
        vs3.setNameValueList(vs3li);
        vs33.add(vs3);
        vtion3.setVariationSpecifics(vs33);

        Variation vtion4 = new Variation();
        vtion4.setSKU("RLauren_Wom_TShirt_Blk_M");
        vtion4.setStartPrice(new StartPrice("USD",20.00));
        vtion4.setQuantity(10);
        VariationSpecifics vs4 = new VariationSpecifics();
        List vs44 = new ArrayList();
        List vs4li = new ArrayList();
        NameValueList vs4nvl1 = new NameValueList();
        vs4nvl1.setName("Color");
        List vs4nvlli1=new ArrayList();
        vs4nvlli1.add("Black");
        vs4nvl1.setValue(vs4nvlli1);
        NameValueList vs4nvl2 = new NameValueList();
        vs4nvl2.setName("Size");
        List vs4nvlli2=new ArrayList();
        vs4nvlli2.add("M");
        vs4nvl2.setValue(vs4nvlli2);
        vs4li.add(vs4nvl1);
        vs4li.add(vs4nvl2);
        vs4.setNameValueList(vs4li);
        vs44.add(vs4);
        vtion4.setVariationSpecifics(vs44);

        Variation vtion5 = new Variation();
        vtion5.setSKU("RLauren_Wom_TShirt_Blu_S");
        vtion5.setStartPrice(new StartPrice("USD",20.00));
        vtion5.setQuantity(10);
        VariationSpecifics vs5 = new VariationSpecifics();
        List vs55 = new ArrayList();
        List vs5li = new ArrayList();
        NameValueList vs5nvl1 = new NameValueList();
        vs5nvl1.setName("Color");
        List vs5nvlli1=new ArrayList();
        vs5nvlli1.add("Blue");
        vs5nvl1.setValue(vs5nvlli1);
        NameValueList vs5nvl2 = new NameValueList();
        vs5nvl2.setName("Size");
        List vs5nvlli2=new ArrayList();
        vs5nvlli2.add("S");
        vs5nvl2.setValue(vs5nvlli2);
        vs5li.add(vs5nvl1);
        vs5li.add(vs5nvl2);
        vs5.setNameValueList(vs5li);
        vs55.add(vs5);
        vtion5.setVariationSpecifics(vs55);

        Variation vtion6 = new Variation();
        vtion6.setSKU("RLauren_Wom_TShirt_Blu_M");
        vtion6.setStartPrice(new StartPrice("USD",20.00));
        vtion6.setQuantity(10);
        VariationSpecifics vs6 = new VariationSpecifics();
        List vs66 = new ArrayList();
        List vs6li = new ArrayList();
        NameValueList vs6nvl1 = new NameValueList();
        vs6nvl1.setName("Color");
        List vs6nvlli1=new ArrayList();
        vs6nvlli1.add("Blue");
        vs6nvl1.setValue(vs6nvlli1);
        NameValueList vs6nvl2 = new NameValueList();
        vs6nvl2.setName("Size");
        List vs6nvlli2=new ArrayList();
        vs6nvlli2.add("M");
        vs6nvl2.setValue(vs6nvlli2);
        vs6li.add(vs6nvl1);
        vs6li.add(vs6nvl2);
        vs6.setNameValueList(vs6li);
        vs66.add(vs6);
        vtion6.setVariationSpecifics(vs66);


        vtLi.add(vtion1);
        vtLi.add(vtion2);
        vtLi.add(vtion3);
        vtLi.add(vtion4);
        vtLi.add(vtion5);
        vtLi.add(vtion6);
        var.setVariation(vtLi);

        Pictures pic = new Pictures();
        pic.setVariationSpecificName("Color");
        VariationSpecificPictureSet vsps1 = new VariationSpecificPictureSet();
        vsps1.setVariationSpecificValue("Pink");
        List livsp1=new ArrayList();
        livsp1.add("http://i12.ebayimg.com/03/i/04/8a/5f/a1_1_sbl.JPG");
        livsp1.add("http://i12.ebayimg.com/03/i/04/8a/5f/a1_1_sb2.JPG");
        vsps1.setPictureURL(livsp1);

        VariationSpecificPictureSet vsps2 = new VariationSpecificPictureSet();
        vsps2.setVariationSpecificValue("Blue");
        List livsp2=new ArrayList();
        livsp2.add("http://i22.ebayimg.com/01/i/04/8e/53/69_1_sbl.JPG");
        livsp2.add("http://i22.ebayimg.com/01/i/04/8e/53/69_1_sb2.JPG");
        livsp2.add("http://i22.ebayimg.com/01/i/04/8e/53/69_1_sb3.JPG");
        vsps2.setPictureURL(livsp2);

        VariationSpecificPictureSet vsps3 = new VariationSpecificPictureSet();
        vsps3.setVariationSpecificValue("Black");
        List livsp3=new ArrayList();
        livsp3.add("http://i4.ebayimg.ebay.com/01/i/000/77/3c/d88f_1_sbl.JPG");
        vsps3.setPictureURL(livsp3);

        VariationSpecificPictureSet vsps4 = new VariationSpecificPictureSet();
        vsps4.setVariationSpecificValue("Yellow");
        List livsp4=new ArrayList();
        livsp4.add("http://i4.ebayimg.ebay.com/01/i/000/77/3c/d88f_1_sbl.JPG");
        vsps4.setPictureURL(livsp4);
        List vspli = new ArrayList();
        vspli.add(vsps1);
        vspli.add(vsps2);
        vspli.add(vsps3);
        vspli.add(vsps4);
        pic.setVariationSpecificPictureSet(vspli);
        var.setPictures(pic);
        tem.setVariations(var);
        addItem.setItem(tem);
        System.out.println(PojoXmlUtil.pojoToXml(addItem));

    }

    /**
     * 单属性
     */
    public static void testAddFixedPriceItemRequest(){
        AddFixedPriceItemRequest addItem = new AddFixedPriceItemRequest();
        addItem.setXmlns("urn:ebay:apis:eBLBaseComponents");
        addItem.setErrorLanguage("en_US");
        addItem.setWarningLevel("High");
        RequesterCredentials rc = new RequesterCredentials();
        rc.seteBayAuthToken("AgAAAA**AQAAAA**aAAAAA**wV1JUQ**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6wFk4GhCpGGoA+dj6x9nY+seQ**cx0CAA**AAMAAA**2kuzIn+bBej1QDsDFfI2N74mj8psZYNYrtgX97fzWSGXO7EjvdlE9leu9HCY1bR9wdrzlAE7AKcT9Oz5BDNZbNQLS+uoifmNUM47lSqxWeYTQS2GtMK25LPYhxY+OQp6UVZ8lUh6Oqr91ub03emzufuZHo+6KSNJfNXMtOBVaB7PDeBQyNWoFBO0/LYiS5ql6HXB7vCj0W+K/iT4t3aPs5KlXAXjewM/Sa+nUDtjT9SseqrKrxdZx5fkAePeSrBs229tdCrkTtE0n+ZE9ppwJjElZpu7yfQL44McNa16KBxYYO0PnX7ENg2yMxf3H4aji0BEfB41lrC1LwhmNSebJGrJXRQVS9jmZyDqYiBdn1t536va/LPTP8kc3GZ7hnZRJuhMxoGGgx4ev5Hip0L7dk6cAPKHIkHUIjfA5pwVHEJZpvea+7uvwAh5pj9U7r6rmB9FXH2G9l+F5SytYlIXsDjwNtrEN53k5HrM0vhnGdd7pUwvyu7Nu4U5aPkZQZjTr6OrTWioDsZZwEz+pf0scw0IYweMhicCqMTNbvkJsj2cikX49C6XSAcoUyrGtGa11vFChrifmq74dPZmUEtT1hDtwL1Ix3VPyZcJtTukKljxa0W0IwIe676X5HmiGhvk5qPPUImkXcZdQUK1gMdZmw0seMl5xmFG33kKVSD9H0p0JAEF4lOcDvjADQZtwLXY3qIhvYcKdOrIffrUAURnJRYnrB/MixizWvw252xBn9tmxpm68O3KsGBzcUwEB0Su");
        addItem.setRequesterCredentials(rc);
        Item tem = new Item();
        tem.setShippingTermsInDescription(false);
        List liship = new ArrayList();
        liship.add("CA");
        tem.setShipToLocations(liship);
        tem.setDescription("&lt;H3&gt;This is Description&lt;/H3&gt;");
        tem.setConditionID(1500);
        tem.setCountry("US");
        tem.setCurrency("USD");
        tem.setDispatchTimeMax(0);
        tem.setListingDuration("GTC");
        tem.setListingType("FixedPriceItem");
        List liPay = new ArrayList();
        liPay.add("PayPal");
        tem.setPaymentMethods(liPay);
        tem.setPayPalEmailAddress("caixu23@gmail.com");
        tem.setPostalCode("95125");
        tem.setTitle("Repair Replacement For Blackberry 3");
        tem.setSite("US");
        tem.setSKU("ZBQ702");
        tem.setQuantity(5);
        StartPrice sp = new StartPrice("USD",19.99);
        tem.setStartPrice(sp);
        tem.setGetItFast(false);

        BuyerRequirementDetails brd = new BuyerRequirementDetails();
        brd.setLinkedPayPalAccount(true);
        brd.setShipToRegistrationCountry(false);
        brd.setZeroFeedbackScore(true);
        brd.setMinimumFeedbackScore(-1);
        MaximumBuyerPolicyViolations mbpv = new MaximumBuyerPolicyViolations();
        mbpv.setCount(4);
        mbpv.setPeriod("Days_30");
        brd.setMaximumBuyerPolicyViolations(mbpv);

        MaximumItemRequirements mir = new MaximumItemRequirements();
        mir.setMaximumItemCount(1);
        mir.setMinimumFeedbackScore(1);
        brd.setMaximumItemRequirements(mir);

        MaximumUnpaidItemStrikesInfo muis = new MaximumUnpaidItemStrikesInfo();
        muis.setCount(2);
        muis.setPeriod("Days_30");
        brd.setMaximumUnpaidItemStrikesInfo(muis);

        VerifiedUserRequirements vur = new VerifiedUserRequirements();
        vur.setMinimumFeedbackScore(5);
        vur.setVerifiedUser(false);
        brd.setVerifiedUserRequirements(vur);

        tem.setBuyerRequirementDetails(brd);

        PictureDetails pd = new PictureDetails();
        List li = new ArrayList();
        li.add("http://i.ebayimg.com/00/s/NjAwWDgwMA==/z/RnwAAOSwBvNTn~M2/$_57.JPG");
        li.add("http://i.ebayimg.com/00/s/NjAwWDgwMA==/z/uXUAAOSw7PBTn~M4/$_57.JPG");
        pd.setPictureURL(li);
        tem.setPictureDetails(pd);

        ReturnPolicy rp = new ReturnPolicy();
        rp.setReturnsWithinOption("Days_60");
        rp.setShippingCostPaidByOption("Buyer");
        rp.setDescription("This is ReturnPolicy Desciption");
        rp.setRefundOption("MoneyBackOrReplacement");
        rp.setReturnsAcceptedOption("ReturnsAccepted");
        tem.setReturnPolicy(rp);

        ShippingDetails sd = new ShippingDetails();

        InternationalShippingServiceOption iss = new InternationalShippingServiceOption();
        iss.setShippingService("UPSWorldWideExpressPlus");
        iss.setShippingServicePriority(2);
        ShippingServiceAdditionalCost ssac = new ShippingServiceAdditionalCost("USD",4.00);
        ShippingServiceCost ssc = new ShippingServiceCost("USD",4.00);
        iss.setShippingServiceCost(ssc);
        iss.setShippingServiceAdditionalCost(ssac);
        List liShip = new ArrayList();
        liShip.add("CA");
        iss.setShipToLocation(liShip);
        List liss = new ArrayList();
        liss.add(iss);
        sd.setInternationalShippingServiceOption(liss);
        sd.setShippingType("Flat");

        ShippingServiceOptions sso = new ShippingServiceOptions();
        sso.setFreeShipping(false);
        sso.setShippingService("USPSParcel");
        sso.setShippingServicePriority(1);
        sso.setShippingServiceCost(new ShippingServiceCost("USD", 1.00));
        sso.setShippingServiceAdditionalCost(new ShippingServiceAdditionalCost("USD", 1));
        ShippingServiceOptions sso2 = new ShippingServiceOptions();
        sso2.setFreeShipping(false);
        sso2.setShippingService("USPSPriorityFlatRateBox");
        sso2.setShippingServicePriority(2);
        sso2.setShippingServiceCost(new ShippingServiceCost("USD", 2.00));
        sso2.setShippingServiceAdditionalCost(new ShippingServiceAdditionalCost("USD", 2));
        List lisso = new ArrayList();
        lisso.add(sso);
        lisso.add(sso2);
        sd.setShippingServiceOptions(lisso);
        tem.setShippingDetails(sd);


        ItemSpecifics is = new ItemSpecifics();
        NameValueList nvl = new NameValueList();
        nvl.setName("type");
        List livalue1 = new ArrayList();
        livalue1.add("phone");
        nvl.setValue(livalue1);
        List linv = new ArrayList();
        NameValueList nvl1 = new NameValueList();
        nvl1.setName("brand");
        List li1 = new ArrayList();
        li1.add("Blackberry");
        nvl1.setValue(li1);
        linv.add(nvl);
        linv.add(nvl1);
        is.setNameValueList(linv);
        tem.setItemSpecifics(is);

        PrimaryCategory pc = new PrimaryCategory();
        pc.setCategoryID("43304");
        tem.setPrimaryCategory(pc);

        addItem.setItem(tem);
        System.out.println(PojoXmlUtil.pojoToXml(addItem));

    }
}
