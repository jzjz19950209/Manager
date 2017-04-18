package com.example.qf.manager;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qf.manager.Model.Bean.User_data;
import com.example.qf.manager.Presenter.FindUseTypePresenter;
import com.example.qf.manager.View.IEditActivityView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class edit_Activity extends AppCompatActivity implements IEditActivityView{
    private TextView textView,currentDate;
    private EditText notes,money;
    private RadioGroup rg;
    private int isIncome=0;
    private DatePicker datePicker;
    private ImageView saveNew;
    private Spinner spinner;
    private Date date;
    private ArrayAdapter<String> spinnerAdapter;
    private List<String> spinnerData;
    private SimpleDateFormat sdf;
    private String useType;
    private String spinnerResult;
    private FindUseTypePresenter findUseTypePresenter=new FindUseTypePresenter(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Bmob.initialize(this, "08c75fae7d012cfb08a09e095665d0b2");
        textView= (TextView) findViewById(R.id.textview_title);
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        saveNew= (ImageView) findViewById(R.id.saveNew);
        datePicker= (DatePicker) findViewById(R.id.datePicker);
        spinner = (Spinner) findViewById(R.id.spinner_edit);
        textView.setText("新的账单");
        rg= (RadioGroup) findViewById(R.id.rg);
        notes= (EditText) findViewById(R.id.notes);
        money= (EditText) findViewById(R.id.money);
        useType = getIntent().getStringExtra("useType");
        rg.check(R.id.out);
        findUseTypePresenter.initData(2);
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
                findUseTypePresenter.initData(isIncome);
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
                UserMethodUtils.searchUseType(UserMethodUtils.sql,UserMethodUtils.currentUserName,isIncome);
                UserMethodUtils.addUseType(UserMethodUtils.sql,UserMethodUtils.currentUserName,spinnerResult,isIncome);
                UserMethodUtils.AddData(UserMethodUtils.sql,userName,Double.parseDouble(money_input),notes_input,isIncome,spinnerResult,sdf.format(date));
                finish();
            }else {
                Toast.makeText(edit_Activity.this, "请输入金额！", Toast.LENGTH_SHORT).show();
            }
        }else if (useType.equals("alter")){
            //String time = getIntent().getStringExtra("time");
            String notes_text = notes.getText().toString();
            String id = getIntent().getStringExtra("id");
            String money_text = money.getText().toString();
            String time=sdf.format(date);
            int update = UserMethodUtils.UpdateData(UserMethodUtils.sql, id, UserMethodUtils.currentUserName, time, isIncome, money_text, notes_text,spinnerResult);
            if (update==-1){
                Toast.makeText(this, "修改失败！", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "修改成功!", Toast.LENGTH_SHORT).show();
            }
            finish();
        }

    }

    @Override
    public void UpdateData(List<String> list) {
        spinnerData=new ArrayList<>();
        if(list!=null){
            spinnerData.addAll(list);
        }else {
            if (isIncome==1){
                spinnerData.add("工资");
            }else {
                spinnerData.add("伙食");

            }
        }
        spinnerData.add("自定义");
        spinnerAdapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,spinnerData);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> parent, View view, int position, long id) {
                if(position==spinnerData.size()-1){

                    final EditText input = new EditText(edit_Activity.this);
                    AlertDialog.Builder builder = new AlertDialog.Builder(edit_Activity.this);
                    builder.setTitle("添加分类").setIcon(android.R.drawable.ic_dialog_info).setView(input)
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    parent.setSelection(0);
                                }
                            });
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            String result = input.getText().toString();
                            spinnerData.add(spinnerData.size()-1,result);
                            spinnerAdapter.notifyDataSetChanged();
                            spinnerResult=result;
                            return;
                        }
                    });
                    builder.show();
                    spinnerResult=spinnerAdapter.getItem(position);
                    return;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerResult=isIncome==1?"工资":"伙食";
    }
}
