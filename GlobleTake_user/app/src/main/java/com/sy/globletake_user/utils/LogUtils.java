package com.sy.globletake_user.utils;

import android.util.Log;

/**
 * Created by sunnetdesign3 on 2017/2/10.
 */
public class LogUtils {
    public static void Loge(String tag,String message){
        Log.e(tag,message);
    }
    public static void Logd(String tag,String message){
        Log.d(tag,message);
    }
    public static void Logi(String tag,String message){
        Log.i(tag,message);
    }
    public static void Logv(String tag,String message){
        Log.v(tag,message);
    }
}
