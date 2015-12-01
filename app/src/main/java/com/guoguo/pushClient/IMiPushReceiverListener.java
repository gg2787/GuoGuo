package com.guoguo.pushClient;

import com.xiaomi.mipush.sdk.MiPushMessage;

/**
 * Created by Administrator on 2015/11/20.
 */
public interface IMiPushReceiverListener {
    public static final int CMD_RECEIVE_ALIAS = 1;
    public static final int CMD_RECEIVE_UNALIAS = 2;

    public abstract void OnReceiveThroughMsg(MiPushMessage message);
    public abstract void OnReceiveRegId(String strRegId);
    public abstract void OnReceiveCommand(int nMiPushCmd, String... argus);
}
