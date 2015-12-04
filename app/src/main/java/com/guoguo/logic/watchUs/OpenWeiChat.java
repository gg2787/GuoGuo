package com.guoguo.logic.watchUs;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Administrator on 2015/12/4.
 */
public class OpenWeiChat {
    public static void OpenWeiChatApp(Context context) {
        if (null == context) {
            return;
        }
        Intent intent = new Intent();
        intent.setClassName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
//        intent.putExtra("LauncherUI_From_Biz_Shortcut", true);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);

        //ActivityNotFoundException
    }
}
