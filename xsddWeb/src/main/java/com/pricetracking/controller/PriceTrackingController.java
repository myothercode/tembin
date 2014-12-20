package com.pricetracking.controller;

import com.base.database.trading.model.TradingListingData;
import com.base.database.trading.model.TradingPriceTracking;
import com.base.domains.CommonParmVO;
import com.base.domains.SessionVO;
import com.base.domains.querypojos.ListingDataQuery;
import com.base.domains.querypojos.PriceTrackingQuery;
import com.base.mybatis.page.Page;
import com.base.mybatis.page.PageJsonBean;
import com.base.utils.cache.SessionCacheSupport;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
import com.trading.service.ITradingListingData;
import com.trading.service.ITradingPriceTracking;
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
        TradingPriceTracking tracking=new TradingPriceTracking();
        if(StringUtils.isNotBlank(itemid)){
            tracking.setItemid(itemid);
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
        SessionVO sessionVO= SessionCacheSupport.getSessionVO();
        List<TradingPriceTracking> trackings= iTradingPriceTracking.selectPriceTrackingByItemId(itemid);
        if(trackings!=null&&trackings.size()>0){
            tracking.setId(trackings.get(0).getId());
        }
        iTradingPriceTracking.savePriceTracking(tracking);
        AjaxSupport.sendSuccessText("", "保存成功");
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
        List<PriceTrackingQuery> priceTrackings= iTradingPriceTracking.selectPriceTrackingList(map,page);
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
        String itemId=request.getParameter("itemId");
        String quantity=request.getParameter("quantity");
        String percent=request.getParameter("percent");
        if(StringUtils.isNotBlank(itemId)){
            TradingListingData data=iTradingListingData.selectByItemid(itemId);
            if(data!=null){
                Integer qua=Integer.valueOf(quantity);
                if(data.getQuantity()<qua){
                    Double percent1=Double.valueOf(percent);
                    Double price=data.getPrice()*(1+percent1);
                    data.setPrice(price);
                }else{
                    Double percent1=Double.valueOf(percent);
                    Double price=data.getPrice()*(1-percent1);
                    data.setPrice(price);
                }
                AjaxSupport.sendSuccessText("","调价成功");
            }else{
                AjaxSupport.sendFailText("fail","在线商品中没有找到");
            }
        }else{
            AjaxSupport.sendFailText("fail","物品号为空");
        }
    }
}
