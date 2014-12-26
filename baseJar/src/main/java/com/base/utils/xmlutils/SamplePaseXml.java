package com.base.utils.xmlutils;

import com.base.database.publicd.model.PublicDataDict;
import com.base.database.trading.model.*;
import com.base.utils.common.DateUtils;
import com.base.utils.common.DictCollectionsUtil;
import com.base.xmlpojo.trading.addproduct.*;
import com.base.xmlpojo.trading.addproduct.attrclass.ShippingServiceAdditionalCost;
import com.base.xmlpojo.trading.addproduct.attrclass.ShippingServiceCost;
import com.base.xmlpojo.trading.addproduct.attrclass.ShippingSurcharge;
import com.base.xmlpojo.trading.addproduct.attrclass.StartPrice;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.impl.cookie.DateParseException;
import org.dom4j.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrtor on 2014/8/7.
 * 解析结构比较简单的xml
 */
public class SamplePaseXml {
    public static String getVFromXmlString(String xml,String nodeName) throws Exception {
        //ByteArrayInputStream is = new ByteArrayInputStream(res.getBytes());//文件
        Document document= DocumentHelper.parseText(xml);
        Element rootElt = document.getRootElement();
        //Iterator iter = rootElt.elementIterator("SessionID");
        Element e =  rootElt.element(nodeName);
        if(e==null){return null;}
        return e.getTextTrim();
    }

