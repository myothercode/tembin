package com.module.controller;

import com.base.database.publicd.model.PublicUserConfig;
import com.base.database.trading.model.*;
import com.base.domains.CommonParmVO;
import com.base.domains.SessionVO;
import com.base.domains.querypojos.BuyerRequirementDetailsQuery;
import com.base.domains.querypojos.PaypalQuery;
import com.base.domains.querypojos.ShippingdetailsQuery;
import com.base.domains.userinfo.UsercontrollerEbayAccountExtend;
import com.base.domains.userinfo.UsercontrollerUserExtend;
import com.base.mybatis.page.Page;
import com.base.mybatis.page.PageJsonBean;
import com.base.userinfo.service.SystemUserManagerService;
import com.base.userinfo.service.impl.SystemUserManagerServiceImpl;
import com.base.utils.annotations.AvoidDuplicateSubmission;
import com.base.utils.cache.DataDictionarySupport;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.common.ConvertPOJOUtil;
import com.base.utils.common.ObjectUtils;
import com.base.utils.xmlutils.PojoXmlUtil;
import com.base.xmlpojo.trading.addproduct.CalculatedShippingRate;
import com.base.xmlpojo.trading.addproduct.InternationalShippingServiceOption;
import com.base.xmlpojo.trading.addproduct.ShippingDetails;
import com.base.xmlpojo.trading.addproduct.ShippingServiceOptions;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
import com.trading.service.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/7/30.
 */
@Controller
public class ShippingDetailsController extends BaseAction{

    @Autowired
    private ITradingShippingDetails iTradingShippingDetails;

    @Autowired
    private ITradingAttrMores iTradingAttrMores;

    @Autowired
    private ITradingShippingServiceOptions iTradingShippingServiceOptions;

    @Autowired
    private ITradingInternationalShippingServiceOption iTradingInternationalShippingServiceOption;
    @Autowired
    private IUsercontrollerEbayAccount iUsercontrollerEbayAccount;
    @Autowired
    private SystemUserManagerService systemUserManagerService;
    @Autowired
    private ITradingShippingServiceOptionsDoc iTradingShippingServiceOptionsDoc;
    @Autowired
    private ITradingInternationalShippingServiceOptionDoc iTradingInternationalShippingServiceOptionDoc;
    @Autowired
    private ITradingItem iTradingItem;

