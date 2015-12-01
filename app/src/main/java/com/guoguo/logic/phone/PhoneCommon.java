package com.guoguo.logic.phone;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;

/**
 * Created by Administrator on 2015/12/1.
 */
public class PhoneCommon {
    public static final int 	UUID_LENGTH 		= 32;

    public static String getUUID(Context context) {
        if (context == null)
            return null;
        String phoneIMEI = getIMEI(context);
        int imeiLength = 0;
        if (phoneIMEI != null)
            imeiLength = phoneIMEI.length();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < UUID_LENGTH - imeiLength; i++) {
            builder.append('0');
        }
        if (phoneIMEI != null)
            builder.append(phoneIMEI);
        return builder.toString();
    }

    public static String getIMEI(Context context) {
        final TelephonyManager tm = (TelephonyManager)context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String phoneIMEI = tm.getDeviceId();
        if(phoneIMEI == null)
        {
            phoneIMEI = "";
        }
        return phoneIMEI;
    }

    public static String getVersionCode(Context context) {
        if (context == null )
            return null;
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            if(null != info){
                return Integer.toString(info.versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
        return "0";
    }

    public static String getVersionName(Context context) {
        if (context == null)
            return null;
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    static public String getAndroidID(Context context) {
        if (null == context) {
            return "";
        }
        try {
            ContentResolver cr = context.getContentResolver();
            return Settings.Secure.getString(cr, Settings.Secure.ANDROID_ID);
        } catch (Exception e) {
            return "";
        }
    }
}