    /**
     * 查询商品明细
     * @param xml
     * @return
     * @throws DocumentException
     */
    public static Item getItem(String xml) throws DocumentException {
        Item item = new Item();
        Document document= DocumentHelper.parseText(xml);
        Element rootElt = document.getRootElement();
        Element element = rootElt.element("Item");
        item.setTitle(element.elementText("Title"));
        item.setCurrency(element.elementText("Currency"));
        item.setCountry(element.elementText("Country"));
        item.setSite(element.elementText("Site"));
        item.setPostalCode(element.elementText("PostalCode"));
        item.setLocation(element.elementText("Location"));
        item.setItemID(element.elementText("ItemID"));
        item.setHitCounter(element.elementText("HitCounter"));
        item.setAutoPay(element.elementText("AutoPay").equals("true")?true:false);
        item.setGiftIcon(element.elementText("GiftIcon"));
        item.setListingDuration(element.elementText("ListingDuration"));
        item.setQuantity(Integer.parseInt(element.elementText("Quantity")));
        item.setPayPalEmailAddress(element.elementText("PayPalEmailAddress"));
        item.setGetItFast("false".equals(element.elementText("GetItFast"))?false:true);
        item.setPrivateListing("true".equals(element.elementText("PrivateListing"))?true:false);
        item.setDispatchTimeMax(Integer.parseInt(element.elementText("DispatchTimeMax")));
        item.setListingDuration(element.elementText("ListingDuration"));
        item.setDescription(element.elementText("Description"));
        item.setSKU(element.elementText("SKU"));
        item.setConditionID(Integer.parseInt(element.elementText("CategoryID")==null?"1000":element.elementText("CategoryID")));
        PrimaryCategory pc = new PrimaryCategory();
        pc.setCategoryID(element.element("PrimaryCategory").elementText("CategoryID"));

        item.setPrimaryCategory(pc);
        String listType = "";
        if("FixedPriceItem".equals(element.elementText("ListingType"))){
            if(element.element("Variations")!=null){
                listType = "2";
            }else{
                listType = element.elementText("ListingType");
            }
        }else{
            listType = element.elementText("ListingType");
        }
        item.setListingType(listType);
        //自定义属性
        Element elspe = element.element("ItemSpecifics");
        if(elspe!=null){
            ItemSpecifics itemSpecifics = new ItemSpecifics();
            Iterator<Element> itnvl = elspe.elementIterator("NameValueList");
            List<NameValueList> linvl = new ArrayList<NameValueList>();
            while (itnvl.hasNext()){
                Element nvl = itnvl.next();
                NameValueList nvli = new NameValueList();
                nvli.setName(nvl.elementText("Name"));
                List<String> listr = new ArrayList();
                Iterator<Element> itval = nvl.elementIterator("Value");
                while (itval.hasNext()){
                    listr.add(itval.next().getText());
                }
                nvli.setValue(listr);
                linvl.add(nvli);
            }
            itemSpecifics.setNameValueList(linvl);
        }
        //图片信息
        Element pice = element.element("PictureDetails");
        if(pice!=null){
            PictureDetails pd = new PictureDetails();
            if(pice.elementText("GalleryType")!=null){
                pd.setGalleryType(pice.elementText("GalleryType"));
            }
            if(pice.elementText("PhotoDisplay")!=null){
                pd.setPhotoDisplay(pice.elementText("PhotoDisplay"));
            }
            if(pice.elementText("GalleryURL")!=null){
                String url = pice.elementText("GalleryURL");
                if(url.indexOf("?")>0){
                    url.substring(0,url.indexOf("?"));
                }
                pd.setGalleryURL(url);
            }
            Iterator<Element> itpicurl = pice.elementIterator("PictureURL");
            List<String> urlli = new ArrayList();
            while (itpicurl.hasNext()){
                Element url = itpicurl.next();
                String urlstr = url.getStringValue();
                if(urlstr.indexOf("?")>0){
                    urlstr = urlstr.substring(0,urlstr.indexOf("?"));
                }
                urlli.add(urlstr);
            }
            pd.setPictureURL(urlli);
            item.setPictureDetails(pd);
        }
        //取得退货政策并封装
        Element returne = element.element("ReturnPolicy");
        ReturnPolicy rp = new ReturnPolicy();
        rp.setRefundOption(returne.elementText("RefundOption"));
        rp.setReturnsWithinOption(returne.elementText("ReturnsWithinOption"));
        rp.setReturnsAcceptedOption(returne.elementText("ReturnsAcceptedOption"));
        rp.setDescription(returne.elementText("Description"));
        rp.setShippingCostPaidByOption(returne.elementText("ShippingCostPaidByOption"));
        item.setReturnPolicy(rp);
        //买家要求
        BuyerRequirementDetails brd = new BuyerRequirementDetails();
        MaximumItemRequirements mirs = new MaximumItemRequirements();
        Element buyere = element.element("BuyerRequirementDetails");
        if(buyere!=null) {
            Element maxiteme = buyere.element("MaximumItemRequirements");
            if(maxiteme!=null) {
                if (maxiteme.elementText("MaximumItemCount") != null) {
                    mirs.setMaximumItemCount(Integer.parseInt(maxiteme.elementText("MaximumItemCount")));
                }
                if (maxiteme.elementText("MinimumFeedbackScore") != null) {
                    mirs.setMinimumFeedbackScore(Integer.parseInt(maxiteme.elementText("MinimumFeedbackScore")));
                }
                brd.setMaximumItemRequirements(mirs);
            }

            Element maxUnpaid = buyere.element("MaximumUnpaidItemStrikesInfo");
            MaximumUnpaidItemStrikesInfo muis = new MaximumUnpaidItemStrikesInfo();
            String count = maxUnpaid.elementText("Count");
            muis.setCount(Integer.parseInt(count));
            muis.setPeriod(maxUnpaid.elementText("Period"));
            brd.setMaximumUnpaidItemStrikesInfo(muis);

            Element maxPolicy = buyere.element("MaximumBuyerPolicyViolations");
            MaximumBuyerPolicyViolations mbpv= new MaximumBuyerPolicyViolations();
            mbpv.setCount(Integer.parseInt(maxPolicy.elementText("Count")));
            mbpv.setPeriod(maxPolicy.elementText("Period"));
            brd.setMaximumBuyerPolicyViolations(mbpv);
            if(buyere.elementText("LinkedPayPalAccount")!=null) {
                brd.setLinkedPayPalAccount(buyere.elementText("LinkedPayPalAccount").equals("true") ? true : false);
            }
            if(buyere.elementText("ShipToRegistrationCountry")!=null) {
                brd.setShipToRegistrationCountry(buyere.elementText("ShipToRegistrationCountry").equals("true") ? true : false);
            }
            item.setBuyerRequirementDetails(brd);
        }
        //运输选项
        ShippingDetails sd = new ShippingDetails();
        Element elsd = element.element("ShippingDetails");
        Iterator<Element> itershipping = elsd.elementIterator("ShippingServiceOptions");
        List<ShippingServiceOptions> lisso = new ArrayList();
        //国内运输
        while (itershipping.hasNext()){
            Element shipping = itershipping.next();
            ShippingServiceOptions sso = new ShippingServiceOptions();
            sso.setShippingService(shipping.elementText("ShippingService"));
            if(shipping.elementText("ShippingServiceCost")!=null){
                sso.setShippingServiceCost(new ShippingServiceCost(shipping.attributeValue("currencyID"),Double.parseDouble(shipping.elementText("ShippingServiceCost"))));
            }
            sso.setShippingServicePriority(Integer.parseInt(shipping.elementText("ShippingServicePriority")));
            if(shipping.elementText("FreeShipping")!=null){
                sso.setFreeShipping(shipping.elementText("FreeShipping").equals("true")?true:false);
            }
            ShippingServiceAdditionalCost ssac= new ShippingServiceAdditionalCost();
            ssac.setValue(Double.parseDouble(shipping.elementText("ShippingServiceAdditionalCost")!=null?shipping.elementText("ShippingServiceAdditionalCost"):"0"));
            sso.setShippingServiceAdditionalCost(ssac);
            ShippingSurcharge ss =new ShippingSurcharge();
            ss.setValue(Double.parseDouble(shipping.elementText("ShippingSurcharge")!=null?shipping.elementText("ShippingSurcharge"):"0"));
            sso.setShippingSurcharge(ss);
            lisso.add(sso);
        }
        if(lisso.size()>0){
            sd.setShippingServiceOptions(lisso);
        }
        //国际运输
        Iterator<Element> iteInt = elsd.elementIterator("InternationalShippingServiceOption");
        List<InternationalShippingServiceOption> liint = new ArrayList<InternationalShippingServiceOption>();
        while (iteInt.hasNext()){
            Element intel = iteInt.next();
            InternationalShippingServiceOption isso = new InternationalShippingServiceOption();
            isso.setShippingService(intel.elementText("ShippingService"));
            isso.setShippingServiceCost(new ShippingServiceCost(intel.attributeValue("currencyID"),Double.parseDouble(intel.elementText("ShippingServiceCost"))));
            Iterator<Element> iteto = intel.elementIterator("ShipToLocation");
            List<String> listr = new ArrayList();
            while (iteto.hasNext()){
                listr.add(iteto.next().getText());
            }
            isso.setShipToLocation(listr);
            isso.setShippingServicePriority(Integer.parseInt(intel.elementText("ShippingServicePriority")));

            ShippingServiceAdditionalCost ssac= new ShippingServiceAdditionalCost();
            ssac.setValue(Double.parseDouble(intel.elementText("ShippingServiceAdditionalCost")!=null?intel.elementText("ShippingServiceAdditionalCost"):"0"));
            isso.setShippingServiceAdditionalCost(ssac);
            liint.add(isso);
        }
        if(liint.size()>0){
            sd.setInternationalShippingServiceOption(liint);
        }
        sd.setShippingType(elsd.elementText("ShippingType"));
        item.setShippingDetails(sd);
        //卖家信息
        Seller seller = new Seller();
        Element elsel = element.element("Seller");
        seller.setUserID(elsel.elementText("UserID"));
        seller.setEmail(elsel.elementText("Email"));
        item.setSeller(seller);
        //多属性
        Element vartions = element.element("Variations");
        if(vartions!=null){
            Iterator<Element> elvar = vartions.elementIterator("Variation");
            List<Variation> livar = new ArrayList();
            while (elvar.hasNext()){
                Element ele = elvar.next();
                Variation var = new Variation();
                var.setSKU(ele.elementText("SKU"));
                var.setQuantity(Integer.parseInt(ele.elementText("Quantity")));
                var.setStartPrice(new StartPrice(ele.attributeValue("currencyID"), Double.parseDouble(ele.elementText("StartPrice"))));
                Element elvs = ele.element("VariationSpecifics");
                Iterator<Element> elnvl = elvs.elementIterator("NameValueList");
                List<NameValueList> linvl = new ArrayList();
                while (elnvl.hasNext()){
                    Element elment = elnvl.next();
                    NameValueList nvl = new NameValueList();
                    nvl.setName(elment.elementText("Name"));
                    List<String> li = new ArrayList<String>();
                    li.add(elment.elementText("Value"));
                    nvl.setValue(li);
                    linvl.add(nvl);
                }
                List<VariationSpecifics> livs = new ArrayList();
                VariationSpecifics vs = new VariationSpecifics();
                vs.setNameValueList(linvl);
                livs.add(vs);
                var.setVariationSpecifics(livs);
                livar.add(var);
            }
            Variations vtions = new Variations();
            vtions.setVariation(livar);
            //多属性值
            Element elvss = vartions.element("VariationSpecificsSet");
            Iterator<Element> itele = elvss.elementIterator("NameValueList");
            List<NameValueList> linvl = new ArrayList();
            while (itele.hasNext()){
                Element nvlel = itele.next();
                NameValueList nvl=new NameValueList();
                nvl.setName(nvlel.elementText("Name"));
                Iterator<Element> itvalue = nvlel.elementIterator("Value");
                List<String> livalue = new ArrayList();
                while (itvalue.hasNext()){
                    Element value = itvalue.next();
                    livalue.add(value.getText());
                }
                nvl.setValue(livalue);
                linvl.add(nvl);
            }
            VariationSpecificsSet vss = new VariationSpecificsSet();
            vss.setNameValueList(linvl);
            vtions.setVariationSpecificsSet(vss);
            //多属性图片信息
            Pictures pic = new Pictures();
            Element elpic = vartions.element("Pictures");
            pic.setVariationSpecificName(elpic.elementText("VariationSpecificName"));
            Iterator<Element> iturl = elpic.elementIterator("VariationSpecificPictureSet");
            List<VariationSpecificPictureSet> livsps = new ArrayList();
            while (iturl.hasNext()){
                Element urle = iturl.next();
                VariationSpecificPictureSet vsps = new VariationSpecificPictureSet();
                vsps.setVariationSpecificValue(urle.elementText("VariationSpecificValue"));
                Iterator<Element> url = urle.elementIterator("PictureURL");
                List li = new ArrayList();
                while (url.hasNext()){
                    Element e = url.next();
                    String urlstr = e.getText();
                    if(urlstr.indexOf("?")>0){
                        urlstr = urlstr.substring(0,urlstr.indexOf("?"));
                    }
                    li.add(urlstr);
                }
                vsps.setPictureURL(li);
                livsps.add(vsps);
            }
            pic.setVariationSpecificPictureSet(livsps);
            vtions.setPictures(pic);

            item.setVariations(vtions);
        }else{
            Element el = element.element("StartPrice");
            item.setStartPrice(new StartPrice(el.attributeValue("currencyID"),Double.parseDouble(el.getText())));
            if(element.element("BuyItNowPrice")!=null) {
                item.setBuyItNowPrice(Double.parseDouble(element.elementText("BuyItNowPrice")));
            }
            if(element.element("ReservePrice")!=null){
                item.setReservePrice(Double.parseDouble(element.elementText("ReservePrice")));
            }
        }
        return item;
    }

