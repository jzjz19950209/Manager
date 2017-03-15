package com.example.qf.manager;

import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.qf.manager.Model.Bean.User_data;
import com.example.qf.manager.View.Adapter.DetailAdapter;
import com.example.qf.manager.View.MyListView;

import java.util.ArrayList;
import java.util.List;

public class detail_Activity extends AppCompatActivity {
    private List<User_data> user_data;
    private List<User_data> data;
    private MyListView myListView;
    private DetailAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_);
        user_data = (List<User_data>) getIntent().getSerializableExtra("user_data");
        myListView= (MyListView) findViewById(R.id.detail_listview);
        FilterData();
        adapter=new DetailAdapter(data,this);
        myListView.setAdapter(adapter);
    }
    public void FilterData(){
        data=new ArrayList<>();
        for (int i = 0; i < user_data.size(); i++) {
            String time = user_data.get(i).getTime();
            if (UserMethodUtils.currentDate.equals(time)){
                data.add(user_data.get(i));
            }
        }
    }
}
