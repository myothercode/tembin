package com.base.utils.scheduleabout;

import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.trading.service.ITradingDataDictionary;
import org.apache.log4j.Logger;
import org.springframework.core.Ordered;

/**
 * Created by Administrtor on 2014/9/3.
 * 数据字典初始化
 */
//@Component
public class DictInit implements Initable {
    static Logger logger = Logger.getLogger(DictInit.class);
    /*@Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("-----y----------xxxxxxxxxxxxxxxxxxxxxx------------------");
    }*/
    @Override
    public void init() {
        try {
            Object o=ApplicationContextUtil.getBean(ITradingDataDictionary.class);
            if(o==null){MainTask.isDongInitMethod="no";logger.error("ApplicationContext还没有准备好！");return;}
            ITradingDataDictionary d = (ITradingDataDictionary)o;
            d.queryDictAll();
            d.queryPublicDictAll();
        } catch (Exception e) {
            MainTask.isDongInitMethod="no";
            logger.error("加载数据字典缓存失败！",e);
        }
        logger.info("加载数据字典成功!");

    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

    /*@Override
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        System.out.println("-----y----------before------------------"+s);
        return o;
    }

    @Override
    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        System.out.println("-----y----------after------------------"+s);
        return o;
    }*/
}
