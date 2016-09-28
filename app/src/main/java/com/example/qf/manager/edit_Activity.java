package com.example.qf.manager;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qf.manager.save.DBHelper_Data;

public class edit_Activity extends AppCompatActivity {
    private TextView textView,currentDate;
    private EditText year,month,day,tag,money;
    private SQLiteDatabase db_data;
    private RadioGroup rg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        textView= (TextView) findViewById(R.id.textview_title);
        currentDate= (TextView) findViewById(R.id.currentDate);
        textView.setText("新的账单");
        currentDate.setText("当前日期："+UserMethod.getTime());
        rg= (RadioGroup) findViewById(R.id.rg);
        year= (EditText) findViewById(R.id.year);
        month= (EditText) findViewById(R.id.month);
        day= (EditText) findViewById(R.id.day);
        tag= (EditText) findViewById(R.id.tag);
        money= (EditText) findViewById(R.id.money);
        DBHelper_Data dbHelper_data=new DBHelper_Data(this);
        db_data=dbHelper_data.getReadableDatabase();
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case 0:
                        
                        break;
                    case 1:

                        break;
                }
            }
        });

    }
    public void saveNew(View view) {
        String year_input=year.getText().toString();
        String month_input=month.getText().toString();
        String day_input=day.getText().toString();
        String tag_input=tag.getText().toString();
        String money_input=money.getText().toString();
        if(!year_input.equals("") && !month_input.equals("") && !day_input.equals("") && !money_input.equals("")){
            int n=Integer.parseInt(month_input);
            int d=Integer.parseInt(day_input);
            if(n>12||n<1){
                Toast.makeText(edit_Activity.this, "月份输入有误！", Toast.LENGTH_SHORT).show();
            }
            else if(d>31||d<1){
                Toast.makeText(edit_Activity.this, "日期输入有误！", Toast.LENGTH_SHORT).show();
            }else {
                String date = year_input + "/" + month_input + "/" + day_input;
                ContentValues values = new ContentValues();
                values.put("date", date);
                values.put("tag", tag_input);
                values.put("money", money_input);
                long insert = db_data.insert(DBHelper_Data.DATATABLE, "userdata", values);
                if (insert != -1) {
                    Toast.makeText(edit_Activity.this, "添加成功！", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        }else{
            Toast.makeText(edit_Activity.this, "嘿！你输漏了>_< !", Toast.LENGTH_SHORT).show();
        }


    }
}
