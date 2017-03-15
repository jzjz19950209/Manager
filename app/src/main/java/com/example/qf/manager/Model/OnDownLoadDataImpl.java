package com.example.qf.manager.Model;

import com.example.qf.manager.Model.Bean.User_data;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by jzjz on 2017/2/20.
 */

public class OnDownLoadDataImpl implements OnDownLoadData {

    @Override
    public void LoadData(final OnLoadDataListener onLoadDataListener) {
        BmobQuery<User_data> bmobQuery = new BmobQuery<>();
        bmobQuery.findObjects(new FindListener<User_data>() {
            @Override
            public void done(List<User_data> list, BmobException e) {
                if (e == null) {
                    onLoadDataListener.onResponse(list);
                }else {
                    onLoadDataListener.onFailure(e.getMessage());
                }
            }

        });
    }
}
