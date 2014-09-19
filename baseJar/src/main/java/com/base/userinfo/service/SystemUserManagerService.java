package com.base.userinfo.service;

import com.base.domains.userinfo.UsercontrollerUserExtend;
import com.base.mybatis.page.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2014/9/19.
 */
public interface SystemUserManagerService {
    /**查询系统用户以及列表*/
    List<UsercontrollerUserExtend> queryAccountListByUserID(Map map,Page page);
}
