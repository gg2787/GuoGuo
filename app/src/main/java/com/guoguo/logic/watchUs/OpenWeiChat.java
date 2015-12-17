package com.guoguo.logic.watchUs;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import com.guoguo.ui.toast.ShowToast;

/**
 * Created by Administrator on 2015/12/4.
 */
public class OpenWeiChat {
    public static String WEIXIN_PACKAGENAME = "com.tencent.mm";
    public static String WEIXIN_LAUNCHER_UI = "com.tencent.mm.ui.LauncherUI";

    public static void openWeiChatApp(Context context) {
        if (null == context) {
            return;
        }
        if (!checkWeiChatApp(context)) {
            ShowToast.showShortToast(context, "未安装微信哦~");
        }
        try {
            Intent intent = new Intent();
            intent.setClassName(WEIXIN_PACKAGENAME, WEIXIN_LAUNCHER_UI);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void copy2Clipboard(String str2Copy, Context context) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
            android.content.ClipboardManager clipboardManager = (android.content.ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboardManager.setText(str2Copy);
        } else {
            android.text.ClipboardManager clipboardManager = (android.text.ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboardManager.setText(str2Copy);
        }
    }

    private static boolean checkWeiChatApp(Context context) {
        String packageName = WEIXIN_PACKAGENAME;
        if (packageName == null || "".equals(packageName)) {
            return false;
        }
        try {
            context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
