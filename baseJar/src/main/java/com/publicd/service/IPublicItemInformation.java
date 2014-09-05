package com.publicd.service;

import com.base.database.publicd.model.PublicItemInformation;
import com.base.domains.querypojos.ItemInformationQuery;
import com.base.mybatis.page.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/7/22.
 */
public interface IPublicItemInformation {

    void saveItemInformation(PublicItemInformation ItemInformation) throws Exception;

    List<ItemInformationQuery> selectItemInformation(Map map, Page page);

    void deleteItemInformation (Long id) throws Exception;

    PublicItemInformation selectItemInformationByid(Long id);

}
