package com.base.userinfo.service.impl;

import com.base.database.userinfo.mapper.UsercontrollerRoleMapper;
import com.base.database.userinfo.mapper.UsercontrollerRolePermissionMapper;
import com.base.database.userinfo.mapper.UsercontrollerUserRoleMapper;
import com.base.database.userinfo.model.UsercontrollerRole;
import com.base.database.userinfo.model.UsercontrollerRolePermission;
import com.base.database.userinfo.model.UsercontrollerRolePermissionExample;
import com.base.database.userinfo.model.UsercontrollerUserRoleExample;
import com.base.domains.SessionVO;
import com.base.domains.userinfo.AddSubUserVO;
import com.base.mybatis.page.Page;
import com.base.userinfo.mapper.SystemUserManagerServiceMapper;
import com.base.userinfo.service.RoleManagerService;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.exception.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 角色管理service
 * Created by Administrator on 2014/9/24.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RoleManagerServiceImpl implements RoleManagerService {
    @Autowired
    private SystemUserManagerServiceMapper systemUserManagerServiceMapper;
    @Autowired
    private UsercontrollerRoleMapper usercontrollerRoleMapper;
    @Autowired
    private UsercontrollerUserRoleMapper userRoleMapper;
    @Autowired
    private UsercontrollerRolePermissionMapper rolePermissionMapper;

    @Override
    /**查询角色清单*/
    public List<UsercontrollerRole> queryRoleList(Map map, Page page){
        List<UsercontrollerRole> usercontrollerRoleList = systemUserManagerServiceMapper.queryRoleList(map, page);
        return usercontrollerRoleList;
    }

    @Override
    /**删除一个角色*/
    public void deleteRoleById(Map map){
        Integer rid=((Long)map.get("roleId")).intValue();
        UsercontrollerRole usercontrollerRole = usercontrollerRoleMapper.selectByPrimaryKey(rid);
        SessionVO sessionVO= SessionCacheSupport.getSessionVO();
        Asserts.assertTrue(usercontrollerRole.getCreateUser()==sessionVO.getId(),"没有权限删除！");
        usercontrollerRoleMapper.deleteByPrimaryKey(rid);//删除主表
        UsercontrollerRolePermissionExample urp=new UsercontrollerRolePermissionExample();
        urp.createCriteria().andRoleIdEqualTo(rid);
        rolePermissionMapper.deleteByExample(urp);//删除角色-权限表
        UsercontrollerUserRoleExample userRoleExample=new UsercontrollerUserRoleExample();
        userRoleExample.createCriteria().andRoleIdEqualTo(rid);
        userRoleMapper.deleteByExample(userRoleExample);//删除user-role表
    }

    @Override
    /**增加或者编辑一个角色*/
    public void addOrEditRole(AddSubUserVO addSubUserVO){
        UsercontrollerRole role=addSubUserVO.getRole();
        SessionVO sessionVO=SessionCacheSupport.getSessionVO();
        role.setCreateUser(sessionVO.getId());
        if(role.getRoleId()==null){
            usercontrollerRoleMapper.insertSelective(role);
            List<UsercontrollerRolePermission> rolePermissions=addSubUserVO.getRolePermissions();
            for (UsercontrollerRolePermission rolePermission:rolePermissions){
                Asserts.assertTrue(role.getRoleId()!=null,"角色主键缺失");
                rolePermission.setRoleId(role.getRoleId());
                rolePermissionMapper.insertSelective(rolePermission);
            }
        }else {

            UsercontrollerRole role1=usercontrollerRoleMapper.selectByPrimaryKey(role.getRoleId());
            Asserts.assertTrue(role1.getCreateUser()==role.getCreateUser(),"没有权限修改!");

            usercontrollerRoleMapper.updateByPrimaryKey(role);
            UsercontrollerRolePermissionExample rolePermissionExample=new UsercontrollerRolePermissionExample();
            rolePermissionExample.createCriteria().andRoleIdEqualTo(role.getRoleId());
            rolePermissionMapper.deleteByExample(rolePermissionExample);
            List<UsercontrollerRolePermission> rolePermissions=addSubUserVO.getRolePermissions();
            for (UsercontrollerRolePermission rolePermission:rolePermissions){
                Asserts.assertTrue(role.getRoleId()!=null,"角色主键缺失");
                rolePermission.setRoleId(role.getRoleId());
                rolePermissionMapper.insertSelective(rolePermission);
            }
        }

    }

    @Override
    /**查询一个角色的信息*/
    public AddSubUserVO getRoleInfoById(Integer roleId){
        AddSubUserVO addSubUserVO = new AddSubUserVO();
        addSubUserVO.setRole(usercontrollerRoleMapper.selectByPrimaryKey(roleId));

        UsercontrollerRolePermissionExample rolePermissionExample=new UsercontrollerRolePermissionExample();
        rolePermissionExample.createCriteria().andRoleIdEqualTo(roleId);
        List<UsercontrollerRolePermission> rolePermissions=rolePermissionMapper.selectByExample(rolePermissionExample);
        addSubUserVO.setRolePermissions(rolePermissions);
        return addSubUserVO;
    }

}
