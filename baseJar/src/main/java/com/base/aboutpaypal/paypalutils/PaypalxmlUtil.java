package com.base.aboutpaypal.paypalutils;

import com.base.aboutpaypal.domain.PaypalVO;
import com.base.database.trading.model.UsercontrollerPaypalAccount;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * Created by Administrator on 2014/9/12.
 */
public class PaypalxmlUtil {
    /**获取paypal余额的请求报文*/
    public static String getBalanceXML(UsercontrollerPaypalAccount uspa){
String xml="<soapenv:Envelope  xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"  xmlns:ns=\"urn:ebay:api:PayPalAPI\" xmlns:ebl=\"urn:ebay:apis:eBLBaseComponents\" xmlns:cc=\"urn:ebay:apis:CoreComponentTypes\" xmlns:ed=\"urn:ebay:apis:EnhancedDataTypes\" >\n" +
        "  <soapenv:Header>\n" +
        "    <ns:RequesterCredentials>\n" +
        "      <ebl:Credentials>\n" +
        "        <ebl:Username>"+uspa.getApiUserName()+"</ebl:Username>\n" +
        "        <ebl:Password>"+uspa.getApiPassword()+"</ebl:Password>\n" +
        "        <ebl:Signature>"+uspa.getApiSignature()+"</ebl:Signature>\n" +
        "      </ebl:Credentials>\n" +
       /* "      <ebl:Credentials>\n" +
        "        <ebl:Username>caixu23_api1.gmail.com</ebl:Username>\n" +
        "        <ebl:Password>1403687298</ebl:Password>\n" +
        "        <ebl:Signature>AFcWxV21C7fd0v3bYYYRCpSSRl31ApdkglpEIkJRC2nCSqYF.9ncdnXs</ebl:Signature>\n" +
        "      </ebl:Credentials>\n" +*/
        "    </ns:RequesterCredentials>\n" +
        "  </soapenv:Header>\n" +
        "  <soapenv:Body>\n" +
        "    <ns:GetBalanceReq>\n" +
        "      <ns:GetBalanceRequest>\n" +
        "        <ebl:DetailLevel>ReturnAll</ebl:DetailLevel>\n" +
        "        <ebl:ErrorLanguage>en_US</ebl:ErrorLanguage>\n" +
        "        <ebl:Version>94.0</ebl:Version>\n" +
        "      </ns:GetBalanceRequest>\n" +
        "    </ns:GetBalanceReq>\n" +
        "  </soapenv:Body>\n" +
        "</soapenv:Envelope>";
        return xml;
    }

    /**解析paypal余额返回的xml*/
    public static PaypalVO getBalance(String xml) throws Exception {
        Element element=getSpecElement(xml,"Body","GetBalanceResponse","Balance");
        PaypalVO paypalVO=new PaypalVO();
        paypalVO.setBalance(element.getTextTrim());
        paypalVO.setUnitName(element.attributeValue("currencyID"));
        return paypalVO;
    }

    /**根据tranctionid获取交易的费用明细*/
    public static String getTransactionDetailsXML(UsercontrollerPaypalAccount uspa,PaypalVO paypalVO){
        String xml="<soapenv:Envelope  xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"  xmlns:ns=\"urn:ebay:api:PayPalAPI\" xmlns:ebl=\"urn:ebay:apis:eBLBaseComponents\" xmlns:cc=\"urn:ebay:apis:CoreComponentTypes\" xmlns:ed=\"urn:ebay:apis:EnhancedDataTypes\" >\n" +
                "  <soapenv:Header>\n" +
                "    <ns:RequesterCredentials>\n" +
                "      <ebl:Credentials>\n" +
                "        <ebl:Username>"+uspa.getApiUserName()+"</ebl:Username>\n" +
                "        <ebl:Password>"+uspa.getApiPassword()+"</ebl:Password>\n" +
                "        <ebl:Signature>"+uspa.getApiSignature()+"</ebl:Signature>\n" +
                "      </ebl:Credentials>\n" +
                /*"      <ebl:Credentials>\n" +
                "        <ebl:Username>caixu23_api1.gmail.com</ebl:Username>\n" +
                "        <ebl:Password>1403687298</ebl:Password>\n" +
                "        <ebl:Signature>AFcWxV21C7fd0v3bYYYRCpSSRl31ApdkglpEIkJRC2nCSqYF.9ncdnXs</ebl:Signature>\n" +
                "      </ebl:Credentials>\n" +*/
                "    </ns:RequesterCredentials>\n" +
                "  </soapenv:Header>\n" +
                "  <soapenv:Body>\n" +
                "    <ns:GetTransactionDetailsReq>\n" +
                "      <ns:GetTransactionDetailsRequest>\n" +
                "        <ns:TransactionID>"+paypalVO.getTransactionID()+"</ns:TransactionID>\n" +
                "        <ebl:Version>94.0</ebl:Version>\n" +
                "      </ns:GetTransactionDetailsRequest>\n" +
                "    </ns:GetTransactionDetailsReq>\n" +
                "  </soapenv:Body>\n" +
                "</soapenv:Envelope>";

        return xml;
    }

/**获取paypal交易的各项费用*/
    public static PaypalVO getTranDetail(String resXML) throws Exception {
        PaypalVO paypalVO=new PaypalVO();
        Element ackelement=getSpecElement(resXML,"Body","GetTransactionDetailsResponse","Ack");
        paypalVO.setAck(ackelement.getTextTrim());

        Element payelement=getSpecElement(resXML,"Body","GetTransactionDetailsResponse",
                "PaymentTransactionDetails","PaymentInfo");
        String tranID=payelement.element("TransactionID").getTextTrim();
        paypalVO.setTransactionID(tranID);
        paypalVO.setPaymentType(payelement.element("PaymentType").getTextTrim());
        paypalVO.setGrossAmount(payelement.element("GrossAmount").getTextTrim());
        paypalVO.setGrossAmountUnit(payelement.element("GrossAmount").attributeValue("currencyID"));

        paypalVO.setFeeAmount(payelement.element("FeeAmount").getTextTrim());
        paypalVO.setFeeAmountUnit(payelement.element("FeeAmount").attributeValue("currencyID"));

        paypalVO.setTaxAmount(payelement.element("TaxAmount").getTextTrim());
        paypalVO.setTaxAmountUnit(payelement.element("TaxAmount").attributeValue("currencyID"));
        return paypalVO;
    }


    /**获取指定的节点(指定获取单个的节点)*/
    public static Element getSpecElement(String xml,String... nodes) throws Exception {
        Document document= DocumentHelper.parseText(xml);
        Element rootElt = document.getRootElement();
        Element element=null;

        if(nodes!=null && nodes.length>0){
            for (int i =0;i<nodes.length;i++){
                if(element==null){element=rootElt;}
                element = element.element(nodes[i]);
            }
        }

        return element;
    }

}

