package com.guoguo.utils;

/**
 * Created by Administrator on 2016/3/23.
 */
public class SyncClass {
    private Object mObjLock = new Object();

    private void syncMethord() {
        synchronized (mObjLock) {
            //do something
        }
    }

}
