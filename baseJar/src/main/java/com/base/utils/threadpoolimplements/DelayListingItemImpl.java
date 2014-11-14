package com.base.utils.threadpoolimplements;

import com.base.database.trading.model.TradingItem;
import com.base.database.trading.model.TradingItemWithBLOBs;
import com.base.utils.common.ObjectUtils;
import com.base.utils.threadpool.TaskMessageVO;
import com.base.utils.xmlutils.SamplePaseXml;
import com.sitemessage.service.SiteMessageStatic;
import com.trading.service.ITradingItem;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrtor on 2014/9/9.
 * 刊登动作后要执行的方法
 */
@Service
public class DelayListingItemImpl implements ThreadPoolBaseInterFace {
    static Logger logger = Logger.getLogger(DelayListingItemImpl.class);

    @Autowired
    private ITradingItem iTradingItem;

    @Override
    public <T> void doWork(String res,T... t) {
        if(StringUtils.isEmpty(res)){return;}
        TaskMessageVO taskMessageVO = (TaskMessageVO)t[0];
        TradingItemWithBLOBs tradingItem = (TradingItemWithBLOBs) taskMessageVO.getObjClass();
        String ack = null;
        String itemId=null;
        try {
            itemId = SamplePaseXml.getVFromXmlString(res, "ItemID");

            ack=SamplePaseXml.getVFromXmlString(res, "Ack");
            if ("Success".equalsIgnoreCase(ack) || "Warning".equalsIgnoreCase(ack)) {
                this.iTradingItem.saveListingSuccess(res,itemId);
            }
            else {return;}
        } catch (Exception e) {
            logger.error("解析xml出错,请稍后到ebay网站确认结果,itemid:"+itemId,e);
            return;
        }
        if(ObjectUtils.isLogicalNull(itemId)){return;}
        tradingItem.setItemId(itemId);
        tradingItem.setIsFlag("Success");
        taskMessageVO.setMessageContext(taskMessageVO.getMessageContext()+",itemID:"+itemId);
        try {
            iTradingItem.saveTradingItem(tradingItem);
        } catch (Exception e) {
            logger.error("写入itemid出错tradingItemid:"+tradingItem.getId()+"itemid:"+itemId,e);
        }
    }

    @Override
    public String getType() {
        return SiteMessageStatic.LISTING_ITEM_BEAN;
    }
}
