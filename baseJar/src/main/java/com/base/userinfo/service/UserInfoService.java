package com.base.userinfo.service;

import com.base.domains.LoginVO;
import com.base.domains.SessionVO;

/**
 * Created by Administrtor on 2014/7/24.
 */
public interface UserInfoService {
    SessionVO getUserInfo(LoginVO loginVO);
}
