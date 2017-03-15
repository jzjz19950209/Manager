package com.example.qf.manager.Model.Bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by qf on 2016/10/8.
 */
public class User extends BmobObject {
    private String userName;
    private String userPassword;
    private String phoneNum;
    public User(){}
    public User(String userName, String userPassword) {
        this.userName = userName;
        this.userPassword = userPassword;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
