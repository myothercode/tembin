package com.pricetracking.controller;

import com.base.database.trading.model.TradingListingData;
import com.base.database.trading.model.TradingPriceTracking;
import com.base.database.trading.model.TradingPriceTrackingAutoPricing;
import com.base.database.trading.model.TradingPriceTrackingPricingRule;
import com.base.domains.CommonParmVO;
import com.base.domains.SessionVO;
import com.base.domains.querypojos.ListingDataQuery;
import com.base.domains.querypojos.PriceTrackingAutoPricingQuery;
import com.base.domains.querypojos.PriceTrackingAutoPricingRecordQuery;
import com.base.domains.querypojos.PriceTrackingQuery;
import com.base.mybatis.page.Page;
import com.base.mybatis.page.PageJsonBean;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.common.DateUtils;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
import com.trading.service.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
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
 * Created by Administrtor on 2014/12/11.
 */
@Controller
@RequestMapping("priceTracking")
public class PriceTrackingController extends BaseAction{

    static Logger logger = Logger.getLogger(PriceTrackingController.class);

    @Value("${EBAY.FINDING.KEY.API.URL}")
    private String findingkeyapiUrl;
    @Autowired
    private ITradingPriceTracking iTradingPriceTracking;
    @Autowired
    private ITradingListingData iTradingListingData;

    @Autowired
    private ITradingPriceTrackingAutoPricing iTradingPriceTrackingAutoPricing;

    @Autowired
    private ITradingPriceTrackingPricingRule iTradingPriceTrackingPricingRule;

    @Autowired
    private ITradingPriceTrackingAutoPricingRecord iTradingPriceTrackingAutoPricingRecord;

