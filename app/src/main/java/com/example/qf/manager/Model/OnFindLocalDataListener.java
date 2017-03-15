package com.example.qf.manager.Model;

import com.example.qf.manager.Model.Bean.User;
import com.example.qf.manager.Model.Bean.User_data;

import java.util.List;

/**
 * Created by jzjz on 2017/3/7.
 */

public interface OnFindLocalDataListener {
    void onResponse(List<User_data> list);
    void onFailure(String msg);
}
