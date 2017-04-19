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
    private String useType;
    public User_data(){}

    public User_data(int id, String userName, String time, String notes, double money, int isIncome, String useType) {
        this.id = id;
        this.userName = userName;
        this.time = time;
        this.notes = notes;
        this.money = money;
        this.isIncome = isIncome;
        this.useType = useType;
    }

    public int getId() {
        return id;
    }

    public String getUseType() {
        return useType;
    }

    public void setUseType(String useType) {
        this.useType = useType;
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

    @Override
    public boolean equals(Object obj) {
//            if(obj==null){
//                return false;
//            }else {
//                if(this == obj){
//                    return true;
//                }
//                if (obj instanceof User_data) {
//                    User_data user_data = (User_data) obj;
//                    return  (user_data.id==this.id);
//                }
//            }
//            return false;
        return true;
    }

    @Override
    public String toString() {
        return "User_data{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", time='" + time + '\'' +
                ", notes='" + notes + '\'' +
                ", money=" + money +
                ", isIncome=" + isIncome +
                ", useType='" + useType + '\'' +
                '}';
    }
}
