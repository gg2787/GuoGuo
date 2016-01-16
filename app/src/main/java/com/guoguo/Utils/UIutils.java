package com.guoguo.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/16.
 */
public class UIutils {

    public static boolean isFore(Context mContext) {
        ActivityManager act_mgr = (ActivityManager)mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasksInfo = act_mgr.getRunningTasks(1);
        if (tasksInfo != null && tasksInfo.size() > 0) {
            ActivityManager.RunningTaskInfo topTask = tasksInfo.get(0);
            if (topTask != null) {
                String pkg_name = topTask.topActivity.getPackageName();
                if (pkg_name != null && pkg_name.equals(mContext.getPackageName())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断当前界面是否是桌面
     */
    public static boolean isHome(Context context) {
        boolean isHome = false;
        List<String> homePackageNames = getHomes(context);
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> rti = mActivityManager.getRunningTasks(1);
        if (rti != null && rti.size() > 0) {
            if (rti.get(0) != null && rti.get(0).topActivity != null) {
                String packageName = rti.get(0).topActivity.getPackageName();
                if (packageName != null) {
                    isHome = homePackageNames.contains(packageName);
                }
            }
        }
        return isHome;
    }

    /**
     * 获得属于桌面的应用的应用包名称
     *
     * @return 返回包含所有包名的字符串列表
     */
    private static List<String> getHomes(Context context) {
        List<String> names = new ArrayList<String>();
        PackageManager packageManager = context.getPackageManager();
        // 属性
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        List<ResolveInfo> resolveInfo = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo ri : resolveInfo) {
            names.add(ri.activityInfo.packageName);
        }
        return names;
    }

    private static SpannableString changeDefaultTextColorToWhite(CharSequence source) {
        SpannableString style = new SpannableString(source);
        for (int i = 0; i < style.length(); i++) {
            ForegroundColorSpan[] spans = style.getSpans(i, i + 1, ForegroundColorSpan.class);
            if (spans.length == 0) {
                style.setSpan(new ForegroundColorSpan(Color.WHITE), i, i + 1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            }
        }
        return style;
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int[] getScreenSize(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return new int[] { metrics.widthPixels, metrics.heightPixels };
    }
}
