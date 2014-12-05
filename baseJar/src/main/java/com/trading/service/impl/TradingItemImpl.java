package com.trading.service.impl;

import com.base.aboutpaypal.service.PayPalService;
import com.base.database.customtrading.mapper.ItemMapper;
import com.base.database.keymove.model.KeyMoveList;
import com.base.database.publicd.mapper.PublicItemPictureaddrAndAttrMapper;
import com.base.database.publicd.mapper.PublicUserConfigMapper;
import com.base.database.publicd.model.*;
import com.base.database.trading.mapper.*;
import com.base.database.trading.model.*;
import com.base.database.userinfo.mapper.UsercontrollerUserMapper;
import com.base.database.userinfo.model.SystemLog;
import com.base.database.userinfo.model.UsercontrollerUser;
import com.base.domains.SessionVO;
import com.base.domains.querypojos.ItemQuery;
import com.base.mybatis.page.Page;
import com.base.utils.applicationcontext.RequestResponseContext;
import com.base.utils.cache.DataDictionarySupport;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.common.*;
import com.base.utils.exception.Asserts;
import com.base.utils.ftpabout.FtpUploadFile;
import com.base.xmlpojo.trading.addproduct.*;
import com.base.xmlpojo.trading.addproduct.attrclass.MadeForOutletComparisonPrice;
import com.base.xmlpojo.trading.addproduct.attrclass.MinimumAdvertisedPrice;
import com.base.xmlpojo.trading.addproduct.attrclass.OriginalRetailPrice;
import com.base.xmlpojo.trading.addproduct.attrclass.StartPrice;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.publicd.service.*;
import com.trading.service.*;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.impl.cookie.DateParseException;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.*;

