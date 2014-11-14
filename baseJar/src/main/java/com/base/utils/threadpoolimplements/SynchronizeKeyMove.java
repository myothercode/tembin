package com.base.utils.threadpoolimplements;

import com.base.database.keymove.mapper.KeyMoveListMapper;
import com.base.database.keymove.model.KeyMoveList;
import com.base.database.keymove.model.KeyMoveListExample;
import com.base.database.trading.mapper.TradingListingpicUrlMapper;
import com.base.database.trading.mapper.UsercontrollerEbayAccountMapper;
import com.base.database.trading.model.TradingListingpicUrl;
import com.base.database.trading.model.UsercontrollerEbayAccount;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.sampleapixml.APINameStatic;
import com.base.utils.common.DateUtils;
import com.base.utils.threadpool.AddApiTask;
import com.base.utils.threadpool.TaskMessageVO;
import com.base.utils.xmlutils.SamplePaseXml;
import com.base.xmlpojo.trading.addproduct.Item;
import com.sitemessage.service.SiteMessageStatic;
import com.trading.service.IUsercontrollerEbayAccount;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrtor on 2014/9/13.
 * 一键搬家后台执行
 */
@Service
public class SynchronizeKeyMove implements ThreadPoolBaseInterFace {
    static Logger logger = Logger.getLogger(SynchronizeKeyMove.class);
    @Autowired
    public TradingListingpicUrlMapper tldm;
    @Value("${EBAY.API.URL}")
    private String apiUrl;
    @Autowired
    public UsercontrollerEbayAccountMapper usercontrollerEbayAccountMapper;
    @Autowired
    public KeyMoveListMapper keyMoveListMapper;

    @Override
    public <T> void doWork(String ebpRes, T... t) {

        if(StringUtils.isEmpty(ebpRes)){return;}
        TaskMessageVO taskMessageVO = (TaskMessageVO)t[0];
        String[] xs= (String[]) taskMessageVO.getObjClass();

        String ebayAccount = String.valueOf(xs[0]);
        String token = String.valueOf(xs[1]);
        String siteebayid = String.valueOf(xs[2]);
        String userid = String.valueOf(xs[3]);
        String siteids = String.valueOf(xs[4]);
        String ebpAck = null;
        try {
            SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date beginDate = new Date();
            Calendar date = Calendar.getInstance();
            date.setTime(beginDate);
            date.set(Calendar.DATE, date.get(Calendar.DATE) - 119);
            Date endDate = dft.parse(dft.format(date.getTime()));
            String  startTo = DateUtils.DateToString(new Date());
            String startFrom = DateUtils.DateToString(endDate);

            AddApiTask addApiTask = new AddApiTask();
            UsercontrollerDevAccountExtend d = new UsercontrollerDevAccountExtend();
            d.setApiCallName(APINameStatic.ListingItemList);
            d.setApiSiteid(siteebayid);
            List<Item> li = new ArrayList();
            ebpAck = SamplePaseXml.getVFromXmlString(ebpRes, "Ack");
            if ("Success".equalsIgnoreCase(ebpAck) || "Warning".equalsIgnoreCase(ebpAck)) {
                Element el = SamplePaseXml.getApiElement(ebpRes, "PaginationResult");
                String totalNumber = el.elementText("TotalNumberOfEntries");
                String pageSize = el.elementText("TotalNumberOfPages");
                for(int i=1;i<=Integer.parseInt(pageSize);i++){
                    String xml = this.getMoveCosXml(token,i+"",startFrom,startTo,ebayAccount);
                    Map resMap = addApiTask.exec(d, xml, apiUrl);
                    String res = (String) resMap.get("message");
                    String acks = SamplePaseXml.getVFromXmlString(res, "Ack");
                    if("Success".equals(acks)) {//ＡＰＩ成功请求，保存数据
                        li.addAll(SamplePaseXml.getItemElememt(res));
                    }else{//ＡＰＩ请求失败
                        continue;
                    }
                }
                UsercontrollerEbayAccount uea = this.usercontrollerEbayAccountMapper.selectByPrimaryKey(Long.parseLong(userid));
                for (Item item : li) {
                    KeyMoveListExample kmle = new KeyMoveListExample();
                    kmle.createCriteria().andItemIdEqualTo(item.getItemID());
                    List<KeyMoveList> likml = this.keyMoveListMapper.selectByExample(kmle);
                    if(likml==null||likml.size()==0){
                        KeyMoveList kml = new KeyMoveList();
                        kml.setCreateTime(new Date());
                        kml.setItemId(item.getItemID());
                        kml.setUserId(uea.getUserId());
                        kml.setEbaytoken(uea.getEbayToken());
                        kml.setTaskFlag("0");
                        kml.setSiteId(siteids);
                        kml.setPaypalId(userid);
                        this.keyMoveListMapper.insertSelective(kml);
                    }
                }
            }else {

            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("解析xml出错,请稍后到ebay网站确认结果");
            return;
        }
    }

    public String getMoveCosXml(String token,String page,String satartfrom,String startto,String ebayneam){
        String colStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<GetSellerListRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">\n" +
                "<RequesterCredentials>\n" +
                "<eBayAuthToken>"+token+"</eBayAuthToken>\n" +
                "</RequesterCredentials>\n" +
                "<Pagination ComplexType=\"PaginationType\">\n" +
                "\t<EntriesPerPage>100</EntriesPerPage>\n" +
                "\t<PageNumber>"+page+"</PageNumber>\n" +
                "</Pagination>\n" +
                "<StartTimeFrom>"+satartfrom+"</StartTimeFrom>\n" +
                "<StartTimeTo>"+startto+"</StartTimeTo>\n" +
                "<UserID>"+ebayneam+"</UserID>\n" +
                "<GranularityLevel>Coarse</GranularityLevel>\n" +
                "<DetailLevel>ItemReturnDescription</DetailLevel>\n" +
                "</GetSellerListRequest>";
        return colStr;
    }

    @Override
    public String getType() {
        return SiteMessageStatic.LISTING_KEY_MOVE_BEAN;
    }
}
