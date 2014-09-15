package com.listingitem.controller;

import com.base.database.publicd.model.PublicUserConfig;
import com.base.database.trading.model.*;
import com.base.domains.CommonParmVO;
import com.base.domains.SessionVO;
import com.base.domains.querypojos.ItemQuery;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.mybatis.page.Page;
import com.base.mybatis.page.PageJsonBean;
import com.base.sampleapixml.APINameStatic;
import com.base.userinfo.service.UserInfoService;
import com.base.utils.annotations.AvoidDuplicateSubmission;
import com.base.utils.cache.DataDictionarySupport;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.common.ObjectUtils;
import com.base.utils.threadpool.AddApiTask;
import com.base.utils.xmlutils.PojoXmlUtil;
import com.base.utils.xmlutils.SamplePaseXml;
import com.base.xmlpojo.trading.addproduct.Item;
import com.base.xmlpojo.trading.addproduct.RequesterCredentials;
import com.base.xmlpojo.trading.addproduct.ReviseItemRequest;
import com.base.xmlpojo.trading.addproduct.ShippingDetails;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
import com.trading.service.ITradingAddItem;
import com.trading.service.ITradingDataDictionary;
import com.trading.service.ITradingItem;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/8/18.
 */
@Controller
public class ListingItemController extends BaseAction {
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private ITradingDataDictionary iTradingDataDictionary;
    @Value("${EBAY.API.URL}")
    private String apiUrl;
    @Autowired
    private ITradingItem iTradingItem;

    @RequestMapping("/getListingItemList.do")
    public ModelAndView getListingItemList(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
        String colStr="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "<GetSellerListRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">\n" +
            "<RequesterCredentials>\n" +
            "<eBayAuthToken>AgAAAA**AQAAAA**aAAAAA**wV1JUQ**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6wFk4GhCpGGoA+dj6x9nY+seQ**cx0CAA**AAMAAA**2kuzIn+bBej1QDsDFfI2N74mj8psZYNYrtgX97fzWSGXO7EjvdlE9leu9HCY1bR9wdrzlAE7AKcT9Oz5BDNZbNQLS+uoifmNUM47lSqxWeYTQS2GtMK25LPYhxY+OQp6UVZ8lUh6Oqr91ub03emzufuZHo+6KSNJfNXMtOBVaB7PDeBQyNWoFBO0/LYiS5ql6HXB7vCj0W+K/iT4t3aPs5KlXAXjewM/Sa+nUDtjT9SseqrKrxdZx5fkAePeSrBs229tdCrkTtE0n+ZE9ppwJjElZpu7yfQL44McNa16KBxYYO0PnX7ENg2yMxf3H4aji0BEfB41lrC1LwhmNSebJGrJXRQVS9jmZyDqYiBdn1t536va/LPTP8kc3GZ7hnZRJuhMxoGGgx4ev5Hip0L7dk6cAPKHIkHUIjfA5pwVHEJZpvea+7uvwAh5pj9U7r6rmB9FXH2G9l+F5SytYlIXsDjwNtrEN53k5HrM0vhnGdd7pUwvyu7Nu4U5aPkZQZjTr6OrTWioDsZZwEz+pf0scw0IYweMhicCqMTNbvkJsj2cikX49C6XSAcoUyrGtGa11vFChrifmq74dPZmUEtT1hDtwL1Ix3VPyZcJtTukKljxa0W0IwIe676X5HmiGhvk5qPPUImkXcZdQUK1gMdZmw0seMl5xmFG33kKVSD9H0p0JAEF4lOcDvjADQZtwLXY3qIhvYcKdOrIffrUAURnJRYnrB/MixizWvw252xBn9tmxpm68O3KsGBzcUwEB0Su</eBayAuthToken>\n" +
            "</RequesterCredentials>\n" +
            "<Pagination ComplexType=\"PaginationType\">\n" +
            "\t<EntriesPerPage>10</EntriesPerPage>\n" +
            "\t<PageNumber>1</PageNumber>\n" +
            "</Pagination>\n" +
            "<StartTimeFrom>2014-07-18T16:15:12.000Z</StartTimeFrom>\n" +
            "<StartTimeTo>2014-08-18T16:15:12.000Z</StartTimeTo>\n" +
            "<UserID>testuser_sandpoint</UserID>\n" +
            "<GranularityLevel>Coarse</GranularityLevel>\n" +
            "<DetailLevel>ItemReturnDescription</DetailLevel>\n" +
            "</GetSellerListRequest>​";

        List<Item> li = SamplePaseXml.getItemElememt(this.cosPostXml(colStr,APINameStatic.ListingItemList));
        modelMap.put("li",li);
        return forword("listingitem/listingitemList",modelMap);
    }

