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
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.common.*;
import com.base.utils.exception.Asserts;
import com.base.utils.mailUtil.MailUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
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
    static Logger logger = Logger.getLogger(SystemUserManagerServiceImpl.class);

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

    @Override
    /**发送找回密码的验证码*/
    public void sendSafeCode(Map map){
        HttpServletRequest request = (HttpServletRequest) map.get("HttpServletRequest");
        String loginUserID = (String) map.get("loginUserID");

        Object sdate=request.getSession().getAttribute("opDateTime_");
        if(sdate!=null){
            long date1= ((Date) sdate).getTime();
            Date date2=new Date();
            long t=((date2.getTime()-date1)/1000L);
            Asserts.assertTrue(t>60L,"请一分钟后再操作!");
        }
        String scode= UUIDUtil.getUUID();
        request.getSession().setAttribute("passWordSafeCode_",scode);
        request.getSession().setAttribute("opDateTime_",new Date());
        request.getSession().setAttribute("loginUserIDchangePWD_",loginUserID);

        UsercontrollerUserExample userExample = new UsercontrollerUserExample();
        userExample.createCriteria().andUserLoginIdEqualTo(loginUserID);
        List<UsercontrollerUser> user = userMapper.selectByExample(userExample);
        Asserts.assertTrue(!ObjectUtils.isLogicalNull(user),"没有此帐号");
        UsercontrollerUser usert=user.get(0);

        Asserts.assertTrue(StringUtils.isNotEmpty(usert.getUserEmail()),"该帐号没有指定email，请联系管理员");

        Email email = new SimpleEmail();
        try {

            SystemLog log=new SystemLog();
            log.setEventname(SystemLogUtils.FIND_PASSWORD);
            log.setOperuser(usert.getUserLoginId());
            log.setEventdesc("通过邮件找回密码!邮箱为"+usert.getUserEmail());
            SystemLogUtils.saveLog(log);

            email.addTo(usert.getUserEmail());
            email.setSubject("tembin密码修改验证码");
            email.setMsg("您正在进行密码找回操作，本次操作验证码为:"+scode);

            MailUtils mailUtils= (MailUtils) ApplicationContextUtil.getBean("mailUtils");
            mailUtils.sendMail(email);

        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }

    }

    @Override
    /**修改被遗忘的密码*/
    public String doChangeForgetPassWord(Map map){
        HttpServletRequest request = (HttpServletRequest) map.get("HttpServletRequest");
        String loginUserID = (String) map.get("loginUserId");
        String newPWD = (String) map.get("newPWD");
        String safeCode = (String) map.get("safeCode");

        Object remotsafeCodeobj= request.getSession().getAttribute("passWordSafeCode_");
        Asserts.assertTrue(!ObjectUtils.isLogicalNull(remotsafeCodeobj),"请先获取邮箱验证码，谢谢！");
        String remotsafeCode = (String) remotsafeCodeobj;
        Asserts.assertTrue(remotsafeCode.equals(safeCode),"邮箱验证码不正确!");
        String loginUserId1= (String) request.getSession().getAttribute("loginUserIDchangePWD_");
        Asserts.assertTrue(loginUserID.equals(loginUserId1),"需要修改的账户名不对！");


        UsercontrollerUserExample userExample = new UsercontrollerUserExample();
        userExample.createCriteria().andUserLoginIdEqualTo(loginUserID);
        List<UsercontrollerUser> users = userMapper.selectByExample(userExample);
        Asserts.assertTrue(!ObjectUtils.isLogicalNull(users),"没有此帐号");
        UsercontrollerUser user=users.get(0);

        String enewp = EncryptionUtil.pwdEncrypt(newPWD, loginUserID);
        user.setUserPassword(enewp);
        userMapper.updateByPrimaryKeySelective(user);
        return "success";
    }

}
