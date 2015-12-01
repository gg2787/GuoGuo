package com.guoguo.logic.service;

import android.os.Binder;

import com.guoguo.logic.log.Log;

/**
 * Created by HP on 2015/11/24.
 */
public class MyBinder extends Binder {
    public void doSomething() {
        Log.error("MyBinder", "MyBinder_doSomething");
    }
}
