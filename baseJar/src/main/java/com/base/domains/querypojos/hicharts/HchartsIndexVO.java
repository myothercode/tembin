package com.base.domains.querypojos.hicharts;

import java.util.List;

/**
 * Created by Administrator on 2014/11/7.
 * Hcharts数据索需要的类
 */
public  class HchartsIndexVO {
    private String type;
    private String name;
    private List<?> data;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }
}
