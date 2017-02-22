package com.example.qf.manager.drawerlayout;

/**
 * Created by qf on 2016/9/10.
 */
public class Day {
    private String day_name;
    public Day(String day_name) {
        this.day_name = day_name;
    }

    public Day() {
    }

    public String getDay_name() {
        return day_name;
    }

    @Override
    public String toString() {
        return "Day{" +
                "day_name='" + day_name + '\'' +
                '}';
    }

    public void setDay_name(String day_name) {
        this.day_name = day_name;
    }
}
