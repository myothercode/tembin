package com.base.utils.scheduleabout.commontask;

import com.base.database.keymove.mapper.KeyMoveListMapper;
import com.base.database.keymove.model.KeyMoveList;
import com.base.database.keymove.model.KeyMoveListExample;
import com.base.database.task.model.ListingDataTask;
import com.base.database.trading.mapper.TradingListingDataMapper;
import com.base.database.trading.mapper.UsercontrollerEbayAccountMapper;
import com.base.database.trading.model.*;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.sampleapixml.APINameStatic;
import com.base.userinfo.service.UserInfoService;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.cache.DataDictionarySupport;
import com.base.utils.cache.TempStoreDataSupport;
import com.base.utils.common.CommAutowiredClass;
import com.base.utils.common.DateUtils;
import com.base.utils.scheduleabout.BaseScheduledClass;
import com.base.utils.scheduleabout.MainTask;
import com.base.utils.scheduleabout.Scheduledable;
import com.base.utils.threadpool.AddApiTask;
import com.base.utils.xmlutils.SamplePaseXml;
import com.base.xmlpojo.trading.addproduct.Item;
import com.task.service.IListingDataTask;
import com.task.service.impl.ListingDataTaskImpl;
import com.trading.service.ITradingItem;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrtor on 2014/8/29.
 * 在线商品每晚执行，定时任务
 */
public class ListingItemDataTaskRun extends BaseScheduledClass implements Scheduledable {
    static Logger logger = Logger.getLogger(ListingItemDataTaskRun.class);
    @Override
    public void run() {
        IListingDataTask iListingDataTask = (IListingDataTask) ApplicationContextUtil.getBean(IListingDataTask.class);
        List<TradingDataDictionary> litdd = DataDictionarySupport.getTradingDataDictionaryByType("site");
        UsercontrollerEbayAccountMapper ueam = (UsercontrollerEbayAccountMapper) ApplicationContextUtil.getBean(UsercontrollerEbayAccountMapper.class);
        UsercontrollerEbayAccountExample ueame = new UsercontrollerEbayAccountExample();
        ueame.createCriteria().andEbayStatusEqualTo("1");
        List<UsercontrollerEbayAccount> liue = ueam.selectByExampleWithBLOBs(ueame);
        for(int i =0 ;i<liue.size();i++) {
            //先同步美国站点，如果数据不全，那么就需要同步所有站点
            UsercontrollerEbayAccount ue = liue.get(i);
            List<ListingDataTask> lidk = iListingDataTask.selectByflag("0",ue.getEbayAccount());
            if(lidk==null||lidk.size()==0) {
                ListingDataTask ldt = new ListingDataTask();
                ldt.setCreateDate(new Date());
                ldt.setEbayaccount(ue.getEbayAccount());
                ldt.setSite("0");
                ldt.setToken(ue.getEbayToken());
                ldt.setUserid(ue.getUserId());
                ldt.setTaskFlag("0");
                iListingDataTask.saveListDataTask(ldt);
            }
            /*for(TradingDataDictionary tdd:litdd){//同步21个站点
                List<ListingDataTask> lidk = iListingDataTask.selectByflag(tdd.getName1(),ue.getEbayAccount());
                if(lidk==null||lidk.size()==0){
                    ListingDataTask ldt= new ListingDataTask();
                    ldt.setCreateDate(new Date());
                    ldt.setEbayaccount(ue.getEbayAccount());
                    ldt.setSite(tdd.getName1());
                    ldt.setToken(ue.getEbayToken());
                    ldt.setUserid(ue.getUserId());
                    ldt.setTaskFlag("0");
                    iListingDataTask.saveListDataTask(ldt);
                }
            }*/
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

    public ListingItemDataTaskRun(){

    }

    @Override
    public String getScheduledType() {
        return MainTask.LISTING_DATA;
    }

    @Override
    public Integer crTimeMinu() {
        return 10;
    }
}
