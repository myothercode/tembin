package com.base.utils.scheduleabout.commontask;

import com.base.database.publicd.mapper.PublicDataDictMapper;
import com.base.database.publicd.model.PublicDataDict;
import com.base.database.trading.model.TradingDataDictionary;
import com.base.sampleapixml.APINameStatic;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.cache.TempStoreDataSupport;
import com.base.utils.httpclient.HttpClientUtil;
import com.base.utils.scheduleabout.BaseScheduledClass;
import com.base.utils.scheduleabout.Scheduledable;
import com.base.utils.threadpool.TaskPool;
import com.base.utils.xmlutils.SamplePaseXml;
import com.test.service.Test1Service;
import com.test.service.TestService;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Created by Administrator on 2014/12/23.
 */
public class TestTaskRun extends BaseScheduledClass implements Scheduledable {
    static Logger logger = Logger.getLogger(TestTaskRun.class);


    @Override
    public String getScheduledType() {
        return "Test_Test_test";
    }

    @Override
    public Integer crTimeMinu() {
        return 1;
    }

    @Override
    public void run() {
        Boolean b= TaskPool.threadIsAliveByName("thread_" + getScheduledType());
        if(b){
            logger.error(getScheduledType()+"===之前的任务还未完成继续等待下一个循环===");
            return;
        }
        logger.error(getScheduledType()+"===任务开始===");
        TaskPool.threadRunTime.put("thread_" + getScheduledType(), new Date());
        Thread.currentThread().setName("thread_" + getScheduledType());


        //TaskPool.threadRunTime.remove("thread_" + getScheduledType());
        logger.error(getScheduledType()+"===任务结束===");
        if(1==1){return;}

        String isRunging = TempStoreDataSupport.pullData("task_" + getScheduledType());
        if(StringUtils.isNotEmpty(isRunging)){return;}
        TempStoreDataSupport.pushData("task_"+getScheduledType(),"x");

        //PublicDataDictMapper dataDictMapper = ApplicationContextUtil.getBean(PublicDataDictMapper.class);

        //TestService testService = ApplicationContextUtil.getBean(TestService.class);
        Test1Service test1Service = ApplicationContextUtil.getBean(Test1Service.class);
        String site="311";
        String ebaysite="0";
        Map map =new HashMap();
        map.put("siteid",site);
        List<PublicDataDict> k=test1Service.selectpddhData(map);
        String xml="";

        for (PublicDataDict publicDataDict :k){
            xml="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                    "<GetCategoryFeaturesRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">\n" +
                    "<RequesterCredentials>\n" +
                    "<eBayAuthToken>AgAAAA**AQAAAA**aAAAAA**QM20Uw**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6wFk4GhCpGGoA+dj6x9nY+seQ**Sd0CAA**AAMAAA**a8K8fmx7s3vurAo/SGFiTl1PTp5pI/+JwbMuDqmBGpz/3++lJUPnHvIVgzjMBQtP89Qx/RDRY0s2Y10elagAew81uMb4EkWRIZIYw7YZdpp+ExQ5BUNnopAPRXeWz9ODtU2ujdk7m1y+wGLeNS8iFwP3bmHjcGyMLHDWvKkyBsky/y9kbpTmMbcry3SlUVykFG5cYeQFgpEjqJohfH6mX2T7NcD0L0eVWzrU4/Wh7NFpmGfxsgzN1e3FA89sLG6HZfJhSg+SBRT+dR29BAf2A9oVI6+yIctnfGPnHL68UrGzmgh+EgUf8aQW8n17zLEZEatUjpwlrAoRIH/CbG+gVZkSkGQyxI/WoqWtwAZKyAApvOSqGNYVRwef61GHMmAyf7eXojMBP3na1wMMAHpde6APji+3QiDlGT49T6wzcUA8TPRTTIQCYuqsEBY0tAjVrTEwcbsPUW9/533q8MNsVywH99VDme1fsKKLK4v93mZg9JzAMbdeNIfrtcH8CVQYQzv4n+xGNvchUD8pwGtZ98RxGk/8dZr0pEMZpcM70YNGLAtzbNEsPvfPdQfDUV6bgsHRBzySa+jAeCDesslrC3fanWJFFa/7YjLnNqdcVpWsC0V/uRWbdzOJ9mo2+sJelL5mPCgWS+YdFHYgdxYRnJCb/VBkkrm7IuSHUuBXWVdlaqs5Miu0fWIj0CZ/KYjBZK7XZyAN7LP1spAiFQJ2vo8/UCqcoay4ftMT68QgNcAyucVEr8gAF7k7RFDFEYSf</eBayAuthToken>\n" +
                    "</RequesterCredentials>\n" +
                    "<CategoryID>"+publicDataDict.getItemId()+"</CategoryID>\n" +
                    "<ViewAllNodes>true</ViewAllNodes>\n" +
                    "<AllFeaturesForCategory>true</AllFeaturesForCategory>\n" +
                    "<DetailLevel>ReturnAll</DetailLevel>\n" +
                    "</GetCategoryFeaturesRequest>";

            List<BasicHeader> headers = new ArrayList<BasicHeader>();
            headers.add(new BasicHeader("X-EBAY-API-COMPATIBILITY-LEVEL","885"));
            headers.add(new BasicHeader("X-EBAY-API-DEV-NAME", "5d70d647-b1e2-4c7c-a034-b343d58ca425"));
            headers.add(new BasicHeader("X-EBAY-API-APP-NAME","sandpoin-1f58-4e64-a45b-56507b02bbeb"));
            headers.add(new BasicHeader("X-EBAY-API-CERT-NAME","936fc911-c05c-455c-8838-3a698f2da43a"));
            headers.add(new BasicHeader("X-EBAY-API-SITEID",ebaysite));
            headers.add(new BasicHeader("X-EBAY-API-CALL-NAME", "GetCategoryFeatures"));
            HttpClient httpClient= HttpClientUtil.getHttpsClient();
            try {
                String res= HttpClientUtil.post(httpClient, "https://api.sandbox.ebay.com/ws/api.dll", xml.toString(), "UTF-8", headers);
                String vs=px(res);
                if(StringUtils.isEmpty(vs)){vs="false";}
                publicDataDict.setVariationsenabled(vs);

                test1Service.updateData(publicDataDict);
            } catch (Exception e) {
                logger.error("===================",e);
                continue;

            }
        }

        TempStoreDataSupport.removeData("task_" + getScheduledType());
    }



    private String px(String res){
        try {
            return SamplePaseXml.getSpecifyElementTextAllInOne(res,"Category","VariationsEnabled");
        } catch (Exception e) {
            return null;
        }
    }

}
