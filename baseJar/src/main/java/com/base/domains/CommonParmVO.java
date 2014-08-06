package com.base.domains;

import com.base.mybatis.page.PageJsonBean;

/**
 * Created by wula on 2014/7/29.
 * 用于contrallor接收参数的通用VO
 */
public class CommonParmVO {
    private Long id;

    private PageJsonBean jsonBean;

    public PageJsonBean getJsonBean() {
        return jsonBean;
    }

    public void setJsonBean(PageJsonBean jsonBean) {
        this.jsonBean = jsonBean;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
