package com.base.userinfo.service.impl;

import com.base.database.userinfo.mapper.UsercontrollerUserEbayMapper;
import com.base.database.userinfo.mapper.UsercontrollerUserMapper;
import com.base.database.userinfo.mapper.UsercontrollerUserRoleMapper;
import com.base.database.userinfo.model.*;
import com.base.domains.RoleVO;
import com.base.domains.SessionVO;
import com.base.domains.userinfo.AddSubUserVO;
import com.base.domains.userinfo.UsercontrollerEbayAccountExtend;
import com.base.domains.userinfo.UsercontrollerUserExtend;
import com.base.mybatis.page.Page;
import com.base.userinfo.mapper.SystemUserManagerServiceMapper;
import com.base.userinfo.mapper.UserInfoServiceMapper;
import com.base.userinfo.service.SystemUserManagerService;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.common.EncryptionUtil;
import com.base.utils.common.ObjectUtils;
import com.base.utils.exception.Asserts;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2014/9/19.
 * 系统账户管理
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SystemUserManagerServiceImpl implements SystemUserManagerService {
    @Autowired
    private SystemUserManagerServiceMapper systemUserManagerServiceMapper;
    @Autowired
    private UsercontrollerUserMapper userMapper;
    @Autowired
    private UserInfoServiceMapper userInfoServiceMapper;
    @Autowired
    private UsercontrollerUserRoleMapper userRoleMapper;
    @Autowired
    private UsercontrollerUserEbayMapper userEbayMapper;

    @Override
    /**查询系统用户以及列表*/
    public List<UsercontrollerUserExtend> queryAccountListByUserID(Map map, Page page){
        SessionVO sessionVO= SessionCacheSupport.getSessionVO();
        map.put("userID",sessionVO.getId());
        return systemUserManagerServiceMapper.queryAccountListByUserID(map,page);
    }

    @Override
    /**对用户账户进行停用启用等操作*/
    public void operationAccount(Map map){
        Integer uid=Integer.valueOf((String)map.get("userid"));
        SessionVO sessionVO=SessionCacheSupport.getSessionVO();
        Integer curruser = ((Long)sessionVO.getId()).intValue();

        UsercontrollerUser u=userMapper.selectByPrimaryKey(uid);

        Asserts.assertTrue(u.getUserId()!=curruser&&!u.getUserId().equals(curruser),"管理员帐号不能被停用或者不能停用当前登录的账户");
        Asserts.assertTrue((u.getUserParentId()==curruser||u.getUserParentId().equals(curruser)),"当前帐号不是管理员帐号不能进行修改操作");

        /*UsercontrollerUserExample userExample=new UsercontrollerUserExample();
        userExample.createCriteria().andUserIdEqualTo(curruser);*/

        UsercontrollerUser user=new UsercontrollerUser();
        user.setUserId(uid);
        user.setStatus("stop".equalsIgnoreCase((String)map.get("doaction"))?"0":"1");
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    /**查询当前用户定义了那些角色*/
    public List<RoleVO> queryAllCustomRole(Map map){
        if(!map.containsKey("userId")){
            SessionVO sessionVO=SessionCacheSupport.getSessionVO();
            map.put("userId",sessionVO.getId());
        }
        List<RoleVO> roleVOs = systemUserManagerServiceMapper.queryAllRoleByUserID(map);
        return roleVOs;
    }

    @Override
    /**获取用户有哪些角色*/
    public List<RoleVO> queryCurrUserRole(Map map){
        if(!map.containsKey("userId")){
            SessionVO sessionVO=SessionCacheSupport.getSessionVO();
            return sessionVO.getRoleVOList();
        }else {
            return systemUserManagerServiceMapper.queryUserRoleByUserID(map);
        }
    }

    @Override
    /**查询账户绑定了哪些ebay账户*/
    public List<UsercontrollerEbayAccountExtend> queryCurrAllEbay(Map map){
        if(!map.containsKey("userID") && !map.containsKey("AllEbay")){
            SessionVO sessionVO=SessionCacheSupport.getSessionVO();
            map.put("userID",sessionVO.getId());
        }
        List<UsercontrollerEbayAccountExtend> ebays = userInfoServiceMapper.queryEbayAccountForUser(map);
        for (UsercontrollerEbayAccountExtend ebayAccountExtend : ebays){
            ebayAccountExtend.setEbayToken("");
        }
        return ebays;
    }
    @Override
    /**查询账户绑定了哪些ebay账户*/
    public List<UsercontrollerEbayAccountExtend> queryACurrAllEbay(Map map){
        SessionVO sessionVO=SessionCacheSupport.getSessionVO();
        map.put("userID",sessionVO.getId());
        List<UsercontrollerEbayAccountExtend> ebays = userInfoServiceMapper.queryAllEbayAccountForUser(map);
        for (UsercontrollerEbayAccountExtend ebayAccountExtend : ebays){
            ebayAccountExtend.setEbayToken("");
        }
        return ebays;
    }

    @Override
    /**编辑页面综合查询用户信息*/
    public AddSubUserVO queryAllUserAccountInfo(Long userID){
        AddSubUserVO addSubUserVO=new AddSubUserVO();
        Map map=new HashMap();
        map.put("userId",userID);
        map.put("userID",userID);
        List<UsercontrollerEbayAccountExtend> es=queryCurrAllEbay(map);
        addSubUserVO.setEbays(es);

        addSubUserVO.setRoles(systemUserManagerServiceMapper.queryUserRoleByUserID(map));

        UsercontrollerUser user = userMapper.selectByPrimaryKey(userID.intValue());
        addSubUserVO.setAddress(user.getAddress());
        addSubUserVO.setEmail(user.getUserEmail());
        addSubUserVO.setName(user.getUserName());
        addSubUserVO.setLoginID(user.getUserLoginId());
        addSubUserVO.setPhone(user.getTelPhone());

        return addSubUserVO;
    }

    @Override
    /**添加子账户*/
    public void addSubAccount(AddSubUserVO addSubUserVO){
        /**判断相同用户名是否已经存在*/
        UsercontrollerUserExample userExample=new UsercontrollerUserExample();
        userExample.createCriteria().andUserLoginIdEqualTo(addSubUserVO.getLoginID());
        List<UsercontrollerUser> u1=userMapper.selectByExample(userExample);

        Asserts.assertTrue(ObjectUtils.isLogicalNull(u1),"相同用户已经存在!");

        UsercontrollerUser user=new UsercontrollerUser();
        user.setUserLoginId(addSubUserVO.getLoginID());
        user.setUserName(addSubUserVO.getName());
        user.setUserEmail(addSubUserVO.getEmail());
        user.setAddress(addSubUserVO.getAddress());
        user.setTelPhone(addSubUserVO.getPhone());

        SessionVO sessionVO=SessionCacheSupport.getSessionVO();
        user.setUserParentId(((Long)sessionVO.getId()).intValue());
        user.setUserPassword(EncryptionUtil.pwdEncrypt("123456", user.getUserLoginId()));//初始密码是123456
        user.setStatus("1");
        user.setDefaultDevAccount(1L);
        user.setUserOrgId(((Long)sessionVO.getOrgId()).intValue());
        userMapper.insertSelective(user);//插入user表
        insertRoleAndEbay(addSubUserVO,user);
    }
    @Override
    /**编辑子账户信息*/
    public void editSubAccount(AddSubUserVO addSubUserVO){
        Asserts.assertTrue(addSubUserVO.getUserID()!=null,"主键不能为空!");

        UsercontrollerUser user1= userMapper.selectByPrimaryKey(addSubUserVO.getUserID().intValue());
        SessionVO sessionVO=SessionCacheSupport.getSessionVO();

        Asserts.assertTrue(user1.getUserOrgId().longValue()==sessionVO.getOrgId(),"没有权限修改");
        Asserts.assertTrue(pdRole(1L,sessionVO.getRoleVOList()),"不是管理员角色！");

        UsercontrollerUser user=new UsercontrollerUser();
        user.setUserName(addSubUserVO.getName());
        user.setUserEmail(addSubUserVO.getEmail());
        user.setAddress(addSubUserVO.getAddress());
        user.setTelPhone(addSubUserVO.getPhone());
        user.setUserId(addSubUserVO.getUserID().intValue());
        int i = userMapper.updateByPrimaryKeySelective(user);
        insertRoleAndEbay(addSubUserVO,user);
    }

    @Override
    /**判断某个账户是否有某个角色roleID参见role表*/
    public boolean pdRole(Long roleID,List<RoleVO> roleVOList){
        for (RoleVO roleVO : roleVOList){
            if(roleID==roleVO.getRoleID()){
                return true;
            }
        }
        return false;
    }

    /**清除并添加用户的权限和ebay帐号信息*/
    private void insertRoleAndEbay(AddSubUserVO addSubUserVO,UsercontrollerUser user){
        if(user.getUserId()!=null){
            UsercontrollerUserRoleExample userRoleExample=new UsercontrollerUserRoleExample();
            userRoleExample.createCriteria().andUserIdEqualTo(user.getUserId());
            userRoleMapper.deleteByExample(userRoleExample);
            UsercontrollerUserEbayExample userEbayExample=new UsercontrollerUserEbayExample();
            userEbayExample.createCriteria().andUserIdEqualTo(user.getUserId().longValue());
            userEbayMapper.deleteByExample(userEbayExample);
        }
        /**插入角色表*/
        for (RoleVO roleVO : addSubUserVO.getRoles()){
            if(roleVO.getRoleID()==null){continue;}
            UsercontrollerUserRole userRole=new UsercontrollerUserRole();
            userRole.setRoleId(roleVO.getRoleID().intValue());
            userRole.setUserId(addSubUserVO.getUserID()==null?user.getUserId():addSubUserVO.getUserID().intValue());
            userRoleMapper.insertSelective(userRole);
        }

        /**插入user-ebay帐号表*/
        for (UsercontrollerEbayAccountExtend uea:addSubUserVO.getEbays()){
            if(uea.getId()==null){continue;}
            UsercontrollerUserEbay userEbay=new UsercontrollerUserEbay();
            userEbay.setEbayId(uea.getId());
            userEbay.setUserId(addSubUserVO.getUserID()==null?user.getUserId().longValue():addSubUserVO.getUserID());
            userEbayMapper.insertSelective(userEbay);
        }
    }

    @Override
    /**修改密码*/
    public void changePWD(String oldPWD,String newPWD){
        SessionVO sessionVO = SessionCacheSupport.getSessionVO();
        UsercontrollerUser user = userMapper.selectByPrimaryKey((int) sessionVO.getId());
        String EOld = EncryptionUtil.pwdEncrypt(oldPWD, user.getUserLoginId());
        Asserts.assertTrue(user.getUserPassword().equalsIgnoreCase(EOld),"原密码错误！");
        user.setUserPassword(EncryptionUtil.pwdEncrypt(newPWD, user.getUserLoginId()));
        userMapper.updateByPrimaryKeySelective(user);
    }

}
