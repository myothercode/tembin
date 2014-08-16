package com.feedback.controller;

import com.base.database.trading.model.TradingFeedBackDetail;
import com.base.domains.querypojos.CommonParmVO;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.sampleapixml.APINameStatic;
import com.base.userinfo.service.UserInfoService;
import com.base.utils.cache.DataDictionarySupport;
import com.base.utils.threadpool.AddApiTask;
import com.base.utils.xmlutils.SamplePaseXml;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
import com.trading.service.ITradingFeedBackDetail;
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
import java.util.List;
import java.util.Map;

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


    @RequestMapping("/ajax/saveFeedBackAll.do")
    @ResponseBody
    public void saveFeedBackAll(ModelMap modelMap,CommonParmVO commonParmVO) throws Exception {
        this.pingDaoList("Positive");
        this.pingDaoList("Neutral");
        this.pingDaoList("Negative");
        AjaxSupport.sendSuccessText("message", "操作成功！");
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
                System.out.println(":::::::::::::::::"+i);
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
}
