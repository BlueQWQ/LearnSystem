package com.example.lanfe.cb_learnsystem.utils.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by gang on 18-1-24.
 */

public class DBOpenHelper extends SQLiteOpenHelper {

    private static String name = "learnSystem.db"; //表示数据库的名称
    //private static int version = 1; //表示数据库的版本号
    private static int version = 2; //更新数据库的版本号，此时会执行 onUpgrade()方法

    public DBOpenHelper(Context context) {
        super(context, name, null, version);
        // TODO Auto-generated constructor stub
    }

    //当数据库创建的时候，是第一次被执行，完成对数据库的表的创建
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // TODO Auto-generated method stub
        //SQLite 数据创建支持的数据类型： 整型数据，字符串类型，日期类型，二进制的数据类型
        //数据库这边有一个特点，就是SQLite数据库中文本类型没有过多的约束，也就是可以把布尔类型的数据存储到文本类型中，这样也是可以的
        String sql = "CREATE TABLE IF NOT EXISTS remind(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "model NUMERIC(1) CHECK(model == 1 OR model == 2 OR model == 3)," +
                "isOn NUMERIC(1) CHECK(isOn == 0 OR isOn == 1)," +
                "alarmTime INTEGER(13)," +
                "createTime INTEGER(13)," +
                "nextAlarmTime INTEGER(13)," +
                "modifyTime INTEGER(13))";
        Log.d("gang", "建立remind表");
        sqLiteDatabase.execSQL(sql);

        sql = "CREATE TABLE IF NOT EXISTS history(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "translate TEXT(255)," +
                "createTime INTEGER(13)," +
                "isTest NUMERIC(1) CHECK(isTest == 0 OR isTest == 1))";
        Log.d("gang", "建立history表");
        sqLiteDatabase.execSQL(sql); //完成数据库的创建
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }

}
