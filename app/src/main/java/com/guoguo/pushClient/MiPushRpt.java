package com.guoguo.pushClient;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

import com.guoguo.logic.log.Log;
import com.guoguo.logic.phone.PhoneCommon;
import com.guoguo.logic.prefs.AppPrefs;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Administrator on 2015/11/23.
 */
//Rpc gcm report
//上报时机:
//        首次安装
//        升级新版本
//        每周上报一次
//        变更推送开关设置时上报
//上报地址:
//        请求方式，POST

//Rpc taskback gcm
//上报时机:
//        推送到达
//        推送内容被点击
//        推送内容被清除
//value   到达:1/点击:2/清除:3
//上报地址:
//        请求方式，POST

public class MiPushRpt {
    private static final String TAG = "MiPushRpt";

    private static final String GCM_URL = "";
    private static final String TASKBACK_GCM_URL = "";
    private static final String APP_FLAG = "cm_market";
    private static final int SOCKET_TIMEOUT = 30 * 1000; // 30 秒

    public static final int BJ_TAST_ACTION_RCV = 1;
    public static final int BJ_TAST_ACTION_CLICK = 2;
    public static final int BJ_TAST_ACTION_DEL = 3;

    public static final int MARKET_MI_PUSH_ACTION_REG_BEGIN = 1;
    public static final int MARKET_MI_PUSH_ACTION_REG_OK = 2;
    public static final int MARKET_MI_PUSH_ACTION_ALIAS_OK = 3;
    public static final int MARKET_MI_PUSH_ACTION_GET_THROUGH_MSG = 4;
    public static final int MARKET_MI_PUSH_ACTION_PARSE_MSG = 5;
    public static final int MARKET_MI_PUSH_ACTION_SHOW_POP = 6;
    public static final int MARKET_MI_PUSH_ACTION_CLICK_POP = 7;

    public static boolean checkNeedRptBjGcm(Context context, String strNewRegid, String strOldRegid) {
        boolean bCheck = false;
        if (checkIsFirstStart(context)) {
            bCheck = true;
        } else if (TextUtils.isEmpty(strOldRegid) || !strNewRegid.equals(strOldRegid)) {
            bCheck = true;
        } else if (checkRptParam()) {
            bCheck = true;
        }

        if (bCheck) {
            saveFirstStart(context);
            saveRptParam();
        }
        return bCheck;
    }

    public static void rptBjGcm(Context context, String strNewRegid, String strOldRegid) {
        if (null == context) {
            return;
        }
        HashMap<String, String> mapParam = new HashMap<String, String>();
        mapParam.put("appflag", APP_FLAG);
        mapParam.put("regid", strNewRegid);
        mapParam.put("aid", PhoneCommon.getAndroidID(context));
        mapParam.put("apkversion", PhoneCommon.getVersionCode(context));
        mapParam.put("sdkversion", Build.VERSION.RELEASE);
        mapParam.put("cl", getLanguageStr());
        mapParam.put("timezone", getTimeZone());
        mapParam.put("guid", MiPushManager.getInstance(context).getSvrid());
        if (!TextUtils.isEmpty(strOldRegid) && !strNewRegid.equals(strOldRegid)) {
            mapParam.put("oregid", strOldRegid);
        }

        sendRpt(mapParam, GCM_URL);
        Log.error(TAG,"rptBjGcm_" + "regid:" + mapParam.get("regid") + "_aid:" + mapParam.get("aid"));
    }

    public static void rptBjTaskback(Context context, int nPushId, int nAction) {
        if (null == context) {
            return;
        }
        HashMap<String, String> mapParam = new HashMap<String, String>();
        mapParam.put("pushid", String.valueOf(nPushId));
        mapParam.put("regid", MiPushClient.getRegId(context));
        mapParam.put("aid", PhoneCommon.getAndroidID(context));
        mapParam.put("apkversion", PhoneCommon.getVersionCode(context));
        mapParam.put("action", String.valueOf(nAction));
        mapParam.put("guid", MiPushManager.getInstance(context).getSvrid());

        sendRpt(mapParam, TASKBACK_GCM_URL);
        Log.error(TAG, "rptBjTaskback_" + "regid:" + mapParam.get("regid") + "_aid:" + mapParam.get("aid"));
    }

    /**
     * 根据是否已记录当前版本号判断是否是第一次启动（）
     * @return
     */
    private static boolean checkIsFirstStart(Context context) {
        String strVersionCode = PhoneCommon.getVersionCode(context);
        String strOldVerCode = AppPrefs.getString(AppPrefs.MIPUSH_BJ_RPT_VER);
        if (TextUtils.isEmpty(strVersionCode)) {
            return false;
        }
        if (TextUtils.isEmpty(strOldVerCode) || !strVersionCode.equals(strOldVerCode)) {
            return true;
        }
        return false;
    }

