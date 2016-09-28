package com.example.qf.manager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by qf on 2016/8/27.
 */
public class UserMethod {


    public void ShowDialog(final Context context){
        //弹出对话框，通过AlertDialog.Builder创建出AlertDialog实例
        AlertDialog.Builder dialog=new AlertDialog.Builder(context);
        //设置标题
        dialog.setTitle("您是否退出？");
        //设置信息
        dialog.setMessage("此操作回到登录界面！");
        //是否可取消
        dialog.setCancelable(true);
        //设置确定按钮点击事件
        dialog.setPositiveButton("退出",new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((Activity) context).finish();
            }
        });
        dialog.setNegativeButton("取消",new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.show();
    }
    public String getPath(){
        String storgePath=String.valueOf(Environment.getExternalStorageDirectory());
        String path=storgePath+"/"+"myapp_for_hxf";
        return path;
    }
    public void createFile(String dirName,String fileName){
        String str=getPath();
        try {
            new File(str).mkdirs();
            File file=new File(str,fileName);
            if(!file.exists()){
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String getTime(){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd EEE");
        return sdf.format(new Date());
    }
    public void setYear(List<String> list, String str){
        list.add("  "+str+"年");
    }






}
