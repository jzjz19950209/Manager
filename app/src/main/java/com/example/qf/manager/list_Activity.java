package com.example.qf.manager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qf.manager.drawerlayout.Date;
import com.example.qf.manager.drawerlayout.Day;
import com.example.qf.manager.drawerlayout.Month;
import com.example.qf.manager.drawerlayout.MyTimeLineAdapter;
import com.example.qf.manager.drawerlayout.OnLoadDataListener;
import com.example.qf.manager.drawerlayout.TimeLineLayout;
import com.example.qf.manager.drawerlayout.Year;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class list_Activity extends AppCompatActivity {
    private DrawerLayout drawerlayout;
    private String userName;
    private TextView title_p;
    private ExpandableListView lv;
    private String[] timeArr;
    private List<Date> dateList = new ArrayList<>();
    private OnLoadDataListener onLoadDataListener=new OnLoadDataListener() {
        @Override
        public void onResponse(List<Date> list) {
            addDate(list);

        }

        @Override
        public void onFaile(String msg) {

        }
    };
    List<String> yearName = new ArrayList<>();
    List<String> monthName = new ArrayList<>();
    List<String> dayName = new ArrayList<>();

    List<Year> yearList = new ArrayList<>();
    private Handler mhandler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        title_p = (TextView) findViewById(R.id.title_p);
        lv = (ExpandableListView) this.findViewById(R.id.lv);
        setSupportActionBar(toolbar);
        TimeLineLayout timeLineLayout = (TimeLineLayout) findViewById(R.id.timelinelayout);
        drawerlayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        setTitle();
        setTimeLine();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent to_edit = new Intent(list_Activity.this, edit_Activity.class);
                to_edit.putExtra("username", userName);
                startActivity(to_edit);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTimeLine();
    }

    private void setTitle() {
        Intent intent = getIntent();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.main);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        userName = intent.getStringExtra("extra_username");
        actionBar.setTitle(userName);
        actionBar.setIcon(R.mipmap.ic_launcher);
        actionBar.setSubtitle(UserMethodUtils.getTime());
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerlayout, toolbar, 0, 0);
        drawerlayout.addDrawerListener(toggle);
        toggle.syncState();

    }

    private void setTimeLine() {
        title_p.setText(UserMethodUtils.getTime());
        BmobQuery<User_data> bmobQuery = new BmobQuery<>();
        bmobQuery.findObjects(new FindListener<User_data>() {
            @Override
            public void done(List<User_data> list, BmobException e) {
                if (e == null) {
                    //Log.d("jzjz", "done: "+Thread.currentThread().getName());
                    for (User_data user_data : list) {
                        if (user_data.getUserName().equals(userName)) {
                            String time = user_data.getTime();
                            double momey = user_data.getMoney();
                            String notes = user_data.getNotes();
                            boolean isIncome = user_data.isIncome();
                            timeArr = time.split("/");
                            yearName.add(timeArr[0]);
                            monthName.add(timeArr[1]);
                            dayName.add(timeArr[2]);
                            dateList.add(new Date(timeArr[0], timeArr[1], timeArr[2]));
//                            mhandler.post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    dateList.add(new Date(timeArr[0], timeArr[1], timeArr[2]));
//                                    lv.deferNotifyDataSetChanged();
//                                }
//                            });
                            //Log.d("jzjz", "done: "+yearName+","+momey+","+notes+","+isIncome);
                        }
                    }
                    onLoadDataListener.onResponse(dateList);

                } else {
                    Toast.makeText(list_Activity.this, "数据请求出错！", Toast.LENGTH_SHORT).show();
                }
            }
        });

//        for(int i=0;i<dayName.size();i++){
//            dayList1.add(new Day(dayName.get(i)));
//            if (!month.equals(monthName.get(i))) {
//               // Log.d("jzjz", "setTimeLine: "+dayList1.size());
//                List<Day> dayList = new ArrayList<>();
//                dayList.addAll(dayList1);
//                dayList1.clear();
//                monthList1.add(new Month(monthName.get(i), dayList));
//            }
//            month =monthName.get(i);
//
//
//            if (!year.equals(yearName.get(i))) {
//                //Log.d("jzjz", "setTimeLine: "+monthList1.size());
//                List<Month> monthList=new ArrayList<>();
//                monthList.addAll(monthList1);
//                monthList1.clear();
//                yearList.add(new Year(yearName.get(i), monthList));
//            }
//            year = yearName.get(i);
//
//        }
//        //Log.d("jzjz", "setTimeLine: "+yearList.size());
//        for (Year y:yearList){
//            Log.d("jzjz", "setTimeLine: "+y.getYear_name()+y.getMonthList().get(0).getMonth_name());
//        }


    }

    private void addDate(List<Date> list) {
        String day, month, year;
//        List<Day> dayList = new ArrayList<>();
//        List<Month> monthList = new ArrayList<>();
        List<Day> dayList;
        List<Month> monthList;
        int index = 0;
        boolean bool_y = true, bool_m = true, bool_d = true;



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
                                    for (int p = 0; p < yearList.get(j).getMonthList().get(index).getDayList().size(); p++) {
                                        if (yearList.get(j).getMonthList().get(index).getDayList().get(p).equals(day)) {
                                            bool_d = false;
                                        }
                                    }
                                    if (bool_d) {//如果号数不存在
                                        yearList.get(j).getMonthList().get(index).getDayList().add(new Day(day));
                                    }
                                }
                            }
                        }

                    }
                }
                bool_d=true;
                bool_m=true;
                bool_y=true;
            }

        }
        yearList=sort(yearList);
        lv.setAdapter(new MyTimeLineAdapter(this, yearList));
    }
    private void init(){
        List<Month> lm;
        List<Day> ld;
        for (int i = 0; i < 5; i++) {
            lm=new ArrayList<>();
            for (int j = 0; j < 12; j++) {
                ld=new ArrayList<>();
                for (int k = 0; k < 30; k++) {
                    ld.add(new Day(""+k));
                }
                lm.add(new Month(""+j,ld));
            }
            yearList.add(new Year(""+(i+2016),lm));
        }
        lv.setAdapter(new MyTimeLineAdapter(this, yearList));
    }

    private List<Year> sort(List<Year> years){
        Year tempYear=null;
        Month tempMonth=null;
        int year_1,year_2,month_1,month_2;
        for (int i = 0; i < years.size()-1; i++) {
            for (int j = 0; j < years.size() - i - 1; j++) {
                year_1= Integer.parseInt(years.get(j).getYear_name());
                year_2 = Integer.parseInt(years.get(j+1).getYear_name());
                if (year_1>year_2){
                    tempYear=years.get(j);
                    years.set(j,years.get(j+1));
                    years.set(j+1,tempYear);
                }
            }
        }
        for (int i = 0; i < years.size(); i++) {
            for (int j = 0; j < years.get(i).getMonthList().size()-1; j++) {
                for (int k = 0; k < years.get(i).getMonthList().size()-1-j; k++) {
                    month_1=Integer.parseInt(years.get(i).getMonthList().get(k).getMonth_name());
                    month_2=Integer.parseInt(years.get(i).getMonthList().get(k+1).getMonth_name());
                    if (month_1>month_2){
                        tempMonth=years.get(i).getMonthList().get(k);
                        years.get(i).getMonthList().set(k,years.get(i).getMonthList().get(k+1));
                        years.get(i).getMonthList().set(k+1,tempMonth);
                    }
                }
            }
        }
        return years;
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

}
