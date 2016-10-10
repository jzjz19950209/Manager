package com.example.qf.manager;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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

public class list_Activity extends AppCompatActivity {
    private DrawerLayout drawerlayout;
    private String userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerlayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        setTitle();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent to_edit=new Intent(list_Activity.this, edit_Activity.class);
                to_edit.putExtra("username",userName);
                startActivity(to_edit);
            }
        });
    }
    private void setTitle() {
        Intent intent = getIntent();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.main);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        userName=intent.getStringExtra("extra_username");
        actionBar.setTitle(userName);
        actionBar.setIcon(R.mipmap.ic_launcher);
        actionBar.setSubtitle(UserMethod.getTime());
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerlayout,toolbar,0,0);
        drawerlayout.addDrawerListener(toggle);
        toggle.syncState();

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
            new UserMethod().ShowDialog(list_Activity.this);
        }

    }
}
