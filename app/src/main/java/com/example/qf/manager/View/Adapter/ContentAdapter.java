package com.example.qf.manager.View.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qf.manager.Model.Bean.Day;
import com.example.qf.manager.Model.Bean.User_data;
import com.example.qf.manager.R;
import com.example.qf.manager.Model.Bean.Year;
import com.example.qf.manager.UserMethodUtils;
import com.example.qf.manager.detail_Activity;
import com.example.qf.manager.list_Activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jzjz on 2017/2/22.
 */

public class ContentAdapter extends BaseAdapter {
    private List<Year> yearList;
    private Context context;
    private LayoutInflater inflater;
    private List<Day> days_list;
    private List<Day> days_deleteContain;
    private int year_position,month_position;
    private List<User_data> user_dataList;
    public ContentAdapter(List<Year> yearList,List<Day> days, Context context,int n,int m) {
        this.yearList = yearList;
        this.context = context;
        this.user_dataList=UserMethodUtils.user_dataList;
        inflater = LayoutInflater.from(context);
        days_list=days;
        year_position=n;
        month_position=m;
        days_deleteContain = UserMethodUtils.DeleteContain(days);
    }

    @Override
    public int getCount() {
        return days_deleteContain.size();
    }

    @Override
    public Object getItem(int position) {
        return days_deleteContain.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        String result_time=null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.content_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.content_time= (TextView) convertView.findViewById(R.id.content_time);
            viewHolder.line= (LinearLayout) convertView.findViewById(R.id.line);
            viewHolder.note_content_week= (TextView) convertView.findViewById(R.id.note_content_week);
            viewHolder.note_num= (TextView) convertView.findViewById(R.id.note_num);
            viewHolder.note_more= (ImageButton) convertView.findViewById(R.id.note_more);
            convertView.setTag(viewHolder);
        } else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        result_time=yearList.get(year_position).getYear_name()+"-"+
                yearList.get(year_position).getMonthList().get(month_position).getMonth_name()+"-"+
                days_deleteContain.get(position).getDay_name();
        viewHolder.content_time.setText(result_time);
        viewHolder.note_content_week.setText(UserMethodUtils.DateToWeek(result_time));
        viewHolder.note_num.setText(""+ UserMethodUtils.ContainTimes(days_list,days_deleteContain.get(position).getDay_name())+" 条");
        final String finalResult_time = result_time;
        viewHolder.note_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(list_Activity.instance, v);  //建立PopupMenu对象
                popup.getMenuInflater().inflate(R.menu.list_item,popup.getMenu());
                popup.setOnMenuItemClickListener(list_Activity.instance);   //设置点击菜单选项事件
                popup.show();
                transferData.transfer(finalResult_time);

            }
        });

        viewHolder.line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(UserMethodUtils.currentDate.length()<8){
                    UserMethodUtils.currentDate+="-"+days_deleteContain.get(position).getDay_name();
                }else {
                    int index = UserMethodUtils.currentDate.lastIndexOf("-");
                    UserMethodUtils.currentDate= UserMethodUtils.currentDate.substring(0, index);
                    UserMethodUtils.currentDate +="-"+days_deleteContain.get(position).getDay_name();
                }
                Intent intent=new Intent(context,detail_Activity.class);
                context.startActivity(intent);
            }
        });

        return convertView;
    }
    class ViewHolder {
        TextView content_time, note_content_week, note_num;
        ImageButton note_more;
        LinearLayout line;
    }
    public interface TransferData{
        void transfer(String result_time);
    }
    private TransferData transferData;
    public void setTransferData(TransferData transferData){
        this.transferData=transferData;
    }
}
