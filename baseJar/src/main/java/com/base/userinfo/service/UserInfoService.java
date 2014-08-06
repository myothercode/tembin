package com.base.userinfo.service;

import com.base.domains.LoginVO;
import com.base.domains.SessionVO;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;

/**
 * Created by Administrtor on 2014/7/24.
 */
public interface UserInfoService {
    SessionVO getUserInfo(LoginVO loginVO);

    /**获取开发者帐号的信息*/
    UsercontrollerDevAccountExtend getDevInfo(Long id) throws Exception;
}
