package com.base.userinfo.mapper;

import com.base.database.trading.model.UsercontrollerDevAccount;
import com.base.database.trading.model.UsercontrollerEbayAccount;
import com.base.domains.LoginVO;
import com.base.domains.PermissionVO;
import com.base.domains.RoleVO;
import com.base.domains.SessionVO;
import com.base.domains.userinfo.UsercontrollerEbayAccountExtend;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/7/23.
 */
public interface UserInfoServiceMapper {

    /**根据用户id获取该用户的角色id*/
    public List<RoleVO> queryUserRole(Map map);

    /**查询权限信息，根据角色id list*/
    public List<PermissionVO> queryPermissionByRoleID(Map map);

    /**查询sessionVO*/
    public SessionVO querySessionVOInfo(LoginVO loginVO);

    /**查询指定用户帐号绑定的ebay帐号*/
    public List<UsercontrollerEbayAccountExtend> queryEbayAccountForUser(Long userID);

    /**根据ebay账户id查找token*/
    public UsercontrollerEbayAccount getTokenByEbayID(Long id);

    /**判断指定的ebay帐号是否绑定了当前的默认账户*/
    public int ebayIsBindDev(Map map);

    /**查询所有的开发帐号列表*/
    public List<UsercontrollerDevAccount> queryAllDevAccount();
}
