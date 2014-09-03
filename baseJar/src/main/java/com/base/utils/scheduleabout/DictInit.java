package com.base.utils.scheduleabout;

import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.trading.service.ITradingDataDictionary;
import org.springframework.core.Ordered;

/**
 * Created by Administrtor on 2014/9/3.
 * 数据字典初始化
 */
//@Component
public class DictInit implements Initable {
    /*@Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("-----y----------xxxxxxxxxxxxxxxxxxxxxx------------------");
    }*/
    @Override
    public void init() {
        ITradingDataDictionary d = (ITradingDataDictionary) ApplicationContextUtil.getBean(ITradingDataDictionary.class);
        d.queryDictAll();
        d.queryPublicDictAll();

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
