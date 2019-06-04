package com.sy.globletake_user.utils;

import android.content.Context;

/**
 * 把dp的长度转换成px长度
 * Created by Administrator on 2016/8/12 0012.
 */
public class DeviceUtil {
    public static float doToPx(Context context,int dp){
        //获取手机屏幕密度:1dp=?px
        float density = context.getResources().getDisplayMetrics().density;
        float px = dp*density+0.5f;
        return px;
    }
}
