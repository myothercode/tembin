package com.base.userinfo.mapper;

import com.base.domains.LoginVO;
import com.base.domains.PermissionVO;
import com.base.domains.RoleVO;
import com.base.domains.SessionVO;

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
}
