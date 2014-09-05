package com.publicd.service.impl;

import com.base.database.publicd.mapper.PublicItemInventoryMapper;
import com.base.database.publicd.model.PublicItemInventory;
import com.base.database.publicd.model.PublicItemInventoryExample;
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
public class PublicItemInventoryImpl implements com.publicd.service.IPublicItemInventory {

    @Autowired
    private PublicItemInventoryMapper PublicItemInventoryMapper;

    @Override
    public void saveItemInventory(PublicItemInventory ItemInventory) throws Exception {
        if(ItemInventory.getId()==null){
            ObjectUtils.toInitPojoForInsert(ItemInventory);
            PublicItemInventoryMapper.insertSelective(ItemInventory);
        }else{
            PublicItemInventory t=PublicItemInventoryMapper.selectByPrimaryKey(ItemInventory.getId());
            Asserts.assertTrue(t != null && t.getCreateUser() != null, "没有找到记录或者记录创建者为空");
            ObjectUtils.valiUpdate(t.getCreateUser(),PublicItemInventoryMapper.class,ItemInventory.getId());
            PublicItemInventoryMapper.updateByPrimaryKeySelective(ItemInventory);
        }
    }

    @Override
    public PublicItemInventory selectItemInventoryByid(Long id) {
        PublicItemInventoryExample example=new PublicItemInventoryExample();
        PublicItemInventoryExample.Criteria cr=example.createCriteria();
        cr.andIdEqualTo(id);
        List<PublicItemInventory> list=PublicItemInventoryMapper.selectByExample(example);
        return list!=null&&list.size()>0?list.get(0):null;
    }
}
