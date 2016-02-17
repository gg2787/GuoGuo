package com.guoguo.pushClient;

import android.content.Context;

import com.guoguo.logic.log.Log;
import com.guoguo.ui.toast.ShowToast;
import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 1、PushMessageReceiver是个抽象类，该类继承了BroadcastReceiver。
 * 2、需要将自定义的DemoMessageReceiver注册在AndroidManifest.xml文件中 <receiver
 * android:exported="true"
 * android:name="com.xiaomi.mipushdemo.DemoMessageReceiver"> <intent-filter>
 * <action android:name="com.xiaomi.mipush.RECEIVE_MESSAGE" /> </intent-filter>
 * <intent-filter> <action android:name="com.xiaomi.mipush.ERROR" />
 * </intent-filter> <intent-filter>
 *  <action android:name="com.xiaomi.mipush.MESSAGE_ARRIVED" /></intent-filter>
 *  </receiver>
 * 3、DemoMessageReceiver的onReceivePassThroughMessage方法用来接收服务器向客户端发送的透传消息
 * 4、DemoMessageReceiver的onNotificationMessageClicked方法用来接收服务器向客户端发送的通知消息，
 * 这个回调方法会在用户手动点击通知后触发
 * 5、DemoMessageReceiver的onNotificationMessageArrived方法用来接收服务器向客户端发送的通知消息，
 * 这个回调方法是在通知消息到达客户端时触发。另外应用在前台时不弹出通知的通 知消息到达客户端也会触发这个回调函数
 * 6、DemoMessageReceiver的onCommandResult方法用来接收客户端向服务器发送命令后的响应结果
 * 7、DemoMessageReceiver的onReceiveRegisterResult方法用来接收客户端向服务器发送注册命令后的响应结果
 * 8、以上这些方法运行在非UI线程中

 */

/**
 * Created by Administrator on 2015/11/17.
 */
public class MyPushMessageReceiver extends PushMessageReceiver {
    private static final String TAG = MyPushMessageReceiver.class.getSimpleName();
    private String mRegId;

    @Override
    public void onReceivePassThroughMessage(Context context, MiPushMessage message) {
        String strContent = message.getContent();
        parseContent(strContent);

        Log.error(TAG, "receive message");
        Log.error(TAG, "message Title:" + message.getTitle());
        Log.error(TAG, "message Dsp:" + message.getDescription());
        Log.error(TAG, "message Content:" + message.getContent());
    }

    @Override
    public void onReceiveRegisterResult(Context context, MiPushCommandMessage message) {
        Log.error(TAG, "onReceiveRegisterResult");
        String command = message.getCommand();
        List<String> arguments = message.getCommandArguments();
        String cmdArg1 = ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);
        String log;
        if (MiPushClient.COMMAND_REGISTER.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mRegId = cmdArg1;
                //ShowToast.showShortToast(context, "getRegId" + mRegId);
                MyPushService.getInstance(context).onGetRegId(mRegId);
                Log.error(TAG, "register_success");
                Log.error(TAG, "getRegId:" + mRegId);
            } else {
                Log.error(TAG, "register_fail");
            }
        } else {
            Log.error(TAG, message.getReason());
        }
        //do sth
    }

    @Override
    public void onCommandResult(Context context, MiPushCommandMessage message) {
        Log.error(TAG, "nCommandResult:");
        String command = message.getCommand();
        List<String> arguments = message.getCommandArguments();
        String cmdArg1 = ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);
        String cmdArg2 = ((arguments != null && arguments.size() > 1) ? arguments.get(1) : null);
        Log.error(TAG, "nCommandResult:" + message.getCommand());
    }

    private void parseContent(String strContent) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{\"Content\":\"").append(strContent).append("\"}");

        try {
            JSONObject jsonObj = new JSONObject(stringBuilder.toString());
            String strTemp = jsonObj.getString("Content");
            jsonObj = new JSONObject(strTemp);

            if (!jsonObj.isNull("action")) {

            }
            if (!jsonObj.isNull("notice")) {

            }
            if (!jsonObj.isNull("imgUrl")) {

            }
        } catch (JSONException e) {

        }


//        msg.setCreateTime(System.currentTimeMillis());
//        if (!jsonObj.isNull("action"))
//            msg.setAction(jsonObj.getInt("action"));
//        if (!jsonObj.isNull("minVersion"))
//            msg.setVerMin(jsonObj.getInt("minVersion"));
//        if (!jsonObj.isNull("maxVersion"))
//            msg.setVerMax(jsonObj.getInt("maxVersion"));
//        if (!jsonObj.isNull("notice"))
//            msg.setNotice(jsonObj.getString("notice"));
//        if (!jsonObj.isNull("startTime"))
//            msg.setStartShowTime(jsonObj.getLong("startTime"));
//        if (!jsonObj.isNull("endTime"))
//            msg.setEndShowTime(jsonObj.getLong("endTime"));
//        if (!jsonObj.isNull("displayType"))
//            msg.setDisplayType(jsonObj.getInt("displayType"));
//        if (!jsonObj.isNull("title"))
//            msg.setTitle(jsonObj.getString("title"));
//        if (!jsonObj.isNull("message"))
//            msg.setMessage(jsonObj.getString("message"));
//        if (!jsonObj.isNull("insertType"))
//            msg.setInsertType(jsonObj.getInt("insertType"));
//        if (!jsonObj.isNull("pushTime"))
//            msg.setPushTime(jsonObj.getLong("pushTime"));
    }
}
