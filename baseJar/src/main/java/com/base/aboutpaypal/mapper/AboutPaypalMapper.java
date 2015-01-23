package com.base.aboutpaypal.mapper;

import com.base.database.trading.model.UsercontrollerPaypalAccount;
import com.base.domains.querypojos.BaseTjReportQuery;
import com.base.mybatis.page.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2014/9/28.
 */
public interface AboutPaypalMapper {
    /**分页查询paypal帐号列表*/
    public List<UsercontrollerPaypalAccount> queryPayPalsByUserId(Map map,Page page);

    /**停用账户时清理掉ebay绑定的帐号*/
    public void clearEbayPaypal(Map map);
    /**分页查询paypal帐号列表*/
    public List<UsercontrollerPaypalAccount> queryPayPalsByUserIdReport(Map map,Page page);

    /**
     * 统计
     * @return
     */
    public List<BaseTjReportQuery> selectPayPalReportList(Map m,Page page);
}
