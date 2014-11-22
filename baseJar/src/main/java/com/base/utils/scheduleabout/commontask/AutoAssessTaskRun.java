package com.base.utils.scheduleabout.commontask;

import com.base.database.keymove.mapper.KeyMoveListMapper;
import com.base.database.keymove.model.KeyMoveList;
import com.base.database.keymove.model.KeyMoveListExample;
import com.base.database.publicd.model.PublicUserConfig;
import com.base.database.task.mapper.TaskGetOrdersMapper;
import com.base.database.task.model.TaskGetOrdersExample;
import com.base.database.trading.mapper.TradingFeedBackDetailMapper;
import com.base.database.trading.mapper.TradingOrderGetOrdersMapper;
import com.base.database.trading.mapper.UsercontrollerEbayAccountMapper;
import com.base.database.trading.model.*;
import com.base.database.userinfo.model.SystemLog;
import com.base.domains.SessionVO;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.sampleapixml.APINameStatic;
import com.base.sampleapixml.BindAccountAPI;
import com.base.userinfo.service.UserInfoService;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.cache.DataDictionarySupport;
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
import com.base.xmlpojo.trading.addproduct.Item;
import com.orderassess.service.IAutoAssessDetail;
import com.orderassess.service.IOrderAutoAssess;
import com.sitemessage.service.SiteMessageService;
import com.sitemessage.service.SiteMessageStatic;
import com.trading.service.ITradingItem;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Created by Administrtor on 2014/8/29.
 * 一键搬家任务
 */
public class AutoAssessTaskRun extends BaseScheduledClass implements Scheduledable {
    static Logger logger = Logger.getLogger(AutoAssessTaskRun.class);
    @Override
    public void run() {
        String isRunging = TempStoreDataSupport.pullData("task_"+getScheduledType());
        if(StringUtils.isNotEmpty(isRunging)){return;}
        TempStoreDataSupport.pushData("task_" + getScheduledType(), "x");
        CommAutowiredClass commPars = (CommAutowiredClass) ApplicationContextUtil.getBean(CommAutowiredClass.class);//获取注入的参数
        IAutoAssessDetail iAutoAssessDetail = (IAutoAssessDetail) ApplicationContextUtil.getBean(IAutoAssessDetail.class);
        UsercontrollerEbayAccountMapper ueam = (UsercontrollerEbayAccountMapper) ApplicationContextUtil.getBean(UsercontrollerEbayAccountMapper.class);
        UsercontrollerEbayAccountExample ueame = new UsercontrollerEbayAccountExample();
        ueame.createCriteria();
        List<UsercontrollerEbayAccount> liue = ueam.selectByExampleWithBLOBs(ueame);
        UsercontrollerDevAccountExtend d = new UsercontrollerDevAccountExtend();
        d.setApiSiteid("0");
        d.setApiCallName(APINameStatic.CompleteSale);
        AddApiTask addApiTask = new AddApiTask();
        for(int i =0 ;i<liue.size();i++) {
            UsercontrollerEbayAccount ue = liue.get(i);
            List<PublicUserConfig> liconfig = DataDictionarySupport.getPublicUserConfigByType("autoAssessType", ue.getUserId());
            if(liconfig!=null&&liconfig.size()>0){
                String configValue = liconfig.get(0).getConfigValue();
                TradingOrderGetOrdersMapper tradingOrderGetOrdersMapper = (TradingOrderGetOrdersMapper) ApplicationContextUtil.getBean(TradingOrderGetOrdersMapper.class);
                IOrderAutoAssess iOrderAutoAssess = (IOrderAutoAssess) ApplicationContextUtil.getBean(IOrderAutoAssess.class);
                TradingOrderGetOrdersExample tde = new TradingOrderGetOrdersExample();
                TradingOrderGetOrdersExample.Criteria tc = tde.createCriteria().andLastmodifiedtimeBetween(DateUtils.turnToDateStart(new Date()), DateUtils.turnToDateEnd(new Date())).andStatusEqualTo("Complete").andSelleruseridEqualTo(ue.getEbayName());
                OrderAutoAssess oaa = iOrderAutoAssess.selectRandomContent(ue.getUserId());
                String content = oaa.getAssesscontent();
                if("2".equals(configValue)){//收到买家的正面评价时
                    List<TradingOrderGetOrders> lito = tradingOrderGetOrdersMapper.selectByExample(tde);
                    this.sendAssessByOrder(lito,"feedback",ue.getEbayToken(),ue.getUserId(),iAutoAssessDetail,addApiTask,d,content,commPars.apiUrl);
                }else if("3".equals(configValue)){//发货时
                    tc.andShippedtimeIsNotNull();
                    List<TradingOrderGetOrders> lito = tradingOrderGetOrdersMapper.selectByExample(tde);
                    this.sendAssessByOrder(lito, "order", ue.getEbayToken(), ue.getUserId(), iAutoAssessDetail, addApiTask, d, content, commPars.apiUrl);
                }else if("4".equals(configValue)){//买家购买物品时
                    tc.andShippedtimeIsNull();
                    List<TradingOrderGetOrders> lito = tradingOrderGetOrdersMapper.selectByExample(tde);
                    this.sendAssessByOrder(lito, "order", ue.getEbayToken(), ue.getUserId(), iAutoAssessDetail, addApiTask, d, content, commPars.apiUrl);
                }
            }
        }
        TempStoreDataSupport.removeData("task_" + getScheduledType());

    }

