package com.example.qf.manager.Model;

import com.example.qf.manager.Model.Bean.User_data;
import com.example.qf.manager.UserMethodUtils;

import java.util.List;

/**
 * Created by jzjz on 2017/3/7.
 */

public class OnFindLocalDataImpl implements OnFindLocalData {
    @Override
    public void FindLocalData(OnFindLocalDataListener onFindLocalDataListener) {
        List<User_data> user_datas = UserMethodUtils.searchData(UserMethodUtils.sql, UserMethodUtils.currentUserName);
        if (user_datas!=null){
            onFindLocalDataListener.onResponse(user_datas);
        }else {
            onFindLocalDataListener.onFailure("没有本地数据!");
        }

    }
}
