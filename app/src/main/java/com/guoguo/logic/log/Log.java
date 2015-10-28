package com.guoguo.logic.log;

/**
 * Created by Administrator on 2015/9/29.
 */
public class Log {
    public static void error(String tag, String text) {
            android.util.Log.e(tag, " " + text);
    }

    public static void error(String tag, String format, Object... args) {
        String text = format;
        if (args.length > 0)
            text = String.format(format, args);
        error(tag, text);
    }
}
