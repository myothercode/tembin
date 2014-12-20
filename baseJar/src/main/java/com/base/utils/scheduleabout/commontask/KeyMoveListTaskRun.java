package com.base.utils.scheduleabout.commontask;

import com.base.database.keymove.mapper.KeyMoveListMapper;
import com.base.database.keymove.model.KeyMoveList;
import com.base.database.keymove.model.KeyMoveListExample;
import com.base.database.trading.model.TradingItemWithBLOBs;
import com.base.database.trading.model.TradingProgress;
import com.base.database.userinfo.model.SystemLog;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.sampleapixml.APINameStatic;
import com.base.userinfo.service.UserInfoService;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.cache.DataDictionarySupport;
import com.base.utils.cache.TempStoreDataSupport;
import com.base.utils.common.CommAutowiredClass;
import com.base.utils.common.DateUtils;
import com.base.utils.common.SystemLogUtils;
import com.base.utils.scheduleabout.BaseScheduledClass;
import com.base.utils.scheduleabout.MainTask;
import com.base.utils.scheduleabout.Scheduledable;
import com.base.utils.threadpool.AddApiTask;
import com.base.utils.xmlutils.SamplePaseXml;
import com.base.xmlpojo.trading.addproduct.Item;
import com.keymove.service.IKeyMoveProgress;
import com.trading.service.ITradingItem;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.*;

/**
 * Created by Administrtor on 2014/8/29.
 * 一键搬家任务
 */
public class KeyMoveListTaskRun extends BaseScheduledClass implements Scheduledable {
    static Logger logger = Logger.getLogger(KeyMoveListTaskRun.class);
    @Override
    public void run() {

        String isRunging = TempStoreDataSupport.pullData("task_"+getScheduledType());
        if(StringUtils.isNotEmpty(isRunging)){return;}
        TempStoreDataSupport.pushData("task_" + getScheduledType(), "x");
        KeyMoveListMapper keyMapper = (KeyMoveListMapper) ApplicationContextUtil.getBean(KeyMoveListMapper.class);
        IKeyMoveProgress iKeyMoveProgress = (IKeyMoveProgress) ApplicationContextUtil.getBean(IKeyMoveProgress.class);
        UserInfoService userInfoService = (UserInfoService) ApplicationContextUtil.getBean(UserInfoService.class);
        ITradingItem iTradingItem = (ITradingItem) ApplicationContextUtil.getBean(ITradingItem.class);
        KeyMoveListExample kmle = new KeyMoveListExample();
        kmle.createCriteria().andTaskFlagEqualTo("0");
        List<KeyMoveList> likml = keyMapper.selectByExampleWithBLOBs(kmle);
        if(likml!=null&&likml.size()>0&&likml.size()>=20) {
            likml = filterLimitList(likml);//限制每次的执行条数
        }
        String str = DateUtils.formatDateTime(new Date());
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
            TradingItemWithBLOBs tradingItem = iTradingItem.selectByItemId(kml.getItemId());
            if(tradingItem==null) {
                try {
                    UsercontrollerDevAccountExtend d = userInfoService.getDevByOrder(new HashMap());
                    d.setApiSiteid(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(kml.getSiteId())).getName1());
                    d.setApiCallName(APINameStatic.GetItem);
                    AddApiTask addApiTask = new AddApiTask();
                    CommAutowiredClass commPars = (CommAutowiredClass) ApplicationContextUtil.getBean(CommAutowiredClass.class);//获取注入的参数
                    Map<String, String> resMap = addApiTask.exec2(d, xml, commPars.apiUrl);
                    /*Map<String, String> resMap = addApiTask.exec2(d, xml, "https://api.ebay.com/ws/api.dll");*/
                    String res = resMap.get("message");
                    String ack = SamplePaseXml.getVFromXmlString(res, "Ack");
                    if ("Success".equals(ack)) {//ＡＰＩ成功请求，保存数据
                        //获取分类名称
                        Item item = new Item();
                        Document document= DocumentHelper.parseText(res);
                        Element rootElt = document.getRootElement();
                        Element element = rootElt.element("Item");
                        String categoryName = element.element("PrimaryCategory").elementText("CategoryName");
                        categoryName = categoryName.replace(":","->");
                        Item itemrs = SamplePaseXml.getItem(res);
                        itemrs.setUUID(kml.getUserId() + "");
                        itemrs.setSite(kml.getSiteId());
                        iTradingItem.saveListingItem(itemrs, kml,categoryName);
                        //保存成功更新任务表
                        kml.setTaskFlag("1");
                        keyMapper.updateByPrimaryKeySelective(kml);
                        liitem.add(itemrs);
                        TradingProgress tp = iKeyMoveProgress.selectById(kml.getProgressId());
                        tp.setEndDate(new Date());
                        iKeyMoveProgress.saveProgress(tp);
                    } else {//ＡＰＩ请求失败
                        kml.setTaskFlag("2");
                        keyMapper.updateByPrimaryKeySelective(kml);
                    }
                } catch (Exception e) {
                    kml.setTaskFlag("2");
                    keyMapper.updateByPrimaryKeySelective(kml);
                    logger.error("keyMoveListTaskRun:",e);
                }
            }else{
                kml.setTaskFlag("1");
                keyMapper.updateByPrimaryKeySelective(kml);
            }
        }
        TempStoreDataSupport.removeData("task_"+getScheduledType());
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

    public KeyMoveListTaskRun(){
    }

    @Override
    public String getScheduledType() {
        return MainTask.KEY_MOVE_LIST_TASK;
    }

    @Override
    public Integer crTimeMinu() {
        return null;
    }
}
