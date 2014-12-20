package com.trading.service.impl;

import com.base.database.customtrading.mapper.PriceTrackingMapper;
import com.base.database.trading.mapper.TradingPriceTrackingMapper;
import com.base.database.trading.model.TradingPriceTracking;
import com.base.database.trading.model.TradingPriceTrackingExample;
import com.base.database.trading.model.TradingReseCategory;
import com.base.domains.querypojos.PriceTrackingQuery;
import com.base.mybatis.page.Page;
import com.base.utils.common.ObjectUtils;
import com.base.utils.httpclient.HttpClientUtil;
import com.base.utils.xmlutils.SamplePaseXml;
import org.apache.http.client.HttpClient;
import org.apache.http.message.BasicHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/8/14.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TradingPriceTrackingImpl implements com.trading.service.ITradingPriceTracking {
    @Autowired
    private TradingPriceTrackingMapper tradingPriceTrackingMapper;
    @Value("${EBAY.FINDING.KEY.API.URL}")
    private String findingkeyapiUrl;
    @Autowired
    private PriceTrackingMapper priceTrackingMapper;

    @Override
    public void savePriceTracking(TradingPriceTracking tradingPriceTracking) throws Exception {
        if(tradingPriceTracking.getId()==null){
            ObjectUtils.toInitPojoForInsert(tradingPriceTracking);
            this.tradingPriceTrackingMapper.insert(tradingPriceTracking);
        }else{
            this.tradingPriceTrackingMapper.updateByPrimaryKeySelective(tradingPriceTracking);
        }
    }

    @Override
    public List<TradingPriceTracking> getPriceTrackingItem(String title) throws Exception {
        List<TradingPriceTracking> list=new ArrayList<TradingPriceTracking>();
        String xml="<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+
                "<findItemsByKeywordsRequest xmlns=\"http://www.ebay.com/marketplace/search/v1/services\">"+
                "<keywords>" + title + "</keywords>"+
                "<outputSelector>SellerInfo</outputSelector>"+
                "<paginationInput>"+
                "<pageNumber>1</pageNumber><entriesPerPage>100</entriesPerPage>"+
                "</paginationInput>"+
                "</findItemsByKeywordsRequest>";
        List<TradingReseCategory> litr = new ArrayList<TradingReseCategory>();
              /*  TradingDataDictionary tdd = DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(siteid));*/
        if(title!=null&&!"".equals(title)) {
            List<BasicHeader> headers = new ArrayList<BasicHeader>();
            headers.add(new BasicHeader("X-EBAY-SOA-SERVICE-NAME", "FindingService"));
            headers.add(new BasicHeader("X-EBAY-SOA-OPERATION-NAME", "findItemsByKeywords"));
            headers.add(new BasicHeader("X-EBAY-SOA-SERVICE-VERSION", "1.12.0"));
            headers.add(new BasicHeader("X-EBAY-SOA-GLOBAL-ID", "EBAY-US"));
            headers.add(new BasicHeader("X-EBAY-SOA-SECURITY-APPNAME", "sandpoin-23af-4f47-a304-242ffed6ff5b"));
            headers.add(new BasicHeader("X-EBAY-SOA-REQUEST-DATA-FORMAT", "XML"));
            HttpClient httpClient = HttpClientUtil.getHttpsClient();
            String res = HttpClientUtil.post(httpClient, findingkeyapiUrl, xml, "UTF-8", headers);
            list=SamplePaseXml.getPriceTrackingItem(res,title);
        }
        return list;
    }

    @Override
    public List<TradingPriceTracking> selectPriceTrackingByItemId(String itemId) {
        TradingPriceTrackingExample example=new TradingPriceTrackingExample();
        TradingPriceTrackingExample.Criteria cr=example.createCriteria();
        cr.andItemidEqualTo(itemId);
        List<TradingPriceTracking> list=tradingPriceTrackingMapper.selectByExample(example);
        return list;
    }

    @Override
    public List<PriceTrackingQuery> selectPriceTrackingList(Map map, Page page) {
        return priceTrackingMapper.selectPriceTrackingList(map,page);
    }

    @Override
    public List<TradingPriceTracking> selectPriceTracking() {
        TradingPriceTrackingExample example=new TradingPriceTrackingExample();
        TradingPriceTrackingExample.Criteria cr=example.createCriteria();
        List<TradingPriceTracking> list=tradingPriceTrackingMapper.selectByExample(example);
        return list;
    }

}
