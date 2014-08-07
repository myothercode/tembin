package com.base.userinfo.service.impl;

import com.base.database.trading.mapper.UsercontrollerDevAccountMapper;
import com.base.database.trading.mapper.UsercontrollerEbayAccountMapper;
import com.base.database.trading.mapper.UsercontrollerEbayDevMapper;
import com.base.database.trading.model.UsercontrollerDevAccount;
import com.base.database.trading.model.UsercontrollerEbayAccount;
import com.base.database.trading.model.UsercontrollerEbayDev;
import com.base.database.trading.model.UsercontrollerEbayDevExample;
import com.base.domains.*;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.userinfo.mapper.UserInfoServiceMapper;
import com.base.utils.common.EncryptionUtil;
import com.base.utils.common.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/7/23.
 * 获取用户信息
 */
@Service
public class UserInfoServiceImpl implements com.base.userinfo.service.UserInfoService {
    @Autowired
    private UserInfoServiceMapper userInfoServiceMapper;//查询用户信息
    @Autowired
    private UsercontrollerDevAccountMapper usercontrollerDevAccountMapper;//查询开发帐号信息
    @Autowired
    private UsercontrollerEbayAccountMapper usercontrollerEbayAccountMapper;
    @Autowired
    private UsercontrollerEbayDevMapper UsercontrollerEbayDevMapper;

    @Override
    public SessionVO getUserInfo(LoginVO loginVO){
        String enPwd= EncryptionUtil.pwdEncrypt(loginVO.getPassword(),loginVO.getLoginId());
        loginVO.setEnpassword(enPwd);
        SessionVO sessionVO=userInfoServiceMapper.querySessionVOInfo(loginVO);
        if(ObjectUtils.isLogicalNull(sessionVO)){return null;}
        Map map = new HashMap();
        map.put("loginId",loginVO.getLoginId());
        List<RoleVO> roleVOs = userInfoServiceMapper.queryUserRole(map);//获取角色信息
        List<PermissionVO> permissions;
        if(!ObjectUtils.isLogicalNull(roleVOs)){
            sessionVO.setRoleVOList(roleVOs);
            Map map1=new HashMap();
            map1.put("idArray",roleVOs);
            permissions = userInfoServiceMapper.queryPermissionByRoleID(map1);//获取权限列表
            sessionVO.setPermissions(permissions);
        }
       return sessionVO;
    }

    @Override
    /**获取开发者帐号的信息*/
    public UsercontrollerDevAccountExtend getDevInfo( Long id ) throws Exception {
        UsercontrollerDevAccount x= usercontrollerDevAccountMapper.selectByPrimaryKey(id);
        return x.toExtend();
    }

    @Override
    /**保存绑定帐号过后的token*/
    public void saveToken(UsercontrollerEbayAccount ebayAccount, CommonParmVO commonParmVO) throws Exception {
        usercontrollerEbayAccountMapper.insertSelective(ebayAccount);
        UsercontrollerEbayDev ebayDev = new UsercontrollerEbayDev();
        ObjectUtils.toInitPojoForInsert(ebayDev);
        ebayDev.setDevAccountId(commonParmVO.getId());
        ebayDev.setEbayAccountId(ebayAccount.getId());

        UsercontrollerEbayDevMapper.insertSelective(ebayDev);
    }
}
