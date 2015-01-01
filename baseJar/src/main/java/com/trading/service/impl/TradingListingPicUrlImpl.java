package com.trading.service.impl;

import com.base.database.customtrading.mapper.ItemAddressMapper;
import com.base.database.trading.mapper.TradingItemAddressMapper;
import com.base.database.trading.mapper.TradingListingpicUrlMapper;
import com.base.database.trading.model.*;
import com.base.domains.querypojos.ItemAddressQuery;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.mybatis.page.Page;
import com.base.sampleapixml.APINameStatic;
import com.base.utils.cache.DataDictionarySupport;
import com.base.utils.common.DateUtils;
import com.base.utils.common.ObjectUtils;
import com.base.utils.exception.Asserts;
import com.base.utils.threadpool.AddApiTask;
import com.base.utils.xmlutils.SamplePaseXml;
import com.trading.service.IUsercontrollerEbayAccount;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 物品所在地
 * Created by cz on 2014/7/23.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TradingListingPicUrlImpl implements com.trading.service.ITradingListingPicUrl {
    @Autowired
    private TradingListingpicUrlMapper tradingListingpicUrlMapper;
    @Autowired
    private IUsercontrollerEbayAccount iUsercontrollerEbayAccount;
    @Value("${EBAY.API.URL}")
    private String apiUrl;
    @Override
    public void saveListingPicUrl(TradingListingpicUrl tradingListingpicUrl) throws Exception {
        if(tradingListingpicUrl.getId()==null){
            this.tradingListingpicUrlMapper.insertSelective(tradingListingpicUrl);
        }else{
            this.tradingListingpicUrlMapper.updateByPrimaryKeySelective(tradingListingpicUrl);
        }
    }

    @Override
    public List<TradingListingpicUrl> selectByMackId(String mackId){
        TradingListingpicUrlExample tlue = new TradingListingpicUrlExample();
        tlue.createCriteria().andMackIdEqualTo(mackId);
        return this.tradingListingpicUrlMapper.selectByExample(tlue);
    }


    @Override
    public TradingListingpicUrl uploadPic(TradingItem tradingItem,String url,String picName,TradingListingpicUrl tlu) throws Exception {
        UsercontrollerEbayAccount ua = this.iUsercontrollerEbayAccount.selectById(Long.parseLong(tradingItem.getEbayAccount()));
        String xml= SamplePaseXml.uploadEbayImage(ua, url, picName);//获取xml
        AddApiTask addApiTask = new AddApiTask();
        UsercontrollerDevAccountExtend d = new UsercontrollerDevAccountExtend();
        d.setApiSiteid(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(tradingItem.getSite())).getName1());
        d.setApiCallName(APINameStatic.UploadSiteHostedPictures);
        Map<String, String> resMap = addApiTask.exec(d,xml,apiUrl);
        String r1 = resMap.get("stat");
        String res = resMap.get("message");
        String ack = SamplePaseXml.getVFromXmlString(res, "Ack");
        if ("Success".equalsIgnoreCase(ack) || "Warning".equalsIgnoreCase(ack)) {//成功
            Document document= SamplePaseXml.formatStr2Doc(res);
            Element rootElt = document.getRootElement();
            Element picelt = rootElt.element("SiteHostedPictureDetails");
            String fullUrl = picelt.elementText("FullURL");
            String enddate = picelt.elementText("UseByDate");
            tlu.setEbayurl(fullUrl);
            tlu.setCheckFlag("1");
            tlu.setEndDate(DateUtils.returnDate(enddate));
        }else{//失败
            tlu.setCheckFlag("2");
        }
        this.tradingListingpicUrlMapper.updateByPrimaryKeySelective(tlu);
        return tlu;
    }

}