/**
 * 商品信息
 * Created by cz on 2014/7/23.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TradingItemImpl implements com.trading.service.ITradingItem {
    static Logger logger = Logger.getLogger(TradingItemImpl.class);
    @Autowired
    private TradingItemMapper tradingItemMapper;
    @Autowired
    private ITradingPictureDetails iTradingPictureDetails;
    @Autowired
    private ITradingAttrMores iTradingAttrMores;
    @Autowired
    private ITradingPublicLevelAttr iTradingPublicLevelAttr;
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private ITradingVariations iTradingVariations;
    @Autowired
    private ITradingVariation iTradingVariation;
    @Autowired
    private ITradingPictures iTradingPictures;
    @Autowired
    private ITradingAddItem iTradingAddItem;
    @Autowired
    private ITradingPayPal iTradingPayPal;
    @Autowired
    private ITradingReturnpolicy iTradingReturnpolicy;
    @Autowired
    private ITradingBuyerRequirementDetails iTradingBuyerRequirementDetails;
    @Autowired
    private ITradingItemAddress iTradingItemAddress;
    @Autowired
    private ITradingShippingDetails iTradingShippingDetails;
    @Autowired
    private TradingReturnpolicyMapper tradingReturnpolicyMapper;
    @Autowired
    private TradingItemAddressMapper tradingItemAddressMapper;
    @Autowired
    private ITradingShippingServiceOptions iTradingShippingServiceOptions;
    @Autowired
    private ITradingInternationalShippingServiceOption iTradingInternationalShippingServiceOption;
    @Autowired
    private ITradingDiscountPriceInfo iTradingDiscountPriceInfo;
    @Autowired
    private UsercontrollerPaypalAccountMapper usercontrollerPaypalAccountMapper;
    @Autowired
    private TradingPaypalMapper tradingPaypalMapper;
    @Autowired
    private TradingBuyerRequirementDetailsMapper tradingBuyerRequirementDetailsMapper;
    @Autowired
    private TradingDiscountpriceinfoMapper tradingDiscountpriceinfoMapper;
    @Autowired
    private TradingShippingdetailsMapper tradingShippingdetailsMapper;
    @Autowired
    public PublicUserConfigMapper publicUserConfigMapper;
    @Autowired
    public IPublicItemInventory iPublicItemInventory;
    @Autowired
    public IPublicItemInformation iPublicItemInformation;
    @Autowired
    public IPublicItemCustom iPublicItemCustom;
    @Autowired
    public IPublicItemSupplier iPublicItemSupplier;
    @Autowired
    public UsercontrollerUserMapper usercontrollerUserMapper;
    @Autowired
    public IPublicItemPictureaddrAndAttr iPublicItemPictureaddrAndAttr;
    @Autowired
    private ITradingDescriptionDetails iTradingDescriptionDetails;
    @Autowired
    private ITradingTemplateInitTable iTradingTemplateInitTable;
    @Autowired
    private PayPalService payPalService;
    @Autowired
    private ITradingListingPicUrl iTradingListingPicUrl;
    @Autowired
    private ITradingListingReport iTradingListingReport;
    @Autowired
    private ITradingListingSuccess iTradingListingSuccess;
    @Value("${IMAGE_URL_PREFIX}")
    private String image_url_prefix;

    @Value("${ITEM_LIST_ICON_URL}")
    private String item_list_icon_url;
    @Autowired
    public PublicItemPictureaddrAndAttrMapper publicItemPictureaddrAndAttrMapper;
    @Autowired
    public ITradingAssessViewSet iTradingAssessViewSet;
    @Autowired
    private ITradingDataDictionary iTradingDataDictionary;
    @Autowired
    private TradingTempTypeKeyMapper tradingTempTypeKeyMapper;


    @Override
    public void saveTradingItem(TradingItemWithBLOBs pojo) throws Exception {
        if(pojo.getId()==null){
            ObjectUtils.toInitPojoForInsert(pojo);
            if(pojo.getUuid()==null){
                pojo.setUuid(UUIDUtil.getUUID());
            }
            this.tradingItemMapper.insertSelective(pojo);
        }else{
            if(pojo.getUuid()==null){
                pojo.setUuid(UUIDUtil.getUUID());
            }
            this.tradingItemMapper.updateByPrimaryKeySelective(pojo);
        }

    }

    @Override
    public void saveTradingItemList(List<TradingItemWithBLOBs> liti) throws Exception {
        for(TradingItemWithBLOBs ti:liti){
            this.saveTradingItem(ti);
        }
    }
    @Override
    public TradingItemWithBLOBs toDAOPojo(Item item) throws Exception {
        TradingItemWithBLOBs pojo = new TradingItemWithBLOBs();
        ConvertPOJOUtil.convert(pojo, item);
        ConvertPOJOUtil.convert(pojo, item.getBestOfferDetails());
        ConvertPOJOUtil.convert(pojo, item.getPrimaryCategory());
        ConvertPOJOUtil.convert(pojo, item.getQuantityInfo());
        ConvertPOJOUtil.convert(pojo, item.getQuantityRestrictionPerBuyer());

        return pojo;
    }

    @Override
    public Map saveItem(Item item, TradingItemWithBLOBs tradingItem1) throws Exception {
        //保存商品信息到数据库中

        if(item.getVariations()!=null){
            Pictures pt = new Pictures();
            pt.setVariationSpecificName(item.getVariations().getVariationSpecificsSet().getNameValueList().get(0).getName());
            if(item.getVariations().getPictures()!=null) {
                pt.setVariationSpecificPictureSet(item.getVariations().getPictures().getVariationSpecificPictureSet());
            }
            item.getVariations().setPictures(pt);
        }
        tradingItem1.setConditionid(item.getConditionID().longValue());
        tradingItem1.setCategoryid(item.getPrimaryCategory().getCategoryID());
        tradingItem1.setCurrency(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(item.getSite())).getValue1());
        tradingItem1.setListingtype(item.getListingType());
        if(item.getStartPrice()!=null&&!"".equals(item.getStartPrice())){
            tradingItem1.setStartprice(item.getStartPrice().getValue());
        }
        if(item.getOutOfStockControl()!=null) {
            tradingItem1.setOutofstockcontrol(item.getOutOfStockControl() ? "1" : "0");
        }
        HttpServletRequest request = RequestResponseContext.getRequest();
        String [] paypals = request.getParameterValues("ebayAccounts");
        Map itemMap = new HashMap();
        String mouth = request.getParameter("dataMouth");//刊登方式
        //String [] dicUrl =
        Asserts.assertTrue(paypals!=null,"请选择一个paypal帐号!");
        for(int is =0;is<paypals.length;is++) {
            TradingItemWithBLOBs tradingItem = new TradingItemWithBLOBs();
            tradingItem = tradingItem1;

            tradingItem.setEbayAccount(paypals[is]);
            if(is>=1){
                tradingItem.setId(null);
            }
            tradingItem.setTitle(request.getParameter("Title_"+paypals[is]));
            tradingItem.setSubtitle(request.getParameter("SubTitle_"+paypals[is]));
            if(request.getParameter("Quantity_"+paypals[is])!=null&&!"".equals(request.getParameter("Quantity_"+paypals[is]))){
                tradingItem.setQuantity(Long.parseLong(request.getParameter("Quantity_"+paypals[is])));
            }
            if(request.getParameter("StartPrice.value_"+paypals[is])!=null&&!"".equals(request.getParameter("StartPrice.value_"+paypals[is]))){
                tradingItem.setStartprice(Double.parseDouble(request.getParameter("StartPrice.value_"+paypals[is])));
            }
            if("timeSave".equals(mouth)){
                tradingItem.setListingWay("1");//表示为定时刊登
            }else{
                tradingItem.setListingWay("0");//表示为正常刊登
            }
            tradingItem.setSku(item.getSKU());
            tradingItem.setListingduration(item.getListingDuration() == null ? "GTC" : item.getListingDuration());
            SessionVO c= SessionCacheSupport.getSessionVO();
            TradingAssessViewSet ta = this.iTradingAssessViewSet.selectByUserid(c.getId());
            if(ta!=null) {
                tradingItem.setAssessRange(request.getParameter("setView")==null?"":request.getParameter("setView"));
                tradingItem.setAssessSetview(ta.getSetview());
            }
            this.saveTradingItem(tradingItem);
            //处理模板中上传的图片
           /* if(tradingItem.getTemplateId()!=null){*///用户选择了模板，才会处理模板图片信息
                String [] tempicUrls = request.getParameterValues("blankimg");//界面中添加的模板图片
                TradingTemplateInitTable ttit = this.iTradingTemplateInitTable.selectById(tradingItem.getTemplateId());
                if(tempicUrls!=null&&tempicUrls.length>0){//如果界面中模板图片不为空，那么替换模板中ＵＲＬ地址
                    this.iTradingAttrMores.deleteByParentId("TemplatePicUrl",tradingItem.getId());
                    for(String url:tempicUrls){
                        TradingAttrMores tam = this.iTradingAttrMores.toDAOPojo("TemplatePicUrl",url);
                        tam.setParentId(tradingItem.getId());
                        tam.setParentUuid(tradingItem.getUuid());
                        this.iTradingAttrMores.saveAttrMores(tam);
                    }
                }
            /*}*/
            itemMap.put(paypals[is],tradingItem.getId());
            if(item.getListingType().equals("Chinese")){//拍买商品保存数据
                TradingAddItem tai = this.iTradingAddItem.selectParentId(tradingItem.getId());
                if(tai==null){
                    tai = new TradingAddItem();
                }
                tai.setParentId(tradingItem.getId());
                tai.setParentUuid(tradingItem.getUuid());
                if(tradingItem.getDiscountpriceinfoId()!=null){
                    tai.setDisId(tradingItem.getDiscountpriceinfoId());
                }
                tai.setListingflag(request.getParameter("ListingFlag"));
                tai.setListingduration(request.getParameter("ListingDuration"));
                tai.setListingscale(Long.parseLong(request.getParameter("ListingScale")==null?"0":request.getParameter("ListingScale")));
                tai.setBuyitnowprice(Long.parseLong(request.getParameter("BuyItNowPrice")==null?"0":request.getParameter("BuyItNowPrice")));
                tai.setPrivatelisting(request.getParameter("PrivateListing"));
                tai.setReserveprice(Long.parseLong(request.getParameter("ReservePrice")==null?"0":request.getParameter("ReservePrice")));
                tai.setSecondflag(request.getParameter("SecondFlag"));
                this.iTradingAddItem.saveAddItem(tai);

            }else if(item.getListingType().equals("2")){//多属性刊登保存数据
                //保存多属性信息
                if(item.getVariations()!=null){
                    //删除下面的所有字属性
                    TradingVariations tvs = this.iTradingVariations.selectByParentId(tradingItem.getId());
                    if(tvs!=null) {
                        List<TradingVariation> dlitv = this.iTradingVariation.selectByParentId(tvs.getId());
                        for (TradingVariation tv : dlitv) {
                            List<TradingPublicLevelAttr> litpla = this.iTradingPublicLevelAttr.selectByParentId("VariationSpecifics", tv.getId());
                            for (TradingPublicLevelAttr tpa : litpla) {
                                this.iTradingPublicLevelAttr.deleteByParentID(null, tpa.getId());
                            }
                            this.iTradingPublicLevelAttr.deleteByParentID("VariationSpecifics", tv.getId());
                        }
                        this.iTradingVariation.deleteParentId(tvs.getId());

                        List<TradingPublicLevelAttr> litpla = this.iTradingPublicLevelAttr.selectByParentId("VariationSpecificsSet", tvs.getId());
                        for (TradingPublicLevelAttr tpa : litpla) {
                            List<TradingPublicLevelAttr> li = this.iTradingPublicLevelAttr.selectByParentId("NameValueList", tpa.getId());
                            for (TradingPublicLevelAttr l : li) {
                                this.iTradingAttrMores.deleteByParentId("Name", l.getId());
                                this.iTradingAttrMores.deleteByParentId("Value", l.getId());
                            }
                            this.iTradingPublicLevelAttr.deleteByParentID("NameValueList", tpa.getId());
                        }
                        this.iTradingPublicLevelAttr.deleteByParentID("VariationSpecificsSet", tvs.getId());
                    }

                    TradingVariations tv = new TradingVariations();
                    tv.setParentId(tradingItem.getId());
                    tv.setParentUuid(tradingItem.getUuid());

                    this.iTradingVariations.saveVariations(tv);
                    List<Variation> livt = item.getVariations().getVariation();
                    for(int i = 0 ; i<livt.size();i++){
                        Variation vtion = livt.get(i);
                        TradingVariation tvtion = this.iTradingVariation.toDAOPojo(vtion);
                        tvtion.setParentId(tv.getId());
                        tvtion.setParentUuid(tv.getUuid());
                        tvtion.setStartprice(vtion.getStartPrice().getValue());

                        this.iTradingVariation.saveVariation(tvtion);

                        this.iTradingPublicLevelAttr.deleteByParentID("VariationSpecifics",tvtion.getId());

                        TradingPublicLevelAttr tpla = this.iTradingPublicLevelAttr.toDAOPojo("VariationSpecifics",null);
                        tpla.setParentId(tvtion.getId());
                        tpla.setParentUuid(tvtion.getUuid());
                        this.iTradingPublicLevelAttr.savePublicLevelAttr(tpla);

                        List<NameValueList> linvls = item.getVariations().getVariationSpecificsSet().getNameValueList();
                        this.iTradingPublicLevelAttr.deleteByParentID(null,tpla.getId());
                        if(linvls!=null&&linvls.size()>0){
                            for(NameValueList vs : linvls){
                                TradingPublicLevelAttr tpl = this.iTradingPublicLevelAttr.toDAOPojo(vs.getName(),vs.getValue().get(i));
                                tpl.setParentUuid(tpla.getUuid());
                                tpl.setParentId(tpla.getId());
                                this.iTradingPublicLevelAttr.savePublicLevelAttr(tpl);
                            }
                        }
                    }

                    if(item.getVariations().getVariationSpecificsSet()!=null){
                        this.iTradingPublicLevelAttr.deleteByParentID("VariationSpecificsSet",tv.getId());
                        TradingPublicLevelAttr tpla = this.iTradingPublicLevelAttr.toDAOPojo("VariationSpecificsSet",null);
                        tpla.setParentId(tv.getId());
                        tpla.setParentUuid(tv.getUuid());
                        this.iTradingPublicLevelAttr.savePublicLevelAttr(tpla);

                        this.iTradingPublicLevelAttr.deleteByParentID("NameValueList",tpla.getId());
                        List<NameValueList> liset = item.getVariations().getVariationSpecificsSet().getNameValueList();
                        for(int i = 0; i<liset.size();i++){
                            NameValueList nvl = liset.get(i);
                            TradingPublicLevelAttr tplas = this.iTradingPublicLevelAttr.toDAOPojo("NameValueList",null);
                            tplas.setParentId(tpla.getId());
                            tplas.setParentUuid(tpla.getUuid());
                            this.iTradingPublicLevelAttr.savePublicLevelAttr(tplas);

                            this.iTradingAttrMores.deleteByParentId("Name",tplas.getId());
                            TradingAttrMores tam = this.iTradingAttrMores.toDAOPojo("Name",nvl.getName());
                            tam.setParentId(tplas.getId());
                            tam.setParentUuid(tplas.getUuid());
                            this.iTradingAttrMores.saveAttrMores(tam);

                            this.iTradingAttrMores.deleteByParentId("Value",tplas.getId());
                            List<String> listr = nvl.getValue();
                            listr = MyCollectionsUtil.listUnique(listr);
                            for(String str: listr){
                                if(str!=null&&!"".equals(str)){
                                    TradingAttrMores tams = this.iTradingAttrMores.toDAOPojo("Value",str);
                                    tams.setParentId(tplas.getId());
                                    tams.setParentUuid(tplas.getUuid());
                                    this.iTradingAttrMores.saveAttrMores(tams);
                                }
                            }
                        }
                    }
                    String [] morePicUrl = request.getParameterValues("pic_mackid_more");
                    //保存多属必图片信息
                    Pictures pictrue = item.getVariations().getPictures();
                    if(pictrue!=null) {
                        TradingPictures tp = this.iTradingPictures.toDAOPojo(pictrue);
                        tp.setParentId(tv.getId());
                        tp.setParentUuid(tv.getUuid());
                        this.iTradingPictures.savePictures(tp);

                        List<VariationSpecificPictureSet> vspsli = pictrue.getVariationSpecificPictureSet();
                        this.iTradingPublicLevelAttr.deleteByParentID("VariationSpecificPictureSet", tp.getId());
                        if (vspsli != null) {
                            for (VariationSpecificPictureSet vsps : vspsli) {
                                TradingPublicLevelAttr tplas = this.iTradingPublicLevelAttr.toDAOPojo("VariationSpecificPictureSet", null);
                                tplas.setParentId(tp.getId());
                                tplas.setParentUuid(tp.getUuid());
                                this.iTradingPublicLevelAttr.savePublicLevelAttr(tplas);

                                this.iTradingPublicLevelAttr.deleteByParentID("VariationSpecificValue", tplas.getId());

                                TradingPublicLevelAttr tpname = this.iTradingPublicLevelAttr.toDAOPojo("VariationSpecificValue", vsps.getVariationSpecificValue());
                                tpname.setParentId(tplas.getId());
                                tpname.setParentUuid(tplas.getUuid());
                                this.iTradingPublicLevelAttr.savePublicLevelAttr(tpname);

                                this.iTradingAttrMores.deleteByParentId("MuAttrPictureURL", tplas.getId());
                                if (vsps.getPictureURL() != null) {
                                    List<String> listr = vsps.getPictureURL();
                                    listr = MyCollectionsUtil.listUnique(listr);
                                    for (int i = 0; i < listr.size(); i++) {
                                        String str = listr.get(i);
                                        if (str != null && !"".equals(str)) {
                                            TradingAttrMores tams = this.iTradingAttrMores.toDAOPojo("MuAttrPictureURL", str);
                                            tams.setParentId(tplas.getId());
                                            tams.setParentUuid(tplas.getUuid());
                                            tams.setAttr1(morePicUrl[i]);
                                            this.iTradingAttrMores.saveAttrMores(tams);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            String [] picUrl = request.getParameterValues("pic_mackid");
            //保存图片信息
            PictureDetails picd = item.getPictureDetails();
            if(picd!=null){
                String [] picurl = request.getParameterValues("PictureDetails_"+paypals[is]+".PictureURL");
                TradingPicturedetails tpicd = this.iTradingPictureDetails.toDAOPojo(picd);
                tpicd.setParentId(tradingItem.getId());
                tpicd.setParentUuid(tradingItem.getUuid());
                tpicd.setGallerytype("Gallery");
                tpicd.setPhotodisplay("PicturePack");
                if(picurl!=null&&picurl.length>0) {
                    tpicd.setGalleryurl(picurl[0]);
                }
                this.iTradingPictureDetails.savePictureDetails(tpicd);
                this.iTradingAttrMores.deleteByParentId("PictureURL",tpicd.getId());
                for(int i = 0;i<picurl.length;i++){
                    TradingAttrMores tam = this.iTradingAttrMores.toDAOPojo("PictureURL",picurl[i]);
                    tam.setParentId(tpicd.getId());
                    tam.setParentUuid(tpicd.getUuid());
                    tam.setAttr1(picUrl[i]);
                    this.iTradingAttrMores.saveAttrMores(tam);
                }
            }else{
                String [] picurl = request.getParameterValues("PictureDetails_"+paypals[is]+".PictureURL");
                TradingPicturedetails tpicd = this.iTradingPictureDetails.toDAOPojo(picd);
                tpicd.setParentId(tradingItem.getId());
                tpicd.setParentUuid(tradingItem.getUuid());
                tpicd.setGallerytype("Gallery");
                tpicd.setPhotodisplay("PicturePack");
                if(picurl!=null&&picurl.length>0) {
                    tpicd.setGalleryurl(picurl[0]);
                }
                this.iTradingPictureDetails.savePictureDetails(tpicd);
                this.iTradingAttrMores.deleteByParentId("PictureURL",tpicd.getId());
                Asserts.assertTrue(picurl!=null,"请为商品选择展示图片!");
                for(int i = 0;i<picurl.length;i++){
                    TradingAttrMores tam = this.iTradingAttrMores.toDAOPojo("PictureURL",picurl[i]);
                    tam.setParentId(tpicd.getId());
                    tam.setParentUuid(tpicd.getUuid());
                    tam.setAttr1(picUrl[i]);
                    this.iTradingAttrMores.saveAttrMores(tam);
                }
            }
            //保存属性值
            if(item.getItemSpecifics()!=null){
                ItemSpecifics iscs = item.getItemSpecifics();
                List<NameValueList> linv = iscs.getNameValueList();
                //删除下面的字属性
                List<TradingPublicLevelAttr> litpla = this.iTradingPublicLevelAttr.selectByParentId("ItemSpecifics",tradingItem.getId());
                for(TradingPublicLevelAttr tpa : litpla){
                    this.iTradingPublicLevelAttr.deleteByParentID(null,tpa.getId());
                }
                this.iTradingPublicLevelAttr.deleteByParentID("ItemSpecifics",tradingItem.getId());
                TradingPublicLevelAttr tpla = this.iTradingPublicLevelAttr.toDAOPojo("ItemSpecifics",null);
                tpla.setParentId(tradingItem.getId());
                tpla.setParentUuid(tradingItem.getUuid());
                this.iTradingPublicLevelAttr.savePublicLevelAttr(tpla);

                this.iTradingPublicLevelAttr.deleteByParentID(null,tpla.getId());
                for(NameValueList nvl : linv){
                    TradingPublicLevelAttr tpl = this.iTradingPublicLevelAttr.toDAOPojo(nvl.getName(),nvl.getValue().get(0).toString());
                    tpl.setParentUuid(tpla.getUuid());
                    tpl.setParentId(tpla.getId());
                    this.iTradingPublicLevelAttr.savePublicLevelAttr(tpl);
                }
            }
        }
        return itemMap;
    }

    @Override
    public Item toItem(TradingItem tradingItem) throws Exception {
        Item item = new Item();
        ConvertPOJOUtil.convert(item,tradingItem);
        if(tradingItem.getListingtype().equals("FixedPriceItem")){//固价刊登
            StartPrice sp = new StartPrice();
            sp.setCurrencyID(tradingItem.getCurrency());
            sp.setValue(tradingItem.getStartprice());
            item.setStartPrice(sp);
        }else if(tradingItem.getListingtype().equals("2")){//多属性

        }else if(tradingItem.getListingtype().equals("Chinese")){//拍买价
            TradingAddItem tai = this.iTradingAddItem.selectParentId(tradingItem.getId());
            Asserts.assertTrue(!ObjectUtils.isLogicalNull(tai),"拍卖价格不能为空！");
            item.setBuyItNowPrice(tai.getBuyitnowprice().doubleValue());
        }
        PrimaryCategory pc = new PrimaryCategory();
        pc.setCategoryID(tradingItem.getCategoryid());
        item.setPrimaryCategory(pc);
        TradingPaypal tpay = this.iTradingPayPal.selectById(tradingItem.getPayId());
        TradingItemAddress tadd = this.iTradingItemAddress.selectById(tradingItem.getItemLocationId());
        TradingShippingdetails tshipping = this.iTradingShippingDetails.selectById(tradingItem.getShippingDeailsId());
        TradingDiscountpriceinfo tdiscount = this.iTradingDiscountPriceInfo.selectById(tradingItem.getDiscountpriceinfoId());
        TradingBuyerRequirementDetails tbuyer = this.iTradingBuyerRequirementDetails.selectById(tradingItem.getBuyerId());
        TradingReturnpolicy treturn = this.iTradingReturnpolicy.selectById(tradingItem.getReturnpolicyId());

        //组装刑登xml
        //组装买家要求
        if(tbuyer!=null) {
            BuyerRequirementDetails brd = this.iTradingBuyerRequirementDetails.toXmlPojo(tradingItem.getBuyerId());
            item.setBuyerRequirementDetails(brd);
        }
        //组装退货政策
        if(treturn!=null) {
            ReturnPolicy rp = this.iTradingReturnpolicy.toXmlPojo(tradingItem.getReturnpolicyId());
            item.setReturnPolicy(rp);
            item.getReturnPolicy().setReturnsAcceptedOption(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(item.getReturnPolicy().getReturnsAcceptedOption())).getValue());
            item.getReturnPolicy().setReturnsWithinOption(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(item.getReturnPolicy().getReturnsWithinOption())).getValue());
            item.getReturnPolicy().setRefundOption(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(item.getReturnPolicy().getRefundOption())).getValue());
            item.getReturnPolicy().setShippingCostPaidByOption(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(item.getReturnPolicy().getShippingCostPaidByOption())).getValue());
        }
        //运输详情
        if(tshipping!=null) {
            ShippingDetails sd = this.iTradingShippingDetails.toXmlPojo(tradingItem.getShippingDeailsId());
            item.setShippingDetails(sd);
        }
        String template = "";
        if(tradingItem.getTemplateId()!=null){//如果选择了模板
            TradingTemplateInitTable tttt = this.iTradingTemplateInitTable.selectById(tradingItem.getTemplateId());
            template = tttt.getTemplateHtml();
            template.replace("{ProductDetail}",item.getDescription());
            if(tttt!=null&&tradingItem.getSellerItemInfoId()!=null){//如果选择了卖家描述
                TradingDescriptionDetailsWithBLOBs tdd = this.iTradingDescriptionDetails.selectById(tradingItem.getSellerItemInfoId());
                template.replace("{PaymentMethodTitle}",tdd.getPayTitle()==null?"PayTitle":tdd.getPayTitle());
                template.replace("{PaymentMethod}",tdd.getPayInfo());

                template.replace("{ShippingDetailTitle}",tdd.getShippingTitle()==null?"ShippingTitle()":tdd.getShippingTitle());
                template.replace("{ShippingDetail}",tdd.getShippingInfo());

                template.replace("{SalesPolicyTitle}",tdd.getGuaranteeTitle()==null?"GuaranteeTitle()":tdd.getGuaranteeTitle());
                template.replace("{SalesPolicy}",tdd.getGuaranteeInfo());

                template.replace("{AboutUsTitle}",tdd.getFeedbackTitle()==null?"FeedbackTitle()":tdd.getFeedbackTitle());
                template.replace("{AboutUs}",tdd.getFeedbackInfo());

                template.replace("{ContactUsTitle}",tdd.getContactTitle()==null?"ContactTitle":tdd.getContactTitle());
                template.replace("{ContactUs}",tdd.getContactInfo());

            }
        }else{//未选择模板，
            template+=item.getDescription()+"</br>";
            if(tradingItem.getSellerItemInfoId()!=null){//如果选择了卖家描述
                TradingDescriptionDetailsWithBLOBs tdd = this.iTradingDescriptionDetails.selectById(tradingItem.getSellerItemInfoId());

                template+=tdd.getPayTitle()+"</br>"+tdd.getPayInfo()+"</br>";
                template+=tdd.getShippingTitle()+"</br>"+tdd.getShippingInfo()+"</br>";
                template+=tdd.getGuaranteeTitle()+"</br>"+tdd.getGuaranteeInfo()+"</br>";
                template+=tdd.getFeedbackTitle()+"</br>"+tdd.getFeedbackInfo()+"</br>";
                template+=tdd.getContactTitle()+"</br>"+tdd.getContactInfo()+"</br>";

            }
        }
        item.setDescription(template);

        TradingItemAddress tia = this.iTradingItemAddress.selectById(tradingItem.getItemLocationId());
        item.setCountry(DataDictionarySupport.getTradingDataDictionaryByID(tia.getCountryId()).getValue());
        item.setCurrency(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(item.getSite())).getValue1());
        item.setSite(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(item.getSite())).getValue());
        item.setPostalCode(tia.getPostalcode());

        TradingPaypal tp = this.iTradingPayPal.selectById(tradingItem.getPayId());
        if(tp!=null) {
            item.setPayPalEmailAddress(payPalService.selectById(Long.parseLong(tp.getPaypal())).getEmail());
        }
        List<String> limo = new ArrayList();
        limo.add("PayPal");
        item.setPaymentMethods(limo);
        if(item.getListingType().equals("Chinese")) {
            item.setListingDuration(item.getListingDuration() == null ? "GTC" : item.getListingDuration());
        }else{
            item.setListingDuration("GTC");
        }
        item.setDispatchTimeMax(0);


        if(tradingItem.getListingtype().equals("2")){//多属性
            TradingVariations tradvars = this.iTradingVariations.selectByParentId(tradingItem.getId());
            Variations vars = new Variations();
            if(tradvars!=null){
                TradingPictures tptures = this.iTradingPictures.selectParnetId(tradvars.getId());
                if(tptures!=null){
                    Pictures pic = new Pictures();
                    List<TradingPublicLevelAttr> litpla = this.iTradingPublicLevelAttr.selectByParentId("VariationSpecificPictureSet",tptures.getId());
                    List<VariationSpecificPictureSet> livss = new ArrayList<VariationSpecificPictureSet>();
                    for(TradingPublicLevelAttr tpla:litpla){
                        VariationSpecificPictureSet vss = new VariationSpecificPictureSet();
                        List<TradingPublicLevelAttr> livalue = this.iTradingPublicLevelAttr.selectByParentId("VariationSpecificValue",tpla.getId());
                        vss.setVariationSpecificValue(livalue.get(0).getValue());
                        List<String> listr = new ArrayList<String>();
                        List<TradingAttrMores> liname = this.iTradingAttrMores.selectByParnetid(tpla.getId(),"MuAttrPictureURL");
                        for(TradingAttrMores tam : liname){
                            List<TradingListingpicUrl> liplu = this.iTradingListingPicUrl.selectByMackId(tam.getAttr1());
                            TradingListingpicUrl plu = liplu.get(0);
                            if(plu.getEbayurl()==null&&plu.getCheckFlag().equals("0")){
                                Thread.sleep(5000L);
                                List<TradingListingpicUrl> litamss = this.iTradingListingPicUrl.selectByMackId(tam.getAttr1());
                                plu = litamss.get(0);
                                if(plu.getCheckFlag().equals("1")){
                                    listr.add(plu.getEbayurl());
                                }else{
                                    Asserts.assertTrue(false,"图片未成功上传，请重新选择图片上传");
                                }
                            }else if(plu.getEbayurl()==null&&plu.getCheckFlag().equals("2")){
                                Asserts.assertTrue(false,"图片未成功上传，请重新选择图片上传");
                            }else if(plu.getCheckFlag().equals("1")){
                                listr.add(plu.getEbayurl());
                            }
                        }
                        vss.setPictureURL(listr);
                        livss.add(vss);
                    }
                    pic.setVariationSpecificPictureSet(livss);
                    if(tptures.getVariationspecificname()!=null){
                        pic.setVariationSpecificName(tptures.getVariationspecificname());
                    }
                    vars.setPictures(pic);
                }

                List<TradingPublicLevelAttr> litpla = this.iTradingPublicLevelAttr.selectByParentId("VariationSpecificsSet",tradvars.getId());
                if(litpla!=null&&litpla.size()>0){
                    VariationSpecificsSet vss = new VariationSpecificsSet();
                    for(TradingPublicLevelAttr tpla : litpla){
                        List<NameValueList> lnvl = new ArrayList<NameValueList>();
                        List<TradingPublicLevelAttr> linvlist = this.iTradingPublicLevelAttr.selectByParentId("NameValueList",tpla.getId());
                        for(TradingPublicLevelAttr nvlist : linvlist){
                            NameValueList nvl = new NameValueList();
                            List<TradingAttrMores> liname = this.iTradingAttrMores.selectByParnetid(nvlist.getId(),"Name");
                            nvl.setName(liname.get(0).getValue());
                            List<TradingAttrMores> lival = this.iTradingAttrMores.selectByParnetid(nvlist.getId(),"Value");
                            List<String> listr = new ArrayList<String>();
                            for(TradingAttrMores str : lival){
                                listr.add(str.getValue());
                            }
                            nvl.setValue(listr);
                            lnvl.add(nvl);
                        }
                        vss.setNameValueList(lnvl);
                    }
                    vars.setVariationSpecificsSet(vss);
                }


                List<Variation> liva = new ArrayList<Variation>();
                List<TradingVariation> litv = this.iTradingVariation.selectByParentId(tradvars.getId());
                if(litv!=null&&litv.size()>0){
                    for(TradingVariation tv:litv){
                        Variation var = new Variation();
                        if(tv.getQuantity()!=null){
                            var.setQuantity(tv.getQuantity().intValue());
                        }
                        if(tv.getStartprice()!=null){
                            StartPrice sp  = new StartPrice();
                            sp.setValue(tv.getStartprice());
                            var.setStartPrice(sp);
                        }
                        if(tv.getSku()!=null){
                            var.setSKU(tv.getSku());
                        }
                        List<TradingPublicLevelAttr> livs = this.iTradingPublicLevelAttr.selectByParentId("VariationSpecifics",tv.getId());
                        if(livs!=null&&livs.size()>0){
                            List<VariationSpecifics> livcs = new ArrayList<VariationSpecifics>();
                            for(TradingPublicLevelAttr tpla : livs){
                                VariationSpecifics vsss = new VariationSpecifics();
                                List<NameValueList> lnvl = new ArrayList<NameValueList>();
                                List<TradingPublicLevelAttr> linvlist = this.iTradingPublicLevelAttr.selectByParentId(null,tpla.getId());
                                for(TradingPublicLevelAttr nvlist : linvlist){
                                    NameValueList nvl = new NameValueList();
                                    nvl.setName(nvlist.getName());
                                    List<String> listr = new ArrayList<String>();
                                    listr.add(nvlist.getValue());
                                    nvl.setValue(listr);
                                    lnvl.add(nvl);
                                }
                                vsss.setNameValueList(lnvl);
                                livcs.add(vsss);
                            }
                            var.setVariationSpecifics(livcs);
                        }
                        if(tv.getMadeforoutletcomparisonprice()!=null||tv.getMinimumadvertisedprice()!=null||tv.getMinimumadvertisedpriceexposure()!=null||tv.getSoldoffebay()!=null||tv.getSoldonebay()!=null||tv.getOriginalretailprice()!=null){
                            DiscountPriceInfo dpi = new DiscountPriceInfo();
                            if(tv.getMadeforoutletcomparisonprice()!=null){
                                MadeForOutletComparisonPrice mfocp = new MadeForOutletComparisonPrice();
                                mfocp.setValue(tv.getMadeforoutletcomparisonprice());
                                dpi.setMadeForOutletComparisonPrice(mfocp);
                            }
                            if(tv.getMinimumadvertisedprice()!=null){
                                MinimumAdvertisedPrice map  = new MinimumAdvertisedPrice();
                                map.setValue(tv.getMinimumadvertisedprice());
                                dpi.setMinimumAdvertisedPrice(map);
                            }
                            if(tv.getMinimumadvertisedpriceexposure()!=null){
                                dpi.setMinimumAdvertisedPriceExposure(tv.getMinimumadvertisedpriceexposure());
                            }
                            if(tv.getOriginalretailprice()!=null){
                                OriginalRetailPrice orp = new OriginalRetailPrice();
                                orp.setValue(tv.getOriginalretailprice());
                                dpi.setOriginalRetailPrice(orp);
                            }
                            if(tv.getSoldonebay()!=null){
                                dpi.setSoldOneBay(tv.getSoldonebay().equals("1")?true:false);
                            }
                            if(tv.getSoldoffebay()!=null){
                                dpi.setSoldOffeBay(tv.getSoldoffebay().equals("1")?true:false);
                            }
                            var.setDiscountPriceInfo(dpi);
                        }
                        liva.add(var);
                    }
                    vars.setVariation(liva);
                }
            }
            item.setVariations(vars);
        }

        //图片
        List<TradingPicturedetails> litpd = this.iTradingPictureDetails.selectByParentId(tradingItem.getId());
        if(litpd!=null&&litpd.size()>0){
            TradingPicturedetails tpd = litpd.get(0);
            PictureDetails pds = new PictureDetails();
            List<TradingAttrMores> litam = this.iTradingAttrMores.selectByParnetid(tpd.getId(),"PictureURL");
            List<String> listr = new ArrayList<String>();
            for(TradingAttrMores tam : litam){
                List<TradingListingpicUrl> liplu = this.iTradingListingPicUrl.selectByMackId(tam.getAttr1());
                if(liplu!=null&&liplu.size()>0){
                    TradingListingpicUrl plu = liplu.get(0);
                    if(plu.getEbayurl()==null&&plu.getCheckFlag().equals("0")){
                        Thread.sleep(5000L);
                        List<TradingListingpicUrl> litamss = this.iTradingListingPicUrl.selectByMackId(tam.getAttr1());
                        plu = litamss.get(0);
                        if(plu.getCheckFlag().equals("1")){
                            listr.add(plu.getEbayurl());
                        }else{
                            Asserts.assertTrue(false,"图片未成功上传，请重新选择图片上传");
                        }
                    }else if(plu.getEbayurl()==null&&plu.getCheckFlag().equals("2")){
                        Asserts.assertTrue(false,"图片未成功上传，请重新选择图片上传");
                    }else if(plu.getCheckFlag().equals("1")){
                        listr.add(plu.getEbayurl());
                    }
                }else {
                    listr.add(tam.getValue());
                }
            }
            pds.setPictureURL(listr);
            item.setPictureDetails(pds);
        }
        item.setListingType(item.getListingType().equals("2")?"FixedPriceItem":item.getListingType());
        item.setUUID(null);
        return item;
    }

    @Override
    public List<ItemQuery> selectByItemList(Map map,Page page){
        return this.itemMapper.selectByItemList(map,page);
    }

    @Override
    public TradingItem selectById(Long id){
        return this.tradingItemMapper.selectByPrimaryKey(id);
    }

    @Override
    public TradingItemWithBLOBs selectByIdBL(Long id){
        return this.tradingItemMapper.selectByPrimaryKey(id);
    }

    @Override
    public TradingItemWithBLOBs selectByItemId(String itemId){
        TradingItemExample tie = new TradingItemExample();
        tie.createCriteria().andItemIdEqualTo(itemId);
        List<TradingItemWithBLOBs> liti = this.tradingItemMapper.selectByExampleWithBLOBs(tie);
        if(liti==null||liti.size()==0){
            return null;
        }else{
            return liti.get(0);
        }
    }
    @Override
    public void updateTradingItem(Item item, TradingItemWithBLOBs tradingItem) throws Exception {
        HttpServletRequest request = RequestResponseContext.getRequest();
        String [] selectType = request.getParameterValues("selectType");
        SessionVO c= SessionCacheSupport.getSessionVO();
        tradingItem = new TradingItemWithBLOBs();
        TradingItem tradingItem1=this.selectByItemId(item.getItemID());
        for(String str : selectType){
            if(str.equals("StartPrice")){//改价格
                tradingItem.setStartprice(item.getStartPrice().getValue());
            }else if(str.equals("Quantity")){//改数量
                tradingItem.setQuantity(item.getQuantity().longValue());
            }else if(str.equals("PictureDetails")){//改图片
                PictureDetails picd = item.getPictureDetails();
                if(picd!=null){
                    TradingPicturedetails tpicd = this.iTradingPictureDetails.toDAOPojo(picd);
                    tpicd.setParentId(tradingItem.getId());
                    tpicd.setParentUuid(tradingItem.getUuid());
                    tpicd.setCheckFlag("1");
                    this.iTradingPictureDetails.savePictureDetails(tpicd);

                    this.iTradingAttrMores.deleteByParentId("PictureURL",tpicd.getId());
                    List<String> lipic = item.getPictureDetails().getPictureURL();
                    lipic = MyCollectionsUtil.listUnique(lipic);
                    for(int i =0;i<lipic.size();i++){
                        TradingAttrMores tam = this.iTradingAttrMores.toDAOPojo("PictureURL",lipic.get(i));
                        tam.setParentId(tpicd.getId());
                        tam.setParentUuid(tpicd.getUuid());
                        this.iTradingAttrMores.saveAttrMores(tam);
                    }
                }
            }else if(str.equals("PayPal")){//改支付方式
                final String jj=item.getPayPalEmailAddress();
                TradingPaypal tp = this.iTradingPayPal.selectById(tradingItem1.getPayId());
                if(tp!=null) {
                    tp.setId(null);
                    List<PublicUserConfig> paypalList = DataDictionarySupport.getPublicUserConfigByType(DataDictionarySupport.PUBLIC_DATA_DICT_PAYPAL, c.getId());
                    Collection<PublicUserConfig> x = Collections2.filter(paypalList, new Predicate<PublicUserConfig>() {
                        @Override
                        public boolean apply(PublicUserConfig tradingDataDictionary) {
                            return jj == tradingDataDictionary.getConfigValue();
                        }
                    });
                    PublicUserConfig[] tt = x.toArray(new PublicUserConfig[]{});
                    List<PublicUserConfig> li = Arrays.asList(tt);
                    tp.setPaypal(li.get(0).getId().toString());
                    tp.setCheckFlag("1");
                    this.iTradingPayPal.savePaypal(tp);
                    tradingItem.setPayId(tp.getId());
                }
            }else if(str.equals("ReturnPolicy")){//改退货政策
                TradingReturnpolicy tr = this.iTradingReturnpolicy.selectById(tradingItem1.getReturnpolicyId());
                if(tr!=null){
                    tr.setId(null);
                    ConvertPOJOUtil.convert(tr, item.getReturnPolicy());
                    tr.setCheckFlag("1");
                    this.iTradingReturnpolicy.saveTradingReturnpolicy(tr);
                    tradingItem.setReturnpolicyId(tr.getId());
                }
            }else if(str.equals("Title")){//改标题　
                tradingItem.setTitle(item.getTitle());
            }else if(str.equals("Buyer")){//改买家要求
                TradingBuyerRequirementDetails buy = this.iTradingBuyerRequirementDetails.selectById(tradingItem1.getBuyerId());
                if(buy!=null){
                    buy.setId(null);
                    ConvertPOJOUtil.convert(buy, item.getBuyerRequirementDetails());
                    buy.setCheckFlag("1");
                    this.iTradingBuyerRequirementDetails.saveBuyerRequirementDetails(buy);
                    tradingItem.setBuyerId(buy.getId());
                }
            }else if(str.equals("SKU")){//改ＳＫＵ
                tradingItem.setSku(item.getSKU());
            }else if(str.equals("PrimaryCategory")){//改分类
                tradingItem.setCategoryid(item.getPrimaryCategory().getCategoryID());
            }else if(str.equals("ConditionID")){//改商品状态
                tradingItem.setConditionid(item.getConditionID().longValue());
            }else if(str.equals("Location")){//物品所在地
                TradingItemAddress addr = this.iTradingItemAddress.selectById(tradingItem1.getItemLocationId());
                if(addr!=null){
                    addr.setId(null);
                    addr.setAddress(item.getLocation());
                    addr.setPostalcode(item.getPostalCode());
                    addr.setCheckFlag("1");
                    List<TradingDataDictionary> li =  DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.DATA_DICT_COUNTRY);
                    TradingDataDictionary pd = null;
                    for(TradingDataDictionary pdd : li ){
                        if(pd.getValue().equals(item.getCountry())){
                            pd = pdd;
                            break;
                        }
                    }
                    addr.setCountryId(pd.getId());
                    addr.setName(item.getLocation());
                    this.iTradingItemAddress.saveItemAddress(addr);
                    tradingItem.setItemLocationId(addr.getId());
                }
            }else if(str.equals("DispatchTimeMax")){//最快处理时间
                tradingItem.setDispatchtimemax(item.getDispatchTimeMax().longValue());
            }else if(str.equals("PrivateListing")){//改是否允许私人买
                tradingItem.setPrivatelisting(item.getPrivateListing()?"1":"0");
            }else if(str.equals("ListingDuration")){//改刊登天数
                tradingItem.setListingduration(item.getListingDuration());
            }else if(str.equals("Description")){//改商品描述
                tradingItem.setDescription(item.getDescription());
            }else if(str.equals("ShippingDetails")){//改运输详情
                TradingShippingdetails ts = this.iTradingShippingDetails.selectById(tradingItem1.getShippingDeailsId());
                if(ts!=null){
                    ts.setId(null);
                    ts.setCheckFlag("1");
                    this.iTradingShippingDetails.saveAllData(ts,item.getShippingDetails(),request.getParameter("notLocationValue"));
                    tradingItem.setShippingDeailsId(ts.getId());
                }
            }
        }
        tradingItem.setId(tradingItem1.getId());
        this.tradingItemMapper.updateByPrimaryKeySelective(tradingItem);
    }
    @Override
    public void saveListingItem(Item item, KeyMoveList kml,String categoryName) throws Exception {
        //for(int is = 0;is < liitem.size();is++){
            //Item item = liitem.get(is);
            TradingItemExample tie = new TradingItemExample();
            tie.createCriteria().andItemIdEqualTo(item.getItemID());
            List<TradingItemWithBLOBs> liti = this.tradingItemMapper.selectByExampleWithBLOBs(tie);
            if(liti==null||liti.size()==0) {
                TradingItemWithBLOBs tradingItem = new TradingItemWithBLOBs();
                ConvertPOJOUtil.convert(tradingItem, item);
                if(item.getStartPrice()!=null) {
                    tradingItem.setStartprice(item.getStartPrice().getValue());
                }
                tradingItem.setSite(item.getSite());
                tradingItem.setIsFlag("Success");
                tradingItem.setEbayAccount(kml.getPaypalId());
                tradingItem.setCategoryid(item.getPrimaryCategory().getCategoryID());
                tradingItem.setConditionid(item.getConditionID().longValue());
                tradingItem.setCreateUser(kml.getUserId());
                tradingItem.setItemName(item.getTitle());
                tradingItem.setItemId(item.getItemID());
                tradingItem.setListingduration(item.getListingDuration());
                tradingItem.setQuantity(item.getQuantity().longValue());
                tradingItem.setCurrency(item.getCurrency());
                tradingItem.setCreateTime(new Date());
                if(item.getOutOfStockControl()!=null) {
                    tradingItem.setOutofstockcontrol(item.getOutOfStockControl() ? "1" : "0");
                }else{
                    tradingItem.setOutofstockcontrol("1");
                }
                tradingItem.setSku(item.getSKU());
                tradingItem.setTitle(item.getTitle());
                if(item.getListingType().equals("FixedPriceItem")&&item.getVariations()!=null){//多属性
                    tradingItem.setListingtype("2");
                }else if(item.getListingType().equals("Chinese")){//拍卖
                    tradingItem.setListingtype(item.getListingType());
                }else{//固价
                    tradingItem.setListingtype(item.getListingType());
                }
                this.saveTradingItem(tradingItem);
                //处理商品模板信息
                PublicDataDict pdd = this.iTradingDataDictionary.selectByParentDicExample(tradingItem.getCategoryid(), tradingItem.getSite());
                Random r = new Random();
                if(pdd==null){//当在分类ＩＤ在本地数据查询不到数据时，默认一个经典的模板
                    List<TradingTemplateInitTable> litt = this.iTradingTemplateInitTable.selectByType(Long.parseLong("523"));//彩用经典模板
                    tradingItem.setTemplateId(litt.get(r.nextInt(litt.size())).getId());
                }else{//本地数据库查询到分类，通过分类名称去匹配，相似度高的就选用分类模板
                    String typeName = StringEscapeUtils.escapeHtml(pdd.getItemEnName());
                    TradingTempTypeKeyExample tte = new TradingTempTypeKeyExample();
                    tte.createCriteria();
                    List<TradingTempTypeKey> littk = this.tradingTempTypeKeyMapper.selectByExample(tte);
                    for(TradingTempTypeKey ttk : littk){
                        String [] keys = ttk.getKeyName().split("&|/|,");
                        for(String ke:keys){
                            if(typeName.indexOf(ke)>0){
                                List<TradingTemplateInitTable> litt = this.iTradingTemplateInitTable.selectByType(ttk.getTempTypeId());//彩用经典模板
                                tradingItem.setTemplateId(litt.get(r.nextInt(litt.size())).getId());
                                break;
                            }
                        }
                    }
                }
                if(tradingItem.getTemplateId()==null){
                    List<TradingTemplateInitTable> litt = this.iTradingTemplateInitTable.selectByType(Long.parseLong("523"));//彩用经典模板
                    tradingItem.setTemplateId(litt.get(r.nextInt(litt.size())).getId());
                }
                this.tradingItemMapper.updateByPrimaryKeySelective(tradingItem);
                //处理商品描述信息
                String des = tradingItem.getDescription();
                org.jsoup.nodes.Document doc = org.jsoup.Jsoup.parse(des);
                org.jsoup.select.Elements content = doc.getElementsByAttributeValue("class", "Pa_Box");
                if(content!=null&&content.size()>0) {
                    for (int i = 0; i < content.size(); i++) {
                        org.jsoup.select.Elements el = content.get(i).getElementsByAttributeValue("class", "Pa_headc");
                        if (el != null && el.size() > 0) {
                            if ("Description".equals(el.get(0).html().toString())) {
                                UsercontrollerUser uu = this.usercontrollerUserMapper.selectByPrimaryKey(Integer.parseInt(tradingItem.getCreateUser() + ""));
                                org.jsoup.select.Elements imgel = content.get(i).getElementsByTag("img");
                                if (imgel != null && imgel.size() > 0) {
                                    for (int j = 0; j < imgel.size(); j++) {
                                        String picUrl = imgel.get(j).attr("src");
                                        if (picUrl.indexOf("?") == -1) {
                                            picUrl = picUrl;
                                        } else {
                                            picUrl = picUrl.substring(0, picUrl.indexOf("?"));
                                        }
                                        URL url = new URL(picUrl);
                                        //打开链接
                                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                        //设置请求方式为"GET"
                                        conn.setRequestMethod("GET");
                                        //超时响应时间为5秒
                                        conn.setConnectTimeout(5 * 1000);
                                        //通过输入流获取图片数据
                                        InputStream inStream = conn.getInputStream();
                                        String stuff = MyStringUtil.getExtension(picUrl, "");
                                        String fileName = FtpUploadFile.ftpUploadFile(inStream, uu.getUserLoginId() + "/" + tradingItem.getSku(), stuff);
                                        inStream.close();
                                        String picUrls = "";
                                        if (fileName == null) {
                                            picUrls = picUrl;
                                        } else {
                                            picUrls = image_url_prefix + uu.getUserLoginId() + "/" + tradingItem.getSku() + "/" + fileName;
                                        }
                                        TradingAttrMores tam = new TradingAttrMores();
                                        tam.setParentId(tradingItem.getId());
                                        tam.setParentUuid(tradingItem.getUuid());
                                        tam.setAttrValue("TemplatePicUrl");
                                        tam.setValue(picUrls);
                                        tam.setCreateTime(new Date());
                                        tam.setUuid(UUIDUtil.getUUID());
                                        tam.setAttr1(EncryptionUtil.md5Encrypt(picUrls));
                                        this.iTradingAttrMores.saveAttrMores(tam);
                                    }
                                }
                                if (imgel != null && imgel.size() > 0) {
                                    for (int j = 0; j < imgel.size(); j++) {
                                        imgel.get(j).remove();
                                    }
                                }
                                tradingItem.setDescription(content.get(i).toString());
                                this.tradingItemMapper.updateByPrimaryKeySelective(tradingItem);
                            }
                        }
                    }
                }


                /**保存产品信息开始**/
                PublicItemInformation itemInformation = null;
                if(tradingItem.getSku()!=null){
                    itemInformation = this.iPublicItemInformation.selectItemInformationBySKU(tradingItem.getSku());
                }
                if(itemInformation==null) {
                    itemInformation = new PublicItemInformation();
                    List<PublicUserConfig> liconf = DataDictionarySupport.getPublicUserConfigByType("itemType", kml.getUserId());
                    //商品分类
                /*String typeid = "";
                for(PublicUserConfig puc : liconf){
                    if("一键搬家分类".equals(puc.getConfigName())){
                        typeid = puc.getId()+"";
                    }
                }
                if("".equals(typeid)){
                    PublicUserConfig puc = new PublicUserConfig();
                    puc.setConfigName("一键搬家分类");
                    puc.setConfigType("itemType");
                    puc.setItemLevel("1");
                    puc.setCreateDate(new Date());
                    this.publicUserConfigMapper.insertSelective(puc);
                    itemInformation.setTypeId(puc.getId());
                }else{
                    itemInformation.setTypeId(Long.parseLong(typeid));
                }*/
                    itemInformation.setTypeId(item.getPrimaryCategory().getCategoryID() == null ? null : Long.parseLong(item.getPrimaryCategory().getCategoryID()));
                    itemInformation.setTypename(categoryName);
                    if (item.getPrimaryCategory().getCategoryID() != null) {
                        itemInformation.setTypeflag(1);
                    } else {
                        itemInformation.setTypeflag(0);
                    }
                    itemInformation.setDescription(tradingItem.getDescription());

                    itemInformation.setSku(tradingItem.getSku());
                    itemInformation.setName(tradingItem.getItemName());
                    //库存
                    PublicItemInventory inventory = new PublicItemInventory();
                    inventory.setCreateUser(kml.getUserId());
                    inventory.setCreateTime(new Date());
                    iPublicItemInventory.saveItemInventory(inventory);
                    itemInformation.setInventoryId(inventory.getId());
                    //标签
                    //供应商
                    PublicItemSupplier pis = new PublicItemSupplier();
                    pis.setCreateUser(kml.getUserId());
                    pis.setCreateTime(new Date());
                    pis.setPrice(tradingItem.getStartprice());
                    iPublicItemSupplier.saveItemSupplier(pis);
                    itemInformation.setSupplierId(pis.getId());
                    //申报
                    PublicItemCustom pic = new PublicItemCustom();
                    pic.setCreateTime(new Date());
                    pic.setCreateUser(kml.getUserId());
                    iPublicItemCustom.saveItemCustom(pic);
                    itemInformation.setCustomId(pic.getId());
                    itemInformation.setCreateUser(kml.getUserId());
                    this.iPublicItemInformation.saveItemInformation(itemInformation);
                    //图片信息
                    PictureDetails picds = item.getPictureDetails();
                    if (picds != null) {
                        List<String> picurl = picds.getPictureURL();
                        for (int i = 0; i < picurl.size(); i++) {
                            PublicItemPictureaddrAndAttr ppaa = new PublicItemPictureaddrAndAttr();
                            ppaa.setCreateTime(new Date());
                            ppaa.setCreateUser(kml.getUserId());
                            ppaa.setAttrtype("picture");
                            ppaa.setAttrname("picture");
                            ppaa.setAttrvalue(picurl.get(i));
                            ppaa.setIteminformationId(itemInformation.getId());
                            this.iPublicItemPictureaddrAndAttr.saveItemPictureaddrAndAttr(ppaa);
                        }
                    }
                }
                /**保存产品信息结束**/

                if(tradingItem.getListingtype().equals("Chinese")){//拍买保存数据
                    TradingAddItem tai = this.iTradingAddItem.selectParentId(tradingItem.getId());
                    if(tai==null){
                        tai = new TradingAddItem();
                    }
                    tai.setParentId(tradingItem.getId());
                    tai.setParentUuid(tradingItem.getUuid());
                    if(tradingItem.getDiscountpriceinfoId()!=null){
                        tai.setDisId(tradingItem.getDiscountpriceinfoId());
                    }
                    tai.setListingflag(item.getPrivateListing()==true?"1":"0");
                    tai.setListingduration(item.getListingDuration());
                    //tai.setListingscale(Long.parseLong(request.getParameter("ListingScale")==null?"0":request.getParameter("ListingScale")));
                    tai.setBuyitnowprice(Long.parseLong(item.getBuyItNowPrice()+""));
                    tai.setPrivatelisting(item.getPrivateListing()==true?"1":"0");
                    tai.setReserveprice(Long.parseLong(item.getReservePrice()+""));
                    //tai.setSecondflag(request.getParameter("SecondFlag"));
                    this.iTradingAddItem.saveAddItem(tai);
                }

                UsercontrollerUser uu = this.usercontrollerUserMapper.selectByPrimaryKey(Integer.parseInt(tradingItem.getCreateUser() + ""));
                if (item.getVariations() != null) {//多属性
                    //删除下面的所有字属性
                    TradingVariations tvs = this.iTradingVariations.selectByParentId(tradingItem.getId());
                    if (tvs != null) {
                        List<TradingVariation> dlitv = this.iTradingVariation.selectByParentId(tvs.getId());
                        for (TradingVariation tv : dlitv) {
                            List<TradingPublicLevelAttr> litpla = this.iTradingPublicLevelAttr.selectByParentId("VariationSpecifics", tv.getId());
                            for (TradingPublicLevelAttr tpa : litpla) {
                                this.iTradingPublicLevelAttr.deleteByParentID(null, tpa.getId());
                            }
                            this.iTradingPublicLevelAttr.deleteByParentID("VariationSpecifics", tv.getId());
                        }
                        this.iTradingVariation.deleteParentId(tvs.getId());

                        List<TradingPublicLevelAttr> litpla = this.iTradingPublicLevelAttr.selectByParentId("VariationSpecificsSet", tvs.getId());
                        for (TradingPublicLevelAttr tpa : litpla) {
                            List<TradingPublicLevelAttr> li = this.iTradingPublicLevelAttr.selectByParentId("NameValueList", tpa.getId());
                            for (TradingPublicLevelAttr l : li) {
                                this.iTradingAttrMores.deleteByParentId("Name", l.getId());
                                this.iTradingAttrMores.deleteByParentId("Value", l.getId());
                            }
                            this.iTradingPublicLevelAttr.deleteByParentID("NameValueList", tpa.getId());
                        }
                        this.iTradingPublicLevelAttr.deleteByParentID("VariationSpecificsSet", tvs.getId());
                    }

                    TradingVariations tv = new TradingVariations();
                    tv.setParentId(tradingItem.getId());
                    tv.setParentUuid(tradingItem.getUuid());
                    tv.setCreateUser(kml.getUserId());
                    this.iTradingVariations.saveVariations(tv);
                    List<Variation> livt = item.getVariations().getVariation();
                    for (int i = 0; i < livt.size(); i++) {
                        Variation vtion = livt.get(i);
                        TradingVariation tvtion = this.iTradingVariation.toDAOPojo(vtion);
                        tvtion.setParentId(tv.getId());
                        tvtion.setParentUuid(tv.getUuid());
                        tvtion.setStartprice(vtion.getStartPrice().getValue());
                        tvtion.setCreateUser(kml.getUserId());
                        this.iTradingVariation.saveVariation(tvtion);

                        this.iTradingPublicLevelAttr.deleteByParentID("VariationSpecifics", tvtion.getId());

                        TradingPublicLevelAttr tpla = this.iTradingPublicLevelAttr.toDAOPojo("VariationSpecifics", null);
                        tpla.setParentId(tvtion.getId());
                        tpla.setParentUuid(tvtion.getUuid());
                        this.iTradingPublicLevelAttr.savePublicLevelAttr(tpla);

                        List<NameValueList> linvls = item.getVariations().getVariationSpecificsSet().getNameValueList();
                        this.iTradingPublicLevelAttr.deleteByParentID(null, tpla.getId());
                        if (linvls != null && linvls.size() > 0) {
                            for (NameValueList vs : linvls) {
                                try {
                                    TradingPublicLevelAttr tpl = this.iTradingPublicLevelAttr.toDAOPojo(vs.getName(), vs.getValue().get(i));
                                    tpl.setParentUuid(tpla.getUuid());
                                    tpl.setParentId(tpla.getId());

                                    this.iTradingPublicLevelAttr.savePublicLevelAttr(tpl);
                                }catch(IndexOutOfBoundsException e){
                                    continue;
                                }
                            }
                        }
                    }

                    if (item.getVariations().getVariationSpecificsSet() != null) {
                        this.iTradingPublicLevelAttr.deleteByParentID("VariationSpecificsSet", tv.getId());
                        TradingPublicLevelAttr tpla = this.iTradingPublicLevelAttr.toDAOPojo("VariationSpecificsSet", null);
                        tpla.setParentId(tv.getId());
                        tpla.setParentUuid(tv.getUuid());

                        this.iTradingPublicLevelAttr.savePublicLevelAttr(tpla);

                        this.iTradingPublicLevelAttr.deleteByParentID("NameValueList", tpla.getId());
                        List<NameValueList> liset = item.getVariations().getVariationSpecificsSet().getNameValueList();
                        for (int i = 0; i < liset.size(); i++) {
                            NameValueList nvl = liset.get(i);
                            TradingPublicLevelAttr tplas = this.iTradingPublicLevelAttr.toDAOPojo("NameValueList", null);
                            tplas.setParentId(tpla.getId());
                            tplas.setParentUuid(tpla.getUuid());

                            this.iTradingPublicLevelAttr.savePublicLevelAttr(tplas);

                            this.iTradingAttrMores.deleteByParentId("Name", tplas.getId());
                            TradingAttrMores tam = this.iTradingAttrMores.toDAOPojo("Name", nvl.getName());
                            tam.setParentId(tplas.getId());
                            tam.setParentUuid(tplas.getUuid());

                            this.iTradingAttrMores.saveAttrMores(tam);

                            this.iTradingAttrMores.deleteByParentId("Value", tplas.getId());
                            List<String> listr = nvl.getValue();
                            listr = MyCollectionsUtil.listUnique(listr);
                            for (String str : listr) {
                                if (str != null && !"".equals(str)) {
                                    TradingAttrMores tams = this.iTradingAttrMores.toDAOPojo("Value", str);
                                    tams.setParentId(tplas.getId());
                                    tams.setParentUuid(tplas.getUuid());
                                    this.iTradingAttrMores.saveAttrMores(tams);
                                }
                            }
                        }
                    }

                    //保存多属必图片信息
                    Pictures pictrue = item.getVariations().getPictures();
                    if (pictrue != null) {
                        TradingPictures tp = this.iTradingPictures.toDAOPojo(pictrue);
                        tp.setParentId(tv.getId());
                        tp.setParentUuid(tv.getUuid());
                        tp.setCreateUser(kml.getUserId());
                        this.iTradingPictures.savePictures(tp);

                        List<VariationSpecificPictureSet> vspsli = pictrue.getVariationSpecificPictureSet();
                        this.iTradingPublicLevelAttr.deleteByParentID("VariationSpecificPictureSet", tp.getId());
                        for (VariationSpecificPictureSet vsps : vspsli) {
                            TradingPublicLevelAttr tplas = this.iTradingPublicLevelAttr.toDAOPojo("VariationSpecificPictureSet", null);
                            tplas.setParentId(tp.getId());
                            tplas.setParentUuid(tp.getUuid());

                            this.iTradingPublicLevelAttr.savePublicLevelAttr(tplas);

                            this.iTradingPublicLevelAttr.deleteByParentID("VariationSpecificValue", tplas.getId());

                            TradingPublicLevelAttr tpname = this.iTradingPublicLevelAttr.toDAOPojo("VariationSpecificValue", vsps.getVariationSpecificValue());
                            tpname.setParentId(tplas.getId());
                            tpname.setParentUuid(tplas.getUuid());
                            this.iTradingPublicLevelAttr.savePublicLevelAttr(tpname);

                            this.iTradingAttrMores.deleteByParentId("MuAttrPictureURL", tplas.getId());

                            List<String> listr = vsps.getPictureURL();
                            listr = MyCollectionsUtil.listUnique(listr);
                            for (String str : listr) {
                                if (str != null && !"".equals(str)) {
                                    String targetUrl = str;
                                    if(str.indexOf("?")>0){
                                        targetUrl = str.substring(0,str.indexOf("?"));
                                    }else{
                                        targetUrl = str;
                                    }
                                    String fileNameTons = this.unFtpPoto(targetUrl,uu.getUserLoginId(),tradingItem.getSku());
                                    String fileUrl = "";
                                    if(StringUtils.isNotEmpty(fileNameTons)){
                                        fileUrl = image_url_prefix + uu.getUserLoginId() + "/" + item.getSKU() + "/" + fileNameTons;
                                    }else{
                                        fileUrl = targetUrl;
                                    }
                                    TradingAttrMores tams = this.iTradingAttrMores.toDAOPojo("MuAttrPictureURL", fileUrl);
                                    tams.setParentId(tplas.getId());
                                    tams.setParentUuid(tplas.getUuid());
                                    tams.setAttr1(EncryptionUtil.md5Encrypt(str));
                                    this.iTradingAttrMores.saveAttrMores(tams);
                                }
                            }
                        }
                    }
                }

                //保存图片信息
                PictureDetails picd = item.getPictureDetails();
                if (picd != null) {
                    TradingPicturedetails tpicd = this.iTradingPictureDetails.toDAOPojo(picd);
                    tpicd.setParentId(tradingItem.getId());
                    tpicd.setParentUuid(tradingItem.getUuid());
                    tpicd.setCreateUser(kml.getUserId());
                    //下载范本列表中的小图片
                    String targetUrl = item_list_icon_url+tradingItem.getItemId()+".jpg";
                    String fileNameTons = this.unFtpPoto(targetUrl,uu.getUserLoginId(),tradingItem.getSku());
                    if(StringUtils.isNotEmpty(fileNameTons)){
                        tpicd.setGalleryurl(image_url_prefix + uu.getUserLoginId() + "/" + item.getSKU() + "/" + fileNameTons);
                    }else{
                        tpicd.setGalleryurl(targetUrl);
                    }
                    this.iTradingPictureDetails.savePictureDetails(tpicd);
                    this.iTradingAttrMores.deleteByParentId("PictureURL", tpicd.getId());
                    List<String> picurl = picd.getPictureURL();
                    for (int i = 0; i < picurl.size(); i++) {
                        try {
                            //new一个URL对象
                            String potourl = "";
                            if(picurl.get(i).indexOf("?")==-1){
                                potourl = picurl.get(i);
                            }else{
                                potourl = picurl.get(i).substring(0,picurl.get(i).indexOf("?"));
                            }
                            URL url = new URL(potourl);
                            //打开链接
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            //设置请求方式为"GET"
                            conn.setRequestMethod("GET");
                            //超时响应时间为5秒
                            conn.setConnectTimeout(5 * 1000);
                            //通过输入流获取图片数据
                            InputStream inStream = conn.getInputStream();
                            String stuff = MyStringUtil.getExtension(potourl, "");
                            String fileName = FtpUploadFile.ftpUploadFile(inStream, uu.getUserLoginId() + "/" + item.getSKU(), stuff);
                            inStream.close();
                            String picUrls = "";
                            if(fileName==null){
                                picUrls = potourl;
                            }else{
                                picUrls = image_url_prefix + uu.getUserLoginId() + "/" + item.getSKU() + "/" + fileName;
                            }
                            TradingAttrMores tam = this.iTradingAttrMores.toDAOPojo("PictureURL", picUrls);
                            tam.setParentId(tpicd.getId());
                            tam.setParentUuid(tpicd.getUuid());
                            tam.setAttr1(EncryptionUtil.md5Encrypt(picUrls));
                            this.iTradingAttrMores.saveAttrMores(tam);

                            //更新商品表中的图片，更新成ＦＴＰ服务器上的地址
                            PublicItemPictureaddrAndAttrExample pipa = new PublicItemPictureaddrAndAttrExample();
                            pipa.createCriteria().andIteminformationIdEqualTo(itemInformation.getId()).andAttrvalueEqualTo(picurl.get(i)).andAttrtypeEqualTo("picture").andAttrnameEqualTo("picture");
                            List<PublicItemPictureaddrAndAttr> lipp = this.publicItemPictureaddrAndAttrMapper.selectByExample(pipa);
                            if(lipp!=null&&lipp.size()>0){
                                PublicItemPictureaddrAndAttr pi = lipp.get(0);
                                pi.setAttrvalue(picUrls);
                                this.publicItemPictureaddrAndAttrMapper.updateByPrimaryKeySelective(pi);
                            }
                        }catch(FileNotFoundException e){
                            continue;
                        }catch(UnknownHostException e){
                            continue;
                        }catch(IOException e){
                            continue;
                        }
                    }

                }

                //保存属性值
                if (item.getItemSpecifics() != null) {
                    ItemSpecifics iscs = item.getItemSpecifics();
                    List<NameValueList> linv = iscs.getNameValueList();
                    //删除下面的字属性
                    List<TradingPublicLevelAttr> litpla = this.iTradingPublicLevelAttr.selectByParentId("ItemSpecifics", tradingItem.getId());
                    for (TradingPublicLevelAttr tpa : litpla) {
                        this.iTradingPublicLevelAttr.deleteByParentID(null, tpa.getId());
                    }
                    this.iTradingPublicLevelAttr.deleteByParentID("ItemSpecifics", tradingItem.getId());
                    TradingPublicLevelAttr tpla = this.iTradingPublicLevelAttr.toDAOPojo("ItemSpecifics", null);
                    tpla.setParentId(tradingItem.getId());
                    tpla.setParentUuid(tradingItem.getUuid());

                    this.iTradingPublicLevelAttr.savePublicLevelAttr(tpla);

                    this.iTradingPublicLevelAttr.deleteByParentID(null, tpla.getId());
                    for (NameValueList nvl : linv) {
                        TradingPublicLevelAttr tpl = this.iTradingPublicLevelAttr.toDAOPojo(nvl.getName(), nvl.getValue().get(0).toString());
                        tpl.setParentUuid(tpla.getUuid());
                        tpl.setParentId(tpla.getId());
                        this.iTradingPublicLevelAttr.savePublicLevelAttr(tpl);
                    }
                }
                //退货政策
                if (item.getReturnPolicy() != null) {
                    TradingReturnpolicy tr = new TradingReturnpolicy();
                    ReturnPolicy rp = item.getReturnPolicy();
                    TradingReturnpolicyExample tre = new TradingReturnpolicyExample();
                    TradingReturnpolicyExample.Criteria cri = tre.createCriteria();
                    if(rp.getRefundOption()!=null&&!"".equals(rp.getRefundOption())) {
                        Map m = new HashMap();
                        m.put("type", "RefundOption");
                        m.put("value", rp.getRefundOption());
                        List<TradingDataDictionary> x = DataDictionarySupport.getTradingDataDictionaryByMap(m);
                        if (x != null && x.size() > 0) {
                            TradingDataDictionary tdd = x.get(0);
                            cri.andRefundoptionEqualTo(tdd.getId() + "");
                            tr.setRefundoption(tdd.getId() + "");
                        }
                    }
                    if(rp.getReturnsWithinOption()!=null&&!"".equals(rp.getReturnsWithinOption())) {
                        Map m1 = new HashMap();
                        m1.put("type", "ReturnsWithinOption");
                        m1.put("value", rp.getReturnsWithinOption());
                        List<TradingDataDictionary> x1 = DataDictionarySupport.getTradingDataDictionaryByMap(m1);
                        if (x1 != null && x1.size() > 0) {
                            TradingDataDictionary tdd = x1.get(0);
                            cri.andReturnswithinoptionEqualTo(tdd.getId() + "");
                            tr.setReturnswithinoption(tdd.getId() + "");
                        }
                    }
                    if(rp.getReturnsAcceptedOption()!=null&&!"".equals(rp.getReturnsAcceptedOption())) {
                        Map m2 = new HashMap();
                        m2.put("type", "ReturnsAcceptedOption");
                        m2.put("value", rp.getReturnsAcceptedOption());
                        List<TradingDataDictionary> x2 = DataDictionarySupport.getTradingDataDictionaryByMap(m2);
                        if (x2 != null && x2.size() > 0) {
                            TradingDataDictionary tdd = x2.get(0);
                            cri.andReturnsacceptedoptionEqualTo(tdd.getId() + "");
                            tr.setReturnsacceptedoption(tdd.getId() + "");
                        }
                    }
                    if(rp.getShippingCostPaidByOption()!=null&&!"".equals(rp.getShippingCostPaidByOption())) {
                        Map m3 = new HashMap();
                        m3.put("type", "ShippingCostPaidByOption");
                        m3.put("value", rp.getShippingCostPaidByOption());
                        List<TradingDataDictionary> x3 = DataDictionarySupport.getTradingDataDictionaryByMap(m3);
                        if (x3 != null && x3.size() > 0) {
                            TradingDataDictionary tdd = x3.get(0);
                            cri.andShippingcostpaidbyoptionEqualTo(tdd.getId() + "");
                            tr.setShippingcostpaidbyoption(tdd.getId() + "");
                        }
                    }
                    cri.andCreateUserEqualTo(kml.getUserId());
                    List<TradingReturnpolicy> litr = this.tradingReturnpolicyMapper.selectByExample(tre);
                    if (litr == null || litr.size() == 0) {
                        //ConvertPOJOUtil.convert(tr, item.getReturnPolicy());
                        tr.setSite(tradingItem.getSite());
                        tr.setCreateUser(kml.getUserId());
                        this.iTradingReturnpolicy.saveTradingReturnpolicy(tr);
                        tradingItem.setReturnpolicyId(tr.getId());
                    } else {
                        tr = litr.get(0);
                        tradingItem.setReturnpolicyId(tr.getId());
                    }
                }
                //物品所在地
                if (item.getLocation() != null) {
                    TradingItemAddressExample tiae = new TradingItemAddressExample();
                    tiae.createCriteria().andAddressEqualTo(item.getLocation()).andCreateUserEqualTo(kml.getUserId());
                    List<TradingItemAddress> liadd = this.tradingItemAddressMapper.selectByExample(tiae);
                    if (liadd == null || liadd.size() == 0) {
                        Map m = new HashMap();
                        m.put("type", "country");
                        m.put("value", item.getCountry());
                        List<TradingDataDictionary> x = DataDictionarySupport.getTradingDataDictionaryByMap(m);
                        if (x != null && x.size() > 0) {
                            TradingDataDictionary tdd = x.get(0);
                            TradingItemAddress tia = this.iTradingItemAddress.toDAOPojo(item.getCountry(), item.getLocation(), tdd.getId(), item.getPostalCode());
                            tia.setCreateUser(kml.getUserId());
                            this.iTradingItemAddress.saveItemAddress(tia);
                            tradingItem.setItemLocationId(tia.getId());
                        }
                    } else {
                        tradingItem.setItemLocationId(liadd.get(0).getId());
                    }
                }
                //支付方式
                if (item.getPayPalEmailAddress() != null) {
                    UsercontrollerPaypalAccountExample upae = new UsercontrollerPaypalAccountExample();
                    upae.createCriteria().andPaypalAccountEqualTo(item.getPayPalEmailAddress()).andCreateUserEqualTo(kml.getUserId());
                    List<UsercontrollerPaypalAccount> liuser = this.usercontrollerPaypalAccountMapper.selectByExample(upae);
                    if (liuser != null && liuser.size() > 0) {
                        UsercontrollerPaypalAccount userPay = liuser.get(0);

                        TradingPaypalExample tpe = new TradingPaypalExample();
                        tpe.createCriteria().andPaypalEqualTo(userPay.getId() + "").andCreateUserEqualTo(tradingItem.getCreateUser()).andSiteEqualTo(tradingItem.getSite());
                        List<TradingPaypal> litp = this.tradingPaypalMapper.selectByExample(tpe);
                        if (litp == null || litp.size() == 0) {
                            TradingPaypal tp = new TradingPaypal();
                            tp.setPayName(item.getPayPalEmailAddress());
                            tp.setPaypal(userPay.getId() + "");
                            tp.setSite(tradingItem.getSite());
                            tp.setCreateUser(kml.getUserId());
                            this.iTradingPayPal.savePaypal(tp);
                            tradingItem.setPayId(tp.getId());
                        } else {
                            tradingItem.setPayId(litp.get(0).getId());
                        }
                    } else {
                        UsercontrollerPaypalAccount upa = new UsercontrollerPaypalAccount();
                        upa.setCreateUser(tradingItem.getCreateUser());
                        upa.setEmail(item.getPayPalEmailAddress());
                        upa.setPaypalAccount(item.getPayPalEmailAddress());
                        upa.setStatus("1");
                        upa.setCreateTime(new Date());
                        this.usercontrollerPaypalAccountMapper.insertSelective(upa);
                        TradingPaypal tp = new TradingPaypal();
                        tp.setPayName(item.getPayPalEmailAddress());
                        tp.setPaypal(upa.getId() + "");
                        tp.setSite(tradingItem.getSite());
                        tp.setCreateUser(kml.getUserId());
                        this.iTradingPayPal.savePaypal(tp);
                        tradingItem.setPayId(tp.getId());
                    }
                }
                //买家要求
                if (item.getBuyerRequirementDetails() != null) {
                    BuyerRequirementDetails brd = item.getBuyerRequirementDetails();
                    TradingBuyerRequirementDetailsExample tbrd = new TradingBuyerRequirementDetailsExample();
                    TradingBuyerRequirementDetailsExample.Criteria cri = tbrd.createCriteria();
                    if (brd.getMaximumBuyerPolicyViolations() != null) {
                        cri.andPolicyCountEqualTo(brd.getMaximumBuyerPolicyViolations().getCount().longValue()).andPolicyPeriodEqualTo(brd.getMaximumBuyerPolicyViolations().getPeriod());
                    }
                    if (brd.getMaximumUnpaidItemStrikesInfo() != null) {
                        cri.andUnpaidCountEqualTo(brd.getMaximumUnpaidItemStrikesInfo().getCount().longValue()).andUnpaidPeriodEqualTo(brd.getMaximumUnpaidItemStrikesInfo().getPeriod());
                    }
                    cri.andCreateUserEqualTo(kml.getUserId());
                    cri.andSiteCodeEqualTo(Integer.parseInt(tradingItem.getSite()));
                    if (brd.getVerifiedUserRequirements() != null) {
                        cri.andVerifiedFlagEqualTo(brd.getVerifiedUserRequirements().getVerifiedUser() == true ? "1" : "0");
                        cri.andVerifiedFeedbackscoreEqualTo(Long.parseLong(brd.getVerifiedUserRequirements().getMinimumFeedbackScore() + ""));
                    }
                    List<TradingBuyerRequirementDetails> litbrd = this.tradingBuyerRequirementDetailsMapper.selectByExample(tbrd);
                    if (litbrd == null || litbrd.size() == 0) {
                        TradingBuyerRequirementDetails tbrds = new TradingBuyerRequirementDetails();
                        tbrds.setName("");
                        tbrds.setBuyerFlag("0");
                        if (brd != null) {
                            if (brd.getMaximumBuyerPolicyViolations() != null) {
                                tbrds.setPolicyCount(brd.getMaximumBuyerPolicyViolations().getCount().longValue());
                                tbrds.setPolicyPeriod(brd.getMaximumBuyerPolicyViolations().getPeriod());
                            }
                            if (brd.getMaximumUnpaidItemStrikesInfo() != null) {
                                tbrds.setUnpaidCount(brd.getMaximumUnpaidItemStrikesInfo().getCount().longValue());
                                tbrds.setUnpaidPeriod(brd.getMaximumUnpaidItemStrikesInfo().getPeriod());
                            }
                            if (brd.getVerifiedUserRequirements() != null) {
                                tbrds.setVerifiedFlag(brd.getVerifiedUserRequirements().getVerifiedUser() == true ? "1" : "0");
                                tbrds.setVerifiedFeedbackscore(Long.parseLong(brd.getVerifiedUserRequirements().getMinimumFeedbackScore() + ""));
                            }

                            if (brd.getMinimumFeedbackScore() != null) {
                                tbrds.setMinimumfeedbackscore(brd.getMinimumFeedbackScore().longValue());
                            } else {
                                tbrds.setMinimumfeedbackscore(null);
                            }
                            if (brd.getMaximumItemRequirements().getMinimumFeedbackScore() != null) {
                                tbrds.setFeedbackscore(Long.parseLong(brd.getMaximumItemRequirements().getMinimumFeedbackScore() + ""));
                            } else {
                                tbrds.setFeedbackscore(null);
                            }
                        }
                        tbrds.setSiteCode(Integer.parseInt(tradingItem.getSite()));
                        tbrds.setCreateUser(kml.getUserId());
                        this.iTradingBuyerRequirementDetails.saveBuyerRequirementDetails(tbrds);
                        tradingItem.setBuyerId(tbrds.getId());
                    } else {
                        tradingItem.setBuyerId(litbrd.get(0).getId());
                    }
                }
                //折扣信息
                if (item.getDiscountPriceInfo() != null) {
                    DiscountPriceInfo dpi = item.getDiscountPriceInfo();
                    TradingDiscountpriceinfoExample tde = new TradingDiscountpriceinfoExample();
                    TradingDiscountpriceinfoExample.Criteria cri = tde.createCriteria();
                    cri.andCreateUserEqualTo(kml.getUserId()).andIsShippingfeeEqualTo("1");
                    if (!ObjectUtils.isLogicalNull(dpi.getMadeForOutletComparisonPrice().getValue())) {
                        cri.andMadeforoutletcomparisonpriceEqualTo(Double.parseDouble(dpi.getMadeForOutletComparisonPrice().getValue() + ""));
                    }
                    if (!ObjectUtils.isLogicalNull(dpi.getMinimumAdvertisedPrice().getValue())) {
                        cri.andMinimumadvertisedpriceEqualTo(Double.parseDouble(dpi.getMinimumAdvertisedPrice().getValue() + ""));
                    }
                    List<TradingDiscountpriceinfo> litdf = this.tradingDiscountpriceinfoMapper.selectByExample(tde);
                    if (litdf == null || litdf.size() == 0) {
                        TradingDiscountpriceinfo tdpi = new TradingDiscountpriceinfo();
                        ObjectUtils.toInitPojoForInsert(tdpi);
                        tdpi.setName("折扣信息");
                        tdpi.setEbayAccount(tradingItem.getEbayAccount());
                        //tdpi.setDisStarttime(DateUtils.parseDateTime(dpi.get + ":00"));
                        //tdpi.setDisEndtime(DateUtils.parseDateTime(disEndtime + ":00"));
                        tdpi.setIsShippingfee("1");
                        if (!ObjectUtils.isLogicalNull(dpi.getMadeForOutletComparisonPrice().getValue())) {
                            tdpi.setMadeforoutletcomparisonprice(Double.parseDouble(dpi.getMadeForOutletComparisonPrice().getValue() + ""));
                        }
                        if (!ObjectUtils.isLogicalNull(dpi.getMinimumAdvertisedPrice().getValue())) {
                            tdpi.setMinimumadvertisedprice(Double.parseDouble(dpi.getMinimumAdvertisedPrice().getValue() + ""));
                        }
                        tdpi.setMinimumadvertisedpriceexposure("DuringCheckout");
                        tdpi.setCreateUser(kml.getUserId());
                        this.iTradingDiscountPriceInfo.saveDiscountpriceinfo(tdpi);
                        tradingItem.setDiscountpriceinfoId(tdpi.getId());
                    } else {
                        tradingItem.setDiscountpriceinfoId(litdf.get(0).getId());
                    }

                }
                //运输选项
                if (item.getShippingDetails() != null) {
                    ShippingDetails shippingDetails = item.getShippingDetails();
                    TradingShippingdetailsExample tse = new TradingShippingdetailsExample();
                    TradingShippingdetailsExample.Criteria cri = tse.createCriteria();
                    cri.andCreateUserEqualTo(kml.getUserId()).andEbayAccountEqualTo(tradingItem.getEbayAccount()).andSiteEqualTo(tradingItem.getSite());
                    if (shippingDetails.getShippingType() != null) {
                        cri.andShippingtypeEqualTo(shippingDetails.getShippingType());
                    }
                    if (shippingDetails.getShippingServiceOptions() != null && shippingDetails.getShippingServiceOptions().size() > 0) {
                        cri.andCountTypeEqualTo(shippingDetails.getShippingServiceOptions().size() + "");
                    }
                    if (shippingDetails.getInternationalShippingServiceOption() != null && shippingDetails.getInternationalShippingServiceOption().size() > 0) {
                        cri.andInterCountTypeEqualTo(shippingDetails.getInternationalShippingServiceOption().size() + "");
                    }
                    List<TradingShippingdetails> lits = this.tradingShippingdetailsMapper.selectByExample(tse);
                    if (lits == null || lits.size() == 0) {
                        TradingShippingdetails tradingShippingdetails = new TradingShippingdetails();

                        ConvertPOJOUtil.convert(tradingShippingdetails, shippingDetails);
                        tradingShippingdetails.setCreateUser(kml.getUserId());
                        tradingShippingdetails.setEbayAccount(tradingItem.getEbayAccount());
                        tradingShippingdetails.setSite(tradingItem.getSite());
                        if(shippingDetails.getShippingServiceOptions()!=null) {
                            tradingShippingdetails.setCountType(shippingDetails.getShippingServiceOptions().size() + "");
                        }
                        if(shippingDetails.getInternationalShippingServiceOption()!=null) {
                            tradingShippingdetails.setInterCountType(shippingDetails.getInternationalShippingServiceOption().size() + "");
                        }

                        this.iTradingShippingDetails.saveShippingDetails(tradingShippingdetails);
                        tradingItem.setShippingDeailsId(tradingShippingdetails.getId());
                        //保存国网运输详情
                        this.iTradingShippingServiceOptions.deleteByParentId(tradingShippingdetails.getId());
                        List<ShippingServiceOptions> lisso = shippingDetails.getShippingServiceOptions();


                        for (int i = 0; i < lisso.size(); i++) {
                            ShippingServiceOptions sso = lisso.get(i);
                            sso.setShippingServicePriority((i + 1));
                            TradingShippingserviceoptions tsp = this.iTradingShippingServiceOptions.toDAOPojo(sso);
                            Map ms = new HashMap();
                            ms.put("type","domestic transportation");
                            ms.put("value",tsp.getShippingservice());
                            ms.put("parentId",tradingItem.getSite());
                            List<TradingDataDictionary> litdd = DataDictionarySupport.getTradingDataDictionaryByMap(ms);
                            tsp.setShippingservice(litdd.get(0).getId()+"");
                            tsp.setParentUuid(tradingShippingdetails.getUuid());
                            tsp.setParentId(tradingShippingdetails.getId());
                            tsp.setCreateUser(kml.getUserId());
                            this.iTradingShippingServiceOptions.saveShippingServiceOptions(tsp);
                        }
                        //保存国际运输详情,包括运送到的地方
                        iTradingInternationalShippingServiceOption.deleteByParentId(tradingShippingdetails.getId());
                        if (shippingDetails.getInternationalShippingServiceOption() != null) {
                            List<InternationalShippingServiceOption> liinter = shippingDetails.getInternationalShippingServiceOption();
                            for (int i = 0; i < liinter.size(); i++) {
                                InternationalShippingServiceOption isso = liinter.get(i);

                                isso.setShippingServicePriority((i + 1));
                                TradingInternationalshippingserviceoption tiss = this.iTradingInternationalShippingServiceOption.toDAOPojo(isso);
                                Map ms = new HashMap();
                                ms.put("type","International transport");
                                ms.put("value",tiss.getShippingservice());
                                //ms.put("parentId",tradingItem.getSite());
                                List<TradingDataDictionary> litdd = DataDictionarySupport.getTradingDataDictionaryByMap(ms);
                                tiss.setShippingservice(litdd.get(0).getId()+"");
                                tiss.setParentId(tradingShippingdetails.getId());
                                tiss.setParentUuid(tradingShippingdetails.getUuid());
                                tiss.setCreateUser(kml.getUserId());
                                this.iTradingInternationalShippingServiceOption.saveInternationalShippingServiceOption(tiss);
                                List<String> toli = isso.getShipToLocation();
                                this.iTradingAttrMores.deleteByParentId("ShipToLocation", tiss.getId());
                                if (toli != null && toli.size() > 0) {
                                    for (String str : toli) {
                                        TradingAttrMores tam = this.iTradingAttrMores.toDAOPojo("ShipToLocation", str);
                                        tam.setParentId(tiss.getId());
                                        tam.setParentUuid(tiss.getUuid());
                                        this.iTradingAttrMores.saveAttrMores(tam);
                                    }
                                }
                            }
                        }
                        this.iTradingAttrMores.deleteByParentId("ExcludeShipToLocation", tradingShippingdetails.getId());
                        //保存不运输到的地方
                        List<String> liexc = item.getShippingDetails().getExcludeShipToLocation();
                        if (!ObjectUtils.isLogicalNull(liexc)) {
                            for (String nostr : liexc) {
                                TradingAttrMores tam = this.iTradingAttrMores.toDAOPojo("ExcludeShipToLocation", nostr);
                                tam.setParentId(tradingShippingdetails.getId());
                                tam.setParentUuid(tradingShippingdetails.getUuid());
                                this.iTradingAttrMores.saveAttrMores(tam);
                            }
                        }
                    }else{
                        tradingItem.setShippingDeailsId(lits.get(0).getId());
                    }
                }
                this.tradingItemMapper.updateByPrimaryKeySelective(tradingItem);
            }
            DataDictionarySupport.removePublicUserConfig(kml.getUserId());
        }
   // }

    /**
     * 下载图片到图片服务器
     * @param targeturl
     * @param loginid
     * @param sku
     * @return
     */
    private String unFtpPoto(String targeturl,String loginid,String sku){
        try {
            URL url = new URL(targeturl);
            //打开链接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //设置请求方式为"GET"
            conn.setRequestMethod("GET");
            //超时响应时间为5秒
            conn.setConnectTimeout(5 * 1000);
            //通过输入流获取图片数据
            InputStream inStream = conn.getInputStream();
            String stuff = MyStringUtil.getExtension(targeturl, "");
            String fileName = FtpUploadFile.ftpUploadFile(inStream, loginid + "/" + sku, stuff);
            inStream.close();
            return fileName;
        }catch(Exception e){
            return null;
        }
    }

    /**
     * 删除范本信息，只改变状态
     * @param ids
     */
    @Override
    public void delItem(String[] ids){
        for(String id:ids){
            TradingItem tradingItem = this.tradingItemMapper.selectByPrimaryKey(Long.parseLong(id));
            tradingItem.setCheckFlag("1");
            this.tradingItemMapper.updateByPrimaryKey(tradingItem);
        }
    }

    /**
     * 重命名
     * @param ids
     * @param fileName
     */
    @Override
    public void rename(String[] ids,String fileName){
        for(String id:ids){
            TradingItem tradingItem = this.tradingItemMapper.selectByPrimaryKey(Long.parseLong(id));
            tradingItem.setItemName(fileName);
            this.tradingItemMapper.updateByPrimaryKey(tradingItem);
        }
    }

    /**
     * 复制
     * @param ids
     * @param ebayaccount
     */
    @Override
    public void copyItem(String[] ids,String ebayaccount) throws Exception {
        for(String id:ids){
            TradingItemWithBLOBs tradingItem = this.tradingItemMapper.selectByPrimaryKey(Long.parseLong(id));
            //复制多属性
            TradingVariations tradvars = this.iTradingVariations.selectByParentId(tradingItem.getId());
            List<TradingPicturedetails> lipicd = this.iTradingPictureDetails.selectByParentId(tradingItem.getId());
            List<TradingPublicLevelAttr> litplass = this.iTradingPublicLevelAttr.selectByParentId("ItemSpecifics", tradingItem.getId());
            tradingItem.setId(null);
            tradingItem.setEbayAccount(ebayaccount);
            tradingItem.setItemId(null);
            tradingItem.setIsFlag(null);

            this.saveTradingItem(tradingItem);

            if(tradvars!=null){
                TradingPictures tptures = this.iTradingPictures.selectParnetId(tradvars.getId());
                List<TradingPublicLevelAttr> litplavss = this.iTradingPublicLevelAttr.selectByParentId("VariationSpecificsSet",tradvars.getId());
                List<TradingVariation> litv = this.iTradingVariation.selectByParentId(tradvars.getId());
                tradvars.setId(null);
                tradvars.setParentId(tradingItem.getId());
                tradvars.setParentUuid(tradingItem.getUuid());
                tradvars.setCreateTime(new Date());
                this.iTradingVariations.saveVariations(tradvars);

                if(tptures!=null){
                    List<TradingPublicLevelAttr> litpla = this.iTradingPublicLevelAttr.selectByParentId("VariationSpecificPictureSet",tptures.getId());
                    tptures.setId(null);
                    tptures.setCreateTime(new Date());
                    tptures.setParentId(tradvars.getId());
                    tptures.setParentUuid(tradvars.getUuid());
                    this.iTradingPictures.savePictures(tptures);
                    for(TradingPublicLevelAttr tpla:litpla){
                        List<TradingPublicLevelAttr> livalue = this.iTradingPublicLevelAttr.selectByParentId("VariationSpecificValue",tpla.getId());
                        List<TradingAttrMores> liname = this.iTradingAttrMores.selectByParnetid(tpla.getId(),"MuAttrPictureURL");
                        tpla.setId(null);
                        tpla.setParentId(tptures.getId());
                        tpla.setParentUuid(tptures.getUuid());
                        this.iTradingPublicLevelAttr.savePublicLevelAttr(tpla);

                        if(livalue!=null&&livalue.size()>0){
                            TradingPublicLevelAttr tplavsv = livalue.get(0);
                            tplavsv.setId(null);
                            tplavsv.setParentId(tpla.getId());
                            tplavsv.setParentUuid(tpla.getUuid());
                            this.iTradingPublicLevelAttr.savePublicLevelAttr(tplavsv);
                        }
                        if(liname!=null&&liname.size()>0){
                            for(TradingAttrMores tam : liname){
                                tam.setId(null);
                                tam.setParentId(tpla.getId());
                                tam.setParentUuid(tpla.getUuid());
                                this.iTradingAttrMores.saveAttrMores(tam);
                            }
                        }
                    }
                }

                if(litplavss!=null&&litplavss.size()>0){
                    for(TradingPublicLevelAttr tpla : litplavss){
                        List<TradingPublicLevelAttr> linvlist = this.iTradingPublicLevelAttr.selectByParentId("NameValueList",tpla.getId());
                        tpla.setId(null);
                        tpla.setParentId(tradvars.getId());
                        tpla.setParentUuid(tradvars.getUuid());
                        this.iTradingPublicLevelAttr.savePublicLevelAttr(tpla);

                        for(TradingPublicLevelAttr nvlist : linvlist){
                            NameValueList nvl = new NameValueList();
                            List<TradingAttrMores> liname = this.iTradingAttrMores.selectByParnetid(nvlist.getId(),"Name");
                            List<TradingAttrMores> lival = this.iTradingAttrMores.selectByParnetid(nvlist.getId(),"Value");
                            nvlist.setId(null);
                            nvlist.setParentId(tpla.getId());
                            nvlist.setParentUuid(tpla.getUuid());
                            this.iTradingPublicLevelAttr.savePublicLevelAttr(nvlist);

                            if(liname!=null&&liname.size()>0){
                                TradingAttrMores tam = liname.get(0);
                                tam.setId(null);
                                tam.setParentId(nvlist.getId());
                                tam.setParentUuid(nvlist.getUuid());
                                this.iTradingAttrMores.saveAttrMores(tam);
                            }
                            if(lival!=null&&lival.size()>0){
                                for(TradingAttrMores str : lival){
                                    str.setId(null);
                                    str.setParentId(nvlist.getId());
                                    str.setParentUuid(nvlist.getUuid());
                                    this.iTradingAttrMores.saveAttrMores(str);
                                }
                            }
                        }
                    }
                }
                if(litv!=null&&litv.size()>0){
                    for(TradingVariation tv:litv){
                        List<TradingPublicLevelAttr> livs = this.iTradingPublicLevelAttr.selectByParentId("VariationSpecifics",tv.getId());
                        tv.setId(null);
                        tv.setParentId(tradvars.getId());
                        tv.setParentUuid(tradvars.getUuid());
                        tv.setCreateTime(new Date());
                        this.iTradingVariation.saveVariation(tv);
                        if(livs!=null&&livs.size()>0){
                            for(TradingPublicLevelAttr tpla : livs){
                                List<TradingPublicLevelAttr> linvlist = this.iTradingPublicLevelAttr.selectByParentId(null,tpla.getId());
                                tpla.setId(null);
                                tpla.setParentId(tv.getId());
                                tpla.setParentUuid(tv.getUuid());
                                this.iTradingPublicLevelAttr.savePublicLevelAttr(tpla);
                                for(TradingPublicLevelAttr nvlist : linvlist){
                                    nvlist.setId(null);
                                    nvlist.setParentId(tpla.getId());
                                    nvlist.setParentUuid(tpla.getUuid());
                                    this.iTradingPublicLevelAttr.savePublicLevelAttr(nvlist);
                                }
                            }
                        }
                    }
                }
            }
            //保存图片信息

            if (lipicd != null&&lipicd.size()>0) {
                TradingPicturedetails tpicd = lipicd.get(0);
                List<TradingAttrMores> litam = this.iTradingAttrMores.selectByParnetid( tpicd.getId(),"PictureURL");
                tpicd.setId(null);
                tpicd.setParentId(tradingItem.getId());
                tpicd.setParentUuid(tradingItem.getUuid());
                this.iTradingPictureDetails.savePictureDetails(tpicd);
                if(litam!=null&&litam.size()>0){
                    for(TradingAttrMores tam:litam){
                        tam.setId(null);
                        tam.setParentId(tpicd.getId());
                        tam.setParentUuid(tpicd.getUuid());
                        this.iTradingAttrMores.saveAttrMores(tam);
                    }
                }

            }

            //保存属性值

            if(litplass!=null&&litplass.size()>0){
                TradingPublicLevelAttr tpa = litplass.get(0);

                List<TradingPublicLevelAttr> litpais = this.iTradingPublicLevelAttr.selectByParentId(null, tpa.getId());
                tpa.setId(null);
                tpa.setParentId(tradingItem.getId());
                tpa.setParentUuid(tradingItem.getUuid());
                this.iTradingPublicLevelAttr.savePublicLevelAttr(tpa);
                for(TradingPublicLevelAttr tpla:litpais){
                    tpla.setId(null);
                    tpla.setParentId(tpa.getId());
                    tpla.setParentUuid(tpa.getUuid());
                    this.iTradingPublicLevelAttr.savePublicLevelAttr(tpla);
                }
            }

        }
    }
    @Override
    public Item toEditItem(TradingItem tradingItem) throws Exception {
        Item item = new Item();
        ConvertPOJOUtil.convert(item,tradingItem);
        if(tradingItem.getListingtype().equals("FixedPriceItem")){//固价刊登
            StartPrice sp = new StartPrice();
            sp.setCurrencyID(tradingItem.getCurrency());
            sp.setValue(tradingItem.getStartprice());
            item.setStartPrice(sp);
        }else if(tradingItem.getListingtype().equals("2")){//多属性

        }else if(tradingItem.getListingtype().equals("Chinese")){//拍买价
            TradingAddItem tai = this.iTradingAddItem.selectParentId(tradingItem.getId());
            Asserts.assertTrue(!ObjectUtils.isLogicalNull(tai),"拍卖价格不能为空！");
            item.setBuyItNowPrice(tai.getBuyitnowprice().doubleValue());
        }
        PrimaryCategory pc = new PrimaryCategory();
        pc.setCategoryID(tradingItem.getCategoryid());
        item.setPrimaryCategory(pc);
        TradingPaypal tpay = this.iTradingPayPal.selectById(tradingItem.getPayId());
        TradingItemAddress tadd = this.iTradingItemAddress.selectById(tradingItem.getItemLocationId());
        TradingShippingdetails tshipping = this.iTradingShippingDetails.selectById(tradingItem.getShippingDeailsId());
        TradingDiscountpriceinfo tdiscount = this.iTradingDiscountPriceInfo.selectById(tradingItem.getDiscountpriceinfoId());
        TradingBuyerRequirementDetails tbuyer = this.iTradingBuyerRequirementDetails.selectById(tradingItem.getBuyerId());
        TradingReturnpolicy treturn = this.iTradingReturnpolicy.selectById(tradingItem.getReturnpolicyId());

        //组装刑登xml
        //组装买家要求
        if(tbuyer!=null) {
            BuyerRequirementDetails brd = this.iTradingBuyerRequirementDetails.toXmlPojo(tradingItem.getBuyerId());
            item.setBuyerRequirementDetails(brd);
        }
        //组装退货政策
        if(treturn!=null) {
            ReturnPolicy rp = this.iTradingReturnpolicy.toXmlPojo(tradingItem.getReturnpolicyId());
            item.setReturnPolicy(rp);
            item.getReturnPolicy().setReturnsAcceptedOption(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(item.getReturnPolicy().getReturnsAcceptedOption())).getValue());
            item.getReturnPolicy().setReturnsWithinOption(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(item.getReturnPolicy().getReturnsWithinOption())).getValue());
            item.getReturnPolicy().setRefundOption(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(item.getReturnPolicy().getRefundOption())).getValue());
            item.getReturnPolicy().setShippingCostPaidByOption(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(item.getReturnPolicy().getShippingCostPaidByOption())).getValue());
        }
        //运输详情
        if(tshipping!=null) {
            ShippingDetails sd = this.iTradingShippingDetails.toXmlPojo(tradingItem.getShippingDeailsId());
            item.setShippingDetails(sd);
        }
        String template = "";
        if(tradingItem.getTemplateId()!=null){//如果选择了模板
            TradingTemplateInitTable tttt = this.iTradingTemplateInitTable.selectById(tradingItem.getTemplateId());
            template = tttt.getTemplateHtml();
            template.replace("{ProductDetail}",item.getDescription());
            if(tttt!=null&&tradingItem.getSellerItemInfoId()!=null){//如果选择了卖家描述
                TradingDescriptionDetailsWithBLOBs tdd = this.iTradingDescriptionDetails.selectById(tradingItem.getSellerItemInfoId());
                template.replace("{PaymentMethodTitle}",tdd.getPayTitle()==null?"PayTitle":tdd.getPayTitle());
                template.replace("{PaymentMethod}",tdd.getPayInfo());

                template.replace("{ShippingDetailTitle}",tdd.getShippingTitle()==null?"ShippingTitle()":tdd.getShippingTitle());
                template.replace("{ShippingDetail}",tdd.getShippingInfo());

                template.replace("{SalesPolicyTitle}",tdd.getGuaranteeTitle()==null?"GuaranteeTitle()":tdd.getGuaranteeTitle());
                template.replace("{SalesPolicy}",tdd.getGuaranteeInfo());

                template.replace("{AboutUsTitle}",tdd.getFeedbackTitle()==null?"FeedbackTitle()":tdd.getFeedbackTitle());
                template.replace("{AboutUs}",tdd.getFeedbackInfo());

                template.replace("{ContactUsTitle}",tdd.getContactTitle()==null?"ContactTitle":tdd.getContactTitle());
                template.replace("{ContactUs}",tdd.getContactInfo());

            }
        }else{//未选择模板，
            template+=item.getDescription()+"</br>";
            if(tradingItem.getSellerItemInfoId()!=null){//如果选择了卖家描述
                TradingDescriptionDetailsWithBLOBs tdd = this.iTradingDescriptionDetails.selectById(tradingItem.getSellerItemInfoId());

                template+=tdd.getPayTitle()+"</br>"+tdd.getPayInfo()+"</br>";
                template+=tdd.getShippingTitle()+"</br>"+tdd.getShippingInfo()+"</br>";
                template+=tdd.getGuaranteeTitle()+"</br>"+tdd.getGuaranteeInfo()+"</br>";
                template+=tdd.getFeedbackTitle()+"</br>"+tdd.getFeedbackInfo()+"</br>";
                template+=tdd.getContactTitle()+"</br>"+tdd.getContactInfo()+"</br>";

            }
        }
        item.setDescription(template);

        TradingItemAddress tia = this.iTradingItemAddress.selectById(tradingItem.getItemLocationId());
        item.setCountry(DataDictionarySupport.getTradingDataDictionaryByID(tia.getCountryId()).getValue());
        item.setCurrency(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(item.getSite())).getValue1());
        item.setSite(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(item.getSite())).getValue());
        item.setPostalCode(tia.getPostalcode());

        TradingPaypal tp = this.iTradingPayPal.selectById(tradingItem.getPayId());
        if(tp!=null) {
            item.setPayPalEmailAddress(payPalService.selectById(Long.parseLong(tp.getPaypal())).getEmail());
        }
        List<String> limo = new ArrayList();
        limo.add("PayPal");
        item.setPaymentMethods(limo);
        if(item.getListingType().equals("Chinese")) {
            item.setListingDuration(item.getListingDuration() == null ? "GTC" : item.getListingDuration());
        }else{
            item.setListingDuration("GTC");
        }
        item.setDispatchTimeMax(0);


        if(tradingItem.getListingtype().equals("2")){//多属性
            TradingVariations tradvars = this.iTradingVariations.selectByParentId(tradingItem.getId());
            Variations vars = new Variations();
            if(tradvars!=null){
                TradingPictures tptures = this.iTradingPictures.selectParnetId(tradvars.getId());
                if(tptures!=null){
                    Pictures pic = new Pictures();
                    List<TradingPublicLevelAttr> litpla = this.iTradingPublicLevelAttr.selectByParentId("VariationSpecificPictureSet",tptures.getId());
                    List<VariationSpecificPictureSet> livss = new ArrayList<VariationSpecificPictureSet>();
                    for(TradingPublicLevelAttr tpla:litpla){
                        VariationSpecificPictureSet vss = new VariationSpecificPictureSet();
                        List<TradingPublicLevelAttr> livalue = this.iTradingPublicLevelAttr.selectByParentId("VariationSpecificValue",tpla.getId());
                        vss.setVariationSpecificValue(livalue.get(0).getValue());
                        List<String> listr = new ArrayList<String>();
                        List<TradingAttrMores> liname = this.iTradingAttrMores.selectByParnetid(tpla.getId(),"MuAttrPictureURL");
                        for(TradingAttrMores tam : liname){
                            List<TradingListingpicUrl> liplu = this.iTradingListingPicUrl.selectByMackId(tam.getAttr1());
                            TradingListingpicUrl plu = liplu.get(0);
                            listr.add(plu.getEbayurl());
                        }
                        vss.setPictureURL(listr);
                        livss.add(vss);
                    }
                    pic.setVariationSpecificPictureSet(livss);
                    if(tptures.getVariationspecificname()!=null){
                        pic.setVariationSpecificName(tptures.getVariationspecificname());
                    }
                    vars.setPictures(pic);
                }

                List<TradingPublicLevelAttr> litpla = this.iTradingPublicLevelAttr.selectByParentId("VariationSpecificsSet",tradvars.getId());
                if(litpla!=null&&litpla.size()>0){
                    VariationSpecificsSet vss = new VariationSpecificsSet();
                    for(TradingPublicLevelAttr tpla : litpla){
                        List<NameValueList> lnvl = new ArrayList<NameValueList>();
                        List<TradingPublicLevelAttr> linvlist = this.iTradingPublicLevelAttr.selectByParentId("NameValueList",tpla.getId());
                        for(TradingPublicLevelAttr nvlist : linvlist){
                            NameValueList nvl = new NameValueList();
                            List<TradingAttrMores> liname = this.iTradingAttrMores.selectByParnetid(nvlist.getId(),"Name");
                            nvl.setName(liname.get(0).getValue());
                            List<TradingAttrMores> lival = this.iTradingAttrMores.selectByParnetid(nvlist.getId(),"Value");
                            List<String> listr = new ArrayList<String>();
                            for(TradingAttrMores str : lival){
                                listr.add(str.getValue());
                            }
                            nvl.setValue(listr);
                            lnvl.add(nvl);
                        }
                        vss.setNameValueList(lnvl);
                    }
                    vars.setVariationSpecificsSet(vss);
                }


                List<Variation> liva = new ArrayList<Variation>();
                List<TradingVariation> litv = this.iTradingVariation.selectByParentId(tradvars.getId());
                if(litv!=null&&litv.size()>0){
                    for(TradingVariation tv:litv){
                        Variation var = new Variation();
                        if(tv.getQuantity()!=null){
                            var.setQuantity(tv.getQuantity().intValue());
                        }
                        if(tv.getStartprice()!=null){
                            StartPrice sp  = new StartPrice();
                            sp.setValue(tv.getStartprice());
                            var.setStartPrice(sp);
                        }
                        if(tv.getSku()!=null){
                            var.setSKU(tv.getSku());
                        }
                        List<TradingPublicLevelAttr> livs = this.iTradingPublicLevelAttr.selectByParentId("VariationSpecifics",tv.getId());
                        if(livs!=null&&livs.size()>0){
                            List<VariationSpecifics> livcs = new ArrayList<VariationSpecifics>();
                            for(TradingPublicLevelAttr tpla : livs){
                                VariationSpecifics vsss = new VariationSpecifics();
                                List<NameValueList> lnvl = new ArrayList<NameValueList>();
                                List<TradingPublicLevelAttr> linvlist = this.iTradingPublicLevelAttr.selectByParentId(null,tpla.getId());
                                for(TradingPublicLevelAttr nvlist : linvlist){
                                    NameValueList nvl = new NameValueList();
                                    nvl.setName(nvlist.getName());
                                    List<String> listr = new ArrayList<String>();
                                    listr.add(nvlist.getValue());
                                    nvl.setValue(listr);
                                    lnvl.add(nvl);
                                }
                                vsss.setNameValueList(lnvl);
                                livcs.add(vsss);
                            }
                            var.setVariationSpecifics(livcs);
                        }
                        if(tv.getMadeforoutletcomparisonprice()!=null||tv.getMinimumadvertisedprice()!=null||tv.getMinimumadvertisedpriceexposure()!=null||tv.getSoldoffebay()!=null||tv.getSoldonebay()!=null||tv.getOriginalretailprice()!=null){
                            DiscountPriceInfo dpi = new DiscountPriceInfo();
                            if(tv.getMadeforoutletcomparisonprice()!=null){
                                MadeForOutletComparisonPrice mfocp = new MadeForOutletComparisonPrice();
                                mfocp.setValue(tv.getMadeforoutletcomparisonprice());
                                dpi.setMadeForOutletComparisonPrice(mfocp);
                            }
                            if(tv.getMinimumadvertisedprice()!=null){
                                MinimumAdvertisedPrice map  = new MinimumAdvertisedPrice();
                                map.setValue(tv.getMinimumadvertisedprice());
                                dpi.setMinimumAdvertisedPrice(map);
                            }
                            if(tv.getMinimumadvertisedpriceexposure()!=null){
                                dpi.setMinimumAdvertisedPriceExposure(tv.getMinimumadvertisedpriceexposure());
                            }
                            if(tv.getOriginalretailprice()!=null){
                                OriginalRetailPrice orp = new OriginalRetailPrice();
                                orp.setValue(tv.getOriginalretailprice());
                                dpi.setOriginalRetailPrice(orp);
                            }
                            if(tv.getSoldonebay()!=null){
                                dpi.setSoldOneBay(tv.getSoldonebay().equals("1")?true:false);
                            }
                            if(tv.getSoldoffebay()!=null){
                                dpi.setSoldOffeBay(tv.getSoldoffebay().equals("1")?true:false);
                            }
                            var.setDiscountPriceInfo(dpi);
                        }
                        liva.add(var);
                    }
                    vars.setVariation(liva);
                }
            }
            item.setVariations(vars);
        }

        //图片
        List<TradingPicturedetails> litpd = this.iTradingPictureDetails.selectByParentId(tradingItem.getId());
        if(litpd!=null&&litpd.size()>0){
            TradingPicturedetails tpd = litpd.get(0);
            PictureDetails pds = new PictureDetails();
            List<TradingAttrMores> litam = this.iTradingAttrMores.selectByParnetid(tpd.getId(),"PictureURL");
            List<String> listr = new ArrayList<String>();
            for(TradingAttrMores tam : litam){
                List<TradingListingpicUrl> liplu = this.iTradingListingPicUrl.selectByMackId(tam.getAttr1());
                if(liplu!=null&&liplu.size()>0){
                    TradingListingpicUrl plu = liplu.get(0);
                    listr.add(plu.getEbayurl());
                }else {
                    listr.add(tam.getValue());
                }
            }
            pds.setPictureURL(listr);
            item.setPictureDetails(pds);
        }
        item.setListingType(item.getListingType().equals("2")?"FixedPriceItem":item.getListingType());
        item.setUUID(null);
        return item;
    }

    /**
     * 刊登成功后，保存成功后时间，及费用信息
     * @param xml
     * @param itemId
     * @throws DateParseException
     */
    @Override
    public void saveListingSuccess(String xml,String itemId) throws DateParseException {
        List<TradingListingSuccess> litls = this.iTradingListingSuccess.selectByItemid(itemId);
        Document document= null;
        try {
            document = DocumentHelper.parseText(xml);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        Element rootElt = document.getRootElement();
        Element recommend = rootElt.element("Fees");
        Iterator<Element> iter = recommend.elementIterator("Fee");
        List<Map> lim = new ArrayList<Map>();

        Double fee = 0.00;
        String currency = "";
        Date startDate=DateUtils.returnDate(rootElt.elementText("StartTime"));
        Date endDate=DateUtils.returnDate(rootElt.elementText("EndTime"));
        while (iter.hasNext()){
            Element ment = iter.next();
            fee+=Double.parseDouble(ment.elementText("Fee"));
            currency = ment.element("Fee").attributeValue("currencyID");
        }
        if(litls!=null&&litls.size()>0){
            TradingListingSuccess tls = litls.get(0);
            tls.setListingFee(fee);
            tls.setCurrency(currency);
            this.iTradingListingSuccess.save(tls);
        }else{
            TradingListingSuccess tls = new TradingListingSuccess();
            tls.setStartDate(startDate);
            tls.setEndDate(endDate);
            tls.setListingFee(fee);
            tls.setCurrency(currency);
            tls.setItemId(itemId);
            this.iTradingListingSuccess.save(tls);
        }
    }
}
