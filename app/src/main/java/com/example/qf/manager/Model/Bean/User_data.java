package com.example.qf.manager.Model.Bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by qf on 2016/10/10.
 */
public class User_data extends BmobObject {
    private int id;
    private String userName;
    private String time;
    private String notes;
    private double money;
    private int isIncome;
    public User_data(){}


    public User_data(int id, String userName, String time, String notes, double money, int isIncome) {
        this.id = id;
        this.userName = userName;
        this.time = time;
        this.notes = notes;
        this.money = money;
        this.isIncome = isIncome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int isIncome() {
        return isIncome;
    }

    public void setIncome(int income) {
        isIncome = income;
    }
}
