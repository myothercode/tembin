package com.feedback.controller;

import com.base.database.customtrading.mapper.ItemReportMapper;
import com.base.database.trading.model.TradingFeedBackDetail;
import com.base.database.trading.model.TradingItemWithBLOBs;
import com.base.database.trading.model.TradingListingReport;
import com.base.domains.SessionVO;
import com.base.domains.querypojos.CommonParmVO;
import com.base.domains.querypojos.FeedBackReportQuery;
import com.base.domains.querypojos.ListingItemReportQuery;
import com.base.domains.querypojos.TablePriceQuery;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.mybatis.page.Page;
import com.base.mybatis.page.PageJsonBean;
import com.base.sampleapixml.APINameStatic;
import com.base.userinfo.service.UserInfoService;
import com.base.utils.cache.DataDictionarySupport;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.common.DateUtils;
import com.base.utils.threadpool.AddApiTask;
import com.base.utils.xmlutils.SamplePaseXml;
import com.common.base.utils.ajax.AjaxResponse;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
import com.trading.service.ITradingFeedBackDetail;
import com.trading.service.ITradingItem;
import com.trading.service.ITradingListingSuccess;
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
import java.util.*;

/**
 * Created by Administrtor on 2014/8/16.
 */
@Controller
public class FeedBackController extends BaseAction {
    @Autowired
    private ITradingFeedBackDetail iTradingFeedBackDetail;
    @Value("${EBAY.API.URL}")
    private String apiUrl;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private ITradingListingSuccess iTradingListingSuccess;
    @Autowired
    private ITradingItem iTradingItem;

    @RequestMapping("/ajax/saveFeedBackAll.do")
    @ResponseBody
    public void saveFeedBackAll(ModelMap modelMap,CommonParmVO commonParmVO) throws Exception {
        this.pingDaoList("Positive");
        this.pingDaoList("Neutral");
        this.pingDaoList("Negative");
        AjaxSupport.sendSuccessText("message", "操作成功！");
    }

    /**
     * 首页统计反馈信息
     * @param modelMap
     * @param commonParmVO
     * @throws Exception
     */
    @RequestMapping("/ajax/getFeedBackReportList.do")
    @ResponseBody
    public void getFeedBackReportList(ModelMap modelMap,CommonParmVO commonParmVO) throws Exception {
        Map basem = new HashMap();
        basem.put("0","Positive");
        basem.put("1","Neutral");
        basem.put("2","Negative");
        List<FeedBackReportQuery> li1 = this.iTradingFeedBackDetail.selectFeedBackReportList("1");//当天
        List<FeedBackReportQuery> li2 = this.iTradingFeedBackDetail.selectFeedBackReportList("2");//昨天
        List<FeedBackReportQuery> li3 = this.iTradingFeedBackDetail.selectFeedBackReportList("3");//本周
        List<FeedBackReportQuery> li4 = this.iTradingFeedBackDetail.selectFeedBackReportList("4");//上周
        List<FeedBackReportQuery> li5 = this.iTradingFeedBackDetail.selectFeedBackReportList("5");//本月
        List<FeedBackReportQuery> li6 = this.iTradingFeedBackDetail.selectFeedBackReportList("6");//上月
        List<Map> lim = new ArrayList<Map>();
        for(int i=0;i<basem.size();i++){
            Map mvalue = new HashMap();
            mvalue.put("dataType",basem.get(i+""));
            for(int j=0;j<li1.size();j++){
                if(basem.get(i+"").equals(li1.get(j).getReturnType())){
                    mvalue.put("day",li1.get(j).getTjNumber());
                }
            }
            if(mvalue.get("day")==null){
                mvalue.put("day","0");
            }

            for(int j=0;j<li2.size();j++){
                if(basem.get(i+"").equals(li2.get(j).getReturnType())){
                    mvalue.put("yesterday",li2.get(j).getTjNumber());
                }
            }
            if(mvalue.get("yesterday")==null){
                mvalue.put("yesterday","0");
            }
            for(int j=0;j<li3.size();j++){
                if(basem.get(i+"").equals(li3.get(j).getReturnType())){
                    mvalue.put("week",li3.get(j).getTjNumber());
                }
            }
            if(mvalue.get("week")==null){
                mvalue.put("week","0");
            }
            for(int j=0;j<li4.size();j++){
                if(basem.get(i+"").equals(li4.get(j).getReturnType())){
                    mvalue.put("thatweek",li4.get(j).getTjNumber());
                }
            }
            if(mvalue.get("thatweek")==null){
                mvalue.put("thatweek","0");
            }

            for(int j=0;j<li5.size();j++){
                if(basem.get(i+"").equals(li5.get(j).getReturnType())){
                    mvalue.put("month",li5.get(j).getTjNumber());
                }
            }
            if(mvalue.get("month")==null){
                mvalue.put("month","0");
            }
            for(int j=0;j<li6.size();j++){
                if(basem.get(i+"").equals(li6.get(j).getReturnType())){
                    mvalue.put("thatmonth",li6.get(j).getTjNumber());
                }
            }
            if(mvalue.get("thatmonth")==null){
                mvalue.put("thatmonth","0");
            }
            lim.add(mvalue);
        }
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        Page page=jsonBean.toPage();
        page.setPageSize(1000);
        jsonBean.setList(lim);
        jsonBean.setTotal((int)page.getTotalCount());
        AjaxSupport.sendSuccessText("",jsonBean);
        AjaxSupport.sendSuccessText("message", jsonBean);
    }

