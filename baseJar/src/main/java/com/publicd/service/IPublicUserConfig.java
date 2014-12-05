package com.publicd.service;

import com.base.database.publicd.model.PublicUserConfig;

import java.util.List;

/**
 * Created by Administrtor on 2014/7/22.
 */
public interface IPublicUserConfig {

    void saveUserConfig(PublicUserConfig UserConfig) throws Exception;

    List<PublicUserConfig> selectUserConfigByItemType(String configType,Long userId);

    PublicUserConfig selectUserConfigById(Long id);

    void deleteUserConfig(PublicUserConfig UserConfig) throws Exception;

   PublicUserConfig selectUserConfigByItemTypeName(String configType,String name);

 /*   List<PublicUserConfig> selectUserConfigByRemark();*/
}
