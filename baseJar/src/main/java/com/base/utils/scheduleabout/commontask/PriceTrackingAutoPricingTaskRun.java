package com.base.utils.scheduleabout.commontask;

import com.base.database.sitemessage.model.PublicSitemessage;
import com.base.database.trading.model.*;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.userinfo.service.UserInfoService;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.cache.TempStoreDataSupport;
import com.base.utils.common.CommAutowiredClass;
import com.base.utils.httpclient.HttpClientUtil;
import com.base.utils.scheduleabout.BaseScheduledClass;
import com.base.utils.scheduleabout.MainTask;
import com.base.utils.scheduleabout.Scheduledable;
import com.base.utils.threadpool.AddApiTask;
import com.base.utils.threadpool.TaskMessageVO;
import com.base.utils.threadpool.TaskPool;
import com.base.utils.xmlutils.SamplePaseXml;
import com.sitemessage.service.SiteMessageService;
import com.trading.service.ITradingListingData;
import com.trading.service.ITradingPriceTrackingAutoPricing;
import com.trading.service.ITradingPriceTrackingAutoPricingRecord;
import com.trading.service.ITradingPriceTrackingPricingRule;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Created by Administrtor on 2014/8/29.
 * 价格跟踪定时去某个卖家商品，定时任务
 */
public class PriceTrackingAutoPricingTaskRun extends BaseScheduledClass implements Scheduledable {
    static Logger logger = Logger.getLogger(PriceTrackingAutoPricingTaskRun.class);

