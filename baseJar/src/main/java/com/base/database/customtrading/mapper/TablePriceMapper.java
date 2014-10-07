package com.base.database.customtrading.mapper;

import com.base.domains.querypojos.ItemQuery;
import com.base.domains.querypojos.TablePriceQuery;
import com.base.mybatis.page.Page;

import java.util.List;
import java.util.Map;

public interface TablePriceMapper {

    List<TablePriceQuery> selectByList(Map map, Page page);

}