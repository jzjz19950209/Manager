package com.example.qf.manager;

import android.app.ListActivity;
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
    private EditText notes,money;
    private RadioGroup rg;
    private int isIncome=0;
    private DatePicker datePicker;
    private ImageView saveNew;
    private Date date;
    private SimpleDateFormat sdf;
    private String useType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Bmob.initialize(this, "08c75fae7d012cfb08a09e095665d0b2");
        textView= (TextView) findViewById(R.id.textview_title);
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        saveNew= (ImageView) findViewById(R.id.saveNew);
        datePicker= (DatePicker) findViewById(R.id.datePicker);
        textView.setText("新的账单");
        rg= (RadioGroup) findViewById(R.id.rg);
        notes= (EditText) findViewById(R.id.notes);
        money= (EditText) findViewById(R.id.money);
        useType = getIntent().getStringExtra("useType");
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
        selectType(useType);
    }
    private void selectType(String useType){
        Calendar calendar=Calendar.getInstance();
        switch (useType){
            case "edit":
                date = new Date();
                break;
            case "alter":
                notes.setText(getIntent().getStringExtra("note"));
                money.setText(getIntent().getStringExtra("money"));
                String type = getIntent().getStringExtra("type");
                if (type.equals("支出")){
                    rg.check(R.id.out);
                }else {
                    rg.check(R.id.in);
                }

                String time = getIntent().getStringExtra("time");
                String[] arr = time.split("-");
                date = new Date(Integer.parseInt(arr[0])-1900,Integer.parseInt(arr[1])-1,Integer.parseInt(arr[2]));
                break;
        }
        calendar.setTime(date);
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date=new Date(year-1900,monthOfYear,dayOfMonth);
            }
        });
    }
    public void saveNew(View view) {
        if(useType.equals("edit")){
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
        }else if (useType.equals("alter")){
            String time = getIntent().getStringExtra("time");

            String notes_text = notes.getText().toString();
            String id = getIntent().getStringExtra("id");
            String money_text = money.getText().toString();
            int update = UserMethodUtils.UpdateData(UserMethodUtils.sql, id, UserMethodUtils.currentUserName, time, isIncome, money_text, notes_text);
            if (update==-1){
                Toast.makeText(this, "修改失败！", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "修改成功!", Toast.LENGTH_SHORT).show();
            }
            finish();
        }

    }
}
