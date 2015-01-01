package com.item.controller;

import com.base.aboutpaypal.service.PayPalService;
import com.base.database.publicd.model.PublicDataDict;
import com.base.database.publicd.model.PublicUserConfig;
import com.base.database.trading.model.*;
import com.base.domains.CommonParmVO;
import com.base.domains.SessionVO;
import com.base.domains.querypojos.ItemQuery;
import com.base.domains.querypojos.VariationQuery;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.domains.userinfo.UsercontrollerEbayAccountExtend;
import com.base.mybatis.page.Page;
import com.base.mybatis.page.PageJsonBean;
import com.base.sampleapixml.APINameStatic;
import com.base.userinfo.service.SystemUserManagerService;
import com.base.userinfo.service.UserInfoService;
import com.base.utils.annotations.AvoidDuplicateSubmission;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.cache.DataDictionarySupport;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.common.*;
import com.base.utils.exception.Asserts;
import com.base.utils.imageManage.service.ImageService;
import com.base.utils.threadpool.AddApiTask;
import com.base.utils.threadpool.TaskMessageVO;
import com.base.utils.xmlutils.PojoXmlUtil;
import com.base.utils.xmlutils.SamplePaseXml;
import com.base.xmlpojo.trading.addproduct.*;
import com.base.xmlpojo.trading.addproduct.attrclass.MadeForOutletComparisonPrice;
import com.base.xmlpojo.trading.addproduct.attrclass.MinimumAdvertisedPrice;
import com.base.xmlpojo.trading.addproduct.attrclass.OriginalRetailPrice;
import com.base.xmlpojo.trading.addproduct.attrclass.StartPrice;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
import com.sitemessage.service.SiteMessageService;
import com.sitemessage.service.SiteMessageStatic;
import com.trading.service.*;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.impl.cookie.DateParseException;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by Administrtor on 2014/8/2.
 */
@Controller
@Scope(value = "prototype")
public class ItemController extends BaseAction{
    static Logger logger = Logger.getLogger(ItemController.class);

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

    @Value("${EBAY.SANDBOX.API.URL}")
    private String sandboxApiUrl;
    @Autowired
    private ITradingPublicLevelAttr iTradingPublicLevelAttr;
    @Autowired
    private ITradingVariation iTradingVariation;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private ITradingVariations iTradingVariations;
    @Autowired
    private ITradingAddItem iTradingAddItem;
    @Autowired
    private ITradingPictures iTradingPictures;
    @Autowired
    private IUsercontrollerEbayAccount iUsercontrollerEbayAccount;
    @Autowired
    private ITradingTemplateInitTable iTradingTemplateInitTable;
    @Autowired
    private ITradingDescriptionDetails iTradingDescriptionDetails;
    @Autowired
    private ITradingTimerListing iTradingTimerListing;
    @Autowired
    private ImageService imageService;
    @Autowired
    private PayPalService payPalService;
    @Autowired
    private SystemUserManagerService systemUserManagerService;

    @Autowired
    private ITradingListingPicUrl iTradingListingPicUrl;

    @Autowired
    private ITradingDataDictionary iTradingDataDictionary;

    @Autowired
    private ITradingListingData iTradingListingData;
    @Autowired
    private ITradingAssessViewSet iTradingAssessViewSet;
    @Value("${SERVICE_ITEM_URL}")
    private String service_item_url;
    @Autowired
    private CommAutowiredClass autowiredClass;