    @RequestMapping("/ajax/getCountSize.do")
    @ResponseBody
    public void getCountSize(ModelMap modelMap,CommonParmVO commonParmVO,HttpServletRequest request,HttpServletResponse response) throws Exception {
        if(request.getParameter("itemid")==null || "0".equals(request.getParameter("itemid"))){return;}
        TradingItemWithBLOBs tradingItemWithBLOBs = this.iTradingItem.selectByItemId(request.getParameter("itemid"));
        String x= (String) request.getParameter("jsonpCallback");
        Map m = new HashMap();
        m.put("itemid",request.getParameter("itemid"));
        m.put("commentType","Positive");
        int PositiveSize  = this.iTradingFeedBackDetail.selectByCount(m);
        m.put("commentType","Neutral");
        int NeutralSize  = this.iTradingFeedBackDetail.selectByCount(m);
        m.put("commentType","Negative");
        int NegativeSize  = this.iTradingFeedBackDetail.selectByCount(m);

        String outStr = "";
        if("1".equals(tradingItemWithBLOBs.getAssessSetview())){
            outStr = "<tr><td><div>PositiveSize:"+PositiveSize+"</div></td></tr>";
        }else{
            outStr = "<tr><td><div>PositiveSize:"+PositiveSize+" | Neutral:"+NeutralSize+" | Negative:"+NegativeSize+"</div></td></tr>";
        }
        String returnStr= "{\"PositiveSize\":\""+PositiveSize+"\",\"NeutralSize\":\""+NeutralSize+"\",\"NegativeSize\":\""+NegativeSize+"\"}";
        /*AjaxSupport.sendSuccessText("returnStr",outStr);*/
        AjaxResponse.sendText(response,"text/plain",x+"({\"htmlStr\":\""+outStr+"\"})");
    }

    public void pingDaoList(String commentType) throws Exception {
        String res = this.cosPostXml(commentType,1);
        Element el = SamplePaseXml.getApiElement(res,"PaginationResult");
        String pagetStr = SamplePaseXml.getSpecifyElementText(el,"TotalNumberOfPages");
        String ack = SamplePaseXml.getVFromXmlString(res, "Ack");
        if(ack.equals("Success")){
            int pages = Integer.parseInt(pagetStr);
            for(int i = pages;i>0;i--){
                res = this.cosPostXml(commentType,i);
                List<TradingFeedBackDetail> litfb = SamplePaseXml.getFeedBackListElement(res);
                this.iTradingFeedBackDetail.saveFeedBackDetail(litfb);
            }
        }
    }

