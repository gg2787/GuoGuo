package com.guoguo.ui.transparentFloatWindow;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2016/4/14.
 */
public class TransparentFloatWindow extends View {
    private Context mContext;

    public TransparentFloatWindow(Context context) {
        this(context, null);
    }

    public TransparentFloatWindow(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TransparentFloatWindow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView(context);
    }

    private void initView(Context context) {

    }

    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
