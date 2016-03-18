package com.guoguo.ui.anmi.seniorpropertyanim;

import android.animation.TypeEvaluator;

/**
 * Created by Administrator on 2016/3/17.
 */
public class MyEvaluator implements TypeEvaluator {
    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        int nStartNum = ((MyAnimValueObject)startValue).getNum();
        int nEndNum = ((MyAnimValueObject)endValue).getNum();
        int nStartColor = ((MyAnimValueObject)startValue).getTextColor();
        int nEndColor = ((MyAnimValueObject)endValue).getTextColor();

        int nNum = (int)(nStartNum + (nEndNum - nStartNum) * fraction);
        int nColor = (int)(nStartColor + (nEndColor - nStartColor) * fraction);
        MyAnimValueObject obj = new MyAnimValueObject(nNum, nColor);

        return obj;
    }
}
