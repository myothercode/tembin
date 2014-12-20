package com.base.utils.scheduleabout.commontask;

import com.base.database.sitemessage.model.PublicSitemessage;
import com.base.database.trading.model.TradingPriceTracking;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.cache.TempStoreDataSupport;
import com.base.utils.common.CommAutowiredClass;
import com.base.utils.httpclient.HttpClientUtil;
import com.base.utils.scheduleabout.BaseScheduledClass;
import com.base.utils.scheduleabout.MainTask;
import com.base.utils.scheduleabout.Scheduledable;
import com.base.utils.threadpool.TaskMessageVO;
import com.base.utils.threadpool.TaskPool;
import com.base.utils.xmlutils.SamplePaseXml;
import com.sitemessage.service.SiteMessageService;
import com.trading.service.ITradingPriceTracking;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrtor on 2014/8/29.
 * 在线商品每晚执行，定时任务
 */
public class PriceTrackingByItemIdTaskRun extends BaseScheduledClass implements Scheduledable {
    static Logger logger = Logger.getLogger(PriceTrackingByItemIdTaskRun.class);
    private void syschronizePriceTracking(List<TradingPriceTracking> priceTrackings){
        CommAutowiredClass commPars = (CommAutowiredClass) ApplicationContextUtil.getBean(CommAutowiredClass.class);//获取注入的参数
        SiteMessageService siteMessageService=(SiteMessageService) ApplicationContextUtil.getBean(SiteMessageService.class);
        ITradingPriceTracking iTradingPriceTracking=(ITradingPriceTracking)ApplicationContextUtil.getBean(ITradingPriceTracking.class);
        if(priceTrackings!=null&&priceTrackings.size()>0){
            String xml="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                    "<GetMultipleItemsRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">";
            for(TradingPriceTracking priceTracking:priceTrackings){
                xml+="<ItemID>"+priceTracking.getItemid()+"</ItemID>";
            }
            xml+="</GetMultipleItemsRequest>​";
            List<BasicHeader> headers = new ArrayList<BasicHeader>();
            headers.add(new BasicHeader("X-EBAY-API-APP-ID", "sandpoin-23af-4f47-a304-242ffed6ff5b"));
            headers.add(new BasicHeader("X-EBAY-API-VERSION", "897"));
            headers.add(new BasicHeader("X-EBAY-API-SITE-ID", "0"));
            headers.add(new BasicHeader("X-EBAY-API-CALL-NAME", "GetMultipleItems"));
            headers.add(new BasicHeader("X-EBAY-API-REQUEST-ENCODING", "XML"));
            HttpClient httpClient = HttpClientUtil.getHttpsClient();
            String ack="";
            String res="";
            try {
                res = HttpClientUtil.post(httpClient, "http://open.api.ebay.com/shopping", xml, "UTF-8", headers);
                int i=1;
                ack = SamplePaseXml.getVFromXmlString(res, "Ack");
            } catch (Exception e) {
                logger.error("PriceTrackingByItemIdTaskRun第50:"+e);
            }
            if("Success".equals(ack)||"Warning".equals(ack)){
                if("Warning".equals(ack)){
                    String errors = "";
                    try{
                        errors = SamplePaseXml.getVFromXmlString(res, "Errors");
                    }catch (Exception e){
                        logger.error("PriceTrackingByItemIdTaskRun第62"+res,e);
                        errors="";
                    }
                    if(!StringUtils.isNotBlank(errors)){
                        try{
                            errors =  SamplePaseXml.getWarningInformation(res);
                        }catch (Exception e){
                            logger.error("PriceTrackingByItemIdTaskRunl第69"+res,e);
                        }
                    }
                    List<PublicSitemessage> list1=siteMessageService.selectPublicSitemessageByMessage("price_tracking_by_itemid_FAIL","定时价格跟踪"+priceTrackings.get(0).getItemid());
                    if(list1==null||list1.size()==0){
                        TaskMessageVO taskMessageVO = new TaskMessageVO();
                        taskMessageVO.setMessageType("price_tracking_by_itemid_FAIL");
                        taskMessageVO.setMessageTitle("定时价格跟踪有警告!");
                        taskMessageVO.setMessageContext("定时价格跟踪调用API有警告:" + errors);
                        taskMessageVO.setMessageTo(priceTrackings.get(0).getCreateUser());
                        taskMessageVO.setMessageFrom("system");
                        taskMessageVO.setOrderAndSeller("定时价格跟踪:"+priceTrackings.get(0).getItemid());
                        siteMessageService.addSiteMessage(taskMessageVO);
                    }
                    logger.error("定时价格跟踪有警告!" + errors);
                }
                List<TradingPriceTracking> priceTrackings1=new ArrayList<TradingPriceTracking>();
                try {
                    priceTrackings1=SamplePaseXml.getPriceTrackingItemByItemId(res);
                } catch (Exception e) {
                    logger.error("解析定时价格跟踪res错误:" + e);
                }
                for(TradingPriceTracking priceTracking:priceTrackings1){
                    List<TradingPriceTracking> trackings=iTradingPriceTracking.selectPriceTrackingByItemId(priceTracking.getItemid());
                    if(trackings!=null&&trackings.size()>0){
                        priceTracking.setId(trackings.get(0).getId());
                    }
                    try {
                        iTradingPriceTracking.savePriceTracking(priceTracking);
                    } catch (Exception e) {
                        logger.error("PriceTrackingByItemIdTaskRunl保存异常:"+e);
                    }
                }
            }else{
                logger.error("调用API失败:"+res);
            }
        }
    }
    @Override
    public void run(){
        int i= TaskPool.scheduledThreadPoolTaskExecutor.getActiveCount();
        if(i>30){
            return;
        }
        String isRunging = TempStoreDataSupport.pullData("task_" + getScheduledType());
        if(StringUtils.isNotEmpty(isRunging)){return;}
        ITradingPriceTracking iTradingPriceTracking=(ITradingPriceTracking)ApplicationContextUtil.getBean(ITradingPriceTracking.class);
        List<TradingPriceTracking> priceTrackings=iTradingPriceTracking.selectPriceTracking();
        if(priceTrackings.size()>20){
            int dou=priceTrackings.size() / 20;
            for(i = 0; i<=dou;i++){
                List<TradingPriceTracking> list=filterLimitList(priceTrackings,i);
                syschronizePriceTracking(list);
            }
        }else{
            syschronizePriceTracking(priceTrackings);
        }
        TempStoreDataSupport.removeData("task_" + getScheduledType());
    }

    /**只从集合记录取多少条*/
    private List<TradingPriceTracking> filterLimitList(List<TradingPriceTracking> tlist,int i){
        int dou=tlist.size() / 20;
        List<TradingPriceTracking> list=new ArrayList<TradingPriceTracking>();
        if(i==dou){
            for(int j=i*20;j<tlist.size();j++){
                TradingPriceTracking l=tlist.get(j);
                list.add(l);
            }
        }else{
            for(int j=i*20;j<(i+1)*20;j++){
                TradingPriceTracking l=tlist.get(j);
                list.add(l);
            }
        }
        return list;
    }

    public PriceTrackingByItemIdTaskRun(){

    }

    @Override
    public String getScheduledType() {
        return MainTask.PRICE_TRACKING_BY_ITEMID;
    }

    @Override
    public Integer crTimeMinu() {
        return 10;
    }
}
