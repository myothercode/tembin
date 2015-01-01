package com.base.utils.threadpoolimplements;

import com.base.database.trading.mapper.TradingListingDataMapper;
import com.base.database.trading.mapper.TradingListingpicUrlMapper;
import com.base.database.trading.model.TradingListingData;
import com.base.database.trading.model.TradingListingDataExample;
import com.base.database.trading.model.TradingListingpicUrl;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.sampleapixml.APINameStatic;
import com.base.utils.common.DateUtils;
import com.base.utils.threadpool.AddApiTask;
import com.base.utils.threadpool.TaskMessageVO;
import com.base.utils.xmlutils.SamplePaseXml;
import com.sitemessage.service.SiteMessageStatic;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/9/13.
 * 图片上传到ebay
 */
@Service
public class SynchronizeUserListingPicUrlImpl implements ThreadPoolBaseInterFace {
    static Logger logger = Logger.getLogger(SynchronizeUserListingPicUrlImpl.class);
    @Autowired
    public TradingListingpicUrlMapper tldm;
    @Value("${EBAY.API.URL}")
    private String apiUrl;
    @Override
    public <T> void doWork(String ebpRes, T... t) {
        if(StringUtils.isEmpty(ebpRes)){return;}
        TaskMessageVO taskMessageVO = (TaskMessageVO)t[0];
        TradingListingpicUrl tlu = (TradingListingpicUrl) taskMessageVO.getObjClass();
        String ebpAck = null;
        try {
            ebpAck = SamplePaseXml.getVFromXmlString(ebpRes, "Ack");
            if ("Success".equalsIgnoreCase(ebpAck) || "Warning".equalsIgnoreCase(ebpAck)) {
                Document document= SamplePaseXml.formatStr2Doc(ebpRes);
                Element rootElt = document.getRootElement();
                Element picelt = rootElt.element("SiteHostedPictureDetails");
                String fullUrl = picelt.elementText("FullURL");
                String enddate = picelt.elementText("UseByDate");
                tlu.setEbayurl(fullUrl);
                tlu.setCheckFlag("1");
                tlu.setEndDate(DateUtils.returnDate(enddate));
            }else {
                tlu.setCheckFlag("2");
            }
            this.tldm.updateByPrimaryKeySelective(tlu);
        } catch (Exception e) {
            logger.error("解析xml出错,请稍后到ebay网站确认结果13"+ebpRes,e);
            return;
        }
    }


    @Override
    public String getType() {
        return SiteMessageStatic.LISTING_PIC_URL_BEAN;
    }
}
