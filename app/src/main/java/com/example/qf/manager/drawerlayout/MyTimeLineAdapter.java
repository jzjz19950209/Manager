package com.example.qf.manager.drawerlayout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.qf.manager.R;

import java.util.List;

/**
 * Created by qf on 2016/9/10.
 */
public class MyTimeLineAdapter extends BaseExpandableListAdapter{
    private Context context;
    private List<Year> yearList;
    public MyTimeLineAdapter(Context context, List<Year> yearList) {
        this.context = context;
        this.yearList = yearList;
    }

    @Override
    public int getGroupCount() {
        return yearList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return yearList.get(groupPosition).getMonthList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return yearList.get(groupPosition).getYear_name();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return yearList.get(groupPosition).getMonthList().get(childPosition).getMonth_name();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder holder=null;
        if(convertView==null){
            holder=new GroupHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.group_item,null);
            holder.group_tv= (TextView) convertView.findViewById(R.id.month);
            convertView.setTag(holder);
        }else{
            holder= (GroupHolder) convertView.getTag();
        }
        holder.group_tv.setText(yearList.get(groupPosition).getYear_name());
        return convertView;
    }
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHolder holder=null;
        if (convertView==null){
            holder=new ChildHolder();
            convertView=LayoutInflater.from(context).inflate(R.layout.child_item,null);
            holder.child_tv= (TextView) convertView.findViewById(R.id.content);
            convertView.setTag(holder);
        }else{
            holder= (ChildHolder) convertView.getTag();
        }
        holder.child_tv.setText(yearList.get(groupPosition).getMonthList().get(childPosition).getMonth_name());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
    class GroupHolder {
        TextView group_tv;
    }

    class ChildHolder {
        TextView child_tv;
    }
}
