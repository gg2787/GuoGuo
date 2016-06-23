package com.guoguo.callActive;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;

import java.util.List;

/**
 * Created by Administrator on 2016/6/1.
 */
public class CallActive {

    private void startCmService(Context context) {
//        Intent intent = new Intent("com.cleanmaster.service.PermanentService.action");
//        intent.setPackage("com.cleanmaster.mguard_cn");
        Intent intent = new Intent("ks.cm.antivirus.defend.DefendService.RESTART");
        intent.setPackage("com.cleanmaster.security_cn");
        intent.putExtra("source", context.getPackageName());
        context.startService(intent);
    }

    private void startWifiKeyService(Context context) {
        Intent intent = new Intent("wifi.intent.action.STICKY_SERVICE");
        intent.setPackage("com.snda.wifilocating");
        intent.putExtra("source", context.getPackageName());
        context.startService(intent);
        new Thread(new Runnable() {
            @Override
            public void run() {

            }
        }).start();
    }

//    private void startTest() {
//        ComponentName name = new ComponentName("com.ijinshan.browser_fast", "com.ijinshan.browser.service.LiebaoPush");
//        Intent intent = new Intent("lbps.action.KEEP_PUSHALIVE");
//        intent.setComponent(name);
//        intent.putExtra("source", 1);
//        startService(intent);
//
//    }
//
//    private void getRunningService() {
//        ActivityManager activityManager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
//        List<ActivityManager.RunningServiceInfo> list = activityManager.getRunningServices(MAX_NUM); //可以使用
//        List<ActivityManager.RunningAppProcessInfo> list2 = activityManager.getRunningAppProcesses();  //有坑
//        return;
//    }
//
//    public List<ResolveInfo> getBrowserList() {
//
//        PackageManager pm = this.getPackageManager();
//
//        Intent browserIntent = new Intent(Intent.ACTION_VIEW);
//        browserIntent.addCategory(Intent.CATEGORY_BROWSABLE);
//        browserIntent.setData(Uri.parse(CM_URL));
//        List<ResolveInfo> resolveinfo = pm.queryIntentActivities(browserIntent, PackageManager.MATCH_DEFAULT_ONLY);//隐式启动的activity必须有default category，除非是action_main
////        filterBrowserList(resolveinfo);
//        return resolveinfo;
//    }
}
