package com.item.controller;

import com.base.database.publicd.model.PublicUserConfig;
import com.base.database.trading.model.*;
import com.base.domains.CommonParmVO;
import com.base.domains.SessionVO;
import com.base.domains.querypojos.ItemQuery;
import com.base.domains.querypojos.PaypalQuery;
import com.base.domains.querypojos.VariationQuery;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.mybatis.page.Page;
import com.base.mybatis.page.PageJsonBean;
import com.base.sampleapixml.APINameStatic;
import com.base.sampleapixml.BindAccountAPI;
import com.base.userinfo.service.UserInfoService;
import com.base.utils.annotations.AvoidDuplicateSubmission;
import com.base.utils.cache.DataDictionarySupport;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.common.ConvertPOJOUtil;
import com.base.utils.common.MyCollectionsUtil;
import com.base.utils.common.ObjectUtils;
import com.base.utils.exception.Asserts;
import com.base.utils.threadpool.AddApiTask;
import com.base.utils.threadpool.ApiCallable;
import com.base.utils.threadpool.TaskPool;
import com.base.utils.xmlutils.PojoXmlUtil;
import com.base.utils.xmlutils.SamplePaseXml;
import com.base.xmlpojo.trading.addproduct.*;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
import com.trading.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.util.concurrent.ListenableFutureTask;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrtor on 2014/8/2.
 */
@Controller
public class ItemController extends BaseAction{

    @Autowired
    private ITradingItem iTradingItem;
    @Autowired
    private ITradingPayPal iTradingPayPal;
    @Autowired
    private ITradingShippingDetails iTradingShippingDetails;
    @Autowired
    private ITradingDiscountPriceInfo iTradingDiscountPriceInfo;
    @Autowired
    private ITradingItemAddress iTradingItemAddress;
    @Autowired
    private ITradingReturnpolicy iTradingReturnpolicy;
    @Autowired
    private ITradingBuyerRequirementDetails iTradingBuyerRequirementDetails;
    @Autowired
    private ITradingAttrMores iTradingAttrMores;
    @Autowired
    private ITradingPictureDetails iTradingPictureDetails;
    @Value("${EBAY.API.URL}")
    private String apiUrl;
    @Autowired
    private ITradingPublicLevelAttr iTradingPublicLevelAttr;
    @Autowired
    private ITradingVariation iTradingVariation;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private ITradingVariations iTradingVariations;

    @Autowired
    private ITradingPictures iTradingPictures;

