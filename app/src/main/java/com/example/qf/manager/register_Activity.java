package com.example.qf.manager;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qf.manager.save.DBHelper;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class register_Activity extends AppCompatActivity implements View.OnClickListener{
    private Button register_ensure,register_clear,register_cancel;
    private EditText register_name,register_password;
    private SQLiteDatabase db;
    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Bmob.initialize(this, "08c75fae7d012cfb08a09e095665d0b2");
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
                    Search(name,password);
//                    if (search(name)) {
//                        long insert=addUser(name, password);
//                        if(insert!=-1){
//                            Toast.makeText(register_Activity.this, "数据库注册成功！", Toast.LENGTH_SHORT).show();
//                        }
//                        startActivity(new Intent(register_Activity.this,login_Activity.class));
//                        finish();
//                    }
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
//    public long addUser(String name, String pwd) {
//        ContentValues values = new ContentValues();
//        values.put("username", name);
//        values.put("password", pwd);
//        long insert = db.insert(DBHelper.USERTABLE, null, values);
//        return insert;
//    }
//    public boolean search(String name) {
//        Cursor cursor = db.query(true, DBHelper.USERTABLE, null,
//                null, null, null, null, null, null);
//        //如果cursor一直可以向下移动，则一直遍历数据库
//        while (cursor.moveToNext()) {
//            //获取username字段的下标,区分大小写
//            int usernameIndex = cursor.getColumnIndex("username");
//            String username = cursor.getString(usernameIndex);
//            if (name.equals(username)) {
//                Toast.makeText(this, "用户名存在！", Toast.LENGTH_SHORT).show();
//                cursor.close();
//                return false;
//            }
//        }
//        //cursor使用完成之后一定要关闭
//        cursor.close();
//        return true;
//    }
    public void Search(final String name, final String password){

        BmobQuery<User> bmobQuery=new BmobQuery<User>();
        bmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if(e==null){
                    for(User user:list){
                        if(name.equals(user.getUserName())){
                            Toast.makeText(register_Activity.this, "账户已存在！", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    User userNew = new User(name, password);
                    userNew.save(new SaveListener<String>() {
                        @Override
                        public void done(String objectId, BmobException e) {
                            if(e==null){
                                Toast.makeText(register_Activity.this, "网络注册成功！"+objectId, Toast.LENGTH_SHORT).show();
                                finish();
                            }else {
                                Toast.makeText(register_Activity.this, "网络注册失败！"+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }else {
                    Toast.makeText(register_Activity.this, "网络异常！", Toast.LENGTH_SHORT).show();
                }
            }
        });
//        bmobQuery.getObject("e7ed565770", new QueryListener<User>() {
//            @Override
//            public void done(User user, BmobException e) {
//                if(e==null){
//                    String userName=user.getUserName();
//                    String userPassword=user.getUserPassword();
//                    if(userName.equals(name)){
//                        Toast.makeText(register_Activity.this, "账户存在！", Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//                    User userNew = new User(name, password);
//                    userNew.save(new SaveListener<String>() {
//                        @Override
//                        public void done(String objectId, BmobException e) {
//                            if(e==null){
//                                Toast.makeText(register_Activity.this, "网络注册成功！"+objectId, Toast.LENGTH_SHORT).show();
//                            }else {
//                                Toast.makeText(register_Activity.this, "网络注册失败！"+e.getMessage(), Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//                }else{
//                    Toast.makeText(register_Activity.this, "网络异常！", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }

}
