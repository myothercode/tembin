package com.base.utils.scheduleabout.commontask;

import com.base.database.publicd.mapper.PublicDataDictMapper;
import com.base.database.publicd.model.PublicDataDict;
import com.base.database.trading.model.TradingDataDictionary;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.sampleapixml.APINameStatic;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.cache.TempStoreDataSupport;
import com.base.utils.httpclient.HttpClientUtil;
import com.base.utils.scheduleabout.BaseScheduledClass;
import com.base.utils.scheduleabout.Scheduledable;
import com.base.utils.threadpool.AddApiTask;
import com.base.utils.threadpool.TaskPool;
import com.base.utils.xmlutils.SamplePaseXml;
import com.test.service.Test1Service;
import com.test.service.TestService;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import sun.applet.Main;

import java.util.*;

/**
 * Created by Administrator on 2014/12/23.
 */
public class TestTaskRun extends BaseScheduledClass implements Scheduledable {
    static Logger logger = Logger.getLogger(TestTaskRun.class);

    String mark="";

    @Override
    public void setMark(String x) {
        this.mark=x;
    }

    @Override
    public String getMark() {
        return this.mark;
    }

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
        System.out.println("====="+this.mark);





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

    private static String getxml(String pagenumber){
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<GetSellerListRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">\n" +
                "<RequesterCredentials>\n" +
                "<eBayAuthToken>AgAAAA**AQAAAA**aAAAAA**kRx8VA**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6AGloWiAZCCogSdj6x9nY+seQ**blACAA**AAMAAA**d0Px77QqgOj2GHC7XDNXkRKusIUT1y5uPdXz87hiC9ghsh75Q6hQb3BRbKwkJsFz3BlORq7L8lEiHsqBnFzd65yK1MJ/CQMsY165Q+4Rw664b0dP3vnPzjeN3cfKOkDwwoLqFGrMclvrrpntfSDBcO/r1QaC+CUB0GD6UiuhdyhBIPd1gb+z0KmYCTwpFENyHDzRtiTcT5qCt5eYfYzsve2e6O1c+NsTyBgJzUD1v78aIluxKhoC+huF9Uxscm2DU4mOr0JYONHJCs3dN18fKLp0Dc3hSvmPSIaxPmjcvlVfWuVPtw6KwXvxw8U8PGUdfACzb9ZIBiUEEhFHU6xv73egj2hkN/ZTJr7yu3l+qvDJFHLlgBMoprseFc0tmDi/hbRUILxuOy8TOpGri71DoQBzwuQxxrG5GMJ77NFLOLYxsH6/gpA/7+vFT1X5CUsIv+BYZyY7g3RLZWYem3Gqv9T+sVNC/DEhxmdO1Yx49rAwHcUw3aeXTrKpa1xCNkgHg4Feheu5V6Pu9lb5DQUC9YidqELrLEvos6yoiH31myqAmI72Gt4i7SBjwS8k5O+7xjxhDrKpg0IFwCdQk4PEByoBnud/dDNyCZkZdCqTkb36aqmgdnTANz9M7DtcQTH/Lf6h+Suj3RVSeFfDZcJJDax7Ie5qwte+oHJ6yTuBZ2dt4hMmKZIZwn26Ei+DUfCPhx6nEqcAOf6Sbxf8RxkWJ2pLcIvbifrditHIuyGjOf4yMoIHOcSp6FsVbmkMleBG</eBayAuthToken>\n" +
                "</RequesterCredentials>\n" +
                "<Pagination ComplexType=\"PaginationType\">\n" +
                "<EntriesPerPage>100</EntriesPerPage>\n" +
                "<PageNumber>"+pagenumber+"</PageNumber>\n" +
                "</Pagination>\n" +
                "<UserID>shopjoy999</UserID>\n" +
                "<EndTimeFrom>2015-01-02T07:45:39.000Z</EndTimeFrom>\n" +
                "<EndTimeTo>2015-04-30T07:45:39.000Z</EndTimeTo>\n" +
                "<IncludeWatchCount>true</IncludeWatchCount>\n" +
                "<IncludeVariations>true</IncludeVariations>\n" +
                "<DetailLevel>ReturnAll</DetailLevel>\n" +
                "<Version>903</Version>\n" +
                "</GetSellerListRequest>";
        return xml;
    }



