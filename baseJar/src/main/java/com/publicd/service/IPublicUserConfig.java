package com.publicd.service;

import com.base.database.publicd.model.PublicUserConfig;

import java.util.List;

/**
 * Created by Administrtor on 2014/7/22.
 */
public interface IPublicUserConfig {

    void saveUserConfig(PublicUserConfig UserConfig) throws Exception;

    List<PublicUserConfig> selectUserConfigByItemType(String configType);

    PublicUserConfig selectUserConfigById(Long id);


   PublicUserConfig selectUserConfigByItemTypeName(String configType,String name);

 /*   List<PublicUserConfig> selectUserConfigByRemark();*/
}
