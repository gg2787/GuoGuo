package com.example.administrator.guoguo.Utils;

import android.content.SharedPreferences;

/**
 * Created by Administrator on 2015/9/29.
 */
public class PrefUtil {
    public static boolean getBoolean(SharedPreferences servicePref, String strKey) {
        if (null != servicePref) {
            return servicePref.getBoolean(strKey, false);
        }
        return false;
    }

    public static void setBoolean(SharedPreferences servicePref, String strKey, boolean value) {
        if (null != servicePref) {
            servicePref.edit().putBoolean(strKey, value).commit();
        }
    }

    public static void setString(SharedPreferences servicePref, String strKey, String strValue) {
        if (null != servicePref) {
            servicePref.edit().putString(strKey, strValue).commit();
        }
    }
}
