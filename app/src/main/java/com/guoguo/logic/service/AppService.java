package com.guoguo.logic.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.guoguo.app.GuoGuoApplication;
import com.guoguo.logic.log.Log;

/**
 * Created by Administrator on 2015/11/19.
 * 服务，运行在AppService进程里
 */
public class AppService extends Service{
    private static final String TAG = AppService.class.getSimpleName();

    public static void StartService() {
        //在主进程中被调用
        if (null == GuoGuoApplication.mContext) {
            return;
        }

        //启动服务
//        Intent intent = new Intent();
//        intent.setAction("com.guoguo.logic_action_AppService");

        Intent intent = new Intent(GuoGuoApplication.mContext, AppService.class);
        GuoGuoApplication.mContext.startService(intent);
    }

    public static void StopService() {
        Intent intent = new Intent(GuoGuoApplication.mContext, AppService.class);
        GuoGuoApplication.mContext.stopService(intent);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.error(TAG, "onBind");
        return null;
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
        Log.error(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }



}

