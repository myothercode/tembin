package com.common.controller;

import com.base.database.publicd.mapper.PublicDataDictMapper;
import com.base.database.publicd.model.PublicDataDict;
import com.base.database.trading.model.TradingDataDictionary;
import com.base.database.trading.model.TradingReseCategory;
import com.base.domains.CommonParmVO;
import com.base.domains.DictDataFilterParmVO;
import com.base.domains.SessionVO;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.domains.userinfo.UsercontrollerEbayAccountExtend;
import com.base.mybatis.page.PageJsonBean;
import com.base.sampleapixml.APINameStatic;
import com.base.sampleapixml.CategoryAPI;
import com.base.userinfo.service.UserInfoService;
import com.base.utils.cache.DataDictionarySupport;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.common.DictCollectionsUtil;
import com.base.utils.common.UUIDUtil;
import com.base.utils.exception.Asserts;
import com.base.utils.httpclient.HttpClientUtil;
import com.base.utils.threadpool.AddApiTask;
import com.base.utils.xmlutils.SamplePaseXml;
import com.common.base.utils.ajax.AjaxResponse;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
import com.trading.service.ITradingDataDictionary;
import com.trading.service.ITradingReseCategory;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.log4j.Logger;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    private ITradingDataDictionary tradingDataDictionary;
    @Autowired
    private ITradingReseCategory iTradingReseCategory;
    @Autowired
    public PublicDataDictMapper publicDataDictMapper;

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
        Asserts.assertTrue(NumberUtils.isNumber(parentCategoryID),"只能输入数字!");


        DictDataFilterParmVO vo=new DictDataFilterParmVO();
        vo.setLongV1(Long.valueOf(parentCategoryID));
        vo.setStringV1(DictCollectionsUtil.categorySpecifics);
        vo.setStringV2(siteID);
        List<PublicDataDict> publicDataDictList = DataDictionarySupport.getPublicDataDictionaryByParentID(vo);
        if(publicDataDictList!=null && publicDataDictList.size()>0){
            AjaxSupport.sendSuccessText("",publicDataDictList);
            return;
        }
        //获取当前帐号绑定的开发帐号信息
        UsercontrollerDevAccountExtend d = userInfoService.getDevInfo(null);
        d.setApiSiteid("0");
        d.setApiCallName(APINameStatic.GetCategorySpecifics);
        //获取当前登录人的所有ebay帐号
        List<UsercontrollerEbayAccountExtend> ebays = userInfoService.getEbayAccountForCurrUser();
        Long ebayID=ebays.get(0).getId();
        String token = userInfoService.getTokenByEbayID(ebayID);
        String xml=CategoryAPI.getCategorySpecificsRequest(token,parentCategoryID);

        AddApiTask addApiTask = new AddApiTask();
        Map<String, String> resMap = addApiTask.exec(d, xml, apiUrl);
        String r1 = resMap.get("stat");
        String res = resMap.get("message");
        if("fail".equalsIgnoreCase(r1)){
            AjaxSupport.sendFailText("fail",res);
            return;
        }
        List<PublicDataDict> publicDataDictList1 = tradingDataDictionary.addPublicData(res);

       // DataDictionarySupport.removePublicDictCache();

        AjaxSupport.sendSuccessText("",publicDataDictList1);
        return;
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
    public void getReseCategoryMenu(ModelMap modelMap,String title,CommonParmVO commonParmVO) throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        sb.append("<findItemsByKeywordsRequest xmlns=\"http://www.ebay.com/marketplace/search/v1/services\">");
        sb.append("<keywords>" + title + "</keywords>");
        sb.append("<paginationInput>");
        sb.append("<entriesPerPage>10</entriesPerPage>");
        sb.append("</paginationInput>");
        sb.append("</findItemsByKeywordsRequest>");

        System.out.println(sb.toString());
        List<BasicHeader> headers = new ArrayList<BasicHeader>();
        headers.add(new BasicHeader("X-EBAY-SOA-SERVICE-NAME","FindingService"));
        headers.add(new BasicHeader("X-EBAY-SOA-OPERATION-NAME","findItemsByKeywords"));
        headers.add(new BasicHeader("X-EBAY-SOA-SERVICE-VERSION","1.12.0"));
        headers.add(new BasicHeader("X-EBAY-SOA-GLOBAL-ID","EBAY-US"));
        headers.add(new BasicHeader("X-EBAY-SOA-SECURITY-APPNAME","sandpoin-23af-4f47-a304-242ffed6ff5b"));
        headers.add(new BasicHeader("X-EBAY-SOA-REQUEST-DATA-FORMAT","XML"));
        HttpClient httpClient= HttpClientUtil.getHttpsClient();
        String res= HttpClientUtil.post(httpClient,"http://svcs.ebay.com/services/search/FindingService/v1?",sb.toString(),"UTF-8",headers);
        List<TradingReseCategory> litr = SamplePaseXml.selectCategoryByKey(res, title);
        for(int i = 0;i<litr.size()-1;i++){
            TradingReseCategory tr1 = litr.get(i);
            for(int j = litr.size()-1;j>i;j-- ){
                TradingReseCategory tr2 = litr.get(j);
                if(tr1.getCategoryId().equals(tr2.getCategoryId())){
                    litr.remove(j);
                }
            }
        }
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        jsonBean.setPageCount(10);
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
    public void saveReseCategory(ModelMap modelMap,TradingReseCategory tradingReseCategory,HttpServletRequest request,HttpServletResponse response) throws Exception {
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
        modelMap.put("title",request.getParameter("title"));
        return forword("/commonPage/category/popSelectCategoryPage",modelMap);
    }

