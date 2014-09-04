package com.sitemessage.service;

import com.base.database.sitemessage.model.CustomPublicSitemessage;
import com.base.database.sitemessage.model.PublicSitemessage;
import com.base.mybatis.page.Page;

import java.util.List;

/**
 * Created by Administrtor on 2014/9/3.
 */
public interface SiteMessageService {
    /**根据信息的读取与否获取信息list*/
    List<CustomPublicSitemessage> querySiteMessage(PublicSitemessage sitemessage, Page page);

    /**读取信息，并标记为已读*/
    CustomPublicSitemessage fetchSiteMessage(PublicSitemessage publicSitemessage);
}
