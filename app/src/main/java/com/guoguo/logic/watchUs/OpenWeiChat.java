package com.guoguo.logic.watchUs;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

/**
 * Created by Administrator on 2015/12/4.
 */
public class OpenWeiChat {
    public static void openWeiChatApp(Context context) {
        if (null == context) {
            return;
        }
        try {
            Intent intent = new Intent();
            intent.setClassName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
//        intent.putExtra("LauncherUI_From_Biz_Shortcut", true);
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
}
