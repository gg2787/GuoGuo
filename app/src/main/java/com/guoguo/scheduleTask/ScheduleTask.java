package com.guoguo.scheduleTask;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

/**
 * Created by Administrator on 2015/9/26.
 */
public class ScheduleTask extends BroadcastReceiver {

    private static final long CHECK_UPDATE_FIRST_DELAY = 20 * 1000; // 20秒后

    public static final String BROKER_CLIENT_ID = "guoguo";

    public static final String ACTION_SCHEDULE = BROKER_CLIENT_ID + ".ACTION_SCHEDULE";
    //单例
    private static ScheduleTask sInstance = null;

    private Context mContext = null;

    public synchronized static ScheduleTask getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new ScheduleTask(context);
        }
        return sInstance;
    }

    private ScheduleTask(Context context) {
        mContext = context.getApplicationContext();
    }

    public static void StartScheduleTask(Context context) {
        Intent intent = new Intent(context, ScheduleTask.class);
        intent.setAction(ACTION_SCHEDULE);

        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);

        long firstTime = System.currentTimeMillis() + CHECK_UPDATE_FIRST_DELAY;
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
        alarmManager.set(AlarmManager.RTC_WAKEUP, firstTime, sender);
    }

    @Override
    public void onReceive(Context context, Intent intent) {


    }
}
