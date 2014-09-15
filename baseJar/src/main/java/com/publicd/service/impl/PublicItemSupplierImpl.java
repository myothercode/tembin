package com.publicd.service.impl;

import com.base.database.publicd.mapper.PublicItemSupplierMapper;
import com.base.database.publicd.model.PublicItemSupplier;
import com.base.database.publicd.model.PublicItemSupplierExample;
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
public class PublicItemSupplierImpl implements com.publicd.service.IPublicItemSupplier {

    @Autowired
    private PublicItemSupplierMapper PublicItemSupplierMapper;

    @Override
    public void saveItemSupplier(PublicItemSupplier ItemSupplier) throws Exception {
        if(ItemSupplier.getId()==null){
            ObjectUtils.toInitPojoForInsert(ItemSupplier);
            PublicItemSupplierMapper.insertSelective(ItemSupplier);
        }else{
            PublicItemSupplier t=PublicItemSupplierMapper.selectByPrimaryKey(ItemSupplier.getId());
            Asserts.assertTrue(t != null && t.getCreateUser() != null, "没有找到记录或者记录创建者为空");
            ObjectUtils.valiUpdate(t.getCreateUser(),PublicItemSupplierMapper.class,ItemSupplier.getId());
            PublicItemSupplierMapper.updateByPrimaryKeySelective(ItemSupplier);
        }
    }

    @Override
    public PublicItemSupplier selectItemSupplierByid(Long id) {
        PublicItemSupplierExample example=new PublicItemSupplierExample();
        PublicItemSupplierExample.Criteria cr=example.createCriteria();
        cr.andIdEqualTo(id);
        List<PublicItemSupplier> list=PublicItemSupplierMapper.selectByExample(example);
        return list!=null&&list.size()>0?list.get(0):null;
    }

    @Override
    public void deleteItemSupplier(Long id) throws Exception {
        if(id!=null){
            PublicItemSupplierMapper.deleteByPrimaryKey(id);
        }
    }

    @Override
    public PublicItemSupplier selectItemSupplierByName(String name) {
        PublicItemSupplierExample example=new PublicItemSupplierExample();
        PublicItemSupplierExample.Criteria cr=example.createCriteria();
        cr.andNameEqualTo(name);
        List<PublicItemSupplier> list=PublicItemSupplierMapper.selectByExample(example);
        return list!=null&&list.size()>0?list.get(0):null;
    }
}
