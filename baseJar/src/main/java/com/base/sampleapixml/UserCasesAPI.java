package com.base.sampleapixml;

import com.base.database.trading.model.TradingGetUserCases;
import com.base.utils.common.DateUtils;
import com.base.utils.xmlutils.SamplePaseXml;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.*;

/**
 * Created by Administrtor on 2014/8/29.
 */
public class UserCasesAPI {
    public static Map<String,Object> parseXMLAndSave(String res) throws Exception {
        Map<String,Object> map=new HashMap<String, Object>();
        List<TradingGetUserCases> list=new ArrayList<TradingGetUserCases>();
        Document document= SamplePaseXml.formatStr2Doc(res);
        Element root=document.getRootElement();
        Element cases=root.element("cases");
        if(cases!=null){
            Iterator caseSummary=cases.elementIterator("caseSummary");
            String totalPages=SamplePaseXml.getSpecifyElementText(root,"paginationOutput","totalPages");
            while(caseSummary.hasNext()){
                TradingGetUserCases userCases=new TradingGetUserCases();
                Element summary= (Element) caseSummary.next();
                userCases.setCaseid(SamplePaseXml.getSpecifyElementText(summary,"caseId","id"));
                userCases.setCasetype(SamplePaseXml.getSpecifyElementText(summary, "caseId", "type"));
                String seller=SamplePaseXml.getSpecifyElementText(summary,"user","role");
                if("SELLER".equals(seller)){
                    userCases.setSellerid(SamplePaseXml.getSpecifyElementText(summary,"user","userId"));
                }else{
                    userCases.setBuyerid(SamplePaseXml.getSpecifyElementText(summary,"user","userId"));
                }
                String buyer=SamplePaseXml.getSpecifyElementText(summary,"otherParty","role");
                if("BUYER".equals(buyer)){
                    userCases.setBuyerid(SamplePaseXml.getSpecifyElementText(summary,"otherParty","userId"));
                }else{
                    userCases.setSellerid(SamplePaseXml.getSpecifyElementText(summary,"otherParty","userId"));
                }
                Element status=summary.element("status");
                Iterator DisStatus=status.elementIterator();
                while(DisStatus.hasNext()){
                    Element disStatus= (Element) DisStatus.next();
                    userCases.setStatus(disStatus.getTextTrim());
                }
                userCases.setItemid(SamplePaseXml.getSpecifyElementText(summary, "item", "itemId"));
                userCases.setItemtitle(SamplePaseXml.getSpecifyElementText(summary, "item", "itemTitle"));
                userCases.setTransactionid(SamplePaseXml.getSpecifyElementText(summary,"item","transactionId"));
                userCases.setCasequantity(SamplePaseXml.getSpecifyElementText(summary,"caseQuantity"));
                userCases.setCaseamount(SamplePaseXml.getSpecifyElementText(summary,"caseAmount"));
                String respondByDate=SamplePaseXml.getSpecifyElementText(summary,"respondByDate");
                userCases.setRespondbydate(DateUtils.returnDate(respondByDate));
                String creationDate=SamplePaseXml.getSpecifyElementText(summary,"creationDate");
                userCases.setCreationdate(DateUtils.returnDate(creationDate));
                String lastModifiedDate=SamplePaseXml.getSpecifyElementText(summary,"lastModifiedDate");
                userCases.setLastmodifieddate(DateUtils.returnDate(lastModifiedDate));
                list.add(userCases);
            }
            map.put("totalPages",totalPages);
            map.put("cases",list);
        }else{
            map.put("totalPages","0");
            map.put("cases",list);
        }
        return map;
    }
}
