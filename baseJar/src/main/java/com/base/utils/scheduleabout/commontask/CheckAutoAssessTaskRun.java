package com.base.utils.scheduleabout.commontask;

import com.base.database.customtrading.mapper.FeedBackReportMapper;
import com.base.database.publicd.model.PublicUserConfig;
import com.base.database.trading.mapper.AutoAssessDetailMapper;
import com.base.database.trading.mapper.TradingFeedBackDetailMapper;
import com.base.database.trading.mapper.TradingOrderGetOrdersMapper;
import com.base.database.trading.mapper.UsercontrollerEbayAccountMapper;
import com.base.database.trading.model.*;
import com.base.database.userinfo.model.SystemLog;
import com.base.domains.SessionVO;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.domains.userinfo.UsercontrollerEbayAccountExtend;
import com.base.domains.userinfo.UsercontrollerUserExtend;
import com.base.sampleapixml.APINameStatic;
import com.base.sampleapixml.BindAccountAPI;
import com.base.userinfo.mapper.SystemUserManagerServiceMapper;
import com.base.userinfo.service.SystemUserManagerService;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.cache.TempStoreDataSupport;
import com.base.utils.common.CommAutowiredClass;
import com.base.utils.common.DateUtils;
import com.base.utils.common.SystemLogUtils;
import com.base.utils.scheduleabout.BaseScheduledClass;
import com.base.utils.scheduleabout.MainTask;
import com.base.utils.scheduleabout.Scheduledable;
import com.base.utils.threadpool.AddApiTask;
import com.base.utils.threadpool.TaskMessageVO;
import com.base.utils.xmlutils.SamplePaseXml;
import com.orderassess.service.IAutoAssessDetail;
import com.orderassess.service.IOrderAutoAssess;
import com.publicd.service.IPublicUserConfig;
import com.sitemessage.service.SiteMessageService;
import com.sitemessage.service.SiteMessageStatic;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import java.util.*;

/**
 * Created by Administrtor on 2015/1/21.
 */
public class CheckAutoAssessTaskRun  extends BaseScheduledClass implements Scheduledable {

