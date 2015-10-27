package com.guoguo.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.guoguo.utils.PrefUtil;

/**
 * Created by Administrator on 2015/9/17.
 */
public class AppPrefs {
    private static SharedPreferences servicePref = null;

    private static boolean mbInit = false;

    public static final String PREFS_NAME = "PrefsName";
    public static final String SHORT_CUT_ADD = "ShortCutAdd";

    public static void init(Context context) {
        servicePref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        mbInit = true;
    }

    public static boolean getBoolean(String keyString) {
        return PrefUtil.getBoolean(servicePref, keyString);
    }

    public static void setBoolean(String keyString, boolean value) {
        PrefUtil.setBoolean(servicePref, keyString, value);
    }

}
