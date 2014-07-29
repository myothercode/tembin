package com.trading.service;

import com.base.database.publicd.model.PublicDataDict;
import com.base.database.publicd.model.PublicUserConfig;
import com.base.database.trading.model.TradingDataDictionary;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * Created by Administrtor on 2014/7/24.
 */
public interface ITradingDataDictionary {
    void saveDataDictionary(TradingDataDictionary tradingDataDictionary);

    TradingDataDictionary toDAOPojo(String value, String name, String type, String value1, String name1) throws Exception;

    List<TradingDataDictionary> selectDictionaryByType(String type, String name1, String name, String value);

    /**根据类型查询TradingDataDictionary*/
    List<TradingDataDictionary> selectDictionaryByType(String type);

    /**根据类型查询PublicDataDict*/
    List<PublicDataDict> selectPublicDataDictByType(String type);

    /**根据类型和用户id查询userDataDict*/
    List<PublicUserConfig> selectUserConfigDataDictByType(String type, Long userId);

    /**根据ID查询TradingDataDictionary*/
    TradingDataDictionary selectDictionaryByID(Long id);

    /**根据ID查询publicDataDictionary*/
    PublicDataDict selectPublicDictionaryByID(Long id);

    /**根据ID查询userConfig*/
    PublicUserConfig selectUserConfigByID(Long id);
}
