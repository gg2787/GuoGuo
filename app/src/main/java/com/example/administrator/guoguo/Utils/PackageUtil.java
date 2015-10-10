package com.example.administrator.guoguo.Utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;

/**
 * Created by Administrator on 2015/9/30.
 */
public class PackageUtil {

    public static String getLauncherPackageName(Context context) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        ResolveInfo res = context.getPackageManager().resolveActivity(intent, 0);//从所有已安装的应用中，获取到与intent相符合的activity
        if (res.activityInfo == null) {
            return "";
        }
        return res.activityInfo.packageName;//获取到这个activity所在的packagename,这里获取的是launcher特有的activity
    }
}
