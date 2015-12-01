package com.guoguo.pushClient;

import android.content.Context;
import android.os.AsyncTask;

import com.guoguo.logic.log.Log;
import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/20.
 */
public class MiPushMsgReceiver extends PushMessageReceiver {
    private static final String TAG = MiPushMsgReceiver.class.getSimpleName();
    //private static final String TAG = "MiPushMsgReceiver";

    private static ArrayList<IMiPushReceiverListener> mPushReceiverCallBackList;

    @Override
    public void onReceivePassThroughMessage(Context context, MiPushMessage message) {
        if (null == message) {
            return;
        }
        Log.error(TAG, "onReceivePassThroughMessage");
        doLogPushMessage("onReceivePassThroughMessage", message);
        notifyPushListenersThroughMsg(message);
    }

    @Override
    public void onReceiveRegisterResult(Context context, MiPushCommandMessage message) {
        if (null == message) {
            return;
        }
        String command = message.getCommand();
        List<String> arguments = message.getCommandArguments();
        if (null == command || null == arguments || arguments.size() <= 0) {
            return;
        }

        if (MiPushClient.COMMAND_REGISTER.equals(command)) {
            if (message.getResultCode() != ErrorCode.SUCCESS) {
                Log.error(TAG, "register_fail");
            }
            String strRegId = arguments.get(0);
            Log.error(TAG, "Regid:" + strRegId);
            notifyPushListenersRegId(strRegId);
        } else {
            Log.error(TAG, message.getReason());
        }
    }

    @Override
    public void onCommandResult(Context context, MiPushCommandMessage message) {
        if (null == message) {
            return;
        }
        Log.error(TAG, "onCommandResult");
        doLogCommandMessage("onCommandResult", message);

        String command = message.getCommand();
        List<String> arguments = message.getCommandArguments();
        if (null == command || null == arguments || arguments.size() <= 0) {
            return;
        }
        String cmdArg1 = arguments.get(0);

        if (MiPushClient.COMMAND_SET_ALIAS.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                notifyPushListenersCmd(IMiPushReceiverListener.CMD_RECEIVE_ALIAS, cmdArg1);
                Log.error(TAG, "alias:" + cmdArg1);
            } else {
                Log.error(TAG, message.getReason());
            }
        } else if (MiPushClient.COMMAND_UNSET_ALIAS.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                notifyPushListenersCmd(IMiPushReceiverListener.CMD_RECEIVE_UNALIAS, cmdArg1);
                Log.error(TAG, "unAlias:" + cmdArg1);
            } else {
                Log.error(TAG, message.getReason());
            }
        }
    }

    public synchronized static void registerPushListener(IMiPushReceiverListener listener) {
        if (null == mPushReceiverCallBackList) {
            mPushReceiverCallBackList = new ArrayList<IMiPushReceiverListener>();
        }
        if (null == listener) {
            return;
        }
        if (mPushReceiverCallBackList.contains(listener)) {
            Log.error(TAG, "registerPushListener_already_exist");
            return;
        }
        mPushReceiverCallBackList.add(listener);
        Log.error(TAG, "registerPushListener");
    }

    public synchronized static void unregisterPushListener(IMiPushReceiverListener listener) {
        if (null == mPushReceiverCallBackList || null == listener) {
            return;
        }
        mPushReceiverCallBackList.remove(listener);
        Log.error(TAG, "unregisterPushListener");
    }

    private synchronized void notifyPushListenersRegId(String strRegId) {
        if (null == strRegId || null == mPushReceiverCallBackList) {
            return;
        }
        for (IMiPushReceiverListener listener : mPushReceiverCallBackList) {
            listener.OnReceiveRegId(strRegId);
        }
    }

    private synchronized void notifyPushListenersThroughMsg(MiPushMessage message) {
        if (null == message || null == mPushReceiverCallBackList) {
            return;
        }
        for (IMiPushReceiverListener listener : mPushReceiverCallBackList) {
            listener.OnReceiveThroughMsg(message);
        }
    }

    private synchronized void notifyPushListenersCmd(int nCmd, String...argus) {
        if (null == mPushReceiverCallBackList) {
            return;
        }
        for (IMiPushReceiverListener listener : mPushReceiverCallBackList) {
            listener.OnReceiveCommand(nCmd, argus);
        }
    }

    private void doLogPushMessage(String strFunc, MiPushMessage message) {
        try {
            StringBuffer stringBuffer = new StringBuffer(strFunc);
            stringBuffer.append("_title:").append(message.getTitle()).
                    append("_Description:").append(message.getDescription()).
                    append("_Content:").append(message.getContent());

            Log.error(TAG, stringBuffer.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doLogCommandMessage(String strFunc, MiPushCommandMessage message) {
        try {
            StringBuffer stringBuffer = new StringBuffer(strFunc);
            stringBuffer.append("_Command:").append(message.getCommand()).
                    append("_Reason:").append(message.getReason()).
                    append("_ResultCode:").append(message.getResultCode()).
                    append("_ArguListSize:").append(message.getCommandArguments().size());

            Log.error(TAG, stringBuffer.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
