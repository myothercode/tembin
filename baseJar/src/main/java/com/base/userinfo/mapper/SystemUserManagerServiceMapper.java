package com.base.userinfo.mapper;

import com.base.domains.userinfo.UsercontrollerUserExtend;
import com.base.mybatis.page.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2014/9/19.
 * 系统用户管理操作
 */
public interface SystemUserManagerServiceMapper {
    /**查询当前登录账户一级子账户的信息*/
    public List<UsercontrollerUserExtend> queryAccountListByUserID(Map map,Page page);
}
