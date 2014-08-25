package com.base.sampleapixml;

import com.base.database.trading.model.TradingOrderGetAccount;
import com.base.utils.common.DateUtils;
import com.base.utils.xmlutils.SamplePaseXml;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrtor on 2014/8/22.
 */
public class GetAccountAPI {
    public static List<TradingOrderGetAccount> parseXMLAndSave(String res) throws Exception {
        List<TradingOrderGetAccount> list=new ArrayList<TradingOrderGetAccount>();
        Document document= DocumentHelper.parseText(res);
        Element root=document.getRootElement();
        Element AccountEntries=root.element("AccountEntries");
        if(AccountEntries!=null){
            TradingOrderGetAccount getAccount=new TradingOrderGetAccount();
            Iterator iterator=AccountEntries.elementIterator("AccountEntry");
            while(iterator.hasNext()){
                Element account= (Element) iterator.next();
                getAccount.setAccountdetailsentrytype(SamplePaseXml.getSpecifyElementText(account,"AccountDetailsEntryType"));
                getAccount.setDescription(SamplePaseXml.getSpecifyElementText(account,"Description"));
                String Date=SamplePaseXml.getSpecifyElementText(account,"Date");
                getAccount.setDate(DateUtils.returnDate(Date));
                getAccount.setGrossdetailamount(SamplePaseXml.getSpecifyElementText(account,"GrossDetailAmount"));
                getAccount.setItemid(SamplePaseXml.getSpecifyElementText(account,"ItemID"));
                getAccount.setNetdetailamount(SamplePaseXml.getSpecifyElementText(account,"NetDetailAmount"));
                getAccount.setRefnumber(SamplePaseXml.getSpecifyElementText(account,"RefNumber"));
                getAccount.setVatpercent(SamplePaseXml.getSpecifyElementText(account,"VATPercent"));
                getAccount.setTitle(SamplePaseXml.getSpecifyElementText(account,"Title"));
                getAccount.setOrderlineitemid(SamplePaseXml.getSpecifyElementText(account,"OrderLineItemID"));
                getAccount.setTransactionid(SamplePaseXml.getSpecifyElementText(account,"TransactionID"));
                list.add(getAccount);
            }
        }
        return list;
    }
}
