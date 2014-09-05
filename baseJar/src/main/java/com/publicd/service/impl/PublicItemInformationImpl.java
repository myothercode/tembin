package com.publicd.service.impl;

import com.base.database.customtrading.mapper.ItemInformationMapper;
import com.base.database.publicd.mapper.PublicItemInformationMapper;
import com.base.database.publicd.model.PublicItemInformation;
import com.base.database.publicd.model.PublicItemInformationExample;
import com.base.domains.querypojos.ItemInformationQuery;
import com.base.mybatis.page.Page;
import com.base.utils.common.ObjectUtils;
import com.base.utils.exception.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 退货政策
 * Created by lq on 2014/7/29.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PublicItemInformationImpl implements com.publicd.service.IPublicItemInformation {

    @Autowired
    private PublicItemInformationMapper PublicItemInformationMapper;
    @Autowired
    private ItemInformationMapper ItemInformationMapper;

    @Override
    public void saveItemInformation(PublicItemInformation ItemInformation) throws Exception {
        if(ItemInformation.getId()==null){
            ObjectUtils.toInitPojoForInsert(ItemInformation);
            PublicItemInformationMapper.insertSelective(ItemInformation);
        }else{
            PublicItemInformation t=PublicItemInformationMapper.selectByPrimaryKey(ItemInformation.getId());
            Asserts.assertTrue(t != null && t.getCreateUser() != null, "没有找到记录或者记录创建者为空");
            ObjectUtils.valiUpdate(t.getCreateUser(),PublicItemInformationMapper.class,ItemInformation.getId());
            PublicItemInformationMapper.updateByPrimaryKeySelective(ItemInformation);
        }
    }
    @Override
    public List<ItemInformationQuery> selectItemInformation(Map map, Page page) {
        return ItemInformationMapper.selectItemInformation(map,page);
    }

    @Override
    public void deleteItemInformation(Long id) throws Exception {
        if(id!=null){
            PublicItemInformationMapper.deleteByPrimaryKey(id);
        }
    }

    @Override
    public PublicItemInformation selectItemInformationByid(Long id) {
        PublicItemInformationExample example=new PublicItemInformationExample();
        PublicItemInformationExample.Criteria cr=example.createCriteria();
        cr.andIdEqualTo(id);
        List<PublicItemInformation> list=PublicItemInformationMapper.selectByExample(example);
        return list!=null&&list.size()>0?list.get(0):null;
    }

}
