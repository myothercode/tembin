package com.base.userinfo.service.impl;

import com.base.database.trading.mapper.UsercontrollerDevAccountMapper;
import com.base.database.trading.mapper.UsercontrollerEbayAccountMapper;
import com.base.database.trading.mapper.UsercontrollerEbayDevMapper;
import com.base.database.trading.mapper.UsercontrollerPaypalAccountMapper;
import com.base.database.trading.model.*;
import com.base.database.userinfo.mapper.UsercontrollerOrgMapper;
import com.base.database.userinfo.mapper.UsercontrollerUserEbayMapper;
import com.base.database.userinfo.mapper.UsercontrollerUserMapper;
import com.base.database.userinfo.mapper.UsercontrollerUserRoleMapper;
import com.base.database.userinfo.model.UsercontrollerOrg;
import com.base.database.userinfo.model.UsercontrollerUser;
import com.base.database.userinfo.model.UsercontrollerUserEbay;
import com.base.database.userinfo.model.UsercontrollerUserRole;
import com.base.domains.*;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.domains.userinfo.UsercontrollerEbayAccountExtend;
import com.base.userinfo.mapper.UserInfoServiceMapper;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.common.EncryptionUtil;
import com.base.utils.common.ObjectUtils;
import com.base.utils.exception.Asserts;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    @Autowired
    private UsercontrollerUserEbayMapper userEbayMapper;
    @Autowired
    private UsercontrollerPaypalAccountMapper paypalAccountMapper;
    @Autowired
    private UsercontrollerOrgMapper usercontrollerOrgMapper;
    @Autowired
    private UsercontrollerUserRoleMapper userRoleMapper;


    @Override
    public SessionVO getUserInfo(LoginVO loginVO){
        String enPwd= EncryptionUtil.pwdEncrypt(loginVO.getPassword(),loginVO.getLoginId());
        loginVO.setEnpassword(enPwd);
        SessionVO sessionVO=userInfoServiceMapper.querySessionVOInfo(loginVO);//用email做登录账户
        if(ObjectUtils.isLogicalNull(sessionVO)){return null;}
        Asserts.assertTrue(StringUtils.isNotEmpty(sessionVO.getStatus()) && "1".equalsIgnoreCase(sessionVO.getStatus()) ,"账户已停用");
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
            /*SessionVO sessionVO = SessionCacheSupport.getSessionVO();
            Asserts.assertTrue(sessionVO.getDefaultDevID()!=null,"未设置默认开发帐号");
            id= Long.valueOf(sessionVO.getDefaultDevID());*/
            return new UsercontrollerDevAccountExtend();
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

        UsercontrollerUserEbay userEbay = new UsercontrollerUserEbay();
        userEbay.setUserId(ebayAccount.getUserId());
        userEbay.setEbayId(ebayAccount.getId());
        userEbayMapper.insertSelective(userEbay);
    }
    @Override
    /**更新将获取到的ebay帐号更新到表*/
    public void updateEbayAccount(UsercontrollerEbayAccount ebayAccount){
        usercontrollerEbayAccountMapper.updateByPrimaryKeySelective(ebayAccount);
    }

    @Override
    /**查询当前系统账户绑定了哪些ebay账户*/
    public List<UsercontrollerEbayAccountExtend> getEbayAccountForCurrUser(){
        SessionVO sessionVO = SessionCacheSupport.getSessionVO();
        Map map =new HashMap();
        map.put("userID",sessionVO.getId());
        map.put("resultNum","all");
        List<UsercontrollerEbayAccountExtend> ebayAccounts=userInfoServiceMapper.queryAllEbayAccountForUser(map);
        return ebayAccounts;
    }

    @Override
    /**根据ebay帐号id 查询token*/
    public String getTokenByEbayID(Long ebayID){
        //boolean b=ebayIsBindDev(ebayID);
        //if(!b){return "1";}//返回1，代表当前ebay帐号没绑定当前设定的默认开发帐号

        UsercontrollerEbayAccount ebayAccount = userInfoServiceMapper.getTokenByEbayID(ebayID);
        return ebayAccount.getEbayToken();
    }
    @Override
    /**根据ebay帐号id 查询UsercontrollerEbayAccount tiken*/
    public UsercontrollerEbayAccount getEbayAccountByEbayID(Long ebayID){
        //boolean b=ebayIsBindDev(ebayID);
        //if(!b){return null;}//返回1，代表当前ebay帐号没绑定当前设定的默认开发帐号

        UsercontrollerEbayAccount ebayAccount = userInfoServiceMapper.getTokenByEbayID(ebayID);
        return ebayAccount;
    }

    @Override
    /**判断ebay账户是否绑定了默认的开发帐号*/
    public boolean ebayIsBindDev(Long ebayID){
        SessionVO sessionVO = SessionCacheSupport.getSessionVO();
        if(sessionVO==null){return true;}
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

    @Override
    /**获取到用量最小的开发帐号*/
    public UsercontrollerDevAccountExtend getDevByOrder(Map map) throws Exception {
        UsercontrollerDevAccount u =userInfoServiceMapper.getDevByOrder(map);
        return u.toExtend();
    }

    @Override
    /**累计开发帐号的使用次数*/
    public void addUseNum(Map map){
        userInfoServiceMapper.addUseNum(map);
    }

    @Override
    /**初清零开发帐号的使用次数*/
    public void initUseNum(Map map){
        userInfoServiceMapper.initUseNum(map);
    }

    @Override
    /**启用或者停用指定ebay账户*/
    public void startOrStopEbayAccount(Map map){
        Long ebayId= (Long) map.get("ebayId");
        String act = (String) map.get("act");
        if("stop".equalsIgnoreCase(act)){
            act="0";
        }else if ("start".equalsIgnoreCase(act)){
            act="1";
        }
        UsercontrollerEbayAccount e = usercontrollerEbayAccountMapper.selectByPrimaryKey(ebayId);
        SessionVO sessionVO = SessionCacheSupport.getSessionVO();
        Asserts.assertTrue(sessionVO.getId()==e.getCreateUser().longValue(),"没有权限修改此记录！");
        e.setEbayStatus(act);
        usercontrollerEbayAccountMapper.updateByPrimaryKey(e);
    }
    @Override
    /**修改ebay账户*/
    public void editEbayAccount(Map map){
        Long ebayId= (Long) map.get("id");
        String name= (String) map.get("name");
        String code= (String) map.get("code");
        String payPalId= (String) map.get("payPalId");
        UsercontrollerEbayAccount e = usercontrollerEbayAccountMapper.selectByPrimaryKey(ebayId);
        SessionVO sessionVO = SessionCacheSupport.getSessionVO();
        Asserts.assertTrue(sessionVO.getId()==e.getCreateUser().longValue(),"没有权限修改此记录！");
        e.setEbayName(name);
        e.setEbayNameCode(code);
        e.setPaypalAccountId(Long.valueOf(payPalId));
        usercontrollerEbayAccountMapper.updateByPrimaryKey(e);
    }

    @Override
    /**根据id查询ebay信息*/
    public UsercontrollerEbayAccountExtend queryEbayInfoById(Map map){
        UsercontrollerEbayAccount ea=usercontrollerEbayAccountMapper.selectByPrimaryKey((Long) map.get("ebayId"));
        UsercontrollerEbayAccountExtend eae=new UsercontrollerEbayAccountExtend();
        eae.setEbayName(ea.getEbayName());
        eae.setEbayNameCode(ea.getEbayNameCode());
        eae.setPaypalAccountId(ea.getPaypalAccountId());

        if(ea.getPaypalAccountId()!=null){
            UsercontrollerPaypalAccount s= paypalAccountMapper.selectByPrimaryKey(ea.getPaypalAccountId());
            eae.setPaypalName(s.getPaypalAccount());
        }
        return eae;
    }

    @Override
    /**新增注册用户*/
    public void regInsertUserInfo(Map map){
        UsercontrollerOrg org= (UsercontrollerOrg) map.get("UsercontrollerOrg");
        UsercontrollerUser user = (UsercontrollerUser) map.get("UsercontrollerUser");
        org.setStatus("1");
        usercontrollerOrgMapper.insert(org);
        user.setUserOrgId(org.getOrgId().intValue());
        user.setStatus("1");
        user.setDefaultDevAccount(1L);
        user.setUserPassword(EncryptionUtil.pwdEncrypt(user.getUserPassword(), user.getUserLoginId()));
        user.setUserParentId(null);
        userMapper.insert(user);

        UsercontrollerUserRole userRole = new UsercontrollerUserRole();
        userRole.setUserId(user.getUserId());
        userRole.setRoleId(1);//通过网页注册的帐号，默认都是管理员帐号
        userRoleMapper.insert(userRole);
    }

}
