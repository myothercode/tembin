package com.base.utils.xmlutils;

import com.base.database.publicd.model.PublicDataDict;
import com.base.database.trading.model.TradingFeedBackDetail;
import com.base.utils.common.DateUtils;
import com.base.utils.common.DictCollectionsUtil;
import com.base.xmlpojo.trading.addproduct.*;
import com.base.xmlpojo.trading.addproduct.attrclass.ShippingServiceAdditionalCost;
import com.base.xmlpojo.trading.addproduct.attrclass.ShippingServiceCost;
import com.base.xmlpojo.trading.addproduct.attrclass.ShippingSurcharge;
import com.base.xmlpojo.trading.addproduct.attrclass.StartPrice;
import org.apache.commons.lang.StringEscapeUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.ArrayList;
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
        item.setGetItFast(element.elementText("GetItFast").equals("false")?false:true);
        item.setPrivateListing(element.elementText("PrivateListing").equals("true")?true:false);
        item.setDispatchTimeMax(Integer.parseInt(element.elementText("DispatchTimeMax")));
        item.setListingDuration(element.elementText("ListingDuration"));
        item.setDescription(element.elementText("Description"));
        PrimaryCategory pc = new PrimaryCategory();
        pc.setCategoryID(element.element("PrimaryCategory").elementText("CategoryID"));
        item.setPrimaryCategory(pc);
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
            mirs.setMaximumItemCount(Integer.parseInt(maxiteme.elementText("MaximumItemCount")));
            mirs.setMinimumFeedbackScore(Integer.parseInt(maxiteme.elementText("MinimumFeedbackScore")));
            brd.setMaximumItemRequirements(mirs);

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
            sso.setShippingServiceCost(new ShippingServiceCost(shipping.attributeValue("currencyID"),Double.parseDouble(shipping.elementText("ShippingServiceCost"))));
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
                    li.add(e.getText());
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
        }

        return item;
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
                mirs.setMaximumItemCount(Integer.parseInt(maxiteme.elementText("MaximumItemCount")));
                mirs.setMinimumFeedbackScore(Integer.parseInt(maxiteme.elementText("MinimumFeedbackScore")));
                brd.setMaximumItemRequirements(mirs);

                Element maxUnpaid = buyere.element("MaximumUnpaidItemStrikesInfo");
                MaximumUnpaidItemStrikesInfo muis = new MaximumUnpaidItemStrikesInfo();
                muis.setCount(Integer.getInteger(maxUnpaid.elementText("Count")));
                muis.setPeriod(maxUnpaid.elementText("Period"));
                brd.setMaximumUnpaidItemStrikesInfo(muis);

                Element maxPolicy = buyere.element("MaximumBuyerPolicyViolations");
                MaximumBuyerPolicyViolations mbpv= new MaximumBuyerPolicyViolations();
                mbpv.setCount(Integer.parseInt(maxPolicy.elementText("Count")));
                mbpv.setPeriod(maxPolicy.elementText("Period"));
                brd.setMaximumBuyerPolicyViolations(mbpv);

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
                Element last=root.element(node);
                if(last!=null){
                    text=last.getTextTrim();
                }
            }else{
                root=root.element(node);
            }
        }
        return text;
    }
}
