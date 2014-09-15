package com.base.aboutpaypal.service.impl;

import com.base.aboutpaypal.domain.PaypalVO;
import com.base.aboutpaypal.paypalutils.PaypalxmlUtil;
import com.base.aboutpaypal.service.PayPalService;
import com.base.database.trading.mapper.UsercontrollerPaypalAccountMapper;
import com.base.database.trading.model.UsercontrollerPaypalAccount;
import com.base.utils.httpclient.HttpClientUtil;
import org.apache.http.client.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2014/9/12.
 */
@Service
public class PayPalServiceImpl implements PayPalService {

    @Autowired
    private UsercontrollerPaypalAccountMapper usercontrollerPaypalAccountMapper;

    @Value("${PAYAPL_API_URL}")
    private String PAYAPL_API_URL; //"https://api-3t.sandbox.paypal.com/2.0"

    @Override
    /**获取paypal余额*/
    public PaypalVO getPaypalBalance(Long paypalId) throws Exception {
        UsercontrollerPaypalAccount uspa = usercontrollerPaypalAccountMapper.selectByPrimaryKey(paypalId);
        HttpClient h= HttpClientUtil.getHttpsClient();
        String res = HttpClientUtil.post(h,PAYAPL_API_URL, PaypalxmlUtil.getBalanceXML(uspa));
        PaypalVO p=PaypalxmlUtil.getBalance(res);
        return p;
    }

    @Override
    /**获取交易的交易费和杂费*/
    public PaypalVO getTransactionDetails(Map map) throws Exception {
        Long paypalId= (Long) map.get("paypalId");
        String transactionID= (String) map.get("transactionID");
        PaypalVO paypalVO=new PaypalVO();
        paypalVO.setTransactionID(transactionID);

        UsercontrollerPaypalAccount uspa = usercontrollerPaypalAccountMapper.selectByPrimaryKey(paypalId);

        HttpClient h= HttpClientUtil.getHttpsClient();
        /*UsercontrollerPaypalAccount uspa=new UsercontrollerPaypalAccount();
        PaypalVO paypalVO=new PaypalVO();
        paypalVO.setTransactionID("4RJ37607494399203");*/
        String res = HttpClientUtil.post(h,PAYAPL_API_URL, PaypalxmlUtil.getTransactionDetailsXML(uspa,paypalVO));
        PaypalVO paypalVO1=PaypalxmlUtil.getTranDetail(res);
        return paypalVO1;

    }

/*    public static void main(String[] args) throws Exception {
        PayPalServiceImpl p=new PayPalServiceImpl();
        p.getTransactionDetails(new HashMap());
    }*/
}