    static Logger logger = Logger.getLogger(AutoAssessTaskRun.class);
    @Override
    public void run() {
        String isRunging = TempStoreDataSupport.pullData("task_" + getScheduledType());
        if(StringUtils.isNotEmpty(isRunging)){return;}
        TempStoreDataSupport.pushData("task_" + getScheduledType(), "x");
        CommAutowiredClass commPars = (CommAutowiredClass) ApplicationContextUtil.getBean(CommAutowiredClass.class);//获取注入的参数
        IAutoAssessDetail iAutoAssessDetail = (IAutoAssessDetail) ApplicationContextUtil.getBean(IAutoAssessDetail.class);
        IPublicUserConfig iPublicUserConfig = (IPublicUserConfig) ApplicationContextUtil.getBean(IPublicUserConfig.class);
        UsercontrollerEbayAccountMapper ueam = (UsercontrollerEbayAccountMapper) ApplicationContextUtil.getBean(UsercontrollerEbayAccountMapper.class);
        SystemUserManagerServiceMapper systemUserManagerServiceMapper = (SystemUserManagerServiceMapper) ApplicationContextUtil.getBean(SystemUserManagerServiceMapper.class);
        SystemUserManagerService systemUserManagerService = (SystemUserManagerService) ApplicationContextUtil.getBean(SystemUserManagerService.class);
        TradingFeedBackDetailMapper tradingFeedBackDetailMapper = (TradingFeedBackDetailMapper) ApplicationContextUtil.getBean(TradingFeedBackDetailMapper.class);
        TradingOrderGetOrdersMapper tradingOrderGetOrdersMapper = (TradingOrderGetOrdersMapper) ApplicationContextUtil.getBean(TradingOrderGetOrdersMapper.class);
        IOrderAutoAssess iOrderAutoAssess = (IOrderAutoAssess) ApplicationContextUtil.getBean(IOrderAutoAssess.class);
        try {
            UsercontrollerDevAccountExtend d = new UsercontrollerDevAccountExtend();
            d.setApiSiteid("0");
            d.setApiCallName(APINameStatic.CompleteSale);
            AddApiTask addApiTask = new AddApiTask();

            List<PublicUserConfig> lipuc = iPublicUserConfig.selectUserConfigByItemTypeList("autoAssessType");
            for (PublicUserConfig puc : lipuc) {
                String configValue = puc.getConfigValue();
                //查询该公司下面所有用户
                Map map = new HashMap();
                map.put("orgID", puc.getUserId());
                map.put("isShowStopOnly", "yes");
                List<UsercontrollerUserExtend> liuue = systemUserManagerServiceMapper.queryAllUsersByOrgID(map);
                List<Long> liUserId = new ArrayList();
                for (UsercontrollerUserExtend uue : liuue) {
                    liUserId.add(uue.getUserId().longValue());
                }
                UsercontrollerEbayAccountExample ueae = new UsercontrollerEbayAccountExample();
                ueae.createCriteria().andCreateUserIn(liUserId);
                List<UsercontrollerEbayAccount> liuea = ueam.selectByExampleWithBLOBs(ueae);

                for (UsercontrollerEbayAccount uea : liuea) {
                    OrderAutoAssess oaa = iOrderAutoAssess.selectRandomContent(liuue);
                    String content = oaa.getAssesscontent();
                    String ebayAccount = uea.getEbayName();
                    String token = uea.getEbayToken();
                    if ("2".equals(configValue)) {//收到买家的正面评价时
                        TradingFeedBackDetailExample tfbe = new TradingFeedBackDetailExample();
                        tfbe.createCriteria().andRoleEqualTo("Buyer").andEbayAccountEqualTo(ebayAccount).andCommenttypeEqualTo("Positive").andCommenttimeBetween(DateUtils.turnToDateStart(DateUtils.nowDateMinusDay(7)),DateUtils.turnToDateEnd(new Date()));
                        List<TradingFeedBackDetail> litfbd = tradingFeedBackDetailMapper.selectByExampleWithBLOBs(tfbe);
                        this.saveAutoAssessDetailByFeedBack(litfbd,token, iAutoAssessDetail, content, commPars.apiUrl);
                    } else if ("3".equals(configValue)) {//发货时
                        TradingOrderGetOrdersExample tde = new TradingOrderGetOrdersExample();
                        tde.createCriteria().andPaidtimeBetween(DateUtils.turnToDateStart(DateUtils.nowDateMinusDay(3)), DateUtils.turnToDateEnd(new Date())).andStatusEqualTo("Complete").andSelleruseridEqualTo(ebayAccount).andShippedtimeIsNotNull();
                        List<TradingOrderGetOrders> lito = tradingOrderGetOrdersMapper.selectByExample(tde);
                        this.saveAutoAssessDetail(lito, token, iAutoAssessDetail, content, commPars.apiUrl);
                    } else if ("4".equals(configValue)) {//买家购买物品时
                        TradingOrderGetOrdersExample tde = new TradingOrderGetOrdersExample();
                        tde.createCriteria().andPaidtimeBetween(DateUtils.turnToDateStart(DateUtils.nowDateMinusDay(3)), DateUtils.turnToDateEnd(new Date())).andStatusEqualTo("Complete").andSelleruseridEqualTo(ebayAccount).andShippedtimeIsNull();
                        List<TradingOrderGetOrders> lito = tradingOrderGetOrdersMapper.selectByExample(tde);
                        this.saveAutoAssessDetail(lito, token, iAutoAssessDetail, content, commPars.apiUrl);
                    }
                }
            }
        }catch(Exception e){
            TempStoreDataSupport.removeData("task_" + getScheduledType());
            logger.error("检查需要发送自动评价信息报错：",e);
        }
        TempStoreDataSupport.removeData("task_" + getScheduledType());
    }

