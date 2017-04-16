package com.example.qf.manager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.qf.manager.Model.Bean.User_data;
import com.example.qf.manager.Presenter.FindUseTypePresenter;
import com.example.qf.manager.View.IEditActivityView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class search_Activity extends AppCompatActivity implements IEditActivityView{
    private DatePicker datePicker1,datePicker2;
    private RadioGroup radioGroup;
    private Button button;
    private int searchType =2;
    private Date date1,date2;
    private Spinner spinner_search;
    private ArrayAdapter<String> adapter;
    private SimpleDateFormat sdf;
    private FindUseTypePresenter findUseTypePresenter=new FindUseTypePresenter(this);
    private String useType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_);
        datePicker1= (DatePicker) findViewById(R.id.datePicker1);
        datePicker2= (DatePicker) findViewById(R.id.datePicker2);
        radioGroup= (RadioGroup) findViewById(R.id.radioGroup);
        button= (Button) findViewById(R.id.search);
        spinner_search= (Spinner) findViewById(R.id.spinnerSearch);
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
        findUseTypePresenter.initData(2);
        radioGroup.check(R.id.expend);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.income:
                        searchType=1;
                        findUseTypePresenter.initData(1);
                        break;
                    case R.id.expend:
                        searchType=2;
                        findUseTypePresenter.initData(2);
                        break;
                    case R.id.sum:
                        searchType=3;
                        findUseTypePresenter.initData(3);
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
                UserMethodUtils.search_dataList = UserMethodUtils.searchDataByTimeAndUseType(UserMethodUtils.sql, UserMethodUtils.currentUserName, date1, date2,useType);
                Intent intent = new Intent(search_Activity.this, search_result_Activity.class);
                intent.putExtra("searchType",searchType);
                intent.putExtra("startDate",sdf.format(date1));
                intent.putExtra("endDate",sdf.format(date2));
                startActivity(intent);
            }
        });

    }

    @Override
    public void UpdateData(List<String> list) {
        List<String> datas = new ArrayList<>();
        if (list==null){
            datas.add("全部");
        }else {

            datas.add("全部");
            datas.addAll(list);
        }
        adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,datas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_search.setAdapter(adapter);
        spinner_search.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = adapter.getItem(position);
                useType = item;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
