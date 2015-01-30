package com.base.utils.scheduleabout.commontask;

import com.base.database.task.model.ListingDataTask;
import com.base.database.task.model.TaskComplement;
import com.base.database.task.model.TaskGetOrders;
import com.base.database.task.model.TradingTaskXml;
import com.base.database.trading.mapper.TradingListingDataMapper;
import com.base.database.trading.model.*;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.sampleapixml.APINameStatic;
import com.base.userinfo.service.UserInfoService;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.cache.TempStoreDataSupport;
import com.base.utils.common.CommAutowiredClass;
import com.base.utils.common.DateUtils;
import com.base.utils.common.MyStringUtil;
import com.base.utils.common.SystemLogUtils;
import com.base.utils.scheduleabout.BaseScheduledClass;
import com.base.utils.scheduleabout.MainTask;
import com.base.utils.scheduleabout.MainTaskStaticParam;
import com.base.utils.scheduleabout.Scheduledable;
import com.base.utils.threadpool.AddApiTask;
import com.base.utils.threadpool.TaskPool;
import com.base.utils.xmlutils.SamplePaseXml;
import com.complement.service.ITradingAutoComplement;
import com.task.service.IListingDataTask;
import com.task.service.ITaskComplement;
import com.task.service.ITradingTaskXml;
import com.trading.service.ITradingItem;
import com.trading.service.ITradingListingSuccess;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrtor on 2014/8/29.
 * 在线商品每2分钟执行，定时任务
 */
public class ListingItemDataTimerTaskRun extends BaseScheduledClass implements Scheduledable {
    static Logger logger = Logger.getLogger(ListingItemDataTimerTaskRun.class);


    public String getCosXml(String ebayName,String startTime,String endTime,int pageNumber,String token){
        String colStr="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<GetSellerListRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">\n" +
                "<RequesterCredentials>\n" +
                "<eBayAuthToken>"+token+"</eBayAuthToken>\n" +
                "</RequesterCredentials>\n" +
                "<Pagination ComplexType=\"PaginationType\">\n" +
                "\t<EntriesPerPage>100</EntriesPerPage>\n" +
                "\t<PageNumber>"+pageNumber+"</PageNumber>\n" +
                "</Pagination>\n" +
                "<EndTimeFrom>"+startTime+"</EndTimeFrom>\n" +
                "<EndTimeTo>"+endTime+"</EndTimeTo>\n" +
                "<UserID>"+ebayName+"</UserID>\n" +
                "<GranularityLevel>Fine</GranularityLevel><IncludeVariations>true</IncludeVariations><IncludeWatchCount>true</IncludeWatchCount>\n" +
                "<DetailLevel>ReturnAll</DetailLevel>\n" +
                "</GetSellerListRequest>​";
        return colStr;
    }

