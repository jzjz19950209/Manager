package com.example.qf.manager;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import cn.bmob.v3.Bmob;

//http://www.open-open.com/lib/view/open1446429625717.html
public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerlayout;
    private Button button;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bmob.initialize(this, "08c75fae7d012cfb08a09e095665d0b2");
        button = (Button) findViewById(R.id.button);
        drawerlayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        toolbar=(Toolbar) findViewById(R.id.toolbar);

        setTitle();
        //打开、关闭drawerlayout
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerlayout.isDrawerOpen(Gravity.LEFT)) {
                    drawerlayout.closeDrawer(Gravity.LEFT);
                } else {
                    drawerlayout.openDrawer(Gravity.LEFT);
                    //获取焦点
                    //drawerlayout.requestDisallowInterceptTouchEvent(true);
                }
            }
        });
        drawerlayout_listener();

    }

    //设置标题栏内容
    private void setTitle() {
        Intent intent = getIntent();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.main);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(intent.getStringExtra("extra_username"));
        actionBar.setIcon(R.mipmap.ic_launcher);
        actionBar.setSubtitle(UserMethod.getTime());
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerlayout,toolbar,0,0);
        drawerlayout.addDrawerListener(toggle);
        toggle.syncState();

    }

    //监听drawerlayout状态变化
    private void drawerlayout_listener() {
        drawerlayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            //位置改变
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            //打开
            @Override
            public void onDrawerOpened(View drawerView) {
                button.setBackgroundResource(R.drawable.an_close);
            }

            //关闭
            @Override
            public void onDrawerClosed(View drawerView) {
                button.setBackgroundResource(R.drawable.an);
            }

            //状态改变
            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    public void editNew(View view) {
        startActivity(new Intent(this, edit_Activity.class));
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
            new UserMethod().ShowDialog(MainActivity.this);
        }

    }


}