    /**
     * 定时从在线商品中同步数据下来，行成在线数据
     * @param xml
     * @return
     * @throws DocumentException
     */
    public static List<TradingListingData> getItemListElememt(String xml,String ebayAccount) throws DocumentException, DateParseException {
        List li = new ArrayList();
        Document document= DocumentHelper.parseText(xml);
        Element rootElt = document.getRootElement();
        Element recommend = rootElt.element("ItemArray");
        Iterator<Element> iter = recommend.elementIterator("Item");
        String listType = "";
        while(iter.hasNext()){
            TradingListingData item = new TradingListingData();
            Element element = iter.next();
            item.setTitle(StringEscapeUtils.escapeXml(element.elementText("Title")));
            item.setItemId(element.elementText("ItemID"));
            item.setSite(element.elementText("Site"));
            item.setSku(element.elementText("SKU"));
            item.setEbayAccount(ebayAccount);
            if("FixedPriceItem".equals(element.elementText("ListingType"))){
                if(element.element("Variations")!=null){
                    listType = "2";
                }else{
                    listType = element.elementText("ListingType");
                }
            }else{
                listType = element.elementText("ListingType");
            }
            item.setListingType(listType);
            item.setPrice(Double.parseDouble(element.element("SellingStatus").elementText("CurrentPrice")));
            Element shippingdes = element.element("ShippingDetails");
            if(shippingdes!=null){
                List<Element> shippingit = shippingdes.elements("ShippingServiceOptions");
                Element shippingOption = shippingit.get(0);
                if(shippingOption!=null){
                    Element option  = shippingOption.element("ShippingServiceCost");
                    if(option!=null){
                        item.setShippingPrice(Double.parseDouble(option.getText()));
                    }else{
                        item.setShippingPrice(0.00);
                    }
                }else{
                    item.setShippingPrice(0.00);
                }
            }else{
                item.setShippingPrice(0.00);
            }
            item.setQuantity(Long.parseLong(element.elementText("Quantity")));
            item.setQuantitysold(Long.parseLong(element.element("SellingStatus").elementText("QuantitySold")));
            Element elflag = element.element("SellingStatus").element("ListingStatus");
            if(elflag!=null){
                if(elflag.getText().equals("Active")){
                    item.setIsFlag("0");
                }else{
                    item.setIsFlag("1");
                }
            }else{
                item.setIsFlag("1");
            }
            item.setSubtitle("");
            item.setBuyitnowprice(Double.parseDouble(element.element("ListingDetails").elementText("ConvertedBuyItNowPrice")));
            item.setReserveprice(Double.parseDouble(element.element("ListingDetails").elementText("ConvertedReservePrice")));
            item.setListingduration(element.elementText("ListingDuration"));
            item.setStarttime(DateUtils.parseDateTime(element.element("ListingDetails").elementText("StartTime").replace("T"," ").replace(".000Z","")));
            item.setEndtime(DateUtils.parseDateTime(element.element("ListingDetails").elementText("EndTime").replace("T"," ").replace(".000Z","")));
            String url = element.element("PictureDetails").elementText("GalleryURL");

            //item.setPicUrl(url.substring(0,url.lastIndexOf("_")+1)+"14"+url.substring(url.lastIndexOf(".")));
            item.setPicUrl("http://thumbs.ebaystatic.com/pict/"+item.getItemId()+".jpg");
            li.add(item);
        }
        return li;
    }

