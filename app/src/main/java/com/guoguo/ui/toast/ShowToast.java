package com.guoguo.ui.toast;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2015/10/15.
 */
public class ShowToast {
    public static void showShortToast (Context context, String strText) {
        showToast(context, strText, Toast.LENGTH_SHORT);
    }

    public static void showLongToast (Context context, String strText) {
        showToast(context, strText, Toast.LENGTH_LONG);
    }

    private static void showToast (Context context, String strText, int nDuration) {
        if (null == context) {
            return;
        }
        Toast toast = Toast.makeText(context, strText, nDuration);
        toast.show();
    }
}
