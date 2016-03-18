package com.guoguo.ui.anmi.seniorpropertyanim;

import android.animation.TimeInterpolator;

/**
 * Created by Administrator on 2016/3/17.
 */
public class MyInterpolator implements TimeInterpolator{
    @Override
    public float getInterpolation(float input) {
        if (input <= 0.3f) {
            return input;
        } else if (input <= 0.5f) {
            return 0.0f;
        } else if (input <= 0.6f) {
            return 1.0f;
        } else if (input > 0.6f) {
            return 1.0f - input;
        }
        return 1.0f;
    }
}