    /**
     * 封装在线商品列表
     * @param xml
     * @return
     * @throws DocumentException
     */
    public static List<Item> getItemElememt(String xml) throws DocumentException {
        List li = new ArrayList();
        Document document= DocumentHelper.parseText(xml);
        Element rootElt = document.getRootElement();
        Element recommend = rootElt.element("ItemArray");
        Iterator<Element> iter = recommend.elementIterator("Item");
        while(iter.hasNext()){
            Item item = new Item();
            Element element = iter.next();
            Element elflag = element.element("SellingStatus").element("ListingStatus");
            if(elflag!=null){//如查商品不在线，就不取在线商品
                if(elflag.getText().equals("Active")){

                }else{
                    continue;
                }
            }
            item.setTitle(element.elementText("Title"));
            item.setCurrency(element.elementText("Currency"));
            item.setCountry(element.elementText("Country"));
            item.setSite(element.elementText("Site"));
            item.setPostalCode(element.elementText("PostalCode"));
            item.setLocation(element.elementText("Location"));
            item.setItemID(element.elementText("ItemID"));
            item.setHitCounter(element.elementText("HitCounter"));
            item.setAutoPay(element.elementText("AutoPay").equals("true") ? true : false);
            item.setGiftIcon(element.elementText("GiftIcon"));
            item.setListingDuration(element.elementText("ListingDuration"));
            item.setQuantity(Integer.parseInt(element.elementText("Quantity")));
            item.setSKU(element.elementText("SKU"));
            StartPrice sp = new StartPrice();
            sp.setValue(Double.parseDouble(element.element("SellingStatus").elementText("CurrentPrice")));
            sp.setCurrencyID(element.element("SellingStatus").element("CurrentPrice").attributeValue("currencyID"));
            item.setStartPrice(sp);
            item.setConditionID(Integer.parseInt(element.elementText("ConditionID")==null?"1000":element.elementText("ConditionID")));
            List lishipto = new ArrayList();
            Iterator<Element> shipe = element.elementIterator("ShipToLocations");
            while (shipe.hasNext()){
                Element elstr = shipe.next();
                lishipto.add(elstr.getText());
            }
            item.setShipToLocations(lishipto);
            //取得退货政策并封装
            Element returne = element.element("ReturnPolicy");
            ReturnPolicy rp = new ReturnPolicy();
            rp.setRefundOption(returne.elementText("RefundOption"));
            rp.setReturnsWithinOption(returne.elementText("ReturnsWithinOption"));
            rp.setReturnsAcceptedOption(returne.elementText("ReturnsAcceptedOption"));
            rp.setDescription(returne.elementText("Description"));
            rp.setShippingCostPaidByOption(returne.elementText("ShippingCostPaidByOption"));
            item.setReturnPolicy(rp);
            //买家要求
            BuyerRequirementDetails brd = new BuyerRequirementDetails();
            MaximumItemRequirements mirs = new MaximumItemRequirements();
            Element buyere = element.element("BuyerRequirementDetails");
            if(buyere!=null) {
                Element maxiteme = buyere.element("MaximumItemRequirements");
                if(maxiteme!=null) {
                    mirs.setMaximumItemCount(Integer.parseInt(maxiteme.elementText("MaximumItemCount")));
                    mirs.setMinimumFeedbackScore(Integer.parseInt(maxiteme.elementText("MinimumFeedbackScore")));
                    brd.setMaximumItemRequirements(mirs);
                }

                Element maxUnpaid = buyere.element("MaximumUnpaidItemStrikesInfo");
                if(maxUnpaid!=null){
                    MaximumUnpaidItemStrikesInfo muis = new MaximumUnpaidItemStrikesInfo();
                    muis.setCount(Integer.getInteger(maxUnpaid.elementText("Count")));
                    muis.setPeriod(maxUnpaid.elementText("Period"));
                    brd.setMaximumUnpaidItemStrikesInfo(muis);
                }

                Element maxPolicy = buyere.element("MaximumBuyerPolicyViolations");
                if(maxPolicy!=null){
                    MaximumBuyerPolicyViolations mbpv= new MaximumBuyerPolicyViolations();
                    mbpv.setCount(Integer.parseInt(maxPolicy.elementText("Count")));
                    mbpv.setPeriod(maxPolicy.elementText("Period"));
                    brd.setMaximumBuyerPolicyViolations(mbpv);
                }
                item.setBuyerRequirementDetails(brd);
            }

            li.add(item);
        }
        return li;
    }
    /**
     * 得到反馈信息列表
     * @param xml
     * @return
     * @throws DocumentException
     */
    public static List<TradingFeedBackDetail> getFeedBackListElement(String xml) throws DocumentException {
        List<TradingFeedBackDetail> lifb = new ArrayList();
        Document document= DocumentHelper.parseText(xml);
        Element rootElt = document.getRootElement();
        Element recommend = rootElt.element("FeedbackDetailArray");
        Iterator<Element> iter = recommend.elementIterator("FeedbackDetail");
        while (iter.hasNext()){
            Element element = iter.next();
            TradingFeedBackDetail tfbd = new TradingFeedBackDetail();
            tfbd.setCommentinguser(element.elementText("CommentingUser"));
            tfbd.setCommentinguserscore(Long.parseLong(element.elementText("CommentingUserScore")));
            tfbd.setCommenttext(StringEscapeUtils.escapeXml(element.element("CommentText").getStringValue()));
            tfbd.setCommenttime(DateUtils.returnDate(element.elementText("CommentTime")));
            tfbd.setCommenttype(element.elementText("CommentType"));
            tfbd.setItemid(element.elementText("ItemID"));
            tfbd.setRole(element.elementText("Role"));
            tfbd.setFeedbackid(element.elementText("FeedbackID"));
            tfbd.setTransactionid(element.elementText("TransactionID"));
            tfbd.setOrderlineitemid(element.elementText("OrderLineItemID"));
            tfbd.setItemtitle(element.elementText("ItemTitle"));
            tfbd.setCreateTime(new Date());
            if(element.elementText("ItemPrice")!=null) {
                tfbd.setItemprice(Double.parseDouble(element.elementText("ItemPrice")));
            }
            lifb.add(tfbd);
        }
        return lifb;
    }

