package com.trading.service.impl;

import com.base.database.customtrading.mapper.ItemMapper;
import com.base.database.trading.mapper.TradingItemMapper;
import com.base.database.trading.model.*;
import com.base.domains.querypojos.ItemQuery;
import com.base.mybatis.page.Page;
import com.base.utils.applicationcontext.RequestResponseContext;
import com.base.utils.cache.DataDictionarySupport;
import com.base.utils.common.ConvertPOJOUtil;
import com.base.utils.common.MyCollectionsUtil;
import com.base.utils.common.ObjectUtils;
import com.base.xmlpojo.trading.addproduct.*;
import com.trading.service.*;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 商品信息
 * Created by cz on 2014/7/23.
 */
@Service
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
    public void saveItem(Item item, TradingItem tradingItem) throws Exception {
        //保存商品信息到数据库中

        if(item.getVariations()!=null){
            item.getVariations().getPictures().setVariationSpecificName(item.getVariations().getVariationSpecificsSet().getNameValueList().get(0).getName());
        }


        tradingItem.setConditionid(item.getConditionID().longValue());
        tradingItem.setCategoryid(item.getPrimaryCategory().getCategoryID());
        tradingItem.setCurrency(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(item.getSite())).getValue1());
        tradingItem.setListingtype(item.getListingType());
        if(item.getStartPrice()!=null&&!"".equals(item.getStartPrice())){
            tradingItem.setStartprice(item.getStartPrice().getValue());
        }
        if(item.getOutOfStockControl()!=null) {
            tradingItem.setOutofstockcontrol(item.getOutOfStockControl() ? "1" : "0");
        }


        this.saveTradingItem(tradingItem);

        HttpServletRequest request = RequestResponseContext.getRequest();
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

                        this.iTradingAttrMores.deleteByParentId("PictureURL",tplas.getId());

                        List<String> listr = vsps.getPictureURL();
                        listr = MyCollectionsUtil.listUnique(listr);
                        for(String str: listr){
                            if(str!=null&&!"".equals(str)){
                                TradingAttrMores tams = this.iTradingAttrMores.toDAOPojo("PictureURL",str);
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
            this.iTradingPublicLevelAttr.deleteByParentID("ItemSpecifics",tradingItem.getId());
            TradingPublicLevelAttr tpla = this.iTradingPublicLevelAttr.toDAOPojo("ItemSpecifics",null);
            tpla.setParentId(tradingItem.getId());
            tpla.setParentUuid(tradingItem.getUuid());
            this.iTradingPublicLevelAttr.savePublicLevelAttr(tpla);

            this.iTradingPublicLevelAttr.deleteByParentID(null,tpla.getId());
            for(NameValueList nvl : linv){
                TradingPublicLevelAttr tpl = this.iTradingPublicLevelAttr.toDAOPojo(nvl.getName(),nvl.getValue().toString());
                tpl.setParentUuid(tpla.getUuid());
                tpl.setParentId(tpla.getId());
                this.iTradingPublicLevelAttr.savePublicLevelAttr(tpl);
            }
        }
    }

    @Override
    public List<ItemQuery> selectByItemList(Map map,Page page){
        return this.itemMapper.selectByItemList(map,page);
    }

    @Override
    public TradingItem selectById(Long id){
        return this.tradingItemMapper.selectByPrimaryKey(id);
    }
}
