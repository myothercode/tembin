package com.base.domains;

import com.base.mybatis.page.PageJsonBean;

/**
 * Created by wula on 2014/7/29.
 * 用于contrallor接收参数的通用VO
 */
public class CommonParmVO {
    private Long id;
    private String strV1;
    private String strV2;
    private String strV3;


    public String getStrV3() {
        return strV3;
    }

    public void setStrV3(String strV3) {
        this.strV3 = strV3;
    }

    public String getStrV2() {
        return strV2;
    }

    public void setStrV2(String strV2) {
        this.strV2 = strV2;
    }

    public String getStrV1() {
        return strV1;
    }

    public void setStrV1(String strV1) {
        this.strV1 = strV1;
    }

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
