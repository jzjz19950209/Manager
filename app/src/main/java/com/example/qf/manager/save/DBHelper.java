package com.example.qf.manager.save;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by qf on 2016/9/8.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final String DBNAME="userInfo.db";
    public static final String USERTABLE="user";
    private static final int STARTVERSION=1;
    private static final int CURRENTVERSION=1;

    public DBHelper(Context context) {
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "+USERTABLE+"(username TEXT PRIMARY KEY,password TEXT);");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
