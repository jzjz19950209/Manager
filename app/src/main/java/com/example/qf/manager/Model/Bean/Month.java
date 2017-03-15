package com.example.qf.manager.Model.Bean;

import java.util.List;

/**
 * Created by qf on 2016/9/10.
 */
public class Month {
    private String month_name;
    private List<Day> dayList;

    public Month() {
    }

    public Month(String month_name, List<Day> dayList) {
        this.month_name = month_name;
        this.dayList = dayList;
    }

    @Override
    public String toString() {
        return "Month{" +
                "month_name='" + month_name + '\'' +
                ", dayList=" + dayList +
                '}';
    }

    public String getMonth_name() {
        return month_name;
    }

    public void setMonth_name(String month_name) {
        this.month_name = month_name;
    }

    public List<Day> getDayList() {
        return dayList;
    }

    public void setDayList(List<Day> dayList) {
        this.dayList = dayList;
    }
}