    @Override
    public void run(){
        int i= TaskPool.scheduledThreadPoolTaskExecutor.getActiveCount();
        if(i>30){
            return;
        }
        String isRunging = TempStoreDataSupport.pullData("task_" + getScheduledType());
        if(StringUtils.isNotEmpty(isRunging)){return;}
        ITradingPriceTrackingAutoPricing iTradingPriceTrackingAutoPricing=(ITradingPriceTrackingAutoPricing)ApplicationContextUtil.getBean(ITradingPriceTrackingAutoPricing.class);
        List<TradingPriceTrackingAutoPricing> priceTrackings=iTradingPriceTrackingAutoPricing.selectPriceTrackingAutoPricings();
        if(priceTrackings.size()>20){
            int dou=priceTrackings.size() / 20;
            for(i = 0; i<=dou;i++){
                List<TradingPriceTrackingAutoPricing> list=filterLimitList(priceTrackings,i);
                syschronizePriceTrackingAutoPricing(list);
            }
        }else{
            syschronizePriceTrackingAutoPricing(priceTrackings);
        }
        TempStoreDataSupport.removeData("task_" + getScheduledType());
    }
    private void syschronizePriceTrackingAutoPricing(List<TradingPriceTrackingAutoPricing> autoPricings){
        ITradingPriceTrackingPricingRule iTradingPriceTrackingPricingRule=(ITradingPriceTrackingPricingRule)ApplicationContextUtil.getBean(ITradingPriceTrackingPricingRule.class);
        ITradingListingData iTradingListingData=(ITradingListingData)ApplicationContextUtil.getBean(ITradingListingData.class);
        ITradingPriceTrackingAutoPricing iTradingPriceTrackingAutoPricing=(ITradingPriceTrackingAutoPricing)ApplicationContextUtil.getBean(ITradingPriceTrackingAutoPricing.class);
        ITradingPriceTrackingAutoPricingRecord iTradingPriceTrackingAutoPricingRecord=(ITradingPriceTrackingAutoPricingRecord)ApplicationContextUtil.getBean(ITradingPriceTrackingAutoPricingRecord.class);
        SiteMessageService siteMessageService=(SiteMessageService)ApplicationContextUtil.getBean(SiteMessageService.class);
        for(TradingPriceTrackingAutoPricing autoPricing:autoPricings){
            Double ours=null;
            Double other=null;
            String oldPrice=null;
            String newPrice=null;
            TradingListingData listingData=iTradingListingData.selectById(autoPricing.getListingdateId());
            if(listingData==null){
                logger.error("在线商品没有该商品:SKU"+autoPricing.getSku()+"id:"+autoPricing.getListingdateId());
                return;
            }
            if("2".equals(listingData.getListingType())){
                logger.error("该商品为多属性商品,不能修改价格,SKU"+autoPricing.getSku()+"id:"+autoPricing.getListingdateId());
                List<PublicSitemessage> list1=siteMessageService.selectPublicSitemessageByMessage("price_tracking_auto_pricing_FAIL","定时价格跟踪自动调价的商品是多属性SKU"+autoPricing.getSku()+"id:"+autoPricing.getListingdateId());
                if(list1==null||list1.size()==0){
                    TaskMessageVO taskMessageVO = new TaskMessageVO();
                    taskMessageVO.setMessageType("price_tracking_auto_pricing_FAIL");
                    taskMessageVO.setMessageTitle("定时价格跟踪自动调价的商品是多属性,不能调价!");
                    taskMessageVO.setMessageContext("定时价格跟踪自动调价的商品是多属性,不能调价!");
                    taskMessageVO.setMessageTo(autoPricing.getCreateUser());
                    taskMessageVO.setMessageFrom("system");
                    taskMessageVO.setOrderAndSeller("定时价格跟踪自动调价的商品是多属性SKU"+autoPricing.getSku()+"id:"+autoPricing.getListingdateId());
                    siteMessageService.addSiteMessage(taskMessageVO);
                }
                return;
            }
            ours=listingData.getPrice();
            List<TradingPriceTrackingPricingRule> rules= iTradingPriceTrackingPricingRule.selectTradingPriceTrackingPricingRuleByAutoPricingId(autoPricing.getId());
            if(rules!=null&&rules.size()>0){
                Map<String,Double> prices=syschronizeGetCompetitorPrice(rules);
                Boolean flag=false;
                //商品都满足相关联的各个竞争对手的规则才调价
                if(prices!=null&&prices.size()>0){
                    if("priceLowerType".equals(rules.get(0).getRuletype())){
                        for(TradingPriceTrackingPricingRule rule:rules){
                            other=prices.get(rule.getCompetitoritemid());
                            if(other!=null){
                                if(other>ours){
                                    flag=true;
                                }else{
                                    flag=false;
                                    continue;
                                }
                            }else{
                                flag=false;
                                continue;
                            }
                        }
                    }else{
                        for(TradingPriceTrackingPricingRule rule:rules){
                            other=prices.get(rule.getCompetitoritemid());
                            if(other!=null){
                                if(other<ours){
                                    flag=true;
                                }else{
                                    flag=false;
                                    continue;
                                }
                            }else{
                                flag=false;
                                continue;
                            }
                        }
                    }
                    if(flag){
                        String increaseOrDecrease=rules.get(0).getIncreaseordecrease();
                        String inputValue=rules.get(0).getInputvalue();
                        Double input=0.0;
                        if(StringUtils.isNotBlank(inputValue)){
                            input=Double.valueOf(inputValue);
                        }
                        String sign=rules.get(0).getSign();
                        oldPrice=ours+"";
                        if("-".equals(increaseOrDecrease)){
                            if("$".equals(sign)){
                                newPrice=(ours-input)+"";
                            }else{
                                newPrice=(ours-ours*(input/100))+"";
                            }
                        }else{
                            if("$".equals(sign)){
                                newPrice=(ours+input)+"";
                            }else{
                                newPrice=(ours+ours*(input/100))+"";
                            }
                        }
                        if(StringUtils.isNotBlank(newPrice)){
                            TradingPriceTrackingAutoPricingRecord record=new TradingPriceTrackingAutoPricingRecord();
                            record.setAfterprice(newPrice);
                            record.setBeforeprice(oldPrice);
                            record.setAutopricingId(autoPricing.getId());
                            record.setCreateTime(new Date());
                            record.setCreateUser(autoPricing.getCreateUser());
                            try {
                                iTradingPriceTrackingAutoPricingRecord.savePriceTrackingAutoPricingRecord(record);
                            } catch (Exception e) {
                                logger.error("TradingPriceTrackingAutoPricingRecord保存异常", e);
                            }
                            autoPricing.setNewprice(Double.valueOf(newPrice));
                            try {
                                iTradingPriceTrackingAutoPricing.savePriceTrackingAutoPricing(autoPricing);
                            } catch (Exception e) {
                                logger.error("TradingPriceTrackingAutoPricing保存异常",e);
                            }
                            //调用API修改价格
                            /*Item item = new Item();
                            item.setItemID(listingData.getItemId());
                            StartPrice sp = new StartPrice();
                            sp.setValue(Double.parseDouble(newPrice));
                            item.setStartPrice(sp);
                            ReviseItemRequest rir = new ReviseItemRequest();
                            RequesterCredentials rc = new RequesterCredentials();
                            IUsercontrollerEbayAccount iUsercontrollerEbayAccount=(IUsercontrollerEbayAccount) ApplicationContextUtil.getBean(IUsercontrollerEbayAccount.class);
                            UsercontrollerEbayAccount ebay=iUsercontrollerEbayAccount.selectByEbayAccount(listingData.getEbayAccount());
                            rc.seteBayAuthToken(ebay.getEbayToken());
                            rir.setXmlns("urn:ebay:apis:eBLBaseComponents");
                            rir.setErrorLanguage("en_US");
                            rir.setWarningLevel("High");
                            rir.setRequesterCredentials(rc);
                            rir.setItem(item);
                            String xml = PojoXmlUtil.pojoToXml(rir);
                            xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"+xml;
                            System.out.println(xml);
                            String returnString="";
                            try {
                                returnString = cosPostXml(xml, APINameStatic.ReviseItem);
                                String ack = SamplePaseXml.getVFromXmlString(returnString,"Ack");
                                if("Success".equalsIgnoreCase(ack)||"Warning".equalsIgnoreCase(ack)){
                                    listingData.setPrice(Double.valueOf(newPrice));
                                    List<TradingListingData> datas=new ArrayList<TradingListingData>();
                                    datas.add(listingData);
                                    iTradingListingData.saveTradingListingDataList(datas);
                                }
                            } catch (Exception e) {
                                logger.error("PriceTrackingAutoPricingTaskRun第199操作失败:",e);
                            }*/
                        }

                    }
                }else{
                    logger.error("调用API失败PriceTrackingAutoPricingTaskRun第79");
                    return;
                }

            }else{
                logger.error("没有找到规则,自动调价商品SKU:"+autoPricing.getSku()+"id:"+autoPricing.getId());
                return;
            }


        }
    }
    public String cosPostXml(String colStr,String month) throws Exception {
        UserInfoService userInfoService=(UserInfoService) ApplicationContextUtil.getBean(UserInfoService.class);
        CommAutowiredClass commPars = (CommAutowiredClass) ApplicationContextUtil.getBean(CommAutowiredClass.class);//获取注入的参数
        UsercontrollerDevAccountExtend d = userInfoService.getDevInfo(1L);
        d.setApiSiteid("0");
        d.setApiCallName(month);
        AddApiTask addApiTask = new AddApiTask();
        Map<String, String> resMap= addApiTask.exec(d, colStr, commPars.apiUrl);
        String res=resMap.get("message");
        String ack = SamplePaseXml.getVFromXmlString(res,"Ack");
        if(ack.equals("Success")||"Warning".equalsIgnoreCase(ack)){
            return res;
        }else{
            return res;
        }

    }
    private Map<String,Double> syschronizeGetCompetitorPrice(List<TradingPriceTrackingPricingRule> rules){
        Map<String,Double> map=new HashMap<String, Double>();
        SiteMessageService siteMessageService=(SiteMessageService) ApplicationContextUtil.getBean(SiteMessageService.class);
        String xml="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<GetMultipleItemsRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">";
        for(TradingPriceTrackingPricingRule rule:rules){
            xml+="<ItemID>"+rule.getCompetitoritemid()+"</ItemID>";
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
            logger.error("PriceTrackingAutoPricingTaskRun第94:"+e);
        }
        if("Success".equals(ack)||"Warning".equals(ack)){
            if("Warning".equals(ack)){
                String errors = "";
                try{
                    errors = SamplePaseXml.getVFromXmlString(res, "Errors");
                }catch (Exception e){
                    logger.error("PriceTrackingAutoPricingTaskRun第102"+res,e);
                    errors="";
                }
                if(!StringUtils.isNotBlank(errors)){
                    try{
                        errors =  SamplePaseXml.getWarningInformation(res);
                    }catch (Exception e){
                        logger.error("PriceTrackingAutoPricingTaskRun第109"+res,e);
                    }
                }
                List<PublicSitemessage> list1=siteMessageService.selectPublicSitemessageByMessage("price_tracking_auto_pricing_FAIL","定时价格跟踪自动调价"+rules.get(0).getCompetitoritemid());
                if(list1==null||list1.size()==0){
                    TaskMessageVO taskMessageVO = new TaskMessageVO();
                    taskMessageVO.setMessageType("price_tracking_auto_pricing_FAIL");
                    taskMessageVO.setMessageTitle("定时价格跟踪自动调价有警告!");
                    taskMessageVO.setMessageContext("定时价格跟踪自动调价调用API有警告:" + errors);
                    taskMessageVO.setMessageTo(rules.get(0).getCreateUser());
                    taskMessageVO.setMessageFrom("system");
                    taskMessageVO.setOrderAndSeller("定时价格跟踪自动调价"+rules.get(0).getCompetitoritemid());
                    siteMessageService.addSiteMessage(taskMessageVO);
                }
                logger.error("定时价格跟踪自动调价有警告!" + errors);
            }
            List<TradingPriceTracking> priceTrackings1=new ArrayList<TradingPriceTracking>();
            try {
                priceTrackings1=SamplePaseXml.getPriceTrackingItemByItemId(res);
            } catch (Exception e) {
                logger.error("解析定时价格跟踪自动调价res错误:" + e);
            }
            for(TradingPriceTracking priceTracking:priceTrackings1){
                String price=priceTracking.getCurrentprice();
                String itemid=priceTracking.getItemid();
                if(StringUtils.isNotBlank(price)){
                    map.put(itemid,Double.valueOf(price));
                }
            }
        }else{
            logger.error("定时价格跟踪自动调价调用API失败:"+res);
        }
        return map;
    }
    /**只从集合记录取多少条*/
    private List<TradingPriceTrackingAutoPricing> filterLimitList(List<TradingPriceTrackingAutoPricing> tlist,int i){
        int dou=tlist.size() / 20;
        List<TradingPriceTrackingAutoPricing> list=new ArrayList<TradingPriceTrackingAutoPricing>();
        if(i==dou){
            for(int j=i*20;j<tlist.size();j++){
                TradingPriceTrackingAutoPricing l=tlist.get(j);
                list.add(l);
            }
        }else{
            for(int j=i*20;j<(i+1)*20;j++){
                TradingPriceTrackingAutoPricing l=tlist.get(j);
                list.add(l);
            }
        }
        return list;
    }

    public PriceTrackingAutoPricingTaskRun(){

    }

    @Override
    public String getScheduledType() {
        return MainTask.PRICE_TRACKING_AUTO_PRICING;
    }

    @Override
    public Integer crTimeMinu() {
        return null;
    }

    @Override
    public void setMark(String x) {

    }

    @Override
    public String getMark() {
        return null;
    }
}
