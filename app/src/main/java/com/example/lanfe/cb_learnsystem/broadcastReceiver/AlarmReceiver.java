package com.example.lanfe.cb_learnsystem.broadcastReceiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.lanfe.cb_learnsystem.MainActivity;
import com.example.lanfe.cb_learnsystem.R;
import com.example.lanfe.cb_learnsystem.utils.database.DBHelper;

/**
 * Created by lanfe on 2018/4/5.
 */

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //当系统到我们设定的时间点的时候会发送广播，执行这里
        Log.e("gang", "提醒时间到了");
        //当系统接收到通知时，可以通过震动、响铃、呼吸灯等多种方式进行提醒。
        //获得NotificationManager实例
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, null);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("提醒时间到了");
        builder.setContentText("快来学习哦!");
        builder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS);

        int flags = PendingIntent.FLAG_UPDATE_CURRENT;
        Intent in = new Intent(context, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context, 0, in, flags);

        builder.setContentIntent(pi);

        Notification notification = builder.build();

        notificationManager.notify(0, notification);

        //更新数据库
        DBHelper db = new DBHelper(context, "remind");
        int id = Integer.parseInt(intent.getStringExtra("id"));
        Log.d("gang", "更新的id：" + id);
        db.updateNextAlarmTime(id);
    }
}
