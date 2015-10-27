package com.guoguo.utils;

/**
 * Created by Administrator on 2015/10/15.
 */
public class AbTest {
    public static boolean getAbTest() {
        int n = (int)(Math.random() * 100) % 2;
        if (0 == n) {
            return true;
        }
        return false;
    }
}
