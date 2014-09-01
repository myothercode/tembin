package com.base.database.customtrading.mapper;

import com.base.domains.querypojos.UserCasesQuery;
import com.base.mybatis.page.Page;

import java.util.List;
import java.util.Map;

public interface UserCasesMapper {

    List<UserCasesQuery> selectUserCases(Map map, Page page);

}