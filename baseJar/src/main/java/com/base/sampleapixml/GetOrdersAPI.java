package com.base.sampleapixml;

import com.base.database.trading.model.TradingOrderGetOrders;
import com.base.database.trading.model.TradingOrderShippingDetails;
import com.base.utils.common.DateUtils;
import com.base.utils.xmlutils.SamplePaseXml;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.*;

/**
 * Created by Administrtor on 2014/8/14.
 */
public class GetOrdersAPI {

    public static List<TradingOrderGetOrders> parseXMLAndSave(String res) throws Exception {
        Document document= DocumentHelper.parseText(res);
        Element root=document.getRootElement();
        Element orderArray=root.element("OrderArray");
        List<TradingOrderGetOrders> lists=new ArrayList<TradingOrderGetOrders>();
        Map<String,Object> map=new HashMap();
        if(orderArray!=null){
            Iterator<Element> iterator =orderArray.elementIterator("Order");
            TradingOrderShippingDetails sd=new TradingOrderShippingDetails();
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
                    String paidtime=SamplePaseXml.getSpecifyElementText(order,"PaidTime");
                    String shippedtime=SamplePaseXml.getSpecifyElementText(order,"ShippedTime");
                    Date PaidTime=DateUtils.returnDate(paidtime);
                    Date ShippedTime=DateUtils.returnDate(shippedtime);
                    getorder.setPaidtime(PaidTime);
                    getorder.setShippedtime(ShippedTime);
                    //-------------------获取第二层的---------------------------
                    getorder.setEbaypaymentstatus(SamplePaseXml.getSpecifyElementText(order,"CheckoutStatus","eBayPaymentStatus"));
                    getorder.setPaymentmethod(SamplePaseXml.getSpecifyElementText(order,"CheckoutStatus","PaymentMethod"));
                    getorder.setStatus(SamplePaseXml.getSpecifyElementText(order,"CheckoutStatus","Status"));
                    getorder.setLastmodifiedtime(DateUtils.returnDate(SamplePaseXml.getSpecifyElementText(order,"CheckoutStatus","LastModifiedTime")));
                    getorder.setSelectedshippingservice(SamplePaseXml.getSpecifyElementText(order,"ShippingServiceSelected","ShippingService"));
                    String selectdShippingServiceCost=SamplePaseXml.getSpecifyElementText(order,"ShippingServiceSelected","ShippingServiceCost");
                    if(selectdShippingServiceCost!=null){
                        getorder.setSelectedshippingservicecost(Double.valueOf(selectdShippingServiceCost));
                    }
                   /* Element details=order.element("ShippingDetails");
                    String percent=SamplePaseXml.getSpecifyElementText(details, "SalesTaxPercent");
                    if(percent!=null){
                        sd.setSalestaxpercent(Double.valueOf(percent));
                    }
                    sd.setSalestaxstate(SamplePaseXml.getSpecifyElementText(details, "SalesTaxState"));
                    sd.setSalestaxamount(SamplePaseXml.getSpecifyElementText(details, "SalesTaxAmount"));
                    String number=SamplePaseXml.getSpecifyElementText(details, "SellingManagerSalesRecordNumber");
                    if(number!=null){
                        sd.setSellingmanagersalesrecordnumber(Integer.valueOf(number));
                    }
                    sd.setGetitfast(SamplePaseXml.getSpecifyElementText(details, "GetItFast"));
                    map.put(ShippingDetails,sd);*/
                  /*  iTradingOrderShippingDetails.saveOrderShippingDetails(sd);*/
                    /*Iterator options=details.elementIterator("ShippingServiceOptions");
                    List<TradingOrderShippingServiceOptions> optionlist=new ArrayList<TradingOrderShippingServiceOptions>();
                    while(options.hasNext()){
                        Element option= (Element) options.next();
                        TradingOrderShippingServiceOptions sso=new TradingOrderShippingServiceOptions();
                        sso.setShippingservice(SamplePaseXml.getSpecifyElementText(option,"ShippingService"));
                        String priority=SamplePaseXml.getSpecifyElementText(option, "ShippingServicePriority");
                        if(priority!=null){
                            sso.setShippingservicepriority(Integer.valueOf(priority));
                        }
                        sso.setExpeditedservice(SamplePaseXml.getSpecifyElementText(option,"ExpeditedService"));
                        String max=SamplePaseXml.getSpecifyElementText(option, "ShippingTimeMax");
                        String min=SamplePaseXml.getSpecifyElementText(option, "ShippingTimeMin");
                        if(max!=null){
                            sso.setShippingtimemax(Integer.valueOf(max));
                        }
                        if(min!=null){
                            sso.setShippingtimemin(Integer.valueOf(min));
                        }
                       *//* sso.setShippingdetailsId(sd.getId());*//*
                        optionlist.add(sso);
                       *//* iTradingOrderShippingServiceOptions.saveOrderShippingServiceOptions(sso);*//*
                    }*/
                   /* map.put(OptionList,optionlist);*/
                    /*getorder.setShippingdetailsId(sd.getId());*/
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
                        String recordnumber=SamplePaseXml.getSpecifyElementText(transaction, "ShippingDetails", "SellingManagerSalesRecordNumber");
                        if(recordnumber!=null){
                            getorder.setSellingmanagersalesrecordnumber(Integer.valueOf(recordnumber));
                        }
                        getorder.setItemid(SamplePaseXml.getSpecifyElementText(transaction,"Item","ItemID"));
                        getorder.setTitle(SamplePaseXml.getSpecifyElementText(transaction,"Item","Title"));
                        String sku=SamplePaseXml.getSpecifyElementText(transaction,"Item","SKU");
                        getorder.setSku(sku);
                        String ActualShippingCost=SamplePaseXml.getSpecifyElementText(transaction,"ActualShippingCost");
                        String ActualHandlingCost=SamplePaseXml.getSpecifyElementText(transaction,"ActualHandlingCost");
                        if(ActualShippingCost!=null){
                            getorder.setActualshippingcost(Double.valueOf(ActualShippingCost));
                        }
                        if(ActualHandlingCost!=null){
                            getorder.setActualhandlingcost(Double.valueOf(ActualHandlingCost));
                        }
                        lists.add(getorder);
                    }
                }

            }
        }
       /* map.put(OrderList,lists);*/
        return lists;
    }
}
