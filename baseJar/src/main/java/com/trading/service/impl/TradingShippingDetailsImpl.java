package com.trading.service.impl;

import com.base.database.customtrading.mapper.ShippingdetailsMapper;
import com.base.database.trading.mapper.TradingInternationalshippingserviceoptionMapper;
import com.base.database.trading.mapper.TradingShippingdetailsMapper;
import com.base.database.trading.mapper.TradingShippingserviceoptionsMapper;
import com.base.database.trading.model.*;
import com.base.domains.querypojos.ShippingdetailsQuery;
import com.base.mybatis.page.Page;
import com.base.utils.common.ConvertPOJOUtil;
import com.base.utils.common.ObjectUtils;
import com.base.utils.exception.Asserts;
import com.base.utils.xmlutils.PojoXmlUtil;
import com.base.xmlpojo.trading.addproduct.InternationalShippingServiceOption;
import com.base.xmlpojo.trading.addproduct.ShippingDetails;
import com.base.xmlpojo.trading.addproduct.ShippingServiceOptions;
import com.trading.service.ITradingAttrMores;
import com.trading.service.ITradingInternationalShippingServiceOption;
import com.trading.service.ITradingShippingServiceOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 运输选项
 * Created by cz on 2014/7/23.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TradingShippingDetailsImpl implements com.trading.service.ITradingShippingDetails {
    @Autowired
    private TradingShippingdetailsMapper tradingShippingdetailsMapper;
    @Autowired
    private ShippingdetailsMapper shippingdetailsMapper;
    @Autowired
    private TradingShippingserviceoptionsMapper tradingShippingserviceoptionsMapper;
    @Autowired
    private TradingInternationalshippingserviceoptionMapper tradingInternationalshippingserviceoptionMapper;
    @Autowired
    private ITradingShippingServiceOptions iTradingShippingServiceOptions;
    @Autowired
    private ITradingInternationalShippingServiceOption iTradingInternationalShippingServiceOption;
    @Autowired
    private ITradingAttrMores iTradingAttrMores;
    /**
     * 保存运输选项数据
     * @param pojo
     * @throws Exception
     */
    @Override
    public void saveShippingDetails(TradingShippingdetails pojo) throws Exception {
        if(pojo.getId()==null){
            ObjectUtils.toInitPojoForInsert(pojo);
            this.tradingShippingdetailsMapper.insertSelective(pojo);
        }else{
            TradingShippingdetails t = this.tradingShippingdetailsMapper.selectByPrimaryKey(pojo.getId());
            Asserts.assertTrue(t != null && t.getCreateUser() != null, "没有找到记录或者记录创建者为空");
            ObjectUtils.valiUpdate(t.getCreateUser(),TradingShippingserviceoptionsMapper.class,pojo.getId());
            this.tradingShippingdetailsMapper.updateByPrimaryKeySelective(pojo);
        }
    }

    @Override
    public TradingShippingdetails toDAOPojo(ShippingDetails shippingDetails) throws Exception {
        TradingShippingdetails pojo = new TradingShippingdetails();
        ObjectUtils.toInitPojoForInsert(pojo);
        ConvertPOJOUtil.convert(pojo,shippingDetails);
        ConvertPOJOUtil.convert(pojo,shippingDetails.getCalculatedShippingRate());
        ConvertPOJOUtil.convert(pojo,shippingDetails.getInsuranceDetails());
        ConvertPOJOUtil.convert(pojo,shippingDetails.getRateTableDetails());
        ConvertPOJOUtil.convert(pojo,shippingDetails.getSalesTax());
        return pojo;
    }
    /**
     * 查询列表数据
     */
    @Override
    public List<ShippingdetailsQuery> selectByShippingdetailsQuery(Map map,Page page){
        return this.shippingdetailsMapper.selectByShippingdetails(map,page);
    }

    /**
     * 通过主键查询运输信息
     * @param id
     * @return
     */
    @Override
    public TradingShippingdetails selectById(Long id){
        return this.tradingShippingdetailsMapper.selectByPrimaryKey(id);
    }

    /**
     * 通过运输选项ID查询国内运输列表
     * @param id
     * @return
     */
    @Override
    public List<TradingShippingserviceoptions> selectByShippingserviceoptions(Long id){
        TradingShippingserviceoptionsExample tse = new TradingShippingserviceoptionsExample();
        TradingShippingserviceoptionsExample.Criteria cri = tse.createCriteria();
        cri.andParentIdEqualTo(id);
        List<TradingShippingserviceoptions> litso = this.tradingShippingserviceoptionsMapper.selectByExample(tse);
        return litso;
    }

    /**
     * 通过运输选项ID查询国际运输列表
     * @param id
     * @return
     */
    @Override
    public List<TradingInternationalshippingserviceoption> selectByInternationalshippingserviceoption(Long id){
        TradingInternationalshippingserviceoptionExample ti = new TradingInternationalshippingserviceoptionExample();
        TradingInternationalshippingserviceoptionExample.Criteria cri = ti.createCriteria();
        cri.andParentIdEqualTo(id);
        List<TradingInternationalshippingserviceoption> liti = this.tradingInternationalshippingserviceoptionMapper.selectByExample(ti);
        return liti;
    }

    /**
     * 保存数据
     * @param tradingShippingdetails
     * @param shippingDetails
     * @param noLocations
     * @throws Exception
     */
    @Override
    public void saveAllData(TradingShippingdetails tradingShippingdetails,ShippingDetails shippingDetails,String noLocations) throws Exception {
        ConvertPOJOUtil.convert(tradingShippingdetails,shippingDetails);
        this.saveShippingDetails(tradingShippingdetails);
        //保存国网运输详情
        this.iTradingShippingServiceOptions.deleteByParentId(tradingShippingdetails.getId());
        List<ShippingServiceOptions> lisso = shippingDetails.getShippingServiceOptions();
        for(int i = 0;i<lisso.size();i++){
            ShippingServiceOptions sso = lisso.get(i);
            sso.setShippingServicePriority((i+1));
            TradingShippingserviceoptions tsp = this.iTradingShippingServiceOptions.toDAOPojo(sso);
            tsp.setParentUuid(tradingShippingdetails.getUuid());
            tsp.setParentId(tradingShippingdetails.getId());
            this.iTradingShippingServiceOptions.saveShippingServiceOptions(tsp);
        }
        //保存国际运输详情,包括运送到的地方
        iTradingInternationalShippingServiceOption.deleteByParentId(tradingShippingdetails.getId());
        if(shippingDetails.getInternationalShippingServiceOption()!=null){
            List<InternationalShippingServiceOption>  liinter = shippingDetails.getInternationalShippingServiceOption();
            for(int i = 0;i<liinter.size();i++){
                InternationalShippingServiceOption isso = liinter.get(i);
                isso.setShippingServicePriority((i+1));
                TradingInternationalshippingserviceoption tiss = this.iTradingInternationalShippingServiceOption.toDAOPojo(isso);
                tiss.setParentId(tradingShippingdetails.getId());
                tiss.setParentUuid(tradingShippingdetails.getUuid());
                this.iTradingInternationalShippingServiceOption.saveInternationalShippingServiceOption(tiss);
                List<String> toli = isso.getShipToLocation();
                this.iTradingAttrMores.deleteByParentId("ShipToLocation",tiss.getId());
                if(toli!=null&&toli.size()>0){
                    for(String str:toli){
                        TradingAttrMores tam = this.iTradingAttrMores.toDAOPojo("ShipToLocation",str);
                        tam.setParentId(tiss.getId());
                        tam.setParentUuid(tiss.getUuid());
                        this.iTradingAttrMores.saveAttrMores(tam);
                    }
                }
            }
        }
        this.iTradingAttrMores.deleteByParentId("ExcludeShipToLocation",tradingShippingdetails.getId());
        //保存不运输到的地方
        if(!ObjectUtils.isLogicalNull(noLocations)){
            String noLocation[] =noLocations.split(",");
            for(String nostr : noLocation){
                TradingAttrMores tam = this.iTradingAttrMores.toDAOPojo("ExcludeShipToLocation",nostr);
                tam.setParentId(tradingShippingdetails.getId());
                tam.setParentUuid(tradingShippingdetails.getUuid());
                this.iTradingAttrMores.saveAttrMores(tam);
            }
        }
    }

    @Override
    public ShippingDetails toXmlPojo(Long id) throws Exception {
        ShippingDetails sd = new ShippingDetails();
        TradingShippingdetails tsd = this.selectById(id);
        if(tsd==null)
            return null;
        ConvertPOJOUtil.convert(sd,tsd);
       // ConvertPOJOUtil.convert(sd.getCalculatedShippingRate(),tsd);
        List<TradingAttrMores> litam = this.iTradingAttrMores.selectByParnetid(tsd.getId(),"ExcludeShipToLocation");
        List<String> listr = new ArrayList();
        for(TradingAttrMores tam:litam){
            listr.add(tam.getValue());
        }
        if(listr.size()>0){
            sd.setExcludeShipToLocation(listr);
        }
        //ConvertPOJOUtil.convert(sd.getInsuranceDetails(),tsd);
        sd.setInternationalShippingServiceOption(this.iTradingInternationalShippingServiceOption.toXmlPojo(tsd.getId()));
        sd.setShippingServiceOptions(this.iTradingShippingServiceOptions.toXmlPojo(tsd.getId()));
        //System.out.println(PojoXmlUtil.pojoToXml(sd));
        return sd;
    }

}
