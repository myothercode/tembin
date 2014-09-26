package com.listingitem.controller;

import com.base.database.publicd.model.PublicUserConfig;
import com.base.database.trading.model.*;
import com.base.domains.CommonParmVO;
import com.base.domains.SessionVO;
import com.base.domains.querypojos.ItemQuery;
import com.base.domains.querypojos.ListingDataAmendQuery;
import com.base.domains.querypojos.PaypalQuery;
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
import com.trading.service.*;
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
    @Autowired
    private ITradingListingData iTradingListingData;
    @Autowired
    private IUsercontrollerEbayAccount iUsercontrollerEbayAccount;


    @RequestMapping("/getListingItemList.do")
    public ModelAndView getListingItemList(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
        /*String colStr="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "<GetSellerListRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">\n" +
            "<RequesterCredentials>\n" +
            "<eBayAuthToken>AgAAAA**AQAAAA**aAAAAA**vVcRVA**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6wFk4GhCpGGoA+dj6x9nY+seQ**cx0CAA**AAMAAA**Z2MB0OtmO4JsPFBZPcjclippjnZG4bwpcpXYRXDdc6wEppv5m/WiCvsjyTKWoVXCMxQl2se3U6Vn93oBL6zg8EcR3GCXCC3ZbTpEQ3lBX8avBrME9VHo0RcfcE7oLVtnBAuSffy3Dk5ICUNyU7g57/rHw8d5DnO3JeitpQcTLKAInt+sEZslri3wa4Mx0xgyFW5OF3w8mNK8ib8+57PTHcApnp8xRTAlIVuwW3F/fGbSFVReS07/MulzlFXBoW/ZPLq+L2aLFpn5s+IB5/gB0HoDo5uGzRnALmXxUz8BuwJMrUE29830W7xVSEaYSYsOcJyue6PjJKyZt0rXf8TNHusXCHX240dWllrjMVxS7pEHgKb/FKfd/79PH3rXTFmuexesXS6H1lRmHBBE1iknFwtzzS+UeN22Rd6W+hjSjuOHB33o2gMS5cOdVXHuHyOQ6VJU3bJL/eNDgyB+wz3HhZmz6sF+lmLIRKP82H1QXdlwdGdpVhAhyqnE4FH4qTgPBMxv6c4jRL5BRuyUZDLeJI1WXmaZ4pNMss+MiME7Qu+7bP7S2TZhmValbfW/FvqSrxR9LlHji7iQSsz2m56x5TLjOtkFWjRxmB6C1wzBVtzdILzbvmA/1+9RlMevalW6bg22irusiv7iuD/AnC9pZ0Sju2XK/7WpjVW4/lZyBmRbqHQJPbU/5MU3xrM8pTV8rZmPfQrRh2araaWGIBE5IW3gsTrETpRUQybXd/a107ee61GwXEUqVat1EfznFpIs</eBayAuthToken>\n" +
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
        modelMap.put("li",li);*/
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

        return forword("listingitem/listingitemList",modelMap);
    }

    @RequestMapping("/getListItemDataAmend.do")
    public ModelAndView getListItemDataAmend(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
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
        String amendType = request.getParameter("amendType");
        if(amendType!=null&&!"".equals(amendType)){
            modelMap.put("amendType",amendType);
        }
        String amendFlag = request.getParameter("amendFlag");
        if(amendFlag!=null&&!"".equals(amendFlag)){
            modelMap.put("amendFlag",amendFlag);
        }
        return forword("listingitem/listingdataAmend",modelMap);
    }

    @RequestMapping("/ajax/getListItemDataAmend.do")
    @ResponseBody
    public void getListItemDataAmend(HttpServletRequest request,ModelMap modelMap,CommonParmVO commonParmVO) throws Exception {
        Map map = new HashMap();
        SessionVO c= SessionCacheSupport.getSessionVO();
        map.put("userid",c.getId());
        String flag = request.getParameter("flag");
        if(flag!=null&&!"".equals(flag)){
            map.put("flag",flag);
        }
        String county = request.getParameter("county");
        if(county!=null&&!"".equals(county)){
            map.put("county",county);
        }
        String listingtype = request.getParameter("listingtype");
        if(listingtype!=null&&!"".equals(listingtype)){
            map.put("listingtype",listingtype);
        }
        String ebayaccount = request.getParameter("ebayaccount");
        if(ebayaccount!=null&&!"".equals(ebayaccount)){
            map.put("ebayaccount",ebayaccount);
        }
        String selectType = request.getParameter("selectType");
        if(selectType!=null&&!"".equals(selectType)){
            map.put("selectType",selectType);
        }
        String selectValue = request.getParameter("selectValue");
        if(selectValue!=null&&!"".equals(selectValue)){
            map.put("selectValue",selectValue);
        }
        String amendType = request.getParameter("amendType");
        if(amendType!=null&&!"".equals(amendType)){
            map.put("amendType",amendType);
        }
        String amendFlag = request.getParameter("amendFlag");
        if(amendFlag!=null&&!"".equals(amendFlag)){
            map.put("amendFlag",amendFlag);
        }
        /**分页组装*/
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        Page page=jsonBean.toPage();
        List<ListingDataAmendQuery> paypalli = this.iTradingListingData.selectAmendData(map, page);
        jsonBean.setList(paypalli);
        jsonBean.setTotal((int)page.getTotalCount());
        AjaxSupport.sendSuccessText("",jsonBean);
    }

    @RequestMapping("/listingdataManager.do")
    public ModelAndView listingdataManager(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
        SessionVO c= SessionCacheSupport.getSessionVO();
        //List<PublicUserConfig> ebayList = DataDictionarySupport.getPublicUserConfigByType(DataDictionarySupport.PUBLIC_DATA_DICT_PAYPAL, c.getId());
        List<UsercontrollerEbayAccount> ebayList = this.iUsercontrollerEbayAccount.selectUsercontrollerEbayAccountByUserId(c.getId());
        modelMap.put("ebayList",ebayList);
        return forword("listingitem/listingdataManager",modelMap);
    }

    @RequestMapping("/editListingItem.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView editListingItem(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
        String itemid = request.getParameter("itemid");
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<GetItemRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">\n" +
                "<RequesterCredentials>\n" +
                "<eBayAuthToken>AgAAAA**AQAAAA**aAAAAA**vVcRVA**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6wFk4GhCpGGoA+dj6x9nY+seQ**cx0CAA**AAMAAA**Z2MB0OtmO4JsPFBZPcjclippjnZG4bwpcpXYRXDdc6wEppv5m/WiCvsjyTKWoVXCMxQl2se3U6Vn93oBL6zg8EcR3GCXCC3ZbTpEQ3lBX8avBrME9VHo0RcfcE7oLVtnBAuSffy3Dk5ICUNyU7g57/rHw8d5DnO3JeitpQcTLKAInt+sEZslri3wa4Mx0xgyFW5OF3w8mNK8ib8+57PTHcApnp8xRTAlIVuwW3F/fGbSFVReS07/MulzlFXBoW/ZPLq+L2aLFpn5s+IB5/gB0HoDo5uGzRnALmXxUz8BuwJMrUE29830W7xVSEaYSYsOcJyue6PjJKyZt0rXf8TNHusXCHX240dWllrjMVxS7pEHgKb/FKfd/79PH3rXTFmuexesXS6H1lRmHBBE1iknFwtzzS+UeN22Rd6W+hjSjuOHB33o2gMS5cOdVXHuHyOQ6VJU3bJL/eNDgyB+wz3HhZmz6sF+lmLIRKP82H1QXdlwdGdpVhAhyqnE4FH4qTgPBMxv6c4jRL5BRuyUZDLeJI1WXmaZ4pNMss+MiME7Qu+7bP7S2TZhmValbfW/FvqSrxR9LlHji7iQSsz2m56x5TLjOtkFWjRxmB6C1wzBVtzdILzbvmA/1+9RlMevalW6bg22irusiv7iuD/AnC9pZ0Sju2XK/7WpjVW4/lZyBmRbqHQJPbU/5MU3xrM8pTV8rZmPfQrRh2araaWGIBE5IW3gsTrETpRUQybXd/a107ee61GwXEUqVat1EfznFpIs</eBayAuthToken>\n" +
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
    public void ListingItemList(HttpServletRequest request,ModelMap modelMap,CommonParmVO commonParmVO) throws Exception {
        Map map = new HashMap();
        SessionVO c= SessionCacheSupport.getSessionVO();
        map.put("userid",c.getId());
        String flag = request.getParameter("flag");
        if(flag!=null&&!"".equals(flag)){
            map.put("flag",flag);
        }
        String county = request.getParameter("county");
        if(county!=null&&!"".equals(county)){
            map.put("county",county);
        }
        String listingtype = request.getParameter("listingtype");
        if(listingtype!=null&&!"".equals(listingtype)){
            map.put("listingtype",listingtype);
        }
        String ebayaccount = request.getParameter("ebayaccount");
        if(ebayaccount!=null&&!"".equals(ebayaccount)){
            map.put("ebayaccount",ebayaccount);
        }
        String selectType = request.getParameter("selectType");
        if(selectType!=null&&!"".equals(selectType)){
            map.put("selectType",selectType);
        }
        String selectValue = request.getParameter("selectValue");
        if(selectValue!=null&&!"".equals(selectValue)){
            map.put("selectValue",selectValue);
        }
        /**分页组装*/
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        Page page=jsonBean.toPage();
        List<TradingListingData> paypalli = this.iTradingListingData.selectData(map,page);
        jsonBean.setList(paypalli);
        jsonBean.setTotal((int)page.getTotalCount());
        AjaxSupport.sendSuccessText("",jsonBean);

        /**分页组装*//*
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        jsonBean.setPageCount(jsonBean.getPageCount());
        jsonBean.setPageNum(jsonBean.getPageNum());


        String colStr="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<GetSellerListRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">\n" +
                "<RequesterCredentials>\n" +
                "<eBayAuthToken>AgAAAA**AQAAAA**aAAAAA**vVcRVA**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6wFk4GhCpGGoA+dj6x9nY+seQ**cx0CAA**AAMAAA**Z2MB0OtmO4JsPFBZPcjclippjnZG4bwpcpXYRXDdc6wEppv5m/WiCvsjyTKWoVXCMxQl2se3U6Vn93oBL6zg8EcR3GCXCC3ZbTpEQ3lBX8avBrME9VHo0RcfcE7oLVtnBAuSffy3Dk5ICUNyU7g57/rHw8d5DnO3JeitpQcTLKAInt+sEZslri3wa4Mx0xgyFW5OF3w8mNK8ib8+57PTHcApnp8xRTAlIVuwW3F/fGbSFVReS07/MulzlFXBoW/ZPLq+L2aLFpn5s+IB5/gB0HoDo5uGzRnALmXxUz8BuwJMrUE29830W7xVSEaYSYsOcJyue6PjJKyZt0rXf8TNHusXCHX240dWllrjMVxS7pEHgKb/FKfd/79PH3rXTFmuexesXS6H1lRmHBBE1iknFwtzzS+UeN22Rd6W+hjSjuOHB33o2gMS5cOdVXHuHyOQ6VJU3bJL/eNDgyB+wz3HhZmz6sF+lmLIRKP82H1QXdlwdGdpVhAhyqnE4FH4qTgPBMxv6c4jRL5BRuyUZDLeJI1WXmaZ4pNMss+MiME7Qu+7bP7S2TZhmValbfW/FvqSrxR9LlHji7iQSsz2m56x5TLjOtkFWjRxmB6C1wzBVtzdILzbvmA/1+9RlMevalW6bg22irusiv7iuD/AnC9pZ0Sju2XK/7WpjVW4/lZyBmRbqHQJPbU/5MU3xrM8pTV8rZmPfQrRh2araaWGIBE5IW3gsTrETpRUQybXd/a107ee61GwXEUqVat1EfznFpIs</eBayAuthToken>\n" +
                "</RequesterCredentials>\n" +
                "<Pagination ComplexType=\"PaginationType\">\n" +
                "\t<EntriesPerPage>"+jsonBean.getPageCount()+"</EntriesPerPage>\n" +
                "\t<PageNumber>"+jsonBean.getPageNum()+"</PageNumber>\n" +
                "</Pagination>\n" +
                "<StartTimeFrom>2014-06-06T16:15:12.000Z</StartTimeFrom>\n" +
                "<StartTimeTo>2014-09-10T18:15:12.000Z</StartTimeTo>\n" +
                "<UserID>testuser_sandpoint</UserID>\n" +
                "<GranularityLevel>Coarse</GranularityLevel>\n" +
                "<DetailLevel>ItemReturnDescription</DetailLevel>\n" +
                "</GetSellerListRequest>​";
        String res = this.cosPostXml(colStr,APINameStatic.ListingItemList);
        Document document= DocumentHelper.parseText(res);
        Element rootElt = document.getRootElement();
        Element totalElt = rootElt.element("PaginationResult");
        String totalCount = totalElt.elementText("TotalNumberOfEntries");
        jsonBean.setTotal(Integer.parseInt(totalCount));
        List<Item> li = SamplePaseXml.getItemElememt(res);
        jsonBean.setList(li);
        AjaxSupport.sendSuccessText("", jsonBean);*/
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
        TradingListingData tld = this.iTradingListingData.selectByItemid(item.getItemID());

        Item ite = new Item();
        List litla = new ArrayList();
        for(String str : selectType){
            TradingListingAmend tla = new TradingListingAmend();
            tla.setItem(Long.parseLong(tld.getItemId()));
            tla.setParentId(tld.getId());

           if(str.equals("StartPrice")){//改价格
               tla.setAmendType("StartPrice");
               tla.setContent("将价格从" + tld.getPrice() + "修改为" + item.getStartPrice().getValue());
               ite.setStartPrice(item.getStartPrice());
               tld.setPrice(item.getStartPrice().getValue());
           }else if(str.equals("Quantity")){//改数量
               tla.setAmendType("Quantity");
               tla.setContent("将数量从" + tld.getQuantity() + "修改为" + item.getQuantity());
               ite.setQuantity(item.getQuantity());
               tld.setQuantity(item.getQuantity().longValue());
           }else if(str.equals("PictureDetails")){//改图片
               tla.setAmendType("PictureDetails");
               tla.setContent("图片修改");
               ite.setPictureDetails(item.getPictureDetails());
           }else if(str.equals("PayPal")){//改支付方式
               tla.setAmendType("PayPal");
               tla.setContent("支付方式修改");
               ite.setPayPalEmailAddress(item.getPayPalEmailAddress());
           }else if(str.equals("ReturnPolicy")){//改退货政策
               tla.setAmendType("ReturnPolicy");
               tla.setContent("退货政策修改");
               ite.setReturnPolicy(item.getReturnPolicy());
           }else if(str.equals("Title")){//改标题　
               tla.setAmendType("Title");
               tla.setContent("标题修改为："+item.getTitle());
               ite.setTitle(item.getTitle());
               tld.setTitle(item.getTitle());
           }else if(str.equals("Buyer")){//改买家要求
               tla.setAmendType("Buyer");
               tla.setContent("修改买家要求");
               ite.setBuyerRequirementDetails(item.getBuyerRequirementDetails());
           }else if(str.equals("SKU")){//改ＳＫＵ
               tla.setAmendType("SKU");
               tla.setContent("SKU修改为："+item.getSKU());
               ite.setSKU(item.getSKU());
               tld.setSku(item.getSKU());
           }else if(str.equals("PrimaryCategory")){//改分类
               tla.setAmendType("PrimaryCategory");
               tla.setContent("商品分类修改为:"+item.getPrimaryCategory().getCategoryID());
               ite.setPrimaryCategory(item.getPrimaryCategory());
           }else if(str.equals("ConditionID")){//改商品状态
               tla.setAmendType("ConditionID");
               tla.setContent("修改商品状态");
               ite.setConditionID(item.getConditionID());
           }else if(str.equals("Location")){//改运输到的地址
               tla.setAmendType("Location");
               ite.setLocation(item.getLocation());
           }else if(str.equals("DispatchTimeMax")){//最快处理时间
               tla.setAmendType("DispatchTimeMax");
               tla.setContent("修改处理时间");
               ite.setDispatchTimeMax(item.getDispatchTimeMax());
           }else if(str.equals("PrivateListing")){//改是否允许私人买
               tla.setAmendType("PrivateListing");
               ite.setPrivateListing(item.getPrivateListing());
           }else if(str.equals("ListingDuration")){//改刊登天数
               tla.setAmendType("ListingDuration");
               tla.setContent("修改刊登天数为："+item.getListingDuration());
               ite.setListingDuration(item.getListingDuration());
           }else if(str.equals("Description")){//改商品描述
               tla.setContent("修改商品描述");
               tla.setAmendType("Description");
               ite.setDescription(item.getDescription());
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
           }
            litla.add(tla);
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
        rc.seteBayAuthToken("AgAAAA**AQAAAA**aAAAAA**vVcRVA**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6wFk4GhCpGGoA+dj6x9nY+seQ**cx0CAA**AAMAAA**Z2MB0OtmO4JsPFBZPcjclippjnZG4bwpcpXYRXDdc6wEppv5m/WiCvsjyTKWoVXCMxQl2se3U6Vn93oBL6zg8EcR3GCXCC3ZbTpEQ3lBX8avBrME9VHo0RcfcE7oLVtnBAuSffy3Dk5ICUNyU7g57/rHw8d5DnO3JeitpQcTLKAInt+sEZslri3wa4Mx0xgyFW5OF3w8mNK8ib8+57PTHcApnp8xRTAlIVuwW3F/fGbSFVReS07/MulzlFXBoW/ZPLq+L2aLFpn5s+IB5/gB0HoDo5uGzRnALmXxUz8BuwJMrUE29830W7xVSEaYSYsOcJyue6PjJKyZt0rXf8TNHusXCHX240dWllrjMVxS7pEHgKb/FKfd/79PH3rXTFmuexesXS6H1lRmHBBE1iknFwtzzS+UeN22Rd6W+hjSjuOHB33o2gMS5cOdVXHuHyOQ6VJU3bJL/eNDgyB+wz3HhZmz6sF+lmLIRKP82H1QXdlwdGdpVhAhyqnE4FH4qTgPBMxv6c4jRL5BRuyUZDLeJI1WXmaZ4pNMss+MiME7Qu+7bP7S2TZhmValbfW/FvqSrxR9LlHji7iQSsz2m56x5TLjOtkFWjRxmB6C1wzBVtzdILzbvmA/1+9RlMevalW6bg22irusiv7iuD/AnC9pZ0Sju2XK/7WpjVW4/lZyBmRbqHQJPbU/5MU3xrM8pTV8rZmPfQrRh2araaWGIBE5IW3gsTrETpRUQybXd/a107ee61GwXEUqVat1EfznFpIs");
        rir.setRequesterCredentials(rc);
        rir.setItem(ite);
        xml = PojoXmlUtil.pojoToXml(rir);
        xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"+xml;
        System.out.println(xml);

        String returnString = this.cosPostXml(xml,APINameStatic.ReviseItem);
        String ack = SamplePaseXml.getVFromXmlString(returnString,"Ack");
        if("Success".equalsIgnoreCase(ack)||"Warning".equalsIgnoreCase(ack)){
            this.saveAmend(litla,"1");
            this.iTradingListingData.updateTradingListingData(tld);
            AjaxSupport.sendSuccessText("message", "操作成功！");
        }else{
            this.saveAmend(litla,"0");
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

    public void saveAmend(List<TradingListingAmend> litlam,String isflag) throws Exception {
        for(TradingListingAmend tla : litlam){
            ObjectUtils.toInitPojoForInsert(tla);
            tla.setIsFlag(isflag);
            this.iTradingListingData.insertTradingListingAmend(tla);
        }
    }
}
