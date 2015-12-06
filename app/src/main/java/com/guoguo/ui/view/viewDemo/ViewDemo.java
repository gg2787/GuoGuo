package com.guoguo.ui.view.viewDemo;

import android.content.Context;
import android.view.ViewConfiguration;

/**
 * Created by HP on 2015/12/3.
 */
public class ViewDemo{
    /**
     * 系统所能识别出的被认为是滑动的最小距离
     * @return
     */
    public int getTouchSlop(Context context) {
        return ViewConfiguration.get(context).getScaledTouchSlop();
    }
}
