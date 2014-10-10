package com.base.aboutpaypal.service.impl;

import com.base.aboutpaypal.domain.PaypalVO;
import com.base.aboutpaypal.mapper.AboutPaypalMapper;
import com.base.aboutpaypal.paypalutils.PaypalxmlUtil;
import com.base.aboutpaypal.service.PayPalService;
import com.base.database.trading.mapper.UsercontrollerEbayAccountMapper;
import com.base.database.trading.mapper.UsercontrollerPaypalAccountMapper;
import com.base.database.trading.model.UsercontrollerEbayAccount;
import com.base.database.trading.model.UsercontrollerPaypalAccount;
import com.base.domains.SessionVO;
import com.base.mybatis.page.Page;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.exception.Asserts;
import com.base.utils.httpclient.HttpClientUtil;
import org.apache.http.client.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2014/9/12.
 */
@Service
public class PayPalServiceImpl implements PayPalService {

    @Autowired
    private UsercontrollerPaypalAccountMapper usercontrollerPaypalAccountMapper;
    @Autowired
    private UsercontrollerEbayAccountMapper ebayAccountMapper;

    @Value("${PAYAPL_API_URL}")
    private String PAYAPL_API_URL; //"https://api-3t.sandbox.paypal.com/2.0"

    @Autowired
    private AboutPaypalMapper aboutPaypalMapper;

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

    @Override
    /**获取paypal账户列表*/
    public List<UsercontrollerPaypalAccount> queryPayPalList(Map map,Page page){
        SessionVO sessionVO = SessionCacheSupport.getSessionVO();
        map.put("userId",sessionVO.getId());
        List<UsercontrollerPaypalAccount> paypalAccounts = aboutPaypalMapper.queryPayPalsByUserId(map,page);
        return paypalAccounts;
    }

    @Override
    public UsercontrollerPaypalAccount selectById(Long id){
        return usercontrollerPaypalAccountMapper.selectByPrimaryKey(id);
    }

    @Override
    /**启用或者停用paypal账户*/
    public void operationPayPalAccount(Map map){
        Long paypalid= (Long) map.get("paypalId");
        String stat= (String) map.get("status");
        UsercontrollerPaypalAccount paypalAccount = usercontrollerPaypalAccountMapper.selectByPrimaryKey(paypalid);
        SessionVO sessionVO=SessionCacheSupport.getSessionVO();
        Asserts.assertTrue((long)paypalAccount.getCreateUser()==sessionVO.getId(),"没有权限修改！");
        paypalAccount.setStatus(stat);
        usercontrollerPaypalAccountMapper.updateByPrimaryKey(paypalAccount);

        if("0".equalsIgnoreCase(stat)){
            aboutPaypalMapper.clearEbayPaypal(map);
        }
    }

    @Override
    /**新增一跳paypal记录*/
    public void addPayPalAccount(Map map){
        UsercontrollerPaypalAccount paypalAccount = (UsercontrollerPaypalAccount) map.get("paypalAccountVO");
        SessionVO sessionVO = SessionCacheSupport.getSessionVO();
        paypalAccount.setStatus("1");
        paypalAccount.setCreateUser(sessionVO.getId());
        paypalAccount.setCreateTime(new Date());
        usercontrollerPaypalAccountMapper.insertSelective(paypalAccount);

    }

/*    public static void main(String[] args) throws Exception {
        PayPalServiceImpl p=new PayPalServiceImpl();
        p.getTransactionDetails(new HashMap());
    }*/
}