    public String cosPostXml(String commentType,int pageNumber) throws Exception {
        String colStr="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<GetFeedbackRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">" +
                "<RequesterCredentials>" +
                "  <eBayAuthToken>AgAAAA**AQAAAA**aAAAAA**CLSRUQ**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6AFlYCjDJGCqA+dj6x9nY+seQ**FdYBAA**AAMAAA**w2sMbwlQ7TBHWxj9EsVedHQRI3+lonY9MDfiyayQbnFkjEanjL/yMCpS/D2B9xHRzRx+ppxWZkRPgeAKJvNotPLLrVTuEzOl5M7pi6Tw8+pzcmIEsOh7HQO78JlyFlvLc/ruE6/hG0E/HO1UX76YBwxp00N9f1NNUpo5u36D/TYsx5O2jXFTKkCOHwz6RW9vtN6TU39aLm+JQme2+NfFFXnbX8MHzoUiX7Sty0R88ZpX5wLp8ZdgXCEc5zZDQziYB1MSXF9hsmby5wKbxFF+OvW/zKADThk1gprgAgnEOucyoao+cUMHopLlYgMbjnLzdCXP5F9z+fkYTnKF6AEl5eHBpcKQGbPzswnKebRoBVw+bI2I1C/iq+PvBUyndFAexjrvlDQbEKr6qb6AWRVTTfkW2ce6a0ixRuCTq35zEpWpfAqkSKo+X23d/Q4V8R30rDXotOWDZL6o408cMO+UQ17uVA2arA1JNkYfc/AZ0T0z7ze5o/yp93jJPlDgi05Ut4fpCAMZw3X85GxrTlbEtawWgoyUbmMuv4f6QHZLZAerOaJA8DRJkzkzjJJ025bp1HvAECOc4ggdv0cofu4q96shssgNYYZJUPM+q4+0fnGK0pxQTNY9SV6vSaVCVoTZJo6vefW7OiHX2/eLoPKFuUfsKXXEv9OY71gD1xzYg/rpCMAqCTq1dKqqyT1R5fxANnoRX7vwkq+7jkCj2fAfKTnHi9mSuBFsilKLmnsqqWy3IGShMgdxiQwBEk6IWi9C</eBayAuthToken>" +
                "</RequesterCredentials>" +
                "  <UserID>containyou</UserID>" +
                "  <CommentType>"+commentType+"</CommentType>" +
                "  <DetailLevel>ReturnAll</DetailLevel>" +
                "  <FeedbackType>FeedbackReceivedAsSeller</FeedbackType>" +
                "  <Pagination>" +
                "    <EntriesPerPage>100</EntriesPerPage>" +
                "    <PageNumber>"+pageNumber+"</PageNumber>" +
                "  </Pagination>" +
                "</GetFeedbackRequest>";

        UsercontrollerDevAccountExtend d = new UsercontrollerDevAccountExtend();
        d.setApiAppName("sandpoin-23af-4f47-a304-242ffed6ff5b");
        d.setApiCertName("165cae7e-4264-4244-adff-e11c3aea204e");
        d.setApiDevName("5d70d647-b1e2-4c7c-a034-b343d58ca425");
        d.setApiCompatibilityLevel("881");
        d.setApiSiteid("0");
        d.setApiCallName(APINameStatic.GetFeedbackRequest);
        AddApiTask addApiTask = new AddApiTask();
        Map<String, String> resMap= addApiTask.exec(d, colStr, "https://api.ebay.com/ws/api.dll");
        String res=resMap.get("message");
        return res;
    }

