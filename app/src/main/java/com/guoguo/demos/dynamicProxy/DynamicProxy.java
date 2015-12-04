package com.guoguo.demos.dynamicProxy;

import com.guoguo.logic.log.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by Administrator on 2015/12/2.
 */
public class DynamicProxy implements InvocationHandler {
    private static final String TAG = DynamicProxy.class.getSimpleName();
    private Object target;

    public Object bind(Object target) {
        this.target = target;
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Object result = null;
        Log.error(TAG, "do things before");
        result = method.invoke(target, args);
        Log.error(TAG, "do things after");
        return result;
    }
}
