package com.base.database.sitemessage.mapper;

import com.base.database.sitemessage.model.CustomPublicSitemessage;
import com.base.database.sitemessage.model.PublicSitemessage;
import com.base.database.sitemessage.model.SiteMessageCountVO;
import com.base.mybatis.page.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/9/4.
 */
public interface CustomPublicSitemessageMapper {
    List<CustomPublicSitemessage> selectSiteMessageList(Map map,Page page);
    CustomPublicSitemessage fetchSiteMessageById(PublicSitemessage publicSitemessage);
    List<SiteMessageCountVO> countSiteMessage(Map map);
    void updateReadedMessage(Map map);
}
