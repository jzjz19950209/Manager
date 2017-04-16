package com.example.qf.manager.Presenter;

import android.widget.Toast;

import com.example.qf.manager.Model.Bean.User_data;
import com.example.qf.manager.Model.OnFindLocalDataListener;
import com.example.qf.manager.Model.OnLoadUseTypeData;
import com.example.qf.manager.Model.OnLoadUseTypeDataImpl;
import com.example.qf.manager.Model.OnLoadUseTypeDataListener;
import com.example.qf.manager.View.IEditActivityView;
import com.example.qf.manager.list_Activity;

import java.util.List;

/**
 * Created by jzjz on 2017/4/15.
 */

public class FindUseTypePresenter {
    private OnLoadUseTypeData onLoadUseTypeData;
    private IEditActivityView iEditActivityView;

    public FindUseTypePresenter(IEditActivityView iEditActivityView) {
        this.iEditActivityView = iEditActivityView;
        onLoadUseTypeData=new OnLoadUseTypeDataImpl();
    }
    public void initData(int isIncome_type){
        onLoadUseTypeData.LoadUseTypeData(isIncome_type, new OnLoadUseTypeDataListener() {
            @Override
            public void onResponse(List<String> list) {
                iEditActivityView.UpdateData(list);
            }

            @Override
            public void onFailure(String msg) {

            }
        });

    }
}
