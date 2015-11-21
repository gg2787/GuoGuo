package com.guoguo.pushClient;

import android.content.Context;

import com.guoguo.app.GuoGuoApplication;
import com.guoguo.logic.log.Log;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import com.xiaomi.mipush.sdk.Logger;


/**
 * 小米推送服务客户端的主体
 * Created by Administrator on 2015/11/17.
 * http://dev.xiaomi.com/doc/?p=544#d5e54
 *     public static final String APP_ID = "2882303761517412146";
 public static final String APP_KEY = "5401741223146";
 */
public class MyPushService {

    public static final String APP_ID = "2882303761517412146";
    public static final String APP_KEY = "5401741223146";
    public static final String TAG = MyPushService.class.getSimpleName();

    private String mStrRegId;
    private String mAlias;
    private Context mContext;
    private static MyPushService mInstance;

    private MyPushService(Context context) {
        mContext = context;
    }

    public synchronized static MyPushService getInstance(Context context) {
        if (null == mInstance) {
            mInstance = new MyPushService(context);
        }
        return mInstance;
    }

    public void initPushService() {
        MiPushClient.checkManifest(mContext);

        Log.error(TAG, "initPushService");
        if (shouldInit(mContext)) {
            MiPushClient.registerPush(mContext, APP_ID, APP_KEY);
        }
    }

    public void unitPushService() {

        if (shouldInit(mContext)) {
            MiPushClient.unregisterPush(mContext);
        }
    }

     /**
     * 因为推送服务XMPushService在AndroidManifest.xml中设置为运行在另外一个进程，这导致本Application会被实例化两次，所以我们需要让应用的主进程初始化。
     * @param context
     * @return
     */
    private boolean shouldInit(Context context) {
        return GuoGuoApplication.getInstance().isMainProcess();
    }

    public void onGetRegId(String strRegId) {
        setRegId(strRegId);
        bindAlias();
    }

    private void setRegId(String strRegId) {
        mStrRegId = strRegId;
    }

    private void bindAlias() {
        mAlias = mStrRegId;
        MiPushClient.setAlias(mContext, mAlias, null);
    }

}
