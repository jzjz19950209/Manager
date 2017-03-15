package com.example.qf.manager.Presenter;

import android.widget.Toast;

import com.example.qf.manager.Model.Bean.User_data;
import com.example.qf.manager.Model.OnDownLoadData;
import com.example.qf.manager.Model.OnDownLoadDataImpl;
import com.example.qf.manager.Model.OnLoadDataListener;
import com.example.qf.manager.View.IListActivityView;
import com.example.qf.manager.list_Activity;

import java.util.List;

/**
 * Created by jzjz on 2017/3/6.
 */

public class DownLoadPresenter {
    private OnDownLoadData onDownLoadData;
    private IListActivityView iListActivityView;

    public DownLoadPresenter(IListActivityView iListActivityView) {
        this.iListActivityView = iListActivityView;
        onDownLoadData=new OnDownLoadDataImpl();
    }
    public void initData(){
        onDownLoadData.LoadData(new OnLoadDataListener() {
            @Override
            public void onResponse(List<User_data> list) {
                iListActivityView.UpdateData(list);
            }

            @Override
            public void onFailure(String msg) {
                Toast.makeText(((list_Activity) iListActivityView), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
