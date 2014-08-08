package com.trading.service.impl;

import com.base.database.customtrading.mapper.ItemMapper;
import com.base.database.trading.mapper.TradingItemMapper;
import com.base.database.trading.model.TradingAttrMores;
import com.base.database.trading.model.TradingItem;
import com.base.database.trading.model.TradingPicturedetails;
import com.base.database.trading.model.TradingPublicLevelAttr;
import com.base.domains.querypojos.ItemQuery;
import com.base.mybatis.page.Page;
import com.base.utils.cache.DataDictionarySupport;
import com.base.utils.common.ConvertPOJOUtil;
import com.base.utils.common.ObjectUtils;
import com.base.xmlpojo.trading.addproduct.Item;
import com.base.xmlpojo.trading.addproduct.ItemSpecifics;
import com.base.xmlpojo.trading.addproduct.NameValueList;
import com.base.xmlpojo.trading.addproduct.PictureDetails;
import com.trading.service.ITradingAttrMores;
import com.trading.service.ITradingPictureDetails;
import com.trading.service.ITradingPublicLevelAttr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        tradingItem.setConditionid(item.getConditionID().longValue());
        tradingItem.setCategoryid(item.getPrimaryCategory().getCategoryID());
        tradingItem.setCurrency(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(item.getSite())).getValue1());
        tradingItem.setListingtype(item.getListingType());
        if(item.getStartPrice()!=null&&!"".equals(item.getStartPrice())){
            tradingItem.setStartprice(item.getStartPrice().getValue());
        }
        this.saveTradingItem(tradingItem);
        //保存图片信息
        PictureDetails picd = item.getPictureDetails();
        if(picd!=null){
            TradingPicturedetails tpicd = this.iTradingPictureDetails.toDAOPojo(picd);
            tpicd.setParentId(tradingItem.getId());
            tpicd.setParentUuid(tradingItem.getUuid());
            this.iTradingPictureDetails.savePictureDetails(tpicd);

            this.iTradingAttrMores.deleteByParentId("PictureURL",tpicd.getId());
            List<String> lipic = item.getPictureDetails().getPictureURL();
            for(String str:lipic){
                TradingAttrMores tam = this.iTradingAttrMores.toDAOPojo("PictureURL",str);
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
