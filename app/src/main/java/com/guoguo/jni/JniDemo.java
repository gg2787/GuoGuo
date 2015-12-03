package com.guoguo.jni;

/**
 * Created by HP on 2015/12/1.
 */
public class JniDemo {
//    static {
//        System.loadLibrary("hello");
//    }

    public native int sayHello();

    public static int jniSayHello() {
        System.loadLibrary("hello");
        int a = new JniDemo().sayHello();
        return a;
    }
}
