/*
package com.common.base.web;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

*/
/**
 * Created by chace.cai on 2014/7/10.
 *//*

@Service("monitorRealm")
public class ShiroMonitorRealm extends AuthorizingRealm {
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String currentUsername = (String)super.getAvailablePrincipal(principals);
        SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();
        Set<String> strings=new HashSet<String>();
        strings.add("admin");
        simpleAuthorInfo.setRoles(strings);

        Set<String> strings1=new HashSet<String>();
        strings1.add("admin:manage");
        simpleAuthorInfo.setStringPermissions(strings1);
        return simpleAuthorInfo;
        //return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //subjectDAO.sessionStorageEvaluator.sessionStorageEnabled
        UsernamePasswordToken token= (UsernamePasswordToken) authenticationToken;
        String userId = token.getUsername();
        char[] pwd = token.getPassword();

        if("jadyer".equals(token.getUsername())){
            AuthenticationInfo authcInfo = new SimpleAuthenticationInfo("jadyer", "123aghfsd", this.getName());
            this.setSession("currentUser", "jadyer");
            return authcInfo;
        }else if("玄玉".equals(token.getUsername())){
            AuthenticationInfo authcInfo = new SimpleAuthenticationInfo("玄玉", "xuanyu", this.getName());
            this.setSession("currentUser", "玄玉");
            return authcInfo;
        }
        return null;
    }
    */
/**
     * 将一些数据放到ShiroSession中,以便于其它地方使用
     * 比如Controller,使用时直接用HttpSession.getAttribute(key)就可以取到
     *//*

    private void setSession(Object key, Object value){
        Subject currentUser = SecurityUtils.getSubject();
        if(null != currentUser){
            Session session = currentUser.getSession();
            if(null != session){
                session.setAttribute(key, value);
            }
        }
    }
}
*/
