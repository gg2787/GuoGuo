package com.guoguo.ui.popupwindow;

import android.widget.PopupWindow;

/**
 * Created by Administrator on 2016/2/26.
 *
 */
public class PopUpWindowManager {
    public static final int POP_UP_WINDOW_WELCOME = 1;

    public static void showWindow(int nWindowType) {
        switch (nWindowType) {
            case POP_UP_WINDOW_WELCOME:
                showWelcomeWindow();
                break;
        }
    }

    private static void showWelcomeWindow() {
//        PopupWindow
    }

}