    public static String postServer(String xml){
        List<BasicHeader> headers = new ArrayList<BasicHeader>();
        headers.add(new BasicHeader("X-EBAY-API-COMPATIBILITY-LEVEL","903"));
        headers.add(new BasicHeader("X-EBAY-API-DEV-NAME", "bbafa7e7-2f98-4783-9c34-f403faeb007f"));
        headers.add(new BasicHeader("X-EBAY-API-APP-NAME","chengdul-6dfe-4b1b-905c-41b1bd716d72"));
        headers.add(new BasicHeader("X-EBAY-API-CERT-NAME","41bd760a-48ab-489b-87b1-f5d33a7044a6"));
        headers.add(new BasicHeader("X-EBAY-API-SITEID","0"));
        headers.add(new BasicHeader("X-EBAY-API-CALL-NAME", "GetMemberMessages"));
        HttpClient httpClient= HttpClientUtil.getHttpsClient();
        try {
            String res= HttpClientUtil.post(httpClient, "https://api.ebay.com/ws/api.dll", xml, "UTF-8", headers);
            return res;
        } catch (Exception e) {
            logger.error("===================",e);
            return "";
        }
    }
    public static void main(String[] args){
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<GetMemberMessagesRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">\n" +
                "<RequesterCredentials>\n" +
                "<eBayAuthToken>AgAAAA**AQAAAA**aAAAAA**5NOXVA**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6AFlIWnC5iEpAidj6x9nY+seQ**blACAA**AAMAAA**CZ8RElUP/IGF0VLNusPZTJy+mEX+76qobIHfTxZYNWZXJM9zozal/wnl7E2R3DxGy0yCHOoGTActbZXK+S3w8ba6IjYmo8yK3x09WZKyTiHVIg+ya3z1O4tG5K3YUkI4sSknxMSgm8BQj7Pnt6Yf8RjZBVIYODOHGKEgZt0iEEhFojTGFkQ/aIf+49xkZ4rs2c4JIpob3varAZE57izIHRreIcn0txHbrAPV4lim7DpZkuWvcIgsd7x3W1j3Zwd1xRHxJ7CkMLOilWOPrkkgWGp5ATfCxcxmvHsg5DYoTrMH+CdvZgnj3RUJ+v8gnjweiVYhqgX9SVkWtXY0EmTbfw1wvDWvHxo7TRwDOg8k3xQGlehmn1EuOiV92uNo5eeTsYLgrctY4lh3vENdpAKAqgCro4wWXe+Wog+wBwfFDcs3o92UN+YSl6EaO4hrIex8RuEsUHP16jiiLlI0cvpFn94OKrLGK9lPofL5iXn+7u1xlEKMSrkSXJkrVQOgh0FdvjEhjQPBKV+OatrXlCXP6ym1McGA21k1ivpOHDpXau857xS/rJdtZ56/kzEKayCJ08cOxgc2XjotKx0VmdqjKDxGuiAhkCn6EipNy04V/hT6sjeuab1lnwt/TQAzq2jk52JcKd6+A+Vzql2QsQGGnEMUTPE4Y+RW/G6z4gQSDWpZeRqF7MtmFhJKE8u4tf1C0aUnWeaXLDGyxFqhPO8I3Nt3YEr4yphZVUEqnnBtmJa0QyLSKfrvAZkvt4X8qvNO</eBayAuthToken>\n" +
                "</RequesterCredentials>\n" +
                "<MailMessageType EnumType=\"MessageTypeCodeType\">All</MailMessageType>\n" +
                "</GetMemberMessagesRequest>";
        try {
            System.out.println(postServer(xml));
            /*Map<String, String> resMap = addApiTask.exec2(d, xml, "https://api.ebay.com/ws/api.dll");
            res = resMap.get("message");
            saveXml = res;
            String ack = SamplePaseXml.getVFromXmlString(res, "Ack");
            if (ack.equals("Success")) {
                Document document = SamplePaseXml.formatStr2Doc(res);
                Element rootElt = document.getRootElement();
                Element totalElt = rootElt.element("PaginationResult");
                String totalCount = totalElt.elementText("TotalNumberOfEntries");
                String page = totalElt.elementText("TotalNumberOfPages");
                for (int i = 1; i <= Integer.parseInt(page); i++) {
                    String colXml = getxml(i + "");
                    Map<String, String> resMapxml = addApiTask.exec2(d, colXml, "https://api.ebay.com/ws/api.dll");
                    String returnstr = resMapxml.get("message");
                    System.out.println(i+">>>>>>>>>>"+returnstr);
                }
            }*/
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
