package com.sy.globletake_user.jpush;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.sy.globletake_user.Bean.JifeiBean;
import com.sy.globletake_user.Bean.OrderReceived;
import com.sy.globletake_user.activity.MainActivity;
import com.sy.globletake_user.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * 
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
	private static final String TAG = "JPush";

	@Override
	public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
		Log.e("action","adction="+intent.getAction());
		Log.e("action","用户端通知或消息！");
		String pushResult = bundle.getString(JPushInterface.EXTRA_EXTRA);
		Log.e("action","pushResult="+pushResult);
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
			String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
			if (regId==null||regId.isEmpty()){
				ToastUtils.Toast_short1("网络异常，暂时无法接收推送消息");
			}
			Log.e(TAG, "Registration Id=" + regId);
            //send the Registration Id to your server...
                        
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
        	Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
//        	processCustomMessage(context, bundle);

        
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);
			Log.e("tag","订单已被接");
			String dataStr = bundle.getString(JPushInterface.EXTRA_ALERT);

			if (dataStr !=null){
				Log.e("PSDATA","dataStr="+dataStr);
				if (dataStr.contains("您的行程被接单")||dataStr.contains("receiving order")){
					if (pushResult!=null){
						Log.e("DATA","pushResult="+pushResult);
						Gson gson = new Gson();
						OrderReceived orderReceivedBean = gson.fromJson(pushResult,OrderReceived.class);
						if (orderReceivedBean!=null){
							orderReceivedBean.setEventStr("订单被接");
							EventBus.getDefault().post(orderReceivedBean);
						}
					}
				}else if (dataStr.contains("开始计费")||dataStr.contains("Start billing")){
					if (pushResult!=null){
						Log.e("DATA","pushResult="+pushResult);
						Gson gson = new Gson();
						JifeiBean jifeiBean = gson.fromJson(pushResult,JifeiBean.class);
						if (jifeiBean!=null){
							jifeiBean.setEventStr("开始计费");
							EventBus.getDefault().post(jifeiBean);
						}
					}
				}else if (dataStr.contains("结束订单")||dataStr.contains("End of the order")){
					if (pushResult!=null){
						Log.e("DATA","pushResult="+pushResult);
						Gson gson = new Gson();
						JifeiBean jifeiBean = gson.fromJson(pushResult,JifeiBean.class);
						if (jifeiBean!=null){
							jifeiBean.setEventStr("结束订单");
							EventBus.getDefault().post(jifeiBean);
						}
					}
				}else if (dataStr.contains("行程被取消")||dataStr.contains("cancelled")){
					EventBus.getDefault().post("行程被取消");
				}
			}
        	
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户点击打开了通知");
            

			//恢复应用到前台显示
			Intent mIntent = new Intent(Intent.ACTION_MAIN);
			mIntent.addCategory(Intent.CATEGORY_LAUNCHER);
			mIntent.setComponent(new ComponentName(context.getPackageName(), MainActivity.class.getCanonicalName()));
			mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
			context.startActivity(mIntent);

		} else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + pushResult);
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..
        	
        } else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
        	boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
        	Log.w(TAG, "[MyReceiver]" + intent.getAction() +" connected state change to "+connected);
        } else {
        	Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }
	}

	// 打印所有的 intent extra 数据
	private static String printBundle(Bundle bundle) {
		StringBuilder sb = new StringBuilder();
		for (String key : bundle.keySet()) {
			if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
				sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
			}else if(key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)){
				sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
			} else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
				if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
					Log.i(TAG, "This message has no Extra data");
					continue;
				}

				try {
					JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
					Iterator<String> it =  json.keys();

					while (it.hasNext()) {
						String myKey = it.next().toString();
						sb.append("\nkey:" + key + ", value: [" +
								myKey + " - " +json.optString(myKey) + "]");
					}
				} catch (JSONException e) {
					Log.e(TAG, "Get message extra JSON error!");
				}

			} else {
				sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
			}
		}
		return sb.toString();
	}
}
