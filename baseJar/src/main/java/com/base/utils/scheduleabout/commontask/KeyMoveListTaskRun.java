package com.base.utils.scheduleabout.commontask;

import com.base.database.keymove.mapper.KeyMoveListMapper;
import com.base.database.keymove.model.KeyMoveList;
import com.base.database.keymove.model.KeyMoveListExample;
import com.base.database.trading.mapper.TradingTimerListingMapper;
import com.base.database.trading.model.TradingItem;
import com.base.database.trading.model.TradingTimerListingExample;
import com.base.database.trading.model.TradingTimerListingWithBLOBs;
import com.base.database.trading.model.UsercontrollerEbayAccount;
import com.base.database.userinfo.mapper.UsercontrollerUserMapper;
import com.base.database.userinfo.model.UsercontrollerUser;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.sampleapixml.APINameStatic;
import com.base.userinfo.service.UserInfoService;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.cache.DataDictionarySupport;
import com.base.utils.common.CommAutowiredClass;
import com.base.utils.common.ObjectUtils;
import com.base.utils.scheduleabout.BaseScheduledClass;
import com.base.utils.scheduleabout.MainTask;
import com.base.utils.scheduleabout.Scheduledable;
import com.base.utils.threadpool.AddApiTask;
import com.base.utils.xmlutils.SamplePaseXml;
import com.base.xmlpojo.trading.addproduct.Item;
import com.trading.service.ITradingItem;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/8/29.
 * 定时刊登任务
 */
public class KeyMoveListTaskRun extends BaseScheduledClass implements Scheduledable {
    static Logger logger = Logger.getLogger(KeyMoveListTaskRun.class);
    @Override
    public void run() {
        KeyMoveListMapper keyMapper = (KeyMoveListMapper) ApplicationContextUtil.getBean(KeyMoveListMapper.class);
        UserInfoService userInfoService = (UserInfoService) ApplicationContextUtil.getBean(UserInfoService.class);
        ITradingItem iTradingItem = (ITradingItem) ApplicationContextUtil.getBean(ITradingItem.class);
        KeyMoveListExample kmle = new KeyMoveListExample();
        kmle.createCriteria().andTaskFlagEqualTo("0");
        List<KeyMoveList> likml = keyMapper.selectByExampleWithBLOBs(kmle);
        List<Item> liitem = new ArrayList<Item>();
        for(KeyMoveList kml:likml){
            String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                    "<GetItemRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">\n" +
                    "<RequesterCredentials>\n" +
                    "<eBayAuthToken>"+kml.getEbaytoken()+"</eBayAuthToken>\n" +
                    "</RequesterCredentials>\n" +
                    "<ItemID>" + kml.getItemId() + "</ItemID>\n" +
                    "<DetailLevel>ReturnAll</DetailLevel>\n" +
                    "</GetItemRequest>";
            try {
                UsercontrollerDevAccountExtend d = userInfoService.getDevInfo(kml.getUserId());
                d.setApiSiteid(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(kml.getSiteId())).getName1());
                d.setApiCallName(APINameStatic.GetItem);
                AddApiTask addApiTask = new AddApiTask();
                Map<String, String> resMap = addApiTask.exec(d, xml, "https://api.sandbox.ebay.com/ws/api.dll");
                String res = resMap.get("message");
                String ack = SamplePaseXml.getVFromXmlString(res, "Ack");
                if("Success".equals(ack)){//ＡＰＩ成功请求，保存数据
                    Item itemrs = SamplePaseXml.getItem(res);
                    itemrs.setUUID(kml.getUserId()+"");
                    itemrs.setSite(kml.getSiteId());
                    iTradingItem.saveListingItem(itemrs,kml);
                    //保存成功更新任务表
                    kml.setTaskFlag("1");
                    keyMapper.updateByPrimaryKeySelective(kml);
                    liitem.add(itemrs);
                }else{//ＡＰＩ请求失败

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**只从集合记录取多少条*/
    private List<TradingTimerListingWithBLOBs> filterLimitList(List<TradingTimerListingWithBLOBs> tlist){

        List<TradingTimerListingWithBLOBs> x=new ArrayList<TradingTimerListingWithBLOBs>();
        for (int i = 0;i<20;i++){
            x.add(tlist.get(i));
        }
        return x;
    }

    public KeyMoveListTaskRun(){
    }

    @Override
    public String getScheduledType() {
        return MainTask.KEY_MOVE_LIST_TASK;
    }
}
