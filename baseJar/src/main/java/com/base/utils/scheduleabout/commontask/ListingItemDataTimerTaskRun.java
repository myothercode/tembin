package com.base.utils.scheduleabout.commontask;

import com.base.database.task.model.ListingDataTask;
import com.base.database.trading.mapper.TradingListingDataMapper;
import com.base.database.trading.model.TradingListingData;
import com.base.database.trading.model.TradingListingDataExample;
import com.base.database.trading.model.TradingListingSuccess;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.sampleapixml.APINameStatic;
import com.base.userinfo.service.UserInfoService;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.cache.TempStoreDataSupport;
import com.base.utils.common.CommAutowiredClass;
import com.base.utils.common.DateUtils;
import com.base.utils.common.SystemLogUtils;
import com.base.utils.scheduleabout.BaseScheduledClass;
import com.base.utils.scheduleabout.MainTask;
import com.base.utils.scheduleabout.Scheduledable;
import com.base.utils.threadpool.AddApiTask;
import com.base.utils.xmlutils.SamplePaseXml;
import com.task.service.IListingDataTask;
import com.trading.service.ITradingListingSuccess;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
                "<StartTimeFrom>"+startTime+"</StartTimeFrom>\n" +
                "<StartTimeTo>"+endTime+"</StartTimeTo>\n" +
                "<UserID>"+ebayName+"</UserID>\n" +
                "<IncludeVariations>true</IncludeVariations><IncludeWatchCount>true</IncludeWatchCount>\n" +
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
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date beginDate = new Date();
        Calendar date = Calendar.getInstance();
        date.setTime(beginDate);
        date.set(Calendar.DATE, date.get(Calendar.DATE) - 119);
        String startTo="";
        String startFrom="";
        try {
            Date endDate = dft.parse(dft.format(date.getTime()));
            startTo = DateUtils.DateToString(new Date());
            startFrom = DateUtils.DateToString(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        AddApiTask addApiTask = new AddApiTask();
        UsercontrollerDevAccountExtend d = new UsercontrollerDevAccountExtend();
        d.setApiCallName(APINameStatic.ListingItemList);
        d.setApiSiteid(siteid);
        String colStr = this.getCosXml(ebayAccount, startFrom, startTo, 1, token);
        try {
            Map<String, String> resMap= addApiTask.exec2(d, colStr, commPars.apiUrl);
            String res=resMap.get("message");
            System.out.println(res);
            String ack = SamplePaseXml.getVFromXmlString(res, "Ack");
            if(ack.equals("Success")) {
                Document document  = DocumentHelper.parseText(res);
                Element rootElt = document.getRootElement();
                Element totalElt = rootElt.element("PaginationResult");
                String totalCount = totalElt.elementText("TotalNumberOfEntries");
                String page = totalElt.elementText("TotalNumberOfPages");
                for(int i=1;i<=Integer.parseInt(page);i++) {
                    String colXml = this.getCosXml(ebayAccount, startFrom, startTo, i, token);
                    Map<String, String> resMapxml = addApiTask.exec2(d, colXml, commPars.apiUrl);
                    String returnstr = resMapxml.get("message");
                    System.out.println(returnstr);
                    String retrunack = SamplePaseXml.getVFromXmlString(returnstr, "Ack");
                    if (retrunack.equals("Success")) {
                        List<TradingListingData> litld = SamplePaseXml.getItemListElememt(returnstr, ebayAccount);
                        for(TradingListingData td : litld){
                            td.setEbayAccount(ebayAccount);
                            td.setCreateUser(userid);
                            TradingListingDataExample tlde  = new TradingListingDataExample();
                            tlde.createCriteria().andItemIdEqualTo(td.getItemId());
                            List<TradingListingData> litl = tldm.selectByExample(tlde);
                            if(litl!=null&&litl.size()>0){
                                TradingListingData oldtd = litl.get(0);
                                td.setId(oldtd.getId());
                                tldm.updateByPrimaryKeySelective(td);
                            }else{
                                tldm.insertSelective(td);
                            }
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
            }
            return "1";
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }


    }
    @Override
    public void run() {
        String isRunging = TempStoreDataSupport.pullData("task_"+getScheduledType());
        if(StringUtils.isNotEmpty(isRunging)){return;}
        TempStoreDataSupport.pushData("task_" + getScheduledType(), "x");
        IListingDataTask iListingDataTask = (IListingDataTask) ApplicationContextUtil.getBean(IListingDataTask.class);
        List<ListingDataTask> lildt = iListingDataTask.selectByTimerTaskflag();
        if(lildt.size()>2){
            lildt =filterLimitList(lildt);
        }
        String taskFlag="";
        for(ListingDataTask ldt:lildt){
            taskFlag = this.saveListingData(ldt.getEbayaccount(),ldt.getUserid(),ldt.getToken(),ldt.getSite());
            ldt.setTaskFlag(taskFlag);
            ldt.setCreateDate(new Date());
            iListingDataTask.saveListDataTask(ldt);
        }
        TempStoreDataSupport.removeData("task_"+getScheduledType());
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
        return null;
    }
}