    private static void sendRpt(HashMap<String, String> mapParam, final String strUrl) {
        if (null == mapParam || TextUtils.isEmpty(strUrl)) {
            return;
        }

        final byte[] data = getRequestData(mapParam);
        if (null == data) {
            return;
        }
        new Thread() {
            @Override
            public void run() {
                postData(data, strUrl);
            }
        }.start();
    }

    private static boolean postData(byte[] data, String strUrl) {
        if (null == data || TextUtils.isEmpty(strUrl)) {
            return false;
        }
        OutputStream outputStream = null;
        InputStream inptStream = null;
        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL(strUrl);

            httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setConnectTimeout(SOCKET_TIMEOUT);     //设置连接超时时间

            httpURLConnection.setDoInput(true);                  //打开输入流，以便从服务器获取数据
            httpURLConnection.setDoOutput(true);                 //打开输出流，以便向服务器提交数据
            httpURLConnection.setRequestMethod("POST");          //设置以Post方式提交数据
            httpURLConnection.setUseCaches(false);               //使用Post方式不能使用缓存
            httpURLConnection.setRequestProperty("Content-Type", "text/html");//设置请求体的类型
            httpURLConnection.setRequestProperty("Content-Length", String.valueOf(data.length));//设置请求体的长度

            outputStream = httpURLConnection.getOutputStream(); //获得输出流，向服务器写入数据
            outputStream.write(data);

            int response = httpURLConnection.getResponseCode();            //获得服务器的响应码
            if(response == HttpURLConnection.HTTP_OK) {
                inptStream = httpURLConnection.getInputStream();
                dealResponseResult(inptStream);                     //处理服务器的响应结果

            }
        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
                if (inptStream != null) {
                    inptStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public static byte[] getRequestData(HashMap<String, String> params) {
        if (null == params) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        try {
            for(HashMap.Entry<String, String> entry : params.entrySet()) {
                stringBuffer.append(entry.getKey())
                        .append("=")
                        .append(entry.getValue())
                        .append("&");
            }
            if (stringBuffer.length() > 0) {
                stringBuffer.deleteCharAt(stringBuffer.length() - 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuffer.toString().getBytes();
    }

    public static String dealResponseResult(InputStream inputStream) {
        String resultData = null;      //存储处理结果
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        try {
            while((len = inputStream.read(data)) != -1) {
                byteArrayOutputStream.write(data, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        resultData = new String(byteArrayOutputStream.toByteArray());
        return resultData;
    }

    private synchronized static String getLanguageStr() {
        Locale locale = Locale.getDefault();
        String language = locale.getLanguage().toLowerCase();
        String country = locale.getCountry();

        StringBuilder builder = new StringBuilder();
        builder.append(country).append("_").append(language);
        return builder.toString();
    }

    private static String getTimeZone() {
        TimeZone tz = TimeZone.getDefault();
        return tz.getID();
    }

    public static void rptMiPushMsg(Context context, int nAction, int nPushId, int nPopId, String strTitle) {
        String strRegid = MiPushClient.getRegId(context);
        String strAlias = MiPushManager.getInstance(context).getSavedAlias();
        Log.error(TAG, "rptMiPushMsg");
    }

    public static void rptMiPushUser(Context context, int nAction) {
        String strRegid = MiPushManager.getInstance(context).getRegId();
        String strAlias = MiPushManager.getInstance(context).getAlias();
        Log.error(TAG, "rptMiPushUser");
    }

    public static void rptMsgClick(Context context, int nMiPushId, int nPopId, String strTitle) {
        rptMiPushMsg(context, MARKET_MI_PUSH_ACTION_CLICK_POP, nMiPushId, nPopId, strTitle);
        rptBjTaskback(context.getApplicationContext(), nMiPushId, BJ_TAST_ACTION_CLICK);
    }

    public static void rptMsgRev(Context context, int nMiPushId) {
        MiPushRpt.rptMiPushMsg(context, MiPushRpt.MARKET_MI_PUSH_ACTION_GET_THROUGH_MSG, nMiPushId, 0, "");
        MiPushRpt.rptBjTaskback(context, nMiPushId, MiPushRpt.BJ_TAST_ACTION_RCV);
    }

    private static boolean checkRptParam() {
        String strOldParam = AppPrefs.getString(AppPrefs.MIPUSH_BJ_RPT_PARAM);
        String strNewParam = getRptParamString();
        if (TextUtils.isEmpty(strOldParam) || !strNewParam.equals(strOldParam)) {
            return true;
        }
        return false;
    }

    private static String getRptParamString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getLanguageStr()).append("_").append(getTimeZone());
        return stringBuilder.toString();
    }

    private static void saveRptParam() {
        String strNewParam = getRptParamString();
        AppPrefs.putString(AppPrefs.MIPUSH_BJ_RPT_PARAM, strNewParam);
    }

    private static void saveFirstStart(Context context) {
        String strVersionCode = PhoneCommon.getVersionCode(context);
        AppPrefs.putString(AppPrefs.MIPUSH_BJ_RPT_VER, strVersionCode);
    }
}


