package com.common.controller;

import com.base.database.keymove.mapper.KeyMoveListMapper;
import com.base.database.keymove.model.KeyMoveList;
import com.base.database.keymove.model.KeyMoveListExample;
import com.base.database.publicd.mapper.PublicDataDictMapper;
import com.base.database.publicd.model.PublicDataDict;
import com.base.database.publicd.model.PublicDataDictExample;
import com.base.database.trading.mapper.UsercontrollerDevAccountMapper;
import com.base.database.trading.mapper.UsercontrollerEbayAccountMapper;
import com.base.database.trading.model.*;
import com.base.database.userinfo.mapper.PublicDataDictCategorySpecificsMapper;
import com.base.database.userinfo.model.PublicDataDictCategorySpecifics;
import com.base.database.userinfo.model.PublicDataDictCategorySpecificsExample;
import com.base.domains.CommonParmVO;
import com.base.domains.DictDataFilterParmVO;
import com.base.domains.SessionVO;
import com.base.domains.querypojos.KeyMoveProgressQuery;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.domains.userinfo.UsercontrollerEbayAccountExtend;
import com.base.mybatis.page.PageJsonBean;
import com.base.sampleapixml.APINameStatic;
import com.base.userinfo.service.SystemUserManagerService;
import com.base.userinfo.service.UserInfoService;
import com.base.utils.cache.DataDictionarySupport;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.common.DateUtils;
import com.base.utils.common.DictCollectionsUtil;
import com.base.utils.common.UUIDUtil;
import com.base.utils.exception.Asserts;
import com.base.utils.httpclient.HttpClientUtil;
import com.base.utils.threadpool.AddApiTask;
import com.base.utils.threadpool.TaskMessageVO;
import com.base.utils.tranfiles.DownEbayFile;
import com.base.utils.xmlutils.SamplePaseXml;
import com.base.xmlpojo.trading.addproduct.Item;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
import com.keymove.service.IKeyMoveProgress;
import com.sitemessage.service.SiteMessageStatic;
import com.test.mapper.TestMapper;
import com.trading.service.ITradingDataDictionary;
import com.trading.service.ITradingItem;
import com.trading.service.ITradingReseCategory;
import com.trading.service.IUsercontrollerEbayAccount;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.log4j.Logger;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrtor on 2014/7/31.
 * 提供一些通用值返回
 */

@Controller
public class UtilController extends BaseAction{
    static Logger logger = Logger.getLogger(UtilController.class);
    @Value("${EBAY.API.URL}")
    private String apiUrl;
    @Autowired
    private ITradingDataDictionary iTradingDataDictionary;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private ITradingReseCategory iTradingReseCategory;
    @Autowired
    public PublicDataDictCategorySpecificsMapper publicDataDictMapper;
    @Autowired
    public TestMapper testMapper;
    @Autowired
    private IUsercontrollerEbayAccount iUsercontrollerEbayAccount;
    @Autowired
    private UsercontrollerEbayAccountMapper usercontrollerEbayAccountMapper;//查询开发帐号信息
    @Autowired
    private ITradingItem iTradingItem;
    @Autowired
    private KeyMoveListMapper keyMoveListMapper;
    @Autowired
    private SystemUserManagerService systemUserManagerService;
    @Autowired
    private IKeyMoveProgress iKeyMoveProgress;

    @Value("${EBAY.FINDING.KEY.API.URL}")
    private String findingkeyapiUrl;
    /**用于更新页面token*/
    @RequestMapping("/ajax/getToken.do")
    @ResponseBody
    public void getToken(HttpServletRequest request){
        SessionVO sessionVO= SessionCacheSupport.getSessionVO();
        if(sessionVO==null){
            AjaxSupport.sendFailText("sessionStatusFalse", "未登录或者已过期");
            return;
        }
        String t=sessionVO.getLoginId()+ UUIDUtil.getUUID();
        request.getSession(false).removeAttribute("_token");
        request.getSession(false).setAttribute("_token", t);
        AjaxSupport.sendSuccessText("_token",t);
    }

