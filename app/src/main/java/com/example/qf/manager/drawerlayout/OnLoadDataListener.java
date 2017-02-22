package com.example.qf.manager.drawerlayout;

import java.util.List;

/**
 * Created by jzjz on 2017/2/20.
 */

public interface OnLoadDataListener {

    void onResponse(List<Date> list);
    void onFaile(String msg);
}