    /**
     * 查询数据并展示
     * @param modelMap
     * @param commonParmVO
     * @return
     */
    @RequestMapping("/ajax/loadShippingDetailsList.do")
    @ResponseBody
    public void loadShippingDetailsList(HttpServletRequest request,ModelMap modelMap,CommonParmVO commonParmVO) throws Exception {
        String docId = request.getParameter("docId");
        SessionVO c= SessionCacheSupport.getSessionVO();
        Map m = new HashMap();
        if(systemUserManagerService.isAdminRole()){
            List<UsercontrollerUserExtend> liuue = systemUserManagerService.queryAllUsersByOrgID("yes");
            List<String> liue = new ArrayList<String>();
            for(UsercontrollerUserExtend uue:liuue){
                liue.add(uue.getUserId()+"");
            }
            liue.add(c.getId()+"");
            m.put("liue",liue);
        }else{
            m.put("userid",c.getId());
        }
        String checkFlag = request.getParameter("checkFlag");
        m.put("checkFlag",checkFlag);
        TradingItem tradingItem= null;
        if(StringUtils.isNotEmpty(docId)){
            tradingItem=this.iTradingItem.selectById(Long.parseLong(docId));
        }
        /**分页组装*/
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        Page page=jsonBean.toPage();
        List<ShippingdetailsQuery> lisq = this.iTradingShippingDetails.selectByShippingdetailsQuery(m,page);
        List<TradingShippingserviceoptionsDoc> litsd = null;
        List<TradingInternationalshippingserviceoptionDoc> liinter = null;
        if(StringUtils.isNotEmpty(docId)) {
            litsd = this.iTradingShippingServiceOptionsDoc.slectByParentIdDocId(Long.parseLong(docId));
            liinter = this.iTradingInternationalShippingServiceOptionDoc.selectByParentIdDocId(Long.parseLong(docId));
        }
        for(ShippingdetailsQuery sq:lisq){
            if(StringUtils.isNotEmpty(docId)&&sq.getId().equals(tradingItem.getShippingDeailsId())&&(litsd.size()>0||liinter.size()>0)){
                //国内运输选项
                List<TradingShippingserviceoptions> lits = new ArrayList<TradingShippingserviceoptions>();
                for(TradingShippingserviceoptionsDoc tsd:litsd){
                    TradingShippingserviceoptions ts = new TradingShippingserviceoptions();
                    if(tsd.getShippingservice()!=null){
                        TradingDataDictionary tdds = DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(tsd.getShippingservice()));
                        if(tdds!=null){
                            ts.setOptionId(Long.parseLong(tsd.getShippingservice()));
                            tsd.setShippingservice(tdds.getName());
                        }
                    }
                    ConvertPOJOUtil.convert(ts,tsd);
                    ts.setId(tsd.getSourceId());
                    lits.add(ts);
                }
                sq.setLits(lits);
                //国际运输
                List<TradingInternationalshippingserviceoption> liin = new ArrayList<TradingInternationalshippingserviceoption>();
                for(TradingInternationalshippingserviceoptionDoc inter:liinter){
                    TradingInternationalshippingserviceoption ins = new TradingInternationalshippingserviceoption();
                    if(inter.getShippingservice()!=null){
                        TradingDataDictionary tdds = DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(inter.getShippingservice()));
                        if(tdds!=null){
                            ins.setOptionId(Long.parseLong(inter.getShippingservice()));
                            inter.setShippingservice(tdds.getName());
                        }
                    }
                    ConvertPOJOUtil.convert(ins,inter);
                    ins.setId(inter.getSourceId());
                    liin.add(ins);
                }
                sq.setLiti(liin);
            }else{
                List<TradingShippingserviceoptions> lits = this.iTradingShippingServiceOptions.selectByParentId(sq.getId());
                for (TradingShippingserviceoptions ts : lits) {
                    if (ts.getShippingservice() != null) {
                        TradingDataDictionary tdds = DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(ts.getShippingservice()));
                        if (tdds != null) {
                            ts.setOptionId(Long.parseLong(ts.getShippingservice()));
                            ts.setShippingservice(tdds.getName());
                        }
                    }
                }
                sq.setLits(lits);
                List<TradingInternationalshippingserviceoption> liin = this.iTradingInternationalShippingServiceOption.selectByParentid(sq.getId());
                for (TradingInternationalshippingserviceoption in : liin) {
                    if (in.getShippingservice() != null) {
                        TradingDataDictionary tdds = DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(in.getShippingservice()));
                        if (tdds != null) {
                            in.setOptionId(Long.parseLong(in.getShippingservice()));
                            in.setShippingservice(tdds.getName());
                        }
                    }
                }
                sq.setLiti(liin);
            }
        }
        jsonBean.setList(lisq);
        jsonBean.setTotal((int)page.getTotalCount());
        AjaxSupport.sendSuccessText("",jsonBean);
    }

    /**
     * 查询列表跳转
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping("/shippingDetailsList.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView shippingDetailsList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        return forword("module/shipping/shippingDetailsList",modelMap);
    }

    /**
     * 查询数据并展示
     * @param modelMap
     * @param request
     * @return
     */
    @RequestMapping("/ajax/shipingService.do")
    @ResponseBody
    public void shipingService(ModelMap modelMap,HttpServletRequest request){
        List<TradingDataDictionary> litype = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.DATA_DICT_SHIPPING_TYPE,Long.parseLong(request.getParameter("parentID")));

        List<Map<String,List<TradingDataDictionary>>> maps = new ArrayList<Map<String, List<TradingDataDictionary>>>();

        for(TradingDataDictionary tdd: litype){
            Map<String,List<TradingDataDictionary>> b= xx(maps,tdd.getName1());
            if(b==null){
                Map<String, List<TradingDataDictionary>> map=new HashMap<String, List<TradingDataDictionary>>();
                List<TradingDataDictionary> list = new ArrayList<TradingDataDictionary>();
                list.add(tdd);
                map.put(tdd.getName1(),list);
                maps.add(map);
            }else {
                List<TradingDataDictionary> list = b.get(tdd.getName1());
                list.add(tdd);
            }
        }

        List<List<TradingDataDictionary>> lll=new ArrayList<List<TradingDataDictionary>>();


        List<ShippingDetailsSSVO> x=new ArrayList<ShippingDetailsSSVO>();


        for (Map<String,List<TradingDataDictionary>> ma : maps){
            for (Map.Entry e : ma.entrySet()){
                List<TradingDataDictionary> f= (List<TradingDataDictionary>) e.getValue();
                ShippingDetailsSSVO d=new ShippingDetailsSSVO();
                d.setDictionaries(f);
                d.setName1(f.get(0).getName1());
                x.add(d);
            }

        }


        AjaxSupport.sendSuccessText("",x);
    }

    private Map<String,List<TradingDataDictionary>> xx( List<Map<String,List<TradingDataDictionary>>> maps,String key){
        for (Map map :maps){
             if(map.get(key)!=null){
                 return map;
             }
        }
        return null;

    }


    /**
     * 新增页面跳转
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping("/addshippingDetails.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView addshippingDetails(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
        /*Map m = new HashMap();
        List<BuyerRequirementDetailsQuery> li = this.iTradingBuyerRequirementDetails.selectTradingBuyerRequirementDetailsByList(m);
        modelMap.put("li",li);*/
        List<TradingDataDictionary> lidata = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.DATA_DICT_SITE);
        modelMap.put("siteList",lidata);

        /*List<TradingDataDictionary> litype = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.DATA_DICT_SHIPPING_TYPE);
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
*/
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

        SessionVO c= SessionCacheSupport.getSessionVO();
        List<UsercontrollerEbayAccountExtend> ebayList=systemUserManagerService.queryCurrAllEbay(new HashMap());
        modelMap.put("ebayList",ebayList);

        return forword("module/shipping/addshippingDetails",modelMap);
    }


    /**
     * 编辑页面跳转
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping("/editshippingDetails.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView editshippingDetails(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap){

        List<TradingDataDictionary> lidata = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.DATA_DICT_SITE);
        modelMap.put("siteList",lidata);

        String type = request.getParameter("type");
        if(type!=null&&!"".equals(type)){
            modelMap.put("type",type);
        }

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

        SessionVO c= SessionCacheSupport.getSessionVO();
        List<UsercontrollerEbayAccountExtend> ebayList=systemUserManagerService.queryCurrAllEbay(new HashMap());
        modelMap.put("ebayList",ebayList);

        TradingShippingdetails tradingShippingdetails = this.iTradingShippingDetails.selectById(Long.parseLong(request.getParameter("id")));
        modelMap.put("tradingShippingdetails",tradingShippingdetails);

        List<TradingShippingserviceoptions> litso = this.iTradingShippingDetails.selectByShippingserviceoptions(tradingShippingdetails.getId());
        modelMap.put("litso",litso);
        List<TradingInternationalshippingserviceoption> litio = this.iTradingShippingDetails.selectByInternationalshippingserviceoption(tradingShippingdetails.getId());
        modelMap.put("litio",litio);
        List li = new ArrayList();
        for(TradingInternationalshippingserviceoption tis : litio){
            li.add(this.iTradingAttrMores.selectByParnetid(tis.getId(),"ShipToLocation"));
        }
        if(li!=null&&li.size()>0){
            modelMap.put("tololi",li);
        }
        List<TradingAttrMores> litam = this.iTradingAttrMores.selectByParnetid(tradingShippingdetails.getId(),"ExcludeShipToLocation");
        modelMap.put("litam",litam);
        List<TradingDataDictionary> litdd = DataDictionarySupport.getTradingDataDictionaryByType("country");
        List<TradingDataDictionary> litdd1 = DataDictionarySupport.getTradingDataDictionaryByType("delta");
        String toname = "",tovalue="";
        List<String> listrs = new ArrayList();
        for(TradingAttrMores tam : litam){
            for(TradingDataDictionary tdd : litdd1){
                if(tam.getValue().equals(tdd.getValue())){
                    toname += tdd.getName()+"，";
                    tovalue += tdd.getValue()+",";
                    listrs.add(tdd.getId()+"");
                }
            }
            for(TradingDataDictionary tdd : litdd){
                if(tam.getValue().equals(tdd.getValue())){
                    Boolean isFlag = true;
                    for(String str:listrs){
                        if(tdd.getParentId().toString().equals(str)){
                            isFlag=false;
                        }
                    }
                    if(isFlag) {
                        toname += tdd.getName() + "，";
                    }
                    tovalue += tdd.getValue()+",";
                }
            }
        }
        if(toname!=null&&!"".equals(toname)) {
            modelMap.put("tamstr", toname.substring(0, toname.length() - 1));
            modelMap.put("tamvaluestr", tovalue.substring(0, tovalue.length() - 1));
        }
        return forword("module/shipping/addshippingDetails",modelMap);
    }


    /**
     * 跳转到洲或地区的列表界面
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping("/locationList.do")
    public ModelAndView locationList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws UnsupportedEncodingException {
        String sdid = request.getParameter("parentId");
        String sdname = request.getParameter("parentName");
        if(sdid!=null&&!"".equals(sdid)) {
            List<TradingAttrMores> litam = this.iTradingAttrMores.selectByParnetid(Long.parseLong(sdid), "ExcludeShipToLocation");
            modelMap.put("litam", litam);
        }
        if(sdname!=null&&!"".equals(sdname)){
            sdname = new String(sdname.getBytes("ISO-8859-1"),"UTF-8");
            modelMap.put("sdname",sdname);
        }

        List<TradingDataDictionary> lidata = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.DATA_DICT_DELTA);
        List<TradingDataDictionary> li1 = new ArrayList();
        List<TradingDataDictionary> li2 = new ArrayList();
        List<TradingDataDictionary> li3 = new ArrayList();
        for(TradingDataDictionary tdd : lidata){
            if(tdd.getName1().equals("Additional Locations")){
                li3.add(tdd);
            }else if(tdd.getName1().equals("Domestic")){
                li1.add(tdd);
            }else if(tdd.getName1().equals("International")){
                li2.add(tdd);
            }
        }
        modelMap.put("li1",li1);
        modelMap.put("li2",li2);
        modelMap.put("li3",li3);
        return forword("module/shipping/locationList",modelMap);
    }

    /**
     * 跳转到国家列表页面
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping("/ajax/countryList.do")
    @ResponseBody
    public void countryList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        String parentid = request.getParameter("parentid");
        List<TradingDataDictionary> lidata = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.DATA_DICT_COUNTRY,Long.parseLong(parentid));
        AjaxSupport.sendSuccessText("",lidata);
    }

    /**
     * 保存运输选项数据
     * @param request
     * @throws Exception
     */
    @RequestMapping("/saveShippingDetails.do")
    @AvoidDuplicateSubmission(needRemoveToken = true)
    @ResponseBody
    public void saveShippingDetails(HttpServletRequest request,TradingShippingdetails tradingShippingdetails,ShippingDetails shippingDetails) throws Exception {

        this.iTradingShippingDetails.saveAllData(tradingShippingdetails,shippingDetails,request.getParameter("notLocationValue"));

        AjaxSupport.sendSuccessText("message", "操作成功！");
    }
    /**
     * 保存数据
     * @param request
     * @param response
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping("/ajax/delshippingDetails.do")
    @AvoidDuplicateSubmission(needRemoveToken = true)
    @ResponseBody
    public void delshippingDetails(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        String id = request.getParameter("id");
        TradingShippingdetails tp= this.iTradingShippingDetails.selectById(Long.parseLong(id));
        if("1".equals(tp.getCheckFlag())){
            tp.setCheckFlag("0");
        }else{
            tp.setCheckFlag("1");
        }
        this.iTradingShippingDetails.saveShippingDetails(tp);
        AjaxSupport.sendSuccessText("","操作成功!");
    }
}
