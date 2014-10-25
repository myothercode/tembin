package com.trading.service.impl;

import com.base.database.trading.mapper.UsercontrollerEbayAccountMapper;
import com.base.database.trading.model.UsercontrollerEbayAccountExample;
import com.base.database.trading.model.UsercontrollerEbayAccount;
import com.base.utils.common.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 退货政策
 * Created by lq on 2014/7/29.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UsercontrollerEbayAccountImpl implements com.trading.service.IUsercontrollerEbayAccount {

    @Autowired
    private UsercontrollerEbayAccountMapper usercontrollerEbayAccountMapper;
    @Override
    public List<UsercontrollerEbayAccount> selectUsercontrollerEbayAccountByUserId(Long userId) {
        UsercontrollerEbayAccountExample useraccount=new UsercontrollerEbayAccountExample();
        UsercontrollerEbayAccountExample.Criteria uc=useraccount.createCriteria();
        uc.andUserIdEqualTo(userId);
        List<UsercontrollerEbayAccount> lists= usercontrollerEbayAccountMapper.selectByExample(useraccount);
        return lists;
    }
    @Override
    public UsercontrollerEbayAccount selectById(Long id){
        return this.usercontrollerEbayAccountMapper.selectByPrimaryKey(id);
    }

    @Override
    public UsercontrollerEbayAccount selectByEbayAccount(String ebayAccount){
        UsercontrollerEbayAccountExample useraccount=new UsercontrollerEbayAccountExample();
        UsercontrollerEbayAccountExample.Criteria uc=useraccount.createCriteria();
        uc.andEbayAccountEqualTo(ebayAccount);
        List<UsercontrollerEbayAccount> lists= usercontrollerEbayAccountMapper.selectByExampleWithBLOBs(useraccount);
        if(!ObjectUtils.isLogicalNull(lists)){
            return lists.get(0);
        }else{
            return null;
        }

    }

}
