package com.publicd.service.impl;

import com.base.database.publicd.mapper.PublicItemPictureaddrAndAttrMapper;
import com.base.database.publicd.model.PublicItemPictureaddrAndAttr;
import com.base.database.publicd.model.PublicItemPictureaddrAndAttrExample;
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
public class PublicItemPictureaddrAndAttrImpl implements com.publicd.service.IPublicItemPictureaddrAndAttr {

    @Autowired
    private PublicItemPictureaddrAndAttrMapper PublicItemPictureaddrAndAttrMapper;

    @Override
    public void saveItemPictureaddrAndAttr(PublicItemPictureaddrAndAttr ItemPictureaddrAndAttr) throws Exception {
        if(ItemPictureaddrAndAttr.getId()==null){
            ObjectUtils.toInitPojoForInsert(ItemPictureaddrAndAttr);
            PublicItemPictureaddrAndAttrMapper.insertSelective(ItemPictureaddrAndAttr);
        }else{
            PublicItemPictureaddrAndAttr t=PublicItemPictureaddrAndAttrMapper.selectByPrimaryKey(ItemPictureaddrAndAttr.getId());
            Asserts.assertTrue(t != null && t.getCreateUser() != null, "没有找到记录或者记录创建者为空");
            ObjectUtils.valiUpdate(t.getCreateUser(),PublicItemPictureaddrAndAttrMapper.class,ItemPictureaddrAndAttr.getId());
            PublicItemPictureaddrAndAttrMapper.updateByPrimaryKeySelective(ItemPictureaddrAndAttr);
        }
    }

    @Override
    public List<PublicItemPictureaddrAndAttr> selectPictureaddrAndAttrByInformationId(Long id,String type) {
        PublicItemPictureaddrAndAttrExample example=new PublicItemPictureaddrAndAttrExample();
        PublicItemPictureaddrAndAttrExample.Criteria cr=example.createCriteria();
        cr.andIteminformationIdEqualTo(id);
        cr.andAttrtypeEqualTo(type);
        List<PublicItemPictureaddrAndAttr> list=PublicItemPictureaddrAndAttrMapper.selectByExample(example);
        return list;
    }

    @Override
    public List<PublicItemPictureaddrAndAttr> selectPictureaddrAndAttrByremarkId(Long remarkId, String type) {
        PublicItemPictureaddrAndAttrExample example=new PublicItemPictureaddrAndAttrExample();
        PublicItemPictureaddrAndAttrExample.Criteria cr=example.createCriteria();
        cr.andRemarkIdEqualTo(remarkId);
        cr.andAttrtypeEqualTo(type);
        List<PublicItemPictureaddrAndAttr> list=PublicItemPictureaddrAndAttrMapper.selectByExample(example);
        return list;
    }

    @Override
    public PublicItemPictureaddrAndAttr selectPictureaddrAndAttrById(Long id) {
        PublicItemPictureaddrAndAttrExample example=new PublicItemPictureaddrAndAttrExample();
        PublicItemPictureaddrAndAttrExample.Criteria cr=example.createCriteria();
        cr.andIdEqualTo(id);
        List<PublicItemPictureaddrAndAttr> list=PublicItemPictureaddrAndAttrMapper.selectByExample(example);
        return list!=null&&list.size()>0?list.get(0):null;
    }

    @Override
    public List<PublicItemPictureaddrAndAttr> selectPictureaddrAndAttrByRemarkIdAndNotInformationId(Long RemarkId, Long InformationId, String type) {
        PublicItemPictureaddrAndAttrExample example=new PublicItemPictureaddrAndAttrExample();
        PublicItemPictureaddrAndAttrExample.Criteria cr=example.createCriteria();
        cr.andRemarkIdEqualTo(RemarkId);
        cr.andIteminformationIdNotEqualTo(InformationId);
        cr.andAttrtypeEqualTo(type);
        List<PublicItemPictureaddrAndAttr> list=PublicItemPictureaddrAndAttrMapper.selectByExample(example);
        return list;
    }

    @Override
    public void deletePublicItemPictureaddrAndAttr(PublicItemPictureaddrAndAttr ItemPictureaddrAndAttr) throws Exception {
        if(ItemPictureaddrAndAttr!=null&&ItemPictureaddrAndAttr.getId()!=null){
            PublicItemPictureaddrAndAttrMapper.deleteByPrimaryKey(ItemPictureaddrAndAttr.getId());
        }
    }
}
