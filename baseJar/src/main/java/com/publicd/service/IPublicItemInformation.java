package com.publicd.service;

import com.base.database.publicd.model.PublicItemInformation;
import com.base.domains.querypojos.ItemInformationQuery;
import com.base.mybatis.page.Page;

import javax.servlet.ServletOutputStream;
import java.io.File;
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

    List<ItemInformationQuery> selectItemInformationByType(Map map, Page page);

    PublicItemInformation selectItemInformationBySKU(String sku);

    void exportItemInformation(List<PublicItemInformation> list,String outputFile,ServletOutputStream outputStream) throws Exception;

    void importItemInformation(File file,String fileName) throws Exception;

    List<PublicItemInformation> selectItemInformationByTypeIsNull();

}