    @RequestMapping("/priceTrackingList.do")
    public ModelAndView queryOrdersList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        return forword("/priceTracking/priceTrackingList",modelMap);
    }
    @RequestMapping("/ajax/priceTrackingApi.do")
    @ResponseBody
    public void loadOrdersList(CommonParmVO commonParmVO,HttpServletRequest request) throws Exception {
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        String qeuryContent=request.getParameter("qeuryContent");
        List<TradingPriceTracking> priceTrackings=new ArrayList<TradingPriceTracking>();
        try {
            priceTrackings = iTradingPriceTracking.getPriceTrackingItem(qeuryContent);
        }catch (Exception e){
            logger.error("",e);
            priceTrackings=new ArrayList<TradingPriceTracking>();
        }
        jsonBean.setList(priceTrackings);
        jsonBean.setTotal(priceTrackings.size());
        AjaxSupport.sendSuccessText("", jsonBean);
    }
    /***/
    @RequestMapping("/ajax/savepriceTracking.do")
    @ResponseBody
    public void savepriceTracking(HttpServletRequest request) throws Exception {
        String itemid=request.getParameter("itemid");
        String sellerusername=request.getParameter("sellerusername");
        String currentprice=request.getParameter("currentprice");
        String currencyid=request.getParameter("currencyid");
        String title=request.getParameter("title");
        String queryTitle=request.getParameter("queryTitle");
        String bidcount=request.getParameter("bidcount");
        String starttime=request.getParameter("starttime");
        String endtime=request.getParameter("endtime");
        String shippingServiceCost=request.getParameter("shippingServiceCost");
        String shippingCurrencyId=request.getParameter("shippingCurrencyId");
        String pictureurl=request.getParameter("pictureurl");
        TradingPriceTracking tracking=new TradingPriceTracking();
        if(StringUtils.isNotBlank(itemid)){
            tracking.setItemid(itemid);
        }
        if(StringUtils.isNotBlank(starttime)){
            tracking.setStarttime(DateUtils.parseDateTime(starttime));
        }
        if(StringUtils.isNotBlank(endtime)){
            tracking.setEndtime(DateUtils.parseDateTime(endtime));
        }
        if(StringUtils.isNotBlank(sellerusername)){
            tracking.setSellerusername(sellerusername);
        }
        if(StringUtils.isNotBlank(currentprice)){
            tracking.setCurrentprice(currentprice);
        }
        if(StringUtils.isNotBlank(currencyid)){
            tracking.setCurrencyid(currencyid);
        }
        if(StringUtils.isNotBlank(title)){
            tracking.setTitle(title);
        }
        if(StringUtils.isNotBlank(queryTitle)){
            tracking.setQuerytitle(queryTitle);
        }
        if(StringUtils.isNotBlank(bidcount)){
            tracking.setBidcount(bidcount);
        }
        if(StringUtils.isNotBlank(shippingServiceCost)){
            tracking.setShippingservicecost(shippingServiceCost);
        }
        if(StringUtils.isNotBlank(shippingCurrencyId)){
            tracking.setShippingcurrencyid(shippingCurrencyId);
        }
        if(StringUtils.isNotBlank(pictureurl)){
            tracking.setPictureurl(pictureurl);
        }
        SessionVO sessionVO= SessionCacheSupport.getSessionVO();
        List<TradingPriceTracking> trackings= iTradingPriceTracking.selectPriceTrackingByItemId(itemid);
        if(trackings!=null&&trackings.size()>0){
            tracking.setId(trackings.get(0).getId());
        }
        iTradingPriceTracking.savePriceTracking(tracking);
        Map map=new HashMap();
        map.put("message","保存成功");
        map.put("itemid",itemid);
        AjaxSupport.sendSuccessText("", map);
    }
    //指定物品号来跟踪
    @RequestMapping("/ajax/saveItemPrice.do")
    @ResponseBody
    public void saveItemPrice(HttpServletRequest request) throws Exception {
        String itemId=request.getParameter("itemId");
        if(StringUtils.isNotBlank(itemId)){
            //TradingPriceTracking tracking=new TradingPriceTracking();
            List<TradingPriceTracking> trackings=iTradingPriceTracking.selectPriceTrackingByItemId(itemId);
            if(trackings!=null&&trackings.size()>0){
                AjaxSupport.sendFailText("fail","物品号已存在");
            }else{
                TradingPriceTracking tracking=new TradingPriceTracking();
                tracking.setItemid(itemId);
                iTradingPriceTracking.savePriceTracking(tracking);
                AjaxSupport.sendSuccessText("","保存成功");
            }
        }else{
            AjaxSupport.sendFailText("fail","物品号为空");
        }
    }
    //自定义调价初始化
    @RequestMapping("/setPrice.do")
    public ModelAndView addComment(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        return forword("/priceTracking/setPrice",modelMap);
    }

    //添加竞争对手
    @RequestMapping("/addCompetitors.do")
    public ModelAndView addCompetitors(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        return forword("/priceTracking/addCompetitors",modelMap);
    }
    //自定义调价初始化
    @RequestMapping("/autoSetPrice.do")
    public ModelAndView autoSetPrice(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        return forword("/priceTracking/autoSetPrice",modelMap);
    }
    //指定商品价格跟踪
    @RequestMapping("/assignItem.do")
    public ModelAndView assignItem(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        return forword("/priceTracking/assignItem",modelMap);
    }

    //搜索竞争对手
    @RequestMapping("/searchCompetitors.do")
    public ModelAndView searchCompetitors(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        return forword("/priceTracking/searchCompetitors",modelMap);
    }
    @RequestMapping("/ajax/priceTrackingQueryList.do")
    @ResponseBody
    public void priceTrackingQueryList(CommonParmVO commonParmVO,HttpServletRequest request) throws Exception {
        String qeuryContent=request.getParameter("qeuryContent");
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        Page page=jsonBean.toPage();
        Map map=new HashMap();
        if(!StringUtils.isNotBlank(qeuryContent)){
            qeuryContent=null;
        }
        SessionVO sessionVO= SessionCacheSupport.getSessionVO();
        map.put("userId",sessionVO.getId());
        map.put("qeuryContent",qeuryContent);
        List<PriceTrackingQuery> priceTrackings=iTradingPriceTracking.selectPriceTrackingList(map,page);
        jsonBean.setList(priceTrackings);
        jsonBean.setTotal((int)page.getTotalCount());
        AjaxSupport.sendSuccessText("", jsonBean);
    }
    @RequestMapping("/viewPricingRecord.do")
    public ModelAndView viewPricingRecord(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        String autoPricingId=request.getParameter("autoPricingId");
        modelMap.put("autoPricingId",autoPricingId);
        return forword("/priceTracking/viewPricingRecord",modelMap);
    }
    @RequestMapping("/ajax/priceTrackingRecordQueryList.do")
    @ResponseBody
    public void priceTrackingRecordQueryList(CommonParmVO commonParmVO,HttpServletRequest request) throws Exception {
        String autoPricingId=request.getParameter("autoPricingId");
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        Page page=jsonBean.toPage();
        Map map=new HashMap();
        if(!StringUtils.isNotBlank(autoPricingId)){
            autoPricingId=null;
        }
        SessionVO sessionVO= SessionCacheSupport.getSessionVO();
        map.put("userId",sessionVO.getId());
        map.put("autoPricingId",autoPricingId);
        List<PriceTrackingAutoPricingRecordQuery> priceTrackings=iTradingPriceTrackingAutoPricingRecord.selectPriceTrackingAutoPricingRecordList(map,page);
        jsonBean.setList(priceTrackings);
        jsonBean.setTotal((int)page.getTotalCount());
        AjaxSupport.sendSuccessText("", jsonBean);
    }
    @RequestMapping("/ajax/priceTrackingQueryList1.do")
    @ResponseBody
    public void priceTrackingQueryList1(CommonParmVO commonParmVO,HttpServletRequest request) throws Exception {
        String qeuryContent=request.getParameter("qeuryContent");
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        Page page=jsonBean.toPage();
        Map map=new HashMap();
        if(!StringUtils.isNotBlank(qeuryContent)){
            qeuryContent=null;
        }
        SessionVO sessionVO= SessionCacheSupport.getSessionVO();
        map.put("userId",sessionVO.getId());
        map.put("qeuryContent",qeuryContent);
        List<PriceTrackingAutoPricingQuery> priceTrackings= iTradingPriceTrackingAutoPricing.selectPriceTrackingAutoPricingList(map,page);
        for(PriceTrackingAutoPricingQuery query:priceTrackings){
            List<TradingPriceTrackingPricingRule> rules= iTradingPriceTrackingPricingRule.selectTradingPriceTrackingPricingRuleByAutoPricingId(query.getId());
            Long priceId=0L;
            String competitorsItemids="";
            for(TradingPriceTrackingPricingRule rule:rules){
                competitorsItemids+=rule.getCompetitoritemid()+",";
            }
            if(StringUtils.isNotBlank(competitorsItemids)){
                query.setCompetitorsItemids(competitorsItemids.substring(0,competitorsItemids.length()-1));
            }
            if(rules!=null&&rules.size()>0){
                query.setCompetitorsTotal(rules.size());
                query.setSign(rules.get(0).getSign());
                query.setIncreaseOrDecrease(rules.get(0).getIncreaseordecrease());
                query.setInputValue(rules.get(0).getInputvalue());
                query.setRuleType(rules.get(0).getRuletype());
            }
        }
        jsonBean.setList(priceTrackings);
        jsonBean.setTotal((int)page.getTotalCount());
        AjaxSupport.sendSuccessText("", jsonBean);
    }
    @RequestMapping("/ajax/loadItemListing.do")
    @ResponseBody
    public void loadItemListing(CommonParmVO commonParmVO,HttpServletRequest request) throws Exception {
        String content=request.getParameter("content");
        SessionVO sessionVO= SessionCacheSupport.getSessionVO();
        Map map=new HashMap();
        map.put("content",content);
        map.put("userId",sessionVO.getId());
        Page page=new Page();
        page.setPageSize(10);
        page.setCurrentPage(1);
        List<ListingDataQuery> datas=iTradingListingData.selectListDateByExample(map, page);
        AjaxSupport.sendSuccessText("",datas);
    }
    //调价
    @RequestMapping("/ajax/saveAutoPriceListingDate.do")
    @ResponseBody
    public void saveAutoPriceListingDate(CommonParmVO commonParmVO,HttpServletRequest request) throws Exception {
        String itemIds=request.getParameter("itemIds");
        String id=request.getParameter("itemId");
        String competitorPrice=request.getParameter("competitorPrice");
        String competitorPriceAdd=request.getParameter("competitorPriceAdd");
        String competitorPriceInput=request.getParameter("competitorPriceInput");
        String competitorPriceSymbol=request.getParameter("competitorPriceSymbol");
        if(StringUtils.isNotBlank(id)){
            TradingListingData data=iTradingListingData.selectById(Long.valueOf(id));
            String sku=data.getSku();
            if("0".equals(competitorPrice)){
                AjaxSupport.sendFailText("fail","请先添加规则");
                return;
            }
            if(!StringUtils.isNotBlank(itemIds)){
                AjaxSupport.sendFailText("fail","请添加竞争对手");
                return;
            }
            TradingPriceTrackingAutoPricing pricing=iTradingPriceTrackingAutoPricing.selectPriceTrackingAutoPricingByListingDateId(Long.valueOf(id));
            TradingPriceTrackingAutoPricing autoPricing=new TradingPriceTrackingAutoPricing();
            if(pricing!=null){
                autoPricing.setId(pricing.getId());
            }
            autoPricing.setSku(sku);
            autoPricing.setListingdateId(Long.valueOf(id));
            iTradingPriceTrackingAutoPricing.savePriceTrackingAutoPricing(autoPricing);
            List<TradingPriceTrackingPricingRule> rules=iTradingPriceTrackingPricingRule.selectTradingPriceTrackingPricingRuleByAutoPricingId(autoPricing.getId());
            if(rules!=null&&rules.size()>0){
                for(TradingPriceTrackingPricingRule pricingRule:rules){
                    iTradingPriceTrackingPricingRule.deleteTradingPriceTrackingPricingRule(pricingRule);
                }
            }
            String[] itemids1=itemIds.split(",");
            for(String itemid:itemids1){
                TradingPriceTrackingPricingRule rule=new TradingPriceTrackingPricingRule();
                if("1".equals(competitorPrice)){
                    rule.setRuletype("priceLowerType");
                }
                if("2".equals(competitorPrice)){
                    rule.setRuletype("priceHigherType");
                }
                rule.setAutopricingId(autoPricing.getId());
                rule.setIncreaseordecrease(competitorPriceAdd);
                rule.setSign(competitorPriceSymbol);
                rule.setInputvalue(competitorPriceInput);
                rule.setCompetitoritemid(itemid);
                iTradingPriceTrackingPricingRule.saveTradingPriceTrackingPricingRule(rule);
            }
            AjaxSupport.sendSuccessText("","添加成功");
        }else{
            AjaxSupport.sendFailText("fail","SKU为空,请先输入SKU");
        }

    }

    @RequestMapping("/ajax/deleteAutoPricing.do")
    @ResponseBody
    public void deleteAutoPricing(CommonParmVO commonParmVO,HttpServletRequest request) throws Exception {
        String id=request.getParameter("id");
        TradingPriceTrackingAutoPricing autoPricing=iTradingPriceTrackingAutoPricing.selectPriceTrackingAutoPricingById(Long.valueOf(id));
        if(autoPricing!=null){
            List<TradingPriceTrackingPricingRule> rules=iTradingPriceTrackingPricingRule.selectTradingPriceTrackingPricingRuleByAutoPricingId(autoPricing.getId());
            if(rules!=null&&rules.size()>0){
                for(TradingPriceTrackingPricingRule rule:rules){
                    iTradingPriceTrackingPricingRule.deleteTradingPriceTrackingPricingRule(rule);
                }
            }
            iTradingPriceTrackingAutoPricing.deletePriceTrackingAutoPricing(autoPricing);
            AjaxSupport.sendSuccessText("","删除成功");
        }else{
            AjaxSupport.sendFailText("fail","没有该数据,请核实!");
        }
    }
    @RequestMapping("/ajax/addCompetitorsInformation.do")
    @ResponseBody
    public void addCompetitorsInformation(CommonParmVO commonParmVO,HttpServletRequest request) throws Exception {
        String id=request.getParameter("itemid");
        if(StringUtils.isNotBlank(id)){
            TradingListingData data=iTradingListingData.selectById(Long.valueOf(id));
            String sku=data.getSku();
            TradingPriceTrackingAutoPricing autoPricing= iTradingPriceTrackingAutoPricing.selectPriceTrackingAutoPricingByListingDateId(Long.valueOf(id));
            if(autoPricing!=null){
                List<TradingPriceTrackingPricingRule> rules= iTradingPriceTrackingPricingRule.selectTradingPriceTrackingPricingRuleByAutoPricingId(autoPricing.getId());
                List<Map> list=new ArrayList<Map>();
                Map map=new HashMap();
                if(rules.get(0).getRuletype().contains("price")){
                    map.put("price",rules.get(0));
                }
                if(rules.get(0).getRuletype().contains("rank")){
                    map.put("rank",rules.get(0));
                }
                map.put("rules",rules);
                list.add(map);
                AjaxSupport.sendSuccessText("",list);
            }else{
                AjaxSupport.sendFailText("fail","无自动调价");
            }
        }else{
            AjaxSupport.sendFailText("fail","SKU为空");
        }
    }
}