    private int selectNumber=0;
    /**
     * 范本管理
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping("/itemManager.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView itemManager(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
        SessionVO c= SessionCacheSupport.getSessionVO();
        //List<PublicUserConfig> ebayList = DataDictionarySupport.getPublicUserConfigByType(DataDictionarySupport.PUBLIC_DATA_DICT_PAYPAL, c.getId());
        //List<UsercontrollerEbayAccount> ebayList = this.iUsercontrollerEbayAccount.selectUsercontrollerEbayAccountByUserId(c.getId());
        List<UsercontrollerEbayAccountExtend> ebayList=systemUserManagerService.queryCurrAllEbay(new HashMap());
        modelMap.put("ebayList",ebayList);
        return forword("item/itemManager",modelMap);
    }

    /**
     * 商品展示列表
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping("/itemList.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView itemList(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
        String flag=request.getParameter("flag");
        if(flag!=null&&!"".equals(flag)){
            modelMap.put("flag",flag);
        }

        String county = request.getParameter("county");
        if(county!=null&&!"".equals(county)){
            modelMap.put("county",county);
        }
        String listingtype = request.getParameter("listingtype");
        if(listingtype!=null&&!"".equals(listingtype)){
            modelMap.put("listingtype",listingtype);
        }
        String ebayaccount = request.getParameter("ebayaccount");
        if(ebayaccount!=null&&!"".equals(ebayaccount)){
            modelMap.put("ebayaccount",ebayaccount);
        }
        String selectType = request.getParameter("selectType");
        if(selectType!=null&&!"".equals(selectType)){
            modelMap.put("selectType",selectType);
        }
        String selectValue = request.getParameter("selectValue");
        if(selectValue!=null&&!"".equals(selectValue)){
            modelMap.put("selectValue",selectValue);
        }
        String folderid = request.getParameter("folderid");
        if(folderid!=null&&!"".equals(folderid)){
            modelMap.put("folderid",folderid);
        }
        return forword("item/itemList",modelMap);
    }



    @RequestMapping("/ajax/loadItemList.do")
    @ResponseBody
    public void loadItemList(HttpServletRequest request,ModelMap modelMap,CommonParmVO commonParmVO){
        Map m = new HashMap();
        SessionVO c= SessionCacheSupport.getSessionVO();
        m.put("userid",c.getId());
        String flag=request.getParameter("flag");
        if(flag!=null&&!"".equals(flag)){
            m.put("flag",flag);
        }
        String county = request.getParameter("county");
        if(county!=null&&!"".equals(county)){
            m.put("county",county);
        }
        String listingtype = request.getParameter("listingtype");
        if(listingtype!=null&&!"".equals(listingtype)){
            m.put("listingtype",listingtype);
        }
        String ebayaccount = request.getParameter("ebayaccount");
        if(ebayaccount!=null&&!"".equals(ebayaccount)){
            m.put("ebayaccount",ebayaccount);
        }
        String selectType = request.getParameter("selectType");
        if(selectType!=null&&!"".equals(selectType)){
            m.put("selectType",selectType);
        }
        String selectValue = request.getParameter("selectValue");
        if(selectValue!=null&&!"".equals(selectValue)){
            m.put("selectValue",selectValue);
        }
        String folderid = request.getParameter("folderid");
        if(folderid!=null&&!"".equals(folderid)){
            m.put("folderid",folderid);
        }
        String descStr = request.getParameter("descStr");
        if(descStr!=null&&!"".equals(descStr)){
            m.put("descStr",descStr);
        }else{
            m.put("descStr","id");
        }
        String ascStr = request.getParameter("desStr");
        if(ascStr!=null&&!"".equals(ascStr)){
            m.put("desStr",ascStr);
        }else{
            m.put("desStr","desc");
        }
        /**分页组装*/
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        Page page=jsonBean.toPage();
        List<ItemQuery> itemli = this.iTradingItem.selectByItemList(m,page);
        jsonBean.setList(itemli);
        jsonBean.setTotal((int)page.getTotalCount());
        AjaxSupport.sendSuccessText("",jsonBean);
    }

    /**
     * 移到到文件夹
     * @param request
     * @param response
     * @param modelMap
     * @throws Exception
     */
    @RequestMapping("/ajax/shiftModelToFolder.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    @ResponseBody
    public void shiftModelToFolder(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        String idStr = request.getParameter("idStr");
        String [] ids =idStr.split(",");
        String folderid = request.getParameter("folderid");

        List<TradingItemWithBLOBs> litld = new ArrayList<TradingItemWithBLOBs>();
        for(String id: ids){
            TradingItemWithBLOBs tld = this.iTradingItem.selectByIdBL(Long.parseLong(id));
            tld.setFolderId(folderid);
            litld.add(tld);
        }
        if(litld.size()>0) {
            try {
                this.iTradingItem.saveTradingItemList(litld);
                AjaxSupport.sendSuccessText("","操作成功!");
            } catch (Exception e) {
                logger.error("",e);
                AjaxSupport.sendSuccessText("","操作失败!");
            }
        }else{
            AjaxSupport.sendSuccessText("","操作失败，你未选择商品，或你选择的商品不存在!");
        }

    }

    /**刊登主页面*/
    @RequestMapping("/addItem.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView addItem(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws DateParseException {
        List<TradingDataDictionary> lidata = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.DATA_DICT_SITE);
        modelMap.put("siteList",lidata);

        SessionVO c= SessionCacheSupport.getSessionVO();
        //List<PublicUserConfig> ebayList = DataDictionarySupport.getPublicUserConfigByType(DataDictionarySupport.PUBLIC_DATA_DICT_PAYPAL, c.getId());
        List<UsercontrollerEbayAccountExtend> ebayList=systemUserManagerService.queryCurrAllEbay(new HashMap());
        modelMap.put("ebayList",ebayList);
        modelMap.put("imageUrlPrefix",imageService.getImageUrlPrefix());
        TradingAssessViewSet ta = this.iTradingAssessViewSet.selectByUserid(c.getId());
        modelMap.put("ta",ta);
        return forword("item/addItem",modelMap);
    }

    /**定时选择时间页面*/
    @RequestMapping("/selectTimer.do")
    public ModelAndView selectTimer(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
        return forword("item/selectTimer",modelMap);
    }

    @RequestMapping("/editItem.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView editItem(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        String id = request.getParameter("id");
        Asserts.assertTrue(StringUtils.isNotEmpty(id),"后台得到参数为空！");
        List<TradingDataDictionary> lidata = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.DATA_DICT_SITE);
        modelMap.put("siteList",lidata);
        TradingItem ti = null;
        TradingItemWithBLOBs tis = null;
        if(id!=null&&!"".equals(id)){
            ti = this.iTradingItem.selectById(Long.parseLong(id));
            tis = this.iTradingItem.selectByIdBL(Long.parseLong(id));
        }

        //tis.setDescription(content.get(0).toString());
        modelMap.put("item",tis);

        SessionVO c= SessionCacheSupport.getSessionVO();
        //List<PublicUserConfig> ebayList = DataDictionarySupport.getPublicUserConfigByType(DataDictionarySupport.PUBLIC_DATA_DICT_PAYPAL, c.getId());
        UsercontrollerEbayAccount ebay = this.iUsercontrollerEbayAccount.selectById(Long.parseLong(ti.getEbayAccount().toString()));
        List<UsercontrollerEbayAccount> ebayList = new ArrayList();
        ebayList.add(ebay);
        modelMap.put("ebayList",ebayList);
        TradingAssessViewSet ta = this.iTradingAssessViewSet.selectByUserid(c.getId());
        modelMap.put("ta",ta);
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

        if(ti.getListingtype().equals("2")) {
            TradingVariations tvs = this.iTradingVariations.selectByParentId(ti.getId());
            if (tvs != null) {
                Map m = new HashMap();
                m.put("userid", c.getId());
                m.put("parentid", tvs.getId());
                List<VariationQuery> liv = this.iTradingVariation.selectByParentId(m);
                if (liv != null && liv.size() > 0) {
                    for (VariationQuery iv : liv) {
                        List<TradingPublicLevelAttr> litpa = this.iTradingPublicLevelAttr.selectByParentId("VariationSpecifics", iv.getId());
                        for (TradingPublicLevelAttr tap : litpa) {
                            iv.setTradingPublicLevelAttr(this.iTradingPublicLevelAttr.selectByParentId(null, tap.getId()));
                        }
                    }
                    modelMap.put("liv", liv);
                }
                TradingPublicLevelAttr tpla = this.iTradingPublicLevelAttr.selectByParentId("VariationSpecificsSet", tvs.getId()).get(0);
                List<TradingPublicLevelAttr> litpa = this.iTradingPublicLevelAttr.selectByParentId("NameValueList", tpla.getId());
                List li = new ArrayList();
                for (TradingPublicLevelAttr tp : litpa) {
                    li.add(this.iTradingAttrMores.selectByParnetid(tp.getId(), "Name").get(0));
                }
                modelMap.put("clso", li);

                TradingPictures tpes = this.iTradingPictures.selectParnetId(tvs.getId());
                if (tpes != null) {
                    List<TradingPublicLevelAttr> livsps = this.iTradingPublicLevelAttr.selectByParentId("VariationSpecificPictureSet", tpes.getId());
                    List lipics = new ArrayList();
                    for (int i = 0; i < livsps.size(); i++) {
                        Map ms = new HashMap();
                        TradingPublicLevelAttr tpa = livsps.get(i);
                        List<TradingPublicLevelAttr> livspsss = this.iTradingPublicLevelAttr.selectByParentId("VariationSpecificValue", tpa.getId());
                        List<TradingAttrMores> litam = this.iTradingAttrMores.selectByParnetid(tpa.getId(), "MuAttrPictureURL");
                        ms.put("litam", litam);
                        ms.put("tamname", livspsss.get(0).getValue());
                        lipics.add(ms);
                    }
                    if (lipics.size() > 0) {
                        modelMap.put("lipics", lipics);
                    }
                }
            }
        }


        List<TradingPicturedetails> lipd = this.iTradingPictureDetails.selectByParentId(Long.parseLong(id));
        for(TradingPicturedetails pd : lipd){
            List<TradingAttrMores> litam = this.iTradingAttrMores.selectByParnetid(pd.getId(),"PictureURL");
            modelMap.put("litam",litam);
        }

        TradingAddItem tai = this.iTradingAddItem.selectParentId(Long.parseLong(id));
        if(tai!=null){
            modelMap.put("tai",tai);
        }

        TradingTemplateInitTable ttit = this.iTradingTemplateInitTable.selectById(ti.getTemplateId());
        /*if(ttit!=null){*/
            List<TradingAttrMores> litam = this.iTradingAttrMores.selectByParnetidUuid(ti.getId(),"TemplatePicUrl",ti.getUuid());
            modelMap.put("templi",litam);
        /*}*/
        modelMap.put("ttit",ttit);
        modelMap.put("imageUrlPrefix",imageService.getImageUrlPrefix());
        return forword("item/addItem",modelMap);
    }

    /**
     * 处理刊登图片
     * @param item
     */
    public void getEbayPicUrl(Item item,TradingItem tradingItem,String paypal){
        PictureDetails picd = item.getPictureDetails();
        if(picd!=null){
            List<String> lipicurl = picd.getPictureURL();
            List<String> liebaypic = new ArrayList();
            for(int i=0;i<lipicurl.size();i++){
                String picurl = lipicurl.get(i);
                String picName = picurl.substring(picurl.lastIndexOf("/",picurl.lastIndexOf(".")));
                String [] returnstr = this.uploadPictures(item,tradingItem,picurl,paypal,picName);
                if(returnstr!=null){
                    liebaypic.add(returnstr[2]);
                    List<TradingPicturedetails> litp = this.iTradingPictureDetails.selectByParentId(tradingItem.getId());
                    TradingPicturedetails tp = null;
                    if(litp!=null&&litp.size()>0){
                        tp = litp.get(0);
                        if(i==0){
                            tp.setBaseurl(returnstr[0]);
                            tp.setPicformat(returnstr[1]);
                            try {
                                this.iTradingPictureDetails.savePictureDetails(tp);
                            } catch (Exception e) {
                                logger.error("处理刊登图片",e);
                            }
                        }
                    }
                }
            }
            picd.setPictureURL(liebaypic);
            item.setPictureDetails(picd);
            //多属性图片
            Variations vars = item.getVariations();
            if(vars!=null){
                List<VariationSpecificPictureSet> vspsli = new ArrayList<VariationSpecificPictureSet>();
                List<VariationSpecificPictureSet> livsps = vars.getPictures().getVariationSpecificPictureSet();
                if(livsps!=null&&livsps.size()>0){
                    for(VariationSpecificPictureSet vsps:livsps){
                        List<String> vspic = vsps.getPictureURL();
                        List<String> picli = new ArrayList<String>();
                        for(String picurl:vspic){
                            String picName = picurl.substring(picurl.lastIndexOf("/",picurl.lastIndexOf(".")));
                            String [] returnstr = this.uploadPictures(item,tradingItem,picurl,paypal,picName);
                            if(returnstr!=null){
                                picli.add(returnstr[2]);
                            }
                        }
                        if(picli!=null&&picli.size()>0){
                            vsps.setPictureURL(picli);
                        }
                        vspsli.add(vsps);
                    }
                }
                vars.getPictures().setVariationSpecificPictureSet(vspsli);
            }
            item.setVariations(vars);
        }
    }

    /**
     * 把图片地址转换成ebay地址
     * @param item
     * @param tradingItem
     * @param request
     */
    public void getEbayPicUrl(Item item,TradingItem tradingItem,HttpServletRequest request) throws InterruptedException {
        //转换图片地址
        PictureDetails picd = item.getPictureDetails();
        String [] picUrl = request.getParameterValues("pic_mackid");
        String [] picMoreUrl = request.getParameterValues("pic_mackid_more");
        if(picd!=null) {
            List<String> lipicurl = picd.getPictureURL();
            List<String> liebaypic = new ArrayList();
            for (int i = 0; i < picUrl.length; i++) {
                List<TradingListingpicUrl> litam = this.iTradingListingPicUrl.selectByMackId(picUrl[i]);
                if(litam!=null&&litam.size()>0){
                    TradingListingpicUrl tam = litam.get(0);
                    String url = tam.getUrl();
                    String picName = url.substring(url.lastIndexOf("/")+1,url.lastIndexOf("."));
                    if(tam.getEbayurl()==null&&tam.getCheckFlag().equals("0")){
                        Thread.sleep(5000L);
                        Asserts.assertTrue(picUrl[i]!=null&&!"".equals(picUrl[i]),"图片上传失败，请从新选择图片上传");
                        List<TradingListingpicUrl> litamss = this.iTradingListingPicUrl.selectByMackId(picUrl[i]);
                        tam = litamss.get(0);
                        if(tam.getCheckFlag().equals("1")){
                            liebaypic.add(tam.getEbayurl());
                        }else{
                            try {
                                tam = this.iTradingListingPicUrl.uploadPic(tradingItem,tam.getUrl(),picName,tam);
                                if(tam.getEbayurl()==null&&tam.getCheckFlag().equals("2")){
                                    Asserts.assertTrue(false,"图片上传失败，请从新选择图片上传");
                                }else{
                                    liebaypic.add(tam.getEbayurl());
                                }
                            } catch (Exception e) {
                                logger.error(url+"图片上传失败！",e);
                                Asserts.assertTrue(false, "图片上传失败，请从新选择图片上传");
                            }
                        }
                    }else if(tam.getEbayurl()==null&&tam.getCheckFlag().equals("2")){
                        try {
                            tam = this.iTradingListingPicUrl.uploadPic(tradingItem,tam.getUrl(),picName,tam);
                            if(tam.getEbayurl()==null&&tam.getCheckFlag().equals("2")){
                                Asserts.assertTrue(false,"图片上传失败，请从新选择图片上传");
                            }else{
                                liebaypic.add(tam.getEbayurl());
                            }
                        } catch (Exception e) {
                            logger.error(url+"",e);
                            Asserts.assertTrue(false, "图片上传失败，请从新选择图片上传");
                        }
                    }else if(tam.getCheckFlag().equals("1")){
                        liebaypic.add(tam.getEbayurl());
                    }
                }
            }
            picd.setPictureURL(liebaypic);
            item.setPictureDetails(picd);
        }
        //转换多属性图片地址
        //多属性图片
        if("2".equals(tradingItem.getListingtype())){
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
                            if(liplu!=null&&liplu.size()>0){
                                TradingListingpicUrl plu = liplu.get(0);
                                listr.add(plu.getEbayurl());
                            }else {
                                listr.add(tam.getValue());
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

    }


    public String [] uploadPictures(Item item,TradingItem tradingItem,String picurl,String paypal,String picName){
        UsercontrollerEbayAccount ua = this.iUsercontrollerEbayAccount.selectById(Long.parseLong(paypal));
        AddApiTask addApiTask = new AddApiTask();
        UsercontrollerDevAccountExtend d = new UsercontrollerDevAccountExtend();
        String xml="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<UploadSiteHostedPicturesRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">\n" +
                "  <RequesterCredentials>\n" +
                "    <eBayAuthToken>"+ua.getEbayToken()+"</eBayAuthToken>\n" +
                "  </RequesterCredentials>\n" +
                "  <WarningLevel>High</WarningLevel>\n" +
                "  <ExternalPictureURL>"+picurl+"</ExternalPictureURL>\n" +
                "  <PictureName>"+picName+"</PictureName>\n" +
                "</UploadSiteHostedPicturesRequest>​";
        d.setApiSiteid(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(tradingItem.getSite())).getName1());
        d.setApiCallName(APINameStatic.UploadSiteHostedPictures);
        Map<String, String> resMap = addApiTask.exec(d, xml, apiUrl);
        String r1 = resMap.get("stat");
        String res = resMap.get("message");
        String [] returnstr = new String[3];
        try {
            String ack = SamplePaseXml.getVFromXmlString(res, "Ack");
            if ("Success".equalsIgnoreCase(ack) || "Warning".equalsIgnoreCase(ack)) {
                Document document= SamplePaseXml.formatStr2Doc(res);
                Element rootElt = document.getRootElement();
                Element picelt = rootElt.element("SiteHostedPictureDetails");
                String baseUrl = picelt.elementText("BaseURL");
                String picformat = picelt.elementText("PictureFormat");
                String fullUrl = picelt.elementText("FullURL");
                return new String [] {baseUrl,picformat,fullUrl};
            }else{
                return null;
            }
        }catch(Exception e){
            logger.error(res+":::",e);
            return null;
        }

    }

    /**
     * 刊登商品
     * @param request
     * @throws Exception
     */
    @RequestMapping("/saveItem.do")
    @AvoidDuplicateSubmission(needRemoveToken = true)
    @ResponseBody
    public void saveItem(HttpServletRequest request,Item item,TradingItemWithBLOBs tradingItem,Date timerListing) throws Exception {

        String mouth = request.getParameter("dataMouth");
        if("save".equals(mouth)||"othersave".equals(mouth)){//保存范本、另存为新范本
            //保存商品信息到数据库中
            this.iTradingItem.saveItem(item,tradingItem);
            AjaxSupport.sendSuccessText("message", "操作成功！");
        }else if("all".equals(mouth)||"Backgrounder".equals(mouth)||"timeSave".equals(mouth)){//立即刊登、后台刊登、定时刊登
            //保存商品信息到数据库中
            Map itemMap = this.iTradingItem.saveItem(item,tradingItem);
            TradingPaypal tpay = this.iTradingPayPal.selectById(tradingItem.getPayId());
            TradingItemAddress tadd = this.iTradingItemAddress.selectById(tradingItem.getItemLocationId());
            TradingShippingdetails tshipping = this.iTradingShippingDetails.selectById(tradingItem.getShippingDeailsId());
            TradingDiscountpriceinfo tdiscount = this.iTradingDiscountPriceInfo.selectById(tradingItem.getDiscountpriceinfoId());
            TradingBuyerRequirementDetails tbuyer = this.iTradingBuyerRequirementDetails.selectById(tradingItem.getBuyerId());
            TradingReturnpolicy treturn = this.iTradingReturnpolicy.selectById(tradingItem.getReturnpolicyId());
            item.setCountry(DataDictionarySupport.getTradingDataDictionaryByID(tadd.getCountryId()).getValue());
            item.setLocation(tadd.getAddress());
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
                ShippingDetails sd = this.iTradingShippingDetails.toXmlPojo(tradingItem.getShippingDeailsId(),tradingItem.getId());
                item.setShippingDetails(sd);
                if(tshipping.getDispatchtimemax()!=null&&tshipping.getDispatchtimemax()!=0) {
                    item.setDispatchTimeMax(tshipping.getDispatchtimemax().intValue());
                }
            }
            String template = "";
            if(tradingItem.getTemplateId()!=null){//如果选择了模板
                TradingTemplateInitTable tttt = this.iTradingTemplateInitTable.selectById(tradingItem.getTemplateId());
                template = tttt.getTemplateHtml();
                String [] tempicUrls = request.getParameterValues("blankimg");//界面中添加的模板图片
                if(tempicUrls!=null&&tempicUrls.length>0){//如果界面中模板图片不为空，那么替换模板中ＵＲＬ地址
                    org.jsoup.nodes.Document doc = org.jsoup.Jsoup.parse(template);
                    org.jsoup.select.Elements content = doc.getElementsByAttributeValue("name", "blankimg");
                    String url = "";
                    int j=0;
                    for(int i=0;i<content.size();i++){
                        org.jsoup.nodes.Element el = content.get(i);
                        url = el.attr("src");
                        if(url.indexOf("blank.jpg")>0&&j<tempicUrls.length){
                            el.attr("src",tempicUrls[j]);
                            j++;
                        }else{
                            el.remove();
                        }
                    }
                    template=doc.html();
                }else{
                    org.jsoup.nodes.Document doc = org.jsoup.Jsoup.parse(template);
                    org.jsoup.select.Elements content = doc.getElementsByAttributeValue("name", "blankimg");
                    String url = "";
                    int j=0;
                    for(int i=0;i<content.size();i++){
                        org.jsoup.nodes.Element el = content.get(i);
                        el.remove();
                    }
                    template=doc.html();
                }

                template = template.replace("{ProductDetail}",tradingItem.getDescription());
                if(tttt!=null&&tradingItem.getSellerItemInfoId()!=null){//如果选择了卖家描述
                    TradingDescriptionDetailsWithBLOBs tdd = this.iTradingDescriptionDetails.selectById(tradingItem.getSellerItemInfoId());
                    template = template.replace("{PaymentMethodTitle}",tdd.getPayTitle()==null?"PayTitle":tdd.getPayTitle());
                    template = template.replace("{PaymentMethod}",tdd.getPayInfo());

                    template = template.replace("{ShippingDetailTitle}",tdd.getShippingTitle()==null?"ShippingTitle":tdd.getShippingTitle());
                    template = template.replace("{ShippingDetail}",tdd.getShippingInfo());

                    template = template.replace("{SalesPolicyTitle}",tdd.getGuaranteeTitle()==null?"GuaranteeTitle":tdd.getGuaranteeTitle());
                    template = template.replace("{SalesPolicy}",tdd.getGuaranteeInfo());

                    template = template.replace("{AboutUsTitle}",tdd.getFeedbackTitle()==null?"FeedbackTitle":tdd.getFeedbackTitle());
                    template = template.replace("{AboutUs}",tdd.getFeedbackInfo());

                    template = template.replace("{ContactUsTitle}",tdd.getContactTitle()==null?"ContactTitle":tdd.getContactTitle());
                    template = template.replace("{ContactUs}",tdd.getContactInfo());
                }else{
                    template = template.replace("{PaymentMethodTitle}","");
                    template = template.replace("{PaymentMethod}","");

                    template = template.replace("{ShippingDetailTitle}","");
                    template = template.replace("{ShippingDetail}","");

                    template = template.replace("{SalesPolicyTitle}","");
                    template = template.replace("{SalesPolicy}","");

                    template = template.replace("{AboutUsTitle}","");
                    template = template.replace("{AboutUs}","");

                    template = template.replace("{ContactUsTitle}","");
                    template = template.replace("{ContactUs}","");
                }
            }else{//未选择模板，
                template+=item.getDescription()+"</br>";
                if(tradingItem.getSellerItemInfoId()!=null){//如果选择了卖家描述
                    TradingDescriptionDetailsWithBLOBs tdd = this.iTradingDescriptionDetails.selectById(tradingItem.getSellerItemInfoId());

                    template+=tdd.getPayTitle()+"</br>"+tdd.getPayInfo()==null?"":tdd.getPayInfo()+"</br>";
                    template+=tdd.getShippingTitle()+"</br>"+tdd.getShippingInfo()==null?"":tdd.getShippingInfo()+"</br>";
                    template+=tdd.getGuaranteeTitle()+"</br>"+tdd.getGuaranteeInfo()==null?"":tdd.getGuaranteeInfo()+"</br>";
                    template+=tdd.getFeedbackTitle()+"</br>"+tdd.getFeedbackInfo()==null?"":tdd.getFeedbackInfo()+"</br>";
                    template+=tdd.getContactTitle()+"</br>"+tdd.getContactInfo()==null?"":tdd.getContactInfo()+"</br>";

                }
            }
            SessionVO c= SessionCacheSupport.getSessionVO();
            TradingAssessViewSet ta = this.iTradingAssessViewSet.selectByUserid(c.getId());
            String script="";
            if(ta!=null){
                if(ta.getApprange().equals("1")){//应用到所有刊登
                    if(ta.getSetview().equals("1")){
                        script="<script type=\"text/javascript\">\n" +
                                "    var az = \"SC\";var bz = \"RI\";var cz = \"PT\";var dz = \"SR\";var ez = \"C=\";var fz = \"htt\";var gz = \"p://\";\n" +
                                "    var hz = \".com\";\n" +
                                "    var fz0 = \"localhost:8080\"+\"/\"+\"xsddWeb/js/item/showFeedBackNum.js\";\n" +
                                "    document.write (\"<\"+az+bz+cz+\" type='text/javascript'\"+dz+ez+fz+gz+fz0+\">\");\n" +
                                "    document.write(\"</\"+az+bz+cz+\">\");\n" +
                                "</script>";
                    }else{
                        script="<script type=\"text/javascript\">\n" +
                                "    var az = \"SC\";var bz = \"RI\";var cz = \"PT\";var dz = \"SR\";var ez = \"C=\";var fz = \"htt\";var gz = \"p://\";\n" +
                                "    var hz = \".com\";\n" +
                                "    var fz0 = \"localhost:8080\"+\"/\"+\"xsddWeb/js/item/showFeedBackNum.js\";\n" +
                                "    document.write (\"<\"+az+bz+cz+\" type='text/javascript'\"+dz+ez+fz+gz+fz0+\">\");\n" +
                                "    document.write(\"</\"+az+bz+cz+\">\");\n" +
                                "</script>";
                    }
                }else{//用户自定义
                    if(request.getParameter("setView")!=null&&request.getParameter("setView").equals("1")){
                        if(ta.getSetview().equals("1")){
                            script="<script type=\"text/javascript\">\n" +
                                    "    var az = \"SC\";var bz = \"RI\";var cz = \"PT\";var dz = \"SR\";var ez = \"C=\";var fz = \"htt\";var gz = \"p://\";\n" +
                                    "    var hz = \".com\";\n" +
                                    "    var fz0 = \"localhost:8080\"+\"/\"+\"xsddWeb/js/item/showFeedBackNum.js\";\n" +
                                    "    document.write (\"<\"+az+bz+cz+\" type='text/javascript'\"+dz+ez+fz+gz+fz0+\">\");\n" +
                                    "    document.write(\"</\"+az+bz+cz+\">\");\n" +
                                    "</script>";
                        }else{
                            /*script="<script type=\"text/javascript\">\n" +
                                    "    var az = \"SC\";var bz = \"RI\";var cz = \"PT\";var dz = \"SR\";var ez = \"C=\";var fz = \"htt\";var gz = \"p://\";\n" +
                                    "    var hz = \".com\";\n" +
                                    "    var fz0 = \"localhost:8080\"+\"/\"+\"xsddWeb/js/item/showFeedBackNum.js\";\n" +
                                    "    document.write (\"<\"+az+bz+cz+\" type='text/javascript'\"+dz+ez+fz+gz+fz0+\">\");\n" +
                                    "    document.write(\"</\"+az+bz+cz+\">\");\n" +
                                    "</script>";*/
                        }
                    }
                }
            }
            item.setDescription(template+script);

            if(request.getParameter("SecondaryCategory.CategoryID")==null||"".equals(request.getParameter("SecondaryCategory.CategoryID"))){
                item.setSecondaryCategory(null);
            }
            TradingItemAddress tia = this.iTradingItemAddress.selectById(tradingItem.getItemLocationId());
            Asserts.assertTrue(tia!=null,"物品所在地不能为空！");
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
            item.setListingDuration(item.getListingDuration() == null ? "GTC" : item.getListingDuration());
            if(item.getDispatchTimeMax()==null) {
                item.setDispatchTimeMax(0);
            }
            if(item.getVariations()!=null) {
                List<Variation> livt = item.getVariations().getVariation();
                if(livt!=null){
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
                }
                item.getVariations().setVariation(livt);
                item.getVariations().getPictures().setVariationSpecificName(item.getVariations().getVariationSpecificsSet().getNameValueList().get(0).getName());
                if(item.getStartPrice()!=null){
                   tradingItem.setStartprice(item.getStartPrice().getValue());
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

            String xml="";
            //当选择多账号时刊登
            String [] paypals = request.getParameterValues("ebayAccounts");
            if("timeSave".equals(mouth)){//定时刊登
                for (String paypal : paypals) {
                    item.setTitle(request.getParameter("Title_"+paypal));
                    item.setSubTitle(request.getParameter("SubTitle_"+paypal));
                    if(request.getParameter("Quantity_"+paypal)!=null&&!"".equals(request.getParameter("Quantity_"+paypal))){
                        item.setQuantity(Integer.parseInt(request.getParameter("Quantity_"+paypal)));
                    }
                    if(request.getParameter("StartPrice.value_"+paypal)!=null&&!"".equals(request.getParameter("StartPrice.value_"+paypal))){
                        StartPrice sp = new StartPrice();
                        sp.setValue(Double.parseDouble(request.getParameter("StartPrice.value_"+paypal)));
                        sp.setCurrencyID(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(tradingItem.getSite())).getValue1());
                        item.setStartPrice(sp);
                    }
                    String [] picurl = request.getParameterValues("PictureDetails_"+paypal+".PictureURL");
                    if(picurl!=null&&picurl.length>0){
                        PictureDetails pd = item.getPictureDetails();
                        if(pd==null){
                            pd = new PictureDetails();
                        }
                        List lipic = new ArrayList();
                        for(int i = 0;i<picurl.length;i++){
                            lipic.add(picurl[i]);
                        }
                        if(lipic.size()>0){
                            pd.setPictureURL(lipic);
                        }
                        item.setPictureDetails(pd);
                    }
                    getEbayPicUrl(item,tradingItem,request);
                    //getEbayPicUrl(item,tradingItem,paypal);
                    UsercontrollerEbayAccount ua = this.iUsercontrollerEbayAccount.selectById(Long.parseLong(paypal));
                    //PublicUserConfig pUserConfig = DataDictionarySupport.getPublicUserConfigByID(ua.getPaypalAccountId());
                    //如果配置ＥＢＡＹ账号时，选择强制使用paypal账号则用该账号
                    //item.setPayPalEmailAddress(pUserConfig.getConfigValue());
                    //定时刊登时，需要获取保存到数据库中的ＩＤ
                    if (item.getListingType().equals("Chinese")) {
                        AddItemRequest addItem = new AddItemRequest();
                        addItem.setXmlns("urn:ebay:apis:eBLBaseComponents");
                        addItem.setErrorLanguage("en_US");
                        addItem.setWarningLevel("High");
                        RequesterCredentials rc = new RequesterCredentials();
                        rc.seteBayAuthToken(ua.getEbayToken());
                        addItem.setRequesterCredentials(rc);
                        addItem.setItem(item);
                        xml = PojoXmlUtil.pojoToXml(addItem);
                        xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + xml;
                    } else {
                        if("2".equals(tradingItem.getListingtype())){
                            item.setStartPrice(null);
                        }
                        AddFixedPriceItemRequest addItem = new AddFixedPriceItemRequest();
                        addItem.setXmlns("urn:ebay:apis:eBLBaseComponents");
                        addItem.setErrorLanguage("en_US");
                        addItem.setWarningLevel("High");
                        RequesterCredentials rc = new RequesterCredentials();
                        rc.seteBayAuthToken(ua.getEbayToken());
                        addItem.setRequesterCredentials(rc);
                        addItem.setItem(item);
                        xml = PojoXmlUtil.pojoToXml(addItem);
                        xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + xml;
                    }
                    //得到刊登成功后的ＩＤ
                    TradingTimerListingWithBLOBs ttl = new TradingTimerListingWithBLOBs();
                    ttl.setItem(Long.parseLong(itemMap.get(paypal).toString()));
                    ttl.setTimer(timerListing);
                    ttl.setTimerMessage(StringEscapeUtils.escapeXml(xml));
                    if (item.getListingType().equals("Chinese")) {
                        ttl.setApiMethod(APINameStatic.AddItem);
                    } else {
                        ttl.setApiMethod(APINameStatic.AddFixedPriceItem);
                    }
                    ttl.setEbayId(paypal);
                    ttl.setStateId(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(tradingItem.getSite())).getName1());
                    ttl.setTimerFlag("0");
                    this.iTradingTimerListing.saveTradingTimer(ttl);
                    //System.out.println(xml);
                }
                AjaxSupport.sendSuccessText("message", "操作成功！");
            }else {//立即刊登
                for (String paypal : paypals) {
                    item.setTitle(request.getParameter("Title_"+paypal));
                    item.setSubTitle(request.getParameter("SubTitle_"+paypal));
                    if(request.getParameter("Quantity_"+paypal)!=null&&!"".equals(request.getParameter("Quantity_"+paypal))){
                        item.setQuantity(Integer.parseInt(request.getParameter("Quantity_"+paypal)));
                    }
                    if(request.getParameter("StartPrice.value_"+paypal)!=null&&!"".equals(request.getParameter("StartPrice.value_"+paypal))){
                        StartPrice sp = new StartPrice();
                        sp.setValue(Double.parseDouble(request.getParameter("StartPrice.value_"+paypal)));
                        sp.setCurrencyID(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(tradingItem.getSite())).getValue1());
                        item.setStartPrice(sp);
                    }
                    String [] picurl = request.getParameterValues("PictureDetails_"+paypal+".PictureURL");
                    if(picurl!=null&&picurl.length>0){
                        PictureDetails pd = item.getPictureDetails();
                        if(pd==null){
                            pd = new PictureDetails();
                        }
                        List lipic = new ArrayList();
                        for(int i = 0;i<picurl.length;i++){
                            lipic.add(picurl[i]);
                        }
                        if(lipic.size()>0){
                            pd.setPictureURL(lipic);
                        }
                        item.setPictureDetails(pd);
                    }
                    getEbayPicUrl(item,tradingItem,request);
                    //getEbayPicUrl(item,tradingItem,paypal);
                    UsercontrollerEbayAccount ua = this.iUsercontrollerEbayAccount.selectById(Long.parseLong(paypal));
                    //PublicUserConfig pUserConfig = DataDictionarySupport.getPublicUserConfigByID(ua.getPaypalAccountId());
                    //如果配置ＥＢＡＹ账号时，选择强制使用paypal账号则用该账号
                    //item.setPayPalEmailAddress(pUserConfig.getConfigValue());
                    //定时刊登时，需要获取保存到数据库中的ＩＤ
                    if (item.getListingType().equals("Chinese")) {
                        AddItemRequest addItem = new AddItemRequest();
                        addItem.setXmlns("urn:ebay:apis:eBLBaseComponents");
                        addItem.setErrorLanguage("en_US");
                        addItem.setWarningLevel("High");
                        RequesterCredentials rc = new RequesterCredentials();
                        rc.seteBayAuthToken(ua.getEbayToken());
                        addItem.setRequesterCredentials(rc);
                        addItem.setItem(item);
                        xml = PojoXmlUtil.pojoToXml(addItem);
                        xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + xml;
                    } else {
                        if("2".equals(tradingItem.getListingtype())){
                            item.setStartPrice(null);
                        }
                        AddFixedPriceItemRequest addItem = new AddFixedPriceItemRequest();
                        addItem.setXmlns("urn:ebay:apis:eBLBaseComponents");
                        addItem.setErrorLanguage("en_US");
                        addItem.setWarningLevel("High");
                        RequesterCredentials rc = new RequesterCredentials();
                        rc.seteBayAuthToken(ua.getEbayToken());
                        addItem.setRequesterCredentials(rc);
                        addItem.setItem(item);
                        xml = PojoXmlUtil.pojoToXml(addItem);
                        xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + xml;
                    }
                    System.out.println(xml);
                    //Asserts.assertTrue(false, "错误");
                    UsercontrollerDevAccountExtend d = new UsercontrollerDevAccountExtend();
                    d.setApiSiteid(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(tradingItem.getSite())).getName1());
                    if (item.getListingType().equals("Chinese")) {
                        d.setApiCallName(APINameStatic.AddItem);
                    } else {
                        d.setApiCallName(APINameStatic.AddFixedPriceItem);
                    }
                    //String xml= BindAccountAPI.getSessionID(d.getRunname());

                    AddApiTask addApiTask = new AddApiTask();

                    if("1".equals(request.getParameter("ListingMessage"))){//如果是延迟通知//后台刊登
                        TaskMessageVO taskMessageVO=new TaskMessageVO();
                        taskMessageVO.setMessageContext("刊登");
                        taskMessageVO.setMessageTitle("刊登操作");
                        taskMessageVO.setMessageType(SiteMessageStatic.LISTING_MESSAGE_TYPE);
                        taskMessageVO.setBeanNameType(SiteMessageStatic.LISTING_ITEM_BEAN);
                        taskMessageVO.setMessageFrom("system");
                        SessionVO sessionVO=SessionCacheSupport.getSessionVO();
                        taskMessageVO.setMessageTo(sessionVO.getId());
                        taskMessageVO.setObjClass(tradingItem);
                        addApiTask.execDelayReturn(d, xml, apiUrl, taskMessageVO);
                        AjaxSupport.sendSuccessText("message", "操作成功！结果请稍后查看消息！");
                        return;
                    }else {
                        Map<String, String> resMap = addApiTask.exec(d, xml, apiUrl);
                        String r1 = resMap.get("stat");
                        String res = resMap.get("message");
                        if ("fail".equalsIgnoreCase(r1)) {
                            logger.error("数据已保存，但刊登失败！由于返回报文不全，无法得到更详细的信息！");
                            AjaxSupport.sendFailText("fail", "数据已保存，但刊登失败！");
                            AjaxSupport.sendFailText("fail", "{\"isFlag\":\"1\",\"message\":\"数据已保存，但刊登失败！\",\"tradingItemId\":\""+tradingItem.getId()+"\"}");
                            return;
                        }
                        String ack = SamplePaseXml.getVFromXmlString(res, "Ack");
                        if ("Success".equalsIgnoreCase(ack) || "Warning".equalsIgnoreCase(ack)) {
                            String itemId = SamplePaseXml.getVFromXmlString(res, "ItemID");
                            tradingItem.setItemId(itemId);
                            tradingItem.setIsFlag("Success");
                            this.iTradingItem.saveTradingItem(tradingItem);
                            this.iTradingItem.saveListingSuccess(res, itemId);
                            //新增在线商品表数据
                            this.iTradingListingData.saveTradingListingDataByTradingItem(tradingItem,res);
                            AjaxSupport.sendSuccessText("message", "商品SKU为："+tradingItem.getSku()+"，名称为：<a target=_blank style='color:blue' href='"+service_item_url+itemId+"'>"+tradingItem.getItemName()+"<a>，刊登成功！");
                        } else {
                            //String errors = SamplePaseXml.getVFromXmlString(res, "Errors");
                            Document document= SamplePaseXml.formatStr2Doc(res);
                            Element rootElt = document.getRootElement();
                            Iterator<Element> e =  rootElt.elementIterator("Errors");
                            String errors = "";
                            if(e!=null){
                                while (e.hasNext()){
                                    Element es = e.next();
                                    errors+=es.elementText("LongMessage")+"/n";
                                }
                            }
                            logger.error("刊登失败："+errors);
                            AjaxSupport.sendFailText("fail", "{\"isFlag\":\"1\",\"message\":\"数据已保存，但刊登失败！"+errors+"\",\"tradingItemId\":\""+tradingItem.getId()+"\"}");
                        }
                    }
                }
            }
        }else if("updateListing".equals(mouth)){//更新在线刊登
            this.updateListingData(item,tradingItem,request);
            AjaxSupport.sendSuccessText("message","更新成功！");
            return;
        }
    }

    public void updateListingData(Item item,TradingItemWithBLOBs tradingItem,HttpServletRequest request) throws Exception{
        //保存商品信息到数据库中
        Map itemMap = this.iTradingItem.saveItem(item,tradingItem);
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
            ShippingDetails sd = this.iTradingShippingDetails.toXmlPojo(tradingItem.getShippingDeailsId(),tradingItem.getId());
            item.setShippingDetails(sd);
            if(tshipping.getDispatchtimemax()!=null&&tshipping.getDispatchtimemax()!=0) {
                item.setDispatchTimeMax(tshipping.getDispatchtimemax().intValue());
            }
        }
        String template = "";
        if(tradingItem.getTemplateId()!=null){//如果选择了模板
            TradingTemplateInitTable tttt = this.iTradingTemplateInitTable.selectById(tradingItem.getTemplateId());
            template = tttt.getTemplateHtml();
            String [] tempicUrls = request.getParameterValues("blankimg");//界面中添加的模板图片
            if(tempicUrls!=null&&tempicUrls.length>0){//如果界面中模板图片不为空，那么替换模板中ＵＲＬ地址
                org.jsoup.nodes.Document doc = org.jsoup.Jsoup.parse(template);
                org.jsoup.select.Elements content = doc.getElementsByAttributeValue("name", "blankimg");
                String url = "";
                int j=0;
                for(int i=0;i<content.size();i++){
                    org.jsoup.nodes.Element el = content.get(i);
                    url = el.attr("src");
                    if(url.indexOf("blank.jpg")>0&&j<=tempicUrls.length){
                        el.attr("src",tempicUrls[j]);
                        j++;
                    }
                }
                template=doc.html();
            }else{
                org.jsoup.nodes.Document doc = org.jsoup.Jsoup.parse(template);
                org.jsoup.select.Elements content = doc.getElementsByAttributeValue("name", "blankimg");
                String url = "";
                int j=0;
                for(int i=0;i<content.size();i++){
                    org.jsoup.nodes.Element el = content.get(i);
                    el.remove();
                }
                template=doc.html();
            }

            template = template.replace("{ProductDetail}",tradingItem.getDescription());
            if(tttt!=null&&tradingItem.getSellerItemInfoId()!=null){//如果选择了卖家描述
                TradingDescriptionDetailsWithBLOBs tdd = this.iTradingDescriptionDetails.selectById(tradingItem.getSellerItemInfoId());
                template = template.replace("{PaymentMethodTitle}",tdd.getPayTitle()==null?"PayTitle":tdd.getPayTitle());
                template = template.replace("{PaymentMethod}",tdd.getPayInfo());

                template = template.replace("{ShippingDetailTitle}",tdd.getShippingTitle()==null?"ShippingTitle":tdd.getShippingTitle());
                template = template.replace("{ShippingDetail}",tdd.getShippingInfo());

                template = template.replace("{SalesPolicyTitle}",tdd.getGuaranteeTitle()==null?"GuaranteeTitle":tdd.getGuaranteeTitle());
                template = template.replace("{SalesPolicy}",tdd.getGuaranteeInfo());

                template = template.replace("{AboutUsTitle}",tdd.getFeedbackTitle()==null?"FeedbackTitle":tdd.getFeedbackTitle());
                template = template.replace("{AboutUs}",tdd.getFeedbackInfo());

                template = template.replace("{ContactUsTitle}",tdd.getContactTitle()==null?"ContactTitle":tdd.getContactTitle());
                template = template.replace("{ContactUs}",tdd.getContactInfo());
            }else{
                template = template.replace("{PaymentMethodTitle}","");
                template = template.replace("{PaymentMethod}","");

                template = template.replace("{ShippingDetailTitle}","");
                template = template.replace("{ShippingDetail}","");

                template = template.replace("{SalesPolicyTitle}","");
                template = template.replace("{SalesPolicy}","");

                template = template.replace("{AboutUsTitle}","");
                template = template.replace("{AboutUs}","");

                template = template.replace("{ContactUsTitle}","");
                template = template.replace("{ContactUs}","");
            }
        }else{//未选择模板，
            template+=item.getDescription()+"</br>";
            if(tradingItem.getSellerItemInfoId()!=null){//如果选择了卖家描述
                TradingDescriptionDetailsWithBLOBs tdd = this.iTradingDescriptionDetails.selectById(tradingItem.getSellerItemInfoId());
                template+=tdd.getPayTitle()+"</br>"+tdd.getPayInfo()==null?"":tdd.getPayInfo()+"</br>";
                template+=tdd.getShippingTitle()+"</br>"+tdd.getShippingInfo()==null?"":tdd.getShippingInfo()+"</br>";
                template+=tdd.getGuaranteeTitle()+"</br>"+tdd.getGuaranteeInfo()==null?"":tdd.getGuaranteeInfo()+"</br>";
                template+=tdd.getFeedbackTitle()+"</br>"+tdd.getFeedbackInfo()==null?"":tdd.getFeedbackInfo()+"</br>";
                template+=tdd.getContactTitle()+"</br>"+tdd.getContactInfo()==null?"":tdd.getContactInfo()+"</br>";

            }
        }
        item.setDescription(template);

        if(request.getParameter("SecondaryCategory.CategoryID")==null||"".equals(request.getParameter("SecondaryCategory.CategoryID"))){
            item.setSecondaryCategory(null);
        }
        TradingItemAddress tia = this.iTradingItemAddress.selectById(tradingItem.getItemLocationId());
        Asserts.assertTrue(tia!=null,"物品所在地不能为空！");
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
        item.setListingDuration(item.getListingDuration() == null ? "GTC" : item.getListingDuration());
        if(item.getDispatchTimeMax()==null) {
            item.setDispatchTimeMax(0);
        }
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
            if(item.getStartPrice()!=null){
                tradingItem.setStartprice(item.getStartPrice().getValue());
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
        String [] paypals = request.getParameterValues("ebayAccounts");
        for (String paypal : paypals) {
            item.setTitle(request.getParameter("Title_"+paypal));
            item.setSubTitle(request.getParameter("SubTitle_"+paypal));
            if(request.getParameter("Quantity_"+paypal)!=null&&!"".equals(request.getParameter("Quantity_"+paypal))){
                item.setQuantity(Integer.parseInt(request.getParameter("Quantity_"+paypal)));
            }
            if(request.getParameter("StartPrice.value_"+paypal)!=null&&!"".equals(request.getParameter("StartPrice.value_"+paypal))){
                StartPrice sp = new StartPrice();
                sp.setValue(Double.parseDouble(request.getParameter("StartPrice.value_"+paypal)));
                sp.setCurrencyID(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(tradingItem.getSite())).getValue1());
                item.setStartPrice(sp);
            }
            String [] picurl = request.getParameterValues("PictureDetails_"+paypal+".PictureURL");
            if(picurl!=null&&picurl.length>0){
                PictureDetails pd = item.getPictureDetails();
                if(pd==null){
                    pd = new PictureDetails();
                }
                List lipic = new ArrayList();
                for(int i = 0;i<picurl.length;i++){
                    lipic.add(picurl[i]);
                }
                if(lipic.size()>0){
                    pd.setPictureURL(lipic);
                }
                item.setPictureDetails(pd);
            }
            TradingItem tradingItem1 = this.iTradingItem.selectById(tradingItem.getId());
            if(tradingItem1.getItemId()==null||"".equals(tradingItem1.getItemId())){
                Asserts.assertTrue(false,"该商品未成功刊登，不能进行修改！");
            }
            item.setItemID(tradingItem1.getItemId());
            getEbayPicUrl(item,tradingItem,request);
            //getEbayPicUrl(item,tradingItem,paypal);
            UsercontrollerEbayAccount ua = this.iUsercontrollerEbayAccount.selectById(Long.parseLong(paypal));
            //PublicUserConfig pUserConfig = DataDictionarySupport.getPublicUserConfigByID(ua.getPaypalAccountId());
            //如果配置ＥＢＡＹ账号时，选择强制使用paypal账号则用该账号
            //item.setPayPalEmailAddress(pUserConfig.getConfigValue());
            //定时刊登时，需要获取保存到数据库中的ＩＤ
            ReviseItemRequest rir = new ReviseItemRequest();
            rir.setXmlns("urn:ebay:apis:eBLBaseComponents");
            rir.setErrorLanguage("en_US");
            rir.setWarningLevel("High");
            RequesterCredentials rc = new RequesterCredentials();
            SessionVO c= SessionCacheSupport.getSessionVO();
            rc.seteBayAuthToken(ua.getEbayToken());
            rir.setRequesterCredentials(rc);
            String xml = "";
            rir.setItem(item);
            xml = PojoXmlUtil.pojoToXml(rir);
            xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"+xml;

            System.out.println(xml);
            //Asserts.assertTrue(false, "错误");
            UsercontrollerDevAccountExtend d = new UsercontrollerDevAccountExtend();
            d.setApiSiteid(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(tradingItem.getSite())).getName1());
            d.setApiCallName(APINameStatic.ReviseItem);
            //String xml= BindAccountAPI.getSessionID(d.getRunname());

            AddApiTask addApiTask = new AddApiTask();

            /*if("1".equals(request.getParameter("ListingMessage"))){//如果是延迟通知*/
                TaskMessageVO taskMessageVO=new TaskMessageVO();
                taskMessageVO.setMessageContext("通过范本更新在线商品");
                taskMessageVO.setMessageTitle("通过范本更新在线商品");
                taskMessageVO.setMessageType(SiteMessageStatic.UPDATE_LISTING_DATA_MESSAGE_TYPE);
                taskMessageVO.setBeanNameType(null);
                taskMessageVO.setMessageFrom("system");
                SessionVO sessionVO=SessionCacheSupport.getSessionVO();
                taskMessageVO.setMessageTo(sessionVO.getId());
                taskMessageVO.setObjClass(null);
                taskMessageVO.setSendOrNotSend(true);
                taskMessageVO.setWeithSendSuccessMessage(true);
                addApiTask.execDelayReturn(d, xml, apiUrl, taskMessageVO);
                AjaxSupport.sendSuccessText("message", "操作成功！结果请稍后查看消息！");
            /*}else {
                Map<String, String> resMap = addApiTask.exec(d, xml, apiUrl);
                String r1 = resMap.get("stat");
                String res = resMap.get("message");
                if ("fail".equalsIgnoreCase(r1)) {
                    AjaxSupport.sendFailText("fail", "数据已保存，但刊登失败！");
                    return;
                }
                String ack = SamplePaseXml.getVFromXmlString(res, "Ack");
                if ("Success".equalsIgnoreCase(ack) || "Warning".equalsIgnoreCase(ack)) {
                    String itemId = SamplePaseXml.getVFromXmlString(res, "ItemID");
                    tradingItem.setItemId(itemId);
                    tradingItem.setIsFlag("Success");
                    this.iTradingItem.saveTradingItem(tradingItem);
                    AjaxSupport.sendSuccessText("message", "操作成功！");
                } else {
                    //String errors = SamplePaseXml.getVFromXmlString(res, "Errors");
                    String errors  = SamplePaseXml.getSpecifyElementTextAllInOne(res,"Errors","LongMessage");

                    AjaxSupport.sendFailText("fail", "数据已保存，但刊登失败！"+errors);
                }
            }*/
        }
    }

    /**
     * 选择产品列表列表
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping("/selectItemInformation.do")
    public ModelAndView selectItemInformation(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
        return forword("/itemInformation/selectItemInformation",modelMap);
    }
    /**
     * 预览功能
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping("/previewItem.do")
    public ModelAndView previewItem(TradingItemWithBLOBs tradingItem,Item item,HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception{
        //保存商品信息到数据库中
        Map itemMap = this.iTradingItem.saveItem(item,tradingItem);
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
            ShippingDetails sd = this.iTradingShippingDetails.toXmlPojo(tradingItem.getShippingDeailsId(),tradingItem.getId());
            item.setShippingDetails(sd);
            if(tshipping.getDispatchtimemax()!=null&&tshipping.getDispatchtimemax()!=0) {
                item.setDispatchTimeMax(tshipping.getDispatchtimemax().intValue());
            }
        }
        String template = "";
        if(tradingItem.getTemplateId()!=null){//如果选择了模板
            TradingTemplateInitTable tttt = this.iTradingTemplateInitTable.selectById(tradingItem.getTemplateId());
            template = tttt.getTemplateHtml();
            String [] tempicUrls = request.getParameterValues("blankimg");//界面中添加的模板图片
            if(tempicUrls!=null&&tempicUrls.length>0){//如果界面中模板图片不为空，那么替换模板中ＵＲＬ地址
                org.jsoup.nodes.Document doc = org.jsoup.Jsoup.parse(template);
                org.jsoup.select.Elements content = doc.getElementsByAttributeValue("name", "blankimg");
                String url = "";
                int j=0;
                for(int i=0;i<content.size();i++){
                    org.jsoup.nodes.Element el = content.get(i);
                    url = el.attr("src");
                    if(url.indexOf("blank.jpg")>0&&j<=tempicUrls.length){
                        el.attr("src",tempicUrls[j]);
                        j++;
                    }
                }
                template=doc.html();
            }else{
                org.jsoup.nodes.Document doc = org.jsoup.Jsoup.parse(template);
                org.jsoup.select.Elements content = doc.getElementsByAttributeValue("name", "blankimg");
                String url = "";
                int j=0;
                for(int i=0;i<content.size();i++){
                    org.jsoup.nodes.Element el = content.get(i);
                    el.remove();
                }
                template=doc.html();
            }

            template = template.replace("{ProductDetail}",tradingItem.getDescription());
            if(tttt!=null&&tradingItem.getSellerItemInfoId()!=null){//如果选择了卖家描述
                TradingDescriptionDetailsWithBLOBs tdd = this.iTradingDescriptionDetails.selectById(tradingItem.getSellerItemInfoId());
                template = template.replace("{PaymentMethodTitle}",tdd.getPayTitle()==null?"PayTitle":tdd.getPayTitle());
                template = template.replace("{PaymentMethod}",tdd.getPayInfo());

                template = template.replace("{ShippingDetailTitle}",tdd.getShippingTitle()==null?"ShippingTitle":tdd.getShippingTitle());
                template = template.replace("{ShippingDetail}",tdd.getShippingInfo());

                template = template.replace("{SalesPolicyTitle}",tdd.getGuaranteeTitle()==null?"GuaranteeTitle":tdd.getGuaranteeTitle());
                template = template.replace("{SalesPolicy}",tdd.getGuaranteeInfo());

                template = template.replace("{AboutUsTitle}",tdd.getFeedbackTitle()==null?"FeedbackTitle":tdd.getFeedbackTitle());
                template = template.replace("{AboutUs}",tdd.getFeedbackInfo());

                template = template.replace("{ContactUsTitle}",tdd.getContactTitle()==null?"ContactTitle":tdd.getContactTitle());
                template = template.replace("{ContactUs}",tdd.getContactInfo());
            }
        }else{//未选择模板，
            template+=item.getDescription()+"</br>";
            if(tradingItem.getSellerItemInfoId()!=null){//如果选择了卖家描述
                TradingDescriptionDetailsWithBLOBs tdd = this.iTradingDescriptionDetails.selectById(tradingItem.getSellerItemInfoId());

                template+=tdd.getPayTitle()+"</br>"+tdd.getPayInfo()==null?"":tdd.getPayInfo()+"</br>";
                template+=tdd.getShippingTitle()+"</br>"+tdd.getShippingInfo()==null?"":tdd.getShippingInfo()+"</br>";
                template+=tdd.getGuaranteeTitle()+"</br>"+tdd.getGuaranteeInfo()==null?"":tdd.getGuaranteeInfo()+"</br>";
                template+=tdd.getFeedbackTitle()+"</br>"+tdd.getFeedbackInfo()==null?"":tdd.getFeedbackInfo()+"</br>";
                template+=tdd.getContactTitle()+"</br>"+tdd.getContactInfo()==null?"":tdd.getContactInfo()+"</br>";

            }
        }
        item.setDescription(template);

        if(request.getParameter("SecondaryCategory.CategoryID")==null||"".equals(request.getParameter("SecondaryCategory.CategoryID"))){
            item.setSecondaryCategory(null);
        }
        TradingItemAddress tia = this.iTradingItemAddress.selectById(tradingItem.getItemLocationId());
        Asserts.assertTrue(tia!=null,"物品所在地不能为空！");
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
        item.setListingDuration(item.getListingDuration() == null ? "GTC" : item.getListingDuration());
        if(item.getDispatchTimeMax()!=null) {
            item.setDispatchTimeMax(0);
        }
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
            if(item.getStartPrice()!=null){
                tradingItem.setStartprice(item.getStartPrice().getValue());
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

        String xml="";
        //当选择多账号时刊登
        String [] paypals = request.getParameterValues("ebayAccounts");
        String paypal = paypals[0];
        item.setTitle(request.getParameter("Title_"+paypal));
        item.setSubTitle(request.getParameter("SubTitle_"+paypal));
        if(request.getParameter("Quantity_"+paypal)!=null&&!"".equals(request.getParameter("Quantity_"+paypal))){
            item.setQuantity(Integer.parseInt(request.getParameter("Quantity_"+paypal)));
        }
        if(request.getParameter("StartPrice.value_"+paypal)!=null&&!"".equals(request.getParameter("StartPrice.value_"+paypal))){
            StartPrice sp = new StartPrice();
            sp.setValue(Double.parseDouble(request.getParameter("StartPrice.value_"+paypal)));
            sp.setCurrencyID(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(tradingItem.getSite())).getValue1());
            item.setStartPrice(sp);
        }
        String [] picurl = request.getParameterValues("PictureDetails_"+paypal+".PictureURL");
        if(picurl!=null&&picurl.length>0){
            PictureDetails pd = item.getPictureDetails();
            if(pd==null){
                pd = new PictureDetails();
            }
            List lipic = new ArrayList();
            for(int i = 0;i<picurl.length;i++){
                lipic.add(picurl[i]);
            }
            if(lipic.size()>0){
                pd.setPictureURL(lipic);
            }
            item.setPictureDetails(pd);
        }
        getEbayPicUrl(item,tradingItem,request);
        //getEbayPicUrl(item,tradingItem,paypal);
        UsercontrollerEbayAccount ua = this.iUsercontrollerEbayAccount.selectById(Long.valueOf(autowiredClass.sandboxEbayID));
        //PublicUserConfig pUserConfig = DataDictionarySupport.getPublicUserConfigByID(ua.getPaypalAccountId());
        //如果配置ＥＢＡＹ账号时，选择强制使用paypal账号则用该账号
        //item.setPayPalEmailAddress(pUserConfig.getConfigValue());
        //定时刊登时，需要获取保存到数据库中的ＩＤ
        item.setPayPalEmailAddress("caixu23@gmail.com");
        if (item.getListingType().equals("Chinese")) {
            AddItemRequest addItem = new AddItemRequest();
            addItem.setXmlns("urn:ebay:apis:eBLBaseComponents");
            addItem.setErrorLanguage("en_US");
            addItem.setWarningLevel("High");
            RequesterCredentials rc = new RequesterCredentials();
            rc.seteBayAuthToken(ua.getEbayToken());
            addItem.setRequesterCredentials(rc);
            addItem.setItem(item);
            xml = PojoXmlUtil.pojoToXml(addItem);
            xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + xml;
        } else {
            if("2".equals(tradingItem.getListingtype())){
                item.setStartPrice(null);
            }
            AddFixedPriceItemRequest addItem = new AddFixedPriceItemRequest();
            addItem.setXmlns("urn:ebay:apis:eBLBaseComponents");
            addItem.setErrorLanguage("en_US");
            addItem.setWarningLevel("High");
            RequesterCredentials rc = new RequesterCredentials();
            rc.seteBayAuthToken(ua.getEbayToken());
            addItem.setRequesterCredentials(rc);
            addItem.setItem(item);
            xml = PojoXmlUtil.pojoToXml(addItem);
            xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + xml;
        }
        System.out.println(xml);
        //Asserts.assertTrue(false, "错误");
        UsercontrollerDevAccountExtend d = new UsercontrollerDevAccountExtend();
        d.setApiSiteid(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(tradingItem.getSite())).getName1());
        if (item.getListingType().equals("Chinese")) {
            d.setApiCallName(APINameStatic.AddItem);
        } else {
            d.setApiCallName(APINameStatic.AddFixedPriceItem);
        }
        //String xml= BindAccountAPI.getSessionID(d.getRunname());

        AddApiTask addApiTask = new AddApiTask();

        Map<String, String> resMap = addApiTask.exec(d, xml, sandboxApiUrl);
        String r1 = resMap.get("stat");
        String res = resMap.get("message");
        if ("fail".equalsIgnoreCase(r1)) {
            AjaxSupport.sendFailText("fail", "预览数据失败！");
        }
        String ack = SamplePaseXml.getVFromXmlString(res, "Ack");
        System.out.println("-----------------");
        System.out.println(res);
        System.out.println("-----------------");
        if ("Success".equalsIgnoreCase(ack) || "Warning".equalsIgnoreCase(ack)) {
            String itemId = SamplePaseXml.getVFromXmlString(res, "ItemID");
            tradingItem.setItemId(itemId);
            tradingItem.setIsFlag("Success");
            //this.iTradingItem.saveTradingItem(tradingItem);
            modelMap.put("tradingItem",tradingItem);
            //AjaxSupport.sendSuccessText("message", "操作成功！");
        } else {
            //String errors = SamplePaseXml.getVFromXmlString(res, "Errors");
            String errors  = SamplePaseXml.getSpecifyElementTextAllInOne(res,"Errors","LongMessage");
            AjaxSupport.sendFailText("fail", "数据已保存，但刊登失败！"+errors);
        }

        return forword("/item/preview",modelMap);
    }

    /**
     * 检查ebay费
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping("/ajax/checkEbayFee.do")
    public void checkEbayFee(TradingItemWithBLOBs tradingItem,Item item,HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception{
        //保存商品信息到数据库中
        Map itemMap = this.iTradingItem.saveItem(item,tradingItem);
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
            ShippingDetails sd = this.iTradingShippingDetails.toXmlPojo(tradingItem.getShippingDeailsId(),tradingItem.getId());
            item.setShippingDetails(sd);
            if(tshipping.getDispatchtimemax()!=null&&tshipping.getDispatchtimemax()!=0) {
                item.setDispatchTimeMax(tshipping.getDispatchtimemax().intValue());
            }
        }
        String template = "";
        if(tradingItem.getTemplateId()!=null){//如果选择了模板
            TradingTemplateInitTable tttt = this.iTradingTemplateInitTable.selectById(tradingItem.getTemplateId());
            template = tttt.getTemplateHtml();
            String [] tempicUrls = request.getParameterValues("blankimg");//界面中添加的模板图片
            if(tempicUrls!=null&&tempicUrls.length>0){//如果界面中模板图片不为空，那么替换模板中ＵＲＬ地址
                org.jsoup.nodes.Document doc = org.jsoup.Jsoup.parse(template);
                org.jsoup.select.Elements content = doc.getElementsByAttributeValue("name", "blankimg");
                String url = "";
                int j=0;
                for(int i=0;i<content.size();i++){
                    org.jsoup.nodes.Element el = content.get(i);
                    url = el.attr("src");
                    if(url.indexOf("blank.jpg")>0&&j<=tempicUrls.length){
                        el.attr("src",tempicUrls[j]);
                        j++;
                    }
                }
                template=doc.html();
            }else{
                org.jsoup.nodes.Document doc = org.jsoup.Jsoup.parse(template);
                org.jsoup.select.Elements content = doc.getElementsByAttributeValue("name", "blankimg");
                String url = "";
                int j=0;
                for(int i=0;i<content.size();i++){
                    org.jsoup.nodes.Element el = content.get(i);
                    el.remove();
                }
                template=doc.html();
            }

            template = template.replace("{ProductDetail}",tradingItem.getDescription());
            if(tttt!=null&&tradingItem.getSellerItemInfoId()!=null){//如果选择了卖家描述
                TradingDescriptionDetailsWithBLOBs tdd = this.iTradingDescriptionDetails.selectById(tradingItem.getSellerItemInfoId());
                template = template.replace("{PaymentMethodTitle}",tdd.getPayTitle()==null?"PayTitle":tdd.getPayTitle());
                template = template.replace("{PaymentMethod}",tdd.getPayInfo());

                template = template.replace("{ShippingDetailTitle}",tdd.getShippingTitle()==null?"ShippingTitle":tdd.getShippingTitle());
                template = template.replace("{ShippingDetail}",tdd.getShippingInfo());

                template = template.replace("{SalesPolicyTitle}",tdd.getGuaranteeTitle()==null?"GuaranteeTitle":tdd.getGuaranteeTitle());
                template = template.replace("{SalesPolicy}",tdd.getGuaranteeInfo());

                template = template.replace("{AboutUsTitle}",tdd.getFeedbackTitle()==null?"FeedbackTitle":tdd.getFeedbackTitle());
                template = template.replace("{AboutUs}",tdd.getFeedbackInfo());

                template = template.replace("{ContactUsTitle}",tdd.getContactTitle()==null?"ContactTitle":tdd.getContactTitle());
                template = template.replace("{ContactUs}",tdd.getContactInfo());
            }
        }else{//未选择模板，
            template+=item.getDescription()+"</br>";
            if(tradingItem.getSellerItemInfoId()!=null){//如果选择了卖家描述
                TradingDescriptionDetailsWithBLOBs tdd = this.iTradingDescriptionDetails.selectById(tradingItem.getSellerItemInfoId());

                template+=tdd.getPayTitle()+"</br>"+tdd.getPayInfo()==null?"":tdd.getPayInfo()+"</br>";
                template+=tdd.getShippingTitle()+"</br>"+tdd.getShippingInfo()==null?"":tdd.getShippingInfo()+"</br>";
                template+=tdd.getGuaranteeTitle()+"</br>"+tdd.getGuaranteeInfo()==null?"":tdd.getGuaranteeInfo()+"</br>";
                template+=tdd.getFeedbackTitle()+"</br>"+tdd.getFeedbackInfo()==null?"":tdd.getFeedbackInfo()+"</br>";
                template+=tdd.getContactTitle()+"</br>"+tdd.getContactInfo()==null?"":tdd.getContactInfo()+"</br>";

            }
        }
        item.setDescription(template);

        if(request.getParameter("SecondaryCategory.CategoryID")==null||"".equals(request.getParameter("SecondaryCategory.CategoryID"))){
            item.setSecondaryCategory(null);
        }
        TradingItemAddress tia = this.iTradingItemAddress.selectById(tradingItem.getItemLocationId());
        Asserts.assertTrue(tia!=null,"物品所在地不能为空！");
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
        item.setListingDuration(item.getListingDuration() == null ? "GTC" : item.getListingDuration());
        if(item.getDispatchTimeMax()==null) {
            item.setDispatchTimeMax(0);
        }
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
            if(item.getStartPrice()!=null){
                tradingItem.setStartprice(item.getStartPrice().getValue());
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

        String xml="";
        //当选择多账号时刊登
        String [] paypals = request.getParameterValues("ebayAccounts");
        String paypal = paypals[0];
        item.setTitle(request.getParameter("Title_"+paypal));
        item.setSubTitle(request.getParameter("SubTitle_"+paypal));
        if(request.getParameter("Quantity_"+paypal)!=null&&!"".equals(request.getParameter("Quantity_"+paypal))){
            item.setQuantity(Integer.parseInt(request.getParameter("Quantity_"+paypal)));
        }
        if(request.getParameter("StartPrice.value_"+paypal)!=null&&!"".equals(request.getParameter("StartPrice.value_"+paypal))){
            StartPrice sp = new StartPrice();
            sp.setValue(Double.parseDouble(request.getParameter("StartPrice.value_"+paypal)));
            sp.setCurrencyID(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(tradingItem.getSite())).getValue1());
            item.setStartPrice(sp);
        }
        String [] picurl = request.getParameterValues("PictureDetails_"+paypal+".PictureURL");
        if(picurl!=null&&picurl.length>0){
            PictureDetails pd = item.getPictureDetails();
            if(pd==null){
                pd = new PictureDetails();
            }
            List lipic = new ArrayList();
            for(int i = 0;i<picurl.length;i++){
                lipic.add(picurl[i]);
            }
            if(lipic.size()>0){
                pd.setPictureURL(lipic);
            }
            item.setPictureDetails(pd);
        }
        getEbayPicUrl(item,tradingItem,request);
        //getEbayPicUrl(item,tradingItem,paypal);
        UsercontrollerEbayAccount ua = this.iUsercontrollerEbayAccount.selectById(Long.parseLong(autowiredClass.sandboxEbayID));
        //PublicUserConfig pUserConfig = DataDictionarySupport.getPublicUserConfigByID(ua.getPaypalAccountId());
        //如果配置ＥＢＡＹ账号时，选择强制使用paypal账号则用该账号
        //item.setPayPalEmailAddress(pUserConfig.getConfigValue());
        //定时刊登时，需要获取保存到数据库中的ＩＤ
        item.setPayPalEmailAddress("caixu23@gmail.com");
        if (item.getListingType().equals("Chinese")) {
            AddItemRequest addItem = new AddItemRequest();
            addItem.setXmlns("urn:ebay:apis:eBLBaseComponents");
            addItem.setErrorLanguage("en_US");
            addItem.setWarningLevel("High");
            RequesterCredentials rc = new RequesterCredentials();
            rc.seteBayAuthToken(ua.getEbayToken());
            addItem.setRequesterCredentials(rc);
            addItem.setItem(item);
            xml = PojoXmlUtil.pojoToXml(addItem);
            xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + xml;
        } else {
            if("2".equals(tradingItem.getListingtype())){
                item.setStartPrice(null);
            }
            AddFixedPriceItemRequest addItem = new AddFixedPriceItemRequest();
            addItem.setXmlns("urn:ebay:apis:eBLBaseComponents");
            addItem.setErrorLanguage("en_US");
            addItem.setWarningLevel("High");
            RequesterCredentials rc = new RequesterCredentials();
            rc.seteBayAuthToken(ua.getEbayToken());
            addItem.setRequesterCredentials(rc);
            addItem.setItem(item);
            xml = PojoXmlUtil.pojoToXml(addItem);
            xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + xml;
        }
        System.out.println(xml);
        //Asserts.assertTrue(false, "错误");
        UsercontrollerDevAccountExtend d = new UsercontrollerDevAccountExtend();
        d.setApiSiteid(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(tradingItem.getSite())).getName1());
        if (item.getListingType().equals("Chinese")) {
            d.setApiCallName(APINameStatic.AddItem);
        } else {
            d.setApiCallName(APINameStatic.AddFixedPriceItem);
        }
        //String xml= BindAccountAPI.getSessionID(d.getRunname());

        AddApiTask addApiTask = new AddApiTask();

        Map<String, String> resMap = addApiTask.exec(d, xml, sandboxApiUrl);
        String r1 = resMap.get("stat");
        String res = resMap.get("message");
        if ("fail".equalsIgnoreCase(r1)) {
            AjaxSupport.sendFailText("fail", "提交数据不全，无法检查费用！");
        }
        String ack = SamplePaseXml.getVFromXmlString(res, "Ack");
        System.out.println("-----------------");
        System.out.println(res);
        System.out.println("-----------------");
        if ("Success".equalsIgnoreCase(ack) || "Warning".equalsIgnoreCase(ack)) {
            /*String itemId = SamplePaseXml.getVFromXmlString(res, "ItemID");
            tradingItem.setItemId(itemId);
            tradingItem.setIsFlag("Success");
            //this.iTradingItem.saveTradingItem(tradingItem);
            modelMap.put("tradingItem",tradingItem);*/
            Document document= SamplePaseXml.formatStr2Doc(res);
            Element rootElt = document.getRootElement();
            Element recommend = rootElt.element("Fees");
            Iterator<Element> iter = recommend.elementIterator("Fee");
            List<Map> lim = new ArrayList<Map>();
            while (iter.hasNext()){
                Element ment = iter.next();
                if(Double.parseDouble(ment.elementText("Fee"))>0){
                    Map m = new HashMap();
                    m.put("name",ment.elementText("Name"));
                    m.put("value",ment.elementText("Fee"));
                    lim.add(m);
                }
            }
            AjaxSupport.sendSuccessText("message", lim);
        } else {
            //String errors = SamplePaseXml.getVFromXmlString(res, "Errors");
            String errors  = SamplePaseXml.getSpecifyElementTextAllInOne(res,"Errors","LongMessage");
            AjaxSupport.sendFailText("fail", "检查ebay费失败:"+errors);
        }
    }

    /**
     * 列表界面,立即刊登
     * @param request
     * @param response
     * @param modelMap
     * @throws Exception
     */
    @RequestMapping("/ajax/listingItem.do")
    @ResponseBody
    public void listingItem(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        String idstr = request.getParameter("id");
        String [] ids = idstr.split(",");
        AddApiTask addApiTask = new AddApiTask();
        String xml = "";
        List<String> successmessage=new ArrayList<String>();
        List<String> errormessage=new ArrayList<String>();
        for(int i=0;i<ids.length;i++){
            TradingItemWithBLOBs tradingItem = this.iTradingItem.selectByIdBL(Long.parseLong(ids[i]));
            Item item = this.iTradingItem.toItem(tradingItem);
            UsercontrollerEbayAccount ua = this.iUsercontrollerEbayAccount.selectById(Long.parseLong(tradingItem.getEbayAccount()));
            if(tradingItem.getListingtype().equals("Chinese")){
                AddItemRequest addItem = new AddItemRequest();
                addItem.setXmlns("urn:ebay:apis:eBLBaseComponents");
                addItem.setErrorLanguage("en_US");
                addItem.setWarningLevel("High");
                RequesterCredentials rc = new RequesterCredentials();
                rc.seteBayAuthToken(ua.getEbayToken());
                addItem.setRequesterCredentials(rc);
                addItem.setItem(item);
                xml = PojoXmlUtil.pojoToXml(addItem);
                xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + xml;
            }else{
                AddFixedPriceItemRequest addItem = new AddFixedPriceItemRequest();
                addItem.setXmlns("urn:ebay:apis:eBLBaseComponents");
                addItem.setErrorLanguage("en_US");
                addItem.setWarningLevel("High");
                RequesterCredentials rc = new RequesterCredentials();
                rc.seteBayAuthToken(ua.getEbayToken());
                addItem.setRequesterCredentials(rc);
                addItem.setItem(item);
                xml = PojoXmlUtil.pojoToXml(addItem);
                xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + xml;
            }
            System.out.println(xml);

            UsercontrollerDevAccountExtend d = new UsercontrollerDevAccountExtend();
            d.setApiSiteid(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(tradingItem.getSite())).getName1());
            if (item.getListingType().equals("Chinese")) {
                d.setApiCallName(APINameStatic.AddItem);
            } else {
                d.setApiCallName(APINameStatic.AddFixedPriceItem);
            }
            //后台刊登
            /*TaskMessageVO taskMessageVO=new TaskMessageVO();
            taskMessageVO.setMessageContext("刊登");
            taskMessageVO.setMessageTitle("刊登操作");
            taskMessageVO.setMessageType(SiteMessageStatic.LISTING_MESSAGE_TYPE);
            taskMessageVO.setBeanNameType(SiteMessageStatic.LISTING_ITEM_BEAN);
            taskMessageVO.setMessageFrom("system");
            SessionVO sessionVO=SessionCacheSupport.getSessionVO();
            taskMessageVO.setMessageTo(sessionVO.getId());
            taskMessageVO.setObjClass(tradingItem);
            addApiTask.execDelayReturn(d, xml, apiUrl, taskMessageVO);*/
            //前台刊登
            Map<String, String> resMap= addApiTask.exec(d, xml, apiUrl);
            String res=resMap.get("message");
            String ack = SamplePaseXml.getVFromXmlString(res,"Ack");
            if(ack.equals("Success")||"Warning".equalsIgnoreCase(ack)){
                tradingItem.setIsFlag("Success");
                String itemId = SamplePaseXml.getVFromXmlString(res, "ItemID");
                tradingItem.setItemId(itemId);
                this.iTradingItem.saveTradingItem(tradingItem);
                this.iTradingItem.saveListingSuccess(res,itemId);
                //新增在线商品数据
                this.iTradingListingData.saveTradingListingDataByTradingItem(tradingItem,res);
                successmessage.add("商品SKU为："+tradingItem.getSku()+"，名称为：<a target=_blank style='color:blue' href='"+service_item_url+itemId+"'>"+tradingItem.getItemName()+"<a>，刊登成功！");
            }else{
                Document document= SamplePaseXml.formatStr2Doc(res);
                Element rootElt = document.getRootElement();
                Element tl = rootElt.element("Errors");
                String longMessage = tl.elementText("LongMessage");

                Iterator<Element> e =  rootElt.elementIterator("Errors");
                String errors = "";
                if(e!=null){
                    while (e.hasNext()){
                        Element es = e.next();
                        errors+=es.elementText("LongMessage")+"/n";
                    }
                }
                logger.error("商品SKU为："+tradingItem.getSku()+"，名称为："+tradingItem.getItemName()+"，刊登失败！失败原因："+errors);
                errormessage.add("商品SKU为："+tradingItem.getSku()+"，名称为："+tradingItem.getItemName()+"，刊登失败！失败原因："+errors);
            }
        }
        Map m = new HashMap();
        if(successmessage.size()>0){
            m.put("su",successmessage);
        }
        if(errormessage.size()>0){
            m.put("er",errormessage);
        }
        AjaxSupport.sendSuccessText("message", m);
    }

    /**
     * 删除范本
     * @param request
     * @param response
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping("/ajax/delItem.do")
    @AvoidDuplicateSubmission(needRemoveToken = true)
    @ResponseBody
    public void delItem(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        String id = request.getParameter("ids");
        String [] ids = id.split(",");
        if(ids!=null&&ids.length>0){
            try{
                this.iTradingItem.delItem(ids);
                for(String str:ids){
                    this.iTradingTimerListing.delTradingTimer(str);
                }
                AjaxSupport.sendSuccessText("",ids);
            }catch(Exception e){
                logger.error("删除失败!",e);
                AjaxSupport.sendSuccessText("","删除失败!");
            }

        }else {
            AjaxSupport.sendSuccessText("","请选择需要删除的范本!");
        }

    }

    /**
     * 重命名
     * @param request
     * @param response
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping("/ajax/rename.do")
    @ResponseBody
    public void rename(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        String id = request.getParameter("ids");
        String [] ids = id.split(",");
        String fileName = request.getParameter("fileName");
        if(ids!=null&&ids.length>0){
            try{
                this.iTradingItem.rename(ids,fileName);
                AjaxSupport.sendSuccessText("","操作成功!");
            }catch(Exception e){
                logger.error(id+"::",e);
                AjaxSupport.sendSuccessText("","删除失败!");
            }

        }else {
            AjaxSupport.sendSuccessText("","请选择需要删除的范本!");
        }
    }

    /**
     * 获取登录用户的Ｅbay账号
     * @param request
     * @param modelMap
     * @param commonParmVO
     * @throws Exception
     */
    @RequestMapping("/ajax/selfEbayAccount.do")
    @ResponseBody
    public void selfEbayAccount(HttpServletRequest request,ModelMap modelMap,CommonParmVO commonParmVO) throws Exception {
        SessionVO c= SessionCacheSupport.getSessionVO();
        List<UsercontrollerEbayAccountExtend> ebayList=systemUserManagerService.queryCurrAllEbay(new HashMap());
        AjaxSupport.sendSuccessText("",ebayList);
    }

    /**
     * 复制
     * @param request
     * @param response
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping("/ajax/copyItem.do")
    @ResponseBody
    public void copyItem(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        String id = request.getParameter("ids");
        String [] ids = id.split(",");
        String ebayaccount = request.getParameter("ebayaccount");
        if(ids!=null&&ids.length>0){
            try{
                this.iTradingItem.copyItem(ids,ebayaccount);
                AjaxSupport.sendSuccessText("","操作成功!");
            }catch(Exception e){
                logger.error("复制报错",e);
                AjaxSupport.sendSuccessText("","复制失败!");
            }

        }else {
            AjaxSupport.sendSuccessText("","请选择需要删除的范本!");
        }
    }

    /**
     * 得到分类名称
     * @param request
     * @param response
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping("/ajax/getCategoryName.do")
    @ResponseBody
    public void getCategoryName(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        String categoryId = request.getParameter("categoryId");
        String siteId = request.getParameter("siteId");
        selectNumber=0;
        String categoryName = this.selectBycriId(categoryId,siteId);
        AjaxSupport.sendSuccessText("",categoryName);
    }


    public String selectBycriId(String categoryId,String siteId){
        List<PublicDataDict> lipdd = this.iTradingDataDictionary.selectByDicExample(categoryId, siteId);
        if(lipdd==null||lipdd.size()==0){
            Asserts.assertTrue(false,"输入的分类错误！");
        }

        PublicDataDict pdd = lipdd.get(0);
        String categoryName = "";
        if(selectNumber>=5){
            return pdd.getItemEnName();
        }
        selectNumber++;
        if(!"0".equals(pdd.getItemParentId())&&!(pdd.getItemId().equals(pdd.getItemParentId()))){
            categoryName = this.selectBycriId(pdd.getItemParentId(),siteId)+" -> "+pdd.getItemEnName();
        }else{
            categoryName = pdd.getItemEnName();
        }
        return categoryName;
    }


    /**
     * 选择站点
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping("/selectSiteList.do")
    public ModelAndView selectSiteList(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
        String siteidStr = request.getParameter("siteidStr");
        List<TradingDataDictionary> lidata = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.DATA_DICT_SITE);
        modelMap.put("siteList",lidata);
        modelMap.put("siteidStr",siteidStr);
        return forword("item/selectSiteList",modelMap);
    }

    @RequestMapping("/editMoreItem.do")
    public ModelAndView editMoreItem(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception{
        String idsStr = request.getParameter("ids");
        String [] ids = idsStr.split(",");
        modelMap.put("idsStr",idsStr);
        TradingItem tradingItem = this.iTradingItem.selectById(Long.parseLong(ids[0]));
        Item item = this.iTradingItem.toEditItem(tradingItem);
        modelMap.put("sku",tradingItem.getSku());
        modelMap.put("ebayAccount",tradingItem.getEbayAccount());
        List<TradingPicturedetails> litp = this.iTradingPictureDetails.selectByParentId(tradingItem.getId());
        for(TradingPicturedetails tp : litp){
            List<TradingAttrMores> lipic = this.iTradingAttrMores.selectByParnetid(tp.getId(),"PictureURL");
            if(lipic!=null&&lipic.size()>0){
                modelMap.put("lipic",lipic);
            }
        }

        modelMap.put("item",item);
        modelMap.put("itemidstr",item.getItemID());
        SessionVO c= SessionCacheSupport.getSessionVO();
        List<PublicUserConfig> paypalList = DataDictionarySupport.getPublicUserConfigByType(DataDictionarySupport.PUBLIC_DATA_DICT_PAYPAL, c.getId());
        modelMap.put("paypalList",paypalList);

        List<TradingDataDictionary> lidata = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.DATA_DICT_SITE);
        modelMap.put("siteList",lidata);

        List<TradingDataDictionary> acceptList = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.RETURNS_ACCEPTED_OPTION);
        modelMap.put("acceptList",acceptList);

        List<TradingDataDictionary> withinList = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.RETURNS_WITHIN_OPTION);
        modelMap.put("withinList",withinList);

        List<TradingDataDictionary> refundList = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.REFUND_OPTION);
        modelMap.put("refundList",refundList);

        List<TradingDataDictionary> costPaidList = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.SHIPPING_COST_PAID);
        modelMap.put("costPaidList",costPaidList);

        List<TradingDataDictionary> lidatas = this.iTradingDataDictionary.selectDictionaryByType("country");
        modelMap.put("countryList",lidatas);
        Long siteid = 0L;
        for(TradingDataDictionary tdd : lidata){
            if(tdd.getValue().equals(item.getSite())){
                siteid=tdd.getId();
                break;
            }
        }
        modelMap.put("siteid",siteid);
        List<TradingDataDictionary> litype = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.DATA_DICT_SHIPPING_TYPE,siteid);
        List<TradingDataDictionary> li1 = new ArrayList<TradingDataDictionary>();
        List<TradingDataDictionary> li2 = new ArrayList<TradingDataDictionary>();
        List<TradingDataDictionary> li3 = new ArrayList<TradingDataDictionary>();
        List<TradingDataDictionary> li4 = new ArrayList<TradingDataDictionary>();
        List<TradingDataDictionary> li5 = new ArrayList<TradingDataDictionary>();
        for(TradingDataDictionary tdd:litype){
            if(tdd.getName1().equals("Economy services")){
                li1.add(tdd);
            }else if(tdd.getName1().equals("Expedited services")){
                li2.add(tdd);
            }else if(tdd.getName1().equals("One-day services")){
                li3.add(tdd);
            }else if(tdd.getName1().equals("Other services")){
                li4.add(tdd);
            }else if(tdd.getName1().equals("Standard services")){
                li5.add(tdd);
            }
        }
        modelMap.put("li1",li1);
        modelMap.put("li2",li2);
        modelMap.put("li3",li3);
        modelMap.put("li4",li4);
        modelMap.put("li5",li5);

        List<TradingDataDictionary> liinter = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.DATA_DICT_SHIPPINGINTER_TYPE);
        List<TradingDataDictionary> inter1 = new ArrayList();
        List<TradingDataDictionary> inter2 = new ArrayList();
        for(TradingDataDictionary tdd:liinter){
            if(tdd.getName1().equals("Expedited services")){
                inter1.add(tdd);
            }else if(tdd.getName1().equals("Other services")){
                inter2.add(tdd);
            }
        }
        modelMap.put("inter1",inter1);
        modelMap.put("inter2",inter2);

        List<TradingDataDictionary> lipackage = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.DATA_DICT_SHIPPINGPACKAGE);

        modelMap.put("lipackage",lipackage);

        List<PublicUserConfig> ebayList = DataDictionarySupport.getPublicUserConfigByType(DataDictionarySupport.PUBLIC_DATA_DICT_EBAYACCOUNT, c.getId());
        modelMap.put("ebayList",ebayList);
        return forword("item/editMoreItem",modelMap);
    }


    /**
     * 批量编辑修改
     * @param request
     * @param response
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping("/ajax/saveMoreItem.do")
    @ResponseBody
    public void saveMoreItem(Item item,TradingItem tradingItem,HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        String [] selectType = request.getParameterValues("selectType");
        String isUpdateFlag = request.getParameter("isUpdateFlag");
        String listingType = request.getParameter("listingType");
        String itemidStr = request.getParameter("ItemID");
        String idsStr = request.getParameter("idsStr");
        String [] itemid = itemidStr.split(",");
        String [] ids = idsStr.split(",");

        for(int i=0;i<ids.length;i++){
            TradingItemWithBLOBs tradingItem1=this.iTradingItem.selectByIdBL(Long.parseLong(ids[i]));

            if("1".equals(isUpdateFlag)&&tradingItem1.getItemId()!=null){//需要更新在线商
                item.setItemID(tradingItem1.getItemId());
                //TradingListingData tld = this.iTradingListingData.selectByItemid(item.getItemID());
                UsercontrollerEbayAccount uea = this.iUsercontrollerEbayAccount.selectById(Long.parseLong(tradingItem1.getEbayAccount()));
                Item ite = new Item();
                List litla = new ArrayList();
                ReviseItemRequest rir = new ReviseItemRequest();
                rir.setXmlns("urn:ebay:apis:eBLBaseComponents");
                rir.setErrorLanguage("en_US");
                rir.setWarningLevel("High");
                RequesterCredentials rc = new RequesterCredentials();
                String ebayAccount = uea.getEbayAccount();
                SessionVO c= SessionCacheSupport.getSessionVO();
                String token =uea.getEbayToken();
                rc.seteBayAuthToken(token);
                rir.setRequesterCredentials(rc);
                ite.setItemID(item.getItemID());
                for(String str : selectType){
                    TradingListingAmendWithBLOBs tla = new TradingListingAmendWithBLOBs();
                    tla.setItem(Long.parseLong(tradingItem1.getItemId()));
                    tla.setParentId(tradingItem1.getId());
                    if(str.equals("StartPrice")){//改价格
                        tla.setAmendType("StartPrice");
                        if("FixedPriceItem".equals(listingType)) {
                            ite.setStartPrice(item.getStartPrice());
                            tla.setContent("将价格从" + tradingItem1.getStartprice() + "修改为" + item.getStartPrice().getValue());
                            tradingItem1.setStartprice(item.getStartPrice().getValue());
                            Item ites = new Item();
                            ites.setItemID(item.getItemID());
                            ites.setStartPrice(item.getStartPrice());
                            rir.setItem(ites);
                            tla.setCosxml("<?xml version=\"1.0\" encoding=\"utf-8\"?>"+PojoXmlUtil.pojoToXml(rir));
                        }else if("2".equals(listingType)){
                            tla.setContent("多属性价格调整！");
                            ite.setVariations(item.getVariations());
                            Item ites = new Item();
                            ites.setItemID(item.getItemID());
                            ites.setVariations(item.getVariations());
                            rir.setItem(ites);
                            tla.setCosxml("<?xml version=\"1.0\" encoding=\"utf-8\"?>"+PojoXmlUtil.pojoToXml(rir));
                        }else if("Chinese".equals(listingType)){
                            tla.setContent("将价格从" + tradingItem1.getStartprice() + "修改为" + item.getStartPrice().getValue());
                            tradingItem1.setStartprice(item.getStartPrice().getValue());
                            item.setBuyItNowPrice(item.getStartPrice().getValue());
                            Item ites = new Item();
                            ites.setItemID(item.getItemID());
                            ites.setBuyItNowPrice(item.getBuyItNowPrice());
                            rir.setItem(ites);
                            tla.setCosxml("<?xml version=\"1.0\" encoding=\"utf-8\"?>"+PojoXmlUtil.pojoToXml(rir));
                        }

                    }else if(str.equals("Quantity")){//改数量
                        tla.setAmendType("Quantity");
                        tla.setContent("将数量从" + tradingItem1.getQuantity() + "修改为" + item.getQuantity());
                        ite.setQuantity(item.getQuantity());
                        tradingItem1.setQuantity(item.getQuantity().longValue());
                        Item ites = new Item();
                        ites.setItemID(item.getItemID());
                        ites.setQuantity(item.getQuantity());
                        rir.setItem(ites);
                        tla.setCosxml("<?xml version=\"1.0\" encoding=\"utf-8\"?>"+PojoXmlUtil.pojoToXml(rir));
                    }else if(str.equals("PictureDetails")){//改图片
                        tla.setAmendType("PictureDetails");
                        tla.setContent("图片修改");

                        Item ites = new Item();
                        String [] pics = request.getParameterValues("PictureDetails_"+item.getItemID()+".PictureURL");
                        PictureDetails pd = new PictureDetails();
                        pd.setGalleryType("Gallery");
                        pd.setGalleryURL(pics[0]);
                        pd.setPhotoDisplay("PicturePack");
                        List<String> listr = new ArrayList<String>();
                        for(String pic:pics){
                            String mackid = EncryptionUtil.md5Encrypt(pic);
                            List<TradingListingpicUrl> litamss = this.iTradingListingPicUrl.selectByMackId(mackid);
                            if(litamss==null||litamss.size()==0){
                                listr.add(pic);
                            }else {
                                TradingListingpicUrl tam = litamss.get(0);
                                listr.add(tam.getEbayurl());
                            }
                        }
                        pd.setPictureURL(listr);
                        ite.setPictureDetails(pd);
                        ites.setPictureDetails(pd);
                        ites.setItemID(item.getItemID());
                        rir.setItem(ites);
                        tla.setCosxml("<?xml version=\"1.0\" encoding=\"utf-8\"?>"+PojoXmlUtil.pojoToXml(rir));
                    }else if(str.equals("PayPal")){//改支付方式
                        tla.setAmendType("PayPal");
                        tla.setContent("支付方式修改");
                        ite.setPayPalEmailAddress(item.getPayPalEmailAddress());
                        Item ites = new Item();
                        ites.setItemID(item.getItemID());
                        ites.setPayPalEmailAddress(item.getPayPalEmailAddress());
                        rir.setItem(ites);
                        tla.setCosxml("<?xml version=\"1.0\" encoding=\"utf-8\"?>"+PojoXmlUtil.pojoToXml(rir));
                    }else if(str.equals("ReturnPolicy")){//改退货政策
                        tla.setAmendType("ReturnPolicy");
                        tla.setContent("退货政策修改");
                        ite.setReturnPolicy(item.getReturnPolicy());
                        Item ites = new Item();
                        ites.setItemID(item.getItemID());
                        ites.setReturnPolicy(item.getReturnPolicy());
                        rir.setItem(ites);
                        tla.setCosxml("<?xml version=\"1.0\" encoding=\"utf-8\"?>"+PojoXmlUtil.pojoToXml(rir));
                    }else if(str.equals("Title")){//改标题　
                        tla.setAmendType("Title");
                        tla.setContent("标题修改为："+item.getTitle());
                        ite.setTitle(item.getTitle());
                        tradingItem1.setTitle(item.getTitle());
                        Item ites = new Item();
                        ites.setItemID(item.getItemID());
                        ites.setTitle(item.getTitle());
                        rir.setItem(ites);
                        tla.setCosxml("<?xml version=\"1.0\" encoding=\"utf-8\"?>"+PojoXmlUtil.pojoToXml(rir));
                    }else if(str.equals("Buyer")){//改买家要求
                        tla.setAmendType("Buyer");
                        tla.setContent("修改买家要求");
                        ite.setBuyerRequirementDetails(item.getBuyerRequirementDetails());
                        Item ites = new Item();
                        ites.setItemID(item.getItemID());
                        ites.setBuyerRequirementDetails(item.getBuyerRequirementDetails());
                        rir.setItem(ites);
                        tla.setCosxml("<?xml version=\"1.0\" encoding=\"utf-8\"?>"+PojoXmlUtil.pojoToXml(rir));
                    }else if(str.equals("SKU")){//改ＳＫＵ
                        tla.setAmendType("SKU");
                        tla.setContent("SKU修改为："+item.getSKU());
                        ite.setSKU(item.getSKU());
                        tradingItem1.setSku(item.getSKU());
                        Item ites = new Item();
                        ites.setItemID(item.getItemID());
                        ites.setSKU(item.getSKU());
                        rir.setItem(ites);
                        tla.setCosxml("<?xml version=\"1.0\" encoding=\"utf-8\"?>"+PojoXmlUtil.pojoToXml(rir));
                    }else if(str.equals("PrimaryCategory")){//改分类
                        tla.setAmendType("PrimaryCategory");
                        tla.setContent("商品分类修改为:"+item.getPrimaryCategory().getCategoryID());
                        ite.setPrimaryCategory(item.getPrimaryCategory());
                        Item ites = new Item();
                        ites.setItemID(item.getItemID());
                        ites.setPrimaryCategory(item.getPrimaryCategory());
                        rir.setItem(ites);
                        tla.setCosxml("<?xml version=\"1.0\" encoding=\"utf-8\"?>"+PojoXmlUtil.pojoToXml(rir));
                    }else if(str.equals("ConditionID")){//改商品状态
                        tla.setAmendType("ConditionID");
                        tla.setContent("修改商品状态");
                        ite.setConditionID(item.getConditionID());
                        Item ites = new Item();
                        ites.setItemID(item.getItemID());
                        ites.setConditionID(item.getConditionID());
                        rir.setItem(ites);
                        tla.setCosxml("<?xml version=\"1.0\" encoding=\"utf-8\"?>"+PojoXmlUtil.pojoToXml(rir));
                    }else if(str.equals("Location")){//改运输到的地址
                        tla.setAmendType("Location");
                        ite.setLocation(item.getLocation());
                        Item ites = new Item();
                        ites.setItemID(item.getItemID());
                        ites.setLocation(item.getLocation());
                        rir.setItem(ites);
                        tla.setCosxml("<?xml version=\"1.0\" encoding=\"utf-8\"?>"+PojoXmlUtil.pojoToXml(rir));
                    }else if(str.equals("DispatchTimeMax")){//最快处理时间
                        tla.setAmendType("DispatchTimeMax");
                        tla.setContent("修改处理时间");
                        ite.setDispatchTimeMax(item.getDispatchTimeMax());
                        Item ites = new Item();
                        ites.setItemID(item.getItemID());
                        ites.setDispatchTimeMax(item.getDispatchTimeMax());
                        rir.setItem(ites);
                        tla.setCosxml("<?xml version=\"1.0\" encoding=\"utf-8\"?>"+PojoXmlUtil.pojoToXml(rir));
                    }else if(str.equals("PrivateListing")){//改是否允许私人买
                        tla.setAmendType("PrivateListing");
                        ite.setPrivateListing(item.getPrivateListing());
                        Item ites = new Item();
                        ites.setItemID(item.getItemID());
                        ites.setPrivateListing(item.getPrivateListing());
                        rir.setItem(ites);
                        tla.setCosxml("<?xml version=\"1.0\" encoding=\"utf-8\"?>"+PojoXmlUtil.pojoToXml(rir));
                    }else if(str.equals("ListingDuration")){//改刊登天数
                        tla.setAmendType("ListingDuration");
                        tla.setContent("修改刊登天数为："+item.getListingDuration());
                        ite.setListingDuration(item.getListingDuration());
                        Item ites = new Item();
                        ites.setItemID(item.getItemID());
                        ites.setListingDuration(item.getListingDuration());
                        rir.setItem(ites);
                        tla.setCosxml("<?xml version=\"1.0\" encoding=\"utf-8\"?>"+PojoXmlUtil.pojoToXml(rir));
                    }else if(str.equals("Description")){//改商品描述
                        tla.setContent("修改商品描述");
                        tla.setAmendType("Description");
                        ite.setDescription(item.getDescription());
                        Item ites = new Item();
                        ites.setItemID(item.getItemID());
                        ites.setDescription(item.getDescription());
                        rir.setItem(ites);
                        tla.setCosxml("<?xml version=\"1.0\" encoding=\"utf-8\"?>"+PojoXmlUtil.pojoToXml(rir));
                    }else if(str.equals("ShippingDetails")){//改运输详情
                        tla.setAmendType("ShippingDetails");
                        tla.setContent("修改运输详情");
                        ShippingDetails sdf = item.getShippingDetails();
                        String nottoLocation = request.getParameter("notLocationValue");
                        if(!ObjectUtils.isLogicalNull(nottoLocation)){
                            String noLocation[] =nottoLocation.split(",");
                            List listr = new ArrayList();
                            for(String nostr : noLocation){
                                listr.add(nostr);
                            }
                            sdf.setExcludeShipToLocation(listr);
                        }
                        ite.setShippingDetails(sdf);
                        Item ites = new Item();
                        ites.setItemID(item.getItemID());
                        ites.setShippingDetails(item.getShippingDetails());
                        rir.setItem(ites);
                        tla.setCosxml("<?xml version=\"1.0\" encoding=\"utf-8\"?>"+PojoXmlUtil.pojoToXml(rir));
                    }
                    litla.add(tla);
                }

                String xml = "";
                rir.setItem(ite);
                xml = PojoXmlUtil.pojoToXml(rir);
                xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"+xml;
                System.out.println(xml);

                String returnString = this.cosPostXml(xml,APINameStatic.ReviseItem);
                String ack = SamplePaseXml.getVFromXmlString(returnString,"Ack");
                if("Success".equalsIgnoreCase(ack)||"Warning".equalsIgnoreCase(ack)){
                    this.saveAmend(litla, "1");
                }else{
                    this.saveAmend(litla,"0");
                    Document document= SamplePaseXml.formatStr2Doc(returnString);
                    Element rootElt = document.getRootElement();
                    Element tl = rootElt.element("Errors");
                    String longMessage = tl.elementText("LongMessage");
                    if(longMessage==null){
                        longMessage = tl.elementText("ShortMessage");
                    }
                    this.saveSystemLog(longMessage,"在线修改商品报错",SiteMessageStatic.LISTING_DATA_UPDATE);
                    AjaxSupport.sendFailText("fail",longMessage);
                }
            }
            if(tradingItem1!=null){//更新数据库中的范本
                this.iTradingItem.updateTradingItem(item,tradingItem1);
            }
        }
        AjaxSupport.sendSuccessText("message", "操作成功！");
    }


    public String cosPostXml(String colStr,String month) throws Exception {
        UsercontrollerDevAccountExtend d = userInfoService.getDevInfo(1L);
        d.setApiSiteid("0");
        d.setApiCallName(month);
        AddApiTask addApiTask = new AddApiTask();
        Map<String, String> resMap= addApiTask.exec(d, colStr, apiUrl);
        String res=resMap.get("message");
        String ack = SamplePaseXml.getVFromXmlString(res,"Ack");
        if(ack.equals("Success")||"Warning".equalsIgnoreCase(ack)){
            return res;
        }else{
            return res;
        }

    }

    public void saveAmend(List<TradingListingAmendWithBLOBs> litlam,String isflag) throws Exception {
        for(TradingListingAmendWithBLOBs tla : litlam){
            ObjectUtils.toInitPojoForInsert(tla);
            tla.setIsFlag(isflag);
            this.iTradingListingData.insertTradingListingAmend(tla);
        }
    }

    public void saveSystemLog(String context,String title,String type){
        SiteMessageService siteMessageService= (SiteMessageService) ApplicationContextUtil.getBean(SiteMessageService.class);
        TaskMessageVO taskMessageVO=new TaskMessageVO();
        taskMessageVO.setMessageContext(context);
        taskMessageVO.setMessageTitle(title);
        taskMessageVO.setMessageType(type);
        taskMessageVO.setMessageFrom("system");
        SessionVO sessionVO=SessionCacheSupport.getSessionVO();
        taskMessageVO.setMessageTo(sessionVO.getId());
        taskMessageVO.setObjClass(null);
        siteMessageService.addSiteMessage(taskMessageVO);
    }

    /**
     * 取消定时任务
     * @param request
     * @param response
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping("/ajax/delTradingTimer.do")
    @ResponseBody
    public void delTradingTimer(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        String itemids = request.getParameter("itemids");
        String [] itemid = itemids.split(",");
        for(String id:itemid){
            this.iTradingTimerListing.delTradingTimer(id);
            TradingItemWithBLOBs tradingItemWithBLOBs = this.iTradingItem.selectByIdBL(Long.parseLong(id));
            tradingItemWithBLOBs.setListingWay("0");
            this.iTradingItem.saveTradingItem(tradingItemWithBLOBs);
        }
        AjaxSupport.sendSuccessText("message", "操作成功！");
    }

    /**
     * 添加备注
     * @param request
     * @param response
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping("/ajax/addItemRemark.do")
    @ResponseBody
    public void addItemRemark(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        String id = request.getParameter("id");
        String [] ids = id.split(",");
        String remark = request.getParameter("remark");
        if(remark==null||"".equals(remark)){
            remark="";
        }else {
            remark = new String(remark.getBytes("ISO-8859-1"), "UTF-8");
        }
        List<TradingItem> litld = new ArrayList<TradingItem>();
        for(int i=0;i<ids.length;i++) {
            TradingItemWithBLOBs tradingItem = this.iTradingItem.selectByIdBL(Long.parseLong(ids[i]));
            if(tradingItem!=null){
                tradingItem.setRemark(remark);
                this.iTradingItem.saveTradingItem(tradingItem);
                litld.add(tradingItem);
            }
        }
        AjaxSupport.sendSuccessText("",litld);
    }

    @RequestMapping("/ajax/timerListingItem.do")
    @ResponseBody
    public void timerListingItem(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        String idstr = request.getParameter("id");
        String timerStr = request.getParameter("timerStr");
        String [] ids = idstr.split(",");
        String xml = "";
        List<Map> lim = new ArrayList();
        for(int i=0;i<ids.length;i++){
            TradingItemWithBLOBs tradingItem = this.iTradingItem.selectByIdBL(Long.parseLong(ids[i]));
            Item item = this.iTradingItem.toItem(tradingItem);
            UsercontrollerEbayAccount ua = this.iUsercontrollerEbayAccount.selectById(Long.parseLong(tradingItem.getEbayAccount()));
            if(tradingItem.getListingtype().equals("Chinese")){
                AddItemRequest addItem = new AddItemRequest();
                addItem.setXmlns("urn:ebay:apis:eBLBaseComponents");
                addItem.setErrorLanguage("en_US");
                addItem.setWarningLevel("High");
                RequesterCredentials rc = new RequesterCredentials();
                rc.seteBayAuthToken(ua.getEbayToken());
                addItem.setRequesterCredentials(rc);
                addItem.setItem(item);
                xml = PojoXmlUtil.pojoToXml(addItem);
                xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + xml;
            }else{
                AddFixedPriceItemRequest addItem = new AddFixedPriceItemRequest();
                addItem.setXmlns("urn:ebay:apis:eBLBaseComponents");
                addItem.setErrorLanguage("en_US");
                addItem.setWarningLevel("High");
                RequesterCredentials rc = new RequesterCredentials();
                rc.seteBayAuthToken(ua.getEbayToken());
                addItem.setRequesterCredentials(rc);
                addItem.setItem(item);
                xml = PojoXmlUtil.pojoToXml(addItem);
                xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + xml;
            }
            tradingItem.setListingWay("1");
            this.iTradingItem.saveTradingItem(tradingItem);

            System.out.println(xml);
            TradingTimerListingWithBLOBs ttl = new TradingTimerListingWithBLOBs();
            ttl.setItem(tradingItem.getId());
            ttl.setTimer(DateUtils.parseDateTime(timerStr));
            ttl.setTimerMessage(StringEscapeUtils.escapeXml(xml));
            if (item.getListingType().equals("Chinese")) {
                ttl.setApiMethod(APINameStatic.AddItem);
            } else {
                ttl.setApiMethod(APINameStatic.AddFixedPriceItem);
            }
            ttl.setEbayId(tradingItem.getEbayAccount());
            ttl.setStateId(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(tradingItem.getSite())).getName1());
            this.iTradingTimerListing.saveTradingTimer(ttl);
            Map m = new HashMap();
            m.put("id",tradingItem.getId());
            m.put("sku",tradingItem.getSku());
            lim.add(m);
        }
        AjaxSupport.sendSuccessText("message", lim);
    }
}
