package com.guoguo.utils;

import android.os.AsyncTask;

import com.guoguo.logic.log.Log;

/**
 * Created by Administrator on 2015/11/4.
 */
public class AsyncTaskImp extends AsyncTask<Integer, Integer, Boolean>{
    private static final String TAG = "AsyncTaskImp";

    @Override
    protected Boolean doInBackground(Integer... params) {
        if (0 == params.length) {
            return false;
        }

        try {
            for (int i = 0; i < 3; i++) {
                Thread.sleep(1000);
                Log.error(TAG, "AsyncTask_" + String.valueOf(params[0]) + "_isRunning");

            }
        } catch (InterruptedException e) {
        } finally {
            Log.error(TAG, "AsyncTask_" + String.valueOf(params[0]) + "_finished");
        }

        return true;
    }


}
