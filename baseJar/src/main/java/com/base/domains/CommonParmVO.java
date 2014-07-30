package com.base.domains;

import com.base.mybatis.page.PageJsonBean;

/**
 * Created by wula on 2014/7/29.
 * 用于contrallor接收参数的通用VO
 */
public class CommonParmVO {
    private PageJsonBean jsonBean;

    public PageJsonBean getJsonBean() {
        return jsonBean;
    }

    public void setJsonBean(PageJsonBean jsonBean) {
        this.jsonBean = jsonBean;
    }
}
