package com.guoguo.logic.restartBroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import com.guoguo.ui.toast.ShowToast;

/**
 * Created by Administrator on 2015/11/11.
 */
public class restartBroadcastReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        ShowToast.showLongToast(context, "restartBroadcast");
    }
}
