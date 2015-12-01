package com.guoguo.jni;

/**
 * Created by HP on 2015/12/1.
 *����һ�����java��
 * �������ʹ��javac��exe���� Java ���룬���JniDemo.class�ļ���
 * C:\Windows\system32>D:\Java\jdk1.7.0_79\bin\javac E:\GuoGuo\app\src\main\java\co
 m\guoguo\jni\jnidemo.java
 ������ʹ��javah.exe���� C/C++ ͷ�ļ�
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
