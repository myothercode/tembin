package com.base.domains.userinfo;

import com.base.domains.RoleVO;

import java.util.List;

/**
 * Created by Administrator on 2014/9/23.
 * 添加用户的vo
 */
public class AddSubUserVO {
    private String name;
    private String loginID;
    private String email;
    private String address;
    private String phone;
    private Long userID;
    private List<RoleVO> roles;
    private List<UsercontrollerEbayAccountExtend> ebays;


    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoginID() {
        return loginID;
    }

    public void setLoginID(String loginID) {
        this.loginID = loginID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<RoleVO> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleVO> roles) {
        this.roles = roles;
    }

    public List<UsercontrollerEbayAccountExtend> getEbays() {
        return ebays;
    }

    public void setEbays(List<UsercontrollerEbayAccountExtend> ebays) {
        this.ebays = ebays;
    }
}
