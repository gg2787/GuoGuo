package com.guoguo.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;

/**
 * Created by Administrator on 2016/3/23.
 */
public class MyHandler {
    private static final int DELAY_TIME = 300;


    /*
    延迟执行
     */
    public void delayHandler(Context context) {
        //不传入looper,非UI线程会崩溃
        new Handler(context.getMainLooper()).postDelayed(new Runnable() {
            public void run() {
                //execute the task
                //do something
            }
        }, DELAY_TIME);

    }

    /*
    运行在ui线程
     */
    public void runInUiThread(Activity activity) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //execute the task
                //do something
            }
        });
    }
}
