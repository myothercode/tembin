package com.base.userinfo.service;

import com.base.database.trading.model.UsercontrollerEbayAccount;
import com.base.database.trading.model.UsercontrollerEbayDev;
import com.base.domains.CommonParmVO;
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

    /**保存绑定帐号过后的token*/
    void saveToken(UsercontrollerEbayAccount ebayAccount, CommonParmVO commonParmVO) throws Exception;
}
