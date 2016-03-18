package com.guoguo.utils;

import android.app.ActivityManager;
import android.content.Context;

import com.guoguo.bean.TestBean;
import com.guoguo.logic.log.Log;

import org.w3c.dom.UserDataHandler;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2015/10/28.
 */
public class DoTest {

//    public static Collection<Integer> mlist = Collections.synchronizedCollection(new Vector<Integer>());

    private static List<Integer> mlist = Collections.synchronizedList(new ArrayList<Integer>());

    public static void stringTest()
    {
        String text = "" ;

        long beginTime = System.currentTimeMillis();
        for ( int i= 0 ;i< 10000 ;i++)
            text = text + i;
        long endTime = System.currentTimeMillis();
        Log.error("Sting执行时间：", String.valueOf(endTime-beginTime));

        StringBuilder sb = new StringBuilder ( "" );
        beginTime = System.currentTimeMillis();
        for ( int i= 0 ;i< 10000 ;i++)
            sb.append(String.valueOf(i));
        endTime = System.currentTimeMillis();
        Log.error("StringBuilder执行时间：", String.valueOf(endTime - beginTime));

    }

    public static void asyncTaskTest() {
        for (int i = 0; i < 5; i++) {
            AsyncTaskImp taskImp = new AsyncTaskImp();
            taskImp.execute(i);
        }
    }

    public static void asyncTaskTestCustomPool() {

        LinkedBlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<Runnable>();
        ThreadPoolExecutor exec = new ThreadPoolExecutor(2, 5, 0L, TimeUnit.MILLISECONDS, blockingQueue);

        for (int i = 0; i < 5; i++) {
            new AsyncTaskImp().executeOnExecutor(exec, i);
        }

//        Executors.newCachedThreadPool();
//        exec.prestartCoreThread();
    }

    public static void serializableTest() {
//        TestBean testBean = new TestBean(1, "2", 3);
//        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("catche.txt"));
//
    }

    private static void testSyncListAdd(Integer nValue) {
        mlist.add(nValue);
        Log.error("testadd" , String.valueOf(nValue));
    }

    private static void testSyncListVisit() {
//        synchronized (mlist) {
            for (Integer nValue : mlist) {
                Log.error("testVisit", String.valueOf(nValue));
//            }

        }

    }

    private static void testSyncListRemove() {
//        synchronized (mlist) {
            mlist.clear();
        Log.error("testRemove", "");
//        }
    }

    public static void testSyncList() {
        for (int n = 0; n < 1000; n++) {
            testSyncListAdd(n);
        }

        new Thread() {
            @Override
            public void run() {
                testSyncListVisit();
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                testSyncListRemove();
            }
        }.start();
    }
}
