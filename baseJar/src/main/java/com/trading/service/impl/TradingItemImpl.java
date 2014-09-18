package com.trading.service.impl;

import com.base.database.customtrading.mapper.ItemMapper;
import com.base.database.keymove.model.KeyMoveList;
import com.base.database.publicd.mapper.PublicUserConfigMapper;
import com.base.database.publicd.model.*;
import com.base.database.trading.mapper.*;
import com.base.database.trading.model.*;
import com.base.database.userinfo.mapper.UsercontrollerUserMapper;
import com.base.database.userinfo.model.UsercontrollerUser;
import com.base.domains.SessionVO;
import com.base.domains.querypojos.ItemQuery;
import com.base.mybatis.page.Page;
import com.base.utils.applicationcontext.RequestResponseContext;
import com.base.utils.cache.DataDictionarySupport;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.common.*;
import com.base.utils.ftpabout.FtpUploadFile;
import com.base.utils.imageManage.service.ImageService;
import com.base.xmlpojo.trading.addproduct.*;
import com.base.xmlpojo.trading.addproduct.attrclass.StartPrice;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.publicd.service.*;
import com.trading.service.*;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

/**
 * 商品信息
 * Created by cz on 2014/7/23.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TradingItemImpl implements com.trading.service.ITradingItem {

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


    @Override
    public void saveTradingItem(TradingItem pojo) throws Exception {
        if(pojo.getId()==null){
            ObjectUtils.toInitPojoForInsert(pojo);
            this.tradingItemMapper.insertSelective(pojo);
        }else{
            this.tradingItemMapper.updateByPrimaryKeySelective(pojo);
        }

    }

    @Override
    public TradingItem toDAOPojo(Item item) throws Exception {
        TradingItem pojo = new TradingItem();
        ConvertPOJOUtil.convert(pojo, item);
        ConvertPOJOUtil.convert(pojo, item.getBestOfferDetails());
        ConvertPOJOUtil.convert(pojo, item.getPrimaryCategory());
        ConvertPOJOUtil.convert(pojo, item.getQuantityInfo());
        ConvertPOJOUtil.convert(pojo, item.getQuantityRestrictionPerBuyer());

        return pojo;
    }

    @Override
    public Map saveItem(Item item, TradingItem tradingItem1) throws Exception {
        //保存商品信息到数据库中

        if(item.getVariations()!=null){
            Pictures pt = new Pictures();
            pt.setVariationSpecificName(item.getVariations().getVariationSpecificsSet().getNameValueList().get(0).getName());
            pt.setVariationSpecificPictureSet(item.getVariations().getPictures().getVariationSpecificPictureSet());
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
        //String [] dicUrl =
        for(int is =0;is<paypals.length;is++) {
            TradingItem tradingItem = new TradingItem();
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



            this.saveTradingItem(tradingItem);
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

                    //保存多属必图片信息
                    Pictures pictrue = item.getVariations().getPictures();
                    if(pictrue!=null){
                        TradingPictures tp = this.iTradingPictures.toDAOPojo(pictrue);
                        tp.setParentId(tv.getId());
                        tp.setParentUuid(tv.getUuid());
                        this.iTradingPictures.savePictures(tp);

                        List<VariationSpecificPictureSet> vspsli = pictrue.getVariationSpecificPictureSet();
                        this.iTradingPublicLevelAttr.deleteByParentID("VariationSpecificPictureSet",tp.getId());
                        for(VariationSpecificPictureSet vsps:vspsli){
                            TradingPublicLevelAttr tplas = this.iTradingPublicLevelAttr.toDAOPojo("VariationSpecificPictureSet",null);
                            tplas.setParentId(tp.getId());
                            tplas.setParentUuid(tp.getUuid());
                            this.iTradingPublicLevelAttr.savePublicLevelAttr(tplas);

                            this.iTradingPublicLevelAttr.deleteByParentID("VariationSpecificValue",tplas.getId());

                            TradingPublicLevelAttr tpname = this.iTradingPublicLevelAttr.toDAOPojo("VariationSpecificValue",vsps.getVariationSpecificValue());
                            tpname.setParentId(tplas.getId());
                            tpname.setParentUuid(tplas.getUuid());
                            this.iTradingPublicLevelAttr.savePublicLevelAttr(tpname);

                            this.iTradingAttrMores.deleteByParentId("MuAttrPictureURL",tplas.getId());

                            List<String> listr = vsps.getPictureURL();
                            listr = MyCollectionsUtil.listUnique(listr);
                            for(String str: listr){
                                if(str!=null&&!"".equals(str)){
                                    TradingAttrMores tams = this.iTradingAttrMores.toDAOPojo("MuAttrPictureURL",str);
                                    tams.setParentId(tplas.getId());
                                    tams.setParentUuid(tplas.getUuid());
                                    this.iTradingAttrMores.saveAttrMores(tams);
                                }
                            }
                        }
                    }
                }
            }
            //保存图片信息
            PictureDetails picd = item.getPictureDetails();
            if(picd!=null){
                TradingPicturedetails tpicd = this.iTradingPictureDetails.toDAOPojo(picd);
                tpicd.setParentId(tradingItem.getId());
                tpicd.setParentUuid(tradingItem.getUuid());
                this.iTradingPictureDetails.savePictureDetails(tpicd);
                this.iTradingAttrMores.deleteByParentId("PictureURL",tpicd.getId());
                String [] picurl = request.getParameterValues("PictureDetails_"+paypals[is]+".PictureURL");
                for(int i = 0;i<picurl.length;i++){
                    TradingAttrMores tam = this.iTradingAttrMores.toDAOPojo("PictureURL",picurl[i]);
                    tam.setParentId(tpicd.getId());
                    tam.setParentUuid(tpicd.getUuid());
                    this.iTradingAttrMores.saveAttrMores(tam);

                }
            }else{
                TradingPicturedetails tpicd = this.iTradingPictureDetails.toDAOPojo(picd);
                tpicd.setParentId(tradingItem.getId());
                tpicd.setParentUuid(tradingItem.getUuid());
                this.iTradingPictureDetails.savePictureDetails(tpicd);

                this.iTradingAttrMores.deleteByParentId("PictureURL",tpicd.getId());
                String [] picurl = request.getParameterValues("PictureDetails_"+paypals[is]+".PictureURL");
                for(int i = 0;i<picurl.length;i++){
                    TradingAttrMores tam = this.iTradingAttrMores.toDAOPojo("PictureURL",picurl[i]);
                    tam.setParentId(tpicd.getId());
                    tam.setParentUuid(tpicd.getUuid());
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
    public List<ItemQuery> selectByItemList(Map map,Page page){
        return this.itemMapper.selectByItemList(map,page);
    }

    @Override
    public TradingItem selectById(Long id){
        return this.tradingItemMapper.selectByPrimaryKey(id);
    }

    @Override
    public TradingItem selectByItemId(String itemId){
        TradingItemExample tie = new TradingItemExample();
        tie.createCriteria().andItemIdEqualTo(itemId);
        List<TradingItem> liti = this.tradingItemMapper.selectByExample(tie);
        if(liti==null||liti.size()==0){
            return null;
        }else{
            return liti.get(0);
        }
    }
    @Override
    public void updateTradingItem(Item item, TradingItem tradingItem) throws Exception {
        HttpServletRequest request = RequestResponseContext.getRequest();
        String [] selectType = request.getParameterValues("selectType");
        SessionVO c= SessionCacheSupport.getSessionVO();
        tradingItem = new TradingItem();
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
    }
    @Override
    public void saveListingItem(Item item, KeyMoveList kml) throws Exception {
        //for(int is = 0;is < liitem.size();is++){
            //Item item = liitem.get(is);
            TradingItemExample tie = new TradingItemExample();
            tie.createCriteria().andItemIdEqualTo(item.getItemID());
            List<TradingItem> liti = this.tradingItemMapper.selectByExampleWithBLOBs(tie);
            if(liti==null||liti.size()==0) {
                TradingItem tradingItem = new TradingItem();
                ConvertPOJOUtil.convert(tradingItem, item);
                tradingItem.setStartprice(item.getStartPrice().getValue());
                tradingItem.setIsFlag("Success");
                tradingItem.setEbayAccount(kml.getPaypalId());
                tradingItem.setCategoryid(item.getPrimaryCategory().getCategoryID());
                tradingItem.setConditionid(item.getConditionID().longValue());
                tradingItem.setCreateUser(kml.getUserId());
                tradingItem.setItemName(item.getTitle());
                if(item.getListingType().equals("FixedPriceItem")&&item.getVariations()!=null){//多属性
                    tradingItem.setListingtype("2");
                }else if(item.getListingType().equals("Chinese")){//拍卖
                    tradingItem.setListingtype(item.getListingType());
                }else{//固价
                    tradingItem.setListingtype(item.getListingType());

                }
                this.saveTradingItem(tradingItem);

                /**保存产品信息开始**/
                PublicItemInformation itemInformation=new PublicItemInformation();
                List<PublicUserConfig> liconf = DataDictionarySupport.getPublicUserConfigByType("itemType",kml.getUserId());
                //商品分类
                String typeid = "";
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
                }
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
                                TradingPublicLevelAttr tpl = this.iTradingPublicLevelAttr.toDAOPojo(vs.getName(), vs.getValue().get(i));
                                tpl.setParentUuid(tpla.getUuid());
                                tpl.setParentId(tpla.getId());

                                this.iTradingPublicLevelAttr.savePublicLevelAttr(tpl);
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
                                    TradingAttrMores tams = this.iTradingAttrMores.toDAOPojo("MuAttrPictureURL", str);
                                    tams.setParentId(tplas.getId());
                                    tams.setParentUuid(tplas.getUuid());
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
                    this.iTradingPictureDetails.savePictureDetails(tpicd);
                    UsercontrollerUser uu = this.usercontrollerUserMapper.selectByPrimaryKey(Integer.parseInt(tradingItem.getCreateUser() + ""));
                    this.iTradingAttrMores.deleteByParentId("PictureURL", tpicd.getId());
                    List<String> picurl = picd.getPictureURL();
                    for (int i = 0; i < picurl.size(); i++) {
                        TradingAttrMores tam = this.iTradingAttrMores.toDAOPojo("PictureURL", picurl.get(i));
                        tam.setParentId(tpicd.getId());
                        tam.setParentUuid(tpicd.getUuid());
                        this.iTradingAttrMores.saveAttrMores(tam);
                        //new一个URL对象
                        URL url = new URL(picurl.get(i));
                        //打开链接
                        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                        //设置请求方式为"GET"
                        conn.setRequestMethod("GET");
                        //超时响应时间为5秒
                        conn.setConnectTimeout(5 * 1000);
                        //通过输入流获取图片数据
                        InputStream inStream = conn.getInputStream();
                        String stuff= MyStringUtil.getExtension(picurl.get(i), "");
                        FtpUploadFile.ftpUploadFile(inStream, uu.getUserName() + "/" + item.getSKU(), stuff);
                        inStream.close();
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

                    Map m = new HashMap();
                    m.put("type", "RefundOption");
                    m.put("value", rp.getRefundOption());
                    List<TradingDataDictionary> x = DataDictionarySupport.getTradingDataDictionaryByMap(m);
                    if (x != null && x.size() > 0) {
                        TradingDataDictionary tdd = x.get(0);
                        cri.andRefundoptionEqualTo(tdd.getId() + "");
                        tr.setRefundoption(tdd.getId() + "");
                    }
                    Map m1 = new HashMap();
                    m1.put("type", "ReturnsWithinOption");
                    m1.put("value", rp.getReturnsWithinOption());
                    List<TradingDataDictionary> x1 = DataDictionarySupport.getTradingDataDictionaryByMap(m1);
                    if (x1 != null && x1.size() > 0) {
                        TradingDataDictionary tdd = x1.get(0);
                        cri.andReturnswithinoptionEqualTo(tdd.getId() + "");
                        tr.setReturnswithinoption(tdd.getId() + "");
                    }
                    Map m2 = new HashMap();
                    m2.put("type", "ReturnsAcceptedOption");
                    m2.put("value", rp.getReturnsAcceptedOption());
                    List<TradingDataDictionary> x2 = DataDictionarySupport.getTradingDataDictionaryByMap(m2);
                    if (x2 != null && x2.size() > 0) {
                        TradingDataDictionary tdd = x2.get(0);
                        cri.andReturnsacceptedoptionEqualTo(tdd.getId() + "");
                        tr.setReturnsacceptedoption(tdd.getId() + "");
                    }
                    Map m3 = new HashMap();
                    m3.put("type", "ShippingCostPaidByOption");
                    m3.put("value", rp.getShippingCostPaidByOption());
                    List<TradingDataDictionary> x3 = DataDictionarySupport.getTradingDataDictionaryByMap(m3);
                    if (x3 != null && x3.size() > 0) {
                        TradingDataDictionary tdd = x3.get(0);
                        cri.andShippingcostpaidbyoptionEqualTo(tdd.getId() + "");
                        tr.setShippingcostpaidbyoption(tdd.getId() + "");
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
                        cri.andMadeforoutletcomparisonpriceEqualTo(Long.parseLong(dpi.getMadeForOutletComparisonPrice().getValue() + ""));
                    }
                    if (!ObjectUtils.isLogicalNull(dpi.getMinimumAdvertisedPrice().getValue())) {
                        cri.andMinimumadvertisedpriceEqualTo(Long.parseLong(dpi.getMinimumAdvertisedPrice().getValue() + ""));
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
                            tdpi.setMadeforoutletcomparisonprice(Long.parseLong(dpi.getMadeForOutletComparisonPrice().getValue() + ""));
                        }
                        if (!ObjectUtils.isLogicalNull(dpi.getMinimumAdvertisedPrice().getValue())) {
                            tdpi.setMinimumadvertisedprice(Long.parseLong(dpi.getMinimumAdvertisedPrice().getValue() + ""));
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
                        tradingShippingdetails.setCountType(shippingDetails.getShippingServiceOptions().size() + "");
                        tradingShippingdetails.setInterCountType(shippingDetails.getInternationalShippingServiceOption().size() + "");

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
                                ms.put("parentId",tradingItem.getSite());
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
                    this.saveTradingItem(tradingItem);
                }
            }
            DataDictionarySupport.removePublicUserConfig(kml.getUserId());
        }
   // }
}
