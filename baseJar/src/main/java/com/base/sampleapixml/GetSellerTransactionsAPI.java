package com.base.sampleapixml;

import com.base.database.trading.model.TradingOrderGetSellerTransactions;
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
public class GetSellerTransactionsAPI {
    public static List<TradingOrderGetSellerTransactions> parseXMLAndSave(String res) throws Exception {
        List<TradingOrderGetSellerTransactions> list=new ArrayList<TradingOrderGetSellerTransactions>();
        Document document= DocumentHelper.parseText(res);
        Element root=document.getRootElement();
        Element TransactionArray=root.element("TransactionArray");
        if(TransactionArray!=null){
            Iterator iterator=TransactionArray.elementIterator("Transaction");
            while(iterator.hasNext()){
                Element Transaction= (Element) iterator.next();
                TradingOrderGetSellerTransactions sellerTransactions=new TradingOrderGetSellerTransactions();
                sellerTransactions.setAmountpaid(SamplePaseXml.getSpecifyElementText(Transaction,"AmountPaid"));
                sellerTransactions.setAdjustmentamount(SamplePaseXml.getSpecifyElementText(Transaction,"AdjustmentAmount"));
                sellerTransactions.setConvertedadjustmentamount(SamplePaseXml.getSpecifyElementText(Transaction,"ConvertedAdjustmentAmount"));
                sellerTransactions.setConvertedamountpaid(SamplePaseXml.getSpecifyElementText(Transaction,"ConvertedAmountPaid"));
                sellerTransactions.setConvertedtransactionprice(SamplePaseXml.getSpecifyElementText(Transaction,"ConvertedTransactionPrice"));
                String CreatedDate=SamplePaseXml.getSpecifyElementText(Transaction,"CreatedDate");
                sellerTransactions.setCreateddate(DateUtils.returnDate(CreatedDate));
                sellerTransactions.setDeposittype(SamplePaseXml.getSpecifyElementText(Transaction,"DepositType"));
                sellerTransactions.setItemid(SamplePaseXml.getSpecifyElementText(Transaction,"Item","ItemID"));
                sellerTransactions.setQuantitypurchased(SamplePaseXml.getSpecifyElementText(Transaction,"QuantityPurchased"));
                sellerTransactions.setEbaypaymentstatus(SamplePaseXml.getSpecifyElementText(Transaction,"Status","eBayPaymentStatus"));
                sellerTransactions.setCheckoutstatus(SamplePaseXml.getSpecifyElementText(Transaction,"Status","CheckoutStatus"));
                String LastTimeModified=SamplePaseXml.getSpecifyElementText(Transaction,"Status","LastTimeModified");
                sellerTransactions.setLasttimemodified(DateUtils.returnDate(LastTimeModified));
                sellerTransactions.setPaymentmethodused(SamplePaseXml.getSpecifyElementText(Transaction,"Status","PaymentMethodUsed"));
                sellerTransactions.setCompletestatus(SamplePaseXml.getSpecifyElementText(Transaction,"Status","CompleteStatus"));
                sellerTransactions.setBuyerselectedshipping(SamplePaseXml.getSpecifyElementText(Transaction,"Status","BuyerSelectedShipping"));
                sellerTransactions.setPaymentholdstatus(SamplePaseXml.getSpecifyElementText(Transaction,"Status","PaymentHoldStatus"));
                sellerTransactions.setIntegratedmerchantcreditcardenabled(SamplePaseXml.getSpecifyElementText(Transaction,"Status","IntegratedMerchantCreditCardEnabled"));
                sellerTransactions.setInquirystatus(SamplePaseXml.getSpecifyElementText(Transaction,"Status","InquiryStatus"));
                sellerTransactions.setReturnstatus(SamplePaseXml.getSpecifyElementText(Transaction,"Status","ReturnStatus"));
                sellerTransactions.setTransactionid(SamplePaseXml.getSpecifyElementText(Transaction,"TransactionID"));
                sellerTransactions.setExternaltransactionid(SamplePaseXml.getSpecifyElementText(Transaction,"ExternalTransaction","ExternalTransactionID"));
                String ExternalTransactionTime=SamplePaseXml.getSpecifyElementText(Transaction,"ExternalTransaction","ExternalTransactionTime");
                sellerTransactions.setExternaltransactiontime(DateUtils.returnDate(ExternalTransactionTime));
                sellerTransactions.setFeeorcreditamount(SamplePaseXml.getSpecifyElementText(Transaction,"ExternalTransaction","FeeOrCreditAmount"));
                sellerTransactions.setPaymentorrefundamount(SamplePaseXml.getSpecifyElementText(Transaction,"ExternalTransaction","PaymentOrRefundAmount"));
                sellerTransactions.setExternaltransactionstatus(SamplePaseXml.getSpecifyElementText(Transaction,"ExternalTransaction","ExternalTransactionStatus"));
                String PaidTime=SamplePaseXml.getSpecifyElementText(Transaction,"PaidTime");
                sellerTransactions.setPaidtime(DateUtils.returnDate(PaidTime));
                sellerTransactions.setOrderid(SamplePaseXml.getSpecifyElementText(Transaction,"ContainingOrder","OrderID"));
                sellerTransactions.setOrderstatus(SamplePaseXml.getSpecifyElementText(Transaction,"ContainingOrder","OrderStatus"));
                sellerTransactions.setCancelstatus(SamplePaseXml.getSpecifyElementText(Transaction,"ContainingOrder","CancelStatus"));
                sellerTransactions.setPaymentstatus(SamplePaseXml.getSpecifyElementText(Transaction,"MonetaryDetails","Payments","Payment","PaymentStatus"));
                sellerTransactions.setPayer(SamplePaseXml.getSpecifyElementText(Transaction,"MonetaryDetails","Payments","Payment","Payer"));
                sellerTransactions.setPayee(SamplePaseXml.getSpecifyElementText(Transaction,"MonetaryDetails","Payments","Payment","Payee"));
                String PaymentTime=SamplePaseXml.getSpecifyElementText(Transaction,"MonetaryDetails","Payments","Payment","PaymentTime");
                sellerTransactions.setPaymenttime(DateUtils.returnDate(PaymentTime));
                sellerTransactions.setPaymentamount(SamplePaseXml.getSpecifyElementText(Transaction,"MonetaryDetails","Payments","Payment","PaymentAmount"));
                sellerTransactions.setReferenceid(SamplePaseXml.getSpecifyElementText(Transaction,"MonetaryDetails","Payments","Payment","ReferenceID"));
                list.add(sellerTransactions);
            }
        }
        return list;
    }
}
