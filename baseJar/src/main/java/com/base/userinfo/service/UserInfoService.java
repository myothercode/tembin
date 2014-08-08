package com.base.userinfo.service;

import com.base.database.trading.model.UsercontrollerDevAccount;
import com.base.database.trading.model.UsercontrollerEbayAccount;
import com.base.domains.CommonParmVO;
import com.base.domains.LoginVO;
import com.base.domains.SessionVO;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.domains.userinfo.UsercontrollerEbayAccountExtend;

import java.util.List;

/**
 * Created by Administrtor on 2014/7/24.
 */
public interface UserInfoService {
    SessionVO getUserInfo(LoginVO loginVO);

    /**获取开发者帐号的信息*/
    UsercontrollerDevAccountExtend getDevInfo(Long id) throws Exception;

    /**保存绑定帐号过后的token*/
    void saveToken(UsercontrollerEbayAccount ebayAccount, CommonParmVO commonParmVO) throws Exception;

    /**查询当前系统账户绑定了哪些ebay账户*/
    List<UsercontrollerEbayAccountExtend> getEbayAccountForCurrUser();

    /**根据ebay帐号id 查询token*/
    String getTokenByEbayID(Long ebayID);

    /**判断ebay账户是否绑定了默认的开发帐号*/
    boolean ebayIsBindDev(Long ebayID);

    /**查询所有的开发帐号*/
    List<UsercontrollerDevAccount> queryAllDevAccount();
}
