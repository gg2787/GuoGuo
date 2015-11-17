package com.guoguo.app;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import com.guoguo.pushClient.MyPushService;

import java.util.List;

/**
 * Created by Administrator on 2015/10/15.
 */
public class GuoGuoApplication extends Application{
    public static Context mContext = null;

    public static GuoGuoApplication mInstance = null;

    public synchronized static GuoGuoApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        mInstance = this;

        init();
    }

    private void init() {
        MyPushService.getInstance(mContext).initPushService();
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