    /**商品类别相关开始==========================================================*/
    /**用于获取商品类别的属性*/
    @RequestMapping("/ajax/getCategorySpecifics.do")
    @ResponseBody
    public void getCategorySpecifics(String parentCategoryID,HttpServletRequest request,String siteID) throws Exception {
        Asserts.assertTrue(StringUtils.isNotEmpty(parentCategoryID),"目录id不能为空");
        Asserts.assertTrue(NumberUtils.isNumber(siteID),"只能输入数字!");

        /*DictDataFilterParmVO vo=new DictDataFilterParmVO();
        vo.setLongV1(Long.valueOf(parentCategoryID));
        vo.setStringV1(DictCollectionsUtil.categorySpecifics);
        vo.setStringV2(siteID);
        List<PublicDataDict> publicDataDictList1 = DataDictionarySupport.getPublicDataDictionaryByParentID(vo);*/

        PublicDataDictCategorySpecificsExample tradingDataDictionaryExample =new PublicDataDictCategorySpecificsExample();
        tradingDataDictionaryExample.createCriteria().andItemTypeEqualTo(DictCollectionsUtil.categorySpecifics)
        .andItemParentIdEqualTo(parentCategoryID).andSiteIdEqualTo(siteID);
        List<PublicDataDictCategorySpecifics> publicDataDictList = this.publicDataDictMapper.selectByExample(tradingDataDictionaryExample);



        if(publicDataDictList!=null && publicDataDictList.size()>0){

        }else {
            publicDataDictList=new ArrayList<PublicDataDictCategorySpecifics>();
        }
        AjaxSupport.sendSuccessText("",publicDataDictList);
        /*TradingDataDictionary td = DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(siteID));
        String sitecode = td.getName1();

        //获取当前帐号绑定的开发帐号信息
        UsercontrollerDevAccountExtend d = userInfoService.getDevInfo(null);
        d.setApiSiteid(sitecode);
        d.setApiCallName(APINameStatic.GetCategorySpecifics);
        //获取当前登录人的所有ebay帐号
        List<UsercontrollerEbayAccountExtend> ebays = userInfoService.getEbayAccountForCurrUser();
        Long ebayID=ebays.get(0).getId();
        String token = userInfoService.getTokenByEbayID(ebayID);



        String xml=CategoryAPI.getCategorySpecificsRequest(token,parentCategoryID,sitecode);

        AddApiTask addApiTask = new AddApiTask();
        Map<String, String> resMap = addApiTask.exec(d, xml, apiUrl);
        String r1 = resMap.get("stat");
        String res = resMap.get("message");
        if("fail".equalsIgnoreCase(r1)){
            AjaxSupport.sendFailText("fail",res);
            return;
        }



        List<PublicDataDict> publicDataDictList1 = tradingDataDictionary.addPublicData(res,siteID);

       // DataDictionarySupport.removePublicDictCache();

        AjaxSupport.sendSuccessText("",publicDataDictList1);
        return;*/
    }

    /**获取类别目录菜单
     * 如果两个参数都不传，那么返回一级菜单
     * 如果只传parentid，那么返回对应的parent菜单
     * */
    @RequestMapping("/ajax/getCategoryMenu.do")
    @ResponseBody
    public void getCategoryMenu(String parentID,String level,String siteID){
        List<PublicDataDict> publicDataDictList=null;
        if(StringUtils.isEmpty(level)){
            level=DictCollectionsUtil.ITEM_LEVEL_ONE;
        }
        if(StringUtils.isEmpty(parentID) || "0".equals(parentID)){
            parentID="0";
            level = DictCollectionsUtil.ITEM_LEVEL_ONE;
            DictDataFilterParmVO vo=new DictDataFilterParmVO();
            vo.setLongV1(Long.valueOf(parentID));
            vo.setStringV1(level);
            vo.setStringV2(DictCollectionsUtil.category);
            vo.setStringV3(siteID);
            publicDataDictList = DataDictionarySupport.getPublicDataDictionaryByItemLevel(vo);
        }else {
            DictDataFilterParmVO vo=new DictDataFilterParmVO();
            vo.setLongV1(Long.valueOf(parentID));
            vo.setStringV1(DictCollectionsUtil.category);
            vo.setStringV2(siteID);
            publicDataDictList = DataDictionarySupport.getPublicDataDictionaryByParentID(vo);
        }


        AjaxSupport.sendSuccessText("",publicDataDictList);
        return;

    }

