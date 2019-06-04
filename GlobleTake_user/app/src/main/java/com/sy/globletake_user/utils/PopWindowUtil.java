package com.sy.globletake_user.utils;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * Created by sunnetdesign3 on 2016/12/16.
 */
public class PopWindowUtil {
    public static void openPopWindow_Bottm(PopupWindow popupWindow, View contentView) {
        //底部显示
//        popupWindow.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
        popupWindow.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
    }
    public static void openPopWindow_Center(PopupWindow popupWindow, View contentView) {
        //底部显示
      popupWindow.showAtLocation(contentView, Gravity.CENTER,0,0);
    }
    public static void openPopWindow_Top(PopupWindow popupWindow, View contentView) {
        //顶部弹出显示
//        popupWindow.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
        popupWindow.showAtLocation(contentView, Gravity.TOP, 0, 0);
    }

    //屏幕亮度设置
    public static void setscreenDimmed(Activity activity,float num) {
        WindowManager.LayoutParams params = activity.getWindow().getAttributes();
        params.alpha = num;
        activity.getWindow().setAttributes(params);
    }
    public static void closePopwindow(PopupWindow popupWindow, View contentView){
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            popupWindow = null;
        }
    }

    /**
     * 设置添加屏幕的背景透明度
     * **/
    public static void backgroundAlpaha(Activity context, float bgAlpha) {
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        context.getWindow().setAttributes(lp);
    }
}
