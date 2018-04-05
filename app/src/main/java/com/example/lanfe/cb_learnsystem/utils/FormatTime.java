package com.example.lanfe.cb_learnsystem.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by lanfe on 2018/4/5.
 */

public class FormatTime {
    //将毫秒值格式化为format
    public static String formatTime(long millisTime, String format) {
        Calendar c = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat(format);
        c.setTimeInMillis(millisTime);
        Date date = c.getTime();
        return "" + sdf.format(date);
    }

    //将得到的提醒时间转换为毫秒
    @SuppressLint("SimpleDateFormat")
    public static long toMillisTime(long millisTime, int hours, int minutes) throws ParseException {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(millisTime);
        Date date = c.getTime();
        String hours_str, minutes_str;
        if (hours < 10)
            hours_str = "0" + hours;
        else
            hours_str = "" + hours;
        if (minutes < 10)
            minutes_str = "0" + minutes;
        else
            minutes_str = "" + minutes;

        String str = new SimpleDateFormat("yyyyMMdd").format(date) + hours_str + minutes_str;
        c.setTime(new SimpleDateFormat("yyyyMMddHHmm").parse(str));
        return c.getTimeInMillis();
    }
}
