package com.guoguo.logic.service;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.guoguo.app.GuoGuoApplication;
import com.guoguo.logic.log.Log;

/**
 * Created by Administrator on 2015/11/19.
 * 服务，运行在AppService进程里
 */
public class AppService extends Service{
    private static final String TAG = AppService.class.getSimpleName();
    private MyBinder mBinder = new MyBinder();

    public static void startService() {
        //在主进程中被调用
        if (null == GuoGuoApplication.mContext) {
            return;
        }

        //启动服务
//        Intent intent = new Intent();
//        intent.setAction("com.guoguo.logic_action_AppService");
        Log.error(TAG, "startService");
        Intent intent = new Intent(GuoGuoApplication.mContext, AppService.class);
        GuoGuoApplication.mContext.startService(intent);
    }

    public static void stopService() {
        Log.error(TAG, "stopService");
        Intent intent = new Intent(GuoGuoApplication.mContext, AppService.class);
        GuoGuoApplication.mContext.stopService(intent);
    }

    public static void bindMyService(ServiceConnection conn) {
        Log.error(TAG, "bindMyService");
        Intent intent = new Intent(GuoGuoApplication.mContext, AppService.class);
        GuoGuoApplication.mContext.bindService(intent, conn, BIND_AUTO_CREATE);
    }

    public static void unbindMyService(ServiceConnection conn) {
        Log.error(TAG, "unbindMyService");
        GuoGuoApplication.mContext.unbindService(conn);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.error(TAG, "onBind");
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.error(TAG, "onCreate");
    }

    @Override
    public void onDestroy() {
        super.onCreate();
        Log.error(TAG, "onDestroy");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.error(TAG, "onStartCommand" + "_flags:" + String.valueOf(flags) + "_startId:" + String.valueOf(startId));
        return super.onStartCommand(intent, flags, startId);
    }



}

