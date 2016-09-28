package com.example.qf.manager;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qf.manager.save.DBHelper;

public class register_Activity extends AppCompatActivity implements View.OnClickListener{
    private Button register_ensure,register_clear,register_cancel;
    private EditText register_name,register_password;
    private SQLiteDatabase db;
    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        DBHelper dbHelper = new DBHelper(this);
        db = dbHelper.getReadableDatabase();
        textView= (TextView) findViewById(R.id.textview_title);
        textView.setText("注册");
        register_ensure= (Button) findViewById(R.id.register_ensure);
        register_clear= (Button) findViewById(R.id.register_clear);
        register_cancel= (Button) findViewById(R.id.register_cancel);
        register_name= (EditText) findViewById(R.id.register_name);
        register_password= (EditText) findViewById(R.id.register_password);
        register_ensure.setOnClickListener(this);
        register_clear.setOnClickListener(this);
        register_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_ensure:
                String name=register_name.getText().toString();
                String password=register_password.getText().toString();
                if (!name.equals("") && !password.equals("")) {
                    name = name.trim();
                    if (search(name)) {
                        long insert=addUser(name, password);
                        if(insert!=-1){
                            Toast.makeText(register_Activity.this, "注册成功！", Toast.LENGTH_SHORT).show();
                        }
                        startActivity(new Intent(register_Activity.this,login_Activity.class));
                        finish();
                    }
                } else {
                    Toast.makeText(register_Activity.this, "姓名或密码为空!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.register_clear:
                register_name.setText("");
                register_password.setText("");
                break;
            case R.id.register_cancel:
                startActivity(new Intent(register_Activity.this,login_Activity.class));
                finish();
                break;
        }
    }
    public long addUser(String name, String pwd) {
        ContentValues values = new ContentValues();
        values.put("username", name);
        values.put("password", pwd);
        long insert = db.insert(DBHelper.USERTABLE, null, values);
        return insert;
    }
    public boolean search(String name) {
        Cursor cursor = db.query(true, DBHelper.USERTABLE, null,
                null, null, null, null, null, null);
        //如果cursor一直可以向下移动，则一直遍历数据库
        while (cursor.moveToNext()) {
            //获取username字段的下标,区分大小写
            int usernameIndex = cursor.getColumnIndex("username");
            String username = cursor.getString(usernameIndex);
            if (name.equals(username)) {
                Toast.makeText(this, "用户名存在！", Toast.LENGTH_SHORT).show();
                cursor.close();
                return false;
            }
        }
        //cursor使用完成之后一定要关闭
        cursor.close();
        return true;
    }
}
