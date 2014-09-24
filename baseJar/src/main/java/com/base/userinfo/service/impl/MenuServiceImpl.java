package com.base.userinfo.service.impl;

import com.base.domains.PermissionVO;
import com.base.domains.SessionVO;
import com.base.userinfo.mapper.UserInfoServiceMapper;
import com.base.userinfo.service.MenuService;
import com.base.userinfo.service.UserInfoService;
import com.base.utils.cache.SessionCacheSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2014/9/15.
 * 管理菜单权限的service
 */
@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private UserInfoServiceMapper userInfoServiceMapper;
    /**获取当前用户的菜单*/
    @Override
    public List<PermissionVO> getUserMenuList(Map map){
        SessionVO sessionVO= SessionCacheSupport.getSessionVO();
        return sessionVO.getPermissions();

    }

    /**获取所有菜单*/
    @Override
    public List<PermissionVO> getAllMenuList(Map map){
        return userInfoServiceMapper.queryAllPermission(map);
    }
}
