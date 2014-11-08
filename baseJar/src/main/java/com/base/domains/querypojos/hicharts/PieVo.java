package com.base.domains.querypojos.hicharts;

/**
 * Created by Administrator on 2014/11/7.
 * 饼图的vo
 */
public class PieVo {
    private String name;
    private Double y;
    private Boolean sliced;
    private Boolean selected;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Boolean getSliced() {
        return sliced;
    }

    public void setSliced(Boolean sliced) {
        this.sliced = sliced;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
}
