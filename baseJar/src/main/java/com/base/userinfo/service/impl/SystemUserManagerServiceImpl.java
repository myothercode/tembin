package com.base.userinfo.service.impl;

import com.base.domains.SessionVO;
import com.base.domains.userinfo.UsercontrollerUserExtend;
import com.base.mybatis.page.Page;
import com.base.userinfo.mapper.SystemUserManagerServiceMapper;
import com.base.userinfo.service.SystemUserManagerService;
import com.base.utils.cache.SessionCacheSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2014/9/19.
 * 系统账户管理
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SystemUserManagerServiceImpl implements SystemUserManagerService {
    @Autowired
    private SystemUserManagerServiceMapper systemUserManagerServiceMapper;

    @Override
    /**查询系统用户以及列表*/
    public List<UsercontrollerUserExtend> queryAccountListByUserID(Map map, Page page){
        SessionVO sessionVO= SessionCacheSupport.getSessionVO();
        map.put("userID",sessionVO.getId());
        return systemUserManagerServiceMapper.queryAccountListByUserID(map,page);
    }
}
