package com.sitemessage.service.impl;

import com.base.database.sitemessage.mapper.CustomPublicSitemessageMapper;
import com.base.database.sitemessage.mapper.PublicSitemessageMapper;
import com.base.database.sitemessage.model.CustomPublicSitemessage;
import com.base.database.sitemessage.model.PublicSitemessage;
import com.base.database.sitemessage.model.PublicSitemessageExample;
import com.base.database.sitemessage.model.SiteMessageCountVO;
import com.base.mybatis.page.Page;
import com.base.utils.common.ObjectUtils;
import com.sitemessage.service.SiteMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/9/4.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SiteMessageServiceImpl implements SiteMessageService {

    @Autowired
    private PublicSitemessageMapper publicSitemessageMapper;
    @Autowired
    private CustomPublicSitemessageMapper customPublicSitemessageMapper;

    @Override
    /**根据信息的读取与否获取信息list*/
    public List<CustomPublicSitemessage> querySiteMessage(PublicSitemessage sitemessage, Page page) {
        List<CustomPublicSitemessage> customPublicSitemessages = customPublicSitemessageMapper.selectSiteMessageList(sitemessage, page);
        return customPublicSitemessages;
    }

    @Override
    /**读取信息，并标记为已读*/
    public CustomPublicSitemessage fetchSiteMessage(PublicSitemessage publicSitemessage) {
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

    /**获取weid未读消息的数量*/
    public List<SiteMessageCountVO> countSiteMessage(Map map){
       return customPublicSitemessageMapper.countSiteMessage(map);
    }
}
