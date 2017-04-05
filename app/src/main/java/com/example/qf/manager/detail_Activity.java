package com.example.qf.manager;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_);
        //user_data = (List<User_data>) getIntent().getSerializableExtra("user_data");
        myListView= (MyListView) findViewById(R.id.detail_listview);
        imageView= (ImageView) findViewById(R.id.noComment);
        data=new ArrayList<>();
        FilterData();
        adapter=new DetailAdapter(data,this);
        myListView.setAdapter(adapter);
        myListView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.add(Menu.NONE,1,1,"修改");
                menu.add(Menu.NONE,2,1,"删除");
            }
        });

    }
    public void FilterData(){
        user_data = UserMethodUtils.user_dataList;
        data.clear();
        for (int i = 0; i < user_data.size(); i++) {
            String time = user_data.get(i).getTime();
            if (UserMethodUtils.currentDate.equals(time)){
                data.add(user_data.get(i));
            }
        }
    }

//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        super.onCreateContextMenu(menu, v, menuInfo);
//        Log.d("jzjz", "onCreateContextMenu: ");
//        if(v==myListView){
//            Log.d("jzjz", "onCreateContextMenu: ");
//            menu.add(Menu.NONE,1,1,"修改");
//            menu.add(Menu.NONE,1,1,"删除");
//        }
//    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = menuInfo.position;
        View view = myListView.getChildAt(position);
        String id = ((TextView) view.findViewById(R.id._id)).getText().toString();
        switch (item.getItemId()){
            case 1:
                String money = ((TextView) view.findViewById(R.id.money)).getText().toString();
                String date = ((TextView) view.findViewById(R.id.date)).getText().toString();
                String note = ((TextView) view.findViewById(R.id.note)).getText().toString();
                String type = ((TextView) view.findViewById(R.id.type)).getText().toString();
                Intent intent=new Intent(detail_Activity.this,edit_Activity.class);
                intent.putExtra("money",money);
                intent.putExtra("time",date);
                intent.putExtra("note",note);
                intent.putExtra("type",type);
                intent.putExtra("id",id);
                intent.putExtra("useType","alter");
                startActivity(intent);
                break;
            case 2:
                UserMethodUtils.DeleteData(UserMethodUtils.sql,Integer.parseInt(id),UserMethodUtils.currentUserName);
                updateData();
                if(data.size()==0){
                    imageView.setVisibility(View.VISIBLE);
                }
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateData();
    }
    private void updateData(){
        list_Activity.instance.findLocalPresenter.initData();
        FilterData();
        adapter.notifyDataSetChanged();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterForContextMenu(myListView);
    }
}
