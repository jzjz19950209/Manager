package com.example.qf.manager.Model;

import com.example.qf.manager.Model.Bean.User_data;

import java.util.List;

/**
 * Created by jzjz on 2017/2/20.
 */

public interface OnLoadDataListener {

    void onResponse(List<User_data> list);
    void onFailure(String msg);
}
