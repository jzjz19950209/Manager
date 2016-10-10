package com.example.qf.manager;

import cn.bmob.v3.BmobObject;

/**
 * Created by qf on 2016/10/10.
 */
public class User_data extends BmobObject {
    private String userName;
    private String time;
    private String notes;
    private double money;
    private boolean isIncome;
    public User_data(){}

    public User_data(String userName, double money, String notes, String time,boolean isIncome) {
        this.userName = userName;
        this.money = money;
        this.notes = notes;
        this.time = time;
        this.isIncome=isIncome;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public boolean isIncome() {
        return isIncome;
    }

    public void setIncome(boolean income) {
        isIncome = income;
    }
}
