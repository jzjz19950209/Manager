package com.example.qf.manager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class register_Activity extends AppCompatActivity implements View.OnClickListener{
    private Button register_ensure,register_clear,register_cancel;
    private EditText register_name,register_password1,register_password2,phoneNum;
    private TextView textView;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Bmob.initialize(this, "08c75fae7d012cfb08a09e095665d0b2");
        textView= (TextView) findViewById(R.id.textview_title);
        textView.setText("注册");
        register_ensure= (Button) findViewById(R.id.register_ensure);
        register_clear= (Button) findViewById(R.id.register_clear);
        register_cancel= (Button) findViewById(R.id.register_cancel);
        register_name= (EditText) findViewById(R.id.register_name);
        register_password1= (EditText) findViewById(R.id.register_password1);
        register_password2= (EditText) findViewById(R.id.register_password2);
        phoneNum= (EditText) findViewById(R.id.phoneNum);
        progressBar= (ProgressBar) findViewById(R.id.progressbar);
        register_ensure.setOnClickListener(this);
        register_clear.setOnClickListener(this);
        register_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_ensure:
                progressBar.setVisibility(View.VISIBLE);
                String name=register_name.getText().toString();
                String password1=register_password1.getText().toString();
                String password2=register_password2.getText().toString();
                String phone_num=phoneNum.getText().toString();
                if (!name.equals("") && !password1.equals("")&&!password2.equals("")&&!phone_num.equals("")) {
                    if(password1.equals(password2)){
                        name = name.trim();
                        register(name,password1,phone_num);
                    }else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(register_Activity.this, "两次密码不一致！", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(register_Activity.this, "姓名、密码或重复密码为空!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.register_clear:
                register_name.setText("");
                register_password1.setText("");
                register_password2.setText("");
                phoneNum.setText("");
                break;
            case R.id.register_cancel:
                startActivity(new Intent(register_Activity.this,login_Activity.class));
                finish();
                break;
        }
    }

    public void register(final String name, final String password, final String phone_num){
        BmobQuery<User> bmobQuery=new BmobQuery<>();
        bmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if(e==null){
                    for(User user:list){
                        if(name.equals(user.getUserName())){
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(register_Activity.this, "账户已存在！", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    User userNew = new User(name, password);
                    userNew.setPhoneNum(phone_num);
                    userNew.save(new SaveListener<String>() {
                        @Override
                        public void done(String objectId, BmobException e) {
                            if(e==null){
                                Toast.makeText(register_Activity.this, "网络注册成功！", Toast.LENGTH_SHORT).show();
                                finish();
                            }else {
                                Toast.makeText(register_Activity.this, "网络注册失败！", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }else {
                    Toast.makeText(register_Activity.this, "网络异常！", Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
            }
        });

    }

}
