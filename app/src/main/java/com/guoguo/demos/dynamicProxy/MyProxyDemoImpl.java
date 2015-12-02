package com.guoguo.demos.dynamicProxy;

import com.guoguo.logic.log.Log;

/**
 * Created by Administrator on 2015/12/2.
 */
public class MyProxyDemoImpl implements IMyProxyDemoInterface{
    private static final String TAG = MyProxyDemoImpl.class.getSimpleName();

    @Override
    public void myFunction() {
        Log.error(TAG, "do myFunction");
    }
}
