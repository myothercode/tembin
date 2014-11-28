package com.base.utils.scheduleother.dorun;

import com.base.database.trading.mapper.TradingTimerListingMapper;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.scheduleabout.BaseScheduledClass;
import com.base.utils.scheduleabout.MainTask;
import com.base.utils.scheduleabout.Scheduledable;
import com.base.utils.scheduleother.StaticParam;
import com.inventory.service.IItemInventory;
import org.apache.log4j.Logger;

/**
 * Created by Administrtor on 2014/8/29.
 * 获取库存数据
 */
public class InventoryTaskRun implements ScheduleOtherBase {
    static Logger logger = Logger.getLogger(InventoryTaskRun.class);

    @Override
    public String stype() {
        return StaticParam.INVENTORY_CHECK_SC_TAKE;
    }

    @Override
    public Integer cyclesTime() {
        return 30;
    }

    @Override
    public void run() {
        IItemInventory iitem=ApplicationContextUtil.getBean(IItemInventory.class);
        iitem.getChuKouYiInventory();
        iitem.getDeShiFangInventory();
        iitem.getSiHaiYouInventory();
    }
}
