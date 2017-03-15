package com.example.qf.manager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.example.qf.manager.Model.Bean.Day;
import com.example.qf.manager.Model.Bean.User;
import com.example.qf.manager.Model.Bean.User_data;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by qf on 2016/8/27.
 */
public class UserMethodUtils {
    public static final String USERTABLE="user";
    public static final String USERDATA="user_data";
    public static SQLiteDatabase sql;
    public static String currentDate;

    public static String currentUserName;
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
    public static String getPath(){
        String storgePath=Environment.getExternalStorageDirectory().getAbsolutePath();
        String path=storgePath+"/"+"MyAccount";
        return path;
    }
    public static void createFile(String fileName){
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
    public static SQLiteDatabase CreateDataBase(String path){
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(path, null);
        sql=sqLiteDatabase;
        return sqLiteDatabase;
    }
    public static File getDatabasePath(String name) {
        // 判断是否存在sd卡
        boolean sdExist = Environment.MEDIA_MOUNTED
                .equals(Environment.getExternalStorageState());
        if (!sdExist) {
            return null;
        } else {// 如果存在
            // 获取sd卡路径
            String dbDir = UserMethodUtils.getPath();
            String dbPath = dbDir + "/" + name;// 数据库路径
            // 判断目录是否存在，不存在则创建该目录
            File dirFile = new File(dbDir);
            if (!dirFile.exists())
                dirFile.mkdirs();

            // 数据库文件是否创建成功
            boolean isFileCreateSuccess = false;
            // 判断文件是否存在，不存在则创建该文件
            File dbFile = new File(dbPath);
            if (!dbFile.exists()) {
                try {
                    isFileCreateSuccess = dbFile.createNewFile();// 创建文件
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                isFileCreateSuccess = true;
            }
            // 返回数据库文件对象
            if (isFileCreateSuccess)
                return dbFile;
            else {
                return null;
            }
        }
    }

    public static SQLiteDatabase CreateDataBase_user(){
        SQLiteDatabase db = CreateDataBase(getPath() + File.separator + "UserData.db");
        db.execSQL("CREATE TABLE IF NOT EXISTS "+USERTABLE+"(username TEXT ,password TEXT,phonenum TEXT);");
        return db;
    }

    public static SQLiteDatabase CreateDataBase_data(){
        SQLiteDatabase db = CreateDataBase(getPath() + File.separator + "UserData.db");
        db.execSQL("CREATE TABLE IF NOT EXISTS "+USERDATA+"(username TEXT  ,money NUMBER,notes TEXT,isIncome BOOLEAN,time TEXT);");
        return db;
    }

    public static boolean AddUser(SQLiteDatabase db, String userName, String password, String phoneNum){
        if (!searchUser(db,userName)){
            ContentValues values=new ContentValues();
            values.put("username",userName);
            values.put("password",password);
            values.put("phonenum",phoneNum);
            db.insert(USERTABLE, null, values);
            return true;
        }else {
            return false;
        }


    }
    public static void AddData(SQLiteDatabase db, String userName, double money, String notes, int isIncome, String time){
        ContentValues values=new ContentValues();
        values.put("username",userName);
        values.put("money",money);
        values.put("notes",notes);
        values.put("isIncome",isIncome);
        values.put("time",time);
        long insert = db.insert(USERDATA, null, values);

    }
    //登录使用
    public static boolean searchUser(Context context,SQLiteDatabase db,String username,String password){
        Cursor cursor=db.rawQuery("select * from "+USERTABLE+" where username = ? and password = ?",new String[]{username,password});
        if (cursor.getCount()>0){
            return true;
        }else {
            Toast.makeText(context, "用户不存在!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    //注册使用
    public static boolean searchUser(SQLiteDatabase db,String username){
        Cursor cursor=db.rawQuery("select * from "+USERTABLE+" where username = ?",new String[]{username});
        if (cursor.getCount()>0){
            return true;
        }else {

            return false;
        }
    }
    public static List<User_data> searchData(SQLiteDatabase db,String username){
        Cursor cursor=db.rawQuery("select * from " + USERDATA + " where username=? ",new String[]{username});
        if (cursor.getCount()>0){
            User_data user_data=null;
            List<User_data> list=new ArrayList<>();
            while (cursor.moveToNext()){
                String name=cursor.getString(cursor.getColumnIndex("username"));
                double money=cursor.getDouble(cursor.getColumnIndex("money"));
                String notes=cursor.getString(cursor.getColumnIndex("notes"));
                int isIncome=cursor.getInt(cursor.getColumnIndex("isIncome"));
                String time=cursor.getString(cursor.getColumnIndex("time"));
                user_data=new User_data();
                user_data.setUserName(name);
                user_data.setMoney(money);
                user_data.setIncome(isIncome);
                user_data.setNotes(notes);
                user_data.setTime(time);
                list.add(user_data);
            }
            cursor.close();
            return list;
        }else {
            cursor.close();
            return null;
        }

    }

    public static void DeleteData(SQLiteDatabase db){

    }
    public static void UpdateData(SQLiteDatabase db){

    }
    //重复次数
    public static int ContainTimes(List<Day> days, String day){
        int n=0;
        for (int i = 0; i < days.size(); i++) {
            if (day.equals(days.get(i).getDay_name())){
                n++;
            }
        }
        return n;
    }

    //去除重复
    public static List<Day> DeleteContain(List<Day> days){
        List<Day> temp=new ArrayList<>();
        temp.addAll(days);
        for (int i = 0; i < temp.size(); i++) {
            for (int j = temp.size()-1; j >i ; j--) {
                if (temp.get(i).getDay_name().equals(temp.get(j).getDay_name())){
                    temp.remove(j);
                }
            }
        }
        return temp;
    }
}
