package com.base.utils.scheduleabout.commontask;

import com.base.database.keymove.mapper.KeyMoveListMapper;
import com.base.database.keymove.model.KeyMoveList;
import com.base.database.keymove.model.KeyMoveListExample;
import com.base.database.trading.model.TradingTimerListingWithBLOBs;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.sampleapixml.APINameStatic;
import com.base.userinfo.mapper.UserInfoServiceMapper;
import com.base.userinfo.service.UserInfoService;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.cache.DataDictionarySupport;
import com.base.utils.common.CommAutowiredClass;
import com.base.utils.scheduleabout.BaseScheduledClass;
import com.base.utils.scheduleabout.MainTask;
import com.base.utils.scheduleabout.Scheduledable;
import com.base.utils.threadpool.AddApiTask;
import com.base.utils.xmlutils.SamplePaseXml;
import com.base.xmlpojo.trading.addproduct.Item;
import com.trading.service.ITradingItem;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Created by Administrtor on 2014/8/29.
 * 开发帐号API次数调用归零
 */
public class SerDevZeroTaskRun extends BaseScheduledClass implements Scheduledable {
    static Logger logger = Logger.getLogger(SerDevZeroTaskRun.class);
    @Override
    public void run() {
        UserInfoServiceMapper userInfoServiceMapper = (UserInfoServiceMapper) ApplicationContextUtil.getBean(UserInfoServiceMapper.class);
        Map map = new HashMap();
        map.put("udate",new Date());
        userInfoServiceMapper.initUseNum(map);
    }

    /**只从集合记录取多少条
    private List<TradingTimerListingWithBLOBs> filterLimitList(List<TradingTimerListingWithBLOBs> tlist){

        List<TradingTimerListingWithBLOBs> x=new ArrayList<TradingTimerListingWithBLOBs>();
        for (int i = 0;i<20;i++){
            x.add(tlist.get(i));
        }
        return x;
    }*/

    public SerDevZeroTaskRun(){
    }

    @Override
    public String getScheduledType() {
        return MainTask.SET_DEV_ZERO;
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
