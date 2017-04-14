package com.example.qf.manager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.qf.manager.Model.Bean.User_data;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class search_Activity extends AppCompatActivity {
    private DatePicker datePicker1,datePicker2;
    private RadioGroup radioGroup;
    private Button button;
    private int searchType =2;
    private Date date1,date2;
    private SimpleDateFormat sdf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_);
        datePicker1= (DatePicker) findViewById(R.id.datePicker1);
        datePicker2= (DatePicker) findViewById(R.id.datePicker2);
        radioGroup= (RadioGroup) findViewById(R.id.radioGroup);
        button= (Button) findViewById(R.id.search);
        Calendar calendar=Calendar.getInstance();
        date1=new Date();
        date2=new Date();
        sdf=new SimpleDateFormat("yyyy-MM-dd");
        datePicker1.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date1=new Date(year-1900,monthOfYear,dayOfMonth);
            }
        });
        datePicker2.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date2=new Date(year-1900,monthOfYear,dayOfMonth);
            }
        });

        radioGroup.check(R.id.expend);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.income:
                        searchType=1;
                        break;
                    case R.id.expend:
                        searchType=2;
                        break;
                    case R.id.sum:
                        searchType=3;
                        break;
                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(date2.before(date1)){
                    Toast.makeText(search_Activity.this, "开始时间不能早于结束时间！", Toast.LENGTH_SHORT).show();
                    return ;
                }
                UserMethodUtils.search_dataList = UserMethodUtils.searchDataByTime(UserMethodUtils.sql, UserMethodUtils.currentUserName, date1, date2);
                Intent intent = new Intent(search_Activity.this, search_result_Activity.class);
                intent.putExtra("searchType",searchType);
                intent.putExtra("startDate",sdf.format(date1));
                intent.putExtra("endDate",sdf.format(date2));
                startActivity(intent);
            }
        });
    }
}