/**商品类别相关结束==========================================================*/


            /*调用ＡＰＩ抓取数据*/
    @RequestMapping("category/saveTradingDic.do")
    @ResponseBody
    public ModelAndView saveTradingDic(ModelMap modelMap, HttpServletRequest request) throws Exception {
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<GeteBayDetailsRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">\n" +
                "<RequesterCredentials>\n" +
                "<eBayAuthToken>AgAAAA**AQAAAA**aAAAAA**wV1JUQ**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6wFk4GhCpGGoA+dj6x9nY+seQ**cx0CAA**AAMAAA**2kuzIn+bBej1QDsDFfI2N74mj8psZYNYrtgX97fzWSGXO7EjvdlE9leu9HCY1bR9wdrzlAE7AKcT9Oz5BDNZbNQLS+uoifmNUM47lSqxWeYTQS2GtMK25LPYhxY+OQp6UVZ8lUh6Oqr91ub03emzufuZHo+6KSNJfNXMtOBVaB7PDeBQyNWoFBO0/LYiS5ql6HXB7vCj0W+K/iT4t3aPs5KlXAXjewM/Sa+nUDtjT9SseqrKrxdZx5fkAePeSrBs229tdCrkTtE0n+ZE9ppwJjElZpu7yfQL44McNa16KBxYYO0PnX7ENg2yMxf3H4aji0BEfB41lrC1LwhmNSebJGrJXRQVS9jmZyDqYiBdn1t536va/LPTP8kc3GZ7hnZRJuhMxoGGgx4ev5Hip0L7dk6cAPKHIkHUIjfA5pwVHEJZpvea+7uvwAh5pj9U7r6rmB9FXH2G9l+F5SytYlIXsDjwNtrEN53k5HrM0vhnGdd7pUwvyu7Nu4U5aPkZQZjTr6OrTWioDsZZwEz+pf0scw0IYweMhicCqMTNbvkJsj2cikX49C6XSAcoUyrGtGa11vFChrifmq74dPZmUEtT1hDtwL1Ix3VPyZcJtTukKljxa0W0IwIe676X5HmiGhvk5qPPUImkXcZdQUK1gMdZmw0seMl5xmFG33kKVSD9H0p0JAEF4lOcDvjADQZtwLXY3qIhvYcKdOrIffrUAURnJRYnrB/MixizWvw252xBn9tmxpm68O3KsGBzcUwEB0Su</eBayAuthToken>\n" +
                "</RequesterCredentials>\n" +
                "<DetailName>ShippingServiceDetails</DetailName>\n" +
                "<Version>885</Version>\n" +
                "</GeteBayDetailsRequest>";

        List<TradingDataDictionary> lidata = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.DATA_DICT_SITE);
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
        }
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
            xml="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                    "<GetCategoriesRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">\n" +
                    "<RequesterCredentials>\n" +
                    "<eBayAuthToken>AgAAAA**AQAAAA**aAAAAA**wV1JUQ**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6wFk4GhCpGGoA+dj6x9nY+seQ**cx0CAA**AAMAAA**2kuzIn+bBej1QDsDFfI2N74mj8psZYNYrtgX97fzWSGXO7EjvdlE9leu9HCY1bR9wdrzlAE7AKcT9Oz5BDNZbNQLS+uoifmNUM47lSqxWeYTQS2GtMK25LPYhxY+OQp6UVZ8lUh6Oqr91ub03emzufuZHo+6KSNJfNXMtOBVaB7PDeBQyNWoFBO0/LYiS5ql6HXB7vCj0W+K/iT4t3aPs5KlXAXjewM/Sa+nUDtjT9SseqrKrxdZx5fkAePeSrBs229tdCrkTtE0n+ZE9ppwJjElZpu7yfQL44McNa16KBxYYO0PnX7ENg2yMxf3H4aji0BEfB41lrC1LwhmNSebJGrJXRQVS9jmZyDqYiBdn1t536va/LPTP8kc3GZ7hnZRJuhMxoGGgx4ev5Hip0L7dk6cAPKHIkHUIjfA5pwVHEJZpvea+7uvwAh5pj9U7r6rmB9FXH2G9l+F5SytYlIXsDjwNtrEN53k5HrM0vhnGdd7pUwvyu7Nu4U5aPkZQZjTr6OrTWioDsZZwEz+pf0scw0IYweMhicCqMTNbvkJsj2cikX49C6XSAcoUyrGtGa11vFChrifmq74dPZmUEtT1hDtwL1Ix3VPyZcJtTukKljxa0W0IwIe676X5HmiGhvk5qPPUImkXcZdQUK1gMdZmw0seMl5xmFG33kKVSD9H0p0JAEF4lOcDvjADQZtwLXY3qIhvYcKdOrIffrUAURnJRYnrB/MixizWvw252xBn9tmxpm68O3KsGBzcUwEB0Su</eBayAuthToken>\n" +
                    "</RequesterCredentials>\n" +
                    "<CategorySiteID>"+tdd.getName1()+"</CategorySiteID>\n" +
                    "<DetailLevel>ReturnAll</DetailLevel>\n" +
                    "<Version>885</Version>\n" +
                    "</GetCategoriesRequest>​";

            UsercontrollerDevAccountExtend d = userInfoService.getDevInfo(1L);
            d.setApiSiteid(tdd.getName1());
            //d.setApiSiteid("0");
            d.setApiCallName(APINameStatic.GetCategories);
            AddApiTask addApiTask = new AddApiTask();
            Map<String, String> resMap = addApiTask.exec(d, xml, apiUrl);
            String res = resMap.get("message");
            List<PublicDataDict> litdd = SamplePaseXml.selectPublicDataDict(res);
            for (PublicDataDict pdd : litdd) {
                pdd.setSiteId(tdd.getId().toString());
                this.publicDataDictMapper.insertSelective(pdd);
            }
        }
        return forword("/test",modelMap);
    }

}