    /**
     * 通过定单数据发送自动评价
     * @param lito
     * @param sourceType
     * @param token
     * @param userId
     * @param iAutoAssessDetail
     * @param addApiTask
     * @param d
     * @param content
     * @param appUrl
     */
    public void sendAssessByOrder(List<TradingOrderGetOrders> lito,String sourceType,String token,Long userId,IAutoAssessDetail iAutoAssessDetail,AddApiTask addApiTask,UsercontrollerDevAccountExtend d,String content,String appUrl){
        for(TradingOrderGetOrders torder:lito){
            List<TradingFeedBackDetail> lifeed = null;
            if("feedback".equals(sourceType)){
                TradingFeedBackDetailMapper tradingFeedBackDetailMapper = (TradingFeedBackDetailMapper) ApplicationContextUtil.getBean(TradingFeedBackDetailMapper.class);
                TradingFeedBackDetailExample tfbd = new TradingFeedBackDetailExample();
                tfbd.createCriteria().andCommenttypeEqualTo("Positive").andCommentinguserEqualTo(torder.getBuyeruserid()).andTransactionidEqualTo(torder.getTransactionid()).andOrderlineitemidEqualTo(torder.getOrderid()).andItemidEqualTo(torder.getItemid());
                lifeed = tradingFeedBackDetailMapper.selectByExampleWithBLOBs(tfbd);
                if(lifeed==null||lifeed.size()==0){
                    continue;
                }
            }
            List<AutoAssessDetailWithBLOBs> liaadb = null;
            List<AutoAssessDetailWithBLOBs> liisflag = null;
            if(sourceType.equals("order")){
                liaadb = iAutoAssessDetail.selectByList(torder.getId(),sourceType,"0");
                liisflag = iAutoAssessDetail.selectByList(torder.getId(),sourceType,"1");
            }else{
                liaadb = iAutoAssessDetail.selectByList(lifeed.get(0).getId(),sourceType,"0");
                liisflag = iAutoAssessDetail.selectByList(lifeed.get(0).getId(),sourceType,"1");
            }
            //如查自动发送评价５次都失败，那么给用户通知。其它次数失败不管
            if(liisflag!=null&&liisflag.size()==5){
                SiteMessageService siteMessageService= (SiteMessageService) ApplicationContextUtil.getBean(SiteMessageService.class);
                TaskMessageVO taskMessageVO=new TaskMessageVO();
                taskMessageVO.setMessageContext("自动发送评价失败，已经失败５次，请用户自已处理评价!定单号："+torder.getOrderid()+"；交易号："+torder.getTransactionid());
                taskMessageVO.setMessageTitle("自动发送评价失败");
                taskMessageVO.setMessageType(SiteMessageStatic.AUTO_ASSESS_TYPE);
                taskMessageVO.setMessageFrom("system");
                SessionVO sessionVO= SessionCacheSupport.getSessionVO();
                taskMessageVO.setMessageTo(userId);
                taskMessageVO.setObjClass(null);
                siteMessageService.addSiteMessage(taskMessageVO);
            }
            if(liisflag!=null&&liisflag.size()>5){//如果失败次数大于５那么不在发送自动评价
                continue;
            }
            if(liaadb==null||liaadb.size()==0){
                Map m = new HashMap();
                m.put("token",token);
                m.put("buyeruserid",torder.getBuyeruserid());
                m.put("CommentText",content);
                m.put("itemid",torder.getItemid());
                m.put("transactionid",torder.getTransactionid());
                String xml = BindAccountAPI.getEvaluate(m);
                Map<String, String> resMap = addApiTask.exec2(d, xml, appUrl);
                String r1 = resMap.get("stat");
                String res = resMap.get("message");
                String ack = null;
                AutoAssessDetailWithBLOBs aad = new AutoAssessDetailWithBLOBs();
                aad.setCreateDate(new Date());
                aad.setPostXml(xml);
                aad.setDataType(sourceType);
                aad.setSourceId(torder.getId());
                aad.setToken(token);
                aad.setItemId(torder.getItemid());
                aad.setContent(content);
                aad.setSendUserid(userId+"");
                aad.setSendUsername(torder.getSelleruserid());
                aad.setTargetUserid(torder.getBuyeruserid());
                aad.setTransactionid(torder.getTransactionid());
                try {
                    ack = SamplePaseXml.getVFromXmlString(res, "Ack");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if ("Success".equalsIgnoreCase(ack)) {
                    aad.setIsFlag("0");
                }else{

                    aad.setIsFlag("1");
                    SystemLog sl = new SystemLog();
                    sl.setCreatedate(new Date());
                    try {
                        sl.setEventdesc(torder.getSelleruserid()+"发送给："+torder.getBuyeruserid()+"评价；发送失败，原因如下："+SamplePaseXml.getSpecifyElementTextAllInOne(res,"Errors","LongMessage"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    sl.setEventname(SystemLogUtils.AUTO_ASSESS);
                    sl.setOperuser(userId+"");
                    try {
                        SystemLogUtils.saveLog(sl);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                iAutoAssessDetail.saveAutoAssessDetail(aad);
            }
        }
    }

    /**只从集合记录取多少条*/
    private List<KeyMoveList> filterLimitList(List<KeyMoveList> tlist){
        return filterLimitListFinal(tlist,20);
        /*List<KeyMoveList> x=new ArrayList<KeyMoveList>();
        for (int i = 0;i<20;i++){
            x.add(tlist.get(i));
        }
        return x;*/
    }

    public AutoAssessTaskRun(){
    }

    @Override
    public String getScheduledType() {
        return MainTask.AUTO_ASSESS;
    }

    @Override
    public Integer crTimeMinu() {
        return null;
    }
}
