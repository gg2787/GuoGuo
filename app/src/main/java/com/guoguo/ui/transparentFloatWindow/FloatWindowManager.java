package com.guoguo.ui.transparentFloatWindow;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by Administrator on 2016/4/14.
 */
public class FloatWindowManager {
    private WindowManager mWindowManager;
    private TransparentFloatWindow mFloatView;


    public void showFloatWindow(Context context) {
        mWindowManager = getWindowManager(context);
        mFloatView = getFloatWindow(context.getApplicationContext());

        final WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        int flags = WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM;
        params.flags = flags;
        // 不设置这个弹出框的透明遮罩显示为黑色
        params.format = PixelFormat.TRANSLUCENT;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.CENTER;

        mWindowManager.addView(mFloatView, params);
    }

    public void hideFloatWindow(Context context) {
        mWindowManager = getWindowManager(context);
        mWindowManager.removeView(mFloatView);
    }

    private TransparentFloatWindow getFloatWindow(Context context) {
        return new TransparentFloatWindow(context);
    }

    private WindowManager getWindowManager(Context context) {
        if (null == mWindowManager) {
            mWindowManager = (WindowManager)context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        }
        return mWindowManager;
    }
}
