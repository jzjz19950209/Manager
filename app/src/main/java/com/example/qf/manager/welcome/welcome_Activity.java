package com.example.qf.manager.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import com.example.qf.manager.login_Activity;
import com.example.qf.manager.R;

public class welcome_Activity extends AppCompatActivity {
    private ViewPager viewPager;
    private int currentposition;
    private int prestate,preposition=0;
    private LinearLayout dotLayout;
    private int[] imgs = new int[]{R.drawable.p95,R.drawable.p96,R.drawable.p97};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        viewPager= (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new MyWelcomeAdapter(imgs,this));
        dotLayout= (LinearLayout) findViewById(R.id.dotLayout);
        addDot();
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {

                currentposition=position;
                dotLayout.getChildAt(preposition).setEnabled(false);
                dotLayout.getChildAt(position).setEnabled(true);
                preposition=position;
            }
            @Override
            public void onPageScrollStateChanged(int state) {
                if(currentposition==imgs.length-1&&prestate==1&&state==0){
                    startActivity(new Intent(welcome_Activity.this, login_Activity.class));
                    welcome_Activity.this.finish();
                }
                prestate=state;
            }
        });
    }
    private void addDot(){
        for(int i=0;i<imgs.length;i++){
            View view=new View(this);
            LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(
                    (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,10,getResources().getDisplayMetrics()),
                    (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,10,getResources().getDisplayMetrics()));
            lp.leftMargin=20;
            view.setLayoutParams(lp);
            view.setEnabled(false);
            view.setBackgroundResource(R.drawable.dot_bg);
            dotLayout.addView(view);
        }
        dotLayout.getChildAt(0).setEnabled(true);
    }

}
