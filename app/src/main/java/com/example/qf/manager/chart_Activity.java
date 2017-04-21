package com.example.qf.manager;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.qf.manager.Model.Bean.User_data;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class chart_Activity extends AppCompatActivity{
    private BarChart mChart;
    private List<User_data> list;
    private List<String> useType_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_);
        mChart= (BarChart) findViewById(R.id.barChart);
        initBarChart();


//        mChart.setMaxVisibleValueCount(60);
//        mChart.setPinchZoom(false);
//        mChart.setDrawBarShadow(false);
//        mChart.setDrawGridBackground(false);
//
//        XAxis xAxis = mChart.getXAxis();
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setDrawGridLines(false);
//
//        mChart.getAxisLeft().setDrawGridLines(false);
//        mChart.animateY(2500);
//        mChart.getLegend().setEnabled(false);


    }
    private void initBarChart() {
        ArrayList<BarEntry> yVals = new ArrayList<>();//Y轴方向第一组数组
        ArrayList<BarEntry> yVals2 = new ArrayList<>();//Y轴方向第二组数组
        ArrayList<BarEntry> yVals3 = new ArrayList<>();//Y轴方向第三组数组
        ArrayList<String> xVals = new ArrayList<>();//X轴数据
        useType_list=new ArrayList<>();
        list=new ArrayList<>();
        list.addAll(UserMethodUtils.search_dataList);
        String useType;
        for(User_data user_data:list){
            if(user_data.isIncome()==0) {
                useType = user_data.getUseType();
                if (!useType_list.contains(useType)) {
                    useType_list.add(useType);
                }
            }
        }
        for(int i=0;i<useType_list.size();i++){
            xVals.add(useType_list.get(i));
            double sum = 0;
            for(User_data user_data:list){
                if(user_data.isIncome()==0) {
                    if (user_data.getUseType().equals(useType_list.get(i))) {
                        sum += user_data.getMoney();
                    }
                }
            }
            yVals.add(new BarEntry(((float) sum),i));
        }
//        for (int i = 0; i < 12; i++) {//添加数据源
//            xVals.add((i + 1) + "月");
//            yVals.add(new BarEntry(random.nextInt(10000), i));
//            yVals2.add(new BarEntry(random.nextInt(10000), i));
//            yVals3.add(new BarEntry(random.nextInt(10000), i));
//
//        }

        BarDataSet barDataSet = new BarDataSet(yVals, getIntent().getStringExtra("startDate")+"~"+getIntent().getStringExtra("endDate")+"支出");
        barDataSet.setColor(getResources().getColor(R.color.colorjz));//设置第一组数据颜色

//        BarDataSet barDataSet2 = new BarDataSet(yVals2, "小花每月支出");
//        barDataSet2.setColor(Color.GREEN);//设置第二组数据颜色
//
//        BarDataSet barDataSet3 = new BarDataSet(yVals3, "小蔡每月支出");
//        barDataSet3.setColor(Color.YELLOW);//设置第三组数据颜色

        ArrayList<BarDataSet> threebardata = new ArrayList<>();//IBarDataSet 接口很关键，是添加多组数据的关键结构，LineChart也是可以采用对应的接口类，也可以添加多组数据
        threebardata.add(barDataSet);
//        threebardata.add(barDataSet2);
//        threebardata.add(barDataSet3);

        BarData bardata = new BarData(xVals, threebardata);
        mChart.setData(bardata);
        mChart.getLegend().setPosition(Legend.LegendPosition.ABOVE_CHART_LEFT);//设置注解的位置在左上方
        mChart.getLegend().setForm(Legend.LegendForm.CIRCLE);//这是左边显示小图标的形状

        mChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);//设置X轴的位置
        mChart.getXAxis().setDrawGridLines(true);//不显示网格

        mChart.getAxisRight().setEnabled(false);//右侧不显示Y轴
        mChart.getAxisLeft().setAxisMinValue(0.0f);//设置Y轴显示最小值，不然0下面会有空隙
        mChart.getAxisLeft().setDrawGridLines(true);//不设置Y轴网格

        mChart.setDescription("");//设置描述
        mChart.setDescriptionTextSize(20.f);//设置描述字体
        mChart.animateXY(1000, 2000);//设置动画
    }
}
