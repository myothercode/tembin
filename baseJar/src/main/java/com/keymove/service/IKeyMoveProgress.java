package com.keymove.service;

import com.base.database.trading.model.TradingProgress;
import com.base.domains.querypojos.KeyMoveProgressQuery;

import java.util.List;

/**
 * Created by Administrtor on 2014/12/8.
 */
public interface IKeyMoveProgress {
    List<KeyMoveProgressQuery> selectByUserId(long userId);

    void saveProgress(TradingProgress tradingProgress);

    void deleteById(Long id);

    TradingProgress selectById(Long id);
}
