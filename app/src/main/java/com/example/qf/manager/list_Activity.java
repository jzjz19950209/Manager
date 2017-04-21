package com.example.qf.manager;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.os.ParcelableCompatCreatorCallbacks;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.PopupWindowCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qf.manager.Model.Bean.User_data;
import com.example.qf.manager.Presenter.DownLoadPresenter;
import com.example.qf.manager.Presenter.FindLocalPresenter;
import com.example.qf.manager.View.Adapter.ContentAdapter;
import com.example.qf.manager.Model.Bean.Date;
import com.example.qf.manager.Model.Bean.Day;
import com.example.qf.manager.Model.Bean.Month;
import com.example.qf.manager.View.Adapter.MyTimeLineAdapter;
import com.example.qf.manager.View.IListActivityView;
import com.example.qf.manager.Model.Bean.Year;
import com.example.qf.manager.View.MyScrollView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import rx.Subscription;

public class list_Activity extends AppCompatActivity implements IListActivityView,PopupMenu.OnMenuItemClickListener{
    private DrawerLayout drawerlayout;
    private TextView title_p;
    private ExpandableListView lv;
    private String[] timeArr;
    private List<Date> dateList = new ArrayList<>();
    private ListView listView;
    public static list_Activity instance;
    private MyScrollView scrollView;
    private ContentAdapter adapter;
    private MyTimeLineAdapter myTimeLineAdapter;
    private ImageView noContent;
    private String tempTime;
    private int success,failure;
    private DownLoadPresenter downLoadPresenter=new DownLoadPresenter(this);
    public FindLocalPresenter findLocalPresenter=new FindLocalPresenter(this);
    List<String> yearName = new ArrayList<>();
    List<String> monthName = new ArrayList<>();
    List<String> dayName = new ArrayList<>();
    List<Year> yearList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        instance = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        title_p = (TextView) findViewById(R.id.title_p);
        scrollView= (MyScrollView) findViewById(R.id.scrollView);
        lv = (ExpandableListView) this.findViewById(R.id.lv);
        listView= (ListView) findViewById(R.id.notes);
        noContent = (ImageView) findViewById(R.id.noComment);
        //downLoadPresenter.initData();

