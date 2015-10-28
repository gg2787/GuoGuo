package com.guoguo.ui.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.guoguo.R;
import com.guoguo.app.GuoGuoApplication;
import com.guoguo.ui.MainActivity;

/**
 * Created by Administrator on 2015/10/28.
 * 自定义通知栏
 */
public class NotificationUtil {
    private static final int NOTIFICATION_ID_INDEX_WELCOME = 1;
    private static final int NOTIFICATION_ID_INDEX_SAYHELLO = 2;
    public static boolean sendWelComeNormalNotification(Context context) {

        int nIcon = R.mipmap.logo;
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("from", context.getString(R.string.welcome));
        PendingIntent pendingIntent = PendingIntent.getActivity(context, NOTIFICATION_ID_INDEX_WELCOME, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = new Notification.Builder(context);
        builder.setSmallIcon(nIcon);
        builder.setContentText(context.getString(R.string.welcome));
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
        Notification notification = builder.getNotification();
        return sendNotification(notification, NOTIFICATION_ID_INDEX_WELCOME, context);
    }

    public static boolean sendSayHelloNormalNotification(Context context) {

        int nIcon = R.mipmap.logo;
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("from", context.getString(R.string.hello_u));
        PendingIntent pendingIntent = PendingIntent.getActivity(context, NOTIFICATION_ID_INDEX_SAYHELLO, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = new Notification.Builder(context);
        builder.setSmallIcon(nIcon);
        builder.setContentText(context.getString(R.string.hello_u));
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
        Notification notification = builder.getNotification();
        return sendNotification(notification, NOTIFICATION_ID_INDEX_SAYHELLO, context);
    }

    private static boolean sendNotification(Notification notification, int nId, Context context) {
        int nSdkVer = Build.VERSION.SDK_INT;
        if (nSdkVer <= 10) {
            return false;
        }

        if (null == notification) {
            return false;
        }

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (null == notificationManager) {
            return false;
        }

        if (nSdkVer < 14) {
            if (null == notification.contentIntent) {
                notification.contentIntent = PendingIntent.getActivity(context, 0, new Intent(), PendingIntent.FLAG_CANCEL_CURRENT);
            }
        }
        notificationManager.notify(nId, notification);
        return true;
    }
}