    public void saveAutoAssessDetailByFeedBack(List<TradingFeedBackDetail> litfbd,String token,IAutoAssessDetail iAutoAssessDetail,String content,String appUrl){
        TradingFeedBackDetailMapper tradingFeedBackDetailMapper = (TradingFeedBackDetailMapper) ApplicationContextUtil.getBean(TradingFeedBackDetailMapper.class);
        AutoAssessDetailMapper autoAssessDetailMapper = (AutoAssessDetailMapper) ApplicationContextUtil.getBean(AutoAssessDetailMapper.class);
        if(litfbd!=null&&litfbd.size()>0){
            for(TradingFeedBackDetail tfbd:litfbd){
                TradingFeedBackDetailExample tfbe = new TradingFeedBackDetailExample();
                tfbe.createCriteria().andItemidEqualTo(tfbd.getItemid()).andTransactionidEqualTo(tfbd.getTransactionid()).andRoleEqualTo("Seller");
                List<TradingFeedBackDetail> ltfbd =tradingFeedBackDetailMapper.selectByExampleWithBLOBs(tfbe);
                if(ltfbd!=null&&ltfbd.size()>0){
                    continue;
                }else{
                    AutoAssessDetailExample aade = new AutoAssessDetailExample();
                    aade.createCriteria().andTransactionidEqualTo(tfbd.getTransactionid()).andItemIdEqualTo(tfbd.getItemid());
                    List<AutoAssessDetailWithBLOBs> liaad = autoAssessDetailMapper.selectByExampleWithBLOBs(aade);
                    if(liaad!=null&&liaad.size()>0){
                        continue;
                    }else{
                        Map m = new HashMap();
                        m.put("token",token);
                        m.put("buyeruserid",tfbd.getCommentinguser());
                        m.put("CommentText",content);
                        m.put("itemid",tfbd.getItemid());
                        m.put("transactionid",tfbd.getTransactionid());
                        String xml = BindAccountAPI.getEvaluate(m);
                        AutoAssessDetailWithBLOBs aad = new AutoAssessDetailWithBLOBs();
                        aad.setCreateDate(new Date());
                        aad.setPostXml(xml);
                        aad.setDataType("Order");
                        aad.setSourceId(tfbd.getId());
                        aad.setToken(token);
                        aad.setItemId(tfbd.getItemid());
                        aad.setContent(content);
                        aad.setSendUserid(tfbd.getEbayAccount());
                        aad.setSendUsername(tfbd.getEbayAccount());
                        aad.setTargetUserid(tfbd.getCommentinguser());
                        aad.setTransactionid(tfbd.getCommentinguser());
                        aad.setIsFlag("0");
                        iAutoAssessDetail.saveAutoAssessDetail(aad);
                    }
                }
            }
        }
    }
    /**
     * 处理定单发送评价信息
     * @param lito
     * @param token
     * @param iAutoAssessDetail
     * @param content
     * @param appUrl
     */
    public void saveAutoAssessDetail(List<TradingOrderGetOrders> lito,String token,IAutoAssessDetail iAutoAssessDetail,String content,String appUrl){
        TradingFeedBackDetailMapper tradingFeedBackDetailMapper = (TradingFeedBackDetailMapper) ApplicationContextUtil.getBean(TradingFeedBackDetailMapper.class);
        AutoAssessDetailMapper autoAssessDetailMapper = (AutoAssessDetailMapper) ApplicationContextUtil.getBean(AutoAssessDetailMapper.class);
        if(lito!=null&&lito.size()>0){
            for(TradingOrderGetOrders too:lito){
                TradingFeedBackDetailExample tfbe = new TradingFeedBackDetailExample();
                tfbe.createCriteria().andItemidEqualTo(too.getItemid()).andTransactionidEqualTo(too.getTransactionid()).andRoleEqualTo("Seller");
                List<TradingFeedBackDetail> ltfbd =tradingFeedBackDetailMapper.selectByExampleWithBLOBs(tfbe);
                if(ltfbd!=null&&ltfbd.size()>0){
                    continue;
                }else{
                    AutoAssessDetailExample aade = new AutoAssessDetailExample();
                    aade.createCriteria().andTransactionidEqualTo(too.getTransactionid()).andItemIdEqualTo(too.getItemid());
                    List<AutoAssessDetailWithBLOBs> liaad = autoAssessDetailMapper.selectByExampleWithBLOBs(aade);
                    if(liaad!=null&&liaad.size()>0){
                        continue;
                    }else{
                        Map m = new HashMap();
                        m.put("token",token);
                        m.put("buyeruserid",too.getBuyeruserid());
                        m.put("CommentText",content);
                        m.put("itemid",too.getItemid());
                        m.put("transactionid",too.getTransactionid());
                        String xml = BindAccountAPI.getEvaluate(m);
                        AutoAssessDetailWithBLOBs aad = new AutoAssessDetailWithBLOBs();
                        aad.setCreateDate(new Date());
                        aad.setPostXml(xml);
                        aad.setDataType("Order");
                        aad.setSourceId(too.getId());
                        aad.setToken(token);
                        aad.setItemId(too.getItemid());
                        aad.setContent(content);
                        aad.setSendUserid(too.getSelleruserid());
                        aad.setSendUsername(too.getSelleruserid());
                        aad.setTargetUserid(too.getBuyeruserid());
                        aad.setTransactionid(too.getTransactionid());
                        aad.setIsFlag("0");
                        iAutoAssessDetail.saveAutoAssessDetail(aad);
                    }
                }
            }
        }
    }
    @Override
    public String getScheduledType() {
        return MainTask.CHECK_AUTO_ASSESS;
    }

    @Override
    public Integer crTimeMinu() {
        return 10;
    }

    @Override
    public void setMark(String x) {

    }

    @Override
    public String getMark() {
        return null;
    }
}
