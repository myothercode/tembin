package com.sitemessage.service;

import com.base.database.sitemessage.model.CustomPublicSitemessage;
import com.base.database.sitemessage.model.PublicSitemessage;
import com.base.database.sitemessage.model.SiteMessageCountVO;
import com.base.mybatis.page.Page;
import com.base.utils.threadpool.TaskMessageVO;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/9/3.
 */
public interface SiteMessageService {
    /**根据信息的读取与否获取信息list*/
    List<CustomPublicSitemessage> querySiteMessage(PublicSitemessage sitemessage, Page page);

    /**读取信息，并标记为已读*/
    CustomPublicSitemessage fetchSiteMessage(PublicSitemessage publicSitemessage);

    /**批量标记为已读*/
    void batchSetReaded(Map map);

    /**获取weid未读消息的数量*/
    List<SiteMessageCountVO> countSiteMessage(Map map);

    /**新增一条消息*/
    void addSiteMessage(TaskMessageVO taskMessageVO);

    List<PublicSitemessage> selectPublicSitemessageByMessage(String messageType,String orderAndSeller);
}
