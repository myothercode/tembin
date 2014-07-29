package com.test.service.impl;

import com.base.database.trading.mapper.TradingPicturesMapper;
import com.base.database.trading.model.TradingBuyerRequirementDetails;
import com.base.database.trading.model.TradingItem;
import com.base.database.trading.model.TradingPictures;
import com.base.database.trading.model.TradingReturnpolicy;
import com.base.mybatis.page.Page;
import com.base.utils.common.ConvertPOJOUtil;
import com.base.utils.exception.Asserts;
import com.base.xmlpojo.trading.addproduct.*;
import com.base.xmlpojo.trading.addproduct.attrclass.StartPrice;
import com.test.mapper.TestMapper;
import com.test.service.TestService;
import com.trading.service.ITradingBuyerRequirementDetails;
import com.trading.service.ITradingItem;
import com.trading.service.ITradingReturnpolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by chace.cai on 2014/7/8.
 */
@Service("testService")
@Transactional(rollbackFor = Exception.class)
public class TestServiceImpl implements TestService {
    @Autowired
    private TestMapper testMapper;
    @Autowired
    private ITradingReturnpolicy itradingReturnpolicy;
    @Autowired
    private TradingPicturesMapper tradingPicturesMapper;
    @Autowired
    private ITradingItem iTradingItem;
    @Autowired
    private ITradingBuyerRequirementDetails iTradingBuyerRequirementDetails;


    @Override
   // @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void serviceTest(){
        Map map=new HashMap();
        map.put("id","1");
        map.put("StringV","xxx");
        Page page=new Page();
        page.setCurrentPage(2);
        page.setPageSize(3);
        List<String> strings = testMapper.queryTest(map,page);


        TradingPictures tradingPictures=new TradingPictures();
        tradingPictures.setCreateTime(new Date());
        tradingPictures.setUuid("hhh");
        tradingPictures.setVariationspecificname("pp");
        tradingPicturesMapper.insertSelective(tradingPictures);

        //testMapper.updateTest(map);
        //Asserts.assertTrue(false,"错误");
        //throw new RuntimeException("d");
    }

    @Override
    public void testReturnPolicy() throws Exception {

       /* ReturnPolicy rp = new ReturnPolicy();
        rp.setReturnsWithinOption("abcd");
        rp.setShippingCostPaidByOption("tcds");
        rp.setDescription("asfasdf");
        rp.setRefundOption("54546");
        rp.setReturnsAcceptedOption("afdasdfafdsfs");
        rp.setExtendedHolidayReturns(true);*/
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
        TradingItem pojo = this.iTradingItem.toDAOPojo(tem);
        //ConvertPOJOUtil.convert(pojo,tem);

        TradingBuyerRequirementDetails tbrd  = this.iTradingBuyerRequirementDetails.toDAOPojo(brd);
        iTradingBuyerRequirementDetails.saveBuyerRequirementDetails(tbrd);

        TradingReturnpolicy trys = this.itradingReturnpolicy.toDAOPojo(rp);
        this.itradingReturnpolicy.saveTradingReturnpolicy(trys);

        pojo.setReturnpolicyId(trys.getId());
        pojo.setBuyerId(tbrd.getId());

        this.iTradingItem.saveTradingItem(pojo);

        //TradingReturnpolicy tr = this.itradingReturnpolicy.toDAOPojo(rp);
        //this.itradingReturnpolicy.saveTradingReturnpolicy(tr);

    }
}
