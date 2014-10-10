package com.item.controller;

import com.base.aboutpaypal.service.PayPalService;
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
import com.base.utils.imageManage.service.ImageService;
import com.base.utils.threadpool.AddApiTask;
import com.base.utils.threadpool.ApiCallable;
import com.base.utils.threadpool.TaskMessageVO;
import com.base.utils.threadpool.TaskPool;
import com.base.utils.xmlutils.PojoXmlUtil;
import com.base.utils.xmlutils.SamplePaseXml;
import com.base.xmlpojo.trading.addproduct.*;
import com.base.xmlpojo.trading.addproduct.attrclass.StartPrice;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
import com.sitemessage.service.SiteMessageStatic;
import com.trading.service.*;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.util.concurrent.ListenableFutureTask;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
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
        List<UsercontrollerEbayAccount> ebayList = this.iUsercontrollerEbayAccount.selectUsercontrollerEbayAccountByUserId(c.getId());
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

        List<TradingItem> litld = new ArrayList<TradingItem>();
        for(String id: ids){
            TradingItem tld = this.iTradingItem.selectById(Long.parseLong(id));
            tld.setFolderId(folderid);
            litld.add(tld);
        }
        if(litld.size()>0) {
            try {
                this.iTradingItem.saveTradingItemList(litld);
                AjaxSupport.sendSuccessText("","操作成功!");
            } catch (Exception e) {
                e.printStackTrace();
                AjaxSupport.sendSuccessText("","操作失败!");
            }
        }else{
            AjaxSupport.sendSuccessText("","操作失败，你未选择商品，或你选择的商品不存在!");
        }

    }

    /**刊登主页面*/
    @RequestMapping("/addItem.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView addItem(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
        List<TradingDataDictionary> lidata = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.DATA_DICT_SITE);
        modelMap.put("siteList",lidata);

        SessionVO c= SessionCacheSupport.getSessionVO();
        //List<PublicUserConfig> ebayList = DataDictionarySupport.getPublicUserConfigByType(DataDictionarySupport.PUBLIC_DATA_DICT_PAYPAL, c.getId());
        List<UsercontrollerEbayAccount> ebayList = this.iUsercontrollerEbayAccount.selectUsercontrollerEbayAccountByUserId(c.getId());
        modelMap.put("ebayList",ebayList);
        modelMap.put("imageUrlPrefix",imageService.getImageUrlPrefix());
        return forword("item/addItem",modelMap);
    }

    @RequestMapping("/editItem.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView editItem(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        String id = request.getParameter("id");
        List<TradingDataDictionary> lidata = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.DATA_DICT_SITE);
        modelMap.put("siteList",lidata);


        TradingItem ti = null;
        if(id!=null&&!"".equals(id)){
            ti = this.iTradingItem.selectById(Long.parseLong(id));
        }
        modelMap.put("item",ti);

        SessionVO c= SessionCacheSupport.getSessionVO();
        //List<PublicUserConfig> ebayList = DataDictionarySupport.getPublicUserConfigByType(DataDictionarySupport.PUBLIC_DATA_DICT_PAYPAL, c.getId());
        UsercontrollerEbayAccount ebay = this.iUsercontrollerEbayAccount.selectById(Long.parseLong(ti.getEbayAccount().toString()));
        List<UsercontrollerEbayAccount> ebayList = new ArrayList();
        ebayList.add(ebay);
        modelMap.put("ebayList",ebayList);

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
            if(tpes!=null) {
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
        modelMap.put("ttit",ttit);
        modelMap.put("imageUrlPrefix",imageService.getImageUrlPrefix());
        return forword("item/addItem",modelMap);
    }

    /**
     * 刊登商品
     * @param request
     * @throws Exception
     */
    @RequestMapping("/saveItem.do")
    @AvoidDuplicateSubmission(needRemoveToken = true)
    @ResponseBody
    public void saveItem(HttpServletRequest request,Item item,TradingItem tradingItem,Date timerListing) throws Exception {

        String mouth = request.getParameter("dataMouth");
        if("save".equals(mouth)){
            //保存商品信息到数据库中
            this.iTradingItem.saveItem(item,tradingItem);
            AjaxSupport.sendSuccessText("message", "操作成功！");
        }else{
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

            if(request.getParameter("SecondaryCategory.CategoryID")==null||"".equals(request.getParameter("SecondaryCategory.CategoryID"))){
                item.setSecondaryCategory(null);
            }
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

                    UsercontrollerEbayAccount ua = this.iUsercontrollerEbayAccount.selectById(Long.parseLong(paypal));
                    PublicUserConfig pUserConfig = DataDictionarySupport.getPublicUserConfigByID(ua.getPaypalAccountId());
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
                    ttl.setTimerMessage(xml);
                    if (item.getListingType().equals("Chinese")) {
                        ttl.setApiMethod(APINameStatic.AddItem);
                    } else {
                        ttl.setApiMethod(APINameStatic.AddFixedPriceItem);
                    }
                    ttl.setEbayId(paypal);
                    ttl.setStateId(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(tradingItem.getSite())).getName1());
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

                    if("1".equals(request.getParameter("ListingMessage"))){//如果是延迟通知
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
                    }


                }
            }
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


    @RequestMapping("/ajax/listingItem.do")
    @ResponseBody
    public void listingItem(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        String idstr = request.getParameter("id");
        String [] ids = idstr.split(",");
        AddApiTask addApiTask = new AddApiTask();
        String xml = "";
        for(int i=0;i<ids.length;i++){
            TradingItem tradingItem = this.iTradingItem.selectById(Long.parseLong(ids[i]));
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
        }
        AjaxSupport.sendSuccessText("message", "操作成功！结果请稍后查看消息！");
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
                AjaxSupport.sendSuccessText("","操作成功!");
            }catch(Exception e){
                e.printStackTrace();
                AjaxSupport.sendSuccessText("","删除失败!");
            }

        }else {
            AjaxSupport.sendSuccessText("","请选择需要删除的范本!");
        }

    }

    /**
     * 保存数据
     * @param request
     * @param response
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping("/ajax/rename.do")
    @AvoidDuplicateSubmission(needRemoveToken = true)
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
                e.printStackTrace();
                AjaxSupport.sendSuccessText("","删除失败!");
            }

        }else {
            AjaxSupport.sendSuccessText("","请选择需要删除的范本!");
        }

    }
}