    @RequestMapping("/editListingItem.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView editListingItem(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
        String itemid = request.getParameter("itemid");
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<GetItemRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">\n" +
                "<RequesterCredentials>\n" +
                "<eBayAuthToken>AgAAAA**AQAAAA**aAAAAA**wV1JUQ**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6wFk4GhCpGGoA+dj6x9nY+seQ**cx0CAA**AAMAAA**2kuzIn+bBej1QDsDFfI2N74mj8psZYNYrtgX97fzWSGXO7EjvdlE9leu9HCY1bR9wdrzlAE7AKcT9Oz5BDNZbNQLS+uoifmNUM47lSqxWeYTQS2GtMK25LPYhxY+OQp6UVZ8lUh6Oqr91ub03emzufuZHo+6KSNJfNXMtOBVaB7PDeBQyNWoFBO0/LYiS5ql6HXB7vCj0W+K/iT4t3aPs5KlXAXjewM/Sa+nUDtjT9SseqrKrxdZx5fkAePeSrBs229tdCrkTtE0n+ZE9ppwJjElZpu7yfQL44McNa16KBxYYO0PnX7ENg2yMxf3H4aji0BEfB41lrC1LwhmNSebJGrJXRQVS9jmZyDqYiBdn1t536va/LPTP8kc3GZ7hnZRJuhMxoGGgx4ev5Hip0L7dk6cAPKHIkHUIjfA5pwVHEJZpvea+7uvwAh5pj9U7r6rmB9FXH2G9l+F5SytYlIXsDjwNtrEN53k5HrM0vhnGdd7pUwvyu7Nu4U5aPkZQZjTr6OrTWioDsZZwEz+pf0scw0IYweMhicCqMTNbvkJsj2cikX49C6XSAcoUyrGtGa11vFChrifmq74dPZmUEtT1hDtwL1Ix3VPyZcJtTukKljxa0W0IwIe676X5HmiGhvk5qPPUImkXcZdQUK1gMdZmw0seMl5xmFG33kKVSD9H0p0JAEF4lOcDvjADQZtwLXY3qIhvYcKdOrIffrUAURnJRYnrB/MixizWvw252xBn9tmxpm68O3KsGBzcUwEB0Su</eBayAuthToken>\n" +
                "</RequesterCredentials>\n" +
                "<ItemID>"+itemid+"</ItemID>\n" +
                "<DetailLevel>ReturnAll</DetailLevel>\n" +
                "</GetItemRequest>";
        String res = this.cosPostXml(xml,APINameStatic.GetItem);
        Item item = null;
        if(res!=null){
            item = SamplePaseXml.getItem(res);
        }
        modelMap.put("item",item);

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

        return forword("listingitem/editListingItem",modelMap);
    }


