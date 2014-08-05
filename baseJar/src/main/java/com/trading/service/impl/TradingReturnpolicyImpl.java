package com.trading.service.impl;

import com.base.database.customtrading.mapper.ReturnpolicyMapper;
import com.base.database.trading.mapper.TradingReturnpolicyMapper;
import com.base.database.trading.model.TradingReturnpolicy;
import com.base.domains.querypojos.ReturnpolicyQuery;
import com.base.mybatis.page.Page;
import com.base.utils.common.ConvertPOJOUtil;
import com.base.utils.common.ObjectUtils;
import com.base.utils.exception.Asserts;
import com.base.xmlpojo.trading.addproduct.ReturnPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * 退货政策
 * Created by lq on 2014/7/29.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TradingReturnpolicyImpl implements com.trading.service.ITradingReturnpolicy {
    @Autowired
    private TradingReturnpolicyMapper tradingReturnpolicyMapper;

    @Autowired
    private ReturnpolicyMapper returnpolicyMapper;
    @Override
    public void saveTradingReturnpolicy(TradingReturnpolicy tradingReturnpolicy) throws Exception {
        if(tradingReturnpolicy.getId()==null){
            ObjectUtils.toInitPojoForInsert(tradingReturnpolicy);
            this.tradingReturnpolicyMapper.insert(tradingReturnpolicy);
        }else{
            TradingReturnpolicy t=tradingReturnpolicyMapper.selectByPrimaryKey(tradingReturnpolicy.getId());
            Asserts.assertTrue(t != null && t.getCreateUser() != null, "没有找到记录或者记录创建者为空");
            ObjectUtils.valiUpdate(t.getCreateUser(),TradingReturnpolicyMapper.class,tradingReturnpolicy.getId());
            this.tradingReturnpolicyMapper.updateByPrimaryKeySelective(tradingReturnpolicy);
        }
    }

    /**
     * xml POJO对象转换成数据库操作对象
     * @param pojo
     * @return
     */
    @Override
    public TradingReturnpolicy toDAOPojo(ReturnPolicy pojo) throws Exception {
        TradingReturnpolicy tr = new TradingReturnpolicy();

        try {
            ConvertPOJOUtil.convert(tr,pojo);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        /*tr.setDescription(pojo.getDescription());
        tr.setExtendedholidayreturns(pojo.getExtendedHolidayReturns().toString().equals("true")?"1":"0");
        tr.setRefundoption(pojo.getRefundOption());
        tr.setRestockingfeevalueoption(pojo.getRestockingFeeValueOption());
        tr.setReturnsacceptedoption(pojo.getReturnsAcceptedOption());
        tr.setReturnswithinoption(pojo.getReturnsWithinOption());;
        tr.setShippingcostpaidbyoption(pojo.getShippingCostPaidByOption());
        tr.setWarrantydurationoption(pojo.getWarrantyDurationOption());
        tr.setWarrantyofferedoption(pojo.getWarrantyOfferedOption());
        tr.setWarrantytypeoption(pojo.getWarrantyTypeOption());
        tr.setEan(pojo.getEAN());*/

        return tr;
    }
    @Override
    public List<ReturnpolicyQuery> selectByReturnpolicyList(Map map,Page page){
        return this.returnpolicyMapper.selectByReturnpolicyList(map,page);
    }

    @Override
    public List<ReturnpolicyQuery> selectByReturnpolicyList(Map map){
        Page page=new Page();
        page.setPageSize(10);
        page.setPageSize(1);
        return this.returnpolicyMapper.selectByReturnpolicyList(map,page);
    }

    @Override
    public TradingReturnpolicy selectById(Long id){
        return this.tradingReturnpolicyMapper.selectByPrimaryKey(id);
    }
}
