package com.xms.bean;

/**
 * Created by dell on 2017/7/26.
 */

public class UserBean {
    public String getMOBILE() {
        return MOBILE;
    }

    public void setMOBILE(String MOBILE) {
        this.MOBILE = MOBILE;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }

    public UserBean(String MOBILE, String PASSWORD) {
        this.MOBILE = MOBILE;
        this.PASSWORD = PASSWORD;
    }

    private String MOBILE;
    private String PASSWORD;
}
