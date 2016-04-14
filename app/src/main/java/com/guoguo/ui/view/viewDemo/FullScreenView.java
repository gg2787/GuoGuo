package com.guoguo.ui.view.viewDemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewConfiguration;

/**
 * Created by HP on 2015/12/3.
 */
public class FullScreenView extends View {

    public FullScreenView(Context context) {
        this(context, null);
    }

    public FullScreenView (Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FullScreenView (Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {

    }


    /**
     * 系统所能识别出的被认为是滑动的最小距离
     * @return
     */
//    public int getTouchSlop(Context context) {
//        return ViewConfiguration.get(context).getScaledTouchSlop();
//    }
}
