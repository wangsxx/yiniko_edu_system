package com.wangs.user.entity.vo;

/**
 * @File : RegisterVo.java
 * @Time : 2021/3/31 10:45
 * @Author: Wangs
 * @Decs :
 */
public class RegisterVo {
    private String nickname;
    private String mobile;
    private String password;
    private String code;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
