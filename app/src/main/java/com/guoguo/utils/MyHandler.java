package com.guoguo.utils;

import android.app.Activity;
import android.os.Handler;

/**
 * Created by Administrator on 2016/3/23.
 */
public class MyHandler {
    private static final int TIP_DELAY_TIME = 300;


    /*
    延迟执行
     */
    public void delayHandler() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                //execute the task
                //do something
            }
        }, TIP_DELAY_TIME);

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