    @RequestMapping("/ajax/ListingItemList.do")
    @ResponseBody
    public void ListingItemList(ModelMap modelMap,CommonParmVO commonParmVO) throws Exception {

        /**分页组装*/
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        jsonBean.setPageCount(jsonBean.getPageCount());
        jsonBean.setPageNum(jsonBean.getPageNum());
        jsonBean.setTotal(100);

        String colStr="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<GetSellerListRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">\n" +
                "<RequesterCredentials>\n" +
                "<eBayAuthToken>AgAAAA**AQAAAA**aAAAAA**wV1JUQ**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6wFk4GhCpGGoA+dj6x9nY+seQ**cx0CAA**AAMAAA**2kuzIn+bBej1QDsDFfI2N74mj8psZYNYrtgX97fzWSGXO7EjvdlE9leu9HCY1bR9wdrzlAE7AKcT9Oz5BDNZbNQLS+uoifmNUM47lSqxWeYTQS2GtMK25LPYhxY+OQp6UVZ8lUh6Oqr91ub03emzufuZHo+6KSNJfNXMtOBVaB7PDeBQyNWoFBO0/LYiS5ql6HXB7vCj0W+K/iT4t3aPs5KlXAXjewM/Sa+nUDtjT9SseqrKrxdZx5fkAePeSrBs229tdCrkTtE0n+ZE9ppwJjElZpu7yfQL44McNa16KBxYYO0PnX7ENg2yMxf3H4aji0BEfB41lrC1LwhmNSebJGrJXRQVS9jmZyDqYiBdn1t536va/LPTP8kc3GZ7hnZRJuhMxoGGgx4ev5Hip0L7dk6cAPKHIkHUIjfA5pwVHEJZpvea+7uvwAh5pj9U7r6rmB9FXH2G9l+F5SytYlIXsDjwNtrEN53k5HrM0vhnGdd7pUwvyu7Nu4U5aPkZQZjTr6OrTWioDsZZwEz+pf0scw0IYweMhicCqMTNbvkJsj2cikX49C6XSAcoUyrGtGa11vFChrifmq74dPZmUEtT1hDtwL1Ix3VPyZcJtTukKljxa0W0IwIe676X5HmiGhvk5qPPUImkXcZdQUK1gMdZmw0seMl5xmFG33kKVSD9H0p0JAEF4lOcDvjADQZtwLXY3qIhvYcKdOrIffrUAURnJRYnrB/MixizWvw252xBn9tmxpm68O3KsGBzcUwEB0Su</eBayAuthToken>\n" +
                "</RequesterCredentials>\n" +
                "<Pagination ComplexType=\"PaginationType\">\n" +
                "\t<EntriesPerPage>"+jsonBean.getPageCount()+"</EntriesPerPage>\n" +
                "\t<PageNumber>"+jsonBean.getPageNum()+"</PageNumber>\n" +
                "</Pagination>\n" +
                "<StartTimeFrom>2014-09-06T16:15:12.000Z</StartTimeFrom>\n" +
                "<StartTimeTo>2014-09-10T18:15:12.000Z</StartTimeTo>\n" +
                "<UserID>testuser_sandpoint</UserID>\n" +
                "<GranularityLevel>Coarse</GranularityLevel>\n" +
                "<DetailLevel>ItemReturnDescription</DetailLevel>\n" +
                "</GetSellerListRequest>​";

        List<Item> li = SamplePaseXml.getItemElememt(this.cosPostXml(colStr,APINameStatic.ListingItemList));
        jsonBean.setList(li);
        AjaxSupport.sendSuccessText("", jsonBean);
    }


    public String cosPostXml(String colStr,String month) throws Exception {
        UsercontrollerDevAccountExtend d = userInfoService.getDevInfo(1L);
        d.setApiSiteid("0");
        d.setApiCallName(month);
        AddApiTask addApiTask = new AddApiTask();
        Map<String, String> resMap= addApiTask.exec(d, colStr, apiUrl);
        String res=resMap.get("message");
        String ack = SamplePaseXml.getVFromXmlString(res,"Ack");
        if(ack.equals("Success")){
            return res;
        }else{
            return res;
        }

    }