    /**
     * 通过ＡＰＩ查询相似分类
     * @param title

     */
    @RequestMapping("/ajax/getReseCategoryMenu.do")
    @ResponseBody
    public void getReseCategoryMenu(ModelMap modelMap,String title,String siteid,CommonParmVO commonParmVO) throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        sb.append("<findItemsByKeywordsRequest xmlns=\"http://www.ebay.com/marketplace/search/v1/services\">");
        sb.append("<keywords>" + title + "</keywords>");
        sb.append("<outputSelector>CategoryHistogram</outputSelector>");
        sb.append("<paginationInput>");
        sb.append("<pageNumber>1</pageNumber><entriesPerPage>1</entriesPerPage>");
        sb.append("</paginationInput>");
        sb.append("</findItemsByKeywordsRequest>");
        List<TradingReseCategory> litr = new ArrayList<TradingReseCategory>();
        TradingDataDictionary tdd = DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(siteid));
        if(title!=null&&!"".equals(title)) {
            List<BasicHeader> headers = new ArrayList<BasicHeader>();
            headers.add(new BasicHeader("X-EBAY-SOA-SERVICE-NAME", "FindingService"));
            headers.add(new BasicHeader("X-EBAY-SOA-OPERATION-NAME", "findItemsByKeywords"));
            headers.add(new BasicHeader("X-EBAY-SOA-SERVICE-VERSION", "1.12.0"));
            headers.add(new BasicHeader("X-EBAY-SOA-GLOBAL-ID", tdd.getDataDesc()));
            headers.add(new BasicHeader("X-EBAY-SOA-SECURITY-APPNAME", "sandpoin-23af-4f47-a304-242ffed6ff5b"));
            headers.add(new BasicHeader("X-EBAY-SOA-REQUEST-DATA-FORMAT", "XML"));
            HttpClient httpClient = HttpClientUtil.getHttpsClient();
            String res = HttpClientUtil.post(httpClient, findingkeyapiUrl, sb.toString(), "UTF-8", headers);
            litr = SamplePaseXml.selectCategoryByKey(res, title);
            for (int i = 0; i < litr.size() - 1; i++) {
                TradingReseCategory tr1 = litr.get(i);
                for (int j = litr.size() - 1; j > i; j--) {
                    TradingReseCategory tr2 = litr.get(j);
                    if (tr1.getCategoryId().equals(tr2.getCategoryId())) {
                        litr.remove(j);
                    }
                }
            }
        }
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        jsonBean.setPageCount(20);
        jsonBean.setPageNum(1);
        jsonBean.setTotal(litr.size());
        jsonBean.setList(litr);
        AjaxSupport.sendSuccessText("", jsonBean);
    }

    /**
     * 保存到本地分类表中
     * @param

     */
    @RequestMapping("/ajax/saveReseCategory.do")
    @ResponseBody
    public void saveReseCategory(ModelMap modelMap,TradingReseCategory tradingReseCategory,String siteId,
                                 HttpServletRequest request,HttpServletResponse response) throws Exception {
        String id = request.getParameter("id");
        this.iTradingReseCategory.saveTradingReseCategory(tradingReseCategory);
        AjaxSupport.sendSuccessText("", "{\"pathstr\":\""+this.getStr(tradingReseCategory.getCategoryId())+"\"}");

    }

    public String getStr(String itemid){
        String str = "";
        PublicDataDict pub = DataDictionarySupport.getPublicDataDictionaryByItemIDs(itemid,DictCollectionsUtil.category);
        str = pub.getItemEnName();
        while (1==1){
            if(!pub.getItemParentId().equals("0")){
                pub = DataDictionarySupport.getPublicDataDictionaryByItemIDs(pub.getItemParentId(),DictCollectionsUtil.category);
                if("".equals(str)){
                    str = pub.getItemEnName();
                }else{
                    str = pub.getItemEnName() + "->" + str;
                }
            }else{
                break;
            }
        }
        return str;
    }
    public PublicDataDict getPublicDataDictString(String itemid){
        PublicDataDict lipublic = DataDictionarySupport.getPublicDataDictionaryByItemIDs(itemid,DictCollectionsUtil.category);
        return lipublic;
    }

    /**商品目录选择页面初始化*/
    @RequestMapping("category/initSelectCategoryPage.do")
    public ModelAndView initSelectCategoryPage(@ModelAttribute( "initSomeParmMap" )ModelMap modelMap,HttpServletRequest request){
        return forword("/commonPage/category/popSelectCategoryPage",modelMap);
    }

    /**查询商品目录选择页面初始化*/
    @RequestMapping("category/initQueryCategoryPage.do")
    public ModelAndView initQueryCategoryPage(@ModelAttribute( "initSomeParmMap" )ModelMap modelMap,HttpServletRequest request){
        modelMap.put("title",request.getParameter("title"));
        return forword("/commonPage/category/popQueryCategoryPage",modelMap);
    }

