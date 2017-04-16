package com.example.qf.manager;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qf.manager.Model.Bean.User;

import java.io.File;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class login_Activity extends AppCompatActivity {
    private Button button_login;
    private Button button_register;
    private EditText editText_name;
    private EditText editText_pwd;
    private Button button_clear;
    private TextView forget_pwd;
    private String userName, password;
    private Intent intent;
    private ProgressBar progressBar;
    private boolean result=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Bmob.initialize(this, "08c75fae7d012cfb08a09e095665d0b2");
        //getSupportActionBar().hide();
        //UserMethodUtils.createFile("userInfo.db");
        UserMethodUtils.createDir();
        //创建数据库文件
        UserMethodUtils.CreateDataBase_user();
        UserMethodUtils.CreateDataBase_useType();
        button_login = (Button) findViewById(R.id.button_login);
        button_register = (Button) findViewById(R.id.button_register);
        button_clear = (Button) findViewById(R.id.button_clear);
        editText_name = (EditText) findViewById(R.id.username);
        editText_pwd = (EditText) findViewById(R.id.password);
        forget_pwd= (TextView) findViewById(R.id.forget_pwd);
        progressBar= (ProgressBar) findViewById(R.id.login_progressbar);
        forget_pwd.setText(Html.fromHtml("<u>"+"忘记密码?"+"</u>"));
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(login_Activity.this, list_Activity.class);
                userName = editText_name.getText().toString();
                password = editText_pwd.getText().toString();
                if (userName.equals("") || password.equals("")) {
                    Toast.makeText(login_Activity.this, "姓名或密码不能为空！", Toast.LENGTH_SHORT).show();
                } else {
                    //Login(userName, password);
                    boolean b = UserMethodUtils.searchUser(login_Activity.this,UserMethodUtils.sql, userName, password);
                    if (b){
                        UserMethodUtils.currentUserName = userName;
                        startActivity(intent);
                    }
                }
            }
        });
        button_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText_name.setText("");
                editText_pwd.setText("");
                Toast.makeText(login_Activity.this, "信息已清空！", Toast.LENGTH_SHORT).show();
            }
        });
        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login_Activity.this, register_Activity.class));
            }
        });
        forget_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        result=false;
    }

    //登陆验证
    public void Login(final String name, final String password) {
        progressBar.setVisibility(View.VISIBLE);
        BmobQuery<User> bmobQuery = new BmobQuery<>();
        bmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if (e == null) {
                    for (User user : list) {
                        if (name.equals(user.getUserName()) && password.equals(user.getUserPassword())) {
                            Toast.makeText(login_Activity.this, "登录成功！", Toast.LENGTH_SHORT).show();
                            intent.putExtra("extra_username", userName);
                            startActivity(intent);
                            //editText_pwd.setText("");
                            progressBar.setVisibility(View.GONE);
                            result=true;
                            return;
                        }
                    }
                    if (!result){
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(login_Activity.this, "账号或密码错误！", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(login_Activity.this, "网络异常！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
