package com.base.database.customtrading.mapper;

import com.base.domains.querypojos.DiscountpriceinfoQuery;
import com.base.mybatis.page.Page;

import java.util.List;
import java.util.Map;

public interface DiscountpriceinfoMapper {

    List<DiscountpriceinfoQuery> selectByDiscountpriceinfoList(Map map,Page page);

}