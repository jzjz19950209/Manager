package com.example.qf.manager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qf.manager.Model.Bean.User_data;
import com.example.qf.manager.View.Adapter.SearchAdapter;
import com.example.qf.manager.View.MyListView;

import java.util.ArrayList;
import java.util.List;

public class search_result_Activity extends AppCompatActivity {
    private TextView time,typeName,search_sum;
    private MyListView myListView;
    private String startDate,endDate;
    private int searchType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result_);
        time = (TextView) findViewById(R.id.search_time);
        typeName= (TextView) findViewById(R.id.typeName);
        search_sum= (TextView) findViewById(R.id.sum);
        myListView = (MyListView) findViewById(R.id.listView_search);
        searchType = getIntent().getIntExtra("searchType",1);
        startDate=getIntent().getStringExtra("startDate");
        endDate=getIntent().getStringExtra("endDate");
        initData();
    }
    private void initData(){
        time.setText(startDate+" 至 "+endDate);
        List<User_data> list = UserMethodUtils.search_dataList;
        if(list==null){
            return;
        }
        List<User_data> list_temp =new ArrayList<>();
        double sum=0;
        int isIncome;
        double money;
        for(User_data user_data:list){
            isIncome = user_data.isIncome();
            money = user_data.getMoney();
            switch (searchType){
                case 1:
                    if (isIncome==1){
                        sum+=money;
                        list_temp.add(user_data);
                    }
                    break;
                case 2:
                    if(isIncome==0){
                        sum+=money;
                        list_temp.add(user_data);
                    }
                    break;
                case 3:
                    if (isIncome==1){
                        sum+=money;
                    }else {
                        sum-=money;
                    }
                    list_temp.add(user_data);
                    break;
                default:
                    break;
            }
        }
        final SearchAdapter adapter=new SearchAdapter(this,list_temp);
        myListView.setAdapter(adapter);


        search_sum.setText(""+sum);
        switch (searchType){
            case 1:
                typeName.setText("总收入");
                break;
            case 2:
                typeName.setText("总支出");
                break;
            case 3:
                typeName.setText("总金额");
                break;
            default:
                break;
        }
    }
}