    /**将商品目录属性api返回的xml解析为List<PublicDataDict>*/
    public static List<PublicDataDict> getListForPublicDataDict(String xml) throws Exception {
        List<PublicDataDict> publicDataDictList = new ArrayList<PublicDataDict>();
        Document document= DocumentHelper.parseText(xml);
        Element rootElt = document.getRootElement();
        Element recommend = rootElt.element("Recommendations");

        Element cad= recommend.element("CategoryID");
        String categoryID=cad.getTextTrim();

        Iterator<Element> iter = recommend.elementIterator("NameRecommendation");
        while (iter.hasNext()){

            Element element = iter.next();
            String itemId=element.element("Name").getText();
            Iterator<Element> valueIter = element.elementIterator("ValueRecommendation");
            while (valueIter.hasNext()){
                Element element1 = valueIter.next();
                PublicDataDict publicDataDict = new PublicDataDict();
                publicDataDict.setItemId(itemId);
                publicDataDict.setItemParentId(categoryID);
                String val=element1.element("Value").getText();
                publicDataDict.setItemEnName(val);
                publicDataDict.setItemType(DictCollectionsUtil.categorySpecifics);
                publicDataDictList.add(publicDataDict);
            }
        }
        if(publicDataDictList.isEmpty()){
            PublicDataDict publicDataDict = new PublicDataDict();
            publicDataDict.setItemId(DictCollectionsUtil.categorySpecifics+categoryID);
            publicDataDict.setItemParentId(categoryID);
            publicDataDict.setItemEnName("noval");
            publicDataDict.setItemType(DictCollectionsUtil.categorySpecifics);
            publicDataDictList.add(publicDataDict);
        }
       return publicDataDictList;
    }

