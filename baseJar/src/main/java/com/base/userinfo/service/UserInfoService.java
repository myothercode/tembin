package com.base.userinfo.service;

import com.base.database.trading.model.UsercontrollerDevAccount;
import com.base.database.trading.model.UsercontrollerEbayAccount;
import com.base.domains.CommonParmVO;
import com.base.domains.LoginVO;
import com.base.domains.SessionVO;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.domains.userinfo.UsercontrollerEbayAccountExtend;
import com.base.mybatis.page.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/7/24.
 */
public interface UserInfoService {
    SessionVO getUserInfo(LoginVO loginVO);

    /**获取开发者帐号的信息*/
    UsercontrollerDevAccountExtend getDevInfo(Long id) throws Exception;

    /**保存绑定帐号过后的token*/
    void saveToken(UsercontrollerEbayAccount ebayAccount, CommonParmVO commonParmVO) throws Exception;

    /**更新将获取到的ebay帐号更新到表*/
    void updateEbayAccount(UsercontrollerEbayAccount ebayAccount);

    /**查询当前系统账户绑定了哪些ebay账户*/
    List<UsercontrollerEbayAccountExtend> getEbayAccountForCurrUser(Map map1,Page page);

    /**根据ebay帐号id 查询token*/
    String getTokenByEbayID(Long ebayID);

    /**根据ebay帐号id 查询UsercontrollerEbayAccount*/
    UsercontrollerEbayAccount getEbayAccountByEbayID(Long ebayID);

    /**判断ebay账户是否绑定了默认的开发帐号*/
    boolean ebayIsBindDev(Long ebayID);

    /**查询所有的开发帐号*/
    List<UsercontrollerDevAccount> queryAllDevAccount();

    /**获取到用量最小的开发帐号*/
    UsercontrollerDevAccountExtend getDevByOrder(Map map) throws Exception;

    /**累计开发帐号的使用次数*/
    void addUseNum(Map map);

    /**初清零开发帐号的使用次数*/
    void initUseNum(Map map);

    /**启用或者停用指定ebay账户*/
    void startOrStopEbayAccount(Map map);

    /**修改ebay账户*/
    void editEbayAccount(Map map);

    /**根据id查询ebay信息*/
    UsercontrollerEbayAccountExtend queryEbayInfoById(Map map);

    /**新增注册用户*/
    void regInsertUserInfo(Map map);
}
