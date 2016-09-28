package com.example.qf.manager.save;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by qf on 2016/9/20.
 */
public class DBHelper_Data extends SQLiteOpenHelper {
    private static final String DATANAME="userdata.db";
    public static final String DATATABLE="userdata";
    private static final int STARTVERSION=1;
    private static final int CURRENTVERSION=1;
    public DBHelper_Data(Context context) {
        super(context, DATANAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "+DATATABLE+
                "(date TEXT PRIMARY KEY,tag TEXT,money TEXT);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
