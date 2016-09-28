package com.example.qf.manager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.qf.manager.save.DBHelper;

public class login_Activity extends AppCompatActivity {
    private Button button_login;
    private Button button_register;
    private EditText editText_name;
    private EditText editText_pwd;
    private Button button_clear;
    String userName, password;
    private SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        //getSupportActionBar().hide();
        button_login = (Button) findViewById(R.id.button_login);
        button_register = (Button) findViewById(R.id.button_register);
        button_clear = (Button) findViewById(R.id.button_clear);
        editText_name = (EditText) findViewById(R.id.username);
        editText_pwd = (EditText) findViewById(R.id.password);
        DBHelper dbHelper = new DBHelper(this);
        db = dbHelper.getReadableDatabase();
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(login_Activity.this, MainActivity.class);
                userName = editText_name.getText().toString();
                password = editText_pwd.getText().toString();
                if (userName.equals("") || password.equals("")) {
                    Toast.makeText(login_Activity.this, "姓名或密码不能为空！", Toast.LENGTH_SHORT).show();
                } else {
                    if (search(userName, password)) {
                        intent.putExtra("extra_username",userName);
                        startActivity(intent);
                        Toast.makeText(login_Activity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        editText_name.setText("");
                        editText_pwd.setText("");

                    } else {
                        Toast.makeText(login_Activity.this, "姓名或密码不正确！", Toast.LENGTH_SHORT).show();
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
                startActivity(new Intent(login_Activity.this,register_Activity.class));
//                String name = editText_name.getText().toString();
//                String pwd = editText_pwd.getText().toString();
//                if (!name.equals("") && !pwd.equals("")) {
//                    name = name.trim();
//                    if (search(name)) {
//                        addData(name, pwd);
//                    }
//                } else {
//                    Toast.makeText(login_Activity.this, "姓名或密码为空!", Toast.LENGTH_SHORT).show();
//                }
            }
        });
    }
//    public void addData(String name, String pwd) {
//        ContentValues values = new ContentValues();
//        values.put("username", name);
//        values.put("password", pwd);
//        long insert = db.insert(DBHelper.USERTABLE, null, values);
//        if (insert != -1) {
//            Toast.makeText(login_Activity.this, "注册成功！", Toast.LENGTH_SHORT).show();
//        }
//    }
    //注册验证
//    public boolean search(String name) {
//        Cursor cursor = db.query(true, DBHelper.USERTABLE, null,
//                null, null, null, null, null, null);
//        //如果cursor一直可以向下移动，则一直遍历数据库
//        while (cursor.moveToNext()) {
//            //获取username字段的下标,区分大小写
//            int usernameIndex = cursor.getColumnIndex("username");
//            String username = cursor.getString(usernameIndex);
//            if (name.equals(username)) {
//                Toast.makeText(login_Activity.this, "用户名存在！", Toast.LENGTH_SHORT).show();
//                cursor.close();
//                return false;
//            }
//        }
//        //cursor使用完成之后一定要关闭
//        cursor.close();
//        return true;
//    }
    //登陆验证
    public boolean search(String name, String password) {
        Cursor cursor = db.query(true, DBHelper.USERTABLE, null,
                null, null, null, null, null, null);
        //如果cursor一直可以向下移动，则一直遍历数据库
        while (cursor.moveToNext()) {
            //获取username字段的下标,区分大小写
            int usernameIndex = cursor.getColumnIndex("username");
            int userpasswordInsex = cursor.getColumnIndex("password");
            String username = cursor.getString(usernameIndex);
            String userpassword = cursor.getString(userpasswordInsex);
            if (name.equals(username) && password.equals(userpassword)) {
                cursor.close();
                return true;
            }
        }
        //cursor使用完成之后一定要关闭
        cursor.close();
        return false;
    }
}