    /**
     * 商品展示列表
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping("/itemList.do")
    public ModelAndView itemList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        return forword("item/itemList",modelMap);
    }

    @RequestMapping("/ajax/loadItemList.do")
    @ResponseBody
    public void loadItemList(ModelMap modelMap,CommonParmVO commonParmVO){
        Map m = new HashMap();
        SessionVO c= SessionCacheSupport.getSessionVO();
        m.put("userid",c.getId());
        /**分页组装*/
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        Page page=jsonBean.toPage();
        List<ItemQuery> itemli = this.iTradingItem.selectByItemList(m,page);
        jsonBean.setList(itemli);
        jsonBean.setTotal((int)page.getTotalCount());
        AjaxSupport.sendSuccessText("",jsonBean);
    }

    @RequestMapping("/addItem.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView addItem(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        List<TradingDataDictionary> lidata = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.DATA_DICT_SITE);
        modelMap.put("siteList",lidata);

        SessionVO c= SessionCacheSupport.getSessionVO();
        List<PublicUserConfig> ebayList = DataDictionarySupport.getPublicUserConfigByType(DataDictionarySupport.PUBLIC_DATA_DICT_PAYPAL, c.getId());
        modelMap.put("ebayList",ebayList);

        return forword("item/addItem",modelMap);
    }

    @RequestMapping("/editItem.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView editItem(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws Exception {
        String id = request.getParameter("id");
        List<TradingDataDictionary> lidata = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.DATA_DICT_SITE);
        modelMap.put("siteList",lidata);

        SessionVO c= SessionCacheSupport.getSessionVO();
        List<PublicUserConfig> ebayList = DataDictionarySupport.getPublicUserConfigByType(DataDictionarySupport.PUBLIC_DATA_DICT_PAYPAL, c.getId());
        modelMap.put("ebayList",ebayList);
        TradingItem ti = null;
        if(id!=null&&!"".equals(id)){
            ti = this.iTradingItem.selectById(Long.parseLong(id));
        }
        modelMap.put("item",ti);

        List<TradingPicturedetails> litp = this.iTradingPictureDetails.selectByParentId(Long.parseLong(id));
        for(TradingPicturedetails tp : litp){
            List<TradingAttrMores> lipic = this.iTradingAttrMores.selectByParnetid(tp.getId(),"PictureURL");
            if(lipic!=null&&lipic.size()>0){
                modelMap.put("lipic",lipic);
            }
        }

        List<TradingPublicLevelAttr> lilevle = this.iTradingPublicLevelAttr.selectByParentId("ItemSpecifics",Long.parseLong(id));
        for(TradingPublicLevelAttr tpla :lilevle){
            List<TradingPublicLevelAttr> lipa = this.iTradingPublicLevelAttr.selectByParentId(null,tpla.getId());
            modelMap.put("lipa",lipa);
        }


        TradingVariations tvs = this.iTradingVariations.selectByParentId(ti.getId());
        if(tvs!=null){
            Map m = new HashMap();
            m.put("userid",c.getId());
            m.put("parentid",tvs.getId());
            List<VariationQuery> liv = this.iTradingVariation.selectByParentId(m);
            if(liv!=null&&liv.size()>0){
                for(VariationQuery iv : liv){
                    List<TradingPublicLevelAttr> litpa= this.iTradingPublicLevelAttr.selectByParentId("VariationSpecifics",iv.getId());
                    for(TradingPublicLevelAttr tap : litpa){
                        iv.setTradingPublicLevelAttr(this.iTradingPublicLevelAttr.selectByParentId(null,tap.getId()));
                    }
                }
                modelMap.put("liv",liv);
            }
            TradingPublicLevelAttr tpla = this.iTradingPublicLevelAttr.selectByParentId("VariationSpecificsSet",tvs.getId()).get(0);
            List<TradingPublicLevelAttr> litpa= this.iTradingPublicLevelAttr.selectByParentId("NameValueList",tpla.getId());
            List li = new ArrayList();
            for(TradingPublicLevelAttr tp :litpa){
                li.add(this.iTradingAttrMores.selectByParnetid(tp.getId(),"Name").get(0));
            }
            modelMap.put("clso",li);

            TradingPictures tpes = this.iTradingPictures.selectParnetId(tvs.getId());

            List<TradingPublicLevelAttr> livsps = this.iTradingPublicLevelAttr.selectByParentId("VariationSpecificPictureSet",tpes.getId());
            List lipics = new ArrayList();
            for(int i = 0;i < livsps.size() ;i++){
                Map ms = new HashMap();
                TradingPublicLevelAttr tpa = livsps.get(i);
                List<TradingPublicLevelAttr> livspsss = this.iTradingPublicLevelAttr.selectByParentId("VariationSpecificValue",tpa.getId());
                List<TradingAttrMores> litam = this.iTradingAttrMores.selectByParnetid(tpa.getId(),"PictureURL");
                ms.put("litam",litam);
                ms.put("tamname",livspsss.get(0).getValue());
                lipics.add(ms);
            }
            if(lipics.size()>0){
                modelMap.put("lipics",lipics);
            }

        }
        List<TradingPicturedetails> lipd = this.iTradingPictureDetails.selectByParentId(Long.parseLong(id));
        for(TradingPicturedetails pd : lipd){
            List<TradingAttrMores> litam = this.iTradingAttrMores.selectByParnetid(pd.getId(),"PictureURL");
            modelMap.put("litam",litam);
        }





        return forword("item/addItem",modelMap);
    }

    /**
     * 保存运输选项数据
     * @param request
     * @throws Exception
     */
    @RequestMapping("/saveItem.do")
    @AvoidDuplicateSubmission(needRemoveToken = true)
    @ResponseBody
    public void saveItem(HttpServletRequest request,Item item,TradingItem tradingItem) throws Exception {

        String mouth = request.getParameter("dataMouth");
        if("save".equals(mouth)){
            //保存商品信息到数据库中
            this.iTradingItem.saveItem(item,tradingItem);
            AjaxSupport.sendSuccessText("message", "操作成功！");
        }else{
            //保存商品信息到数据库中
            this.iTradingItem.saveItem(item,tradingItem);

            TradingPaypal tpay = this.iTradingPayPal.selectById(tradingItem.getPayId());
            TradingItemAddress tadd = this.iTradingItemAddress.selectById(tradingItem.getItemLocationId());
            TradingShippingdetails tshipping = this.iTradingShippingDetails.selectById(tradingItem.getShippingDeailsId());
            TradingDiscountpriceinfo tdiscount = this.iTradingDiscountPriceInfo.selectById(tradingItem.getDiscountpriceinfoId());
            TradingBuyerRequirementDetails tbuyer = this.iTradingBuyerRequirementDetails.selectById(tradingItem.getBuyerId());
            TradingReturnpolicy treturn = this.iTradingReturnpolicy.selectById(tradingItem.getReturnpolicyId());

            //组装刑登xml
            //组装买家要求
            BuyerRequirementDetails brd = this.iTradingBuyerRequirementDetails.toXmlPojo(tradingItem.getBuyerId());
            item.setBuyerRequirementDetails(brd);
            //组装退货政策
            ReturnPolicy rp = this.iTradingReturnpolicy.toXmlPojo(tradingItem.getReturnpolicyId());
            item.setReturnPolicy(rp);

            ShippingDetails sd = this.iTradingShippingDetails.toXmlPojo(tradingItem.getShippingDeailsId());
            item.setShippingDetails(sd);

            item.getReturnPolicy().setReturnsAcceptedOption(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(item.getReturnPolicy().getReturnsAcceptedOption())).getValue());
            item.getReturnPolicy().setReturnsWithinOption(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(item.getReturnPolicy().getReturnsWithinOption())).getValue());
            item.getReturnPolicy().setRefundOption(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(item.getReturnPolicy().getRefundOption())).getValue());
            item.getReturnPolicy().setShippingCostPaidByOption(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(item.getReturnPolicy().getShippingCostPaidByOption())).getValue());


            if(request.getParameter("SecondaryCategory.CategoryID")==null||"".equals(request.getParameter("SecondaryCategory.CategoryID"))){
                item.setSecondaryCategory(null);
            }
            TradingItemAddress tia = this.iTradingItemAddress.selectById(tradingItem.getItemLocationId());
            item.setCountry(DataDictionarySupport.getTradingDataDictionaryByID(tia.getCountryId()).getValue());
            item.setCurrency(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(item.getSite())).getValue1());
            item.setSite(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(item.getSite())).getValue());
            item.setPostalCode(tia.getPostalcode());

            TradingPaypal tp = this.iTradingPayPal.selectById(tradingItem.getPayId());
            item.setPayPalEmailAddress(DataDictionarySupport.getPublicUserConfigByID(Long.parseLong(tp.getPaypal())).getConfigValue());
            List<String> limo = new ArrayList();
            limo.add("PayPal");
            item.setPaymentMethods(limo);
            item.setListingDuration("GTC");
            item.setDispatchTimeMax(0);
            if(item.getVariations()!=null) {
                List<Variation> livt = item.getVariations().getVariation();
                for(int i = 0 ; i<livt.size();i++){
                    Variation vtion = livt.get(i);
                    List<VariationSpecifics> livar = new ArrayList();
                    VariationSpecifics vsf = new VariationSpecifics();
                    List<NameValueList> linvls = item.getVariations().getVariationSpecificsSet().getNameValueList();
                    if(linvls!=null&&linvls.size()>0){
                        List<NameValueList> linameList = new ArrayList();
                        for(NameValueList vs : linvls){
                            NameValueList nvl = new NameValueList();
                            nvl.setName(vs.getName());
                            List li = new ArrayList();
                            li.add(vs.getValue().get(i));
                            nvl.setValue(li);
                            linameList.add(nvl);
                        }
                        vsf.setNameValueList(linameList);
                    }
                    livar.add(vsf);
                    vtion.setVariationSpecifics(livar);
                }
                item.getVariations().setVariation(livt);
                item.getVariations().getPictures().setVariationSpecificName(item.getVariations().getVariationSpecificsSet().getNameValueList().get(0).getName());
                if(item.getStartPrice().getValue()==0){
                    item.setStartPrice(null);
                }

                List<NameValueList>  linvl= item.getVariations().getVariationSpecificsSet().getNameValueList();
                for(NameValueList nvlst : linvl){
                    List<String> listr = nvlst.getValue();
                    listr = MyCollectionsUtil.listUnique(listr);
                    nvlst.setValue(listr);
                }
                item.getVariations().getVariationSpecificsSet().setNameValueList(linvl);
            }

            item.setListingType(item.getListingType().equals("2")?"FixedPriceItem":item.getListingType());


            AddFixedPriceItemRequest addItem = new AddFixedPriceItemRequest();
            addItem.setXmlns("urn:ebay:apis:eBLBaseComponents");
            addItem.setErrorLanguage("en_US");
            addItem.setWarningLevel("High");
            RequesterCredentials rc = new RequesterCredentials();
            rc.seteBayAuthToken("AgAAAA**AQAAAA**aAAAAA**wV1JUQ**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6wFk4GhCpGGoA+dj6x9nY+seQ**cx0CAA**AAMAAA**2kuzIn+bBej1QDsDFfI2N74mj8psZYNYrtgX97fzWSGXO7EjvdlE9leu9HCY1bR9wdrzlAE7AKcT9Oz5BDNZbNQLS+uoifmNUM47lSqxWeYTQS2GtMK25LPYhxY+OQp6UVZ8lUh6Oqr91ub03emzufuZHo+6KSNJfNXMtOBVaB7PDeBQyNWoFBO0/LYiS5ql6HXB7vCj0W+K/iT4t3aPs5KlXAXjewM/Sa+nUDtjT9SseqrKrxdZx5fkAePeSrBs229tdCrkTtE0n+ZE9ppwJjElZpu7yfQL44McNa16KBxYYO0PnX7ENg2yMxf3H4aji0BEfB41lrC1LwhmNSebJGrJXRQVS9jmZyDqYiBdn1t536va/LPTP8kc3GZ7hnZRJuhMxoGGgx4ev5Hip0L7dk6cAPKHIkHUIjfA5pwVHEJZpvea+7uvwAh5pj9U7r6rmB9FXH2G9l+F5SytYlIXsDjwNtrEN53k5HrM0vhnGdd7pUwvyu7Nu4U5aPkZQZjTr6OrTWioDsZZwEz+pf0scw0IYweMhicCqMTNbvkJsj2cikX49C6XSAcoUyrGtGa11vFChrifmq74dPZmUEtT1hDtwL1Ix3VPyZcJtTukKljxa0W0IwIe676X5HmiGhvk5qPPUImkXcZdQUK1gMdZmw0seMl5xmFG33kKVSD9H0p0JAEF4lOcDvjADQZtwLXY3qIhvYcKdOrIffrUAURnJRYnrB/MixizWvw252xBn9tmxpm68O3KsGBzcUwEB0Su");
            addItem.setRequesterCredentials(rc);
            addItem.setItem(item);

            String xml= PojoXmlUtil.pojoToXml(addItem);
            xml="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+xml;
            System.out.println(xml);


            //Asserts.assertTrue(false, "错误");
            UsercontrollerDevAccountExtend d = userInfoService.getDevInfo(1L);
            d.setApiSiteid(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(tradingItem.getSite())).getName1());
            d.setApiCallName(APINameStatic.AddFixedPriceItem);
            //String xml= BindAccountAPI.getSessionID(d.getRunname());

            AddApiTask addApiTask = new AddApiTask();
            Map<String, String> resMap= addApiTask.exec(d, xml, apiUrl);
            String r1=resMap.get("stat");
            String res=resMap.get("message");
            if("fail".equalsIgnoreCase(r1)){
                AjaxSupport.sendFailText("fail","数据已保存，但刊登失败！");
                return;
            }
            String ack = SamplePaseXml.getVFromXmlString(res,"Ack");
            if("Success".equalsIgnoreCase(ack)||"Warning".equalsIgnoreCase(ack)){
                String itemId= SamplePaseXml.getVFromXmlString(res, "ItemID");
                tradingItem.setItemId(itemId);
                tradingItem.setIsFlag("Success");
                this.iTradingItem.saveTradingItem(tradingItem);
                AjaxSupport.sendSuccessText("message", "操作成功！");
            }else {
                String errors=SamplePaseXml.getVFromXmlString(res,"Errors");
                //logger.error("获取apisessionid失败!"+errors);
                AjaxSupport.sendFailText("fail","数据已保存，但刊登失败！");
            }
        }

    }
}
