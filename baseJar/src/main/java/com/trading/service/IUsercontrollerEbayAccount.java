package com.trading.service;

import com.base.database.trading.model.UsercontrollerEbayAccount;

import java.util.List;

/**
 * Created by Administrtor on 2014/7/22.
 */
public interface IUsercontrollerEbayAccount {
    List<UsercontrollerEbayAccount> selectUsercontrollerEbayAccountByUserId(Long serId);

    UsercontrollerEbayAccount selectById(Long id);
}
