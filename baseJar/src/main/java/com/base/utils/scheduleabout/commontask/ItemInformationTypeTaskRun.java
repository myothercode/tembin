package com.base.utils.scheduleabout.commontask;

import com.base.database.publicd.model.PublicDataDict;
import com.base.database.publicd.model.PublicItemInformation;
import com.base.database.trading.model.TradingReseCategory;
import com.base.database.userinfo.model.SystemLog;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.cache.DataDictionarySupport;
import com.base.utils.cache.TempStoreDataSupport;
import com.base.utils.common.CommAutowiredClass;
import com.base.utils.common.SystemLogUtils;
import com.base.utils.httpclient.HttpClientUtil;
import com.base.utils.scheduleabout.BaseScheduledClass;
import com.base.utils.scheduleabout.MainTask;
import com.base.utils.scheduleabout.Scheduledable;
import com.base.utils.threadpool.TaskPool;
import com.base.utils.xmlutils.SamplePaseXml;
import com.publicd.service.IPublicItemInformation;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/8/29.
 * 在线商品每晚执行，定时任务
 */
public class ItemInformationTypeTaskRun extends BaseScheduledClass implements Scheduledable {
    static Logger logger = Logger.getLogger(ItemInformationTypeTaskRun.class);
    private void syschronizeItemInformationType(List<PublicItemInformation> informations){
        CommAutowiredClass commV = (CommAutowiredClass) ApplicationContextUtil.getBean(CommAutowiredClass.class);
        IPublicItemInformation iPublicItemInformation=(IPublicItemInformation) ApplicationContextUtil.getBean(IPublicItemInformation.class);

        for(PublicItemInformation itemInformation:informations){
            try {
                StringBuffer sb = new StringBuffer();
                sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                sb.append("<findItemsByKeywordsRequest xmlns=\"http://www.ebay.com/marketplace/search/v1/services\">");
                sb.append("<keywords>" + itemInformation.getName() + "</keywords>");
                sb.append("<paginationInput>");
                sb.append("<pageNumber>1</pageNumber><entriesPerPage>1</entriesPerPage>");
                sb.append("</paginationInput>");
                sb.append("</findItemsByKeywordsRequest>");
                List<TradingReseCategory> litr = new ArrayList<TradingReseCategory>();
              /*  TradingDataDictionary tdd = DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(siteid));*/
                if(itemInformation.getName()!=null&&!"".equals(itemInformation.getName())) {
                    List<BasicHeader> headers = new ArrayList<BasicHeader>();
                    headers.add(new BasicHeader("X-EBAY-SOA-SERVICE-NAME", "FindingService"));
                    headers.add(new BasicHeader("X-EBAY-SOA-OPERATION-NAME", "findItemsByKeywords"));
                    headers.add(new BasicHeader("X-EBAY-SOA-SERVICE-VERSION", "1.12.0"));
                    headers.add(new BasicHeader("X-EBAY-SOA-GLOBAL-ID", "EBAY-US"));
                    headers.add(new BasicHeader("X-EBAY-SOA-SECURITY-APPNAME", "sandpoin-23af-4f47-a304-242ffed6ff5b"));
                    headers.add(new BasicHeader("X-EBAY-SOA-REQUEST-DATA-FORMAT", "XML"));
                    HttpClient httpClient = HttpClientUtil.getHttpsClient();
                    String res = HttpClientUtil.post(httpClient, commV.findingkeyapiUrl, sb.toString(), "UTF-8", headers);
                    litr = SamplePaseXml.selectCategoryBytitle(res, itemInformation.getName());
                    if(litr.size()>0){
                        String typeid=litr.get(0).getCategoryId();
                        String typename=litr.get(0).getCategoryName();
                        if(StringUtils.isNotBlank(typeid)){
                            itemInformation.setTypeId(Long.valueOf(typeid));
                        }
                        if(StringUtils.isNotBlank(typename)){
                            itemInformation.setTypename(typename);
                        }
                        Map<String,String> map=new HashMap<String, String>();
                        map.put("item_id",typeid);
                        map.put("site_id","311");
                        List<PublicDataDict> list = DataDictionarySupport.getPublicDataDictionaryByMap(map);
                        if(list.size()==0){
                            List<SystemLog> logs=SystemLogUtils.selectSystemLogByEventnameAndEventdesc(SystemLogUtils.ITEM_INFORMATION_TYPE,"itemId:"+typeid+",siteId:311,商品名称:"+typename);
                            if(logs.size()==0){
                                SystemLog systemLog=new SystemLog();
                                systemLog.setEventname(SystemLogUtils.ITEM_INFORMATION_TYPE);
                                systemLog.setOperuser(itemInformation.getCreateUser()+"");
                                systemLog.setEventdesc("itemId:"+typeid+",siteId:311,商品名称:"+typename);
                                SystemLogUtils.saveLog(systemLog);
                            }
                        }
                    }
                    itemInformation.setTypeflag(1);
                    iPublicItemInformation.saveItemInformation(itemInformation);
                }
            } catch (Exception e) {
                itemInformation.setTypeflag(1);
                try {
                    iPublicItemInformation.saveItemInformation(itemInformation);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                e.printStackTrace();
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
        IPublicItemInformation iPublicItemInformation=(IPublicItemInformation) ApplicationContextUtil.getBean(IPublicItemInformation.class);
        List<PublicItemInformation> informations=iPublicItemInformation.selectItemInformationByTypeIsNull();
        if(informations.size()>20){
            informations=filterLimitList(informations);
        }
        syschronizeItemInformationType(informations);
        TempStoreDataSupport.removeData("task_" + getScheduledType());

    }

    /**只从集合记录取多少条*/
    private List<PublicItemInformation> filterLimitList(List<PublicItemInformation> tlist){
        return filterLimitListFinal(tlist,20);
    }

    public ItemInformationTypeTaskRun(){

    }

    @Override
    public String getScheduledType() {
        return MainTask.ITEM_INFORMATION_TYPE;
    }

    @Override
    public Integer crTimeMinu() {
        return null;
    }
}
