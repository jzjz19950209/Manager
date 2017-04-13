package com.example.qf.manager.View.Adapter;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.qf.manager.Model.Bean.User_data;
import com.example.qf.manager.R;

import java.util.List;

/**
 * Created by jzjz on 2017/4/13.
 */

public class SearchAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<User_data> list;

    public SearchAdapter(Context context, List<User_data> list) {
        this.context = context;
        this.list = list;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView=inflater.inflate(R.layout.search_result_item,parent,false);
            viewHolder.time = (TextView) convertView.findViewById(R.id.search_item_time);
            viewHolder.type = (TextView) convertView.findViewById(R.id.search_item_type);
            viewHolder.money= (TextView) convertView.findViewById(R.id.search_item_money);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.time.setText(list.get(position).getTime());
        viewHolder.type.setText(list.get(position).isIncome()==1?"收入":"支出");
        viewHolder.money.setText(""+list.get(position).getMoney());
        return convertView;
    }
    class ViewHolder{
        TextView time,type,money;
    }

}