/**商品类别相关结束==========================================================*/


            /*调用ＡＰＩ抓取数据*/
    @RequestMapping("category/saveTradingDic.do")
    @ResponseBody
    public ModelAndView saveTradingDic(ModelMap modelMap, HttpServletRequest request) throws Exception {
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<GeteBayDetailsRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">\n" +
                "<RequesterCredentials>\n" +
                "<eBayAuthToken>AgAAAA**AQAAAA**aAAAAA**ODN9VA**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6wFk4GhCpGGoA+dj6x9nY+seQ**Sd0CAA**AAMAAA**a8K8fmx7s3vurAo/SGFiTl1PTp5pI/+JwbMuDqmBGpz/3++lJUPnHvIVgzjMBQtP89Qx/RDRY0s2Y10elagAew81uMb4EkWRIZIYw7YZdpp+ExQ5BUNnopAPRXeWz9ODtU2ujdk7m1y+wGLeNS8iFwP3bmHjcGyMLHDWvKkyBsky/y9kbpTmMbcry3SlUVykFG5cYeQFgpEjqJohfH6mX2T7NcD0L0eVWzrU4/Wh7NFpmGfxsgzN1e3FA89sLG6HZfJhSg+SBRT+dR29BAf2A9oVI6+yIctnfGPnHL68UrGzmgh+EgUf8aQW8n17zLEZEatUjpwlrAoRIH/CbG+gVZkSkGQyxI/WoqWtwAZKyAApvOSqGNYVRwef61GHMmAyf7eXojMBP3na1wMMAHpde6APji+3QiDlGT49T6wzcUA8TPRTTIQCYuqsEBY0tAjVrTEwcbsPUW9/533q8MNsVywH99VDme1fsKKLK4v93mZg9JzAMbdeNIfrtcH8CVQYQzv4n+xGNvchUD8pwGtZ98RxGk/8dZr0pEMZpcM70YNGLAtzbNEsPvfPdQfDUV6bgsHRBzySa+jAeCDesslrC3fanWJFFa/7YjLnNqdcVpWsC0V/uRWbdzOJ9mo2+sJelL5mPCgWS+YdFHYgdxYRnJCb/VBkkrm7IuSHUuBXWVdlaqs5Miu0fWIj0CZ/KYjBZK7XZyAN7LP1spAiFQJ2vo8/UCqcoay4ftMT68QgNcAyucVEr8gAF7k7RFDFEYSf</eBayAuthToken>\n" +
                "</RequesterCredentials>\n" +
                "<DetailName>ShippingServiceDetails</DetailName>\n" +
                "<Version>901</Version>\n" +
                "</GeteBayDetailsRequest>";
        TradingDataDictionary tdd = DataDictionarySupport.getTradingDataDictionaryByID(3774L);
        UsercontrollerDevAccountExtend d = userInfoService.getDevByOrder(new HashMap());
        d.setApiSiteid(tdd.getName1());
        //d.setApiSiteid("0");
        d.setApiCallName(APINameStatic.GeteBayDetails);
        AddApiTask addApiTask = new AddApiTask();
        Map<String, String> resMap = addApiTask.exec(d, xml, apiUrl);
        String res = resMap.get("message");
        List<TradingDataDictionary> litdd = SamplePaseXml.selectShippingService(res);
        for (TradingDataDictionary tdddesc : litdd) {
            tdddesc.setParentId(tdd.getId());
            tdddesc.setDataDesc(tdd.getName() + "国内运输");
            this.iTradingDataDictionary.saveDataDictionary(tdddesc);
        }




        /*List<TradingDataDictionary> lidata = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.DATA_DICT_SITE);
        modelMap.put("siteList", lidata);
        for (int i = 0; i < lidata.size(); i++) {
            TradingDataDictionary tdd = lidata.get(i);
            UsercontrollerDevAccountExtend d = userInfoService.getDevInfo(1L);
            d.setApiSiteid(tdd.getName1());
            //d.setApiSiteid("0");
            d.setApiCallName(APINameStatic.GeteBayDetails);
            AddApiTask addApiTask = new AddApiTask();
            Map<String, String> resMap = addApiTask.exec(d, xml, apiUrl);
            String res = resMap.get("message");
            List<TradingDataDictionary> litdd = SamplePaseXml.selectShippingService(res);
            for (TradingDataDictionary tdddesc : litdd) {
                tdddesc.setParentId(tdd.getId());
                tdddesc.setDataDesc(tdd.getName() + "国内运输");
                this.iTradingDataDictionary.saveDataDictionary(tdddesc);
            }
        }*/
        return forword("/test",modelMap);
    }

    @RequestMapping("category/getCategories.do")
    @ResponseBody
    public ModelAndView getCategories(ModelMap modelMap, HttpServletRequest request) throws Exception {
        String xml = "";

        List<TradingDataDictionary> lidata = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.DATA_DICT_SITE);
        modelMap.put("siteList", lidata);
        for (int i = 0; i < lidata.size(); i++) {
            TradingDataDictionary tdd = lidata.get(i);
            if(tdd.getId()==291||tdd.getId()==311){
                continue;
            }
            xml="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                    "<GetCategoriesRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">\n" +
                    "<RequesterCredentials>\n" +
                    "<eBayAuthToken>AgAAAA**AQAAAA**aAAAAA**wV1JUQ**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6wFk4GhCpGGoA+dj6x9nY+seQ**cx0CAA**AAMAAA**2kuzIn+bBej1QDsDFfI2N74mj8psZYNYrtgX97fzWSGXO7EjvdlE9leu9HCY1bR9wdrzlAE7AKcT9Oz5BDNZbNQLS+uoifmNUM47lSqxWeYTQS2GtMK25LPYhxY+OQp6UVZ8lUh6Oqr91ub03emzufuZHo+6KSNJfNXMtOBVaB7PDeBQyNWoFBO0/LYiS5ql6HXB7vCj0W+K/iT4t3aPs5KlXAXjewM/Sa+nUDtjT9SseqrKrxdZx5fkAePeSrBs229tdCrkTtE0n+ZE9ppwJjElZpu7yfQL44McNa16KBxYYO0PnX7ENg2yMxf3H4aji0BEfB41lrC1LwhmNSebJGrJXRQVS9jmZyDqYiBdn1t536va/LPTP8kc3GZ7hnZRJuhMxoGGgx4ev5Hip0L7dk6cAPKHIkHUIjfA5pwVHEJZpvea+7uvwAh5pj9U7r6rmB9FXH2G9l+F5SytYlIXsDjwNtrEN53k5HrM0vhnGdd7pUwvyu7Nu4U5aPkZQZjTr6OrTWioDsZZwEz+pf0scw0IYweMhicCqMTNbvkJsj2cikX49C6XSAcoUyrGtGa11vFChrifmq74dPZmUEtT1hDtwL1Ix3VPyZcJtTukKljxa0W0IwIe676X5HmiGhvk5qPPUImkXcZdQUK1gMdZmw0seMl5xmFG33kKVSD9H0p0JAEF4lOcDvjADQZtwLXY3qIhvYcKdOrIffrUAURnJRYnrB/MixizWvw252xBn9tmxpm68O3KsGBzcUwEB0Su</eBayAuthToken>\n" +
                    "</RequesterCredentials>\n" +
                    "<CategorySiteID>"+tdd.getName1()+"</CategorySiteID>\n" +
                    "<DetailLevel>ReturnAll</DetailLevel>\n" +
                    "<Version>885</Version>\n" +
                    "</GetCategoriesRequest>​";
            List<BasicHeader> headers = new ArrayList<BasicHeader>();
            headers.add(new BasicHeader("X-EBAY-API-COMPATIBILITY-LEVEL","885"));
            headers.add(new BasicHeader("X-EBAY-API-DEV-NAME", "5d70d647-b1e2-4c7c-a034-b343d58ca425"));
            headers.add(new BasicHeader("X-EBAY-API-APP-NAME","sandpoin-1f58-4e64-a45b-56507b02bbeb"));
            headers.add(new BasicHeader("X-EBAY-API-CERT-NAME","936fc911-c05c-455c-8838-3a698f2da43a"));
            headers.add(new BasicHeader("X-EBAY-API-SITEID",tdd.getName1()));
            headers.add(new BasicHeader("X-EBAY-API-CALL-NAME",APINameStatic.GetCategories));
            HttpClient httpClient= HttpClientUtil.getHttpsClient();
            String res= HttpClientUtil.post(httpClient, "https://api.sandbox.ebay.com/ws/api.dll", xml.toString(), "UTF-8", headers);
            List<PublicDataDict> litdd = SamplePaseXml.selectPublicDataDict(res);
            for (PublicDataDict pdd : litdd) {
                pdd.setSiteId(tdd.getId().toString());
                try {
                 //   this.publicDataDictMapper.insertSelective(pdd);
                }catch(Exception e){
                    continue;
                }
            }
        }
        return forword("/test",modelMap);
    }


    /**抓取目录属性*/
    @RequestMapping("category/getCategoriesSpec.do")
    @ResponseBody
    public String getCategoriesSpec(ModelMap modelMap, HttpServletRequest request) throws Exception {

        DownEbayFile.main(null);

        if(1==1){return "ok";}

        List<Map> maps=testMapper.queryTest(new HashMap());


        for (Map m:maps){

        Map map=new HashMap();
        map.put("siteID",m.get("StringV"));
        List<PublicDataDict> x=  testMapper.selectForCatchData(map);
        m.put("flag","1");
        testMapper.updateTest(m);

int i=0;
        for (PublicDataDict p:x){
i++;
            if(i>4800){
                m.put("flag","0");
                testMapper.updateTest(m);
                return "还未完成!";}

            String xml="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                    "<GetCategorySpecificsRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">" +
                    "<RequesterCredentials>" +
                    "<eBayAuthToken>AgAAAA**AQAAAA**aAAAAA**wV1JUQ**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6wFk4GhCpGGoA+dj6x9nY+seQ**cx0CAA**AAMAAA**2kuzIn+bBej1QDsDFfI2N74mj8psZYNYrtgX97fzWSGXO7EjvdlE9leu9HCY1bR9wdrzlAE7AKcT9Oz5BDNZbNQLS+uoifmNUM47lSqxWeYTQS2GtMK25LPYhxY+OQp6UVZ8lUh6Oqr91ub03emzufuZHo+6KSNJfNXMtOBVaB7PDeBQyNWoFBO0/LYiS5ql6HXB7vCj0W+K/iT4t3aPs5KlXAXjewM/Sa+nUDtjT9SseqrKrxdZx5fkAePeSrBs229tdCrkTtE0n+ZE9ppwJjElZpu7yfQL44McNa16KBxYYO0PnX7ENg2yMxf3H4aji0BEfB41lrC1LwhmNSebJGrJXRQVS9jmZyDqYiBdn1t536va/LPTP8kc3GZ7hnZRJuhMxoGGgx4ev5Hip0L7dk6cAPKHIkHUIjfA5pwVHEJZpvea+7uvwAh5pj9U7r6rmB9FXH2G9l+F5SytYlIXsDjwNtrEN53k5HrM0vhnGdd7pUwvyu7Nu4U5aPkZQZjTr6OrTWioDsZZwEz+pf0scw0IYweMhicCqMTNbvkJsj2cikX49C6XSAcoUyrGtGa11vFChrifmq74dPZmUEtT1hDtwL1Ix3VPyZcJtTukKljxa0W0IwIe676X5HmiGhvk5qPPUImkXcZdQUK1gMdZmw0seMl5xmFG33kKVSD9H0p0JAEF4lOcDvjADQZtwLXY3qIhvYcKdOrIffrUAURnJRYnrB/MixizWvw252xBn9tmxpm68O3KsGBzcUwEB0Su</eBayAuthToken>" +
                    "</RequesterCredentials>" +
                    "<CategoryID>"+p.getItemId()+"</CategoryID>" +
                    "</GetCategorySpecificsRequest>​";

            TradingDataDictionary t= DataDictionarySupport.getTradingDataDictionaryByID(Long.valueOf(p.getSiteId()));

            List<BasicHeader> headers = new ArrayList<BasicHeader>();
            headers.add(new BasicHeader("X-EBAY-API-COMPATIBILITY-LEVEL","885"));
            /*headers.add(new BasicHeader("X-EBAY-API-DEV-NAME", "bbafa7e7-2f98-4783-9c34-f403faeb007f"));
            headers.add(new BasicHeader("X-EBAY-API-APP-NAME","chengdul-5b82-4a84-a496-6a2c75e4e0d5"));
            headers.add(new BasicHeader("X-EBAY-API-CERT-NAME","724f4467-9280-437c-a998-f5bcfee67ce5"));*/
            headers.add(new BasicHeader("X-EBAY-API-DEV-NAME", "5d70d647-b1e2-4c7c-a034-b343d58ca425"));
            headers.add(new BasicHeader("X-EBAY-API-APP-NAME","sandpoin-1f58-4e64-a45b-56507b02bbeb"));
            headers.add(new BasicHeader("X-EBAY-API-CERT-NAME","936fc911-c05c-455c-8838-3a698f2da43a"));
            headers.add(new BasicHeader("X-EBAY-API-SITEID",t.getName1()));
            headers.add(new BasicHeader("X-EBAY-API-CALL-NAME",APINameStatic.GetCategorySpecifics));

            HttpClient httpClient= HttpClientUtil.getHttpsClient();
            String res= HttpClientUtil.post(httpClient, "https://api.sandbox.ebay.com/ws/api.dll", xml, "UTF-8", headers);

            try {
                List<PublicDataDict> publicDataDictList1 = iTradingDataDictionary.addPublicData(res, String.valueOf(t.getId()));
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

        }

        }



        return "ok";
    }


    @RequestMapping("keymove/userSelectSite.do")
    public ModelAndView userSelectSite(ModelMap modelMap,HttpServletRequest request){
        List<TradingDataDictionary> lidata = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.DATA_DICT_SITE);
        modelMap.put("siteList",lidata);
        SessionVO c= SessionCacheSupport.getSessionVO();
        List<UsercontrollerEbayAccountExtend> ebayList=systemUserManagerService.queryCurrAllEbay(new HashMap());
        modelMap.put("ebayList",ebayList);
        return forword("/userselect/userselect",modelMap);
    }

    /**
     * 统计一键搬家进度
     * @param modelMap
     * @param request
     * @throws Exception
     */
    @RequestMapping("keymove/keyProgress.do")
    @ResponseBody
    public void keyProgress(ModelMap modelMap, HttpServletRequest request) throws Exception {
        SessionVO c= SessionCacheSupport.getSessionVO();
        List<KeyMoveProgressQuery> limp = this.iKeyMoveProgress.selectByUserId(c.getId());
        if(limp!=null&&limp.size()>0){
            KeyMoveProgressQuery kmpq = limp.get(0);
            UsercontrollerEbayAccount uea = this.iUsercontrollerEbayAccount.selectById(Long.parseLong(kmpq.getPaypalId()));
            modelMap.put("ebayAccount",uea.getEbayAccount());
            TradingProgress tps = this.iKeyMoveProgress.selectById(kmpq.getProgressId());
            modelMap.put("startDate",DateUtils.secondsBetween(tps.getStartDate(), tps.getEndDate()));
        }
        modelMap.put("limp",limp);
        AjaxSupport.sendSuccessText("message", modelMap);

    }

    /*一键搬家*/
    @RequestMapping("keymove/saveItemToLocation.do")
    @ResponseBody
    public void saveItemToLocation(ModelMap modelMap, HttpServletRequest request) throws Exception {
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date beginDate = new Date();
        Calendar date = Calendar.getInstance();
        date.setTime(beginDate);
        date.set(Calendar.DATE, date.get(Calendar.DATE) - 119);
        Date endDate = dft.parse(dft.format(date.getTime()));
        String  startTo = DateUtils.DateToString(new Date());
        String startFrom = DateUtils.DateToString(endDate);

        String [] userId = request.getParameterValues("ebayAccounts");
        TradingDataDictionary sitedata = DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(request.getParameter("site")));
        String colStr;
        List<Item> li = new ArrayList();
        for(String id : userId){
            UsercontrollerEbayAccount uea = this.usercontrollerEbayAccountMapper.selectByPrimaryKey(Long.parseLong(id));
            colStr = this.getMoveCosXml(uea.getEbayToken(),"1",startFrom,startTo,uea.getEbayAccount());
            System.out.println(colStr);
            UsercontrollerDevAccountExtend d = userInfoService.getDevByOrder(new HashMap());

            d.setApiSiteid(sitedata.getName1());
            d.setApiCallName(APINameStatic.ListingItemList);
            AddApiTask addApiTask = new AddApiTask();
            TaskMessageVO taskMessageVO=new TaskMessageVO();
            taskMessageVO.setMessageContext("刊登");
            taskMessageVO.setMessageTitle("刊登操作");
            taskMessageVO.setMessageType(SiteMessageStatic.LISTING_KEY_MOVE_MESSAGE_TYPE);
            taskMessageVO.setBeanNameType(SiteMessageStatic.LISTING_KEY_MOVE_BEAN);
            taskMessageVO.setMessageFrom("system");
            SessionVO sessionVO=SessionCacheSupport.getSessionVO();
            taskMessageVO.setMessageTo(sessionVO.getId());
            taskMessageVO.setSendOrNotSend(false);
            taskMessageVO.setObjClass(new String[]{uea.getEbayAccount(),uea.getEbayToken(),sitedata.getName1(),id,sitedata.getId()+""});
            addApiTask.execDelayReturn(d, colStr, apiUrl, taskMessageVO);
            AjaxSupport.sendSuccessText("message", "操作成功！结果请稍后查看消息！");

            /*Map<String, String> resMap = addApiTask.exec(d, colStr, apiUrl);
            String res = resMap.get("message");
            String ack = SamplePaseXml.getVFromXmlString(res, "Ack");
            if("Success".equals(ack)) {//ＡＰＩ成功请求，保存数据
                //String totalNumber = SamplePaseXml.getVFromXmlString(res, "ReturnedItemCountActual");
                Element el = SamplePaseXml.getApiElement(res, "PaginationResult");
                String totalNumber = el.elementText("TotalNumberOfEntries");
                String pageSize = el.elementText("TotalNumberOfPages");
                for(int i=1;i<=Integer.parseInt(pageSize);i++){
                    String xml = this.getMoveCosXml(uea.getEbayToken(),i+"",startFrom,startTo,uea.getEbayAccount());
                    resMap = addApiTask.exec(d, xml, apiUrl);
                    res = resMap.get("message");
                    String acks = SamplePaseXml.getVFromXmlString(res, "Ack");
                    if("Success".equals(acks)) {//ＡＰＩ成功请求，保存数据
                        li.addAll(SamplePaseXml.getItemElememt(res));
                    }else{//ＡＰＩ请求失败
                        continue;
                    }
                }
            }else{//ＡＰＩ请求失败
                AjaxSupport.sendFailText("fail","请求数据失败！");
            }*/
        }
        /*List<Item> liitem = new ArrayList<Item>();
        for(String id : userId) {
            UsercontrollerEbayAccount uea = this.usercontrollerEbayAccountMapper.selectByPrimaryKey(Long.parseLong(id));
            for (Item item : li) {
                KeyMoveListExample kmle = new KeyMoveListExample();
                kmle.createCriteria().andUserIdEqualTo(uea.getUserId()).andItemIdEqualTo(item.getItemID()).andSiteIdEqualTo(sitedata.getName1());
                List<KeyMoveList> likml = this.keyMoveListMapper.selectByExample(kmle);
                if(likml==null||likml.size()==0){
                    KeyMoveList kml = new KeyMoveList();
                    kml.setCreateTime(new Date());
                    kml.setItemId(item.getItemID());
                    kml.setUserId(uea.getUserId());
                    kml.setEbaytoken(uea.getEbayToken());
                    kml.setTaskFlag("0");
                    kml.setSiteId(sitedata.getId()+"");
                    kml.setPaypalId(id);
                    this.keyMoveListMapper.insertSelective(kml);
                }
            }
        }*/
        AjaxSupport.sendSuccessText("message", "已成功记录到任务表，过一段时间就会从把数据搬到我们的范本页面！");
    }
    public String getMoveCosXml(String token,String page,String satartfrom,String startto,String ebayneam){
        String colStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<GetSellerListRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">\n" +
                "<RequesterCredentials>\n" +
                "<eBayAuthToken>"+token+"</eBayAuthToken>\n" +
                "</RequesterCredentials>\n" +
                "<Pagination ComplexType=\"PaginationType\">\n" +
                "\t<EntriesPerPage>100</EntriesPerPage>\n" +
                "\t<PageNumber>"+page+"</PageNumber>\n" +
                "</Pagination>\n" +
                "<StartTimeFrom>"+satartfrom+"</StartTimeFrom>\n" +
                "<StartTimeTo>"+startto+"</StartTimeTo>\n" +
                "<UserID>"+ebayneam+"</UserID>\n" +
                "<GranularityLevel>Coarse</GranularityLevel>\n" +
                "<DetailLevel>ItemReturnDescription</DetailLevel>\n" +
                "</GetSellerListRequest>";
        return colStr;
    }
    public String cosPost(){
        return "";
    }

}
