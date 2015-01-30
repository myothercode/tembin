package com.inventory.service.impl;

import com.base.database.inventory.mapper.UserInventorySetMapper;
import com.base.database.inventory.model.UserInventorySet;
import com.base.database.inventory.model.UserInventorySetExample;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrtor on 2015/1/27.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserInventorySetImpl implements com.inventory.service.IUserInventorySet {
    static Logger logger = Logger.getLogger(UserInventorySetImpl.class);
    @Autowired
    private UserInventorySetMapper userInventorySetMapper;


    @Override
    public void saveUserInventorySet(UserInventorySet userInventorySet){
        if(userInventorySet.getId()==null){
            this.userInventorySetMapper.insertSelective(userInventorySet);
        }else{
            this.userInventorySetMapper.updateByPrimaryKeySelective(userInventorySet);
        }
    }

    @Override
    public List<UserInventorySet> selectByOrgIdList(long orgId){
        UserInventorySetExample ins = new UserInventorySetExample();
        ins.createCriteria().andOrgIdEqualTo(orgId);
        return this.userInventorySetMapper.selectByExample(ins);
    }

    @Override
    public UserInventorySet selectById(long id){
        return this.userInventorySetMapper.selectByPrimaryKey(id);
    }

    @Override
    public void deleteInventorySet(Long id){
        this.userInventorySetMapper.deleteByPrimaryKey(id);
    }

    /**
     * 查询同一仓库，同一用户名，同一公司是否有相同的数据
     * @param dataType
     * @param userName
     * @param orgId
     * @return
     */
    @Override
    public UserInventorySet selectDistinc(String dataType,String userName,Long orgId){
        UserInventorySetExample ins = new UserInventorySetExample();
        ins.createCriteria().andOrgIdEqualTo(orgId).andDataTypeEqualTo(dataType).andUserNameEqualTo(userName);
        List<UserInventorySet> liins = this.userInventorySetMapper.selectByExample(ins);
        if(liins!=null&&liins.size()>0){
            return liins.get(0);
        }else{
            return null;
        }
    }

}
