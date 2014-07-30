package com.trading.service.impl;

import com.base.database.customtrading.mapper.ReturnpolicyMapper;
import com.base.database.trading.mapper.TradingReturnpolicyMapper;
import com.base.database.trading.model.TradingReturnpolicy;
import com.base.domains.querypojos.ReturnpolicyQuery;
import com.base.utils.common.ConvertPOJOUtil;
import com.base.xmlpojo.trading.addproduct.ReturnPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 退货政策
 * Created by cz on 2014/7/22.
 */
@Service
public class TradingReturnpolicyImpl implements com.trading.service.ITradingReturnpolicy {

    @Autowired
    private TradingReturnpolicyMapper tradingReturnpolicyMapper;

    @Autowired
    private ReturnpolicyMapper returnpolicyMapper;
    @Override
    public void saveTradingReturnpolicy(TradingReturnpolicy tradingReturnpolicy){
        if(tradingReturnpolicy.getId()==null){
            this.tradingReturnpolicyMapper.insert(tradingReturnpolicy);
        }else{
            tradingReturnpolicy.setUuid(null);
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
        //公共属性值
        //tr.setId(123l);
        tr.setCreateUser(123l);
        tr.setCreateTime(new Date());
        tr.setUuid(UUID.randomUUID().toString().replace("-",""));

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
    public List<ReturnpolicyQuery> selectByReturnpolicyList(Map map){
        return this.returnpolicyMapper.selectByReturnpolicyList(map);
    }

}
