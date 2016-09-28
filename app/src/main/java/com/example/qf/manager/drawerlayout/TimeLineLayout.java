package com.example.qf.manager.drawerlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.qf.manager.R;
import com.example.qf.manager.UserMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qf on 2016/9/10.
 */
public class TimeLineLayout extends RelativeLayout {
    private ExpandableListView lv;
    private List<Year> yearList;
    private List<String> list=new ArrayList<>();
    private Spinner spinner;
    public TimeLineLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.timeline,this);
        TextView title_p= (TextView) findViewById(R.id.title_p);
        title_p.setText(UserMethod.getTime());
        initData();
        initView();

    }
//    private void setSpinner(List<String> list){
//        spinner= (Spinner) findViewById(R.id.spinner);
//        spinner.setAdapter(new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,list));
//    }

    private void initView(){
        lv= (ExpandableListView) this.findViewById(R.id.lv);
        lv.setAdapter(new MyTimeLineAdapter(getContext(),yearList));
    }
    private void initData() {
            yearList= new ArrayList<>();
            for (int j = 0; j <= 12; j++) {
                List<Month> monthList = new ArrayList<>();
                for (int i = 1; i <=12; i++) {
                    List<Day> dayList=new ArrayList<>();
                    for(int k=1;k<=30;k++){
                        dayList.add(new Day("day"+k));
                    }
                    monthList.add(new Month("month"+ i,dayList));
                }
                yearList.add(new Year((2016+j)+"年",monthList));
            }
    }
}
