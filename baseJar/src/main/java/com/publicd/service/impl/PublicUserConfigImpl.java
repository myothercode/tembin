package com.publicd.service.impl;

import com.base.database.publicd.mapper.PublicUserConfigMapper;
import com.base.database.publicd.model.PublicUserConfig;
import com.base.database.publicd.model.PublicUserConfigExample;
import com.base.utils.common.ObjectUtils;
import com.base.utils.exception.Asserts;
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
public class PublicUserConfigImpl implements com.publicd.service.IPublicUserConfig {
    @Autowired
    private PublicUserConfigMapper publicUserConfigMapper;
    @Override
    public void saveUserConfig(PublicUserConfig UserConfig) throws Exception {
        if(UserConfig.getId()==null){
            ObjectUtils.toInitPojoForInsert(UserConfig);
            publicUserConfigMapper.insert(UserConfig);
        }else{
            PublicUserConfig t=publicUserConfigMapper.selectByPrimaryKey(UserConfig.getId());
            Asserts.assertTrue(t != null && t.getUserId() != null, "没有找到记录或者记录创建者为空");
            ObjectUtils.valiUpdate(t.getUserId(),PublicUserConfigMapper.class,UserConfig.getId());
            publicUserConfigMapper.updateByPrimaryKeySelective(UserConfig);
        }
    }

    @Override
    public List<PublicUserConfig> selectUserConfigByItemType() {
        PublicUserConfigExample example=new PublicUserConfigExample();
        PublicUserConfigExample.Criteria cr=example.createCriteria();
        cr.andConfigTypeEqualTo("itemType");
        List<PublicUserConfig> list=publicUserConfigMapper.selectByExample(example);
        return list;
    }

/*    @Override
    public List<PublicUserConfig> selectUserConfigByRemark() {
        PublicUserConfigExample example=new PublicUserConfigExample();
        PublicUserConfigExample.Criteria cr=example.createCriteria();
        cr.andConfigTypeEqualTo("remark");
        List<PublicUserConfig> list=publicUserConfigMapper.selectByExample(example);
        return list;
    }*/


}