    /**
     * 首页统计刊登信息
     * @param modelMap
     * @param commonParmVO
     * @throws Exception
     */
    @RequestMapping("/ajax/getItemReportList.do")
    @ResponseBody
    public void getItemReportList(ModelMap modelMap,CommonParmVO commonParmVO) throws Exception {
        SessionVO c= SessionCacheSupport.getSessionVO();
        Map m = new HashMap();
        m.put("datestr", DateUtils.formatDate(new Date()));
        m.put("userid",c.getId());
        List<TradingListingReport> lim = this.iTradingListingSuccess.selectItemReportList(m);
        //当天刊登
        List<ListingItemReportQuery> dayListing = this.iTradingListingSuccess.selectListingItemReport(c.getId(),"1","1",null);
        //本周刊登
        List<ListingItemReportQuery> weekListing = this.iTradingListingSuccess.selectListingItemReport(c.getId(),"3","1",null);
        //本月刊登
        List<ListingItemReportQuery> monthListing = this.iTradingListingSuccess.selectListingItemReport(c.getId(),"5","1",null);
        //当天结束刊登
        List<ListingItemReportQuery> dayendListing = this.iTradingListingSuccess.selectListingItemReport(c.getId(),"1","2",null);
        //本周结束刊登
        List<ListingItemReportQuery> weekendListing = this.iTradingListingSuccess.selectListingItemReport(c.getId(),"3","2",null);
        //本月结束刊登
        List<ListingItemReportQuery> monthendListing = this.iTradingListingSuccess.selectListingItemReport(c.getId(),"5","2",null);

        //当天结束刊登买出去有
        List<ListingItemReportQuery> dayendListingSold = this.iTradingListingSuccess.selectListingItemReport(c.getId(),"1","2","1");
        //本周结束刊登买出去有
        List<ListingItemReportQuery> weekendListingSold = this.iTradingListingSuccess.selectListingItemReport(c.getId(),"3","2","1");
        //本月结束刊登买出去有
        List<ListingItemReportQuery> monthendListingSold = this.iTradingListingSuccess.selectListingItemReport(c.getId(),"5","2","1");


        //计算刊登费用
        //当天刊登费
        List<ListingItemReportQuery> dayListingFee = this.iTradingListingSuccess.selectListingItemReportFee(c.getId(),"1","1",null);
        //本周刊登费
        List<ListingItemReportQuery> weekListingFee = this.iTradingListingSuccess.selectListingItemReportFee(c.getId(),"3","1",null);
        //本月刊登费
        List<ListingItemReportQuery> monthListingFee = this.iTradingListingSuccess.selectListingItemReportFee(c.getId(),"5","1",null);


        //当天结束刊登费
        List<ListingItemReportQuery> dayListingEndFee = this.iTradingListingSuccess.selectListingItemReportFee(c.getId(),"1","2",null);
        //本周结束刊登费
        List<ListingItemReportQuery> weekListingEndFee = this.iTradingListingSuccess.selectListingItemReportFee(c.getId(),"3","2",null);
        //本月结束刊登费
        List<ListingItemReportQuery> monthListingEndFee = this.iTradingListingSuccess.selectListingItemReportFee(c.getId(),"5","2",null);


        //当天销售比
        List<ListingItemReportQuery> dayListingSales = this.iTradingListingSuccess.selectListingItemSales("1","1",null);
        //昨天销售比
        List<ListingItemReportQuery> yesterdayListingSales = this.iTradingListingSuccess.selectListingItemSales("2","1",null);
        //本周销售比
        List<ListingItemReportQuery> weekListingSales = this.iTradingListingSuccess.selectListingItemReportFee(c.getId(),"3","1",null);
        //上周销售比
        List<ListingItemReportQuery> thatweekListingSales = this.iTradingListingSuccess.selectListingItemReportFee(c.getId(),"4","1",null);
        //本月销售比
        List<ListingItemReportQuery> monthListingSales = this.iTradingListingSuccess.selectListingItemReportFee(c.getId(),"5","1",null);
        //上月销售比
        List<ListingItemReportQuery> thatmonthListingSales = this.iTradingListingSuccess.selectListingItemReportFee(c.getId(),"6","1",null);


        for(TradingListingReport tlr : lim){
            if(tlr.getDatatype().equals("1")&&dayListing.size()>0){
                tlr.setDay(dayListing.get(0).getTjNumber()==null?"0":dayListing.get(0).getTjNumber());
            }
            if(tlr.getDatatype().equals("1")&&weekListing.size()>0){
                tlr.setWeek(weekListing.get(0).getTjNumber()==null?"0":weekListing.get(0).getTjNumber());
            }
            if(tlr.getDatatype().equals("1")&&monthListing.size()>0){
                tlr.setMonth(monthListing.get(0).getTjNumber()==null?"0":monthListing.get(0).getTjNumber());
            }
            if(tlr.getDatatype().equals("2")&&dayendListing.size()>0){
                tlr.setDay(dayendListing.get(0).getTjNumber()==null?"0":dayendListing.get(0).getTjNumber());
            }
            if(tlr.getDatatype().equals("2")&&weekendListing.size()>0){
                tlr.setWeek(weekendListing.get(0).getTjNumber()==null?"0":weekendListing.get(0).getTjNumber());
            }
            if(tlr.getDatatype().equals("2")&&monthendListing.size()>0){
                tlr.setMonth(monthendListing.get(0).getTjNumber()==null?"0":monthendListing.get(0).getTjNumber());
            }
            if(tlr.getDatatype().equals("3")&&dayendListingSold.size()>0){
                tlr.setDay(dayendListingSold.get(0).getTjNumber()==null?"0":dayendListingSold.get(0).getTjNumber());
            }
            if(tlr.getDatatype().equals("3")&&weekendListingSold.size()>0){
                tlr.setWeek(weekendListingSold.get(0).getTjNumber()==null?"0":weekendListingSold.get(0).getTjNumber());
            }
            if(tlr.getDatatype().equals("3")&&monthendListingSold.size()>0){
                tlr.setMonth(monthendListingSold.get(0).getTjNumber()==null?"0":monthendListingSold.get(0).getTjNumber());
            }
            if(tlr.getDatatype().equals("4")&&dayListingSales.size()>0){
                tlr.setDay(dayListingSales.get(0).getTjNumber()==null?"0.00%":dayListingSales.get(0).getTjNumber()+"%");
            }
            if(tlr.getDatatype().equals("4")&&yesterdayListingSales.size()>0){
                tlr.setYesterday(yesterdayListingSales.get(0).getTjNumber()==null?"0.00%":yesterdayListingSales.get(0).getTjNumber()+"%");
            }
            if(tlr.getDatatype().equals("4")&&weekListingSales.size()>0){
                tlr.setWeek(weekListingSales.get(0).getTjNumber()==null?"0.00%":weekListingSales.get(0).getTjNumber()+"%");
            }
            if(tlr.getDatatype().equals("4")&&thatweekListingSales.size()>0){
                tlr.setThatweek(thatweekListingSales.get(0).getTjNumber()==null?"0.00%":thatweekListingSales.get(0).getTjNumber()+"%");
            }
            if(tlr.getDatatype().equals("4")&&monthListingSales.size()>0){
                tlr.setMonth(monthListingSales.get(0).getTjNumber()==null?"0.00%":monthListingSales.get(0).getTjNumber()+"%");
            }
            if(tlr.getDatatype().equals("4")&&thatmonthListingSales.size()>0){
                tlr.setThatmonth(thatmonthListingSales.get(0).getTjNumber()==null?"0.00%":thatmonthListingSales.get(0).getTjNumber()+"%");
            }
            if(tlr.getDatatype().equals("5")&&dayListingFee.size()>0){
                tlr.setDay(dayListingFee.get(0).getTjNumber()==null?"0.00":dayListingFee.get(0).getTjNumber());
            }
            if(tlr.getDatatype().equals("5")&&weekListingFee.size()>0){
                tlr.setWeek(weekListingFee.get(0).getTjNumber()==null?"0.00":weekListingFee.get(0).getTjNumber());
            }
            if(tlr.getDatatype().equals("5")&&monthListingFee.size()>0){
                tlr.setMonth(monthListingFee.get(0).getTjNumber()==null?"0.00":monthListingFee.get(0).getTjNumber());
            }
            if(tlr.getDatatype().equals("6")&&dayListingEndFee.size()>0){
                tlr.setDay(dayListingEndFee.get(0).getTjNumber()==null?"0.00":dayListingEndFee.get(0).getTjNumber());
            }
            if(tlr.getDatatype().equals("6")&&weekListingEndFee.size()>0){
                tlr.setWeek(weekListingEndFee.get(0).getTjNumber()==null?"0.00":weekListingEndFee.get(0).getTjNumber());
            }
            if(tlr.getDatatype().equals("6")&&monthListingEndFee.size()>0){
                tlr.setMonth(monthListingEndFee.get(0).getTjNumber()==null?"0.00":monthListingEndFee.get(0).getTjNumber());
            }

        }
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        Page page=jsonBean.toPage();
        page.setPageSize(1000);
        jsonBean.setList(lim);
        jsonBean.setTotal((int)page.getTotalCount());
        AjaxSupport.sendSuccessText("",jsonBean);
        AjaxSupport.sendSuccessText("message", jsonBean);
    }
}
