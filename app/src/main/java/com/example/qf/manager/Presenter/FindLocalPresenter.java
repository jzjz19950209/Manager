package com.example.qf.manager.Presenter;

import android.util.Log;
import android.widget.Toast;

import com.example.qf.manager.Model.Bean.User_data;
import com.example.qf.manager.Model.OnFindLocalData;
import com.example.qf.manager.Model.OnFindLocalDataImpl;
import com.example.qf.manager.Model.OnFindLocalDataListener;
import com.example.qf.manager.View.IListActivityView;
import com.example.qf.manager.list_Activity;

import java.util.List;

/**
 * Created by jzjz on 2017/3/7.
 */

public class FindLocalPresenter {
    private OnFindLocalData onFindLocalData;
    private IListActivityView iListActivityView;

    public FindLocalPresenter(IListActivityView iListActivityView) {
        this.iListActivityView = iListActivityView;
        onFindLocalData=new OnFindLocalDataImpl();
    }
    public void initData(){
        onFindLocalData.FindLocalData(new OnFindLocalDataListener() {
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
