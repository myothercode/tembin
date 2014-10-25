package com.task.service;

import com.base.database.task.model.ListingDataTask;

import java.util.List;

/**
 * Created by Administrtor on 2014/10/17.
 */
public interface IListingDataTask {
    void saveListDataTask(ListingDataTask listingDataTask);

    List<ListingDataTask> selectByflag(String siteid, String ebayAccount);

    List<ListingDataTask> selectByTimerTaskflag();
}