    public String  saveListingData(String ebayAccount,Long userid,String token,String siteid){
        String datestr = DateUtils.formatDateTime(new Date());
        UserInfoService userInfoService = (UserInfoService) ApplicationContextUtil.getBean(UserInfoService.class);
        TradingListingDataMapper tldm = (TradingListingDataMapper) ApplicationContextUtil.getBean(TradingListingDataMapper.class);
        CommAutowiredClass commPars = (CommAutowiredClass) ApplicationContextUtil.getBean(CommAutowiredClass.class);//获取注入的参数
        ITradingListingSuccess iTradingListingSuccess = (ITradingListingSuccess) ApplicationContextUtil.getBean(ITradingListingSuccess.class);
        ITradingAutoComplement iTradingAutoComplement = (ITradingAutoComplement) ApplicationContextUtil.getBean(ITradingAutoComplement.class);
        ITaskComplement iTaskComplement = (ITaskComplement) ApplicationContextUtil.getBean(ITaskComplement.class);
        ITradingTaskXml iTradingTaskXml = (ITradingTaskXml) ApplicationContextUtil.getBean(ITradingTaskXml.class);
        ITradingItem iTradingItem = (ITradingItem) ApplicationContextUtil.getBean(ITradingItem.class);
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date beginDate = new Date();
        Calendar date = Calendar.getInstance();
        date.setTime(beginDate);
        date.set(Calendar.DATE, date.get(Calendar.DATE) + 119);
        String endTo="";
        String endFrom="";
        try {
            endTo = DateUtils.DateToString(DateUtils.nowDateAddDay(100));
            endFrom = DateUtils.DateToString(DateUtils.nowDateMinusDay(18));
        } catch (Exception e) {
            logger.error("ListItemDataTimerTask:",e);
        }
        String saveXml = "";
        AddApiTask addApiTask = new AddApiTask();
        UsercontrollerDevAccountExtend d = new UsercontrollerDevAccountExtend();
        d.setApiCallName(APINameStatic.ListingItemList);
        d.setApiSiteid(siteid);
        String colStr = this.getCosXml(ebayAccount, endFrom, endTo, 1, token);
        String res="";
        try {
            Map<String, String> resMap= addApiTask.exec2(d, colStr, commPars.apiUrl);
            res=resMap.get("message");
            saveXml = res;
            String ack = SamplePaseXml.getVFromXmlString(res, "Ack");
            if(ack.equals("Success")) {
                Document document  = SamplePaseXml.formatStr2Doc(res);
                Element rootElt = document.getRootElement();
                Element totalElt = rootElt.element("PaginationResult");
                String totalCount = totalElt.elementText("TotalNumberOfEntries");
                String page = totalElt.elementText("TotalNumberOfPages");
                for(int i=1;i<=Integer.parseInt(page);i++) {
                    String colXml = this.getCosXml(ebayAccount, endFrom, endTo, i, token);
                    Map<String, String> resMapxml = addApiTask.exec2(d, colXml, commPars.apiUrl);
                    String returnstr = resMapxml.get("message");
                    System.out.println(returnstr);
                    saveXml=returnstr;
                    String retrunack = SamplePaseXml.getVFromXmlString(returnstr, "Ack");
                    if (retrunack.equals("Success")) {
                        List<TradingListingData> litld = SamplePaseXml.getItemListElememt(returnstr, ebayAccount);
                        for(TradingListingData td : litld){
                            td.setEbayAccount(ebayAccount);
                            td.setCreateUser(userid);
                            TradingListingDataExample tlde  = new TradingListingDataExample();
                            tlde.createCriteria().andItemIdEqualTo(td.getItemId());
                            List<TradingListingData> litl = tldm.selectByExample(tlde);
                            List<TradingAutoComplement> litac = iTradingAutoComplement.selectByEbayAccount(td.getEbayAccount());
                            boolean isFlag = true;
                            if(litl!=null&&litl.size()>0){
                                TradingListingData oldtd = litl.get(0);
                                td.setId(oldtd.getId());
                                td.setUpdateDate(new Date());
                                tldm.updateByPrimaryKeySelective(td);
                            }else{
                                td.setUpdateDate(new Date());
                                td.setCreateDate(new Date());
                                tldm.insertSelective(td);
                            }
                            //如查物品下架，改变范本表中的状态
                            if("1".equals(td.getIsFlag())) {
                                TradingItemWithBLOBs tradingItemWithBLOBs = iTradingItem.selectByItemId(td.getItemId());
                                if (tradingItemWithBLOBs != null) {
                                    tradingItemWithBLOBs.setIsFlag("");
                                    tradingItemWithBLOBs.setItemId("");
                                    iTradingItem.saveTradingItem(tradingItemWithBLOBs);
                                }
                            }

                            iTradingAutoComplement.checkAutoComplementType(td,token,siteid);
                            List<TradingListingSuccess> litls = iTradingListingSuccess.selectByItemid(td.getItemId());
                            if(litls==null||litls.size()==0){
                                TradingListingSuccess tls = new TradingListingSuccess();
                                tls.setItemId(td.getItemId());
                                tls.setStartDate(td.getStarttime());
                                tls.setEndDate(td.getEndtime());
                                iTradingListingSuccess.save(tls);
                            }else{
                                TradingListingSuccess tls = litls.get(0);
                                tls.setStartDate(td.getStarttime());
                                tls.setEndDate(td.getEndtime());
                                iTradingListingSuccess.save(tls);
                            }
                        }
                    }
                }
            }else{
                return "2";
            }
            return "1";
        } catch (Exception e) {
            TradingTaskXml ttx = new TradingTaskXml();
            ttx.setCreateDate(new Date());
            ttx.setTaskType(getScheduledType());
            ttx.setXmlContent(saveXml);
            iTradingTaskXml.saveTradingTaskXml(ttx);
            logger.error("listItemDT:",e);
            return "2";
        }


    }
    @Override
    public void run() {
        IListingDataTask iListingDataTask = (IListingDataTask) ApplicationContextUtil.getBean(IListingDataTask.class);
        List<ListingDataTask> lildt= null;
        if (MainTaskStaticParam.CATCH_LISTINGDATA_QUEUE.isEmpty()){
            lildt = iListingDataTask.selectByTimerTaskflag();
            if (lildt==null || lildt.isEmpty()){return;}
        }

        if(MainTaskStaticParam.CATCH_LISTINGDATA_QUEUE.isEmpty()){
            for (ListingDataTask t : lildt){
                try {
                    Boolean b= TaskPool.threadIsAliveByName("thread_" + getScheduledType() + "_" + t.getId());
                    if (b){
                        //logger.error(getScheduledType()+t.getId()+"===之前的帐号任务还未结束不放入===");
                        continue;
                    }
                    MainTaskStaticParam.CATCH_LISTINGDATA_QUEUE.put(t);
                } catch (Exception e) {logger.error("放入ListingData队列出错",e);continue;}
            }
        }

        String taskFlag="";
        ListingDataTask o = null;
        try {
            Iterator<ListingDataTask> iterator=MainTaskStaticParam.CATCH_LISTINGDATA_QUEUE.iterator();
            while (iterator.hasNext()){
                ListingDataTask oo=MainTaskStaticParam.CATCH_LISTINGDATA_QUEUE.take();
                if (oo==null){continue;}
                Boolean b= TaskPool.threadIsAliveByName("thread_" + getScheduledType() + "_" + oo.getId());
                if (b){
                    //logger.error(getScheduledType()+oo.getId()+"===之前的帐号任务还未结束取下一个===");
                    continue;
                }
                o=oo;
                break;
            }
        } catch (Exception e) {

        }
        if(o==null){
            return;
        }
        //logger.error(getScheduledType() +o.getId() + "===任务开始===");
        Thread.currentThread().setName("thread_" + getScheduledType()+"_"+o.getId());
        taskFlag = this.saveListingData(o.getEbayaccount(),o.getUserid(),o.getToken(),o.getSite());
        o.setTaskFlag(taskFlag);
        o.setCreateDate(new Date());
        iListingDataTask.saveListDataTask(o);
        TaskPool.threadRunTime.remove("thread_" + getScheduledType()+"_"+o.getId());
        Thread.currentThread().setName("thread_" + getScheduledType()+"_"+o.getId()+ MyStringUtil.getRandomStringAndNum(5));
        //logger.error(getScheduledType()+o.getId() + "===任务结束===");
        /*for(ListingDataTask ldt:lildt){
            taskFlag = this.saveListingData(ldt.getEbayaccount(),ldt.getUserid(),ldt.getToken(),ldt.getSite());
            ldt.setTaskFlag(taskFlag);
            ldt.setCreateDate(new Date());
            iListingDataTask.saveListDataTask(ldt);
        }*/
    }

    /**只从集合记录取多少条*/
    private List<ListingDataTask> filterLimitList(List<ListingDataTask> tlist){

        return filterLimitListFinal(tlist,2);

        /*List<ListingDataTask> x=new ArrayList<ListingDataTask>();
        for (int i = 0;i<2;i++){
            x.add(tlist.get(i));
        }
        return x;*/
    }

    public ListingItemDataTimerTaskRun(){

    }

    @Override
    public String getScheduledType() {
        return MainTask.LISTING_TIMER_TASK_DATA;
    }

    @Override
    public Integer crTimeMinu() {
        return 2;
    }

    @Override
    public void setMark(String x) {

    }

    @Override
    public String getMark() {
        return null;
    }
}
