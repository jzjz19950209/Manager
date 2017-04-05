package com.example.qf.manager.View.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.qf.manager.Model.Bean.User_data;
import com.example.qf.manager.R;

import java.util.List;

/**
 * Created by jzjz on 2017/3/15.
 */

public class DetailAdapter extends BaseAdapter {
    private List<User_data> user_dataList;
    private Context context;
    private LayoutInflater inflater;
    public DetailAdapter(List<User_data> user_dataList, Context context) {
        this.user_dataList = user_dataList;
        this.context = context;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return user_dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return user_dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            viewHolder=new ViewHolder();
            convertView =inflater.inflate(R.layout.detail_listview_item,parent,false);
            viewHolder.id= (TextView) convertView.findViewById(R.id._id);
            viewHolder.num= (TextView) convertView.findViewById(R.id.num);
            viewHolder.date= (TextView) convertView.findViewById(R.id.date);
            viewHolder.type= (TextView) convertView.findViewById(R.id.type);
            viewHolder.note= (TextView) convertView.findViewById(R.id.note);
            viewHolder.money= (TextView) convertView.findViewById(R.id.money);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        int id = user_dataList.get(position).getId();
        viewHolder.id.setText(""+id);
        viewHolder.num.setText(""+(position+1));
        viewHolder.date.setText(user_dataList.get(position).getTime());
        viewHolder.type.setText(user_dataList.get(position).isIncome()==1?"收入":"支出");
        viewHolder.money.setText(""+user_dataList.get(position).getMoney());
        viewHolder.note.setText(user_dataList.get(position).getNotes());
        return convertView;
    }
    class ViewHolder{
        TextView num,date,type,money,note,id;
    }
//    interface OnTransmitData{
//        void Transmit(int id,String date,String type,double money,String notes);
//    }
//    private OnTransmitData onTransmitData;
//    public void setOnTransmitData(OnTransmitData onTransmitData){
//        this.onTransmitData=onTransmitData;
//    }
}
