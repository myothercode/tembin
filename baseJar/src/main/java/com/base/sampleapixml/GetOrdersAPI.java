package com.base.sampleapixml;

import com.base.database.trading.model.TradingOrderGetOrders;
import com.base.database.trading.model.TradingOrderOrderVariationSpecifics;
import com.base.database.trading.model.TradingOrderShippingDetails;
import com.base.utils.common.DateUtils;
import com.base.utils.xmlutils.SamplePaseXml;
import org.apache.commons.lang.StringEscapeUtils;
import org.dom4j.Document;
import org.dom4j.Element;

import java.util.*;

/**
 * Created by Administrtor on 2014/8/14.
 */
public class GetOrdersAPI {

    public static Map<String,Object> parseXMLAndSave(String res) throws Exception {
        Document document= SamplePaseXml.formatStr2Doc(res);
        Element root=document.getRootElement();
        Element orderArray=root.element("OrderArray");
        String totalPage=SamplePaseXml.getSpecifyElementText(root,"PaginationResult","TotalNumberOfPages");
        List<TradingOrderGetOrders> lists=new ArrayList<TradingOrderGetOrders>();
        List<List<TradingOrderOrderVariationSpecifics>> OrderVariationSpecificses=new ArrayList<List<TradingOrderOrderVariationSpecifics>>();
        Map<String,Object> map=new HashMap();
        map.put("totalPage",totalPage);
        if(orderArray!=null){
            Iterator<Element> iterator =orderArray.elementIterator("Order");
            TradingOrderShippingDetails sd=new TradingOrderShippingDetails();
            while(iterator.hasNext()){
                Element order=iterator.next();
                Element transactionArray=order.element("TransactionArray");
                if(transactionArray!=null){
                    Iterator<Element> it=transactionArray.elementIterator("Transaction");

                    while(it.hasNext()){
                        TradingOrderGetOrders getorder=new TradingOrderGetOrders();
                        //--------解析order下面第一层
                        getorder.setSelleremail(SamplePaseXml.getSpecifyElementText(order,"SellerEmail"));
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
                        getorder.setAmountpaid(SamplePaseXml.getSpecifyElementText(order,"AmountPaid"));
                        getorder.setCancelstatus(SamplePaseXml.getSpecifyElementText(order,"CancelStatus"));
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
                        getorder.setName(StringEscapeUtils.escapeXml(SamplePaseXml.getSpecifyElementText(order,"ShippingAddress","Name")));
                        getorder.setStreet1(StringEscapeUtils.escapeXml(SamplePaseXml.getSpecifyElementText(order,"ShippingAddress","Street1")));
                        getorder.setStreet2(StringEscapeUtils.escapeXml(SamplePaseXml.getSpecifyElementText(order,"ShippingAddress","Street2")));
                        getorder.setCityname(StringEscapeUtils.escapeXml(SamplePaseXml.getSpecifyElementText(order,"ShippingAddress","CityName")) );
                        getorder.setStateorprovince(StringEscapeUtils.escapeXml(SamplePaseXml.getSpecifyElementText(order,"ShippingAddress","StateOrProvince")));
                        getorder.setCountry(StringEscapeUtils.escapeXml(SamplePaseXml.getSpecifyElementText(order,"ShippingAddress","Country")));
                        getorder.setCountryname(StringEscapeUtils.escapeXml(SamplePaseXml.getSpecifyElementText(order,"ShippingAddress","CountryName")));
                        getorder.setPhone(StringEscapeUtils.escapeXml(SamplePaseXml.getSpecifyElementText(order,"ShippingAddress","Phone")));
                        getorder.setPostalcode(StringEscapeUtils.escapeXml(SamplePaseXml.getSpecifyElementText(order,"ShippingAddress","PostalCode")));
                        getorder.setAddressid(StringEscapeUtils.escapeXml(SamplePaseXml.getSpecifyElementText(order,"ShippingAddress","AddressID")));
                        getorder.setAddressowner(StringEscapeUtils.escapeXml(SamplePaseXml.getSpecifyElementText(order,"ShippingAddress","AddressOwner")));
                        getorder.setExternaladdressid(StringEscapeUtils.escapeXml(SamplePaseXml.getSpecifyElementText(order,"ShippingAddress","ExternalAddressID")));
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
                        Element Variation=transaction.element("Variation");
                        if(Variation!=null){
                            Element VariationSpecifics=Variation.element("VariationSpecifics");
                            if(VariationSpecifics!=null){
                                Iterator NameValueList=VariationSpecifics.elementIterator("NameValueList");
                                List<TradingOrderOrderVariationSpecifics> orderOrderVariationSpecificses=new ArrayList<TradingOrderOrderVariationSpecifics>();
                                while(NameValueList.hasNext()){
                                    Element nameValueList= (Element) NameValueList.next();
                                    TradingOrderOrderVariationSpecifics orderVariationSpecifics=new TradingOrderOrderVariationSpecifics();
                                    orderVariationSpecifics.setSku(SamplePaseXml.getSpecifyElementText(Variation,"SKU"));
                                    orderVariationSpecifics.setName(SamplePaseXml.getSpecifyElementText(nameValueList,"Name"));
                                    orderVariationSpecifics.setValue(SamplePaseXml.getSpecifyElementText(nameValueList,"Value"));
                                    orderOrderVariationSpecificses.add(orderVariationSpecifics);
                                }
                                OrderVariationSpecificses.add(orderOrderVariationSpecificses);
                                getorder.setVariationsku(SamplePaseXml.getSpecifyElementText(Variation,"SKU"));
                            }

                        }
                        getorder.setShipmenttrackingnumber(SamplePaseXml.getSpecifyElementText(transaction,"ShippingDetails","ShipmentTrackingDetails","ShipmentTrackingNumber"));
                        getorder.setShippingcarrierused(SamplePaseXml.getSpecifyElementText(transaction,"ShippingDetails","ShipmentTrackingDetails","ShippingCarrierUsed"));
                        lists.add(getorder);
                    }
                }

            }
        }
        map.put("OrderVariation",OrderVariationSpecificses);
        map.put("OrderList",lists);
        return map;
    }
}
