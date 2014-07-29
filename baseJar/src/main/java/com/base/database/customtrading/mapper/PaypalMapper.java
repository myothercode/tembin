package com.base.database.customtrading.mapper;

import com.base.domains.querypojos.ItemAddressQuery;
import com.base.domains.querypojos.PaypalQuery;
import com.base.mybatis.page.Page;

import java.util.List;
import java.util.Map;

public interface PaypalMapper {

    /**
     *
     * @param map
     * @return
     */
    List<PaypalQuery> selectByPayPalList(Map map,Page page);
}