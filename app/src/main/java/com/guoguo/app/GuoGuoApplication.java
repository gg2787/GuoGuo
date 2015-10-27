package com.guoguo.app;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2015/10/15.
 */
public class GuoGuoApplication extends Application{
    public static Context mContext = null;

    public static GuoGuoApplication mInstance = null;

    public static GuoGuoApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        mInstance = this;
    }
}
