package com.example.lanfe.cb_learnsystem.utils.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by gang on 18-1-24.
 */

public class DBHelper {
    private SQLiteDatabase db;
    private Cursor cursor;
    private DBOpenHelper helper;
    private String table;
    private Context context;

    private long A_DAY_MILLIS = 1000 * 60 * 60 * 24;
    private long A_WEEK_MILLIS = 1000 * 60 * 60 * 24 * 7;

    public DBHelper(Context context, String table) {
        Log.d("gang", "获取DBHelper实例");
        helper = new DBOpenHelper(context);
        this.table = table;
        this.context = context;
    }

    public void insert(ContentValues contentValues) {
        Log.d("gang", "DBHelper执行插入操作");
        db = helper.getReadableDatabase();
        Long systemTime = System.currentTimeMillis();
        switch (table) {
            case "remind":
                contentValues.put("nextAlarmTime", "" + contentValues.get("alarmTime"));
                contentValues.put("modifyTime", "" + systemTime);
                break;
            case "history":
                contentValues.put("isTest", 0);
                break;
        }
        contentValues.put("createTime", "" + systemTime);
        db.insert(table, null, contentValues);
        db.close();
    }

    public void delete(int id) {
        Log.d("gang", "DBHelper执行删除操作");
        db = helper.getReadableDatabase();
        String sql = "delete from " + table + " where id =" + id;
        db.execSQL(sql);
        db.close();
    }

    public void update(int id, String item, String change) {
        db = helper.getReadableDatabase();
        String sql = "UPDATE " + table + " SET " + item + " = " + change + " WHERE id = " + id;
        Log.d("gang", "DBHelper执行更新操作:" + sql);
        db.execSQL(sql);
        switch (table) {
            case "remind":
                if (item == "isOn" && change == "1") {
                    sql = "UPDATE " + table + " SET modifyTime = " + System.currentTimeMillis() + " WHERE id = " + id;
                    db.execSQL(sql);
                }
                break;
            case "history":
                break;
        }

        db.close();
    }

    //之后需要调用closeDB函数
    public Cursor search(String selection) {
        Log.d("gang", "DBHelper执行" + selection + "操作");
        db = helper.getReadableDatabase();
        cursor = db.query(table, null, selection, null, null, null, null);
        return cursor;
    }

    //之后需要调用closeDB函数
    public Cursor search() {
        Log.d("gang", "DBHelper执行查找整张表操作");
        db = helper.getReadableDatabase();
        cursor = db.query(table, null, null, null, null, null, null);
        return cursor;
    }

    public void closeDB() {
        Log.d("gang", "DBHelper关闭数据库");
        cursor.close();
        db.close();
    }

    //　更新该项数据的NextAlarmTime
    public void updateNextAlarmTime(int id) {
        long nextAlarmTime = 0;
        Cursor cursor = search("id == " + id);
        Log.d("gang", "on的个数" + cursor.getCount());
        if (cursor.moveToFirst()) {
            int model = cursor.getInt(1);
            long alarmTime = cursor.getLong(3);
            long modifyTime = cursor.getLong(6);
            switch (model) {
                case 1:
                    if (modifyTime > alarmTime) {
                        nextAlarmTime = alarmTime + A_DAY_MILLIS;
                        update(id, "nextAlarmTime", "" + nextAlarmTime);
                    } else
                        update(id, "isOn", "0");
                    break;
                case 2:
                    nextAlarmTime = alarmTime + A_DAY_MILLIS;
                    update(id, "nextAlarmTime", "" + nextAlarmTime);
                    break;
                case 3:
                    nextAlarmTime = alarmTime + A_WEEK_MILLIS;
                    update(id, "nextAlarmTime", "" + nextAlarmTime);
                    break;
            }
        }
        cursor.close();
        closeDB();
    }
}