        setSupportActionBar(toolbar);
        drawerlayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        setTitle();

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent to_edit = new Intent(list_Activity.this, edit_Activity.class);
                to_edit.putExtra("username", UserMethodUtils.currentUserName);
                to_edit.putExtra("useType","edit");
                startActivity(to_edit);
            }
        });
        scrollView.setOnScrollToBottomLintener(new MyScrollView.OnScrollToBottomListener() {
            @Override
            public void onScrollBottomListener(boolean isBottom) {
                if(isBottom){
                    fab.hide();

                }else {
                    fab.show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        init();
        findLocalPresenter.initData();
        title_p.setText(UserMethodUtils.getTime());
        if (UserMethodUtils.groupP!=-1&&UserMethodUtils.childP!=-1){
            myTimeLineAdapter.myCallBack.click(UserMethodUtils.groupP,UserMethodUtils.childP);
        }
        if (myTimeLineAdapter!=null){
            myTimeLineAdapter.notifyDataSetChanged();
        }

    }
    private void init(){
        dateList.clear();
        yearList.clear();
        yearName.clear();
        monthName.clear();
        dayName.clear();
    }
    private void setTitle() {
        //Intent intent = getIntent();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.main);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        //userName = intent.getStringExtra("extra_username");
        actionBar.setTitle(UserMethodUtils.currentUserName);
        actionBar.setIcon(R.mipmap.ic_launcher);
        actionBar.setSubtitle(UserMethodUtils.getTime());
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerlayout, toolbar, 0, 0);
        drawerlayout.addDrawerListener(toggle);
        toggle.syncState();

    }


    private void addDate(List<Date> list, final List<User_data> user_dataList) {
        String day, month, year;
        List<Day> dayList;
        List<Month> monthList;
        int index = 0;
        boolean bool_y = true, bool_m = true;
        if (list.size() != 0) {
            for (int i = 0; i < list.size(); i++) {
                day = list.get(i).getDay();
                month = list.get(i).getMonth();
                year = list.get(i).getYear();
                if (yearList.size() == 0) {
                    dayList = new ArrayList<>();
                    monthList = new ArrayList<>();
                    dayList.add(new Day(day));
                    monthList.add(new Month(month, dayList));
                    yearList.add(new Year(year, monthList));
                } else {
                    for (Year y : yearList) {
                        if (y.getYear_name().equals(year)) {
                            bool_y = false;
                        }
                    }
                    if (bool_y) { //如果年份不同执行
                        dayList = new ArrayList<>();
                        monthList = new ArrayList<>();
                        dayList.add(new Day(day));
                        monthList.add(new Month(month, dayList));
                        yearList.add(new Year(year, monthList));
                    } else {  //如果年份存在
                        for (int j = 0; j < yearList.size(); j++) {
                            if (yearList.get(j).getYear_name().equals(year)) {
                                for (int k = 0; k < yearList.get(j).getMonthList().size(); k++) {
                                    if (yearList.get(j).getMonthList().get(k).getMonth_name().equals(month)) {
                                        bool_m = false;
                                        index = k;
                                    }
                                }
                                if (bool_m) {//如果月份不存在执行
                                    dayList = new ArrayList<>();
                                    dayList.add(new Day(day));
                                    yearList.get(j).getMonthList().add(new Month(month, dayList));
                                } else {//如果月份存在
                                    yearList.get(j).getMonthList().get(index).getDayList().add(new Day(day));
                                }
                            }
                        }
                    }
                }
                bool_m=true;
                bool_y=true;
            }
        }
        yearList=sort(yearList);
        myTimeLineAdapter = new MyTimeLineAdapter(list_Activity.this, yearList);
        myTimeLineAdapter.setMyCallBack(new MyTimeLineAdapter.MyCallBack() {
            @Override
            public void click(int groupPosition, int childPosition) {
                if(yearList.size()==0){
                    noContent.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.GONE);
                    return;
                }else {
                    listView.setVisibility(View.VISIBLE);
                }
                UserMethodUtils.groupP=groupPosition;
                UserMethodUtils.childP=childPosition;
                List<Day> days = null;

                UserMethodUtils.currentDate = yearList.get(groupPosition).getYear_name() + "-" +
                            yearList.get(groupPosition).getMonthList().get(childPosition).getMonth_name();
                days = sort_day(yearList, groupPosition, childPosition);


                adapter=new ContentAdapter(yearList,days,list_Activity.this,groupPosition,childPosition);
                ViewGroup.LayoutParams lp = listView.getLayoutParams();
                int count = UserMethodUtils.DeleteContain(days).size();
                int totalHeight = 0;
                View view = null;
                for (int i = 0; i < count; i++) {
                    view = adapter.getView(i, null, listView);
                    view.measure(0, 0);
                    totalHeight += view.getMeasuredHeight();
                }
                lp.height = totalHeight;
                listView.requestLayout();
                listView.setAdapter(adapter);
                noContent.setVisibility(View.GONE);
                drawerlayout.closeDrawer(Gravity.LEFT);
                adapter.setTransferData(new ContentAdapter.TransferData() {
                    @Override
                    public void transfer(String result_time) {
                        tempTime = result_time;
                    }
                });
            }
        });
        lv.setAdapter(myTimeLineAdapter);
        UserMethodUtils.user_dataList = user_dataList;

    }


    private List<Year> sort(List<Year> years){
        Year tempYear = null;
        Month tempMonth = null;
        int year_1,year_2,month_1,month_2;
        for (int i = 0; i < years.size()-1; i++) {
            for (int j = 0; j < years.size() - i - 1; j++) {
                year_1 = Integer.parseInt(years.get(j).getYear_name());
                year_2 = Integer.parseInt(years.get(j+1).getYear_name());
                if (year_1 > year_2){
                    tempYear = years.get(j);
                    years.set(j,years.get(j+1));
                    years.set(j+1,tempYear);
                }
            }
        }
        for (int i = 0; i < years.size(); i++) {
            for (int j = 0; j < years.get(i).getMonthList().size()-1; j++) {
                for (int k = 0; k < years.get(i).getMonthList().size()-1-j; k++) {
                    month_1 = Integer.parseInt(years.get(i).getMonthList().get(k).getMonth_name());
                    month_2 = Integer.parseInt(years.get(i).getMonthList().get(k+1).getMonth_name());
                    if (month_1>month_2){
                        tempMonth = years.get(i).getMonthList().get(k);
                        years.get(i).getMonthList().set(k,years.get(i).getMonthList().get(k+1));
                        years.get(i).getMonthList().set(k+1,tempMonth);
                    }
                }
            }
        }
        return years;
    }
    public List<Day> sort_day(List<Year> years,int groupPosition, int childPosition){
        List<Day> dayList = years.get(groupPosition).getMonthList().get(childPosition).getDayList();
        Day temp;
        for (int i = 0; i < dayList.size()-1; i++) {
            for (int j = 0; j < dayList.size()-1-i; j++) {
                int day_1 = Integer.parseInt(dayList.get(j).getDay_name());
                int day_2 = Integer.parseInt(dayList.get(j+1).getDay_name());
                if (day_1<day_2){
                    temp = dayList.get(j);
                    dayList.set(j,dayList.get(j+1));
                    dayList.set(j+1,temp);
                }
            }
        }
        return  dayList;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //获取菜单管理器
        MenuInflater inflater = getMenuInflater();
        //加载菜单文件
        inflater.inflate(R.menu.main, menu);
        //1.item所属的groupid，
        //2.item的id
        //3.优先级
        //4.item上的文本内容
//        menu.add(Menu.NONE, 1, 1, "动态创建菜单1");
//        //创建一个二级菜单
//        SubMenu subMenu = menu.addSubMenu("二级菜单");
//        //往SubMenu中添加菜单项
//        subMenu.add(Menu.NONE, 21, 1, "二级菜单1");
//        subMenu.add(Menu.NONE, 22, 1, "二级菜单2");
        //返回true表示菜单创建成功
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (drawerlayout.isDrawerOpen(Gravity.LEFT)) {
                    drawerlayout.closeDrawer(Gravity.LEFT);
                } else {
                    drawerlayout.openDrawer(Gravity.LEFT);
                }
                break;
            case R.id.search:
                startActivity(new Intent(this,search_Activity.class));
                break;
            case R.id.Synchronized_to_could:
                Synchronized_to_could();
                break;
            case R.id.Synchronized_to_local:
                Synchronized_to_local();
                break;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onBackPressed() {
        if (drawerlayout.isDrawerOpen(Gravity.LEFT)) {
            drawerlayout.closeDrawer(Gravity.LEFT);
        } else {
            new UserMethodUtils().ShowDialog(list_Activity.this);
        }

    }

    @Override
    public void UpdateData(List<User_data> list) {
        for (User_data user_data : list) {
                String time = user_data.getTime();
                double momey = user_data.getMoney();
                String notes = user_data.getNotes();
                int isIncome = user_data.isIncome();
                timeArr = time.split("-");
                yearName.add(timeArr[0]);
                monthName.add(timeArr[1]);
                dayName.add(timeArr[2]);
                dateList.add(new Date(timeArr[0], timeArr[1], timeArr[2]));
        }
        addDate(dateList,list);
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete:
                 int index = UserMethodUtils.DeleteDataByTime(UserMethodUtils.sql, tempTime, UserMethodUtils.currentUserName);
                 if(index==-1){
                     Toast.makeText(instance, "删除失败！", Toast.LENGTH_SHORT).show();
                 }else{
                     Toast.makeText(instance, "删除成功！", Toast.LENGTH_SHORT).show();
                     init();
                     findLocalPresenter.initData();
                     title_p.setText(UserMethodUtils.getTime());
                     if (UserMethodUtils.groupP!=-1&&UserMethodUtils.childP!=-1){
                         myTimeLineAdapter.myCallBack.click(UserMethodUtils.groupP,UserMethodUtils.childP);
                     }
                 }
                return true;
            default:
                return false;
        }

    }
    private List<User_data> getNetWorkData(){
        final BmobQuery<User_data> bmobQuery=new BmobQuery<>();

        final List<User_data> netWork_list=new ArrayList<User_data>();
        bmobQuery.findObjects(new FindListener<User_data>() {
            @Override
            public void done(List<User_data> list, BmobException e) {
                netWork_list.addAll(list);
            }
        });
        return netWork_list;
    }
    private void Synchronized_to_could(){
        success=0;failure=0;
        new Thread(new Runnable() {
            @Override
            public void run() {
                BmobQuery<User_data> bmobQuery=new BmobQuery<>();
                final List<User_data> local_list = UserMethodUtils.searchData(UserMethodUtils.sql, UserMethodUtils.currentUserName);
                final List<User_data> netWork_list=new ArrayList<>();
                bmobQuery.findObjects(new FindListener<User_data>() {
                    @Override
                    public void done(List<User_data> list, BmobException e) {
                        netWork_list.addAll(list);
                        if(netWork_list.size()==0){
                            for (User_data user_data:local_list){
                                AddDataToNetWork(user_data);
                            }
                        }else {
                            for(User_data user_data:local_list){
                                if(!netWork_list.contains(user_data)){

                                    AddDataToNetWork(user_data);
                                }
                            }
                        }
                    }
                });

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(list_Activity.this, "成功同步"+success+"条，"+"失败"+failure+"条！", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }).start();

    }
    private void AddDataToNetWork(User_data user_data){
         user_data.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
                    success++;
                } else {
                    failure++;
                }
            }
        });
    }

    private void Synchronized_to_local() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                BmobQuery<User_data> bmobQuery=new BmobQuery<>();
                final List<User_data> local_list = new ArrayList<>();
                List<User_data> temp =UserMethodUtils.searchData(UserMethodUtils.sql, UserMethodUtils.currentUserName);
                if (temp!=null){
                    local_list.addAll(temp);
                }
                final List<User_data> netWork_list=new ArrayList<>();
                bmobQuery.findObjects(new FindListener<User_data>() {
                    @Override
                    public void done(List<User_data> list, BmobException e) {
                        netWork_list.addAll(list);
                        if(local_list.size()==0){
                            for (User_data user_data:netWork_list){
                                AddDataToLocal(user_data);
                            }
                        }else {
                            for (User_data user_data:netWork_list){
                                if (!local_list.contains(user_data)){
                                    AddDataToLocal(user_data);
                                }

                            }
                        }
                    }
                });

            }
        }).start();

    }
    private void AddDataToLocal(User_data user_data){
        String userName = user_data.getUserName();
        double money = user_data.getMoney();
        String time = user_data.getTime();
        int id = user_data.getId();
        String notes = user_data.getNotes();
        String useType = user_data.getUseType();
        int income = user_data.isIncome();
        UserMethodUtils.AddData(UserMethodUtils.sql,userName,money,notes,income,useType,time,id,true);
    }
}
