package com.base.userinfo.mapper;

import com.base.database.userinfo.model.UsercontrollerRole;
import com.base.domains.RoleVO;
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

    /**根据用户id查询用户有那些角色*/
    public List<RoleVO> queryUserRoleByUserID(Map map);

    /**查询用户定义的角色和系统内置角色*/
    public List<RoleVO> queryAllRoleByUserID(Map map);

    /**查询角色列表*/
    public List<UsercontrollerRole> queryRoleList(Map map,Page page);
}