    /**
     * 保存运输选项数据
     * @param request
     * @throws Exception
     */
    @RequestMapping("/saveListingItem.do")
    @AvoidDuplicateSubmission(needRemoveToken = true)
    @ResponseBody
    public void saveListingItem(HttpServletRequest request,Item item,TradingItem tradingItem) throws Exception {
        String [] selectType = request.getParameterValues("selectType");
        String isUpdateFlag = request.getParameter("isUpdateFlag");
        if("1".equals(isUpdateFlag)){//需要更新范本
            TradingItem tradingItem1=this.iTradingItem.selectByItemId(item.getItemID());
            if(tradingItem1!=null){//更新数据库中的范本
                this.iTradingItem.updateTradingItem(item,tradingItem1);
            }
        }
        Item ite = new Item();
        for(String str : selectType){
           if(str.equals("StartPrice")){//改价格
               ite.setStartPrice(item.getStartPrice());
           }else if(str.equals("Quantity")){//改数量
               ite.setQuantity(item.getQuantity());
           }else if(str.equals("PictureDetails")){//改图片
               ite.setPictureDetails(item.getPictureDetails());
           }else if(str.equals("PayPal")){//改支付方式
               ite.setPayPalEmailAddress(item.getPayPalEmailAddress());
           }else if(str.equals("ReturnPolicy")){//改退货政策
               ite.setReturnPolicy(item.getReturnPolicy());
           }else if(str.equals("Title")){//改标题　
               ite.setTitle(item.getTitle());
           }else if(str.equals("Buyer")){//改买家要求
               ite.setBuyerRequirementDetails(item.getBuyerRequirementDetails());
           }else if(str.equals("SKU")){//改ＳＫＵ
               ite.setSKU(item.getSKU());
           }else if(str.equals("PrimaryCategory")){//改分类
               ite.setPrimaryCategory(item.getPrimaryCategory());
           }else if(str.equals("ConditionID")){//改商品状态
               ite.setConditionID(item.getConditionID());
           }else if(str.equals("Location")){//改运输到的地址
               ite.setLocation(item.getLocation());
           }else if(str.equals("DispatchTimeMax")){//最快处理时间
               ite.setDispatchTimeMax(item.getDispatchTimeMax());
           }else if(str.equals("PrivateListing")){//改是否允许私人买
               ite.setPrivateListing(item.getPrivateListing());
           }else if(str.equals("ListingDuration")){//改刊登天数
               ite.setListingDuration(item.getListingDuration());
           }else if(str.equals("Description")){//改商品描述
               ite.setDescription(item.getDescription());
           }else if(str.equals("ShippingDetails")){//改运输详情
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
           }
        }
        ite.setItemID(item.getItemID());
        String xml = "";
        ReviseItemRequest rir = new ReviseItemRequest();
        rir.setXmlns("urn:ebay:apis:eBLBaseComponents");
        rir.setErrorLanguage("en_US");
        rir.setWarningLevel("High");
        RequesterCredentials rc = new RequesterCredentials();
        //生产
        //rc.seteBayAuthToken("AgAAAA**AQAAAA**aAAAAA**W2xvUQ**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6AFlICkCpSGoA6dj6x9nY+seQ**GNABAA**AAMAAA**Vn4yBA7u+ZDMqwb6Sdip+KaomBablhv7dCVnFt5ksAUd7RjjA4ANJ4TQVoIAQ35NZQzalPoKaGzLBFhURJa2xpJPj/BMSb0ihuql4NDVCUOsPFoWMVPIwQdVQ6dZ29DL66dBcuiRgJsTakDttxgK02lfiBgiEP0YCruAhjIKFzZPSivuvkSqKn2HIFKjJq0VDlCvqaBgYkGm26ITKH9dQj/Ql9jK3BHeWA6GSZ+nR9HPIufHLdNpT4axILEd3Lg2X/d34+QoP46rGb4iwO64AzvOXcF//WE4MuJsTQ4d6qgw6DOajpDBL0PNq1n6HItAylImyPRzfvU8hw8neigieh3CtmjzjJ81bY/swlFQdPlV6zZVE99pegMT0DO9Fms5la8W3MSeoHgWdq4i7AR6GBjlh9W9x8z05I91wOx2wNJb0ETcbwl0YbWxs72K49FYF12CZbXQytfJZNLHi+X9/jFgf4TfdrJgagMhUqP9M6Of3R2POF/4+9j/y7s11M6aWw2oxsJ6VAZQKZXtZ5T6/UfP89VA7M1t68R6f6kVr5hoD5glQa2lIw6bIQR4tubYPTAhg5uPCjWifEwYJoV5VuwAk/WHKEvihNHrYGu3c1SMuJlHatLBx7vSNrFsPFWsmP6Z3I6bBRyjSY57KQwxM3SHJvvbYO8etfU+S1gCXuvFMarCCgxv8MhdDUhA/F6A3QE+KjW91xKz8BQ/UJKBS5kOJF13xqSh+j/zoH6EVmRDLvD0uAW7xsSAiMuwT5Kq");
        //测试
        rc.seteBayAuthToken("AgAAAA**AQAAAA**aAAAAA**wV1JUQ**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6wFk4GhCpGGoA+dj6x9nY+seQ**cx0CAA**AAMAAA**2kuzIn+bBej1QDsDFfI2N74mj8psZYNYrtgX97fzWSGXO7EjvdlE9leu9HCY1bR9wdrzlAE7AKcT9Oz5BDNZbNQLS+uoifmNUM47lSqxWeYTQS2GtMK25LPYhxY+OQp6UVZ8lUh6Oqr91ub03emzufuZHo+6KSNJfNXMtOBVaB7PDeBQyNWoFBO0/LYiS5ql6HXB7vCj0W+K/iT4t3aPs5KlXAXjewM/Sa+nUDtjT9SseqrKrxdZx5fkAePeSrBs229tdCrkTtE0n+ZE9ppwJjElZpu7yfQL44McNa16KBxYYO0PnX7ENg2yMxf3H4aji0BEfB41lrC1LwhmNSebJGrJXRQVS9jmZyDqYiBdn1t536va/LPTP8kc3GZ7hnZRJuhMxoGGgx4ev5Hip0L7dk6cAPKHIkHUIjfA5pwVHEJZpvea+7uvwAh5pj9U7r6rmB9FXH2G9l+F5SytYlIXsDjwNtrEN53k5HrM0vhnGdd7pUwvyu7Nu4U5aPkZQZjTr6OrTWioDsZZwEz+pf0scw0IYweMhicCqMTNbvkJsj2cikX49C6XSAcoUyrGtGa11vFChrifmq74dPZmUEtT1hDtwL1Ix3VPyZcJtTukKljxa0W0IwIe676X5HmiGhvk5qPPUImkXcZdQUK1gMdZmw0seMl5xmFG33kKVSD9H0p0JAEF4lOcDvjADQZtwLXY3qIhvYcKdOrIffrUAURnJRYnrB/MixizWvw252xBn9tmxpm68O3KsGBzcUwEB0Su");
        rir.setRequesterCredentials(rc);
        rir.setItem(ite);
        xml = PojoXmlUtil.pojoToXml(rir);
        xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"+xml;
        System.out.println(xml);

        String returnString = this.cosPostXml(xml,APINameStatic.ReviseItem);
        String ack = SamplePaseXml.getVFromXmlString(returnString,"Ack");
        if("Success".equalsIgnoreCase(ack)||"Warning".equalsIgnoreCase(ack)){
            AjaxSupport.sendSuccessText("message", "操作成功！");
        }else{
            Document document= DocumentHelper.parseText(returnString);
            Element rootElt = document.getRootElement();
            Element tl = rootElt.element("Errors");
            String longMessage = tl.elementText("LongMessage");
            if(longMessage==null){
                longMessage = tl.elementText("ShortMessage");
            }
            AjaxSupport.sendFailText("fail",longMessage);
        }

    }


}
