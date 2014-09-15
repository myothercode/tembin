package com.base.userinfo.service.impl;

import com.base.domains.PermissionVO;
import com.base.domains.SessionVO;
import com.base.userinfo.service.MenuService;
import com.base.userinfo.service.UserInfoService;
import com.base.utils.cache.SessionCacheSupport;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2014/9/15.
 * 管理菜单权限的service
 */
@Service
public class MenuServiceImpl implements MenuService {
    //private UserInfoService userInfoService;
    /**获取当前用户的菜单*/
    @Override
    public List<PermissionVO> getUserMenuList(Map map){
        SessionVO sessionVO= SessionCacheSupport.getSessionVO();
        return sessionVO.getPermissions();

    }
}
