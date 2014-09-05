package com.publicd.service;

import com.base.database.publicd.model.PublicItemCustom;

/**
 * Created by Administrtor on 2014/7/22.
 */
public interface IPublicItemCustom {

    void saveItemCustom(PublicItemCustom ItemCustom) throws Exception;

    PublicItemCustom selectItemCustomByid(Long id);
}
