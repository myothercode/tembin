package com.trading.service.impl;

import com.base.database.customtrading.mapper.AutoMessageMapper;
import com.base.database.trading.mapper.TradingAutoMessageMapper;
import com.base.database.trading.mapper.UsercontrollerEbayAccountMapper;
import com.base.database.trading.model.TradingAutoMessage;
import com.base.database.trading.model.TradingAutoMessageExample;
import com.base.database.userinfo.mapper.UsercontrollerUserMapper;
import com.base.database.userinfo.model.UsercontrollerUser;
import com.base.database.userinfo.model.UsercontrollerUserExample;
import com.base.domains.querypojos.AutoMessageQuery;
import com.base.domains.userinfo.UsercontrollerUserExtend;
import com.base.mybatis.page.Page;
import com.base.userinfo.mapper.SystemUserManagerServiceMapper;
import com.base.userinfo.service.UserInfoService;
import com.base.utils.common.ObjectUtils;
import com.base.utils.exception.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/8/14.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TradingAutoMessageImpl implements com.trading.service.ITradingAutoMessage {
    @Autowired
    private TradingAutoMessageMapper tradingAutoMessageMapper;
    @Autowired
    private AutoMessageMapper autoMessageMapper;
    @Autowired
    private SystemUserManagerServiceMapper systemUserManagerServiceMapper;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UsercontrollerUserMapper usercontrollerUserMapper;

    @Override
    public void saveAutoMessage(TradingAutoMessage autoMessage) throws Exception {
        if(autoMessage.getId()==null){
            ObjectUtils.toInitPojoForInsert(autoMessage);
            this.tradingAutoMessageMapper.insertSelective(autoMessage);
        }else{
            TradingAutoMessage t=tradingAutoMessageMapper.selectByPrimaryKey(autoMessage.getId());
            Asserts.assertTrue(t != null && t.getCreateUser() != null, "没有找到记录或者记录创建者为空");
            ObjectUtils.valiUpdate(t.getCreateUser(),TradingAutoMessageMapper.class,autoMessage.getId());
            this.tradingAutoMessageMapper.updateByPrimaryKeySelective(autoMessage);
        }
    }

    @Override
    public List<AutoMessageQuery> selectAutoMessageList(Map map, Page page) {
        return autoMessageMapper.selectAutoMessageList(map,page);
    }

    @Override
    public List<TradingAutoMessage> selectAutoMessageById(Long id) {
        TradingAutoMessageExample example=new TradingAutoMessageExample();
        TradingAutoMessageExample.Criteria cr=example.createCriteria();
        cr.andIdEqualTo(id);
        List<TradingAutoMessage> list=tradingAutoMessageMapper.selectByExample(example);
        return list;
    }

    @Override
    public void deleteAutoMessage(TradingAutoMessage autoMessage) throws Exception {
        if(autoMessage!=null&&autoMessage.getId()!=null){
            tradingAutoMessageMapper.deleteByPrimaryKey(autoMessage.getId());
        }
    }

    @Override
    public List<AutoMessageQuery> selectShippingServiceOptionList(Map map, Page page) {
        return autoMessageMapper.selectShippingServiceOptionList(map,page);
    }

    @Override
    public List<AutoMessageQuery> selectInternationalShippingServiceList(Map map, Page page) {
        return autoMessageMapper.selectInternationalShippingServiceList(map,page);
    }

    @Override
    public List<TradingAutoMessage> selectAutoMessageByType(String type,Long userid) {
        TradingAutoMessageExample example=new TradingAutoMessageExample();
        TradingAutoMessageExample.Criteria cr=example.createCriteria();
        //-----------
        UsercontrollerUserExample example1=new UsercontrollerUserExample();
        UsercontrollerUserExample.Criteria criteria=example1.createCriteria();
        criteria.andUserIdEqualTo(Integer.valueOf(userid+""));
        List<UsercontrollerUser> users=usercontrollerUserMapper.selectByExample(example1);
        UsercontrollerUser user=new UsercontrollerUser();
        if(users!=null&&users.size()>0){
            user=users.get(0);
        }
        Map map=new HashMap();
        map.put("orgID",user.getUserOrgId());
        map.put("isShowStopOnly","yes");
        List<UsercontrollerUserExtend> userExtends=systemUserManagerServiceMapper.queryAllUsersByOrgID(map);
        List<Long> userids=new ArrayList<Long>();
        if(userExtends!=null&&userExtends.size()>0){
            for(UsercontrollerUserExtend userExtend:userExtends){
                Long userid1=Long.valueOf(userExtend.getUserId());
                userids.add(userid1);
            }
        }else{
            userids.add(userid);
        }
        //----------
        cr.andTypeEqualTo(type);
        cr.andCreateUserIn(userids);
        List<TradingAutoMessage> list=tradingAutoMessageMapper.selectByExample(example);
        return list;
    }
}