    /**
     * 获取根需要解析的元素
     */
    public static Element getApiElement(String xml,String nodeName) throws  Exception{
        Document document= DocumentHelper.parseText(xml);
        Element rootElt = document.getRootElement();
        Element root=  rootElt.element(nodeName);
        return root;
    }
    /**
     * 获取指定元素下面的某层指定元素内容
     */
    public static String getSpecifyElementText(Element root,String... firstElement) throws  Exception{
        String text=null;
        String[] nodes=firstElement;
        int length=nodes.length;
        for(int i=0;i<length;i++){
            String node=nodes[i];
            if(i==(length-1)){
                if(root==null){
                    return null;
                }
                Element last=root.element(node);
                if(last!=null){
                    text=last.getTextTrim();
                }
            }else{
                if(root==null){
                    return null;
                }
                root=root.element(node);

            }
        }
        return text;
    }

    /**直接根据nodes来获取消息*/
    public static String getSpecifyElementTextAllInOne(String res,String... nodes) throws Exception {
        Document document= DocumentHelper.parseText(res);
        Element rootElt = document.getRootElement();
        return getSpecifyElementText(rootElt,nodes);
    }

    /**
     * 通过Title 查询是相似分类信息
     * @param xml
     * @return
     * @throws DocumentException
     */
    public static List<TradingReseCategory> selectCategoryByKey(String xml) throws DocumentException {
        List<TradingReseCategory> litrc = new ArrayList();
        //商品分类目录查询
        Document document= DocumentHelper.parseText(xml);
        Element rootElt = document.getRootElement();
        if(rootElt==null){
            return litrc;
        }
        Element recommend = rootElt.element("SuggestedCategoryArray");
        if(recommend==null){
            return litrc;
        }
        Iterator<Element> ite = recommend.elementIterator("SuggestedCategory");
        while (ite.hasNext()){
            Element ele = ite.next();
            Element cate = ele.element("Category");
            Element PercentItemFound = ele.element("PercentItemFound");
            TradingReseCategory trc = new TradingReseCategory();
            trc.setId(Long.parseLong(cate.elementText("CategoryID")));
            trc.setCategoryId(cate.elementText("CategoryID"));
            Iterator<Element> ites = cate.elementIterator("CategoryParentName");
            String cateName = "";
            while (ites.hasNext()){
                Element ent = ites.next();
                cateName=cateName+ent.getText()+":";
            }
            trc.setCategoryKey(PercentItemFound.getText()+"%");
            trc.setCategoryName(cateName+cate.elementText("CategoryName"));
            litrc.add(trc);
        }
        return litrc;
    }
    /**
     * 通过Title 商品所属分类查询
     * @param xml
     * @param key
     * @return
     * @throws DocumentException
     */
    public static List<TradingReseCategory> selectCategoryBytitle(String xml,String key) throws DocumentException {
        List<TradingReseCategory> litrc = new ArrayList();
        //之前是做商品所属分类查询
        Document document= DocumentHelper.parseText(xml);
        Element rootElt = document.getRootElement();
        Element recommend = rootElt.element("searchResult");
        Iterator<Element> iter = recommend.elementIterator("item");
        while (iter.hasNext()){
            Element ele=iter.next();
            Element elecate = ele.element("primaryCategory");
            TradingReseCategory trc = new TradingReseCategory();
            trc.setId(Long.parseLong(elecate.elementText("categoryId")));
            trc.setCategoryId(elecate.elementText("categoryId"));
            trc.setCategoryName(StringEscapeUtils.escapeHtml(elecate.elementText("categoryName")));
            trc.setCategoryKey(key);
            litrc.add(trc);
        }

        return litrc;
    }

