package com.publicd.service.impl;

import com.base.database.publicd.mapper.PublicItemCustomMapper;
import com.base.database.publicd.model.PublicItemCustom;
import com.base.database.publicd.model.PublicItemCustomExample;
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
public class PublicItemCustomImpl implements com.publicd.service.IPublicItemCustom {

    @Autowired
    private PublicItemCustomMapper PublicItemCustomMapper;

    @Override
    public void saveItemCustom(PublicItemCustom ItemCustom) throws Exception {
        if(ItemCustom.getId()==null){
            ObjectUtils.toInitPojoForInsert(ItemCustom);
            PublicItemCustomMapper.insertSelective(ItemCustom);
        }else{
            PublicItemCustom t=PublicItemCustomMapper.selectByPrimaryKey(ItemCustom.getId());
            Asserts.assertTrue(t != null && t.getCreateUser() != null, "没有找到记录或者记录创建者为空");
            ObjectUtils.valiUpdate(t.getCreateUser(),PublicItemCustomMapper.class,ItemCustom.getId());
            PublicItemCustomMapper.updateByPrimaryKeySelective(ItemCustom);
        }
    }

    @Override
    public PublicItemCustom selectItemCustomByid(Long id) {
        PublicItemCustomExample example=new PublicItemCustomExample();
        PublicItemCustomExample.Criteria cr=example.createCriteria();
        cr.andIdEqualTo(id);
        List<PublicItemCustom> list=PublicItemCustomMapper.selectByExample(example);
        return list!=null&&list.size()>0?list.get(0):null;
    }
}
