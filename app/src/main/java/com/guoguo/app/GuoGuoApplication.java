package com.guoguo.app;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.guoguo.logic.service.AppService;
import com.guoguo.pushClient.MyPushService;
import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import com.xiaomi.mipush.sdk.Logger;

import java.io.File;
import java.util.List;

/**
 * Created by Administrator on 2015/10/15.
 */
public class GuoGuoApplication extends Application{
    private static final String TAG = GuoGuoApplication.class.getSimpleName();
    public static Context mContext = null;

    public static GuoGuoApplication mInstance = null;

    public synchronized static GuoGuoApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        com.guoguo.logic.log.Log.error(TAG, "onCreate");
        super.onCreate();
        mContext = this;
        mInstance = this;

        LoggerInterface newLogger = new LoggerInterface() {

            @Override
            public void setTag(String tag) {
                // ignore
            }

            @Override
            public void log(String content, Throwable t) {
                Log.d(TAG, content, t);
            }

            @Override
            public void log(String content) {
                Log.d(TAG, content);
            }
        };
        Logger.setLogger(GuoGuoApplication.mContext, newLogger);
        init();
    }

    private void init() {
        if (isMainProcess()) {
            MyPushService.getInstance(mContext).initPushService();
            //AppService.StartService();
        }
    }

    public boolean isMainProcess() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }
}