    /**
     * 组装运输方式
     * @param xml
     * @return
     * @throws DocumentException
     */
    public static List<TradingDataDictionary> selectShippingService(String xml) throws DocumentException {
        List<TradingDataDictionary> lidata = new ArrayList();
        Document document= DocumentHelper.parseText(xml);
        Element rootElt = document.getRootElement();
        Iterator<Element> ies = rootElt.elementIterator("ShippingServiceDetails");
        while (ies.hasNext()){
            Element element = ies.next();
            TradingDataDictionary tdd = new TradingDataDictionary();
            tdd.setType("domestic transportation");
            tdd.setValue(element.elementText("ShippingService"));
            tdd.setName(element.elementText("Description"));
            tdd.setName1(element.elementText("ShippingCategory"));
            lidata.add(tdd);
        }
        return lidata;
    }

    /**
     * 得到分类
     * @param xml
     * @return
     * @throws DocumentException
     */
    public static List<PublicDataDict> selectPublicDataDict(String xml) throws DocumentException {
        List<PublicDataDict> lidata = new ArrayList();
        Document document= DocumentHelper.parseText(xml);
        Element rootElt = document.getRootElement();
        Element el = rootElt.element("CategoryArray");
        Iterator<Element> ies = el.elementIterator("Category");
        while (ies.hasNext()){
            Element ment = ies.next();
            PublicDataDict pdd = new PublicDataDict();
            pdd.setItemEnName(ment.elementText("CategoryName"));
            pdd.setItemId(ment.elementText("CategoryID"));
            pdd.setItemLevel(ment.elementText("CategoryLevel"));
            pdd.setItemParentId(ment.elementText("CategoryParentID"));
            pdd.setItemType("category");
            lidata.add(pdd);
        }
        return lidata;
    }

