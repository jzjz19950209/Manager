package com.example.qf.manager.Model;

import com.example.qf.manager.UserMethodUtils;

import java.util.List;

/**
 * Created by jzjz on 2017/4/15.
 */

public class OnLoadUseTypeDataImpl implements OnLoadUseTypeData {

    @Override
    public void LoadUseTypeData(int isIncome_type, OnLoadUseTypeDataListener onLoadUseTypeDataListener) {
        List<String> list = UserMethodUtils.searchUseType(UserMethodUtils.sql, UserMethodUtils.currentUserName, isIncome_type);
        onLoadUseTypeDataListener.onResponse(list);
    }
}
