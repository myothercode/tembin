package com.orderassess.service;

import com.base.database.trading.model.OrderAutoAssess;
import com.base.domains.userinfo.UsercontrollerUserExtend;

import java.util.List;

/**
 * Created by Administrtor on 2014/11/19.
 */
public interface IOrderAutoAssess {
    void saveAssessConent(OrderAutoAssess orderAutoAssess);

    void deltelAccessConent(Long id);

    OrderAutoAssess selectById(Long id);

    List<OrderAutoAssess> selectAssessList(Long userid);

    OrderAutoAssess selectRandomContent(List<UsercontrollerUserExtend> liuue);
}