    /**上传图片的api*/
    public static String uploadEbayImage(UsercontrollerEbayAccount ua,String url,String picName){
        String xml="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<UploadSiteHostedPicturesRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">\n" +
                "  <RequesterCredentials>\n" +
                "    <eBayAuthToken>"+ua.getEbayToken()+"</eBayAuthToken>\n" +
                "  </RequesterCredentials>\n" +
                "  <WarningLevel>High</WarningLevel>\n" +
                "  <ExternalPictureURL>"+url+"</ExternalPictureURL>\n" +
                "  <PictureName>"+picName+"</PictureName><ErrorLanguage>zh_HK</ErrorLanguage>\n" +
                "</UploadSiteHostedPicturesRequest>";
        return xml;
    }
    public static String getWarningInformation(String res) throws Exception {
        Document document= DocumentHelper.parseText(res);
        Element rootElt = document.getRootElement();
        String errors = SamplePaseXml.getSpecifyElementText(rootElt,"Errors","LongMessage");
        return errors;
    }
    public static List<TradingPriceTracking> getPriceTrackingItem(String res,String title) throws Exception {
        List<TradingPriceTracking> list=new ArrayList<TradingPriceTracking>();
        Document document= DocumentHelper.parseText(res);
        Element rootElt = document.getRootElement();
        Element searchResult=rootElt.element("searchResult");
        Iterator items=searchResult.elementIterator("item");
        while(items.hasNext()){
            TradingPriceTracking priceTracking=new TradingPriceTracking();
            Element item= (Element) items.next();
            priceTracking.setItemid(SamplePaseXml.getSpecifyElementText(item, "itemId"));
            priceTracking.setCategoryid(SamplePaseXml.getSpecifyElementText(item, "primaryCategory", "categoryId"));
            priceTracking.setCategoryname(SamplePaseXml.getSpecifyElementText(item, "primaryCategory", "categoryName"));
            priceTracking.setCurrentprice(SamplePaseXml.getSpecifyElementText(item, "sellingStatus", "currentPrice"));
            priceTracking.setSellerusername(SamplePaseXml.getSpecifyElementText(item, "sellerInfo", "sellerUserName"));
            priceTracking.setTitle(SamplePaseXml.getSpecifyElementText(item, "title"));
            priceTracking.setBidcount(SamplePaseXml.getSpecifyElementText(item, "sellingStatus", "bidCount"));
            String starttime=SamplePaseXml.getSpecifyElementText(item,"listingInfo","startTime");
            String endtime=SamplePaseXml.getSpecifyElementText(item,"listingInfo","endTime");
            if(StringUtils.isNotBlank(starttime)){
                priceTracking.setStarttime(DateUtils.returnDate(starttime));
            }
            if(StringUtils.isNotBlank(endtime)){
                priceTracking.setEndtime(DateUtils.returnDate(endtime));
            }
            Element sellingStatus=item.element("sellingStatus");
            String currencyId1="";
            if(sellingStatus!=null){
                Element currentPrice=sellingStatus.element("currentPrice");
                if(currentPrice!=null){
                    Attribute currencyId=currentPrice.attribute("currencyId");
                    if(currencyId!=null){
                        currencyId1=currencyId.getValue();
                    }
                }
            }
            priceTracking.setCurrencyid(currencyId1);
            priceTracking.setQuerytitle(title);
            list.add(priceTracking);
        }
        return list;
    }
    //解析价格跟踪
    public static List<TradingPriceTracking> getPriceTrackingItemByItemId(String res) throws Exception {
        List<TradingPriceTracking> priceTrackings=new ArrayList<TradingPriceTracking>();
        Document document= DocumentHelper.parseText(res);
        Element rootElt = document.getRootElement();
        Iterator items=rootElt.elementIterator("Item");
        while(items.hasNext()){
            TradingPriceTracking priceTracking=new TradingPriceTracking();
            Element item= (Element) items.next();
            priceTracking.setItemid(SamplePaseXml.getSpecifyElementText(item,"ItemID"));
            priceTracking.setTitle(SamplePaseXml.getSpecifyElementText(item,"Title"));
            priceTracking.setCurrentprice(SamplePaseXml.getSpecifyElementText(item,"ConvertedCurrentPrice"));
            priceTracking.setBidcount(SamplePaseXml.getSpecifyElementText(item,"BidCount"));
            Element ConvertedCurrentPrice=item.element("ConvertedCurrentPrice");
            String endtime=SamplePaseXml.getSpecifyElementText(item,"EndTime");
            if(StringUtils.isNotBlank(endtime)){
                priceTracking.setEndtime(DateUtils.returnDate(endtime));
            }
            String currencyId1="";
            if(ConvertedCurrentPrice!=null){
                Attribute currencyId=ConvertedCurrentPrice.attribute("currencyId");
                if(currencyId!=null){
                    currencyId1=currencyId.getValue();
                }
            }
            priceTracking.setCurrencyid(currencyId1);
            priceTrackings.add(priceTracking);
        }
        return priceTrackings;
    }
}
