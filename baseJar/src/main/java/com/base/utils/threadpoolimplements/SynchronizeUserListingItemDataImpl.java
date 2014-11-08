package com.base.utils.threadpoolimplements;

import com.base.database.customtrading.mapper.ListingItemReportMapper;
import com.base.database.trading.mapper.TradingListingDataMapper;
import com.base.database.trading.model.*;
import com.base.domains.SessionVO;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.sampleapixml.APINameStatic;
import com.base.sampleapixml.GetEBPCaseDetailAPI;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.common.DateUtils;
import com.base.utils.threadpool.AddApiTask;
import com.base.utils.threadpool.TaskMessageVO;
import com.base.utils.xmlutils.SamplePaseXml;
import com.sitemessage.service.SiteMessageStatic;
import com.trading.service.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/9/13.
 * 用户点击在线同步商品
 */
@Service
public class SynchronizeUserListingItemDataImpl implements ThreadPoolBaseInterFace {
    static Logger logger = Logger.getLogger(SynchronizeUserListingItemDataImpl.class);
    @Autowired
    public TradingListingDataMapper tldm;
    @Value("${EBAY.API.URL}")
    private String apiUrl;
    @Autowired
    private ListingItemReportMapper listingItemReportMapper;
    @Autowired
    private ITradingListingSuccess iTradingListingSuccess;
    @Override
    public <T> void doWork(String ebpRes, T... t) {
        if(StringUtils.isEmpty(ebpRes)){return;}
        TaskMessageVO taskMessageVO = (TaskMessageVO)t[0];

        String[] xs= (String[]) taskMessageVO.getObjClass();

        String ebpAck = null;
        String ebayAccount = String.valueOf(xs[0]);
        String userid = String.valueOf(xs[1]);
        String token = String.valueOf(xs[2]);
        String siteid = String.valueOf(xs[3]);
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date beginDate = new Date();
        Calendar date = Calendar.getInstance();
        date.setTime(beginDate);
        date.set(Calendar.DATE, date.get(Calendar.DATE) - 119);
        String startTo="";
        String startFrom="";
        try {
            Date endDate = dft.parse(dft.format(date.getTime()));
            startTo = DateUtils.DateToString(new Date());
            startFrom = DateUtils.DateToString(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        AddApiTask addApiTask = new AddApiTask();
        UsercontrollerDevAccountExtend d = new UsercontrollerDevAccountExtend();
        d.setApiCallName(APINameStatic.ListingItemList);
        d.setApiSiteid(siteid);
        try {
            ebpAck = SamplePaseXml.getVFromXmlString(ebpRes, "Ack");
            if ("Success".equalsIgnoreCase(ebpAck)) {
                Document document= DocumentHelper.parseText(ebpRes);
                Element rootElt = document.getRootElement();
                Element totalElt = rootElt.element("PaginationResult");
                String totalCount = totalElt.elementText("TotalNumberOfEntries");
                String page =  totalElt.elementText("TotalNumberOfPages");
                for(int i=1;i<=Integer.parseInt(page);i++) {
                    String colStr = this.getCosXml(ebayAccount, startFrom, startTo, i, token);
                    Map<String, String> resMap = addApiTask.exec(d, colStr, apiUrl);
                    String res = resMap.get("message");
                    System.out.println(res);
                    String ack = SamplePaseXml.getVFromXmlString(res, "Ack");
                    if (ack.equals("Success")) {
                        List<TradingListingData> litld = SamplePaseXml.getItemListElememt(res, ebayAccount);
                        for(TradingListingData td : litld){
                            td.setEbayAccount(ebayAccount);
                            td.setCreateUser(Long.parseLong(userid));
                            TradingListingDataExample tlde  = new TradingListingDataExample();
                            tlde.createCriteria().andItemIdEqualTo(td.getItemId());
                            List<TradingListingData> litl = tldm.selectByExample(tlde);
                            if(litl!=null&&litl.size()>0){
                                TradingListingData oldtd = litl.get(0);
                                td.setId(oldtd.getId());
                                tldm.updateByPrimaryKeySelective(td);
                            }else{
                                tldm.insertSelective(td);
                            }
                            List<TradingListingSuccess> litls = this.iTradingListingSuccess.selectByItemid(td.getItemId());
                            if(litls==null||litls.size()==0){
                                TradingListingSuccess tls = new TradingListingSuccess();
                                tls.setItemId(td.getItemId());
                                tls.setStartDate(td.getStarttime());
                                tls.setEndDate(td.getEndtime());
                                this.iTradingListingSuccess.save(tls);
                            }else{
                                TradingListingSuccess tls = litls.get(0);
                                tls.setStartDate(td.getStarttime());
                                tls.setEndDate(td.getEndtime());
                                this.iTradingListingSuccess.save(tls);
                            }
                        }
                    }else{
                        continue;
                    }
                }
            }else {return;}
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("解析xml出错,请稍后到ebay网站确认结果");
            return;
        }
    }

    /**
     * 同步在线商品
     * @param ebayName
     * @param startTime
     * @param endTime
     * @param pageNumber
     * @param token
     * @return
     */
    public String getCosXml(String ebayName,String startTime,String endTime,int pageNumber,String token){
        String colStr="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<GetSellerListRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">\n" +
                "<RequesterCredentials>\n" +
                "<eBayAuthToken>"+token+"</eBayAuthToken>\n" +
                "</RequesterCredentials>\n" +
                "<Pagination ComplexType=\"PaginationType\">\n" +
                "\t<EntriesPerPage>100</EntriesPerPage>\n" +
                "\t<PageNumber>"+pageNumber+"</PageNumber>\n" +
                "</Pagination>\n" +
                "<StartTimeFrom>"+startTime+"</StartTimeFrom>\n" +
                "<StartTimeTo>"+endTime+"</StartTimeTo>\n" +
                "<UserID>"+ebayName+"</UserID>\n" +
                "<IncludeVariations>true</IncludeVariations><IncludeWatchCount>true</IncludeWatchCount>\n" +
                "<DetailLevel>ReturnAll</DetailLevel>\n" +
                "</GetSellerListRequest>​";
        return colStr;
    }
    @Override
    public String getType() {
        return SiteMessageStatic.SYN_MESSAGE_LISTING_DATA_BEAN;
    }
}
