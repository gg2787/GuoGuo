package com.guoguo.pushClient;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;

import com.guoguo.logic.log.Log;
import com.guoguo.logic.phone.PhoneCommon;
import com.guoguo.logic.prefs.AppPrefs;
import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import com.xiaomi.mipush.sdk.Logger;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mipush.sdk.MiPushMessage;

import java.lang.ref.WeakReference;

/**
 * 小米推送管理类

 mipush SDK注意事项：
 1.小米服务器异步的。如先unsetAlias，再setAlias，小米服务器收到的顺序可能是反的，如果参数是同一个strAlias,就可能有问题。
 2.AndroidManifest中的PushMessageHandler需要定义在和MiPushClient.registerPush调用在同一进程
 3.卸载app后重装，regid会变，它是mipush保存在手机系统下的xml文件，root手机可以查看。
 4.unregister之后，regID会失效,所以不需要接受推送时不要调用unregister，可以使用pausePush和resumePush来控制

 */
public class MiPushManager implements IMiPushReceiverListener {
    public static final String TAG = MiPushManager.class.getSimpleName();
    public static final String APP_ID = "2882303761517412146";
    public static final String APP_KEY = "5401741223146";

    private static final int MSG_LOOP_QUIT = 1;
    private static final int MSG_RECEIVE_REGID = 2;
    private static final int MSG_RECEIVE_THROUGH = 3;
    private static final int MSG_RECEIVE_ALIAS = 4;
    private static final int MSG_RECEIVE_UNALIAS = 5;

    private MyHandler mMsgHandler = null;

    private String mStrRegId = null;
    private String mStrAlias = null;
    private Context mContext = null;
    private static MiPushManager mInstance = null;

    private MiPushManager(Context context) {
        mContext = context.getApplicationContext();
    }

    public synchronized static MiPushManager getInstance(Context context) {
        if (null == mInstance) {
            mInstance = new MiPushManager(context);
        }
        return mInstance;
    }

    public void initPushService() {
        initLoopThread();
        initPushListener();
        initMiPushService();
        initMiPushLog();
        Log.error(TAG, "initPushService");
    }

    /**
     * unregister之后，regID会失效,所以不调用unregister
     */
    public void unitPushService() {
        uninitPushListener();
        uninitLoopThread();
        Log.error(TAG, "unitPushService");
    }

    private void initPushListener() {
        MiPushMsgReceiver.registerPushListener(this);
    }

    private void uninitPushListener() {
        MiPushMsgReceiver.unregisterPushListener(this);
    }

    /**
     * 此函数仅用于接入阶段检测manifest配置是否正确
     * mipush中的NetworkStatusReceiver在某些情况下会暂时性丢失（会恢复），这是小米已知bug
     * 因此正式版本不能调用此检测函数，它可能导致mipush内部抛出异常
     */
    private void checkMiPushCfg() {
        MiPushClient.checkManifest(mContext);
    }

    private void initMiPushService() {
        MiPushClient.registerPush(mContext, APP_ID, APP_KEY);
        MiPushRpt.rptMiPushUser(mContext, MiPushRpt.MARKET_MI_PUSH_ACTION_REG_BEGIN);
    }

    private void initLoopThread() {
        HandlerThread thread = new HandlerThread("");
        thread.start();
        mMsgHandler = new MyHandler(thread.getLooper(), this);
    }

    private void uninitLoopThread() {
        if (null == mMsgHandler) {
            return;
        }
        Message msg = mMsgHandler.obtainMessage();
        msg.what = MSG_LOOP_QUIT;
        mMsgHandler.sendMessage(msg);
    }

    public void OnReceiveThroughMsg(MiPushMessage message){
        if (null == mMsgHandler || null == message) {
            return;
        }
        Log.error(TAG, "OnReceiveThroughMsg");

        Message msg = mMsgHandler.obtainMessage();
        msg.what = MSG_RECEIVE_THROUGH;
        msg.obj = message;
        mMsgHandler.sendMessage(msg);
    }

    public void OnReceiveRegId(String strRegId) {
        if (null == mMsgHandler || null == strRegId) {
            return;
        }
        Log.error(TAG, "OnReceiveRegId_RegId:" + strRegId);

        Message msg = mMsgHandler.obtainMessage();
        msg.what = MSG_RECEIVE_REGID;
        msg.obj = strRegId;
        mMsgHandler.sendMessage(msg);
    }

    public void OnReceiveCommand(int nMiPushCmd, String... argus) {
        if (null == mMsgHandler) {
            return;
        }
        Log.error(TAG, "OnReceiveCommand:" + String.valueOf(nMiPushCmd));

        Message msg = mMsgHandler.obtainMessage();
        boolean bSend = false;

        switch (nMiPushCmd) {
            case CMD_RECEIVE_ALIAS:
                if (argus.length > 0) {
                    msg.what = MSG_RECEIVE_ALIAS;
                    msg.obj = argus[0];
                    bSend = true;
                }
                break;
            case CMD_RECEIVE_UNALIAS:
                if (argus.length > 0) {
                    msg.what = MSG_RECEIVE_UNALIAS;
                    msg.obj = argus[0];
                    bSend = true;
                }
                break;
            default:
                break;
        }
        if (bSend) {
            mMsgHandler.sendMessage(msg);
        }
    }

    private class MyHandler extends Handler {
        WeakReference<MiPushManager> mMgr;

        public MyHandler(Looper looper, MiPushManager mgr) {
            super(looper);
            mMgr = new WeakReference<>(mgr);
        }

