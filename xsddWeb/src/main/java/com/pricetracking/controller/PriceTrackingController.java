package com.pricetracking.controller;

import com.base.database.trading.model.TradingPriceTracking;
import com.base.domains.CommonParmVO;
import com.base.domains.SessionVO;
import com.base.domains.querypojos.PriceTrackingQuery;
import com.base.mybatis.page.Page;
import com.base.mybatis.page.PageJsonBean;
import com.base.utils.cache.SessionCacheSupport;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
import com.trading.service.ITradingPriceTracking;
import org.apache.commons.lang.StringUtils;
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
    @Value("${EBAY.FINDING.KEY.API.URL}")
    private String findingkeyapiUrl;
    @Autowired
    private ITradingPriceTracking iTradingPriceTracking;
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
        List<TradingPriceTracking> trackings= iTradingPriceTracking.selectPriceTrackingByTileAndSeller(itemid,title,queryTitle,sessionVO.getId(),sellerusername);
        if(trackings!=null&&trackings.size()>0){
            tracking.setId(trackings.get(0).getId());
        }
        iTradingPriceTracking.savePriceTracking(tracking);
        AjaxSupport.sendSuccessText("", "保存成功");
    }
    //自定义调价初始化
    @RequestMapping("/setPrice.do")
    public ModelAndView addComment(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        return forword("/priceTracking/setPrice",modelMap);
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
}
