package com.trading.service.impl;

import com.base.database.customtrading.mapper.ItemMapper;
import com.base.database.publicd.model.PublicDataDict;
import com.base.database.publicd.model.PublicUserConfig;
import com.base.database.trading.mapper.TradingItemMapper;
import com.base.database.trading.model.*;
import com.base.domains.SessionVO;
import com.base.domains.querypojos.ItemQuery;
import com.base.mybatis.page.Page;
import com.base.utils.applicationcontext.RequestResponseContext;
import com.base.utils.cache.DataDictionarySupport;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.common.ConvertPOJOUtil;
import com.base.utils.common.MyCollectionsUtil;
import com.base.utils.common.ObjectUtils;
import com.base.xmlpojo.trading.addproduct.*;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.trading.service.*;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
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
            item.getVariations().getPictures().setVariationSpecificName(item.getVariations().getVariationSpecificsSet().getNameValueList().get(0).getName());
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
        for(int is =0;is<paypals.length;is++) {
            TradingItem tradingItem = new TradingItem();
            tradingItem = tradingItem1;

            tradingItem.setEbayAccount(paypals[is]);
            if(is>=1){
                tradingItem.setId(null);
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
                List<String> lipic = item.getPictureDetails().getPictureURL();
                lipic = MyCollectionsUtil.listUnique(lipic);
                for(int i =0;i<lipic.size();i++){
                    TradingAttrMores tam = this.iTradingAttrMores.toDAOPojo("PictureURL",lipic.get(i));
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
}
