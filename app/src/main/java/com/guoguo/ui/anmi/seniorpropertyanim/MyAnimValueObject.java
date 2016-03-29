package com.guoguo.ui.anmi.seniorpropertyanim;

/**
 * Created by Administrator on 2016/3/17.
 * 属性动画的高级用法
 */
public class MyAnimValueObject {
    private int mNum = 0;
    private int mTextColor = 0xff000000;

    public MyAnimValueObject(int nNum, int nTextColor) {
        mNum = nNum;
        mTextColor = nTextColor;
    }

    public int getNum() {
        return mNum;
    }

    public int getTextColor() {
        return mTextColor;
    }
}


