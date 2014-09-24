package com.base.userinfo.service;

import com.base.domains.PermissionVO;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2014/9/15.
 */
public interface MenuService {
    List<PermissionVO> getUserMenuList(Map map);

    List<PermissionVO> getAllMenuList(Map map);
}
