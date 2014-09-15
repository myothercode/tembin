package com.publicd.service;

import com.base.database.publicd.model.PublicItemPictureaddrAndAttr;

import java.util.List;

/**
 * Created by Administrtor on 2014/7/22.
 */
public interface IPublicItemPictureaddrAndAttr {

    void saveItemPictureaddrAndAttr(PublicItemPictureaddrAndAttr ItemPictureaddrAndAttr) throws Exception;
    List<PublicItemPictureaddrAndAttr> selectPictureaddrAndAttrByInformationId(Long id,String type,Long userid);
    void deletePublicItemPictureaddrAndAttr(PublicItemPictureaddrAndAttr ItemPictureaddrAndAttr) throws Exception;
}
