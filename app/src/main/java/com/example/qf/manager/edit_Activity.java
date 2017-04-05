package com.example.qf.manager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qf.manager.Model.Bean.User_data;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class edit_Activity extends AppCompatActivity {
    private TextView textView,currentDate;
    private EditText year,month,day,notes,money;
    private RadioGroup rg;
    private int isIncome=0;
    private DatePicker datePicker;
    private ImageView saveNew;
    private Date date;
    private SimpleDateFormat sdf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Bmob.initialize(this, "08c75fae7d012cfb08a09e095665d0b2");
        textView= (TextView) findViewById(R.id.textview_title);
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        //currentDate= (TextView) findViewById(R.id.currentDate);
        saveNew= (ImageView) findViewById(R.id.saveNew);
        datePicker= (DatePicker) findViewById(R.id.datePicker);
        textView.setText("新的账单");
//        currentDate.setText("当前日期："+ UserMethodUtils.getTime());
        rg= (RadioGroup) findViewById(R.id.rg);
//        year= (EditText) findViewById(R.id.year);
//        month= (EditText) findViewById(R.id.month);
//        day= (EditText) findViewById(R.id.day);
        notes= (EditText) findViewById(R.id.notes);
        money= (EditText) findViewById(R.id.money);
        rg.check(R.id.out);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.in:
                        isIncome=1;
                        break;
                    case R.id.out:
                        isIncome=0;
                        break;
                }
            }
        });
        Calendar calendar=Calendar.getInstance();
        date = new Date();
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date=new Date(year-1900,monthOfYear,dayOfMonth);
            }
        });
    }
    public void saveNew(View view) {
        String notes_input = notes.getText().toString();
        String money_input = money.getText().toString();
        if(!money_input.equals("")){
            if(notes_input.equals("")){
                notes_input="--";
            }
            String userName = UserMethodUtils.currentUserName;
            UserMethodUtils.AddData(UserMethodUtils.sql,userName,Double.parseDouble(money_input),notes_input,isIncome,sdf.format(date));
            finish();
        }else {
            Toast.makeText(edit_Activity.this, "请输入金额！", Toast.LENGTH_SHORT).show();
        }

//                if(!year_input.equals("") && !month_input.equals("") && !day_input.equals("") && !money_input.equals("")){
//                    int m=Integer.parseInt(month_input);
//                    int d=Integer.parseInt(day_input);
//                    if(m>12||m<1){
//                        Toast.makeText(edit_Activity.this, "月份输入有误！", Toast.LENGTH_SHORT).show();
//                    }
//                    else if(d>31||d<1){
//                        Toast.makeText(edit_Activity.this, "日期输入有误！", Toast.LENGTH_SHORT).show();
//                    }else {
//                        String time = year_input + "/" + month_input + "/" + day_input;
//                        User_data user_data=new User_data();
//                        user_data.setUserName(userName);
//                        user_data.setNotes(notes_input);
//                        user_data.setTime(time);
//                        user_data.setMoney(Double.parseDouble(money_input));
//                        user_data.setIncome(isIncome);
//                UserMethodUtils.AddData(UserMethodUtils.sql,userName,Double.parseDouble(money_input),notes_input,isIncome,time);
//                finish();
////                user_data.save(new SaveListener<String>() {
////                    @Override
////                    public void done(String s, BmobException e) {
////                        if(e==null){
////                            Toast.makeText(edit_Activity.this, "网络保存成功！", Toast.LENGTH_SHORT).show();
////                            finish();
////                        }else {
////                            Toast.makeText(edit_Activity.this, "网络保存失败！", Toast.LENGTH_SHORT).show();
////                        }
////                    }
////                });
//            }
//        }else{
//            Toast.makeText(edit_Activity.this, "嘿！你输漏了>_< !", Toast.LENGTH_SHORT).show();
//        }
    }
}
