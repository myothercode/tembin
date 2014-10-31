package com.base.utils.threadpoolimplements;

import com.base.database.trading.model.TradingMessageGetmymessage;
import com.base.sampleapixml.GetMyMessageAPI;
import com.base.utils.threadpool.TaskMessageVO;
import com.base.utils.xmlutils.SamplePaseXml;
import com.sitemessage.service.SiteMessageStatic;
import com.trading.service.ITradingMessageGetmymessage;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/9/13.
 */
@Service
public class SynchronizeGetMessageImpl implements ThreadPoolBaseInterFace {
    static Logger logger = Logger.getLogger(SynchronizeGetMessageImpl.class);
    @Autowired
    private ITradingMessageGetmymessage iTradingMessageGetmymessage;
    @Override
    public <T> void doWork(String res, T... t) {
        if(StringUtils.isEmpty(res)){return;}
        TaskMessageVO taskMessageVO = (TaskMessageVO)t[0];
        Map map=(Map)taskMessageVO.getObjClass();
        Long accountId= (Long) map.get("accountId");
        Long ebay= (Long) map.get("ebay");
        String ack = null;
        try {
            ack = SamplePaseXml.getVFromXmlString(res, "Ack");
            if ("Success".equalsIgnoreCase(ack)) {
                List<Element> messages = GetMyMessageAPI.getMessages(res);
                for(Element message:messages){
                    TradingMessageGetmymessage ms= GetMyMessageAPI.addDatabase(message, accountId, ebay);//保存到数据库
                    if("true".equals(ms.getRead())){
                        List<TradingMessageGetmymessage> getmymessages=iTradingMessageGetmymessage.selectMessageGetmymessageByMessageId(ms.getMessageid());
                        if(getmymessages.size()>0){
                            ms.setId(getmymessages.get(0).getId());
                        }
                    }
                    ms.setCreateUser(taskMessageVO.getMessageTo());
                    iTradingMessageGetmymessage.saveMessageGetmymessage(ms);
                }
            }else {return;}
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("解析xml出错,请稍后到ebay网站确认结果");
            return;
        }

    }

    @Override
    public String getType() {
        return SiteMessageStatic.SYNCHRONIZE_GET_MESSAGE_BEAN;
    }
}
