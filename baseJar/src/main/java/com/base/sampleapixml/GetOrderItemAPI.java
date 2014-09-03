package com.base.sampleapixml;

import com.base.database.trading.model.*;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.utils.common.DateUtils;
import com.base.utils.threadpool.AddApiTask;
import com.base.utils.xmlutils.SamplePaseXml;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.*;

/**
 * Created by Administrtor on 2014/8/16.
 */
public class GetOrderItemAPI {
    public static String ORDER_ITEM="orderItem";
    public static String LISTING_DETAILS="listingDetails";
    public static String ORDER_SELLER="orderSeller";
    public static String SELLER_INFORMATION="sellerInformation";
    public static String SELLING_STATUS="sellingStatus";
    public static String SHIPPING_DETAILS="shippingDetails";
    public static String SHIPPING_RATE="shippingRate";
    public static String SERVICE_OPTIONS="serviceOptions";
    public static String PICTURE_DETAILS="picturedetails";
    public static String RETURN_POLICY="orderReturnpolicy";
    public static String ITEM_SPECIFICS="itemSpecifics";
    public static String VARIATION="variation";
    public static String PICTURES="pictures";
    public static String VARIATION_SPECIFICS="variationspecificsset";

    public static Map<String,String> apiGetOrderItem(UsercontrollerDevAccountExtend d,String token,String url,String Itemid){
        Map map=new HashMap();
        map.put("token",token);
        map.put("Itemid",Itemid);
        String xml=BindAccountAPI.getGetOrderItem(map);
        AddApiTask addApiTask = new AddApiTask();
        Map<String, String> resMap = addApiTask.exec(d,xml,url);
        return resMap;
    }
    public static Map<String,Object> parseXMLAndSave(String res) throws Exception {
        Map<String,Object> map=new HashMap<String, Object>();
        Document document= DocumentHelper.parseText(res);
        Element root=document.getRootElement();
        Element Item=root.element("Item");
        TradingOrderGetItem orderItem=new TradingOrderGetItem();
        TradingOrderListingDetails listingDetails=new TradingOrderListingDetails();
        TradingOrderSeller orderSeller=new TradingOrderSeller();
        TradingOrderSellerInformation sellerInformation=new TradingOrderSellerInformation();
        TradingOrderSellingStatus sellingStatus=new TradingOrderSellingStatus();
        TradingOrderShippingDetails shippingDetails=new TradingOrderShippingDetails();
        TradingOrderCalculatedShippingRate shippingRate=new TradingOrderCalculatedShippingRate();
        /*TradingOrderShippingServiceOptions serviceOptions=new TradingOrderShippingServiceOptions();
        TradingOrderItemSpecifics itemSpecifics=new TradingOrderItemSpecifics();
        TradingOrderVariation variation=new TradingOrderVariation();*/
        TradingOrderPictureDetails picturedetails=new TradingOrderPictureDetails();
        TradingOrderReturnpolicy orderReturnpolicy=new TradingOrderReturnpolicy();
        /*TradingOrderPictures pictures=new TradingOrderPictures();*/
        orderItem.setAutopay(SamplePaseXml.getSpecifyElementText(Item,"AutoPay"));
        orderItem.setBuyerprotection(SamplePaseXml.getSpecifyElementText(Item,"BuyerProtection"));
        String nowprice=SamplePaseXml.getSpecifyElementText(Item, "BuyItNowPrice");
        if(nowprice!=null){
            orderItem.setBuyitnowprice(Double.valueOf(nowprice));
        }
        orderItem.setCountry(SamplePaseXml.getSpecifyElementText(Item,"Country"));
        orderItem.setCurrency(SamplePaseXml.getSpecifyElementText(Item,"Currency"));
        String giftIcon=SamplePaseXml.getSpecifyElementText(Item, "GiftIcon");
        if(giftIcon!=null){
            orderItem.setGifticon(Integer.valueOf(giftIcon));
        }
        orderItem.setHitcounter(SamplePaseXml.getSpecifyElementText(Item,"HitCounter"));
        orderItem.setItemid(SamplePaseXml.getSpecifyElementText(Item,"ItemID"));
        orderItem.setListingduration(SamplePaseXml.getSpecifyElementText(Item,"ListingDuration"));
        orderItem.setListingtype(SamplePaseXml.getSpecifyElementText(Item,"ListingType"));
        orderItem.setLocation(SamplePaseXml.getSpecifyElementText(Item,"Location"));
        orderItem.setPaymentmethods(SamplePaseXml.getSpecifyElementText(Item,"PaymentMethods"));
        orderItem.setCategoryid(SamplePaseXml.getSpecifyElementText(Item,"PrimaryCategory","CategoryID"));
        orderItem.setCategoryname(SamplePaseXml.getSpecifyElementText(Item,"PrimaryCategory","CategoryName"));
        orderItem.setPrivatelisting(SamplePaseXml.getSpecifyElementText(Item,"PrivateListing"));
        String quantity=SamplePaseXml.getSpecifyElementText(Item, "Quantity");
        if(quantity!=null){
            orderItem.setQuantity(Integer.valueOf(quantity));
        }
        orderItem.setItemrevised(SamplePaseXml.getSpecifyElementText(Item,"ReviseStatus","ItemRevised"));
        orderItem.setShiptolocations(SamplePaseXml.getSpecifyElementText(Item,"ShipToLocations"));
        orderItem.setSite(SamplePaseXml.getSpecifyElementText(Item,"Site"));
        String startPrice=SamplePaseXml.getSpecifyElementText(Item,"StartPrice");
        if(startPrice!=null){
            orderItem.setStartprice(Double.valueOf(startPrice));
        }
        orderItem.setTimeleft(SamplePaseXml.getSpecifyElementText(Item,"TimeLeft"));
        orderItem.setTitle(SamplePaseXml.getSpecifyElementText(Item,"Title"));
        String HitCount=SamplePaseXml.getSpecifyElementText(Item,"HitCount");
        if(HitCount!=null){
            orderItem.setHitcount(Integer.valueOf(HitCount));
        }
        orderItem.setLocationdefaulted(SamplePaseXml.getSpecifyElementText(Item,"LocationDefaulted"));
        orderItem.setGetitfast(SamplePaseXml.getSpecifyElementText(Item,"GetItFast"));
        orderItem.setPostalcode(SamplePaseXml.getSpecifyElementText(Item,"PostalCode"));
        String DispatchTimeMax=SamplePaseXml.getSpecifyElementText(Item,"DispatchTimeMax");
        if(DispatchTimeMax!=null){
            orderItem.setDispatchtimemax(Integer.valueOf(DispatchTimeMax));
        }
        orderItem.setProxyitem(SamplePaseXml.getSpecifyElementText(Item,"ProxyItem"));
        String BuyerGuaranteePrice=SamplePaseXml.getSpecifyElementText(Item,"BuyerGuaranteePrice");
        if(BuyerGuaranteePrice!=null){
            orderItem.setBuyerguaranteeprice(Double.valueOf(BuyerGuaranteePrice));
        }
        Iterator iterator=Item.elementIterator("PaymentAllowedSite");
        String PaymentAllowedSite="";
        while(iterator.hasNext()){
            Element AllowedSite= (Element) iterator.next();
            PaymentAllowedSite+=AllowedSite.getTextTrim()+",";
        }
        orderItem.setPaymentallowedsite(PaymentAllowedSite);
        map.put(ORDER_ITEM,orderItem);
        listingDetails.setAdult(SamplePaseXml.getSpecifyElementText(Item,"ListingDetails","Adult"));
        listingDetails.setBindingauction(SamplePaseXml.getSpecifyElementText(Item,"ListingDetails","BindingAuction"));
        listingDetails.setCheckoutenabled(SamplePaseXml.getSpecifyElementText(Item,"ListingDetails","CheckoutEnabled"));
        String ConvertedBuyItNowPrice=SamplePaseXml.getSpecifyElementText(Item,"ListingDetails","ConvertedBuyItNowPrice");
        if(ConvertedBuyItNowPrice!=null){
            listingDetails.setConvertedbuyitnowprice(Double.valueOf(ConvertedBuyItNowPrice));
        }
        String ConvertedStartPrice= SamplePaseXml.getSpecifyElementText(Item,"ListingDetails","ConvertedStartPrice");
        if(ConvertedStartPrice!=null){
            listingDetails.setConvertedstartprice(Double.valueOf(ConvertedStartPrice));
        }
       /* listingDetails.setAdult(SamplePaseXml.getSpecifyElementText(Item,"ListingDetails","ConvertedReservePrice"));*/
        listingDetails.setHasreserveprice(SamplePaseXml.getSpecifyElementText(Item,"ListingDetails","HasReservePrice"));
        String StartTime=SamplePaseXml.getSpecifyElementText(Item,"ListingDetails","StartTime");
        listingDetails.setStarttime(DateUtils.returnDate(StartTime));
        String EndTime=SamplePaseXml.getSpecifyElementText(Item, "ListingDetails", "EndTime");
        listingDetails.setEndtime(DateUtils.returnDate(EndTime));
        listingDetails.setViewitemurl(SamplePaseXml.getSpecifyElementText(Item, "ListingDetails", "ViewItemURL"));
        listingDetails.setHasunansweredquestions(SamplePaseXml.getSpecifyElementText(Item, "ListingDetails", "HasUnansweredQuestions"));
        listingDetails.setHaspublicmessages(SamplePaseXml.getSpecifyElementText(Item, "ListingDetails", "HasPublicMessages"));
        map.put(LISTING_DETAILS,listingDetails);
        orderSeller.setAboutmepage(SamplePaseXml.getSpecifyElementText(Item,"Seller","AboutMePage"));
        orderSeller.setEmail(SamplePaseXml.getSpecifyElementText(Item,"Seller","Email"));
        orderSeller.setFeedbackscore(SamplePaseXml.getSpecifyElementText(Item,"Seller","FeedbackScore"));
        String PositiveFeedbackPercent=SamplePaseXml.getSpecifyElementText(Item,"Seller","PositiveFeedbackPercent");
        if(PositiveFeedbackPercent!=null){
            orderSeller.setPositivefeedbackpercent(Double.valueOf(PositiveFeedbackPercent));
        }
        orderSeller.setFeedbackprivate(SamplePaseXml.getSpecifyElementText(Item,"Seller","FeedbackPrivate"));
        orderSeller.setFeedbackratingstar(SamplePaseXml.getSpecifyElementText(Item, "Seller", "FeedbackRatingStar"));
        orderSeller.setIdverified(SamplePaseXml.getSpecifyElementText(Item, "Seller", "IDVerified"));
        orderSeller.setEbaygoodstanding(SamplePaseXml.getSpecifyElementText(Item, "Seller", "eBayGoodStanding"));
        orderSeller.setNewuser(SamplePaseXml.getSpecifyElementText(Item, "Seller", "NewUser"));
        String RegistrationDate=SamplePaseXml.getSpecifyElementText(Item,"Seller","RegistrationDate");
        orderSeller.setRegistrationdate(DateUtils.returnDate(RegistrationDate));
        orderSeller.setSite(SamplePaseXml.getSpecifyElementText(Item, "Seller", "Site"));
        orderSeller.setStatus(SamplePaseXml.getSpecifyElementText(Item, "Seller", "Status"));
        orderSeller.setUserid(SamplePaseXml.getSpecifyElementText(Item, "Seller", "UserID"));
        orderSeller.setUseridchanged(SamplePaseXml.getSpecifyElementText(Item, "Seller", "UserIDChanged"));
        String UserIDLastChanged=SamplePaseXml.getSpecifyElementText(Item,"Seller","UserIDLastChanged");
        orderSeller.setUseridlastchanged(DateUtils.returnDate(UserIDLastChanged));
        orderSeller.setVatstatus(SamplePaseXml.getSpecifyElementText(Item, "Seller", "VATStatus"));
        orderSeller.setMotorsdealer(SamplePaseXml.getSpecifyElementText(Item, "Seller", "MotorsDealer"));
        map.put(ORDER_SELLER, orderSeller);
        sellerInformation.setAllowpaymentedit(SamplePaseXml.getSpecifyElementText(Item, "Seller", "SellerInfo", "AllowPaymentEdit"));
        sellerInformation.setCheckoutenabled(SamplePaseXml.getSpecifyElementText(Item, "Seller", "SellerInfo", "CheckoutEnabled"));
        sellerInformation.setCipbankaccountstored(SamplePaseXml.getSpecifyElementText(Item, "Seller", "SellerInfo", "CIPBankAccountStored"));
        sellerInformation.setGoodstanding(SamplePaseXml.getSpecifyElementText(Item, "Seller", "SellerInfo", "GoodStanding"));
        sellerInformation.setLiveauctionauthorized(SamplePaseXml.getSpecifyElementText(Item, "Seller", "SellerInfo", "LiveAuctionAuthorized"));
        sellerInformation.setMerchandizingpref(SamplePaseXml.getSpecifyElementText(Item, "Seller", "SellerInfo", "MerchandizingPref"));
        sellerInformation.setQualifiesforb2bvat(SamplePaseXml.getSpecifyElementText(Item, "Seller", "SellerInfo", "QualifiesForB2BVAT"));
        sellerInformation.setStoreowner(SamplePaseXml.getSpecifyElementText(Item, "Seller", "SellerInfo", "StoreOwner"));
        sellerInformation.setSafepaymentexempt(SamplePaseXml.getSpecifyElementText(Item, "Seller", "SellerInfo", "SafePaymentExempt"));
        sellerInformation.setSellerlevel(SamplePaseXml.getSpecifyElementText(Item, "Seller", "SellerInfo", "SellerLevel"));
        sellerInformation.setStoreurl(SamplePaseXml.getSpecifyElementText(Item, "Seller", "SellerInfo", "StoreURL"));
        sellerInformation.setExpresseligible(SamplePaseXml.getSpecifyElementText(Item, "Seller", "SellerInfo", "ExpressEligible"));
        sellerInformation.setExpresswallet(SamplePaseXml.getSpecifyElementText(Item, "Seller", "SellerInfo", "ExpressWallet"));
        map.put(SELLER_INFORMATION, sellerInformation);
        String BidCount=SamplePaseXml.getSpecifyElementText(Item, "SellingStatus", "BidCount");
        if(BidCount!=null){
            sellingStatus.setBidcount(Integer.valueOf(BidCount));
        }
        String BidIncrement=SamplePaseXml.getSpecifyElementText(Item, "SellingStatus", "BidIncrement");
        if(BidIncrement!=null){
            sellingStatus.setBidincrement(Double.valueOf(BidIncrement));
        }
        String ConvertedCurrentPrice=SamplePaseXml.getSpecifyElementText(Item,"SellingStatus","ConvertedCurrentPrice");
        if(ConvertedCurrentPrice!=null){
            sellingStatus.setConvertedcurrentprice(Double.valueOf(ConvertedCurrentPrice));
        }
        String CurrentPrice=SamplePaseXml.getSpecifyElementText(Item, "SellingStatus", "CurrentPrice");
        if(CurrentPrice!=null){
            sellingStatus.setCurrentprice(Double.valueOf(CurrentPrice));
        }
        String MinimumToBid=SamplePaseXml.getSpecifyElementText(Item,"SellingStatus","MinimumToBid");
        if(MinimumToBid!=null){
            sellingStatus.setMinimumtobid(Double.valueOf(MinimumToBid));
        }
        String QuantitySold=SamplePaseXml.getSpecifyElementText(Item,"SellingStatus","QuantitySold");
        if(QuantitySold!=null){
            sellingStatus.setQuantitysold(Integer.valueOf(QuantitySold));
        }
        sellingStatus.setReservemet(SamplePaseXml.getSpecifyElementText(Item,"SellingStatus","ReserveMet"));
        sellingStatus.setSecondchanceeligible(SamplePaseXml.getSpecifyElementText(Item,"SellingStatus","SecondChanceEligible"));
        sellingStatus.setListingstatus(SamplePaseXml.getSpecifyElementText(Item,"SellingStatus","ListingStatus"));
        map.put(SELLING_STATUS,sellingStatus);
        shippingDetails.setPaymentinstructions(SamplePaseXml.getSpecifyElementText(Item,"ShippingDetails","PaymentInstructions"));
        String SalesTaxPercent=SamplePaseXml.getSpecifyElementText(Item,"ShippingDetails","SalesTax","SalesTaxPercent");
        if(SalesTaxPercent!=null){
            shippingDetails.setSalestaxpercent(Double.valueOf(SalesTaxPercent));
        }
        shippingDetails.setSalestaxstate(SamplePaseXml.getSpecifyElementText(Item,"ShippingDetails","SalesTax","SalesTaxState"));
        shippingDetails.setShippingincludedintax(SamplePaseXml.getSpecifyElementText(Item, "ShippingDetails", "SalesTax", "ShippingIncludedInTax"));
        shippingDetails.setShippingtype(SamplePaseXml.getSpecifyElementText(Item, "ShippingDetails", "ShippingType"));
        shippingDetails.setThirdpartycheckout(SamplePaseXml.getSpecifyElementText(Item, "ShippingDetails", "ThirdPartyCheckout"));
        map.put(SHIPPING_DETAILS, shippingDetails);
        shippingRate.setOriginatingpostalcode(SamplePaseXml.getSpecifyElementText(Item,"ShippingDetails","CalculatedShippingRate","OriginatingPostalCode"));
        String PackageDepth=SamplePaseXml.getSpecifyElementText(Item,"ShippingDetails","CalculatedShippingRate","PackageDepth");
        if(PackageDepth!=null){
            shippingRate.setPackagedepth(Integer.valueOf(PackageDepth));
        }
        String PackageLength=SamplePaseXml.getSpecifyElementText(Item,"ShippingDetails","CalculatedShippingRate","PackageLength");
        if(PackageLength!=null){
            shippingRate.setPackagelength(Integer.valueOf(PackageLength));
        }
        String PackageWidth=SamplePaseXml.getSpecifyElementText(Item,"ShippingDetails","CalculatedShippingRate","PackageWidth");
        if(PackageWidth!=null){
            shippingRate.setPackagewidth(Integer.valueOf(PackageWidth));
        }
        String PackagingHandlingCosts=SamplePaseXml.getSpecifyElementText(Item,"ShippingDetails","CalculatedShippingRate","PackagingHandlingCosts");
        if(PackagingHandlingCosts!=null){
            shippingRate.setPackaginghandlingcosts(Double.valueOf(PackagingHandlingCosts));
        }
        shippingRate.setShippingirregular(SamplePaseXml.getSpecifyElementText(Item,"ShippingDetails","CalculatedShippingRate","ShippingIrregular"));
        shippingRate.setShippingpackage(SamplePaseXml.getSpecifyElementText(Item,"ShippingDetails","CalculatedShippingRate","ShippingPackage"));
        String WeightMajor=SamplePaseXml.getSpecifyElementText(Item,"ShippingDetails","CalculatedShippingRate","WeightMajor");
        if(WeightMajor!=null){
            shippingRate.setWeightmajor(Integer.valueOf(WeightMajor));
        }
        String WeightMinor=SamplePaseXml.getSpecifyElementText(Item,"ShippingDetails","CalculatedShippingRate","WeightMinor");
        if(WeightMinor!=null){
            shippingRate.setWeightminor(Integer.valueOf(WeightMinor));
        }
        map.put(SHIPPING_RATE,shippingRate);
        List<TradingOrderShippingServiceOptions> options=new ArrayList<TradingOrderShippingServiceOptions>();
        Element sh=Item.element("ShippingDetails");
        Iterator opt=sh.elementIterator("ShippingServiceOptions");
        while(opt.hasNext()){
            TradingOrderShippingServiceOptions serviceOptions=new TradingOrderShippingServiceOptions();
            Element option= (Element) opt.next();
            serviceOptions.setShippingservice(SamplePaseXml.getSpecifyElementText(option,"ShippingService"));
            String ShippingServicePriority=SamplePaseXml.getSpecifyElementText(option,"ShippingServicePriority");
            if(ShippingServicePriority!=null){
                serviceOptions.setShippingservicepriority(Integer.valueOf(ShippingServicePriority));
            }
            serviceOptions.setExpeditedservice(SamplePaseXml.getSpecifyElementText(option,"ExpeditedService"));
            String ShippingTimeMin=SamplePaseXml.getSpecifyElementText(option,"ShippingTimeMin");
            if(ShippingTimeMin!=null){
                serviceOptions.setShippingtimemin(Integer.valueOf(ShippingTimeMin));
            }
            String ShippingTimeMax=SamplePaseXml.getSpecifyElementText(option,"ShippingTimeMax");
            if(ShippingTimeMax!=null){
                serviceOptions.setShippingtimemax(Integer.valueOf(ShippingTimeMax));
            }
            serviceOptions.setFreeshipping(SamplePaseXml.getSpecifyElementText(option,"FreeShipping"));
            options.add(serviceOptions);
        }
        Element interoption=sh.element("InternationalShippingServiceOption");
        if(interoption!=null){
            TradingOrderShippingServiceOptions serviceOptions=new TradingOrderShippingServiceOptions();
            serviceOptions.setShippingservice(SamplePaseXml.getSpecifyElementText(interoption,"ShippingService"));
            String ShippingServicePriority=SamplePaseXml.getSpecifyElementText(interoption,"ShippingServicePriority");
            if(ShippingServicePriority!=null){
                serviceOptions.setShippingservicepriority(Integer.valueOf(ShippingServicePriority));
            }
            serviceOptions.setExpeditedservice(SamplePaseXml.getSpecifyElementText(interoption,"ExpeditedService"));
            String ShippingTimeMin=SamplePaseXml.getSpecifyElementText(interoption,"ShippingTimeMin");
            if(ShippingTimeMin!=null){
                serviceOptions.setShippingtimemin(Integer.valueOf(ShippingTimeMin));
            }
            String ShippingTimeMax=SamplePaseXml.getSpecifyElementText(interoption,"ShippingTimeMax");
            if(ShippingTimeMax!=null){
                serviceOptions.setShippingtimemax(Integer.valueOf(ShippingTimeMax));
            }
            serviceOptions.setFreeshipping(SamplePaseXml.getSpecifyElementText(interoption,"FreeShipping"));
            serviceOptions.setShiptolocation(SamplePaseXml.getSpecifyElementText(interoption,"ShipToLocation"));
            options.add(serviceOptions);
        }
        map.put(SERVICE_OPTIONS,options);
        picturedetails.setGallerytype(SamplePaseXml.getSpecifyElementText(Item, "PictureDetails", "GalleryType"));
        picturedetails.setGalleryurl(SamplePaseXml.getSpecifyElementText(Item, "PictureDetails", "GalleryURL"));
        picturedetails.setPhotodisplay(SamplePaseXml.getSpecifyElementText(Item, "PictureDetails", "PhotoDisplay"));
        Element pic=Item.element("PictureDetails");
        Iterator pics=pic.elementIterator("PictureURL");
        List<String> lists=new ArrayList<String>();
        while(pics.hasNext()){
            Element p= (Element) pics.next();
            String pp=p.getTextTrim();
            lists.add(pp);
            if(lists.size()==1){
                picturedetails.setPictureurl(lists.get(0));
            }else if(lists.size()==2){
                picturedetails.setPictureurl1(lists.get(1));
            }else if(lists.size()==3){
                picturedetails.setPictureurl2(lists.get(2));
            }
        }

        map.put(PICTURE_DETAILS,picturedetails);
        orderReturnpolicy.setRefundoption(SamplePaseXml.getSpecifyElementText(Item,"ReturnPolicy","RefundOption"));
        orderReturnpolicy.setRefund(SamplePaseXml.getSpecifyElementText(Item,"ReturnPolicy","Refund"));
        orderReturnpolicy.setReturnswithinoption(SamplePaseXml.getSpecifyElementText(Item,"ReturnPolicy","ReturnsWithinOption"));
        orderReturnpolicy.setReturnswithin(SamplePaseXml.getSpecifyElementText(Item,"ReturnPolicy","ReturnsWithin"));
        orderReturnpolicy.setReturnsacceptedoption(SamplePaseXml.getSpecifyElementText(Item,"ReturnPolicy","ReturnsAcceptedOption"));
        orderReturnpolicy.setReturnsaccepted(SamplePaseXml.getSpecifyElementText(Item,"ReturnPolicy","ReturnsAccepted"));
        orderReturnpolicy.setDescription(SamplePaseXml.getSpecifyElementText(Item,"ReturnPolicy","Description"));
        orderReturnpolicy.setShippingcostpaidbyoption(SamplePaseXml.getSpecifyElementText(Item,"ReturnPolicy","ShippingCostPaidByOption"));
        orderReturnpolicy.setShippingcostpaidby(SamplePaseXml.getSpecifyElementText(Item,"ReturnPolicy","ShippingCostPaidBy"));
        map.put(RETURN_POLICY,orderReturnpolicy);
        Element ItemSpecifics=Item.element("ItemSpecifics");
        List<TradingOrderItemSpecifics> specificList=new ArrayList<TradingOrderItemSpecifics>();
        if(ItemSpecifics!=null){
            Iterator Specifics=ItemSpecifics.elementIterator("NameValueList");
            while(Specifics.hasNext()){
                Element specific= (Element) Specifics.next();
                TradingOrderItemSpecifics itemSpecifics=new TradingOrderItemSpecifics();
                itemSpecifics.setName(SamplePaseXml.getSpecifyElementText(specific,"Name"));
                itemSpecifics.setValue(SamplePaseXml.getSpecifyElementText(specific,"Value"));
                specificList.add(itemSpecifics);
            }
        }
        map.put(ITEM_SPECIFICS,specificList);
        Element Variations=Item.element("Variations");
        List<TradingOrderVariation> variationList=new ArrayList<TradingOrderVariation>();
        List<TradingOrderVariationSpecifics> specificsList=new ArrayList<TradingOrderVariationSpecifics>();
        if(Variations!=null){
            Iterator Variation=Variations.elementIterator("Variation");
            while(Variation.hasNext()){
                Element varEl= (Element) Variation.next();
                TradingOrderVariation variation=new TradingOrderVariation();
                variation.setSku(SamplePaseXml.getSpecifyElementText(varEl,"SKU"));
                String StartPrice=SamplePaseXml.getSpecifyElementText(varEl, "StartPrice");
                if(StartPrice!=null){
                    variation.setStartprice(Double.valueOf(StartPrice));
                }
                String Quantity=SamplePaseXml.getSpecifyElementText(varEl, "Quantity");
                if(Quantity!=null){
                    variation.setQuantity(Integer.valueOf(Quantity));
                }
                String Quantitysold=SamplePaseXml.getSpecifyElementText(varEl, "SellingStatus", "QuantitySold");
                if(Quantitysold!=null){
                    variation.setQuantitysold(Integer.valueOf(Quantitysold));
                }
                variationList.add(variation);
                Element specificsEl=varEl.element("VariationSpecifics");
                Iterator valueList=specificsEl.elementIterator("NameValueList");
                while(valueList.hasNext()){
                    TradingOrderVariationSpecifics specifics=new TradingOrderVariationSpecifics();
                    Element s= (Element) valueList.next();
                    specifics.setName(SamplePaseXml.getSpecifyElementText(s,"Name"));
                    specifics.setValue(SamplePaseXml.getSpecifyElementText(s,"Value"));
                    specificsList.add(specifics);
                }
            }
        }
        List<TradingOrderPictures> pictrueList=new ArrayList<TradingOrderPictures>();
        if(Variations!=null){
            Element Pictures=Variations.element("Pictures");
            Iterator pictureSet=Pictures.elementIterator("VariationSpecificPictureSet");
            while(pictureSet.hasNext()){
                Element picturnEl= (Element) pictureSet.next();
                TradingOrderPictures pictures=new TradingOrderPictures();
                pictures.setVariationspecificname(SamplePaseXml.getSpecifyElementText(Pictures,"VariationSpecificName"));
                pictures.setVariationspecificvalue(SamplePaseXml.getSpecifyElementText(picturnEl,"VariationSpecificValue"));
                pictures.setPictureurl(SamplePaseXml.getSpecifyElementText(picturnEl,"PictureURL"));
                pictrueList.add(pictures);
            }
        }
        map.put(VARIATION,variationList);
        map.put(PICTURES,pictrueList);
        map.put(VARIATION_SPECIFICS,specificsList);
       /* listingDetails.set(SamplePaseXml.getSpecifyElementText(Item,"ListingDetails","ViewItemURLForNaturalSearch"));*/
        return map;
    }
}
