package com.base.sampleapixml;

import com.base.database.trading.model.TradingOrderGetOrders;
import com.base.utils.common.DateUtils;
import com.base.utils.xmlutils.SamplePaseXml;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrtor on 2014/8/14.
 */
public class GetOrdersAPI {
    public static List<TradingOrderGetOrders> parseXMLAndSave(String res) throws Exception {
        Document document= DocumentHelper.parseText(res);
        Element root=document.getRootElement();
        Element orderArray=root.element("OrderArray");
        List<TradingOrderGetOrders> lists=new ArrayList<TradingOrderGetOrders>();
        if(orderArray!=null){
            Iterator<Element> iterator =orderArray.elementIterator("Order");
            while(iterator.hasNext()){
                Element order=iterator.next();
                Element transactionArray=order.element("TransactionArray");
                if(transactionArray!=null){
                    Iterator<Element> it=transactionArray.elementIterator("Transaction");
                    TradingOrderGetOrders getorder=new TradingOrderGetOrders();
                    //--------解析order下面第一层
                    getorder.setOrderid(SamplePaseXml.getSpecifyElementText(order, "OrderID"));
                    getorder.setOrderstatus(SamplePaseXml.getSpecifyElementText(order,"OrderStatus"));
                    getorder.setAdjustmentamount(SamplePaseXml.getSpecifyElementText(order, "AdjustmentAmount"));
                    getorder.setAmountsaved(SamplePaseXml.getSpecifyElementText(order, "AmountSaved"));
                    getorder.setCreatinguserrole(SamplePaseXml.getSpecifyElementText(order, "CreatingUserRole"));
                    getorder.setPaymentmethods(SamplePaseXml.getSpecifyElementText(order, "PaymentMethods"));
                    getorder.setSubtotal(SamplePaseXml.getSpecifyElementText(order, "Subtotal"));
                    getorder.setTotal(SamplePaseXml.getSpecifyElementText(order, "Total"));
                    getorder.setDigitaldelivery(SamplePaseXml.getSpecifyElementText(order, "DigitalDelivery"));
                    getorder.setBuyeruserid(SamplePaseXml.getSpecifyElementText(order, "BuyerUserID"));
                    getorder.setSelleruserid(SamplePaseXml.getSpecifyElementText(order, "SellerUserID"));
                    String CreatedTime=SamplePaseXml.getSpecifyElementText(order,"CreatedTime");
                    Date time=DateUtils.returnDate(CreatedTime);
                    getorder.setCreatedtime(time);
                    //-------------------获取第二层的---------------------------
                    getorder.setEbaypaymentstatus(SamplePaseXml.getSpecifyElementText(order,"CheckoutStatus","eBayPaymentStatus"));
                    getorder.setPaymentmethod(SamplePaseXml.getSpecifyElementText(order,"CheckoutStatus","PaymentMethod"));
                    getorder.setStatus(SamplePaseXml.getSpecifyElementText(order,"CheckoutStatus","Status"));
                    getorder.setLastmodifiedtime(DateUtils.returnDate(SamplePaseXml.getSpecifyElementText(order,"CheckoutStatus","LastModifiedTime")));
                    getorder.setSalestaxpercent(SamplePaseXml.getSpecifyElementText(order,"ShippingDetails","SalesTax","SalesTaxPercent"));
                    getorder.setSalestaxamount(SamplePaseXml.getSpecifyElementText(order,"ShippingDetails","SalesTax","SalesTaxAmount"));
                    getorder.setShippingincludedintax(SamplePaseXml.getSpecifyElementText(order,"ShippingDetails","SalesTax","ShippingIncludedInTax"));
                    getorder.setSalestaxstate(SamplePaseXml.getSpecifyElementText(order,"ShippingDetails","SalesTax","SalesTaxState"));
                    getorder.setShippingservice(SamplePaseXml.getSpecifyElementText(order,"ShippingDetails","ShippingServiceOptions","ShippingService"));
                    getorder.setShippingservicepriority(SamplePaseXml.getSpecifyElementText(order,"ShippingDetails","ShippingServiceOptions","ShippingServicePriority"));
                    getorder.setExpeditedservice(SamplePaseXml.getSpecifyElementText(order,"ShippingDetails","ShippingServiceOptions","ExpeditedService"));
                    getorder.setSellingmanagersalesrecordnumber(SamplePaseXml.getSpecifyElementText(order,"ShippingDetails","SellingManagerSalesRecordNumber"));
                    getorder.setGetitfast(SamplePaseXml.getSpecifyElementText(order,"ShippingDetails","GetItFast"));
                    getorder.setName(SamplePaseXml.getSpecifyElementText(order,"ShippingAddress","Name"));
                    getorder.setStreet1(SamplePaseXml.getSpecifyElementText(order,"ShippingAddress","Street1"));
                    getorder.setStreet2(SamplePaseXml.getSpecifyElementText(order,"ShippingAddress","Street2"));
                    getorder.setCityname(SamplePaseXml.getSpecifyElementText(order,"ShippingAddress","CityName"));
                    getorder.setStateorprovince(SamplePaseXml.getSpecifyElementText(order,"ShippingAddress","StateOrProvince"));
                    getorder.setCountry(SamplePaseXml.getSpecifyElementText(order,"ShippingAddress","Country"));
                    getorder.setCountryname(SamplePaseXml.getSpecifyElementText(order,"ShippingAddress","CountryName"));
                    getorder.setPhone(SamplePaseXml.getSpecifyElementText(order,"ShippingAddress","Phone"));
                    getorder.setPostalcode(SamplePaseXml.getSpecifyElementText(order,"ShippingAddress","PostalCode"));
                    getorder.setAddressid(SamplePaseXml.getSpecifyElementText(order,"ShippingAddress","AddressID"));
                    getorder.setAddressowner(SamplePaseXml.getSpecifyElementText(order,"ShippingAddress","AddressOwner"));
                    getorder.setExternaladdressid(SamplePaseXml.getSpecifyElementText(order,"ShippingAddress","ExternalAddressID"));
                    while(it.hasNext()){
                        //-------------------解析order下面的transaction----------------------------------
                        Element transaction=it.next();
                        getorder.setQuantitypurchased(SamplePaseXml.getSpecifyElementText(transaction,"QuantityPurchased"));
                        getorder.setTransactionid(SamplePaseXml.getSpecifyElementText(transaction,"TransactionID"));
                        getorder.setTransactionprice(SamplePaseXml.getSpecifyElementText(transaction,"TransactionPrice"));
                        getorder.setBuyeremail(SamplePaseXml.getSpecifyElementText(transaction,"Buyer","Email"));
                        getorder.setSellingmanagersalesrecordnumber(SamplePaseXml.getSpecifyElementText(transaction,"ShippingDetails","SellingManagerSalesRecordNumber"));
                        getorder.setItemid(SamplePaseXml.getSpecifyElementText(transaction,"Item","ItemID"));
                        lists.add(getorder);
                    }
                }

            }
        }
        return lists;
    }
}
