package com.example.qf.manager.Model;

import com.example.qf.manager.Model.Bean.User_data;

import java.util.List;

/**
 * Created by jzjz on 2017/4/15.
 */

public interface OnLoadUseTypeDataListener {
    void onResponse(List<String> list);
    void onFailure(String msg);
}
