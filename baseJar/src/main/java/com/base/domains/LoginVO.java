package com.base.domains;

/**
 * Created by Administrtor on 2014/7/24.
 * 登陆时信息VO
 */
public class LoginVO {
    private String loginId;//用户id
    private String password;//密码
    private String enpassword;//加密后的密码
    private String capcode;//图片验证码
    private String email;//邮箱

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCapcode() {
        return capcode;
    }

    public void setCapcode(String capcode) {
        this.capcode = capcode;
    }

    public String getEnpassword() {
        return enpassword;
    }

    public void setEnpassword(String enpassword) {
        this.enpassword = enpassword;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
