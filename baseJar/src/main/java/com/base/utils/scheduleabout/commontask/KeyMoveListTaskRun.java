package com.base.utils.scheduleabout.commontask;

import com.base.database.customtrading.mapper.KeyMoveProgressQueryMapper;
import com.base.database.keymove.mapper.KeyMoveListMapper;
import com.base.database.keymove.model.KeyMoveList;
import com.base.database.keymove.model.KeyMoveListExample;
import com.base.database.trading.model.TradingDataDictionary;
import com.base.database.trading.model.TradingItemWithBLOBs;
import com.base.database.trading.model.TradingProgress;
import com.base.database.userinfo.model.SystemLog;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.mybatis.page.Page;
import com.base.sampleapixml.APINameStatic;
import com.base.userinfo.service.UserInfoService;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.cache.DataDictionarySupport;
import com.base.utils.cache.TempStoreDataSupport;
import com.base.utils.common.CommAutowiredClass;
import com.base.utils.common.DateUtils;
import com.base.utils.common.MyStringUtil;
import com.base.utils.common.SystemLogUtils;
import com.base.utils.scheduleabout.BaseScheduledClass;
import com.base.utils.scheduleabout.MainTask;
import com.base.utils.scheduleabout.Scheduledable;
import com.base.utils.threadpool.AddApiTask;
import com.base.utils.threadpool.TaskPool;
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

        /*String isRunging = TempStoreDataSupport.pullData("task_"+getScheduledType());
        if(StringUtils.isNotEmpty(isRunging)){return;}
        TempStoreDataSupport.pushData("task_" + getScheduledType(), "x");*/
        Boolean b= TaskPool.threadIsAliveByName("thread_" + getScheduledType());
        if(b){
            //logger.error(getScheduledType()+"===之前的任务还未完成继续等待下一个循环===");
            return;
        }
        //logger.error(getScheduledType()+"===任务开始===");
        Thread.currentThread().setName("thread_" + getScheduledType());

        KeyMoveProgressQueryMapper keyMoveProgressQueryMapper = (KeyMoveProgressQueryMapper) ApplicationContextUtil.getBean(KeyMoveProgressQueryMapper.class);
        KeyMoveListMapper keyMapper = (KeyMoveListMapper) ApplicationContextUtil.getBean(KeyMoveListMapper.class);
        IKeyMoveProgress iKeyMoveProgress = (IKeyMoveProgress) ApplicationContextUtil.getBean(IKeyMoveProgress.class);
        UserInfoService userInfoService = (UserInfoService) ApplicationContextUtil.getBean(UserInfoService.class);
        ITradingItem iTradingItem = (ITradingItem) ApplicationContextUtil.getBean(ITradingItem.class);
        /*KeyMoveListExample kmle = new KeyMoveListExample();*/
        List<String> lis = new ArrayList<String>();
        lis.add("0");//未执行
        lis.add("3");//执行一次，且失败，
        /*kmle.createCriteria().andTaskFlagIn(lis);
        List<KeyMoveList> likml = keyMapper.selectByExampleWithBLOBs(kmle);*/
        Map map = new HashMap();
        map.put("taskFlagList",lis);
        Page page = new Page();
        page.setPageSize(20);
        page.setCurrentPage(1);
        List<KeyMoveList> likml = keyMoveProgressQueryMapper.selectByPageKeyMoveList(map,page);
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
                        Document document= SamplePaseXml.formatStr2Doc(res);
                        Element rootElt = document.getRootElement();
                        Element element = rootElt.element("Item");
                        String categoryName = element.element("PrimaryCategory").elementText("CategoryName");
                        categoryName = categoryName.replace(":","->");
                        Item itemrs = SamplePaseXml.getItem(res);
                        itemrs.setUUID(kml.getUserId() + "");
                        Map ms = new HashMap();
                        ms.put("type","site");
                        ms.put("value",itemrs.getSite());
                        List<TradingDataDictionary> litdd = DataDictionarySupport.getTradingDataDictionaryByMap(ms);
                        if(litdd!=null&&litdd.size()>0){
                            itemrs.setSite(litdd.get(0).getId()+"");
                        }else{
                            logger.error("通过站点值，查询站点信息报错");
                            continue;
                        }
                        iTradingItem.saveListingItem(itemrs, kml,categoryName);
                        //保存成功更新任务表
                        kml.setTaskFlag("1");
                        keyMapper.updateByPrimaryKeySelective(kml);
                        liitem.add(itemrs);
                        TradingProgress tp = iKeyMoveProgress.selectById(kml.getProgressId());
                        tp.setEndDate(new Date());
                        iKeyMoveProgress.saveProgress(tp);
                    } else {//ＡＰＩ请求失败
                        if("3".equals(kml.getTaskFlag())){
                            kml.setTaskFlag("2");
                        }else{
                            kml.setTaskFlag("3");
                        }
                        keyMapper.updateByPrimaryKeySelective(kml);
                    }
                } catch (Exception e) {
                    if("3".equals(kml.getTaskFlag())){
                        kml.setTaskFlag("2");
                    }else{
                        kml.setTaskFlag("3");
                    }
                    keyMapper.updateByPrimaryKeySelective(kml);
                    logger.error("keyMoveListTaskRun:",e);
                }
            }else{
                kml.setTaskFlag("1");
                keyMapper.updateByPrimaryKeySelective(kml);
            }
        }
        TaskPool.threadRunTime.remove("thread_" + getScheduledType());
        Thread.currentThread().setName("thread_" + getScheduledType()+ MyStringUtil.getRandomStringAndNum(5));
        //logger.error(getScheduledType() + "===任务结束===");
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
        return 5;
    }

    @Override
    public void setMark(String x) {

    }

    @Override
    public String getMark() {
        return null;
    }
}