        @Override
        public void handleMessage(Message msg) {
            MiPushManager theMgr = mMgr.get();
            if (null == theMgr) {
                return;
            }

            super.handleMessage(msg);

            int what = msg.what;
            Object obj = msg.obj;
            switch (what) {
                case MSG_LOOP_QUIT:
                    if (null != getLooper()) {
                        getLooper().quit();
                    }
                    Log.error(TAG, "handleMessage_quitLooper");
                    break;
                case MSG_RECEIVE_REGID:
                    if (null != obj && obj instanceof String) {
                        handleRegId((String) obj);
                    }
                    break;
                case MSG_RECEIVE_THROUGH:
                    if (null != obj && obj instanceof MiPushMessage) {
                        handleMiPushMessage((MiPushMessage)obj);
                        Log.error(TAG, "handleMessage_through_msg");
                    }
                    break;
                case MSG_RECEIVE_ALIAS:
                    if (null != obj && obj instanceof String) {
                        setAlias((String) obj);
                        saveAlias((String) obj);
                        rptAlias();
                        Log.error(TAG, "handleMessage_set_alias");
                    }
                    break;
                case MSG_RECEIVE_UNALIAS:
                    Log.error(TAG, "handleMessage_unset_alias");
                    break;
                default:
                    break;
            }
        }
    }

    private void rptAlias() {
        MiPushRpt.rptMiPushUser(mContext, MiPushRpt.MARKET_MI_PUSH_ACTION_ALIAS_OK);
    }

    private void handleMiPushMessage(MiPushMessage miPushMessage) {
        if (null == miPushMessage) {
            return;
        }

        rptRevMsg(miPushMessage);
        //MiPushMsgHandlerAdapter.handleMsg(mContext, miPushMessage);
    }

    private void handleRegId(String strRegId) {
        if (null == strRegId) {
            return;
        }
        setRegId(strRegId);
        String strOldRegId = getSavedRegId();
        saveRegId(strRegId);
        rptRegId(strRegId, strOldRegId);

        String strSvrId = getSvrid();
        String strOldSvrId = getSavedAlias();
        if (TextUtils.isEmpty(strSvrId)) {
            Log.error(TAG, "svrid is null!");
            return;
        }

        if (!TextUtils.isEmpty(strOldSvrId) && !strSvrId.equals(strOldSvrId)) {
            miPushUnSetAlias(strOldSvrId);
            Log.error(TAG, "alias not equals");
        }

        miPushSetAlias(strSvrId);
    }

    private synchronized void setRegId(String strRegId) {
        mStrRegId = strRegId;
    }

    public synchronized String getRegId() {
        if (null == mStrRegId) {
            return "";
        }
        return mStrRegId;
    }

    public synchronized String getAlias() {
        if (null == mStrAlias) {
            return "";
        }
        return mStrAlias;
    }

    private void rptRegId(String strNewRegid, String strOldRegId) {
        if (MiPushRpt.checkNeedRptBjGcm(mContext, strNewRegid, strOldRegId)) {
            MiPushRpt.rptBjGcm(mContext, strNewRegid, strOldRegId);
        }
        MiPushRpt.rptMiPushUser(mContext, MiPushRpt.MARKET_MI_PUSH_ACTION_REG_OK);
    }

    public void miPushSetAlias(String strAlias) {
        MiPushClient.setAlias(mContext, strAlias, null);
        MiPushClient.subscribe(mContext, "GUOGUOTEST", null);
        Log.error(TAG, "miPushSetAlias:" + strAlias);
    }

    private void miPushUnSetAlias(String strAlias) {
        MiPushClient.unsetAlias(mContext, strAlias, null);
        Log.error(TAG, "miPushUnSetAlias:" + strAlias);
    }

    public String getSvrid() {
//        String strUUID = PhoneCommon.getUUID(mContext);
//        if (null == strUUID) {
//            return null;
//        }
//        String strSvrid = PhoneCommon.getSvrid(mContext, strUUID);
//        if (null == strSvrid) {
//            return null;
//        }
        return PhoneCommon.getUUID(mContext);
    }

    private synchronized void setAlias(String strAlias) {
        mStrAlias = strAlias;
    }

    private void initMiPushLog() {
        LoggerInterface newLogger = new LoggerInterface() {
            @Override
            public void setTag(String tag) {
            }

            @Override
            public void log(String content, Throwable t) {
                Log.error(TAG, content, t);
            }

            @Override
            public void log(String content) {
                Log.error(TAG, content);
            }
        };
        Logger.setLogger(mContext, newLogger);
    }

    private void rptRevMsg(MiPushMessage miPushMessage) {
        if (null == miPushMessage) {
            return;
        }

//        int nPushId = MiPushMsgHandlerAdapter.getMsgPushId(miPushMessage.getContent());
//        MiPushRpt.rptMsgRev(mContext, nPushId);
    }

    private void saveRegId(String strRegId) {
        AppPrefs.putString(AppPrefs.MIPUSH_REGID, strRegId);
    }

    private void saveAlias(String strAlias) {
        AppPrefs.putString(AppPrefs.MIPUSH_ALIAS, strAlias);
    }

    public String getSavedRegId() {
        return AppPrefs.getString(AppPrefs.MIPUSH_REGID);
    }

    public String getSavedAlias() {
        return AppPrefs.getString(AppPrefs.MIPUSH_ALIAS);
    }
}
