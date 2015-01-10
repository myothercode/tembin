package com.base.aboutpaypal.service.impl;

import com.base.aboutpaypal.domain.PaypalVO;
import com.base.aboutpaypal.mapper.AboutPaypalMapper;
import com.base.aboutpaypal.paypalutils.PaypalxmlUtil;
import com.base.aboutpaypal.service.PayPalService;
import com.base.database.trading.mapper.TradingPaypalMapper;
import com.base.database.trading.mapper.UsercontrollerEbayAccountMapper;
import com.base.database.trading.mapper.UsercontrollerPaypalAccountMapper;
import com.base.database.trading.model.*;
import com.base.database.trading.model.UsercontrollerPaypalAccountExample;
import com.base.domains.SessionVO;
import com.base.domains.userinfo.UsercontrollerUserExtend;
import com.base.mybatis.page.Page;
import com.base.userinfo.service.SystemUserManagerService;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.exception.Asserts;
import com.base.utils.httpclient.HttpClientUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.HttpClient;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

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
    @Autowired
    private SystemUserManagerService systemUserManagerService;


    /**获取paypal开发帐号信息,createUser为-1的默认为开发帐号*/
    public UsercontrollerPaypalAccount getADevSin(Long paypalId,String... source){
        UsercontrollerPaypalAccountExample accountExample=new UsercontrollerPaypalAccountExample();
        accountExample.createCriteria().andCreateUserEqualTo(-1L);
        List<UsercontrollerPaypalAccount> uspaList = usercontrollerPaypalAccountMapper.selectByExample(accountExample);
        UsercontrollerPaypalAccount account0=uspaList.get(0);
        UsercontrollerPaypalAccount uspa = usercontrollerPaypalAccountMapper.selectByPrimaryKey(paypalId);
        Asserts.assertTrue(StringUtils.isNotEmpty(uspa.getEmail()),"paypal帐户的email不能为空!");
        if (!"1".equals(uspa.getSfCheck()) && source==null){return null;}
        uspa.setApiPassword(account0.getApiPassword());
        uspa.setApiSignature(account0.getApiSignature());
        uspa.setApiUserName(account0.getApiUserName());
        return uspa;
    }



    @Override
    /**获取paypal余额*/
    public PaypalVO getPaypalBalance(Long paypalId) throws Exception {
        UsercontrollerPaypalAccount uspa = getADevSin(paypalId,"balance");
        HttpClient h= HttpClientUtil.getHttpsClient();
        String res = HttpClientUtil.post(h,PAYAPL_API_URL, PaypalxmlUtil.getBalanceXML(uspa));
        PaypalVO p=PaypalxmlUtil.getBalance(res);
        return p;
    }
    @Override
    /**修改paypal帐号为已验证*/
    public void setPayPalSFCheck(Long paypalId, String sfCheck){
        UsercontrollerPaypalAccount paypalAccount=new UsercontrollerPaypalAccount();
        paypalAccount.setId(paypalId);
        paypalAccount.setSfCheck(sfCheck==null?"0":sfCheck);//0为未验证 1为已验证
        usercontrollerPaypalAccountMapper.updateByPrimaryKeySelective(paypalAccount);
    }

    @Override
    /**获取交易的交易费和杂费*/
    public Map getTransactionDetails(Map map) throws Exception {
        Map map1=new HashMap();
       /* Long paypalId= (Long) map.get("paypalId");*/
        String transactionID= (String) map.get("transactionID");
        String email= (String) map.get("paypalEmail");
        PaypalVO paypalVO=new PaypalVO();
        paypalVO.setTransactionID(transactionID);
        UsercontrollerPaypalAccountExample example=new UsercontrollerPaypalAccountExample();
        UsercontrollerPaypalAccountExample.Criteria cr=example.createCriteria();
        cr.andPaypalAccountEqualTo(email);
        List<UsercontrollerPaypalAccount> paypalAccounts=usercontrollerPaypalAccountMapper.selectByExample(example);

        UsercontrollerPaypalAccount uspa = getADevSin(paypalAccounts.get(0).getId());
        if(uspa==null){
            return null;
        }
        uspa.setEmail(email);
        HttpClient h= HttpClientUtil.getHttpsClient();
        /*UsercontrollerPaypalAccount uspa=new UsercontrollerPaypalAccount();
        PaypalVO paypalVO=new PaypalVO();
        paypalVO.setTransactionID("4RJ37607494399203");*/

      /*  String res = HttpClientUtil.post(h,"https://api-3t.paypal.com/2.0", PaypalxmlUtil.getTransactionDetailsXML(uspa,paypalVO));*/
        String res = HttpClientUtil.post(h,PAYAPL_API_URL, PaypalxmlUtil.getTransactionDetailsXML(uspa,paypalVO));
        PaypalVO paypalVO1=PaypalxmlUtil.getTranDetail(res,uspa);
        map1.put("paypal",paypalVO1);
        map1.put("res",res);
        map1.put("account",uspa);
        return map1;

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
        Asserts.assertTrue((long)paypalAccount.getCreateUser()==sessionVO.getId(), "没有权限修改！");
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

    @Override
    public Map<String,String> refundTransactionFull(Map map) throws Exception {
        Map<String,String> map1=new HashMap<String, String>();
        Long paypalid= (Long) map.get("paypalId");
        String transactionID= (String) map.get("transactionID");
        UsercontrollerPaypalAccount paypalAccount = getADevSin(paypalid);
        if(paypalAccount==null){
            map1.put("flag","Unverified");
            return  map1;
        }
        HttpClient h= HttpClientUtil.getHttpsClient();
        String res = HttpClientUtil.post(h,PAYAPL_API_URL, PaypalxmlUtil.getRefundTransactionFull(paypalAccount, transactionID));
        Element element = PaypalxmlUtil.getSpecElement(res,"Body","RefundTransactionResponse","Ack");
        String flag=element.getTextTrim();
        if(!StringUtils.isNotBlank(flag)){
            return null;
        }
        map1.put("flag",flag);
        if("Failure".equals(flag)){
            Element reson=PaypalxmlUtil.getSpecElement(res,"Body","RefundTransactionResponse","Errors","LongMessage");
            String reason =reson.getTextTrim();
            map1.put("message",reason);
            return map1;
        }
        return map1;
    }

    @Override
    public Map<String, String> refundTransactionPartial(Map map) throws Exception {
        Map<String,String> map1=new HashMap<String, String>();
        Long paypalid= (Long) map.get("paypalId");
        String transactionID= (String) map.get("transactionID");
        String money= (String) map.get("money");
        UsercontrollerPaypalAccount paypalAccount = getADevSin(paypalid);
        if(paypalAccount==null){
            map1.put("flag","Unverified");
            return  map1;
        }
        HttpClient h= HttpClientUtil.getHttpsClient();
        String res = HttpClientUtil.post(h,PAYAPL_API_URL, PaypalxmlUtil.getRefundTransactionPartial(paypalAccount, transactionID, money));
        Element element=PaypalxmlUtil.getSpecElement(res,"Body","RefundTransactionResponse","Ack");
        String flag=element.getTextTrim();
        if(!StringUtils.isNotBlank(flag)){
            return null;
        }
        map1.put("flag",flag);
        if("Failure".equals(flag)){
            Element reson=PaypalxmlUtil.getSpecElement(res,"Body","RefundTransactionResponse","Errors","LongMessage");
            String reason =reson.getTextTrim();
            map1.put("message",reason);
            return map1;
        }
        return map1;
    }

/*    public static void main(String[] args) throws Exception {
        PayPalServiceImpl p=new PayPalServiceImpl();
        p.getTransactionDetails(new HashMap());
    }*/


    @Override
    public List<UsercontrollerPaypalAccount> selectByOrgId(){
        SessionVO c = SessionCacheSupport.getSessionVO();
        List<UsercontrollerUserExtend> liuue = systemUserManagerService.queryAllUsersByOrgID("yes");
        List<Long> liue = new ArrayList<Long>();
        if(liuue!=null&&liuue.size()>0) {
            for (UsercontrollerUserExtend uue : liuue) {
                liue.add(uue.getUserId().longValue());
            }
        }
        liue.add(c.getId());
        UsercontrollerPaypalAccountExample upa = new UsercontrollerPaypalAccountExample();
        upa.createCriteria().andCreateUserIn(liue);
        return this.usercontrollerPaypalAccountMapper.selectByExample(upa);
    }
}
