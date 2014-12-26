package com.complement.service.impl;

import com.base.database.customtrading.mapper.AutoComplementMapper;
import com.base.database.customtrading.mapper.SystemLogQueryMapper;
import com.base.database.inventory.model.ItemInventory;
import com.base.database.inventory.model.ShihaiyouInventory;
import com.base.database.task.model.TaskComplement;
import com.base.database.trading.mapper.TradingAutoComplementMapper;
import com.base.database.trading.mapper.TradingListingDataMapper;
import com.base.database.trading.model.*;
import com.base.domains.querypojos.SystemLogQuery;
import com.base.mybatis.page.Page;
import com.complement.service.ITradingAutoComplement;
import com.complement.service.ITradingInventoryComplement;
import com.complement.service.ITradingSetComplement;
import com.inventory.service.IItemInventory;
import com.task.service.ITaskComplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/12/11.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TradingAutoComplementImpl implements com.complement.service.ITradingAutoComplement {

    @Autowired
    private TradingAutoComplementMapper tradingAutoComplementMapper;
    @Autowired
    private AutoComplementMapper autoComplementMapper;
    @Autowired
    private SystemLogQueryMapper systemLogQueryMapper;
    @Autowired
    private ITradingSetComplement iTradingSetComplement;
    @Autowired
    private TradingListingDataMapper tradingListingDataMapper;
    @Autowired
    private ITaskComplement iTaskComplement;
    @Autowired
    private ITradingInventoryComplement iTradingInventoryComplement;
    @Autowired
    private IItemInventory iItemInventory;
    @Override
    public List<TradingAutoComplement> selectByList(Map m, Page page){
        return this.autoComplementMapper.selectAutoComplementList(m,page);
    }

    @Override
    public void saveAutoComplement(TradingAutoComplement tradingAutoComplement){
        if(tradingAutoComplement.getId()!=null){
            this.tradingAutoComplementMapper.updateByPrimaryKeySelective(tradingAutoComplement);
        }else{
            this.tradingAutoComplementMapper.insertSelective(tradingAutoComplement);
        }
    }
    @Override
    public int delAutoComplement(long id){
        return this.tradingAutoComplementMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void delByEbayId(Long ebayId){
        TradingAutoComplementExample tace = new TradingAutoComplementExample();
        tace.createCriteria().andEbayIdEqualTo(ebayId);
        this.tradingAutoComplementMapper.deleteByExample(tace);
    }

    @Override
    public TradingAutoComplement selectById(long id){
        return this.tradingAutoComplementMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<SystemLogQuery> selectLogList(Map m, Page page){
        return this.systemLogQueryMapper.selectLogList(m,page);
    }

    @Override
    public List<TradingAutoComplement> selectByEbayAccount(String ebayAccount){
        TradingAutoComplementExample tace = new TradingAutoComplementExample();
        tace.createCriteria().andEbayAccountEqualTo(ebayAccount).andCheckFlagEqualTo("0");
        return this.tradingAutoComplementMapper.selectByExample(tace);
    }

    @Override
    public void checkAutoComplementType(TradingListingData tradingListingData,String token,String siteid){
        String ebayAccount = tradingListingData.getEbayAccount();
        boolean isFlag = true;
        TradingSetComplement tsc = this.iTradingSetComplement.selectByEbayAccount(ebayAccount);
        if(tsc!=null) {
            if (tsc.getComplementType().equals("1")) {//该ebay账户设置成自动补数
                List<TradingAutoComplement> litac = this.selectByEbayAccount(tradingListingData.getEbayAccount());
                for (TradingAutoComplement tac : litac) {//排除设置好的规则，如果有满足条件的商品就不再做库存补冲
                    if(tac!=null) {
                        if (tac.getAutoType().equals("1") && tac.getSkuKey().equals(tradingListingData.getSku())) {//设置规则中，ＳＫＵ相等时
                            isFlag = false;
                        } else if (tac.getAutoType().equals("2") && tradingListingData.getSku().indexOf(tac.getSkuKey()) > 0) {//设置规则中，ＳＫＵ开头相等时
                            isFlag = false;
                        }
                    }
                }
                TradingListingDataExample tlde = new TradingListingDataExample();
                tlde.createCriteria().andItemIdEqualTo(tradingListingData.getItemId());
                List<TradingListingData> litl = tradingListingDataMapper.selectByExample(tlde);
                if (litl != null && litl.size() > 0) {
                    TradingListingData oldtd = litl.get(0);
                    if (oldtd.getQuantity() > tradingListingData.getQuantity() && tradingListingData.getIsFlag().equals("0") && isFlag) {
                        TaskComplement tc = new TaskComplement();
                        tc.setToken(token);
                        tc.setSite(siteid);
                        tc.setTaskFlag("0");
                        tc.setRepValue(oldtd.getQuantity() + "");
                        tc.setCreateDate(new Date());
                        tc.setItemId(tradingListingData.getItemId());
                        tc.setOldValue(tradingListingData.getQuantity() + "");
                        tc.setEbayAccount(tradingListingData.getEbayAccount());
                        tc.setDataType("1");
                        iTaskComplement.saveTaskComplement(tc);
                    }
                }
            } else if (tsc.getComplementType().equals("2")) {//该ebay账号设置成库存补数
                TradingInventoryComplement tic = this.iTradingInventoryComplement.selectBySkuOrEbayAccount(tradingListingData.getSku(), tradingListingData.getEbayAccount());
                if (tic != null) {//如果配置的规则不为空，那么就按照库存中的数字来调整商品的数量
                    List<TradingInventoryComplementMore> liticm = this.iTradingInventoryComplement.slectByParentId(tic.getId());
                    int countNumber = 0;
                    if(liticm!=null&&liticm.size()>0) {
                        for (TradingInventoryComplementMore ticm : liticm) {
                            //先检查出口易，第四方有没有这样的ＳＫＵ
                            List<ItemInventory> liii = this.iItemInventory.selectBySku(ticm.getSkuValue());
                            if (liii != null && liii.size() > 0) {
                                for (ItemInventory ii : liii) {
                                    countNumber += ii.getAvailStock();
                                }
                            }
                            //检查四海邮ＳＫU
                            List<ShihaiyouInventory> lishy = this.iItemInventory.selectShiHaiYouByBySku(ticm.getSkuValue());
                            if (lishy != null && lishy.size() > 0) {
                                for (ShihaiyouInventory sh : lishy) {
                                    countNumber += sh.getQuantity();
                                }
                            }
                        }
                    }
                    if (countNumber != 0 && countNumber != tradingListingData.getQuantity()) {
                        TaskComplement tc = new TaskComplement();
                        tc.setToken(token);
                        tc.setSite(siteid);
                        tc.setTaskFlag("0");
                        tc.setRepValue(countNumber + "");
                        tc.setCreateDate(new Date());
                        tc.setItemId(tradingListingData.getItemId());
                        tc.setOldValue(tradingListingData.getQuantity() + "");
                        tc.setEbayAccount(tradingListingData.getEbayAccount());
                        tc.setDataType("2");
                        iTaskComplement.saveTaskComplement(tc);
                    }
                }
            }//如果ebay账号未设置补数方式，那么将不会补数，由用户手动调整在线数量
        }

    }
}
