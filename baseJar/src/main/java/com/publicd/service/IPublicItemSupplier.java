package com.publicd.service;

import com.base.database.publicd.model.PublicItemSupplier;

/**
 * Created by Administrtor on 2014/7/22.
 */
public interface IPublicItemSupplier {

    void saveItemSupplier(PublicItemSupplier ItemSupplier) throws Exception;

    PublicItemSupplier selectItemSupplierByid(Long id);

}
