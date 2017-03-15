package com.example.qf.manager.Model.Bean;

import java.util.List;

/**
 * Created by qf on 2016/9/10.
 */
public class Year {
    private String year_name;
    private List<Month> monthList;

    public Year() {
    }

    public Year(String year_name, List<Month> monthList) {
        this.year_name = year_name;
        this.monthList = monthList;
    }

    @Override
    public String toString() {
        return "Year{" +
                "year_name='" + year_name + '\'' +
                ", monthList=" + monthList +
                '}';
    }

    public String getYear_name() {
        return year_name;
    }

    public void setYear_name(String year_name) {
        this.year_name = year_name;
    }

    public List<Month> getMonthList() {
        return monthList;
    }

    public void setMonthList(List<Month> monthList) {
        this.monthList = monthList;
    }
}
