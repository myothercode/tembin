package com.base.userinfo.service;

import com.base.domains.userinfo.AddSubUserVO;
import com.base.mybatis.page.Page;

import java.util.Map;

/**
 * Created by Administrator on 2014/9/24.
 */
public interface RoleManagerService {
    /**查询角色清单*/
    java.util.List<com.base.database.userinfo.model.UsercontrollerRole> queryRoleList(Map map, Page page);

    /**删除一个角色*/
    void deleteRoleById(Map map);

    /**增加或者编辑一个角色*/
    void addOrEditRole(AddSubUserVO addSubUserVO);

    /**查询一个角色的信息*/
    AddSubUserVO getRoleInfoById(Integer roleId);
}
