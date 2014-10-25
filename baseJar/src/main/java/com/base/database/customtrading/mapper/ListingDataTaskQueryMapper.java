package com.base.database.customtrading.mapper;

import com.base.database.task.model.ListingDataTask;
import java.util.Map;

public interface ListingDataTaskQueryMapper {
    ListingDataTask selectByMaxCreateDate(Map map);
}