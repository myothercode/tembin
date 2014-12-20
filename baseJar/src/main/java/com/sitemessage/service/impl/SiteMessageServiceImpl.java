package com.sitemessage.service.impl;

import com.base.database.sitemessage.mapper.CustomPublicSitemessageMapper;
import com.base.database.sitemessage.mapper.PublicSitemessageMapper;
import com.base.database.sitemessage.model.CustomPublicSitemessage;
import com.base.database.sitemessage.model.PublicSitemessage;
import com.base.database.sitemessage.model.PublicSitemessageExample;
import com.base.database.sitemessage.model.SiteMessageCountVO;
import com.base.database.userinfo.model.SystemLog;
import com.base.domains.SessionVO;
import com.base.mybatis.page.Page;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.common.ObjectUtils;
import com.base.utils.common.SystemLogUtils;
import com.base.utils.threadpool.TaskMessageVO;
import com.sitemessage.service.SiteMessageService;
import org.apache.commons.codec.StringEncoder;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/9/4.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SiteMessageServiceImpl implements SiteMessageService {
    static Logger logger = Logger.getLogger(SiteMessageServiceImpl.class);

    @Autowired
    private PublicSitemessageMapper publicSitemessageMapper;
    @Autowired
    private CustomPublicSitemessageMapper customPublicSitemessageMapper;

    @Override
    /**根据信息的读取与否获取信息list*/
    public List<CustomPublicSitemessage> querySiteMessage(PublicSitemessage sitemessage, Page page) {
        SessionVO sessionVO = SessionCacheSupport.getSessionVO();
        Map map=new HashMap();
        map.put("messageTo",sessionVO.getId());
        if(!"all".equalsIgnoreCase(sitemessage.getMessageType())){
            map.put("messageType",sitemessage.getMessageType());
        }
        if(StringUtils.isNotEmpty(sitemessage.getReaded()) ){
            map.put("readed",sitemessage.getReaded());
        }
        List<CustomPublicSitemessage> customPublicSitemessages = customPublicSitemessageMapper.selectSiteMessageList(map, page);
        return customPublicSitemessages;
    }

    /**填写相应的消息类型*/

    @Override
    /**读取一条信息，并标记为已读*/
    public CustomPublicSitemessage fetchSiteMessage(PublicSitemessage publicSitemessage) {
        SessionVO sessionVO = SessionCacheSupport.getSessionVO();
        publicSitemessage.setMessageTo(sessionVO.getId());
        CustomPublicSitemessage customPublicSitemessage = customPublicSitemessageMapper.fetchSiteMessageById(publicSitemessage);
        if (customPublicSitemessage != null &&
                ("0".equals(customPublicSitemessage.getReaded()) || ObjectUtils.isLogicalNull(customPublicSitemessage.getReaded()))) {
            PublicSitemessage h = new PublicSitemessage();
            h.setId(publicSitemessage.getId());
            h.setReaded("1");//已读
            publicSitemessageMapper.updateByPrimaryKeySelective(h);
        }
        return customPublicSitemessage;
    }

    @Override
    /**批量标记为已读*/
    public void batchSetReaded(Map map){
        //Long[] ids= (Long[]) map.get("idArray");
        SessionVO sessionVO = SessionCacheSupport.getSessionVO();
        map.put("mToId",sessionVO.getId());
        customPublicSitemessageMapper.updateReadedMessage(map);
    }

    @Override
    /**获取未读消息的数量*/
    public List<SiteMessageCountVO> countSiteMessage(Map map){
       return customPublicSitemessageMapper.countSiteMessage(map);
    }

    @Override
    /**新增一条消息*/
    public void addSiteMessage(TaskMessageVO taskMessageVO){
        SystemLog systemLog = new SystemLog();
        systemLog.setOperuser(taskMessageVO.getMessageTo().toString());
        systemLog.setEventname(SystemLogUtils.NO_API_Message);
        systemLog.setEventdesc("消息类型:" + taskMessageVO.getMessageType() + ",消息内容:" + taskMessageVO.getMessageContext());

        if(!taskMessageVO.getSendOrNotSend()){//如果设置了不发送消息，那么任何消息都不发送，但是会记录系统日志
            try {
                SystemLogUtils.saveLog(systemLog);
            } catch (Exception e) {
                logger.error("记录日志错误!尝试重新记录"+taskMessageVO.getMessageTo()+";"+taskMessageVO.getMessageContext(),e);
                systemLog.setEventdesc(StringEscapeUtils.escapeHtml(systemLog.getEventdesc()));
                try {
                    SystemLogUtils.saveLog(systemLog);
                } catch (Exception e1) {
                    logger.error("尝试失败"+taskMessageVO.getMessageTo()+";"+taskMessageVO.getMessageContext(),e1);
                }

            }
            return;}

        if((!taskMessageVO.getWeithSendSuccessMessage() && taskMessageVO.getMessageType().endsWith("_SUCCESS"))){
            try {
                SystemLogUtils.saveLog(systemLog);
            } catch (Exception e) {
                //logger.error("记录日志错误!"+taskMessageVO.getMessageTo()+";"+taskMessageVO.getMessageContext(),e);
                systemLog.setEventdesc(StringEscapeUtils.escapeHtml(systemLog.getEventdesc()));
                try {
                    SystemLogUtils.saveLog(systemLog);
                } catch (Exception e1) {
                    logger.error("尝试记录失败"+taskMessageVO.getMessageTo()+";"+taskMessageVO.getMessageContext(),e1);
                }
            }
            return;
        }

        try {
            publicSitemessageMapper.insertSelective(taskMessageVO.toPublicSiteMessage());
        } catch (Exception e) {
            taskMessageVO.setMessageContext(StringEscapeUtils.escapeHtml(taskMessageVO.getMessageContext()));
            try {
                publicSitemessageMapper.insertSelective(taskMessageVO.toPublicSiteMessage());
            } catch (Exception e1) {
                logger.error("记录站点消息失败!"+taskMessageVO.getMessageContext(),e1);
            }
        }
    }

    @Override
    public List<PublicSitemessage> selectPublicSitemessageByMessage(String messageType,String orderAndSeller) {
        PublicSitemessageExample example=new PublicSitemessageExample();
        PublicSitemessageExample.Criteria cr=example.createCriteria();
        cr.andMessageTypeEqualTo(messageType);
        cr.andOrderandsellerEqualTo(orderAndSeller);
        List<PublicSitemessage> list=publicSitemessageMapper.selectByExample(example);
        return list;
    }
}
