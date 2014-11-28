package com.base.utils.scheduleabout.commontask;

import com.base.database.task.model.ListingDataTask;
import com.base.database.trading.mapper.UsercontrollerEbayAccountMapper;
import com.base.database.trading.model.*;
import com.base.database.userinfo.mapper.UsercontrollerUserMapper;
import com.base.database.userinfo.model.UsercontrollerUser;
import com.base.database.userinfo.model.UsercontrollerUserExample;
import com.base.domains.querypojos.ListingItemReportQuery;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.cache.DataDictionarySupport;
import com.base.utils.scheduleabout.BaseScheduledClass;
import com.base.utils.scheduleabout.MainTask;
import com.base.utils.scheduleabout.Scheduledable;
import com.task.service.IListingDataTask;
import com.trading.service.ITradingListingReport;
import com.trading.service.ITradingListingSuccess;
import org.apache.http.impl.cookie.DateParseException;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/8/29.
 * 在线商品每晚执行，定时任务
 */
public class ItemReportTaskRun extends BaseScheduledClass implements Scheduledable {
    static Logger logger = Logger.getLogger(ItemReportTaskRun.class);
    @Override
    public void run() {
        ITradingListingReport tldm = (ITradingListingReport) ApplicationContextUtil.getBean(ITradingListingReport.class);
        ITradingListingSuccess iTradingListingSuccess = (ITradingListingSuccess) ApplicationContextUtil.getBean(ITradingListingSuccess.class);
        UsercontrollerUserMapper usercontrollerUserMapper = (UsercontrollerUserMapper) ApplicationContextUtil.getBean(UsercontrollerUserMapper.class);
        Map m = new HashMap();
        m.put("0","1");
        m.put("1","2");
        m.put("2","3");
        m.put("3","4");
        m.put("4","5");
        m.put("5","6");
        UsercontrollerUserExample uue = new UsercontrollerUserExample();
        uue.createCriteria().andStatusEqualTo("1");
        List<UsercontrollerUser> liuu = usercontrollerUserMapper.selectByExample(uue);
        if(liuu!=null&&liuu.size()>0){
            for(UsercontrollerUser uu:liuu){
//昨天刊登
                List<ListingItemReportQuery> dayListing = iTradingListingSuccess.selectListingItemReport(uu.getUserId(),"2","1",null);
                //上周刊登
                List<ListingItemReportQuery> weekListing = iTradingListingSuccess.selectListingItemReport(uu.getUserId(),"4","1",null);
                //上月刊登
                List<ListingItemReportQuery> monthListing = iTradingListingSuccess.selectListingItemReport(uu.getUserId(),"6","1",null);
                //昨天天结束刊登
                List<ListingItemReportQuery> dayendListing = iTradingListingSuccess.selectListingItemReport(uu.getUserId(),"2","2",null);
                //上周结束刊登
                List<ListingItemReportQuery> weekendListing = iTradingListingSuccess.selectListingItemReport(uu.getUserId(),"4","2",null);
                //上月结束刊登
                List<ListingItemReportQuery> monthendListing = iTradingListingSuccess.selectListingItemReport(uu.getUserId(),"6","2",null);
                //昨天结束刊登买出去有
                List<ListingItemReportQuery> dayendListingSold = iTradingListingSuccess.selectListingItemReport(uu.getUserId(),"2","2","1");
                //上周结束刊登买出去有
                List<ListingItemReportQuery> weekendListingSold = iTradingListingSuccess.selectListingItemReport(uu.getUserId(),"4","2","1");
                //上月结束刊登买出去有
                List<ListingItemReportQuery> monthendListingSold = iTradingListingSuccess.selectListingItemReport(uu.getUserId(),"6","2","1");

                //计算刊登费用
                //当天刊登费
                List<ListingItemReportQuery> dayListingFee = iTradingListingSuccess.selectListingItemReportFee(uu.getUserId(),"2","1",null);
                //本周刊登费
                List<ListingItemReportQuery> weekListingFee = iTradingListingSuccess.selectListingItemReportFee(uu.getUserId(),"4","1",null);
                //本月刊登费
                List<ListingItemReportQuery> monthListingFee = iTradingListingSuccess.selectListingItemReportFee(uu.getUserId(),"6","1",null);


                //当天结束刊登费
                List<ListingItemReportQuery> dayListingEndFee = iTradingListingSuccess.selectListingItemReportFee(uu.getUserId(),"2","2",null);
                //本周结束刊登费
                List<ListingItemReportQuery> weekListingEndFee = iTradingListingSuccess.selectListingItemReportFee(uu.getUserId(),"4","2",null);
                //本月结束刊登费
                List<ListingItemReportQuery> monthListingEndFee = iTradingListingSuccess.selectListingItemReportFee(uu.getUserId(),"6","2",null);

                for(int i=0;i<m.size();i++){
                    try {
                        List<TradingListingReport> litlr = tldm.selectByNowDate((String) m.get(i + ""));
                        if(litlr!=null&&litlr.size()>0){
                            TradingListingReport tlr = litlr.get(0);
                            if(m.get(i+"").equals("1")){
                                tlr.setYesterday(dayListing.size()>0?dayListing.get(0).getTjNumber():"0");
                                tlr.setThatweek(weekListing.size()>0?weekListing.get(0).getTjNumber():"0");
                                tlr.setThatmonth(monthListing.size()>0?monthListing.get(0).getTjNumber():"0");
                            }else if(m.get(i+"").equals("2")){
                                tlr.setYesterday(dayendListing.size()>0?dayendListing.get(0).getTjNumber():"0");
                                tlr.setThatweek(weekendListing.size()>0?weekendListing.get(0).getTjNumber():"0");
                                tlr.setThatmonth(monthendListing.size()>0?monthendListing.get(0).getTjNumber():"0");
                            }else if(m.get(i+"").equals("3")){
                                tlr.setYesterday(dayendListingSold.size()>0?dayendListingSold.get(0).getTjNumber():"0");
                                tlr.setThatweek(weekendListingSold.size()>0?weekendListingSold.get(0).getTjNumber():"0");
                                tlr.setThatmonth(monthendListingSold.size()>0?monthendListingSold.get(0).getTjNumber():"0");
                            }else if(m.get(i+"").equals("5")){
                                tlr.setYesterday(dayListingFee.size()>0&&dayListingFee.get(0).getTjNumber()!=null?dayListingFee.get(0).getTjNumber():"0.00");
                                tlr.setThatweek(weekListingFee.size()>0&&weekListingFee.get(0).getTjNumber()!=null?weekListingFee.get(0).getTjNumber():"0.00");
                                tlr.setThatmonth(monthListingFee.size()>0&&monthListingFee.get(0).getTjNumber()!=null?monthListingFee.get(0).getTjNumber():"0.00");
                            }else if(m.get(i+"").equals("6")){
                                tlr.setYesterday(dayListingEndFee.size()>0&&dayListingEndFee.get(0).getTjNumber()!=null?dayListingEndFee.get(0).getTjNumber():"0.00");
                                tlr.setThatweek(weekListingEndFee.size()>0&&weekListingEndFee.get(0).getTjNumber()!=null?weekListingEndFee.get(0).getTjNumber():"0.00");
                                tlr.setThatmonth(monthListingEndFee.size()>0&&monthListingEndFee.get(0).getTjNumber()!=null?monthListingEndFee.get(0).getTjNumber():"0.00");
                            }
                            tlr.setUserId(uu.getUserId()+"");
                            tldm.save(tlr);
                        }else{
                            TradingListingReport tlr = new TradingListingReport();
                            tlr.setDatatype((String)m.get(i+""));
                            tlr.setCreateDate(new Date());
                            tlr.setUserId(uu.getUserId()+"");
                            if(m.get(i+"").equals("1")){
                                tlr.setYesterday(dayListing.size()>0?dayListing.get(0).getTjNumber():"0");
                                tlr.setThatweek(weekListing.size()>0?weekListing.get(0).getTjNumber():"0");
                                tlr.setThatmonth(monthListing.size()>0?monthListing.get(0).getTjNumber():"0");
                            }else if(m.get(i+"").equals("2")){
                                tlr.setYesterday(dayendListing.size()>0?dayendListing.get(0).getTjNumber():"0");
                                tlr.setThatweek(weekendListing.size()>0?weekendListing.get(0).getTjNumber():"0");
                                tlr.setThatmonth(monthendListing.size()>0?monthendListing.get(0).getTjNumber():"0");
                            }else if(m.get(i+"").equals("3")){
                                tlr.setYesterday(dayendListingSold.size()>0?dayendListingSold.get(0).getTjNumber():"0");
                                tlr.setThatweek(weekendListingSold.size()>0?weekendListingSold.get(0).getTjNumber():"0");
                                tlr.setThatmonth(monthendListingSold.size()>0?monthendListingSold.get(0).getTjNumber():"0");
                            }else if(m.get(i+"").equals("5")){
                                tlr.setYesterday(dayListingFee.size()>0&&dayListingFee.get(0).getTjNumber()!=null?dayListingFee.get(0).getTjNumber():"0.00");
                                tlr.setThatweek(weekListingFee.size()>0&&weekListingFee.get(0).getTjNumber()!=null?weekListingFee.get(0).getTjNumber():"0.00");
                                tlr.setThatmonth(monthListingFee.size()>0&&monthListingFee.get(0).getTjNumber()!=null?monthListingFee.get(0).getTjNumber():"0.00");
                            }else if(m.get(i+"").equals("6")){
                                tlr.setYesterday(dayListingEndFee.size()>0&&dayListingEndFee.get(0).getTjNumber()!=null?dayListingEndFee.get(0).getTjNumber():"0.00");
                                tlr.setThatweek(weekListingEndFee.size()>0&&weekListingEndFee.get(0).getTjNumber()!=null?weekListingEndFee.get(0).getTjNumber():"0.00");
                                tlr.setThatmonth(monthListingEndFee.size()>0&&monthListingEndFee.get(0).getTjNumber()!=null?monthListingEndFee.get(0).getTjNumber():"0.00");
                            }
                            tldm.save(tlr);
                        }
                    } catch (DateParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    /**只从集合记录取多少条*/
    private List<UsercontrollerEbayAccount> filterLimitList(List<UsercontrollerEbayAccount> tlist){
        return filterLimitListFinal(tlist,2);
        /*List<UsercontrollerEbayAccount> x=new ArrayList<UsercontrollerEbayAccount>();
        for (int i = 0;i<2;i++){
            x.add(tlist.get(i));
        }
        return x;*/
    }

    public ItemReportTaskRun(){

    }

    @Override
    public String getScheduledType() {
        return MainTask.LISTING_REPORT;
    }

    @Override
    public Integer crTimeMinu() {
        return null;
    }
}
