package com.guoguo.log;

import android.content.Context;
import android.content.SharedPreferences;

import com.guoguo.Utils.PrefUtil;

import java.util.Calendar;

/**
 * Created by Administrator on 2015/9/29.
 */
public class Log {
    private static SharedPreferences servicePref = null;
    private static String LOG_FILE_NAME = "LogFileName";

    public static void init(Context context) {
        servicePref = context.getSharedPreferences(LOG_FILE_NAME, Context.MODE_PRIVATE);
    }

    public static void error (String strTag, String strLabel, String strContent) {
        Calendar calCur = Calendar.getInstance();
        String strTime = calCur.toString();
        PrefUtil.setString(servicePref, strTime + "_" + strTag + "_" + strLabel, strContent);
    }

    //获取进程名的代码
       /* int pid = android.os.Process.myPid();
        String strProName = null;
        ActivityManager mActivityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager
                .getRunningAppProcesses()) {
            if (appProcess.pid == pid) {

                strProName =  appProcess.processName;
                break;
            }
        }
        String strLogPid = strLog + ",进程名：" + strProName + ",context:" + mContext.toString();
        Log.error(TAG, strLogPid);*/
}
