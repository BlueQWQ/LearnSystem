package com.example.lanfe.cb_learnsystem.fragment;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.lanfe.cb_learnsystem.R;
import com.example.lanfe.cb_learnsystem.broadcastReceiver.AlarmReceiver;
import com.example.lanfe.cb_learnsystem.utils.FormatTime;
import com.example.lanfe.cb_learnsystem.utils.database.DBHelper;

import java.text.ParseException;
import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by lanfe on 2018/4/5.
 */

public class FragmentRemind extends Fragment {

    public long MAX_MILLIS = 6666666666666L;
    Handler handler = new Handler();
    private View view;
    private int id = -1;
    //设置switch监听
    private CompoundButton.OnCheckedChangeListener switch_Listener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            int id = (int) buttonView.getTag(R.id.remind_id);
            if (isChecked) {
                //Toast.makeText(buttonView.getContext(), "开启了提醒", Toast.LENGTH_SHORT).show();
                DBHelper db = new DBHelper(buttonView.getContext(), "remind");
                db.update(id, "isOn", "1");
            } else {
                //Toast.makeText(buttonView.getContext(), "关闭了提醒", Toast.LENGTH_SHORT).show();
                DBHelper db = new DBHelper(buttonView.getContext(), "remind");
                db.update(id, "isOn", "0");
            }
            //关闭提醒，再从数据库中获取最近的提醒
            stopRemind();
            startRemind();
        }
    };
    //设置alarmTime监听
    private TextView.OnClickListener alarmTime_listener = new TextView.OnClickListener() {
        Calendar calendar = Calendar.getInstance();

        @Override
        public void onClick(View v) {
            //找到当前的TextView
            final TextView textView = v.findViewById(v.getId());
            //从Tag获取TextView的数据库id
            final int id = (int) textView.getTag(R.id.remind_id);
            new TimePickerDialog(view.getContext(), new TimePickerDialog.OnTimeSetListener() {
                long alarmTime = 0;
                long systemTime = System.currentTimeMillis();

                @Override
                public void onTimeSet(final TimePicker view, final int hourOfDay, final int minute) {
                    try {
                        alarmTime = FormatTime.toMillisTime(systemTime, hourOfDay, minute);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    //更新数据库
                    DBHelper db = new DBHelper(view.getContext(), "remind");
                    db.update(id, "alarmTime", "" + alarmTime);
                    loadingView();
                    stopRemind();
                    startRemind();
                }
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
        }
    };
    //设置model监听
    private TextView.OnClickListener model_listener = new TextView.OnClickListener() {
        @Override
        public void onClick(View v) {
            //找到当前的TextView
            final TextView textView = v.findViewById(v.getId());
            //从Tag获取TextView的数据库id
            final int id = (int) textView.getTag(R.id.remind_id);
            final String[] items = {"仅提醒一次", "每天提醒一次", "每周提醒一次"};
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    int model = i + 1;
                    //更新数据库
                    DBHelper db = new DBHelper(view.getContext(), "remind");
                    db.update(id, "model", "" + model);
                    dialogInterface.cancel();
                    loadingView();
                }
            });
            builder.setTitle("选择提醒模式");
            builder.setCancelable(true);
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    };
    //长按监听
    private View.OnLongClickListener items_listener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(final View v) {
            final View list_remind = v.findViewById(v.getId());
            //从Tag获取LinearLayout的数据库id
            final int id = (int) list_remind.getTag(R.id.remind_id);
            new AlertDialog.Builder(v.getContext())
                    .setMessage("确定删除吗？")
                    .setPositiveButton("确定", new AlertDialog.OnClickListener() {

                        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DBHelper db = new DBHelper(v.getContext(), "remind");
                            db.delete(id);
                            loadingView();
                        }
                    })
                    .setNegativeButton("取消", null)
                    .show();
            return true;
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.remind_content, null);
        //添加floating按钮的监听
        floatingActionButtonListener();
        return view;
    }

    @Override
    public void onResume() {
        //从数据库更新页面
        loadingView();
        super.onResume();
    }

    // 从数据库加载remind表中的数据
    public void loadingView() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                //获取LinearLayout实例
                LinearLayout linearLayout = view.findViewById(R.id.remind_linearLayout);
                linearLayout.removeAllViews();
                //连接数据库取得数据
                DBHelper db = new DBHelper(view.getContext(), "remind");
                Cursor cursor = db.search();
                //若存在数据则遍历remind表
                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast()) {
                        int id = cursor.getInt(0);
                        int model = cursor.getInt(1);
                        int isOn = cursor.getInt(2);
                        long alarmTime = cursor.getLong(3);
                        View list_remind = View.inflate(view.getContext(), R.layout.remind_item, null);
                        TextView model_tv = list_remind.findViewById(R.id.remind_model_tv);
                        TextView alarmTime_tv = list_remind.findViewById(R.id.remind_alarmTime_tv);
                        Switch switch_btn = list_remind.findViewById(R.id.remind_switch_btn);
                        LinearLayout items = list_remind.findViewById(R.id.list_remind);

                        //设置长按监听
                        items.setOnLongClickListener(items_listener);
                        alarmTime_tv.setOnLongClickListener(items_listener);
                        model_tv.setOnLongClickListener(items_listener);

                        //添加items按钮的Tag
                        items.setTag(R.id.remind_id, id);

                        //设置switch_btn的内容
                        switch (isOn) {
                            case 1:
                                switch_btn.setChecked(true);
                                break;
                            case 0:
                                switch_btn.setChecked(false);
                                break;
                        }
                        //添加switch_btn按钮的监听
                        //添加switch_btn按钮的Tag
                        switch_btn.setTag(R.id.remind_id, id);
                        switch_btn.setOnCheckedChangeListener(switch_Listener);

                        //设置alarmTime_tv的内容
                        alarmTime_tv.setText(FormatTime.formatTime(alarmTime, "HH:mm"));
                        //添加alarmTime_tv按钮的监听
                        //添加alarmTime_tv按钮的Tag
                        alarmTime_tv.setTag(R.id.remind_id, id);
                        alarmTime_tv.setOnClickListener(alarmTime_listener);

                        //设置model_tv的内容
                        switch (model) {
                            case 1:
                                model_tv.setText("提醒一次");
                                break;
                            case 2:
                                model_tv.setText("每天提醒");
                                break;
                            case 3:
                                model_tv.setText("每周提醒");
                                break;
                        }
                        //添加model_tv按钮的监听
                        //添加model_tv按钮的Tag
                        model_tv.setTag(R.id.remind_id, id);
                        model_tv.setOnClickListener(model_listener);
                        //为每个控件生成一个不同的id
                        model_tv.setId(View.generateViewId());
                        alarmTime_tv.setId(View.generateViewId());
                        switch_btn.setId(View.generateViewId());
                        list_remind.setId(View.generateViewId());
                        //在linearLayout中增加一个list_remind
                        linearLayout.addView(list_remind);
                        //cursor指针+1
                        cursor.moveToNext();
                    }
                } else {
                    View welcome = View.inflate(view.getContext(), R.layout.remind_welcome, null);
                    linearLayout.addView(welcome);
                }
                cursor.close();
                db.closeDB();
            }
        });

    }

    //添加一个新的提醒到数据库
    private void floatingActionButtonListener() {
        FloatingActionButton floatingActionButton = (FloatingActionButton) view.findViewById(R.id.remind_floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Calendar calendar = Calendar.getInstance();
                new TimePickerDialog(view.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(final TimePicker view, final int hourOfDay, final int minute) {
                        String times = hourOfDay + "时" + minute + "分";
                        //Toast.makeText(view.getContext(), "选择的时间" + times, Toast.LENGTH_SHORT).show();
                        final String[] items = {"仅提醒一次", "每天提醒一次", "每周提醒一次"};
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                        builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Toast.makeText(view.getContext(), "你选择了 " + items[i], Toast.LENGTH_SHORT).show();
                                int model = i + 1;
                                long alarmTime = 0;
                                long systemTime = System.currentTimeMillis();
                                try {
                                    alarmTime = FormatTime.toMillisTime(systemTime, hourOfDay, minute);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                DBHelper db = new DBHelper(view.getContext(), "remind");
                                ContentValues cValue = new ContentValues();
                                cValue.put("model", model);
                                cValue.put("isOn", 1);
                                cValue.put("alarmTime", alarmTime);
                                db.insert(cValue);
                                //Log.d("设置的提醒时间为：", FormatTime.formatTime(alarmTime,"yyyyMMddHHmm"));
                                //Log.d("当时的系统时间为：", FormatTime.formatTime(systemTime,"yyyyMMddHHmm"));
                                //重载remind界面
                                loadingView();
                                stopRemind();
                                startRemind();
                                dialogInterface.cancel();
                            }
                        }).setCancelable(true);
                        AlertDialog dialog = builder.create();
                        dialog.show();

                    }
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
            }
        });

    }

    //开启提醒
    public void startRemind() {
        long systemTime = System.currentTimeMillis();//获取当前毫秒值
        long alarmTime = getNextAlarmTime();//获取设置的毫秒值

        if (alarmTime == MAX_MILLIS) {
            Toast.makeText(view.getContext(), "无提醒", Toast.LENGTH_SHORT).show();
            Log.d("gang", "无提醒");
            return;
        }

        // 如果当前时间大于设置的时间，那么就从更新当前时间在数据库中的下次提醒时间
        if (systemTime > alarmTime) {
            DBHelper db = new DBHelper(view.getContext(), "remind");
            db.updateNextAlarmTime(id);
            startRemind();
            return;
        }
        Toast.makeText(view.getContext(), "下次闹钟时间为" + FormatTime.formatTime(alarmTime, "yyyy-MM-dd HH:mm"), Toast.LENGTH_SHORT).show();
        Log.d("gang", "下次闹钟时间为" + FormatTime.formatTime(alarmTime, "yyyy-MM-dd HH:mm"));
        Log.d("gang", "系统时间为" + System.currentTimeMillis());
        Log.d("gang", "提醒时间为" + alarmTime);
        //AlarmReceiver.class为广播接受者
        Intent intent = new Intent(view.getContext(), AlarmReceiver.class);
        intent.putExtra("id", "" + id);
        PendingIntent pi = PendingIntent.getBroadcast(view.getContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //得到AlarmManager实例
        AlarmManager am = (AlarmManager) view.getContext().getSystemService(ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, alarmTime, pi);
        loadingView();
    }

    //关闭提醒
    public void stopRemind() {
        Intent intent = new Intent(view.getContext(), AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(view.getContext(), 0,
                intent, 0);

        AlarmManager am = (AlarmManager) view.getContext().getSystemService(ALARM_SERVICE);
        //取消警报
        assert am != null;
        am.cancel(pi);
        loadingView();
    }

    //查询数据库中的下个提醒时间
    private long getNextAlarmTime() {
        DBHelper db = new DBHelper(view.getContext(), "remind");
        Cursor cursor = db.search("isOn == 1 ");
        //Log.d("gang",""+cursor.getCount());
        long minTime = MAX_MILLIS;
        long nextAlarmTime;
        int i = 0;
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                i = cursor.getInt(0);
                nextAlarmTime = cursor.getLong(5);
                if (nextAlarmTime < minTime) {
                    minTime = nextAlarmTime;
                    id = i;
                    //Log.d("gang","id = "+id);
                }
                //cursor指针+1
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.closeDB();
        return minTime;
    }

}