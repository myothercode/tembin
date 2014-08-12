package com.base.userinfo.service.impl;

import com.base.database.trading.mapper.UsercontrollerDevAccountMapper;
import com.base.database.trading.mapper.UsercontrollerEbayAccountMapper;
import com.base.database.trading.mapper.UsercontrollerEbayDevMapper;
import com.base.database.trading.model.*;
import com.base.database.userinfo.mapper.UsercontrollerUserMapper;
import com.base.database.userinfo.model.UsercontrollerUser;
import com.base.domains.*;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.domains.userinfo.UsercontrollerEbayAccountExtend;
import com.base.userinfo.mapper.UserInfoServiceMapper;
import com.base.utils.cache.SessionCacheSupport;
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
    @Autowired
    private UsercontrollerUserMapper userMapper;

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
        if(id==null || id==0L){//如果为空，那么就去取账户设置的默认dev
            SessionVO sessionVO = SessionCacheSupport.getSessionVO();
            UsercontrollerUser user=userMapper.selectByPrimaryKey(((Long)sessionVO.getId()).intValue());
            id=user.getDefaultDevAccount();
        }
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

    @Override
    /**查询当前系统账户绑定了哪些ebay账户*/
    public List<UsercontrollerEbayAccountExtend> getEbayAccountForCurrUser(){
        SessionVO sessionVO = SessionCacheSupport.getSessionVO();
        List<UsercontrollerEbayAccountExtend> ebayAccounts=userInfoServiceMapper.queryEbayAccountForUser(sessionVO.getId());
        return ebayAccounts;
    }

    @Override
    /**根据ebay帐号id 查询token*/
    public String getTokenByEbayID(Long ebayID){
        boolean b=ebayIsBindDev(ebayID);
        if(!b){return "1";}//返回1，代表当前ebay帐号没绑定当前设定的默认开发帐号

        UsercontrollerEbayAccount ebayAccount = userInfoServiceMapper.getTokenByEbayID(ebayID);
        return ebayAccount.getEbayToken();
    }

    @Override
    /**判断ebay账户是否绑定了默认的开发帐号*/
    public boolean ebayIsBindDev(Long ebayID){
        SessionVO sessionVO = SessionCacheSupport.getSessionVO();
        UsercontrollerUser user=userMapper.selectByPrimaryKey(((Long)sessionVO.getId()).intValue());
        Long defaultDevID=user.getDefaultDevAccount();//当前用户的默认绑定dev帐号
        Map map = new HashMap();
        map.put("ebayID",ebayID);
        map.put("devID",defaultDevID);
        int r=userInfoServiceMapper.ebayIsBindDev(map);
        return r>0?true:false;
    }


    @Override
    /**查询所有的开发帐号*/
    public List<UsercontrollerDevAccount> queryAllDevAccount(){
        return userInfoServiceMapper.queryAllDevAccount();
    }
}
