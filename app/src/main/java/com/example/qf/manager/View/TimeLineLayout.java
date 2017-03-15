package com.example.qf.manager.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.example.qf.manager.R;

/**
 * Created by qf on 2016/9/10.
 */
public class TimeLineLayout extends RelativeLayout {
    public TimeLineLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.timeline,this);
    }

}
