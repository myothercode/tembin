package com.task.service.impl;

import com.base.database.customtrading.mapper.ListingDataMapper;
import com.base.database.task.mapper.ListingDataTaskMapper;
import com.base.database.task.model.ListingDataTask;
import com.base.database.task.model.ListingDataTaskExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrtor on 2014/10/17.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ListingDataTaskImpl implements com.task.service.IListingDataTask {
    @Autowired
    private ListingDataTaskMapper listingDataTaskMapper;

    @Override
    public void saveListDataTask(ListingDataTask listingDataTask){
        if(listingDataTask.getId()==null){
            this.listingDataTaskMapper.insertSelective(listingDataTask);
        }else{
            this.listingDataTaskMapper.updateByPrimaryKeySelective(listingDataTask);
        }
    }

    /**
     * 查询定时任务是否执行
     * @param siteid
     * @param ebayAccount
     * @return
     */
    @Override
    public List<ListingDataTask> selectByflag(String siteid,String ebayAccount){
        ListingDataTaskExample tde = new ListingDataTaskExample();
        ListingDataTaskExample.Criteria c = tde.createCriteria();
        c.andTaskFlagEqualTo("0");
        if(siteid!=null){
            c.andSiteEqualTo(siteid);
        }
        if(ebayAccount!=null){
            c.andEbayaccountEqualTo(ebayAccount);
        }
        return this.listingDataTaskMapper.selectByExampleWithBLOBs(tde);
    }

    /**
     * 定时任务执行ＳＱＬ
     * @return
     */
    @Override
    public List<ListingDataTask> selectByTimerTaskflag(){
        ListingDataTaskExample tde = new ListingDataTaskExample();
        ListingDataTaskExample.Criteria c = tde.createCriteria();
        c.andTaskFlagEqualTo("0");
        return this.listingDataTaskMapper.selectByExampleWithBLOBs(tde);
    }
}
